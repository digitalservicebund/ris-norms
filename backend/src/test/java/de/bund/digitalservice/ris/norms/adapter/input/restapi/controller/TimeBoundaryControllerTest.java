package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import org.junit.jupiter.api.Nested;
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
@WebMvcTest(TimeBoundaryController.class)
@Import(SecurityConfig.class)
class TimeBoundaryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadTimeBoundariesUseCase loadTimeBoundariesUseCase;
  @MockBean private UpdateTimeBoundariesUseCase updateTimeBoundariesUseCase;

  @Nested
  class getTimeBoundaries {
    @Test
    void getTimeBoundariesReturnsCorrectData() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String eventRef =
          """
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2017-03-16" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                      """
              .strip();

      final String timeInterval =
          """
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                      """
              .strip();

      List<TimeBoundary> timeBoundaries =
          List.of(new TimeBoundary(XmlMapper.toNode(timeInterval), XmlMapper.toNode(eventRef)));

      when(loadTimeBoundariesUseCase.loadTimeBoundariesOfNorm(any())).thenReturn(timeBoundaries);

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].date", is("2017-03-16")))
          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")));

      verify(loadTimeBoundariesUseCase, times(1))
          .loadTimeBoundariesOfNorm(any(LoadTimeBoundariesUseCase.Query.class));
    }
  }

  @Nested
  class updateTimeBoundaries {
    @Test
    void updateTimeBoundariesReturnsSuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String eventRef1 =
          """
                            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
                                  GUID="7174df87-6418-4d47-ac4e-5fb87c2caa50"
                                  date="1964-09-21"
                                  source="attributsemantik-noch-undefiniert"
                                  type="generation"
                                  refersTo="inkrafttreten"/>
                      """
              .strip();

      final String timeInterval1 =
          """
                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                   GUID="6fc42c43-6598-4c95-ac57-da132b047ec1"
                                   refersTo="geltungszeit"
                                   start="#meta-1_lebzykl-1_ereignis-2"/>
                      """
              .strip();

      List<TimeBoundary> timeBoundaries =
          List.of(new TimeBoundary(XmlMapper.toNode(timeInterval1), XmlMapper.toNode(eventRef1)));

      when(updateTimeBoundariesUseCase.updateTimeBoundariesOfNorm(any()))
          .thenReturn(timeBoundaries);

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/timeBoundaries", eli)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "[{\"date\": \"1964-09-21\", \"eid\": \"meta-1_lebzykl-1_ereignis-2\"}]"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].date", is("1964-09-21")))
          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")));

      verify(updateTimeBoundariesUseCase, times(1))
          .updateTimeBoundariesOfNorm(any(UpdateTimeBoundariesUseCase.Query.class));
    }

    @Test
    void updateTimeBoundariesReturnsDateMalformed() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/timeBoundaries", eli)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("[{\"date\": \"THISISNODATE\", \"eid\": null}]"))
          .andExpect(status().isBadRequest());
    }

    @Test
    void updateTimeBoundariesReturnsDateIsNull() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/timeBoundaries", eli)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("[{\"date\": null, \"eid\": null}]"))
          .andExpect(status().isBadRequest());
    }
  }
}
