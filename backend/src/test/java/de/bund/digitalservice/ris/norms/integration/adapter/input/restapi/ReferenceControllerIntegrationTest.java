package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class ReferenceControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Test
  void itThrowsNormNotFound() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1";

    // When // Then
    mockMvc
        .perform(post("/api/v1/references/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isNotFound());
  }

  @Test
  void itReturnsSameXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1";
    final Norm norm = NormFixtures.loadFromDisk("NormWithReferencesFound.xml");
    normRepository.save(NormMapper.mapToDto(norm));

    // When // Then
    mockMvc
        .perform(post("/api/v1/references/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().xml(XmlMapper.toString(norm.getDocument())));
  }

  @Test
  void itReturnsUpdatedXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1";
    final Norm norm = NormFixtures.loadFromDisk("NormWithReferencesToFind.xml");
    normRepository.save(NormMapper.mapToDto(norm));

    // When // Then
    mockMvc
        .perform(post("/api/v1/references/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(
            xpath("//quotedStructure//heading[@eId='hauptteil-1_para-2_überschrift-1']/ref[1]/@eId")
                .string("hauptteil-1_para-2_überschrift-1_ref-1"))
        .andExpect(
            xpath(
                    "//quotedStructure//heading[@eId='hauptteil-1_para-2_überschrift-1']/ref[1]/text()")
                .string("§ 5"))
        .andExpect(
            xpath("//quotedStructure//heading[@eId='hauptteil-1_para-2_überschrift-1']/ref[2]/@eId")
                .string("hauptteil-1_para-2_überschrift-1_ref-2"))
        .andExpect(
            xpath(
                    "//quotedStructure//heading[@eId='hauptteil-1_para-2_überschrift-1']/ref[2]/text()")
                .string("Verordnung (EG) Nr. 1035/2001"))
        .andExpect(
            xpath(
                    "//quotedText[@eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2']/ref/@eId")
                .string(
                    "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2_ref-1"))
        .andExpect(
            xpath(
                    "//quotedText[@eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2']/ref/text()")
                .string("§ 9 Absatz 1 Satz 2"));
  }
}
