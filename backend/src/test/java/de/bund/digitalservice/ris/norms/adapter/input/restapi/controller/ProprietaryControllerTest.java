package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.service.ProprietaryService;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(ProprietaryController.class)
@Import(SecurityConfig.class)
class ProprietaryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProprietaryService proprietaryService;

  @Test
  void return404IfNormNotFound() throws Exception {
    // given
    var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
    when(proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli)))
        .thenThrow(new NormNotFoundException(eli));
    // when
    mockMvc
        .perform(
            get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isNotFound());
  }

  @Test
  void returnFna() throws Exception {
    // given
    var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
    var proprietaryNodeText =
        """
            <akn:proprietary eId="meta-1_proprietary-1"
                                         GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                         source="attributsemantik-noch-undefiniert">
                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                <meta:typ>gesetz</meta:typ>
                                <meta:form>stammform</meta:form>
                                <meta:fassung>verkuendungsfassung</meta:fassung>
                                <meta:art>rechtsetzungsdokument</meta:art>
                                <meta:initiant>nicht-vorhanden</meta:initiant>
                                <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
                                <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
                                <meta:fna>754-28-1</meta:fna>
                                <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
                                <meta:gesta>nicht-vorhanden</meta:gesta>
                                <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
                                <meta:federfuehrung>
                                    <meta:federfuehrend ab="2002-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                                    <meta:federfuehrend ab="2002-10-01" bis="2002-12-01">Bundesministerium der Justiz</meta:federfuehrend>
                                </meta:federfuehrung>
                            </meta:legalDocML.de_metadaten>
                        </akn:proprietary>
            """;
    var proprietary = Proprietary.builder().node(XmlMapper.toNode(proprietaryNodeText)).build();
    when(proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli)))
        .thenReturn(Optional.of(proprietary));
    // when
    mockMvc
        .perform(
            get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna.value").value("754-28-1"));
  }
}
