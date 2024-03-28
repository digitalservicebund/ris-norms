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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "table" wird verwendet, um eine Tabelle einzufügen.
 *
 * <p>Java class for table complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="table"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="caption" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}caption" minOccurs="0"/&gt;
 *         &lt;element name="tr" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}tr" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="title" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}titleLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.table" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *       &lt;attribute name="width" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}widthLiterals" /&gt;
 *       &lt;attribute name="border" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}borderLiterals" /&gt;
 *       &lt;attribute name="cellpadding" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}cellpaddingLiterals" /&gt;
 *       &lt;attribute name="cellspacing" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}cellspacingLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "table",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"caption", "tr"})
public class Table {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Caption caption;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<Tr> tr;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "title")
  protected String title;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "style")
  protected String style;

  @XmlAttribute(name = "width")
  protected BigInteger width;

  @XmlAttribute(name = "border")
  protected BigInteger border;

  @XmlAttribute(name = "cellpadding")
  protected BigInteger cellpadding;

  @XmlAttribute(name = "cellspacing")
  protected BigInteger cellspacing;

  /**
   * Gets the value of the caption property.
   *
   * @return possible object is {@link Caption }
   */
  public Caption getCaption() {
    return caption;
  }

  /**
   * Sets the value of the caption property.
   *
   * @param value allowed object is {@link Caption }
   */
  public void setCaption(Caption value) {
    this.caption = value;
  }

  /**
   * Gets the value of the tr property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the tr property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getTr().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Tr }
   */
  public List<Tr> getTr() {
    if (tr == null) {
      tr = new ArrayList<Tr>();
    }
    return this.tr;
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

  /**
   * Gets the value of the width property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getWidth() {
    return width;
  }

  /**
   * Sets the value of the width property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setWidth(BigInteger value) {
    this.width = value;
  }

  /**
   * Gets the value of the border property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getBorder() {
    return border;
  }

  /**
   * Sets the value of the border property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setBorder(BigInteger value) {
    this.border = value;
  }

  /**
   * Gets the value of the cellpadding property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getCellpadding() {
    return cellpadding;
  }

  /**
   * Sets the value of the cellpadding property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setCellpadding(BigInteger value) {
    this.cellpadding = value;
  }

  /**
   * Gets the value of the cellspacing property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getCellspacing() {
    return cellspacing;
  }

  /**
   * Sets the value of the cellspacing property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setCellspacing(BigInteger value) {
    this.cellspacing = value;
  }
}
