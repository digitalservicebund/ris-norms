package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "regelungstextHauptteil" wird benutzt, um innerhalb eines Regelungstextes einen
 * Hauptteil einzufügen.
 *
 * <p>Java class for regelungstextHauptteil complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="regelungstextHauptteil"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextGliederung"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.regelungstextHauptteil" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "regelungstextHauptteil",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {
      "book",
      "part",
      "chapter",
      "subchapter",
      "section",
      "article",
      "subsection",
      "title",
      "subtitle"
    })
public class RegelungstextHauptteil {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungBuch> book;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungTeil> part;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungKapitel> chapter;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungUnterkapitel> subchapter;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungAbschnitt> section;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Einzelvorschrift> article;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungUnterabschnitt> subsection;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungTitel> title;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<GliederungUntertitel> subtitle;

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
   * Gets the value of the book property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the book property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getBook().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungBuch }
   */
  public List<GliederungBuch> getBook() {
    if (book == null) {
      book = new ArrayList<GliederungBuch>();
    }
    return this.book;
  }

  /**
   * Gets the value of the part property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the part property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getPart().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungTeil }
   */
  public List<GliederungTeil> getPart() {
    if (part == null) {
      part = new ArrayList<GliederungTeil>();
    }
    return this.part;
  }

  /**
   * Gets the value of the chapter property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the chapter property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getChapter().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungKapitel }
   */
  public List<GliederungKapitel> getChapter() {
    if (chapter == null) {
      chapter = new ArrayList<GliederungKapitel>();
    }
    return this.chapter;
  }

  /**
   * Gets the value of the subchapter property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the subchapter property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getSubchapter().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungUnterkapitel }
   */
  public List<GliederungUnterkapitel> getSubchapter() {
    if (subchapter == null) {
      subchapter = new ArrayList<GliederungUnterkapitel>();
    }
    return this.subchapter;
  }

  /**
   * Gets the value of the section property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the section property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getSection().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungAbschnitt }
   */
  public List<GliederungAbschnitt> getSection() {
    if (section == null) {
      section = new ArrayList<GliederungAbschnitt>();
    }
    return this.section;
  }

  /**
   * Gets the value of the article property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the article property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getArticle().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Einzelvorschrift }
   */
  public List<Einzelvorschrift> getArticle() {
    if (article == null) {
      article = new ArrayList<Einzelvorschrift>();
    }
    return this.article;
  }

  /**
   * Gets the value of the subsection property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the subsection property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getSubsection().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungUnterabschnitt }
   */
  public List<GliederungUnterabschnitt> getSubsection() {
    if (subsection == null) {
      subsection = new ArrayList<GliederungUnterabschnitt>();
    }
    return this.subsection;
  }

  /**
   * Gets the value of the title property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the title property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getTitle().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungTitel }
   */
  public List<GliederungTitel> getTitle() {
    if (title == null) {
      title = new ArrayList<GliederungTitel>();
    }
    return this.title;
  }

  /**
   * Gets the value of the subtitle property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the subtitle property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getSubtitle().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link GliederungUntertitel }
   */
  public List<GliederungUntertitel> getSubtitle() {
    if (subtitle == null) {
      subtitle = new ArrayList<GliederungUntertitel>();
    }
    return this.subtitle;
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
