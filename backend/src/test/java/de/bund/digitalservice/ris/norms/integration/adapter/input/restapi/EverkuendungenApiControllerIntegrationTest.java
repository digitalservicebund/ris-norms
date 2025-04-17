package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.EVERKUENDUNG_USER })
class EverkuendungenApiControllerIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;

  @Nested
  class postVerkuendung {

    @Test
    void itSavesVerkuendungToBucket() throws Exception {
      final MockMultipartFile file = createMockFile(
        "file",
        "normendokumentationspaket.zip",
        "application/zip"
      );
      final MockMultipartFile signature = createMockFile(
        "signature",
        "signature.sig",
        "text/plain"
      );
      var result = mockMvc
        .perform(
          multipart("/api/v1/external/verkuendungen")
            .file(file)
            .file(signature)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.processId").isNotEmpty())
        .andReturn();

      final String processId = JsonPath.read(
        result.getResponse().getContentAsString(),
        "$.processId"
      );

      assertFileExists(processId, file.getOriginalFilename());
      assertFileExists(processId, signature.getOriginalFilename());
    }
  }

  @Nested
  class getVerkuendungStatus {

    @Test
    void itRetrievesVerkuendungError() throws Exception {
      // given
      UUID processId = UUID.randomUUID();
      VerkuendungImportProcess verkuendungImportProcess = VerkuendungImportProcess
        .builder()
        .id(processId)
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

      verkuendungImportProcessesRepository.save(
        VerkuendungImportProcessMapper.mapToDto(verkuendungImportProcess)
      );

      // when/then
      mockMvc
        .perform(
          get("/api/v1/external/verkuendungen/status/{processId}", processId)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.type").value("/errors/job-run-failed"))
        .andExpect(
          jsonPath("$.title")
            .value(
              "Tried to import a Normendokumentationspacket the max amount of times but failed"
            )
        )
        .andExpect(jsonPath("$.detail").value("none"));
    }

    @Test
    void itRetrievesSuccessOnlyHasStatus() throws Exception {
      // given
      UUID processId = UUID.randomUUID();
      VerkuendungImportProcess verkuendungImportProcess = VerkuendungImportProcess
        .builder()
        .id(processId)
        .status(VerkuendungImportProcess.Status.SUCCESS)
        .createdAt(Instant.now())
        .startedAt(Instant.now())
        .finishedAt(Instant.now())
        .detail(
          List.of(
            VerkuendungImportProcessDetail
              .builder()
              .type("someString")
              .title("another string")
              .detail("none")
              .build()
          )
        )
        .build();

      verkuendungImportProcessesRepository.save(
        VerkuendungImportProcessMapper.mapToDto(verkuendungImportProcess)
      );

      // when/then
      mockMvc
        .perform(
          get("/api/v1/external/verkuendungen/status/{processId}", processId)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.verkuendungStatus").value("success"))
        .andExpect(jsonPath("$.type").doesNotExist())
        .andExpect(jsonPath("$.title").doesNotExist())
        .andExpect(jsonPath("$.detail").doesNotExist());
    }
  }

  private MockMultipartFile createMockFile(String name, String filename, String contentType)
    throws IOException {
    return new MockMultipartFile(name, filename, contentType, InputStream.nullInputStream());
  }

  private void assertFileExists(String processId, String filename) {
    Assertions
      .assertThat(Files.exists(getEverkuendungPath().resolve(processId).resolve(filename)))
      .isTrue();
  }
}
