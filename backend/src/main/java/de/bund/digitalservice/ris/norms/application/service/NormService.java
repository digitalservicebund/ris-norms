package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class NormService
  implements
    LoadNormUseCase,
    LoadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase,
    LoadRegelungstextUseCase,
    LoadZielnormReferencesUseCase,
    LoadZielnormenPreviewUseCase,
    UpdateZielnormReferencesUseCase,
    DeleteZielnormReferencesUseCase {

  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateNormPort updateNormPort;
  private final LoadRegelungstextPort loadRegelungstextPort;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final EliService eliService;

  public NormService(
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    UpdateNormPort updateNormPort,
    LoadRegelungstextPort loadRegelungstextPort,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    EliService eliService
  ) {
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateNormPort = updateNormPort;
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.eliService = eliService;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Query query) {
    return switch (query) {
      case EliQuery(NormEli eli) -> loadNormPort
        .loadNorm(new LoadNormPort.Command(eli))
        .orElseThrow(() -> new NormNotFoundException(eli.toString()));
      case GuidQuery(UUID guid) -> loadNormByGuidPort
        .loadNormByGuid(new LoadNormByGuidPort.Command(guid))
        .orElseThrow(() -> new NormNotFoundException(guid.toString()));
    };
  }

  @Override
  public Regelungstext loadRegelungstext(final LoadRegelungstextUseCase.Query query) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));
  }

  @Override
  public String loadRegelungstextXml(final LoadRegelungstextXmlUseCase.Query query) {
    final Regelungstext regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    return XmlMapper.toString(regelungstext.getDocument());
  }

  @Override
  public String updateRegelungstextXml(UpdateRegelungstextXmlUseCase.Query query) {
    var regelungstextToBeUpdated = new Regelungstext(XmlMapper.toDocument(query.xml()));

    var existingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().asNormEli().toString()));
    var existingRegelungstext = existingNorm
      .getRegelungstextByEli(query.eli())
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    if (
      !existingRegelungstext.getExpressionEli().equals(regelungstextToBeUpdated.getExpressionEli())
    ) {
      throw new InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingRegelungstext.getGuid().equals(regelungstextToBeUpdated.getGuid())) {
      throw new InvalidUpdateException("Changing the GUID is not supported.");
    }

    var regelungstexte = new HashSet<>(existingNorm.getRegelungstexte());
    regelungstexte.remove(existingRegelungstext);
    regelungstexte.add(regelungstextToBeUpdated);
    existingNorm.setRegelungstexte(regelungstexte);

    var updatedNorm = updateNorm(existingNorm).get(query.eli().asNormEli());
    var updatedRegelungstext = updatedNorm.getRegelungstextByEli(query.eli()).orElseThrow();

    return XmlMapper.toString(updatedRegelungstext.getDocument());
  }

  /**
   * It not only saves a {@link Norm} but makes sure that all Eids are consistent.
   *
   * @param normToBeUpdated the norm which shall be saved
   * @return An {@link Map} containing the updated and saved {@link Norm}
   * @throws NormNotFoundException if the norm cannot be found
   */
  public Map<NormExpressionEli, Norm> updateNorm(Norm normToBeUpdated) {
    normToBeUpdated
      .getDokumente()
      .forEach(dokument -> {
        EidConsistencyGuardian.eliminateDeadReferences(dokument.getDocument());
        EidConsistencyGuardian.correctEids(dokument.getDocument());
      });

    Norm savedNorm = updateNormPort
      .updateNorm(new UpdateNormPort.Command(normToBeUpdated))
      .orElseThrow(() -> new NormNotFoundException(normToBeUpdated.getManifestationEli().toString())
      );

    return Map.of(normToBeUpdated.getExpressionEli(), savedNorm);
  }

  @Override
  public List<ZielnormReference> loadZielnormReferences(LoadZielnormReferencesUseCase.Query query) {
    return loadNorm(new LoadNormUseCase.EliQuery(query.eli()))
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .toList();
  }

  @Override
  public List<ZielnormReference> updateZielnormReferences(
    UpdateZielnormReferencesUseCase.Query query
  ) {
    var norm = loadNorm(new LoadNormUseCase.EliQuery(query.eli()));
    var zielnormReferences = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateZielnormenReferences();

    query
      .zielnormReferences()
      .forEach(zielnormReferenceUpdateData -> {
        var zielnormReference = zielnormReferences
          .stream()
          .filter(reference -> reference.getEId().equals(zielnormReferenceUpdateData.eId()))
          .findFirst()
          .orElseGet(() ->
            zielnormReferences.add(
              zielnormReferenceUpdateData.typ(),
              zielnormReferenceUpdateData.geltungszeit(),
              zielnormReferenceUpdateData.eId(),
              zielnormReferenceUpdateData.zielnorm()
            )
          );

        zielnormReference.setTyp(zielnormReferenceUpdateData.typ());
        zielnormReference.setGeltungszeit(zielnormReferenceUpdateData.geltungszeit());
        zielnormReference.setZielnorm(zielnormReferenceUpdateData.zielnorm());
      });

    updateNorm(norm);

    return zielnormReferences.stream().toList();
  }

  @Override
  public List<ZielnormReference> deleteZielnormReferences(
    DeleteZielnormReferencesUseCase.Query query
  ) {
    var norm = loadNorm(new LoadNormUseCase.EliQuery(query.eli()));
    var proprietary = norm.getRegelungstext1().getMeta().getOrCreateProprietary();
    var customModsMetadata = proprietary.getOrCreateCustomModsMetadata();
    var zielnormReferences = customModsMetadata.getOrCreateZielnormenReferences();

    query
      .zielnormReferenceEIds()
      .forEach(eId ->
        zielnormReferences
          .stream()
          .filter(reference -> reference.getEId().equals(eId))
          .forEach(zielnormReferences::remove)
      );

    if (zielnormReferences.isEmpty()) {
      customModsMetadata.removeZielnormenReferences();
    }
    proprietary.removeMetadataParentIfEmpty(Namespace.METADATEN_NORMS_APPLICATION_MODS);

    updateNorm(norm);

    return zielnormReferences.stream().toList();
  }

  @Override
  public List<ZielnormPreview> loadZielnormenPreview(LoadZielnormenPreviewUseCase.Query query) {
    var verkuendungNorm = loadNorm(new LoadNormUseCase.EliQuery(query.eli()));

    List<NormWorkEli> zielnormWorkElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .map(ZielnormReference::getZielnorm)
      .distinct()
      .toList();

    return zielnormWorkElis
      .stream()
      .map(zielnormWorkEli -> {
        var latestZielnormExpression = loadNorm(new LoadNormUseCase.EliQuery(zielnormWorkEli));
        return new ZielnormPreview(
          zielnormWorkEli,
          latestZielnormExpression.getTitle().orElse(null),
          latestZielnormExpression.getShortTitle().orElse(null),
          generateZielnormPreviewExpressions(verkuendungNorm, zielnormWorkEli)
        );
      })
      .toList();
  }

  /**
   * Generate the preview list of expressions for a specific Zielnorm
   * <br/>
   * This list includes the expressions that needs to be created for the dates of the changes of the Verkündung, as well
   * as the existing expression that need to be set to gegenstandlos and the expressions replacing these.
   * <br/>
   * Every existing expression of a date after the first change to the Zielnorm needs to be set to gegenstandlos and a
   * new expression needs to be created as this expression than needs to include the changes of the now gegenstandlose
   * expression as well as the once from the previous changes due to the Verkündung.
   */
  private List<ZielnormPreview.Expression> generateZielnormPreviewExpressions(
    Norm verkuendungNorm,
    NormWorkEli zielnormWorkEli
  ) {
    var geltungszeiten = findGeltungszeitenForZielnorm(verkuendungNorm, zielnormWorkEli);

    var earliestGeltungszeit = geltungszeiten.stream().sorted().findFirst();

    if (earliestGeltungszeit.isEmpty()) {
      return List.of();
    }

    var relevantExistingExpressions = collectRelevantExistingExpressions(
      zielnormWorkEli,
      earliestGeltungszeit.get()
    );

    List<ZielnormPreview.Expression> expressions = new ArrayList<>();

    relevantExistingExpressions.forEach(expression -> {
      expressions.add(
        new ZielnormPreview.Expression(
          expression,
          true,
          true,
          ZielnormPreview.CreatedBy.OTHER_VERKUENDUNG
        )
      );

      expressions.add(
        new ZielnormPreview.Expression(
          eliService.findNextExpressionEli(
            expression.asWorkEli(),
            expression.getPointInTime(),
            expression.getLanguage()
          ),
          false,
          false,
          ZielnormPreview.CreatedBy.SYSTEM
        )
      );
    });

    geltungszeiten
      .stream()
      .sorted()
      .forEach(date -> {
        // find a possible already created entry that was created as a replacement for an existing expression. If we also created such an entry for a Geltungszeitregel we need to change the createdBy to "diese Verkündung"
        var existingEntry = expressions
          .stream()
          .filter(
            expression ->
              expression.normExpressionEli().getPointInTime().equals(date) &&
              expression.normExpressionEli().getLanguage().equals("deu") &&
              expression.createdBy().equals(ZielnormPreview.CreatedBy.SYSTEM)
          )
          .findFirst();

        if (existingEntry.isPresent()) {
          expressions.remove(existingEntry.get());
          expressions.add(
            new ZielnormPreview.Expression(
              existingEntry.get().normExpressionEli(),
              false,
              false,
              ZielnormPreview.CreatedBy.THIS_VERKUENDUNG
            )
          );
        } else {
          expressions.add(
            new ZielnormPreview.Expression(
              eliService.findNextExpressionEli(zielnormWorkEli, date, "deu"),
              false,
              false,
              ZielnormPreview.CreatedBy.THIS_VERKUENDUNG
            )
          );
        }
      });

    return expressions
      .stream()
      .sorted(Comparator.comparing(ZielnormPreview.Expression::normExpressionEli))
      .toList();
  }

  /**
   * Find the dates of all geltungszeiten that are used by changes of the specific zielnorm
   */
  private List<LocalDate> findGeltungszeitenForZielnorm(
    Norm verkuendungNorm,
    NormWorkEli zielnormWorkEli
  ) {
    return verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .filter(zielnormReference -> zielnormReference.getZielnorm().equals(zielnormWorkEli))
      .map(ZielnormReference::getGeltungszeit)
      .distinct()
      .map(geltungszeitId ->
        verkuendungNorm
          .getRegelungstext1()
          .getZeitgrenzen()
          .stream()
          .filter(geltungszeit -> geltungszeit.getId().equals(geltungszeitId))
          .findFirst()
          .orElseThrow(RuntimeException::new)
          .getDate()
      )
      .toList();
  }

  /**
   * Collect all expressions that need to be overwritten when creating the expressions for the Verkündung.
   * This includes all non-gegenstandlose expressions after (or at) the first geltungszeitgrenze.
   */
  private List<NormExpressionEli> collectRelevantExistingExpressions(
    NormWorkEli zielnormWorkEli,
    LocalDate earliestGeltungszeit
  ) {
    return loadNormExpressionElisPort
      .loadNormExpressionElis(new LoadNormExpressionElisPort.Command(zielnormWorkEli))
      .stream()
      .filter(normExpressionEli ->
        normExpressionEli.getPointInTime().isAfter(earliestGeltungszeit.minusDays(1))
      )
      .flatMap(normExpressionEli ->
        loadNormPort.loadNorm(new LoadNormPort.Command(normExpressionEli)).stream()
      )
      .filter(Predicate.not(Norm::isGegenstandlos))
      .map(Norm::getExpressionEli)
      .toList();
  }
}
