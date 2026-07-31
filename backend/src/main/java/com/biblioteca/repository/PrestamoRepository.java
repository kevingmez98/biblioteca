package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import com.biblioteca.model.enums.EstadoPrestamo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuarioId(UUID usuarioId);

    List<Prestamo> findByEjemplarId(Long ejemplarId);

    List<Prestamo> findByEstadoPrestamo(EstadoPrestamo estado);

    List<Prestamo> findByEjemplar_LibroId(Long libroId);
}
