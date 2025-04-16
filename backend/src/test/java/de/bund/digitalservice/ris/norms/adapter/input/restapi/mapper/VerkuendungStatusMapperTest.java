package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungStatusErrorResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VerkuendungStatusMapperTest {

  @Test
  void toVerkuendungStatusResponse() {
    // given
    VerkuendungImportProcess verkuendungImportProcess = VerkuendungImportProcess
      .builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcess.Status.ERROR)
      .createdAt(Instant.now())
      .startedAt(Instant.now())
      .finishedAt(Instant.now())
      .detail(
        List.of(
          VerkuendungImportProcessDetail
            .builder()
            .type("/errors/job-run-failed")
            .title(
              "Tried to import a Normendokumentationspacket the max amount of times but failed"
            )
            .detail("none")
            .build()
        )
      )
      .build();

    //when
    VerkuendungStatusErrorResponseSchema verkuendungStatusResponseSchema =
      (VerkuendungStatusErrorResponseSchema) VerkuendungStatusMapper.fromVerkuendungImportProcess(
        verkuendungImportProcess
      );
    assertThat(verkuendungStatusResponseSchema)
      .isInstanceOf(VerkuendungStatusErrorResponseSchema.class);
    assertThat(verkuendungStatusResponseSchema.status()).isEqualTo("error");
    assertThat(verkuendungStatusResponseSchema.type()).isEqualTo("/errors/job-run-failed");
    assertThat(verkuendungStatusResponseSchema.title())
      .isEqualTo("Tried to import a Normendokumentationspacket the max amount of times but failed");
    assertThat(verkuendungStatusResponseSchema.detail()).isEqualTo("none");
  }
}
