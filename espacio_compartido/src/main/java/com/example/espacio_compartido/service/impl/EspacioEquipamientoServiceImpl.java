package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.EspacioEquipamientoDTO;
import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.Equipamiento;
import com.example.espacio_compartido.model.EspacioEquipamiento;
import com.example.espacio_compartido.model.EspacioEquipamientoId;
import com.example.espacio_compartido.repository.EquipamientoRepository;
import com.example.espacio_compartido.repository.EspacioEquipamientoRepository;
import com.example.espacio_compartido.repository.EspacioRepository; 
import com.example.espacio_compartido.service.IEspacioEquipamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspacioEquipamientoServiceImpl implements IEspacioEquipamientoService {

    private final EspacioEquipamientoRepository espacioEquipamientoRepository;
    private final EspacioRepository espacioRepository;
    private final EquipamientoRepository equipamientoRepository;

    @Autowired
    public EspacioEquipamientoServiceImpl(EspacioEquipamientoRepository espacioEquipamientoRepository,
                                          EspacioRepository espacioRepository,
                                          EquipamientoRepository equipamientoRepository) {
        this.espacioEquipamientoRepository = espacioEquipamientoRepository;
        this.espacioRepository = espacioRepository;
        this.equipamientoRepository = equipamientoRepository;
    }

    @Override
    @Transactional
    public EspacioEquipamientoDTO asociarEquipamientoAEspacio(EspacioEquipamientoDTO espacioEquipamientoDTO) {
        Espacio espacio = espacioRepository.findById(espacioEquipamientoDTO.getIdEspacio())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + espacioEquipamientoDTO.getIdEspacio()));

        if (!espacio.getEstado()) {
            throw new RuntimeException("No se puede asociar equipamiento: El espacio con ID " + espacio.getIdEspacio() + " está inactivo.");
        }

        Equipamiento equipamiento = equipamientoRepository.findByIdEquipamientoAndEstadoETrue(espacioEquipamientoDTO.getIdEquipamiento())
                .orElseThrow(() -> new RuntimeException("Equipamiento no encontrado o inactivo con ID: " + espacioEquipamientoDTO.getIdEquipamiento()));

        EspacioEquipamientoId id = new EspacioEquipamientoId(espacioEquipamientoDTO.getIdEspacio(), espacioEquipamientoDTO.getIdEquipamiento());
        Optional<EspacioEquipamiento> existingAssociation = espacioEquipamientoRepository.findById(id);

        EspacioEquipamiento espacioEquipamiento;
        if (existingAssociation.isPresent()) {
            espacioEquipamiento = existingAssociation.get();
            espacioEquipamiento.setCantidad(espacioEquipamientoDTO.getCantidad());
        } else {
            espacioEquipamiento = EspacioEquipamiento.builder()
                    .id(id)
                    .espacio(espacio)  //agregado
                    .equipamiento(equipamiento)  //agregado
                    .cantidad(espacioEquipamientoDTO.getCantidad())
                    .build();
        }

        EspacioEquipamiento savedAssociation = espacioEquipamientoRepository.save(espacioEquipamiento);
        return convertToDTO(savedAssociation);
    }

    @Override
    @Transactional(readOnly = true)
    public EspacioEquipamientoDTO obtenerEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento) {
        EspacioEquipamientoId id = new EspacioEquipamientoId(idEspacio, idEquipamiento);
        EspacioEquipamiento association = espacioEquipamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación de Equipamiento no encontrada para Espacio ID: " + idEspacio + " y Equipamiento ID: " + idEquipamiento));
        return convertToDTO(association);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioEquipamientoDTO> obtenerEquipamientosPorIdEspacio(Long idEspacio) {
        espacioRepository.findById(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + idEspacio));
        return espacioEquipamientoRepository.findById_IdEspacio(idEspacio).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void desasociarEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento) {
        EspacioEquipamientoId id = new EspacioEquipamientoId(idEspacio, idEquipamiento);
        if (!espacioEquipamientoRepository.existsById(id)) {
            throw new RuntimeException("Asociación de Equipamiento no encontrada para desasociar: Espacio ID: " + idEspacio + " y Equipamiento ID: " + idEquipamiento);
        }
        espacioEquipamientoRepository.deleteById(id);
    }

    // --- Métodos Auxiliares de Conversión ---

    private EspacioEquipamientoDTO convertToDTO(EspacioEquipamiento espacioEquipamiento) {
        return EspacioEquipamientoDTO.builder()
                .idEspacio(espacioEquipamiento.getId().getIdEspacio())
                .idEquipamiento(espacioEquipamiento.getId().getIdEquipamiento())
                .cantidad(espacioEquipamiento.getCantidad())
                .build();
    }

    /*DETALLE */
    public List<EspacioEquipamiento> listarPorIdEspacio(Long idEspacio) {
        return espacioEquipamientoRepository.findByEspacioIdEspacio(idEspacio);
    }


}