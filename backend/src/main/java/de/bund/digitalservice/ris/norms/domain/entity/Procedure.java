package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@Getter
@NoArgsConstructor
@Data
public class Procedure {

    private UUID uuid;
    private String eli;
    private ProcedureState state;

}
