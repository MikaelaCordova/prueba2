package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.UsuarioDTO;
import com.example.espacio_compartido.service.IUsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        long inicio = System.currentTimeMillis();
        logger.info("[USUARIO] Inicio getAllUsuarios: {}", inicio);
        
        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        
        long fin = System.currentTimeMillis();
        logger.info("[USUARIO] Fin getAllUsuarios: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[USUARIO] Inicio getUsuarioById: {}, ID: {}", inicio, id);
        
        UsuarioDTO usuario = usuarioService.getUsuarioById(id);
        
        long fin = System.currentTimeMillis();
        logger.info("[USUARIO] Fin getUsuarioById: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        long inicio = System.currentTimeMillis();
        logger.info("[USUARIO] Inicio createUsuario: {}", inicio);
        
        UsuarioDTO nuevoUsuario = usuarioService.createUsuario(usuarioDTO);
        
        long fin = System.currentTimeMillis();
        logger.info("[USUARIO] Fin createUsuario: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        long inicio = System.currentTimeMillis();
        logger.info("[USUARIO] Inicio updateUsuario: {}, ID: {}", inicio, id);
        
        UsuarioDTO usuarioActualizado = usuarioService.updateUsuario(id, usuarioDTO);
        
        long fin = System.currentTimeMillis();
        logger.info("[USUARIO] Fin updateUsuario: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[USUARIO] Inicio deleteUsuario: {}, ID: {}", inicio, id);
        
        usuarioService.deleteUsuario(id);
        
        long fin = System.currentTimeMillis();
        logger.info("[USUARIO] Fin deleteUsuario: {} (Duración: {} ms)", fin, (fin - inicio));
        
        return ResponseEntity.noContent().build();
    }
}