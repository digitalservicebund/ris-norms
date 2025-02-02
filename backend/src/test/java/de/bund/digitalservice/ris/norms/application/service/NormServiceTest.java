package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class NormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final SingleModValidator singleModValidator = mock(SingleModValidator.class);
  final UpdateNormService updateNormService = mock(UpdateNormService.class);
  final TimeMachineService timeMachineService = mock(TimeMachineService.class);
  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);

  final NormService service = new NormService(
    loadNormPort,
    updateNormPort,
    singleModValidator,
    updateNormService,
    timeMachineService,
    loadRegelungstextPort
  );

  @Nested
  class loadNorm {

    @Test
    void itCallsLoadNormAndReturnsNorm() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");

      var norm = Norm
        .builder()
        .dokumente(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                  <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                     <akn:act name="regelungstext">
                        <!-- Metadaten -->
                        <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                              </akn:FRBRExpression>
                          </akn:identification>
                        </akn:meta>
                     </akn:act>
                  </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var returnedNorm = service.loadNorm(new LoadNormUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(returnedNorm).isEqualTo(norm);
    }

    @Test
    void itThrowsWhenNormIsNotFound() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
      var query = new LoadNormUseCase.Query(eli);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadNorm(query))
        // Then
        .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }
  }

  @Nested
  class loadRegelungstext {

    @Test
    void itReturnsRegelungstext() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var regelungstext = new Regelungstext(
        XmlMapper.toDocument(
          """
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
               </akn:act>
            </akn:akomaNtoso>
          """
        )
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var returnedRegelungstext = service.loadRegelungstext(
        new LoadRegelungstextUseCase.Query(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(returnedRegelungstext).isEqualTo(regelungstext);
    }

    @Test
    void itThrowsWhenNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadRegelungstextUseCase.Query(eli);
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadRegelungstext(query))
        // Then
        .isInstanceOf(RegelungstextNotFoundException.class);

      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }
  }

  @Nested
  class loadRegelungstextXml {

    @Test
    void itCallsLoadRegelungstextAndReturnsXml() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var regelungstext = new Regelungstext(
        XmlMapper.toDocument(
          """
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
               </akn:act>
            </akn:akomaNtoso>
          """
        )
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var xml = service.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Query(eli));

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xml).contains("eId=\"meta-1_ident-1_frbrexpression-1_frbrthis-1\"");
    }

    @Test
    void itCallsLoadRegelungstextAndThrowsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());
      var query = new LoadRegelungstextXmlUseCase.Query(eli);

      // When
      assertThatThrownBy(() -> service.loadRegelungstextXml(query))
        // then
        .isInstanceOf(RegelungstextNotFoundException.class);
    }
  }

  @Nested
  class updateRegelungstextXml {

    @Test
    void itUpdatesXml() throws InvalidUpdateException {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var oldXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
      var newXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
      var oldNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(oldXml))))
        .build();
      var newNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(newXml))))
        .build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(newNorm));

      // When
      var result = service.updateRegelungstextXml(
        new UpdateRegelungstextXmlUseCase.Query(eli, newXml)
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(1))
        .updateNorm(argThat(argument -> argument.norm().equals(newNorm)));
      assertThat(result)
        .contains("Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes");
    }

    @Test
    void itThrowsNormNotFoundIfNormDoesNotExist() throws InvalidUpdateException {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var newXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                    </akn:FRBRExpression>
                </akn:identification>
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

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      var query = new UpdateRegelungstextXmlUseCase.Query(eli, newXml);

      // When
      assertThatThrownBy(() -> service.updateRegelungstextXml(query))
        // then
        .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
    }

    @Test
    void itThrowsIfEliChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var oldXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                    </akn:FRBRExpression>
                </akn:identification>
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
      var newXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/2000-01-01/1/deu/regelungstext-1" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                    </akn:FRBRExpression>
                </akn:identification>
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
      var oldNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(oldXml))))
        .build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Query(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }

    @Test
    void itThrowsIfGuidChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var oldXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                    </akn:FRBRExpression>
                </akn:identification>
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
      var newXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                    </akn:FRBRExpression>
                </akn:identification>
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
      var oldNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(oldXml))))
        .build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Query(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }
  }

  @Nested
  class updateNorm {

    @Test
    void itSavesANorm() {
      // given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      Norm targetNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      NormExpressionEli zf0EliTargetNorm = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(targetNorm));
      when(updateNormPort.updateNorm(new UpdateNormPort.Command(amendingNorm)))
        .thenReturn(Optional.of(amendingNorm));
      when(updateNormPort.updateNorm(new UpdateNormPort.Command(targetNorm)))
        .thenReturn(Optional.of(targetNorm));
      when(
        updateNormService.updateOnePassiveModification(
          new UpdatePassiveModificationsUseCase.Query(targetNorm, amendingNorm, zf0EliTargetNorm)
        )
      )
        .thenReturn(targetNorm);

      // when
      service.updateNorm(amendingNorm);

      // then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), zf0EliTargetNorm)));
      verify(updateNormPort, times(1))
        .updateNorm(
          argThat(argument -> Objects.equals(argument, new UpdateNormPort.Command(amendingNorm)))
        );
      verify(updateNormPort, times(1))
        .updateNorm(
          argThat(argument -> Objects.equals(argument, new UpdateNormPort.Command(targetNorm)))
        );
      verify(updateNormService, times(1))
        .updateOnePassiveModification(
          argThat(argument ->
            Objects.equals(argument.zf0Norm(), targetNorm) &&
            Objects.equals(argument.amendingNorm(), amendingNorm) &&
            Objects.equals(argument.targetNormEli(), zf0EliTargetNorm)
          )
        );
    }

    @Test
    void itThrowsAnExceptionIfNormIsNotFound() {
      // given
      String text =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                          xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
              <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
                      <akn:analysis eId="meta-1_analysis-1"
                                    GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1"
                                    source="attributsemantik-noch-undefiniert">
                          <akn:activeModifications eId="meta-1_analysis-1_activemod-1"
                                                   GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                              <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1"
                                              GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46"
                                              type="substitution">
                                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                                              GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                                              href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
                                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                                                   GUID="94c1e417-e849-4269-8320-9f0173b39626"
                                                   href="eli/bund/bgbl-1/2000/s111/2000-08-05/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml"/>
                                  <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                                             GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
                                             period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                              </akn:textualMod>
                              <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1"
                                              GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46"
                                              type="substitution">
                                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                                              GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                                              href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
                                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                                                   GUID="94c1e417-e849-4269-8320-9f0173b39626"
                                                   href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml"/>
                                  <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                                             GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
                                             period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                              </akn:textualMod>
                          </akn:activeModifications>
                      </akn:analysis>
                  </akn:meta>
              </akn:act>
          </akn:akomaNtoso>
        """;
      Norm amendingNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(text))))
        .build();
      amendingNorm.getMeta().getAnalysis().get().getActiveModifications();
      Norm targetNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      NormExpressionEli zf0EliTargetNorm = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      NormExpressionEli zf0EliNonExistent = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s111/2000-08-05/1/deu"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Command(zf0EliTargetNorm)))
        .thenReturn(Optional.of(targetNorm));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(zf0EliNonExistent)))
        .thenReturn(Optional.empty());
      when(updateNormPort.updateNorm(new UpdateNormPort.Command(amendingNorm)))
        .thenReturn(Optional.of(amendingNorm));
      when(updateNormPort.updateNorm(new UpdateNormPort.Command(targetNorm)))
        .thenReturn(Optional.of(targetNorm));
      when(
        updateNormService.updateOnePassiveModification(
          new UpdatePassiveModificationsUseCase.Query(targetNorm, amendingNorm, zf0EliTargetNorm)
        )
      )
        .thenReturn(targetNorm);

      // when
      Throwable thrown = catchThrowable(() -> {
        service.updateNorm(amendingNorm);
      });

      // then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), zf0EliNonExistent)));
      verify(updateNormPort, never())
        .updateNorm(
          argThat(argument -> Objects.equals(argument, new UpdateNormPort.Command(amendingNorm)))
        );
      verify(updateNormPort, never())
        .updateNorm(
          argThat(argument -> Objects.equals(argument, new UpdateNormPort.Command(targetNorm)))
        );
      verify(updateNormService, never())
        .updateOnePassiveModification(
          argThat(argument ->
            Objects.equals(argument.zf0Norm(), targetNorm) &&
            Objects.equals(argument.amendingNorm(), amendingNorm) &&
            Objects.equals(argument.targetNormEli(), zf0EliTargetNorm)
          )
        );
      assertThat(thrown).isInstanceOf(NormNotFoundException.class);
    }
  }

  @Nested
  class UpdateMod {

    @Test
    void itCallsLoadNormAndThrowsNormNotFoundBecauseEliNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // when/than
      var throwable = AssertionsForClassTypes.catchThrowable(() ->
        service.updateMod(
          new UpdateModUseCase.Query(
            eli,
            "eid",
            "refersTo",
            "time-boundary-eid",
            new Href("destinanation-href"),
            null,
            "new text"
          )
        )
      );

      assertThat(throwable).isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
    }

    @Test
    void itThrowsValidationExceptionBecauseInputDestinationHrefWithoutEli() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      final Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when/than
      final UpdateModUseCase.Query query = new UpdateModUseCase.Query(
        eli,
        "eid",
        "refersTo",
        "time-boundary-eid",
        new Href("#destinanation-href"),
        null,
        "new text"
      );
      assertThatThrownBy(() -> service.updateMod(query))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "The destinationHref with value #destinanation-href does not contain a eli"
        );
    }

    @Test
    void itThrowsValidationExceptionBecauseMetaModNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      final Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      final Norm targetNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      final Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(targetNorm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);

      // when/than
      final UpdateModUseCase.Query query = new UpdateModUseCase.Query(
        eli,
        "eid",
        "refersTo",
        "time-boundary-eid",
        new Href("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"),
        null,
        "new text"
      );
      assertThatThrownBy(() -> service.updateMod(query))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "Did not find a textual mod with eId eid in the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
        );
    }

    @Test
    void itCallsTimeMachineBeforeValidator() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String newCharacterRange = "20-25";
      String newTimeBoundaryEid = "#time-boundary-eid";
      Href newDestinationHref = new Href.Builder()
        .setEli(zf0Norm.getRegelungstext1ExpressionEli())
        .setEId("hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange(newCharacterRange))
        .setFileExtension("xml")
        .buildAbsolute();
      String newContent = "new text";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(zf0Norm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      service.updateMod(
        new UpdateModUseCase.Query(
          amendingNormEli,
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1", // <-
          // this
          // matters now
          "refersTo",
          newTimeBoundaryEid, // <- this will be set
          newDestinationHref, // <- this will be set in ActivMods AND mod
          null,
          newContent,
          false
        )
      );

      // Then
      verify(timeMachineService, times(1))
        .applyPassiveModifications(
          argThat(argument ->
            Objects.equals(argument.regelungstext(), zf0Norm.getRegelungstext1()) &&
            Objects.equals(argument.date(), Instant.MIN)
          )
        );
    }

    @Test
    void itCallsTheValidator() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Mod mod = amendingNorm
        .getMods()
        .stream()
        .filter(m ->
          m
            .getEid()
            .equals("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1")
        )
        .findFirst()
        .orElseThrow();
      Norm targetNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      DokumentExpressionEli targetNormEli = targetNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String newCharacterRange = "20-25";
      String newTimeBoundaryEid = "#time-boundary-eid";
      Href newDestinationHref = new Href.Builder()
        .setEli(targetNormEli)
        .setEId("hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange(newCharacterRange))
        .setFileExtension("xml")
        .buildAbsolute();
      String newContent = "new text";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(targetNorm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(timeMachineService.applyPassiveModifications(any()))
        .thenReturn(zf0Norm.getRegelungstext1());

      // When
      service.updateMod(
        new UpdateModUseCase.Query(
          amendingNormEli,
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1", // <-
          // this
          // matters now
          "refersTo",
          newTimeBoundaryEid, // <- this will be set
          newDestinationHref, // <- this will be set in ActivMods AND mod
          null,
          newContent,
          false
        )
      );

      // Then
      verify(singleModValidator, times(1))
        .validate(
          argThat(zf0NormArg -> zf0NormArg.equals(zf0Norm.getRegelungstext1())),
          argThat(m -> m.equals(mod))
        );
    }

    @Test
    void itCallsAllUpdateServices() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      String newCharacterRange = "9-34";
      String newTimeBoundaryEid = "#time-boundary-eid";
      Href newDestinationHref = new Href.Builder()
        .setEli(zf0Norm.getRegelungstext1ExpressionEli())
        .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange(newCharacterRange))
        .setFileExtension("xml")
        .buildAbsolute();
      String newContent = "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(zf0Norm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      var returnedXml = service.updateMod(
        new UpdateModUseCase.Query(
          amendingNormEli,
          eId,
          "refersTo",
          newTimeBoundaryEid, // <- this will be set
          newDestinationHref, // <- this will be set in ActivMods AND mod
          null,
          newContent,
          false
        )
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli.asNormEli())));
      verify(loadNormPort, times(2))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), zf0Norm.getExpressionEli())));
      verify(updateNormService, times(1))
        .updateOneActiveModification(
          argThat(argument ->
            Objects.equals(argument.amendingNorm(), amendingNorm) &&
            Objects.equals(argument.eId(), eId) &&
            Objects.equals(argument.timeBoundaryEid(), newTimeBoundaryEid) &&
            Objects.equals(argument.destinationHref(), newDestinationHref)
          )
        );
      verify(updateNormService, times(2))
        .updateOnePassiveModification(
          argThat(argument ->
            Objects.equals(argument.zf0Norm(), zf0Norm) &&
            Objects.equals(argument.amendingNorm(), amendingNorm)
          )
        );
      verify(updateNormPort, times(1))
        .updateNorm(argThat(argument -> Objects.equals(argument.norm(), amendingNorm)));
      verify(updateNormPort, times(1))
        .updateNorm(argThat(argument -> Objects.equals(argument.norm(), zf0Norm)));

      final Document amendingXmlDocument = XmlMapper.toDocument(returnedXml.amendingNormXml());
      final Norm resultAmendingNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(amendingXmlDocument)))
        .build();

      final Mod mod = resultAmendingNorm.getMods().getFirst();
      assertThat(mod.getTargetRefHref()).isPresent();
      assertThat(mod.getTargetRefHref()).contains(newDestinationHref);
      assertThat(mod.getNewText()).contains(newContent);
      assertThat(returnedXml.targetNormZf0Xml())
        .isEqualTo(XmlMapper.toString(zf0Norm.getDocument()));
    }
  }

  @Nested
  class updateMods {

    @Test
    void itCallsTheTimeMachineBeforeValidator() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(zf0Norm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      service.updateMods(
        new UpdateModsUseCase.Query(
          amendingNormEli,
          List.of(
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
              newTimeBoundaryEid
            )
          ),
          false
        )
      );

      // Then
      verify(timeMachineService, times(1))
        .applyPassiveModifications(
          argThat(argument ->
            Objects.equals(argument.regelungstext(), zf0Norm.getRegelungstext1()) &&
            Objects.equals(argument.date(), Instant.MIN)
          )
        );
    }

    @Test
    void itCallsTheValidator() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Mod mod = amendingNorm
        .getMods()
        .stream()
        .filter(m ->
          m
            .getEid()
            .equals("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1")
        )
        .findFirst()
        .orElseThrow();
      Norm targetNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(targetNorm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(timeMachineService.applyPassiveModifications(any()))
        .thenReturn(zf0Norm.getRegelungstext1());

      // When
      service.updateMods(
        new UpdateModsUseCase.Query(
          amendingNormEli,
          List.of(
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
              newTimeBoundaryEid
            )
          ),
          false
        )
      );

      // Then
      verify(singleModValidator, times(1))
        .validate(
          argThat(zf0NormArg -> zf0NormArg.equals(zf0Norm.getRegelungstext1())),
          argThat(m -> m.equals(mod))
        );
    }

    @Test
    void itCallsAllUpdateServices() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(zf0Norm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      var result = service.updateMods(
        new UpdateModsUseCase.Query(
          amendingNormEli,
          List.of(new UpdateModsUseCase.NewModData(eId, newTimeBoundaryEid)),
          false
        )
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli.asNormEli())));
      verify(loadNormPort, times(2))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), zf0Norm.getExpressionEli())));
      verify(updateNormService, times(1))
        .updateOneActiveModification(
          argThat(argument ->
            Objects.equals(argument.amendingNorm(), amendingNorm) &&
            Objects.equals(argument.eId(), eId) &&
            Objects.equals(argument.timeBoundaryEid(), newTimeBoundaryEid)
          )
        );
      verify(updateNormService, times(2))
        .updateOnePassiveModification(
          argThat(argument ->
            Objects.equals(argument.zf0Norm(), zf0Norm) &&
            Objects.equals(argument.amendingNorm(), amendingNorm)
          )
        );
      verify(updateNormPort, times(1))
        .updateNorm(argThat(argument -> Objects.equals(argument.norm(), amendingNorm)));

      final Document amendingXmlDocument = XmlMapper.toDocument(result.amendingNormXml());
      final Norm resultAmendingNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(amendingXmlDocument)))
        .build();

      final Mod mod = resultAmendingNorm.getMods().getFirst();
      assertThat(mod.getTargetRefHref()).isPresent();
      assertThat(mod.getTargetRefHref().get().value())
        .contains(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml"
        );
      assertThat(mod.getNewText()).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
      assertThat(result.targetNormZf0Xml()).isEqualTo(XmlMapper.toString(zf0Norm.getDocument()));
    }

    @Test
    void itUpdatesTheSameZf0IfMultipleModsModifyIt() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMultipleSimpleMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm targetNorm = Fixtures.loadNormFromDisk("NormWithMultipleSimpleModsTargetNorm.xml");
      DokumentExpressionEli targetNormEli = targetNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithMultipleSimpleModsTargetNorm.xml");

      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(targetNorm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      service.updateMods(
        new UpdateModsUseCase.Query(
          amendingNormEli,
          List.of(
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
              "#meta-1_geltzeiten-1_geltungszeitgr-1"
            ),
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
              "#meta-1_geltzeiten-1_geltungszeitgr-1"
            )
          ),
          false
        )
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli.asNormEli())));
      verify(loadNormPort, times(2))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli.asNormEli())));
      verify(updateNormService, times(2)).updateOneActiveModification(any());
      verify(updateNormService, times(3)).updateOnePassiveModification(any());
      verify(updateNormPort, times(2)).updateNorm(any());
    }

    @Test
    void itUpdatesUsingRref() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      Norm targetNorm = Fixtures.loadNormFromDisk(
        "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
      );
      DokumentExpressionEli targetNormEli = targetNorm.getRegelungstext1ExpressionEli();
      Norm zf0Norm = Fixtures.loadNormFromDisk("NormWithPassiveModsQuotedStructureAndUpTo.xml");

      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(amendingNorm))
        .thenReturn(Optional.of(targetNorm));
      when(updateNormService.updateOneActiveModification(any())).thenReturn(amendingNorm);
      when(updateNormService.updateOnePassiveModification(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));

      // When
      service.updateMods(
        new UpdateModsUseCase.Query(
          amendingNormEli,
          List.of(
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
              "#meta-1_geltzeiten-1_geltungszeitgr-2"
            ),
            new UpdateModsUseCase.NewModData(
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
              "#meta-1_geltzeiten-1_geltungszeitgr-2"
            )
          ),
          false
        )
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli.asNormEli())));
      verify(loadNormPort, times(2))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli.asNormEli())));
      verify(updateNormService, times(2)).updateOneActiveModification(any());
      verify(updateNormService, times(3)).updateOnePassiveModification(any());
      verify(updateNormPort, times(2)).updateNorm(any());
    }

    @Test
    void itThrowsNormNotFoundExceptionWhenAmendingLawCanNotBeLoaded() {
      // Given
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // when
      UpdateModsUseCase.Query query = new UpdateModsUseCase.Query(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/3000/s1/3000-01-01/1/deu/regelungstext-1"
        ),
        List.of(new UpdateModsUseCase.NewModData("not-relevant", "not-relevant")),
        false
      );

      assertThatThrownBy(() -> service.updateMods(query))
        // Then
        .isInstanceOf(NormNotFoundException.class)
        .hasMessageContaining(
          "Norm with eli eli/bund/bgbl-1/3000/s1/3000-01-01/1/deu/regelungstext-1 does not exist"
        );
    }

    @Test
    void itThrowsInvalidUpdateExceptionWhenModWithGivenEidNotFound() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      UpdateModsUseCase.Query query = new UpdateModsUseCase.Query(
        amendingNormEli,
        List.of(new UpdateModsUseCase.NewModData("eid-that-does-not-exist", newTimeBoundaryEid)),
        false
      );

      assertThatThrownBy(() -> service.updateMods(query))
        // Then
        .isInstanceOf(InvalidUpdateException.class)
        .hasMessageContaining(
          "Mod with eId eid-that-does-not-exist not found in amending law eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
        );
    }

    @Test
    void itThrowsInvalidUpdateExceptionWhenModHrefDoesNotContainEli() {
      // Given
      Norm amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      String eid = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1";
      amendingNorm
        .getMods()
        .stream()
        .filter(mod -> mod.getEid().equals(eid))
        .findFirst()
        .ifPresent(m -> m.setTargetRefHref(new Href("#fake-href")));

      DokumentExpressionEli amendingNormEli = amendingNorm.getRegelungstext1ExpressionEli();
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      UpdateModsUseCase.Query query = new UpdateModsUseCase.Query(
        amendingNormEli,
        List.of(new UpdateModsUseCase.NewModData(eid, newTimeBoundaryEid)),
        false
      );

      assertThatThrownBy(() -> service.updateMods(query))
        // Then
        .isInstanceOf(InvalidUpdateException.class)
        .hasMessageContaining(
          "No eli found in href of mod hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        );
    }
  }
}
