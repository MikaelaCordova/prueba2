package com.example.espacio_compartido.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservador", nullable = false)
    @NotNull(message = "El reservador es obligatorio")
    private Reservador reservador; // Referencia al usuario que hace la reserva

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_espacio", nullable = false)
    @NotNull(message = "El espacio es obligatorio para la reserva")
    private Espacio espacio; // Referencia al espacio reservado

    @Column(name = "fecha_creacion", nullable = false)
    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDate fechaCreacion; // Fecha en que se hizo la reserva

    @Column(name = "fecha", nullable = false)
    //@FutureOrPresent(message = "La fecha de reserva debe ser hoy o futura")
    @NotNull(message = "La fecha de la reserva es obligatoria")
    private LocalDate fechaReserva; // Fecha en la que se utilizará el espacio

    @Column(name = "hora_inicio", nullable = false)
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio; // Horario de inicio de la reserva

    @Column(name = "hora_fin", nullable = false)
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin; // Horario de finalización de la reserva

    @Column(name = "motivo", length = 255)
    private String motivo; // Propósito de la reserva

    @Column(name = "estado_e", nullable = false, length = 20)
    private String estadoE; // Estado de la reserva

    @Column(name = "activo", nullable = false)
    private boolean activo = true; // Indica si la reserva está activa (true) o eliminada lógicamente (false)
}
