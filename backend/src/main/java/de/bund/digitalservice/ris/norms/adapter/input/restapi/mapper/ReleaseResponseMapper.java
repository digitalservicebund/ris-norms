package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Mapper class for converting to a {@link ReleaseResponseSchema}. */
@Service
public class ReleaseResponseMapper {

  /**
   * Creates a {@link ReleaseResponseSchema} instance from an {@link Announcement} entity and the
   * affected {@link Norm}s.
   *
   * @param announcement The input {@link Announcement} entity to be converted.
   * @param affectedNorms The affected {@link Norm}s to be included.
   * @return A new {@link ReleaseResponseSchema} instance mapped from the input {@link
   *     Announcement}.
   */
  public static ReleaseResponseSchema fromAnnouncement(
      final Announcement announcement, final List<Norm> affectedNorms) {
    return de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema
        .builder()
        .amendingLawEli(announcement.getNorm().getEli().orElse(null))
        .releaseAt(announcement.getReleasedByDocumentalistAt())
        .zf0Elis(affectedNorms.stream().map(Norm::getEli).flatMap(Optional::stream).toList())
        .build();
  }

  /**
   * Creates a {@link ReleaseResponseSchema} instance from an {@link AmendingLaw} entity.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be converted.
   * @return A new {@link ReleaseResponseSchema} instance mapped from the input {@link AmendingLaw}.
   */
  public static ReleaseResponseSchema fromAmendingLaw(final AmendingLaw amendingLaw) {
    return ReleaseResponseSchema.builder()
        .releaseAt(amendingLaw.getReleasedAt())
        .amendingLawEli(amendingLaw.getEli())
        .zf0Elis(
            amendingLaw.getArticles().stream()
                .map(Article::getTargetLawZf0)
                .map(TargetLaw::getEli)
                .toList())
        .build();
  }
}
