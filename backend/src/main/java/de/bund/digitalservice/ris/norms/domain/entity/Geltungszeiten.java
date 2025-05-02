package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;

/**
 * A norms:geltungszeiten. A collection of {@link Zeitgrenze}
 */
@AllArgsConstructor
public class Geltungszeiten extends AbstractCollection<Zeitgrenze> {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "geltungszeiten";

  @Getter
  private final Element element;

  private final Function<Zeitgrenze, Boolean> zeitgrenzeInUseProvider;

  /**
   * Creates a new norms:geltungszeiten element and appends it to the custom mods metadata element.
   * @param customModsMetadata the node under which a new {@link Geltungszeiten} should be created.
   * @return the newly created {@link Geltungszeiten}
   */
  public static Geltungszeiten createAndAppend(CustomModsMetadata customModsMetadata) {
    return new Geltungszeiten(
      NodeCreator.createElement(NAMESPACE, TAG_NAME, customModsMetadata.getElement()),
      customModsMetadata::isZeitgrenzeInUse
    );
  }

  public void add(Zeitgrenze.Art art, LocalDate date) {
    Zeitgrenze.createAndAppend(this, art, date);
  }

  /**
   * Is the given {@link Zeitgrenze} currently in use?
   * @param zeitgrenze the {@link Zeitgrenze} to check
   * @return true if it is in use, false otherwise
   */
  public boolean isZeitgrenzeInUse(Zeitgrenze zeitgrenze) {
    return zeitgrenzeInUseProvider.apply(zeitgrenze);
  }

  @NotNull
  @Override
  public Stream<Zeitgrenze> stream() {
    return NodeParser
      .getElementsFromExpression("./%s".formatted(Zeitgrenze.TAG_NAME), getElement())
      .stream()
      .map(zeitgrenzenElement -> new Zeitgrenze(zeitgrenzenElement, this::isZeitgrenzeInUse));
  }

  @NotNull
  @Override
  public Iterator<Zeitgrenze> iterator() {
    return stream().iterator();
  }

  @Override
  public boolean remove(Object o) {
    if (!(o instanceof Zeitgrenze zeitgrenze)) return false;

    if (zeitgrenze.getElement().getParentNode() != getElement()) return false;

    getElement().removeChild(zeitgrenze.getElement());
    return true;
  }

  @Override
  public int size() {
    return (int) stream().count();
  }
}
