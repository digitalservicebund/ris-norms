package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class XmlFormattingIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Test
  void itReturnsPrettyPrintedXmlEvenIfStoredUgly() throws Exception {
    Regelungstext originalText = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-verkuendung-1.xml"
    );
    DokumentDto dto = DokumentMapper.mapToDto(originalText);

    String expectedPrettyXml = dto.getXml();

    dto.setXml(dto.getXml().replaceAll(">\\s+<", "><").replaceAll("\\r?\\n", ""));

    dokumentRepository.save(dto);

    String response = mockMvc
      .perform(
        get(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/regelungstext-verkuendung-1"
        ).accept(MediaType.APPLICATION_XML)
      )
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    assertThat(response).isEqualToIgnoringWhitespace(expectedPrettyXml);
  }
}
