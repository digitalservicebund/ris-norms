package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ProcedureDTO;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;

public class ProcedureMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ProcedureMapper() {}

  public static Procedure mapToDomain(final ProcedureDTO procedureDTO) {

    return new Procedure(
        ProcedureState.valueOf(procedureDTO.getState()),
        procedureDTO.getEli(),
        procedureDTO.getPrintAnnouncementGazette(),
        procedureDTO.getPrintAnnouncementYear(),
        procedureDTO.getPrintAnnouncementPage());
  }

  public static ProcedureDTO mapToDto(final Procedure procedure) {
    return ProcedureDTO.builder()
        .state(procedure.getState().name())
        .eli(procedure.getEli())
        .printAnnouncementGazette(procedure.getPrintAnnouncementGazette())
        .printAnnouncementYear(procedure.getPrintAnnouncementYear())
        .printAnnouncementPage(procedure.getPrintAnnouncementPage())
        .build();
  }
}
