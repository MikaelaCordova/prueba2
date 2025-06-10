package com.example.espacio_compartido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "espacio_equipamiento")
public class EspacioEquipamiento {

    @EmbeddedId
    private EspacioEquipamientoId id; // Clave primaria compuesta

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEspacio") 
    @JoinColumn(name = "id_espacio", referencedColumnName = "id_espacio", insertable = false, updatable = false)
    private Espacio espacio; 

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEquipamiento") 
    @JoinColumn(name = "id_equipamiento", referencedColumnName = "id_equipamiento", insertable = false, updatable = false)
    private Equipamiento equipamiento;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}