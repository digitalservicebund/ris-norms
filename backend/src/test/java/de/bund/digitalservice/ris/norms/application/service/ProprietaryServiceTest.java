package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadElementsByTypeFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final ProprietaryService proprietaryService = new ProprietaryService(loadNormPort);

  @Test
  void throwsNormNotFoundExceptionIfNormNotFound() {
    // given
    var eli = "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    // when
    assertThatThrownBy(
            () ->
                proprietaryService.loadProprietaryFromNorm(
                    new LoadProprietaryFromNormUseCase.Query(eli)))
        // then
        .isInstanceOf(LoadElementsByTypeFromNormUseCase.NormNotFoundException.class);
  }

  // TODO: proprietary not found

  // TODO: fna not found

  @Test
  void returnFna() throws LoadElementsByTypeFromNormUseCase.NormNotFoundException {
    // given
    var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
    when(loadNormPort.loadNorm(new LoadNormPort.Command(eli)))
        .thenReturn(Optional.of(normWithProprietary));
    // when
    var result =
        proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli));
    // then
    assertThat(result).isPresent();
    assertThat(result.get().getFna().orElseThrow()).isEqualTo("754-28-1");
  }
}
