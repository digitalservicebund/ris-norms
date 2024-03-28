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
 * Die Klasse "tr" wird benutzt, um eine Zeile in eine Tabelle einzufügen.
 *
 * <p>Java class for tr complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="tr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="th" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}th" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="td" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}td" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.tr" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "tr",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"th", "td"})
public class Tr {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Th> th;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Td> td;

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
   * Gets the value of the th property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the th property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getTh().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Th }
   */
  public List<Th> getTh() {
    if (th == null) {
      th = new ArrayList<Th>();
    }
    return this.th;
  }

  /**
   * Gets the value of the td property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the td property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getTd().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Td }
   */
  public List<Td> getTd() {
    if (td == null) {
      td = new ArrayList<Td>();
    }
    return this.td;
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
