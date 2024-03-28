package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "signaturInhalt" bündelt mehrere Elemente, die für eine Signatur relevant sind,
 * darunter fallen Ort, Datum, Organisation, Funktionsbezeichnung und Personenname. Die Klasse
 * "signaturInhalt" wird nur bei nicht-normativen Dokumenten verwendet.
 *
 * <p>Java class for signaturInhalt complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="signaturInhalt"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="location" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ort" minOccurs="0"/&gt;
 *         &lt;element name="date" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}datum" minOccurs="0"/&gt;
 *         &lt;element name="organization" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}organisation" minOccurs="0"/&gt;
 *         &lt;element name="role" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}funktionsbezeichnung" minOccurs="0"/&gt;
 *         &lt;element name="person" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}personenname" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.signaturInhalt" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "signaturInhalt",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"content"})
public class SignaturInhalt {

  @XmlElementRefs({
    @XmlElementRef(
        name = "location",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "date",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "organization",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "role",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "person",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false)
  })
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "style")
  protected String style;

  /**
   * Die Klasse "signaturInhalt" bündelt mehrere Elemente, die für eine Signatur relevant sind,
   * darunter fallen Ort, Datum, Organisation, Funktionsbezeichnung und Personenname. Die Klasse
   * "signaturInhalt" wird nur bei nicht-normativen Dokumenten verwendet.Gets the value of the
   * content property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the content property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getContent().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link JAXBElement }{@code <}{@link
   * Datum }{@code >} {@link JAXBElement }{@code <}{@link Funktionsbezeichnung }{@code >} {@link
   * JAXBElement }{@code <}{@link Organisation }{@code >} {@link JAXBElement }{@code <}{@link Ort
   * }{@code >} {@link JAXBElement }{@code <}{@link Personenname }{@code >} {@link String }
   */
  public List<Serializable> getContent() {
    if (content == null) {
      content = new ArrayList<Serializable>();
    }
    return this.content;
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

  /**
   * Gets the value of the style property.
   *
   * @return possible object is {@link String }
   */
  public String getStyle() {
    return style;
  }

  /**
   * Sets the value of the style property.
   *
   * @param value allowed object is {@link String }
   */
  public void setStyle(String value) {
    this.style = value;
  }
}
