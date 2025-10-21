package es.udc.lembranza.rest.dtos;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuthenticatedUserDto {
    private String token;
    private long expiresIn;
    private String type = "Bearer";
    private UserSummaryDto user;
}
