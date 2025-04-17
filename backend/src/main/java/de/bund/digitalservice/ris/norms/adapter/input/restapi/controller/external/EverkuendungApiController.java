package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.external;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.VerkuendungStatusMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungsProcessIdResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.io.IOException;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller for verkuendungen-related actions. */
@RestController
@RequestMapping("/api/v1/external/verkuendungen")
public class EverkuendungApiController {

  private final StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase;
  private final LoadNormendokumentationspacketProcessingStatusUseCase statusService;

  public EverkuendungApiController(
    StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase,
    LoadNormendokumentationspacketProcessingStatusUseCase loadNormendokumentationspacketProcessingStatusUseCase
  ) {
    this.storeNormendokumentationspaketUseCase = storeNormendokumentationspaketUseCase;
    this.statusService = loadNormendokumentationspacketProcessingStatusUseCase;
  }

  /**
   * Endpoint to upload the Normendokumentationspaket
   *
   * @param file containing the Normendokumentationspaket
   * @param signature verifying the Normendokumentationspaket
   * @return processId that can be used to get the status from the status endpoint
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<VerkuendungsProcessIdResponseSchema> postVerkuendung(
    @RequestParam final MultipartFile file,
    @RequestParam final MultipartFile signature
  ) throws IOException {
    return ResponseEntity
      .accepted()
      .body(
        new VerkuendungsProcessIdResponseSchema(
          storeNormendokumentationspaketUseCase
            .storeNormendokumentationspaket(
              new StoreNormendokumentationspaketUseCase.Query(
                file.getResource(),
                signature.getResource()
              )
            )
            .toString()
        )
      );
  }

  /**
   * Endpoint to retrieve the status of background processing a new Verkuendung
   * @param processId The uuid of the processing requests
   * @return The processing status of the Verkuendung
   */
  @GetMapping("/status/{processId}")
  public ResponseEntity<VerkuendungStatusResponseSchema> getStatus(@PathVariable UUID processId) {
    VerkuendungImportProcess status = statusService.getStatus(
      new LoadNormendokumentationspacketProcessingStatusUseCase.Query(processId)
    );
    return ResponseEntity.ok(VerkuendungStatusMapper.fromVerkuendungImportProcess(status));
  }
}
