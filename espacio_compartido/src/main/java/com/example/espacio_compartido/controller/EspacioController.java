package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.EspacioDTO;
import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.service.IEspacioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/espacio")
@Validated
public class EspacioController {
    //prueba
    private final IEspacioService espacioService;
    private static final Logger logger = LoggerFactory.getLogger(EspacioController.class);

    @Autowired
    public EspacioController(IEspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping
    public ResponseEntity<List<EspacioDTO>> obtenerTodosLosEspacios() {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio obtenerTodosLosEspacios: {}", inicio);
        List<EspacioDTO> espacios = espacioService.obtenerTodosLosEspacios();
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin obtenerTodosLosEspacios: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacioDTO> obtenerEspacioPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio obtenerEspacioPorId (ID: {}): {}", id, inicio);
        EspacioDTO espacio = espacioService.obtenerEspacioPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin obtenerEspacioPorId (ID: {}): {} (Duración: {} ms)", id, fin, (fin - inicio));
        return ResponseEntity.ok(espacio);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EspacioDTO> crearEspacio(@Valid @RequestBody EspacioDTO espacioDTO) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio crearEspacio: {}", inicio);
        EspacioDTO creado = espacioService.crearEspacio(espacioDTO);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin crearEspacio (ID: {}): {} (Duración: {} ms)", creado.getIdEspacio(), fin, (fin - inicio));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EspacioDTO> actualizarEspacio(@PathVariable Long id, @Valid @RequestBody EspacioDTO espacioDTO) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio actualizarEspacio (ID: {}): {}", id, inicio);
        EspacioDTO actualizado = espacioService.actualizarEspacio(id, espacioDTO);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin actualizarEspacio (ID: {}): {} (Duración: {} ms)", id, fin, (fin - inicio));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<EspacioDTO> eliminarEspacio(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio eliminarEspacio (ID: {}): {}", id, inicio);
        EspacioDTO eliminado = espacioService.eliminarEspacio(id);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin eliminarEspacio (ID: {}): {} (Duración: {} ms)", id, fin, (fin - inicio));
        return ResponseEntity.ok(eliminado);
    }

    @DeleteMapping("/{id}/fisico")
    @Transactional
    public ResponseEntity<String> eliminarEspacioFisicamente(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio eliminarEspacioFisicamente (ID: {}): {}", id, inicio);
        espacioService.eliminarEspacioFisicamente(id);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin eliminarEspacioFisicamente (ID: {}): {} (Duración: {} ms)", id, fin, (fin - inicio));
        return ResponseEntity.ok("Espacio eliminado físicamente");
    }

    
    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> obtenerEspacioDetalle(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio obtenerEspacioDetalle (ID: {}): {}", id, inicio);
        
        Espacio espacio = espacioService.obtenerEntidadEspacioPorId(id);
        Map<String, Object> response = construirRespuestaDetallada(espacio);
        
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin obtenerEspacioDetalle (ID: {}): {} (Duración: {} ms)", id, fin, (fin - inicio));
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> construirRespuestaDetallada(Espacio espacio) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idEspacio", espacio.getIdEspacio());
        response.put("descripcion", espacio.getDescripcion());
        
        Map<String, Object> categoriaMap = new LinkedHashMap<>();
        categoriaMap.put("idCategoria", espacio.getCategoria().getIdCategoria());
        categoriaMap.put("nombre", espacio.getCategoria().getNombre());
        categoriaMap.put("descripcion", espacio.getCategoria().getDescripcion());
        
        response.put("categoria", categoriaMap);
        response.put("facultad", espacio.getFacultad());
        response.put("carrera", espacio.getCarrera());
        response.put("ubicacion", espacio.getUbicacion());
        response.put("capacidad", espacio.getCapacidad());
        response.put("estado", espacio.getEstado());
        
        return response;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EspacioDTO>> obtenerEspaciosActivos() {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio obtenerEspaciosActivos: {}", inicio);
        List<EspacioDTO> espacios = espacioService.obtenerEspaciosPorEstado(true);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin obtenerEspaciosActivos: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<EspacioDTO>> obtenerEspaciosInactivos() {
        long inicio = System.currentTimeMillis();
        logger.info("[ESPACIO] Inicio obtenerEspaciosInactivos: {}", inicio);
        List<EspacioDTO> espacios = espacioService.obtenerEspaciosPorEstado(false);
        long fin = System.currentTimeMillis();
        logger.info("[ESPACIO] Fin obtenerEspaciosInactivos: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(espacios);
    }
    //-------------------------------------
    @GetMapping("/espaciosconbinados")
    public ResponseEntity<List<EspacioDTO>> getEspaciosDisponibles(
        @RequestParam(required = false) String facultad,
        @RequestParam(required = false) String carrera,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Integer capacidad
    ) {
        List<EspacioDTO> espacios = espacioService.filtrarEspacios(facultad, carrera, categoria, capacidad);
        return ResponseEntity.ok(espacios);
    }
    @GetMapping("/capacidades")
    public ResponseEntity<List<Integer>> obtenerCapacidades() {
        List<Integer> capacidades = espacioService.obtenerCapacidadesDisponibles();
        return ResponseEntity.ok(capacidades);
    }

}