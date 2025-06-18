package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.NormDBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
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
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .isPresent()
        .satisfies(normDb ->
          assertThat(normDb).contains(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        );
    }

    @Test
    void itFindsAllDokumenteOfNormOnDB() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      var offenestruktur = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
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
        new LoadNormPort.Options(
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
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Options(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional).contains(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );
    }

    @Test
    void itFindsNormByManifestationEli() {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Options(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );

      // Then
      assertThat(normOptional).contains(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );
    }

    @Test
    void itFindsNewestManifestationOfNorm() {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional).contains(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15")
      );
    }

    @Test
    void itFindsNormByWorkEli() {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.PUBLISHED
      );

      // When
      final Optional<Norm> normOptional = normDBService.loadNorm(
        new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );

      // Then
      assertThat(normOptional).contains(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );
    }
  }

  @Test
  void itFindsNormByGuidOnDB() {
    // When
    Fixtures.loadAndSaveNormFixture(
      dokumentRepository,
      binaryFileRepository,
      normManifestationRepository,
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
      NormPublishState.UNPUBLISHED
    );

    // When
    final Optional<Norm> normOptional = normDBService.loadNormByGuid(
      new LoadNormByGuidPort.Options(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"))
    );

    // Then
    assertThat(normOptional)
      .isPresent()
      .contains(Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"));
  }

  @Nested
  class checkNormExistence {

    @Nested
    class manifestationEli {

      @Test
      void itReturnsTrueIfExists() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(
            NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        );

        // Then
        assertThat(result).isTrue();
      }

      @Test
      void itReturnsFalseIfItDoesNotExist() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(
            NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-06")
          )
        );

        // Then
        assertThat(result).isFalse();
      }
    }

    @Nested
    class expressionEli {

      @Test
      void itReturnsTrueIfExists() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        );

        // Then
        assertThat(result).isTrue();
      }

      @Test
      void itReturnsFalseIfItDoesNotExist() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu")
          )
        );

        // Then
        assertThat(result).isFalse();
      }
    }

    @Nested
    class workEli {

      @Test
      void itReturnsTrueIfExists() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
        );

        // Then
        assertThat(result).isTrue();
      }

      @Test
      void itReturnsFalseIfItDoesNotExist() {
        // Given
        Fixtures.loadAndSaveNormFixture(
          dokumentRepository,
          binaryFileRepository,
          normManifestationRepository,
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
          NormPublishState.UNPUBLISHED
        );

        // When
        final var result = normDBService.checkNormExistence(
          new CheckNormExistencePort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s595"))
        );

        // Then
        assertThat(result).isFalse();
      }
    }
  }

  @Nested
  class updateNorm {

    @Test
    void itUpdatesNorm() {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );

      var newNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15"
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = normDBService.updateNorm(new UpdateNormPort.Options(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(normFromDatabase).contains(newNorm);
    }

    @Test
    void itUpdatesNormWhenDokumenteAreRemoved() {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      assertThat(dokumentRepository.findAll()).hasSize(4);
      assertThat(binaryFileRepository.findAll()).hasSize(1);

      var newNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      newNorm.setBinaryFiles(Set.of());
      newNorm.setDokumente(Set.of(newNorm.getRechtsetzungsdokument(), newNorm.getRegelungstext1()));

      // When
      var normFromDatabase = normDBService.updateNorm(new UpdateNormPort.Options(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(binaryFileRepository.findAll()).isEmpty();
      assertThat(normFromDatabase).contains(newNorm);
    }

    @Test
    void itUpdatesNormWithMultipleRegelungstexte() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext1));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext2));

      var newNorm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, regelungstext2)
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = normDBService.updateNorm(new UpdateNormPort.Options(newNorm));

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
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
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
            "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-verkuendung-1.xml"
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
          new LoadNormManifestationElisByPublishStatePort.Options(
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
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
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
            "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-verkuendung-1.xml"
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
        new LoadNormExpressionElisPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );

      // Then
      assertThat(publishedNorms)
        .hasSize(1)
        .containsExactly(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        );
    }
  }

  @Nested
  class deleteNorm {

    @Test
    void deleteNormByManifestationEli_shouldDeleteIfPublishStateMatches() {
      final Norm norm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      boolean result = normDBService.deleteNorm(
        new DeleteNormPort.Options(norm.getManifestationEli(), NormPublishState.UNPUBLISHED)
      );

      assertThat(result).isTrue();
      assertThat(
        normManifestationRepository.findByManifestationEli(norm.getManifestationEli().toString())
      ).isEmpty();
      norm
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isEmpty();
        });
      norm
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isEmpty();
        });
    }

    @Test
    void deleteNormByManifestationEli_shouldNotDeleteIfPublishStateNoMatch() {
      final Norm norm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.PUBLISHED
      );

      boolean result = normDBService.deleteNorm(
        new DeleteNormPort.Options(norm.getManifestationEli(), NormPublishState.UNPUBLISHED)
      );

      assertThat(result).isFalse();
      assertThat(
        normManifestationRepository.findByManifestationEli(norm.getManifestationEli().toString())
      ).isPresent();
      norm
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isPresent();
        });
      norm
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isPresent();
        });
    }

    @Test
    void deleteNormByManifestationEli_shouldReturnTrueIfNormDoesNotExist() {
      var options = new DeleteNormPort.Options(
        NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"),
        NormPublishState.UNPUBLISHED
      );
      boolean result = normDBService.deleteNorm(options);

      assertThat(result).isTrue();
    }

    @Test
    void deleteNormByExpressionEli_shouldReturnTrueIfNormDoesNotExist() {
      var options = new DeleteNormPort.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"),
        NormPublishState.UNPUBLISHED
      );
      boolean result = normDBService.deleteNorm(options);

      assertThat(result).isTrue();
    }

    @Test
    void deleteNormByWorkEli_shouldThrowUnsupportedOperationException() {
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      var options = new DeleteNormPort.Options(workEli, NormPublishState.UNPUBLISHED);

      assertThatThrownBy(() -> normDBService.deleteNorm(options))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("Deleting by work ELI (eli/bund/bgbl-1/1964/s593) is not supported.");
    }

    @Test
    void deleteNormByExpressionEli_shouldDeleteIfPublishStateMatches() {
      final Norm firstManifestation = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      final Norm secondManifestation = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );

      boolean result = normDBService.deleteNorm(
        new DeleteNormPort.Options(
          firstManifestation.getExpressionEli(),
          NormPublishState.UNPUBLISHED
        )
      );

      assertThat(result).isTrue();
      assertThat(
        normManifestationRepository.findByManifestationEli(
          firstManifestation.getManifestationEli().toString()
        )
      ).isEmpty();
      firstManifestation
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isEmpty();
        });
      firstManifestation
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isEmpty();
        });
      assertThat(
        normManifestationRepository.findByManifestationEli(
          secondManifestation.getManifestationEli().toString()
        )
      ).isEmpty();
      secondManifestation
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isEmpty();
        });
      secondManifestation
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isEmpty();
        });
    }

    @Test
    void deleteNormByExpressionEli_shouldNotDeleteIfPublishStateNoMatch() {
      final Norm firstManifestation = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      final Norm secondManifestation = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15",
        NormPublishState.PUBLISHED
      );

      boolean result = normDBService.deleteNorm(
        new DeleteNormPort.Options(
          firstManifestation.getExpressionEli(),
          NormPublishState.UNPUBLISHED
        )
      );

      assertThat(result).isFalse();
      assertThat(
        normManifestationRepository.findByManifestationEli(
          firstManifestation.getManifestationEli().toString()
        )
      ).isPresent();
      firstManifestation
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isPresent();
        });
      firstManifestation
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isPresent();
        });
      assertThat(
        normManifestationRepository.findByManifestationEli(
          secondManifestation.getManifestationEli().toString()
        )
      ).isPresent();
      secondManifestation
        .getDokumente()
        .forEach(dokument -> {
          assertThat(
            dokumentRepository.findByEliDokumentManifestation(
              dokument.getManifestationEli().toString()
            )
          ).isPresent();
        });
      secondManifestation
        .getBinaryFiles()
        .forEach(binaryFile -> {
          assertThat(
            binaryFileRepository.findByEliDokumentManifestation(
              binaryFile.getDokumentManifestationEli().toString()
            )
          ).isPresent();
        });
    }
  }
}
