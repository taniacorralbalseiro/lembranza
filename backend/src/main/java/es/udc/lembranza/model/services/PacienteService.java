package es.udc.lembranza.model.services;

import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.rest.dtos.PacienteUpdateDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.UUID;

public interface PacienteService {
    Paciente registrarPaciente(Paciente nuevo, UUID centroPublicId, UUID grupoPublicId, String password) throws InstanceNotFoundException, DuplicateInstanceException;
    Paciente updatePaciente(UUID publicId, Paciente paciente) throws InstanceNotFoundException;
    void darDeBaja(UUID publicId, LocalDate fechaBaja) throws InstanceNotFoundException;
    public Page<Paciente> findPacientesByCentroId(Long centroId, int page, int size);
    Paciente getByPublicId(UUID publicId) throws InstanceNotFoundException;
    Page<Paciente> findPacientesByCentroPublicId(UUID centroPublicId, int page, int size);
}
