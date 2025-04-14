package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusErrorResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusProcessingOrSuccessResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.util.stream.Collectors;

/** Mapper class for converting between {@link VerkuendungImportProcess} and {@link VerkuendungStatusResponseSchema}. */
public class VerkuendungStatusMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private VerkuendungStatusMapper() {}

  /**
   * Creates a {@link VerkuendungStatusResponseSchema} instance from a {@link VerkuendungImportProcess} entity.
   * @param verkuendungImportProcess The input {@link VerkuendungImportProcess} entity to be converted.
   * @return A new {@link VerkuendungStatusResponseSchema}.
   */
  public static VerkuendungStatusResponseSchema fromVerkuendungImportProcess(
    VerkuendungImportProcess verkuendungImportProcess
  ) {
    return switch (verkuendungImportProcess.getStatus()) {
      case CREATED -> new VerkuendungStatusProcessingOrSuccessResponseSchema(
        VerkuendungImportProcess.Status.CREATED.toString()
      );
      case PROCESSING -> new VerkuendungStatusProcessingOrSuccessResponseSchema(
        VerkuendungImportProcess.Status.PROCESSING.toString()
      );
      case SUCCESS -> new VerkuendungStatusProcessingOrSuccessResponseSchema(
        VerkuendungImportProcess.Status.SUCCESS.toString()
      );
      case ERROR -> new VerkuendungStatusErrorResponseSchema(
        VerkuendungImportProcess.Status.ERROR.toString(),
        verkuendungImportProcess.getDetail().getFirst().getType(),
        verkuendungImportProcess
          .getDetail()
          .stream()
          .map(VerkuendungImportProcessDetail::getTitle)
          .collect(Collectors.joining(" ;")),
        verkuendungImportProcess
          .getDetail()
          .stream()
          .map(VerkuendungImportProcessDetail::getDetail)
          .collect(Collectors.joining(" ;"))
      );
    };
  }
}
