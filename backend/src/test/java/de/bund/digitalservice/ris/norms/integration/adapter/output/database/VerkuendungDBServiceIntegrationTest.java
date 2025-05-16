package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.VerkuendungDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveVerkuendungPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class VerkuendungDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private VerkuendungDBService verkuendungDBService;

  @Autowired
  private VerkuendungRepository verkuendungRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    verkuendungRepository.deleteAll();
  }

  @Test
  void itFindsVerkuendungOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    dokumentRepository.save(DokumentMapper.mapToDto(norm.getRegelungstext1()));
    var verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();
    verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung));

    // When
    final Optional<Verkuendung> verkuendungOptional = verkuendungDBService.loadVerkuendungByNormEli(
      new LoadVerkuendungByNormEliPort.Command(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      )
    );

    // Then
    assertThat(verkuendungOptional)
      .get()
      .usingRecursiveComparison()
      .ignoringFields("importTimestamp")
      .isEqualTo(verkuendung);

    assertThat(verkuendungOptional.get().getImportTimestamp()).isCloseTo(
      Instant.now(),
      new TemporalUnitWithinOffset(5, ChronoUnit.MINUTES)
    );
  }

  @Test
  void itLoadsAllVerkuendungenFromDB() {
    // Given
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
    );

    var verkuendung1 = Verkuendung.builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      .build();
    verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung1));
    var verkuendung2 = Verkuendung.builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
      .build();
    verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung2));

    // When
    final List<Verkuendung> verkuendungs = verkuendungDBService.loadAllVerkuendungen();

    // Then
    assertThat(verkuendungs)
      .usingRecursiveFieldByFieldElementComparatorIgnoringFields("importTimestamp")
      .containsExactly(verkuendung2, verkuendung1);
  }

  @Test
  void itCreatesNewVerkuendung() {
    // Given
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
    );
    var verkuendung = Verkuendung.builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
      .build();

    // When
    var verkuendungFromDatabase = verkuendungDBService.updateOrSaveVerkuendung(
      new UpdateOrSaveVerkuendungPort.Command(verkuendung)
    );

    // Then
    assertThat(verkuendungFromDatabase)
      .usingRecursiveComparison()
      .ignoringFields("importTimestamp")
      .isEqualTo(verkuendung);
    assertThat(verkuendungFromDatabase.getImportTimestamp())
      .isNotNull()
      .isCloseTo(Instant.now(), within(1, ChronoUnit.MINUTES));
  }
}
