package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;

/** Mapper class for converting between {@link DokumentDto} and {@link Dokument}. */
public class DokumentMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private DokumentMapper() {}

  /**
   * Maps a {@link DokumentDto} to the domain {@link Dokument}.
   *
   * @param dokumentDto The input {@link DokumentDto} to be mapped.
   * @return A new {@link Dokument} mapped from the input {@link DokumentDto}.
   */
  public static Dokument mapToDomain(final DokumentDto dokumentDto) {
    return switch (dokumentDto.getSubtype()) {
      case "regelungstext" -> new Regelungstext(XmlMapper.toDocument(dokumentDto.getXml()));
      case "offene-struktur" -> new OffeneStruktur(XmlMapper.toDocument(dokumentDto.getXml()));
      default -> throw new IllegalArgumentException(
        "Dokument subtype " + dokumentDto.getSubtype() + " not supported"
      );
    };
  }

  /**
   * Maps a domain {@link Dokument} to {@link DokumentDto}.
   *
   * @param dokument The input {@link Dokument} to be mapped.
   * @return A new {@link DokumentDto} mapped from the input {@link Dokument}.
   */
  public static DokumentDto mapToDto(final Dokument dokument) {
    return DokumentDto.builder().xml(XmlMapper.toString(dokument.getDocument())).build();
  }
}
