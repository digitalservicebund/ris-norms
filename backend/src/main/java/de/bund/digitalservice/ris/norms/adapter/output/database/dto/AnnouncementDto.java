package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

/**
 * Data Transfer Object (DTO) class representing an announcement entity. This class is annotated
 * with Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "announcements")
public class AnnouncementDto {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "eli_norm_expression")
  private String eliNormExpression;

  @Generated(event = EventType.INSERT)
  @Column(name = "import_timestamp", updatable = false, insertable = false)
  private Instant importTimestamp;
}
