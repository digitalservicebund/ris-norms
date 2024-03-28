package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "einzelvorschrift" wird genutzt, um innerhalb eines Regelungstextes eine
 * Einzelvorschrift einzufügen. Im Sinne der Modellierung von LegalDocML.de wird der Begriff
 * "einzelvorschrift" weiter gefasst, als dies im HdR der Fall ist. Die Klasse umfasst sowohl
 * Einzelvorschriften eines Stammrechtsaktes (üblicherweise in Form von Paragraphen) als auch
 * Einzelvorschriften eines Mantelrechtsaktes (üblicherweise in Form von Artikeln). Auf Ebene des
 * Schemas werden keine spezifischen Formen von Einzelvorschriften unterschieden, da diese
 * strukturell identisch aufgebaut sind. Die Darstellung spezifischer Einzelvorschriften erfolgt
 * ausschließlich durch die Zuordnung von @refersTo-Attributen.
 *
 * <p>Java class for einzelvorschrift complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="einzelvorschrift"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="num" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}artUndZaehlbezeichnung"/&gt;
 *         &lt;element name="heading" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ueberschrift" minOccurs="0"/&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}einzelvorschriftInhalt"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="period" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}periodLiterals.einzelvorschrift" /&gt;
 *       &lt;attribute name="refersTo" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.einzelvorschrift" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.einzelvorschrift" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "einzelvorschrift",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"num", "heading", "componentRef", "paragraph"})
public class Einzelvorschrift {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected ArtUndZaehlbezeichnung num;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Ueberschrift heading;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected StammformVerweis componentRef;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<JuristischerAbsatz> paragraph;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "period", required = true)
  protected String period;

  @XmlAttribute(name = "refersTo")
  protected RefersToLiteralsEinzelvorschrift refersTo;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the num property.
   *
   * @return possible object is {@link ArtUndZaehlbezeichnung }
   */
  public ArtUndZaehlbezeichnung getNum() {
    return num;
  }

  /**
   * Sets the value of the num property.
   *
   * @param value allowed object is {@link ArtUndZaehlbezeichnung }
   */
  public void setNum(ArtUndZaehlbezeichnung value) {
    this.num = value;
  }

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
   * Gets the value of the componentRef property.
   *
   * @return possible object is {@link StammformVerweis }
   */
  public StammformVerweis getComponentRef() {
    return componentRef;
  }

  /**
   * Sets the value of the componentRef property.
   *
   * @param value allowed object is {@link StammformVerweis }
   */
  public void setComponentRef(StammformVerweis value) {
    this.componentRef = value;
  }

  /**
   * Gets the value of the paragraph property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the paragraph property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getParagraph().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link JuristischerAbsatz }
   */
  public List<JuristischerAbsatz> getParagraph() {
    if (paragraph == null) {
      paragraph = new ArrayList<JuristischerAbsatz>();
    }
    return this.paragraph;
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
   * Gets the value of the period property.
   *
   * @return possible object is {@link String }
   */
  public String getPeriod() {
    return period;
  }

  /**
   * Sets the value of the period property.
   *
   * @param value allowed object is {@link String }
   */
  public void setPeriod(String value) {
    this.period = value;
  }

  /**
   * Gets the value of the refersTo property.
   *
   * @return possible object is {@link RefersToLiteralsEinzelvorschrift }
   */
  public RefersToLiteralsEinzelvorschrift getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsEinzelvorschrift }
   */
  public void setRefersTo(RefersToLiteralsEinzelvorschrift value) {
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
