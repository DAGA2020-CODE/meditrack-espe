package com.espe.meditrack.service;

import com.espe.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class AppointmentService {

    public Flux<Appointment> getValidAppointments() {
        // Generamos 5 citas (3 válidas, 2 inválidas)
        Appointment v1 = new Appointment("A1", "Juan", "Cardiología", 50.0, Arrays.asList("a@a.com"));
        Appointment v2 = new Appointment("A2", "Ana", "Pediatría", 30.0, Arrays.asList("b@b.com"));
        Appointment v3 = new Appointment("A3", "Luis", "Dermatología", 45.0, Arrays.asList("c@c.com"));
        Appointment i1 = new Appointment("I1", "Error 1", "General", 0.0, Arrays.asList("d@d.com")); // costUsd = 0
        Appointment i2 = new Appointment("I2", "Error 2", "Dental", 20.0, List.of()); // notifyEmails vacío

        return Flux.just(v1, v2, v3, i1, i2)
                // filter: Se usa para aplicar la regla de negocio y descartar citas inválidas
                .filter(a -> a.getCostUsd() > 0 && !a.getNotifyEmails().isEmpty())
                // map: Transforma los datos; en este caso pasamos la especialidad a mayúsculas como se pide
                .map(a -> new Appointment(a.getId(), a.getPatientName(), a.getSpecialty().toUpperCase(), a.getCostUsd(), a.getNotifyEmails()))
                // defaultIfEmpty: Si tras el filtro no queda nada, emite una cita genérica de respaldo
                .defaultIfEmpty(new Appointment("0", "GENERICO", "GENERAL", 1.0, Arrays.asList("admin@meditrack.com")));
    }

    public Mono<Appointment> findById(String id) {
        return getValidAppointments()
                .filter(a -> a.getId().equals(id))
                .next() // Toma el primer elemento que coincida
                // switchIfEmpty: Maneja de forma reactiva el caso donde no se encuentra el ID sin usar if/else bloqueantes
                .switchIfEmpty(Mono.error(new RuntimeException("Cita no encontrada con ID: " + id)));
    }
}