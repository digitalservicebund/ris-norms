package de.bund.digitalservice.ris.norms.domain.entity;

import org.w3c.dom.Node;

/** Class representing the akn:akomaNtoso */
public class XsdLocation {
  private final Node node;

  public XsdLocation(Node node) {
    this.node = node;
  }

  public String getAknNameSpace() {
    return node.getAttributes().getNamedItem("xmlns:akn").getNodeValue();
  }

  public void setAknNameSpace(String aknNameSpace) {
    node.getAttributes().getNamedItem("xmlns:akn").setNodeValue(aknNameSpace);
  }

  public String getXsiSchemaLocation() {
    return node.getAttributes().getNamedItem("xsi:schemaLocation").getNodeValue();
  }

  public void setXsiSchemaLocation(String xsiSchemaLocation) {
    node.getAttributes().getNamedItem("xsi:schemaLocation").setNodeValue(xsiSchemaLocation);
  }
}
