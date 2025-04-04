package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DokumentDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DokumentDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private DokumentDBService dokumentDBService;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class loadRegelungstext {

    @Test
    void itFindsRegelungstextByExpressionEli() {
      // Given
      var regelungstextOld = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstextCurrent = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15/regelungstext-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstextOld));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstextCurrent));

      // When
      var loadedRegelungstext = dokumentDBService.loadRegelungstext(
        new LoadRegelungstextPort.Command(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
          )
        )
      );

      // Then
      assertThat(loadedRegelungstext).contains(regelungstextCurrent);
    }

    @Test
    void itFindsRegelungstextByManifestationEli() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext));

      // When
      var loadedRegelungstext = dokumentDBService.loadRegelungstext(
        new LoadRegelungstextPort.Command(
          DokumentManifestationEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext);
    }
  }
}
