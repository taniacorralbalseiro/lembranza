package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.EntradaHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaHistorialRepository extends JpaRepository<EntradaHistorial, Long> {
}
