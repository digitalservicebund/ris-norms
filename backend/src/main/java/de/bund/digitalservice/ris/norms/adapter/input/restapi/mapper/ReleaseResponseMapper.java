package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/** Mapper class for converting to a {@link ReleaseResponseSchema}. */
@Service
public class ReleaseResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ReleaseResponseMapper() {}

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
    final Announcement announcement,
    final List<Norm> affectedNorms
  ) {
    return ReleaseResponseSchema
      .builder()
      .amendingLawEli(announcement.getEli().toString())
      .releaseAt(
        announcement
          .getReleases()
          .stream()
          .map(Release::getReleasedAt)
          .max(Comparator.naturalOrder())
          .orElse(null)
      )
      .zf0Elis(
        affectedNorms.stream().map(Norm::getExpressionEli).map(ExpressionEli::toString).toList()
      )
      .build();
  }
}
