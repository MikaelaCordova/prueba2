package com.example.espacio_compartido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamientoDTO implements Serializable {

    private Long idEquipamiento; 

    @NotBlank(message = "El nombre del equipamiento es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El estado del equipamiento es obligatorio  :)")
    private Boolean estadoE;

    private String imgEquipamiento;
    
}