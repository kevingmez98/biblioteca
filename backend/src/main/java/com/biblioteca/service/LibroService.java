package com.biblioteca.service;

import com.biblioteca.dto.libro.LibroRequest;
import com.biblioteca.dto.libro.LibroResponse;
import com.biblioteca.exception.EntityNotFoundException;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    @Transactional
    public LibroResponse crear(LibroRequest request) {
        var libro = Libro.builder()
                .titulo(request.titulo())
                .isbn(request.isbn())
                .edicion(request.edicion())
                .fechaPublicacion(request.fechaPublicacion())
                .autor(request.autor())
                .build();

        var saved = libroRepository.save(libro);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> listar() {
        return libroRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LibroResponse obtenerPorId(Long id) {
        var libro = libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro", id));
        return toResponse(libro);
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest request) {
        var libro = libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro", id));

        libro.setTitulo(request.titulo());
        libro.setIsbn(request.isbn());
        libro.setEdicion(request.edicion());
        libro.setFechaPublicacion(request.fechaPublicacion());
        libro.setAutor(request.autor());

        var saved = libroRepository.save(libro);
        return toResponse(saved);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new EntityNotFoundException("Libro", id);
        }
        libroRepository.deleteById(id);
    }

    private LibroResponse toResponse(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getEdicion(),
                libro.getFechaPublicacion(),
                libro.getAutor()
        );
    }
}
