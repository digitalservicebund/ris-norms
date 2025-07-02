package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface representing the use case for updating a list of {@link Zeitgrenze}.
 */
public interface UpdateZeitgrenzenUseCase {
  /**
   * Updates a list of time boundaries of a {@link Dokument} based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) of the {@link Dokument}.
   * @return the list of {@link Zeitgrenze}
   */
  List<Zeitgrenze> updateZeitgrenzenOfDokument(Options options);

  /**
   * A record representing the parameters needed to query time boundaries related to a {@link Dokument}.
   *
   * @param eli The ELI used to identify the {@link Dokument}
   * @param zeitgrenzen the list of the new {@link Zeitgrenze}n. Existing {@link Zeitgrenze}n that are not listed will be removed.
   */
  record Options(DokumentExpressionEli eli, List<ZeitgrenzenUpdateData> zeitgrenzen) {}

  /**
   * Data for updating a zeitgrenze
   *
   * @param art type of the zeitgrenze
   * @param date date of the zeitgrenze
   */
  record ZeitgrenzenUpdateData(LocalDate date, Zeitgrenze.Art art) {}

  /**
   * Exception when a zeitgrenze is supposed to be deleted but still in use.
   */
  class ZeitgrenzeCanNotBeDeletedAsItIsUsedException
    extends RuntimeException
    implements NormsAppException {

    final LocalDate date;
    final Zeitgrenze.Art art;

    public ZeitgrenzeCanNotBeDeletedAsItIsUsedException(Zeitgrenze zeitgrenze) {
      super(
        "Zeitgrenze (date: %s, art: %s) can not be deleted as it is used.".formatted(
          zeitgrenze.getDate(),
          zeitgrenze.getArt()
        )
      );
      this.date = zeitgrenze.getDate();
      this.art = zeitgrenze.getArt();
    }

    @Override
    public URI getType() {
      return URI.create("/errors/zeitgrenzen-can-not-be-deleted-as-it-is-used");
    }

    @Override
    public String getTitle() {
      return "Zeitgrenze can not be deleted";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("date", date, "art", art);
    }
  }
}
