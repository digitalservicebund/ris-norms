package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.berichtAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.berichtAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="berichtabschnitt-ueberweisung"/&gt;
 *     &lt;enumeration value="berichtabschnitt-wesentlicher-inhalt-der-vorlage"/&gt;
 *     &lt;enumeration value="berichtabschnitt-stellungnahmen-der-mitberatenden-ausschuesse"/&gt;
 *     &lt;enumeration value="berichtabschnitt-beratungsverlauf"/&gt;
 *     &lt;enumeration value="berichtabschnitt-sonstige"/&gt;
 *     &lt;enumeration value="berichtabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="berichtabschnitt-erfuellungsaufwand"/&gt;
 *     &lt;enumeration value="berichtabschnitt-erfuellungsaufwand-fuer-buergerinnen-und-buerge"/&gt;
 *     &lt;enumeration value="berichtabschnitt-erfuellungsaufwand-fuer-die-wirtschaft"/&gt;
 *     &lt;enumeration value="berichtabschnitt-erfuellungsaufwand-der-verwaltung"/&gt;
 *     &lt;enumeration value="berichtabschnitt-weitere-kosten"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.berichtAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsBerichtAbschnitt {
  @XmlEnumValue("berichtabschnitt-ueberweisung")
  BERICHTABSCHNITT_UEBERWEISUNG("berichtabschnitt-ueberweisung"),
  @XmlEnumValue("berichtabschnitt-wesentlicher-inhalt-der-vorlage")
  BERICHTABSCHNITT_WESENTLICHER_INHALT_DER_VORLAGE(
      "berichtabschnitt-wesentlicher-inhalt-der-vorlage"),
  @XmlEnumValue("berichtabschnitt-stellungnahmen-der-mitberatenden-ausschuesse")
  BERICHTABSCHNITT_STELLUNGNAHMEN_DER_MITBERATENDEN_AUSSCHUESSE(
      "berichtabschnitt-stellungnahmen-der-mitberatenden-ausschuesse"),
  @XmlEnumValue("berichtabschnitt-beratungsverlauf")
  BERICHTABSCHNITT_BERATUNGSVERLAUF("berichtabschnitt-beratungsverlauf"),
  @XmlEnumValue("berichtabschnitt-sonstige")
  BERICHTABSCHNITT_SONSTIGE("berichtabschnitt-sonstige"),
  @XmlEnumValue("berichtabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand")
  BERICHTABSCHNITT_HAUSHALTSAUSGABEN_OHNE_ERFUELLUNGSAUFWAND(
      "berichtabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand"),
  @XmlEnumValue("berichtabschnitt-erfuellungsaufwand")
  BERICHTABSCHNITT_ERFUELLUNGSAUFWAND("berichtabschnitt-erfuellungsaufwand"),
  @XmlEnumValue("berichtabschnitt-erfuellungsaufwand-fuer-buergerinnen-und-buerge")
  BERICHTABSCHNITT_ERFUELLUNGSAUFWAND_FUER_BUERGERINNEN_UND_BUERGE(
      "berichtabschnitt-erfuellungsaufwand-fuer-buergerinnen-und-buerge"),
  @XmlEnumValue("berichtabschnitt-erfuellungsaufwand-fuer-die-wirtschaft")
  BERICHTABSCHNITT_ERFUELLUNGSAUFWAND_FUER_DIE_WIRTSCHAFT(
      "berichtabschnitt-erfuellungsaufwand-fuer-die-wirtschaft"),
  @XmlEnumValue("berichtabschnitt-erfuellungsaufwand-der-verwaltung")
  BERICHTABSCHNITT_ERFUELLUNGSAUFWAND_DER_VERWALTUNG(
      "berichtabschnitt-erfuellungsaufwand-der-verwaltung"),
  @XmlEnumValue("berichtabschnitt-weitere-kosten")
  BERICHTABSCHNITT_WEITERE_KOSTEN("berichtabschnitt-weitere-kosten");
  private final String value;

  RefersToLiteralsBerichtAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsBerichtAbschnitt fromValue(String v) {
    for (RefersToLiteralsBerichtAbschnitt c : RefersToLiteralsBerichtAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
