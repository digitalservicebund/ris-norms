package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toDocument;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import de.bund.digitalservice.ris.norms.domain.entity.EventRef;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TemporalGroup;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import de.bund.digitalservice.ris.norms.domain.entity.TimeInterval;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeBoundaryMapperTest {

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
    """.strip();

  @Test
  void canMapFromUseCaseData() {
    // Given
    Norm norm = new Norm(toDocument(xml));
    TimeInterval timeInterval = norm.getTimeBoundaries().getFirst().getTimeInterval();
    EventRef eventRef = norm.getTimeBoundaries().getFirst().getEventRef();
    TemporalGroup temporalGroup = norm.getTimeBoundaries().getFirst().getTemporalGroup();
    TimeBoundary timeBoundary = new TimeBoundary(timeInterval, eventRef, temporalGroup);

    // When
    TimeBoundarySchema timeBoundarySchema = TimeBoundaryMapper.fromUseCaseData(timeBoundary);

    // Then
    assertThat(timeBoundarySchema.getDate()).isEqualTo(LocalDate.parse("2023-12-30"));
    assertThat(timeBoundarySchema.getEventRefEid()).isEqualTo("meta-1_lebzykl-1_ereignis-2");
  }

  @Nested
  class fromResponseSchema {

    @Test
    void canMapFromResponseSchemaEmpty() {
      // Given
      List<TimeBoundarySchema> timeBoundaries = List.of();

      // When
      List<TimeBoundaryChangeData> timeBoundaryChangeData = TimeBoundaryMapper.fromResponseSchema(
        timeBoundaries
      );

      // Then
      assertThat(timeBoundaryChangeData).isEmpty();
    }

    @Test
    void canMapFromResponseSchemaOne() {
      // Given
      List<TimeBoundarySchema> timeBoundaries = List.of(
        new TimeBoundarySchema(
          LocalDate.parse("2023-12-30"),
          "meta-1_lebzykl-1_ereignis-2",
          "meta-1_geltzeiten-1_geltungszeitgr-1"
        )
      );

      // When
      List<TimeBoundaryChangeData> timeBoundaryChangeData = TimeBoundaryMapper.fromResponseSchema(
        timeBoundaries
      );

      // Then
      assertThat(timeBoundaryChangeData).isNotEmpty();
      assertThat(timeBoundaryChangeData.getFirst().date()).isEqualTo(LocalDate.parse("2023-12-30"));
      assertThat(timeBoundaryChangeData.getFirst().eid()).isEqualTo("meta-1_lebzykl-1_ereignis-2");
    }

    @Test
    void canMapFromResponseSchemaTwo() {
      // Given
      List<TimeBoundarySchema> timeBoundaries = List.of(
        new TimeBoundarySchema(
          LocalDate.parse("2023-12-30"),
          "meta-1_lebzykl-1_ereignis-2",
          "meta-1_geltzeiten-1_geltungszeitgr-1"
        ),
        new TimeBoundarySchema(
          LocalDate.parse("2016-01-28"),
          "meta-1_lebzykl-1_ereignis-1",
          "meta-1_geltzeiten-1_geltungszeitgr-1"
        )
      );

      // When
      List<TimeBoundaryChangeData> timeBoundaryChangeData = TimeBoundaryMapper.fromResponseSchema(
        timeBoundaries
      );

      // Then
      assertThat(timeBoundaryChangeData).isNotEmpty();
      assertThat(timeBoundaryChangeData.getFirst().date()).isEqualTo(LocalDate.parse("2023-12-30"));
      assertThat(timeBoundaryChangeData.getFirst().eid()).isEqualTo("meta-1_lebzykl-1_ereignis-2");
      assertThat(timeBoundaryChangeData.getLast().date()).isEqualTo(LocalDate.parse("2016-01-28"));
      assertThat(timeBoundaryChangeData.getLast().eid()).isEqualTo("meta-1_lebzykl-1_ereignis-1");
    }
  }
}
