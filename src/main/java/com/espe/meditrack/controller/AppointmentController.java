package com.espe.meditrack.controller;

import com.espe.meditrack.model.Appointment;
import com.espe.meditrack.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Appointment> getAll() {
        return service.getValidAppointments();
    }

    @GetMapping("/{id}")
    public Mono<Appointment> getById(@PathVariable String id) {
        return service.findById(id);
    }
}