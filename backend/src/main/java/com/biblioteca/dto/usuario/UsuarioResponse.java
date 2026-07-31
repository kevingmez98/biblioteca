package com.biblioteca.dto.usuario;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nombre,
        String apellido,
        String email,
        LocalDate fechaNacimiento,
        boolean activo
) {
}
