package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.EspacioEquipamientoDTO;
import com.example.espacio_compartido.model.Equipamiento;
import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.EspacioEquipamiento;
import com.example.espacio_compartido.service.IEspacioEquipamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/espacio-equipamiento") 
@Validated
public class EspacioEquipamientoController {

    private final IEspacioEquipamientoService espacioEquipamientoService;
    private static final Logger logger = LoggerFactory.getLogger(EspacioEquipamientoController.class);

    @Autowired
    public EspacioEquipamientoController(IEspacioEquipamientoService espacioEquipamientoService) {
        this.espacioEquipamientoService = espacioEquipamientoService;
    }

    @PostMapping
    public ResponseEntity<EspacioEquipamientoDTO> asociarEquipamientoAEspacio(
            @Valid @RequestBody EspacioEquipamientoDTO espacioEquipamientoDTO) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para asociar/actualizar equipamiento ID {} a espacio ID {} con cantidad {}.",
                espacioEquipamientoDTO.getIdEquipamiento(), espacioEquipamientoDTO.getIdEspacio(), espacioEquipamientoDTO.getCantidad());
        EspacioEquipamientoDTO result = espacioEquipamientoService.asociarEquipamientoAEspacio(espacioEquipamientoDTO);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación creada/actualizada exitosamente.");
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{idEspacio}/{idEquipamiento}")
    public ResponseEntity<EspacioEquipamientoDTO> obtenerEquipamientoDeEspacio(
            @PathVariable Long idEspacio,
            @PathVariable Long idEquipamiento) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para obtener asociación de espacio ID {} y equipamiento ID {}.",
                idEspacio, idEquipamiento);
        EspacioEquipamientoDTO association = espacioEquipamientoService.obtenerEquipamientoDeEspacio(idEspacio, idEquipamiento);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación encontrada exitosamente.");
        return ResponseEntity.ok(association);
    }

    @DeleteMapping("/{idEspacio}/{idEquipamiento}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<Void> desasociarEquipamientoDeEspacio(
            @PathVariable Long idEspacio,
            @PathVariable Long idEquipamiento) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para desasociar equipamiento ID {} de espacio ID {}.",
                idEquipamiento, idEspacio);
        espacioEquipamientoService.desasociarEquipamientoDeEspacio(idEspacio, idEquipamiento);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación desasociada exitosamente.");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idEspacio}/equipamientos")
    public ResponseEntity<List<EspacioEquipamientoDTO>> obtenerEquipamientosPorEspacio(@PathVariable Long idEspacio) {
        List<EspacioEquipamientoDTO> lista = espacioEquipamientoService.obtenerEquipamientosPorIdEspacio(idEspacio);
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/por-espacio/{idEspacio}")
    public ResponseEntity<List<Map<String, Object>>> listarPorIdEspacio(@PathVariable Long idEspacio) {
        List<EspacioEquipamiento> lista = espacioEquipamientoService.listarPorIdEspacio(idEspacio);

        List<Map<String, Object>> response = new ArrayList<>();
        for (EspacioEquipamiento ee : lista) {
            Map<String, Object> item = new LinkedHashMap<>();
            
            // Detalle Espacio
            item.put("idEspacio", ee.getEspacio().getIdEspacio());
            item.put("descripcion", ee.getEspacio().getDescripcion());
            
            Map<String, Object> categoriaMap = new LinkedHashMap<>();
            categoriaMap.put("idCategoria", ee.getEspacio().getCategoria().getIdCategoria());
            categoriaMap.put("nombre", ee.getEspacio().getCategoria().getNombre());
            categoriaMap.put("descripcion", ee.getEspacio().getCategoria().getDescripcion());
            item.put("categoria", categoriaMap);
            
            item.put("facultad", ee.getEspacio().getFacultad());
            item.put("carrera", ee.getEspacio().getCarrera());
            item.put("ubicacion", ee.getEspacio().getUbicacion());
            item.put("capacidad", ee.getEspacio().getCapacidad());
            item.put("estado", ee.getEspacio().getEstado());

            // Detalle Equipamiento
            Map<String, Object> equipamientoMap = new LinkedHashMap<>();
            equipamientoMap.put("idEquipamiento", ee.getEquipamiento().getIdEquipamiento());
            equipamientoMap.put("nombre", ee.getEquipamiento().getNombre());
            equipamientoMap.put("descripcion", ee.getEquipamiento().getDescripcion());
            equipamientoMap.put("estado", ee.getEquipamiento().getEstadoE());
            item.put("equipamiento", equipamientoMap);

            // Cantidad
            item.put("cantidad", ee.getCantidad());

            response.add(item);
        }

        return ResponseEntity.ok(response);
    }

}