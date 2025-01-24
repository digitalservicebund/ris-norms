package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class TimeIntervalTest {

  @Test
  void getEventRefEId() {
    // given
    final TimeInterval timeInterval = new TimeInterval(
      XmlMapper.toElement(
        """
         <akn:timeInterval xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
        """
      )
    );

    assertThat(timeInterval.getEventRefEId()).contains("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getEventRefEIdEmpty() {
    // given
    final TimeInterval timeInterval = new TimeInterval(
      XmlMapper.toElement(
        """
        <akn:timeInterval xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" />
                       """
      )
    );

    assertThat(timeInterval.getEventRefEId()).isEmpty();
  }

  @Test
  void create() {
    // given
    TemporalGroup temporalGroup = new TemporalGroup(
      XmlMapper.toElement(
        """
        <akn:temporalGroup xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
        </akn:temporalGroup>
        """
      )
    );

    // when
    var timeInterval = TimeInterval.createAndAppend(temporalGroup.getElement());
    timeInterval.setStart(
      new Href.Builder().setEId("meta-1_lebzykl-1_ereignis-2").buildInternalReference()
    );
    timeInterval.setRefersTo("geltungszeit");

    // then
    assertThat(timeInterval.getEventRefEId()).contains("meta-1_lebzykl-1_ereignis-2");
    assertThat(EId.fromMandatoryNode(timeInterval.getElement()))
      .isEqualTo(new EId("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"));
    assertThat(temporalGroup.getTimeInterval().getElement()).isEqualTo(timeInterval.getElement());
  }
}
