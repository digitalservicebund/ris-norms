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
 * Die Klasse "eingangsformelUndVerzeichnis" wird benutzt, um innerhalb eines Regelungstextes eine
 * Eingangsformel, Ermächtigungsnormen oder Präambeln, sowie Verzeichnisse einzufügen. Durch
 * Schematron-Regeln wird überprüft, dass Eingangsformeln nur bei Gesetzen benutzt werden und dass
 * Verordnungen und Verwaltungsvorschriften Ermächtigungsnormen bereitstellen.
 *
 * <p>Java class for eingangsformelUndVerzeichnis complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="eingangsformelUndVerzeichnis"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="formula" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eingangsformel" minOccurs="0"/&gt;
 *         &lt;element name="citations" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ermaechtigungsnormen" minOccurs="0"/&gt;
 *         &lt;element name="recitals" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}praeambel" minOccurs="0"/&gt;
 *         &lt;element name="blockContainer" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}verzeichniscontainer" maxOccurs="2" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.eingangsformelUndVerzeichnis" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "eingangsformelUndVerzeichnis",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"formula", "citations", "recitals", "blockContainer"})
public class EingangsformelUndVerzeichnis {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Eingangsformel formula;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Ermaechtigungsnormen citations;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected Praeambel recitals;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected List<Verzeichniscontainer> blockContainer;

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
   * Gets the value of the formula property.
   *
   * @return possible object is {@link Eingangsformel }
   */
  public Eingangsformel getFormula() {
    return formula;
  }

  /**
   * Sets the value of the formula property.
   *
   * @param value allowed object is {@link Eingangsformel }
   */
  public void setFormula(Eingangsformel value) {
    this.formula = value;
  }

  /**
   * Gets the value of the citations property.
   *
   * @return possible object is {@link Ermaechtigungsnormen }
   */
  public Ermaechtigungsnormen getCitations() {
    return citations;
  }

  /**
   * Sets the value of the citations property.
   *
   * @param value allowed object is {@link Ermaechtigungsnormen }
   */
  public void setCitations(Ermaechtigungsnormen value) {
    this.citations = value;
  }

  /**
   * Gets the value of the recitals property.
   *
   * @return possible object is {@link Praeambel }
   */
  public Praeambel getRecitals() {
    return recitals;
  }

  /**
   * Sets the value of the recitals property.
   *
   * @param value allowed object is {@link Praeambel }
   */
  public void setRecitals(Praeambel value) {
    this.recitals = value;
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
