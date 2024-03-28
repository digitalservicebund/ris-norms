package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="act" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextVerkuendungsfassung"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"act"})
@XmlRootElement(name = "akomaNtoso", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class AkomaNtoso {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected RegelungstextVerkuendungsfassung act;

  /**
   * Gets the value of the act property.
   *
   * @return possible object is {@link RegelungstextVerkuendungsfassung }
   */
  public RegelungstextVerkuendungsfassung getAct() {
    return act;
  }

  /**
   * Sets the value of the act property.
   *
   * @param value allowed object is {@link RegelungstextVerkuendungsfassung }
   */
  public void setAct(RegelungstextVerkuendungsfassung value) {
    this.act = value;
  }
}
