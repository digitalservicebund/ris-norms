package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "inhaltsuebersichtEintrag" wird benutzt, um innerhalb einer Inhaltsübersicht einen
 * Eintrag hinzuzufügen.
 *
 * <p>Java class for inhaltsuebersichtEintrag complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="inhaltsuebersichtEintrag"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="span" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}span" maxOccurs="2"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="href" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}hrefLiterals" /&gt;
 *       &lt;attribute name="level" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}levelLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.inhaltsuebersichtEintrag" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "inhaltsuebersichtEintrag",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"content"})
public class InhaltsuebersichtEintrag {

  @XmlElementRef(
      name = "span",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      type = JAXBElement.class)
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name = "href", required = true)
  protected String href;

  @XmlAttribute(name = "level", required = true)
  protected BigInteger level;

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
   * Die Klasse "inhaltsuebersichtEintrag" wird benutzt, um innerhalb einer Inhaltsübersicht einen
   * Eintrag hinzuzufügen.Gets the value of the content property.
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
   * Span }{@code >} {@link String }
   */
  public List<Serializable> getContent() {
    if (content == null) {
      content = new ArrayList<Serializable>();
    }
    return this.content;
  }

  /**
   * Gets the value of the href property.
   *
   * @return possible object is {@link String }
   */
  public String getHref() {
    return href;
  }

  /**
   * Sets the value of the href property.
   *
   * @param value allowed object is {@link String }
   */
  public void setHref(String value) {
    this.href = value;
  }

  /**
   * Gets the value of the level property.
   *
   * @return possible object is {@link BigInteger }
   */
  public BigInteger getLevel() {
    return level;
  }

  /**
   * Sets the value of the level property.
   *
   * @param value allowed object is {@link BigInteger }
   */
  public void setLevel(BigInteger value) {
    this.level = value;
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
