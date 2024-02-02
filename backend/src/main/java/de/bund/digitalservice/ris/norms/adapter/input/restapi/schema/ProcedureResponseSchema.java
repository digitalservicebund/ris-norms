package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the response schema for a {@link Procedure} entity in the REST API. This class is used
 * to structure the response data sent by the API.
 */
@AllArgsConstructor
@Data
public class ProcedureResponseSchema {

  private String state;
  private String eli;
  private String printAnnouncementGazette;
  private String printAnnouncementYear;
  private String printAnnouncementPage;

  /**
   * Creates a {@link ProcedureResponseSchema} instance from a {@link Procedure} entity.
   *
   * @param procedureData The input {@link Procedure} entity to be converted.
   * @return A new {@link ProcedureResponseSchema} instance mapped from the input {@link Procedure}.
   */
  public static ProcedureResponseSchema fromUseCaseData(final Procedure procedureData) {
    return new ProcedureResponseSchema(
        procedureData.getState().name(),
        procedureData.getEli(),
        procedureData.getPrintAnnouncementGazette(),
        procedureData.getPrintAnnouncementYear(),
        procedureData.getPrintAnnouncementPage());
  }
}
