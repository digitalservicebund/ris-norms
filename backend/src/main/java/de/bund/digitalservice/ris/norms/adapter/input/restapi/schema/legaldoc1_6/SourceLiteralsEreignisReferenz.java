package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for sourceLiterals.ereignisReferenz.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="sourceLiterals.ereignisReferenz"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals"&gt;
 *     &lt;enumeration value="attributsemantik-noch-undefiniert"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "sourceLiterals.ereignisReferenz",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum SourceLiteralsEreignisReferenz {
  @XmlEnumValue("attributsemantik-noch-undefiniert")
  ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT("attributsemantik-noch-undefiniert");
  private final String value;

  SourceLiteralsEreignisReferenz(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static SourceLiteralsEreignisReferenz fromValue(String v) {
    for (SourceLiteralsEreignisReferenz c : SourceLiteralsEreignisReferenz.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
