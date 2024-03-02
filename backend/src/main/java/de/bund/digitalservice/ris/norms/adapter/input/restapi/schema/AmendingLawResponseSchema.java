package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Represents the response schema for a {@link AmendingLaw} entity in the REST API. This class is
 * used to structure the response data sent by the API.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class AmendingLawResponseSchema {

  private String eli;

  private String title;

  private String printAnnouncementGazette;

  private String digitalAnnouncementMedium;

  private LocalDate publicationDate;

  private String printAnnouncementPage;

  private String digitalAnnouncementEdition;
}
