package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.entschließungsantrag.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.entschließungsantrag"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="entschließungsantrag"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.entschlie\u00dfungsantrag",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsEntschließungsantrag {
  @XmlEnumValue("entschlie\u00dfungsantrag")
  ENTSCHLIESSUNGSANTRAG("entschlie\u00dfungsantrag");
  private final String value;

  NameLiteralsEntschließungsantrag(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsEntschließungsantrag fromValue(String v) {
    for (NameLiteralsEntschließungsantrag c : NameLiteralsEntschließungsantrag.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
