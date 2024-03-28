package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "geltungszeiten" wird genutzt, um alle Geltungszeitregeln zu einem Dokument zu
 * erfassen.
 *
 * <p>Java class for geltungszeiten complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="geltungszeiten"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="temporalGroup" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}geltungszeitGruppe" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="source" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}sourceLiterals.geltungszeiten" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.geltungszeiten" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "geltungszeiten",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"temporalGroup"})
public class Geltungszeiten {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<GeltungszeitGruppe> temporalGroup;

  @XmlAttribute(name = "source", required = true)
  protected SourceLiteralsGeltungszeiten source;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the temporalGroup property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the temporalGroup property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getTemporalGroup().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GeltungszeitGruppe }
   */
  public List<GeltungszeitGruppe> getTemporalGroup() {
    if (temporalGroup == null) {
      temporalGroup = new ArrayList<GeltungszeitGruppe>();
    }
    return this.temporalGroup;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is {@link SourceLiteralsGeltungszeiten }
   */
  public SourceLiteralsGeltungszeiten getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is {@link SourceLiteralsGeltungszeiten }
   */
  public void setSource(SourceLiteralsGeltungszeiten value) {
    this.source = value;
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
