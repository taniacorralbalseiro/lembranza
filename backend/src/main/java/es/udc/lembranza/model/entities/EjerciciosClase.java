package es.udc.lembranza.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ejercicios_clase",uniqueConstraints = {
        @UniqueConstraint(name="uk_clase_orden", columnNames={"clase_id","orden"}),
        @UniqueConstraint(name="uk_clase_ejercicio", columnNames={"clase_id","ejercicio_id"}) })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class EjerciciosClase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ejercicios_clase_seq")
    @SequenceGenerator(name = "ejercicios_clase_seq", sequenceName = "ejercicios_clase_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true, columnDefinition = "uuid")
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID publicId = UUID.randomUUID();

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer orden;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clase_id", nullable = false)
    @ToString.Exclude
    private Clase clase;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ejercicio_id", nullable = false)
    @ToString.Exclude
    private Ejercicio ejercicio;
}

