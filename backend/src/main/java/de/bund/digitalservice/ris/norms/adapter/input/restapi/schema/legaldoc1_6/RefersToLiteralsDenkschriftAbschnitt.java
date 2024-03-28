package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.denkschriftAbschnitt.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.denkschriftAbschnitt"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="denkschrift-abschnitt-allgemeines"/&gt;
 *     &lt;enumeration value="denkschrift-abschnitt-besonderes"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.denkschriftAbschnitt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsDenkschriftAbschnitt {
  @XmlEnumValue("denkschrift-abschnitt-allgemeines")
  DENKSCHRIFT_ABSCHNITT_ALLGEMEINES("denkschrift-abschnitt-allgemeines"),
  @XmlEnumValue("denkschrift-abschnitt-besonderes")
  DENKSCHRIFT_ABSCHNITT_BESONDERES("denkschrift-abschnitt-besonderes");
  private final String value;

  RefersToLiteralsDenkschriftAbschnitt(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsDenkschriftAbschnitt fromValue(String v) {
    for (RefersToLiteralsDenkschriftAbschnitt c : RefersToLiteralsDenkschriftAbschnitt.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
