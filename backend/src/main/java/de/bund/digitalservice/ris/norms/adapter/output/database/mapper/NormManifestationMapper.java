package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/** Mapper class for converting between {@link DokumentDto} and {@link Norm}. */
public class NormManifestationMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormManifestationMapper() {}

  /**
   * Maps a {@link NormManifestationDto} to the domain {@link Norm}.
   *
   * @param normManifestationDto The input {@link NormManifestationDto} to be mapped.
   * @return A new {@link Norm} mapped from the input {@link NormManifestationDto}.
   */
  public static Norm mapToDomain(@Nonnull final NormManifestationDto normManifestationDto) {
    return Norm
      .builder()
      .dokumente(
        normManifestationDto
          .getDokumente()
          .stream()
          .map(DokumentMapper::mapToDomain)
          .collect(Collectors.toSet())
      )
      .binaryFiles(
        normManifestationDto
          .getBinaryFiles()
          .stream()
          .map(BinaryFileMapper::mapToDomain)
          .collect(Collectors.toSet())
      )
      .publishState(normManifestationDto.getPublishState())
      .build();
  }

  /**
   * Maps a domain {@link Norm} to {@link NormManifestationDto}.
   *
   * @param norm The input {@link Norm} to be mapped.
   * @return A new {@link NormManifestationDto} mapped from the input {@link Norm}.
   */
  public static NormManifestationDto mapToDto(final Norm norm) {
    return NormManifestationDto
      .builder()
      .publishState(norm.getPublishState())
      .dokumente(norm.getDokumente().stream().map(DokumentMapper::mapToDto).toList())
      .binaryFiles(norm.getBinaryFiles().stream().map(BinaryFileMapper::mapToDto).toList())
      .build();
  }
}
