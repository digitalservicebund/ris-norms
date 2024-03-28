package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

/**
 * Die Klasse "geltungszeitIntervall" wird genutzt, um konkrete Zeitintervalle innerhalb von
 * Geltungszeitgruppen zu erfassen.
 *
 * <p>Java class for geltungszeitIntervall complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="geltungszeitIntervall"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="end" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}endLiterals.geltungszeitIntervall" /&gt;
 *       &lt;attribute name="start" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}startLiterals.geltungszeitIntervall" /&gt;
 *       &lt;attribute name="duration" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}durationLiterals" /&gt;
 *       &lt;attribute name="refersTo" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}refersToLiterals.geltungszeitIntervall" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.geltungszeitIntervall" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geltungszeitIntervall", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class GeltungszeitIntervall {

  @XmlAttribute(name = "end")
  protected String end;

  @XmlAttribute(name = "start", required = true)
  protected String start;

  @XmlAttribute(name = "duration")
  protected Duration duration;

  @XmlAttribute(name = "refersTo", required = true)
  protected RefersToLiteralsGeltungszeitIntervall refersTo;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the end property.
   *
   * @return possible object is {@link String }
   */
  public String getEnd() {
    return end;
  }

  /**
   * Sets the value of the end property.
   *
   * @param value allowed object is {@link String }
   */
  public void setEnd(String value) {
    this.end = value;
  }

  /**
   * Gets the value of the start property.
   *
   * @return possible object is {@link String }
   */
  public String getStart() {
    return start;
  }

  /**
   * Sets the value of the start property.
   *
   * @param value allowed object is {@link String }
   */
  public void setStart(String value) {
    this.start = value;
  }

  /**
   * Gets the value of the duration property.
   *
   * @return possible object is {@link Duration }
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * Sets the value of the duration property.
   *
   * @param value allowed object is {@link Duration }
   */
  public void setDuration(Duration value) {
    this.duration = value;
  }

  /**
   * Gets the value of the refersTo property.
   *
   * @return possible object is {@link RefersToLiteralsGeltungszeitIntervall }
   */
  public RefersToLiteralsGeltungszeitIntervall getRefersTo() {
    return refersTo;
  }

  /**
   * Sets the value of the refersTo property.
   *
   * @param value allowed object is {@link RefersToLiteralsGeltungszeitIntervall }
   */
  public void setRefersTo(RefersToLiteralsGeltungszeitIntervall value) {
    this.refersTo = value;
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
