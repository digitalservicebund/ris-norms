package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.CreateZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import org.springframework.stereotype.Service;

/**
 * Service for loading ZF0 versions of norms. If not present in DB, it will be created using the
 * amending law and target law.
 */
@Service
public class CreateZf0Service implements CreateZf0UseCase {

  private final UpdateNormService updateNormService;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;

  public CreateZf0Service(
    final UpdateNormService updateNormService,
    final UpdateOrSaveNormPort updateOrSaveNormPort,
    CreateNewVersionOfNormService createNewVersionOfNormService
  ) {
    this.updateNormService = updateNormService;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
  }

  @Override
  public Norm createZf0(Query query) {
    final Norm amendingNorm = query.amendingLaw();
    final Norm targetNorm = query.targetLaw();

    return createAndPersistZf0(query.persistZf0(), amendingNorm, targetNorm);
  }

  private Norm createAndPersistZf0(boolean shouldPersist, Norm amendingNorm, Norm targetNorm) {
    final Norm zf0Norm = createNewVersionOfNormService.createNewManifestation(targetNorm);

    updateNormService.updateOnePassiveModification(
      new UpdatePassiveModificationsUseCase.Query(
        zf0Norm,
        amendingNorm,
        targetNorm.getExpressionEli()
      )
    );

    if (shouldPersist) {
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(zf0Norm));
    }
    return zf0Norm;
  }
}
