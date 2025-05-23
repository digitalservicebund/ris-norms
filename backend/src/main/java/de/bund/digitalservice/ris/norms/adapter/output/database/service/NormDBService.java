package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    DeleteNormPort,
    LoadNormManifestationElisByPublishStatePort,
    LoadNormExpressionElisPort {

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

    var dokumentDtos = dokumentRepository.saveAll(
      options
        .norm()
        .getDokumente()
        .stream()
        .map(regelungstext ->
          dokumentRepository
            .findByEliDokumentManifestation(regelungstext.getManifestationEli().toString())
            .map(dokumentDto -> {
              dokumentDto.setXml(XmlMapper.toString(regelungstext.getDocument()));
              return dokumentDto;
            })
        )
        .flatMap(Optional::stream)
        .collect(Collectors.toSet())
    );

    var binaryFileDtos = binaryFileRepository.saveAll(
      options
        .norm()
        .getBinaryFiles()
        .stream()
        .map(binaryFile ->
          binaryFileRepository
            .findById(binaryFile.getDokumentManifestationEli().toString())
            .map(binaryFileDto -> {
              binaryFileDto.setContent(binaryFile.getContent());
              return binaryFileDto;
            })
        )
        .flatMap(Optional::stream)
        .collect(Collectors.toSet())
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
  public void deleteNorm(DeleteNormPort.Options options) {
    var normDto = normManifestationRepository.findByManifestationEli(options.eli().toString());

    if (normDto.isEmpty()) {
      return;
    }

    if (!normDto.get().getPublishState().equals(options.publishState())) {
      return;
    }

    dokumentRepository.deleteAll(normDto.get().getDokumente());
    normManifestationRepository.delete(normDto.get());
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
}
