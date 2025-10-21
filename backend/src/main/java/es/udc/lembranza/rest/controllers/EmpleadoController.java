package es.udc.lembranza.rest.controllers;

import es.udc.lembranza.model.entities.Empleado;
import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.exceptions.DuplicateInstanceException;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.model.services.EmpleadoService;
import es.udc.lembranza.rest.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<EmpleadoSummaryDto> registrarEmpleado(@Valid @RequestBody EmpleadoCreateDto dto)
            throws DuplicateInstanceException, InstanceNotFoundException {
        log.info("Iniciando el registro de empleado");
        Empleado nuevoEmpleado = EmpleadoConversor.toEmpleado(dto);
        log.info("FINALIZADO el registro de empleado");

        var empleado = empleadoService.registrarEmpleado(nuevoEmpleado, dto.getCentroPublicId(), dto.getPassword());
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{publicId}")
                .buildAndExpand(empleado.getPublicId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(EmpleadoConversor.toEmpleadoSummaryDto(empleado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> darDeBajaEmpleado(@PathVariable UUID publicId) throws InstanceNotFoundException {
        empleadoService.darDeBaja(publicId, LocalDate.now());
        return ResponseEntity.noContent().build();
    }

}
