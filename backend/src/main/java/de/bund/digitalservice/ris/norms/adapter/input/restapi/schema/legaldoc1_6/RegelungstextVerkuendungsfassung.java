package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "regelungstextVerkuendungsfassung" bildet ein eigenständiges Teildokument eines
 * Rechtsetzungsdokuments in der Verkündungsfassung in Form eines separaten XML-Dokuments, das durch
 * die Klasse "rechtsetzungsdokument" referenziert und in dieser eingebunden wird.
 *
 * <p>Java class for regelungstextVerkuendungsfassung complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="regelungstextVerkuendungsfassung"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="meta" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}metadatenVollstaendig"/&gt;
 *         &lt;group ref="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextEinleitung"/&gt;
 *         &lt;element name="body" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextHauptteil"/&gt;
 *         &lt;element name="conclusions" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}regelungstextSchluss" minOccurs="0"/&gt;
 *         &lt;element name="attachments" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}anlagencontainer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals.regelungstextVerkuendungsfassung" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "regelungstextVerkuendungsfassung",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"meta", "preface", "preamble", "body", "conclusions", "attachments"})
public class RegelungstextVerkuendungsfassung {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected MetadatenVollstaendig meta;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected RegelungstextDokumentenkopf preface;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected EingangsformelUndVerzeichnis preamble;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected RegelungstextHauptteil body;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected RegelungstextSchluss conclusions;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Anlagencontainer attachments;

  @XmlAttribute(name = "name", required = true)
  protected NameLiteralsRegelungstextVerkuendungsfassung name;

  /**
   * Gets the value of the meta property.
   *
   * @return possible object is {@link MetadatenVollstaendig }
   */
  public MetadatenVollstaendig getMeta() {
    return meta;
  }

  /**
   * Sets the value of the meta property.
   *
   * @param value allowed object is {@link MetadatenVollstaendig }
   */
  public void setMeta(MetadatenVollstaendig value) {
    this.meta = value;
  }

  /**
   * Gets the value of the preface property.
   *
   * @return possible object is {@link RegelungstextDokumentenkopf }
   */
  public RegelungstextDokumentenkopf getPreface() {
    return preface;
  }

  /**
   * Sets the value of the preface property.
   *
   * @param value allowed object is {@link RegelungstextDokumentenkopf }
   */
  public void setPreface(RegelungstextDokumentenkopf value) {
    this.preface = value;
  }

  /**
   * Gets the value of the preamble property.
   *
   * @return possible object is {@link EingangsformelUndVerzeichnis }
   */
  public EingangsformelUndVerzeichnis getPreamble() {
    return preamble;
  }

  /**
   * Sets the value of the preamble property.
   *
   * @param value allowed object is {@link EingangsformelUndVerzeichnis }
   */
  public void setPreamble(EingangsformelUndVerzeichnis value) {
    this.preamble = value;
  }

  /**
   * Gets the value of the body property.
   *
   * @return possible object is {@link RegelungstextHauptteil }
   */
  public RegelungstextHauptteil getBody() {
    return body;
  }

  /**
   * Sets the value of the body property.
   *
   * @param value allowed object is {@link RegelungstextHauptteil }
   */
  public void setBody(RegelungstextHauptteil value) {
    this.body = value;
  }

  /**
   * Gets the value of the conclusions property.
   *
   * @return possible object is {@link RegelungstextSchluss }
   */
  public RegelungstextSchluss getConclusions() {
    return conclusions;
  }

  /**
   * Sets the value of the conclusions property.
   *
   * @param value allowed object is {@link RegelungstextSchluss }
   */
  public void setConclusions(RegelungstextSchluss value) {
    this.conclusions = value;
  }

  /**
   * Gets the value of the attachments property.
   *
   * @return possible object is {@link Anlagencontainer }
   */
  public Anlagencontainer getAttachments() {
    return attachments;
  }

  /**
   * Sets the value of the attachments property.
   *
   * @param value allowed object is {@link Anlagencontainer }
   */
  public void setAttachments(Anlagencontainer value) {
    this.attachments = value;
  }

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link NameLiteralsRegelungstextVerkuendungsfassung }
   */
  public NameLiteralsRegelungstextVerkuendungsfassung getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link NameLiteralsRegelungstextVerkuendungsfassung }
   */
  public void setName(NameLiteralsRegelungstextVerkuendungsfassung value) {
    this.name = value;
  }
}
