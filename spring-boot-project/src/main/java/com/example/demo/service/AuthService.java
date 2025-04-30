package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public String register(User user,Role role) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return "Email already registered.";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(role);
        user.setEnabled(false);
        userRepo.save(user);

        ConfirmationToken token = tokenService.createToken(user);

        String link = "http://localhost:8084/api/auth/confirm?token=" + token.getToken();
        String body = "Clique ici pour confirmer ton compte :\n" + link;

        emailService.sendEmail(user.getEmail(), "Confirmation de compte", body);

        return "Inscription réussie ! Vérifie ton email pour confirmer ton compte.";
    }



    public String login(String email, String password) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        return "Connexion réussie !";
    }

    public String confirmToken(String tokenValue) {
        ConfirmationToken token = tokenService.getToken(tokenValue);

        if (token == null || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Lien invalide ou expiré.";
        }

        User user = token.getUser();
        user.setEnabled(true);
        userRepo.save(user);
        tokenService.deleteToken(token);

        return "Compte confirmé avec succès !";
    }
}
