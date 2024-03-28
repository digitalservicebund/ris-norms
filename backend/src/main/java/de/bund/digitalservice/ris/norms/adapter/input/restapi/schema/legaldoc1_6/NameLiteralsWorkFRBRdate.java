package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.work.FRBRdate.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.work.FRBRdate"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="entwurfsfassung"/&gt;
 *     &lt;enumeration value="verkuendungsfassung"/&gt;
 *     &lt;enumeration value="neufassung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "nameLiterals.work.FRBRdate", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsWorkFRBRdate {
  @XmlEnumValue("entwurfsfassung")
  ENTWURFSFASSUNG("entwurfsfassung"),
  @XmlEnumValue("verkuendungsfassung")
  VERKUENDUNGSFASSUNG("verkuendungsfassung"),
  @XmlEnumValue("neufassung")
  NEUFASSUNG("neufassung");
  private final String value;

  NameLiteralsWorkFRBRdate(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsWorkFRBRdate fromValue(String v) {
    for (NameLiteralsWorkFRBRdate c : NameLiteralsWorkFRBRdate.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
