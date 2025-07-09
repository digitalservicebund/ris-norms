package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Application service for all operations related to the {@link Zeitgrenze}*/
@Service
@Slf4j
public class ZeitgrenzeService implements LoadZeitgrenzenUseCase, UpdateZeitgrenzenUseCase {

  private final LoadNormPort loadNormPort;
  private final NormService normService;

  public ZeitgrenzeService(LoadNormPort loadNormPort, NormService normService) {
    this.loadNormPort = loadNormPort;
    this.normService = normService;
  }

  @Override
  public List<Zeitgrenze> loadZeitgrenzen(LoadZeitgrenzenUseCase.Options options) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Options(options.eli()))
      .orElseThrow(() -> new NormNotFoundException(options.eli()))
      .getRegelungstext1()
      .getZeitgrenzen();
  }

  @Override
  public List<Zeitgrenze> updateZeitgrenzen(UpdateZeitgrenzenUseCase.Options options) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Options(options.eli()))
      .orElseThrow(() -> new NormNotFoundException(options.eli()));

    var geltungszeiten = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateGeltungszeiten();

    var zeitgrenzenUpdatesToProcess = options
      .zeitgrenzen()
      .stream()
      .sorted(Comparator.comparing(ZeitgrenzenUpdateData::date))
      .collect(Collectors.toList());

    geltungszeiten.forEach(zeitgrenze -> {
      var matchingZeitgrenzenUpdate = zeitgrenzenUpdatesToProcess
        .stream()
        .filter(
          zeitgrenzenUpdateData ->
            zeitgrenzenUpdateData.art() == zeitgrenze.getArt() &&
            zeitgrenzenUpdateData.date().isEqual(zeitgrenze.getDate())
        )
        .findAny();

      if (matchingZeitgrenzenUpdate.isEmpty()) {
        if (zeitgrenze.isInUse()) {
          throw new ZeitgrenzeCanNotBeDeletedAsItIsUsedException(zeitgrenze);
        }
        geltungszeiten.remove(zeitgrenze);
      } else {
        zeitgrenzenUpdatesToProcess.remove(matchingZeitgrenzenUpdate.get());
      }
    });

    zeitgrenzenUpdatesToProcess.forEach(zeitgrenzenUpdate ->
      geltungszeiten.add(zeitgrenzenUpdate.art(), zeitgrenzenUpdate.date())
    );

    if (geltungszeiten.isEmpty()) {
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .flatMap(Proprietary::getCustomModsMetadata)
        .ifPresent(CustomModsMetadata::removeGeltungszeiten);
    }

    normService.updateNorm(norm);

    return geltungszeiten.stream().toList();
  }
}
