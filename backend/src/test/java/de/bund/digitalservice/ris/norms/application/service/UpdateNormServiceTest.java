package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.EventRefType;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
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
    void itChangesNothingImportantIfPassiveModificationsAlreadyExist() {

      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // When
      var updatedAmendingLaw =
          updateNormService.updatePassiveModifications(
              new UpdatePassiveModificationsUseCase.Query(targetLaw, amendingLaw));

      // Then
      assertThat(updatedAmendingLaw.getPassiveModifications()).hasSize(1);
      assertThat(updatedAmendingLaw.getTimeBoundaries()).hasSize(4);

      var passiveModification = updatedAmendingLaw.getPassiveModifications().getFirst();
      assertThat(passiveModification.getType()).contains("substitution");
      assertThat(passiveModification.getSourceHref())
          .contains(
              new Href(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"));
      assertThat(passiveModification.getDestinationHref())
          .contains(
              new Href("#hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"));
      assertThat(passiveModification.getForcePeriodEid())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-5");
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
      assertThat(updatedAmendingLaw.getTimeBoundaries())
          .hasSize(4); // 3 existing time-boundaries + 1 new one for the mod
      var eventRefNode = updatedAmendingLaw.getTimeBoundaries().get(3).getEventRefNode();
      assertThat(NodeParser.getValueFromExpression("@type", eventRefNode))
          .contains(EventRefType.AMENDMENT.getValue());

      var newPassiveModification = updatedAmendingLaw.getPassiveModifications().getFirst();
      assertThat(newPassiveModification.getType()).contains("substitution");
      assertThat(newPassiveModification.getSourceHref())
          .contains(
              new Href(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"));
      assertThat(newPassiveModification.getDestinationHref())
          .contains(
              new Href("#hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"));
      assertThat(
              updatedAmendingLaw.getStartDateForTemporalGroup(
                  newPassiveModification.getForcePeriodEid().orElseThrow()))
          .contains("2023-12-30");
    }

    @Test
    void itAddsMultiplePassiveModificationsIfNoneExist() {

      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedAmendingLaw =
          updateNormService.updatePassiveModifications(
              new UpdatePassiveModificationsUseCase.Query(targetLaw, amendingLaw));

      // Then
      assertThat(updatedAmendingLaw.getPassiveModifications()).hasSize(2);
      assertThat(updatedAmendingLaw.getTimeBoundaries())
          .hasSize(4); // 3 existing time-boundaries + 1 new one for both mods

      var newPassiveModification1 = updatedAmendingLaw.getPassiveModifications().getFirst();
      assertThat(newPassiveModification1.getType()).contains("substitution");
      assertThat(newPassiveModification1.getSourceHref())
          .contains(
              new Href(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"));
      assertThat(newPassiveModification1.getDestinationHref())
          .contains(new Href("#hauptteil-1_para-20_abs-1/100-126"));
      assertThat(
              updatedAmendingLaw.getStartDateForTemporalGroup(
                  newPassiveModification1.getForcePeriodEid().orElseThrow()))
          .contains("2023-12-30");

      var newPassiveModification2 = updatedAmendingLaw.getPassiveModifications().get(1);
      assertThat(newPassiveModification2.getType()).contains("substitution");
      assertThat(newPassiveModification2.getSourceHref())
          .contains(
              new Href(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-2_ändbefehl-1.xml"));
      assertThat(newPassiveModification2.getDestinationHref())
          .contains(new Href("#hauptteil-1_para-20_abs-1/100-126"));
      assertThat(
              updatedAmendingLaw.getStartDateForTemporalGroup(
                  newPassiveModification2.getForcePeriodEid().orElseThrow()))
          .contains("2023-12-30");
    }
  }
}
