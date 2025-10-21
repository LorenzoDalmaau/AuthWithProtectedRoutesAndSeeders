package com.ceac.demo1.config;

import com.ceac.demo1.services.auth.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación que se ejecuta una vez por petición.
 * - Omite endpoints públicos (p. ej. /auth/**, /actuator/**).
 * - Para el resto, exige un token válido en el header Authorization.
 * - Si el token es válido, adjunta el email del usuario a la request.
 * - Si no lo es, devuelve 401 con un cuerpo JSON mínimo.
 */
@Component
@Order(1) // Prioridad del filtro en la cadena (menor número = mayor prioridad).
public class AuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization"; // evita "stringly-typed"
    private static final String REQ_ATTR_USER_EMAIL = "userEmail";

    private final TokenService tokenService;

    // AntPathMatcher permite patrón tipo /ruta/** para decidir si una URL es pública.
    private final AntPathMatcher matcher = new AntPathMatcher();

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Determina si la petición apunta a un endpoint público.
     * Ajusta aquí las rutas que no requieren autenticación.
     */
    private boolean isPublic(HttpServletRequest req) {
        String path = req.getRequestURI();
        // Endpoints públicos. Añade aquí health checks, swagger, etc. si procede.
        return matcher.match("/auth/**", path) || matcher.match("/actuator/**", path);
    }

    /**
     * Lógica principal del filtro:
     * - Deja pasar si es público.
     * - Si no, intenta validar el token y propagar el email de usuario.
     * - Si falla, corta con 401 y JSON.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // OJO: aquí se espera el header Authorization. Idealmente "Bearer <token>"
            String authHeader = request.getHeader(AUTH_HEADER);

            // TokenService se encarga de validar y devolver el email (o lanza excepción si no es válido).
            String email = tokenService.requireUser(authHeader);

            // Dejamos el email accesible aguas abajo (controladores, etc.).
            request.setAttribute(REQ_ATTR_USER_EMAIL, email);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // No damos detalles del motivo para no filtrar info sensible.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\":\"Debes iniciar sesión para continuar.\"}");
        }
    }
}
