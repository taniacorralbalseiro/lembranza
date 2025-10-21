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
public class PacienteCreateDto {

    @NotBlank @Size(max = 80)
    String nombre;

    @NotBlank @Size(max = 120)
    String apellidos;

    @Past
    LocalDate fechaNacimiento;

    @NotBlank @Size(max = 20)
    String telefono;

    @NotBlank @Size(min = 8, max = 16)
    String nif;

    @Email @NotBlank
    String email;

    @NotBlank
    String password; // contraseña en texto plano solo para creación

    @NotNull
    LocalDate fechaAltaCentro;

    @NotNull
    EstadoPacienteDto estadoPaciente;

    @NotNull
    NivelEducativoDto nivelEducativo;

    @NotNull
    SituacionLegalDto situacionLegal;

    String observacionesNoClinicas;

    @NotNull
    EstadoCivilDto estadoCivil;

    @NotNull
    UUID centroPublicId;

    @NotNull
    UUID grupoPublicId;

    public PacienteCreateDto() {}

}
