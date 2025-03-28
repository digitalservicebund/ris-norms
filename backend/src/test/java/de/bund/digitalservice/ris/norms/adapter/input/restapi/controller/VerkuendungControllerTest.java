package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.controller.external.VerkuendungController;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import java.io.InputStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(VerkuendungController.class)
class VerkuendungControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StoreNormendokumentationspaketUseCase storeNormendokumentationspaketUseCase;

  @Nested
  class postVerkuendung {

    @Test
    void itShouldReturnOk() throws Exception {
      when(storeNormendokumentationspaketUseCase.storeNormendokumentationspaket(any()))
        .thenReturn("process-id");

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
        .andExpect(jsonPath("$.processId").value("process-id"));

      verify(storeNormendokumentationspaketUseCase, times(1))
        .storeNormendokumentationspaket(
          assertArg(query -> {
            assertThat(query.file()).isEqualTo(file);
            assertThat(query.signature()).isEqualTo(signature);
          })
        );
    }
  }
}
