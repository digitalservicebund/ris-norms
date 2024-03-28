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
 * Die Klasse "aenderungZitatStruktur" wird benutzt, um den regelungssprachlichen Teil einer
 * Änderung einzufügen. Dieser wird durch eine zitierte Struktur ausgezeichnet.
 * "aenderungZitatStruktur" wird jedoch nicht für Fragmente eines Inhalts verwendet; sofern nicht
 * der gesamte Inhalt geändert werden soll, sondern nur ein Teil, ist stattdessen
 * "aenderungZitatText" (<akn:quotedText>) zu verwenden.
 *
 * <p>Java class for aenderungZitatStruktur complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="aenderungZitatStruktur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="longTitle" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}dokumentenkopfTitel" maxOccurs="unbounded"/&gt;
 *         &lt;element name="citations" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ermaechtigungsnormen" maxOccurs="unbounded"/&gt;
 *         &lt;element name="recitals" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}praeambel" maxOccurs="unbounded"/&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextGliederung" maxOccurs="unbounded"/&gt;
 *         &lt;element name="paragraph" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}juristischerAbsatz" maxOccurs="unbounded"/&gt;
 *         &lt;element name="list" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}juristischerAbsatzUntergliederung" maxOccurs="unbounded"/&gt;
 *         &lt;element name="recital" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}praeambelInhalt" maxOccurs="unbounded"/&gt;
 *         &lt;element name="citation" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ermaechtigungsnorm" maxOccurs="unbounded"/&gt;
 *         &lt;element name="point" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}elementUntergliederung" maxOccurs="unbounded"/&gt;
 *         &lt;element name="wrapUp" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}textNachUntergliederung" maxOccurs="unbounded"/&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}aenderungsInhalt" maxOccurs="unbounded"/&gt;
 *         &lt;element name="documentRef" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}anlagenverweis" maxOccurs="unbounded"/&gt;
 *         &lt;element name="blockContainer" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}verzeichniscontainer" maxOccurs="unbounded"/&gt;
 *         &lt;element name="componentRef" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}stammformVerweis" maxOccurs="unbounded"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="endQuote" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}endQuoteLiterals" /&gt;
 *       &lt;attribute name="startQuote" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}startQuoteLiterals" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.aenderungZitatStruktur" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "aenderungZitatStruktur",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {
      "longTitle",
      "citations",
      "recitals",
      "regelungstextGliederung",
      "paragraph",
      "list",
      "recital",
      "citation",
      "point",
      "wrapUp",
      "aenderungsInhalt",
      "documentRef",
      "blockContainer",
      "componentRef"
    })
public class AenderungZitatStruktur {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<DokumentenkopfTitel> longTitle;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Ermaechtigungsnormen> citations;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Praeambel> recitals;

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

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<JuristischerAbsatz> paragraph;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<JuristischerAbsatzUntergliederung> list;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<PraeambelInhalt> recital;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Ermaechtigungsnorm> citation;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<ElementUntergliederung> point;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<TextNachUntergliederung> wrapUp;

  @XmlElements({
    @XmlElement(
        name = "foreign",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = ExternesMarkup.class),
    @XmlElement(
        name = "blockList",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Liste.class),
    @XmlElement(
        name = "tblock",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Inhaltsabschnitt.class),
    @XmlElement(
        name = "toc",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Inhaltsuebersicht.class),
    @XmlElement(name = "ol", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ol.class),
    @XmlElement(name = "ul", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Ul.class),
    @XmlElement(
        name = "table",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Table.class),
    @XmlElement(name = "p", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = P.class),
    @XmlElement(
        name = "block",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Block.class),
    @XmlElement(
        name = "num",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = ArtUndZaehlbezeichnung.class),
    @XmlElement(
        name = "heading",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = Ueberschrift.class),
    @XmlElement(name = "td", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Td.class),
    @XmlElement(name = "th", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Th.class),
    @XmlElement(name = "tr", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", type = Tr.class)
  })
  protected List<Object> aenderungsInhalt;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Anlagenverweis> documentRef;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Verzeichniscontainer> blockContainer;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<StammformVerweis> componentRef;

  @XmlAttribute(name = "endQuote", required = true)
  protected String endQuote;

  @XmlAttribute(name = "startQuote", required = true)
  protected String startQuote;

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
   * Gets the value of the longTitle property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the longTitle property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getLongTitle().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link DokumentenkopfTitel }
   */
  public List<DokumentenkopfTitel> getLongTitle() {
    if (longTitle == null) {
      longTitle = new ArrayList<DokumentenkopfTitel>();
    }
    return this.longTitle;
  }

