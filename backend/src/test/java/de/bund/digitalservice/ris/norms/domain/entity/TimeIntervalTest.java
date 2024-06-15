package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class TimeIntervalTest {

  @Test
  void getEventRefEId() {
    // given
    final TimeInterval timeInterval =
        TimeInterval.builder()
            .node(
                XmlMapper.toNode(
                    """
                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                           """))
            .build();

    assertThat(timeInterval.getEventRefEId()).contains("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getEventRefEIdEmpty() {
    // given
    final TimeInterval timeInterval =
        TimeInterval.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" />
                                           """))
            .build();

    assertThat(timeInterval.getEventRefEId()).isEmpty();
  }
}
