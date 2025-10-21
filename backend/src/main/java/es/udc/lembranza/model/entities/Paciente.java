package es.udc.lembranza.model.entities;

import es.udc.lembranza.model.entities.enumerados.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "paciente",
        indexes = {
                @Index(name = "ix_paciente_centro", columnList = "centro_id"),
                @Index(name = "ix_paciente_grupo", columnList = "grupo_id")
        }
)
@DiscriminatorValue("PACIENTE")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder
public class Paciente extends Usuario {

    @Column(name = "fecha_alta_centro", nullable = false)
    @NotNull
    private LocalDate fechaAltaCentro;

    @Column(name = "fecha_baja_centro")
    private LocalDate fechaBajaCentro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_paciente", length = 45, nullable = false)
    @Builder.Default
    @NotNull
    private EstadoPaciente estadoPaciente = EstadoPaciente.ACTIVO;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_educativo", length = 45,  nullable = false)
    @NotNull
    private NivelEducativo nivelEducativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacion_legal", length = 40, nullable = false)
    @NotNull
    private SituacionLegal situacionLegal;

    @Column(name = "observaciones_no_clinicas", length = 500)
    private String observacionesNoClinicas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil", length = 40, nullable = false)
    @NotNull
    private EstadoCivil estadoCivil;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "centro_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_centro_paciente"))
    @ToString.Exclude
    private Centro centro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_grupo_paciente"))
    @ToString.Exclude
    private Grupo grupo;

    @OneToOne(mappedBy = "paciente", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ToString.Exclude
    private Historial historial;

    @OneToMany(mappedBy = "paciente",  fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<ContactoEmergencia> contactosEmergencia = new ArrayList<>();


    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ToString.Exclude
    @Builder.Default
    private List<RegistroEjercicio> registros = new ArrayList<>();


    public void setHistorial(Historial historial) {
        if (historial == null) {
            throw new IllegalArgumentException("No se puede asignar un historial nulo");
        }

        // Si ya está asignado a otro paciente distinto, error
        if (historial.getPaciente() != null && historial.getPaciente() != this) {
            throw new IllegalStateException("Ese historial ya pertenece a otro paciente");
        }

        // Romper la relación anterior si había
        if (this.historial != null) {
            this.historial.setPaciente(null);
        }

        this.historial = historial;
        if (historial.getPaciente() != this) {
            historial.setPaciente(this);
        }
    }

    public void removeHistorial() {
        if (this.historial != null) {
            Historial old = this.historial;
            this.historial = null;
            if (old.getPaciente() == this) {
                old.setPaciente(null);
            }
        }
    }

    public void addContactoEmergencia(ContactoEmergencia c) {
        contactosEmergencia.add(c);
        c.setPaciente(this);
    }

    public void removeContactoEmergencia(ContactoEmergencia c) {
        contactosEmergencia.remove(c);
        c.setPaciente(null); // lo borrará en BD por orphanRemoval
    }


    public void addRegistro(RegistroEjercicio r) {
        this.registros.add(r);
        r.setPaciente(this);
    }
    public void removeRegistro(RegistroEjercicio r) {
        this.registros.remove(r);
        r.setPaciente(null); // no borra en BD (no hay orphanRemoval)
    }

}

