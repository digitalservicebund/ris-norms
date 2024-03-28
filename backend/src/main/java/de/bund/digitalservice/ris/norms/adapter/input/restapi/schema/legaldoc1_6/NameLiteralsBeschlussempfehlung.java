package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.beschlussempfehlung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.beschlussempfehlung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="beschlussempfehlung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.beschlussempfehlung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsBeschlussempfehlung {
  @XmlEnumValue("beschlussempfehlung")
  BESCHLUSSEMPFEHLUNG("beschlussempfehlung");
  private final String value;

  NameLiteralsBeschlussempfehlung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsBeschlussempfehlung fromValue(String v) {
    for (NameLiteralsBeschlussempfehlung c : NameLiteralsBeschlussempfehlung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
