package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.EquipamientoDTO;
import java.util.List;

public interface IEquipamientoService {
    List<EquipamientoDTO> obtenerTodosLosEquipamientosActivos();
    EquipamientoDTO obtenerEquipamientoPorId(Long idEquipamiento);
    EquipamientoDTO crearEquipamiento(EquipamientoDTO equipamientoDTO);
    EquipamientoDTO actualizarEquipamiento(Long idEquipamiento, EquipamientoDTO equipamientoDTO);
    EquipamientoDTO eliminarEquipamiento(Long idEquipamiento);
}