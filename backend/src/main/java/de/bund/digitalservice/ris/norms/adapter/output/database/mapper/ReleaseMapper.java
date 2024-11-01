package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.domain.entity.Release;

/** Mapper class for converting between {@link ReleaseDto} and {@link Release}. */
public class ReleaseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ReleaseMapper() {}

  /**
   * Maps a {@link ReleaseDto} to the domain {@link Release}.
   *
   * @param releaseDto The input {@link ReleaseDto} to be mapped.
   * @return A new {@link Release} mapped from the input {@link ReleaseDto}.
   */
  public static Release mapToDomain(final ReleaseDto releaseDto) {
    return Release
      .builder()
      .releasedAt(releaseDto.getReleasedAt())
      .publishedNorms(releaseDto.getNorms().stream().map(NormMapper::mapToDomain).toList())
      .build();
  }

  /**
   * Maps a domain {@link Release} to {@link ReleaseDto}.
   *
   * @param release The input {@link Release} to be mapped.
   * @return A new {@link ReleaseDto} mapped from the input {@link Release}.
   */
  public static ReleaseDto mapToDto(final Release release) {
    return ReleaseDto.builder().releasedAt(release.getReleasedAt()).build();
  }
}
