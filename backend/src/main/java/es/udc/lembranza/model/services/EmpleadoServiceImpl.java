package es.udc.lembranza.model.services;

import es.udc.lembranza.model.entities.Empleado;
import es.udc.lembranza.model.entities.Historial;
import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.entities.enumerados.EstadoCuenta;
import es.udc.lembranza.model.entities.enumerados.EstadoEmpleado;
import es.udc.lembranza.model.entities.enumerados.RolUsuario;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.model.repository.CentroRepository;
import es.udc.lembranza.model.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final CentroRepository centroRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public Empleado registrarEmpleado(Empleado nuevo, UUID centroPublicId, String password)
            throws InstanceNotFoundException, DuplicateInstanceException{
        if (empleadoRepository.existsByEmail(nuevo.getEmail()))
            throw new DuplicateInstanceException("project.entities.paciente", nuevo.getEmail());

        var centro = centroRepository.findByPublicId(centroPublicId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.centro", centroPublicId));


        var passwordHash = passwordEncoder.encode(password);

        nuevo.setCentro(centro);
        nuevo.setPasswordHash(passwordHash);
        nuevo.setRolUsuario(RolUsuario.EMPLEADO);
        log.info(">>> Voy a guardar empleado: {}", nuevo.getEmail());
        try {
            return empleadoRepository.save(nuevo);
        } catch (jakarta.validation.ConstraintViolationException e) {
            e.getConstraintViolations().forEach(v -> {
                // Campo, valor inválido y motivo
                log.error("Constraint violation: {} {} -> {}",
                        v.getPropertyPath(), v.getInvalidValue(), v.getMessage());
            });
            throw e; // o tradúcelo a tu excepción/ProblemDetail
        }
    }

    @Override
    public void darDeBaja(UUID publicId, LocalDate fechaCese) throws InstanceNotFoundException {
        var empleado = empleadoRepository.findByPublicId(publicId)
                .orElseThrow(() -> new InstanceNotFoundException("Empleado", publicId));

        empleado.setEstadoCuenta(EstadoCuenta.INACTIVO);
        empleado.setEstado(EstadoEmpleado.SUSPENDIDO); // o el valor que corresponda en tu enum
        empleado.setFechaCese(fechaCese != null ? fechaCese : LocalDate.now());

        empleadoRepository.save(empleado);
    }
}


