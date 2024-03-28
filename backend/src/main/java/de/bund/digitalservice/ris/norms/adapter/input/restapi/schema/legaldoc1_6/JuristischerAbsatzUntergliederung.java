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
 * Die Klasse "juristischerAbsatzUntergliederung" wird genutzt, um eine Liste innerhalb eines
 * juristischen Absatzes hinzuzufügen.
 *
 * <p>Java class for juristischerAbsatzUntergliederung complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="juristischerAbsatzUntergliederung"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="intro" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}textVorUntergliederung"/&gt;
 *         &lt;element name="point" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}elementUntergliederung" maxOccurs="unbounded"/&gt;
 *         &lt;element name="wrapUp" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}textNachUntergliederung" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="period" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}periodLiterals.juristischerAbsatzUntergliederung" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.juristischerAbsatzUntergliederung" /&gt;
 *       &lt;attribute name="style" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}styleLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "juristischerAbsatzUntergliederung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"intro", "point", "wrapUp"})
public class JuristischerAbsatzUntergliederung {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected TextVorUntergliederung intro;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<ElementUntergliederung> point;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected TextNachUntergliederung wrapUp;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "period")
  protected String period;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "style")
  protected String style;

  /**
   * Gets the value of the intro property.
   *
   * @return possible object is {@link TextVorUntergliederung }
   */
  public TextVorUntergliederung getIntro() {
    return intro;
  }

  /**
   * Sets the value of the intro property.
   *
   * @param value allowed object is {@link TextVorUntergliederung }
   */
  public void setIntro(TextVorUntergliederung value) {
    this.intro = value;
  }

  /**
   * Gets the value of the point property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the point property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getPoint().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link ElementUntergliederung }
   */
  public List<ElementUntergliederung> getPoint() {
    if (point == null) {
      point = new ArrayList<ElementUntergliederung>();
    }
    return this.point;
  }

  /**
   * Gets the value of the wrapUp property.
   *
   * @return possible object is {@link TextNachUntergliederung }
   */
  public TextNachUntergliederung getWrapUp() {
    return wrapUp;
  }

  /**
   * Sets the value of the wrapUp property.
   *
   * @param value allowed object is {@link TextNachUntergliederung }
   */
  public void setWrapUp(TextNachUntergliederung value) {
    this.wrapUp = value;
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
