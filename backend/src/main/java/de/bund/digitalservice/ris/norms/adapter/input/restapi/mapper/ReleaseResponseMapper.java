package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import org.springframework.stereotype.Service;

/** Mapper class for converting to a {@link ReleaseResponseSchema}. */
@Service
public class ReleaseResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ReleaseResponseMapper() {}

  /**
   * Creates a {@link ReleaseResponseSchema} instance from an {@link Release} entity
   *
   * @param release The input {@link Release} entity to be converted.
   * @return A new {@link ReleaseResponseSchema} instance mapped from the input {@link
   *     Release}.
   */
  public static ReleaseResponseSchema fromRelease(final Release release) {
    return ReleaseResponseSchema.builder()
      .norms(
        release
          .getPublishedNorms()
          .stream()
          .map(Norm::getRegelungstext1)
          .map(Regelungstext::getManifestationEli)
          .map(DokumentManifestationEli::toString)
          .toList()
      )
      .build();
  }
}
