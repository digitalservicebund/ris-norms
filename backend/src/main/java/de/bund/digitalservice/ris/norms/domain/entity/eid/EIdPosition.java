package de.bund.digitalservice.ris.norms.domain.entity.eid;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import org.w3c.dom.Element;

/**
 * The position information of a {@link EIdPart}.
 */
public sealed interface EIdPosition
  permits OrdinalEIdPosition, ZaehlbezeichnungsbasierteEIdPosition {
  /**
   * Find the position information that should be used for the last part of the eId of the element.
   * @param element the element whose position information should be determined
   * @param eIdPartType the {@link EIdPartType} of the element
   * @return the position information for the last part of the eId for the element
   */
  static EIdPosition findEIdPosition(Element element, EIdPartType eIdPartType) {
    if (eIdPartType.equals(EIdPartType.ART) || eIdPartType.equals(EIdPartType.ABS)) {
      if (forcesOrdinaleZaehlung(element)) {
        return OrdinalEIdPosition.findEIdPosition(element, eIdPartType);
      }

      return ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(element);
    }

    return OrdinalEIdPosition.findEIdPosition(element, eIdPartType);
  }

  private static boolean forcesOrdinaleZaehlung(Element element) {
    return NodeParser.getNodeFromExpression(
      "./num[@refersTo=\"ordinale-zaehlung-eid\"]",
      element
    ).isPresent();
  }
}
