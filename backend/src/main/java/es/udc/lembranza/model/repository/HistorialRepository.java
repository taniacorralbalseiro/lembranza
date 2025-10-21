package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialRepository extends JpaRepository<Historial, Long> {
}
