package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProcedureResponseSchema {

  private String state;
  private String eli;
  private String printAnnouncementGazette;
  private String printAnnouncementYear;
  private String printAnnouncementPage;

  public static ProcedureResponseSchema fromUseCaseData(final Procedure procedureData) {
    return new ProcedureResponseSchema(
        procedureData.getState().name(),
        procedureData.getEli(),
        procedureData.getPrintAnnouncementGazette(),
        procedureData.getPrintAnnouncementYear(),
        procedureData.getPrintAnnouncementPage());
  }
}
