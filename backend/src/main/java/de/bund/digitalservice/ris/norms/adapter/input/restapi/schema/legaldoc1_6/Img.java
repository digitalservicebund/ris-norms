package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;

/**
 * Die Klasse "img" ist sinngemäß aus HTML übernommen und wird zum Einbinden von Bildern genutzt.
 *
 * <p>Java class for img complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="img"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="alt" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}altLiterals" /&gt;
 *       &lt;attribute name="src" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}srcLiterals" /&gt;
 *       &lt;attribute name="width" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}widthLiterals" /&gt;
 *       &lt;attribute name="height" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}heightLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="title" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}titleLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.img" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "img", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class Img {

  @XmlAttribute(name = "alt")
  protected String alt;

  @XmlAttribute(name = "src", required = true)
  protected String src;

  @XmlAttribute(name = "width")
  protected BigInteger width;

  @XmlAttribute(name = "height")
  protected BigInteger height;

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

  /**
   * Gets the value of the alt property.
   *
   * @return possible object is {@link String }
   */
  public String getAlt() {
    return alt;
  }

  /**
   * Sets the value of the alt property.
   *
   * @param value allowed object is {@link String }
   */
  public void setAlt(String value) {
    this.alt = value;
  }

  /**
   * Gets the value of the src property.
   *
   * @return possible object is {@link String }
   */
  public String getSrc() {
    return src;
  }

  /**
   * Sets the value of the src property.
   *
   * @param value allowed object is {@link String }
   */
  public void setSrc(String value) {
    this.src = value;
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
   * Gets the value of the height property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getHeight() {
    return height;
  }

  /**
   * Sets the value of the height property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setHeight(BigInteger value) {
    this.height = value;
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
}
