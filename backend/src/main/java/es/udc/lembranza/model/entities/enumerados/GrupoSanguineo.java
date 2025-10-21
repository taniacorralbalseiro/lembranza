package es.udc.lembranza.model.entities.enumerados;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GrupoSanguineo {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String codigo;


    GrupoSanguineo(String codigo) { this.codigo = codigo; }

    @JsonValue
    public String getCodigo() { return codigo; }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GrupoSanguineo fromLabel(String codigo) {
        for (var g : values()) if (g.codigo.equalsIgnoreCase(codigo)) return g;
        throw new IllegalArgumentException("Grupo sanguíneo inválido: " + codigo);
    }
}
