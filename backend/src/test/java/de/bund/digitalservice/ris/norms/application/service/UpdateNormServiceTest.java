package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UpdateNormServiceTest {

  final UpdateNormService updateNormService = new UpdateNormService();

  @Nested
  class updatePassiveModifications {

    @Test
    void itChangesNothingImportantIfPassiveModificationsAlreadyExist() {
      // Given
      Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithMods.xml");
      Norm targetLaw = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Law = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");

      // When
      var updatedZfoLaw = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          targetLaw.getExpressionEli()
        )
      );

      // Then
      final List<TextualMod> passiveModifications = updatedZfoLaw
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList());
      assertThat(passiveModifications).hasSize(1);
      assertThat(updatedZfoLaw.getTimeBoundaries()).hasSize(5);

      var passiveModification = passiveModifications.getFirst();
      assertThat(passiveModification.getType()).contains("substitution");
      assertThat(passiveModification.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(passiveModification.getDestinationHref())
        .contains(new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"));
      assertThat(passiveModification.getForcePeriodEid())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-5");
    }

    @Test
    void itAddsPassiveModificationsIfNoneExist() {
      // Given
      Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithMods.xml");
      Norm zf0Law = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedZf0Law = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      final List<TextualMod> passiveModifications = updatedZf0Law
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList());
      assertThat(passiveModifications).hasSize(1);
      assertThat(updatedZf0Law.getTimeBoundaries()).hasSize(4); // 3 existing time-boundaries + 1 new one for the mod
      var eventRefNode = updatedZf0Law.getTimeBoundaries().get(3).getEventRef().getElement();
      assertThat(NodeParser.getValueFromExpression("@type", eventRefNode))
        .contains(EventRefType.AMENDMENT.getValue());

      var newPassiveModification = passiveModifications.getFirst();
      assertThat(newPassiveModification.getType()).contains("substitution");
      assertThat(newPassiveModification.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(newPassiveModification.getDestinationHref())
        .contains(new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"));
      assertThat(
        updatedZf0Law.getStartDateForTemporalGroup(
          newPassiveModification.getForcePeriodEid().orElseThrow()
        )
      )
        .contains("2023-12-30");
    }

    @Test
    void itAddsMultiplePassiveModificationsIfNoneExist() {
      // Given
      Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithMultipleMods.xml");
      Norm targetLaw = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Law = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");

      // When
      var updatedZfoLaw = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          targetLaw.getExpressionEli()
        )
      );

      // Then
      final List<TextualMod> passiveModifications = updatedZfoLaw
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList());
      assertThat(passiveModifications).hasSize(2);
      assertThat(updatedZfoLaw.getTimeBoundaries()).hasSize(5); // 4 existing time-boundaries + 1 new one for both mods

      var newPassiveModification1 = passiveModifications.getFirst();
      assertThat(newPassiveModification1.getType()).contains("substitution");
      assertThat(newPassiveModification1.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(newPassiveModification1.getDestinationHref())
        .contains(
          new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/10-34")
        );
      assertThat(
        updatedZfoLaw.getStartDateForTemporalGroup(
          newPassiveModification1.getForcePeriodEid().orElseThrow()
        )
      )
        .contains("2023-12-30");

      final TextualMod newPassiveModification2 = updatedZfoLaw
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList())
        .get(1);
      assertThat(newPassiveModification2.getType()).contains("substitution");
      assertThat(newPassiveModification2.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-2_ändbefehl-1.xml"
          )
        );
      assertThat(newPassiveModification2.getDestinationHref())
        .contains(
          new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/10-34")
        );
      assertThat(
        updatedZfoLaw.getStartDateForTemporalGroup(
          newPassiveModification2.getForcePeriodEid().orElseThrow()
        )
      )
        .contains("2023-12-30");
    }

    @Test
    void itAddsPassiveModificationWithoutForcePeriodIfNoneExist() {
      // Given
      Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithMods.xml");
      amendingLaw.deleteByEId("meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1");
      Norm zf0Law = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedZf0Law = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(
        updatedZf0Law
          .getMeta()
          .getAnalysis()
          .map(Analysis::getPassiveModifications)
          .orElse(Collections.emptyList())
      )
        .hasSize(1);
      assertThat(updatedZf0Law.getTimeBoundaries()).hasSize(3); // 3 existing time-boundaries

      var newPassiveModification = updatedZf0Law
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList())
        .getFirst();
      assertThat(newPassiveModification.getType()).contains("substitution");
      assertThat(newPassiveModification.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(newPassiveModification.getDestinationHref())
        .contains(new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"));
      assertThat(newPassiveModification.getForcePeriodEid()).isEmpty();
    }

    @Test
    void itAddsOnePassiveModificationWithUpTo() {
      // Given
      final Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
      final TextualMod activeMod = amendingLaw
        .getMeta()
        .getOrCreateAnalysis()
        .getActiveModifications()
        .getFirst();
      activeMod.setDestinationUpTo(
        new Href(
          "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_art-2_abs-2.xml"
        )
      );

      final Norm zf0Law = Fixtures.loadNormFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );
      final NormExpressionEli targetLawELi = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1999/66/1999-01-01/1/deu"
      );

      // When
      var updatedZfoLaw = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(zf0Law, amendingLaw, targetLawELi)
      );

      // Then
      final List<TextualMod> passiveModifications = updatedZfoLaw
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList());
      assertThat(passiveModifications).hasSize(2);

      var firstPassMod = passiveModifications.getFirst();
      assertThat(firstPassMod.getType()).contains("substitution");
      assertThat(firstPassMod.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(firstPassMod.getDestinationHref()).contains(new Href("#hauptteil-1_art-2_abs-1"));
      assertThat(firstPassMod.getDestinationUpTo()).contains(new Href("#hauptteil-1_art-2_abs-2"));
      assertThat(
        updatedZfoLaw.getStartDateForTemporalGroup(firstPassMod.getForcePeriodEid().orElseThrow())
      )
        .contains("2002-02-21");

      var secondPassMod = passiveModifications.getLast();
      assertThat(secondPassMod.getType()).contains("substitution");
      assertThat(secondPassMod.getSourceHref())
        .contains(
          new Href(
            "eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"
          )
        );
      assertThat(secondPassMod.getDestinationHref())
        .contains(
          new Href("#hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b")
        );
      assertThat(secondPassMod.getDestinationUpTo())
        .contains(
          new Href("#hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f")
        );
      assertThat(
        updatedZfoLaw.getStartDateForTemporalGroup(secondPassMod.getForcePeriodEid().orElseThrow())
      )
        .contains("2002-02-21");
    }
  }
}
