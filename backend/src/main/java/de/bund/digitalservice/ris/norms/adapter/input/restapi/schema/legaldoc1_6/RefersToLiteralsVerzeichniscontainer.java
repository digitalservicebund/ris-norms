package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.verzeichniscontainer.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.verzeichniscontainer"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="inhaltsuebersicht"/&gt;
 *     &lt;enumeration value="anlagenuebersicht"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.verzeichniscontainer",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsVerzeichniscontainer {
  @XmlEnumValue("inhaltsuebersicht")
  INHALTSUEBERSICHT("inhaltsuebersicht"),
  @XmlEnumValue("anlagenuebersicht")
  ANLAGENUEBERSICHT("anlagenuebersicht");
  private final String value;

  RefersToLiteralsVerzeichniscontainer(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsVerzeichniscontainer fromValue(String v) {
    for (RefersToLiteralsVerzeichniscontainer c : RefersToLiteralsVerzeichniscontainer.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
