package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class TemporalDataTest {

  @Test
  void getTemporalGroups() {
    // given
    final TemporalData temporalData = new TemporalData(
      XmlMapper.toElement(
        """
               <akn:temporalData xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1_geltzeiten-n1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
                     <akn:temporalGroup eId="meta-n1_geltzeiten-n1_geltungszeitgr-n1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
                        <akn:timeInterval eId="meta-n1_geltzeiten-n1_geltungszeitgr-n1_gelzeitintervall-n1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16"
                                          start="#meta-n1_lebzykl-n1_ereignis-n2" refersTo="geltungszeit"/>
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-n1_geltzeiten-n1_geltungszeitgr-n2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
                        <akn:timeInterval eId="meta-n1_geltzeiten-n1_geltungszeitgr-n2_gelzeitintervall-n1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f"
                                          start="#meta-n1_lebzykl-n1_ereignis-n4" refersTo="geltungszeit"/>
                     </akn:temporalGroup>
                 </akn:temporalData>
        """
      )
    );

    assertThat(temporalData.getTemporalGroups()).hasSize(2);
  }

  @Test
  void getTemporalGroupsEmptyList() {
    // given
    final TemporalData temporalData = new TemporalData(
      XmlMapper.toElement(
        """
        <akn:temporalData xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1_geltzeiten-n1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
         </akn:temporalData>
        """
      )
    );

    assertThat(temporalData.getTemporalGroups()).isEmpty();
  }
}
