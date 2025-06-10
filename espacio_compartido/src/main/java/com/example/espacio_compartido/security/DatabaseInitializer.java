package com.example.espacio_compartido.security;

import com.example.espacio_compartido.model.Rol;
import com.example.espacio_compartido.model.Usuario;
import com.example.espacio_compartido.repository.RolRepository;
import com.example.espacio_compartido.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Inicializar roles si no existen
        initRoles();
        
        // Crear usuario admin por defecto si no existe
        createDefaultAdmin();
    }

    private void initRoles() {
        if (rolRepository.count() == 0) {
            Rol adminRol = new Rol();
            adminRol.setNombre(Rol.RolNombre.ROLE_ADMIN);
            rolRepository.save(adminRol);

            Rol encargadoRol = new Rol();
            encargadoRol.setNombre(Rol.RolNombre.ROLE_ENCARGADO);
            rolRepository.save(encargadoRol);
            
            System.out.println("Roles inicializados correctamente");
        }
    }

    private void createDefaultAdmin() {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@sistema.com");
            admin.setNombre("Administrador");     
            admin.setApellido("Sistema"); 
            admin.setActivo(true);

            Set<Rol> roles = new HashSet<>();
            Rol adminRol = rolRepository.findByNombre(Rol.RolNombre.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(adminRol);
            
            admin.setRoles(roles);
            usuarioRepository.save(admin);
            
            System.out.println("Usuario admin creado correctamente");
        }
    }
}