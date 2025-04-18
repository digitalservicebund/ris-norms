package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.VerkuendungResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.CreateVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllVerkuendungenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsAffectedByVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller for verkuendung-related actions. */
@RestController
@RequestMapping("/api/v1/verkuendungen")
public class VerkuendungenController {

  private final LoadAllVerkuendungenUseCase loadAllVerkuendungenUseCase;
  private final CreateVerkuendungUseCase createVerkuendungUseCase;
  private final LoadNormUseCase loadNormUseCase;
  private final LoadVerkuendungUseCase loadVerkuendungUseCase;
  private final LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase;

  public VerkuendungenController(
    LoadAllVerkuendungenUseCase loadAllVerkuendungenUseCase,
    CreateVerkuendungUseCase createVerkuendungUseCase,
    LoadNormUseCase loadNormUseCase,
    LoadVerkuendungUseCase loadVerkuendungUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase
  ) {
    this.loadAllVerkuendungenUseCase = loadAllVerkuendungenUseCase;
    this.createVerkuendungUseCase = createVerkuendungUseCase;
    this.loadNormUseCase = loadNormUseCase;
    this.loadVerkuendungUseCase = loadVerkuendungUseCase;
    this.loadNormExpressionsAffectedByVerkuendungUseCase =
    loadNormExpressionsAffectedByVerkuendungUseCase;
  }

  /**
   * Retrieves all available {@link Verkuendung}s
   *
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link
   *     Verkuendung}s
   *     <p>Returns HTTP 200 (OK) and a list of {@link Verkuendung}s on successful execution.
   *     <p>If no verkuendung is found, the list is empty.
   */
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<VerkuendungResponseSchema>> getAllVerkuendungen() {
    var responseSchemas = loadAllVerkuendungenUseCase
      .loadAllVerkuendungen()
      .stream()
      .map(verkuendung -> {
        var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(verkuendung.getEli()));
        return VerkuendungResponseMapper.fromAnnouncedNorm(verkuendung, norm);
      })
      .toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves a single {@link Verkuendung} by its expression eli
   *
   * @param eli the expression eli of the verkuendung
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link
   *     Verkuendung}s
   *     <p>Returns HTTP 200 (OK) and the response schema of information from the {@link Verkuendung} on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the verkuendung is not found.
   */
  @SuppressWarnings("java:S6856") // reliability issue because missing @PathVariable annotations. But we don't need it. Spring is automatically binding all path variables to our class NormExpressionEli
  @GetMapping(
    value = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}",
    produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<VerkuendungResponseSchema> getVerkuendung(NormExpressionEli eli) {
    var verkuendung = loadVerkuendungUseCase.loadVerkuendung(new LoadVerkuendungUseCase.Query(eli));
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(verkuendung.getEli()));
    return ResponseEntity.ok(VerkuendungResponseMapper.fromAnnouncedNorm(verkuendung, norm));
  }

  /**
   * Retrieves the list of zielnormen expressions of an {@link Verkuendung} by its expression eli
   *
   * @param eli the expression eli of the verkuendung
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link Norm}s
   *     <p>Returns HTTP 200 (OK) and the response schema of information from the {@link Norm}s on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the verkuendung is not found.
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
   * Creates a new {@link Verkuendung} using the provided Norm XML.
   *
   * @param file a file containing an amending norm as an XML file that contains LDML.de
   * @param force in case a norm already exists, if set to true, the norm will be overwritten
   * @return information about the newly created verkuendung
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<VerkuendungResponseSchema> postVerkuendung(
    @RequestParam final MultipartFile file,
    @RequestParam(defaultValue = "false") final Boolean force
  ) throws IOException {
    var verkuendung = createVerkuendungUseCase.createVerkuendung(
      new CreateVerkuendungUseCase.Query(file, force)
    );
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(verkuendung.getEli()));
    return ResponseEntity.ok(VerkuendungResponseMapper.fromAnnouncedNorm(verkuendung, norm));
  }
}
