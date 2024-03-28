package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "metadatenMinimal" wird benutzt, um fachliche und technische Metadaten zu einem
 * Rechtsetzungsdokument oder einem der darin referenzierten Dokumente nachzuhalten. Angaben zu
 * Lebenszyklus und Geltungszeiten sind hierbei jedoch explizit nicht möglich.
 *
 * <p>Java class for metadatenMinimal complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="metadatenMinimal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identification" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}identifikation"/&gt;
 *         &lt;element name="lifecycle" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}lebenszyklus" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="analysis" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}dokumentauswertung" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="temporalData" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}geltungszeiten" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="proprietary" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}weitereMetadaten" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.metadatenMinimal" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "metadatenMinimal",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"identification", "analysis", "proprietary"})
public class MetadatenMinimal {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Identifikation identification;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Dokumentauswertung> analysis;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<WeitereMetadaten> proprietary;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the identification property.
   *
   * @return possible object is {@link Identifikation }
   */
  public Identifikation getIdentification() {
    return identification;
  }

  /**
   * Sets the value of the identification property.
   *
   * @param value allowed object is {@link Identifikation }
   */
  public void setIdentification(Identifikation value) {
    this.identification = value;
  }

  /**
   * Gets the value of the analysis property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the analysis property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getAnalysis().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Dokumentauswertung }
   */
  public List<Dokumentauswertung> getAnalysis() {
    if (analysis == null) {
      analysis = new ArrayList<Dokumentauswertung>();
    }
    return this.analysis;
  }

  /**
   * Gets the value of the proprietary property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the proprietary property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getProprietary().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link WeitereMetadaten }
   */
  public List<WeitereMetadaten> getProprietary() {
    if (proprietary == null) {
      proprietary = new ArrayList<WeitereMetadaten>();
    }
    return this.proprietary;
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
