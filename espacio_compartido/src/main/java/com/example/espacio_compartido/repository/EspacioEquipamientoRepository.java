package com.example.espacio_compartido.repository; 

import com.example.espacio_compartido.model.EspacioEquipamiento;
import com.example.espacio_compartido.model.EspacioEquipamientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioEquipamientoRepository extends JpaRepository<EspacioEquipamiento, EspacioEquipamientoId> {

    List<EspacioEquipamiento> findById_IdEspacio(Long idEspacio);
    Optional<EspacioEquipamiento> findById_IdEspacioAndId_IdEquipamiento(Long idEspacio, Long idEquipamiento);
    Boolean existsById_IdEspacioAndId_IdEquipamiento(Long idEspacio, Long idEquipamiento);
    List<EspacioEquipamiento> findByEspacioIdEspacio(Long idEspacio);
}