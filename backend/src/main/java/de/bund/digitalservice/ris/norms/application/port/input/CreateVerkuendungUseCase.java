package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/** UseCase for creating a new {@link Verkuendung}. */
public interface CreateVerkuendungUseCase {
  /**
   * Creates a new {@link Verkuendung} based on the provided norm xml file.
   *
   * @param query The query containing the norm for the new {@link Verkuendung}.
   * @return The newly created {@link Verkuendung}.
   */
  Verkuendung createVerkuendung(Query query) throws IOException;

  /**
   * A record representing the query for creating an {@link Verkuendung}.
   *
   * @param file An XML file that contains the norm for the new {@link Verkuendung}
   * @param force in case a norm already exists, if set to true, the amending norm will be overwritten. Default: false
   */
  record Query(MultipartFile file, boolean force) {}
}
