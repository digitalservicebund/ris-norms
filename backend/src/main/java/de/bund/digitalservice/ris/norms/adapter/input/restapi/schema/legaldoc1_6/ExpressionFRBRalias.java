package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Die Klasse "expression.FRBRalias" dient der Angabe eines übergreifend einmaligen Identfikators
 * (GUID) auf der Expression-Ebene.
 *
 * <p>Java class for expression.FRBRalias complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="expression.FRBRalias"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}nameLiterals.expression.FRBRalias" /&gt;
 *       &lt;attribute name="value" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}valueLiterals.expression.FRBRalias" /&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.expression.FRBRalias" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expression.FRBRalias", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
public class ExpressionFRBRalias {

  @XmlAttribute(name = "name", required = true)
  protected NameLiteralsExpressionFRBRalias name;

  @XmlAttribute(name = "value", required = true)
  protected String value;

  @XmlAttribute(name = "GUID", required = true)
  protected String guid;

  @XmlAttribute(name = "eId", required = true)
  protected String eId;

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link NameLiteralsExpressionFRBRalias }
   */
  public NameLiteralsExpressionFRBRalias getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link NameLiteralsExpressionFRBRalias }
   */
  public void setName(NameLiteralsExpressionFRBRalias value) {
    this.name = value;
  }

  /**
   * Gets the value of the value property.
   *
   * @return possible object is {@link String }
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   *
   * @param value allowed object is {@link String }
   */
  public void setValue(String value) {
    this.value = value;
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
