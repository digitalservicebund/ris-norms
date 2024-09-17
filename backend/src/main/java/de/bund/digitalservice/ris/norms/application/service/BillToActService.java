package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Temporary service to covert a bill to an act. This is needed for the "Mini-Kreislauf" where we
 * receive bills from E-Gesetzgebung. Later we will get acts from E-Verkündung.
 */
@Service
@Slf4j
public class BillToActService {

  private static final String ROOT_DIR = "../../..";
  private static final String SCHEMA = "Grammatiken";
  private static final String ELI_BUND_BGBL_1 = "eli/bund/bgbl-1/";
  private static final String VALUE = "value";
  private static final String AKN_FRBRALIAS = "akn:FRBRalias";
  private static final String EVENT_REF_NODE = "akn:eventRef";
  private static final String VERKUENDUNGSFASSUNG = "verkuendungsfassung";
  private static final String META_PROPRIETARY_SECTION = "//meta/proprietary";
  private static final String SOURCE = "source";
  private static final String REFERSTO = "refersTo";
  private static final String YYYY_MM_DD = "yyyy-MM-dd";
  private static final String FRBREXPRESSION_FRBRDATE = "//identification/FRBRExpression/FRBRdate";
  private static final String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT =
    "attributsemantik-noch-undefiniert";
  private static final String AKN_P = "akn:p";

