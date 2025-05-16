package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungDto;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/** Mapper class for converting between {@link VerkuendungDto} and {@link Verkuendung}. */
public class VerkuendungMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private VerkuendungMapper() {}

  /**
   * Maps a {@link VerkuendungDto} to the domain {@link Verkuendung}.
   *
   * @param verkuendungDto The input {@link VerkuendungDto} to be mapped.
   * @return A new {@link Verkuendung} mapped from the input {@link VerkuendungDto}.
   */
  public static Verkuendung mapToDomain(final VerkuendungDto verkuendungDto) {
    return Verkuendung.builder()
      .eli(NormExpressionEli.fromString(verkuendungDto.getEliNormExpression()))
      .importTimestamp(verkuendungDto.getImportTimestamp())
      .build();
  }

  /**
   * Maps a domain {@link Verkuendung} to {@link VerkuendungDto}.
   *
   * @param verkuendung The input {@link Verkuendung} to be mapped.
   * @return A new {@link VerkuendungDto} mapped from the input {@link Verkuendung}.
   */
  public static VerkuendungDto mapToDto(final Verkuendung verkuendung) {
    return VerkuendungDto.builder().eliNormExpression(verkuendung.getEli().toString()).build();
  }
}
