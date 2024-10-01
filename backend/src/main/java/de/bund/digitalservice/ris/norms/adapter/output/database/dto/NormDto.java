package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.hibernate.generator.EventType.INSERT;
import static org.hibernate.generator.EventType.UPDATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Data Transfer Object (DTO) class representing a norm entity. This class is annotated with Lombok
 * annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "norms")
public class NormDto {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  private UUID guid;

  @Generated(event = { INSERT, UPDATE })
  @Column(name = "eli_expression", updatable = false, insertable = false)
  private String eliExpression;

  @Generated(event = { INSERT, UPDATE })
  @Column(name = "eli_manifestation", updatable = false, insertable = false)
  private String eliManifestation;

  @NotNull
  @Column
  @JdbcTypeCode(SqlTypes.SQLXML)
  private String xml;
}
