package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
}
