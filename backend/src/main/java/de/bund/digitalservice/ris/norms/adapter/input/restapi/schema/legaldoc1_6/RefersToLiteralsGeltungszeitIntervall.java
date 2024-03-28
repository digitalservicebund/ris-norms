package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.geltungszeitIntervall.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.geltungszeitIntervall"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="geltungszeit"/&gt;
 *     &lt;enumeration value="aenderungsantrag"/&gt;
 *     &lt;enumeration value="beschlussempfehlung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.geltungszeitIntervall",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsGeltungszeitIntervall {
  @XmlEnumValue("geltungszeit")
  GELTUNGSZEIT("geltungszeit"),
  @XmlEnumValue("aenderungsantrag")
  AENDERUNGSANTRAG("aenderungsantrag"),
  @XmlEnumValue("beschlussempfehlung")
  BESCHLUSSEMPFEHLUNG("beschlussempfehlung");
  private final String value;

  RefersToLiteralsGeltungszeitIntervall(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsGeltungszeitIntervall fromValue(String v) {
    for (RefersToLiteralsGeltungszeitIntervall c : RefersToLiteralsGeltungszeitIntervall.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
