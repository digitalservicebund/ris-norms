package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "expression.FRBRlanguage" dient der Angabe einer Sprachfassung auf der
 * Expression-Ebene.
 *
 * <p>Java class for expression.FRBRlanguage complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="expression.FRBRlanguage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="language" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}languageLiterals.expression.FRBRlanguage" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.expression.FRBRlanguage" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expression.FRBRlanguage", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class ExpressionFRBRlanguage {

  @XmlAttribute(name = "language", required = true)
  protected LanguageLiteralsExpressionFRBRlanguage language;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  /**
   * Gets the value of the language property.
   *
   * @return possible object is {@link LanguageLiteralsExpressionFRBRlanguage }
   */
  public LanguageLiteralsExpressionFRBRlanguage getLanguage() {
    return language;
  }

  /**
   * Sets the value of the language property.
   *
   * @param value allowed object is {@link LanguageLiteralsExpressionFRBRlanguage }
   */
  public void setLanguage(LanguageLiteralsExpressionFRBRlanguage value) {
    this.language = value;
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
