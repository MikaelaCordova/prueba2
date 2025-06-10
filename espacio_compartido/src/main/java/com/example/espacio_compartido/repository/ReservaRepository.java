package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.Reserva;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

/*
     * Obtiene todas las reservas activas.
     * @return Lista de reservas activas.
     */
    @Query("SELECT r FROM Reserva r WHERE r.activo = true")
    List<Reserva> findByActivoTrue();

    /*
     * Obtiene una reserva específica por su ID si está activa.
     * @param id ID de la reserva.
     * @return Reserva encontrada.
     */
    @Query("SELECT r FROM Reserva r WHERE r.idReserva = :id AND r.activo = true")
    Optional<Reserva> findByIdAndActivoTrue(@Param("id") Long id);

    /*
     * Obtiene reservas por estado, asegurando que sean activas.
     * @param estado Estado de la reserva (ej. "CONFIRMADA", "CANCELADA").
     * @return Lista de reservas con el estado especificado y activas.
     */
    @Query("SELECT r FROM Reserva r WHERE r.estadoE = :estado AND r.activo = true")
    List<Reserva> findByEstadoEAndActivoTrue(@Param("estado") String estado);

    /*
     * Obtiene reservas activas por espacio y fecha.
     * @param espacioId ID del espacio.
     * @param fechaReserva Fecha de reserva.
     * @return Lista de reservas activas en ese espacio y fecha.
     */
    @Query("SELECT r FROM Reserva r WHERE r.espacio.id = :espacioId AND r.fechaReserva = :fechaReserva AND r.activo = true")
    List<Reserva> findByEspacioIdAndFechaReservaAndActivoTrue(@Param("espacioId") Long espacioId, @Param("fechaReserva") LocalDate fechaReserva);

    /*
     * Obtiene reservas activas por reservador.
     * @param idReservador ID del reservador.
     * @return Lista de reservas activas del reservador.
     */
    @Query("SELECT r FROM Reserva r WHERE r.reservador.id = :idReservador AND r.activo = true")
    List<Reserva> findByIdReservadorAndActivoTrue(@Param("idReservador") Long idReservador);

    /*
     * Obtiene reservas activas por correo del reservador.
     * @param correoReservador Correo institucional del reservador.
     * @return Lista de reservas activas asociadas al correo.
     */
    @Query("SELECT r FROM Reserva r JOIN r.reservador res WHERE res.correoInstitucional = :correoReservador AND r.activo = true")
    List<Reserva> findByReservadorCorreoAndActivoTrue(@Param("correoReservador") String correoReservador);

    /*
     * Obtiene reservas activas por espacio, estado y rango de fechas.
     * @param espacio Espacio reservado.
     * @param estadoE Estado de la reserva.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de reservas activas en ese rango y con el estado indicado.
     */
    @Query("SELECT r FROM Reserva r WHERE r.espacio = :espacio AND r.estadoE = :estadoE AND r.fechaReserva BETWEEN :fechaInicio AND :fechaFin AND r.activo = true")
    List<Reserva> findByEspacioAndEstadoEAndFechaReservaBetweenAndActivoTrue(@Param("espacio") Espacio espacio, @Param("estadoE") String estadoE, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    /*
     * Obtiene reservas activas por espacio y fecha ordenadas por hora de inicio.
     * @param espacio Espacio reservado.
     * @param fecha Fecha de reserva.
     * @return Lista de reservas activas ordenadas por hora de inicio.
     */
    @Query("SELECT r FROM Reserva r WHERE r.espacio = :espacio AND r.fechaReserva = :fecha AND r.activo = true ORDER BY r.horaInicio ASC")
    List<Reserva> findByEspacioAndFechaReservaAndActivoTrueOrderByHoraInicioAsc(@Param("espacio") Espacio espacio, @Param("fecha") LocalDate fecha);



    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reserva r WHERE r.idReserva = :id")
    Optional<Reserva> findByIdWithLock(@Param("id") Long id);
    
    List<Reserva> findByEstadoE(String estado); 

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.espacio.idEspacio = :idEspacio AND r.fechaReserva = :fechaReserva AND r.horaInicio = :horaInicio")
    boolean existeReserva(@Param("idEspacio") Long idEspacio, @Param("fechaReserva") LocalDate fechaReserva, @Param("horaInicio") LocalTime horaInicio);

    @Modifying
    @Query("DELETE FROM Reserva r WHERE r.espacio.idEspacio = :idEspacio")
    void eliminarReservasPorEspacio(@Param("idEspacio") Long idEspacio);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.idEspacio = :espacioId AND r.fechaReserva = :fechaReserva")
    List<Reserva> findByEspacioIdAndFechaReserva(@Param("espacioId") Long espacioId, @Param("fechaReserva") LocalDate fechaReserva);

    //metodo para encontrar las reserva de un idreservador
    @Query("SELECT r FROM Reserva r WHERE r.reservador.id = :idReservador")
    List<Reserva> findByIdReservador(@Param("idReservador") Long idReservador);

    //metodo para obetner reservar en base a un correo
    @Query("SELECT r FROM Reserva r JOIN r.reservador res ON r.reservador.id = res.id WHERE res.correoInstitucional = :correoReservador")
    List<Reserva> findByCorreoReservador(@Param("correoReservador") String correoReservador);

    /* 
    @Query("SELECT r FROM Reserva r " +
        "JOIN r.espacio e " +
        "JOIN e.categoria c " +
        "WHERE (:facultad IS NULL OR :facultad = '' OR e.facultad = :facultad) " +
        "AND (:carrera IS NULL OR :carrera = '' OR e.carrera = :carrera) " +
        "AND (:categoria IS NULL OR :categoria = '' OR c.nombre = :categoria) " +
        "AND (:fecha IS NULL OR r.fechaReserva = :fecha) " +
        "AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR r.fechaReserva BETWEEN :fechaInicio AND :fechaFin) " +
        "AND (:estado IS NULL OR :estado = '' OR r.estadoE = :estado)")
    List<Reserva> filtrarReservas(
        @Param("facultad") String facultad,
        @Param("carrera") String carrera,
        @Param("categoria") String categoria,
        @Param("fecha") LocalDate fecha,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin,
        @Param("estado") String estado
    );*/
    @Query("SELECT r FROM Reserva r " +
            "JOIN r.espacio e " +
            "JOIN e.categoria c " +
            "WHERE (:facultad IS NULL OR :facultad = '' OR e.facultad = :facultad) " +
            "AND (:carrera IS NULL OR :carrera = '' OR e.carrera = :carrera) " +
            "AND (:categoria IS NULL OR :categoria = '' OR c.nombre = :categoria) " +
            "AND (:fecha IS NULL OR r.fechaReserva = :fecha) " +
            "AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR r.fechaReserva BETWEEN :fechaInicio AND :fechaFin) " +
            "AND (:estado IS NULL OR :estado = '' OR r.estadoE = :estado) " +
            "AND r.activo = true") // Filtra solo reservas activas
    List<Reserva> filtrarReservas(
        @Param("facultad") String facultad,
        @Param("carrera") String carrera,
        @Param("categoria") String categoria,
        @Param("fecha") LocalDate fecha,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin,
        @Param("estado") String estado
    );




    List<Reserva> findByEspacioAndEstadoEAndFechaReservaBetween(
        Espacio espacio, String estadoE, LocalDate fechaInicio, LocalDate fechaFin
    );

    List<Reserva> findByEspacioAndFechaReservaOrderByHoraInicioAsc(Espacio espacio, LocalDate fechaReserva);

}

