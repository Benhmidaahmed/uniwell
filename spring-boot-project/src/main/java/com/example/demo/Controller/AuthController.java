package com.example.demo.Controller;

import com.example.demo.entities.Role;
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
    @PostMapping("/user/register")
    public String registerUserEP(@RequestBody User user) {
        return authService.register(user,Role.USER);
    }

    @PostMapping("/admin/register")
    public String registerAdminEp(@RequestBody User user) {
        return authService.register(user,Role.ADMIN);
    }

    @PostMapping("/psy/register")
    public String registerPsyEp(@RequestBody User user) {
        return authService.register(user,Role.Psy);
    }

    @PostMapping("/student/register")
    public String registerStudentEp(@RequestBody User user) {
        return authService.register(user,Role.STUDENT);
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
