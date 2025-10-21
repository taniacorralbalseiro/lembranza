package es.udc.lembranza.rest.dtos;

import es.udc.lembranza.rest.dtos.enums.PuestoEmpleadoDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoSummaryDto {

    private UUID publicId;

    @NotBlank
    @Size(max = 80)
    String nombre;

    @NotBlank @Size(max = 120)
    String apellidos;

    @Email
    @NotBlank
    private String email;

    @NotBlank @Size(max = 20)
    private String telefono;

    @NotNull
    PuestoEmpleadoDto puesto;

    @NotBlank @Size(max = 120)
    private String especialidad;

}
