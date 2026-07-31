package com.biblioteca.controller;

import com.biblioteca.dto.ejemplar.EjemplarRequest;
import com.biblioteca.dto.ejemplar.EjemplarResponse;
import com.biblioteca.service.EjemplarService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ejemplares")
@RequiredArgsConstructor
public class EjemplarController {

    private final EjemplarService ejemplarService;

    @PostMapping
    public ResponseEntity<EjemplarResponse> crear(@Valid @RequestBody EjemplarRequest request) {
        var response = ejemplarService.crear(request);
        return ResponseEntity.created(URI.create("/api/ejemplares/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EjemplarResponse>> listar() {
        return ResponseEntity.ok(ejemplarService.listar());
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<EjemplarResponse>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(ejemplarService.listarPorLibro(libroId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjemplarResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ejemplarService.obtenerPorId(id));
    }

    @PatchMapping("/{id}/dar-de-baja")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        ejemplarService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }
}
