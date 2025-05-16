package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.stream.Stream;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Collection of {@link NormExpressionEli}s that are amended by this Verkündung
 * See also: ADR-0016
 */
@Getter
public class AmendedNormExpressions extends AbstractSet<NormExpressionEli> {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "amended-norm-expressions";
  public static final String NORM_EXPRESSION_TAG_NAME = "norm-expression";

  private final Element element;

  public AmendedNormExpressions(Element element) {
    this.element = element;
  }

  /**
   * Creates a new norms:amended-norm-expressions element and appends it to the given node.
   * @param parentNode the node under which a new {@link AmendedNormExpressions} should be created.
   * @return the newly created {@link AmendedNormExpressions}
   */
  public static AmendedNormExpressions createAndAppend(Node parentNode) {
    return new AmendedNormExpressions(NodeCreator.createElement(NAMESPACE, TAG_NAME, parentNode));
  }

  @Override
  public boolean add(NormExpressionEli normExpressionEli) {
    if (contains(normExpressionEli)) {
      return false;
    }

    var newNode = NodeCreator.createElement(NAMESPACE, NORM_EXPRESSION_TAG_NAME, getElement());
    newNode.setTextContent(normExpressionEli.toString());

    return true;
  }

  @Override
  public Stream<NormExpressionEli> stream() {
    return NodeParser.getNodesFromExpression(
      "./%s/text()".formatted(AmendedNormExpressions.NORM_EXPRESSION_TAG_NAME),
      getElement()
    )
      .stream()
      .map(Node::getNodeValue)
      .map(NormExpressionEli::fromString);
  }

  @Override
  public Iterator<NormExpressionEli> iterator() {
    return stream().iterator();
  }

  @Override
  public int size() {
    return (int) stream().count();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    // ignoring element. We only care about the content of it and that is checked by the implementation in AbstractSet
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    // ignoring element. We only care about the content of it and that is hashed by the implementation in AbstractSet
    return super.hashCode();
  }
}
