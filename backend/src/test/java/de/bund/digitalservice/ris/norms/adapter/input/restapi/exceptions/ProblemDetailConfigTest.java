package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import java.net.URI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

class ProblemDetailConfigTest {

  @Nested
  class CreateProblemDetail {
    @Test
    void createProblemDetailNormNotFound() {
      // given
      NormNotFoundException normNotFoundException = new NormNotFoundException("some/eli");

      // when
      ProblemDetail problemDetail =
          ProblemDetailConfig.createProblemDetail(normNotFoundException, HttpStatus.NOT_FOUND);

      // then
      assertThat(problemDetail).isNotNull();
      assertThat(problemDetail.getType()).isEqualTo(URI.create("/errors/norm-not-found"));
      assertThat(problemDetail.getDetail()).isEqualTo("Norm with eli some/eli does not exist");
      assertThat(problemDetail.getStatus()).isEqualTo(404);
      assertThat(problemDetail.getTitle()).isEqualTo("Norm not found");
    }

    @Test
    void throwExceptionWhenProblemDetailNotDefined() {
      // given
      RuntimeException runtimeException = new RuntimeException("message");

      // when/then
      assertThatThrownBy(
              () -> ProblemDetailConfig.createProblemDetail(runtimeException, HttpStatus.NOT_FOUND))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }
}
