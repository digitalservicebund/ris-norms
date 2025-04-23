package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReferenceSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;

/**
 * Mapper between {@link ZielnormReference} and {@link ZielnormReferenceSchema}
 */
public class ZielnormReferenceMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ZielnormReferenceMapper() {}

  /**
   * Creates a {@link ZielnormReferenceSchema} instance from a {@link ZielnormReference} entity.
   *
   * @param norm The input {@link ZielnormReference} entity to be converted.
   * @return A new {@link ZielnormReferenceSchema} instance mapped from the input {@link Norm}.
   */
  public static ZielnormReferenceSchema fromUseCaseData(final ZielnormReference norm) {
    return new ZielnormReferenceSchema(
      norm.getTyp(),
      norm.getGeltungszeit(),
      norm.getEId().toString(),
      norm.getZielnorm().toString()
    );
  }
}
