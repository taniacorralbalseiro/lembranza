package es.udc.lembranza.rest.dtos;

import es.udc.lembranza.rest.dtos.enums.EstadoCivilDto;
import es.udc.lembranza.rest.dtos.enums.EstadoPacienteDto;
import es.udc.lembranza.rest.dtos.enums.NivelEducativoDto;
import es.udc.lembranza.rest.dtos.enums.SituacionLegalDto;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PacienteUpdateDto {
    @NotBlank
    @Size(max = 80)
    private String nombre;

    @NotBlank @Size(max = 120)
    private String apellidos;

    @Past
    LocalDate fechaNacimiento;

    @Email
    @NotBlank
    private String email;

    @NotBlank @Size(max = 20)
    private String telefono;

    @NotNull
    private EstadoPacienteDto estadoPaciente;

    @NotNull
    private NivelEducativoDto nivelEducativo;

    @NotNull
    private SituacionLegalDto situacionLegal;

    private String observacionesNoClinicas;

    @NotNull
    private EstadoCivilDto estadoCivil;

    @NotNull
    LocalDate fechaBajaCentro;
}
