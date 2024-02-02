package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ProcedureDTO;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;

/** Mapper class for converting between {@link ProcedureDTO} and {@link Procedure}. */
public class ProcedureMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ProcedureMapper() {}

  /**
   * Maps a {@link ProcedureDTO} to a {@link Procedure} entity.
   *
   * @param procedureDTO The input {@link ProcedureDTO} to be mapped.
   * @return A new {@link Procedure} entity mapped from the input {@link ProcedureDTO}.
   */
  public static Procedure mapToDomain(final ProcedureDTO procedureDTO) {

    return new Procedure(
        ProcedureState.valueOf(procedureDTO.getState()),
        procedureDTO.getEli(),
        procedureDTO.getPrintAnnouncementGazette(),
        procedureDTO.getPrintAnnouncementYear(),
        procedureDTO.getPrintAnnouncementPage());
  }

  /**
   * Maps a {@link Procedure} entity to a {@link ProcedureDTO}.
   *
   * @param procedure The input {@link Procedure} entity to be mapped.
   * @return A new {@link ProcedureDTO} mapped from the input {@link Procedure} entity.
   */
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
