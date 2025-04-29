package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReferenceSchema;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;

/**
 * Mapper between {@link ZielnormReference} and {@link ZielnormReferenceSchema}
 */
public class ZielnormReferenceMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ZielnormReferenceMapper() {}

  /**
   * Creates a {@link ZielnormReferenceSchema} instance from a {@link ZielnormReference} entity.
   *
   * @param reference The input {@link ZielnormReference} entity to be converted.
   * @return A new {@link ZielnormReferenceSchema} instance mapped from the input.
   */
  public static ZielnormReferenceSchema fromUseCaseData(final ZielnormReference reference) {
    return new ZielnormReferenceSchema(
      reference.getTyp(),
      reference.getGeltungszeit().toString(),
      reference.getEId().toString(),
      reference.getZielnorm().toString()
    );
  }

  /**
   * Creates a {@link UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData} instance from a {@link ZielnormReferenceSchema} entity.
   *
   * @param schema The input {@link ZielnormReferenceSchema} to be converted.
   * @return A new {@link UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData} instance mapped from the input.
   */
  public static UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData toUseCaseData(
    final ZielnormReferenceSchema schema
  ) {
    return new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
      schema.typ(),
      new Zeitgrenze.Id(schema.geltungszeit()),
      new EId(schema.eId()),
      NormWorkEli.fromString(schema.zielnorm())
    );
  }
}
