package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.EspacioDTO;
import com.example.espacio_compartido.model.Espacio;
import java.util.List;

public interface IEspacioService {
    //prueba
    List<EspacioDTO> obtenerTodosLosEspacios();
    EspacioDTO obtenerEspacioPorId(Long id);
    EspacioDTO crearEspacio(EspacioDTO espacioDTO);
    EspacioDTO actualizarEspacio(Long id, EspacioDTO espacioDTO);
    EspacioDTO eliminarEspacio(Long id);
    void eliminarEspacioFisicamente(Long id);
    Espacio obtenerEntidadEspacioPorId(Long id);
    List<EspacioDTO> obtenerEspaciosPorEstado(Boolean estado);

    //------------------
    /**
        * arruine yooo
     * Filtra reservas por facultad, carrera, categoría, fecha exacta o rango.
     * Todos los filtros son opcionales y combinables.
     * 
     * @param facultad Nombre de la facultad.
     * @param carrera Nombre de la carrera.
     * @param categoria Nombre de la categoría.
     * @param fecha Fecha exacta (opcional si se usa rango).
     * @param rango "HOY", "SEMANA", "MES", etc.
     * @return Lista de ReservaDTO con las reservas que coinciden.
     */
   
    List<EspacioDTO> filtrarEspacios(String facultad, String carrera, String categoria, Integer capacidad);    
    /**
     * Obtiene todas las capacidades únicas disponibles en los espacios.
     * Útil para filtros y estadísticas.
     * 
     * @return Lista de números representando las capacidades distintas.
     */
    List<Integer> obtenerCapacidadesDisponibles();
    
    
    
}