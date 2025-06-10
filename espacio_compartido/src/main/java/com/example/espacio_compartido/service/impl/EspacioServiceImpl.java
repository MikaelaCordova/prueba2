package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.EspacioDTO;
import com.example.espacio_compartido.model.*;
import com.example.espacio_compartido.repository.*;
import com.example.espacio_compartido.service.IEspacioService;
import com.example.espacio_compartido.validation.EspacioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspacioServiceImpl implements IEspacioService {
    //prueba
    private final EspacioRepository espacioRepository;
    private final CategoriaRepository categoriaRepository;
    private final EspacioValidator espacioValidator;

    @Autowired
    public EspacioServiceImpl(EspacioRepository espacioRepository, CategoriaRepository categoriaRepository,EspacioValidator espacioValidator) {
        this.espacioRepository = espacioRepository;
        this.categoriaRepository = categoriaRepository;
        this.espacioValidator = espacioValidator;
    }

    @Override
    public List<EspacioDTO> obtenerTodosLosEspacios() {
        return espacioRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EspacioDTO obtenerEspacioPorId(Long id) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        return convertToDTO(espacio);
    }

    @Override
    @Transactional
    public EspacioDTO crearEspacio(EspacioDTO espacioDTO) {
        espacioValidator.validacionCompletaEspacio(espacioDTO);
        Espacio espacio = convertToEntity(espacioDTO);
        return convertToDTO(espacioRepository.save(espacio));
    }

    @Override
    @Transactional
    public EspacioDTO actualizarEspacio(Long id, EspacioDTO espacioDTO) {
        Espacio espacioExistente = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        
        espacioValidator.validacionCompletaEspacio(espacioDTO);
        
        espacioExistente.setDescripcion(espacioDTO.getDescripcion());
        espacioExistente.setCategoria(categoriaRepository.findById(espacioDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));
        espacioExistente.setFacultad(espacioDTO.getFacultad());
        espacioExistente.setCarrera(espacioDTO.getCarrera());
        espacioExistente.setUbicacion(espacioDTO.getUbicacion());
        espacioExistente.setCapacidad(espacioDTO.getCapacidad());
        espacioExistente.setEstado(espacioDTO.getEstado());
        espacioExistente.setImgEspacio(espacioDTO.getImgEspacio());

        return convertToDTO(espacioRepository.save(espacioExistente));
    }

    @Override
    @Transactional
    public EspacioDTO eliminarEspacio(Long id) {
        Espacio espacioExistente = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        
        espacioExistente.setEstado(false); //Eliminacion logica
        Espacio espacioEliminado = espacioRepository.save(espacioExistente);       
        return convertToDTO(espacioEliminado);
    }

    @Override
    @Transactional(readOnly = true)
    public Espacio obtenerEntidadEspacioPorId(Long id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
    } 

    @Override
    @Transactional
    public void eliminarEspacioFisicamente(Long id) {
        Espacio espacioExistente = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + id));

        espacioRepository.delete(espacioExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioDTO> obtenerEspaciosPorEstado(Boolean estado) {
        return espacioRepository.findByEstado(estado)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EspacioDTO convertToDTO(Espacio espacio) {
        return EspacioDTO.builder()
                .idEspacio(espacio.getIdEspacio())
                .descripcion(espacio.getDescripcion())
                .idCategoria(espacio.getCategoria().getIdCategoria())
                .facultad(espacio.getFacultad())
                .carrera(espacio.getCarrera())
                .ubicacion(espacio.getUbicacion())
                .capacidad(espacio.getCapacidad())
                .estado(espacio.getEstado())
                .imgEspacio(espacio.getImgEspacio())
                .build();
    }

    private Espacio convertToEntity(EspacioDTO dto) {
        return Espacio.builder()
                .idEspacio(dto.getIdEspacio())
                .descripcion(dto.getDescripcion())
                .categoria(categoriaRepository.findById(dto.getIdCategoria())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada")))
                .facultad(dto.getFacultad())
                .carrera(dto.getCarrera())
                .ubicacion(dto.getUbicacion())
                .capacidad(dto.getCapacidad())
                .estado(dto.getEstado())
                .imgEspacio(dto.getImgEspacio())
                .build();
    }

    //---------------------------------------------
    @Override
    @Cacheable(value = "reservasFiltradas")
    @Transactional(readOnly = true)
    public List<EspacioDTO> filtrarEspacios(String facultad, String carrera, String categoria, Integer capacidad) {
        List<Espacio> espacios = espacioRepository.filtrarEspacios(facultad, carrera, categoria, capacidad);
        return espacios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    //--------------------------------------------
    @Override
    public List<Integer> obtenerCapacidadesDisponibles() {
        return espacioRepository.obtenerCapacidadesUnicas();
    }

    
}