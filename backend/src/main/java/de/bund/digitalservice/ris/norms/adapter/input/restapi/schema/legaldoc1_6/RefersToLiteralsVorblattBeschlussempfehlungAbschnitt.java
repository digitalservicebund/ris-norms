package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.vorblattBeschlussempfehlungAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.vorblattBeschlussempfehlungAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="vorblattabschnitt-problem"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-loesung"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-alternativen"/&gt;
 *     &lt;enumeration value="vorblattabschnitt-kosten"/&gt;
 *     &lt;enumeration value="weiterer-vorblattabschnitt"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.vorblattBeschlussempfehlungAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsVorblattBeschlussempfehlungAbschnitt {
  @XmlEnumValue("vorblattabschnitt-problem")
  VORBLATTABSCHNITT_PROBLEM("vorblattabschnitt-problem"),
  @XmlEnumValue("vorblattabschnitt-loesung")
  VORBLATTABSCHNITT_LOESUNG("vorblattabschnitt-loesung"),
  @XmlEnumValue("vorblattabschnitt-alternativen")
  VORBLATTABSCHNITT_ALTERNATIVEN("vorblattabschnitt-alternativen"),
  @XmlEnumValue("vorblattabschnitt-kosten")
  VORBLATTABSCHNITT_KOSTEN("vorblattabschnitt-kosten"),
  @XmlEnumValue("weiterer-vorblattabschnitt")
  WEITERER_VORBLATTABSCHNITT("weiterer-vorblattabschnitt");
  private final String value;

  RefersToLiteralsVorblattBeschlussempfehlungAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsVorblattBeschlussempfehlungAbschnitt fromValue(String v) {
    for (RefersToLiteralsVorblattBeschlussempfehlungAbschnitt c :
        RefersToLiteralsVorblattBeschlussempfehlungAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
