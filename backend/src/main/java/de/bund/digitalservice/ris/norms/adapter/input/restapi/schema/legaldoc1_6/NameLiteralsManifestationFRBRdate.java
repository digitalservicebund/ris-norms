package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.manifestation.FRBRdate.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.manifestation.FRBRdate"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="generierung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.manifestation.FRBRdate",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsManifestationFRBRdate {
  @XmlEnumValue("generierung")
  GENERIERUNG("generierung");
  private final String value;

  NameLiteralsManifestationFRBRdate(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsManifestationFRBRdate fromValue(String v) {
    for (NameLiteralsManifestationFRBRdate c : NameLiteralsManifestationFRBRdate.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
