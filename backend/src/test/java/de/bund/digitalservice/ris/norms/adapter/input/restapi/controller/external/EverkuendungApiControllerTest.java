package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.external;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.SecurelessControllerTest;
import de.bund.digitalservice.ris.norms.application.exception.ImportProcessNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(EverkuendungApiController.class)
class EverkuendungApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase;

  @MockitoBean
  private LoadNormendokumentationspacketProcessingStatusUseCase statusService;

  @Nested
  class postVerkuendung {

    @Test
    void itShouldReturnOk() throws Exception {
      UUID processId = UUID.randomUUID();
      when(storeNormendokumentationspaketUseCase.storeNormendokumentationspaket(any())).thenReturn(
        processId
      );

      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "normendokumentationspaket.zip",
        "application/zip",
        InputStream.nullInputStream()
      );
      final MockMultipartFile signature = new MockMultipartFile(
        "signature",
        "signature.sig",
        "text/plain",
        InputStream.nullInputStream()
      );

      mockMvc
        .perform(
          multipart("/api/v1/external/verkuendungen")
            .file(file)
            .file(signature)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.processId").value(processId.toString()));

      verify(storeNormendokumentationspaketUseCase, times(1)).storeNormendokumentationspaket(
        assertArg(query -> {
          assertThat(query.file().getFilename()).isEqualTo("normendokumentationspaket.zip");
          assertThat(query.signature().getFilename()).isEqualTo("signature.sig");
        })
      );
    }
  }

  @Nested
  class getStatus {

    @Test
    void itReturnsStatusOnly() throws Exception {
      // given
      Instant now = Instant.now();
      UUID processId = UUID.randomUUID();
      VerkuendungImportProcess status = VerkuendungImportProcess.builder()
        .id(processId)
        .status(VerkuendungImportProcess.Status.PROCESSING)
        .createdAt(now.minusSeconds(60))
        .startedAt(now)
        .build();

      // when
      when(statusService.getStatus(any())).thenReturn(status);

      // then

      mockMvc
        .perform(
          get("/api/v1/external/verkuendungen/status/{processId}", processId).accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.verkuendungStatus").value("processing"));
    }

    @Test
    void processIdNotFound() throws Exception {
      // given
      UUID processId = UUID.randomUUID();

      // when
      when(statusService.getStatus(any())).thenThrow(new ImportProcessNotFoundException(processId));

      // then

      mockMvc
        .perform(
          get("/api/v1/external/verkuendungen/status/{processId}", processId).accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void itReturnsErrorStatus() throws Exception {
      // given
      Instant now = Instant.now();
      UUID processId = UUID.randomUUID();
      VerkuendungImportProcess status = VerkuendungImportProcess.builder()
        .id(processId)
        .status(VerkuendungImportProcess.Status.ERROR)
        .createdAt(now.minusSeconds(60))
        .startedAt(now)
        .finishedAt(now.plusSeconds(60))
        .detail(
          """
          {
            "type": "/errors/job-run-failed",
            "title": "Tried to import a Normendokumentationspacket the max amount of times but failed",
            "detail": "detail message",
            "additionalProperty": "some-value"
          }
          """
        )
        .build();

      // when
      when(statusService.getStatus(any())).thenReturn(status);

      // then

      mockMvc
        .perform(
          get("/api/v1/external/verkuendungen/status/{processId}", processId).accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.type").value("/errors/job-run-failed"))
        .andExpect(
          jsonPath("$.title").value(
            "Tried to import a Normendokumentationspacket the max amount of times but failed"
          )
        )
        .andExpect(jsonPath("$.detail").value("detail message"))
        .andExpect(jsonPath("$.additionalProperty").value("some-value"));
    }
  }
}
