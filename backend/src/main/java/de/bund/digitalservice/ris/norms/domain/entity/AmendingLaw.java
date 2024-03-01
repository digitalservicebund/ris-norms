package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents an amending law entity with various attributes. This class is annotated with Lombok
 * annotations for generating builders, getters, toString, and constructors.
 */
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AmendingLaw {

  private String eli;

  private String printAnnouncementGazette;

  private String digitalAnnouncementMedium;

  private LocalDate publicationDate;

  private String printAnnouncementPage;

  private String digitalAnnouncementEdition;

  private String title;

  private String xml;

  private List<Article> articles;
}
