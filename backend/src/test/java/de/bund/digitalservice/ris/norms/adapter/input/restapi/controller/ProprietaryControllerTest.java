package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ProprietaryController.class)
class ProprietaryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private LoadProprietaryFromDokumentUseCase loadProprietaryFromDokumentUseCase;

  @MockitoBean
  private UpdateProprietaryFrameFromDokumentUseCase updateProprietaryFrameFromDokumentUseCase;

  @MockitoBean
  private UpdateProprietarySingleElementFromDokumentUseCase updateProprietarySingleElementFromDokumentUseCase;

  @Nested
  class getProprietary {

    @Test
    void returns404IfNormNotFound() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      when(loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli.asNormEli()))).thenThrow(
        new NormNotFoundException(eli)
      );
      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound());
    }

    @Test
    void returnsProprietaryResponseSchema() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      when(loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli.asNormEli()))).thenReturn(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("art").value("regelungstext"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").value("rechtsverordnung"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("Bezeichnung gemäß Vorlage"))
        .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DEU"))
        .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(true))
        .andExpect(jsonPath("organisationsEinheit").value("Aktuelle Organisationseinheit"))
        .andExpect(jsonPath("ressort").value("Bundesministerium der Justiz"));
    }

    @Test
    void returnsEmptyValuesIfSpecificProprietaryDataIsNotFound() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      when(loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli.asNormEli()))).thenReturn(
        Fixtures.loadNormFromDisk(
          ProprietaryControllerTest.class,
          "vereinsgesetz-with-invalid-proprietary-metadata"
        )
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
        .andExpect(jsonPath("art").isEmpty())
        .andExpect(jsonPath("typ").isEmpty())
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty())
        .andExpect(jsonPath("ressort").isEmpty());
    }

    @Test
    void returnsEmptyValuesIfProprietaryIsEmpty() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      when(loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli.asNormEli()))).thenReturn(
        Fixtures.loadNormFromDisk(
          ProprietaryControllerTest.class,
          "vereinsgesetz-with-empty-proprietary-metadata"
        )
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
        .andExpect(jsonPath("art").isEmpty())
        .andExpect(jsonPath("typ").isEmpty())
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty())
        .andExpect(jsonPath("ressort").isEmpty());
    }
  }

  @Nested
  class updateProprietary {

    @Test
    void updatesProprietarySuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";

      when(
        updateProprietaryFrameFromDokumentUseCase.updateProprietaryFrameFromDokument(any())
      ).thenReturn(
        Fixtures.loadNormFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
        ).getRahmenMetadata()
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," +
              "\"typ\": \"new-typ\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"SN,ÄN,ÜN\"," +
              "\"staat\": \"DEU\"," +
              "\"beschliessendesOrgan\": \"Bundestag\"," +
              "\"qualifizierteMehrheit\": true," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"," +
              "\"ressort\": \"new ressort\"}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("art").value("regelungstext"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").value("rechtsverordnung"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("Bezeichnung gemäß Vorlage"))
        .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DEU"))
        .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(true))
        .andExpect(jsonPath("organisationsEinheit").value("Aktuelle Organisationseinheit"))
        .andExpect(jsonPath("ressort").value("Bundesministerium der Justiz"));

      verify(
        updateProprietaryFrameFromDokumentUseCase,
        times(1)
      ).updateProprietaryFrameFromDokument(
        argThat(
          query ->
            query
              .dokumentExpressionEli()
              .toString()
              .equals("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1") &&
            query.inputMetadata().fna().equals("new-fna") &&
            query.inputMetadata().art().equals("new-art") &&
            query.inputMetadata().typ().equals("new-typ") &&
            query.inputMetadata().subtyp().equals("new-subtyp") &&
            query.inputMetadata().bezeichnungInVorlage().equals("new-bezeichnungInVorlage") &&
            query.inputMetadata().artDerNorm().equals("SN,ÄN,ÜN") &&
            query.inputMetadata().staat().equals("DEU") &&
            query.inputMetadata().beschliessendesOrgan().equals("Bundestag") &&
            query.inputMetadata().qualifizierterMehrheit().equals(true) &&
            query.inputMetadata().organisationsEinheit().equals("Andere Organisationseinheit") &&
            query.inputMetadata().ressort().equals("new ressort")
        )
      );
    }

    @Test
    void itReturnsNotFoundIfNormIsNotFound() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";

      when(
        updateProprietaryFrameFromDokumentUseCase.updateProprietaryFrameFromDokument(any())
      ).thenThrow(
        new NormNotFoundException(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu")
        )
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," +
              "\"typ\": \"new-typ\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"SN,ÄN,ÜN\"," +
              "\"staat\": \"DEU\"," +
              "\"beschliessendesOrgan\": \"Bundestag\"," +
              "\"qualifizierteMehrheit\": true," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"," +
              "\"ressort\": \"new ressort\"}"
            )
        )
        .andExpect(status().isNotFound());
    }
  }

  @Nested
  class getProprietarySingleElement {

    @Test
    void returns404IfNormNotFound() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";
      when(
        loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
          new LoadProprietaryFromDokumentUseCase.Options(eli)
        )
      ).thenThrow(new DokumentNotFoundException(eli.toString()));
      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isNotFound());
    }

    @Test
    void returnsProprietaryResponseSchema() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-1_art-1";

      var proprietary = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      )
        .getMeta()
        .getOrCreateProprietary();
      when(
        loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
          new LoadProprietaryFromDokumentUseCase.Options(eli)
        )
      ).thenReturn(proprietary);

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").value("SN"));
    }

    @Test
    void returnsEmptyValuesIfSpecificProprietaryDataIsNotFound() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-1_art-1";

      var proprietary = Fixtures.loadNormFromDisk(
        ProprietaryControllerTest.class,
        "vereinsgesetz-with-invalid-proprietary-metadata"
      )
        .getRegelungstext1()
        .getMeta()
        .getOrCreateProprietary();
      when(
        loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
          new LoadProprietaryFromDokumentUseCase.Options(eli)
        )
      ).thenReturn(proprietary);

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnsEmptyValuesIfProprietaryDoesNotExist() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-1_art-1";

      var proprietary = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15/regelungstext-verkuendung-1.xml"
      )
        .getMeta()
        .getOrCreateProprietary();
      when(
        loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
          new LoadProprietaryFromDokumentUseCase.Options(eli)
        )
      ).thenReturn(proprietary);

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }
  }

  @Nested
  class updateProprietarySingleElement {

    @Test
    void updatesProprietarySuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      var eid = new EId("hauptteil-1_abschnitt-0_art-1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                               <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
                                   <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
                                           <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                                       <ris:artDerNorm>SN</ris:artDerNorm>
                                   </ris:einzelelement>
                               </ris:legalDocML.de_metadaten>
                           </akn:proprietary>
          """
        )
      );

      when(
        updateProprietarySingleElementFromDokumentUseCase.updateProprietarySingleElementFromDokument(
          any()
        )
      ).thenReturn(proprietary);

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}", eli, eid)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"artDerNorm\": \"SN\"}")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").value("SN"));

      verify(
        updateProprietarySingleElementFromDokumentUseCase,
        times(1)
      ).updateProprietarySingleElementFromDokument(
        argThat(
          query ->
            query
              .dokumentExpressionEli()
              .toString()
              .equals("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1") &&
            query.eid().equals(eid) &&
            query.inputMetadata().artDerNorm().equals("SN")
        )
      );
    }

    @Test
    void itReturnsNotFoundIfNormIsNotFound() throws Exception {
      // given
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";

      when(
        updateProprietarySingleElementFromDokumentUseCase.updateProprietarySingleElementFromDokument(
          any()
        )
      ).thenThrow(new DokumentNotFoundException("Document not found"));

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}", eli, eid)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"artDerNorm\": \"SN\"}")
        )
        .andExpect(status().isNotFound());
    }
  }
}
