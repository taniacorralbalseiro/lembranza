package es.udc.lembranza.rest.dtos;

import es.udc.lembranza.rest.dtos.enums.EstadoEmpleadoDto;
import es.udc.lembranza.rest.dtos.enums.PuestoEmpleadoDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class EmpleadoDetailsDto {

    private UUID publicId;

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    private String nColegiado;
    private String especialidad;

    private LocalDate fechaNacimiento;   // opcional si quieres mostrarlo en detalle
    private LocalDate fechaContratacion;
    private LocalDate fechaCese;         // puede ser null

    private BigDecimal salario;

    private PuestoEmpleadoDto puesto;
    private EstadoEmpleadoDto estado;

    private UUID centroPublicId;         // para identificar centro en el front
}
