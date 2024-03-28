package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.begruendungsteil.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.begruendungsteil"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="begruendung-allgemeiner-teil"/&gt;
 *     &lt;enumeration value="begruendung-besonderer-teil"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.begruendungsteil",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsBegruendungsteil {
  @XmlEnumValue("begruendung-allgemeiner-teil")
  BEGRUENDUNG_ALLGEMEINER_TEIL("begruendung-allgemeiner-teil"),
  @XmlEnumValue("begruendung-besonderer-teil")
  BEGRUENDUNG_BESONDERER_TEIL("begruendung-besonderer-teil");
  private final String value;

  RefersToLiteralsBegruendungsteil(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsBegruendungsteil fromValue(String v) {
    for (RefersToLiteralsBegruendungsteil c : RefersToLiteralsBegruendungsteil.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
