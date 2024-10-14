package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final NormService normService = mock(NormService.class);
  final ProprietaryService proprietaryService = new ProprietaryService(
    loadNormPort,
    updateNormPort,
    normService
  );

  @Nested
  class loadProprietary {

    @Test
    void throwsNormNotFoundExceptionIfNormNotFound() {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      LoadProprietaryFromNormUseCase.Query query = new LoadProprietaryFromNormUseCase.Query(eli);
      // when
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      // then
      assertThatThrownBy(() -> proprietaryService.loadProprietaryFromNorm(query))
        // then
        .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void returnNewProprietaryNodeIfProprietaryNotFound() {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var normWithoutProprietary = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithoutProprietary));

      // when
      var result = proprietaryService.loadProprietaryFromNorm(
        new LoadProprietaryFromNormUseCase.Query(eli)
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
    }

    @Test
    void returnsProprietary() {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithProprietary));
      // when
      var result = proprietaryService.loadProprietaryFromNorm(
        new LoadProprietaryFromNormUseCase.Query(eli)
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
      var eli = ExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      UpdateProprietaryFrameFromNormUseCase.Query query =
        new UpdateProprietaryFrameFromNormUseCase.Query(
          eli,
          LocalDate.parse("2003-01-01"),
          new UpdateProprietaryFrameFromNormUseCase.Metadata(
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
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> proprietaryService.updateProprietaryFrameFromNorm(query))
        // then
        .isInstanceOf(NormNotFoundException.class);
    }
  }

  @Nested
  class updateProprietarySingleElement {

    @Test
    void throwsNormNotFoundExceptionIfNormNotFound() {
      // given
      var eid = "hauptteil-1_abschnitt-0_art-1";
      var eli = ExpressionEli.fromString(
        "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      UpdateProprietarySingleElementFromNormUseCase.Query query =
        new UpdateProprietarySingleElementFromNormUseCase.Query(
          eli,
          eid,
          LocalDate.parse("2003-01-01"),
          new UpdateProprietarySingleElementFromNormUseCase.Metadata("SN")
        );
      // when
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> proprietaryService.updateProprietarySingleElementFromNorm(query))
        // then
        .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void updatesProprietaryByCreatingNewProprietaryAndMetadatenDsAndEinzelelementNodes() {
      // given
      var eid = "hauptteil-1_abschnitt-0_art-1";
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var date = LocalDate.parse("2003-01-01");
      var normWithoutProprietary = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithoutProprietary));

      // when
      var result = proprietaryService.updateProprietarySingleElementFromNorm(
        new UpdateProprietarySingleElementFromNormUseCase.Query(
          eli,
          eid,
          date,
          new UpdateProprietarySingleElementFromNormUseCase.Metadata("SN")
        )
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getArtDerNorm(date, eid)).contains("SN");
    }

    @Test
    void updatesProprietaryByCreatingNewMetadatenDsNodes() {
      // given
      var eid = "hauptteil-1_abschnitt-0_art-1";
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var date = LocalDate.parse("2003-01-01");
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithProprietary));

      // when
      var result = proprietaryService.updateProprietarySingleElementFromNorm(
        new UpdateProprietarySingleElementFromNormUseCase.Query(
          eli,
          eid,
          date,
          new UpdateProprietarySingleElementFromNormUseCase.Metadata("ÜN")
        )
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getArtDerNorm(date, eid)).contains("ÜN");
    }

    @Test
    void resetsAllFields() {
      // given
      var eid = "hauptteil-1_abschnitt-0_art-1";
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var date = LocalDate.parse("1980-01-01");
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithProprietary));

      // when
      var result = proprietaryService.updateProprietarySingleElementFromNorm(
        new UpdateProprietarySingleElementFromNormUseCase.Query(
          eli,
          eid,
          date,
          new UpdateProprietarySingleElementFromNormUseCase.Metadata(null)
        )
      );

      // then
      assertThat(result).isInstanceOf(Proprietary.class);
      assertThat(result.getArtDerNorm(date, eid)).isEmpty();
    }
  }
}
