package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadExpressionsOfNormWorkPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormWorksPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link Norm} / {@link NormManifestationDto}
 */
@Service
public class NormDBService
  extends DBService
  implements
    LoadNormPort,
    LoadNormByGuidPort,
    UpdateNormPort,
    UpdateOrSaveNormPort,
    UpdateNormPublishStatePort,
    DeleteNormPort,
    LoadNormManifestationElisByPublishStatePort,
    LoadNormExpressionElisPort,
    LoadNormWorksPort,
    LoadExpressionsOfNormWorkPort {

  private final DokumentRepository dokumentRepository;
  private final NormManifestationRepository normManifestationRepository;
  private final BinaryFileRepository binaryFileRepository;

  public NormDBService(
    DokumentRepository dokumentRepository,
    NormManifestationRepository normManifestationRepository,
    BinaryFileRepository binaryFileRepository
  ) {
    this.dokumentRepository = dokumentRepository;
    this.normManifestationRepository = normManifestationRepository;
    this.binaryFileRepository = binaryFileRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Options options) {
    return switch (options.eli()) {
      case NormExpressionEli expressionEli -> normManifestationRepository
        .findFirstByExpressionEliOrderByManifestationEliDesc(expressionEli.toString())
        .map(NormManifestationMapper::mapToDomain);
      case NormManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the norm based on the expression eli as the point in time
          // manifestation is the only additional identifying part of the eli
          yield this.loadNorm(new LoadNormPort.Options(manifestationEli.asExpressionEli()));
        }

        yield normManifestationRepository
          .findByManifestationEli(manifestationEli.toString())
          .map(NormManifestationMapper::mapToDomain);
      }
      case NormWorkEli workEli -> normManifestationRepository
        .findFirstByWorkEliOrderByManifestationEliDesc(workEli.toString())
        .map(NormManifestationMapper::mapToDomain);
    };
  }

  @Override
  public Optional<Norm> loadNormByGuid(LoadNormByGuidPort.Options options) {
    return normManifestationRepository
      .findFirstByExpressionAktuelleVersionIdOrderByManifestationEli(options.guid())
      .map(NormManifestationMapper::mapToDomain);
  }

  @Override
  public Optional<Norm> updateNorm(UpdateNormPort.Options options) {
    Optional<NormManifestationDto> normManifestationDto =
      normManifestationRepository.findByManifestationEli(
        options.norm().getManifestationEli().toString()
      );

    if (normManifestationDto.isEmpty()) {
      return Optional.empty();
    }

    var dokumentDtos = updateDokumente(
      options.norm().getManifestationEli(),
      options.norm().getDokumente()
    );
    var binaryFileDtos = updateBinaryFiles(
      options.norm().getManifestationEli(),
      options.norm().getBinaryFiles()
    );

    normManifestationDto.get().setDokumente(dokumentDtos);
    normManifestationDto.get().setBinaryFiles(binaryFileDtos);
    normManifestationDto.get().setPublishState(options.norm().getPublishState());

    return Optional.of(
      NormManifestationMapper.mapToDomain(
        normManifestationRepository.save(normManifestationDto.get())
      )
    );
  }

  /**
   * Update the saved {@link Dokument}e for the norm manifestation in the {@link DokumentRepository}. It removes additional existing Dokumente that are not provided.
   */
  private List<DokumentDto> updateDokumente(
    NormManifestationEli normManifestationEli,
    Collection<Dokument> dokumente
  ) {
    Map<DokumentManifestationEli, DokumentDto> existingDokumentDtos = dokumentRepository
      .findAllByEliNormManifestation(normManifestationEli.toString())
      .stream()
      .collect(
        Collectors.toMap(
          dto -> DokumentManifestationEli.fromString(dto.getEliDokumentManifestation()),
          dto -> dto
        )
      );
    List<DokumentDto> updatedDokumentDtos = dokumente
      .stream()
      .map(dokument ->
        Optional.ofNullable(existingDokumentDtos.remove(dokument.getManifestationEli()))
          .map(dokumentDto -> {
            dokumentDto.setXml(XmlMapper.toString(dokument.getDocument()));
            return dokumentDto;
          })
          .orElseGet(() -> DokumentMapper.mapToDto(dokument))
      )
      .toList();

    dokumentRepository.deleteAll(existingDokumentDtos.values());
    return dokumentRepository.saveAll(updatedDokumentDtos);
  }

  /**
   * Update the saved {@link BinaryFile}s for the norm manifestation in the {@link BinaryFileRepository}. It removes additional existing BinaryFiles that are not provided.
   */
  private List<BinaryFileDto> updateBinaryFiles(
    NormManifestationEli normManifestationEli,
    Collection<BinaryFile> binaryFiles
  ) {
    Map<DokumentManifestationEli, BinaryFileDto> existingBinaryFileDtos = binaryFileRepository
      .findAllByEliNormManifestation(normManifestationEli.toString())
      .stream()
      .collect(
        Collectors.toMap(
          dto -> DokumentManifestationEli.fromString(dto.getEliDokumentManifestation()),
          dto -> dto
        )
      );
    List<BinaryFileDto> updatedBinaryFileDtos = binaryFiles
      .stream()
      .map(binaryFile ->
        Optional.ofNullable(existingBinaryFileDtos.remove(binaryFile.getDokumentManifestationEli()))
          .map(binaryFileDto -> {
            binaryFileDto.setContent(binaryFile.getContent());
            return binaryFileDto;
          })
          .orElseGet(() -> BinaryFileMapper.mapToDto(binaryFile))
      )
      .toList();

    binaryFileRepository.deleteAll(existingBinaryFileDtos.values());
    return binaryFileRepository.saveAll(updatedBinaryFileDtos);
  }

  @Override
  public Norm updateOrSave(UpdateOrSaveNormPort.Options options) {
    final Optional<Norm> updatedNorm = updateNorm(new UpdateNormPort.Options(options.norm()));
    if (updatedNorm.isEmpty()) {
      dokumentRepository.saveAllAndFlush(
        options
          .norm()
          .getDokumente()
          .stream()
          .map(DokumentMapper::mapToDto)
          .collect(Collectors.toSet())
      );

      binaryFileRepository.saveAllAndFlush(
        options
          .norm()
          .getBinaryFiles()
          .stream()
          .map(BinaryFileMapper::mapToDto)
          .collect(Collectors.toSet())
      );

      NormManifestationDto normManifestationDto = normManifestationRepository
        .findByManifestationEli(options.norm().getManifestationEli().toString())
        .orElseThrow();

      normManifestationDto.setPublishState(options.norm().getPublishState());

      return NormManifestationMapper.mapToDomain(
        normManifestationRepository.save(normManifestationDto)
      );
    } else {
      return updatedNorm.get();
    }
  }

  @Override
  public void updateNormPublishState(UpdateNormPublishStatePort.Options options) {
    normManifestationRepository.updatePublishStateByManifestationEli(
      options.normManifestationEli().toString(),
      options.publishState()
    );
  }

  @Override
  public boolean deleteNorm(DeleteNormPort.Options options) {
    return switch (options.eli()) {
      case NormExpressionEli expressionEli -> {
        var manifestations = normManifestationRepository.findAllByExpressionEli(
          expressionEli.toString()
        );
        boolean hasConflictingState = manifestations
          .stream()
          .anyMatch(f -> !f.getPublishState().equals(options.publishState()));
        if (hasConflictingState) {
          yield false;
        }
        for (var normDto : manifestations) {
          dokumentRepository.deleteAll(normDto.getDokumente());
          binaryFileRepository.deleteAll(normDto.getBinaryFiles());
          normManifestationRepository.delete(normDto);
        }
        yield true;
      }
      case NormManifestationEli manifestationEli -> {
        var normDto = normManifestationRepository.findByManifestationEli(
          manifestationEli.toString()
        );
        if (normDto.isEmpty()) {
          yield true;
        }
        if (!normDto.get().getPublishState().equals(options.publishState())) {
          yield false;
        }
        dokumentRepository.deleteAll(normDto.get().getDokumente());
        binaryFileRepository.deleteAll(normDto.get().getBinaryFiles());
        normManifestationRepository.delete(normDto.get());
        yield true;
      }
      case NormWorkEli workEli -> throw new UnsupportedOperationException(
        "Deleting by work ELI (%s) is not supported.".formatted(workEli)
      );
    };
  }

  @Override
  public List<NormManifestationEli> loadNormManifestationElisByPublishState(
    LoadNormManifestationElisByPublishStatePort.Options options
  ) {
    return normManifestationRepository
      .findManifestationElisByPublishState(options.publishState())
      .stream()
      .map(NormManifestationEli::fromString)
      .toList();
  }

  @Override
  public List<NormExpressionEli> loadNormExpressionElis(
    LoadNormExpressionElisPort.Options options
  ) {
    return normManifestationRepository
      .findExpressionElisByWorkEli(options.eli().toString())
      .stream()
      .map(NormExpressionEli::fromString)
      .toList();
  }

  @Override
  public Page<LoadNormWorksPort.Result> loadNormWorks(LoadNormWorksPort.Options options) {
    return normManifestationRepository
      .findDistinctOnWorkEliByOrderByWorkEliAsc(options.pageable())
      .map(result ->
        new LoadNormWorksPort.Result(
          NormWorkEli.fromString(result.getEliNormWork()),
          result.getTitle()
        )
      );
  }

  @Override
  public List<LoadExpressionsOfNormWorkPort.Result> loadExpressionsOfNormWork(
    LoadExpressionsOfNormWorkPort.Options options
  ) {
    return dokumentRepository
      .findExpressionsOfWork(options.eli().toString())
      .stream()
      .map(dokumentDto ->
        new LoadExpressionsOfNormWorkPort.Result(
          NormExpressionEli.fromString(dokumentDto.getEliNormExpression()),
          dokumentDto.getGegenstandlos()
        )
      )
      .toList();
  }
}
