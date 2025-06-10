package com.example.espacio_compartido.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservador")
@EqualsAndHashCode
public class Reservador {
//prueba


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservador")
    private Long idReservador;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ap_paterno", nullable = false)
    private String apPaterno;

    @Column(name = "ap_materno", nullable = false)
    private String apMaterno;

    @Column(name = "tipo", length = 20, nullable = false)
    private String tipo;

    @Column(name = "correo_institucional", nullable = false, unique = true)
    private String correoInstitucional;

    @Column(name = "telefono", length = 8, nullable = false)
    private String telefono;

    @Column(name = "estado_e", nullable = false)
    private Boolean estadoE;

    // Relación con Reserva (Un reservador puede tener múltiples reservas) + el manejo de casacada
    @OneToMany(mappedBy = "reservador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

}
