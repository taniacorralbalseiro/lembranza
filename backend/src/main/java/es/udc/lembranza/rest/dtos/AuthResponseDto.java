package es.udc.lembranza.rest.dtos;

public record AuthResponseDto(
        String token,
        long   expiresInSeconds,
        String tokenType,      // "Bearer"
        String role            // p.ej. "ROLE_ADMIN"
) { }
