package com.example.espacio_compartido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioDisponibilidadDTO implements Serializable {
    //prueba
    private Long espacioId;
    private String nombreEspacio;
    private LocalDate fecha;
    private List<LocalTime> horasDisponibles;
    private List<LocalTime> horasOcupadas;
}
