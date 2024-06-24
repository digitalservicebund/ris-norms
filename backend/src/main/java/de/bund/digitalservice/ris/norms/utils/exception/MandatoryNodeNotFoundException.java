package de.bund.digitalservice.ris.norms.utils.exception;

import lombok.Getter;

/**
 * This exception indicates that a mandatory XML node was not found. If node name is passed, the
 * xpath is relative. If norm eli not passed is because the node containing the eli is not present.
 */
@Getter
public class MandatoryNodeNotFoundException extends RuntimeException {

  public MandatoryNodeNotFoundException(final String xpath) {
    super(String.format("Element with xpath '%s' not found", xpath));
  }

  public MandatoryNodeNotFoundException(final String xpath, final String normEli) {
    super(String.format("Element with xpath '%s' not found in norm '%s'", xpath, normEli));
  }

  public MandatoryNodeNotFoundException(
      final String xpath, final String nodeName, final String normEli) {
    super(
        String.format(
            "Element with xpath '%s' not found in '%s' of norm '%s'", xpath, nodeName, normEli));
  }
}
