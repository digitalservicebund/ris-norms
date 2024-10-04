package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.CreateZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.utils.DocumentUtils;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Service for loading ZF0 versions of norms. If not present in DB, it will be created using the
 * amending law and target law.
 */
@Service
public class CreateZf0Service implements CreateZf0UseCase {

  private final UpdateNormService updateNormService;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public CreateZf0Service(
    final UpdateNormService updateNormService,
    final UpdateOrSaveNormPort updateOrSaveNormPort
  ) {
    this.updateNormService = updateNormService;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public Norm createZf0(Query query) {
    final Norm amendingNorm = query.amendingLaw();
    final Norm targetNorm = query.targetLaw();

    return createAndPersistZf0(query.persistZf0(), amendingNorm, targetNorm);
  }

  private Norm createAndPersistZf0(boolean shouldPersist, Norm amendingNorm, Norm targetNorm) {
    final Norm zf0Norm = Norm
      .builder()
      .document(DocumentUtils.cloneDocument(targetNorm.getDocument()))
      .build();

    updateFRBRManifestation(zf0Norm);
    updateNormService.updatePassiveModifications(
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

  private void updateFRBRManifestation(final Norm zf0Norm) {
    final FRBRManifestation frbrManifestation = zf0Norm.getMeta().getFRBRManifestation();
    final String date = LocalDate.now().toString();

    // 1.replace date of eli parts
    final ManifestationEli zf0Eli = ManifestationEli.fromString(frbrManifestation.getEli());
    zf0Eli.setPointInTimeManifestation(date);
    frbrManifestation.setEli(zf0Eli.toString());
    frbrManifestation.setURI(zf0Eli.toUri());

    // 2. FRBRdate --> current system date + @name="generierung"
    frbrManifestation.setFBRDate(date, "generierung");
  }
}
