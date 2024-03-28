package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "aenderungenAktiv" wird benutzt, um die Metadaten zu allen in einem Dokument
 * angelegten Änderungsbefehlen abzubilden.
 *
 * <p>Java class for aenderungenAktiv complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="aenderungenAktiv"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungsbefehle" maxOccurs="unbounded"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.aenderungenAktiv" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "aenderungenAktiv",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"aenderungsbefehle"})
public class AenderungenAktiv {

  @XmlElements({
    @XmlElement(
        name = "textualMod",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Textaenderung.class),
    @XmlElement(
        name = "forceMod",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Geltungszeitaenderung.class)
  })
  protected List<Object> aenderungsbefehle;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the aenderungsbefehle property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the aenderungsbefehle property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getAenderungsbefehle().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Geltungszeitaenderung }
   * {@link Textaenderung }
   */
  public List<Object> getAenderungsbefehle() {
    if (aenderungsbefehle == null) {
      aenderungsbefehle = new ArrayList<Object>();
    }
    return this.aenderungsbefehle;
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
