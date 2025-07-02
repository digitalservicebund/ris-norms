package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link Dokument} / {@link DokumentDto}
 */
@Service
public class DokumentDBService
  extends DBService
  implements LoadDokumentPort, UpdateDokumentPort, LoadRegelungstextPort {

  private final DokumentRepository dokumentRepository;

  public DokumentDBService(DokumentRepository dokumentRepository) {
    this.dokumentRepository = dokumentRepository;
  }

  @Override
  public Optional<Dokument> loadDokument(LoadDokumentPort.Options options) {
    return switch (options.eli()) {
      case DokumentExpressionEli expressionEli -> dokumentRepository
        .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          expressionEli.toString()
        )
        .map(DokumentMapper::mapToDomain);
      case DokumentManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the regelungstext based on the expression eli as the point in
          // time manifestation is the only additional identifying part of the eli
          yield this.loadDokument(new LoadDokumentPort.Options(manifestationEli.asExpressionEli()));
        }
        yield dokumentRepository
          .findByEliDokumentManifestation(manifestationEli.toString())
          .map(DokumentMapper::mapToDomain);
      }
      case DokumentWorkEli ignored -> throw new IllegalArgumentException(
        "It's currently not possible to load a regelungstext by it's work eli."
      );
    };
  }

  @Override
  public Optional<Dokument> updateDokument(UpdateDokumentPort.Options options) {
    Optional<DokumentDto> optionalDokumentDto =
      dokumentRepository.findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
        options.dokument().getExpressionEli().toString()
      );
    if (optionalDokumentDto.isEmpty()) {
      return Optional.of(
        DokumentMapper.mapToDomain(
          dokumentRepository.saveAndFlush(DokumentMapper.mapToDto(options.dokument()))
        )
      );
    } else {
      final DokumentDto dokumentDto = optionalDokumentDto.get();
      dokumentDto.setXml(XmlMapper.toString(options.dokument().getDocument()));
      return Optional.of(DokumentMapper.mapToDomain(dokumentRepository.saveAndFlush(dokumentDto)));
    }
  }

  @Override
  public Optional<Regelungstext> loadRegelungstext(LoadRegelungstextPort.Options options) {
    return this.loadDokument(new LoadDokumentPort.Options(options.eli())).map(
      Regelungstext.class::cast
    );
  }
}
