package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateRahmenMetadataUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {

  final LoadDokumentPort loadDokumentPort = mock(LoadDokumentPort.class);
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final NormService normService = mock(NormService.class);
  final ProprietaryService proprietaryService = new ProprietaryService(
    loadDokumentPort,
    loadNormPort,
    normService
  );

  @Nested
  class loadProprietary {

    @Test
    void throwsDokumentNotFoundExceptionIfNormNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-verkuendung-1"
      );
      LoadProprietaryFromDokumentUseCase.Options options =
        new LoadProprietaryFromDokumentUseCase.Options(eli);
      // when
      when(loadDokumentPort.loadDokument(any())).thenReturn(Optional.empty());
      // then
      assertThatThrownBy(() ->
        proprietaryService.loadProprietaryFromDokument(options)
      ).isInstanceOf(
        // then
        DokumentNotFoundException.class
      );
    }

    @Test
    void returnNewProprietaryNodeIfProprietaryNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-verkuendung-1"
      );
      var regelungsTextWithoutProprietary = Fixtures.loadRegelungstextFromDisk(
        ProprietaryServiceTest.class,
        "vereinsgesetz-without-proprietary/regelungstext-verkuendung-1.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Options(eli))).thenReturn(
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
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/regelungstext-verkuendung-1"
      );
      var regelungsText = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      when(loadDokumentPort.loadDokument(new LoadDokumentPort.Options(eli))).thenReturn(
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
    void throwsNormNotFoundExceptionIfNormNotFound() {
      // given
      var eli = NormExpressionEli.fromString("eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu");
      UpdateRahmenMetadataUseCase.Options options = new UpdateRahmenMetadataUseCase.Options(
        eli,
        new UpdateRahmenMetadataUseCase.InputMetadata(
          "fna",
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
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> proprietaryService.updateRahmenMetadata(options)).isInstanceOf(
        NormNotFoundException.class
      ); // then
    }

    @Test
    void returnsUpdatedProprietaryNode() {
      // given
      var eli = NormExpressionEli.fromString("eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu");
      var normWithoutProprietary = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(loadNormPort.loadNorm(new LoadNormPort.Options(eli))).thenReturn(
        Optional.of(normWithoutProprietary)
      );
      var normWithProprietary = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(normService.updateNorm(normWithoutProprietary)).thenReturn(normWithProprietary);

      // when
      var result = proprietaryService.updateRahmenMetadata(
        new UpdateRahmenMetadataUseCase.Options(
          eli,
          new UpdateRahmenMetadataUseCase.InputMetadata(
            "dummyFna",
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
      assertThat(result.getFna()).contains("754-28-1");
      assertThat(result.getTyp()).contains("gesetz");
      assertThat(result.getSubtyp()).contains("rechtsverordnung");
      assertThat(result.getBezeichnungInVorlage()).contains("Bezeichnung gemäß Vorlage");
      assertThat(result.getArtDerNorm()).contains("SN,ÄN,ÜN");
      assertThat(result.getBeschliessendesOrgan()).contains("Bundestag");
      assertThat(result.getQualifizierteMehrheit()).contains(true);
      assertThat(result.getStaat()).contains("DEU");
      assertThat(result.getOrganisationsEinheit()).contains("Aktuelle Organisationseinheit");
      assertThat(result.getRessort()).contains("Bundesministerium der Justiz");
    }
  }

  @Nested
  class updateProprietarySingleElement {

    @Test
    void throwsDokumentNotFoundExceptionIfNormNotFound() {
      // given
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/regelungstext-verkuendung-1"
      );
      UpdateProprietarySingleElementFromDokumentUseCase.Options options =
        new UpdateProprietarySingleElementFromDokumentUseCase.Options(
          eli,
          eid,
          new UpdateProprietarySingleElementFromDokumentUseCase.InputMetadata("SN")
        );
      // when
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() ->
        proprietaryService.updateProprietarySingleElementFromDokument(options)
      ).isInstanceOf(NormNotFoundException.class); // then
    }

    @Test
    void returnsUpdatedProprietaryNode() {
      // given
      var eid = new EId("art-z20");
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
      var normWithProprietary = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(normService.updateNorm(any())).thenReturn(normWithProprietary);

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
