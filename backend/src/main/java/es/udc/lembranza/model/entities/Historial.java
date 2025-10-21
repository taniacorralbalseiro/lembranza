package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.GrupoSanguineo;
import es.udc.lembranza.model.entities.enumerados.NivelCognitivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "historial",
        uniqueConstraints = @UniqueConstraint(name = "uk_historial_paciente", columnNames = "paciente_id"))
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded=true)
@ToString
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_seq")
    @SequenceGenerator(name = "historial_seq", sequenceName = "historial_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    @NotNull
    private NivelCognitivo nivelCognitivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_sanguineo", length = 10, nullable = false)
    @NotNull
    private GrupoSanguineo grupoSanguineo;

    @Column(length = 2000, nullable = false)
    @NotBlank
    private String antecedentes;

    @Column(name = "diagnostico", length = 1000, nullable = false)
    @NotBlank
    private String diagnostico;

    @Column(length = 500)
    private String alergias;

    @Column(length = 2000)
    private String observaciones;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private Instant actualizadoEn;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_historial_paciente_paciente"))
    @ToString.Exclude
    private Paciente paciente;

    @OneToMany(mappedBy = "historial", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("fecha DESC, id DESC")
    @ToString.Exclude
    @Builder.Default
    private List<EntradaHistorial> entradas = new ArrayList<>();

    public static Historial crearVacio() {
        return Historial.builder()
                .nivelCognitivo(NivelCognitivo.SIN_EVALUAR)
                .grupoSanguineo(GrupoSanguineo.O_POSITIVO) // o el que decidas como default
                .antecedentes("Sin antecedentes registrados")
                .diagnostico("Sin diagnóstico inicial")
                .build();
    }

    public void addEntrada(EntradaHistorial e) {
        this.entradas.add(e);
        e.setHistorial(this);
    }
    public void removeEntrada(EntradaHistorial e) {
        this.entradas.remove(e);
        e.setHistorial(null);
    }

}

