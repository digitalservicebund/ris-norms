package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
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
   * Creates a new expression of the given norm for the given date. Also creates a new manifestation of the old
   * expression with the updated "nachfolgende-version-id".
   *
   * @param norm the norm for which a new expression should be created.
   * @param date the date of the change that creates this expression.
   * @return a {@link CreateNewExpressionResult} containing both the new expression and the new manifestation of the old expression
   */
  public CreateNewExpressionResult createNewExpression(Norm norm, LocalDate date) {
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

    newExpression
      .getRegelungstexte()
      .forEach(regelungstext -> {
        setNewExpressionMetadata(
          regelungstext,
          DokumentExpressionEli.fromNormEli(
            newExpressionEli,
            regelungstext.getExpressionEli().getSubtype()
          )
        );
        setNewManifestationMetadata(
          regelungstext,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            regelungstext.getManifestationEli().getSubtype(),
            regelungstext.getManifestationEli().getFormat()
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
      .getRegelungstexte()
      .forEach(regelungstext ->
        setNewManifestationMetadata(
          regelungstext,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            regelungstext.getManifestationEli().getSubtype(),
            regelungstext.getManifestationEli().getFormat()
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
    newManifestationOfOldExpression
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression()
      .setFRBRaliasNextVersionId(
        newExpression
          .getRegelungstext1()
          .getMeta()
          .getFRBRExpression()
          .getFRBRaliasCurrentVersionId()
      );

    return newManifestationOfOldExpression;
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
   * @param regelungstext the new expression
   * @param expressionEli the new eli for the expression
   */
  private void setNewExpressionMetadata(
    Regelungstext regelungstext,
    DokumentExpressionEli expressionEli
  ) {
    var oldEli = regelungstext.getExpressionEli();
    var oldVersionId = regelungstext.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId();
    var expression = regelungstext.getMeta().getFRBRExpression();
    expression.setEli(expressionEli);
    expression.setFBRDate(
      expressionEli.getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
      "aenderung"
    );
    expression.setURI(expressionEli.toUri());
    expression.setFRBRVersionNumber(expressionEli.getVersion());
    expression.setFRBRaliasCurrentVersionId(UUID.randomUUID());
    if (!oldEli.getPointInTime().isEqual(expressionEli.getPointInTime())) {
      expression.setFRBRaliasPreviousVersionId(oldVersionId);
    }
    expression.deleteAliasNextVersionId();
  }

  /**
   * Sets the metadata for a new manifestation based on the eli.
   * @param regelungstext the new manifestation
   * @param manifestationEli the new eli for the manifestation
   */
  private void setNewManifestationMetadata(
    Regelungstext regelungstext,
    DokumentManifestationEli manifestationEli
  ) {
    var manifestation = regelungstext.getMeta().getFRBRManifestation();
    manifestation.setEli(manifestationEli);
    manifestation.setURI(manifestationEli.toUri());
    manifestation.setFBRDate(
      manifestationEli.getPointInTimeManifestation().format(DateTimeFormatter.ISO_LOCAL_DATE),
      "generierung"
    );
  }
}
