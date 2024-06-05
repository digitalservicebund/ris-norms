package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:analysis */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Analysis {

  private final Node node;
}
