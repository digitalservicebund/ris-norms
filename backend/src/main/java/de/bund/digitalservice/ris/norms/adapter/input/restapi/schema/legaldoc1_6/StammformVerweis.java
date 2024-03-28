package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "stammformVerweis" wird benutzt, um innerhalb eines Regelungstextes in Mantelform eine
 * Stammform einzubinden.
 *
 * <p>Java class for stammformVerweis complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="stammformVerweis"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.stammformVerweis" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="src" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}srcLiterals" /&gt;
 *       &lt;attribute name="showAs" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}showAsLiterals.stammformVerweis" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stammformVerweis", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class StammformVerweis {

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "src", required = true)
  protected String src;

  @XmlAttribute(name = "showAs", required = true)
  protected ShowAsLiteralsStammformVerweis showAs;

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

  /**
   * Gets the value of the src property.
   *
   * @return possible object is {@link String }
   */
  public String getSrc() {
    return src;
  }

  /**
   * Sets the value of the src property.
   *
   * @param value allowed object is {@link String }
   */
  public void setSrc(String value) {
    this.src = value;
  }

  /**
   * Gets the value of the showAs property.
   *
   * @return possible object is {@link ShowAsLiteralsStammformVerweis }
   */
  public ShowAsLiteralsStammformVerweis getShowAs() {
    return showAs;
  }

  /**
   * Sets the value of the showAs property.
   *
   * @param value allowed object is {@link ShowAsLiteralsStammformVerweis }
   */
  public void setShowAs(ShowAsLiteralsStammformVerweis value) {
    this.showAs = value;
  }
}
