package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.hibernate.generator.EventType.INSERT;
import static org.hibernate.generator.EventType.UPDATE;

import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
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

  @Generated(event = { INSERT, UPDATE })
  @Column(name = "guid", updatable = false, insertable = false)
  private UUID guid;

  @Generated(event = { INSERT, UPDATE })
  @Column(name = "eli_work", updatable = false, insertable = false)
  private String eliWork;

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

  @Column
  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @NotNull
  @Builder.Default
  private NormPublishState publishState = NormPublishState.UNPUBLISHED;
}
