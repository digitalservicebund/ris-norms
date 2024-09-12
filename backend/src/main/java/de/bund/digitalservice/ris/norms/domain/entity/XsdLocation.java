package de.bund.digitalservice.ris.norms.domain.entity;

import org.w3c.dom.Node;

/** Class representing the akn:akomaNtoso */
public class XsdLocation {
  private final Node node;

  /**
   * Returns a {@link XsdLocation} instance.
   *
   * @return the akn:akomaNtoso node as {@link XsdLocation}
   */
  public XsdLocation(Node node) {
    this.node = node;
  }

  public String getAknNameSpace() {
    return node.getAttributes().getNamedItem("xmlns:akn").getNodeValue();
  }
}
