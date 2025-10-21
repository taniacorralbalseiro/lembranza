package es.udc.lembranza.model.services;

import es.udc.lembranza.model.entities.Empleado;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.util.UUID;

public interface EmpleadoService {
    Empleado registrarEmpleado(Empleado nuevo, UUID centroPublicId, String password)
            throws InstanceNotFoundException, DuplicateInstanceException;
    void darDeBaja(UUID publicId, LocalDate fechaCese) throws InstanceNotFoundException;
}
