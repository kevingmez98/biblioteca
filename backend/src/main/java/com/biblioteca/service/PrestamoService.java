package com.biblioteca.service;

import com.biblioteca.dto.prestamo.PrestamoRequest;
import com.biblioteca.dto.prestamo.PrestamoResponse;
import com.biblioteca.exception.EntityNotFoundException;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.enums.EstadoEjemplar;
import com.biblioteca.model.enums.EstadoPrestamo;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EjemplarRepository ejemplarRepository;

    @Transactional
    public PrestamoResponse registrar(PrestamoRequest request) {
        var usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario", request.usuarioId()));

        // Si ya hay un prestamo activo para el usuario se lanza un errror
        if (!prestamoRepository.findByUsuarioIdAndEstadoPrestamo(request.usuarioId(), EstadoPrestamo.ACTIVO).isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene un prestamo activo");
        }

        var ejemplar = ejemplarRepository.findById(request.ejemplarId())
                .orElseThrow(() -> new EntityNotFoundException("Ejemplar", request.ejemplarId()));

        if (ejemplar.getEstado() != EstadoEjemplar.DISPONIBLE) {
            throw new IllegalStateException("Ejemplar no disponible para prestamo");
        }

        var prestamo = Prestamo.builder()
                .fechaPrestamo(request.fechaPrestamo() != null ? request.fechaPrestamo() : LocalDate.now())
                .estadoPrestamo(EstadoPrestamo.ACTIVO)
                .usuario(usuario)
                .ejemplar(ejemplar)
                .build();

        ejemplar.setEstado(EstadoEjemplar.PRESTADO);
        ejemplarRepository.save(ejemplar);

        var saved = prestamoRepository.save(prestamo);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PrestamoResponse> listarPorUsuario(UUID usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrestamoResponse> listarPorLibro(Long libroId) {
        return prestamoRepository.findByEjemplar_LibroId(libroId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PrestamoResponse devolver(Long prestamoId) {
        var prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new EntityNotFoundException("Prestamo", prestamoId));

        if (prestamo.getEstadoPrestamo() == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("El prestamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstadoPrestamo(EstadoPrestamo.DEVUELTO);

        var ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado(EstadoEjemplar.DISPONIBLE);
        ejemplarRepository.save(ejemplar);

        var saved = prestamoRepository.save(prestamo);
        return toResponse(saved);
    }

    // Mapeo a DTO
    private PrestamoResponse toResponse(Prestamo prestamo) {
        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion(),
                prestamo.getEstadoPrestamo().name(),
                prestamo.getUsuario().getId(),
                prestamo.getEjemplar().getId()
        );
    }
}
