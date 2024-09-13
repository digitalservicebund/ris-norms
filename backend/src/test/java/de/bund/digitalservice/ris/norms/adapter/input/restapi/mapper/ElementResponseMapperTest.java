package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class ElementResponseMapperTest {
  @Test
  void convertsAnArticleNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
              <akn:num GUID="000" eId="hauptteil-1_bezeichnung-1">
                <akn:marker GUID="000" eId="hauptteil-1_bezeichnung-1_zaehlbez-1" name="1"/>
                § 1
              </akn:num>
              <akn:heading GUID="000" eId="hauptteil-1_überschrift-1">
                Überschrift des Artikels
              </akn:heading>
              <akn:paragraph GUID="000" eId="hauptteil-1_abs-1"></akn:paragraph>
            </akn:article>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("§ 1 Überschrift des Artikels");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_para-1");
    assertThat(output.getType()).isEqualTo("article");
  }

  @Test
  void convertsAnArticleWithMissingMarkerToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
              <akn:heading GUID="000" eId="hauptteil-1_überschrift-1">
                Überschrift des Artikels
              </akn:heading>
              <akn:paragraph GUID="000" eId="hauptteil-1_abs-1"></akn:paragraph>
            </akn:article>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Überschrift des Artikels");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_para-1");
    assertThat(output.getType()).isEqualTo("article");
  }

  @Test
  void convertsAnArticleWithMissingHeadingToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
              <akn:num GUID="000" eId="hauptteil-1_bezeichnung-1">
                <akn:marker GUID="000" eId="hauptteil-1_bezeichnung-1_zaehlbez-1" name="1"/>
                § 1
              </akn:num>
              <akn:paragraph GUID="000" eId="hauptteil-1_abs-1"></akn:paragraph>
            </akn:article>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("§ 1");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_para-1");
    assertThat(output.getType()).isEqualTo("article");
  }

  @Test
  void convertsAnArticleWithoutTitleToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
              <akn:paragraph GUID="000" eId="hauptteil-1_abs-1"></akn:paragraph>
            </akn:article>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEmpty();
    assertThat(output.getEid()).isEqualTo("hauptteil-1_para-1");
    assertThat(output.getType()).isEqualTo("article");
  }

  @Test
  void convertsABookToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:book eId="hauptteil-1_buch-1" GUID="3c36aeed-d116-47c1-b475-a5e8bdfefe63">
              <akn:num
                eId="hauptteil-1_buch-1_bezeichnung-1"
                GUID="5b8320d2-2ed9-4652-aa5a-147da381f87c"
              >
                <akn:marker
                  eId="hauptteil-1_buch-1_bezeichnung-1_zaehlbez-1"
                  GUID="9b24f762-a99d-48c6-9dd7-d1cb9bbd758f"
                  name="1"
                />Buch 1</akn:num
              >
              <akn:heading
                eId="hauptteil-1_buch-1_überschrift-1"
                GUID="20bf09db-2a0b-4cc6-ac38-9d485062c187"
                >Überschrift Buch</akn:heading
              >
            </akn:book>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Buch 1 Überschrift Buch");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_buch-1");
    assertThat(output.getType()).isEqualTo("book");
  }

  @Test
  void convertsAPartToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:part
              eId="hauptteil-1_buch-1_teil-1"
              GUID="8afdae9b-dcf4-48c8-ba38-31f24526bcf0"
            >
              <akn:num
                eId="hauptteil-1_buch-1_teil-1_bezeichnung-1"
                GUID="30151b69-5d3a-4fc6-b46a-86b0de37fb15"
              >
                <akn:marker
                  eId="hauptteil-1_buch-1_teil-1_bezeichnung-1_zaehlbez-1"
                  GUID="6c5d6dc7-f14b-4e1d-8f8b-570a13aeff9c"
                  name="1"
                />Teil 1</akn:num
              >
              <akn:heading
                eId="hauptteil-1_buch-1_teil-1_überschrift-1"
                GUID="344fa409-e0ba-4f53-beae-a61abf138efb"
                >Überschrift Teil</akn:heading
              ></akn:part
            >
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Teil 1 Überschrift Teil");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_buch-1_teil-1");
    assertThat(output.getType()).isEqualTo("part");
  }

  @Test
  void convertsAChapterToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:chapter
              eId="hauptteil-1_buch-1_teil-1_kapitel-1"
              GUID="d4b23c42-acab-4220-af45-faa22820d588"
            >
              <akn:num
                eId="hauptteil-1_buch-1_teil-1_kapitel-1_bezeichnung-1"
                GUID="dcca1b77-1a1d-4c67-9cb1-b6687fae065e"
              >
                <akn:marker
                  eId="hauptteil-1_buch-1_teil-1_kapitel-1_bezeichnung-1_zaehlbez-1"
                  GUID="1543b5cf-b6a2-47a0-99a7-172299fdb9f7"
                  name="1"
                />Kapitel 1</akn:num
              >
              <akn:heading
                eId="hauptteil-1_buch-1_teil-1_kapitel-1_überschrift-1"
                GUID="32cd7607-c65e-44ef-bbbb-d0163a122583"
                >Überschrift Kapitel</akn:heading
              ></akn:chapter
            >
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Kapitel 1 Überschrift Kapitel");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1");
    assertThat(output.getType()).isEqualTo("chapter");
  }

  @Test
  void convertsASectionToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:section
              eId="hauptteil-1_abschnitt-1"
              GUID="f1474e45-57fe-4ba4-983e-efef589d31f3"
            >
              <akn:num
                eId="hauptteil-1_abschnitt-1_bezeichnung-1"
                GUID="28b0d5bc-1bb2-4c2f-8766-ba8df3d05441"
              >
                <akn:marker
                  eId="hauptteil-1_abschnitt-1_bezeichnung-1_zaehlbez-1"
                  GUID="d1af8069-6f00-442c-841c-6c4183a49a84"
                  name="1"
                />Abschnitt 1</akn:num
              >
              <akn:heading
                eId="hauptteil-1_abschnitt-1_überschrift-1"
                GUID="d241c159-828c-4b06-8aa2-90b14ade12cb"
                >Allgemeines</akn:heading
              ></akn:section
            >
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Abschnitt 1 Allgemeines");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_abschnitt-1");
    assertThat(output.getType()).isEqualTo("section");
  }

  @Test
  void convertsASubsectionToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:subsection
              eId="hauptteil-1_abschnitt-1_uabschnitt-1"
              GUID="18F63BE0-B9AB-4464-92A0-8469EE5CE8A0"
            >
              <akn:num
                eId="hauptteil-1_abschnitt-1_uabschnitt-1_bezeichnung-1"
                GUID="335ed7f7-c7fb-442b-bb6f-9986d3defcfe"
              >
                <akn:marker
                  eId="hauptteil-1_abschnitt-1_uabschnitt-1_bezeichnung-1_zaehlbez-1"
                  GUID="a953c37a-7cec-4876-826e-fb9e2193603a"
                  name="1"
                />
              </akn:num>
              <akn:heading
                eId="hauptteil-1_abschnitt-1_uabschnitt-1*überschrift-1"
                GUID="7cabc2bc-f00c-4435-9c3a-1bc84111cf2e"
                >Art 1 bis 6</akn:heading
              >
            </akn:subsection>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Art 1 bis 6");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_abschnitt-1_uabschnitt-1");
    assertThat(output.getType()).isEqualTo("subsection");
  }

  @Test
  void convertsATitleToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
          <akn:title
            eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1"
            GUID="564b7bae-affe-42db-a82a-957727e75da3"
          >
            <akn:num
            eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_bezeichnung-1"
            GUID="496212de-91d3-4a5b-8ef4-c2c8aeb28a00"> <akn:marker
            eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_bezeichnung-1_zaehlbez-1"
            GUID="c9c285df-aa3a-4aa7-b9a3-d4e85987375e" name="1"/>Titel 1</akn:num>
            <akn:heading
            eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_überschrift-1"
            GUID="0e672fb2-d15a-4339-a6bd-0e019fc3cc04">Überschrift Titel</akn:heading>
          </akn:title>
          """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Titel 1 Überschrift Titel");
    assertThat(output.getEid())
        .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1");
    assertThat(output.getType()).isEqualTo("title");
  }

  @Test
  void convertsASubtitleToNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:subtitle
              eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1"
              GUID="bdcbfaa4-faa7-48e1-89e1-951fe3181c05"
            >
              <akn:num
                eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_bezeichnung-1"
                GUID="c4f689b2-0090-4898-b59c-3d2f3d6bdec2"
              >
                <akn:marker
                  eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_bezeichnung-1_zaehlbez-1"
                  GUID="0ac85cd0-d9c7-43c0-8374-946fbf2dd4f6"
                  name="1"
                />Untertitel 1</akn:num
              >
              <akn:heading
                eId="hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_überschrift-1"
                GUID="597d4424-edac-46de-8732-5bb39b13479a"
                >Überschrift Untertitel</akn:heading
              ></akn:subtitle
            >
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Untertitel 1 Überschrift Untertitel");
    assertThat(output.getEid())
        .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1");
    assertThat(output.getType()).isEqualTo("subtitle");
  }

  @Test
  void convertsAPrefaceNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:preface xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="einleitung-1" GUID="000">
              <akn:longTitle></akn:longTitle>
              <akn:block></akn:block>
            </akn:preface>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Dokumentenkopf");
    assertThat(output.getEid()).isEqualTo("einleitung-1");
    assertThat(output.getType()).isEqualTo("preface");
  }

  @Test
  void convertsAPreambleNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:preamble xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="preambel-1" GUID="000">
              <akn:formula></akn:formula>
            </akn:preamble>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Eingangsformel");
    assertThat(output.getEid()).isEqualTo("preambel-1");
    assertThat(output.getType()).isEqualTo("preamble");
  }

  @Test
  void convertsAConclusionsNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:conclusions eId="schluss-1" GUID="000">
              <akn:formula></akn:formula>
              <akn:blockContainer></akn:blockContainer>
            </akn:conclusions>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("Schlussteil");
    assertThat(output.getEid()).isEqualTo("schluss-1");
    assertThat(output.getType()).isEqualTo("conclusions");
  }

  @Test
  void convertsAnArbitraryNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:someRandomNode xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="random-1" GUID="000">
            </akn:someRandomNode>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEmpty();
    assertThat(output.getEid()).isEqualTo("random-1");
    assertThat(output.getType()).isEqualTo("someRandomNode");
  }

  @Test
  void throwsWhenConvertingANodeWithoutEid() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:preface xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="000">
              <akn:longTitle></akn:longTitle>
              <akn:block></akn:block>
            </akn:preface>
            """);

    // When
    assertThatThrownBy(() -> ElementResponseMapper.fromElementNode(node))
        .isInstanceOf(NullPointerException.class);
  }
}
