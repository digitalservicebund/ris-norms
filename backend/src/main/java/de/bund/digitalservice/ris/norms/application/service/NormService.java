package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
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
import org.springframework.transaction.annotation.Transactional;

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
    LoadZielnormenExpressionsUseCase,
    CreateZielnormenExpressionsUseCase,
    UpdateZielnormReferencesUseCase,
    DeleteZielnormReferencesUseCase {

  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateNormPort updateNormPort;
  private final LoadRegelungstextPort loadRegelungstextPort;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final EliService eliService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeleteNormPort deleteNormPort;

  public NormService(
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    UpdateNormPort updateNormPort,
    LoadRegelungstextPort loadRegelungstextPort,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    EliService eliService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    DeleteNormPort deleteNormPort
  ) {
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateNormPort = updateNormPort;
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.eliService = eliService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deleteNormPort = deleteNormPort;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Options options) {
    return switch (options) {
      case EliOptions(NormEli eli) -> loadNormPort
        .loadNorm(new LoadNormPort.Options(eli))
        .orElseThrow(() -> new NormNotFoundException(eli));
      case GuidOptions(UUID guid) -> loadNormByGuidPort
        .loadNormByGuid(new LoadNormByGuidPort.Options(guid))
        .orElseThrow(() -> new NormNotFoundException(guid));
    };
  }

  @Override
  public Regelungstext loadRegelungstext(final LoadRegelungstextUseCase.Options options) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));
  }

  @Override
  public String loadRegelungstextXml(final LoadRegelungstextXmlUseCase.Options options) {
    final Regelungstext regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));

    return XmlMapper.toString(regelungstext.getDocument());
  }

  @Override
  public String updateRegelungstextXml(UpdateRegelungstextXmlUseCase.Options options) {
    var regelungstextToBeUpdated = new Regelungstext(XmlMapper.toDocument(options.xml()));

    var existingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Options(options.eli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(options.eli().asNormEli()));
    var existingRegelungstext = existingNorm
      .getRegelungstextByEli(options.eli())
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));

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

    var updatedNorm = updateNorm(existingNorm).get(options.eli().asNormEli());
    var updatedRegelungstext = updatedNorm.getRegelungstextByEli(options.eli()).orElseThrow();

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
      .updateNorm(new UpdateNormPort.Options(normToBeUpdated))
      .orElseThrow(() -> new NormNotFoundException(normToBeUpdated.getManifestationEli()));

    return Map.of(normToBeUpdated.getExpressionEli(), savedNorm);
  }

  @Override
  public List<ZielnormReference> loadZielnormReferences(
    LoadZielnormReferencesUseCase.Options options
  ) {
    return loadNorm(new EliOptions(options.eli()))
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
    UpdateZielnormReferencesUseCase.Options options
  ) {
    var norm = loadNorm(new EliOptions(options.eli()));
    var zielnormReferences = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateZielnormenReferences();

    options
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
    DeleteZielnormReferencesUseCase.Options options
  ) {
    var norm = loadNorm(new EliOptions(options.eli()));
    var proprietary = norm.getRegelungstext1().getMeta().getOrCreateProprietary();
    var customModsMetadata = proprietary.getOrCreateCustomModsMetadata();
    var zielnormReferences = customModsMetadata.getOrCreateZielnormenReferences();

    options
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
  @Transactional
  public Zielnorm createZielnormExpressions(CreateZielnormenExpressionsUseCase.Options options) {
    final List<Zielnorm> zielNormenPreview = loadZielnormExpressions(
      new LoadZielnormenExpressionsUseCase.Options(options.verkuendungEli())
    );
    final Norm verkuendungNorm = loadNorm(new LoadNormUseCase.EliOptions(options.verkuendungEli()));
    final Zielnorm affectedNorm = zielNormenPreview
      .stream()
      .filter(f -> f.normWorkEli().equals(options.affectedWorkEli()))
      .findFirst()
      .orElseThrow(() ->
        new IllegalStateException(
          String.format("Affected norm with %s not found", options.affectedWorkEli())
        )
      );
    return createZielNormen(verkuendungNorm, affectedNorm);
  }

  @Override
  public List<Zielnorm> loadZielnormExpressions(LoadZielnormenExpressionsUseCase.Options options) {
    return loadZielnormenPreview(options.verkuendungEli());
  }

  private List<Zielnorm> loadZielnormenPreview(final NormExpressionEli eli) {
    var verkuendungNorm = loadNorm(new LoadNormUseCase.EliOptions(eli));

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
        var latestZielnormExpression = loadNorm(new EliOptions(zielnormWorkEli));
        return new Zielnorm(
          zielnormWorkEli,
          latestZielnormExpression.getTitle().orElse(null),
          latestZielnormExpression.getShortTitle().orElse(null),
          generateZielnormPreviewExpressions(verkuendungNorm, zielnormWorkEli)
        );
      })
      .toList();
  }

  /**
   * Generate the preview list of expressions for a specific Zielnorm <br>
   * This list includes the expressions that needs to be created for the dates of the changes of the
   * Verkündung, as well as the existing expression that need to be set to gegenstandlos and the
   * expressions replacing these. <br>
   * Every existing expression of a date after the first change to the Zielnorm needs to be set to
   * gegenstandlos and a new expression needs to be created as this expression than needs to include
   * the changes of the now gegenstandlose expression as well as the once from the previous changes
   * due to the Verkündung.
   */
  private List<Zielnorm.Expression> generateZielnormPreviewExpressions(
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

    List<Zielnorm.Expression> expressions = new ArrayList<>();

    final Optional<AmendedNormExpressions> affectedExpressionElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getAmendedNormExpressions);

    relevantExistingExpressions.forEach(expression -> {
      if (affectedExpressionElis.isPresent() && affectedExpressionElis.get().contains(expression)) {
        expressions.add(
          new Zielnorm.Expression(expression, false, true, Zielnorm.CreatedBy.THIS_VERKUENDUNG)
        );
      } else {
        expressions.add(
          new Zielnorm.Expression(expression, true, true, Zielnorm.CreatedBy.OTHER_VERKUENDUNG)
        );
        expressions.add(
          new Zielnorm.Expression(
            eliService.findNextExpressionEli(
              expression.asWorkEli(),
              expression.getPointInTime(),
              expression.getLanguage()
            ),
            false,
            false,
            Zielnorm.CreatedBy.SYSTEM
          )
        );
      }
    });

    geltungszeiten
      .stream()
      .sorted()
      .forEach(date -> {
        // find a possible already created entry that was created as a replacement for an
        // existing expression. If we also created such an entry for a Geltungszeitregel we
        // need to change the createdBy to "diese Verkündung"
        var existingEntry = expressions
          .stream()
          .filter(
            expression ->
              expression.normExpressionEli().getPointInTime().equals(date) &&
              expression.normExpressionEli().getLanguage().equals("deu") &&
              expression.createdBy().equals(Zielnorm.CreatedBy.SYSTEM)
          )
          .findFirst();

        if (existingEntry.isPresent()) {
          expressions.remove(existingEntry.get());
          expressions.add(
            new Zielnorm.Expression(
              existingEntry.get().normExpressionEli(),
              false,
              false,
              Zielnorm.CreatedBy.THIS_VERKUENDUNG
            )
          );
        } else {
          // Only add new not-yet-created expressions if they are really not yet created
          var alreadyCreated = expressions
            .stream()
            .filter(
              expression ->
                expression.normExpressionEli().getPointInTime().equals(date) &&
                expression.normExpressionEli().getLanguage().equals("deu") &&
                expression.createdBy().equals(Zielnorm.CreatedBy.THIS_VERKUENDUNG)
            )
            .findFirst();
          if (alreadyCreated.isEmpty()) {
            expressions.add(
              new Zielnorm.Expression(
                eliService.findNextExpressionEli(zielnormWorkEli, date, "deu"),
                false,
                false,
                Zielnorm.CreatedBy.THIS_VERKUENDUNG
              )
            );
          }
        }
      });
    return expressions
      .stream()
      .sorted(Comparator.comparing(Zielnorm.Expression::normExpressionEli))
      .toList();
  }

  /** Find the dates of all geltungszeiten that are used by changes of the specific zielnorm */
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
   * Collect all expressions that need to be overwritten when creating the expressions for the
   * Verkündung. This includes all non-gegenstandlose expressions after (or at) the first
   * geltungszeitgrenze.
   */
  private List<NormExpressionEli> collectRelevantExistingExpressions(
    NormWorkEli zielnormWorkEli,
    LocalDate earliestGeltungszeit
  ) {
    return loadNormExpressionElisPort
      .loadNormExpressionElis(new LoadNormExpressionElisPort.Options(zielnormWorkEli))
      .stream()
      .filter(normExpressionEli ->
        normExpressionEli.getPointInTime().isAfter(earliestGeltungszeit.minusDays(1))
      )
      .flatMap(normExpressionEli ->
        loadNormPort.loadNorm(new LoadNormPort.Options(normExpressionEli)).stream()
      )
      .filter(Predicate.not(Norm::isGegenstandlos))
      .map(Norm::getExpressionEli)
      .toList();
  }

  private Zielnorm createZielNormen(final Norm verkuendungNorm, final Zielnorm zielnorm) {
    final AmendedNormExpressions amendedNormExpressions = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateAmendedNormExpressions();

    zielnorm
      .expressions()
      .forEach(expression -> {
        if (
          expression.isCreated() && amendedNormExpressions.contains(expression.normExpressionEli())
        ) {
          final Norm norm = loadNormPort
            .loadNorm(new LoadNormPort.Options(expression.normExpressionEli()))
            .orElseThrow(() ->
              new IllegalStateException(
                String.format("Norm %s must exist", expression.normExpressionEli())
              )
            );
          // Override by creating new expression from closest previous and replacing the already created one
          final Norm previousClosestExpression = findPreviousClosestExistingExpression(
            zielnorm.normWorkEli(),
            expression.normExpressionEli().getPointInTime()
          ).orElseThrow(() -> new IllegalStateException("Previous closest expression not found"));
          final CreateNewVersionOfNormService.CreateNewExpressionResult result =
            createNewVersionOfNormService.createNewOverridenExpression(
              previousClosestExpression,
              norm
            );
          updateOrSaveNormPort.updateOrSave(
            new UpdateOrSaveNormPort.Options(result.newExpression())
          );
          updateOrSaveNormPort.updateOrSave(
            new UpdateOrSaveNormPort.Options(result.newManifestationOfOldExpression())
          );
        } else {
          // Take previous closest already-created expression (there must be at least 1)
          final Norm previousClosestExpression = findPreviousClosestExistingExpression(
            zielnorm.normWorkEli(),
            expression.normExpressionEli().getPointInTime()
          ).orElseThrow(() -> new IllegalStateException("Previous closest expression not found"));

          // Check if the previous closest is actually one that should be set to gegenstandslos
          boolean isGegenstandslos = zielnorm
            .expressions()
            .stream()
            .anyMatch(
              f ->
                f.normExpressionEli().equals(previousClosestExpression.getExpressionEli()) &&
                f.isGegenstandslos()
            );
          final CreateNewVersionOfNormService.CreateNewExpressionResult result = isGegenstandslos
            ? createNewVersionOfNormService.createNewExpression(
              previousClosestExpression,
              expression.normExpressionEli().getPointInTime(),
              verkuendungNorm.getRegelungstext1().getMeta().getFRBRWork().getFBRDate()
            )
            : createNewVersionOfNormService.createNewExpression(
              previousClosestExpression,
              expression.normExpressionEli().getPointInTime()
            );
          updateOrSaveNormPort.updateOrSave(
            new UpdateOrSaveNormPort.Options(result.newExpression())
          );
          updateOrSaveNormPort.updateOrSave(
            new UpdateOrSaveNormPort.Options(result.newManifestationOfOldExpression())
          );
          // Add expression eli of new expression into amended-expressions
          amendedNormExpressions.add(result.newExpression().getExpressionEli());
        }
      });
    // remove orphan entries of amended-expressions
    amendedNormExpressions
      .stream()
      .filter(f ->
        !zielnorm
          .expressions()
          .stream()
          .map(Zielnorm.Expression::normExpressionEli)
          .toList()
          .contains(f)
      )
      .forEach(normExpressionEli -> {
        loadNormPort
          .loadNorm(new LoadNormPort.Options(normExpressionEli))
          .ifPresent(normLoaded ->
            deleteNormPort.deleteNorm(
              new DeleteNormPort.Options(
                normLoaded.getManifestationEli(),
                NormPublishState.UNPUBLISHED
              )
            )
          );
        amendedNormExpressions.remove(normExpressionEli);
      });

    // Save Verkündung because of updated amended-expressions
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(verkuendungNorm));

    return new Zielnorm(
      zielnorm.normWorkEli(),
      zielnorm.title(),
      zielnorm.shortTitle(),
      zielnorm
        .expressions()
        .stream()
        .map(expr ->
          new Zielnorm.Expression(
            expr.normExpressionEli(),
            expr.isGegenstandslos(),
            true,
            expr.createdBy()
          )
        )
        .toList()
    );
  }

  /**
   * Get the previous closest non-gegenstandslos norm to the passed date.
   */
  private Optional<Norm> findPreviousClosestExistingExpression(
    NormWorkEli zielnormWorkEli,
    LocalDate dateForNewExpression
  ) {
    return loadNormExpressionElisPort
      .loadNormExpressionElis(new LoadNormExpressionElisPort.Options(zielnormWorkEli))
      .stream()
      .filter(eli -> eli.getPointInTime().isBefore(dateForNewExpression))
      .map(eli ->
        new AbstractMap.SimpleEntry<>(eli, loadNormPort.loadNorm(new LoadNormPort.Options(eli)))
      )
      .filter(entry -> entry.getValue().isPresent()) // keep only existing Norms
      .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().get()))
      .filter(entry -> !entry.getValue().isGegenstandlos())
      .max(Map.Entry.comparingByKey())
      .map(Map.Entry::getValue);
  }
}
