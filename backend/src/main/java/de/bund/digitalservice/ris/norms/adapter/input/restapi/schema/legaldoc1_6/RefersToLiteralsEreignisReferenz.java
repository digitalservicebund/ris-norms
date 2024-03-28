package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.ereignisReferenz.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.ereignisReferenz"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="ausfertigung"/&gt;
 *     &lt;enumeration value="neufassung"/&gt;
 *     &lt;enumeration value="inkrafttreten"/&gt;
 *     &lt;enumeration value="ausserkrafttreten"/&gt;
 *     &lt;enumeration value="entfernt"/&gt;
 *     &lt;enumeration value="ausfertigung-mit-noch-unbekanntem-datum"/&gt;
 *     &lt;enumeration value="inkrafttreten-mit-noch-unbekanntem-datum"/&gt;
 *     &lt;enumeration value="ausserkrafttreten-mit-noch-unbekanntem-datum"/&gt;
 *     &lt;enumeration value="aenderungsantrag"/&gt;
 *     &lt;enumeration value="beschlussempfehlung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.ereignisReferenz",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsEreignisReferenz {
  @XmlEnumValue("ausfertigung")
  AUSFERTIGUNG("ausfertigung"),
  @XmlEnumValue("neufassung")
  NEUFASSUNG("neufassung"),
  @XmlEnumValue("inkrafttreten")
  INKRAFTTRETEN("inkrafttreten"),
  @XmlEnumValue("ausserkrafttreten")
  AUSSERKRAFTTRETEN("ausserkrafttreten"),
  @XmlEnumValue("entfernt")
  ENTFERNT("entfernt"),
  @XmlEnumValue("ausfertigung-mit-noch-unbekanntem-datum")
  AUSFERTIGUNG_MIT_NOCH_UNBEKANNTEM_DATUM("ausfertigung-mit-noch-unbekanntem-datum"),
  @XmlEnumValue("inkrafttreten-mit-noch-unbekanntem-datum")
  INKRAFTTRETEN_MIT_NOCH_UNBEKANNTEM_DATUM("inkrafttreten-mit-noch-unbekanntem-datum"),
  @XmlEnumValue("ausserkrafttreten-mit-noch-unbekanntem-datum")
  AUSSERKRAFTTRETEN_MIT_NOCH_UNBEKANNTEM_DATUM("ausserkrafttreten-mit-noch-unbekanntem-datum"),
  @XmlEnumValue("aenderungsantrag")
  AENDERUNGSANTRAG("aenderungsantrag"),
  @XmlEnumValue("beschlussempfehlung")
  BESCHLUSSEMPFEHLUNG("beschlussempfehlung");
  private final String value;

  RefersToLiteralsEreignisReferenz(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsEreignisReferenz fromValue(String v) {
    for (RefersToLiteralsEreignisReferenz c : RefersToLiteralsEreignisReferenz.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
