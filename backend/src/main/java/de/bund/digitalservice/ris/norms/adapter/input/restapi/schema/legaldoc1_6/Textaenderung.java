package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "textaenderung" wird benutzt, um die Metadaten zu einem Änderungsbefehl abzubilden.
 *
 * <p>Java class for textaenderung complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="textaenderung"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="source" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungUrsprung"/&gt;
 *         &lt;element name="destination" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungZielelement" maxOccurs="unbounded"/&gt;
 *         &lt;element name="force" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}geltungszeitverweis"/&gt;
 *         &lt;element name="new" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungNeu" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="type" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals.textaenderung" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="status" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}statusLiterals.textaenderung" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.textaenderung" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "textaenderung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"source", "destination", "force", "_new"})
public class Textaenderung {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected AenderungUrsprung source;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<AenderungZielelement> destination;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Geltungszeitverweis force;

  @XmlElement(name = "new", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected AenderungNeu _new;

  @XmlAttribute(name = "type", required = true)
  protected TypeLiteralsTextaenderung type;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "status")
  protected StatusLiteralsTextaenderung status;

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
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the destination property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getDestination().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link AenderungZielelement }
   */
  public List<AenderungZielelement> getDestination() {
    if (destination == null) {
      destination = new ArrayList<AenderungZielelement>();
    }
    return this.destination;
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
   * Gets the value of the new property.
   *
   * @return possible object is {@link AenderungNeu }
   */
  public AenderungNeu getNew() {
    return _new;
  }

  /**
   * Sets the value of the new property.
   *
   * @param value allowed object is {@link AenderungNeu }
   */
  public void setNew(AenderungNeu value) {
    this._new = value;
  }

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link TypeLiteralsTextaenderung }
   */
  public TypeLiteralsTextaenderung getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link TypeLiteralsTextaenderung }
   */
  public void setType(TypeLiteralsTextaenderung value) {
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
   * Gets the value of the status property.
   *
   * @return possible object is {@link StatusLiteralsTextaenderung }
   */
  public StatusLiteralsTextaenderung getStatus() {
    return status;
  }

  /**
   * Sets the value of the status property.
   *
   * @param value allowed object is {@link StatusLiteralsTextaenderung }
   */
  public void setStatus(StatusLiteralsTextaenderung value) {
    this.status = value;
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
