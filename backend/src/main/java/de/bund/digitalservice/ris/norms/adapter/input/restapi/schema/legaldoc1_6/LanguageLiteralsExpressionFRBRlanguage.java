package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for languageLiterals.expression.FRBRlanguage.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="languageLiterals.expression.FRBRlanguage"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}languageLiterals"&gt;
 *     &lt;enumeration value="deu"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "languageLiterals.expression.FRBRlanguage",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(LanguageLiterals.class)
public enum LanguageLiteralsExpressionFRBRlanguage {
  @XmlEnumValue("deu")
  DEU(LanguageLiterals.DEU);
  private final LanguageLiterals value;

  LanguageLiteralsExpressionFRBRlanguage(LanguageLiterals v) {
    value = v;
  }

  public LanguageLiterals value() {
    return value;
  }

  public static LanguageLiteralsExpressionFRBRlanguage fromValue(LanguageLiterals v) {
    for (LanguageLiteralsExpressionFRBRlanguage c :
        LanguageLiteralsExpressionFRBRlanguage.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
