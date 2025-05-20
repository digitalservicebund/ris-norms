package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TableOfContentsResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TableOfContentsResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTocFromRegelungstextUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Retrieve the table of contents of a {@link Regelungstext}. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/toc"
)
public class TableOfContentsController {

  private final LoadTocFromRegelungstextUseCase loadTocFromRegelungstextUseCase;

  public TableOfContentsController(
    LoadTocFromRegelungstextUseCase loadTocFromRegelungstextUseCase
  ) {
    this.loadTocFromRegelungstextUseCase = loadTocFromRegelungstextUseCase;
  }

  /**
   * Return the table of contents of a {@link Regelungstext}.
   *
   * @param dokumentExpressionEli the eli at the document level
   * @return the specific metadata returned in the form of {@link ProprietaryFrameSchema}
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<TableOfContentsResponseSchema>> getToc(
    final DokumentExpressionEli dokumentExpressionEli
  ) {
    var toc = loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(
      new LoadTocFromRegelungstextUseCase.Options(dokumentExpressionEli)
    );
    return ResponseEntity.ok(TableOfContentsResponseMapper.fromTableOfContents(toc));
  }
}
