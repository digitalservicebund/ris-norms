package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

public class ElementResponseMapperTest {
  @Test
  void convertsAnArticleNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:article GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
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
            <akn:article GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
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
            <akn:article GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
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
            <akn:article GUID="000" eId="hauptteil-1_para-1" refersTo="stammform">
              <akn:paragraph GUID="000" eId="hauptteil-1_abs-1"></akn:paragraph>
            </akn:article>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("");
    assertThat(output.getEid()).isEqualTo("hauptteil-1_para-1");
    assertThat(output.getType()).isEqualTo("article");
  }

  @Test
  void convertsAPrefaceNode() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:preface eId="einleitung-1" GUID="000">
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
            <akn:preamble eId="preambel-1" GUID="000">
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
            <akn:someRandomNode eId="random-1" GUID="000">
            </akn:someRandomNode>
            """);

    // When
    var output = ElementResponseMapper.fromElementNode(node);

    // Then
    assertThat(output.getTitle()).isEqualTo("");
    assertThat(output.getEid()).isEqualTo("random-1");
    assertThat(output.getType()).isEqualTo("someRandomNode");
  }

  @Test
  void throwsWhenConvertingANodeWithoutEid() {
    // Given
    var node =
        XmlMapper.toNode(
            """
            <akn:preface GUID="000">
              <akn:longTitle></akn:longTitle>
              <akn:block></akn:block>
            </akn:preface>
            """);

    // When
    assertThatThrownBy(() -> ElementResponseMapper.fromElementNode(node));
  }
}
