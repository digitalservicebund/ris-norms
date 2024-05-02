package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a passive modification. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class PassiveModification {
  private final Node node;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the passive modification
   */
  public Optional<String> getEid() {
    return NodeParser.getValueFromExpression("./@eId", this.node);
  }

  /**
   * Returns the type of modification as {@link String}.
   *
   * @return The type of the passive modification
   */
  public Optional<String> getType() {
    return NodeParser.getValueFromExpression("./@type", this.node);
  }

  /**
   * Returns the source href as {@link String}.
   *
   * @return The source href of the passive modification
   */
  public Optional<String> getSourceHref() {
    return NodeParser.getValueFromExpression("./source/@href", this.node);
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
   * Returns eid of the force as {@link String}. This eid identifies the temporal group of this
   * passive modification.
   *
   * @return The force eid of the passive modification
   */
  public Optional<String> getForcePeriodEid() {
    return NodeParser.getValueFromExpression("./force/@period", this.node)
        .map(value -> value.replaceFirst("^#", ""));
  }
}
