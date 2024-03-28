package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "manifestation.identifikation" wird benutzt, um Metadaten zu einem Dokument zu
 * hinterlegen, die die FRBR-Ebene "Manifestation" betreffen. Diese Ebene beschreibt die
 * Repräsentation einer konkreten Version eines Rechtsetzungsdokuments in einem konkreten Format in
 * einer Datei.
 *
 * <p>Java class for manifestation.identifikation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="manifestation.identifikation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FRBRthis" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.FRBRthis"/&gt;
 *         &lt;element name="FRBRuri" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.FRBRuri"/&gt;
 *         &lt;element name="FRBRdate" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.FRBRdate"/&gt;
 *         &lt;element name="FRBRauthor" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.FRBRauthor"/&gt;
 *         &lt;element name="FRBRformat" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.FRBRformat"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.manifestation.identifikation" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "manifestation.identifikation",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"frbRthis", "frbRuri", "frbRdate", "frbRauthor", "frbRformat"})
public class ManifestationIdentifikation {

  @XmlElement(
      name = "FRBRthis",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationFRBRthis frbRthis;

  @XmlElement(
      name = "FRBRuri",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationFRBRuri frbRuri;

  @XmlElement(
      name = "FRBRdate",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationFRBRdate frbRdate;

  @XmlElement(
      name = "FRBRauthor",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationFRBRauthor frbRauthor;

  @XmlElement(
      name = "FRBRformat",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationFRBRformat frbRformat;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the frbRthis property.
   *
   * @return possible object is {@link ManifestationFRBRthis }
   */
  public ManifestationFRBRthis getFRBRthis() {
    return frbRthis;
  }

  /**
   * Sets the value of the frbRthis property.
   *
   * @param value allowed object is {@link ManifestationFRBRthis }
   */
  public void setFRBRthis(ManifestationFRBRthis value) {
    this.frbRthis = value;
  }

  /**
   * Gets the value of the frbRuri property.
   *
   * @return possible object is {@link ManifestationFRBRuri }
   */
  public ManifestationFRBRuri getFRBRuri() {
    return frbRuri;
  }

  /**
   * Sets the value of the frbRuri property.
   *
   * @param value allowed object is {@link ManifestationFRBRuri }
   */
  public void setFRBRuri(ManifestationFRBRuri value) {
    this.frbRuri = value;
  }

  /**
   * Gets the value of the frbRdate property.
   *
   * @return possible object is {@link ManifestationFRBRdate }
   */
  public ManifestationFRBRdate getFRBRdate() {
    return frbRdate;
  }

  /**
   * Sets the value of the frbRdate property.
   *
   * @param value allowed object is {@link ManifestationFRBRdate }
   */
  public void setFRBRdate(ManifestationFRBRdate value) {
    this.frbRdate = value;
  }

  /**
   * Gets the value of the frbRauthor property.
   *
   * @return possible object is {@link ManifestationFRBRauthor }
   */
  public ManifestationFRBRauthor getFRBRauthor() {
    return frbRauthor;
  }

  /**
   * Sets the value of the frbRauthor property.
   *
   * @param value allowed object is {@link ManifestationFRBRauthor }
   */
  public void setFRBRauthor(ManifestationFRBRauthor value) {
    this.frbRauthor = value;
  }

  /**
   * Gets the value of the frbRformat property.
   *
   * @return possible object is {@link ManifestationFRBRformat }
   */
  public ManifestationFRBRformat getFRBRformat() {
    return frbRformat;
  }

  /**
   * Sets the value of the frbRformat property.
   *
   * @param value allowed object is {@link ManifestationFRBRformat }
   */
  public void setFRBRformat(ManifestationFRBRformat value) {
    this.frbRformat = value;
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
