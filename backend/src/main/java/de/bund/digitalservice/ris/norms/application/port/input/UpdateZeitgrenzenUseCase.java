package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
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
   * Updates a list of time boundaries of a {@link Norm} based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) of the {@link Norm}.
   * @return the list of {@link Zeitgrenze}
   */
  List<Zeitgrenze> updateZeitgrenzen(Options options);

  /**
   * A record representing the parameters needed to update time boundaries.
   *
   * @param eli The ELI used to identify the {@link Norm}
   * @param zeitgrenzen the list of the new {@link Zeitgrenze}n. Existing {@link Zeitgrenze}n that are not listed will be removed.
   */
  record Options(NormExpressionEli eli, List<ZeitgrenzenUpdateData> zeitgrenzen) {}

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
