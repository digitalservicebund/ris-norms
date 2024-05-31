package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import org.junit.jupiter.api.Test;

class ProprietaryServiceTest {

  final ProprietaryService proprietaryService = mock(ProprietaryService.class);

  @Test
  void returnsEmptyIfNormNotFound() throws Exception {
    // given
    var eli = "eli/bund/INVALID_ELI/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    // when
    var result =
        proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli));
    // then
    assertThat(result).isEmpty();
  }

  @Test
  void returnFna() throws Exception {
    // given
    var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    // when
    var result =
        proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli));
    // then
    assertThat(result).isPresent();
    assertThat(result.get().getFna().orElseThrow()).isEqualTo("754-28-1");
  }
}
