package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.datum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.datum"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="fristablauf-datum"/&gt;
 *     &lt;enumeration value="ausfertigung-datum"/&gt;
 *     &lt;enumeration value="ausfertigung-mit-noch-unbekanntem-datum"/&gt;
 *     &lt;enumeration value="zuleitung-datum"/&gt;
 *     &lt;enumeration value="inkrafttreten-datum"/&gt;
 *     &lt;enumeration value="inkrafttreten-mit-noch-unbekanntem-datum"/&gt;
 *     &lt;enumeration value="verkuendung-datum"/&gt;
 *     &lt;enumeration value="datum-ohne-lebenszyklusbezug"/&gt;
 *     &lt;enumeration value="ausserkrafttreten-datum-komplett"/&gt;
 *     &lt;enumeration value="ausserkrafttreten-mit-noch-unbekanntem-datum-komplett"/&gt;
 *     &lt;enumeration value="ausserkrafttreten-datum-teil"/&gt;
 *     &lt;enumeration value="ausserkrafttreten-mit-noch-unbekanntem-datum-teil"/&gt;
 *     &lt;enumeration value="sitzung-datum"/&gt;
 *     &lt;enumeration value="bericht-datum"/&gt;
 *     &lt;enumeration value="beschlussempfehlung-datum"/&gt;
 *     &lt;enumeration value="aenderungsantrag-datum"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "refersToLiterals.datum", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsDatum {
  @XmlEnumValue("fristablauf-datum")
  FRISTABLAUF_DATUM("fristablauf-datum"),
  @XmlEnumValue("ausfertigung-datum")
  AUSFERTIGUNG_DATUM("ausfertigung-datum"),
  @XmlEnumValue("ausfertigung-mit-noch-unbekanntem-datum")
  AUSFERTIGUNG_MIT_NOCH_UNBEKANNTEM_DATUM("ausfertigung-mit-noch-unbekanntem-datum"),
  @XmlEnumValue("zuleitung-datum")
  ZULEITUNG_DATUM("zuleitung-datum"),
  @XmlEnumValue("inkrafttreten-datum")
  INKRAFTTRETEN_DATUM("inkrafttreten-datum"),
  @XmlEnumValue("inkrafttreten-mit-noch-unbekanntem-datum")
  INKRAFTTRETEN_MIT_NOCH_UNBEKANNTEM_DATUM("inkrafttreten-mit-noch-unbekanntem-datum"),
  @XmlEnumValue("verkuendung-datum")
  VERKUENDUNG_DATUM("verkuendung-datum"),
  @XmlEnumValue("datum-ohne-lebenszyklusbezug")
  DATUM_OHNE_LEBENSZYKLUSBEZUG("datum-ohne-lebenszyklusbezug"),
  @XmlEnumValue("ausserkrafttreten-datum-komplett")
  AUSSERKRAFTTRETEN_DATUM_KOMPLETT("ausserkrafttreten-datum-komplett"),
  @XmlEnumValue("ausserkrafttreten-mit-noch-unbekanntem-datum-komplett")
  AUSSERKRAFTTRETEN_MIT_NOCH_UNBEKANNTEM_DATUM_KOMPLETT(
      "ausserkrafttreten-mit-noch-unbekanntem-datum-komplett"),
  @XmlEnumValue("ausserkrafttreten-datum-teil")
  AUSSERKRAFTTRETEN_DATUM_TEIL("ausserkrafttreten-datum-teil"),
  @XmlEnumValue("ausserkrafttreten-mit-noch-unbekanntem-datum-teil")
  AUSSERKRAFTTRETEN_MIT_NOCH_UNBEKANNTEM_DATUM_TEIL(
      "ausserkrafttreten-mit-noch-unbekanntem-datum-teil"),
  @XmlEnumValue("sitzung-datum")
  SITZUNG_DATUM("sitzung-datum"),
  @XmlEnumValue("bericht-datum")
  BERICHT_DATUM("bericht-datum"),
  @XmlEnumValue("beschlussempfehlung-datum")
  BESCHLUSSEMPFEHLUNG_DATUM("beschlussempfehlung-datum"),
  @XmlEnumValue("aenderungsantrag-datum")
  AENDERUNGSANTRAG_DATUM("aenderungsantrag-datum");
  private final String value;

  RefersToLiteralsDatum(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsDatum fromValue(String v) {
    for (RefersToLiteralsDatum c : RefersToLiteralsDatum.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
