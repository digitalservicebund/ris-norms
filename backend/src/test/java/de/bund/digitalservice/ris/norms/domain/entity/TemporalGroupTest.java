package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class TemporalGroupTest {

  @Test
  void getEid() {
    // given
    TemporalGroup temporalGroup = new TemporalGroup(
      XmlMapper.toNode(
        """
            <akn:temporalGroup xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
        </akn:temporalGroup>
        """
      )
    );

    // when
    var eid = temporalGroup.getEid();

    // then
    assertThat(eid).isEqualTo("meta-1_geltzeiten-1_geltungszeitgr-1");
  }

  @Test
  void getEventRefEId() {
    // given
    TemporalGroup temporalGroup = new TemporalGroup(
      XmlMapper.toNode(
        """
            <akn:temporalGroup xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
        </akn:temporalGroup>
        """
      )
    );

    // when
    var timeInterval = temporalGroup.getTimeInterval();

    // then
    assertThat(timeInterval).isNotNull();
  }

  @Test
  void create() {
    // given
    final TemporalData temporalData = new TemporalData(
      XmlMapper.toNode(
        """
        <akn:temporalData xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
         </akn:temporalData>
        """
      )
    );

    // when
    var temporalGroup = temporalData.addTemporalGroup();

    // then
    assertThat(temporalData.getTemporalGroups()).hasSize(1);
    assertThat(EId.fromMandatoryNode(temporalGroup.getNode()))
      .isEqualTo(new EId("meta-1_geltzeiten-1_geltungszeitgr-1"));
  }
}
