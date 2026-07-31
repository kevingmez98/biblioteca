package com.biblioteca.dto.ejemplar;

import jakarta.validation.constraints.NotNull;

public record EjemplarRequest(
        @NotNull Long libroId
) {
}
