package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Represents the response schema for a {@link Norm} entity in the REST API. This class is used to
 * structure the response data sent by the API.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class NormResponseSchema {

  private String eli;

  private String title;

  private String shortTitle;

  private String fna;

  private String frbrName;

  private String frbrNumber;

  /**
   * @deprecated use {@link #frbrName} instead
   */
  @Deprecated(forRemoval = true, since = "2024-04-18")
  private String printAnnouncementGazette;

  /**
   * @deprecated use {@link #frbrName} instead
   */
  @Deprecated(forRemoval = true, since = "2024-04-18")
  private String digitalAnnouncementMedium;

  private LocalDate publicationDate;

  /**
   * @deprecated use {@link #frbrNumber} instead
   */
  @Deprecated(forRemoval = true, since = "2024-04-18")
  private String printAnnouncementPage;

  /**
   * @deprecated use {@link #frbrNumber} instead
   */
  @Deprecated(forRemoval = true, since = "2024-04-18")
  private String digitalAnnouncementEdition;
}
