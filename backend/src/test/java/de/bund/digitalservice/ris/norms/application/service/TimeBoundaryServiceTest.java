package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTimeBoundariesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTimeBoundariesUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeBoundaryServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);

  final TimeBoundaryService service = new TimeBoundaryService(loadNormPort, updateNormPort);

  @Nested
  class loadTimeBoundariesOfNorm {

    @Test
    void itCallsLoadTimeBoundariesOfNormAndReturnsTimeBoundaries() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
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
                                          <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-01-01"
                                              source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                                       </akn:lifecycle>
                                       <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                           <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                           </akn:temporalGroup>
                                           <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                                              <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                                           </akn:temporalGroup>
                                       </akn:temporalData>
                                    </akn:meta>
                                 </akn:act>
                              </akn:akomaNtoso>
                            """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var timeBoundaries =
          service.loadTimeBoundariesOfNorm(new LoadTimeBoundariesUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(timeBoundaries).hasSize(2);

      // handle 1st time boundary
      assertThat(timeBoundaries.get(0).getDate()).contains(LocalDate.parse("2023-12-30"));
      assertThat(timeBoundaries.get(0).getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-2");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
      assertThat(timeBoundaries.get(0).getTimeIntervalEid().get())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("start")
                  .getNodeValue())
          .contains("#" + timeBoundaries.get(0).getEventRefEid().get());

      // handle 2nd time boundary
      assertThat(timeBoundaries.get(1).getDate()).contains(LocalDate.parse("2024-01-01"));
      assertThat(timeBoundaries.get(1).getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-3");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("fdfaeef0-0300-4e5b-9e8b-14d2162bfb00");
      assertThat(timeBoundaries.get(1).getTimeIntervalEid().get())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("8118030a-5fa4-4f9c-a880-b7ba19e5edfb");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("start")
                  .getNodeValue())
          .contains("#" + timeBoundaries.get(1).getEventRefEid().get());
    }

    @Test
    void itCallsLoadTimeBoundariesOfNormAndReturnsTimeBoundariesEmpty() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
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
                                                       </akn:lifecycle>
                                                       <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                                       </akn:temporalData>
                                                    </akn:meta>
                                                 </akn:act>
                                              </akn:akomaNtoso>
                                            """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var timeBoundaries =
          service.loadTimeBoundariesOfNorm(new LoadTimeBoundariesUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(timeBoundaries).isEmpty();
    }
  }

  @Nested
  class updateTimeBoundariesOfNorm {

    @Test
    void itCallsUpdateTimeBoundariesOfNormAndReturnsTimeBoundaries() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
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
                                            """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var timeBoundaryChangeDataNewDate =
          new TimeBoundaryChangeData(null, LocalDate.parse("2024-01-02"));

      var timeBoundaries =
          service.updateTimeBoundariesOfNorm(
              new UpdateTimeBoundariesUseCase.Query(eli, List.of(timeBoundaryChangeDataNewDate)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(timeBoundaries).hasSize(2);

      // old one still there
      assertThat(timeBoundaries.get(0).getDate()).contains(LocalDate.parse("2023-12-30"));
      assertThat(timeBoundaries.get(0).getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-2");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
      assertThat(timeBoundaries.get(0).getTimeIntervalEid().get())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("start")
                  .getNodeValue())
          .contains("#" + timeBoundaries.get(0).getEventRefEid().get());

      // new one added
      assertThat(timeBoundaries.get(1).getDate()).contains(LocalDate.parse("2024-01-02"));
      assertThat(timeBoundaries.get(1).getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-3");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .isNotEmpty();
      assertThat(timeBoundaries.get(1).getTimeIntervalEid().get())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .isNotEmpty();
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeIntervalNode()
                  .getAttributes()
                  .getNamedItem("start")
                  .getNodeValue())
          .contains("#" + timeBoundaries.get(1).getEventRefEid().get());
    }
  }
}
