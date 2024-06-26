package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeMachineServiceTest {
  final NormService normService = mock(NormService.class);

  final TimeMachineService timeMachineService = new TimeMachineService(normService);

  @Nested
  class applyPassiveModifications {
    @Test
    void returnUnchangedIfNoPassiveModifications() {
      // given
      final var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                      """))
              .build();

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX));

      // then
      assertThat(result).isEqualTo(norm);
    }

    @Test
    void applyOnePassiveModification() {
      // given
      final var norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      final var amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void applyPassiveModificationsInCorrectOrder() {
      // given
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");

      final var amendingLaw = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2, 3 oder 4 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void applyPassiveModificationsWhereTargetNodeEqualsNodeToChange() {
      // given
      final var norm =
          NormFixtures.loadFromDisk("NormWithPassiveModsWhereTargetNodeEqualsNodeToChange.xml");

      final var amendingLaw =
          NormFixtures.loadFromDisk("NormWithModsWhereTargetNodeEqualsNodeToChange.xml");
      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              """
                              Das Bundesamt für Verfassungsschutz trifft für die gemeinsamen Dateien die technischen und organisatorischen Maßnahmen
                                                              entsprechend §
                                                              64 des Bundesdatenschutzgesetzes. Es hat bei jedem Zugriff für Zwecke der Datenschutzkontrolle den Zeitpunkt, die
                                                              Angaben, die die
                                                              Feststellung der abgefragten Datensätze ermöglichen, sowie die abfragende Stelle zu protokollieren. Die Auswertung der
                                                              Protokolldaten
                                                              ist nach dem Stand der Technik zu gewährleisten. Die protokollierten Daten dürfen nur für Zwecke der
                                                              Datenschutzkontrolle, der
                                                              Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebs der Datenverarbeitungsanlage verwendet werden.
                                                              Die
                                                              Protokolldaten sind nach Ablauf von fünf Jahren zu löschen.
                              """);
    }

    @Test
    void applyPassiveModificationsBeforeDate() {
      // given
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var amendingLaw = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(
                  norm, Instant.parse("2017-03-01T00:00:00.000Z")));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void applyOnePassiveModificationWithCustomNorm() {
      // given
      final var norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      final var amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX, Set.of(amendingLaw)));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void doNotApplyPassiveModificationWithoutForcePeriod() {
      // given
      final var norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      norm.deleteByEId("meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1");

      final var amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, Instant.MAX));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }
  }
}
