package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for placementLiterals.fussnote.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="placementLiterals.fussnote"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}placementLiterals"&gt;
 *     &lt;enumeration value="bottom"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "placementLiterals.fussnote", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(PlacementLiterals.class)
public enum PlacementLiteralsFussnote {
  @XmlEnumValue("bottom")
  BOTTOM(PlacementLiterals.BOTTOM);
  private final PlacementLiterals value;

  PlacementLiteralsFussnote(PlacementLiterals v) {
    value = v;
  }

  public PlacementLiterals value() {
    return value;
  }

  public static PlacementLiteralsFussnote fromValue(PlacementLiterals v) {
    for (PlacementLiteralsFussnote c : PlacementLiteralsFussnote.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
