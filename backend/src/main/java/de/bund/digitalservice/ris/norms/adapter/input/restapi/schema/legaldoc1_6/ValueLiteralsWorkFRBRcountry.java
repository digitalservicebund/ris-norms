package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for valueLiterals.work.FRBRcountry.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="valueLiterals.work.FRBRcountry"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}countryLiterals"&gt;
 *     &lt;enumeration value="de"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "valueLiterals.work.FRBRcountry",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum(CountryLiterals.class)
public enum ValueLiteralsWorkFRBRcountry {
  @XmlEnumValue("de")
  DE(CountryLiterals.DE);
  private final CountryLiterals value;

  ValueLiteralsWorkFRBRcountry(CountryLiterals v) {
    value = v;
  }

  public CountryLiterals value() {
    return value;
  }

  public static ValueLiteralsWorkFRBRcountry fromValue(CountryLiterals v) {
    for (ValueLiteralsWorkFRBRcountry c : ValueLiteralsWorkFRBRcountry.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }
}
