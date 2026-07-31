package com.biblioteca.controller;

import com.biblioteca.dto.prestamo.PrestamoRequest;
import com.biblioteca.dto.prestamo.PrestamoResponse;
import com.biblioteca.service.PrestamoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
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
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoResponse> registrar(@Valid @RequestBody PrestamoRequest request) {
        var response = prestamoService.registrar(request);
        return ResponseEntity.created(URI.create("/api/prestamos/" + response.id())).body(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(prestamoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<PrestamoResponse>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(prestamoService.listarPorLibro(libroId));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolver(id));
    }
}
