package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.vorblattBeschlussempfehlung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.vorblattBeschlussempfehlung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="vorblatt-beschlussempfehlung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.vorblattBeschlussempfehlung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsVorblattBeschlussempfehlung {
  @XmlEnumValue("vorblatt-beschlussempfehlung")
  VORBLATT_BESCHLUSSEMPFEHLUNG("vorblatt-beschlussempfehlung");
  private final String value;

  NameLiteralsVorblattBeschlussempfehlung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsVorblattBeschlussempfehlung fromValue(String v) {
    for (NameLiteralsVorblattBeschlussempfehlung c :
        NameLiteralsVorblattBeschlussempfehlung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
