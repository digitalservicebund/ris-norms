package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:FRBRManifestation */
@Getter
@SuperBuilder(toBuilder = true)
public class FRBRManifestation extends FRBR {

  public FRBRManifestation(final Node node, final String normEli) {
    super(node, normEli);
  }
}
