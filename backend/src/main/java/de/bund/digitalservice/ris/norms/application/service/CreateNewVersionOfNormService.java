package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service for creating new expressions and manifestations of a norm.
 */
@Service
public class CreateNewVersionOfNormService {

  private final EliService eliService;
  private final LoadNormByGuidPort loadNormByGuidPort;

  public CreateNewVersionOfNormService(
    EliService eliService,
    LoadNormByGuidPort loadNormByGuidPort
  ) {
    this.eliService = eliService;
    this.loadNormByGuidPort = loadNormByGuidPort;
  }

  /**
   * Result object for creating a new expression, containing both the new expression and the new manifestation of the
   * old expression.
   * @param newExpression the new expression
   * @param newManifestationOfOldExpression the new manifestation of the old expression (with the updated "nachfolgende-version-id")
   */
  public record CreateNewExpressionResult(
    Norm newExpression,
    Norm newManifestationOfOldExpression
  ) {}

  /**
   * Result object for creating a new expression, containing the new expression, the new manifestation of the
   * preciding expression as well as the new manifestation for the gegenstandslos expression
   * @param newExpression the new expression
   * @param newManifestationOfPrecidingExpression the new manifestation of the preciding expression (with the updated "nachfolgende-version-id")
   * @param newManifestationOfGegenstandslosExpression the new manifestation of the old expression (with the metadata gegenstandslos)
   */
  public record CreateNewExpressionResultIncludingGegenstandslos(
    Norm newExpression,
    Norm newManifestationOfPrecidingExpression,
    Norm newManifestationOfGegenstandslosExpression
  ) {}

  /**
   * Creates a new expression of the given norm for the given date. Also creates a new manifestation of the old
   * expression with the updated "nachfolgende-version-id".
   *
   * @param norm the norm for which a new expression should be created.
   * @param date the date of the change that creates this expression.
   * @return a {@link CreateNewExpressionResult} containing both the new expression and the new manifestation of the old expression
   */
  public CreateNewExpressionResult createNewExpression(Norm norm, LocalDate date) {
    var newExpression = createOnlyNewExpression(norm, date);

    Norm newManifestationOfOldExpression = createNewManifestationOfOldExpression(
      norm,
      newExpression
    );
    return new CreateNewExpressionResult(newExpression, newManifestationOfOldExpression);
  }

  /**
   * Creates a new expression based on the passed norm but uses the ELIs and GUIDs of the already existing expression. Meaning an
   * overriding is happening. Also creates new manifestation of old expression (using the duplicated new expression)
   *
   * @param norm the norm for which a new expression should be created.
   * @param toDuplicateExpression the already existing expression
   * @return a {@link CreateNewExpressionResult} containing both the new expression and the new manifestation of the old expression
   */
  public CreateNewExpressionResult createNewOverridenExpression(
    Norm norm,
    Norm toDuplicateExpression
  ) {
    var newExpression = new Norm(norm);
    var newManifestationEli = NormManifestationEli.fromExpressionEli(
      toDuplicateExpression.getExpressionEli(),
      LocalDate.now()
    );
    newExpression
      .getDokumente()
      .forEach(dokument -> {
        setNewExpressionMetadataWithCurrentAndNextGuid(
          dokument,
          DokumentExpressionEli.fromNormEli(
            toDuplicateExpression.getExpressionEli(),
            dokument.getExpressionEli().getSubtype()
          ),
          toDuplicateExpression.getGuid(),
          toDuplicateExpression
            .getRegelungstext1()
            .getMeta()
            .getFRBRExpression()
            .getFRBRaliasNextVersionId()
            .orElse(null)
        );
        setNewManifestationMetadata(
          dokument,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            dokument.getManifestationEli().getSubtype(),
            dokument.getManifestationEli().getFormat()
          )
        );
      });
    Norm newManifestationOfOldExpression = createNewManifestationOfOldExpression(
      norm,
      newExpression
    );

