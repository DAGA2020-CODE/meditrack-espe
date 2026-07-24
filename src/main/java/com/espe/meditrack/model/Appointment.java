package com.espe.meditrack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Clase final para que no sea heredada (Inmutable)
public final class Appointment {
    private final String id;
    private final String patientName;
    private final String specialty;
    private final Double costUsd;
    private final List<String> notifyEmails;

    public Appointment(String id, String patientName, String specialty, Double costUsd, List<String> notifyEmails) {
        this.id = id;
        this.patientName = patientName;
        this.specialty = specialty;
        this.costUsd = costUsd;
        // Copia defensiva: creamos una nueva lista para que cambios externos no afecten a la interna
        this.notifyEmails = notifyEmails != null ? new ArrayList<>(notifyEmails) : new ArrayList<>();
    }

    public String getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getSpecialty() { return specialty; }
    public Double getCostUsd() { return costUsd; }

    public List<String> getNotifyEmails() {
        // Devolvemos una lista de solo lectura para mantener la inmutabilidad
        return Collections.unmodifiableList(notifyEmails);
    }
}