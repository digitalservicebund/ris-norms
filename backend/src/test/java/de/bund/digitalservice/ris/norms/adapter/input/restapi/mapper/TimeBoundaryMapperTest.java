package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeBoundaryMapperTest {

  @Test
  void canMapFromUseCaseData() {
    // Given
    Regelungstext regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
    );
    TimeInterval timeInterval = regelungstext.getTimeBoundaries().getFirst().getTimeInterval();
    EventRef eventRef = regelungstext.getTimeBoundaries().getFirst().getEventRef();
    TemporalGroup temporalGroup = regelungstext.getTimeBoundaries().getFirst().getTemporalGroup();
    TimeBoundary timeBoundary = new TimeBoundary(timeInterval, eventRef, temporalGroup);

    // When
    TimeBoundarySchema timeBoundarySchema = TimeBoundaryMapper.fromUseCaseData(timeBoundary);

    // Then
    assertThat(timeBoundarySchema.getDate()).isEqualTo(LocalDate.parse("2017-03-16"));
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
