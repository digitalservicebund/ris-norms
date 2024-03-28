package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.inhaltsabschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.inhaltsabschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-nachhaltigkeitsaspekte"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-weitere-kosten"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="regelungsfolgen-abschnitt-weitere-regelungsfolgen"/&gt;
 *     &lt;enumeration value="begruendung-erfuellungsaufwand-fuer-buergerinnen-und-buerger"/&gt;
 *     &lt;enumeration value="begruendung-erfuellungsaufwand-fuer-die-wirtschaft"/&gt;
 *     &lt;enumeration value="begruendung-erfuellungsaufwand-der-verwaltung"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-fuer-buergerinnen-und-buerger"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-fuer-die-wirtschaft"/&gt;
 *     &lt;enumeration value="davon-buerokratiekosten-aus-informationspflichten"/&gt;
 *     &lt;enumeration value="erfuellungsaufwand-der-verwaltung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.inhaltsabschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsInhaltsabschnitt {
  @XmlEnumValue("regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung")
  REGELUNGSFOLGEN_ABSCHNITT_RECHTS_UND_VERWALTUNGSVEREINFACHUNG(
      "regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung"),
  @XmlEnumValue("regelungsfolgen-abschnitt-nachhaltigkeitsaspekte")
  REGELUNGSFOLGEN_ABSCHNITT_NACHHALTIGKEITSASPEKTE(
      "regelungsfolgen-abschnitt-nachhaltigkeitsaspekte"),
  @XmlEnumValue("regelungsfolgen-abschnitt-erfuellungsaufwand")
  REGELUNGSFOLGEN_ABSCHNITT_ERFUELLUNGSAUFWAND("regelungsfolgen-abschnitt-erfuellungsaufwand"),
  @XmlEnumValue("regelungsfolgen-abschnitt-weitere-kosten")
  REGELUNGSFOLGEN_ABSCHNITT_WEITERE_KOSTEN("regelungsfolgen-abschnitt-weitere-kosten"),
  @XmlEnumValue("regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung")
  REGELUNGSFOLGEN_ABSCHNITT_GLEICHSTELLUNGSPOLITISCHE_RELEVANZPRUEFUNG(
      "regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung"),
  @XmlEnumValue("regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand")
  REGELUNGSFOLGEN_ABSCHNITT_HAUSHALTSAUSGABEN_OHNE_ERFUELLUNGSAUFWAND(
      "regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"),
  @XmlEnumValue("regelungsfolgen-abschnitt-weitere-regelungsfolgen")
  REGELUNGSFOLGEN_ABSCHNITT_WEITERE_REGELUNGSFOLGEN(
      "regelungsfolgen-abschnitt-weitere-regelungsfolgen"),
  @XmlEnumValue("begruendung-erfuellungsaufwand-fuer-buergerinnen-und-buerger")
  BEGRUENDUNG_ERFUELLUNGSAUFWAND_FUER_BUERGERINNEN_UND_BUERGER(
      "begruendung-erfuellungsaufwand-fuer-buergerinnen-und-buerger"),
  @XmlEnumValue("begruendung-erfuellungsaufwand-fuer-die-wirtschaft")
  BEGRUENDUNG_ERFUELLUNGSAUFWAND_FUER_DIE_WIRTSCHAFT(
      "begruendung-erfuellungsaufwand-fuer-die-wirtschaft"),
  @XmlEnumValue("begruendung-erfuellungsaufwand-der-verwaltung")
  BEGRUENDUNG_ERFUELLUNGSAUFWAND_DER_VERWALTUNG("begruendung-erfuellungsaufwand-der-verwaltung"),
  @XmlEnumValue("erfuellungsaufwand-fuer-buergerinnen-und-buerger")
  ERFUELLUNGSAUFWAND_FUER_BUERGERINNEN_UND_BUERGER(
      "erfuellungsaufwand-fuer-buergerinnen-und-buerger"),
  @XmlEnumValue("erfuellungsaufwand-fuer-die-wirtschaft")
  ERFUELLUNGSAUFWAND_FUER_DIE_WIRTSCHAFT("erfuellungsaufwand-fuer-die-wirtschaft"),
  @XmlEnumValue("davon-buerokratiekosten-aus-informationspflichten")
  DAVON_BUEROKRATIEKOSTEN_AUS_INFORMATIONSPFLICHTEN(
      "davon-buerokratiekosten-aus-informationspflichten"),
  @XmlEnumValue("erfuellungsaufwand-der-verwaltung")
  ERFUELLUNGSAUFWAND_DER_VERWALTUNG("erfuellungsaufwand-der-verwaltung");
  private final String value;

  RefersToLiteralsInhaltsabschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsInhaltsabschnitt fromValue(String v) {
    for (RefersToLiteralsInhaltsabschnitt c : RefersToLiteralsInhaltsabschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
