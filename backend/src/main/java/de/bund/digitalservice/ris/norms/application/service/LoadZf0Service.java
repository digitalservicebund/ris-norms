package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.DocumentUtils;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service for loading ZF0 versions of norms. If not present in DB, it will be created using the
 * amending law and target law.
 */
@Service
public class LoadZf0Service implements LoadZf0UseCase {

  private final UpdateNormService updateNormService;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public LoadZf0Service(
    final UpdateNormService updateNormService,
    final LoadNormByGuidPort loadNormByGuidPort,
    final UpdateOrSaveNormPort updateOrSaveNormPort
  ) {
    this.updateNormService = updateNormService;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public Norm loadOrCreateZf0(Query query) {
    final Norm amendingNorm = query.amendingLaw();
    final Norm targetNorm = query.targetLaw();

    return targetNorm
      .getMeta()
      .getFRBRExpression()
      .getFRBRaliasNextVersionId()
      .flatMap(uuid -> loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(uuid)))
      .orElseGet(() -> createAndPersistZf0(query.persistZf0(), amendingNorm, targetNorm));
  }

  private Norm createAndPersistZf0(boolean shouldPersist, Norm amendingNorm, Norm targetNorm) {
    final Norm zf0Norm = Norm
      .builder()
      .document(DocumentUtils.cloneDocument(targetNorm.getDocument()))
      .build();

    final String announcementDateAmendingLaw = amendingNorm.getMeta().getFRBRWork().getFBRDate();

    updateFRBRExpression(zf0Norm, targetNorm, announcementDateAmendingLaw);
    updateFRBRManifestation(zf0Norm, announcementDateAmendingLaw);
    updateNormService.updatePassiveModifications(
      new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNorm.getEli())
    );

    if (shouldPersist) {
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(zf0Norm));
    }
    return zf0Norm;
  }

  private void updateFRBRExpression(
    final Norm zf0Norm,
    final Norm targetNorm,
    final String announcementDateAmendingLaw
  ) {
    final FRBRExpression zf0FrbrExpression = zf0Norm.getMeta().getFRBRExpression();

    updateFRBRalias(zf0FrbrExpression, targetNorm);
    updateEli(zf0FrbrExpression, announcementDateAmendingLaw);
    updateFRBRDate(zf0FrbrExpression, announcementDateAmendingLaw);
  }

  private void updateFRBRalias(FRBRExpression zf0FrbrExpression, Norm targetNorm) {
    final UUID previousVersionId = targetNorm
      .getMeta()
      .getFRBRExpression()
      .getFRBRaliasCurrentVersionId();
    zf0FrbrExpression.setFRBRaliasPreviousVersionId(previousVersionId);
    final UUID zf0currentVersionId = UUID.randomUUID();
    zf0FrbrExpression.setFRBRaliasCurrentVersionId(zf0currentVersionId);
    zf0FrbrExpression.deleteAliasNextVersionId();

    FRBRExpression targetNormExpression = targetNorm.getMeta().getFRBRExpression();
    targetNormExpression.setFRBRaliasNextVersionId(zf0currentVersionId);
  }

  private void updateEli(FRBRExpression zf0FrbrExpression, String announcementDateAmendingLaw) {
    final Eli zf0Eli = new Eli(zf0FrbrExpression.getEli());
    zf0Eli.setPointInTime(announcementDateAmendingLaw);
    zf0FrbrExpression.setEli(zf0Eli.getValue());
  }

  private void updateFRBRDate(
    FRBRExpression zf0FrbrExpression,
    String announcementDateAmendingLaw
  ) {
    zf0FrbrExpression.setFBRDate(announcementDateAmendingLaw, "aenderung");
  }

  private void updateFRBRManifestation(
    final Norm zf0Norm,
    final String announcementDateAmendingLaw
  ) {
    final FRBRManifestation frbrManifestation = zf0Norm.getMeta().getFRBRManifestation();

    // 1.replace date of eli parts
    final Eli zf0Eli = new Eli(frbrManifestation.getEli());
    zf0Eli.setPointInTime(announcementDateAmendingLaw);
    frbrManifestation.setEli(zf0Eli.getValue());

    // 2. FRBRdate --> current system date + @name="generierung"
    frbrManifestation.setFBRDate(LocalDate.now().toString(), "generierung");
  }
}
