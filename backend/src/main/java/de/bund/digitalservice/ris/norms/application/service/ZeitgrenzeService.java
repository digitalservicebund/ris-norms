package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
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

  private final LoadRegelungstextPort loadRegelungstextPort;
  private final UpdateDokumentPort updateDokumentPort;

  public ZeitgrenzeService(
    LoadRegelungstextPort loadRegelungstextPort,
    UpdateDokumentPort updateDokumentPort
  ) {
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.updateDokumentPort = updateDokumentPort;
  }

  @Override
  public List<Zeitgrenze> loadZeitgrenzenFromDokument(LoadZeitgrenzenUseCase.Options options) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()))
      .getZeitgrenzen();
  }

  @Override
  public List<Zeitgrenze> updateZeitgrenzenOfDokument(UpdateZeitgrenzenUseCase.Options options) {
    final Regelungstext regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));

    var geltungszeiten = regelungstext
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

    updateDokumentPort.updateDokument(new UpdateDokumentPort.Options(regelungstext));
    return geltungszeiten.stream().toList();
  }
}
