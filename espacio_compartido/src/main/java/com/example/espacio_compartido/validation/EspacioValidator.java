package com.example.espacio_compartido.validation;

import org.springframework.stereotype.Component;
import com.example.espacio_compartido.dto.EspacioDTO;

@Component
public class EspacioValidator {

    public void validaDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripción no puede estar vacía");
        }
        if (descripcion.length() > 500) {
            throw new BusinessException("La descripción no puede exceder los 500 caracteres");
        }
    }

    public void validaIdCategoria(Long idCategoria) {
        if (idCategoria == null) {
            throw new BusinessException("La categoría es obligatoria");
        }
    }

    public void validaFacultad(String facultad) {
        if (facultad != null && facultad.length() > 100) {
            throw new BusinessException("La facultad no puede exceder los 100 caracteres");
        }
    }

    public void validaCarrera(String carrera) {
        if (carrera != null && carrera.length() > 100) {
            throw new BusinessException("La carrera no puede exceder los 100 caracteres");
        }
    }

    public void validaUbicacion(String ubicacion) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new BusinessException("La ubicación es obligatoria");
        }
        if (ubicacion.length() > 200) {
            throw new BusinessException("La ubicación no puede exceder los 200 caracteres");
        }
    }

    public void validaCapacidad(Integer capacidad) {
        if (capacidad == null) {
            throw new BusinessException("La capacidad es obligatoria");
        }
        if (capacidad <= 0) {
            throw new BusinessException("La capacidad debe ser mayor a 0");
        }
    }

    public void validaEstado(Boolean estado) {
        if (estado == null) {
            throw new BusinessException("El estado es obligatorio");
        }
    }

    public void validacionCompletaEspacio(EspacioDTO espacioDTO) {
        validaDescripcion(espacioDTO.getDescripcion());
        validaIdCategoria(espacioDTO.getIdCategoria());
        validaFacultad(espacioDTO.getFacultad());
        validaCarrera(espacioDTO.getCarrera());
        validaUbicacion(espacioDTO.getUbicacion());
        validaCapacidad(espacioDTO.getCapacidad());
        validaEstado(espacioDTO.getEstado());
    }

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}