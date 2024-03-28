package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.regelungstextVerkuendungsfassung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.regelungstextVerkuendungsfassung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="regelungstext"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.regelungstextVerkuendungsfassung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsRegelungstextVerkuendungsfassung {
  @XmlEnumValue("regelungstext")
  REGELUNGSTEXT("regelungstext");
  private final String value;

  NameLiteralsRegelungstextVerkuendungsfassung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsRegelungstextVerkuendungsfassung fromValue(String v) {
    for (NameLiteralsRegelungstextVerkuendungsfassung c :
        NameLiteralsRegelungstextVerkuendungsfassung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
