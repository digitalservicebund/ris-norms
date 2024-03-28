package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for sourceLiterals.lebenszyklus.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="sourceLiterals.lebenszyklus"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals"&gt;
 *     &lt;enumeration value="attributsemantik-noch-undefiniert"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "sourceLiterals.lebenszyklus", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum SourceLiteralsLebenszyklus {
  @XmlEnumValue("attributsemantik-noch-undefiniert")
  ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT("attributsemantik-noch-undefiniert");
  private final String value;

  SourceLiteralsLebenszyklus(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static SourceLiteralsLebenszyklus fromValue(String v) {
    for (SourceLiteralsLebenszyklus c : SourceLiteralsLebenszyklus.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
