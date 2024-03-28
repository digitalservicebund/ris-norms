package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.regelungstextEntwurfsfassung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.regelungstextEntwurfsfassung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="regelungstext"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.regelungstextEntwurfsfassung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsRegelungstextEntwurfsfassung {
  @XmlEnumValue("regelungstext")
  REGELUNGSTEXT("regelungstext");
  private final String value;

  NameLiteralsRegelungstextEntwurfsfassung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsRegelungstextEntwurfsfassung fromValue(String v) {
    for (NameLiteralsRegelungstextEntwurfsfassung c :
        NameLiteralsRegelungstextEntwurfsfassung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
