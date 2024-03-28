package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "work.identifikation" wird benutzt, um Metadaten zu einem Dokument zu hinterlegen, die
 * die FRBR-Ebene "Work" betreffen. Diese Ebene beschreibt die Identität eines
 * Rechtsetzungsdokuments als abstraktes Dokument, jedoch nicht dessen eigentlichen Inhalt.
 *
 * <p>Java class for work.identifikation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="work.identifikation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FRBRthis" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRthis"/&gt;
 *         &lt;element name="FRBRuri" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRuri"/&gt;
 *         &lt;element name="FRBRalias" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRalias"/&gt;
 *         &lt;element name="FRBRdate" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRdate"/&gt;
 *         &lt;element name="FRBRauthor" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRauthor"/&gt;
 *         &lt;element name="FRBRcountry" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRcountry"/&gt;
 *         &lt;element name="FRBRnumber" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRnumber"/&gt;
 *         &lt;element name="FRBRname" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRname"/&gt;
 *         &lt;element name="FRBRsubtype" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.FRBRsubtype"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.work.identifikation" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "work.identifikation",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {
      "frbRthis",
      "frbRuri",
      "frbRalias",
      "frbRdate",
      "frbRauthor",
      "frbRcountry",
      "frbRnumber",
      "frbRname",
      "frbRsubtype"
    })
public class WorkIdentifikation {

  @XmlElement(
      name = "FRBRthis",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRthis frbRthis;

  @XmlElement(
      name = "FRBRuri",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRuri frbRuri;

  @XmlElement(
      name = "FRBRalias",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRalias frbRalias;

  @XmlElement(
      name = "FRBRdate",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRdate frbRdate;

  @XmlElement(
      name = "FRBRauthor",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRauthor frbRauthor;

  @XmlElement(
      name = "FRBRcountry",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRcountry frbRcountry;

  @XmlElement(
      name = "FRBRnumber",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRnumber frbRnumber;

  @XmlElement(
      name = "FRBRname",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRname frbRname;

  @XmlElement(
      name = "FRBRsubtype",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkFRBRsubtype frbRsubtype;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the frbRthis property.
   *
   * @return possible object is {@link WorkFRBRthis }
   */
  public WorkFRBRthis getFRBRthis() {
    return frbRthis;
  }

  /**
   * Sets the value of the frbRthis property.
   *
   * @param value allowed object is {@link WorkFRBRthis }
   */
  public void setFRBRthis(WorkFRBRthis value) {
    this.frbRthis = value;
  }

  /**
   * Gets the value of the frbRuri property.
   *
   * @return possible object is {@link WorkFRBRuri }
   */
  public WorkFRBRuri getFRBRuri() {
    return frbRuri;
  }

  /**
   * Sets the value of the frbRuri property.
   *
   * @param value allowed object is {@link WorkFRBRuri }
   */
  public void setFRBRuri(WorkFRBRuri value) {
    this.frbRuri = value;
  }

  /**
   * Gets the value of the frbRalias property.
   *
   * @return possible object is {@link WorkFRBRalias }
   */
  public WorkFRBRalias getFRBRalias() {
    return frbRalias;
  }

  /**
   * Sets the value of the frbRalias property.
   *
   * @param value allowed object is {@link WorkFRBRalias }
   */
  public void setFRBRalias(WorkFRBRalias value) {
    this.frbRalias = value;
  }

  /**
   * Gets the value of the frbRdate property.
   *
   * @return possible object is {@link WorkFRBRdate }
   */
  public WorkFRBRdate getFRBRdate() {
    return frbRdate;
  }

  /**
   * Sets the value of the frbRdate property.
   *
   * @param value allowed object is {@link WorkFRBRdate }
   */
  public void setFRBRdate(WorkFRBRdate value) {
    this.frbRdate = value;
  }

  /**
   * Gets the value of the frbRauthor property.
   *
   * @return possible object is {@link WorkFRBRauthor }
   */
  public WorkFRBRauthor getFRBRauthor() {
    return frbRauthor;
  }

  /**
   * Sets the value of the frbRauthor property.
   *
   * @param value allowed object is {@link WorkFRBRauthor }
   */
  public void setFRBRauthor(WorkFRBRauthor value) {
    this.frbRauthor = value;
  }

  /**
   * Gets the value of the frbRcountry property.
   *
   * @return possible object is {@link WorkFRBRcountry }
   */
  public WorkFRBRcountry getFRBRcountry() {
    return frbRcountry;
  }

  /**
   * Sets the value of the frbRcountry property.
   *
   * @param value allowed object is {@link WorkFRBRcountry }
   */
  public void setFRBRcountry(WorkFRBRcountry value) {
    this.frbRcountry = value;
  }

  /**
   * Gets the value of the frbRnumber property.
   *
   * @return possible object is {@link WorkFRBRnumber }
   */
  public WorkFRBRnumber getFRBRnumber() {
    return frbRnumber;
  }

  /**
   * Sets the value of the frbRnumber property.
   *
   * @param value allowed object is {@link WorkFRBRnumber }
   */
  public void setFRBRnumber(WorkFRBRnumber value) {
    this.frbRnumber = value;
  }

  /**
   * Gets the value of the frbRname property.
   *
   * @return possible object is {@link WorkFRBRname }
   */
  public WorkFRBRname getFRBRname() {
    return frbRname;
  }

  /**
   * Sets the value of the frbRname property.
   *
   * @param value allowed object is {@link WorkFRBRname }
   */
  public void setFRBRname(WorkFRBRname value) {
    this.frbRname = value;
  }

  /**
   * Gets the value of the frbRsubtype property.
   *
   * @return possible object is {@link WorkFRBRsubtype }
   */
  public WorkFRBRsubtype getFRBRsubtype() {
    return frbRsubtype;
  }

  /**
   * Sets the value of the frbRsubtype property.
   *
   * @param value allowed object is {@link WorkFRBRsubtype }
   */
  public void setFRBRsubtype(WorkFRBRsubtype value) {
    this.frbRsubtype = value;
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
