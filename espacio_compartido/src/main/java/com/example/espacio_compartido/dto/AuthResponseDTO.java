package com.example.espacio_compartido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private List<String> roles;

    public AuthResponseDTO(String token, Long id, String username, String email, String nombre, String apellido, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.roles = roles;
    }
}