package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.inline.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.inline"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="ausschussueberweisung"/&gt;
 *     &lt;enumeration value="amtliche-abkuerzung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "refersToLiterals.inline", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsInline {
  @XmlEnumValue("ausschussueberweisung")
  AUSSCHUSSUEBERWEISUNG("ausschussueberweisung"),
  @XmlEnumValue("amtliche-abkuerzung")
  AMTLICHE_ABKUERZUNG("amtliche-abkuerzung");
  private final String value;

  RefersToLiteralsInline(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsInline fromValue(String v) {
    for (RefersToLiteralsInline c : RefersToLiteralsInline.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
