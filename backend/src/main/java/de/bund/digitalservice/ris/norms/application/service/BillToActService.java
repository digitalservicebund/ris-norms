package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
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
  private static final String ELI_BUND_BGBL_1 = "eli/bund/bgbl-1/";
  public static final String VALUE = "value";
  public static final String AKN_FRBRALIAS = "akn:FRBRalias";
  public static final String VERKUENDUNGSFASSUNG = "verkuendungsfassung";

  private Document document;

  @Override
  public Norm convert(Query query) {
    if (query.norm().isAct()) return query.norm();
    this.document = query.norm().getDocument();
    updateXsdLocation(document);
    updateBillToAct();
    rewriteFbrWork();
    rewriteFbrExpression();
    rewriteFbrManifestation();
    addNecessaryMetaData();

    return new Norm(document);
  }

  private void updateXsdLocation(Document document) {
    Element akomaNtoso = (Element) document.getElementsByTagName("akn:akomaNtoso").item(0);
    akomaNtoso.setAttribute("xmlns:akn", "http://Inhaltsdaten.LegalDocML.de/1.6/");
    akomaNtoso.setAttribute(
      "xsi:schemaLocation",
      "http://Metadaten.LegalDocML.de/1.6/ " +
      ROOT_DIR +
      "/" +
      SCHEMA +
      "/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ " +
      ROOT_DIR +
      "/" +
      SCHEMA +
      "/legalDocML.de-regelungstextverkuendungsfassung.xsd"
    );
  }

  private void updateBillToAct() {
    Element bill = (Element) document.getElementsByTagName("akn:bill").item(0);
    final var parentNode = bill.getParentNode();
    final Node newChildFragment = parentNode.getOwnerDocument().createDocumentFragment();
    var newElement = parentNode.getOwnerDocument().createElement("akn:act");
    newElement.setAttribute("name", "regelungstext");
    NodeParser
      .nodeListToList(bill.getChildNodes())
      .forEach(child -> {
        final Node importedChild = parentNode.getOwnerDocument().importNode(child, true);
        newElement.appendChild(importedChild);
      });
    newChildFragment.appendChild(newElement);
    parentNode.insertBefore(newChildFragment, bill);
    parentNode.removeChild(bill);
  }

  private void rewriteFbrWork() {
    // (3) Rewrite FRBRWork
    Element fRBRthis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRthis",
      document
    );
    Element fRBRuri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRuri",
      document
    );
    Element fRBRdate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRdate",
      document
    );
    Element fRBRname = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRname",
      document
    );
    Element fRBRnumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRnumber",
      document
    );

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate verkuendungsDate = LocalDate.parse(fRBRdate.getAttribute("date"), formatter);

    String num = fRBRnumber.getAttribute(VALUE);

    fRBRthis.setAttribute(
      VALUE,
      ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num + "/regelungstext-1"
    );
    fRBRuri.setAttribute(VALUE, ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num);
    fRBRdate.setAttribute("name", VERKUENDUNGSFASSUNG);
    fRBRname.setAttribute(VALUE, "bgbl-1");
  }

  private void rewriteFbrExpression() {
    Element fRBRExpression = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression",
      document
    );
    Element fRBRthis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRthis",
      document
    );
    Element fRBRuri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRuri",
      document
    );
    Element fRBRdate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRdate",
      document
    );
    Element fRBRVersionNumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRversionNumber",
      document
    );
    Element fRBRlanguage = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRlanguage",
      document
    );
    Element fRBRNumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRnumber",
      document
    );

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate verkuendungsDate = LocalDate.parse(fRBRdate.getAttribute("date"), formatter);
    String num = fRBRNumber.getAttribute(VALUE);
    String versionNumber = fRBRVersionNumber.getAttribute(VALUE);

    fRBRthis.setAttribute(
      VALUE,
      ELI_BUND_BGBL_1 +
      verkuendungsDate.getYear() +
      "/" +
      num +
      "/" +
      verkuendungsDate +
      "/" +
      versionNumber +
      "/" +
      fRBRlanguage.getAttribute("language") +
      "/regelungstext-1"
    );
    fRBRuri.setAttribute(
      VALUE,
      ELI_BUND_BGBL_1 +
      verkuendungsDate.getYear() +
      "/" +
      num +
      "/" +
      verkuendungsDate +
      "/" +
      versionNumber +
      "/" +
      fRBRlanguage.getAttribute("language")
    );
    fRBRdate.setAttribute("name", "verkuendung");

    Element fRBROldCurrentVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRalias[@name=\"aktuelle-version-id\"]",
      document
    );
    Element fRBROldNextVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRalias[@name=\"nachfolgende-version-id\"]",
      document
    );

    final Node newChildFragment = fRBROldCurrentVersion.getOwnerDocument().createDocumentFragment();
    Element fRBRNewPredecessorVersion = fRBROldCurrentVersion
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewPredecessorVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-1");
    fRBRNewPredecessorVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewPredecessorVersion.setAttribute("name", "vorherige-version-id");
    fRBRNewPredecessorVersion.setAttribute(VALUE, fRBROldCurrentVersion.getAttribute(VALUE));
    newChildFragment.appendChild(fRBRNewPredecessorVersion);

    Element fRBRNewCurrentVersion = fRBROldCurrentVersion
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewCurrentVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-2");
    fRBRNewCurrentVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewCurrentVersion.setAttribute("name", "aktuelle-version-id");
    fRBRNewCurrentVersion.setAttribute(VALUE, fRBROldCurrentVersion.getAttribute(VALUE));
    newChildFragment.appendChild(fRBRNewCurrentVersion);

    Element fRBRNewFutureVersion = fRBROldCurrentVersion
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewFutureVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-3");
    fRBRNewFutureVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewFutureVersion.setAttribute("name", "nachfolgende-version-id");
    fRBRNewFutureVersion.setAttribute(VALUE, UUID.randomUUID().toString());
    newChildFragment.appendChild(fRBRNewFutureVersion);

    fRBRExpression.insertBefore(newChildFragment, fRBROldCurrentVersion);
    fRBRExpression.removeChild(fRBROldCurrentVersion);
    fRBRExpression.removeChild(fRBROldNextVersion);
  }

  private void rewriteFbrManifestation() {
    Element fRBRManifestationThis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRManifestation/FRBRthis",
      document
    );
    Element fRBRManifestationUri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRManifestation/FRBRuri",
      document
    );

    Element fRBRExpressionThis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRthis",
      document
    );

    fRBRManifestationThis.setAttribute(VALUE, fRBRExpressionThis.getAttribute(VALUE) + ".xml");
    fRBRManifestationUri.setAttribute(VALUE, fRBRExpressionThis.getAttribute(VALUE) + ".xml");
  }

  private void addNecessaryMetaData() {
    Element date = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRExpression/FRBRdate",
      document
    );

    if (NodeParser.getNodeFromExpression("//meta/proprietary", document).isEmpty()) {
      Element parent = (Element) NodeParser.getMandatoryNodeFromExpression("//act/meta", document);
      Element proprietary = document.createElement("akn:proprietary");
      proprietary.setAttribute("eId", "meta-1_proprietary-1");
      proprietary.setAttribute("GUID", UUID.randomUUID().toString());
      proprietary.setAttribute("source", "attributsemantik-noch-undefiniert");

      parent.appendChild(proprietary);
    }

    if (
      NodeParser
        .getNodeFromExpression("//meta/proprietary/legalDocML.de_metadaten", document)
        .isEmpty()
    ) {
      Element legalDocMlDeMetadaten = document.createElement("meta:legalDocML.de_metadaten");
      legalDocMlDeMetadaten.setAttribute("xmlns:meta", "http://Metadaten.LegalDocML.de/1.6/");
      Node proprietary = NodeParser.getMandatoryNodeFromExpression("//meta/proprietary", document);
      proprietary.appendChild(legalDocMlDeMetadaten);
    }

    Element dsCustomMeta = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//meta/proprietary/legalDocML.de_metadaten",
      document
    );

    if (NodeParser.getNodeFromExpression("./fassung", dsCustomMeta).isEmpty()) {
      Element fassung = document.createElement("meta:fassung");
      fassung.setTextContent(VERKUENDUNGSFASSUNG);
      dsCustomMeta.appendChild(fassung);
    } else {
      Element fassung = (Element) NodeParser.getMandatoryNodeFromExpression(
        "./fassung",
        dsCustomMeta
      );
      fassung.setTextContent(VERKUENDUNGSFASSUNG);
    }

    if (NodeParser.getNodeFromExpression("./fna", dsCustomMeta).isEmpty()) {
      Element fna = document.createElement("meta:fna");
      fna.appendChild(document.createTextNode("nicht-vorhanden"));
      dsCustomMeta.appendChild(fna);
    }
    if (NodeParser.getNodeFromExpression("./gesta", dsCustomMeta).isEmpty()) {
      Element gesta = document.createElement("meta:gesta");
      gesta.appendChild(document.createTextNode("nicht-vorhanden"));
      dsCustomMeta.appendChild(gesta);
    }
    if (NodeParser.getNodeFromExpression("./federfuehrung", dsCustomMeta).isEmpty()) {
      Element federfuehrung = document.createElement("meta:federfuehrung");
      Element federfuehrend = document.createElement("meta:federfuehrend");
      federfuehrend.setAttribute("ab", date.getAttribute("date"));
      federfuehrend.setAttribute("bis", "unbestimmt");
      federfuehrend.appendChild(document.createTextNode("Bundesministerium der Justiz"));
      federfuehrung.appendChild(federfuehrend);
      dsCustomMeta.appendChild(federfuehrung);
    }
  }
}
