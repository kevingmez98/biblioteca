package com.biblioteca.dto.ejemplar;

public record EjemplarResponse(
        Long id,
        String codigo,
        String estado,
        Long libroId
) {
}
