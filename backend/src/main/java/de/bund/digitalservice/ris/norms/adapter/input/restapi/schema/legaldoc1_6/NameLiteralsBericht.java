package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.bericht.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.bericht"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="bericht"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "nameLiterals.bericht", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsBericht {
  @XmlEnumValue("bericht")
  BERICHT("bericht");
  private final String value;

  NameLiteralsBericht(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsBericht fromValue(String v) {
    for (NameLiteralsBericht c : NameLiteralsBericht.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
