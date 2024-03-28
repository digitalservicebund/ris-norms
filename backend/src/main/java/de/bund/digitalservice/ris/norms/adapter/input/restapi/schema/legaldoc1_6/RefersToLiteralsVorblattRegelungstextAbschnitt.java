package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.vorblattRegelungstextAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.vorblattRegelungstextAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="vorblattabschnitt-problem-und-ziel"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-loesung"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-alternativen"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-fuer-buergerinnen-und-buerger"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-fuer-die-wirtschaft"/&gt;
 *     &lt;enumeration value="davon-buerokratiekosten-aus-informationspflichten"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-der-verwaltung"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-weitere-kosten"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.vorblattRegelungstextAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsVorblattRegelungstextAbschnitt {
  @XmlEnumValue("vorblattabschnitt-problem-und-ziel")
  VORBLATTABSCHNITT_PROBLEM_UND_ZIEL("vorblattabschnitt-problem-und-ziel"),
  @XmlEnumValue("vorblattabschnitt-loesung")
  VORBLATTABSCHNITT_LOESUNG("vorblattabschnitt-loesung"),
  @XmlEnumValue("vorblattabschnitt-alternativen")
  VORBLATTABSCHNITT_ALTERNATIVEN("vorblattabschnitt-alternativen"),
  @XmlEnumValue("vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand")
  VORBLATTABSCHNITT_HAUSHALTSAUSGABEN_OHNE_ERFUELLUNGSAUFWAND(
      "vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"),
  @XmlEnumValue("vorblattabschnitt-erfuellungsaufwand")
  VORBLATTABSCHNITT_ERFUELLUNGSAUFWAND("vorblattabschnitt-erfuellungsaufwand"),
  @XmlEnumValue("erfuellungsaufwand-fuer-buergerinnen-und-buerger")
  ERFUELLUNGSAUFWAND_FUER_BUERGERINNEN_UND_BUERGER(
      "erfuellungsaufwand-fuer-buergerinnen-und-buerger"),
  @XmlEnumValue("erfuellungsaufwand-fuer-die-wirtschaft")
  ERFUELLUNGSAUFWAND_FUER_DIE_WIRTSCHAFT("erfuellungsaufwand-fuer-die-wirtschaft"),
  @XmlEnumValue("davon-buerokratiekosten-aus-informationspflichten")
  DAVON_BUEROKRATIEKOSTEN_AUS_INFORMATIONSPFLICHTEN(
      "davon-buerokratiekosten-aus-informationspflichten"),
  @XmlEnumValue("erfuellungsaufwand-der-verwaltung")
  ERFUELLUNGSAUFWAND_DER_VERWALTUNG("erfuellungsaufwand-der-verwaltung"),
  @XmlEnumValue("vorblattabschnitt-weitere-kosten")
  VORBLATTABSCHNITT_WEITERE_KOSTEN("vorblattabschnitt-weitere-kosten");
  private final String value;

  RefersToLiteralsVorblattRegelungstextAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsVorblattRegelungstextAbschnitt fromValue(String v) {
    for (RefersToLiteralsVorblattRegelungstextAbschnitt c :
        RefersToLiteralsVorblattRegelungstextAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
