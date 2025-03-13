package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.VerkuendungResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;
  private final CreateAnnouncementUseCase createAnnouncementUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public AnnouncementController(
    LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase,
    CreateAnnouncementUseCase createAnnouncementUseCase,
    LoadNormUseCase loadNormUseCase
  ) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
    this.createAnnouncementUseCase = createAnnouncementUseCase;
    this.loadNormUseCase = loadNormUseCase;
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
  public ResponseEntity<List<VerkuendungResponseSchema>> getAllAnnouncements() {
    var responseSchemas = loadAllAnnouncementsUseCase
      .loadAllAnnouncements()
      .stream()
      .map(Announcement::getEli)
      .map(LoadNormUseCase.Query::new)
      .map(loadNormUseCase::loadNorm)
      .map(VerkuendungResponseMapper::fromUseCaseData)
      .toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Creates a new {@link Announcement} using the provided Norm XML.
   *
   * @param file a file containing an amending norm as an XML file that contains LDML.de
   * @param force in case a norm already exists, if set to true, the norm will be overwritten
   * @return information about the newly created announcement
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<VerkuendungResponseSchema> postAnnouncement(
    @RequestParam final MultipartFile file,
    @RequestParam(defaultValue = "false") final Boolean force
  ) throws IOException {
    var announcement = createAnnouncementUseCase.createAnnouncement(
      new CreateAnnouncementUseCase.Query(file, force)
    );
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(announcement.getEli()));
    return ResponseEntity.ok(VerkuendungResponseMapper.fromUseCaseData(norm));
  }
}
