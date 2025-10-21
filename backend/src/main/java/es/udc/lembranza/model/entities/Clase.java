package es.udc.lembranza.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clase")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@Builder
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clase_seq")
    @SequenceGenerator(name = "clase_seq", sequenceName = "clase_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @NotNull
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;


    /** Ventana de planificación (rango) */
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer duracionMin;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @OrderBy("orden ASC")
    private List<EjerciciosClase> ejerciciosClase = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_imparte_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_clase_empleado"))
    @ToString.Exclude
    private Empleado empleadoPrograma;



    public void addEjercicio(EjerciciosClase ec) {
        ejerciciosClase.add(ec);
        ec.setClase(this);
    }
    public void removeEjercicio(EjerciciosClase ec) {
        ejerciciosClase.remove(ec);
        ec.setClase(null); // lo borrará por orphanRemoval
    }

}