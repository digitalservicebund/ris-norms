package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.aenderungsantrag.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.aenderungsantrag"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="aenderungsantrag"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.aenderungsantrag",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsAenderungsantrag {
  @XmlEnumValue("aenderungsantrag")
  AENDERUNGSANTRAG("aenderungsantrag");
  private final String value;

  NameLiteralsAenderungsantrag(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsAenderungsantrag fromValue(String v) {
    for (NameLiteralsAenderungsantrag c : NameLiteralsAenderungsantrag.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
