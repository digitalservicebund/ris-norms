package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

/**
 * DTO for an element of getting the list of all norm works.
 */
@AllArgsConstructor
@Getter
public class NormWorkListElementDto {

  @Immutable
  @Column(name = "eli_norm_work")
  private String eliNormWork;

  @Immutable
  @Column(name = "title")
  private String title;
}
