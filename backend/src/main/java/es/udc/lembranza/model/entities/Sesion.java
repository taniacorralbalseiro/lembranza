package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.EstadoSesion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name="sesion",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="uk_sesion_grupo_fecha_hora",
                        columnNames={"grupo_id","fecha","hora_inicio"}
                )
        }
)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sesion_seq")
    @SequenceGenerator(name = "sesion_seq", sequenceName = "sesion_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    /** Fecha concreta de la sesión */
    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EstadoSesion estado;

    @Column(length = 1000)
    private String observaciones;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

    /* ================== Relaciones ================== */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clase_id", nullable = false)
    @ToString.Exclude
    private Clase clase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    @ToString.Exclude
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_imparte_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_empleado_sesion"))
    @ToString.Exclude
    private Empleado empleadoImparte;

    @OneToMany(mappedBy = "sesion", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<RegistroEjercicio> registros = new ArrayList<>();

    public void addRegistro(RegistroEjercicio r) {
        registros.add(r);
        r.setSesion(this);
    }
    public void removeRegistro(RegistroEjercicio r) {
        registros.remove(r);
        r.setSesion(null); // no borra en BD si no hay orphanRemoval
    }

}

