package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class representing the norms-application-only metadata
 */
public class CustomModsMetadata {

  private final Element element;

  public CustomModsMetadata(Element element) {
    this.element = element;
  }

  /**
   * Get a list of all the norm expressions that have been amended by this norm
   * @return all norm expression elis amended by this norm
   */
  public List<NormExpressionEli> getAmendedNormExpressionElis() {
    return NodeParser
      .getNodesFromExpression("./amended-norm-expressions/norm-expression/text()", element)
      .stream()
      .map(Node::getNodeValue)
      .map(NormExpressionEli::fromString)
      .toList();
  }
}
