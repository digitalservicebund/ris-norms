package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusErrorResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusProcessingOrSuccessResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;

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
      case CREATED, PROCESSING, SUCCESS -> new VerkuendungStatusProcessingOrSuccessResponseSchema(
        verkuendungImportProcess.getStatus().toString()
      );
      case ERROR -> new VerkuendungStatusErrorResponseSchema(
        VerkuendungImportProcess.Status.ERROR.toString(),
        verkuendungImportProcess.getDetail()
      );
    };
  }
}
