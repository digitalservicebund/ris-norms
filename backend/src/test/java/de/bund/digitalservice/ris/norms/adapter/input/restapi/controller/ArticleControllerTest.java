package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ArticleController.class)
class ArticleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadRegelungstextUseCase loadRegelungstextUseCase;

  @MockitoBean
  private LoadArticlesFromDokumentUseCase loadArticlesFromDokumentUseCase;

  @MockitoBean
  private LoadSpecificArticlesXmlFromDokumentUseCase loadSpecificArticlesXmlFromDokumentUseCase;

  @MockitoBean
  private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @MockitoBean
  private LoadArticleHtmlUseCase loadArticleHtmlUseCase;

  @Nested
  class getArticles {

    @Test
    void itReturnsArticles() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");

      when(loadArticlesFromDokumentUseCase.loadArticlesFromDokument(any()))
        .thenReturn(regelungstext.getArticles());

      // When
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles")
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].eid").value("hauptteil-1_art-2"))
        .andExpect(jsonPath("$[2]").doesNotExist());

      verify(loadArticlesFromDokumentUseCase, times(1))
        .loadArticlesFromDokument(
          argThat(argument ->
            Objects.equals(
              argument.eli(),
              DokumentExpressionEli.fromString(
                "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
              )
            )
          )
        );
    }

    @Test
    void itReturnsUnprocessableEntityWhenMandatoryNodeIsMissing() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModificationsInDifferentArticles.xml"
      );

      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      when(loadArticlesFromDokumentUseCase.loadArticlesFromDokument(any()))
        .thenThrow(new MandatoryNodeNotFoundException("example-xpath", "example/eli"));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?amendedBy=eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isUnprocessableEntity());
    }
  }

  @Nested
  class getArticlesRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadSpecificArticlesXmlFromDokumentUseCase.loadSpecificArticlesXmlFromDokument(any()))
        .thenReturn(List.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When
      mockMvc
        .perform(
          get("/api/v1/norms/{eli}/articles?refersTo=something", eli).accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string("<div>\n" + html + "\n</div>\n"));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class getArticle {

    @Test
    void itReturnsArticle() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");

      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("hauptteil-1_art-1"));

      verify(loadRegelungstextUseCase, times(1))
        .loadRegelungstext(
          argThat(argument ->
            Objects.equals(
              argument.eli(),
              DokumentExpressionEli.fromString(
                "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
              )
            )
          )
        );
    }

    @Test
    void itReturnsNothingIfArticleDoesNotExists() throws Exception {
      // Given
      var regelungstext = new Regelungstext(
        XmlMapper.toDocument(
          """
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                     <!-- Artikel 1 : Hauptänderung -->
                     <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="hauptaenderung">
                        <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                               Artikel 1</akn:num>
                        <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                        <!-- Absatz (1) -->
                        <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                           <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                               </akn:num>
                           <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                              <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                 <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                              </akn:intro>
                           </akn:list>
                        </akn:paragraph>
                     </akn:article>
                      <!-- Artikel 3: Geltungszeitregel-->
                      <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="geltungszeitregel">
                         <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                Artikel 3</akn:num>
                         <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                         <!-- Absatz (1) -->
                         <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                            <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">

                                </akn:num>
                            <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                               <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                  nach der Verkündung</akn:date> in Kraft. </akn:p>
                            </akn:content>
                         </akn:paragraph>
                      </akn:article>
                  </akn:body>
               </akn:act>
            </akn:akomaNtoso>
          """
        )
      );

      // When
      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/hauptteil-1_art-4523"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound());

      verify(loadRegelungstextUseCase, times(1))
        .loadRegelungstext(
          argThat(argument ->
            Objects.equals(
              argument.eli(),
              DokumentExpressionEli.fromString(
                "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
              )
            )
          )
        );
    }
  }

  @Nested
  class getArticleRender {

    @Test
    void itReturnsArticleRender() throws Exception {
      // Given
      var regelungstext = new Regelungstext(
        XmlMapper.toDocument(
          """
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                     <!-- Artikel 1 : Hauptänderung -->
                     <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="hauptaenderung">
                        <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                               Artikel 1</akn:num>
                        <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                        <!-- Absatz (1) -->
                        <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                           <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                               </akn:num>
                           <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                              <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                 <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                              </akn:intro>
                           </akn:list>
                        </akn:paragraph>
                     </akn:article>
                      <!-- Artikel 3: Geltungszeitregel-->
                      <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="geltungszeitregel">
                         <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                Artikel 3</akn:num>
                         <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                         <!-- Absatz (1) -->
                         <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                            <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">

                                </akn:num>
                            <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                               <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                  nach der Verkündung</akn:date> in Kraft. </akn:p>
                            </akn:content>
                         </akn:paragraph>
                      </akn:article>
                  </akn:body>
               </akn:act>
            </akn:akomaNtoso>
          """
        )
      );

      // When
      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);
      when(loadArticleHtmlUseCase.loadArticleHtml(any())).thenReturn("<div></div>");

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().string("<div></div>"));
    }
  }
}
