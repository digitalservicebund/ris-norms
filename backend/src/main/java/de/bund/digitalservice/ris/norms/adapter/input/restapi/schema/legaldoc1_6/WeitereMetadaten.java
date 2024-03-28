package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "weitereMetadaten" wird genutzt, um der XML-Instanz zusätzliche Metadaten beizufügen,
 * die von verschiedenen (Fach-) Anwendungen oder Schnittstellen benötigt werden.
 *
 * <p>Java class for weitereMetadaten complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="weitereMetadaten"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;any maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="source" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals.weitereMetadaten" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.weitereMetadaten" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "weitereMetadaten",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"any"})
public class WeitereMetadaten {

  @XmlAnyElement(lax = true)
  protected List<Object> any;

  @XmlAttribute(name = "source", required = true)
  protected SourceLiteralsWeitereMetadaten source;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the any property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the any property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getAny().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Object }
   */
  public List<Object> getAny() {
    if (any == null) {
      any = new ArrayList<Object>();
    }
    return this.any;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link SourceLiteralsWeitereMetadaten }
   */
  public SourceLiteralsWeitereMetadaten getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link SourceLiteralsWeitereMetadaten }
   */
  public void setSource(SourceLiteralsWeitereMetadaten value) {
    this.source = value;
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
