package com.example.espacio_compartido.dto;

import java.io.Serializable;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioDTO implements Serializable {

    private Long idEspacio;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    @Size(max = 100, message = "La facultad no puede exceder los 100 caracteres")
    private String facultad;

    @Size(max = 100, message = "La carrera no puede exceder los 100 caracteres")
    private String carrera;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 200, message = "La ubicación no puede exceder los 200 caracteres")
    private String ubicacion;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @NotNull(message = "El estado de esapacio es obligatorio")
    private Boolean estado;

    //campo para la imagen
    private String imgEspacio;
}