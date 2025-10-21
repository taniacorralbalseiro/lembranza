package es.udc.lembranza.rest.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDto {

    private String globalError;
    private List<FieldErrorDto> fieldErrors;

    // Constructores de conveniencia (como en tu ejemplo)
    public ErrorsDto(String globalError) {
        this.globalError = globalError;
    }

    public ErrorsDto(List<FieldErrorDto> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}