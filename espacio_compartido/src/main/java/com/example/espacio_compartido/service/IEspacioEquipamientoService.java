package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.EspacioEquipamientoDTO;
import com.example.espacio_compartido.model.EspacioEquipamiento;

import java.util.List;

public interface IEspacioEquipamientoService {

    EspacioEquipamientoDTO asociarEquipamientoAEspacio(EspacioEquipamientoDTO espacioEquipamientoDTO);

    EspacioEquipamientoDTO obtenerEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento);

    List<EspacioEquipamientoDTO> obtenerEquipamientosPorIdEspacio(Long idEspacio); 

    void desasociarEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento);

    /*DEATELLE */
List<EspacioEquipamiento> listarPorIdEspacio(Long idEspacio);}