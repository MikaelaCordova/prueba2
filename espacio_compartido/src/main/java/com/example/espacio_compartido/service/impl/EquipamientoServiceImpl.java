package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.EquipamientoDTO;
import com.example.espacio_compartido.model.Equipamiento;
import com.example.espacio_compartido.repository.EquipamientoRepository;
import com.example.espacio_compartido.service.IEquipamientoService;
import com.example.espacio_compartido.validation.EquipamientoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamientoServiceImpl implements IEquipamientoService {

    private final EquipamientoRepository equipamientoRepository;
    private final EquipamientoValidator equipamientoValidator;

    @Autowired
    public EquipamientoServiceImpl(EquipamientoRepository equipamientoRepository,
                                   EquipamientoValidator equipamientoValidator) {
        this.equipamientoRepository = equipamientoRepository;
        this.equipamientoValidator = equipamientoValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipamientoDTO> obtenerTodosLosEquipamientosActivos() {
        return equipamientoRepository.findByEstadoETrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EquipamientoDTO obtenerEquipamientoPorId(Long idEquipamiento) {
        Equipamiento equipamiento = equipamientoRepository.findByIdEquipamientoAndEstadoETrue(idEquipamiento)
                .orElseThrow(() -> new RuntimeException("Equipamiento no encontrado o inactivo con ID: " + idEquipamiento));
        return convertToDTO(equipamiento);
    }

    @Override
    @Transactional
    public EquipamientoDTO crearEquipamiento(EquipamientoDTO equipamientoDTO) {
        //equipamientoValidator.validarNombreEquipamientoUnico(equipamientoDTO.getNombre());

        Equipamiento equipamiento = convertToEntity(equipamientoDTO);
        equipamiento.setEstadoE(true); // Nuevo equipamiento siempre activo por defecto
        Equipamiento savedEquipamiento = equipamientoRepository.save(equipamiento);
        return convertToDTO(savedEquipamiento);
    }
    /*
    @Override
    @Transactional
    public EquipamientoDTO actualizarEquipamiento(Long idEquipamiento, EquipamientoDTO equipamientoDTO) {
        Equipamiento equipamientoExistente = equipamientoRepository.findById(idEquipamiento)
                .orElseThrow(() -> new RuntimeException("Equipamiento no encontrado con ID: " + idEquipamiento));

        //if (!equipamientoExistente.getNombre().equalsIgnoreCase(equipamientoDTO.getNombre())) {
         //   equipamientoValidator.validarNombreEquipamientoUnico(equipamientoDTO.getNombre());
        //}

        equipamientoExistente.setNombre(equipamientoDTO.getNombre());
        equipamientoExistente.setDescripcion(equipamientoDTO.getDescripcion());
        equipamientoExistente.setEstadoE(equipamientoDTO.getEstadoE());

        Equipamiento updatedEquipamiento = equipamientoRepository.save(equipamientoExistente);
        return convertToDTO(updatedEquipamiento);
    }*/
   @Override
    @Transactional
    public EquipamientoDTO actualizarEquipamiento(Long idEquipamiento, EquipamientoDTO equipamientoDTO) {
        Equipamiento equipamientoExistente = equipamientoRepository.findById(idEquipamiento)
                .orElseThrow(() -> new RuntimeException("Equipamiento no encontrado con ID: " + idEquipamiento));

        /* 
        if (!equipamientoExistente.getNombre().equalsIgnoreCase(equipamientoDTO.getNombre())) {
            equipamientoValidator.validarNombreEquipamientoUnico(equipamientoDTO.getNombre());
        }*/

        equipamientoExistente.setNombre(equipamientoDTO.getNombre());
        equipamientoExistente.setDescripcion(equipamientoDTO.getDescripcion());
        equipamientoExistente.setEstadoE(equipamientoDTO.getEstadoE());
        equipamientoExistente.setImgEquipamiento(equipamientoDTO.getImgEquipamiento()); 

        Equipamiento updatedEquipamiento = equipamientoRepository.save(equipamientoExistente);
        return convertToDTO(updatedEquipamiento);
    }

    @Override
    @Transactional
    public EquipamientoDTO eliminarEquipamiento(Long idEquipamiento) {
        Equipamiento equipamientoExistente = equipamientoRepository.findByIdEquipamientoAndEstadoETrue(idEquipamiento)
                .orElseThrow(() -> new RuntimeException("Equipamiento no encontrado o ya inactivo con ID: " + idEquipamiento));

        equipamientoExistente.setEstadoE(false); 
        Equipamiento deletedEquipamiento = equipamientoRepository.save(equipamientoExistente);
        return convertToDTO(deletedEquipamiento);
    }

    // --- Conversión ---

    private EquipamientoDTO convertToDTO(Equipamiento equipamiento) {
        return EquipamientoDTO.builder()
                .idEquipamiento(equipamiento.getIdEquipamiento())
                .nombre(equipamiento.getNombre())
                .descripcion(equipamiento.getDescripcion())
                .estadoE(equipamiento.getEstadoE())
                .imgEquipamiento(equipamiento.getImgEquipamiento())
                .build();
    }

    private Equipamiento convertToEntity(EquipamientoDTO equipamientoDTO) {
        return Equipamiento.builder()
                .idEquipamiento(equipamientoDTO.getIdEquipamiento())
                .nombre(equipamientoDTO.getNombre())
                .descripcion(equipamientoDTO.getDescripcion())
                .estadoE(equipamientoDTO.getEstadoE())
                .imgEquipamiento(equipamientoDTO.getImgEquipamiento())
                .build();
    }
}