package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.XmlMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class NormControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsAmendingLawXml() throws Exception {
    // Given
    final String xml =
        """
            <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
              <akn:act name="regelungstext">
                 <!-- Metadaten -->
                 <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                    <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                       <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                          <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                          <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                       </akn:FRBRExpression>
                   </akn:identification>
                 </akn:meta>
              </akn:act>
            </akn:akomaNtoso>""";

    // When
    var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    normRepository.save(NormMapper.mapToDto(norm));

    // When // Then
    mockMvc
        .perform(
            get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                .accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(xml));
  }
}
