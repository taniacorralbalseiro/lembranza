package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.entities.enumerados.EstadoCuenta;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNif(String nif);
    Page<Paciente> findByCentroId(Long centroId, Pageable pageable);

    boolean existsByNif(@NotBlank @Size(max = 16, min = 8) String nif);

    boolean existsByEmail(@Email @NotBlank @Size(max = 150) String email);

    Optional<Paciente> findByPublicId(UUID publicId);
    boolean existsByPublicId(UUID publicId);
    void deleteByPublicId(UUID publicId);

    Page<Paciente> findByCentro_PublicId(UUID centroPublicId, Pageable pageable);
    Page<Paciente> findByGrupo_PublicId(UUID grupoPublicId, Pageable pageable);
    Page<Paciente> findByCentro_PublicIdAndEstadoCuenta(UUID centroPublicId, EstadoCuenta estadoCuenta,
                                                        Pageable pageable);
}
