package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.bekanntmachung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.bekanntmachung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="bekanntmachungstext"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "nameLiterals.bekanntmachung", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsBekanntmachung {
  @XmlEnumValue("bekanntmachungstext")
  BEKANNTMACHUNGSTEXT("bekanntmachungstext");
  private final String value;

  NameLiteralsBekanntmachung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsBekanntmachung fromValue(String v) {
    for (NameLiteralsBekanntmachung c : NameLiteralsBekanntmachung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
