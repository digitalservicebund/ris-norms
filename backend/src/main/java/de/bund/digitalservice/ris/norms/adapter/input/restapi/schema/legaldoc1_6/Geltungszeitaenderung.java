package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "geltungszeitaenderung" wird benutzt, um die Metadaten zu einem Änderungsbefehl,
 * welcher Geltungszeiten ändert, abzubilden.
 *
 * <p>Java class for geltungszeitaenderung complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="geltungszeitaenderung"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="source" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungUrsprung"/&gt;
 *         &lt;element name="destination" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungZielelement"/&gt;
 *         &lt;element name="force" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}geltungszeitverweis"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="type" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals.geltungszeitaenderung" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.geltungszeitaenderung" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "geltungszeitaenderung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"source", "destination", "force"})
public class Geltungszeitaenderung {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected AenderungUrsprung source;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected AenderungZielelement destination;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Geltungszeitverweis force;

  @XmlAttribute(name = "type", required = true)
  protected TypeLiteralsGeltungszeitaenderung type;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link AenderungUrsprung }
   */
  public AenderungUrsprung getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link AenderungUrsprung }
   */
  public void setSource(AenderungUrsprung value) {
    this.source = value;
  }

  /**
   * Gets the value of the destination property.
   *
   * @return possible object is {@link AenderungZielelement }
   */
  public AenderungZielelement getDestination() {
    return destination;
  }

  /**
   * Sets the value of the destination property.
   *
   * @param value allowed object is {@link AenderungZielelement }
   */
  public void setDestination(AenderungZielelement value) {
    this.destination = value;
  }

  /**
   * Gets the value of the force property.
   *
   * @return possible object is {@link Geltungszeitverweis }
   */
  public Geltungszeitverweis getForce() {
    return force;
  }

  /**
   * Sets the value of the force property.
   *
   * @param value allowed object is {@link Geltungszeitverweis }
   */
  public void setForce(Geltungszeitverweis value) {
    this.force = value;
  }

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link TypeLiteralsGeltungszeitaenderung }
   */
  public TypeLiteralsGeltungszeitaenderung getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link TypeLiteralsGeltungszeitaenderung }
   */
  public void setType(TypeLiteralsGeltungszeitaenderung value) {
    this.type = value;
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
