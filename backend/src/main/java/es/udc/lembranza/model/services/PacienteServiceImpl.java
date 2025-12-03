package es.udc.lembranza.model.services;

import es.udc.lembranza.model.entities.Historial;
import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.entities.enumerados.EstadoCuenta;
import es.udc.lembranza.model.entities.enumerados.EstadoPaciente;
import es.udc.lembranza.model.entities.enumerados.RolUsuario;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.model.repository.CentroRepository;
import es.udc.lembranza.model.repository.GrupoRepository;
import es.udc.lembranza.model.repository.PacienteRepository;
import es.udc.lembranza.rest.dtos.PacienteConversor;
import es.udc.lembranza.rest.dtos.PacienteUpdateDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final CentroRepository  centroRepository;
    private final GrupoRepository grupoRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(rollbackFor = {DuplicateInstanceException.class, InstanceNotFoundException.class})
    public Paciente registrarPaciente(Paciente nuevo, UUID centroPublicId, UUID grupoPublicId, String password) throws DuplicateInstanceException, InstanceNotFoundException {

        if (pacienteRepository.existsByEmail(nuevo.getEmail()))
            throw new DuplicateInstanceException("project.entities.paciente", nuevo.getEmail());

        var centro = centroRepository.findByPublicId(centroPublicId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.centro", centroPublicId));

        var grupo = grupoRepository.findByPublicId(grupoPublicId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.grupo", grupoPublicId));

        var passwordHash = passwordEncoder.encode(password);

        Historial historial = Historial.crearVacio();
        nuevo.setCentro(centro);
        nuevo.setGrupo(grupo);
        nuevo.setPasswordHash(passwordHash);
        nuevo.setRolUsuario(RolUsuario.PACIENTE);
        nuevo.setHistorial(historial);
        historial.setPaciente(nuevo);
        return pacienteRepository.save(nuevo);
    }


    @Transactional
    public Paciente updatePaciente(UUID publicId, Paciente updated) throws InstanceNotFoundException {
        var paciente = pacienteRepository.findByPublicId(publicId)
                .orElseThrow(() -> new InstanceNotFoundException("Paciente", publicId));

        paciente.setNombre(updated.getNombre().trim());
        paciente.setApellidos(updated.getApellidos().trim());
        paciente.setEmail(updated.getEmail().trim());
        paciente.setTelefono(updated.getTelefono().trim());
        paciente.setEstadoPaciente(updated.getEstadoPaciente());
        paciente.setNivelEducativo(updated.getNivelEducativo());
        paciente.setSituacionLegal(updated.getSituacionLegal());
        paciente.setObservacionesNoClinicas(updated.getObservacionesNoClinicas());
        paciente.setEstadoCivil(updated.getEstadoCivil());
        paciente.setFechaBajaCentro(updated.getFechaBajaCentro());


        return pacienteRepository.save(paciente);
    }

    @Override
    public void darDeBaja(UUID publicId, LocalDate fechaBaja) throws InstanceNotFoundException {
        var paciente = pacienteRepository.findByPublicId(publicId)
                .orElseThrow(() -> new InstanceNotFoundException("Paciente", publicId));

        paciente.setEstadoCuenta(EstadoCuenta.INACTIVO);
        paciente.setEstadoPaciente(EstadoPaciente.BAJA);
        paciente.setFechaBajaCentro(fechaBaja != null ? fechaBaja : LocalDate.now());

        pacienteRepository.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByCentroId(Long centroId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findByCentroId(centroId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente getByPublicId(UUID publicId) throws InstanceNotFoundException {
        return pacienteRepository.findByPublicId(publicId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.paciente", publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByCentroPublicId(UUID centroPublicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findByCentro_PublicId(centroPublicId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByNombre(String nombre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return pacienteRepository.findByNombreContaining(nombre, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByApellido(String apellido, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findByApellidosContaining(apellido, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByNif(String nif, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findByNifContaining(nif, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        return pacienteRepository.findByEmailContaining(email, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesByGrupoPublicId(UUID grupoPublicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findByGrupo_PublicId(grupoPublicId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findPacientesAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return pacienteRepository.findAll(pageable);
    }

}
