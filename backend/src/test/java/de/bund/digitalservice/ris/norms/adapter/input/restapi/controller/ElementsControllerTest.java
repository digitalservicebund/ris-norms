package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(ElementsController.class)
@Import(SecurityConfig.class)
public class ElementsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadNormUseCase loadNormUseCase;

    @Test
    void itReturnsElementsResponseEntrySchema() throws Exception {
        // given
        var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
        when(loadNormUseCase.loadNorm(any())).thenReturn(Optional.of(norm));

        // when
        mockMvc
                .perform(
                        get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements?type=article")
                )
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
//                .andExpect(jsonPath("$[0].title").value("§ 20"))
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].eid").value("hauptteil-1_para-20"))
                .andExpect(jsonPath("$[0].type").value("article"));
    }
}
