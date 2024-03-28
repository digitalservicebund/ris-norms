package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for showAsLiterals.stammformVerweis.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="showAsLiterals.stammformVerweis"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}showAsLiterals"&gt;
 *     &lt;enumeration value="regelungstext-eingebundene-stammform"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "showAsLiterals.stammformVerweis",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum ShowAsLiteralsStammformVerweis {
  @XmlEnumValue("regelungstext-eingebundene-stammform")
  REGELUNGSTEXT_EINGEBUNDENE_STAMMFORM("regelungstext-eingebundene-stammform");
  private final String value;

  ShowAsLiteralsStammformVerweis(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ShowAsLiteralsStammformVerweis fromValue(String v) {
    for (ShowAsLiteralsStammformVerweis c : ShowAsLiteralsStammformVerweis.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
