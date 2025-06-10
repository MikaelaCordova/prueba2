package com.example.espacio_compartido.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase Embeddable que representa la clave primaria compuesta para la entidad EspacioEquipamiento.
 * Contiene los IDs del espacio y del equipamiento para definir una relación many-to-many.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode 
@NoArgsConstructor
@AllArgsConstructor
public class EspacioEquipamientoId implements Serializable {

    @Column(name = "id_espacio")
    private Long idEspacio;

    @Column(name = "id_equipamiento")
    private Long idEquipamiento;
}