package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ProprietaryResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Retrieve proprietary data of a {@link Norm}. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/proprietary")
public class ProprietaryController {

  private final LoadProprietaryFromNormUseCase loadProprietaryFromNormUseCase;

  public ProprietaryController(LoadProprietaryFromNormUseCase loadProprietaryFromNormUseCase) {
    this.loadProprietaryFromNormUseCase = loadProprietaryFromNormUseCase;
  }

  /**
   * Return proprietary data of {@link Norm}
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return {@link Proprietary} of the Norm identified by the ElI
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietaryResponseSchema> getProprietary(final Eli eli) {

    try {
      var proprietary =
          loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli.getValue()));

      return ResponseEntity.ok(ProprietaryResponseMapper.fromProprietary(proprietary));

    } catch (NormNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Return proprietary data of {@link Norm} at a given date
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param atDate Date at which to return the proprietary
   * @return {@link Proprietary} of the Norm identified by the ElI
   */
  @GetMapping(
      path = "/{atDate}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietaryResponseSchema> getProprietaryAtDate(
      final Eli eli, @PathVariable final LocalDate atDate) {

    try {
      var proprietary =
          loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli.getValue()));

      return ResponseEntity.ok(ProprietaryResponseMapper.fromProprietary(proprietary, atDate));

    } catch (NormNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
