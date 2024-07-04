package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten_ds */
@Getter
public class MetadatenDs extends Metadaten<MetadatenDs.Metadata> {

  @Builder
  public MetadatenDs(final Node node) {
    super(node, "start", "end");
  }

  /**
   * The list of all simple metadata within the meta:legalDocML.de_metadaten_ds block. They consist
   * of a single string property.
   */
  public enum Metadata implements MetadataInterface {
    FNA("./fna"),
    ART("./art"),
    TYP("./typ"),
    SUBTYP("./subtyp"),
    BEZEICHNUNG_IN_VORLAGE("./bezeichnungInVorlage"),
    ART_DER_NORM("./artDerNorm"),
    STAAT("./normgeber"),
    BESCHLIESSENDES_ORGAN("./beschliessendesOrgan"),
    ORGANISATIONS_EINHEIT("./organisationsEinheit");

    private final String xpath;

    Metadata(final String xpath) {
      this.xpath = xpath;
    }

    @Override
    public String getXpath() {
      return this.xpath;
    }
  }

  /**
   * The list of all attribute. They consist of the enum {@link Metadata} they belong to and its
   * name.
   */
  public enum Attribute {
    QUALIFIZIERTE_MEHRHEIT(Metadata.BESCHLIESSENDES_ORGAN, "qualifizierteMehrheit");

    private final Metadata simpleMetadatum;
    private final String name;

    Attribute(final Metadata simpleMetadatum, final String name) {
      this.simpleMetadatum = simpleMetadatum;
      this.name = name;
    }
  }

  /**
   * It returns the value of the attribute by retrieving first the belonging metadatum by using
   * getSimpleMetadatum
   *
   * @param attribute the attribute to be retrieved
   * @param date the specific date
   * @return an optional string value of the attribute, if found
   */
  public Optional<String> getAttributeOfSimpleMetadatumAt(
      final MetadatenDs.Attribute attribute, final LocalDate date) {
    return getSimpleNodeAt(attribute.simpleMetadatum, date)
        .flatMap(m -> m.getAttribute(attribute.name));
  }

  /**
   * Creates or updates an attribute of a simple node with the new passed value.
   *
   * @param attribute the attribute to be created/updated
   * @param date the specific date
   * @param newValue the new value
   */
  public void setAttributeOfSimpleMetadatum(
      final MetadatenDs.Attribute attribute, final LocalDate date, final String newValue) {
    getSimpleNodeAt(attribute.simpleMetadatum, date)
        .ifPresent(
            m -> {
              if (m.getValue().isEmpty()) {
                m.removeAttribute(attribute.name);
              } else {
                m.setAttribute(attribute.name, newValue);
              }
            });
  }

  /**
   * It returns the value for a specific metadata from a single element referenced by its eid for a
   * specific date.
   *
   * @param metadatumSingleElement the enum metadatum
   * @param eid the eid of the single element
   * @param date the selected date
   * @return value of the requested metadata element or empty if it doesn't exist
   */
  public Optional<String> getSingleElementSimpleMetadatum(
      final Einzelelement.Metadata metadatumSingleElement, final String eid, final LocalDate date) {
    return NodeParser.getNodeFromExpression(
            "./einzelelement[@href='#%s']".formatted(eid), this.getNode())
        .flatMap(node -> new Einzelelement(node).getSimpleMetadatum(metadatumSingleElement, date));
  }

  /**
   * It updates the value for a specific metadata from a single element referenced by its eid for a
   * specific date. If that specific single element was not introduced before, this method will
   * create an "einzelement" node while referencing its origin eid in the href attribute.
   *
   * @param metadatumSingleElement the enum metadatum
   * @param eid the eid of the single element
   * @param date the selected date
   * @param newValue the new value
   */
  public void updateSingleElementSimpleMetadatum(
      final Einzelelement.Metadata metadatumSingleElement,
      final String eid,
      final LocalDate date,
      final String newValue) {
    NodeParser.getNodeFromExpression("./einzelelement[@href='#%s']".formatted(eid), this.getNode())
        .ifPresentOrElse(
            nodeFound -> {
              Einzelelement e = new Einzelelement(nodeFound);
              e.updateSimpleMetadatum(metadatumSingleElement, date, newValue);

              var nodeList = nodeFound.getChildNodes();
              boolean nodeIsEmpty = true;

              // all child nodes need to be not of type ELEMENT_NODE
              for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                  nodeIsEmpty = false;
                  break;
                }
              }

              if (nodeIsEmpty) {
                nodeFound.getParentNode().removeChild(nodeFound);
              }
            },
            () -> {
              if (StringUtils.isNotEmpty(newValue)) {
                // Create and set the new element
                final Element newElement =
                    NodeCreator.createElement("einzelelement", this.getNode());
                newElement.setAttribute("href", "#%s".formatted(eid));
                new Einzelelement(newElement)
                    .updateSimpleMetadatum(metadatumSingleElement, date, newValue);
              }
            });
  }
}
