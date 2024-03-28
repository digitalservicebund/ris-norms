package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for typeLiterals.geltungszeitaenderung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="typeLiterals.geltungszeitaenderung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals"&gt;
 *     &lt;enumeration value="entryIntoForce"/&gt;
 *     &lt;enumeration value="endOfEnactment"/&gt;
 *     &lt;enumeration value="prorogationOfForce"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "typeLiterals.geltungszeitaenderung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(TypeLiterals.class)
public enum TypeLiteralsGeltungszeitaenderung {
  @XmlEnumValue("entryIntoForce")
  ENTRY_INTO_FORCE(TypeLiterals.ENTRY_INTO_FORCE),
  @XmlEnumValue("endOfEnactment")
  END_OF_ENACTMENT(TypeLiterals.END_OF_ENACTMENT),
  @XmlEnumValue("prorogationOfForce")
  PROROGATION_OF_FORCE(TypeLiterals.PROROGATION_OF_FORCE);
  private final TypeLiterals value;

  TypeLiteralsGeltungszeitaenderung(TypeLiterals v) {
    value = v;
  }

  public TypeLiterals value() {
    return value;
  }

  public static TypeLiteralsGeltungszeitaenderung fromValue(TypeLiterals v) {
    for (TypeLiteralsGeltungszeitaenderung c : TypeLiteralsGeltungszeitaenderung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
