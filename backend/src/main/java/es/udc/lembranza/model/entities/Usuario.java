package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.EstadoCuenta;
import es.udc.lembranza.model.entities.enumerados.RolUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_public_id", columnNames = "public_id"),
                @UniqueConstraint(name = "uk_usuario_nif", columnNames = "nif"),
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING, length = 20)
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "public_id", nullable = false, updatable = false, columnDefinition = "uuid")
    @Builder.Default
    private UUID publicId = UUID.randomUUID();

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "rol_user", nullable = false)
    private RolUsuario rolUsuario;

    @Column(name = "nombre", nullable = false)
    @NotBlank @Size(max = 80)
    private String nombre;

    @NotBlank @Size(max = 120)
    private String apellidos;

    @Past
    private LocalDate fechaNacimiento;

    @Size(max = 20)
    @NotBlank
    private String telefono;

    @NotBlank
    @Size(max = 16, min = 8)
    @Column(nullable = false, length = 16)
    private String nif;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    @Builder.Default
    private EstadoCuenta estadoCuenta = EstadoCuenta.ACTIVO;

    private LocalDateTime ultimoAcceso;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;


}
