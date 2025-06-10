package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.dto.HorarioDisponibilidadDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {
//prueba
    /** ahsbjhasba
     * Obtiene todas las reservas registradas.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerTodasLasReservas(); 

    /**
     * Obtiene una reserva específica por su ID.
     * @param id ID de la reserva.
     * @return ReservaDTO encontrada.
     */
    ReservaDTO obtenerReservaPorId(Long id); 
    /*
     * Obtener reservas por un estado especifico
     * @param string del estado solicitado
     * @return lista de reservasdto encontradas
     */
    List<ReservaDTO> obtenerReservasPorEstado(String estado);


    /**
     * Crea una nueva reserva, validando disponibilidad y datos antes de guardarla.
     * asegurar de que pueda recibir varios horarios
     * @param reservaDTO DTO de la reserva a crear.
     * @return ReservaDTO creada.
     * @throws RuntimeException si el espacio no existe o hay solapamiento de horarios.
     */
    ReservaDTO crearReserva(@Valid ReservaDTO reservaDTO); 

    /**
     * Modifica una reserva existente, validando disponibilidad y datos antes de actualizar.
     * @param id ID de la reserva a actualizar.
     * @param reservaDTO DTO con los nuevos datos.
     * @return ReservaDTO actualizada.
     * @throws RuntimeException si la reserva no existe o hay solapamiento de horarios.
     */
    ReservaDTO modificarReserva(Long id, @Valid ReservaDTO reservaDTO); 

    /**
     * Elimina una reserva por su ID.
     * @param id ID de la reserva a eliminar.
     * @throws RuntimeException si la reserva no existe.
     */
    void eliminarReserva(Long id);
    /*
     * borrar una rserva del frontend
     * @param id ID de la reserva a eliminar.
     */
    void desactivarReserva(Long id);
    /**
     * Obtiene las reservas de un espacio para una fecha específica.
     * @param espacioId ID del espacio.
     * @param fecha Fecha de la reserva.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorEspacioYFecha(Long espacioId, LocalDate fecha);

    /**
     * Obtiene reservas por reservador, ya sea por ID o por nombre.
     * @param idReservador ID del reservador.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorIdReservador(Long idReservador);

    /**
     * Obtiene reservas por nombre de reservador.
     * @param nombreReservador Nombre del reservador.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorCorreo(String nombreReservador);

    /**
     * Obtiene las reservas de un espacio dentro de un rango de fechas.
     * @param espacioId ID del espacio.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorEspacioYRangoFechas(Long espacioId, LocalDate fechaInicio, LocalDate fechaFin);



    /**   EL MAS COMPLEJO :(
     * Obtiene los horarios disponibles en un espacio para una fecha específica.
     * Este método permitirá calcular los intervalos libres, evitando conflictos de reserva.
     * @param espacioId ID del espacio.
     * @param fecha Fecha de consulta.
     * @return HorarioDisponibilidadDTO con las horas libres y ocupadas.
     */
    HorarioDisponibilidadDTO obtenerHorariosDisponibles(Long espacioId, LocalDate fecha); 

    /**
 * Filtra reservas por facultad, carrera, categoría, fecha exacta, rango y estado.
 * Todos los filtros son opcionales y combinables.
 * 
 * @param facultad Nombre de la facultad.
 * @param carrera Nombre de la carrera.
 * @param categoria Nombre de la categoría.
 * @param fecha Fecha exacta (opcional si se usa rango).
 * @param rango "HOY", "SEMANA", "MES", etc.
 * @param estado Estado de la reserva ("CONFIRMADA", "CANCELADA").
 * @return Lista de ReservaDTO con las reservas que coinciden.
 */
List<ReservaDTO> filtrarReservas(String facultad, String carrera, String categoria, LocalDate fecha, String rango, String estado);


}
