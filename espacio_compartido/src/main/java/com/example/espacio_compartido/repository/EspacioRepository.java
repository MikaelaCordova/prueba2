package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Espacio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Espacio e WHERE e.idEspacio = :id")
    Optional<Espacio> findByIdWithLock(Long id);

    List<Espacio> findByEstado(Boolean estado);
    //-----------------------
    /*@Query("SELECT e FROM Espacio e " +
            "JOIN e.categoria c " +
            "WHERE (:facultad IS NULL OR e.facultad = :facultad) " +
            "AND (:carrera IS NULL OR e.carrera = :carrera) " +
            "AND (:categoria IS NULL OR c.nombre = :categoria) " +
            "AND (:capacidad IS NULL OR e.capacidad >= :capacidad) " +
            "AND NOT EXISTS (" +
            "   SELECT r FROM Reserva r " +
            "   WHERE r.espacio = e " +
            "   AND r.fechaReserva = CURRENT_DATE " +
            "   AND r.estadoE = 'CONFIRMADA'" +
            ")")
    List<Espacio> filtrarEspacios(
        @Param("facultad") String facultad,
        @Param("carrera") String carrera,
        @Param("categoria") String categoria,
        @Param("capacidad") Integer capacidad

        Nota: Afecta a las reservas
    );*/

    @Query("SELECT e FROM Espacio e " +
            "JOIN e.categoria c " +
            "WHERE (:facultad IS NULL OR e.facultad = :facultad) " +
            "AND (:carrera IS NULL OR e.carrera = :carrera) " +
            "AND (:categoria IS NULL OR c.nombre = :categoria) " +
            "AND (:capacidad IS NULL OR e.capacidad >= :capacidad)")
    List<Espacio> filtrarEspacios( // Este es el nombre original que tenías
        @Param("facultad") String facultad,
        @Param("carrera") String carrera,
        @Param("categoria") String categoria,
        @Param("capacidad") Integer capacidad
);
    //------------------------------
    @Query("SELECT DISTINCT e.capacidad FROM Espacio e ORDER BY e.capacidad ASC")
    List<Integer> obtenerCapacidadesUnicas();

}
