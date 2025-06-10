package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Reservador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface ReservadorRepository extends JpaRepository<Reservador, Long> {

    Boolean existsByCorreoInstitucional(String correoInstitucional);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reservador r WHERE r.idReservador = :id")
    Optional<Reservador> findByIdWithLock(@Param("id") Long id);

}
