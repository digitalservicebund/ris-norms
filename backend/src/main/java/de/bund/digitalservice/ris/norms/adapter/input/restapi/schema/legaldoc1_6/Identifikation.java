package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "identifikation" wird benutzt, um Metadaten zu einem Dokument zu hinterlegen, das Teil
 * eines Rechtsetzungsdokuments ist. Darin werden verschiedene Metadaten zu den FRBR-Ebenen Werk,
 * Version und Manifestation ausgezeichnet.
 *
 * <p>Java class for identifikation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="identifikation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FRBRWork" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}work.identifikation"/&gt;
 *         &lt;element name="FRBRExpression" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}expression.identifikation"/&gt;
 *         &lt;element name="FRBRManifestation" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}manifestation.identifikation"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="source" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals.identifikation" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.identifikation" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "identifikation",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"frbrWork", "frbrExpression", "frbrManifestation"})
public class Identifikation {

  @XmlElement(
      name = "FRBRWork",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected WorkIdentifikation frbrWork;

  @XmlElement(
      name = "FRBRExpression",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ExpressionIdentifikation frbrExpression;

  @XmlElement(
      name = "FRBRManifestation",
      namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
      required = true)
  protected ManifestationIdentifikation frbrManifestation;

  @XmlAttribute(name = "source", required = true)
  protected SourceLiteralsIdentifikation source;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the frbrWork property.
   *
   * @return possible object is {@link WorkIdentifikation }
   */
  public WorkIdentifikation getFRBRWork() {
    return frbrWork;
  }

  /**
   * Sets the value of the frbrWork property.
   *
   * @param value allowed object is {@link WorkIdentifikation }
   */
  public void setFRBRWork(WorkIdentifikation value) {
    this.frbrWork = value;
  }

  /**
   * Gets the value of the frbrExpression property.
   *
   * @return possible object is {@link ExpressionIdentifikation }
   */
  public ExpressionIdentifikation getFRBRExpression() {
    return frbrExpression;
  }

  /**
   * Sets the value of the frbrExpression property.
   *
   * @param value allowed object is {@link ExpressionIdentifikation }
   */
  public void setFRBRExpression(ExpressionIdentifikation value) {
    this.frbrExpression = value;
  }

  /**
   * Gets the value of the frbrManifestation property.
   *
   * @return possible object is {@link ManifestationIdentifikation }
   */
  public ManifestationIdentifikation getFRBRManifestation() {
    return frbrManifestation;
  }

  /**
   * Sets the value of the frbrManifestation property.
   *
   * @param value allowed object is {@link ManifestationIdentifikation }
   */
  public void setFRBRManifestation(ManifestationIdentifikation value) {
    this.frbrManifestation = value;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link SourceLiteralsIdentifikation }
   */
  public SourceLiteralsIdentifikation getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link SourceLiteralsIdentifikation }
   */
  public void setSource(SourceLiteralsIdentifikation value) {
    this.source = value;
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
