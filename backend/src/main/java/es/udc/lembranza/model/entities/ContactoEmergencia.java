package es.udc.lembranza.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "contacto_emergencia",
        uniqueConstraints = @UniqueConstraint(
                name="uk_contacto_prioridad",
                columnNames={"paciente_id","prioridad"}
        ),
        indexes = @Index(name = "idx_contacto_emergencia_paciente", columnList = "paciente_id")
)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded=true)
@ToString
public class ContactoEmergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacto_emergencia_seq")
    @SequenceGenerator(name = "contacto_emergencia_seq", sequenceName = "contacto_emergencia_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @NotNull
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nombre;

    @Column(nullable = false, length = 20)
    @NotBlank
    private String telefono;

    @Column(length = 50, nullable=false)
    @NotBlank
    private String relacion;

    @Column(nullable = false)
    @Min(1)
    @NotNull
    private Integer prioridad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_contacto_emergencia_paciente"))
    @ToString.Exclude
    private Paciente paciente;

}
