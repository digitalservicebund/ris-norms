package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActiveModificationTest {

  private static final String COMMON_XML =
      """
          <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1"
                          GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46"
                          type="substitution">
            <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                        GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                        href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
            <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                             GUID="94c1e417-e849-4269-8320-9f0173b39626"
                             href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-126.xml"/>
            <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                       GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
                       period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
          </akn:textualMod>
          """;

  private ActiveModification activeModification;

  @BeforeEach
  void setUp() {
    activeModification = ActiveModification.builder().node(XmlMapper.toNode(COMMON_XML)).build();
  }

  @Test
  void getEid() {
    // when
    var eid = activeModification.getEid();

    // then
    assertThat(eid).contains("meta-1_analysis-1_activemod-1_textualmod-1");
  }

  @Test
  void getType() {
    // when
    var type = activeModification.getType();

    // then
    assertThat(type).contains("substitution");
  }

  @Test
  void getSourceHref() {
    // when
    var sourceHref = activeModification.getSourceHref();

    // then
    assertThat(sourceHref)
        .contains("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getSourceEid() {
    // when
    var sourceEid = activeModification.getSourceEid();

    // then
    assertThat(sourceEid)
        .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getDestinationHref() {
    // when
    var destinationHref = activeModification.getDestinationHref();

    // then
    assertThat(destinationHref)
        .contains(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-126.xml");
  }

  @Test
  void setDestinationHref() {
    // when
    activeModification.setDestinationHref("new-destination-href");
    var destinationHref = activeModification.getDestinationHref();

    // then
    assertThat(destinationHref).contains("new-destination-href");
  }

  @Test
  void getDestinationEli() {
    // when
    var destinationEli = activeModification.getDestinationEli();

    // then
    assertThat(destinationEli)
        .contains("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");
  }

  @Test
  void getDestinationEid() {
    // when
    var destinationEid = activeModification.getDestinationEid();

    // then
    assertThat(destinationEid).contains("para-9_abs-3");
  }

  @Test
  void getForcePeriodEid() {
    // when
    var forcePeriodEid = activeModification.getForcePeriodEid();

    // then
    assertThat(forcePeriodEid).contains("meta-1_geltzeiten-1_geltungszeitgr-1");
  }

  @Test
  void setForcePeriodEid() {
    // when
    activeModification.setForcePeriodEid("new-period-eid");
    var forcePeriodEid = activeModification.getForcePeriodEid();

    // then
    assertThat(forcePeriodEid).contains("new-period-eid");
  }

  @Test
  void getDestinationCharacterRange() {
    // when
    var destinationCharacterRange = activeModification.getDestinationCharacterRange();

    // then
    assertThat(destinationCharacterRange).contains("100-126");
  }
}
