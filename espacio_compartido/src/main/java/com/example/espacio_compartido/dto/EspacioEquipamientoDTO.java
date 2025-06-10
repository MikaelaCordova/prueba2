package com.example.espacio_compartido.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioEquipamientoDTO implements Serializable {

    @NotNull(message = "El ID del espacio es obligatorio")
    private Long idEspacio;

    @NotNull(message = "El ID del equipamiento es obligatorio")
    private Long idEquipamiento;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}