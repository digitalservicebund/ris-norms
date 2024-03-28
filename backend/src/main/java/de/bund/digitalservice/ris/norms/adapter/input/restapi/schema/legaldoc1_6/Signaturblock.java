package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "signaturblock" wird genutzt, um eine oder mehrere Signaturen anzugeben.
 *
 * <p>Java class for signaturblock complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="signaturblock"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="p" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}ortUndDatum"/&gt;
 *         &lt;element name="signature" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}signatur" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.signaturblock" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "signaturblock",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"p", "signature"})
public class Signaturblock {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected OrtUndDatum p;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<Signatur> signature;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the p property.
   *
   * @return possible object is {@link OrtUndDatum }
   */
  public OrtUndDatum getP() {
    return p;
  }

  /**
   * Sets the value of the p property.
   *
   * @param value allowed object is {@link OrtUndDatum }
   */
  public void setP(OrtUndDatum value) {
    this.p = value;
  }

  /**
   * Gets the value of the signature property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the signature property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getSignature().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Signatur }
   */
  public List<Signatur> getSignature() {
    if (signature == null) {
      signature = new ArrayList<Signatur>();
    }
    return this.signature;
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
