package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toDocument;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TimeBoundaryTest {

  String xml =
      """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                                  <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                                      source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                                  <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                                      source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                               </akn:lifecycle>
                               <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                           <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                           </akn:temporalGroup>
                               </akn:temporalData>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """
          .strip();

  @Test
  void getDate() {
    // given

    Norm norm = new Norm(toDocument(xml));
    TimeInterval timeInterval = norm.getTimeBoundaries().getFirst().getTimeInterval();
    EventRef eventRef = norm.getTimeBoundaries().getFirst().getEventRef();
    TemporalGroup temporalGroup = norm.getTimeBoundaries().getFirst().getTemporalGroup();

    // when
    TimeBoundary tb = new TimeBoundary(timeInterval, eventRef, temporalGroup);

    // then
    assertThat(tb.getEventRef().getDate()).contains(LocalDate.parse("2023-12-30"));
  }

  @Test
  void getEventRefEid() {
    // given

    Norm norm = new Norm(toDocument(xml));
    TimeInterval timeInterval = norm.getTimeBoundaries().getFirst().getTimeInterval();
    EventRef eventRef = norm.getTimeBoundaries().getFirst().getEventRef();
    TemporalGroup temporalGroup = norm.getTimeBoundaries().getFirst().getTemporalGroup();

    // when
    TimeBoundary tb = new TimeBoundary(timeInterval, eventRef, temporalGroup);

    // then
    assertThat(tb.getEventRefEid()).contains("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getTimeIntervalEid() {
    // given
    Norm norm = new Norm(toDocument(xml));
    TimeInterval timeInterval = norm.getTimeBoundaries().getFirst().getTimeInterval();
    EventRef eventRef = norm.getTimeBoundaries().getFirst().getEventRef();
    TemporalGroup temporalGroup = norm.getTimeBoundaries().getFirst().getTemporalGroup();

    // when
    TimeBoundary tb = new TimeBoundary(timeInterval, eventRef, temporalGroup);

    // then
    assertThat(tb.getTimeIntervalEid())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
  }
}
