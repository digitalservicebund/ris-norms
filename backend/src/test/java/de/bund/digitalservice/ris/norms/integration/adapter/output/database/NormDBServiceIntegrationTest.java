package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.NormDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class NormDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private NormDBService normDBService;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class loadNorm {

    @Test
    void itFindsNormOnDB() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .isPresent()
        .satisfies(normDb ->
          assertThat(normDb)
            .contains(
              Fixtures.loadNormFromDisk(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
              )
            )
        );
    }

    @Test
    void itFindsAllDokumenteOfNormOnDB() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-2.xml"
      );
      var offenestruktur = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/offenestruktur-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext1));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext2));
      dokumentRepository.save(DokumentMapper.mapToDto(offenestruktur));

      var binaryFile = Fixtures.loadBinaryFileFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
        )
      );
      binaryFileRepository.save(BinaryFileMapper.mapToDto(binaryFile));

      var norm = new Norm(
        NormPublishState.UNPUBLISHED,
        Set.of(regelungstext1, regelungstext2, offenestruktur),
        Set.of(binaryFile)
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional).isPresent();
      assertThat(normOptional.get().getRegelungstexte()).hasSize(2);
      assertThat(normOptional.get().getDokumente()).hasSize(3);
      assertThat(normOptional.get().getBinaryFiles()).hasSize(1);
      assertThat(normOptional).contains(norm);
    }

    @Test
    void itFindsNormByManifestationEliWithoutPointInTimeManifestationOnDB() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        );
    }

    @Test
    void itFindsNormByManifestationEli() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        );
    }

    @Test
    void itFindsNewestManifestationOfNorm() {
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
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15/regelungstext-1.xml"
          )
        );
    }

    @Test
    void itFindsNormByWorkEli() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Command(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        );
    }
  }

  @Test
  void itFindsNormByGuidOnDB() {
    // When
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );

    // When
    final Optional<Norm> normOptional = normDBService.loadNormByGuid(
      new LoadNormByGuidPort.Command(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"))
    );

    // Then
    assertThat(normOptional)
      .isPresent()
      .contains(
        Fixtures.loadNormFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      );
  }

  @Nested
  class updateNorm {

    @Test
    void itUpdatesNorm() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
          )
        )
      );

      var newNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = normDBService.updateNorm(new UpdateNormPort.Command(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(1);
      assertThat(normFromDatabase).contains(newNorm);
    }

    @Test
    void itUpdatesNormWithMultipleRegelungstexte() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-2.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext1));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext2));

      var newNorm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, regelungstext2)
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = normDBService.updateNorm(new UpdateNormPort.Command(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(normFromDatabase).contains(newNorm);
    }
  }

  @Nested
  class loadNormsByPublishState {

    @Test
    void itLoadsNormIdByPublishState() {
      // Given

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normManifestationRepository.save(normDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-1.xml"
          )
        )
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16")
        .orElseThrow();
      normDto2.setPublishState(NormPublishState.PUBLISHED);
      normManifestationRepository.save(normDto);

      // When
      final List<NormManifestationEli> publishedNorms =
        normDBService.loadNormManifestationElisByPublishState(
          new LoadNormManifestationElisByPublishStatePort.Command(
            NormPublishState.QUEUED_FOR_PUBLISH
          )
        );

      // Then
      assertThat(publishedNorms).isNotEmpty().hasSize(1);
    }
  }

  @Nested
  class loadPublishedNormExpressionElis {

    @Test
    void itLoadsPublishedNormExpressionElis() {
      // Given

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.PUBLISHED);
      normManifestationRepository.save(normDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-1.xml"
          )
        )
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16")
        .orElseThrow();
      normDto2.setPublishState(NormPublishState.PUBLISHED);
      normManifestationRepository.save(normDto);

      // When
      final List<NormExpressionEli> publishedNorms = normDBService.loadNormExpressionElis(
        new LoadNormExpressionElisPort.Command(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );

      // Then
      assertThat(publishedNorms)
        .hasSize(1)
        .containsExactly(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        );
    }
  }
}
