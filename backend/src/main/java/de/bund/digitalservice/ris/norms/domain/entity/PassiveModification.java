package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a passive modification. */
@Getter
@SuperBuilder(toBuilder = true)
public class PassiveModification extends Modification {

  /**
   * Constructor for PassiveModification.
   *
   * @param node the Node object to initialize
   */
  public PassiveModification(Node node) {
    super(node);
  }

  /**
   * Returns the source eli as {@link String}.
   *
   * @return The source eli of the passive modification
   */
  public Optional<String> getSourceEli() {
    return this.getSourceHref()
        .map(
            source ->
                Arrays.stream(source.split("/"))
                    .takeWhile(part -> !part.endsWith(".xml"))
                    .collect(Collectors.joining("/")));
  }

  /**
   * Returns the source eid as {@link String}.
   *
   * @return The source eid of the passive modification
   */
  public Optional<String> getSourceEid() {
    return this.getSourceHref()
        .map(source -> Arrays.stream(source.split("/")).toList().getLast().replace(".xml", ""));
  }

  /**
   * Returns the destination eid as {@link String}.
   *
   * @return The destination eid of the passive modification
   */
  public Optional<String> getDestinationEid() {
    return this.getDestinationHref()
        .flatMap(value -> Arrays.stream(value.replaceFirst("^#", "").split("/")).findFirst());
  }
}
