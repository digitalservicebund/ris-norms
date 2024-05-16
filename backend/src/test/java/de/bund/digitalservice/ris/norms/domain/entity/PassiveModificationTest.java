package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class PassiveModificationTest {

  @Test
  void getEid() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
            <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                            GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                            type="substitution">
                <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                            GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                            href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                 GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                 href="#para-20_abs-1/100-126"/>
                <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                           GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                           period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
            </akn:textualMod>
            """))
            .build();

    // when
    var eid = passiveModification.getEid();

    // then
    assertThat(eid).contains("meta-1_analysis-1_pasmod-1_textualmod-2");
  }

  @Test
  void getType() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var type = passiveModification.getType();

    // then
    assertThat(type).contains("substitution");
  }

  @Test
  void getSourceHref() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var sourceHref = passiveModification.getSourceHref();

    // then
    assertThat(sourceHref)
        .contains(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml");
  }

  @Test
  void getSourceEli() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var sourceEli = passiveModification.getSourceEli();

    // then
    assertThat(sourceEli).contains("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1");
  }

  @Test
  void getSourceEid() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var sourceEid = passiveModification.getSourceEid();

    // then
    assertThat(sourceEid)
        .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getForcePeriodEid() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                                        type="substitution">
                                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                                             href="#para-20_abs-1/100-126"/>
                                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                        </akn:textualMod>
                                        """))
            .build();

    // when
    var forcePeriodEid = passiveModification.getForcePeriodEid();

    // then
    assertThat(forcePeriodEid).contains("meta-1_geltzeiten-1_geltungszeitgr-2");
  }

  @Test
  void getDestinationEid() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var destinationEid = passiveModification.getDestinationEid();

    // then
    assertThat(destinationEid).contains("para-20_abs-1");
  }

  @Test
  void getDestinationHref() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var destinationHref = passiveModification.getDestinationHref();

    // then
    assertThat(destinationHref).contains("#para-20_abs-1/100-126");
  }

  @Test
  void getDestinationCharacterRange() {
    // given
    PassiveModification passiveModification =
        PassiveModification.builder()
            .node(
                XmlMapper.toNode(
                    """
                        <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                        GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                        type="substitution">
                            <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                        GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                        href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                            <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                             GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                             href="#para-20_abs-1/100-126"/>
                            <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                       GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                       period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                        </akn:textualMod>
                        """))
            .build();

    // when
    var destinationCharacterRange = passiveModification.getDestinationCharacterRange();

    // then
    assertThat(destinationCharacterRange).contains("100-126");
  }
}
