package es.udc.lembranza.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")   // o "USUARIO", como prefieras
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Admin extends Usuario {
    // sin campos extra
}
