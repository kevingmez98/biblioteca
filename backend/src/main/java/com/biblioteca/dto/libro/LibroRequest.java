package com.biblioteca.dto.libro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record LibroRequest(
        @NotBlank String titulo,
        @NotBlank String isbn,
        @NotBlank String edicion,
        @Past LocalDate fechaPublicacion,
        @NotBlank String autor
) {
}
