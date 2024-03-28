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
 * Die Klasse "li" wird verwendet, um einen Eintrag in eine Liste einzufügen.
 *
 * <p>Java class for li complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="li"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}HTMLblock" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="value" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}valueLiterals.li" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.li" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "li",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"content"})
public class Li {

  @XmlElementRefs({
    @XmlElementRef(
        name = "ol",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "ul",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "table",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "p",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false)
  })
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name = "value", required = true)
  protected String value;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Die Klasse "li" wird verwendet, um einen Eintrag in eine Liste einzufügen.Gets the value of the
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
   * Ol }{@code >} {@link JAXBElement }{@code <}{@link P }{@code >} {@link JAXBElement }{@code
   * <}{@link Table }{@code >} {@link JAXBElement }{@code <}{@link Ul }{@code >} {@link String }
   */
  public List<Serializable> getContent() {
    if (content == null) {
      content = new ArrayList<Serializable>();
    }
    return this.content;
  }

  /**
   * Gets the value of the value property.
   *
   * @return possible object is {@link String }
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   *
   * @param value allowed object is {@link String }
   */
  public void setValue(String value) {
    this.value = value;
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
}
