package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "work.FRBRcountry" wird benutzt, um auf der Work-Ebene denjenigen Staat anzugeben, der
 * für ein Rechtsetzungsdokument zuständig ist. Die Angabe erfolgt mitels eines Codes gemäß ISO
 * 3166-1 Alpha-2.
 *
 * <p>Java class for work.FRBRcountry complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="work.FRBRcountry"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="value" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}valueLiterals.work.FRBRcountry" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.work.FRBRcountry" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "work.FRBRcountry", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class WorkFRBRcountry {

  @XmlAttribute(name = "value", required = true)
  protected ValueLiteralsWorkFRBRcountry value;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the value property.
   *
   * @return possible object is {@link ValueLiteralsWorkFRBRcountry }
   */
  public ValueLiteralsWorkFRBRcountry getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   *
   * @param value allowed object is {@link ValueLiteralsWorkFRBRcountry }
   */
  public void setValue(ValueLiteralsWorkFRBRcountry value) {
    this.value = value;
  }

  /**
   * Gets the value of the eId property.
   *
   * @return possible object is {@link String }
   */
  public String getEId() {
    return eId;
  }

  /**
   * Sets the value of the eId property.
   *
   * @param value allowed object is {@link String }
   */
  public void setEId(String value) {
    this.eId = value;
  }

  /**
   * Gets the value of the guid property.
   *
   * @return possible object is {@link String }
   */
  public String getGUID() {
    return guid;
  }

  /**
   * Sets the value of the guid property.
   *
   * @param value allowed object is {@link String }
   */
  public void setGUID(String value) {
    this.guid = value;
  }
}
