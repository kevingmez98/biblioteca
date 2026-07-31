package com.biblioteca.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record UsuarioRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String email,
        @Past LocalDate fechaNacimiento
) {
}
