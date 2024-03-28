package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for typeLiterals.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="typeLiterals"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="generation"/&gt;
 *     &lt;enumeration value="amendment"/&gt;
 *     &lt;enumeration value="repeal"/&gt;
 *     &lt;enumeration value="substitution"/&gt;
 *     &lt;enumeration value="insertion"/&gt;
 *     &lt;enumeration value="replacement"/&gt;
 *     &lt;enumeration value="renumbering"/&gt;
 *     &lt;enumeration value="split"/&gt;
 *     &lt;enumeration value="join"/&gt;
 *     &lt;enumeration value="entryIntoForce"/&gt;
 *     &lt;enumeration value="endOfEnactment"/&gt;
 *     &lt;enumeration value="prorogationOfForce"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "typeLiterals", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum TypeLiterals {
  @XmlEnumValue("generation")
  GENERATION("generation"),
  @XmlEnumValue("amendment")
  AMENDMENT("amendment"),
  @XmlEnumValue("repeal")
  REPEAL("repeal"),
  @XmlEnumValue("substitution")
  SUBSTITUTION("substitution"),
  @XmlEnumValue("insertion")
  INSERTION("insertion"),
  @XmlEnumValue("replacement")
  REPLACEMENT("replacement"),
  @XmlEnumValue("renumbering")
  RENUMBERING("renumbering"),
  @XmlEnumValue("split")
  SPLIT("split"),
  @XmlEnumValue("join")
  JOIN("join"),
  @XmlEnumValue("entryIntoForce")
  ENTRY_INTO_FORCE("entryIntoForce"),
  @XmlEnumValue("endOfEnactment")
  END_OF_ENACTMENT("endOfEnactment"),
  @XmlEnumValue("prorogationOfForce")
  PROROGATION_OF_FORCE("prorogationOfForce");
  private final String value;

  TypeLiterals(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static TypeLiterals fromValue(String v) {
    for (TypeLiterals c : TypeLiterals.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
