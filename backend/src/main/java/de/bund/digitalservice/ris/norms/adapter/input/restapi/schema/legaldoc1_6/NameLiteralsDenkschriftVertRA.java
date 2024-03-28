package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.denkschriftVertRA.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.denkschriftVertRA"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="denkschrift"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.denkschriftVertRA",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsDenkschriftVertRA {
  @XmlEnumValue("denkschrift")
  DENKSCHRIFT("denkschrift");
  private final String value;

  NameLiteralsDenkschriftVertRA(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsDenkschriftVertRA fromValue(String v) {
    for (NameLiteralsDenkschriftVertRA c : NameLiteralsDenkschriftVertRA.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
