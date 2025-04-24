package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 * Domain entity representing information about the progress of the background
 * processing of a new Verkündung.
 */
@Getter
@Builder
public class VerkuendungImportProcess {

  private UUID id;

  private Status status;

  private Instant createdAt;

  private Instant startedAt;

  private Instant finishedAt;

  private String detail;

  /**
   * The different statuses that a background process can have.
   */
  public enum Status {
    CREATED,
    PROCESSING,
    SUCCESS,
    ERROR;

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }
}
