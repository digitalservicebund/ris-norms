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
 * Die Klasse "regelungstextDokumentenkopf" wird benutzt, um einem Regelungstext einen
 * Dokumentenkopf hinzuzufügen.
 *
 * <p>Java class for regelungstextDokumentenkopf complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="regelungstextDokumentenkopf"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="longTitle" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}dokumentenkopfTitel"/&gt;
 *         &lt;element name="block" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}datumContainer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.regelungstextDokumentenkopf" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "regelungstextDokumentenkopf",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"longTitle", "block"})
public class RegelungstextDokumentenkopf {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected DokumentenkopfTitel longTitle;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected DatumContainer block;

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
   * Gets the value of the longTitle property.
   *
   * @return possible object is {@link DokumentenkopfTitel }
   */
  public DokumentenkopfTitel getLongTitle() {
    return longTitle;
  }

  /**
   * Sets the value of the longTitle property.
   *
   * @param value allowed object is {@link DokumentenkopfTitel }
   */
  public void setLongTitle(DokumentenkopfTitel value) {
    this.longTitle = value;
  }

  /**
   * Gets the value of the block property.
   *
   * @return possible object is {@link DatumContainer }
   */
  public DatumContainer getBlock() {
    return block;
  }

  /**
   * Sets the value of the block property.
   *
   * @param value allowed object is {@link DatumContainer }
   */
  public void setBlock(DatumContainer value) {
    this.block = value;
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
