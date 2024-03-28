package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for typeLiterals.ereignisReferenz.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="typeLiterals.ereignisReferenz"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals"&gt;
 *     &lt;enumeration value="generation"/&gt;
 *     &lt;enumeration value="amendment"/&gt;
 *     &lt;enumeration value="repeal"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "typeLiterals.ereignisReferenz",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(TypeLiterals.class)
public enum TypeLiteralsEreignisReferenz {
  @XmlEnumValue("generation")
  GENERATION(TypeLiterals.GENERATION),
  @XmlEnumValue("amendment")
  AMENDMENT(TypeLiterals.AMENDMENT),
  @XmlEnumValue("repeal")
  REPEAL(TypeLiterals.REPEAL);
  private final TypeLiterals value;

  TypeLiteralsEreignisReferenz(TypeLiterals v) {
    value = v;
  }

  public TypeLiterals value() {
    return value;
  }

  public static TypeLiteralsEreignisReferenz fromValue(TypeLiterals v) {
    for (TypeLiteralsEreignisReferenz c : TypeLiteralsEreignisReferenz.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
