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

    @Size(max = 80)
    private String nombre;

    @Size(max = 120)
    private String apellidos;

    @Past
    LocalDate fechaNacimiento;

    @Email
    private String email;

    @Size(max = 20)
    private String telefono;

    private EstadoPacienteDto estadoPaciente;

    private NivelEducativoDto nivelEducativo;

    private SituacionLegalDto situacionLegal;

    private String observacionesNoClinicas;

    private EstadoCivilDto estadoCivil;

    LocalDate fechaBajaCentro;
}
