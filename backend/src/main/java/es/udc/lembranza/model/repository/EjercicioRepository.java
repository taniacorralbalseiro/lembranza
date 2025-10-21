package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
}
