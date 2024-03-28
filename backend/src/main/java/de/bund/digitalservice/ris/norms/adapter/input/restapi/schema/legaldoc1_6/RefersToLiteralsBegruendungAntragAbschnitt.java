package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.begruendungAntragAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.begruendungAntragAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="begruendungsabschnitt-antrag"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.begruendungAntragAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsBegruendungAntragAbschnitt {
  @XmlEnumValue("begruendungsabschnitt-antrag")
  BEGRUENDUNGSABSCHNITT_ANTRAG("begruendungsabschnitt-antrag");
  private final String value;

  RefersToLiteralsBegruendungAntragAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsBegruendungAntragAbschnitt fromValue(String v) {
    for (RefersToLiteralsBegruendungAntragAbschnitt c :
        RefersToLiteralsBegruendungAntragAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
