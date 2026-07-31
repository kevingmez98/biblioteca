package com.biblioteca.dto.prestamo;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record PrestamoRequest(
        @NotNull UUID usuarioId,
        @NotNull Long ejemplarId,
        LocalDate fechaPrestamo
) {
}
