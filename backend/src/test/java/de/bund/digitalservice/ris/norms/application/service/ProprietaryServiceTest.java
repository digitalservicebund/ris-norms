package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {

  final LoadDokumentPort loadDokumentPort = mock(LoadDokumentPort.class);
  final UpdateDokumentPort updateDokumentPort = mock(UpdateDokumentPort.class);
  final ProprietaryService proprietaryService = new ProprietaryService(
    loadDokumentPort,
    updateDokumentPort
  );

  @Nested
  class loadProprietary {

    @Test
    void throwsDokumentNotFoundExceptionIfNormNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      LoadProprietaryFromDokumentUseCase.Options options =
        new LoadProprietaryFromDokumentUseCase.Options(eli);
      // when
      when(loadDokumentPort.loadDokument(any())).thenReturn(Optional.empty());
      // then
      assertThatThrownBy(() -> proprietaryService.loadProprietaryFromDokument(options)
      ).isInstanceOf(
        // then
        DokumentNotFoundException.class
      );
    }

    @Test
    void returnNewProprietaryNodeIfProprietaryNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      var regelungsTextWithoutProprietary = Fixtures.loadRegelungstextFromDisk(
        ProprietaryServiceTest.class,
        "vereinsgesetz-without-proprietary.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Command(eli))).thenReturn(
        Optional.of(regelungsTextWithoutProprietary)
      );

      // when
      var result = proprietaryService.loadProprietaryFromDokument(
        new LoadProprietaryFromDokumentUseCase.Options(eli)
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
    }

    @Test
    void returnsProprietary() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      var regelungsText = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Command(eli))).thenReturn(
        Optional.of(regelungsText)
      );
      // when
      var result = proprietaryService.loadProprietaryFromDokument(
        new LoadProprietaryFromDokumentUseCase.Options(eli)
      );
      // then
      assertThat(result).isInstanceOf(Proprietary.class);
    }
  }

  @Nested
  class updateProprietaryFrame {

    @Test
    void throwsDokumentNotFoundExceptionIfNormNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      UpdateProprietaryFrameFromDokumentUseCase.Options options =
        new UpdateProprietaryFrameFromDokumentUseCase.Options(
          eli,
          new UpdateProprietaryFrameFromDokumentUseCase.InputMetadata(
            "fna",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
          )
        );
      // when
      when(loadDokumentPort.loadDokument(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> proprietaryService.updateProprietaryFrameFromDokument(options)
      ).isInstanceOf(DokumentNotFoundException.class); // then
    }

    @Test
    void returnsUpdatedProprietaryNode() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      var regelungsTextWithoutProprietary = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Command(eli))).thenReturn(
        Optional.of(regelungsTextWithoutProprietary)
      );
      var regelungsTextWithProprietary = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        updateDokumentPort.updateDokument(
          new UpdateDokumentPort.Command(regelungsTextWithoutProprietary)
        )
      ).thenReturn(Optional.of(regelungsTextWithProprietary));

      // when
      var result = proprietaryService.updateProprietaryFrameFromDokument(
        new UpdateProprietaryFrameFromDokumentUseCase.Options(
          eli,
          new UpdateProprietaryFrameFromDokumentUseCase.InputMetadata(
            "dummyFna",
            "dummyArt",
            "dummyTyp",
            "dummySubtyp",
            "dummyBezeichnung",
            "dummyArtDerNorm",
            "DE",
            "dummyOrgan",
            false,
            "dummyRessort",
            "dummyOrganisation"
          )
        )
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getMetadataValue(Metadata.FNA)).contains("754-28-1");
      assertThat(result.getMetadataValue(Metadata.TYP)).contains("gesetz");
      assertThat(result.getMetadataValue(Metadata.ART)).contains("regelungstext");
      assertThat(result.getMetadataValue(Metadata.SUBTYP)).contains("rechtsverordnung");
      assertThat(result.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "Bezeichnung gemäß Vorlage"
      );
      assertThat(result.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(result.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("Bundestag");
      assertThat(result.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains("true");
      assertThat(result.getMetadataValue(Metadata.STAAT)).contains("DEU");
      assertThat(result.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Aktuelle Organisationseinheit"
      );
      assertThat(result.getRessort(LocalDate.parse("2002-10-02"))).contains(
        "Bundesministerium der Justiz"
      );
    }
  }

  @Nested
  class updateProprietarySingleElement {

    @Test
    void throwsDokumentNotFoundExceptionIfNormNotFound() {
      // given
      var eid = new EId("hauptteil-1_abschnitt-0_art-1");
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      UpdateProprietarySingleElementFromDokumentUseCase.Options options =
        new UpdateProprietarySingleElementFromDokumentUseCase.Options(
          eli,
          eid,
          new UpdateProprietarySingleElementFromDokumentUseCase.InputMetadata("SN")
        );
      // when
      when(loadDokumentPort.loadDokument(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() ->
        proprietaryService.updateProprietarySingleElementFromDokument(options)
      ).isInstanceOf(DokumentNotFoundException.class); // then
    }

    @Test
    void returnsUpdatedProprietaryNode() {
      // given
      var eid = new EId("hauptteil-1_art-1");
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-1"
      );
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Command(eli))).thenReturn(
        Optional.of(regelungstext)
      );
      var regelungsTextWithProprietary = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        updateDokumentPort.updateDokument(new UpdateDokumentPort.Command(regelungstext))
      ).thenReturn(Optional.of(regelungsTextWithProprietary));

      // when
      var result = proprietaryService.updateProprietarySingleElementFromDokument(
        new UpdateProprietarySingleElementFromDokumentUseCase.Options(
          eli,
          eid,
          new UpdateProprietarySingleElementFromDokumentUseCase.InputMetadata("SN")
        )
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("SN");
    }
  }
}
