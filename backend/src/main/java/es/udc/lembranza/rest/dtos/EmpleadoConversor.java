package es.udc.lembranza.rest.dtos;

import es.udc.lembranza.model.entities.Empleado;
import es.udc.lembranza.model.entities.enumerados.EstadoEmpleado;
import es.udc.lembranza.model.entities.enumerados.PuestoEmpleado;
import es.udc.lembranza.model.entities.enumerados.RolUsuario;
import es.udc.lembranza.rest.dtos.enums.EstadoEmpleadoDto;
import es.udc.lembranza.rest.dtos.enums.PuestoEmpleadoDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class EmpleadoConversor {

    private EmpleadoConversor() {}

    // =========================
    // Entidad -> DTO resumen
    // =========================
    public static EmpleadoSummaryDto toEmpleadoSummaryDto(Empleado empleado) {
        return new EmpleadoSummaryDto(
                empleado.getPublicId(),
                empleado.getNombre(),
                empleado.getApellidos(),
                empleado.getEmail(),
                empleado.getTelefono(),
                toPuestoEmpleadoDto(empleado.getPuesto()),
                empleado.getEspecialidad()
        );
    }

    public static List<EmpleadoSummaryDto> toEmpleadoSummaryDtos(List<Empleado> empleados) {
        return empleados.stream().map(EmpleadoConversor::toEmpleadoSummaryDto).toList();
    }

    public static Page<EmpleadoSummaryDto> toEmpleadoSummaryPage(Page<Empleado> pageEmpleados) {
        Objects.requireNonNull(pageEmpleados, "pageEmpleados no puede ser null");
        return pageEmpleados.map(EmpleadoConversor::toEmpleadoSummaryDto);
    }

    // =========================
    // Entidad -> DTO detalle
    // =========================
    public static EmpleadoDetailsDto toEmpleadoDetailsDto(Empleado e) {
        UUID centroPid = e.getCentro() != null ? e.getCentro().getPublicId() : null;

        return new EmpleadoDetailsDto(
                e.getPublicId(),
                e.getNombre(),
                e.getApellidos(),
                e.getEmail(),
                e.getTelefono(),
                e.getNColegiado(),
                e.getEspecialidad(),
                e.getFechaNacimiento(),
                e.getFechaContratacion(),
                e.getFechaCese(),
                e.getSalario(),
                toPuestoEmpleadoDto(e.getPuesto()),
                toEstadoEmpleadoDto(e.getEstado()),
                centroPid
        );
    }

    // =========================
    // DTO creación -> Entidad
    // =========================
    public static Empleado toEmpleado(EmpleadoCreateDto dto) {
        return Empleado.builder()
                .nombre(dto.getNombre().trim())
                .apellidos(dto.getApellidos().trim())
                .fechaNacimiento(dto.getFechaNacimiento())
                .telefono(dto.getTelefono().trim())
                .nif(dto.getNif().trim())
                .email(dto.getEmail().trim())
                .rolUsuario(RolUsuario.EMPLEADO)
                .nColegiado(dto.getNColegiado().trim())
                .especialidad(dto.getEspecialidad().trim())
                .fechaContratacion(dto.getFechaContratacion())
                .fechaCese(dto.getFechaCese())             // puede venir null
                .salario(dto.getSalario())
                .estado(toEstadoEmpleado(dto.getEstado()))
                .puesto(toPuestoEmpleado(dto.getPuesto()))
                .build();
    }

    // =========================
    // Enums DTO -> Modelo
    // =========================
    public static EstadoEmpleado toEstadoEmpleado(EstadoEmpleadoDto dto) {
        return switch (dto) {
            case ALTA -> EstadoEmpleado.ALTA;
            case BAJA -> EstadoEmpleado.BAJA;
            case SUSPENDIDO -> EstadoEmpleado.SUSPENDIDO;
        };
    }

    public static PuestoEmpleado toPuestoEmpleado(PuestoEmpleadoDto dto) {
        return switch (dto) {
            case EDUCADOR -> PuestoEmpleado.EDUCADOR;
            case MEDICO -> PuestoEmpleado.MEDICO;
            case TERAPEUTA -> PuestoEmpleado.TERAPEUTA;
            case ENFERMERIA -> PuestoEmpleado.ENFERMERIA;
            case ADMINISTRACION -> PuestoEmpleado.ADMINISTRACION;
            case OTRO -> PuestoEmpleado.OTRO;
        };
    }

    // =========================
    // Enums Modelo -> DTO
    // =========================
    public static EstadoEmpleadoDto toEstadoEmpleadoDto(EstadoEmpleado model) {
        return switch (model) {
            case ALTA -> EstadoEmpleadoDto.ALTA;
            case BAJA -> EstadoEmpleadoDto.BAJA;
            case SUSPENDIDO -> EstadoEmpleadoDto.SUSPENDIDO;
        };
    }

    public static PuestoEmpleadoDto toPuestoEmpleadoDto(PuestoEmpleado model) {
        return switch (model) {
            case EDUCADOR -> PuestoEmpleadoDto.EDUCADOR;
            case MEDICO -> PuestoEmpleadoDto.MEDICO;
            case TERAPEUTA -> PuestoEmpleadoDto.TERAPEUTA;
            case ENFERMERIA -> PuestoEmpleadoDto.ENFERMERIA;
            case ADMINISTRACION -> PuestoEmpleadoDto.ADMINISTRACION;
            case OTRO -> PuestoEmpleadoDto.OTRO;
        };
    }
}
