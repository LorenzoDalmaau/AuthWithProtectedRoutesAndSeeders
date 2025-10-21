package com.ceac.demo1.services.auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    /// token -> email
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    ///  UUID.randomUUID() genera un identificador único universal (por ejemplo: tcfhvgbhnjmkl213425)
    /// Lo convertimos a texto con .toString()
    /// Lo guardamos en el mapa session, asociado al email asociado que acaba de autenticarse.
    /// Devolvemos el token para enviarlo como respuesta al cliente (por ejemplo, al hacer /auth/login)
    public String createToken(String email) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, email);
        return token;
    }

    /// Busca en el mapa si esxiste ese token.
    public String getEmail(String token) {
        return sessions.get(token);
    }

    ///  Eliminamos el token del mapa sessions.
    /// Se usa en el logout principalmente para "cerrar sesión"
    /// Después de hacer logout, ese token deja de ser válido y ya no podrá usarse.
    public void revoke(String token) {
        sessions.remove(token);
    }

    public String requireUser(String authorizationHeader) {
        /// Si el header Autorization no existe o no empieza con "Bearer " (¡espacio incluido!)
        /// ... se lanza una excepción 401 Unathorized.
        ///  El mensaje "Debes iniciar sesión para continuar." se devuelve al cliente en formato JSON
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión para continuar.");

        ///Cortar la palabra "Bearer " del inicio del header (que tiene 7 caracter).
        /// Dejamos solo el token
        String token = authorizationHeader.substring(7);

        ///  Buscar el email asociado a ese token en el mapa sessions
        /// Si el no token, getEmail() devuelve null;
        String email = getEmail(token);

        ///  Si el token no existe en el mapa (por ejemplo, porque el usuario no hizo login, o logout)
        if (email == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o expirado");

        return email;

        ///  Si todo va bien, devuelve el mail del usuario autenticado.
        /// Este valor puede ser usado en los controladores si se necesita saber quién está realizando la acción.
    }
}