package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "aenderungZielelement" wird benutzt, um eindeutig anzugeben, an welchem Zielelement
 * eines Rechtsetzungsdokuments eine Änderung vorgenommen werden soll, die durch den dazugehörigen
 * Änderungsbefehl formuliert wurde.
 *
 * <p>Java class for aenderungZielelement complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="aenderungZielelement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="pos" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}posLiterals" /&gt;
 *       &lt;attribute name="upTo" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}upToLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.aenderungZielelement" /&gt;
 *       &lt;attribute name="href" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}hrefLiterals" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aenderungZielelement", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class AenderungZielelement {

  @XmlAttribute(name = "pos")
  protected PosLiterals pos;

  @XmlAttribute(name = "upTo")
  protected String upTo;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "href", required = true)
  protected String href;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the pos property.
   *
   * @return possible object is {@link PosLiterals }
   */
  public PosLiterals getPos() {
    return pos;
  }

  /**
   * Sets the value of the pos property.
   *
   * @param value allowed object is {@link PosLiterals }
   */
  public void setPos(PosLiterals value) {
    this.pos = value;
  }

  /**
   * Gets the value of the upTo property.
   *
   * @return possible object is {@link String }
   */
  public String getUpTo() {
    return upTo;
  }

  /**
   * Sets the value of the upTo property.
   *
   * @param value allowed object is {@link String }
   */
  public void setUpTo(String value) {
    this.upTo = value;
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
   * Gets the value of the href property.
   *
   * @return possible object is {@link String }
   */
  public String getHref() {
    return href;
  }

  /**
   * Sets the value of the href property.
   *
   * @param value allowed object is {@link String }
   */
  public void setHref(String value) {
    this.href = value;
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
