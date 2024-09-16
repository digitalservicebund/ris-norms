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
  public static final String META_PROPRIETARY_SECTION = "//meta/proprietary";
  public static final String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT =
    "attributsemantik-noch-undefiniert";
  public static final String SOURCE = "source";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";

  @Override
  public Norm convert(Query query) {
    if (query.norm().isAct()) return query.norm();
    final Document document = query.norm().getDocument();

    updateXsdLocation(document);
    updateBillToAct(document);
    rewriteFbrWork(document);
    rewriteFbrExpression(document);
    rewriteFbrManifestation(document);
    addNecessaryMetaData(document);
    addTemporalInformation(document);

    return new Norm(document);
  }

  private void updateXsdLocation(Document document) {
    final Element akomaNtoso = (Element) document.getElementsByTagName("akn:akomaNtoso").item(0);
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

  private void updateBillToAct(Document document) {
    final Element bill = (Element) document.getElementsByTagName("akn:bill").item(0);
    final Node parentNode = bill.getParentNode();
    final Node newChildFragment = parentNode.getOwnerDocument().createDocumentFragment();
    final Element newElement = parentNode.getOwnerDocument().createElement("akn:act");
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

  private void rewriteFbrWork(Document document) {
    // (3) Rewrite FRBRWork
    final Element fRBRthis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRthis",
      document
    );
    final Element fRBRuri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRuri",
      document
    );
    final Element fRBRdate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRdate",
      document
    );
    final Element fRBRname = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRname",
      document
    );
    final Element fRBRnumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRWork/FRBRnumber",
      document
    );

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate
      .parse(fRBRdate.getAttribute("date"), formatter)
      .plusDays(1);

    final String num = fRBRnumber.getAttribute(VALUE);

    fRBRthis.setAttribute(
      VALUE,
      ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num + "/regelungstext-1"
    );
    fRBRuri.setAttribute(VALUE, ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num);
    fRBRdate.setAttribute("name", VERKUENDUNGSFASSUNG);
    fRBRdate.setAttribute("date", verkuendungsDate.format(formatter));
    fRBRname.setAttribute(VALUE, "bgbl-1");
  }

  private void rewriteFbrExpression(Document document) {
    final Element fRBRExpression = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression",
      document
    );
    final Element fRBRthis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRthis",
      document
    );
    final Element fRBRuri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRuri",
      document
    );
    final Element fRBRdate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRdate",
      document
    );
    final Element fRBRVersionNumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRversionNumber",
      document
    );
    final Element fRBRlanguage = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRlanguage",
      document
    );
    final Element fRBRNumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRnumber",
      document
    );

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate
      .parse(fRBRdate.getAttribute("date"), formatter)
      .plusDays(1);
    final String num = fRBRNumber.getAttribute(VALUE);
    final String versionNumber = fRBRVersionNumber.getAttribute(VALUE);

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
    fRBRdate.setAttribute("date", verkuendungsDate.format(formatter));

    final Element fRBROldCurrentVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRalias[@name=\"aktuelle-version-id\"]",
      document
    );
    final Element fRBROldNextVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
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

    final Element fRBRNewCurrentVersion = fRBROldCurrentVersion
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewCurrentVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-2");
    fRBRNewCurrentVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewCurrentVersion.setAttribute("name", "aktuelle-version-id");
    fRBRNewCurrentVersion.setAttribute(VALUE, fRBROldCurrentVersion.getAttribute(VALUE));
    newChildFragment.appendChild(fRBRNewCurrentVersion);

    final Element fRBRNewFutureVersion = fRBROldCurrentVersion
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

  private void rewriteFbrManifestation(Document document) {
    final Element fRBRManifestationThis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRManifestation/FRBRthis",
      document
    );
    final Element fRBRManifestationUri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRManifestation/FRBRuri",
      document
    );

    final Element fRBRExpressionThis = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRthis",
      document
    );

    final Element fRBRManifestationDate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRManifestation/FRBRdate",
      document
    );

    final Element fRBRExpressionDate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRdate",
      document
    );
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate.parse(
      fRBRExpressionDate.getAttribute("date"),
      formatter
    );

    fRBRManifestationThis.setAttribute(VALUE, fRBRExpressionThis.getAttribute(VALUE) + ".xml");
    fRBRManifestationUri.setAttribute(VALUE, fRBRExpressionThis.getAttribute(VALUE) + ".xml");
    fRBRManifestationDate.setAttribute("date", verkuendungsDate.format(formatter));
  }

  private void addNecessaryMetaData(Document document) {
    final Element date = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//FRBRExpression/FRBRdate",
      document
    );

    if (NodeParser.getNodeFromExpression(META_PROPRIETARY_SECTION, document).isEmpty()) {
      final Element parent = (Element) NodeParser.getMandatoryNodeFromExpression(
        "//act/meta",
        document
      );
      final Element proprietary = document.createElement("akn:proprietary");
      proprietary.setAttribute("eId", "meta-1_proprietary-1");
      proprietary.setAttribute("GUID", UUID.randomUUID().toString());
      proprietary.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

      parent.appendChild(proprietary);
    }

    if (
      NodeParser
        .getNodeFromExpression("//meta/proprietary/legalDocML.de_metadaten", document)
        .isEmpty()
    ) {
      final Element legalDocMlDeMetadaten = document.createElement("meta:legalDocML.de_metadaten");
      legalDocMlDeMetadaten.setAttribute("xmlns:meta", "http://Metadaten.LegalDocML.de/1.6/");
      final Node proprietary = NodeParser.getMandatoryNodeFromExpression(
        META_PROPRIETARY_SECTION,
        document
      );
      proprietary.appendChild(legalDocMlDeMetadaten);
    }

    final Element dsCustomMeta = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//meta/proprietary/legalDocML.de_metadaten",
      document
    );

    if (NodeParser.getNodeFromExpression("./fassung", dsCustomMeta).isEmpty()) {
      final Element fassung = document.createElement("meta:fassung");
      fassung.setTextContent(VERKUENDUNGSFASSUNG);
      dsCustomMeta.appendChild(fassung);
    } else {
      final Element fassung = (Element) NodeParser.getMandatoryNodeFromExpression(
        "./fassung",
        dsCustomMeta
      );
      fassung.setTextContent(VERKUENDUNGSFASSUNG);
    }

    if (NodeParser.getNodeFromExpression("./fna", dsCustomMeta).isEmpty()) {
      final Element fna = document.createElement("meta:fna");
      fna.appendChild(document.createTextNode("nicht-vorhanden"));
      dsCustomMeta.appendChild(fna);
    }
    if (NodeParser.getNodeFromExpression("./gesta", dsCustomMeta).isEmpty()) {
      final Element gesta = document.createElement("meta:gesta");
      gesta.appendChild(document.createTextNode("nicht-vorhanden"));
      dsCustomMeta.appendChild(gesta);
    }
    if (NodeParser.getNodeFromExpression("./federfuehrung", dsCustomMeta).isEmpty()) {
      final Element federfuehrung = document.createElement("meta:federfuehrung");
      final Element federfuehrend = document.createElement("meta:federfuehrend");
      federfuehrend.setAttribute("ab", date.getAttribute("date"));
      federfuehrend.setAttribute("bis", "unbestimmt");
      federfuehrend.appendChild(document.createTextNode("Bundesministerium der Justiz"));
      federfuehrung.appendChild(federfuehrend);
      dsCustomMeta.appendChild(federfuehrung);
    }
  }

  private void addTemporalInformation(Document document) {
    if (
      NodeParser.getNodeFromExpression("//meta/lifecycle", document).isEmpty() &&
      NodeParser.getNodeFromExpression("//meta/temporalData", document).isEmpty()
    ) {
      final var parentNode = (Element) NodeParser.getMandatoryNodeFromExpression(
        "//meta",
        document
      );
      final Node lifecycleFragment = parentNode.getOwnerDocument().createDocumentFragment();
      final Element verkuendungsDate = (Element) NodeParser.getMandatoryNodeFromExpression(
        "//FRBRExpression/FRBRdate",
        document
      );
      final String verkuendungsDateString = verkuendungsDate.getAttribute("date");

      final Element lifecycle = parentNode.getOwnerDocument().createElement("akn:lifecycle");
      lifecycle.setAttribute("eId", "meta-1_lebzykl-1");
      lifecycle.setAttribute("GUID", UUID.randomUUID().toString());
      lifecycle.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

      final Element eventRefAusfertigung = parentNode
        .getOwnerDocument()
        .createElement("akn:eventRef");
      final String eIdAusfertigung = "meta-1_lebzykl-1_ereignis-1";
      eventRefAusfertigung.setAttribute("eId", eIdAusfertigung);
      eventRefAusfertigung.setAttribute("GUID", UUID.randomUUID().toString());
      eventRefAusfertigung.setAttribute("date", verkuendungsDateString);
      eventRefAusfertigung.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      eventRefAusfertigung.setAttribute("refersTo", "ausfertigung");
      eventRefAusfertigung.setAttribute("type", "generation");
      lifecycle.appendChild(eventRefAusfertigung);
      lifecycleFragment.appendChild(lifecycle);

      final Element proprietarySection = (Element) NodeParser.getMandatoryNodeFromExpression(
        META_PROPRIETARY_SECTION,
        document
      );
      parentNode.insertBefore(lifecycleFragment, proprietarySection);

      final Node temporalDataFragment = parentNode.getOwnerDocument().createDocumentFragment();
      final Element temporalData = document.createElement("akn:temporalData");
      temporalData.setAttribute("eId", "meta-1_geltzeiten-1");
      temporalData.setAttribute("GUID", UUID.randomUUID().toString());
      temporalData.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      temporalDataFragment.appendChild(temporalData);

      parentNode.insertBefore(temporalDataFragment, proprietarySection);
    }
  }
}
