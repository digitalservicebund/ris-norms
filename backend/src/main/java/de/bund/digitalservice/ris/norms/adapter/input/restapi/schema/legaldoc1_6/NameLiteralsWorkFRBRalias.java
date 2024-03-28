package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.work.FRBRalias.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.work.FRBRalias"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="übergreifende-id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "nameLiterals.work.FRBRalias", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsWorkFRBRalias {
  @XmlEnumValue("\u00fcbergreifende-id")
  ÜBERGREIFENDE_ID("\u00fcbergreifende-id");
  private final String value;

  NameLiteralsWorkFRBRalias(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsWorkFRBRalias fromValue(String v) {
    for (NameLiteralsWorkFRBRalias c : NameLiteralsWorkFRBRalias.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
