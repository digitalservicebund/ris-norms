package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.w3c.dom.Node;

/**
 * The type of part of an EId.
 *
 * <p>See LDML.de 1.6 Section 7.1.3
 */
@Getter
public enum EIdPartType {
  A("a", List.of("akn:a")),
  ABBR("abbr", List.of("akn:abbr")),
  ABS("abs", List.of("akn:paragraph")),
  ABSCHNITT("abschnitt", List.of("akn:section")),
  ACTIVEMOD("activemod", List.of("akn:activeModifications")),
  AENDBEFEHL("ändbefehl", List.of("akn:mod")),
  ANALYSIS("analysis", List.of("akn:analysis")),
  ANLAGE("anlage", List.of("akn:attachment")),
  ANLAGEN("anlagen", List.of("akn:attachments")),
  ART("art", List.of("akn:article")), // two possible eIds for this element
  BEZEICHNUNG("bezeichnung", List.of("akn:num")),
  BEZUGSDOC("bezugsdoc", List.of("akn:affectedDocument")),
  BEZUGSDOK("bezugsdok", List.of("akn:relatedDocument")),
  BILD("bild", List.of("akn:img")),
  BLOCK("block", List.of("akn:block")),
  BLOCKCONTAINER("blockcontainer", List.of("akn:blockContainer")),
  BR("br", List.of("akn:br")),
  BREF("bref", List.of("akn:rref")), // new in LDML.de 1.7
  BUCH("buch", List.of("akn:book")),
  CONTAINER("container", List.of("akn:hcontainer")),
  DATUM("datum", List.of("akn:date")),
  DESTINATION("destination", List.of("akn:destination")),
  DOCAUTH("docauth", List.of("akn:docAuthority")),
  DOCDATUM("docdatum", List.of("akn:docDate")),
  DOCPROPONENT("docproponent", List.of("akn:docProponent")),
  DOCSTADIUM("docstadium", List.of("akn:docStage")),
  DOCTITEL("doctitel", List.of("akn:docTitle")),
  DOCTYPE("doctype", List.of("akn:docType")),
  DOKTITEL("doktitel", List.of("akn:longTitle")),
  DRUCKSACHENNR("drucksachennr", List.of("akn:docNumber")),
  EINLEITUNG("einleitung", List.of("akn:preface")),
  EINTRAG("eintrag", List.of("akn:tocItem")),
  EOL("eol", List.of("akn:eol")),
  EOP("eop", List.of("akn:eop")),
  EREIGNIS("ereignis", List.of("akn:eventRef")),
  ERNORM("ernorm", List.of("akn:citation")),
  ERNORMEN("ernormen", List.of("akn:citations")),
  EXMARKUP("exmarkup", List.of("akn:foreign")),
  FETTSCHRIFT("fettschrift", List.of("akn:b")),
  FKTBEZ("fktbez", List.of("akn:role")),
  FNOTE("fnote", List.of("akn:authorialNote")),
  FORMEL("formel", List.of("akn:formula")),
  FRBRALIAS("frbralias", List.of("akn:FRBRalias")),
  FRBRAUTHOR("frbrauthor", List.of("akn:FRBRauthor")),
  FRBRCOUNTRY("frbrcountry", List.of("akn:FRBRcountry")),
  FRBRDATE("frbrdate", List.of("akn:FRBRdate")),
  FRBRERSIONNUMBER(
      "frbrersionnumber",
      List.of("akn:FRBRversionNumber")), // TODO: (Malte Laukötter, 2024-08-28) change to
  // "frbrversionnumber" for LDML.de 1.7
  FRBREXPRESSION("frbrexpression", List.of("akn:FRBRExpression")),
  FRBRFORMAT("frbrformat", List.of("akn:FRBRformat")),
  FRBRLANGUAGE("frbrlanguage", List.of("akn:FRBRlanguage")),
  FRBRMANIFESTATION("frbrmanifestation", List.of("akn:FRBRManifestation")),
  FRBRNAME("frbrname", List.of("akn:FRBRname")),
  FRBRNUMBER("frbrnumber", List.of("akn:FRBRnumber")),
  FRBRSUBTYPE("frbrsubtype", List.of("akn:FRBRsubtype")),
  FRBRTHIS("frbrthis", List.of("akn:FRBRthis")),
  FRBRURI("frbruri", List.of("akn:FRBRuri")),
  FRBRWORK("frbrwork", List.of("akn:FRBRWork")),
  GELTUNGSZEITGR("geltungszeitgr", List.of("akn:temporalGroup")),
  GELTZEITEN("geltzeiten", List.of("akn:temporalData")),
  GELZEITAEND("gelzeitaend", List.of("akn:forceMod")),
  GELZEITINTERVALL("gelzeitintervall", List.of("akn:timeInterval")),
  GELZEITNACHW("gelzeitnachw", List.of("akn:force")),
  HAUPTTEIL("hauptteil", List.of("akn:body", "akn:mainBody")),
  IDENT("ident", List.of("akn:identification")),
  INHALT("inhalt", List.of("akn:content")),
  INHALTABSCHNITT("inhaltabschnitt", List.of("akn:tblock")),
  INHUEBS("inhuebs", List.of("akn:toc")),
  INLINE("inline", List.of("akn:inline")),
  INTRO("intro", List.of("akn:intro")),
  KAPITEL("kapitel", List.of("akn:chapter")),
  KOMBAENDBEFEHL("kombändbefehl", List.of("akn:mmod")), // new in LDML.de 1.7
  KURSIV("kursiv", List.of("akn:i")),
  KURZTITEL("kurztitel", List.of("akn:shortTitle")),
  LEBZYKL("lebzykl", List.of("akn:lifecycle")),
  LISTE("liste", List.of("akn:blockList")),
  LISTEGEOR("listegeor", List.of("akn:ol")),
  LISTENEING("listeneing", List.of("akn:listIntroduction")),
  LISTENELEM("listenelem", List.of("akn:item", "akn:li", "akn:point")),
  LISTENSCHL("listenschl", List.of("akn:listWrapUp")),
  LISTEUNGE("listeunge", List.of("akn:ul")),
  META("meta", List.of("akn:meta")),
  NEW("new", List.of("akn:new")),
  ORG("org", List.of("akn:organization")),
  ORT("ort", List.of("akn:location")),
  PARA("para", List.of("akn:article")), // two possible eIds for this element
  PASMOD("pasmod", List.of("akn:passiveModifications")),
  PERSON("person", List.of("akn:person")),
  PREAMBEL("preambel", List.of("akn:preamble")),
  PROPRIETARY("proprietary", List.of("akn:proprietary")),
  PRAEAMBELINH("präambelinh", List.of("akn:recital")),
  PRAEAMBELN("präambeln", List.of("akn:recitals")),
  QUOTSTRUCT("quotstruct", List.of("akn:quotedStructure")),
  QUOTTEXT("quottext", List.of("akn:quotedText")),
  RDOKHAUPTTEIL("rdokhauptteil", List.of("akn:collectionBody")),
  REF("ref", List.of("akn:ref")),
  SCHLUSS("schluss", List.of("akn:conclusions")),
  SCHLUSSTEXT("schlusstext", List.of("akn:wrapUp")),
  SIGNATUR("signatur", List.of("akn:signature")),
  SITZUNG("sitzung", List.of("akn:session")),
  SOURCE("source", List.of("akn:source")),
  SPAN("span", List.of("akn:span")),
  STFMVERWEIS("stfmverweis", List.of("akn:componentRef")),
  SUB("sub", List.of("akn:sub")),
  SUP("sup", List.of("akn:sup")),
  TABELLE("tabelle", List.of("akn:table")),
  TABELLEINH("tabelleinh", List.of("akn:td")),
  TABELLEKOPF("tabellekopf", List.of("akn:th")),
  TABELLEREIHE("tabellereihe", List.of("akn:tr")),
  TBLUE("tblue", List.of("akn:caption")),
  TEIL("teil", List.of("akn:part")),
  TEXT("text", List.of("akn:p")),
  TEXTUALMOD("textualmod", List.of("akn:textualMod")),
  TITEL("titel", List.of("akn:title")),
  TLDOKVERWEIS("tldokverweis", List.of("akn:component")),
  TLDOKVERWEISE("tldokverweise", List.of("akn:components")),
  U("u", List.of("akn:u")),
  UABSCHNITT("uabschnitt", List.of("akn:subsection")),
  UEBERSCHRIFT("überschrift", List.of("akn:heading")),
  UKAPITEL("ukapitel", List.of("akn:subchapter")),
  UNTERGL("untergl", List.of("akn:list")),
  UTITEL("utitel", List.of("akn:subtitle")),
  VERWEIS("verweis", List.of("akn:documentRef")),
  ZAEHLBEZ(
      "zaehlbez",
      List.of(
          "akn:marker")) // TODO: (Malte Laukötter, 2024-08-28) change to "marker" for LDML.de 1.7
;

