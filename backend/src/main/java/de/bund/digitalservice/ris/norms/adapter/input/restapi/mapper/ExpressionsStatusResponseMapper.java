package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReleaseStatusResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReleaseStatusResponseSchema.Expression;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReleaseStatusResponseSchema.Expression.Status;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Mapper class for converting between List of {@link Norm}s and {@link ZielnormReleaseStatusResponseSchema}. */
public class ExpressionsStatusResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ExpressionsStatusResponseMapper() {}

  /**
   * Creates a {@link ZielnormReleaseStatusResponseSchema} instance from a List of {@link Norm} entities.
   *
   * @param norms The input List of {@link Norm} entities to be converted.
   * @return A new {@link ZielnormReleaseStatusResponseSchema} instance mapped from the input List of {@link Norm}.
   */
  public static ZielnormReleaseStatusResponseSchema fromNorms(final List<Norm> norms) {
    if (norms == null || norms.isEmpty()) {
      return null;
    }

    // Assuming all norms in the list belong to the same work (Zielnorm)
    Norm firstNorm = norms.getFirst();

    return new ZielnormReleaseStatusResponseSchema(
      firstNorm.getWorkEli().toString(),
      firstNorm.getTitle().orElse(null),
      firstNorm.getShortTitle().orElse(null),
      mapExpressions(norms)
    );
  }

  /**
   * Creates a {@link ZielnormReleaseStatusResponseSchema} instance from a Single {@link Norm}.
   *
   * @param norm The published {@link Norm} since no UNPUBLISHED norms exist.
   * @return A new {@link ZielnormReleaseStatusResponseSchema} instance mapped the {@link Norm}.
   */
  public static ZielnormReleaseStatusResponseSchema fromPublishedNorm(@NonNull final Norm norm) {
    return new ZielnormReleaseStatusResponseSchema(
      norm.getWorkEli().toString(),
      norm.getTitle().orElse(null),
      norm.getShortTitle().orElse(null),
      List.of()
    );
  }

  private static List<Expression> mapExpressions(List<Norm> norms) {
    return norms
      .stream()
      .map(norm ->
        new Expression(
          norm.getExpressionEli().toString(),
          norm.isGegenstandlos(),
          mapPublishStateToStatus(norm)
        )
      )
      .toList();
  }

  private static Status mapPublishStateToStatus(Norm norm) {
    Optional<String> publishState = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getMetadataValue(Metadata.STAND);
    return publishState
      .map(s ->
        switch (s) {
          case "volldokumentation" -> Status.VOLLDOKUMENTATION_RELEASED;
          case "praetext" -> Status.PRAETEXT_RELEASED;
          default -> Status.NOT_RELEASED;
        }
      )
      .orElse(Status.NOT_RELEASED);
  }
}
