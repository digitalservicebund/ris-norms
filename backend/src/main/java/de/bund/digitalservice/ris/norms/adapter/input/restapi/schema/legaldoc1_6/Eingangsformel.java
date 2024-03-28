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

/**
 * Die Klasse "eingangsformel" wird benutzt, um die Eingangsformel eines Dokumentes zu beschreiben.
 * Sie besteht immer aus einem Eingangssatz. Bei Verordnungen und Verwaltungsvorschriften wird die
 * Eingangsformel nicht verwendet.
 *
 * <p>Java class for eingangsformel complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="eingangsformel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="p" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eingangsformelEingangssatz"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals.eingangsformel" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="refersTo" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.eingangsformel" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.eingangsformel" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "eingangsformel",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"p"})
public class Eingangsformel {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected EingangsformelEingangssatz p;

  @XmlAttribute(name = "name", required = true)
  protected NameLiteralsEingangsformel name;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "refersTo", required = true)
  protected RefersToLiteralsEingangsformel refersTo;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the p property.
   *
   * @return possible object is {@link EingangsformelEingangssatz }
   */
  public EingangsformelEingangssatz getP() {
    return p;
  }

  /**
   * Sets the value of the p property.
   *
   * @param value allowed object is {@link EingangsformelEingangssatz }
   */
  public void setP(EingangsformelEingangssatz value) {
    this.p = value;
  }

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link NameLiteralsEingangsformel }
   */
  public NameLiteralsEingangsformel getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link NameLiteralsEingangsformel }
   */
  public void setName(NameLiteralsEingangsformel value) {
    this.name = value;
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
   * @return possible object is {@link RefersToLiteralsEingangsformel }
   */
  public RefersToLiteralsEingangsformel getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsEingangsformel }
   */
  public void setRefersTo(RefersToLiteralsEingangsformel value) {
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
