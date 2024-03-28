package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.anschreiben.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.anschreiben"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="anschreiben"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "nameLiterals.anschreiben", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsAnschreiben {
  @XmlEnumValue("anschreiben")
  ANSCHREIBEN("anschreiben");
  private final String value;

  NameLiteralsAnschreiben(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsAnschreiben fromValue(String v) {
    for (NameLiteralsAnschreiben c : NameLiteralsAnschreiben.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
