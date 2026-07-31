package com.biblioteca.service;

import com.biblioteca.dto.ejemplar.EjemplarRequest;
import com.biblioteca.dto.ejemplar.EjemplarResponse;
import com.biblioteca.exception.EntityNotFoundException;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.enums.EstadoEjemplar;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    @Transactional
    public EjemplarResponse crear(EjemplarRequest request) {
        var libro = libroRepository.findById(request.libroId())
                .orElseThrow(() -> new EntityNotFoundException("Libro", request.libroId()));

        // Tener cuidado para producción (Por temas de concurrrencia mas que todo)
        var count = ejemplarRepository.findByLibroId(libro.getId()).size();
        var codigo = "%s-%03d".formatted(libro.getIsbn(), count + 1);

        var ejemplar = Ejemplar.builder()
                .codigo(codigo)
                .estado(EstadoEjemplar.DISPONIBLE)
                .libro(libro)
                .build();

        var saved = ejemplarRepository.save(ejemplar);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EjemplarResponse> listar() {
        return ejemplarRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EjemplarResponse> listarPorLibro(Long libroId) {
        return ejemplarRepository.findByLibroId(libroId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EjemplarResponse> listarDisponiblesPorIsbn(String isbn) {
        return ejemplarRepository.findByLibro_IsbnAndEstado(isbn, EstadoEjemplar.DISPONIBLE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EjemplarResponse obtenerPorId(Long id) {
        var ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ejemplar", id));
        return toResponse(ejemplar);
    }

    @Transactional
    public void darDeBaja(Long id) {
        var ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ejemplar", id));

        if (ejemplar.getEstado() == EstadoEjemplar.PRESTADO) {
            throw new IllegalStateException("No se puede dar de baja un ejemplar prestado");
        }

        ejemplar.setEstado(EstadoEjemplar.BAJA);
        ejemplarRepository.save(ejemplar);
    }

    private EjemplarResponse toResponse(Ejemplar ejemplar) {
        return new EjemplarResponse(
                ejemplar.getId(),
                ejemplar.getCodigo(),
                ejemplar.getEstado().name(),
                ejemplar.getLibro().getId()
        );
    }
}
