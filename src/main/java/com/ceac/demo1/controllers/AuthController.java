package com.ceac.demo1.controllers;

import com.ceac.demo1.entities.UserModel;
import com.ceac.demo1.services.UserService;
import com.ceac.demo1.services.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService; /// Dependencia: Lógica de negocio que tiene que ver con el usuario.
    private final TokenService tokenService; /// Dependencia: Gestión de tokens (sesiones simples)
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    ///  Registro: Enviar el UserModel con firstName, lastName, email, password
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel body) {
        if (userService.emailExists(body.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("No funciona");
        }
        userService.saveUser(body);
        String token = tokenService.createToken(body.getEmail());

        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel body) {
        var userOpt = userService.findByEmail(body.getEmail());

        if(userOpt.isEmpty() || !userService.matches(body.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("{\"message\":\"Credenciales inválidas\"}");
        }

        String token = tokenService.createToken(body.getEmail());
        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String auth) {

        if (auth != null && auth.startsWith("Bearer ")) {
            tokenService.revoke(auth.substring(7));
        }

        return ResponseEntity.ok("{\"message\":\"Session cerrada\"}");
    }

    /**
     * *****Función Cambiar Contraseña****
     * El front tendrá que introducir su email.
     * Tendremos que validar si ese email existe en la BD
     * para poder modificar las contraseñas.
     *
     * Devolveremos al usuario: "Este email no existe" en caso de que no exista en la BD.
     * Devolveremos al usuario: "Contraseña modificada" en caso de que si exista en la BD.
     */

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserModel body) {
        String email = body.getEmail(); // <- Guardamos el email para validar después
        String newPassword = body.getPassword(); /// <- Guardamos la contraseña nueva para modificarla

        var opt = userService.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("{\"message\":\"Email no existe\"}");

        if (email == null || email.isBlank() || newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("{\"message\":\"Email y contraseña son obligatorios\"}");
        }

        var user = opt.get();
        user.setPassword(encoder.encode(newPassword)); // Encriptamos la paswword
        userService.updateUserbyId(user.getId(), user); // Guardamos el cambio

        return ResponseEntity.ok("{\"message\":\"Contraseña modificada\"}");
    }
}

