package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for posLiterals.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="posLiterals"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="before"/&gt;
 *     &lt;enumeration value="after"/&gt;
 *     &lt;enumeration value="start"/&gt;
 *     &lt;enumeration value="end"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "posLiterals", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum PosLiterals {
  @XmlEnumValue("before")
  BEFORE("before"),
  @XmlEnumValue("after")
  AFTER("after"),
  @XmlEnumValue("start")
  START("start"),
  @XmlEnumValue("end")
  END("end");
  private final String value;

  PosLiterals(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static PosLiterals fromValue(String v) {
    for (PosLiterals c : PosLiterals.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
