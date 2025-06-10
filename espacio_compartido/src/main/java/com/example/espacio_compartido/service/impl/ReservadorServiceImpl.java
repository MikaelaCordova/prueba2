package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.ReservadorDTO;
import com.example.espacio_compartido.model.Reservador;
import com.example.espacio_compartido.repository.ReservadorRepository;
import com.example.espacio_compartido.service.IReservadorService;
import com.example.espacio_compartido.validation.ReservadorValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservadorServiceImpl implements IReservadorService {

    private final ReservadorRepository reservadorRepository;
    private final ReservadorValidator reservadorValidator;

    @Autowired
    public ReservadorServiceImpl(ReservadorRepository reservadorRepository, ReservadorValidator reservadorValidator) {
        this.reservadorRepository = reservadorRepository;
        this.reservadorValidator = reservadorValidator;
    }

    @Override
    public List<ReservadorDTO> obtenerTodosLosReservadores() {
        return reservadorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservadorDTO obtenerReservadorPorId(Long id) {
        Reservador reservador = reservadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservador no encontrado con ID: " + id));
        return convertToDTO(reservador);
    }

    @Override
    @Transactional
    public ReservadorDTO crearReservador(ReservadorDTO reservadorDTO) {
        reservadorValidator.validacionCompletaReservador(reservadorDTO);

        Reservador reservador = convertToEntity(reservadorDTO);
        Reservador reservadorGuardado = reservadorRepository.save(reservador);

        return convertToDTO(reservadorGuardado);
    }

    @Override
    @Transactional
    public ReservadorDTO actualizarReservador(Long id, ReservadorDTO reservadorDTO) {
        Reservador reservadorExistente = reservadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservador no encontrado con ID: " + id));

        reservadorValidator.validacionCompletaReservador(reservadorDTO);

        reservadorExistente.setNombre(reservadorDTO.getNombre());
        reservadorExistente.setApPaterno(reservadorDTO.getApPaterno());
        reservadorExistente.setApMaterno(reservadorDTO.getApMaterno());
        reservadorExistente.setTipo(reservadorDTO.getTipo());
        reservadorExistente.setCorreoInstitucional(reservadorDTO.getCorreoInstitucional());
        reservadorExistente.setTelefono(reservadorDTO.getTelefono());
        reservadorExistente.setEstadoE(reservadorDTO.getEstadoE());

        Reservador reservadorActualizado = reservadorRepository.save(reservadorExistente);
        return convertToDTO(reservadorActualizado);
    }

    @Override
    @Transactional
    public ReservadorDTO eliminarReservador(Long id) {
        Reservador reservadorExistente = reservadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservador no encontrado con ID: " + id));

        reservadorExistente.setEstadoE(false); // Eliminación lógica
        Reservador reservadorEliminado = reservadorRepository.save(reservadorExistente);

        return convertToDTO(reservadorEliminado);
    }

    @Override
    @Transactional(readOnly = false)
    public Reservador obtenerReservadorConBloqueo(Long id) {
        Reservador reservador = reservadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservador no encontrado con ID: " + id));
        try {
            Thread.sleep(15000); // Simula espera para mostrar bloqueo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return reservador;
    }

    @Override
    @Transactional
    public void eliminarReservadorFisicamente(Long id) {
        Reservador reservadorExistente = reservadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservador no encontrado con ID: " + id));
        reservadorRepository.delete(reservadorExistente);
    }

    private ReservadorDTO convertToDTO(Reservador reservador) {
        return ReservadorDTO.builder()
                .idReservador(reservador.getIdReservador())
                .nombre(reservador.getNombre())
                .apPaterno(reservador.getApPaterno())
                .apMaterno(reservador.getApMaterno())
                .tipo(reservador.getTipo())
                .correoInstitucional(reservador.getCorreoInstitucional())
                .telefono(reservador.getTelefono())
                .estadoE(reservador.getEstadoE())
                .build();
    }

    private Reservador convertToEntity(ReservadorDTO reservadorDTO) {
        return Reservador.builder()
                .idReservador(reservadorDTO.getIdReservador())
                .nombre(reservadorDTO.getNombre())
                .apPaterno(reservadorDTO.getApPaterno())
                .apMaterno(reservadorDTO.getApMaterno())
                .tipo(reservadorDTO.getTipo())
                .correoInstitucional(reservadorDTO.getCorreoInstitucional())
                .telefono(reservadorDTO.getTelefono())
                .estadoE(reservadorDTO.getEstadoE())
                .build();
    }

}
