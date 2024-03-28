package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.vorblattRegelungstext.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.vorblattRegelungstext"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="vorblatt"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.vorblattRegelungstext",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsVorblattRegelungstext {
  @XmlEnumValue("vorblatt")
  VORBLATT("vorblatt");
  private final String value;

  NameLiteralsVorblattRegelungstext(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsVorblattRegelungstext fromValue(String v) {
    for (NameLiteralsVorblattRegelungstext c : NameLiteralsVorblattRegelungstext.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
