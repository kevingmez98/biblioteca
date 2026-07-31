package com.biblioteca.repository;

import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.enums.EstadoEjemplar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

    List<Ejemplar> findByLibroId(Long libroId);

    List<Ejemplar> findByLibroIdAndEstado(Long libroId, EstadoEjemplar estado);

    Optional<Ejemplar> findByCodigo(String codigo);
}
