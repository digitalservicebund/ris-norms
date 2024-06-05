package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class AnalysisTest {

  @Test
  void getActiveModifications() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                            <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                              <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                  </akn:textualMod>
                                  <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                      <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                      <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                  </akn:textualMod>
                              </akn:activeModifications>
                          </akn:analysis>
                          """))
            .build();

    // then
    assertThat(analysis.getActiveModifications()).hasSize(2);
  }

  @Test
  void getActiveModificationsEmpty() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                          </akn:analysis>
                                           """))
            .build();

    // then
    assertThat(analysis.getActiveModifications()).isEmpty();
  }

  @Test
  void getPassiveModifications() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                              <akn:passiveModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                                  </akn:textualMod>
                                                  <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                                      <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                                      <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                                      <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                                  </akn:textualMod>
                                              </akn:passiveModifications>
                                          </akn:analysis>
                                          """))
            .build();

    // then
    assertThat(analysis.getPassiveModifications()).hasSize(2);
  }

  @Test
  void getPassiveModificationsEmpty() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                              </akn:analysis>
                                               """))
            .build();

    // then
    assertThat(analysis.getPassiveModifications()).isEmpty();
  }

  @Test
  void itShouldCreatesThePassiveModificationsNodeIfItDoesNotExist() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                              <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                                              </akn:analysis>
                                                               """))
            .build();

    // when
    final var passiveModificationsNode = analysis.getOrCreatePassiveModificationsNode();

    // then
    assertThat(passiveModificationsNode).isNotNull();
    assertThat(analysis.getOrCreatePassiveModificationsNode()).isEqualTo(passiveModificationsNode);
  }

  @Test
  void itShouldFindThePassiveModificationsNodeIfItExist() {
    final Analysis analysis =
        Analysis.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                            <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                                              <akn:passiveModifications eId="meta-1_analysis-1_activemod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                                                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                                                  </akn:textualMod>
                                                                  <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                                                      <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                                                      <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                                                      <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                                                  </akn:textualMod>
                                                              </akn:passiveModifications>
                                                          </akn:analysis>
                                                          """))
            .build();

    // when
    final var passiveModificationsNode = analysis.getOrCreatePassiveModificationsNode();

    // then
    assertThat(passiveModificationsNode).isNotNull();
    assertThat(NodeParser.getValueFromExpression("@GUID", passiveModificationsNode))
        .contains("77aae58f-06c9-4189-af80-a5f3ada6432c");
  }
}
