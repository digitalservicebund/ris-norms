package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "anlagenverweis" wird innerhalb eines Anlagenelements benutzt, um auf ein
 * Anlagendokument zu verweisen und dieses somit als Anlage zu einem Regelungstext einzufügen.
 *
 * <p>Java class for anlagenverweis complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="anlagenverweis"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.anlagenverweis" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="href" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}hrefLiterals" /&gt;
 *       &lt;attribute name="showAs" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}showAsLiterals.anlagenverweis" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anlagenverweis", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class Anlagenverweis {

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "href", required = true)
  protected String href;

  @XmlAttribute(name = "showAs", required = true)
  protected ShowAsLiteralsAnlagenverweis showAs;

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

  /**
   * Gets the value of the href property.
   *
   * @return possible object is {@link String }
   */
  public String getHref() {
    return href;
  }

  /**
   * Sets the value of the href property.
   *
   * @param value allowed object is {@link String }
   */
  public void setHref(String value) {
    this.href = value;
  }

  /**
   * Gets the value of the showAs property.
   *
   * @return possible object is {@link ShowAsLiteralsAnlagenverweis }
   */
  public ShowAsLiteralsAnlagenverweis getShowAs() {
    return showAs;
  }

  /**
   * Sets the value of the showAs property.
   *
   * @param value allowed object is {@link ShowAsLiteralsAnlagenverweis }
   */
  public void setShowAs(ShowAsLiteralsAnlagenverweis value) {
    this.showAs = value;
  }
}
