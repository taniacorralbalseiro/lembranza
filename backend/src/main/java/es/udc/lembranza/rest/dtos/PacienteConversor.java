package es.udc.lembranza.rest.dtos;

import es.udc.lembranza.model.entities.Paciente;
import es.udc.lembranza.model.entities.enumerados.*;
import es.udc.lembranza.rest.dtos.enums.EstadoCivilDto;
import es.udc.lembranza.rest.dtos.enums.EstadoPacienteDto;
import es.udc.lembranza.rest.dtos.enums.NivelEducativoDto;
import es.udc.lembranza.rest.dtos.enums.SituacionLegalDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PacienteConversor {

    private PacienteConversor() {}

    public final static List<PacienteSummaryDto> toPacienteSummaryDtos(List<Paciente> pacientes) {
        return pacientes.stream().map(PacienteConversor::toPacienteSummaryDto) // method reference
                .toList();
    }

    public final static PacienteSummaryDto toPacienteSummaryDto(Paciente paciente) {
        return new PacienteSummaryDto(paciente.getPublicId(), paciente.getNombre(), paciente.getApellidos(), paciente.getEmail(), paciente.getTelefono());
    }

    public static Page<PacienteSummaryDto> toPacienteSummaryPage(Page<Paciente> pagePacientes) {
        Objects.requireNonNull(pagePacientes, "pagePacientes no puede ser null");
        return pagePacientes.map(PacienteConversor::toPacienteSummaryDto);
    }

    public static Paciente fromPacienteCreateDtotoPaciente(PacienteCreateDto dto) {
        return Paciente.builder()
                .nombre(dto.getNombre().trim())
                .apellidos(dto.getApellidos().trim())
                .fechaNacimiento(dto.getFechaNacimiento())
                .telefono(dto.getTelefono().trim())
                .nif(dto.getNif().trim())
                .email(dto.getEmail().trim())
                .rolUsuario(RolUsuario.PACIENTE)
                .fechaAltaCentro(dto.getFechaAltaCentro())
                .estadoPaciente(toEstadoPaciente(dto.getEstadoPaciente()))
                .nivelEducativo(toNivelEducativo(dto.getNivelEducativo()))
                .situacionLegal(toSituacionLegal(dto.getSituacionLegal()))
                .observacionesNoClinicas(dto.getObservacionesNoClinicas())
                .estadoCivil(toEstadoCivil(dto.getEstadoCivil()))
                .build();
    }

    public static Paciente fromPacienteUpdateDtoToPaciente(PacienteUpdateDto dto) {
        return Paciente.builder()
                .nombre(dto.getNombre().trim())
                .apellidos(dto.getApellidos().trim())
                .fechaNacimiento(dto.getFechaNacimiento())
                .telefono(dto.getTelefono().trim())
                .email(dto.getEmail().trim())
                .estadoPaciente(toEstadoPaciente(dto.getEstadoPaciente()))
                .nivelEducativo(toNivelEducativo(dto.getNivelEducativo()))
                .situacionLegal(toSituacionLegal(dto.getSituacionLegal()))
                .observacionesNoClinicas(dto.getObservacionesNoClinicas())
                .estadoCivil(toEstadoCivil(dto.getEstadoCivil()))
                .fechaBajaCentro(dto.getFechaBajaCentro())
                .build();
    }

    public static EstadoCivil toEstadoCivil(EstadoCivilDto dto) {
        return switch (dto) {
            case OTRO -> EstadoCivil.OTRO;
            case VIUDO ->  EstadoCivil.VIUDO;
            case CASADO ->   EstadoCivil.CASADO;
            case SOLTERO ->  EstadoCivil.SOLTERO;
            case DIVORCIADO ->   EstadoCivil.DIVORCIADO;
        };
    }
    public static EstadoPaciente toEstadoPaciente(EstadoPacienteDto dto) {
        return switch (dto) {
            case BAJA ->  EstadoPaciente.BAJA;
            case PENDIENTE ->  EstadoPaciente.PENDIENTE;
            case DEFUNCION ->  EstadoPaciente.DEFUNCION;
            case ACTIVO ->  EstadoPaciente.ACTIVO;
        };
    }

    public static NivelEducativo toNivelEducativo(NivelEducativoDto dto) {
        return switch (dto) {
            case OTRO -> NivelEducativo.OTRO;
            case MEDIO ->   NivelEducativo.MEDIO;
            case BASICO ->    NivelEducativo.BASICO;
            case SIN_ESTUDIOS ->   NivelEducativo.SIN_ESTUDIOS;
            case UNIVERSITARIO ->    NivelEducativo.UNIVERSITARIO;
        };
    }

    public static SituacionLegal toSituacionLegal(SituacionLegalDto dto) {
        return switch (dto) {
            case AUTONOMO ->  SituacionLegal.AUTONOMO;
            case INCAPACITADO_PARCIAL ->   SituacionLegal.INCAPACITADO_PARCIAL;
            case INCAPACITADO_TOTAL ->    SituacionLegal.INCAPACITADO_TOTAL;
        };
    }

}
