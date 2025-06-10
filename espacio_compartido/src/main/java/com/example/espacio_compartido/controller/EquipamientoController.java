package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.EquipamientoDTO;
import com.example.espacio_compartido.service.IEquipamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/equipamientos")
@Validated 
public class EquipamientoController {

    private final IEquipamientoService equipamientoService;
    private static final Logger logger = LoggerFactory.getLogger(EquipamientoController.class);

    @Autowired
    public EquipamientoController(IEquipamientoService equipamientoService) {
        this.equipamientoService = equipamientoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipamientoDTO>> obtenerTodosLosEquipamientosActivos() {
        logger.info("[EQUIPAMIENTO] Solicitud para obtener todos los equipamientos activos.");
        List<EquipamientoDTO> equipamientos = equipamientoService.obtenerTodosLosEquipamientosActivos();
        logger.info("[EQUIPAMIENTO] {} equipamientos activos encontrados.", equipamientos.size());
        return ResponseEntity.ok(equipamientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamientoDTO> obtenerEquipamientoPorId(@PathVariable Long id) {
        logger.info("[EQUIPAMIENTO] Solicitud para obtener equipamiento con ID: {}", id);
        EquipamientoDTO equipamiento = equipamientoService.obtenerEquipamientoPorId(id);
        logger.info("[EQUIPAMIENTO] Equipamiento con ID {} obtenido exitosamente.", id);
        return ResponseEntity.ok(equipamiento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EquipamientoDTO> crearEquipamiento(@Valid @RequestBody EquipamientoDTO equipamientoDTO) {
        logger.info("[EQUIPAMIENTO] Solicitud para crear nuevo equipamiento: {}", equipamientoDTO.getNombre());
        EquipamientoDTO nuevoEquipamiento = equipamientoService.crearEquipamiento(equipamientoDTO);
        logger.info("[EQUIPAMIENTO] Equipamiento '{}' creado exitosamente con ID: {}", nuevoEquipamiento.getNombre(), nuevoEquipamiento.getIdEquipamiento());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamientoDTO> actualizarEquipamiento(
            @PathVariable Long id,
            @Valid @RequestBody EquipamientoDTO equipamientoDTO) {
        logger.info("[EQUIPAMIENTO] Solicitud para actualizar equipamiento con ID: {}", id);
        EquipamientoDTO equipamientoActualizado = equipamientoService.actualizarEquipamiento(id, equipamientoDTO);
        logger.info("[EQUIPAMIENTO] Equipamiento con ID {} actualizado exitosamente.", id);
        return ResponseEntity.ok(equipamientoActualizado);
    }

    @PutMapping("/{id}/baja")
    public ResponseEntity<EquipamientoDTO> eliminarEquipamiento(@PathVariable Long id) {
        logger.info("[EQUIPAMIENTO] Solicitud para eliminar lógicamente equipamiento con ID: {}", id);
        EquipamientoDTO equipamientoEliminado = equipamientoService.eliminarEquipamiento(id);
        logger.info("[EQUIPAMIENTO] Equipamiento con ID {} eliminado lógicamente.", id);
        return ResponseEntity.ok(equipamientoEliminado);
    }
}