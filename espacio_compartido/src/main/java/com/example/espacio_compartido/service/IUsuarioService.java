package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.UsuarioDTO;
import com.example.espacio_compartido.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> getAllUsuarios();
    
    UsuarioDTO getUsuarioById(Long id);
    
    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);
    
    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO);
    
    void deleteUsuario(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}