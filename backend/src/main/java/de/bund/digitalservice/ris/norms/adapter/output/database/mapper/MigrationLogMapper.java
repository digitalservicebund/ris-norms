package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;

/**
 * Mapper class for converting from {@link MigrationLogDto} to {@link MigrationLog}.
 */
public class MigrationLogMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private MigrationLogMapper() {}

  /**
   * Maps a {@link MigrationLogDto} to the domain {@link MigrationLog}.
   *
   * @param migrationLogDto The input {@link MigrationLogDto} to be mapped.
   * @return A new {@link MigrationLog} mapped from the input {@link MigrationLogDto}.
   */
  public static MigrationLog mapToDomain(final MigrationLogDto migrationLogDto) {
    return MigrationLog
      .builder()
      .size(migrationLogDto.getSize())
      .createdAt(migrationLogDto.getCreatedAt())
      .build();
  }

  /**
   * Maps a {@link MigrationLog} to the domain {@link MigrationLogDto}.
   *
   * @param migrationLog The input {@link MigrationLog} to be mapped.
   * @return A new {@link MigrationLogDto} mapped from the input {@link MigrationLog}.
   */
  public static MigrationLogDto mapToDto(final MigrationLog migrationLog) {
    return MigrationLogDto
      .builder()
      .createdAt(migrationLog.getCreatedAt())
      .size(migrationLog.getSize())
      .build();
  }
}
