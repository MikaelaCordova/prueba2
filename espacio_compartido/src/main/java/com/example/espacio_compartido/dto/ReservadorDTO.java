package com.example.espacio_compartido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import jakarta.validation.constraints.*;

/**
 * DTO para transferencia de datos de Reservador con validaciones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservadorDTO implements Serializable {

    private Long idReservador;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String apMaterno;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "docente|estudiante|administrativo", 
             message = "El tipo debe ser docente, estudiante o administrativo")
    private String tipo;

    @NotBlank(message = "El correo institucional es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@umsa\\.bo$", 
             message = "El correo debe ser institucional terminando en @umsa.bo")
    private String correoInstitucional;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El teléfono debe tener exactamente 8 dígitos")
    private String telefono;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estadoE;
}
