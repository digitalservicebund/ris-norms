package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "th" wird benutzt, um eine Kopfzeile in eine Tabelle einzufügen.
 *
 * <p>Java class for th complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="th"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}inhaltselement" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="colspan" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}colspanLiterals" /&gt;
 *       &lt;attribute name="rowspan" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}rowspanLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.th" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "th",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"inhaltselement"})
public class Th {

  @XmlElements({
    @XmlElement(
        name = "foreign",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = ExternesMarkup.class),
    @XmlElement(
        name = "blockList",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Liste.class),
    @XmlElement(
        name = "tblock",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Inhaltsabschnitt.class),
    @XmlElement(
        name = "toc",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Inhaltsuebersicht.class),
    @XmlElement(name = "ol", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ol.class),
    @XmlElement(name = "ul", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ul.class),
    @XmlElement(
        name = "table",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Table.class),
    @XmlElement(name = "p", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = P.class),
    @XmlElement(
        name = "block",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Block.class)
  })
  protected List<Object> inhaltselement;

  @XmlAttribute(name = "colspan")
  protected BigInteger colspan;

  @XmlAttribute(name = "rowspan")
  protected BigInteger rowspan;

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
   * Gets the value of the inhaltselement property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the inhaltselement property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getInhaltselement().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Block } {@link
   * ExternesMarkup } {@link Inhaltsabschnitt } {@link Inhaltsuebersicht } {@link Liste } {@link Ol
   * } {@link P } {@link Table } {@link Ul }
   */
  public List<Object> getInhaltselement() {
    if (inhaltselement == null) {
      inhaltselement = new ArrayList<Object>();
    }
    return this.inhaltselement;
  }

  /**
   * Gets the value of the colspan property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getColspan() {
    return colspan;
  }

  /**
   * Sets the value of the colspan property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setColspan(BigInteger value) {
    this.colspan = value;
  }

  /**
   * Gets the value of the rowspan property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getRowspan() {
    return rowspan;
  }

  /**
   * Sets the value of the rowspan property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setRowspan(BigInteger value) {
    this.rowspan = value;
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
