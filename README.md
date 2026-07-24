# 🩺 MediTrack — Servicio Reactivo de Citas Médicas

Este proyecto es una implementación de un servicio de gestión de citas médicas basado en **Programación Reactiva** utilizando **Spring WebFlux** y **Project Reactor**. El objetivo principal es aplicar conceptos de inmutabilidad, operadores reactivos y pruebas unitarias no bloqueantes.

## 🎯 Objetivos del Proyecto
*   **Reactividad:** Exposición de flujos de datos asíncronos mediante `Flux` y `Mono`.
*   **Inmutabilidad:** Creación de un modelo de dominio 100% inmutable con copias defensivas.
*   **Testing:** Validación de flujos reactivos mediante `StepVerifier` y el patrón **AAA** (Arrange-Act-Assert).
*   **Git Flow:** Gestión de desarrollo mediante ramas por funcionalidad y commits semánticos.

## 🚀 Tecnologías Utilizadas
*   **Java 17**
*   **Spring Boot 3.3.0** (Spring WebFlux)
*   **Project Reactor**
*   **JUnit 4** & **Reactor Test**
*   **Maven**

## 🛠️ Estructura del Proyecto
El proyecto sigue una arquitectura limpia organizada por paquetes dentro de `com.espe.meditrack`:
- `model`: Entidades inmutables (`Appointment`).
- `service`: Lógica de negocio reactiva (`AppointmentService`).
- `controller`: Endpoints REST reactivos (`AppointmentController`).

## 📋 Reglas de Negocio
Una cita médica se considera **válida** solo si:
1.  El costo de la consulta (`costUsd`) es mayor a **0**.
2.  La lista de correos de notificación (`notifyEmails`) **no está vacía**.

## ⚙️ Operadores Reactivos Implementados
En el `AppointmentService` se utilizan los siguientes operadores justificadamente:
- `.filter()`: Filtra las citas según las reglas de negocio.
- `.map()`: Transforma la especialidad médica a mayúsculas.
- `.defaultIfEmpty()`: Emite una cita genérica si el flujo queda vacío tras el filtrado.
- `.switchIfEmpty()`: Maneja el error de "Cita no encontrada" de forma no bloqueante.

## 🧪 Pruebas Unitarias
Se han implementado pruebas bajo el patrón **AAA**:
*   **Modelo:** Verificación de inmutabilidad y copias defensivas.
*   **Servicio:** Pruebas de flujo con `StepVerifier` cubriendo casos de éxito, flujos vacíos y manejo de errores.

Para ejecutar las pruebas, utiliza el comando:
```bash
mvn test
🖥️ Ejecución y Endpoints
Para iniciar el servicio localmente:
code
Bash
./mvnw spring-boot:run
Endpoints disponibles (vía cURL):
Obtener todas las citas válidas:
code
Bash
curl http://localhost:8080/api/appointments
Buscar cita por ID:
code
Bash
curl http://localhost:8080/api/appointments/A1
