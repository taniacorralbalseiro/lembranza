package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.TipoEntradaHistorial;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "entrada_historial",
        indexes = {
                @Index(name = "idx_entrada_historial_historial_fecha", columnList = "historial_id, fecha"),
                @Index(name = "idx_entrada_historial_tipo", columnList = "tipo")
        })
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded=true)
@ToString
public class EntradaHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entrada_historial_seq")
    @SequenceGenerator(name = "entrada_historial_seq", sequenceName = "entrada_historial_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false)
    @NotNull
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    @NotNull
    private TipoEntradaHistorial tipo;

    @Column(name = "notas", length = 4000)
    private String notas;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historial_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_entrada_historial_historial"))
    @ToString.Exclude
    private Historial historial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_empleado_id",
            foreignKey = @ForeignKey(name = "fk_entrada_historial_empleado"))
    @ToString.Exclude
    private Empleado autorEmpleado;
}

