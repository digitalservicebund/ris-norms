package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.vereinbarungVertRAHauptteil.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.vereinbarungVertRAHauptteil"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="vertrag"/&gt;
 *     &lt;enumeration value="fakultativprotokoll"/&gt;
 *     &lt;enumeration value="notenwechsel"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.vereinbarungVertRAHauptteil",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsVereinbarungVertRAHauptteil {
  @XmlEnumValue("vertrag")
  VERTRAG("vertrag"),
  @XmlEnumValue("fakultativprotokoll")
  FAKULTATIVPROTOKOLL("fakultativprotokoll"),
  @XmlEnumValue("notenwechsel")
  NOTENWECHSEL("notenwechsel");
  private final String value;

  RefersToLiteralsVereinbarungVertRAHauptteil(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsVereinbarungVertRAHauptteil fromValue(String v) {
    for (RefersToLiteralsVereinbarungVertRAHauptteil c :
        RefersToLiteralsVereinbarungVertRAHauptteil.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
