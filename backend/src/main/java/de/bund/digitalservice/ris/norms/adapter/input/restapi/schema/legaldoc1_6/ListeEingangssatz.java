package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "listeEingangssatz" wird benutzt, um innerhalb einer Liste einen einleitenden Satz vor
 * den Listenelementen einzufügen.
 *
 * <p>Java class for listeEingangssatz complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="listeEingangssatz"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}inlineelement" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.listeEingangssatz" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "listeEingangssatz",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"content"})
public class ListeEingangssatz {

  @XmlElementRefs({
    @XmlElementRef(
        name = "mod",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "ref",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "authorialNote",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "span",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "b",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "i",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "a",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "u",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "sub",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "sup",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "abbr",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "date",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "location",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "organization",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "person",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "role",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "signature",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "docTitle",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "shortTitle",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "docStage",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "docProponent",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "docType",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "docNumber",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "inline",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "img",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "br",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "eop",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "eol",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "affectedDocument",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "relatedDocument",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false),
    @XmlElementRef(
        name = "session",
        namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
        type = JAXBElement.class,
        required = false)
  })
  @XmlMixed
  protected List<Serializable> content;

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
   * Die Klasse "listeEingangssatz" wird benutzt, um innerhalb einer Liste einen einleitenden Satz
   * vor den Listenelementen einzufügen.Gets the value of the content property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the content property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getContent().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link JAXBElement }{@code <}{@link
   * A }{@code >} {@link JAXBElement }{@code <}{@link Abbr }{@code >} {@link JAXBElement }{@code
   * <}{@link Aenderungsbefehl }{@code >} {@link JAXBElement }{@code <}{@link B }{@code >} {@link
   * JAXBElement }{@code <}{@link BetroffenesDokument }{@code >} {@link JAXBElement }{@code <}{@link
   * Bezugsdokument }{@code >} {@link JAXBElement }{@code <}{@link Br }{@code >} {@link JAXBElement
   * }{@code <}{@link Datum }{@code >} {@link JAXBElement }{@code <}{@link DokumentenStatus }{@code
   * >} {@link JAXBElement }{@code <}{@link DokumentenTyp }{@code >} {@link JAXBElement }{@code
   * <}{@link Dokumententitel }{@code >} {@link JAXBElement }{@code <}{@link Drucksachennummer
   * }{@code >} {@link JAXBElement }{@code <}{@link EndeSeite }{@code >} {@link JAXBElement }{@code
   * <}{@link EndeZeile }{@code >} {@link JAXBElement }{@code <}{@link Funktionsbezeichnung }{@code
   * >} {@link JAXBElement }{@code <}{@link Fussnote }{@code >} {@link JAXBElement }{@code <}{@link
   * I }{@code >} {@link JAXBElement }{@code <}{@link Img }{@code >} {@link JAXBElement }{@code
   * <}{@link Initiant }{@code >} {@link JAXBElement }{@code <}{@link Inline }{@code >} {@link
   * JAXBElement }{@code <}{@link Kurztitel }{@code >} {@link JAXBElement }{@code <}{@link
   * Organisation }{@code >} {@link JAXBElement }{@code <}{@link Ort }{@code >} {@link JAXBElement
   * }{@code <}{@link Personenname }{@code >} {@link JAXBElement }{@code <}{@link Referenz }{@code
   * >} {@link JAXBElement }{@code <}{@link SignaturInhalt }{@code >} {@link JAXBElement }{@code
   * <}{@link Sitzung }{@code >} {@link JAXBElement }{@code <}{@link Span }{@code >} {@link
   * JAXBElement }{@code <}{@link Sub }{@code >} {@link JAXBElement }{@code <}{@link Sup }{@code >}
   * {@link JAXBElement }{@code <}{@link U }{@code >} {@link String }
   */
  public List<Serializable> getContent() {
    if (content == null) {
      content = new ArrayList<Serializable>();
    }
    return this.content;
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
