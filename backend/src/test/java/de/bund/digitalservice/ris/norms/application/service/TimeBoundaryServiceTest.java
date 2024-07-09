package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTimeBoundariesAmendedByUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTimeBoundariesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTimeBoundariesUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import de.bund.digitalservice.ris.norms.helper.MemoryAppender;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

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
      assertThat(timeBoundaries.getFirst().getEventRef().getDate())
          .contains(LocalDate.parse("2023-12-30"));
      assertThat(timeBoundaries.getFirst().getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-2");
      assertThat(
              timeBoundaries
                  .getFirst()
                  .getTimeInterval()
                  .getNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
      assertThat(
              timeBoundaries
                  .getFirst()
                  .getTimeInterval()
                  .getNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
      assertThat(timeBoundaries.getFirst().getTimeIntervalEid().get())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
      assertThat(
              timeBoundaries
                  .getFirst()
                  .getTimeInterval()
                  .getNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeInterval()
                  .getNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(0)
                  .getTimeInterval()
                  .getNode()
                  .getAttributes()
                  .getNamedItem("start")
                  .getNodeValue())
          .contains("#" + timeBoundaries.get(0).getEventRefEid().get());

      // handle 2nd time boundary
      assertThat(timeBoundaries.get(1).getEventRef().getDate())
          .contains(LocalDate.parse("2024-01-01"));
      assertThat(timeBoundaries.get(1).getEventRefEid().get())
          .contains("meta-1_lebzykl-1_ereignis-3");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeInterval()
                  .getNode()
                  .getParentNode()
                  .getAttributes()
                  .getNamedItem("eId")
                  .getNodeValue())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeInterval()
                  .getNode()
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
                  .getTimeInterval()
                  .getNode()
                  .getAttributes()
                  .getNamedItem("GUID")
                  .getNodeValue())
          .contains("8118030a-5fa4-4f9c-a880-b7ba19e5edfb");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeInterval()
                  .getNode()
                  .getAttributes()
                  .getNamedItem("refersTo")
                  .getNodeValue())
          .contains("geltungszeit");
      assertThat(
              timeBoundaries
                  .get(1)
                  .getTimeInterval()
                  .getNode()
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
  class loadTimeBoundariesAmendedBy {

    @Test
    void itCallsLoadTimeBoundariesAmendedByAndReturnsTimeBoundaries() {

      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var amendedBy = "eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                                    <?xml version="1.0" encoding="UTF-8"?>
                                    <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                                    <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                                                                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                                       <akn:act name="regelungstext">
                                          <!-- Metadaten -->
                                          <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                             <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                                                <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                                                   <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-1" GUID="06fb52c3-fce1-4be8-accc-3035452378ff" type="substitution">
                                                      <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-1_source-1" GUID="5384f580-110b-4f8a-8794-8b85c29aabdf" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml" />
                                                      <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-1_destination-1" GUID="2c26512f-fb04-45f2-8283-660274e52fdb" href="#para-9_abs-3" />
                                                      <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-1_gelzeitnachw-1" GUID="45331583-4386-4e3f-b68f-5af327347874" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
                                                   </akn:textualMod>
                                                   <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                                                      <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                                                      <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                                                      <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-3" />
                                                   </akn:textualMod>
                                                   <!-- Passive mod from different amending law -->
                                                   <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                                                      <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/120/2024-06-28/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                                                      <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                                                      <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-4" />
                                                   </akn:textualMod>
                                                </akn:passiveModifications>
                                             </akn:analysis>
                                             <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-01-01" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-02-28" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                                             </akn:lifecycle>
                                             <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                                   <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                                </akn:temporalGroup>
                                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                                                   <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                                                </akn:temporalGroup>
                                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                                                   <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                                                </akn:temporalGroup>
                                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                                                   <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
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
          service.loadTimeBoundariesAmendedBy(
              new LoadTimeBoundariesAmendedByUseCase.Query(eli, amendedBy));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));

      assertThat(timeBoundaries).hasSize(2);

      assertThat(timeBoundaries.getFirst().getTemporalGroupEid())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
      assertThat(timeBoundaries.getFirst().getTimeIntervalEid())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1");
      assertThat(timeBoundaries.getFirst().getEventRefEid())
          .contains("meta-1_lebzykl-1_ereignis-3");

      assertThat(timeBoundaries.getLast().getTemporalGroupEid())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-3");
      assertThat(timeBoundaries.getLast().getTimeIntervalEid())
          .contains("meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1");
      assertThat(timeBoundaries.getLast().getEventRefEid()).contains("meta-1_lebzykl-1_ereignis-4");
    }

    @Test
    void itCallsLoadTimeBoundariesAmendedByAndThrowsNormNotFoundException() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var amendedBy = "eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1";

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      Throwable thrown =
          catchThrowable(
              () ->
                  service.loadTimeBoundariesAmendedBy(
                      new LoadTimeBoundariesAmendedByUseCase.Query(eli, amendedBy)));

      assertThat(thrown).isInstanceOf(NormNotFoundException.class);
    }
  }

  @Nested
  class updateTimeBoundariesOfNorm {
    @Test
    void itCallsUpdateTimeBoundariesOfNormAndReturnsTimeBoundariesNothingChanged() {

      String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var oldXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-3" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
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
                    <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                        <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                           <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                              <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                              <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                              Innern</akn:docProponent>
                              <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                           </akn:p>
                        </akn:longTitle>
                        <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                           <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                        </akn:block>
                     </akn:preface>
                 </akn:act>
              </akn:akomaNtoso>
              """;

      var normBefore = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();
      var normAfter = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();

      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(normBefore));
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(normAfter));

      // When
      var timeBoundaryChangeDataOldStays =
          new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-2", LocalDate.parse("2023-12-30"));

      var result =
          service.updateTimeBoundariesOfNorm(
              new UpdateTimeBoundariesUseCase.Query(eli, List.of(timeBoundaryChangeDataOldStays)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1))
          .updateNorm(argThat(argument -> Objects.equals(argument.norm(), normAfter)));

      assertThat(result).isNotEmpty();
    }

    @Test
    void itCallsUpdateTimeBoundariesOfNormAndReturnsTimeBoundariesNew() {

      String eli = "eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1";

      var xml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1" />
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-3" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
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
                    <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                        <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                           <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                              <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                              <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                              Innern</akn:docProponent>
                              <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                           </akn:p>
                        </akn:longTitle>
                        <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                           <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                        </akn:block>
                     </akn:preface>
                 </akn:act>
              </akn:akomaNtoso>
              """;

      var normBefore = Norm.builder().document(XmlMapper.toDocument(xml)).build();

      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(normBefore));
      // When
      var timeBoundaryChangeDataOldStays =
          new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-2", LocalDate.parse("2023-12-30"));
      var timeBoundaryChangeDataNewDate =
          new TimeBoundaryChangeData(null, LocalDate.parse("2024-01-02"));

      service.updateTimeBoundariesOfNorm(
          new UpdateTimeBoundariesUseCase.Query(
              eli, List.of(timeBoundaryChangeDataOldStays, timeBoundaryChangeDataNewDate)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1))
          .updateNorm(
              argThat(
                  argument ->
                      Objects.equals(
                              argument
                                  .norm()
                                  .getTimeBoundaries()
                                  .getFirst()
                                  .getEventRef()
                                  .getDate()
                                  .get(),
                              LocalDate.parse("2023-12-30"))
                          && Objects.equals(
                              argument
                                  .norm()
                                  .getTimeBoundaries()
                                  .getLast()
                                  .getEventRef()
                                  .getDate()
                                  .get(),
                              LocalDate.parse("2024-01-02"))
                          && argument.norm().getTimeBoundaries().size() == 2));
    }

    @Test
    void itCallsUpdateTimeBoundariesOfNormAndReturnsTimeBoundariesDelete() {

      String eli = "eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1";

      var xml =
          """
                  <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                         http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                     <akn:act name="regelungstext">
                        <!-- Metadaten -->
                        <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1" />
                                 <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                                 <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                                 <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-3" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                              </akn:FRBRExpression>
                          </akn:identification>
                           <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                              <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                                  source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                              <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                                  source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                              <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="9ccda9b8-b213-43c5-8ee0-ec47c3c602bb" date="2024-03-13"
                                  source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                           </akn:lifecycle>
                           <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                 <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                 </akn:temporalGroup>
                                 <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f550bb38-e322-48ac-acf6-6b53e2e174b8">
                                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="6ea11eeb-31d2-47a5-9cac-7a31a14b86d1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                                 </akn:temporalGroup>
                           </akn:temporalData>
                        </akn:meta>
                        <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                            <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                               <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                                  <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                                  <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                                  Innern</akn:docProponent>
                                  <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                               </akn:p>
                            </akn:longTitle>
                            <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                               <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                            </akn:block>
                         </akn:preface>
                     </akn:act>
                  </akn:akomaNtoso>
                  """;

      var normBefore = Norm.builder().document(XmlMapper.toDocument(xml)).build();

      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(normBefore));

      // When
      var timeBoundaryChangeDataOldStays =
          new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-2", LocalDate.parse("2023-12-30"));

      service.updateTimeBoundariesOfNorm(
          new UpdateTimeBoundariesUseCase.Query(eli, List.of(timeBoundaryChangeDataOldStays)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1))
          .updateNorm(
              argThat(
                  argument ->
                      Objects.equals(
                              argument
                                  .norm()
                                  .getTimeBoundaries()
                                  .getLast()
                                  .getEventRef()
                                  .getDate()
                                  .get(),
                              LocalDate.parse("2023-12-30"))
                          && argument.norm().getTimeBoundaries().size() == 1));
    }

    @Test
    void itChangesADate() {

      String eli = "eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1";

      var xml =
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
                              <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="9ccda9b8-b213-43c5-8ee0-ec47c3c602bb" date="2024-03-13"
                                  source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                           </akn:lifecycle>
                           <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                 <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                 </akn:temporalGroup>
                                 <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f550bb38-e322-48ac-acf6-6b53e2e174b8">
                                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="6ea11eeb-31d2-47a5-9cac-7a31a14b86d1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                                 </akn:temporalGroup>
                           </akn:temporalData>
                        </akn:meta>
                     </akn:act>
                  </akn:akomaNtoso>
                  """;

      var normBefore = Norm.builder().document(XmlMapper.toDocument(xml)).build();

      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(normBefore));

      // When
      var timeBoundaryChangeDataNewDate1 =
          new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-2", LocalDate.parse("1980-01-01"));
      var timeBoundaryChangeDataNewDate2 =
          new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-3", LocalDate.parse("1990-01-01"));

      service.updateTimeBoundariesOfNorm(
          new UpdateTimeBoundariesUseCase.Query(
              eli, List.of(timeBoundaryChangeDataNewDate1, timeBoundaryChangeDataNewDate2)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1))
          .updateNorm(
              argThat(
                  argument ->
                      LocalDate.parse("1980-01-01")
                              .equals(
                                  argument
                                      .norm()
                                      .getTimeBoundaries()
                                      .getFirst()
                                      .getEventRef()
                                      .getDate()
                                      .get())
                          && LocalDate.parse("1990-01-01")
                              .equals(
                                  argument
                                      .norm()
                                      .getTimeBoundaries()
                                      .get(1)
                                      .getEventRef()
                                      .getDate()
                                      .get())
                          && argument.norm().getTimeBoundaries().size() == 2));
    }

    @Test
    void itChangesDateEIdNotFound() {
      MemoryAppender memoryAppender;
      Logger logger = (Logger) LoggerFactory.getLogger(TimeBoundaryService.class);
      memoryAppender = new MemoryAppender();
      memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
      logger.setLevel(Level.ALL);
      logger.addAppender(memoryAppender);
      memoryAppender.start();

      String eli = "eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1";

      var xml =
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
                                  <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="9ccda9b8-b213-43c5-8ee0-ec47c3c602bb" date="2024-03-13"
                                      source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                               </akn:lifecycle>
                               <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                     </akn:temporalGroup>
                                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f550bb38-e322-48ac-acf6-6b53e2e174b8">
                                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="6ea11eeb-31d2-47a5-9cac-7a31a14b86d1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                                     </akn:temporalGroup>
                               </akn:temporalData>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                      """;

      var normBefore = Norm.builder().document(XmlMapper.toDocument(xml)).build();

      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(normBefore));

      // When
      var timeBoundaryChangeDataNewDate1 =
          new TimeBoundaryChangeData(
              "meta-1_lebzykl-1_ereignis-1000", LocalDate.parse("1970-01-01"));

      service.updateTimeBoundariesOfNorm(
          new UpdateTimeBoundariesUseCase.Query(eli, List.of(timeBoundaryChangeDataNewDate1)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1)).updateNorm(any());

      assertThat(
              memoryAppender.contains(
                  "The following time boundaries should be changed but the eId was not found: [TimeBoundaryChangeData[eid=meta-1_lebzykl-1_ereignis-1000, date=1970-01-01]]",
                  Level.ERROR))
          .isTrue();
    }
  }
}
