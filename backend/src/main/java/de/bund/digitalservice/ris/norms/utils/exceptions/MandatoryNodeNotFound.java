package de.bund.digitalservice.ris.norms.utils.exceptions;

import lombok.Getter;

/**
 * This exception indicates that a mandatory XML node was not found. If node name is passed, the
 * xpath is relative. If norm eli not passed is because the node containing the eli is not present.
 */
@Getter
public class MandatoryNodeNotFound extends RuntimeException {

  public MandatoryNodeNotFound(String xpath) {
    super(String.format("Element '%s' not found", xpath));
  }

  public MandatoryNodeNotFound(String xpath, String normEli) {
    super(String.format("Element '%s' not found in norm '%s'", xpath, normEli));
  }

  public MandatoryNodeNotFound(String xpath, String nodeName, String normEli) {
    super(String.format("Element '%s' not found in '%s' of norm '%s'", xpath, nodeName, normEli));
  }
}
