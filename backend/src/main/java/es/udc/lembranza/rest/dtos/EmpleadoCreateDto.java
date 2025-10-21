package es.udc.lembranza.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.udc.lembranza.rest.dtos.enums.EstadoEmpleadoDto;
import es.udc.lembranza.rest.dtos.enums.PuestoEmpleadoDto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpleadoCreateDto {
    @NotBlank
    @Size(max = 80)
    String nombre;

    @NotBlank @Size(max = 120)
    String apellidos;

    @Past
    LocalDate fechaNacimiento;

    @NotBlank @Size(max = 20)
    String telefono;

    @NotBlank @Size(min = 8, max = 16)
    String nif;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank @Size(max = 40)
    @JsonProperty("nColegiado")
    String nColegiado;

    @NotBlank @Size(max = 80)
    String especialidad;

    @NotNull
    LocalDate fechaContratacion;

    LocalDate fechaCese;

    @NotNull @PositiveOrZero
    BigDecimal salario;

    @NotNull
    PuestoEmpleadoDto puesto;

    @NotNull
    EstadoEmpleadoDto estado;

    @NotNull
    UUID centroPublicId;
}
