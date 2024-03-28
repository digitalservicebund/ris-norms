package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for showAsLiterals.anlagenverweis.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="showAsLiterals.anlagenverweis"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}showAsLiterals"&gt;
 *     &lt;enumeration value="regelungstext"/&gt;
 *     &lt;enumeration value="vorblatt"/&gt;
 *     &lt;enumeration value="begruendung"/&gt;
 *     &lt;enumeration value="anschreiben"/&gt;
 *     &lt;enumeration value="vereinbarung"/&gt;
 *     &lt;enumeration value="denkschrift"/&gt;
 *     &lt;enumeration value="bekanntmachungstext"/&gt;
 *     &lt;enumeration value="offene-struktur"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "showAsLiterals.anlagenverweis",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum ShowAsLiteralsAnlagenverweis {
  @XmlEnumValue("regelungstext")
  REGELUNGSTEXT("regelungstext"),
  @XmlEnumValue("vorblatt")
  VORBLATT("vorblatt"),
  @XmlEnumValue("begruendung")
  BEGRUENDUNG("begruendung"),
  @XmlEnumValue("anschreiben")
  ANSCHREIBEN("anschreiben"),
  @XmlEnumValue("vereinbarung")
  VEREINBARUNG("vereinbarung"),
  @XmlEnumValue("denkschrift")
  DENKSCHRIFT("denkschrift"),
  @XmlEnumValue("bekanntmachungstext")
  BEKANNTMACHUNGSTEXT("bekanntmachungstext"),
  @XmlEnumValue("offene-struktur")
  OFFENE_STRUKTUR("offene-struktur");
  private final String value;

  ShowAsLiteralsAnlagenverweis(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ShowAsLiteralsAnlagenverweis fromValue(String v) {
    for (ShowAsLiteralsAnlagenverweis c : ShowAsLiteralsAnlagenverweis.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
