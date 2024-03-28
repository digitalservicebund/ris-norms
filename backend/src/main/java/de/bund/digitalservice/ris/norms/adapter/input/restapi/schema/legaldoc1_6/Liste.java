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
 * Die Klasse "liste" wird benutzt, um eine Liste mit vordefinierten Strukturelementen einzufügen.
 * Diese verfügt im Gegensatz zu nummerierten und unnummerierten Listen über Listenelemente mit
 * jeweils einer Art- und Zählbezeichnung, einer Überschrift und Text.
 *
 * <p>Java class for liste complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="liste"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="listIntroduction" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}listeEingangssatz" minOccurs="0"/&gt;
 *         &lt;element name="item" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}listeListenelement" maxOccurs="unbounded"/&gt;
 *         &lt;element name="listWrapUp" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}listeSchlusssatz" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}id"/&gt;
 *       &lt;attribute name="GUID" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}GUIDLiterals" /&gt;
 *       &lt;attribute name="eId" use="required" type="{http://Inhaltsdaten.LegalDocML.de/1.6/}eIdLiterals.liste" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "liste",
    namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/",
    propOrder = {"listIntroduction", "item", "listWrapUp"})
public class Liste {

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected ListeEingangssatz listIntroduction;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/", required = true)
  protected List<ListeListenelement> item;

  @XmlElement(namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
  protected ListeSchlusssatz listWrapUp;

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
   * Gets the value of the listIntroduction property.
   *
   * @return possible object is {@link ListeEingangssatz }
   */
  public ListeEingangssatz getListIntroduction() {
    return listIntroduction;
  }

  /**
   * Sets the value of the listIntroduction property.
   *
   * @param value allowed object is {@link ListeEingangssatz }
   */
  public void setListIntroduction(ListeEingangssatz value) {
    this.listIntroduction = value;
  }

  /**
   * Gets the value of the item property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the Jakarta XML Binding
   * object. This is why there is not a <CODE>set</CODE> method for the item property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getItem().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link ListeListenelement }
   */
  public List<ListeListenelement> getItem() {
    if (item == null) {
      item = new ArrayList<ListeListenelement>();
    }
    return this.item;
  }

  /**
   * Gets the value of the listWrapUp property.
   *
   * @return possible object is {@link ListeSchlusssatz }
   */
  public ListeSchlusssatz getListWrapUp() {
    return listWrapUp;
  }

  /**
   * Sets the value of the listWrapUp property.
   *
   * @param value allowed object is {@link ListeSchlusssatz }
   */
  public void setListWrapUp(ListeSchlusssatz value) {
    this.listWrapUp = value;
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
