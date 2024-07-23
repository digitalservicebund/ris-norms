package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextualModTest {

  private static final String COMMON_XML =
      """
          <akn:textualMod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1_analysis-1_activemod-1_textualmod-1"
                          GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46"
                          type="substitution">
            <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                        GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                        href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
            <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                             GUID="94c1e417-e849-4269-8320-9f0173b39626"
                             href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-126.xml"
                             upTo="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml"/>
            <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                       GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
                       period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
          </akn:textualMod>
          """;

  private TextualMod textualMod;

  @BeforeEach
  void setUp() {
    textualMod = TextualMod.builder().node(XmlMapper.toNode(COMMON_XML)).build();
  }

  @Test
  void getEid() {
    // when
    var eid = textualMod.getEid();

    // then
    assertThat(eid).contains("meta-1_analysis-1_activemod-1_textualmod-1");
  }

  @Test
  void getType() {
    // when
    var type = textualMod.getType();

    // then
    assertThat(type).contains("substitution");
  }

  @Test
  void getSourceHref() {
    // when
    var sourceHref = textualMod.getSourceHref();

    // then
    assertThat(sourceHref)
        .contains(
            new Href(
                "#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"));
  }

  @Test
  void getDestinationHref() {
    // when
    var destinationHref = textualMod.getDestinationHref();

    // then
    assertThat(destinationHref)
        .contains(
            new Href(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-126.xml"));
  }

  @Test
  void setDestinationHref() {
    // when
    textualMod.setDestinationHref(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-130.xml");
    var destinationHref = textualMod.getDestinationHref();

    // then
    assertThat(destinationHref)
        .contains(
            new Href(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-130.xml"));
  }

  @Test
  void setDestinationUpto() {
    // when
    textualMod.setDestinationUpTo(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-130.xml");
    var destinationUpTo = textualMod.getDestinationUpTo();

    // then
    assertThat(destinationUpTo)
        .contains(
            new Href(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-130.xml"));
  }

  @Test
  void deleteDestinationUpto() {
    // given
    textualMod.setDestinationUpTo(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3/100-130.xml");

    // when
    textualMod.setDestinationUpTo(null);

    // then
    var destinationUpTo = textualMod.getDestinationUpTo();
    assertThat(destinationUpTo).isNotPresent();
  }

  @Test
  void ignoreDeleteInDestinationUptoWhenNotPresent() {
    // when
    textualMod.setDestinationUpTo(null);

    // then
    var destinationUpTo = textualMod.getDestinationUpTo();
    assertThat(destinationUpTo).isNotPresent();
  }

  @Test
  void getForcePeriodEid() {
    // when
    var forcePeriodEid = textualMod.getForcePeriodEid();

    // then
    assertThat(forcePeriodEid).contains("meta-1_geltzeiten-1_geltungszeitgr-1");
  }

  @Test
  void setForcePeriodEid() {
    // when
    textualMod.setForcePeriodEid("new-period-eid");
    var forcePeriodEid = textualMod.getForcePeriodEid();

    // then
    assertThat(forcePeriodEid).contains("new-period-eid");
  }
}
