package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.HorarioDisponibilidadDTO;
import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.service.IReservaService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController {
    //prueba
    private final IReservaService reservaService;
    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerTodasLasReservas: {}", inicio);

        List<ReservaDTO> reservas = reservaService.obtenerTodasLasReservas();

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerTodasLasReservas: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(reservas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerReservaPorId: {}", inicio);

        ReservaDTO reserva = reservaService.obtenerReservaPorId(id);

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerReservaPorId: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.ok(reserva);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<ReservaDTO> editarReserva(@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO) {
        logger.info("[CACHE] Editando reserva con ID: {}", id);

        // Llamar al servicio para editar la reserva
        ReservaDTO reservaEditada = reservaService.modificarReserva(id, reservaDTO);

        // Devolver la respuesta con la reserva editada
        return ResponseEntity.ok(reservaEditada);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorEstado(@RequestParam String estado) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerReservasPorEstado: Estado solicitado: {}", estado);

        List<ReservaDTO> reservas = reservaService.obtenerReservasPorEstado(estado);

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerReservasPorEstado: {} (Duración: {} ms, Tamaño: {})", fin, (fin - inicio), reservas.size());

        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/crear")
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody @Valid ReservaDTO reservaDTO) {
        logger.info("[CACHE] Creando nueva reserva... Eliminando caché antigua.");

        ReservaDTO nuevaReserva = reservaService.crearReserva(reservaDTO);

        logger.info("[CACHE] Reserva creada con éxito! Caché de reservas por estado y lista general eliminada.");

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }
    /* 
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        logger.info("[CACHE] Eliminando reserva con ID: " + id);

        reservaService.eliminarReserva(id);

        logger.info("[CACHE] Reserva eliminada con éxito! Caché de reservas por estado y lista general eliminada.");

        return ResponseEntity.noContent().build(); 
    }
    */
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        logger.info("[CACHE] Cambiando estado de reserva con ID: " + id + " a CANCELADA");

        reservaService.eliminarReserva(id);

        logger.info("[CACHE] Estado de reserva actualizado! Caché eliminado.");

        return ResponseEntity.noContent().build(); 
    }

    @GetMapping("espacioyfecha/{espacioId}/{fecha}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorEspacioYFecha(
            @PathVariable Long espacioId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        logger.info("[CACHE] Consultando reservas para el espacio ID: " + espacioId + " en la fecha: " + fecha);
        
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorEspacioYFecha(espacioId, fecha);
        
        logger.info("[CACHE] Consulta completada! Se encontraron " + reservas.size() + " reservas.");
        
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("reservador/{idReservador}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorIdReservador(@PathVariable Long idReservador) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerReservasPorIdReservador (ID: {}): {}", idReservador, inicio);
        
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorIdReservador(idReservador);
        
        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerReservasPorIdReservador (ID: {}): {} (Duración: {} ms)", idReservador, fin, (fin - inicio));
        
        return ResponseEntity.ok(reservas);
    }
    @GetMapping("correo/{correoReservador}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorCorreo(@PathVariable String correoReservador) {
        long inicio = System.currentTimeMillis();
        logger.info("[CACHE] Inicio obtenerReservasPorCorreo (Correo: {}): {}", correoReservador, inicio);

        List<ReservaDTO> reservas = reservaService.obtenerReservasPorCorreo(correoReservador);

        long fin = System.currentTimeMillis();
        logger.info("[CACHE] Fin obtenerReservasPorCorreo (Correo: {}): {} (Duración: {} ms)", correoReservador, fin, (fin - inicio));

        return ResponseEntity.ok(reservas);
    }
    //-----------------
    @GetMapping("/horariosdisponibles")
    public ResponseEntity<HorarioDisponibilidadDTO> obtenerHorariosDisponibles(
            @RequestParam Long espacioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerHorariosDisponibles para espacioId: {} y fecha: {}", espacioId, fecha);

        HorarioDisponibilidadDTO disponibilidad = reservaService.obtenerHorariosDisponibles(espacioId, fecha);

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerHorariosDisponibles: Duración {} ms", (fin - inicio));

        return ResponseEntity.ok(disponibilidad);
    }


    @GetMapping("/espacio/{espacioId}/reservasporrango")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorEspacioYRangoFechas(
        @PathVariable Long espacioId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerReservasPorEspacioYRangoFechas para espacioId: {} entre {} y {}", espacioId, fechaInicio, fechaFin);
        
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorEspacioYRangoFechas(espacioId, fechaInicio, fechaFin);
        
        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerReservasPorEspacioYRangoFechas: {} (Duracion: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.ok(reservas);
    }

    /* 
    @GetMapping("/api/reserva/filtrocombinado")
    public ResponseEntity<List<ReservaDTO>> filtrarReservas(
            @RequestParam(required = false) String facultad,
            @RequestParam(required = false) String carrera,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) String rango
    ) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio filtrarReservas");

        List<ReservaDTO> reservas = reservaService.filtrarReservas(facultad, carrera, categoria, fecha, rango);

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin filtrarReservas: {} ms", (fin - inicio));
        return ResponseEntity.ok(reservas);
    }*/
    @GetMapping("/api/reserva/filtrocombinado")
    public ResponseEntity<List<ReservaDTO>> filtrarReservas(
            @RequestParam(required = false) String facultad,
            @RequestParam(required = false) String carrera,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) String rango,
            @RequestParam(required = false) String estado // Filtro adicional para estado
    ) {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio filtrarReservas con estado: {}", estado);

        List<ReservaDTO> reservas = reservaService.filtrarReservas(facultad, carrera, categoria, fecha, rango, estado);

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin filtrarReservas: {} ms (Resultados: {})", (fin - inicio), reservas.size());
        
        return ResponseEntity.ok(reservas);
    }
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarReserva(@PathVariable Long id) {
        logger.info("[CACHE] Desactivando reserva con ID: {}", id);

        reservaService.desactivarReserva(id);

        logger.info("[CACHE] Reserva desactivada correctamente! Caché eliminado.");
        
        return ResponseEntity.noContent().build();
    }


}
