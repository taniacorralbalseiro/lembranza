package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.EstadoRegistroEjercicio;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "registro_ejercicio",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_reg_paciente_sesion_ejercicio",
                columnNames = {"paciente_id","sesion_id","ejercicio_id"}
        ))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RegistroEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registro_ejercicio_seq")
    @SequenceGenerator(name = "registro_ejercicio_seq", sequenceName = "registro_ejercicio_seq", allocationSize = 1)

    private Long id;


    @Builder.Default
    @EqualsAndHashCode.Include
    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    private UUID publicId = UUID.randomUUID();

    /* ================== Datos de ejecución ================== */
    @Column(nullable = true)
    @Min(0)
    private Integer duracionMin;

    @Column(nullable = true)
    @Min(0)
    private Integer repeticiones;

    @Column(nullable = true)
    @Min(0)
    private Integer puntuacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoRegistroEjercicio estado;

    @Column(length = 1000)
    private String observaciones;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

    /* ================== Relaciones ================== */

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sesion_id", nullable = false)
    @ToString.Exclude
    private Sesion sesion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    @ToString.Exclude
    private Paciente paciente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ejercicio_id", nullable = false)
    @ToString.Exclude
    private Ejercicio ejercicio;
}

