package com.biblioteca.dto.libro;

import java.time.LocalDate;

public record LibroResponse(
        Long id,
        String titulo,
        String isbn,
        String edicion,
        LocalDate fechaPublicacion,
        String autor
) {
}
