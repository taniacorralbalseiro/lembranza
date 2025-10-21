package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Centro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CentroRepository extends JpaRepository<Centro, Long> {

    Optional<Centro> findByPublicId(UUID centroPublicId);

    Optional<Centro> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Centro> findByCiudadIgnoreCase(String ciudad, Pageable pageable);

    Page<Centro> findByProvinciaIgnoreCase(String provincia, Pageable pageable);

    Page<Centro> findByCiudadIgnoreCaseAndProvinciaIgnoreCase(String ciudad, String provincia, Pageable pageable);

    Page<Centro> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

}
