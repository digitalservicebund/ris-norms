package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
@Slf4j
public class VerkuendungService
  implements
    LoadAllVerkuendungenUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase,
    LoadVerkuendungUseCase {

  private final LoadAllVerkuendungenPort loadAllVerkuendungenPort;
  private final LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort;
  private final LoadNormPort loadNormPort;

  public VerkuendungService(
    LoadAllVerkuendungenPort loadAllVerkuendungenPort,
    LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort,
    LoadNormPort loadNormPort
  ) {
    this.loadAllVerkuendungenPort = loadAllVerkuendungenPort;
    this.loadVerkuendungByNormEliPort = loadVerkuendungByNormEliPort;
    this.loadNormPort = loadNormPort;
  }

  @Override
  public List<Verkuendung> loadAllVerkuendungen() {
    return loadAllVerkuendungenPort.loadAllVerkuendungen();
  }

  @Override
  public Verkuendung loadVerkuendung(LoadVerkuendungUseCase.Options options) {
    return loadVerkuendungByNormEliPort
      .loadVerkuendungByNormEli(new LoadVerkuendungByNormEliPort.Options(options.eli()))
      .orElseThrow(() -> new VerkuendungNotFoundException(options.eli().toString()));
  }

  @Override
  public List<Norm> loadNormExpressionsAffectedByVerkuendung(
    LoadNormExpressionsAffectedByVerkuendungUseCase.Options options
  ) {
    var verkuendungNorm = loadNormPort
      .loadNorm(new LoadNormPort.Options(options.eli()))
      .orElseThrow(() -> new VerkuendungNotFoundException(options.eli().toString()));

    var affectedExpressionElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getAmendedNormExpressions);

    if (affectedExpressionElis.isEmpty()) {
      return List.of();
    }

    return affectedExpressionElis
      .get()
      .stream()
      .map(eli -> {
        var norm = loadNormPort.loadNorm(new LoadNormPort.Options(eli));
        if (norm.isEmpty()) {
          log.warn(
            "Norm with ELI {} could not be loaded when collecting expressions affected by Verkuendung with ELI {}",
            eli,
            options.eli()
          );
        }
        return norm;
      })
      .flatMap(Optional::stream)
      .toList();
  }
}
