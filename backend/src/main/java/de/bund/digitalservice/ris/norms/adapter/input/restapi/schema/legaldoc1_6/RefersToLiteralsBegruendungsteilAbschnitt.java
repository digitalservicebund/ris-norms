package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.begruendungsteilAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.begruendungsteilAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="begruendungsabschnitt-zielsetzung-und-notwendigkeit"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-wesentlicher-inhalt"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-alternativen"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-regelungskompetenz"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-vereinbarkeit"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-befristung-evaluierung"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-regelungsfolgen"/&gt;
 *     &lt;enumeration value="begruendungsabschnitt-regelungstext"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.begruendungsteilAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsBegruendungsteilAbschnitt {
  @XmlEnumValue("begruendungsabschnitt-zielsetzung-und-notwendigkeit")
  BEGRUENDUNGSABSCHNITT_ZIELSETZUNG_UND_NOTWENDIGKEIT(
      "begruendungsabschnitt-zielsetzung-und-notwendigkeit"),
  @XmlEnumValue("begruendungsabschnitt-wesentlicher-inhalt")
  BEGRUENDUNGSABSCHNITT_WESENTLICHER_INHALT("begruendungsabschnitt-wesentlicher-inhalt"),
  @XmlEnumValue("begruendungsabschnitt-alternativen")
  BEGRUENDUNGSABSCHNITT_ALTERNATIVEN("begruendungsabschnitt-alternativen"),
  @XmlEnumValue("begruendungsabschnitt-regelungskompetenz")
  BEGRUENDUNGSABSCHNITT_REGELUNGSKOMPETENZ("begruendungsabschnitt-regelungskompetenz"),
  @XmlEnumValue("begruendungsabschnitt-vereinbarkeit")
  BEGRUENDUNGSABSCHNITT_VEREINBARKEIT("begruendungsabschnitt-vereinbarkeit"),
  @XmlEnumValue("begruendungsabschnitt-befristung-evaluierung")
  BEGRUENDUNGSABSCHNITT_BEFRISTUNG_EVALUIERUNG("begruendungsabschnitt-befristung-evaluierung"),
  @XmlEnumValue("begruendungsabschnitt-regelungsfolgen")
  BEGRUENDUNGSABSCHNITT_REGELUNGSFOLGEN("begruendungsabschnitt-regelungsfolgen"),
  @XmlEnumValue("begruendungsabschnitt-regelungstext")
  BEGRUENDUNGSABSCHNITT_REGELUNGSTEXT("begruendungsabschnitt-regelungstext");
  private final String value;

  RefersToLiteralsBegruendungsteilAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsBegruendungsteilAbschnitt fromValue(String v) {
    for (RefersToLiteralsBegruendungsteilAbschnitt c :
        RefersToLiteralsBegruendungsteilAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
