package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.UsuarioDTO;
import com.example.espacio_compartido.model.Rol;
import com.example.espacio_compartido.model.Usuario;
import com.example.espacio_compartido.repository.RolRepository;
import com.example.espacio_compartido.repository.UsuarioRepository;
import com.example.espacio_compartido.service.IUsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        return convertToDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        // Validar que no exista un usuario con el mismo username o email
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new RuntimeException("Error: El nombre de usuario ya está en uso");
        }
        
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso");
        }
        
        // Crear y guardar el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setNombre(usuarioDTO.getNombre()); 
        usuario.setApellido(usuarioDTO.getApellido()); 
        usuario.setActivo(true);
        
        // Asignar roles
        Set<Rol> roles = new HashSet<>();
        
        // Por defecto, asignar ROLE_ENCARGADO
        Rol rolEncargado = rolRepository.findByNombre(Rol.RolNombre.ROLE_ENCARGADO)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        roles.add(rolEncargado);
        
        // Si se especifica que es ADMIN, añadir ese rol
        if (usuarioDTO.getRoles() != null && usuarioDTO.getRoles().contains("admin")) {
            Rol rolAdmin = rolRepository.findByNombre(Rol.RolNombre.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(rolAdmin);
        }
        
        usuario.setRoles(roles);
        usuario = usuarioRepository.save(usuario);
        
        return convertToDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        
        // Actualizar campos
        if (usuarioDTO.getUsername() != null && !usuario.getUsername().equals(usuarioDTO.getUsername())) {
            if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
                throw new RuntimeException("Error: El nombre de usuario ya está en uso");
            }
            usuario.setUsername(usuarioDTO.getUsername());
        }
        
        if (usuarioDTO.getEmail() != null && !usuario.getEmail().equals(usuarioDTO.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("Error: El email ya está en uso");
            }
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getNombre() != null) {
            usuario.setNombre(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getApellido() != null) {
            usuario.setApellido(usuarioDTO.getApellido());
        }

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        
        if (usuarioDTO.getActivo() != null) {
            usuario.setActivo(usuarioDTO.getActivo());
        }
        
        // Actualizar roles si se especifican
        if (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()) {
            Set<Rol> roles = new HashSet<>();
            
            // Añadir rol ENCARGADO si está en la lista
            if (usuarioDTO.getRoles().contains("encargado")) {
                Rol rolEncargado = rolRepository.findByNombre(Rol.RolNombre.ROLE_ENCARGADO)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(rolEncargado);
            }
            
            // Añadir rol ADMIN si está en la lista
            if (usuarioDTO.getRoles().contains("admin")) {
                Rol rolAdmin = rolRepository.findByNombre(Rol.RolNombre.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(rolAdmin);
            }
            
            if (!roles.isEmpty()) {
                usuario.setRoles(roles);
            }
        }
        
        usuario = usuarioRepository.save(usuario);
        
        return convertToDTO(usuario);
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    // Método auxiliar para convertir entidad a DTO
    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());      
        dto.setApellido(usuario.getApellido());
        dto.setActivo(usuario.getActivo());
        
        // No estamos incluyendo la contraseña en el DTO por seguridad
        
        // Convertir roles a strings
        Set<String> rolesStr = usuario.getRoles().stream()
                .map(rol -> rol.getNombre().name().replace("ROLE_", "").toLowerCase())
                .collect(Collectors.toSet());
        dto.setRoles(rolesStr);
        
        return dto;
    }
}