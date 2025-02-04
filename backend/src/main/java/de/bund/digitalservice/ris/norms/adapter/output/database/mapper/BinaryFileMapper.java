package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;

/** Mapper class for converting between {@link BinaryFileDto} and {@link BinaryFile}. */
public class BinaryFileMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private BinaryFileMapper() {}

  /**
   * Maps a {@link BinaryFileDto} to the domain {@link BinaryFile}.
   *
   * @param binaryFileDto The input {@link BinaryFileDto} to be mapped.
   * @return A new {@link BinaryFile} mapped from the input {@link BinaryFileDto}.
   */
  public static BinaryFile mapToDomain(final BinaryFileDto binaryFileDto) {
    return new BinaryFile(
      DokumentManifestationEli.fromString(binaryFileDto.getEliDokumentManifestation()),
      binaryFileDto.getContent()
    );
  }

  /**
   * Maps a domain {@link BinaryFile} to {@link BinaryFileDto}.
   *
   * @param binaryFile The input {@link BinaryFile} to be mapped.
   * @return A new {@link BinaryFileDto} mapped from the input {@link BinaryFile}.
   */
  public static BinaryFileDto mapToDto(final BinaryFile binaryFile) {
    return new BinaryFileDto(
      binaryFile.getDokumentManifestationEli().toString(),
      binaryFile.getContent()
    );
  }
}
