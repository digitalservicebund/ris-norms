package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.sprachfassungPraeambelInhaltsuebersicht.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.sprachfassungPraeambelInhaltsuebersicht"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="sprachfassung-inhaltsuebersicht"/&gt;
 *     &lt;enumeration value="sprachfassung-praeambel"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.sprachfassungPraeambelInhaltsuebersicht",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsSprachfassungPraeambelInhaltsuebersicht {
  @XmlEnumValue("sprachfassung-inhaltsuebersicht")
  SPRACHFASSUNG_INHALTSUEBERSICHT("sprachfassung-inhaltsuebersicht"),
  @XmlEnumValue("sprachfassung-praeambel")
  SPRACHFASSUNG_PRAEAMBEL("sprachfassung-praeambel");
  private final String value;

  RefersToLiteralsSprachfassungPraeambelInhaltsuebersicht(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsSprachfassungPraeambelInhaltsuebersicht fromValue(String v) {
    for (RefersToLiteralsSprachfassungPraeambelInhaltsuebersicht c :
        RefersToLiteralsSprachfassungPraeambelInhaltsuebersicht.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
