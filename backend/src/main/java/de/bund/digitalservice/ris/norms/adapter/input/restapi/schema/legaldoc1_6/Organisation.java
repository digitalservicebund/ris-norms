package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "organisation" wird verwendet, um eine Organisiation einzufügen.
 *
 * <p>Java class for organisation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="organisation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="refersTo" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.organisation" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="title" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}titleLiterals.organisation" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.organisation" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "organisation",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"content"})
public class Organisation {

  @XmlMixed protected List<String> content;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "refersTo", required = true)
  protected RefersToLiteralsOrganisation refersTo;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "title", required = true)
  protected String title;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "style")
  protected String style;

  /**
   * Die Klasse "organisation" wird verwendet, um eine Organisiation einzufügen.Gets the value of
   * the content property.
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
   * <p>Objects of the following type(s) are allowed in the list {@link String }
   */
  public List<String> getContent() {
    if (content == null) {
      content = new ArrayList<String>();
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
   * Gets the value of the refersTo property.
   *
   * @return possible object is {@link RefersToLiteralsOrganisation }
   */
  public RefersToLiteralsOrganisation getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsOrganisation }
   */
  public void setRefersTo(RefersToLiteralsOrganisation value) {
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
   * Gets the value of the title property.
   *
   * @return possible object is {@link String }
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the value of the title property.
   *
   * @param value allowed object is {@link String }
   */
  public void setTitle(String value) {
    this.title = value;
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
