package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.EVERKUENDUNG_USER })
class VerkuendungControllerIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

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
