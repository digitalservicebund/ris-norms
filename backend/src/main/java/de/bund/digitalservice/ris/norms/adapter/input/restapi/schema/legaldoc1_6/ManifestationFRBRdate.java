package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Die Klasse "manifestation.FRBRdate" dient der Angabe des Datums auf Manifestation-Ebene.
 *
 * <p>Java class for manifestation.FRBRdate complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="manifestation.FRBRdate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="date" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}dateLiterals.manifestation.FRBRdate" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals.manifestation.FRBRdate" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.manifestation.FRBRdate" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "manifestation.FRBRdate", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class ManifestationFRBRdate {

  @XmlAttribute(name = "date", required = true)
  protected XMLGregorianCalendar date;

  @XmlAttribute(name = "name", required = true)
  protected NameLiteralsManifestationFRBRdate name;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the date property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   */
  public XMLGregorianCalendar getDate() {
    return date;
  }

  /**
   * Sets the value of the date property.
   *
   * @param value allowed object is {@link XMLGregorianCalendar }
   */
  public void setDate(XMLGregorianCalendar value) {
    this.date = value;
  }

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link NameLiteralsManifestationFRBRdate }
   */
  public NameLiteralsManifestationFRBRdate getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link NameLiteralsManifestationFRBRdate }
   */
  public void setName(NameLiteralsManifestationFRBRdate value) {
    this.name = value;
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
}
