package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.rechtsetzungsdokument.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.rechtsetzungsdokument"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="rechtsetzungsdokument-entwurfsfassung"/&gt;
 *     &lt;enumeration value="rechtsetzungsdokument-verkuendungsfassung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.rechtsetzungsdokument",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsRechtsetzungsdokument {
  @XmlEnumValue("rechtsetzungsdokument-entwurfsfassung")
  RECHTSETZUNGSDOKUMENT_ENTWURFSFASSUNG("rechtsetzungsdokument-entwurfsfassung"),
  @XmlEnumValue("rechtsetzungsdokument-verkuendungsfassung")
  RECHTSETZUNGSDOKUMENT_VERKUENDUNGSFASSUNG("rechtsetzungsdokument-verkuendungsfassung");
  private final String value;

  NameLiteralsRechtsetzungsdokument(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsRechtsetzungsdokument fromValue(String v) {
    for (NameLiteralsRechtsetzungsdokument c : NameLiteralsRechtsetzungsdokument.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
