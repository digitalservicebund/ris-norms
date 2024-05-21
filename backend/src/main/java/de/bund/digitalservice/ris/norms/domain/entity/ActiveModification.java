package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing an active modification. */
@Getter
@SuperBuilder(toBuilder = true)
public class ActiveModification extends Modification {

  /**
   * Constructor for ActiveModification.
   *
   * @param node the Node object to initialize
   */
  public ActiveModification(Node node) {
    super(node);
  }

  /**
   * Returns the source eli as {@link String}.
   *
   * @return The source eli of the active modification
   */
  public Optional<String> getDestinationEli() {
    return this.getDestinationHref()
        .map(
            source ->
                Arrays.stream(source.split("/"))
                    .takeWhile(part -> !part.endsWith(".xml"))
                    .collect(Collectors.joining("/")));
  }

  /**
   * Returns the destination eid as {@link String}.
   *
   * @return The destination eid of the active modification
   */
  public Optional<String> getDestinationEid() {
    return this.getDestinationHref()
        .map(source -> Arrays.stream(source.split("/")).toList().getLast().replace(".xml", ""));
  }

  /**
   * Returns the source eid as {@link String}.
   *
   * @return The source eid of the active modification
   */
  public Optional<String> getSourceEid() {
    return this.getSourceHref().map(value -> value.replaceFirst("^#", ""));
  }
}