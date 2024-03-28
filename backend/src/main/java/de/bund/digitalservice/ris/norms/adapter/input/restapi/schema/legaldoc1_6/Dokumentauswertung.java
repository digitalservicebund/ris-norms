package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "dokumentauswertung" wird benutzt, um Metadaten zu Änderungsbefehlen zu erfassen, die
 * innerhalb eines Dokuments ausgezeichnet werden (aenderungAktiv). Außerdem werden auch Metadaten
 * zu solchen Änderungsbefehlen erfasst, die in externen Dokumenten angelegt werden, sich aber auf
 * das aktuelle Dokument beziehen (aenderungPassiv).
 *
 * <p>Java class for dokumentauswertung complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="dokumentauswertung"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activeModifications" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungenAktiv" minOccurs="0"/&gt;
 *         &lt;element name="passiveModifications" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungenPassiv" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="source" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals.dokumentauswertung" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.dokumentauswertung" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "dokumentauswertung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"activeModifications", "passiveModifications"})
public class Dokumentauswertung {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected AenderungenAktiv activeModifications;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected AenderungenPassiv passiveModifications;

  @XmlAttribute(name = "source", required = true)
  protected SourceLiteralsDokumentauswertung source;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the activeModifications property.
   *
   * @return possible object is {@link AenderungenAktiv }
   */
  public AenderungenAktiv getActiveModifications() {
    return activeModifications;
  }

  /**
   * Sets the value of the activeModifications property.
   *
   * @param value allowed object is {@link AenderungenAktiv }
   */
  public void setActiveModifications(AenderungenAktiv value) {
    this.activeModifications = value;
  }

  /**
   * Gets the value of the passiveModifications property.
   *
   * @return possible object is {@link AenderungenPassiv }
   */
  public AenderungenPassiv getPassiveModifications() {
    return passiveModifications;
  }

  /**
   * Sets the value of the passiveModifications property.
   *
   * @param value allowed object is {@link AenderungenPassiv }
   */
  public void setPassiveModifications(AenderungenPassiv value) {
    this.passiveModifications = value;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link SourceLiteralsDokumentauswertung }
   */
  public SourceLiteralsDokumentauswertung getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link SourceLiteralsDokumentauswertung }
   */
  public void setSource(SourceLiteralsDokumentauswertung value) {
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
