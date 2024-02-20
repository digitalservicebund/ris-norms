package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a amending law entity. This class is annotated with
 * Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "amending_law")
public class AmendingLawDTO {
  @Id @GeneratedValue private UUID id;

  @NotNull
  @Size(max = 255)
  @Column
  private String eli;

  @NotNull
  @Size(max = 15)
  @Column(name = "print_announcement_gazette")
  private String printAnnouncementGazette;

  @NotNull
  @Size(max = 15)
  @Column(name = "digital_announcement_medium")
  private String digitalAnnouncementMedium;

  @NotNull
  @Column(name = "publication_date")
  private LocalDate publicationDate;

  @NotNull
  @Size(max = 15)
  @Column(name = "printed_announcement_page")
  private String printAnnouncementPage;

  @NotNull
  @Size(max = 15)
  @Column(name = "digital_announcement_edition")
  private String digitalAnnouncementEdition;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "amending_law_id")
  private List<ArticleDto> articleDtos;
}
