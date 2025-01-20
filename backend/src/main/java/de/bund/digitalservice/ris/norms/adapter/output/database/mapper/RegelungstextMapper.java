package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;

/** Mapper class for converting between {@link DokumentDto} and {@link Regelungstext}. */
public class RegelungstextMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private RegelungstextMapper() {}

  /**
   * Maps a {@link DokumentDto} to the domain {@link Norm}.
   *
   * @param dokumentDto The input {@link DokumentDto} to be mapped.
   * @return A new {@link Regelungstext} mapped from the input {@link DokumentDto}.
   */
  public static Regelungstext mapToDomain(final DokumentDto dokumentDto) {
    return new Regelungstext(XmlMapper.toDocument(dokumentDto.getXml()));
  }

  /**
   * Maps a domain {@link Regelungstext} to {@link DokumentDto}.
   *
   * @param regelungstext The input {@link Regelungstext} to be mapped.
   * @param publishState The publishState of the {@link Norm} associated with the {@link Regelungstext}. For now this is still stored on every dokument.
   * @return A new {@link DokumentDto} mapped from the input {@link Regelungstext}.
   */
  public static DokumentDto mapToDto(
    final Regelungstext regelungstext,
    final NormPublishState publishState
  ) {
    return DokumentDto
      .builder()
      .xml(XmlMapper.toString(regelungstext.getDocument()))
      .publishState(publishState)
      .build();
  }
}
