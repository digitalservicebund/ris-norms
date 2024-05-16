package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UpdateNormServiceTest {
  final UpdateNormService updateNormService = new UpdateNormService();

  @Nested
  class updatePassiveModifications {
    @Test
    void itChangesNothingIfPassiveModificationsAlreadyExist() {

      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // When
      var updatedAmendingLaw =
          updateNormService.updatePassiveModifications(
              new UpdatePassiveModificationsUseCase.Query(targetLaw, amendingLaw));

      // Then
      assertThat(updatedAmendingLaw.getPassiveModifications()).hasSize(1);
    }

    @Test
    void itAddsPassiveModificationsIfNoneExist() {

      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedAmendingLaw =
          updateNormService.updatePassiveModifications(
              new UpdatePassiveModificationsUseCase.Query(targetLaw, amendingLaw));

      // Then
      assertThat(updatedAmendingLaw.getPassiveModifications()).hasSize(1);

      var newPassiveModification = updatedAmendingLaw.getPassiveModifications().getFirst();
      assertThat(newPassiveModification.getType()).contains("substitution");
      assertThat(newPassiveModification.getSourceEli())
          .contains("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1");
      assertThat(newPassiveModification.getSourceEid())
          .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1");
      assertThat(newPassiveModification.getDestinationHref()).contains("#para-6_abs-3/100-126");
      // TODO: (Malte Laukötter, 2024-05-16) verify time boundaries
    }
  }
}
