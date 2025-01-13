package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.UpdateActiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

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
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm zf0Law = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedZf0Law = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          ExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
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
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

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
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw.deleteByEId("meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1");
      Norm zf0Law = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      // When
      var updatedZf0Law = updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(
          zf0Law,
          amendingLaw,
          ExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
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
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
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

      final Norm zf0Law = NormFixtures.loadFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );
      final ExpressionEli targetLawELi = ExpressionEli.fromString(
        "eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1"
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

  @Nested
  class updateActiveModifications {

    @Test
    void itChangesActiveModForQuotedText() {
      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      ExpressionEli targetNormEli = targetNorm.getExpressionEli();
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      String newCharacterRange = "20-25";
      Href newDestinationHref = new Href.Builder()
        .setEli(targetNormEli)
        .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange(newCharacterRange))
        .setFileExtension("xml")
        .buildAbsolute();
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newContent = "new-text";

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          eId,
          newDestinationHref,
          null,
          newTimeBoundaryEid,
          newContent
        )
      );

      // Then
      final TextualMod activeModifications = updatedAmendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst();
      assertThat(activeModifications.getDestinationHref()).contains(newDestinationHref);
      assertThat(activeModifications.getForcePeriodEid()).contains(newTimeBoundaryEid);
      final Mod mod = updatedAmendingNorm
        .getMods()
        .stream()
        .filter(f -> f.getEid().equals(eId))
        .findFirst()
        .orElseThrow();
      assertThat(mod.getNewText()).contains(newContent);
    }

    @Test
    void itChangesActiveModForQuotedStructureRangeMod() {
      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModsQuotedStructure.xml");
      ExpressionEli targetNormEli = targetNorm.getExpressionEli();
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      Href newDestinationHref = new Href(
        targetNormEli + "/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
      );
      Href newDestinationUpTo = new Href(
        targetNormEli + "/hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-2/"
      );
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newContent = "new-text";

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          eId,
          newDestinationHref,
          newDestinationUpTo,
          newTimeBoundaryEid,
          newContent
        )
      );

      // Then
      final Optional<TextualMod> activeModifications = updatedAmendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .stream()
        .filter(activeMod -> activeMod.getSourceHref().get().toString().replace("#", "").equals(eId)
        )
        .findFirst();
      assertThat(activeModifications).isPresent();
      assertThat(activeModifications.get().getForcePeriodEid()).contains(newTimeBoundaryEid);
      assertThat(activeModifications.get().getDestinationHref()).contains(newDestinationHref);
      assertThat(activeModifications.get().getDestinationUpTo()).contains(newDestinationUpTo);
    }

    @Test
    void itChangesActiveModForQuotedStructureSingleTargetMod() {
      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModsQuotedStructure.xml");
      ExpressionEli targetNormEli = targetNorm.getExpressionEli();
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      Href newDestinationHref = new Href(
        targetNormEli + "/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
      );
      Href newDestinationUpTo = null;
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newContent = "new-text";

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          eId,
          newDestinationHref,
          newDestinationUpTo,
          newTimeBoundaryEid,
          newContent
        )
      );

      // Then
      final Optional<TextualMod> activeModifications = updatedAmendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .stream()
        .filter(activeMod -> activeMod.getSourceHref().get().toString().replace("#", "").equals(eId)
        )
        .findFirst();
      assertThat(activeModifications).isPresent();
      assertThat(activeModifications.get().getDestinationHref()).contains(newDestinationHref);
      assertThat(activeModifications.get().getDestinationUpTo()).isNotPresent();
      assertThat(activeModifications.get().getForcePeriodEid()).contains(newTimeBoundaryEid);
    }

    @Test
    void itDeletesUpToInActiveModForQuotedStructureSingleTargetMod() {
      // Given
      Norm amendingLawSetup = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      Norm targetNormSetup = NormFixtures.loadFromDisk("NormWithoutPassiveModsQuotedStructure.xml");
      ExpressionEli targetNormEliSetup = targetNormSetup.getExpressionEli();
      String eIdSetup =
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      Href newDestinationHrefSetup = new Href(
        targetNormEliSetup + "/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
      );
      Href newDestinationUpToSetup = new Href(
        targetNormEliSetup + "/hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-2/"
      );
      String newTimeBoundaryEidSetup = "#time-boundary-eid";
      String newContentSetup = "new-text";

      updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLawSetup,
          eIdSetup,
          newDestinationHrefSetup,
          newDestinationUpToSetup,
          newTimeBoundaryEidSetup,
          newContentSetup
        )
      );

      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModsQuotedStructure.xml");
      ExpressionEli targetNormEli = targetNorm.getExpressionEli();
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      Href newDestinationHref = new Href(
        targetNormEli + "/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
      );
      Href newDestinationUpTo = null;
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newContent = "new-text";

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          eId,
          newDestinationHref,
          newDestinationUpTo,
          newTimeBoundaryEid,
          newContent
        )
      );

      // Then
      final Optional<TextualMod> activeModifications = updatedAmendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .stream()
        .filter(activeMod -> activeMod.getSourceHref().get().toString().replace("#", "").equals(eId)
        )
        .findFirst();
      assertThat(activeModifications).isPresent();
      assertThat(activeModifications.get().getDestinationHref()).contains(newDestinationHref);
      assertThat(activeModifications.get().getDestinationUpTo()).isNotPresent();
      assertThat(activeModifications.get().getForcePeriodEid()).contains(newTimeBoundaryEid);
    }

    @Test
    void itReplacesRefWithRrefAfterPassingUpTo() {
      // Given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      final Norm targetNorm = NormFixtures.loadFromDisk(
        "NormWithoutPassiveModsQuotedStructure.xml"
      );
      final String modEid =
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      final Href newDestinationHref = new Href(
        targetNorm.getExpressionEli() + "/hauptteil-1_art-2_abs-1.xml"
      );
      final Href newDestinationUpTo = new Href(
        targetNorm.getExpressionEli() + "/hauptteil-1_art-2_abs-3.xml"
      );

      final Optional<Mod> modBeforeUpdate = amendingLaw
        .getMods()
        .stream()
        .filter(m -> m.getEid().equals(modEid))
        .findFirst();

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          modEid,
          newDestinationHref,
          newDestinationUpTo,
          "#time-boundary-eid",
          "<test></test>"
        )
      );

      // Then
      final Optional<Mod> updatedMod = updatedAmendingNorm
        .getMods()
        .stream()
        .filter(m -> m.getEid().equals(modEid))
        .findFirst();
      assertThat(updatedMod).isPresent();
      assertThat(updatedMod.get().getTargetRefHref()).isEmpty();
      assertThat(updatedMod.get().getTargetRrefFrom()).isPresent();
      assertThat(updatedMod.get().getTargetRrefFrom())
        .isPresent()
        .get()
        .hasToString(
          "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_art-2_abs-1.xml"
        );
      assertThat(updatedMod.get().getTargetRrefUpTo()).isPresent();
      assertThat(updatedMod.get().getTargetRrefUpTo())
        .isPresent()
        .get()
        .hasToString(
          "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_art-2_abs-3.xml"
        );
      assertThat(updatedMod.get().getElement().getTextContent())
        .isEqualTo(modBeforeUpdate.get().getElement().getTextContent());
    }

    @Test
    void itReplacesRrefWithRefAfterNotPassingUpTo() {
      // Given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
      final Norm targetNorm = NormFixtures.loadFromDisk(
        "NormWithoutPassiveModsQuotedStructure.xml"
      );
      final String modEid =
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      final Href newDestinationHref = new Href(
        targetNorm.getExpressionEli() + "/hauptteil-1_art-2_abs-2.xml"
      );

      final Optional<Mod> modBeforeUpdate = amendingLaw
        .getMods()
        .stream()
        .filter(m -> m.getEid().equals(modEid))
        .findFirst();

      // When
      var updatedAmendingNorm = updateNormService.updateOneActiveModification(
        new UpdateActiveModificationsUseCase.Query(
          amendingLaw,
          modEid,
          newDestinationHref,
          null,
          "#time-boundary-eid",
          "<test></test>"
        )
      );

      // Then
      final Optional<Mod> updatedMod = updatedAmendingNorm
        .getMods()
        .stream()
        .filter(m -> m.getEid().equals(modEid))
        .findFirst();
      assertThat(updatedMod).isPresent();
      assertThat(updatedMod.get().getTargetRrefFrom()).isEmpty();
      assertThat(updatedMod.get().getTargetRrefUpTo()).isEmpty();
      assertThat(updatedMod.get().getTargetRefHref()).isPresent();
      assertThat(updatedMod.get().getTargetRefHref())
        .isPresent()
        .get()
        .hasToString(
          "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_art-2_abs-2.xml"
        );
      assertThat(updatedMod.get().getElement().getTextContent())
        .isEqualTo(modBeforeUpdate.get().getElement().getTextContent());
    }
  }
}
