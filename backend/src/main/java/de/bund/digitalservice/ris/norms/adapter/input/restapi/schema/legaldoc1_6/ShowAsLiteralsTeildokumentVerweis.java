package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for showAsLiterals.teildokumentVerweis.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="showAsLiterals.teildokumentVerweis"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}showAsLiterals"&gt;
 *     &lt;enumeration value="beschlussempfehlung"/&gt;
 *     &lt;enumeration value="vorblatt-beschlussempfehlung"/&gt;
 *     &lt;enumeration value="bericht"/&gt;
 *     &lt;enumeration value="aenderungsantrag"/&gt;
 *     &lt;enumeration value="entschliessungsantrag"/&gt;
 *     &lt;enumeration value="begruendung-antrag"/&gt;
 *     &lt;enumeration value="unterrichtung"/&gt;
 *     &lt;enumeration value="regelungstext"/&gt;
 *     &lt;enumeration value="vorblatt"/&gt;
 *     &lt;enumeration value="anschreiben"/&gt;
 *     &lt;enumeration value="denkschrift"/&gt;
 *     &lt;enumeration value="offene-struktur"/&gt;
 *     &lt;enumeration value="externes-dokument"/&gt;
 *     &lt;enumeration value="begruendung"/&gt;
 *     &lt;enumeration value="vereinbarung"/&gt;
 *     &lt;enumeration value="bekanntmachungstext"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "showAsLiterals.teildokumentVerweis",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum ShowAsLiteralsTeildokumentVerweis {
  @XmlEnumValue("beschlussempfehlung")
  BESCHLUSSEMPFEHLUNG("beschlussempfehlung"),
  @XmlEnumValue("vorblatt-beschlussempfehlung")
  VORBLATT_BESCHLUSSEMPFEHLUNG("vorblatt-beschlussempfehlung"),
  @XmlEnumValue("bericht")
  BERICHT("bericht"),
  @XmlEnumValue("aenderungsantrag")
  AENDERUNGSANTRAG("aenderungsantrag"),
  @XmlEnumValue("entschliessungsantrag")
  ENTSCHLIESSUNGSANTRAG("entschliessungsantrag"),
  @XmlEnumValue("begruendung-antrag")
  BEGRUENDUNG_ANTRAG("begruendung-antrag"),
  @XmlEnumValue("unterrichtung")
  UNTERRICHTUNG("unterrichtung"),
  @XmlEnumValue("regelungstext")
  REGELUNGSTEXT("regelungstext"),
  @XmlEnumValue("vorblatt")
  VORBLATT("vorblatt"),
  @XmlEnumValue("anschreiben")
  ANSCHREIBEN("anschreiben"),
  @XmlEnumValue("denkschrift")
  DENKSCHRIFT("denkschrift"),
  @XmlEnumValue("offene-struktur")
  OFFENE_STRUKTUR("offene-struktur"),
  @XmlEnumValue("externes-dokument")
  EXTERNES_DOKUMENT("externes-dokument"),
  @XmlEnumValue("begruendung")
  BEGRUENDUNG("begruendung"),
  @XmlEnumValue("vereinbarung")
  VEREINBARUNG("vereinbarung"),
  @XmlEnumValue("bekanntmachungstext")
  BEKANNTMACHUNGSTEXT("bekanntmachungstext");
  private final String value;

  ShowAsLiteralsTeildokumentVerweis(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ShowAsLiteralsTeildokumentVerweis fromValue(String v) {
    for (ShowAsLiteralsTeildokumentVerweis c : ShowAsLiteralsTeildokumentVerweis.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
