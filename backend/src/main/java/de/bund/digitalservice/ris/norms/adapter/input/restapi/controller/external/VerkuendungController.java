package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.external;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungsProcessIdResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller for verkuendungen-related actions. */
@RestController
@RequestMapping("/api/v1/external/verkuendungen")
public class VerkuendungController {

  private final StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase;

  public VerkuendungController(
    StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase
  ) {
    this.storeNormendokumentationspaketUseCase = storeNormendokumentationspaketUseCase;
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
  ) {
    return ResponseEntity
      .accepted()
      .body(
        new VerkuendungsProcessIdResponseSchema(
          storeNormendokumentationspaketUseCase.storeNormendokumentationspaket(
            new StoreNormendokumentationspaketUseCase.Query(file, signature)
          )
        )
      );
  }
}
