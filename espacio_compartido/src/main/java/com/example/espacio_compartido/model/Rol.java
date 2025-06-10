package com.example.espacio_compartido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private RolNombre nombre;

    public enum RolNombre {
        ROLE_ADMIN,    // Acceso completo a todo el sistema
        ROLE_ENCARGADO // Acceso restringido (solo reservas y consultas)
    }
}
