package es.udc.lembranza.rest.controllers;


import es.udc.lembranza.model.entities.CustomUserDetails;
import es.udc.lembranza.model.exceptions.InstanceNotFoundException;
import es.udc.lembranza.model.services.CustomUserDetailService;
import es.udc.lembranza.rest.dtos.*;
import es.udc.lembranza.rest.common.JwtService; // Lo creamos en el Paso 4
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService; // implementamos en Paso 4
    private final CustomUserDetailService customUserDetailService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUserDto> login(@Valid @RequestBody AuthRequestDto req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());

        // Autentica contra tu UserDetailsService + PasswordEncoder
        var authentication = authManager.authenticate(authToken);

        // Principal con authorities (roles)
        var userDetails = (UserDetails) authentication.getPrincipal();

        // Genera el JWT (Paso 4)
        var token = jwtService.generateToken(userDetails);
        var expiresIn = jwtService.getExpirationSeconds();

        // Saca un rol (si hay varios, coge el primero para la respuesta)
        var role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        var user = UserSummaryDto.builder()
                .publicId(null) // completar si resuelves el Usuario
                .nombre(null)
                .apellidos(null)
                .email(userDetails.getUsername())
                .rol(role.replace("ROLE_", ""))
                .build();

        return ResponseEntity.ok(
                AuthenticatedUserDto.builder()
                        .token(token)
                        .expiresIn(expiresIn)
                        .type("Bearer")
                        .user(user)
                        .build()
        );
    }

    @PostMapping("/loginFromServiceToken")
    public ResponseEntity<AuthenticatedUserDto> loginFromServiceToken(@RequestBody ServiceTokenRequestDto req) {

        // 1) Extraer username del token (fallará si está corrupto/expirado)
        String username = jwtService.extractUsername(req.token());

        // 2) Cargar el usuario y validar token (firma + expiración + subject)
        var userDetails = customUserDetailService.loadUserByUsername(username);
        if (!jwtService.isTokenValid(req.token(), userDetails)) {
            // Semántica: si no es válido → 401
            return ResponseEntity.status(401).build();
        }

        // 3) Como en los apuntes: devolvemos EL MISMO token + el usuario
        var role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("");

        var user = UserSummaryDto.builder()
                .publicId(null)  // completar si resuelves el Usuario (punto 3)
                .nombre(null)
                .apellidos(null)
                .email(userDetails.getUsername())
                .rol(role.replace("ROLE_", ""))
                .build();

        return ResponseEntity.ok(
                AuthenticatedUserDto.builder()
                        .token(req.token())                      // <-- mismo token
                        .expiresIn(jwtService.getExpirationSeconds()) // contrato estable
                        .type("Bearer")
                        .user(user)
                        .build()
        );
    }
}