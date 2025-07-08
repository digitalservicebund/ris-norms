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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Slf4j
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
    DeleteZielnormReferencesUseCase,
    LoadNormWorksUseCase,
    LoadExpressionsOfNormWorkUseCase {

  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final LoadRegelungstextPort loadRegelungstextPort;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final EliService eliService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeleteNormPort deleteNormPort;
  private final LoadNormWorksPort loadNormWorksPort;
  private final LoadExpressionsOfNormWorkPort loadExpressionsOfNormWorkPort;
  private final LdmlDeElementSorter ldmlDeElementSorter;
  private final LdmlDeValidator ldmlDeValidator;

  public NormService(
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    LoadRegelungstextPort loadRegelungstextPort,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    EliService eliService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    DeleteNormPort deleteNormPort,
    LoadNormWorksPort loadNormWorksPort,
    LoadExpressionsOfNormWorkPort loadExpressionsOfNormWorkPort,
    LdmlDeElementSorter ldmlDeElementSorter,
    LdmlDeValidator ldmlDeValidator
  ) {
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.eliService = eliService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deleteNormPort = deleteNormPort;
    this.loadNormWorksPort = loadNormWorksPort;
    this.loadExpressionsOfNormWorkPort = loadExpressionsOfNormWorkPort;
    this.ldmlDeElementSorter = ldmlDeElementSorter;
    this.ldmlDeValidator = ldmlDeValidator;
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

    var updatedNorm = updateNorm(existingNorm);
    var updatedRegelungstext = updatedNorm.getRegelungstextByEli(options.eli()).orElseThrow();

    return XmlMapper.toString(updatedRegelungstext.getDocument());
  }

  /**
   * It not only saves a {@link Norm} but also does various pre-processing steps (like fixing eIds, removing dead
   * references or sorting elements). It always saves to the working copy of the expression. This method can not be used
   * to change the publishing state.
   * If the expression does not exist yet, it is created. It does not take care of updating the timeline.
   *
   * @param normToBeUpdated the norm which shall be saved
   * @return The updated and saved {@link Norm}
   * @throws NormNotFoundException if the norm cannot be found
   */
  public Norm updateNorm(Norm normToBeUpdated) {
    var norm = createNewVersionOfNormService.createNewManifestation(
      normToBeUpdated,
      Norm.WORKING_COPY_DATE
    );

    norm
      .getDokumente()
      .forEach(dokument -> {
        EidConsistencyGuardian.eliminateDeadReferences(dokument.getDocument());
        EidConsistencyGuardian.correctEids(dokument.getDocument());
        ldmlDeElementSorter.sortElements(dokument.getDocument().getDocumentElement());
      });

    ldmlDeValidator.validateXSDSchema(norm);

    return updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(norm));
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
   * This list includes:
   * <ul>
   *    <li>the expressions that are orphans, meaning those that were already created by the Verkündung but the zielnorm
   *     reference was removed afterward. These orphans can be before the first geltungszeitregel or after it</li>
   *    <li>the expressions that needs to be created for the dates of the changes of the Verkündung</li>
   *    <li>the existing expression that need to be set to gegenstandlos and the expressions replacing these</li>
   * </ul>
   * <br>
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

    if (earliestGeltungszeit.isEmpty()) return List.of();

    var existingSortedExpressionElis = loadNormExpressionElisPort
      .loadNormExpressionElis(new LoadNormExpressionElisPort.Options(zielnormWorkEli))
      .stream()
      .flatMap(normExpressionEli ->
        loadNormPort.loadNorm(new LoadNormPort.Options(normExpressionEli)).stream()
      )
      .filter(Predicate.not(Norm::isGegenstandlos))
      .map(Norm::getExpressionEli)
      .sorted()
      .toList();

    var affectedExpressionElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getAmendedNormExpressions);

    final List<Zielnorm.Expression> expressions = new ArrayList<>();

    existingSortedExpressionElis.forEach(expressionEli -> {
      if (expressionEli.getPointInTime().isBefore(earliestGeltungszeit.get())) {
        // expressions before the first geltungszeit may have been created by this verkuendung in a previous step (possible orphans)
        if (
          affectedExpressionElis.isPresent() &&
          isOrphan(affectedExpressionElis.get(), geltungszeiten, expressionEli)
        ) {
          expressions.add(
            new Zielnorm.Expression(
              expressionEli,
              false,
              true,
              true,
              Zielnorm.CreatedBy.THIS_VERKUENDUNG
            )
          );
        }
      } else {
        // expressions after the first geltungszeit
        boolean wasAlreadyCreatedByThisVerkuendung =
          affectedExpressionElis.isPresent() &&
          affectedExpressionElis.get().contains(expressionEli);
        if (wasAlreadyCreatedByThisVerkuendung) {
          // If it was already created by this verkuendung, present under "amended-norm-expressions", could be an override or actually an orphan
          expressions.add(
            new Zielnorm.Expression(
              expressionEli,
              false,
              true,
              isOrphan(affectedExpressionElis.get(), geltungszeiten, expressionEli),
              isSystemExpression(affectedExpressionElis.get(), expressionEli)
            )
          );
        } else {
          // If it was not created by this verkuendung, it will be set to gegenstandslos and a new SYSTEM one replacing it will be created
          expressions.add(
            new Zielnorm.Expression(
              expressionEli,
              true,
              true,
              false,
              Zielnorm.CreatedBy.OTHER_VERKUENDUNG
            )
          );
          var nextEli = eliService.findNextExpressionEli(
            expressionEli.asWorkEli(),
            expressionEli.getPointInTime(),
            expressionEli.getLanguage()
          );
          expressions.add(
            new Zielnorm.Expression(nextEli, false, false, false, Zielnorm.CreatedBy.SYSTEM)
          );
        }
      }
    });

    // We iterate now the geltungszeiten to create new expressions, meaning those that do not yet exist
    geltungszeiten
      .stream()
      .sorted()
      .forEach(geltungszeit -> {
        // find a possible already created entry that was created as a replacement for an existing expression. If we also
        // created such an entry for a Geltungszeitregel we need to change the createdBy to "diese Verkündung"
        var replacement = expressions
          .stream()
          .filter(
            expr ->
              expr.normExpressionEli().getPointInTime().equals(geltungszeit) &&
              expr.normExpressionEli().getLanguage().equals("deu") &&
              expr.createdBy() == Zielnorm.CreatedBy.SYSTEM
          )
          .findFirst();
        if (replacement.isPresent()) {
          expressions.remove(replacement.get());
          expressions.add(
            new Zielnorm.Expression(
              replacement.get().normExpressionEli(),
              false,
              false,
              false,
              Zielnorm.CreatedBy.THIS_VERKUENDUNG
            )
          );
        } else {
          // Only add new not-yet-created expressions if they are really not yet created
          boolean alreadyExists = expressions
            .stream()
            .anyMatch(
              expr ->
                expr.normExpressionEli().getPointInTime().equals(geltungszeit) &&
                expr.normExpressionEli().getLanguage().equals("deu") &&
                expr.createdBy() == Zielnorm.CreatedBy.THIS_VERKUENDUNG
            );
          if (!alreadyExists) {
            var eli = eliService.findNextExpressionEli(zielnormWorkEli, geltungszeit, "deu");
            expressions.add(
              new Zielnorm.Expression(eli, false, false, false, Zielnorm.CreatedBy.THIS_VERKUENDUNG)
            );
          }
        }
      });
    return expressions
      .stream()
      .sorted(Comparator.comparing(Zielnorm.Expression::normExpressionEli))
      .toList();
  }

  private Zielnorm.CreatedBy isSystemExpression(
    AmendedNormExpressions amendedNormExpressions,
    NormExpressionEli expressionEli
  ) {
    boolean createdByReplacing = amendedNormExpressions
      .find(expressionEli)
      .map(NormExpression::getCreatedByReplacingExistingExpression)
      .orElse(false);
    boolean createdByZeitgrenze = amendedNormExpressions
      .find(expressionEli)
      .map(NormExpression::getCreatedByZeitgrenze)
      .orElse(false);
    if (createdByReplacing && !createdByZeitgrenze) {
      return Zielnorm.CreatedBy.SYSTEM;
    } else {
      return Zielnorm.CreatedBy.THIS_VERKUENDUNG;
    }
  }

  /*
  This method is applying the algorithm described in ADR 0020
   */
  private boolean isOrphan(
    final AmendedNormExpressions amendedNormExpressions,
    final List<LocalDate> geltungszeiten,
    final NormExpressionEli normExpressionEli
  ) {
    final Optional<NormExpression> normExpressionOptional = amendedNormExpressions.find(
      normExpressionEli
    );
    if (normExpressionOptional.isEmpty()) {
      return false;
    }

    final NormExpression normExpression = normExpressionOptional.get();
    final boolean createdByZeitgrenze = normExpression.getCreatedByZeitgrenze();
    final boolean createdByReplacing = normExpression.getCreatedByReplacingExistingExpression();
    final LocalDate pointInTime = normExpression.getNormExpressionEli().getPointInTime();

    final boolean zeitgrenzeMissing = !geltungszeiten.contains(pointInTime);
    final boolean hasAnyPrecedingWithZeitgrenze = amendedNormExpressions
      .getNormExpressions()
      .stream()
      .takeWhile(expr -> !expr.equals(normExpression))
      .anyMatch(NormExpression::getCreatedByZeitgrenze);

    final boolean isCase1 = createdByZeitgrenze && !createdByReplacing && zeitgrenzeMissing;
    final boolean isCase2 =
      !createdByZeitgrenze && createdByReplacing && !hasAnyPrecedingWithZeitgrenze;
    final boolean isCase3_1 =
      createdByZeitgrenze &&
      createdByReplacing &&
      zeitgrenzeMissing &&
      !hasAnyPrecedingWithZeitgrenze;
    // Case 3.2 — Do not mark as orphan because it isn't an orphan. Updating created-by-zeitgrenze to false handled in createZielNormen

    return isCase1 || isCase2 || isCase3_1;
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

  private Zielnorm createZielNormen(final Norm verkuendungNorm, final Zielnorm zielnorm) {
    final AmendedNormExpressions amendedNormExpressions = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateAmendedNormExpressions();

    final List<LocalDate> geltungszeiten = findGeltungszeitenForZielnorm(
      verkuendungNorm,
      zielnorm.normWorkEli()
    );

    // Keeping track of removed orphans so that their info is not returned in the Zielnorm object
    final List<NormExpressionEli> removedOrphans = new ArrayList<>();

    zielnorm
      .expressions()
      .stream()
      // Don't include those that should become gegenstandslos because they will me mark as gegenstandslos when the replacing new expression is created
      .filter(f -> !f.isGegenstandslos())
      .forEach(expression ->
        processExpression(
          expression,
          zielnorm,
          amendedNormExpressions,
          geltungszeiten,
          removedOrphans,
          verkuendungNorm
        )
      );

    cleanUpOrphanAmendedExpressions(zielnorm, amendedNormExpressions, removedOrphans);

    updateNorm(verkuendungNorm);

    return new Zielnorm(
      zielnorm.normWorkEli(),
      zielnorm.title(),
      zielnorm.shortTitle(),
      zielnorm
        .expressions()
        .stream()
        .filter(f -> !removedOrphans.contains(f.normExpressionEli()))
        .map(expr ->
          new Zielnorm.Expression(
            expr.normExpressionEli(),
            expr.isGegenstandslos(),
            true,
            false,
            expr.createdBy()
          )
        )
        .toList()
    );
  }

  private void processExpression(
    Zielnorm.Expression expression,
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<LocalDate> geltungszeiten,
    List<NormExpressionEli> removedOrphans,
    Norm verkuendungNorm
  ) {
    final NormExpressionEli expressionEli = expression.normExpressionEli();
    if (expression.isCreated() && amendedNormExpressions.contains(expressionEli)) {
      handleCreatedExpression(
        expressionEli,
        zielnorm,
        amendedNormExpressions,
        geltungszeiten,
        removedOrphans
      );
    } else if (!expression.isCreated()) {
      handleUncreatedExpression(expressionEli, zielnorm, amendedNormExpressions, verkuendungNorm);
    }
  }

  private void handleCreatedExpression(
    NormExpressionEli expressionEli,
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<LocalDate> geltungszeiten,
    List<NormExpressionEli> removedOrphans
  ) {
    // Expression is already created, and it is contained in the amended-norm-expressions, meaning needs to be overriden
    if (geltungszeiten.contains(expressionEli.getPointInTime())) {
      Norm norm = loadNormPort
        .loadNorm(new LoadNormPort.Options(expressionEli))
        .orElseThrow(() ->
          new IllegalStateException(String.format("Norm %s must exist", expressionEli))
        );
      // Build new expression by taking the previous closest one (meaning not cloning the one being overriden)
      Norm previous = findPreviousNotSameDayThanClosestExistingExpression(
        zielnorm.normWorkEli(),
        expressionEli.getPointInTime()
      ).orElseThrow(() ->
        new IllegalStateException("Previous closest expression (not same day) not found")
      );
      var result = createNewVersionOfNormService.createNewOverridenExpression(previous, norm);
      // Result is a new expression with the same eli but content based on the previous closest expression, which can be different from the one used to create the overriden expression
      // We also need to create new manifestation of expression on which the new expression was based, in case it differs from the one that was used to create the overriden expression
      updateNorm(result.newExpression());
      updateNorm(result.newManifestationOfOldExpression());
    } else {
      // It is an orphan, retrieved from DB because first time boundary for work eli was before this orphan
      boolean deleted = removeOrphan(expressionEli);
      if (deleted) {
        amendedNormExpressions.remove(expressionEli);
        removedOrphans.add(expressionEli);
      }
    }
  }

  private void handleUncreatedExpression(
    NormExpressionEli expressionEli,
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    Norm verkuendungNorm
  ) {
    // If it is not created yet, so take the previous closest expression, which can be on the same day (for the case of same day, leading to a gegenstandslos version)
    Norm previous = findPreviousAndSameDayClosestExistingExpression(
      zielnorm.normWorkEli(),
      expressionEli.getPointInTime()
    ).orElseThrow(() ->
      new IllegalStateException("Previous closest expression (including same day) not found")
    );

    // Check if the previous closest is actually one that should be set to gegenstandslos (by checking our zielnormen preview list)
    boolean shouldBeGegenstandslos = zielnorm
      .expressions()
      .stream()
      .anyMatch(
        f -> f.normExpressionEli().equals(previous.getExpressionEli()) && f.isGegenstandslos()
      );

    if (shouldBeGegenstandslos) {
      // If the one retrieved should become gegenstandslos we need to create three 3 manifestations, for that we need to preceding expression of the one becoming gegenstandslos
      Norm preceding = findPreviousNotSameDayThanClosestExistingExpression(
        zielnorm.normWorkEli(),
        expressionEli.getPointInTime()
      ).orElseThrow(() ->
        new IllegalStateException(
          "Previous closest expression (preceding a becoming gegentstandslos one) not found"
        )
      );
      var result = createNewVersionOfNormService.createNewExpression(
        previous,
        preceding,
        expressionEli.getPointInTime(),
        verkuendungNorm.getRegelungstext1().getMeta().getFRBRWork().getFBRDate()
      );
      updateNorm(result.newExpression());
      updateNorm(result.newManifestationOfGegenstandslosExpression());
      updateNorm(result.newManifestationOfPrecidingExpression());
      // Add expression eli of new expression into amended-expressions
      var geltungszeiten = findGeltungszeitenForZielnorm(verkuendungNorm, zielnorm.normWorkEli());
      amendedNormExpressions.add(
        result.newExpression().getExpressionEli(),
        geltungszeiten.contains(result.newExpression().getExpressionEli().getPointInTime()),
        true
      );
    } else {
      // If the previous one is not to become gegenstandslos, is just a normal new creation of expression, including new manifestation this previous expression
      var result = createNewVersionOfNormService.createNewExpression(
        previous,
        expressionEli.getPointInTime()
      );
      updateNorm(result.newExpression());
      updateNorm(result.newManifestationOfOldExpression());
      // Add expression eli of new expression into amended-expressions
      amendedNormExpressions.add(result.newExpression().getExpressionEli(), true, false);
    }
  }

  private void cleanUpOrphanAmendedExpressions(
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<NormExpressionEli> removedOrphans
  ) {
    amendedNormExpressions
      .getNormExpressions()
      .stream()
      .filter(normExpression ->
        zielnorm
          .expressions()
          .stream()
          .map(Zielnorm.Expression::normExpressionEli)
          .noneMatch(expressionEli -> normExpression.getNormExpressionEli().equals(expressionEli))
      )
      .forEach(normExpression -> {
        boolean deleted = removeOrphan(normExpression.getNormExpressionEli());
        if (deleted) {
          amendedNormExpressions.remove(normExpression.getNormExpressionEli());
          removedOrphans.add(normExpression.getNormExpressionEli());
          fixTimeline(normExpression.getNormExpressionEli());
        }
      });
  }

  /**
   * This function "fixes" the timeline, only needed when an orphan expression is removed that had a date before the first time boundary for the target work eli.
   * Because then it was not retrieved by the list expressions and therefore not processed within the loop (where also orphans are removed including automatically fixing of timeline)
   * Fixing timeline means correcting the previous/next GUIDs or the expressions
   * @param removedNormExpressionEli - the eli of the removed orphan expression
   */
  private void fixTimeline(final NormExpressionEli removedNormExpressionEli) {
    final List<NormExpressionEli> sortedExpressionsElis = loadNormExpressionElisPort
      .loadNormExpressionElis(
        new LoadNormExpressionElisPort.Options(removedNormExpressionEli.asWorkEli())
      )
      .stream()
      .sorted()
      .toList();

    // The removed expression eli WILL NOT be in the list of retrieved elis, binarySearch will return (-(insertion point) - 1), so we invert it to get the position where it would be inserted.
    int insertionPoint =
      -Collections.binarySearch(sortedExpressionsElis, removedNormExpressionEli) - 1;

    final Optional<Norm> previousNorm = insertionPoint > 0
      ? loadNormPort.loadNorm(
        new LoadNormPort.Options(sortedExpressionsElis.get(insertionPoint - 1))
      )
      : Optional.empty();

    final Optional<Norm> nextNorm = insertionPoint < sortedExpressionsElis.size()
      ? loadNormPort.loadNorm(new LoadNormPort.Options(sortedExpressionsElis.get(insertionPoint)))
      : Optional.empty();

    // We only have 2 cases:
    // 1. The removed orphan was in between two expressions  (both previous- and next-norms are present)
    // 2. The removed orphan was at the end of the timeline  (previous-norm is present)
    // The case that only the next-norm is present is not possible because at least the original expression must be present
    if (previousNorm.isPresent() && nextNorm.isPresent()) {
      final Norm previousExpression = previousNorm.get();
      final FRBRExpression previousFrbrExpression = previousExpression
        .getRegelungstext1()
        .getMeta()
        .getFRBRExpression();
      final Norm nextExpression = nextNorm.get();
      final FRBRExpression nextFrbrExpression = nextExpression
        .getRegelungstext1()
        .getMeta()
        .getFRBRExpression();

      previousFrbrExpression.setFRBRaliasNextVersionId(
        nextFrbrExpression.getFRBRaliasCurrentVersionId()
      );
      nextFrbrExpression.setFRBRaliasPreviousVersionId(
        previousFrbrExpression.getFRBRaliasCurrentVersionId()
      );
      updateNorm(previousExpression);
      updateNorm(nextExpression);
    } else if (previousNorm.isPresent()) {
      final Norm previousExpression = previousNorm.get();
      previousExpression
        .getRegelungstext1()
        .getMeta()
        .getFRBRExpression()
        .deleteAliasNextVersionId();
      updateNorm(previousExpression);
    }
  }

  private boolean removeOrphan(final NormExpressionEli normExpressionEli) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Options(normExpressionEli))
      .map(normLoaded ->
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Options(normLoaded.getExpressionEli(), NormPublishState.UNPUBLISHED)
        )
      )
      .orElse(true);
  }

  /**
   * Get the previous closest non-gegenstandslos norm to the passed date (including same date)
   */
  private Optional<Norm> findPreviousAndSameDayClosestExistingExpression(
    NormWorkEli zielnormWorkEli,
    LocalDate dateForNewExpression
  ) {
    return findClosestExistingExpressionMatching(
      zielnormWorkEli,
      eli -> !eli.getPointInTime().isAfter(dateForNewExpression) // eli <= date
    );
  }

  /**
   * Get the previous closest non-gegenstandslos norm to the passed date (not same date)
   */
  private Optional<Norm> findPreviousNotSameDayThanClosestExistingExpression(
    NormWorkEli zielnormWorkEli,
    LocalDate dateForNewExpression
  ) {
    return findClosestExistingExpressionMatching(
      zielnormWorkEli,
      eli -> eli.getPointInTime().isBefore(dateForNewExpression) // eli < date
    );
  }

  private Optional<Norm> findClosestExistingExpressionMatching(
    NormWorkEli zielnormWorkEli,
    Predicate<NormExpressionEli> filter
  ) {
    return loadNormExpressionElisPort
      .loadNormExpressionElis(new LoadNormExpressionElisPort.Options(zielnormWorkEli))
      .stream()
      .filter(filter)
      .map(eli ->
        new AbstractMap.SimpleEntry<>(eli, loadNormPort.loadNorm(new LoadNormPort.Options(eli)))
      )
      .filter(entry -> entry.getValue().isPresent())
      .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().get()))
      .filter(entry -> !entry.getValue().isGegenstandlos())
      .max(Map.Entry.comparingByKey())
      .map(Map.Entry::getValue);
  }

  @Override
  public Page<LoadNormWorksUseCase.Result> loadNormWorks(LoadNormWorksUseCase.Options options) {
    return loadNormWorksPort
      .loadNormWorks(new LoadNormWorksPort.Options(options.pageable()))
      .map(result -> new LoadNormWorksUseCase.Result(result.eli(), result.title()));
  }

  @Override
  public List<LoadExpressionsOfNormWorkUseCase.Result> loadExpressionsOfNormWork(
    LoadExpressionsOfNormWorkUseCase.Options options
  ) {
    return loadExpressionsOfNormWorkPort
      .loadExpressionsOfNormWork(new LoadExpressionsOfNormWorkPort.Options(options.eli()))
      .stream()
      .map(result ->
        new LoadExpressionsOfNormWorkUseCase.Result(result.eli(), result.gegenstandslos())
      )
      .toList();
  }
}
