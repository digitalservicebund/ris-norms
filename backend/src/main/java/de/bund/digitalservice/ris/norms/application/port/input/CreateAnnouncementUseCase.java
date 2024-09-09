package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/** UseCase for creating a new {@link Announcement}. */
public interface CreateAnnouncementUseCase {

  /**
   * Creates a new {@link Announcement} based on the provided norm xml file.
   *
   * @param query The query containing the norm for the new {@link Announcement}.
   * @return The newly created {@link Announcement}.
   */
  Announcement createAnnouncement(Query query) throws IOException;

  /**
   * A record representing the query for creating an {@link Announcement}.
   *
   * @param file An XML file that contains the norm for the new {@link Announcement}
   */
  record Query(MultipartFile file) {}
}
