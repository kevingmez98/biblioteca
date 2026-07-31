package com.biblioteca.dto.ejemplar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EjemplarRequest(
        @NotBlank String codigo,
        @NotNull Long libroId
) {
}
