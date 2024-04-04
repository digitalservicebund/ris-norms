package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.TargetLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.TargetLawRepository;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;

class TargetLawControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private TargetLawRepository targetLawRepository;

  @AfterEach
  void cleanUp() {
    targetLawRepository.deleteAll();
  }

  TimeMachineService timeMachineService;

  @Test
  void itCallsTargetLawServiceAndReturnsTargetLaw() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final Document xml = timeMachineService.stringToXmlDocument("<target></target>");
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    final TargetLaw targetLaw =
        TargetLaw.builder().eli(eli).title(title).xml(xml).fna(fna).shortTitle(shortTitle).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(targetLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/target-laws/{eli}", encodedEli))
        .andExpect(jsonPath("eli").value(equalTo(eli)))
        .andExpect(jsonPath("title").value(equalTo(title)))
        .andExpect(jsonPath("fna").value(equalTo(fna)))
        .andExpect(jsonPath("shortTitle").value(equalTo(shortTitle)))
        .andExpect(jsonPath("xml").doesNotExist());
  }

  @Test
  void itCallsTargetLawServiceAndReturnsTargetLawXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final Document xml = timeMachineService.stringToXmlDocument("<target></target>");
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    final TargetLaw targetLaw =
        TargetLaw.builder().eli(eli).title(title).xml(xml).fna(fna).shortTitle(shortTitle).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When // Then
    mockMvc
        .perform(get("/api/v1/target-laws/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
        .andExpect(content().string("<target></target>"));
  }

  @Test
  void itCallsTargetLawServiceAndReturnsTargetLawHtml() throws Exception {
    // Given
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
            .title("Title vom Gesetz")
            .xml(timeMachineService.stringToXmlDocument(
                """
                <?xml version="1.0" encoding="UTF-8"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-metadaten.xsd
                                      http://Inhaltsdaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                  <akn:body>
                    <akn:p eId="one">text1</akn:p>
                    <akn:p eId="two">text2</akn:p>
                  </akn:body>
                </akn:akomaNtoso>
                """))
            .fna("4711")
            .shortTitle("TitleGesetz")
            .build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When // Then
    mockMvc
        .perform(
            get("/api/v1/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
                .accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(
            content()
                .string(
                    containsString(
                        """
                            <span class="akn-p" data-eId="two">text2</span>
                            """)));
  }

  @Test
  void itSendATargetLawXmlAndReturnsTheSameTargetLawXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final Document xml = timeMachineService.stringToXmlDocument("<target></target>");
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    final TargetLaw targetLaw =
        TargetLaw.builder().eli(eli).title(title).xml(xml).fna(fna).shortTitle(shortTitle).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    final String newXml = "<new></new>";

    // When // Then
    mockMvc
        .perform(
            put("/api/v1/target-laws/{eli}", eli)
                .content(newXml)
                .contentType(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(newXml));
  }

  @Test
  void itPostsAmendingLawAndReturns404() throws Exception {
    // Given
    final String amendingLawXmlText =
        """
              <?xml version="1.0" encoding="UTF-8"?>
              <akn:body>
                  <akn:mod>
                   In <akn:ref
       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
       2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
       <akn:quotedText>new</akn:quotedText>.
                  </akn:mod>

                  "old" -> "new"

                </akn:body>
              """
            .strip();

    final String targetLawEliShort = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

    // When // Then
    mockMvc
        .perform(
            post("/api/v1/target-laws/{eli}/preview", targetLawEliShort)
                .content(amendingLawXmlText)
                .contentType(MediaType.APPLICATION_XML))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void itPostsAmendingLawAndReturnsAppliedTargetLawXml() throws Exception {
    // Given
    final String amendingLawXmlText =
        """
                  <?xml version="1.0" encoding="UTF-8"?>
                  <akn:body>
                      <akn:mod>
                       In <akn:ref
           href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
           2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
           <akn:quotedText>new</akn:quotedText>.
                      </akn:mod>

                      "old" -> "new"

                    </akn:body>
                  """
            .strip();

    final String targetLawEliShort = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final Document targetLawXmlText = timeMachineService.stringToXmlDocument(
        """
                  <?xml version="1.0" encoding="UTF-8"?>
                  <akn:body>
                      <akn:p eId="one">old text</akn:p>
                      <akn:p eId="two">old text</akn:p>
                    </akn:body>
                  """);
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    // When
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli(targetLawEliShort)
            .title(title)
            .xml(targetLawXmlText)
            .fna(fna)
            .shortTitle(shortTitle)
            .build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When // Then
    mockMvc
        .perform(
            post("/api/v1/target-laws/{eli}/preview", targetLawEliShort)
                .content(amendingLawXmlText)
                .contentType(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
        .andExpect(content().string(containsString("<akn:p eId=\"two\">new text</akn:p>")));
  }

  @Test
  void itPostsAmendingLawAndReturnsAppliedTargetLawAsHtml() throws Exception {
    // Given
    final String amendingLawXmlText =
        """
                      <?xml version="1.0" encoding="UTF-8"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-metadaten.xsd
                                            http://Inhaltsdaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                        <akn:body>
                          <akn:mod>
                           In <akn:ref
               href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
               2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
               <akn:quotedText>new</akn:quotedText>.
                          </akn:mod>

                          "old" -> "new"

                        </akn:body>
                      </akn:akomaNtoso>
                      """
            .strip();

    final String targetLawEliShort = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final Document targetLawXml = timeMachineService.stringToXmlDocument(
        """
                      <?xml version="1.0" encoding="UTF-8"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-metadaten.xsd
                                            http://Inhaltsdaten.LegalDocML.de/1.6/ ../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                        <akn:body>
                          <akn:p eId="one">old text</akn:p>
                          <akn:p eId="two">old text</akn:p>
                        </akn:body>
                      </akn:akomaNtoso>
                      """);
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    // When
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli(targetLawEliShort)
            .title(title)
            .xml(targetLawXml)
            .fna(fna)
            .shortTitle(shortTitle)
            .build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When // Then
    mockMvc
        .perform(
            post("/api/v1/target-laws/{eli}/preview", targetLawEliShort)
                .content(amendingLawXmlText)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(
            content()
                .string(
                    containsString(
                        """
                        <span class="akn-p" data-eId="two">new text</span>
                    """)));
  }
}
