package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
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
      assertThat(
              NodeParser.getNodesFromExpression(
                  "//passiveModifications/textualMod", updatedAmendingLaw.getDocument()))
          .hasSize(1);
    }
  }
}
