package com.example.demo.Controller;

import com.example.demo.entities.User;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Inscription avec envoi d’email
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    // Connexion simple (on peut plus tard ajouter un token JWT)
    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        return authService.login(loginUser.getEmail(), loginUser.getPassword());
    }

    // Confirmation de compte via lien reçu par email
    @GetMapping("/confirm")
    public String confirm(@RequestParam String token) {
        return authService.confirmToken(token);
    }
}
