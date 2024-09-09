package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ReleaseResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;
  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase;
  private final LoadTargetNormsAffectedByAnnouncementUseCase
      loadTargetNormsAffectedByAnnouncementUseCase;
  private final ReleaseAnnouncementUseCase releaseAnnouncementUseCase;
  private final CreateAnnouncementUseCase createAnnouncementUseCase;

  public AnnouncementController(
      LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase,
      LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase,
      LoadTargetNormsAffectedByAnnouncementUseCase loadTargetNormsAffectedByAnnouncementUseCase,
      ReleaseAnnouncementUseCase releaseAnnouncementUseCase,
      CreateAnnouncementUseCase createAnnouncementUseCase) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
    this.loadAnnouncementByNormEliUseCase = loadAnnouncementByNormEliUseCase;
    this.loadTargetNormsAffectedByAnnouncementUseCase =
        loadTargetNormsAffectedByAnnouncementUseCase;
    this.releaseAnnouncementUseCase = releaseAnnouncementUseCase;
    this.createAnnouncementUseCase = createAnnouncementUseCase;
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

  /**
   * Retrieves a release of an {@link Announcement} based on its {@link Norm}'s expression ELI. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved release.
   *     <p>Returns HTTP 200 (OK) and the release if found.
   *     <p>Returns HTTP 404 (Not Found) if no release is found.
   */
  @GetMapping(
      path =
          "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseResponseSchema> getRelease(final Eli eli) {
    var announcement =
        loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(
            new LoadAnnouncementByNormEliUseCase.Query(eli.getValue()));
    var affectedNorms =
        loadTargetNormsAffectedByAnnouncementUseCase.loadTargetNormsAffectedByAnnouncement(
            new LoadTargetNormsAffectedByAnnouncementUseCase.Query(eli.getValue()));

    return ResponseEntity.ok(ReleaseResponseMapper.fromAnnouncement(announcement, affectedNorms));
  }

  /**
   * Releases an {@link Announcement} (and all related {@link Norm}'s) based on its {@link Norm}'s
   * expression ELI. The ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Announcement} is found.
   */
  @PutMapping(
      path =
          "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseResponseSchema> putRelease(final Eli eli) {
    var announcement =
        releaseAnnouncementUseCase.releaseAnnouncement(
            new ReleaseAnnouncementUseCase.Query(eli.getValue()));

    var affectedNorms =
        loadTargetNormsAffectedByAnnouncementUseCase.loadTargetNormsAffectedByAnnouncement(
            new LoadTargetNormsAffectedByAnnouncementUseCase.Query(eli.getValue()));

    return ResponseEntity.ok(ReleaseResponseMapper.fromAnnouncement(announcement, affectedNorms));
  }

  /**
   * Creates a new {@link Announcement} using the norm-file provided.
   *
   * @param file a file containing an amending norm as an XML file that contains LDML.de
   * @return information about the newly created announcement
   */
  @PostMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<NormResponseSchema> postAnnouncement(@RequestParam final MultipartFile file)
      throws IOException {
    var announcement =
        createAnnouncementUseCase.createAnnouncement(new CreateAnnouncementUseCase.Query(file));
    return ResponseEntity.ok(NormResponseMapper.fromUseCaseData(announcement.getNorm()));
  }
}
