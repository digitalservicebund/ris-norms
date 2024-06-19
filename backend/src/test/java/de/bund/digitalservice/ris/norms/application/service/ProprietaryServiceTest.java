package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final ProprietaryService proprietaryService =
      new ProprietaryService(loadNormPort, updateNormPort);

  @Nested
  class loadProprietary {
    @Test
    void throwsNormNotFoundExceptionIfNormNotFound() {
      // given
      var eli = "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      // when
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      // then
      assertThatThrownBy(
              () ->
                  proprietaryService.loadProprietaryFromNorm(
                      new LoadProprietaryFromNormUseCase.Query(eli)))
          // then
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void returnNewProprietaryNodeIfProprietaryNotFound() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var normWithoutProprietary = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
          .thenReturn(Optional.of(normWithoutProprietary));

      // when
      var result =
          proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
    }

    @Test
    void returnsProprietary() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
          .thenReturn(Optional.of(normWithProprietary));
      // when
      var result =
          proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli));
      // then
      assertThat(result).isInstanceOf(Proprietary.class);
    }
  }

  @Nested
  class updateProprietary {

    @Test
    void throwsNormNotFoundExceptionIfNormNotFound() {
      // given
      var eli = "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      // when
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      assertThatThrownBy(
              () ->
                  proprietaryService.updateProprietaryFromNorm(
                      new UpdateProprietaryFromNormUseCase.Query(
                          eli,
                          LocalDate.now(),
                          new UpdateProprietaryFromNormUseCase.Metadata(
                              "fna", null, null, null, null, null, null, null, null))))
          // then
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void updatesProprietaryByCreatingNewProprietaryAndMetadatenDsNodes() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.now();
      var normWithoutProprietary = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
          .thenReturn(Optional.of(normWithoutProprietary));

      // when
      var result =
          proprietaryService.updateProprietaryFromNorm(
              new UpdateProprietaryFromNormUseCase.Query(
                  eli,
                  date,
                  new UpdateProprietaryFromNormUseCase.Metadata(
                      "fna", null, null, null, null, null, null, null, null)));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getFna(date)).contains("fna");
    }

    @Test
    void updatesProprietaryByCreatingNewMetadatenDsNodes() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.now();
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
          .thenReturn(Optional.of(normWithProprietary));

      // when
      var result =
          proprietaryService.updateProprietaryFromNorm(
              new UpdateProprietaryFromNormUseCase.Query(
                  eli,
                  date,
                  new UpdateProprietaryFromNormUseCase.Metadata(
                      "fna",
                      "art",
                      "typ",
                      "subtype",
                      "bezeichnungInVorlage",
                      "SN,ÄN,ÜN",
                      "DEU",
                      "Bundestag",
                      true)));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getFna(date)).contains("fna");
      assertThat(result.getArt(date)).contains("art");
      assertThat(result.getTyp(date)).contains("typ");
      assertThat(result.getSubtyp(date)).contains("subtype");
      assertThat(result.getBezeichnungInVorlage(date)).contains("bezeichnungInVorlage");
      assertThat(result.getArtDerNorm(date)).contains("SN,ÄN,ÜN");
      assertThat(result.getNormgeber(date)).contains("DEU");
      assertThat(result.getBeschliessendesOrgan(date)).contains("Bundestag");
      assertThat(result.getQualifizierteMehrheit(date)).contains(true);
    }

    @Test
    void resetsAllFields() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.now();
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
          .thenReturn(Optional.of(normWithProprietary));

      // when
      var result =
          proprietaryService.updateProprietaryFromNorm(
              new UpdateProprietaryFromNormUseCase.Query(
                  eli,
                  date,
                  new UpdateProprietaryFromNormUseCase.Metadata(
                      null, null, null, null, null, null, null, null, null)));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getFna(date)).contains("");
      assertThat(result.getArt(date)).contains("");
      assertThat(result.getTyp(date)).contains("");
      assertThat(result.getSubtyp(date)).contains("");
      assertThat(result.getBezeichnungInVorlage(date)).contains("");
      assertThat(result.getArtDerNorm(date)).contains("");
      assertThat(result.getNormgeber(date)).contains("");
      assertThat(result.getBeschliessendesOrgan(date)).contains("");
      assertThat(result.getQualifizierteMehrheit(date)).isEmpty();
    }
  }
}
