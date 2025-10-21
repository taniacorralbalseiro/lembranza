package es.udc.lembranza.model.repository;

import es.udc.lembranza.model.entities.Empleado;
import es.udc.lembranza.model.entities.enumerados.EstadoCuenta;
import es.udc.lembranza.model.entities.enumerados.PuestoEmpleado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByPublicId(UUID publicId);
    Optional<Empleado> findByNif(String nif);
    Page<Empleado> findByCentroId(UUID centroId, Pageable pageable);
    Page<Empleado> findByCentro_PublicIdAndEstadoCuenta(UUID centroPublicId, EstadoCuenta estadoCuenta,
                                                        Pageable pageable);
    Page<Empleado> findByPuesto(PuestoEmpleado puesto, Pageable pageable);
    boolean existsByEmail(@Email @NotBlank @Size(max = 150) String email);
}
