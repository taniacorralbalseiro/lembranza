package es.udc.lembranza.rest.controllers;

import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.rest.dtos.*;
import es.udc.lembranza.model.services.PacienteService;
import es.udc.lembranza.rest.dtos.PacienteSummaryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.UUID;

import static es.udc.lembranza.rest.dtos.PacienteConversor.toPacienteSummaryPage;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<PacienteSummaryDto> registrarPaciente(@Valid @RequestBody PacienteCreateDto dto)
            throws DuplicateInstanceException, InstanceNotFoundException {

        Paciente nuevoPaciente = PacienteConversor.fromPacienteCreateDtotoPaciente(dto);
        var paciente = pacienteService.registrarPaciente(nuevoPaciente, dto.getCentroPublicId(),
                dto.getGrupoPublicId(), dto.getPassword());
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{publicId}")
                .buildAndExpand(paciente.getPublicId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(PacienteConversor.toPacienteSummaryDto(paciente));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PutMapping("/{publicId}")
    public ResponseEntity<PacienteSummaryDto> actualizarPaciente(
            @PathVariable UUID publicId,
            @Valid @RequestBody PacienteUpdateDto dto
    ) throws InstanceNotFoundException {
        Paciente pacienteUpdate = PacienteConversor.fromPacienteUpdateDtoToPaciente(dto);
        var actualizado = pacienteService.updatePaciente(publicId, pacienteUpdate);
        return ResponseEntity.ok(PacienteConversor.toPacienteSummaryDto(actualizado));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> darDeBajaPaciente(@PathVariable UUID publicId) throws InstanceNotFoundException {
        pacienteService.darDeBaja(publicId, LocalDate.now());
        return ResponseEntity.noContent().build();
    }



    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping
    public Page<PacienteSummaryDto> listarPacientesPorCentroId(
            @RequestParam Long centroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pacientes = pacienteService.findPacientesByCentroId(centroId, page, size);
        return toPacienteSummaryPage(pacientes);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/{publicId}")
    public PacienteSummaryDto getPaciente(@PathVariable UUID publicId) throws InstanceNotFoundException {
        var p = pacienteService.getByPublicId(publicId);
        return PacienteConversor.toPacienteSummaryDto(p);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/por-centro")
    public Page<PacienteSummaryDto> listarPorCentroPublicId(
            @RequestParam UUID centroPublicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pacientes = pacienteService.findPacientesByCentroPublicId(centroPublicId, page, size);
        return toPacienteSummaryPage(pacientes);
    }

}