package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a procedure entity with various attributes. This class is annotated with Lombok
 * annotations for generating builders, getters, toString, and constructors.
 */
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@Getter
@AllArgsConstructor
@Data
public class Procedure {

  private ProcedureState state;
  private String eli;
  private String printAnnouncementGazette;
  private String printAnnouncementYear;
  private String printAnnouncementPage;
}
