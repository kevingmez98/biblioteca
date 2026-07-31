package com.biblioteca.controller;

import com.biblioteca.dto.usuario.UsuarioRequest;
import com.biblioteca.dto.usuario.UsuarioResponse;
import com.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Serializar a json
@RequestMapping("/api/usuarios") // prefijo de ruta
@RequiredArgsConstructor // Genera constructor con los campos
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping // Atajo para verbo http
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) { // activar validaciones en el dto y deserializar request
        var response = usuarioService.crear(request);
        return ResponseEntity.created(URI.create("/api/usuarios/" + response.id())).body(response); // retorna code 201 + header Location
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar()); // retorna 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) { //@pathvariable - extraer el valor en la url
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build(); // retorna 204
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build(); // retorna 204
    }
}
