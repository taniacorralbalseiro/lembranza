package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
}
