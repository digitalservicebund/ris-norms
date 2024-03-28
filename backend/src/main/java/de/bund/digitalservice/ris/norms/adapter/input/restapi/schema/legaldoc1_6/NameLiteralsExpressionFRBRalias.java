package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.expression.FRBRalias.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.expression.FRBRalias"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="vorherige-version-id"/&gt;
 *     &lt;enumeration value="aktuelle-version-id"/&gt;
 *     &lt;enumeration value="nachfolgende-version-id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.expression.FRBRalias",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsExpressionFRBRalias {
  @XmlEnumValue("vorherige-version-id")
  VORHERIGE_VERSION_ID("vorherige-version-id"),
  @XmlEnumValue("aktuelle-version-id")
  AKTUELLE_VERSION_ID("aktuelle-version-id"),
  @XmlEnumValue("nachfolgende-version-id")
  NACHFOLGENDE_VERSION_ID("nachfolgende-version-id");
  private final String value;

  NameLiteralsExpressionFRBRalias(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsExpressionFRBRalias fromValue(String v) {
    for (NameLiteralsExpressionFRBRalias c : NameLiteralsExpressionFRBRalias.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
