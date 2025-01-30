package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

/**
 * Data Transfer Object (DTO) class representing a norm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "norm_manifestation")
public class NormManifestationDto {

  @Id
  @Immutable
  @Column(name = "eli_norm_manifestation")
  private String manifestationEli;

  @Immutable
  @Column(name = "eli_norm_expression")
  private String expressionEli;

  @Immutable
  @Column(name = "expression_aktuelle_version_id")
  private UUID expressionAktuelleVersionId;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "eliNormManifestation")
  @Column(name = "eli_norm_manifestation")
  private List<DokumentDto> dokumente;

  @Column
  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @NotNull
  @Builder.Default
  private NormPublishState publishState = NormPublishState.UNPUBLISHED;
}