  /**
   * Coverts a bill to an act. This is needed for the "Mini-Kreislauf" where we receive bills from
   * E-Gesetzgebung. Later we will get acts from E-Verkündung.
   *
   * @param xmlString a bill xml as a {@link String}
   * @return The updated act as a {@link String}
   */
  public String convert(String xmlString) {
    Document document = XmlMapper.toDocument(xmlString);
    if (NodeParser.getNodeFromExpression("//*/act", document).isPresent()) return xmlString;

    updateXsdLocation(document);
    updateBillToAct(document);
    rewriteFbrWork(document);
    rewriteFbrExpression(document);
    rewriteFbrManifestation(document);
    addNecessaryMetaData(document);
    addTemporalInformation(document);
    addPeriodToArticle(document);
    addFormulaAndSignature(document);
    addMandatoryGuids(document);

    return XmlMapper.toString(document);
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
      "//identification/FRBRWork/FRBRthis",
      document
    );
    final Element fRBRuri = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRuri",
      document
    );
    final Element fRBRdate = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRdate",
      document
    );
    final Element fRBRname = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRname",
      document
    );
    final Element fRBRnumber = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRnumber",
      document
    );

    final Element fRBRauthor = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRWork/FRBRauthor",
      document
    );

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate.parse(fRBRdate.getAttribute("date"), formatter);

    final String num = fRBRnumber.getAttribute(VALUE);

    fRBRthis.setAttribute(
      VALUE,
      ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num + "/regelungstext-1"
    );
    fRBRuri.setAttribute(VALUE, ELI_BUND_BGBL_1 + verkuendungsDate.getYear() + "/" + num);
    fRBRdate.setAttribute("name", VERKUENDUNGSFASSUNG);
    fRBRdate.setAttribute("date", verkuendungsDate.format(formatter));
    fRBRname.setAttribute(VALUE, "bgbl-1");
    fRBRauthor.setAttribute("href", "recht.bund.de/institution/bundespraesident");
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
      FRBREXPRESSION_FRBRDATE,
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
    final LocalDate verkuendungsDate = LocalDate.parse(fRBRdate.getAttribute("date"), formatter);
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

    String predessorUUID;
    String currentUUID;
    final Element fRBRAuthor = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//identification/FRBRExpression/FRBRauthor",
      document
    );

    if (
      NodeParser
        .getNodeFromExpression(
          "//identification/FRBRExpression/FRBRalias[@name=\"aktuelle-version-id\"]",
          document
        )
        .isPresent() &&
      NodeParser
        .getNodeFromExpression(
          "//identification/FRBRExpression/FRBRalias[@name=\"nachfolgende-version-id\"]",
          document
        )
        .isPresent()
    ) {
      final Element fRBROldCurrentVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
        "//identification/FRBRExpression/FRBRalias[@name=\"aktuelle-version-id\"]",
        document
      );
      final Element fRBROldNextVersion = (Element) NodeParser.getMandatoryNodeFromExpression(
        "//identification/FRBRExpression/FRBRalias[@name=\"nachfolgende-version-id\"]",
        document
      );

      predessorUUID = fRBROldCurrentVersion.getAttribute(VALUE);
      currentUUID = fRBROldNextVersion.getAttribute(VALUE);
      fRBRExpression.removeChild(fRBROldCurrentVersion);
      fRBRExpression.removeChild(fRBROldNextVersion);
    } else {
      predessorUUID = UUID.randomUUID().toString();
      currentUUID = UUID.randomUUID().toString();
    }

    final Node newChildFragment = fRBRExpression.getOwnerDocument().createDocumentFragment();
    Element fRBRNewPredecessorVersion = fRBRExpression
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewPredecessorVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-1");
    fRBRNewPredecessorVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewPredecessorVersion.setAttribute("name", "vorherige-version-id");
    fRBRNewPredecessorVersion.setAttribute(VALUE, predessorUUID);
    newChildFragment.appendChild(fRBRNewPredecessorVersion);

    final Element fRBRNewCurrentVersion = fRBRExpression
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewCurrentVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-2");
    fRBRNewCurrentVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewCurrentVersion.setAttribute("name", "aktuelle-version-id");
    fRBRNewCurrentVersion.setAttribute(VALUE, currentUUID);
    newChildFragment.appendChild(fRBRNewCurrentVersion);

    final Element fRBRNewFutureVersion = fRBRExpression
      .getOwnerDocument()
      .createElement(AKN_FRBRALIAS);
    fRBRNewFutureVersion.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-3");
    fRBRNewFutureVersion.setAttribute("GUID", UUID.randomUUID().toString());
    fRBRNewFutureVersion.setAttribute("name", "nachfolgende-version-id");
    fRBRNewFutureVersion.setAttribute(VALUE, UUID.randomUUID().toString());
    newChildFragment.appendChild(fRBRNewFutureVersion);

    fRBRExpression.insertBefore(newChildFragment, fRBRAuthor);

    fRBRAuthor.setAttribute("href", "recht.bund.de/institution/bundespraesident");
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
      FRBREXPRESSION_FRBRDATE,
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
      FRBREXPRESSION_FRBRDATE,
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
        FRBREXPRESSION_FRBRDATE,
        document
      );
      final String verkuendungsDateString = verkuendungsDate.getAttribute("date");

      final Element lifecycle = parentNode.getOwnerDocument().createElement("akn:lifecycle");
      lifecycle.setAttribute("eId", "meta-1_lebzykl-1");
      lifecycle.setAttribute("GUID", UUID.randomUUID().toString());
      lifecycle.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

      final Element eventRefAusfertigung = parentNode
        .getOwnerDocument()
        .createElement(EVENT_REF_NODE);
      final String eIdAusfertigung = "meta-1_lebzykl-1_ereignis-1";
      eventRefAusfertigung.setAttribute("eId", eIdAusfertigung);
      eventRefAusfertigung.setAttribute("GUID", UUID.randomUUID().toString());
      eventRefAusfertigung.setAttribute("date", verkuendungsDateString);
      eventRefAusfertigung.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      eventRefAusfertigung.setAttribute(REFERSTO, "ausfertigung");
      eventRefAusfertigung.setAttribute("type", "generation");
      lifecycle.appendChild(eventRefAusfertigung);

      final Element eventRefInkraftUnbekannt = parentNode
        .getOwnerDocument()
        .createElement(EVENT_REF_NODE);
      final String eIdInkraft = "meta-1_lebzykl-1_ereignis-2";
      eventRefInkraftUnbekannt.setAttribute("eId", eIdInkraft);
      eventRefInkraftUnbekannt.setAttribute("GUID", UUID.randomUUID().toString());
      eventRefInkraftUnbekannt.setAttribute("date", "0001-01-01");
      eventRefInkraftUnbekannt.setAttribute(SOURCE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      eventRefInkraftUnbekannt.setAttribute(REFERSTO, "inkrafttreten-mit-noch-unbekanntem-datum");
      eventRefInkraftUnbekannt.setAttribute("type", "generation");
      lifecycle.appendChild(eventRefInkraftUnbekannt);
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

      final Element temporalGroup = parentNode
        .getOwnerDocument()
        .createElement("akn:temporalGroup");
      temporalGroup.setAttribute("eId", "meta-1_geltzeiten-1_geltungszeitgr-1");
      temporalGroup.setAttribute("GUID", UUID.randomUUID().toString());
      final Element timeInterval = parentNode.getOwnerDocument().createElement("akn:timeInterval");
      timeInterval.setAttribute("eId", "meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
      timeInterval.setAttribute("GUID", UUID.randomUUID().toString());
      timeInterval.setAttribute("start", "#meta-1_lebzykl-1_ereignis-2");
      timeInterval.setAttribute(REFERSTO, "geltungszeit");
      temporalGroup.appendChild(timeInterval);
      temporalData.appendChild(temporalGroup);
      temporalDataFragment.appendChild(temporalData);

      parentNode.insertBefore(temporalDataFragment, proprietarySection);
    }
  }

  private void addPeriodToArticle(Document document) {
    NodeParser
      .getNodesFromExpression("//body//article[not(ancestor-or-self::mod)]", document)
      .stream()
      .filter(article -> {
        final Optional<String> optionalRefersTo = NodeParser.getValueFromExpression(
          "./@refersTo",
          article
        );
        return optionalRefersTo.isEmpty() || !optionalRefersTo.get().equals("geltungszeitregel");
      })
      .forEach(filtered ->
        ((Element) filtered).setAttribute("period", "#meta-1_geltzeiten-1_geltungszeitgr-1")
      );
  }

  private void addFormulaAndSignature(Document document) {
    final Element conclusions = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//conclusions",
      document
    );

    conclusions.setAttribute("eId", "schluss-1");
    conclusions.setAttribute("GUID", UUID.randomUUID().toString());

    final Element formula = conclusions.getOwnerDocument().createElement("akn:formula");
    formula.setAttribute("eId", "schluss-1_formel-1");
    formula.setAttribute("GUID", UUID.randomUUID().toString());
    formula.setAttribute(REFERSTO, "schlussformel");
    formula.setAttribute("name", ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
    conclusions.appendChild(formula);

    final Element formulaParagraph = formula.getOwnerDocument().createElement(AKN_P);
    formulaParagraph.setAttribute("eId", "schluss-1_formel-1_text-1");
    formulaParagraph.setAttribute("GUID", UUID.randomUUID().toString());
    formulaParagraph.setTextContent(
      "Das vorstehende Gesetz wird hiermit ausgefertigt. Es ist im Bundesgesetzblatt zu verkünden."
    );
    formula.appendChild(formulaParagraph);

    final Element blockContainer = conclusions
      .getOwnerDocument()
      .createElement("akn:blockContainer");
    blockContainer.setAttribute("eId", "schluss-1_blockcontainer-1");
    blockContainer.setAttribute("GUID", UUID.randomUUID().toString());
    conclusions.appendChild(blockContainer);

    final Element blockContainerParagraph = blockContainer.getOwnerDocument().createElement(AKN_P);
    blockContainerParagraph.setAttribute("eId", "schluss-1_blockcontainer-1_text-1");
    blockContainerParagraph.setAttribute("GUID", UUID.randomUUID().toString());
    blockContainer.appendChild(blockContainerParagraph);

    final Element blockContainerParagraphLocation = blockContainerParagraph
      .getOwnerDocument()
      .createElement("akn:location");
    blockContainerParagraphLocation.setAttribute("eId", "schluss-1_blockcontainer-1_text-1_ort-1");
    blockContainerParagraphLocation.setAttribute("GUID", UUID.randomUUID().toString());
    blockContainerParagraphLocation.setAttribute(REFERSTO, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
    blockContainerParagraph.appendChild(blockContainerParagraphLocation);

    Element ausfertigungsDateNode = (Element) NodeParser.getMandatoryNodeFromExpression(
      "//meta/lifecycle/eventRef[@refersTo=\"ausfertigung\"]",
      document
    );
    final Element blockContainerParagraphDate = blockContainerParagraph
      .getOwnerDocument()
      .createElement("akn:date");
    blockContainerParagraphDate.setAttribute("eId", "schluss-1_blockcontainer-1_text-1_datum-1");
    blockContainerParagraphDate.setAttribute("GUID", UUID.randomUUID().toString());
    blockContainerParagraphDate.setAttribute("date", ausfertigungsDateNode.getAttribute("date"));
    blockContainerParagraphDate.setAttribute(REFERSTO, "ausfertigung-datum");
    blockContainerParagraph.appendChild(blockContainerParagraphDate);

    final Element blockContainerSignatur = blockContainer
      .getOwnerDocument()
      .createElement("akn:signature");
    blockContainerSignatur.setAttribute("eId", "schluss-1_blockcontainer-1_signatur-1");
    blockContainerSignatur.setAttribute("GUID", UUID.randomUUID().toString());
    blockContainer.appendChild(blockContainerSignatur);

    final Element blockContainerSignaturPerson = blockContainerSignatur
      .getOwnerDocument()
      .createElement("akn:person");
    blockContainerSignaturPerson.setAttribute(
      "eId",
      "schluss-1_blockcontainer-1_signatur-1_person-1"
    );
    blockContainerSignaturPerson.setAttribute("GUID", UUID.randomUUID().toString());
    blockContainerSignaturPerson.setAttribute(REFERSTO, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
    blockContainerSignatur.appendChild(blockContainerSignaturPerson);
  }

  private void addMandatoryGuids(final Node node) {
    List<Node> nodesToUpdate = new ArrayList<>();
    nodesToUpdate.add(node);

    while (!nodesToUpdate.isEmpty()) {
      var currentNode = nodesToUpdate.removeFirst();

      if (currentNode instanceof Element currentElement && shouldHaveGuid(currentElement)) {
        currentElement.setAttribute("GUID", UUID.randomUUID().toString());
      }

      nodesToUpdate.addAll(NodeParser.nodeListToList(currentNode.getChildNodes()));
    }
  }

  private static boolean shouldHaveGuid(Element currentElement) {
    String[] guidElements = {
      "akn:activeModifications",
      "akn:affectedDocument",
      "akn:analysis",
      "akn:article",
      "akn:block",
      "akn:blockContainer",
      "akn:body",
      "akn:book",
      "akn:chapter",
      "akn:citation",
      "akn:citations",
      "akn:componentRef",
      "akn:conclusions",
      "akn:content",
      "akn:date",
      "akn:destination",
      "akn:docTitle",
      "akn:docProponent",
      "akn:docStage",
      "akn:documentRef",
      EVENT_REF_NODE,
      "akn:force",
      "akn:foreign",
      "akn:formula",
      AKN_FRBRALIAS,
      "akn:FRBRauthor",
      "akn:FRBRcountry",
      "akn:FRBRdate",
      "akn:FRBRExpression",
      "akn:FRBRformat",
      "akn:FRBRlanguage",
      "akn:FRBRManifestation",
      "akn:FRBRname",
      "akn:FRBRnumber",
      "akn:FRBRsubtype",
      "akn:FRBRthis",
      "akn:FRBRuri",
      "akn:FRBRversionNumber",
      "akn:FRBRWork",
      "akn:heading",
      "akn:identification",
      "akn:inline",
      "akn:intro",
      "akn:li",
      "akn:lifecycle",
      "akn:list",
      "akn:location",
      "akn:longTitle",
      "akn:marker",
      "akn:meta",
      "akn:mod",
      "akn:num",
      "akn:ol",
      "akn:organization",
      AKN_P,
      "akn:paragraph",
      "akn:part",
      "akn:person",
      "akn:point",
      "akn:preamble",
      "akn:preface",
      "akn:proprietary",
      "akn:quotedStructure",
      "akn:quotedText",
      "akn:recital",
      "akn:recitals",
      "akn:ref",
      "akn:role",
      "akn:section",
      "akn:shortTitle",
      "akn:signature",
      "akn:source",
      "akn:span",
      "akn:subchapter",
      "akn:subtitle",
      "akn:table",
      "akn:td",
      "akn:temporalData",
      "akn:temporalGroup",
      "akn:textualMod",
      "akn:title",
      "akn:th",
      "akn:timeInterval",
      "akn:toc",
      "akn:tocItem",
      "akn:tr",
      "akn:ul",
      "akn:wrapUp",
    };

    return Arrays
      .stream(guidElements)
      .anyMatch(element -> element.equals(currentElement.getNodeName()));
  }
}