  /**
   * Gets the value of the citations property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the citations property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getCitations().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Ermaechtigungsnormen }
   */
  public List<Ermaechtigungsnormen> getCitations() {
    if (citations == null) {
      citations = new ArrayList<Ermaechtigungsnormen>();
    }
    return this.citations;
  }

  /**
   * Gets the value of the recitals property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the recitals property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getRecitals().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Praeambel }
   */
  public List<Praeambel> getRecitals() {
    if (recitals == null) {
      recitals = new ArrayList<Praeambel>();
    }
    return this.recitals;
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
   * Gets the value of the paragraph property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the paragraph property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getParagraph().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link JuristischerAbsatz }
   */
  public List<JuristischerAbsatz> getParagraph() {
    if (paragraph == null) {
      paragraph = new ArrayList<JuristischerAbsatz>();
    }
    return this.paragraph;
  }

  /**
   * Gets the value of the list property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the list property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getList().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link
   * JuristischerAbsatzUntergliederung }
   */
  public List<JuristischerAbsatzUntergliederung> getList() {
    if (list == null) {
      list = new ArrayList<JuristischerAbsatzUntergliederung>();
    }
    return this.list;
  }

  /**
   * Gets the value of the recital property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the recital property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getRecital().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link PraeambelInhalt }
   */
  public List<PraeambelInhalt> getRecital() {
    if (recital == null) {
      recital = new ArrayList<PraeambelInhalt>();
    }
    return this.recital;
  }

  /**
   * Gets the value of the citation property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the citation property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getCitation().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Ermaechtigungsnorm }
   */
  public List<Ermaechtigungsnorm> getCitation() {
    if (citation == null) {
      citation = new ArrayList<Ermaechtigungsnorm>();
    }
    return this.citation;
  }

  /**
   * Gets the value of the point property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the point property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getPoint().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link ElementUntergliederung }
   */
  public List<ElementUntergliederung> getPoint() {
    if (point == null) {
      point = new ArrayList<ElementUntergliederung>();
    }
    return this.point;
  }

  /**
   * Gets the value of the wrapUp property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the wrapUp property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getWrapUp().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link TextNachUntergliederung }
   */
  public List<TextNachUntergliederung> getWrapUp() {
    if (wrapUp == null) {
      wrapUp = new ArrayList<TextNachUntergliederung>();
    }
    return this.wrapUp;
  }

  /**
   * Gets the value of the aenderungsInhalt property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the aenderungsInhalt property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getAenderungsInhalt().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link ArtUndZaehlbezeichnung }
   * {@link Block } {@link ExternesMarkup } {@link Inhaltsabschnitt } {@link Inhaltsuebersicht }
   * {@link Liste } {@link Ol } {@link P } {@link Table } {@link Td } {@link Th } {@link Tr } {@link
   * Ueberschrift } {@link Ul }
   */
  public List<Object> getAenderungsInhalt() {
    if (aenderungsInhalt == null) {
      aenderungsInhalt = new ArrayList<Object>();
    }
    return this.aenderungsInhalt;
  }

  /**
   * Gets the value of the documentRef property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the documentRef property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getDocumentRef().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Anlagenverweis }
   */
  public List<Anlagenverweis> getDocumentRef() {
    if (documentRef == null) {
      documentRef = new ArrayList<Anlagenverweis>();
    }
    return this.documentRef;
  }

  /**
   * Gets the value of the blockContainer property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the blockContainer property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getBlockContainer().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Verzeichniscontainer }
   */
  public List<Verzeichniscontainer> getBlockContainer() {
    if (blockContainer == null) {
      blockContainer = new ArrayList<Verzeichniscontainer>();
    }
    return this.blockContainer;
  }

  /**
   * Gets the value of the componentRef property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the componentRef property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getComponentRef().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link StammformVerweis }
   */
  public List<StammformVerweis> getComponentRef() {
    if (componentRef == null) {
      componentRef = new ArrayList<StammformVerweis>();
    }
    return this.componentRef;
  }

  /**
   * Gets the value of the endQuote property.
   *
   * @return possible object is {@link String }
   */
  public String getEndQuote() {
    return endQuote;
  }

  /**
   * Sets the value of the endQuote property.
   *
   * @param value allowed object is {@link String }
   */
  public void setEndQuote(String value) {
    this.endQuote = value;
  }

  /**
   * Gets the value of the startQuote property.
   *
   * @return possible object is {@link String }
   */
  public String getStartQuote() {
    return startQuote;
  }

  /**
   * Sets the value of the startQuote property.
   *
   * @param value allowed object is {@link String }
   */
  public void setStartQuote(String value) {
    this.startQuote = value;
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
