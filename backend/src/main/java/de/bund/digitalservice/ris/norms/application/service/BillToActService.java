package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Service for the reference pattern recognition. */
@Service
@Slf4j
public class BillToActService implements BillToActUseCase {

  private static final String ROOT_DIR = "../../..";
  private static final String SCHEMA = "Grammatiken";

  Document document;

  @Override
  public Norm convert(Query query) {
    if (query.norm().isAct()) return query.norm();
    this.document = query.norm().getDocument();
    updateXsdLocation(document);
    updateBillToAct();

    return new Norm(document);
  }

  private void updateXsdLocation(Document document) {
    Element akomaNtoso = (Element) document.getElementsByTagName("akn:akomaNtoso").item(0);
    akomaNtoso.setAttribute("xmlns:akn", "http://Inhaltsdaten.LegalDocML.de/1.6/");
    akomaNtoso.setAttribute(
        "xsi:schemaLocation",
        "http://Metadaten.LegalDocML.de/1.6/ "
            + ROOT_DIR
            + "/"
            + SCHEMA
            + "/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ "
            + ROOT_DIR
            + "/"
            + SCHEMA
            + "/legalDocML.de-regelungstextverkuendungsfassung.xsd");
  }

  private void updateBillToAct() {
    Element bill = (Element) document.getElementsByTagName("akn:bill").item(0);
    final var parentNode = bill.getParentNode();
    final Node newChildFragment = parentNode.getOwnerDocument().createDocumentFragment();
    var newElement = parentNode.getOwnerDocument().createElement("akn:act");
    newElement.setAttribute("name", "regelungstext");
    NodeParser.nodeListToList(bill.getChildNodes())
        .forEach(
            child -> {
              final Node importedChild = parentNode.getOwnerDocument().importNode(child, true);
              newElement.appendChild(importedChild);
            });
    newChildFragment.appendChild(newElement);
    parentNode.insertBefore(newChildFragment, bill);
    parentNode.removeChild(bill);
  }
}
