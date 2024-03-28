package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Die Klasse "ereignisReferenz" wird genutzt, um spezifische Ereignisse zu erfassen, die das
 * vorliegenden Dokument verändert haben.
 *
 * <p>Java class for ereignisReferenz complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ereignisReferenz"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="date" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}dateLiterals.ereignisReferenz" /&gt;
 *       &lt;attribute name="source" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals.ereignisReferenz" /&gt;
 *       &lt;attribute name="refersTo" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.ereignisReferenz" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}typeLiterals.ereignisReferenz" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.ereignisReferenz" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ereignisReferenz", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class EreignisReferenz {

  @XmlAttribute(name = "date", required = true)
  protected XMLGregorianCalendar date;

  @XmlAttribute(name = "source", required = true)
  protected SourceLiteralsEreignisReferenz source;

  @XmlAttribute(name = "refersTo", required = true)
  protected RefersToLiteralsEreignisReferenz refersTo;

  @XmlAttribute(name = "type", required = true)
  protected TypeLiteralsEreignisReferenz type;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the date property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   */
  public XMLGregorianCalendar getDate() {
    return date;
  }

  /**
   * Sets the value of the date property.
   *
   * @param value allowed object is {@link XMLGregorianCalendar }
   */
  public void setDate(XMLGregorianCalendar value) {
    this.date = value;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link SourceLiteralsEreignisReferenz }
   */
  public SourceLiteralsEreignisReferenz getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link SourceLiteralsEreignisReferenz }
   */
  public void setSource(SourceLiteralsEreignisReferenz value) {
    this.source = value;
  }

  /**
   * Gets the value of the refersTo property.
   *
   * @return possible object is {@link RefersToLiteralsEreignisReferenz }
   */
  public RefersToLiteralsEreignisReferenz getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsEreignisReferenz }
   */
  public void setRefersTo(RefersToLiteralsEreignisReferenz value) {
    this.refersTo = value;
  }

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link TypeLiteralsEreignisReferenz }
   */
  public TypeLiteralsEreignisReferenz getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link TypeLiteralsEreignisReferenz }
   */
  public void setType(TypeLiteralsEreignisReferenz value) {
    this.type = value;
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
