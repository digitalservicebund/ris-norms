package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.vereinbarungVertRA.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.vereinbarungVertRA"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="vereinbarung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.vereinbarungVertRA",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsVereinbarungVertRA {
  @XmlEnumValue("vereinbarung")
  VEREINBARUNG("vereinbarung");
  private final String value;

  NameLiteralsVereinbarungVertRA(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsVereinbarungVertRA fromValue(String v) {
    for (NameLiteralsVereinbarungVertRA c : NameLiteralsVereinbarungVertRA.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
