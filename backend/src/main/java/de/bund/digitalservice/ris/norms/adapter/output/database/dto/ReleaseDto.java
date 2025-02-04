package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a release entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "releases")
public class ReleaseDto {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "released_at")
  private Instant releasedAt;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "release_norms",
    joinColumns = @JoinColumn(name = "release_id"),
    inverseJoinColumns = @JoinColumn(
      name = "eli_norm_manifestation",
      referencedColumnName = "eli_norm_manifestation"
    )
  )
  @Builder.Default
  private List<NormManifestationDto> norms = new ArrayList<>();
}
