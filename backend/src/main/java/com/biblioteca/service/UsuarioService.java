package com.biblioteca.service;

import com.biblioteca.dto.usuario.UsuarioRequest;
import com.biblioteca.dto.usuario.UsuarioResponse;
import com.biblioteca.exception.EntityNotFoundException;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        var usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .fechaNacimiento(request.fechaNacimiento())
                .activo(true)
                .build();

        var saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findByActivoTrue()
                .stream() // Convertir a stream
                .map(this::toResponse) // Mapea cada usuario se convierte en UsuarioResponse
                .toList(); // Nuevamente a lista
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(UUID id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", id));
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse actualizar(UUID id, UsuarioRequest request) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", id));

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());
        usuario.setFechaNacimiento(request.fechaNacimiento());

        var saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    @Transactional
    public void desactivar(UUID id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", id));

        if (!usuario.getActivo()) {
            return;
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    
    @Transactional
    public void eliminar(UUID id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", id));

        if (!prestamoRepository.findByUsuarioId(id).isEmpty()) {
            throw new IllegalStateException("No se puede eliminar el usuario porque tiene prestamos asociados");
        }

        usuarioRepository.delete(usuario);
    }

    // Mapeo a DTO
    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getFechaNacimiento(),
                usuario.getActivo()
        );
    }
}
