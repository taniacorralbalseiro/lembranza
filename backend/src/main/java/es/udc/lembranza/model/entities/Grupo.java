package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.EstadoGrupo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "grupo")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@Builder
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_seq")
    @SequenceGenerator(name = "grupo_seq", sequenceName = "grupo_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @NotBlank
    @Column(nullable = false, length = 120, unique = true)
    private String descripcion;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer capacidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoGrupo estado;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;


    // @OneToMany(mappedBy = "grupo")
    // private List<Paciente> pacientes = new ArrayList<>();
    //Si colecciones (@OneToMany pacientes), añade @ToString(exclude = "pacientes") para evitar cargas LAZY involuntarias y bucles en logs.
}
