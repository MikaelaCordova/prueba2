package com.example.espacio_compartido.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "espacio")
public class Espacio {
    //prueba

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espacio")
    private Long idEspacio;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "facultad", length = 100)
    private String facultad;

    @Column(name = "carrera", length = 100)
    private String carrera;

    @Column(name = "ubicacion", nullable = false, length = 200)
    private String ubicacion;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "estado", nullable = false)
    private Boolean estado; 
    
    // Relación con Reserva (Un espacio puede tener múltiples reservas) + manejo de cascada
    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    //campo para la imgen
    @Column(name = "img_espacio")
    private String imgEspacio;
}