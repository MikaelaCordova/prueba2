package com.example.espacio_compartido.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Size(max = 50, message = "El email debe tener máximo 50 caracteres")
    @Email(message = "El formato del email no es válido")
    private String email;
    private String nombre;
    
    private String apellido;

    private Set<String> roles;
    
    private Boolean activo;
}