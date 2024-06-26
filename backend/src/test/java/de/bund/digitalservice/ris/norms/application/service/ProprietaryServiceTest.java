package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
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
    void returnNewProprietaryNodeIfProprietaryNotFound() {
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
    void returnsProprietary() {
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
                          LocalDate.parse("2003-01-01"),
                          new UpdateProprietaryFromNormUseCase.Metadata(
                              "fna", null, null, null, null, null, null, null, null, null, null))))
          // then
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void updatesProprietaryByCreatingNewProprietaryAndMetadatenDsNodes() {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.parse("2003-01-01");
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
                      "fna", null, null, null, null, null, null, null, null, null, null)));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getFna(date)).contains("fna");
      assertThat(result.getArt(date)).isEmpty();
      assertThat(result.getTyp(date)).isEmpty();
      assertThat(result.getSubtyp(date)).isEmpty();
      assertThat(result.getBezeichnungInVorlage(date)).isEmpty();
      assertThat(result.getArtDerNorm(date)).isEmpty();
      assertThat(result.getNormgeber(date)).isEmpty();
      assertThat(result.getBeschliessendesOrgan(date)).isEmpty();
      assertThat(result.getQualifizierteMehrheit(date)).isEmpty();
      assertThat(result.getOrganisationsEinheit(date)).isEmpty();
    }

    @Test
    void updatesProprietaryByCreatingNewMetadatenDsNodes() {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.parse("2003-01-01");
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
                      "ÄN,ÜN",
                      "DDR",
                      "Landtag",
                      false,
                      "BMJ - Bundesministerium der Justiz",
                      "andere org einheit")));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getFna(date)).contains("fna");
      assertThat(result.getArt(date)).contains("art");
      assertThat(result.getTyp(date)).contains("typ");
      assertThat(result.getSubtyp(date)).contains("subtype");
      assertThat(result.getBezeichnungInVorlage(date)).contains("bezeichnungInVorlage");
      assertThat(result.getArtDerNorm(date)).contains("ÄN,ÜN");
      assertThat(result.getNormgeber(date)).contains("DDR");
      assertThat(result.getBeschliessendesOrgan(date)).contains("Landtag");
      assertThat(result.getQualifizierteMehrheit(date)).contains(false);
      assertThat(result.getFederfuehrung(date)).contains("BMJ - Bundesministerium der Justiz");
      assertThat(result.getOrganisationsEinheit(date)).contains("andere org einheit");
    }

    @Test
    void resetsAllFields() {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var date = LocalDate.parse("2003-01-01");
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
                      null, null, null, null, null, null, null, null, null, null, null)));

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      // returns defaults from DE block
      assertThat(result.getFna(date)).contains("754-28-1");
      assertThat(result.getArt(date)).contains("rechtsetzungsdokument");
      assertThat(result.getTyp(date)).contains("gesetz");
      assertThat(result.getSubtyp(date)).isEmpty();
      assertThat(result.getBezeichnungInVorlage(date)).isEmpty();
      assertThat(result.getArtDerNorm(date)).isEmpty();
      assertThat(result.getNormgeber(date)).isEmpty();
      assertThat(result.getBeschliessendesOrgan(date)).isEmpty();
      assertThat(result.getQualifizierteMehrheit(date)).isEmpty();
      assertThat(result.getFederfuehrung(date)).isEmpty();
      assertThat(result.getOrganisationsEinheit(date)).isEmpty();
    }
  }
}
