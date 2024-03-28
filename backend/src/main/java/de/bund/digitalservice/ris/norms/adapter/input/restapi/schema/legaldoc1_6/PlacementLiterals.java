package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for placementLiterals.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="placementLiterals"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="side"/&gt;
 *     &lt;enumeration value="left"/&gt;
 *     &lt;enumeration value="right"/&gt;
 *     &lt;enumeration value="bottom"/&gt;
 *     &lt;enumeration value="inline"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "placementLiterals", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum PlacementLiterals {
  @XmlEnumValue("side")
  SIDE("side"),
  @XmlEnumValue("left")
  LEFT("left"),
  @XmlEnumValue("right")
  RIGHT("right"),
  @XmlEnumValue("bottom")
  BOTTOM("bottom"),
  @XmlEnumValue("inline")
  INLINE("inline");
  private final String value;

  PlacementLiterals(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static PlacementLiterals fromValue(String v) {
    for (PlacementLiterals c : PlacementLiterals.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
