package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ModificationValidatorTest {
  private final DBService dbService = mock(DBService.class);
  private final ModificationValidator underTest = new ModificationValidator(dbService);

  @Test
  void allAffectedDocumentsExist() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    when(dbService.loadNorm(any()))
        .thenReturn(
            Optional.of(
                new Norm(
                    XmlMapper.toDocument(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <test>content</test>"))));

    // when
    underTest.affectedDocumentsExists(amendingLaw);

    // then
    verify(dbService, times(1))
        .loadNorm(
            argThat(
                argument ->
                    Objects.equals(
                        argument.eli(),
                        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")));
  }

  @Test
  void normDoesNotExist() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.empty());

    // when
    Throwable thrown = catchThrowable(() -> underTest.affectedDocumentsExists(amendingLaw));

    // then
    assertThat(thrown).isInstanceOf(XmlProcessingException.class);
  }
}
