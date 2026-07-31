package com.biblioteca.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        int status,
        String message,
        List<String> errors,
        String path,
        LocalDateTime timestamp
) {
    public ErrorResponse(int status, String message, String path) {
        this(status, message, List.of(), path, LocalDateTime.now());
    }

    public ErrorResponse(int status, String message, List<String> errors, String path) {
        this(status, message, errors, path, LocalDateTime.now());
    }
}
