package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for typeLiterals.textaenderung.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="typeLiterals.textaenderung"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals"&gt;
 *     &lt;enumeration value="repeal"/&gt;
 *     &lt;enumeration value="substitution"/&gt;
 *     &lt;enumeration value="insertion"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "typeLiterals.textaenderung", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(TypeLiterals.class)
public enum TypeLiteralsTextaenderung {
  @XmlEnumValue("repeal")
  REPEAL(TypeLiterals.REPEAL),
  @XmlEnumValue("substitution")
  SUBSTITUTION(TypeLiterals.SUBSTITUTION),
  @XmlEnumValue("insertion")
  INSERTION(TypeLiterals.INSERTION);
  private final TypeLiterals value;

  TypeLiteralsTextaenderung(TypeLiterals v) {
    value = v;
  }

  public TypeLiterals value() {
    return value;
  }

  public static TypeLiteralsTextaenderung fromValue(TypeLiterals v) {
    for (TypeLiteralsTextaenderung c : TypeLiteralsTextaenderung.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
