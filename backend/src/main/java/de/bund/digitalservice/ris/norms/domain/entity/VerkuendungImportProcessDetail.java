package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain entity representing detail information (like errors) related to
 * the background processing of a new Verkündung.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class VerkuendungImportProcessDetail {

  private String type;

  private String title;

  private String detail;
}
