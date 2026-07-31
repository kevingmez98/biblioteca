package com.biblioteca.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Object id) {
        super("%s con id %s no encontrado".formatted(entity, id));
    }
}
