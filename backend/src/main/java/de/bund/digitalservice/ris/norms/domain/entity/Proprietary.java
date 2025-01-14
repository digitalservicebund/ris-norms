package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;

/**
 * Encapsulates metadata that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to DigitalService.
 */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Proprietary {

  private final Element element;

  /**
   * Retrieves the optional {@link MetadatenDe} instance from the {@link Proprietary}.
   *
   * @return an optional with a {@link MetadatenDe}
   */
  public Optional<MetadatenDe> getMetadatenDe() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://Metadaten.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenDe::new);
  }

  /**
   * Retrieves the {@link MetadatenDe} instance from the {@link Proprietary}.
   *
   * @return the retrieved {@link MetadatenDe} or the newly created one.
   */
  public MetadatenDe getOrCreateMetadatenDe() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://Metadaten.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenDe::new)
      .orElseGet(() -> {
        final var newElement = NodeCreator.createElement(
          Namespace.METADATEN,
          "legalDocML.de_metadaten",
          element
        );
        return new MetadatenDe(newElement);
      });
  }

  /**
   * Retrieves the optional {@link MetadatenDs} instance from the {@link Proprietary}.
   *
   * @return an optional with a {@link MetadatenDs}
   */
  public Optional<MetadatenDs> getMetadatenDs() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://MetadatenRIS.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenDs::new);
  }

  /**
   * Retrieves the {@link MetadatenDs} instance from the {@link Proprietary}.
   *
   * @return the retrieved {@link MetadatenDs} or the newly created one.
   */
  public MetadatenDs getOrCreateMetadatenDs() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://MetadatenRIS.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenDs::new)
      .orElseGet(() -> {
        final var newElement = NodeCreator.createElement(
          Namespace.METADATEN_RIS,
          "legalDocML.de_metadaten",
          element
        );
        return new MetadatenDs(newElement);
      });
  }

  /**
   * Retrieves the optional {@link MetadatenBund} instance from the {@link Proprietary}.
   *
   * @return an optional with a {@link MetadatenBund}
   */
  public Optional<MetadatenBund> getMetadatenBund() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://MetadatenBundesregierung.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenBund::new);
  }

  /**
   * Retrieves the {@link MetadatenBund} instance from the {@link Proprietary}.
   *
   * @return the retrieved {@link MetadatenBund} or the newly created one.
   */
  public MetadatenBund getOrCreateMetadatenBund() {
    return NodeParser
      .getElementFromExpression(
        "./Q{http://MetadatenBundesregierung.LegalDocML.de/1.7.1/}legalDocML.de_metadaten",
        element
      )
      .map(MetadatenBund::new)
      .orElseGet(() -> {
        final var newElement = NodeCreator.createElement(
          Namespace.METADATEN_BUNDESREGIERUNG,
          "legalDocML.de_metadaten",
          element
        );
        return new MetadatenBund(newElement);
      });
  }

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm from the MetadatenDe block.
   *
   * @return FNA or empty if it doesn't exist.
   */
  public Optional<String> getFna() {
    return getMetadatenDe().flatMap(MetadatenDe::getFna);
  }

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm from the MetadatenDs block at a specific
   * date, if empty it will get the one from MetadatenDe.
   *
   * @param date the specific date of the time boundary.
   * @return FNA or empty if present neither in the MetadatenDe nor in the MetadatenDs block.
   */
  public Optional<String> getFna(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.FNA, date))
      .or(this::getFna);
  }

  private Optional<String> getArt() {
    return getMetadatenDe().flatMap(MetadatenDe::getArt);
  }

  /**
   * Returns the Art ("Art der Norm") of the norm from the MetadatenDs block at a specific date, if
   * empty it will get the one from MetadatenDe.
   *
   * @param date the specific date of the time boundary.
   * @return Art or empty if present neither in the MetadatenDe nor in the MetadatenDs block.
   */
  public Optional<String> getArt(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.ART, date))
      .or(this::getArt);
  }

  private Optional<String> getTyp() {
    return getMetadatenDe().flatMap(MetadatenDe::getTyp);
  }

  /**
   * Returns the type ("Typ des Dokuments") of the document from the MetadatenDs block at a specific
   * date, if empty it will get the one from MetadatenDe.
   *
   * @param date the specific date of the time boundary.
   * @return Typ or empty if present neither in the MetadatenDe nor in the MetadatenDs block.
   */
  public Optional<String> getTyp(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.TYP, date))
      .or(this::getTyp);
  }

  /**
   * Returns the subtype ("Subtyp") of the document from the MetadatenDs block at a specific date.
   *
   * @param date the specific date of the time boundary.
   * @return Subtyp or empty if it doesn't exist.
   */
  public Optional<String> getSubtyp(final LocalDate date) {
    return getMetadatenDs().flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.SUBTYP, date));
  }

  /**
   * Returns the designation according to specification ("Bezeichnung gemäß Vorlage") of the
   * document from the MetadatenDs block at a specific date.
   *
   * @param date the specific date of the time boundary.
   * @return "Bezeichnung gemäß Vorlage" or empty if it doesn't exist.
   */
  public Optional<String> getBezeichnungInVorlage(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.BEZEICHNUNG_IN_VORLAGE, date));
  }

  /**
   * Returns the ("Art der Norm") which can be "SN and/or ÄN and/or ÜN" of the document from the
   * MetadatenDs block at a specific date. Check metadata.xsd for String regex
   *
   * @param date the specific date of the time boundary.
   * @return "Art der Norm" or empty if it doesn't exist.
   */
  public Optional<String> getArtDerNorm(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.ART_DER_NORM, date));
  }

  /**
   * Returns the ("Art der Norm") which can be "SN or ÄN or ÜN" of the document from the MetadatenDs
   * block for a single element at a specific date.
   *
   * @param date the specific date of the time boundary.
   * @param eid the eId of the single element
   * @return "Art der Norm" or empty if it doesn't exist.
   */
  public Optional<String> getArtDerNorm(final LocalDate date, final String eid) {
    return getMetadatenDs()
      .flatMap(m ->
        m.getSingleElementSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, eid, date)
      );
  }

  /**
   * Returns the ("Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder
   * Rechtsmacht die Norm trägt") of the document from the MetadatenDs block at a specific date.
   *
   * @param date the specific date of the time boundary.
   * @return "Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder
   *     Rechtsmacht die Norm trägt" or empty if it doesn't exist.
   */
  public Optional<String> getStaat(final LocalDate date) {
    return getMetadatenDs().flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.STAAT, date));
  }

  /**
   * Returns the ("Beschließendes Organ") of the document from the MetadatenDs block at a specific
   * date.
   *
   * @param date the specific date of the time boundary.
   * @return "Beschließendes Organ" or empty if it doesn't exist.
   */
  public Optional<String> getBeschliessendesOrgan(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.BESCHLIESSENDES_ORGAN, date));
  }

  /**
   * Returns the ("Qualifizierte Mehrheit") attribute value of the ("Qualifizierte Mehrheit") of the
   * document from the MetadatenDs block at a specific date.
   *
   * @param date the specific date of the time boundary.
   * @return "Qualifizierte Mehrheit" true/false or empty if "Qualifizierte Mehrheit" doesn't exist.
   */
  public Optional<Boolean> getQualifizierteMehrheit(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m ->
        m
          .getAttributeOfSimpleMetadatumAt(MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, date)
          .map(Boolean::parseBoolean)
      );
  }

  /**
   * Returns the ("Ressort") of the document from the MetadatenBund block at a specific
   * date.
   *
   * @param date the specific date of the time boundary.
   * @return "Ressort" or empty if it doesn't exist.
   */
  public Optional<String> getRessort(final LocalDate date) {
    return getMetadatenBund()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, date));
  }

  /**
   * Returns the ("Organisationseinheit") of the document from the MetadatenDs block at a specific
   * date.
   *
   * @param date the specific date of the time boundary.
   * @return "Organisationseinheit" or empty if it doesn't exist.
   */
  public Optional<String> getOrganisationsEinheit(final LocalDate date) {
    return getMetadatenDs()
      .flatMap(m -> m.getSimpleMetadatum(MetadatenDs.Metadata.ORGANISATIONS_EINHEIT, date));
  }
}
