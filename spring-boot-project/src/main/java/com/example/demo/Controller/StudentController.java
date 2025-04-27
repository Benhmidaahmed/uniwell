package com.example.demo.Controller;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class StudentController {
    private final UserRepository userRepository;

    public StudentController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @GetMapping("/students")
    public ResponseEntity<List<User>> getStudents() {
        List<User> students = userRepository.findByRolesContaining(Role.STUDENT);
        return ResponseEntity.ok(students);
    }
}

