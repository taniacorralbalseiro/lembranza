package es.udc.lembranza.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PacienteSummaryDto {

    private UUID publicId;

    @NotBlank
    @Size(max = 80)
    String nombre;

    @NotBlank @Size(max = 120)
    String apellidos;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(max = 20)
    private String telefono;

    public PacienteSummaryDto() {}

    public PacienteSummaryDto(UUID publicId, String nombre, String apellidos, String email, String telefono) {
        this.publicId = publicId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }

}