    return new CreateNewExpressionResult(newExpression, newManifestationOfOldExpression);
  }

  /**
   * Creates a new expression taking into consideration that it will "replace" an already existing expression that is becoming gegenstandslos,
   * meaning it produces a new completely expression cloned from the expression becoming gegenstandslos, it sets the one becoming gegenstandslos
   * as that (by creating a new manifestation) and also creates a new manifestation of the expression that was preceding the one that became gegenstandslos,
   * so that the nachfolgende-version-id is corrected so that it points to the new non-gegenstandslos expression
   *
   * @param gegenstandslosExpression - the expression becoming gegenstandslos
   * @param precedingExpression - the expression that was preceding the one becoming gegenstandslos
   * @param date - the date for the new expression
   * @param verkuendungDate - the announcement date of the verkuendung, used for the meta:gegenstandslos@seit
   * @return result containing the new expression, the new manifestation for the gegenstandslos and the new manifestation of the preceding expression pointing to the new created expression
   */
  public CreateNewExpressionResultIncludingGegenstandslos createNewExpression(
    Norm gegenstandslosExpression,
    Norm precedingExpression,
    LocalDate date,
    String verkuendungDate
  ) {
    // We create 1 new expression and 2 new manifestations

    //  1. the new expression, creating a clone of the one becoming gegenstandslos, using counter +1 and keeping the same vorherige-version-id (because both elis have the same point-in-time)
    var newExpression = createOnlyNewExpression(gegenstandslosExpression, date);
    // Set vorherige-version-id by getting the akuelle-version-id of the preceding expression, because it can have varied to the one the becoming gegenstandslos had
    newExpression
      .getDokumente()
      .forEach(dokument ->
        dokument
          .getMeta()
          .getFRBRExpression()
          .setFRBRaliasPreviousVersionId(precedingExpression.getGuid())
      );

    //  2. new manifestation for the one becoming gegenstandslos (keeping all GUIDs is fine)
    final Norm newManifestationForGegenstandslosExpression = createNewManifestation(
      gegenstandslosExpression
    );
    newManifestationForGegenstandslosExpression.setGegenstandlos(verkuendungDate);

    //  3. new manifestation for the preceding expression for setting nachfolgende-version.id
    final Norm newManifestationOfPrecedingExpression = createNewManifestation(precedingExpression);
    newManifestationOfPrecedingExpression
      .getDokumente()
      .forEach(dokument ->
        dokument.getMeta().getFRBRExpression().setFRBRaliasNextVersionId(newExpression.getGuid())
      );

    return new CreateNewExpressionResultIncludingGegenstandslos(
      newExpression,
      newManifestationOfPrecedingExpression,
      newManifestationForGegenstandslosExpression
    );
  }

  /**
   * Creates a new manifestation of the given norm. Uses the current date for the point-in-time-manifestation.
   * @param norm the norm for which a new manifestation should be created.
   * @return the newly created manifestation.
   */
  public Norm createNewManifestation(Norm norm) {
    return createNewManifestation(norm, LocalDate.now());
  }

  /**
   * Creates a new manifestation of the given norm.
   * @param norm the norm for which a new manifestation should be created.
   * @param pointInTimeManifestation the date for the point-in-time-manifestation
   *
   * @return the newly created manifestation.
   */
  public Norm createNewManifestation(Norm norm, LocalDate pointInTimeManifestation) {
    var newManifestation = new Norm(norm);
    var newManifestationEli = NormManifestationEli.fromExpressionEli(
      newManifestation.getExpressionEli(),
      pointInTimeManifestation
    );

    newManifestation
      .getDokumente()
      .forEach(dokument ->
        setNewManifestationMetadata(
          dokument,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            dokument.getManifestationEli().getSubtype(),
            dokument.getManifestationEli().getFormat()
          )
        )
      );

    return newManifestation;
  }

  /**
   * Creates a new manifestation of the old expression of a norm. This will update the "nachfolgende-version-id".
   * @param oldExpression the old expression of the norm.
   * @param newExpression the new expression of the norm
   * @return the new manifestation of the old expression.
   */
  private Norm createNewManifestationOfOldExpression(Norm oldExpression, Norm newExpression) {
    Norm newManifestationOfOldExpression = createNewManifestation(
      findPreviousExpressionOfNewExpression(oldExpression, newExpression)
    );
    // We can just take the GUID of the Regelungstext because all Dokumente of the same expression share the same GUID
    newManifestationOfOldExpression
      .getDokumente()
      .forEach(dokument ->
        dokument.getMeta().getFRBRExpression().setFRBRaliasNextVersionId(newExpression.getGuid())
      );
    return newManifestationOfOldExpression;
  }

  private Norm createOnlyNewExpression(Norm norm, LocalDate date) {
    var newExpression = new Norm(norm);
    var newExpressionEli = eliService.findNextExpressionEli(
      newExpression.getWorkEli(),
      date,
      newExpression.getExpressionEli().getLanguage()
    );
    var newManifestationEli = NormManifestationEli.fromExpressionEli(
      newExpressionEli,
      LocalDate.now()
    );
    final UUID uuidForAllDokumente = UUID.randomUUID();
    newExpression
      .getDokumente()
      .forEach(dokument -> {
        setNewExpressionMetadata(
          dokument,
          DokumentExpressionEli.fromNormEli(
            newExpressionEli,
            dokument.getExpressionEli().getSubtype()
          ),
          uuidForAllDokumente
        );
        setNewManifestationMetadata(
          dokument,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            dokument.getManifestationEli().getSubtype(),
            dokument.getManifestationEli().getFormat()
          )
        );
      });
    return newExpression;
  }

  /**
   * Find the expression of the updated norm in which the "nachfolgende-version-id" needs to be updated. This is not the
   * oldExpression if the new expression is not for a new date but only the versionNumber was increased. In this case
   * a new manifestation of the previous version of the oldExpression needs to be created.
   * @param oldExpression the old expression
   * @param newExpression the new expression
   * @return the norm in which the "nachfolgende-version-id" needs to be set
   */
  private Norm findPreviousExpressionOfNewExpression(Norm oldExpression, Norm newExpression) {
    if (
      !newExpression
        .getExpressionEli()
        .getPointInTime()
        .isEqual(oldExpression.getExpressionEli().getPointInTime())
    ) {
      return oldExpression;
    }

    var previousVersionId = oldExpression
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression()
      .getFRBRaliasPreviousVersionId()
      .orElseThrow();

    var previousExpression = loadNormByGuidPort.loadNormByGuid(
      new LoadNormByGuidPort.Options(previousVersionId)
    );

    if (previousExpression.isEmpty()) {
      throw new NormNotFoundException(previousVersionId);
    }

    return previousExpression.get();
  }

  /**
   * Sets the metadata for a new expression based on the eli.
   * @param dokument the new expression
   * @param expressionEli the new eli for the expression
   * @param uuidForAllDokumente the UUID for the aktuelle-version-id that needs to be the same for all Dokumente of an expression
   */
  private void setNewExpressionMetadata(
    Dokument dokument,
    DokumentExpressionEli expressionEli,
    UUID uuidForAllDokumente
  ) {
    var oldEli = dokument.getExpressionEli();
    var oldVersionId = dokument.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId();
    var expression = dokument.getMeta().getFRBRExpression();
    expression.setEli(expressionEli);
    expression.setFBRDate(
      expressionEli.getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
      "aenderung"
    );
    expression.setURI(expressionEli.toUri());
    expression.setFRBRVersionNumber(expressionEli.getVersion());
    expression.setFRBRaliasCurrentVersionId(uuidForAllDokumente);
    if (!oldEli.getPointInTime().isEqual(expressionEli.getPointInTime())) {
      expression.setFRBRaliasPreviousVersionId(oldVersionId);
    }
    expression.deleteAliasNextVersionId();
  }

  /**
   * Set metadata for expression but keeps the passed current and next GUIDs (when doing a cloning of the expression)
   * @param dokument - the new expression
   * @param expressionEli - the eli for the new expression
   * @param currentGuid - the current GUID of the expression that is being overriden
   * @param nextGuid - the next GUID of the expression that is being overriden
   */
  private void setNewExpressionMetadataWithCurrentAndNextGuid(
    Dokument dokument,
    DokumentExpressionEli expressionEli,
    UUID currentGuid,
    UUID nextGuid
  ) {
    setNewExpressionMetadata(dokument, expressionEli, currentGuid);
    var expression = dokument.getMeta().getFRBRExpression();
    expression.setFRBRaliasCurrentVersionId(currentGuid);
    if (nextGuid != null) {
      expression.setFRBRaliasNextVersionId(nextGuid);
    } else {
      expression.deleteAliasNextVersionId();
    }
  }

  /**
   * Sets the metadata for a new manifestation based on the eli.
   * @param dokument a dokument of the new manifestation
   * @param manifestationEli the new eli for the manifestation
   */
  private void setNewManifestationMetadata(
    Dokument dokument,
    DokumentManifestationEli manifestationEli
  ) {
    var manifestation = dokument.getMeta().getFRBRManifestation();
    manifestation.setEli(manifestationEli);
    manifestation.setURI(manifestationEli.toUri());
    manifestation.setFBRDate(
      manifestationEli.getPointInTimeManifestation().format(DateTimeFormatter.ISO_LOCAL_DATE),
      "generierung"
    );
  }
}
