package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.begruendungAntrag.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.begruendungAntrag"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="begruendung-aenderungsantrag"/&gt;
 *     &lt;enumeration value="begruendung-entschliessungsantrag"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.begruendungAntrag",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsBegruendungAntrag {
  @XmlEnumValue("begruendung-aenderungsantrag")
  BEGRUENDUNG_AENDERUNGSANTRAG("begruendung-aenderungsantrag"),
  @XmlEnumValue("begruendung-entschliessungsantrag")
  BEGRUENDUNG_ENTSCHLIESSUNGSANTRAG("begruendung-entschliessungsantrag");
  private final String value;

  NameLiteralsBegruendungAntrag(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsBegruendungAntrag fromValue(String v) {
    for (NameLiteralsBegruendungAntrag c : NameLiteralsBegruendungAntrag.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
