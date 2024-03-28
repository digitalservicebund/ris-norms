package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.kurztitel.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.kurztitel"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="amtliche-abkuerzung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "refersToLiterals.kurztitel", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsKurztitel {
  @XmlEnumValue("amtliche-abkuerzung")
  AMTLICHE_ABKUERZUNG("amtliche-abkuerzung");
  private final String value;

  RefersToLiteralsKurztitel(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsKurztitel fromValue(String v) {
    for (RefersToLiteralsKurztitel c : RefersToLiteralsKurztitel.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
