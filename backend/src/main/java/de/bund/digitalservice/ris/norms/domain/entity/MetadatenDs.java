package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
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
    NORMGEBER("./normgeber"),
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
}
