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
import java.util.UUID;
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
      UUID processId = UUID.randomUUID();
      when(storeNormendokumentationspaketUseCase.storeNormendokumentationspaket(any()))
        .thenReturn(processId);

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

      verify(storeNormendokumentationspaketUseCase, times(1))
        .storeNormendokumentationspaket(
          assertArg(query -> {
            assertThat(query.file().getFilename()).isEqualTo("normendokumentationspaket.zip");
            assertThat(query.signature().getFilename()).isEqualTo("signature.sig");
          })
        );
    }
  }
}
