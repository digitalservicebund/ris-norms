package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;

  public AnnouncementController(LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
  }

  /**
   * Retrieves all available {@link Announcement}s
   *
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link
   *     Announcement}s
   *     <p>Returns HTTP 200 (OK) and a list of {@link Announcement}s on successful execution.
   *     <p>If no announcement is found, the list is empty.
   */
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<NormResponseSchema>> getAllAnnouncements() {
    var responseSchemas =
        loadAllAnnouncementsUseCase.loadAllAnnouncements().stream()
            .map(Announcement::getNorm)
            .map(NormResponseMapper::fromUseCaseData)
            .toList();
    return ResponseEntity.ok(responseSchemas);
  }
}
