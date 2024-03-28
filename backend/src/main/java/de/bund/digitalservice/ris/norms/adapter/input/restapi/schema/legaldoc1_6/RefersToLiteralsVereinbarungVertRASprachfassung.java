package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.vereinbarungVertRASprachfassung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.vereinbarungVertRASprachfassung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="verbindliche-sprachfassung"/&gt;
 *     &lt;enumeration value="uebersetzung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.vereinbarungVertRASprachfassung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsVereinbarungVertRASprachfassung {
  @XmlEnumValue("verbindliche-sprachfassung")
  VERBINDLICHE_SPRACHFASSUNG("verbindliche-sprachfassung"),
  @XmlEnumValue("uebersetzung")
  UEBERSETZUNG("uebersetzung");
  private final String value;

  RefersToLiteralsVereinbarungVertRASprachfassung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsVereinbarungVertRASprachfassung fromValue(String v) {
    for (RefersToLiteralsVereinbarungVertRASprachfassung c :
        RefersToLiteralsVereinbarungVertRASprachfassung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
