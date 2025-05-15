package com.example.demo.Controller;

import com.example.demo.dto.CreateAppointmentRequest;
import com.example.demo.entities.Appointment;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.service.AppointmentService;
import com.example.demo.repository.AppointmentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {


    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    private AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody CreateAppointmentRequest req) {
        Appointment saved = appointmentService.createAppointment(
            req.getClient(), req.getDate(), req.getUserId(), req.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PostMapping("/submit")
    public ResponseEntity<String> submitAppointment(@RequestBody Appointment appointment) {
        System.out.println("Received appointment: " + appointment);

        if (appointment.getClient() == null || appointment.getDate() == null) {
            return ResponseEntity.badRequest().body("Missing required fields!");
        }

        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Appointment saved successfully!");
    }
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentRepository.findAll());
    }
    }