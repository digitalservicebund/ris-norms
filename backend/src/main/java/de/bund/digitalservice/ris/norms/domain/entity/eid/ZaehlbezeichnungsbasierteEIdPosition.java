package de.bund.digitalservice.ris.norms.domain.entity.eid;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.web.util.UriUtils;
import org.w3c.dom.Node;

/**
 * The position information of an EId calculated using the Zählbezeichnung.
 * @param value the position value without the prefix
 */
public record ZaehlbezeichnungsbasierteEIdPosition(String value) implements EIdPosition {
  static final String PREFIX = "z";

  static ZaehlbezeichnungsbasierteEIdPosition findEIdPosition(Node node) {
    Optional<String> position = NodeParser.getValueFromExpression(
      "normalize-space(lower-case(./num/text()))",
      node
    )
      .map(a -> a.replaceAll("(§ )|(art\\. )|(art )|(artikel )", ""))
      .map(a -> a.replaceAll("(\\()(\\d+[a-z]*)(\\))", "$2"))
      .map(a -> a.replaceAll("[-_.]", "~"))
      .map(a -> UriUtils.encode(a, StandardCharsets.UTF_8).toLowerCase());

    return new ZaehlbezeichnungsbasierteEIdPosition(position.orElse(""));
  }

  @Override
  public String toString() {
    return PREFIX + value;
  }

  /**
   * Create a new {@link ZaehlbezeichnungsbasierteEIdPosition} based on a string representation of the position.
   * @param string the position information starting with z
   * @return the {@link ZaehlbezeichnungsbasierteEIdPosition} for the string
   */
  public static ZaehlbezeichnungsbasierteEIdPosition fromString(String string) {
    if (!string.startsWith(PREFIX)) {
      throw new IllegalArgumentException("OrdinalEIdPosition must start with '" + PREFIX + "'");
    }

    return new ZaehlbezeichnungsbasierteEIdPosition(string.substring(1));
  }
}
