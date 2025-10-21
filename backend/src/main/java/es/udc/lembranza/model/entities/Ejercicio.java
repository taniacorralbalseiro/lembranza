package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.Categoria;
import es.udc.lembranza.model.entities.enumerados.Dificultad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ejercicio")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@Builder
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ejercicio_seq")
    @SequenceGenerator(name = "ejercicio_seq", sequenceName = "ejercicio_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Categoria categoria;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Dificultad dificultad;

    @Column(length = 255)
    private String material; // opcional

    @Column(length = 255)
    private String urlVideo; // opcional

    @NotNull
    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

}