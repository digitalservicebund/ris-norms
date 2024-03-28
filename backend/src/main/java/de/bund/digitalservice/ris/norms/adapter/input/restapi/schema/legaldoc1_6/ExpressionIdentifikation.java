package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "expression.identifikation" wird benutzt, um Metadaten zu einem Dokument zu
 * hinterlegen, die die FRBR-Ebene "Expression" betreffen. Diese Ebene beschreibt eine konkrete
 * Version eines Rechtsetzungsdokuments und damit dessen Inhalt.
 *
 * <p>Java class for expression.identifikation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="expression.identifikation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FRBRthis" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRthis"/&gt;
 *         &lt;element name="FRBRuri" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRuri"/&gt;
 *         &lt;element name="FRBRalias" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRalias" maxOccurs="3" minOccurs="2"/&gt;
 *         &lt;element name="FRBRauthor" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRauthor"/&gt;
 *         &lt;element name="FRBRdate" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRdate"/&gt;
 *         &lt;element name="FRBRlanguage" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRlanguage"/&gt;
 *         &lt;element name="FRBRversionNumber" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.FRBRversionNumber"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.expression.identifikation" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "expression.identifikation",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {
      "frbRthis",
      "frbRuri",
      "frbRalias",
      "frbRauthor",
      "frbRdate",
      "frbRlanguage",
      "frbRversionNumber"
    })
public class ExpressionIdentifikation {

  @XmlElement(
      name = "FRBRthis",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRthis frbRthis;

  @XmlElement(
      name = "FRBRuri",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRuri frbRuri;

  @XmlElement(
      name = "FRBRalias",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected List<ExpressionFRBRalias> frbRalias;

  @XmlElement(
      name = "FRBRauthor",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRauthor frbRauthor;

  @XmlElement(
      name = "FRBRdate",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRdate frbRdate;

  @XmlElement(
      name = "FRBRlanguage",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRlanguage frbRlanguage;

  @XmlElement(
      name = "FRBRversionNumber",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionFRBRversionNumber frbRversionNumber;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the frbRthis property.
   *
   * @return possible object is {@link ExpressionFRBRthis }
   */
  public ExpressionFRBRthis getFRBRthis() {
    return frbRthis;
  }

  /**
   * Sets the value of the frbRthis property.
   *
   * @param value allowed object is {@link ExpressionFRBRthis }
   */
  public void setFRBRthis(ExpressionFRBRthis value) {
    this.frbRthis = value;
  }

  /**
   * Gets the value of the frbRuri property.
   *
   * @return possible object is {@link ExpressionFRBRuri }
   */
  public ExpressionFRBRuri getFRBRuri() {
    return frbRuri;
  }

  /**
   * Sets the value of the frbRuri property.
   *
   * @param value allowed object is {@link ExpressionFRBRuri }
   */
  public void setFRBRuri(ExpressionFRBRuri value) {
    this.frbRuri = value;
  }

  /**
   * Gets the value of the frbRalias property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the frbRalias property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getFRBRalias().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link ExpressionFRBRalias }
   */
  public List<ExpressionFRBRalias> getFRBRalias() {
    if (frbRalias == null) {
      frbRalias = new ArrayList<ExpressionFRBRalias>();
    }
    return this.frbRalias;
  }

  /**
   * Gets the value of the frbRauthor property.
   *
   * @return possible object is {@link ExpressionFRBRauthor }
   */
  public ExpressionFRBRauthor getFRBRauthor() {
    return frbRauthor;
  }

  /**
   * Sets the value of the frbRauthor property.
   *
   * @param value allowed object is {@link ExpressionFRBRauthor }
   */
  public void setFRBRauthor(ExpressionFRBRauthor value) {
    this.frbRauthor = value;
  }

  /**
   * Gets the value of the frbRdate property.
   *
   * @return possible object is {@link ExpressionFRBRdate }
   */
  public ExpressionFRBRdate getFRBRdate() {
    return frbRdate;
  }

  /**
   * Sets the value of the frbRdate property.
   *
   * @param value allowed object is {@link ExpressionFRBRdate }
   */
  public void setFRBRdate(ExpressionFRBRdate value) {
    this.frbRdate = value;
  }

  /**
   * Gets the value of the frbRlanguage property.
   *
   * @return possible object is {@link ExpressionFRBRlanguage }
   */
  public ExpressionFRBRlanguage getFRBRlanguage() {
    return frbRlanguage;
  }

  /**
   * Sets the value of the frbRlanguage property.
   *
   * @param value allowed object is {@link ExpressionFRBRlanguage }
   */
  public void setFRBRlanguage(ExpressionFRBRlanguage value) {
    this.frbRlanguage = value;
  }

  /**
   * Gets the value of the frbRversionNumber property.
   *
   * @return possible object is {@link ExpressionFRBRversionNumber }
   */
  public ExpressionFRBRversionNumber getFRBRversionNumber() {
    return frbRversionNumber;
  }

  /**
   * Sets the value of the frbRversionNumber property.
   *
   * @param value allowed object is {@link ExpressionFRBRversionNumber }
   */
  public void setFRBRversionNumber(ExpressionFRBRversionNumber value) {
    this.frbRversionNumber = value;
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
