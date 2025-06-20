package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.exception.VerkuendungWithoutNormException;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.VerkuendungResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ZielnormenPreviewResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormenPreviewResponseSchema;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.Zielnorm;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
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
  private final LoadNormUseCase loadNormUseCase;
  private final LoadVerkuendungUseCase loadVerkuendungUseCase;
  private final LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase;
  private final LoadZielnormenExpressionsUseCase loadZielnormenExpressionsUseCase;
  private final CreateZielnormenExpressionsUseCase createZielnormenExpressionsUseCase;
  private final ProcessNormendokumentationspaketUseCase processNormendokumentationspaketUseCase;

  public VerkuendungenController(
    LoadAllVerkuendungenUseCase loadAllVerkuendungenUseCase,
    LoadNormUseCase loadNormUseCase,
    LoadVerkuendungUseCase loadVerkuendungUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase,
    LoadZielnormenExpressionsUseCase loadZielnormenExpressionsUseCase,
    CreateZielnormenExpressionsUseCase createZielnormenExpressionsUseCase,
    ProcessNormendokumentationspaketUseCase processNormendokumentationspaketUseCase
  ) {
    this.loadAllVerkuendungenUseCase = loadAllVerkuendungenUseCase;
    this.loadNormUseCase = loadNormUseCase;
    this.loadVerkuendungUseCase = loadVerkuendungUseCase;
    this.loadNormExpressionsAffectedByVerkuendungUseCase =
      loadNormExpressionsAffectedByVerkuendungUseCase;
    this.loadZielnormenExpressionsUseCase = loadZielnormenExpressionsUseCase;
    this.createZielnormenExpressionsUseCase = createZielnormenExpressionsUseCase;
    this.processNormendokumentationspaketUseCase = processNormendokumentationspaketUseCase;
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
        try {
          var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(verkuendung.getEli()));
          return VerkuendungResponseMapper.fromAnnouncedNorm(verkuendung, norm);
        } catch (NormNotFoundException e) {
          throw new VerkuendungWithoutNormException(verkuendung.getEli().toString());
        }
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
    var verkuendung = loadVerkuendungUseCase.loadVerkuendung(
      new LoadVerkuendungUseCase.Options(eli)
    );
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(verkuendung.getEli()));
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
        new LoadNormExpressionsAffectedByVerkuendungUseCase.Options(eli)
      );

    return ResponseEntity.ok(zielnormen.stream().map(NormResponseMapper::fromUseCaseData).toList());
  }

  /**
   * Get the list of zielnorm expressions that should be set to gegenstandslos or be created when applying all the currently existing {@link ZielnormReference}s of the Verkündung
   * @param eli the expression eli of the Verkündung
   * @return A {@link ResponseEntity} containing the list of zielnorm expressions
   *     <p>Returns HTTP 200 (OK) and the list of zielnorm expressions on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the Verkündung is not found.
   */
  @SuppressWarnings("java:S6856") // reliability issue because missing @PathVariable annotations. But we don't need it. Spring is automatically binding all path variables to our class NormExpressionEli
  @GetMapping(
    value = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/zielnormen/expressions/preview",
    produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<ZielnormenPreviewResponseSchema>> getZielnormenPreview(
    NormExpressionEli eli
  ) {
    return ResponseEntity.ok(
      loadZielnormenExpressionsUseCase
        .loadZielnormExpressions(new LoadZielnormenExpressionsUseCase.Options(eli))
        .stream()
        .map(ZielnormenPreviewResponseMapper::fromUseCaseData)
        .toList()
    );
  }

  /**
   * Create new expressions for one specific Zielnorm referenced in the Verkündung according to specified Zeitgrenzen.
   * <p>
   *
   * @param verkuendungEli the expression eli of the Verkündung
   * @param affectedAgent the agent of the work eli of the affected document
   * @param affectedYear the year of the work eli of the affected document
   * @param affectedNaturalId the natural identifier of the work eli of the affected document
   * @return A {@link ResponseEntity} containing the list of zielnorm expressions that were set to gegenstandslos or were created
   *     <p>Returns HTTP 200 (OK) and the list of zielnorm expressions on successful execution.
   *     <p>Returns HTTP 404 (Not Found) if the Verkündung is not found.
   */
  @SuppressWarnings("java:S6856") // reliability issue because missing @PathVariable annotations. But we don't need it. Spring is automatically binding all path variables to our class NormExpressionEli
  @PostMapping(
    value = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/zielnormen/eli/bund/{affectedAgent}/{affectedYear}/{affectedNaturalId}/expressions/create",
    produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ZielnormenPreviewResponseSchema> createZielnormenExpressions(
    NormExpressionEli verkuendungEli,
    @PathVariable("affectedAgent") String affectedAgent,
    @PathVariable("affectedYear") String affectedYear,
    @PathVariable("affectedNaturalId") String affectedNaturalId
  ) {
    final Zielnorm zielnorm = createZielnormenExpressionsUseCase.createZielnormExpressions(
      new CreateZielnormenExpressionsUseCase.Options(
        verkuendungEli,
        new NormWorkEli(affectedAgent, affectedYear, affectedNaturalId)
      )
    );
    return ResponseEntity.ok(ZielnormenPreviewResponseMapper.fromUseCaseData(zielnorm));
  }

  /**
   * Creates a new {@link Verkuendung} using the provided Norm XML.
   *
   * @param file a file containing a zip file of a Normendokumentationspaket
   * @param force in case a norm already exists, if set to true, the norm will be overwritten
   * @return information about the newly created verkuendung
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<VerkuendungResponseSchema> postVerkuendung(
    @RequestParam final MultipartFile file,
    @RequestParam(defaultValue = "false") final Boolean force
  ) throws IOException {
    var verkuendung = processNormendokumentationspaketUseCase.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.DirectProcessingOptions(file.getBytes(), force)
    );

    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(verkuendung.getEli()));
    return ResponseEntity.ok(VerkuendungResponseMapper.fromAnnouncedNorm(verkuendung, norm));
  }
}
