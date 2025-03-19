package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.VerkuendungResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsAffectedByVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;
  private final CreateAnnouncementUseCase createAnnouncementUseCase;
  private final LoadNormUseCase loadNormUseCase;
  private final LoadAnnouncementUseCase loadAnnouncementUseCase;
  private final LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase;

  public AnnouncementController(
    LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase,
    CreateAnnouncementUseCase createAnnouncementUseCase,
    LoadNormUseCase loadNormUseCase,
    LoadAnnouncementUseCase loadAnnouncementUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase
  ) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
    this.createAnnouncementUseCase = createAnnouncementUseCase;
    this.loadNormUseCase = loadNormUseCase;
    this.loadAnnouncementUseCase = loadAnnouncementUseCase;
    this.loadNormExpressionsAffectedByVerkuendungUseCase =
    loadNormExpressionsAffectedByVerkuendungUseCase;
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
      .map(announcement -> {
        var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(announcement.getEli()));
        return VerkuendungResponseMapper.fromAnnouncedNorm(announcement, norm);
      })
      .toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves a single {@link Announcement} by its expression eli
   *
   * @param eli the expression eli of the announcement
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link
   *     Announcement}s
   *     <p>Returns HTTP 200 (OK) and the response schema of information from the {@link Announcement} on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the announcement is not found.
   */
  @SuppressWarnings("java:S6856") // reliability issue because missing @PathVariable annotations. But we don't need it. Spring is automatically binding all path variables to our class NormExpressionEli
  @GetMapping(
    value = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}",
    produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<VerkuendungResponseSchema> getAnnouncement(NormExpressionEli eli) {
    var announcement = loadAnnouncementUseCase.loadAnnouncement(
      new LoadAnnouncementUseCase.Query(eli)
    );
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(announcement.getEli()));
    return ResponseEntity.ok(VerkuendungResponseMapper.fromAnnouncedNorm(announcement, norm));
  }

  /**
   * Retrieves the list of zielnormen expressions of an {@link Announcement} by its expression eli
   *
   * @param eli the expression eli of the announcement
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link Norm}s
   *     <p>Returns HTTP 200 (OK) and the response schema of information from the {@link Norm}s on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the announcement is not found.
   */
  @SuppressWarnings("java:S6856") // reliability issue because missing @PathVariable annotations. But we don't need it. Spring is automatically binding all path variables to our class NormExpressionEli
  @GetMapping(
    value = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/zielnormen",
    produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<NormResponseSchema>> getVerkuendungsZielnormen(NormExpressionEli eli) {
    var zielnormen =
      loadNormExpressionsAffectedByVerkuendungUseCase.loadNormExpressionsAffectedByVerkuendung(
        new LoadNormExpressionsAffectedByVerkuendungUseCase.Query(eli)
      );

    return ResponseEntity.ok(zielnormen.stream().map(NormResponseMapper::fromUseCaseData).toList());
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
    return ResponseEntity.ok(VerkuendungResponseMapper.fromAnnouncedNorm(announcement, norm));
  }
}