  /** Name for the part type to use in the eId. */
  private final String name;

  /** Names of the akn:elements that can use this part type for the last part of their eId. */
  private final List<String> aknElements;

  EIdPartType(String name, List<String> aknElements) {
    this.name = name;
    this.aknElements = aknElements;
  }

  /**
   * Get the expected EId Part Type for the given akn element.
   *
   * @param aknElement the akn element.
   * @return the type part of the last element of the eId for the given element
   */
  static Optional<EIdPartType> forAknElement(Node aknElement) {
    // See Schematron rules SCH-00570 and SCH-00580
    if (aknElement.getNodeName().equals("akn:article")) {
      return Optional.of(forAknArticle(aknElement));
    }

    return Arrays.stream(EIdPartType.values())
        .filter(partType -> partType.aknElements.contains(aknElement.getNodeName()))
        .findFirst();
  }

  private static EIdPartType forAknArticle(Node aknArticleElement) {
    var isInQuotedStructure =
        NodeParser.getNodeFromExpression("ancestor::quotedStructure", aknArticleElement);

    var refersToAttribute = aknArticleElement.getAttributes().getNamedItem("refersTo");
    var typ = Optional.ofNullable(refersToAttribute).map(Node::getNodeValue).orElse("");

    // SCH-00580
    if (isInQuotedStructure.isPresent()) {
      if ("vertragsgesetz".equals(typ) || "vertragsverordnung".equals(typ)) {
        return EIdPartType.ART;
      }

      return EIdPartType.PARA;
    }

    var form =
        NodeParser.getValueFromExpression(
            "//akomaNtoso/*/meta/proprietary/legalDocML.de_metadaten/form", aknArticleElement);

    if (form.isEmpty()) {
      throw new IllegalArgumentException(
          "Could not determine expected eId for akn:article node. No meta:form found.");
    }

    switch (form.get()) {
        // SCH-00570-000 (second part)
      case "eingebundene-stammform" -> {
        return EIdPartType.PARA;
      }
        // SCH-00570-010
      case "mantelform" -> {
        return EIdPartType.ART;
      }
      case "stammform" -> {
        // SCH-00570-005
        if ("vertragsgesetz".equals(typ) || "vertragsverordnung".equals(typ)) {
          return EIdPartType.ART;
        }

        // SCH-00570-000 (first part)
        return EIdPartType.PARA;
      }
      default ->
          throw new IllegalArgumentException(
              "Could not determine expected eId for akn:article node. Unexpected form (%s)"
                  .formatted(form));
    }
  }
}
