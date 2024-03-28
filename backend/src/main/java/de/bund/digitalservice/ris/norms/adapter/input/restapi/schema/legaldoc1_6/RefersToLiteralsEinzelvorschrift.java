package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for refersToLiterals.einzelvorschrift.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="refersToLiterals.einzelvorschrift"&gt;
 *   &lt;restriction base="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals"&gt;
 *     &lt;enumeration value="stammform"/&gt;
 *     &lt;enumeration value="mantelform"/&gt;
 *     &lt;enumeration value="eingebundene-stammform"/&gt;
 *     &lt;enumeration value="geltungszeitregel"/&gt;
 *     &lt;enumeration value="vertragsgesetz"/&gt;
 *     &lt;enumeration value="vertragsverordnung"/&gt;
 *     &lt;enumeration value="hauptaenderung"/&gt;
 *     &lt;enumeration value="folgeaenderung"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(
    name = "refersToLiterals.einzelvorschrift",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum RefersToLiteralsEinzelvorschrift {
  @XmlEnumValue("stammform")
  STAMMFORM("stammform"),
  @XmlEnumValue("mantelform")
  MANTELFORM("mantelform"),
  @XmlEnumValue("eingebundene-stammform")
  EINGEBUNDENE_STAMMFORM("eingebundene-stammform"),
  @XmlEnumValue("geltungszeitregel")
  GELTUNGSZEITREGEL("geltungszeitregel"),
  @XmlEnumValue("vertragsgesetz")
  VERTRAGSGESETZ("vertragsgesetz"),
  @XmlEnumValue("vertragsverordnung")
  VERTRAGSVERORDNUNG("vertragsverordnung"),
  @XmlEnumValue("hauptaenderung")
  HAUPTAENDERUNG("hauptaenderung"),
  @XmlEnumValue("folgeaenderung")
  FOLGEAENDERUNG("folgeaenderung");
  private final String value;

  RefersToLiteralsEinzelvorschrift(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RefersToLiteralsEinzelvorschrift fromValue(String v) {
    for (RefersToLiteralsEinzelvorschrift c : RefersToLiteralsEinzelvorschrift.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
