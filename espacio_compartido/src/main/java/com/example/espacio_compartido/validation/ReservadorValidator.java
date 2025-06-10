package com.example.espacio_compartido.validation;

import org.springframework.stereotype.Component;
import com.example.espacio_compartido.dto.ReservadorDTO;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ReservadorValidator {

    private static final List<String> TIPOS_PERMITIDOS = Arrays.asList("docente", "estudiante", "administrativo");
    private static final Pattern CORREO_INSTITUCIONAL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@umsa\\.bo$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\d{8}$");

    public void validaTipo(String tipo) {
        if (tipo == null || !TIPOS_PERMITIDOS.contains(tipo.toLowerCase())) {
            throw new BusinessException("Tipo no válido. Debe ser uno de: " + TIPOS_PERMITIDOS);
        }
    }

    public void validaCorreoInstitucional(String correo) {
        if (correo == null || !CORREO_INSTITUCIONAL_PATTERN.matcher(correo).matches()) {
            throw new BusinessException("Correo institucional no válido. Debe terminar en @umsa.bo");
        }
    }

    public void validaTelefono(String telefono) {
        if (telefono == null || !TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new BusinessException("Teléfono no válido. Debe contener exactamente 8 dígitos numéricos");
        }
    }

    public void validacionCompletaReservador(ReservadorDTO reservadorDTO) {
        validaTipo(reservadorDTO.getTipo());
        validaCorreoInstitucional(reservadorDTO.getCorreoInstitucional());
        validaTelefono(reservadorDTO.getTelefono());
        // Puedes agregar más validaciones si lo consideras necesario (por ejemplo, no nulos, longitud, etc.)
    }

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}
