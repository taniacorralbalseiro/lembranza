package es.udc.lembranza.model.repository;
import es.udc.lembranza.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByPublicId(UUID publicId);
    Optional<Usuario> findByNif(String nif);
    Optional<Usuario> findByEmail(String email);
    boolean existsByNif(String nif);
    boolean existsByEmail(String email);
}