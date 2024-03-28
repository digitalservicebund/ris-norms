package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.aenderungsbefehl.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.aenderungsbefehl"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="aenderungsbefehl-einfuegen"/&gt;
 *     &lt;enumeration value="aenderungsbefehl-ersetzen"/&gt;
 *     &lt;enumeration value="aenderungsbefehl-streichen"/&gt;
 *     &lt;enumeration value="aenderungsbefehl-neufassung"/&gt;
 *     &lt;enumeration value="aenderungsbefehl-ausserkrafttreten"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.aenderungsbefehl",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsAenderungsbefehl {
  @XmlEnumValue("aenderungsbefehl-einfuegen")
  AENDERUNGSBEFEHL_EINFUEGEN("aenderungsbefehl-einfuegen"),
  @XmlEnumValue("aenderungsbefehl-ersetzen")
  AENDERUNGSBEFEHL_ERSETZEN("aenderungsbefehl-ersetzen"),
  @XmlEnumValue("aenderungsbefehl-streichen")
  AENDERUNGSBEFEHL_STREICHEN("aenderungsbefehl-streichen"),
  @XmlEnumValue("aenderungsbefehl-neufassung")
  AENDERUNGSBEFEHL_NEUFASSUNG("aenderungsbefehl-neufassung"),
  @XmlEnumValue("aenderungsbefehl-ausserkrafttreten")
  AENDERUNGSBEFEHL_AUSSERKRAFTTRETEN("aenderungsbefehl-ausserkrafttreten");
  private final String value;

  RefersToLiteralsAenderungsbefehl(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsAenderungsbefehl fromValue(String v) {
    for (RefersToLiteralsAenderungsbefehl c : RefersToLiteralsAenderungsbefehl.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
