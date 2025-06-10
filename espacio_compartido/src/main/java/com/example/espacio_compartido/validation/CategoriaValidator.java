package com.example.espacio_compartido.validation;

import org.springframework.stereotype.Component;
import com.example.espacio_compartido.dto.CategoriaDTO;

@Component
public class CategoriaValidator {



    public void validaNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre no puede estar vacío");
        }
    }
    public void validaDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripcion no puede estar vacío");
        }
    }

    public void validacionCompletaCategoria(CategoriaDTO categoriaDTO) {
        validaNombre(categoriaDTO.getNombre());
        validaDescripcion(categoriaDTO.getDescripcion());
    }

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}