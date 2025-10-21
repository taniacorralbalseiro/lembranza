package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.ContactoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {
}
