package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.UUID;
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
public class VerkuendungImportProcessDetail {

  private UUID id;

  private String type;

  private String title;

  private String detail;
}
