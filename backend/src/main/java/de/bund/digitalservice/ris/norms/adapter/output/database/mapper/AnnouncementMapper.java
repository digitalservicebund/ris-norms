package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/** Mapper class for converting between {@link AnnouncementDto} and {@link Announcement}. */
public class AnnouncementMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private AnnouncementMapper() {}

  /**
   * Maps a {@link AnnouncementDto} to the domain {@link Announcement}.
   *
   * @param announcementDto The input {@link AnnouncementDto} to be mapped.
   * @return A new {@link Announcement} mapped from the input {@link AnnouncementDto}.
   */
  public static Announcement mapToDomain(final AnnouncementDto announcementDto) {
    return Announcement
      .builder()
      .eli(NormExpressionEli.fromString(announcementDto.getEliNormExpression()))
      .importTimestamp(announcementDto.getImportTimestamp())
      .build();
  }

  /**
   * Maps a domain {@link Announcement} to {@link AnnouncementDto}.
   *
   * @param announcement The input {@link Announcement} to be mapped.
   * @return A new {@link AnnouncementDto} mapped from the input {@link Announcement}.
   */
  public static AnnouncementDto mapToDto(final Announcement announcement) {
    return AnnouncementDto.builder().eliNormExpression(announcement.getEli().toString()).build();
  }
}
