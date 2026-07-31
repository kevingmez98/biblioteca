package com.biblioteca.controller;

import com.biblioteca.dto.libro.LibroRequest;
import com.biblioteca.dto.libro.LibroResponse;
import com.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest request) {
        var response = libroService.crear(request);
        return ResponseEntity.created(URI.create("/api/libros/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LibroResponse>> listar() {
        return ResponseEntity.ok(libroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        return ResponseEntity.ok(libroService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
