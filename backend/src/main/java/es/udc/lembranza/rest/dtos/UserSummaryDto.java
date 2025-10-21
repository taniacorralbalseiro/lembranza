package es.udc.lembranza.rest.dtos;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserSummaryDto {
    private UUID publicId;
    private String nombre;
    private String apellidos;
    private String email;
    private String rol;
}
