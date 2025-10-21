package es.udc.lembranza.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "centro")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@Builder
public class Centro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "centro_seq")
    @SequenceGenerator(name = "centro_seq", sequenceName = "centro_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    @NotNull
    private UUID publicId = UUID.randomUUID();

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String direccion;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String ciudad;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String provincia;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false)
    private String telefono;

    @NotBlank
    @Email
    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

}
