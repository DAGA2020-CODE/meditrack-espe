package com.espe.meditrack;

import com.espe.meditrack.model.Appointment;
import com.espe.meditrack.service.AppointmentService;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediTrackTests {

    // --- 4.1 Pruebas del Modelo ---
    @Test
    public void getNotifyEmails_listaModificadaExternamente_noCambiaEstadoInterno() {
        // Arrange
        List<String> emails = new ArrayList<>(Arrays.asList("doctor@espe.edu.ec"));
        Appointment app = new Appointment("1", "Paciente", "Cardio", 10.0, emails);

        // Act
        emails.add("hacker@test.com");

        // Assert
        Assert.assertEquals("El tamaño no debe cambiar por ser inmutable", 1, app.getNotifyEmails().size());
        Assert.assertNotSame("No deben ser la misma instancia de lista", emails, app.getNotifyEmails());
    }

    // --- 4.2 Pruebas del Servicio (StepVerifier) ---

    @Test
    public void getValidAppointments_flujoNormal_emiteExactamenteTresCitas() {
        // Arrange
        AppointmentService service = new AppointmentService();
        // Act
        Flux<Appointment> flujo = service.getValidAppointments();
        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void findById_idInexistente_retornaErrorRuntime() {
        // Arrange
        AppointmentService service = new AppointmentService();
        // Act
        Mono<Appointment> resultado = service.findById("ID-QUE-NO-EXISTE");
        // Assert
        StepVerifier.create(resultado)
                .expectError(RuntimeException.class)
                .verify();
    }
}