package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.ReservadorDTO;
import com.example.espacio_compartido.model.Reservador;
import com.example.espacio_compartido.service.IReservadorService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/reservador")
@Validated
public class ReservadorController {

    private final IReservadorService reservadorService;
    private static final Logger logger = LoggerFactory.getLogger(ReservadorController.class);

    @Autowired
    public ReservadorController(IReservadorService reservadorService) {
        this.reservadorService = reservadorService;
    }

    @GetMapping
    public ResponseEntity<List<ReservadorDTO>> obtenerTodosLosReservadores() {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVADOR] Inicio obtenerTodosLosReservadores: {}", inicio);
        List<ReservadorDTO> reservadores = reservadorService.obtenerTodosLosReservadores();
        long fin = System.currentTimeMillis();
        logger.info("[RESERVADOR] Fin obtenerTodosLosReservadores: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(reservadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservadorDTO> obtenerReservadorPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVADOR] Inicio obtenerReservadorPorId: {}", inicio);
        ReservadorDTO reservador = reservadorService.obtenerReservadorPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[RESERVADOR] Fin obtenerReservadorPorId: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(reservador);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservadorDTO> crearReservador(@Valid @RequestBody ReservadorDTO reservadorDTO) {
        ReservadorDTO creado = reservadorService.crearReservador(reservadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReservadorDTO> actualizarReservador(@PathVariable Long id, @RequestBody ReservadorDTO reservadorDTO) {
        ReservadorDTO actualizado = reservadorService.actualizarReservador(id, reservadorDTO);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/{id}/eliminar")
    @Transactional
    public ResponseEntity<ReservadorDTO> eliminarReservador(@PathVariable Long id) {
        ReservadorDTO eliminado = reservadorService.eliminarReservador(id);
        return ResponseEntity.ok(eliminado);
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<Reservador> obtenerReservadorConBloqueo(@PathVariable Long id) {
        Reservador reservador = reservadorService.obtenerReservadorConBloqueo(id);
        return ResponseEntity.ok(reservador);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarReservadorFisicamente(@PathVariable Long id) {
        reservadorService.eliminarReservadorFisicamente(id);
        return ResponseEntity.ok("Borrado correctamente");
    }
}
