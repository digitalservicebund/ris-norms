package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "geltungszeitGruppe" wird genutzt, um Gruppen für Geltungszeitregeln zu erfassen.
 *
 * <p>Java class for geltungszeitGruppe complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="geltungszeitGruppe"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="timeInterval" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}geltungszeitIntervall"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.geltungszeitGruppe" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "geltungszeitGruppe",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"timeInterval"})
public class GeltungszeitGruppe {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected GeltungszeitIntervall timeInterval;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the timeInterval property.
   *
   * @return possible object is {@link GeltungszeitIntervall }
   */
  public GeltungszeitIntervall getTimeInterval() {
    return timeInterval;
  }

  /**
   * Sets the value of the timeInterval property.
   *
   * @param value allowed object is {@link GeltungszeitIntervall }
   */
  public void setTimeInterval(GeltungszeitIntervall value) {
    this.timeInterval = value;
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
