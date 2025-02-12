package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
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
  private static final String EVENT_REF_NODE = "akn:eventRef";
  private static final String VERKUENDUNGSFASSUNG = "verkuendungsfassung";
  private static final String REFERSTO = "refersTo";
  private static final String YYYY_MM_DD = "yyyy-MM-dd";
  private static final String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT =
    "attributsemantik-noch-undefiniert";
  private static final String AKN_P = "akn:p";

  /**
   * Coverts a bill to an act. This is needed for the "Mini-Kreislauf" where we receive bills from
   * E-Gesetzgebung. Later we will get acts from E-Verkündung.
   *
   * @param document a bill xml as a {@link String}
   * @return The updated act as a {@link String}
   */
  public String convert(Document document) {
    if (
      NodeParser.getElementFromExpression("//*/act", document).isPresent()
    ) return XmlMapper.toString(document);

    updateXsdLocation(document);
    updateBillToAct(document);

    var regelungstext = new Regelungstext(document);
    rewriteFbrWork(regelungstext);

    rewriteFbrExpression(regelungstext);
    rewriteFbrManifestation(regelungstext);
    addNecessaryMetaData(regelungstext);
    addTemporalInformation(regelungstext);
    addPeriodToArticle(document);

    addFormulaAndSignature(document);
    addMandatoryGuids(document);
    return XmlMapper.toString(document);
  }

  private void updateXsdLocation(Document document) {
    final Element akomaNtoso = (Element) document.getElementsByTagName("akn:akomaNtoso").item(0);
    akomaNtoso.setAttribute("xmlns:akn", Namespace.INHALTSDATEN.getNamespaceUri());
    akomaNtoso.setAttribute(
      "xsi:schemaLocation",
      "http://Metadaten.LegalDocML.de/1.7.2/ " +
      ROOT_DIR +
      "/" +
      SCHEMA +
      "/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ " +
      ROOT_DIR +
      "/" +
      SCHEMA +
      "/legalDocML.de-regelungstextverkuendungsfassung.xsd"
    );
  }

  private void updateBillToAct(Document document) {
    final Element bill = (Element) document.getElementsByTagName("akn:bill").item(0);
    final Node parentNode = bill.getParentNode();
    final Element act = parentNode.getOwnerDocument().createElement("akn:act");
    act.setAttribute("name", "regelungstext");
    NodeParser.nodeListToList(bill.getChildNodes()).forEach(act::appendChild);
    parentNode.replaceChild(act, bill);
  }

  private void rewriteFbrWork(Regelungstext regelungstext) {
    // (3) Rewrite FRBRWork
    final FRBRWork frbrWork = regelungstext.getMeta().getFRBRWork();

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate.parse(frbrWork.getFBRDate(), formatter);
    final String naturalIdentifier = frbrWork.getFRBRnumber().orElseThrow();
    final DokumentWorkEli workEli = new DokumentWorkEli(
      "bgbl-1",
      String.valueOf(verkuendungsDate.getYear()),
      naturalIdentifier,
      "regelungstext-1"
    );

    frbrWork.setEli(workEli);
    frbrWork.setURI(workEli.toUri());
    frbrWork.setFBRDate(verkuendungsDate.format(formatter), VERKUENDUNGSFASSUNG);
    frbrWork.setFRBRName("bgbl-1");
    frbrWork.setFRBRAuthor("recht.bund.de/institution/bundespraesident");
  }

  private void rewriteFbrExpression(Regelungstext regelungstext) {
    final FRBRExpression fRBRExpression = regelungstext.getMeta().getFRBRExpression();

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate.parse(fRBRExpression.getFBRDate(), formatter);

    final DokumentExpressionEli expressionEli = DokumentExpressionEli.fromWorkEli(
      regelungstext.getWorkEli(),
      verkuendungsDate,
      fRBRExpression.getFRBRVersionNumber().orElseThrow(),
      fRBRExpression.getFRBRlanguage().orElseThrow()
    );

    fRBRExpression.setEli(expressionEli);
    fRBRExpression.setURI(expressionEli.toUri());
    fRBRExpression.setFBRDate(verkuendungsDate.format(formatter), "verkuendung");
    fRBRExpression.setFRBRAuthor("recht.bund.de/institution/bundespraesident");

    fRBRExpression.setFRBRaliasPreviousVersionId(fRBRExpression.getFRBRaliasCurrentVersionId());
    fRBRExpression.setFRBRaliasCurrentVersionId(
      fRBRExpression.getFRBRaliasNextVersionId().orElse(UUID.randomUUID())
    );
    fRBRExpression.setFRBRaliasNextVersionId(UUID.randomUUID());
  }

  private void rewriteFbrManifestation(Regelungstext regelungstext) {
    final FRBRExpression fRBRExpression = regelungstext.getMeta().getFRBRExpression();
    final FRBRManifestation frbrManifestation = regelungstext.getMeta().getFRBRManifestation();

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    final LocalDate verkuendungsDate = LocalDate.parse(fRBRExpression.getFBRDate(), formatter);

    final DokumentManifestationEli manifestationEli = DokumentManifestationEli.fromExpressionEli(
      regelungstext.getExpressionEli(),
      verkuendungsDate,
      "xml"
    );

    frbrManifestation.setEli(manifestationEli);
    frbrManifestation.setURI(manifestationEli.toUri());
    frbrManifestation.setFBRDate(verkuendungsDate.format(formatter), "generierung");
  }

  private void addNecessaryMetaData(Regelungstext regelungstext) {
    Proprietary proprietary = regelungstext.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(Metadata.FASSUNG, VERKUENDUNGSFASSUNG);
    if (proprietary.getMetadataValue(Metadata.FNA).isEmpty()) {
      proprietary.setMetadataValue(Metadata.FNA, "nicht-vorhanden");
    }
    if (proprietary.getMetadataValue(Metadata.GESTA).isEmpty()) {
      proprietary.setMetadataValue(Metadata.GESTA, "nicht-vorhanden");
    }
    if (proprietary.getRessort(LocalDate.MAX).isEmpty()) {
      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
      final LocalDate verkuendungsDate = LocalDate.parse(
        regelungstext.getMeta().getFRBRExpression().getFBRDate(),
        formatter
      );
      proprietary.setRessort("Bundesministerium der Justiz", verkuendungsDate);
    }
  }

  private void addTemporalInformation(Regelungstext regelungstext) {
    try {
      regelungstext.getMeta().getTemporalData();
      regelungstext.getMeta().getLifecycle();
    } catch (MandatoryNodeNotFoundException exception) {
      final Lifecycle lifecycle = regelungstext.getMeta().getOrCreateLifecycle();

      final EventRef ausfertigung = lifecycle.addEventRef();
      ausfertigung.setDate(regelungstext.getMeta().getFRBRExpression().getFBRDate());
      ausfertigung.setRefersTo("ausfertigung");
      ausfertigung.setType("generation");

      final EventRef inkrafttreten = lifecycle.addEventRef();
      inkrafttreten.setDate("0001-01-01");
      inkrafttreten.setRefersTo("inkrafttreten-mit-noch-unbekanntem-datum");
      inkrafttreten.setType("generation");

      final TemporalData temporalData = regelungstext.getMeta().getOrCreateTemporalData();
      final TemporalGroup temporalGroup = temporalData.addTemporalGroup();
      final TimeInterval timeInterval = temporalGroup.getOrCreateTimeInterval();
      timeInterval.setStart(
        new Href.Builder().setEId(inkrafttreten.getEid().toString()).buildInternalReference()
      );
      timeInterval.setRefersTo("geltungszeit");
    }
  }

  private void addPeriodToArticle(Document document) {
    NodeParser
      .getElementsFromExpression("//body//article[not(ancestor-or-self::mod)]", document)
      .stream()
      .filter(article -> {
        final Optional<String> optionalRefersTo = NodeParser.getValueFromExpression(
          "./@refersTo",
          article
        );
        return optionalRefersTo.isEmpty() || !optionalRefersTo.get().equals("geltungszeitregel");
      })
      .forEach(filtered -> filtered.setAttribute("period", "#meta-1_geltzeiten-1_geltungszeitgr-1")
      );
  }

  private void addFormulaAndSignature(Document document) {
    final Element conclusions = NodeParser.getMandatoryElementFromExpression(
      "//conclusions",
      document
    );

    conclusions.setAttribute("eId", "schluss-1");
    conclusions.setAttribute("GUID", UUID.randomUUID().toString());

    final Element formula = NodeCreator.createElementWithEidAndGuid("akn:formula", conclusions);
    formula.setAttribute(REFERSTO, "schlussformel");
    formula.setAttribute("name", ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

    final Element formulaParagraph = NodeCreator.createElementWithEidAndGuid(AKN_P, formula);
    formulaParagraph.setTextContent(
      "Das vorstehende Gesetz wird hiermit ausgefertigt. Es ist im Bundesgesetzblatt zu verkünden."
    );

    final Element blockContainer = NodeCreator.createElementWithEidAndGuid(
      "akn:blockContainer",
      conclusions
    );
    final Element blockContainerParagraph = NodeCreator.createElementWithEidAndGuid(
      AKN_P,
      blockContainer
    );

    final Element blockContainerParagraphLocation = NodeCreator.createElementWithEidAndGuid(
      "akn:location",
      blockContainerParagraph
    );
    blockContainerParagraphLocation.setAttribute(REFERSTO, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

    Element ausfertigungsDateNode = NodeParser.getMandatoryElementFromExpression(
      "//meta/lifecycle/eventRef[@refersTo=\"ausfertigung\"]",
      document
    );
    final Element blockContainerParagraphDate = NodeCreator.createElementWithEidAndGuid(
      "akn:date",
      blockContainerParagraph
    );
    blockContainerParagraphDate.setAttribute("date", ausfertigungsDateNode.getAttribute("date"));
    blockContainerParagraphDate.setAttribute(REFERSTO, "ausfertigung-datum");

    final Element blockContainerSignatur = NodeCreator.createElementWithEidAndGuid(
      "akn:signature",
      blockContainer
    );
    final Element blockContainerSignaturPerson = NodeCreator.createElementWithEidAndGuid(
      "akn:person",
      blockContainerSignatur
    );
    blockContainerSignaturPerson.setAttribute(REFERSTO, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
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
      "akn:FRBRalias",
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
