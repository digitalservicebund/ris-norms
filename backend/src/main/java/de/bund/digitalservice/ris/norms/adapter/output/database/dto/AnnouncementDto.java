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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "eli")
  private String eli;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "announcement_releases",
    joinColumns = @JoinColumn(name = "announcement_id"),
    inverseJoinColumns = @JoinColumn(name = "release_id")
  )
  @Builder.Default
  private List<ReleaseDto> releases = new ArrayList<>();
}
