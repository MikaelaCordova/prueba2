package com.example.espacio_compartido.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.espacio_compartido.dto.ReservaDTO;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

@Component
public class ReservaValidator {
//prueba
    private static final Pattern ESTADO_PATTERN = Pattern.compile("^(CONFIRMADA|CANCELADA)$");
    private static final int MOTIVO_MAX_LENGTH = 255;
    private static final LocalTime HORA_INICIO_PERMITIDA = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIN_PERMITIDA = LocalTime.of(22, 0);
    private static final LocalDate LIMITE_FUTURO = LocalDate.now().plusYears(1);

    public void validaFechaCreacion(LocalDate fechaCreacion) {
        if (fechaCreacion == null || fechaCreacion.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de creación no puede ser en el futuro.");
        }
    }
    public void validaFechaReserva(LocalDate fechaReserva) {
        if (fechaReserva == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha no puede ser nula.");
        }
        if (fechaReserva.isBefore(LocalDate.now())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes reservar para una fecha pasada.");
        }
        if (fechaReserva.getYear() < 2025) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El año de la reserva debe ser 2025 o posterior.");
        }
        if (fechaReserva.isAfter(LIMITE_FUTURO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes reservar espacios con más de un año de anticipación.");
        }
        if (fechaReserva.isAfter(LocalDate.of(2100, 1, 1))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha ingresada no es válida.");
        }
        try {
            fechaReserva.getMonthValue();
            fechaReserva.getDayOfMonth();
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha ingresada tiene un mes o día inválido.");
        }
    }


    public void validaHoras(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio == null || horaFin == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las horas de inicio y fin son obligatorias.");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La hora de inicio debe ser anterior a la hora de fin.");
        }
        if (horaInicio.isBefore(HORA_INICIO_PERMITIDA) || horaFin.isAfter(HORA_FIN_PERMITIDA)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El horario de reserva debe estar entre 07:00 y 22:00.");
        }
        if (horaInicio.equals(horaFin)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La hora de inicio y fin no pueden ser iguales.");
        }
        /* 
        if (horaInicio.plusHours(2).isBefore(horaFin)) { 
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las reservas deben ser máximo de 2 horas.");
        }*/
    }

    public void validaEstado(String estadoE) {
        if (estadoE == null || !ESTADO_PATTERN.matcher(estadoE).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no válido. Debe ser uno de: PENDIENTE, CONFIRMADA, CANCELADA");
        }
    }

    public void validaMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El motivo de la reserva es obligatorio.");
        }
        if (motivo.length() > MOTIVO_MAX_LENGTH) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El motivo no puede superar los 255 caracteres.");
        }
        if (motivo.matches("^(.)\\1{5,}$")) { 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El motivo no puede ser solo caracteres repetidos.");
        }
    }
    public void validaCorreoReservador(String correoReservador) {
        if (correoReservador == null || correoReservador.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo del reservador no puede estar vacío.");
        }

        if (correoReservador.length() < 5 || correoReservador.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo debe tener entre 5 y 100 caracteres.");
        }

        if (!correoReservador.matches("^[A-Za-z0-9._%+-]+@umsa\\.bo$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo debe pertenecer al dominio @umsa.bo.");
        }
    }

    public void validacionCompletaReserva(ReservaDTO reservaDTO) {
        validaFechaCreacion(reservaDTO.getFechaCreacion());
        validaFechaReserva(reservaDTO.getFechaReserva());
        validaHoras(reservaDTO.getHoraInicio(), reservaDTO.getHoraFin());
        validaMotivo(reservaDTO.getMotivo());
        validaEstado(reservaDTO.getEstadoE());
    }
}
