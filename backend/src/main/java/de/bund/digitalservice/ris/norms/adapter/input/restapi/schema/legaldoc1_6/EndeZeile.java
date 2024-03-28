package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;

/**
 * Die Klasse "endeZeile" markiert einen Zeilenumbruch. Über die Attribute kann dieser spezifischer
 * ausgestaltet werden, z. B. falls die Zeile in einem Wort umbrechen soll.
 *
 * <p>Java class for endeZeile complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="endeZeile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="breakAt" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}breakAtLiterals" /&gt;
 *       &lt;attribute name="breakWith" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}breakWithLiterals" /&gt;
 *       &lt;attribute name="number" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}numberLiterals" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.endeZeile" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "endeZeile", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class EndeZeile {

  @XmlAttribute(name = "breakAt")
  protected BigInteger breakAt;

  @XmlAttribute(name = "breakWith")
  protected String breakWith;

  @XmlAttribute(name = "number")
  protected BigInteger number;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the breakAt property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getBreakAt() {
    return breakAt;
  }

  /**
   * Sets the value of the breakAt property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setBreakAt(BigInteger value) {
    this.breakAt = value;
  }

  /**
   * Gets the value of the breakWith property.
   *
   * @return possible object is {@link String }
   */
  public String getBreakWith() {
    return breakWith;
  }

  /**
   * Sets the value of the breakWith property.
   *
   * @param value allowed object is {@link String }
   */
  public void setBreakWith(String value) {
    this.breakWith = value;
  }

  /**
   * Gets the value of the number property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getNumber() {
    return number;
  }

  /**
   * Sets the value of the number property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setNumber(BigInteger value) {
    this.number = value;
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

  /**
   * Gets the value of the id property.
   *
   * @return possible object is {@link String }
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is {@link String }
   */
  public void setId(String value) {
    this.id = value;
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
}
