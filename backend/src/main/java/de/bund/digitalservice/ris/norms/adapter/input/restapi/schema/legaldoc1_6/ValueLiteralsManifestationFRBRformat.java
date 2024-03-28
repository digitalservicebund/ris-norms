package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for valueLiterals.manifestation.FRBRformat.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="valueLiterals.manifestation.FRBRformat"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}valueStringLiterals"&gt;
 *     &lt;enumeration value="xml"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "valueLiterals.manifestation.FRBRformat",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum ValueLiteralsManifestationFRBRformat {
  @XmlEnumValue("xml")
  XML("xml");
  private final String value;

  ValueLiteralsManifestationFRBRformat(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ValueLiteralsManifestationFRBRformat fromValue(String v) {
    for (ValueLiteralsManifestationFRBRformat c : ValueLiteralsManifestationFRBRformat.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
