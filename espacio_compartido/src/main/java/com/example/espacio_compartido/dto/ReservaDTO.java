package com.example.espacio_compartido.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO implements Serializable {
    private Long idReserva;

    @NotNull(message = "El identificador del reservador es obligatorio")
    @Positive(message = "El ID del reservador debe ser un número positivo")
    private Long idReservador;

    @NotNull(message = "El identificador del espacio es obligatorio")
    @Positive(message = "El ID del espacio debe ser un número positivo")
    private Long idEspacio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de creación no puede ser en el futuro")
    private LocalDate fechaCreacion;

    @NotNull(message = "La fecha de reserva es obligatoria")
    //@FutureOrPresent(message = "La fecha de reserva debe ser hoy o futura")
    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate fechaReserva;

    @NotNull(message = "La hora de inicio es obligatoria")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFin;

    @Size(max = 300, message = "El motivo no puede tener más de 300 caracteres")
    private String motivo;

    @NotBlank(message = "El estado de la reserva es obligatorio")
    @Pattern(regexp = "CONFIRMADA|CANCELADA", message = "El estado debe ser CONFIRMADA, CANCELADA")
    private String estadoE;

    private Boolean activo; 
}
