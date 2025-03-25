package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain entity representing information about the progress of the background
 * processing of a new Verkündung.
 */
@Getter
@Setter
@Builder
public class VerkuendungImportProcess {

  private UUID id;

  private Status status;

  private Instant createdAt;

  private Instant startedAt;

  private Instant finishedAt;

  private List<VerkuendungImportProcessDetail> detail;

  /**
   * The different statuses that a background process can have.
   */
  public enum Status {
    CREATED,
    PROCESSING,
    SUCCESS,
    ERROR,
  }
}
