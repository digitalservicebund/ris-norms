package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/** Mapper class for converting between {@link DokumentDto} and {@link Norm}. */
public class NormMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormMapper() {}

  /**
   * Maps multiple {@link DokumentDto}s to the domain {@link Norm}.
   *
   * @param dokumentDtos The input {@link DokumentDto}s to be mapped.
   * @return A new {@link Norm} mapped from the input {@link DokumentDto}.
   */
  public static Optional<Norm> mapToDomain(@Nonnull final Collection<DokumentDto> dokumentDtos) {
    if (dokumentDtos.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
      Norm
        .builder()
        .regelungstexte(
          dokumentDtos.stream().map(RegelungstextMapper::mapToDomain).collect(Collectors.toSet())
        )
        .publishState(dokumentDtos.stream().findFirst().get().getPublishState())
        .build()
    );
  }

  /**
   * Maps a domain {@link Norm} to {@link DokumentDto}s for each of the dokuments of the norm.
   *
   * @param norm The input {@link Norm} to be mapped.
   * @return A new {@link DokumentDto}s mapped from the input {@link Norm}.
   */
  public static Set<DokumentDto> mapToDtos(final Norm norm) {
    return norm
      .getRegelungstexte()
      .stream()
      .map(regelungstext -> RegelungstextMapper.mapToDto(regelungstext, norm.getPublishState()))
      .collect(Collectors.toSet());
  }
}
