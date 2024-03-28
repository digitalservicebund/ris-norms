package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for statusLiterals.textaenderung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="statusLiterals.textaenderung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}statusLiterals"&gt;
 *     &lt;enumeration value="removed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "statusLiterals.textaenderung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum StatusLiteralsTextaenderung {
  @XmlEnumValue("removed")
  REMOVED("removed");
  private final String value;

  StatusLiteralsTextaenderung(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static StatusLiteralsTextaenderung fromValue(String v) {
    for (StatusLiteralsTextaenderung c : StatusLiteralsTextaenderung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
