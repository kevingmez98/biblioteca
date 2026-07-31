package com.biblioteca.dto.prestamo;

import java.time.LocalDate;
import java.util.UUID;

public record PrestamoResponse(
        Long id,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucion,
        String estadoPrestamo,
        UUID usuarioId,
        Long ejemplarId
) {
}
