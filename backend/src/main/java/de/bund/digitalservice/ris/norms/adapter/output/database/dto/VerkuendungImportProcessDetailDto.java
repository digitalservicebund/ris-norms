package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.*;

/**
 * DTO representing detail information (like errors) related to the background
 * processing of a new Verkündung.
 */
@Entity
@Table(name = "verkuendung_import_process_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VerkuendungImportProcessDetailDto {

  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "type")
  private String type;

  @Column(name = "title")
  private String title;

  @Column(name = "detail")
  private String detail;
}
