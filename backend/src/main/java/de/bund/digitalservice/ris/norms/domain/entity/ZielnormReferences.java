package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A norms:zielnorm-references. A collection of {@link ZielnormReference}, see also: ADR-0015
 */
@Getter
@AllArgsConstructor
public class ZielnormReferences extends AbstractCollection<ZielnormReference> {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "zielnorm-references";

  private final Element element;

  /**
   * Creates a new norms:zielnorm-references element and appends it to the given node.
   * @param parentNode the node under which a new {@link ZielnormReferences} should be created.
   * @return the newly created {@link ZielnormReferences}
   */
  public static ZielnormReferences createAndAppend(Node parentNode) {
    return new ZielnormReferences(NodeCreator.createElement(NAMESPACE, TAG_NAME, parentNode));
  }

  public ZielnormReference add(
    String typ,
    Zeitgrenze.Id geltungszeit,
    EId eId,
    NormWorkEli zielnorm
  ) {
    return ZielnormReference.createAndAppend(getElement(), typ, geltungszeit, eId, zielnorm);
  }

  @NotNull
  @Override
  public Stream<ZielnormReference> stream() {
    return NodeParser.getElementsFromExpression(
      "./%s".formatted(ZielnormReference.TAG_NAME),
      getElement()
    )
      .stream()
      .map(ZielnormReference::new);
  }

  @NotNull
  @Override
  public Iterator<ZielnormReference> iterator() {
    return stream().iterator();
  }

  @Override
  public boolean remove(Object o) {
    if (!(o instanceof ZielnormReference zielnormReference)) return false;

    if (zielnormReference.getElement().getParentNode() != getElement()) return false;

    getElement().removeChild(zielnormReference.getElement());
    return true;
  }

  @Override
  public int size() {
    return (int) stream().count();
  }
}
