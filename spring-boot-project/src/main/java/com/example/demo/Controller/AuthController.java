package com.example.demo.Controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.PsychologistRegisterRequest;
import com.example.demo.dto.StudentRegisterRequest;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Inscription avec envoi d’email
   
    @PostMapping("/student/register")
    public String registerStudent(@RequestBody StudentRegisterRequest request) {
        User user = new User();
        // Set common fields
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setNumTel(request.getPhoneNumber());
        
        // Set student-specific fields
        user.setStudentCardNumber(request.getStudentCardNumber());
        user.setUniversity(request.getUniversity());
        user.setStudyLevel(request.getStudyLevel());
        
        return authService.register(user, Role.STUDENT);
    }

    @PostMapping("/psychologist/register")
    public String registerPsychologist(@RequestBody PsychologistRegisterRequest request) {
        User user = new User();
        // Set common fields
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setNumTel(request.getPhoneNumber());
        
        // Set psychologist-specific fields
        user.setAdeliNumber(request.getAdeliNumber());
        user.setSpecialization(request.getSpecialization());
        
        return authService.register(user, Role.Psy);
    }


    // Connexion simple (on peut plus tard ajouter un token JWT)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Modifiez pour retourner plus d'informations
            Map<String, Object> response = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Confirmation de compte via lien reçu par email
    @GetMapping("/confirm")
    public String confirm(@RequestParam String token) {
        return authService.confirmToken(token);
    }
}
