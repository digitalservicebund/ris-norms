package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Application service for all operations related to the {@link Zeitgrenze}*/
@Service
@Slf4j
public class ZeitgrenzeService implements LoadZeitgrenzenUseCase, UpdateZeitgrenzenUseCase {

  private final LoadRegelungstextPort loadRegelungstextPort;

  public ZeitgrenzeService(LoadRegelungstextPort loadRegelungstextPort) {
    this.loadRegelungstextPort = loadRegelungstextPort;
  }

  @Override
  public List<Zeitgrenze> loadZeitgrenzenFromDokument(LoadZeitgrenzenUseCase.Query query) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()))
      .getZeitgrenzen();
  }

  @Override
  public List<Zeitgrenze> updateZeitgrenzenOfDokument(UpdateZeitgrenzenUseCase.Query query) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()))
      .setZeitgrenzen(query.zeitgrenzen());
  }
}
