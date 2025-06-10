package com.example.espacio_compartido.validation;

import com.example.espacio_compartido.dto.UsuarioDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UsuarioValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UsuarioDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsuarioDTO usuario = (UsuarioDTO) target;
        
        // Validar username
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "El nombre de usuario es obligatorio");
        if (usuario.getUsername() != null && (usuario.getUsername().length() < 3 || usuario.getUsername().length() > 20)) {
            errors.rejectValue("username", "field.size", "El nombre de usuario debe tener entre 3 y 20 caracteres");
        }
        
        // Validar password
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "La contraseña es obligatoria");
        if (usuario.getPassword() != null && (usuario.getPassword().length() < 6 || usuario.getPassword().length() > 40)) {
            errors.rejectValue("password", "field.size", "La contraseña debe tener entre 6 y 40 caracteres");
        }
        
        // Validar email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "El email es obligatorio");
        if (usuario.getEmail() != null && !usuario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.rejectValue("email", "field.pattern", "Formato de email inválido");
        }
        if (usuario.getNombre() != null && usuario.getNombre().length() > 50) {
            errors.rejectValue("nombre", "field.size", "El nombre debe tener máximo 50 caracteres");
        }

        if (usuario.getApellido() != null && usuario.getApellido().length() > 50) {
            errors.rejectValue("apellido", "field.size", "El apellido debe tener máximo 50 caracteres");
        }
    }
}