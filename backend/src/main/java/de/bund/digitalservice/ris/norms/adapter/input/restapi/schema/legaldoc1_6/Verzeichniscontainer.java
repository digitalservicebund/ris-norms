package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "verzeichniscontainer" wird genutzt, um innerhalb eines Regelungstextes ein
 * Strukturelement für Verzeichnisse beizufügen. Die Darstellung spezifischer Verzeichniscontainer
 * erfolgt ausschließlich durch die Zuordnung von @refersTo-Attributen.
 *
 * <p>Java class for verzeichniscontainer complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="verzeichniscontainer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="heading" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ueberschrift"/&gt;
 *         &lt;element name="toc" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}inhaltsuebersicht"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="refersTo" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.verzeichniscontainer" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.verzeichniscontainer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "verzeichniscontainer",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"heading", "toc"})
public class Verzeichniscontainer {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Ueberschrift heading;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Inhaltsuebersicht toc;

  @XmlAttribute(name = "refersTo", required = true)
  protected RefersToLiteralsVerzeichniscontainer refersTo;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the heading property.
   *
   * @return possible object is {@link Ueberschrift }
   */
  public Ueberschrift getHeading() {
    return heading;
  }

  /**
   * Sets the value of the heading property.
   *
   * @param value allowed object is {@link Ueberschrift }
   */
  public void setHeading(Ueberschrift value) {
    this.heading = value;
  }

  /**
   * Gets the value of the toc property.
   *
   * @return possible object is {@link Inhaltsuebersicht }
   */
  public Inhaltsuebersicht getToc() {
    return toc;
  }

  /**
   * Sets the value of the toc property.
   *
   * @param value allowed object is {@link Inhaltsuebersicht }
   */
  public void setToc(Inhaltsuebersicht value) {
    this.toc = value;
  }

  /**
   * Gets the value of the refersTo property.
   *
   * @return possible object is {@link RefersToLiteralsVerzeichniscontainer }
   */
  public RefersToLiteralsVerzeichniscontainer getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsVerzeichniscontainer }
   */
  public void setRefersTo(RefersToLiteralsVerzeichniscontainer value) {
    this.refersTo = value;
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
