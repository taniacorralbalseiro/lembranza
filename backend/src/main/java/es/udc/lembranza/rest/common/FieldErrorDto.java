package es.udc.lembranza.rest.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDto {
    private String fieldName;
    private String message;
}
