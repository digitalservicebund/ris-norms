package de.bund.digitalservice.ris.norms.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

class OidcIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser(roles = { Roles.NORMS_USER })
  void accessToNormsApi() throws Exception {
    mvc
      .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
      // then
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = { Roles.EVERKUENDUNG_USER })
  void accessToEvApi() throws Exception {
    mvc
      .perform(createMultipartRequest("/api/v1/external/verkuendungen"))
      .andExpect(status().isAccepted());
  }

  @Test
  @WithMockUser(roles = { Roles.EVERKUENDUNG_USER })
  void noAccessNormsApiBecauseOfEvRole() throws Exception {
    mvc
      .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = { Roles.NORMS_USER })
  void noAccessEvApiBecauseOfNormsRole() throws Exception {
    mvc
      .perform(createMultipartRequest("/api/v1/external/verkuendungen"))
      .andExpect(status().isForbidden());
  }

  @Test
  void noAccessNormsApiIfNotLoggedIn() throws Exception {
    mvc
      .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void noAccessEvApiIfNotLoggedIn() throws Exception {
    mvc
      .perform(createMultipartRequest("/api/v1/external/verkuendungen"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void shouldServeFrontendWhenLoggedOut() throws Exception {
    mvc
      .perform(get("/app/verkuendungen").accept(MediaType.TEXT_HTML))
      .andExpect(status().is2xxSuccessful());
  }

  private MockHttpServletRequestBuilder createMultipartRequest(String url) throws IOException {
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

    return multipart(url).file(file).file(signature).accept(MediaType.APPLICATION_JSON);
  }
}
