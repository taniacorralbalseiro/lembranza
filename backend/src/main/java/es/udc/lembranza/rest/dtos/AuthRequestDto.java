package es.udc.lembranza.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
