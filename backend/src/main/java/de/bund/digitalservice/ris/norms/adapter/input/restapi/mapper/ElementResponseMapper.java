package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.ElementController;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Map;
import org.w3c.dom.Node;

/** Mapper class for converting between {@link Node} and {@link ElementResponseSchema}. */
public class ElementResponseMapper {
  // Private constructor to hide the implicit public one and prevent instantiation
  private ElementResponseMapper() {}

  // TODO: Is this referring to enums from the controller allowed?
  // Will be moved to service either way I guess
  private static final Map<String, String> staticNodeTitles =
      Map.ofEntries(
          Map.entry(ElementController.ElementType.PREFACE.name(), "Dokumentenkopf"),
          Map.entry(ElementController.ElementType.PREAMBLE.name(), "Eingangsformel"),
          Map.entry(ElementController.ElementType.CONCLUSIONS.name(), "Schlussteil"));

  private static String getNodeType(Node node) {
    return node.getNodeName().replace("akn:", "");
  }

  private static String getNodeTitle(Node node) {
    var nodeTypeName = getNodeType(node).toUpperCase();

    String title;

    if (staticNodeTitles.containsKey(nodeTypeName)) {
      title = staticNodeTitles.get(nodeTypeName);
    } else if (nodeTypeName.equals("ARTICLE")) {
      var num = NodeParser.getValueFromExpression("./num", node).orElse("").strip();
      var heading = NodeParser.getValueFromExpression("./heading", node).orElse("").strip();
      title = (num + " " + heading).strip();
    } else {
      title = "";
    }

    return title;
  }

  /**
   * Creates a {@link ElementResponseSchema} instance from a {@link Node}.
   *
   * @param node The input node to be converted.
   * @return A new {@link ElementResponseSchema} instance mapped from the input.
   */
  public static ElementResponseSchema fromElementNode(final Node node) {
    return ElementResponseSchema.builder()
        .title(getNodeTitle(node))
        .eid(NodeParser.getValueFromExpression("./@eId", node).orElseThrow())
        .type(getNodeType(node))
        .build();
  }
}