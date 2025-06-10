package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.CategoriaDTO;
import com.example.espacio_compartido.model.Categoria;
import com.example.espacio_compartido.service.ICategoriaService;

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
@RequestMapping("/api/categoria")
@Validated
public class CategoriaController {

    private final ICategoriaService categoriaService;
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        long inicio = System.currentTimeMillis();
        logger.info("[CATEGORIA] Inicio obtenerTodasLasCategorias: {}", inicio);
        List<CategoriaDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        long fin = System.currentTimeMillis();
        logger.info("[CATEGORIA] Fin obtenerTodasLasCategorias: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[CATEGORIA] Inicio obtenerCategoriaPorId: {}", inicio);
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[CATEGORIA] Fin obtenerCategoriaPorId: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO creada = categoriaService.crearCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO actualizada = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaDTO> eliminarCategoria(@PathVariable Long id) {
        CategoriaDTO eliminada = categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok(eliminada);
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<Categoria> obtenerCategoriaConBloqueo(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaConBloqueo(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}/fisico")
    @Transactional
    public ResponseEntity<String> eliminarCategoriaFisicamente(@PathVariable Long id) {
        categoriaService.eliminarCategoriaFisicamente(id);
        return ResponseEntity.ok("Categoría borrada correctamente");
    }
}