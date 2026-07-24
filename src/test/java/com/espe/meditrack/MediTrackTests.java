package com.espe.meditrack;

import com.espe.meditrack.model.Appointment;
import com.espe.meditrack.service.AppointmentService;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediTrackTests {

    @Test
    public void appointment_copiaDefensiva_noDebeCambiarObjetoInterno() {
        // Arrange
        List<String> emails = new ArrayList<>(Arrays.asList("test@espe.edu.ec"));
        Appointment app = new Appointment("1", "P1", "Spec", 10.0, emails);

        // Act
        emails.add("hacker@test.com"); // Intentamos modificar la lista original externa

        // Assert
        Assert.assertEquals(1, app.getNotifyEmails().size());
        Assert.assertNotSame(emails, app.getNotifyEmails());
    }

    @Test
    public void getValidAppointments_debeEmitirSoloLasTresValidas() {
        // Arrange
        AppointmentService service = new AppointmentService();
        // Act
        Flux<Appointment> flujo = service.getValidAppointments();
        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3) // Esperamos las 3 válidas que definimos en el servicio
                .verifyComplete();
    }
}
