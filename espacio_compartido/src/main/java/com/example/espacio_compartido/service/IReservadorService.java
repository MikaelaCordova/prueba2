package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.ReservadorDTO;
import com.example.espacio_compartido.model.Reservador;

import java.util.List;

public interface IReservadorService {

    List<ReservadorDTO> obtenerTodosLosReservadores();

    ReservadorDTO obtenerReservadorPorId(Long id);

    ReservadorDTO crearReservador(ReservadorDTO reservadorDTO);

    ReservadorDTO actualizarReservador(Long id, ReservadorDTO reservadorDTO);

    ReservadorDTO eliminarReservador(Long id);

    Reservador obtenerReservadorConBloqueo(Long id);
    
    void eliminarReservadorFisicamente(Long id);

}
