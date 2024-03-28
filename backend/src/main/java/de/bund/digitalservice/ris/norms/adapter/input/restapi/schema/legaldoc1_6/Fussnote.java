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
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "fussnote" wird benutzt, um eine Fußnote in einem beliebigen Inhaltselement
 * einzufügen.
 *
 * <p>Java class for fussnote complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="fussnote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}HTMLblock" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="marker" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}markerLiterals" /&gt;
 *       &lt;attribute name="placement" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}placementLiterals.fussnote" /&gt;
 *       &lt;attribute name="placementBase" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}placementBaseLiterals" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.fussnote" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "fussnote",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"htmLblock"})
public class Fussnote {

  @XmlElements({
    @XmlElement(name = "ol", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ol.class),
    @XmlElement(name = "ul", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ul.class),
    @XmlElement(
        name = "table",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Table.class),
    @XmlElement(name = "p", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = P.class)
  })
  protected List<Object> htmLblock;

  @XmlAttribute(name = "marker", required = true)
  protected String marker;

  @XmlAttribute(name = "placement", required = true)
  protected PlacementLiteralsFussnote placement;

  @XmlAttribute(name = "placementBase", required = true)
  protected String placementBase;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the htmLblock property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the htmLblock property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getHTMLblock().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Ol } {@link P } {@link Table
   * } {@link Ul }
   */
  public List<Object> getHTMLblock() {
    if (htmLblock == null) {
      htmLblock = new ArrayList<Object>();
    }
    return this.htmLblock;
  }

  /**
   * Gets the value of the marker property.
   *
   * @return possible object is {@link String }
   */
  public String getMarker() {
    return marker;
  }

  /**
   * Sets the value of the marker property.
   *
   * @param value allowed object is {@link String }
   */
  public void setMarker(String value) {
    this.marker = value;
  }

  /**
   * Gets the value of the placement property.
   *
   * @return possible object is {@link PlacementLiteralsFussnote }
   */
  public PlacementLiteralsFussnote getPlacement() {
    return placement;
  }

  /**
   * Sets the value of the placement property.
   *
   * @param value allowed object is {@link PlacementLiteralsFussnote }
   */
  public void setPlacement(PlacementLiteralsFussnote value) {
    this.placement = value;
  }

  /**
   * Gets the value of the placementBase property.
   *
   * @return possible object is {@link String }
   */
  public String getPlacementBase() {
    return placementBase;
  }

  /**
   * Sets the value of the placementBase property.
   *
   * @param value allowed object is {@link String }
   */
  public void setPlacementBase(String value) {
    this.placementBase = value;
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
