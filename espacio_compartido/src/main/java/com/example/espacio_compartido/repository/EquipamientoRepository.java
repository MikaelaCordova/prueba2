package com.example.espacio_compartido.repository; 

import com.example.espacio_compartido.model.Equipamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipamientoRepository extends JpaRepository<Equipamiento, Long> {

    Boolean existsByNombreIgnoreCase(String nombre);

    List<Equipamiento> findByEstadoETrue();

    Optional<Equipamiento> findByNombreIgnoreCaseAndEstadoETrue(String nombre);

    Optional<Equipamiento> findByIdEquipamientoAndEstadoETrue(Long idEquipamiento);
}