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
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  private final LdmlDeEmptyElementRemover ldmlDeEmptyElementRemover;
  private final LdmlDeValidator ldmlDeValidator;
  private final CreateNewWorkService createNewWorkService;

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
    LdmlDeEmptyElementRemover ldmlDeEmptyElementRemover,
    LdmlDeValidator ldmlDeValidator,
    CreateNewWorkService createNewWorkService
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
    this.ldmlDeEmptyElementRemover = ldmlDeEmptyElementRemover;
    this.ldmlDeValidator = ldmlDeValidator;
    this.createNewWorkService = createNewWorkService;
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
        ldmlDeEmptyElementRemover.removeEmptyElements(dokument.getDocument().getDocumentElement());
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
              zielnormReferenceUpdateData.zielnorm(),
              zielnormReferenceUpdateData.isNewWork()
            )
          );

        zielnormReference.setTyp(zielnormReferenceUpdateData.typ());
        zielnormReference.setGeltungszeit(zielnormReferenceUpdateData.geltungszeit());
        zielnormReference.setZielnorm(zielnormReferenceUpdateData.zielnorm());
        zielnormReference.setNewWork(zielnormReferenceUpdateData.isNewWork());
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

    // we need two lists one for laws that get changed but already exist in the database and one with laws that exist as "eingebundene Stammform"
    Map<Boolean, List<NormWorkEli>> partitionedZielnormWorkElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .collect(
        Collectors.partitioningBy(
          ZielnormReference::isNewWork,
          Collectors.mapping(
            ZielnormReference::getZielnorm,
            Collectors.collectingAndThen(Collectors.toSet(), ArrayList::new)
          )
        )
      );

    Stream<Zielnorm> zielnormsChange = partitionedZielnormWorkElis
      .getOrDefault(false, Collections.emptyList())
      .stream()
      .map(zielnormWorkEli -> {
        var latestZielnormExpression = loadNorm(new EliOptions(zielnormWorkEli));
        return new Zielnorm(
          zielnormWorkEli,
          latestZielnormExpression.getLongTitle().orElse(""),
          latestZielnormExpression.getShortTitle().orElse(""),
          generateZielnormPreviewExpressions(verkuendungNorm, zielnormWorkEli)
        );
      });

    Stream<Zielnorm> zielnormsNew = partitionedZielnormWorkElis
      .getOrDefault(true, Collections.emptyList())
      .stream()
      .map(zielnormWorkEli -> {
        try {
          Norm latestZielnormExpression = loadNorm(new EliOptions(zielnormWorkEli));
          return new Zielnorm(
            zielnormWorkEli,
            latestZielnormExpression.getLongTitle().map(String::trim).orElse(""),
            latestZielnormExpression.getShortTitle().map(String::trim).orElse(""),
            List.of(
              new Zielnorm.Expression(
                latestZielnormExpression.getExpressionEli(),
                false,
                true,
                false,
                Zielnorm.CreatedBy.THIS_VERKUENDUNG
              )
            )
          );
        } catch (NormNotFoundException e) {
          Norm latestZielnormExpression = extractNorm(verkuendungNorm, zielnormWorkEli);
          return new Zielnorm(
            zielnormWorkEli,
            latestZielnormExpression.getLongTitle().map(String::trim).orElse(""),
            latestZielnormExpression.getShortTitle().map(String::trim).orElse(""),
            List.of(
              new Zielnorm.Expression(
                NormExpressionEli.fromWorkEli(
                  zielnormWorkEli,
                  getGeltungszeit(verkuendungNorm, zielnormWorkEli),
                  1,
                  latestZielnormExpression.getExpressionEli().getLanguage()
                ),
                false,
                false,
                false,
                Zielnorm.CreatedBy.THIS_VERKUENDUNG
              )
            )
          );
        }
      });
    return Stream.concat(zielnormsChange, zielnormsNew).toList();
  }

  private Norm extractNorm(Norm verkuendung, final NormWorkEli zielNormWorkEli) {
    Dokument regelungstext1 = verkuendung
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .filter(z -> z.getZielnorm().equals(zielNormWorkEli))
      .flatMap(zielnormReference -> {
        Article referencingArticle = verkuendung
          .getRegelungstext1()
          .getArticles()
          .stream()
          .filter(article -> article.getEid().equals(zielnormReference.getEId()))
          .findFirst()
          .orElseThrow(() ->
            new IllegalStateException(
              "Reference was wrong: No article found for EId " + zielnormReference.getEId()
            )
          );

        return referencingArticle
          .getEingebundeneStammform()
          .map(eli ->
            verkuendung
              .getDokumente()
              .stream()
              .filter(d -> d.getManifestationEli().equals(eli))
              .findFirst()
              .orElseThrow(() ->
                new IllegalStateException("There was no Reglungstext found for eli " + eli)
              )
          )
          .stream();
      })
      .findFirst()
      .orElseThrow(() ->
        new IllegalStateException(
          "There was no Reglungstext found for zielNormWorkEli " + zielNormWorkEli
        )
      );

    return Norm.builder()
      .publishState(NormPublishState.UNPUBLISHED)
      .dokumente(Set.of(regelungstext1))
      .build();
  }

  private LocalDate getGeltungszeit(Norm verkuendung, final NormWorkEli zielNormWorkEli) {
    Zeitgrenze.Id geltungszeitId = verkuendung
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .filter(z -> z.getZielnorm().equals(zielNormWorkEli))
      .findFirst()
      .map(ZielnormReference::getGeltungszeit)
      .orElseThrow(() ->
        new IllegalStateException("No Geltungszeit id found for " + zielNormWorkEli)
      );

    return verkuendung
      .getRegelungstext1()
      .getZeitgrenzen()
      .stream()
      .filter(geltungszeit -> geltungszeit.getId().equals(geltungszeitId))
      .findFirst()
      .orElseThrow(() ->
        new IllegalStateException("No Zeitgrenze found for Norm with eli " + zielNormWorkEli)
      )
      .getDate();
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
   * the changes of the now gegenstandlose expression as well as the ones from the previous changes
   * due to the Verkündung.
   */
  private List<Zielnorm.Expression> generateZielnormPreviewExpressions(
    Norm verkuendungNorm,
    NormWorkEli zielnormWorkEli
  ) {
    var geltungszeiten = findGeltungszeitenForZielnorm(verkuendungNorm, zielnormWorkEli);
    var earliestGeltungszeit = geltungszeiten.stream().min(LocalDate::compareTo);
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

    var affectedExpressionElisOpt = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getAmendedNormExpressions);

    List<Zielnorm.Expression> expressions = new ArrayList<>();

    // First handling the already existing expressions (could be 1. orphans, 2. overrides or 3. becoming-gegenstandslos)
    for (var eli : existingSortedExpressionElis) {
      handleExistingExpression(
        eli,
        geltungszeiten,
        earliestGeltungszeit.get(),
        affectedExpressionElisOpt,
        expressions
      );
    }

    // Then handling the actual geltungszeiten of the verkuendung (meaning 1. replacing expressions or simply 2. new expressions)
    for (var geltungszeit : geltungszeiten.stream().sorted().toList()) {
      handleGeltungszeit(geltungszeit, zielnormWorkEli, expressions);
    }

    return expressions
      .stream()
      .sorted(Comparator.comparing(Zielnorm.Expression::normExpressionEli))
      .toList();
  }

  private void handleExistingExpression(
    NormExpressionEli eli,
    List<LocalDate> geltungszeiten,
    LocalDate earliestGeltungszeit,
    Optional<AmendedNormExpressions> affectedExpressionElisOpt,
    List<Zielnorm.Expression> expressions
  ) {
    boolean isBeforeFirstGeltungszeit = eli.getPointInTime().isBefore(earliestGeltungszeit);

    if (isBeforeFirstGeltungszeit) {
      // 1. Before the first geltungszeit can only be orphans (if determined by the isOrphan helper method)
      if (
        affectedExpressionElisOpt.isPresent() &&
        isOrphan(affectedExpressionElisOpt.get(), geltungszeiten, eli)
      ) {
        expressions.add(
          new Zielnorm.Expression(eli, false, true, true, Zielnorm.CreatedBy.THIS_VERKUENDUNG)
        );
      }
      return;
    }

    // After first geltungszeit could be...
    boolean wasCreatedByVerkuendung =
      affectedExpressionElisOpt.isPresent() && affectedExpressionElisOpt.get().contains(eli);
    if (wasCreatedByVerkuendung) {
      // ... 2. overrides (were created by the same verkuendung already) or ...
      expressions.add(
        new Zielnorm.Expression(
          eli,
          false,
          true,
          isOrphan(affectedExpressionElisOpt.get(), geltungszeiten, eli),
          isSystemExpression(affectedExpressionElisOpt.get(), eli)
            ? Zielnorm.CreatedBy.SYSTEM
            : Zielnorm.CreatedBy.THIS_VERKUENDUNG
        )
      );
    } else {
      // ... 3. becoming-gegenstandslos expressions
      expressions.add(
        new Zielnorm.Expression(eli, true, true, false, Zielnorm.CreatedBy.OTHER_VERKUENDUNG)
      );
      var nextEli = eliService.findNextExpressionEli(
        eli.asWorkEli(),
        eli.getPointInTime(),
        eli.getLanguage()
      );
      expressions.add(
        new Zielnorm.Expression(nextEli, false, false, false, Zielnorm.CreatedBy.SYSTEM)
      );
    }
  }

  private void handleGeltungszeit(
    LocalDate geltungszeit,
    NormWorkEli zielnormWorkEli,
    List<Zielnorm.Expression> expressions
  ) {
    var existingExpressions = expressions
      .stream()
      .filter(
        expr ->
          expr.normExpressionEli().getPointInTime().equals(geltungszeit) &&
          expr.normExpressionEli().getLanguage().equals("deu")
      )
      .toList();
    var existingExpressionBySystem = existingExpressions
      .stream()
      .filter(f -> f.createdBy() == Zielnorm.CreatedBy.SYSTEM)
      .findFirst();
    if (existingExpressionBySystem.isPresent()) {
      // 1. replacing expression, we created already a SYSTEM one that needs to be replaced by THIS_VERKUENDUNG, because verkuendung had the same geltungszeit as the existing expression
      expressions.remove(existingExpressionBySystem.get());
      expressions.add(
        new Zielnorm.Expression(
          existingExpressionBySystem.get().normExpressionEli(),
          false,
          false,
          false,
          Zielnorm.CreatedBy.THIS_VERKUENDUNG
        )
      );
      return;
    }

    var existingExpressionByThisVerkuendung = existingExpressions
      .stream()
      .filter(f -> f.createdBy() == Zielnorm.CreatedBy.THIS_VERKUENDUNG)
      .findFirst();
    if (existingExpressionByThisVerkuendung.isEmpty()) {
      // 2. completely new expression that has not been created yet
      var eli = eliService.findNextExpressionEli(zielnormWorkEli, geltungszeit, "deu");
      expressions.add(
        new Zielnorm.Expression(eli, false, false, false, Zielnorm.CreatedBy.THIS_VERKUENDUNG)
      );
    }
  }

  /*
This method is applying the algorithm described in ADR 0020
 */
  private boolean isSystemExpression(
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
    return createdByReplacing && !createdByZeitgrenze;
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

    // Following are the cases 1, 2 and 3.1 of the ADR 0020
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

  private boolean isNewWork(Norm verkuendungNorm, NormWorkEli zielnormWorkEli) {
    return verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .filter(zielnormReference -> zielnormReference.getZielnorm().equals(zielnormWorkEli))
      .anyMatch(ZielnormReference::isNewWork);
  }

  private Zielnorm createZielNormen(final Norm verkuendungNorm, final Zielnorm zielnorm) {
    final AmendedNormExpressions amendedNormExpressions = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateAmendedNormExpressions();

    final List<LocalDate> geltungszeitenForZielnorm = findGeltungszeitenForZielnorm(
      verkuendungNorm,
      zielnorm.normWorkEli()
    );

    // Keeping track of removed orphans so that their info is not returned in the Zielnorm object
    final List<NormExpressionEli> removedOrphans = new ArrayList<>();

    final boolean isNewWork = isNewWork(verkuendungNorm, zielnorm.normWorkEli());
    if (isNewWork) {
      createNewWork(
        zielnorm,
        amendedNormExpressions,
        geltungszeitenForZielnorm,
        removedOrphans,
        verkuendungNorm
      );
    } else {
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
            geltungszeitenForZielnorm,
            removedOrphans,
            verkuendungNorm
          )
        );
    }

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

  private void createNewWork(
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<LocalDate> geltungszeitenForZielnorm,
    List<NormExpressionEli> removedOrphans,
    Norm verkuendungNorm
  ) {
    zielnorm
      .expressions()
      .forEach(expression -> {
        if (!expression.isCreated()) {
          // 1. Not created yet
          // load norm from verkuendung using the target work eli, since computed expression eli may differ from eli of saved document (different zeitgrenze)
          // no need to use create-service with working-copy-date, because the embedded norm will have already that date, because of updating first the verkündung with geltungszeitregel and threfore creating a working-copy, also of embedded norms
          var embeddedNorm = extractNorm(verkuendungNorm, zielnorm.normWorkEli());
          var newNorm = createNewWorkService.createNewWork(
            embeddedNorm,
            verkuendungNorm.getRechtsetzungsdokument(),
            expression.normExpressionEli()
          );
          updateNorm(newNorm);
          amendedNormExpressions.add(expression.normExpressionEli(), true, false);
        } else if (!amendedNormExpressions.contains(expression.normExpressionEli())) {
          // 2. Created but not present in amended-norm-expressions, should not be the case
          throw new ExpressionOfNewWorkAlreadyExistsException(
            expression.normExpressionEli().toString()
          );
        } else if (amendedNormExpressions.contains(expression.normExpressionEli())) {
          // 3     created and present in amended-norm-expressions
          if (geltungszeitenForZielnorm.contains(expression.normExpressionEli().getPointInTime())) {
            // 3.a.  override
            // Just do the same as if where not yet created, without adding the expression eli to amended-norm-expressions
            var embeddedNorm = extractNorm(verkuendungNorm, zielnorm.normWorkEli());
            var newNorm = createNewWorkService.createNewWork(
              embeddedNorm,
              verkuendungNorm.getRechtsetzungsdokument(),
              expression.normExpressionEli()
            );
            updateNorm(newNorm);
          } else {
            // 3.b.  orphan
            boolean deleted = removeOrphan(expression.normExpressionEli());
            if (deleted) {
              amendedNormExpressions.remove(expression.normExpressionEli());
              removedOrphans.add(expression.normExpressionEli());
            }
          }
        }
      });
  }

  private void processExpression(
    Zielnorm.Expression expression,
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<LocalDate> geltungszeitenForZielnorm,
    List<NormExpressionEli> removedOrphans,
    Norm verkuendungNorm
  ) {
    final NormExpressionEli expressionEli = expression.normExpressionEli();
    if (expression.isCreated() && amendedNormExpressions.contains(expressionEli)) {
      handleCreatedExpression(
        expressionEli,
        zielnorm,
        amendedNormExpressions,
        geltungszeitenForZielnorm,
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
    List<LocalDate> geltungszeitenForZielnorm,
    List<NormExpressionEli> removedOrphans
  ) {
    // Expression is already created, and it is contained in the amended-norm-expressions, meaning needs to be overriden
    if (geltungszeitenForZielnorm.contains(expressionEli.getPointInTime())) {
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

  /** Clean the orphans whose expressions eli present in amended-norm-expressions BUT the zielnorm-references would not produce that expressions */
  private void cleanUpOrphanAmendedExpressions(
    Zielnorm zielnorm,
    AmendedNormExpressions amendedNormExpressions,
    List<NormExpressionEli> removedOrphans
  ) {
    amendedNormExpressions
      .getNormExpressions()
      .stream()
      .filter(f -> f.getNormExpressionEli().asWorkEli().equals(zielnorm.normWorkEli()))
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
