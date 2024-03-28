package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "gliederungTeil" wird genutzt, um ein Gliederungselement einzufügen und damit einen
 * Regelungstext zu untergliedern.
 *
 * <p>Java class for gliederungTeil complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="gliederungTeil"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="num" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}artUndZaehlbezeichnung"/&gt;
 *         &lt;element name="heading" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ueberschrift"/&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextGliederung" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.gliederungTeil" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "gliederungTeil",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"num", "heading", "regelungstextGliederung"})
public class GliederungTeil {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected ArtUndZaehlbezeichnung num;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected Ueberschrift heading;

  @XmlElements({
    @XmlElement(
        name = "book",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungBuch.class),
    @XmlElement(
        name = "part",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungTeil.class),
    @XmlElement(
        name = "chapter",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungKapitel.class),
    @XmlElement(
        name = "subchapter",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungUnterkapitel.class),
    @XmlElement(
        name = "section",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungAbschnitt.class),
    @XmlElement(
        name = "article",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Einzelvorschrift.class),
    @XmlElement(
        name = "subsection",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungUnterabschnitt.class),
    @XmlElement(
        name = "title",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungTitel.class),
    @XmlElement(
        name = "subtitle",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = GliederungUntertitel.class)
  })
  protected List<Object> regelungstextGliederung;

  @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the num property.
   *
   * @return possible object is {@link ArtUndZaehlbezeichnung }
   */
  public ArtUndZaehlbezeichnung getNum() {
    return num;
  }

  /**
   * Sets the value of the num property.
   *
   * @param value allowed object is {@link ArtUndZaehlbezeichnung }
   */
  public void setNum(ArtUndZaehlbezeichnung value) {
    this.num = value;
  }

  /**
   * Gets the value of the heading property.
   *
   * @return possible object is {@link Ueberschrift }
   */
  public Ueberschrift getHeading() {
    return heading;
  }

  /**
   * Sets the value of the heading property.
   *
   * @param value allowed object is {@link Ueberschrift }
   */
  public void setHeading(Ueberschrift value) {
    this.heading = value;
  }

  /**
   * Gets the value of the regelungstextGliederung property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the regelungstextGliederung
   * property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getRegelungstextGliederung().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Einzelvorschrift } {@link
   * GliederungAbschnitt } {@link GliederungBuch } {@link GliederungKapitel } {@link GliederungTeil
   * } {@link GliederungTitel } {@link GliederungUnterabschnitt } {@link GliederungUnterkapitel }
   * {@link GliederungUntertitel }
   */
  public List<Object> getRegelungstextGliederung() {
    if (regelungstextGliederung == null) {
      regelungstextGliederung = new ArrayList<Object>();
    }
    return this.regelungstextGliederung;
  }

  /**
   * Gets the value of the id property.
   *
   * @return possible object is {@link String }
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is {@link String }
   */
  public void setId(String value) {
    this.id = value;
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
