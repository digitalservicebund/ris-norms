package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlProcessor;
import org.junit.jupiter.api.Test;

class TemporalGroupTest {

  @Test
  void getEid() {
    // given
    TemporalGroup temporalGroup =
        TemporalGroup.builder()
            .node(
                XmlProcessor.toNode(
                    """
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                        </akn:temporalGroup>
                        """))
            .build();

    // when
    var eid = temporalGroup.getEid();

    // then
    assertThat(eid).contains("meta-1_geltzeiten-1_geltungszeitgr-1");
  }

  @Test
  void getEventRefEId() {
    // given
    TemporalGroup temporalGroup =
        TemporalGroup.builder()
            .node(
                XmlProcessor.toNode(
                    """
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                        </akn:temporalGroup>
                        """))
            .build();

    // when
    var timeInterval = temporalGroup.getTimeInterval();

    // then
    assertThat(timeInterval).isNotNull();
  }
}
