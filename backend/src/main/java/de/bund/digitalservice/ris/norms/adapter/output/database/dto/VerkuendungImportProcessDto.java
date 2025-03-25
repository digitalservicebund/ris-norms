package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO representing information about the progress of the background processing
 * of a new Verkündung.
 */
@Entity
@Table(name = "verkuendung_import_processes")
@Getter
@Setter
@NoArgsConstructor
public class VerkuendungImportProcessDto {

  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "started_at")
  private Instant startedAt;

  @Column(name = "finished_at")
  private Instant finishedAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
  private List<VerkuendungImportProcessDetailDto> detail;

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
