package com.example.espacio_compartido.validation;

import com.example.espacio_compartido.repository.EquipamientoRepository;
import org.springframework.stereotype.Component;

@Component
public class EquipamientoValidator {

    private final EquipamientoRepository equipamientoRepository;

    public EquipamientoValidator(EquipamientoRepository equipamientoRepository) {
        this.equipamientoRepository = equipamientoRepository;
    }
/* 
    public void validarNombreEquipamientoUnico(String nombre) {
        if (equipamientoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new RuntimeException("Ya existe un equipamiento con el nombre: " + nombre);
        }
    }*/


}