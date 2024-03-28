package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for nameLiterals.bekanntmachungDokumentenkopfContainer.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="nameLiterals.bekanntmachungDokumentenkopfContainer"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals"&gt;
 *     &lt;enumeration value="attributsemantik-noch-undefiniert"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "nameLiterals.bekanntmachungDokumentenkopfContainer",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum NameLiteralsBekanntmachungDokumentenkopfContainer {
  @XmlEnumValue("attributsemantik-noch-undefiniert")
  ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT("attributsemantik-noch-undefiniert");
  private final String value;

  NameLiteralsBekanntmachungDokumentenkopfContainer(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NameLiteralsBekanntmachungDokumentenkopfContainer fromValue(String v) {
    for (NameLiteralsBekanntmachungDokumentenkopfContainer c :
        NameLiteralsBekanntmachungDokumentenkopfContainer.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
