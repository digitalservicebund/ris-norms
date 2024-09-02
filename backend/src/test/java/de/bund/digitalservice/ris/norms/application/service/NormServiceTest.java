package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class NormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final SingleModValidator singleModValidator = mock(SingleModValidator.class);
  final UpdateNormService updateNormService = mock(UpdateNormService.class);
  final LoadZf0Service loadZf0Service = mock(LoadZf0Service.class);
  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final NormService service =
      new NormService(
          loadNormPort,
          updateNormPort,
          singleModValidator,
          updateNormService,
          loadZf0Service,
          updateOrSaveNormPort);

  @Nested
  class loadNorm {

    @Test
    void itCallsLoadNormAndReturnsNorm() {
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
                                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                        </akn:FRBRExpression>
                                    </akn:identification>
                                  </akn:meta>
                               </akn:act>
                            </akn:akomaNtoso>
                          """))
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
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
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
  class loadNormXml {

    @Test
    void itCallsLoadNormAndReturnsXml() {
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
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
               </akn:act>
            </akn:akomaNtoso>
          """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var xml = service.loadNormXml(new LoadNormXmlUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xml).contains("eId=\"meta-1_ident-1_frbrexpression-1_frbrthis-1\"");
    }

    @Test
    void itCallsLoadNormAndThrowsIfNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadNormXml(new LoadNormXmlUseCase.Query(eli)))

          // then
          .isInstanceOf(NormNotFoundException.class);
    }
  }

  @Nested
  class updateNormXml {

    @Test
    void itUpdatesXml() throws InvalidUpdateException {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

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
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
      var oldNorm = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();
      var newNorm = Norm.builder().document(XmlMapper.toDocument(newXml)).build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(newNorm));

      // When
      var result = service.updateNormXml(new UpdateNormXmlUseCase.Query(eli, newXml));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(1))
          .updateNorm(argThat(argument -> Objects.equals(argument.norm(), newNorm)));
      assertThat(result)
          .contains("Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes");
    }

    @Test
    void itThrowsNormNotFoundIfNormDoesNotExist() throws InvalidUpdateException {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var newXml =
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

      // When
      assertThatThrownBy(() -> service.updateNormXml(new UpdateNormXmlUseCase.Query(eli, newXml)))

          // then
          .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
    }

    @Test
    void itThrowsIfEliChanges() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

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
      var oldNorm = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown =
          catchThrowable(() -> service.updateNormXml(new UpdateNormXmlUseCase.Query(eli, newXml)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }

    @Test
    void itThrowsIfGuidChanges() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

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
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
      var oldNorm = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown =
          catchThrowable(() -> service.updateNormXml(new UpdateNormXmlUseCase.Query(eli, newXml)));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }
  }

  @Nested
  class UpdateMod {

    @Test
    void itCallsLoadNormAndThrowsNormNotFoundBecauseEliNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // when/than
      var throwable =
          AssertionsForClassTypes.catchThrowable(
              () ->
                  service.updateMod(
                      new UpdateModUseCase.Query(
                          eli,
                          "eid",
                          "refersTo",
                          "time-boundary-eid",
                          "destinanation-href",
                          null,
                          "new text")));

      assertThat(throwable).isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
    }

    @Test
    void itCallsTheValidator() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      String amendingNormEli = amendingNorm.getEli();
      Mod mod =
          amendingNorm.getMods().stream()
              .filter(
                  m ->
                      m.getEid().isPresent()
                          && m.getEid()
                              .get()
                              .equals(
                                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"))
              .findFirst()
              .orElseThrow();
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      String targetNormEli = targetNorm.getEli();
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      String newCharacterRange = "20-25";
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newDestinationHref =
          targetNormEli
              + "/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
              + newCharacterRange
              + ".xml";
      String newContent = "new text";
      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      service.updateMod(
          new UpdateModUseCase.Query(
              amendingNormEli,
              "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1", // <-
              // this
              // matters now
              "refersTo",
              newTimeBoundaryEid, // <- this will be set
              newDestinationHref, // <- this will be set in ActivMods AND mod
              null,
              newContent,
              false));

      // Then
      verify(singleModValidator, times(1))
          .validate(argThat(zf0NormArg -> zf0NormArg.equals(zf0Norm)), argThat(m -> m.equals(mod)));
    }

    @Test
    void itCallsAllUpdateServices() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      String amendingNormEli = amendingNorm.getEli();
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      String targetNormEli = targetNorm.getEli();
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1";
      String newCharacterRange = "9-34";
      String newTimeBoundaryEid = "#time-boundary-eid";
      String newDestinationHref =
          targetNormEli
              + "/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/"
              + newCharacterRange
              + ".xml";
      String newContent = "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3";
      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      var returnedXml =
          service.updateMod(
              new UpdateModUseCase.Query(
                  amendingNormEli,
                  eId,
                  "refersTo",
                  newTimeBoundaryEid, // <- this will be set
                  newDestinationHref, // <- this will be set in ActivMods AND mod
                  null,
                  newContent,
                  false));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli)));
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli)));
      verify(loadZf0Service, times(1)).loadOrCreateZf0(any());
      verify(updateNormService, times(1))
          .updateActiveModifications(
              argThat(
                  argument ->
                      Objects.equals(argument.amendingNorm(), amendingNorm)
                          && Objects.equals(argument.eId(), eId)
                          && Objects.equals(argument.timeBoundaryEid(), newTimeBoundaryEid)
                          && Objects.equals(argument.destinationHref(), newDestinationHref)));
      verify(updateNormService, times(1))
          .updatePassiveModifications(
              argThat(
                  argument ->
                      Objects.equals(argument.zf0Norm(), zf0Norm)
                          && Objects.equals(argument.amendingNorm(), amendingNorm)));
      verify(updateNormPort, times(1))
          .updateNorm(argThat(argument -> Objects.equals(argument.norm(), amendingNorm)));
      verify(updateOrSaveNormPort, times(1))
          .updateOrSave(argThat(argument -> Objects.equals(argument.norm(), zf0Norm)));

      // TOOD: what to check instead?
      //   assertThat(returnedXml).isPresent();
      final Document amendingXmlDocument = XmlMapper.toDocument(returnedXml.amendingNormXml());
      final Norm resultAmendingNorm = Norm.builder().document(amendingXmlDocument).build();

      final Mod mod = resultAmendingNorm.getMods().getFirst();
      assertThat(mod.getTargetRefHref()).isPresent();
      assertThat(mod.getTargetRefHref().get().value()).contains(newDestinationHref);
      assertThat(mod.getNewText()).contains(newContent);
      assertThat(returnedXml.targetNormZf0Xml())
          .isEqualTo(XmlMapper.toString(zf0Norm.getDocument()));
    }
  }

  @Nested
  class updateMods {

    @Test
    void itCallsTheValidator() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      String amendingNormEli = amendingNorm.getEli();
      Mod mod =
          amendingNorm.getMods().stream()
              .filter(
                  m ->
                      m.getEid().isPresent()
                          && m.getEid()
                              .get()
                              .equals(
                                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"))
              .findFirst()
              .orElseThrow();
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      service.updateMods(
          new UpdateModsUseCase.Query(
              amendingNormEli,
              List.of(
                  new UpdateModsUseCase.NewModData(
                      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
                      newTimeBoundaryEid)),
              false));

      // Then
      verify(singleModValidator, times(1))
          .validate(argThat(zf0NormArg -> zf0NormArg.equals(zf0Norm)), argThat(m -> m.equals(mod)));
    }

    @Test
    void itCallsAllUpdateServices() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      String amendingNormEli = amendingNorm.getEli();
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      String targetNormEli = targetNorm.getEli();
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      String eId = "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1";
      String newTimeBoundaryEid = "#time-boundary-eid";
      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      var result =
          service.updateMods(
              new UpdateModsUseCase.Query(
                  amendingNormEli,
                  List.of(new UpdateModsUseCase.NewModData(eId, newTimeBoundaryEid)),
                  false));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli)));
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli)));
      verify(loadZf0Service, times(1)).loadOrCreateZf0(any());
      verify(updateNormService, times(1))
          .updateActiveModifications(
              argThat(
                  argument ->
                      Objects.equals(argument.amendingNorm(), amendingNorm)
                          && Objects.equals(argument.eId(), eId)
                          && Objects.equals(argument.timeBoundaryEid(), newTimeBoundaryEid)));
      verify(updateNormService, times(1))
          .updatePassiveModifications(
              argThat(
                  argument ->
                      Objects.equals(argument.zf0Norm(), zf0Norm)
                          && Objects.equals(argument.amendingNorm(), amendingNorm)));
      verify(updateNormPort, times(1))
          .updateNorm(argThat(argument -> Objects.equals(argument.norm(), amendingNorm)));
      verify(updateOrSaveNormPort, times(1))
          .updateOrSave(argThat(argument -> Objects.equals(argument.norm(), zf0Norm)));

      // TODO: what to essert, here?
      //   assertThat(result).isPresent();
      final Document amendingXmlDocument = XmlMapper.toDocument(result.amendingNormXml());
      final Norm resultAmendingNorm = Norm.builder().document(amendingXmlDocument).build();

      final Mod mod = resultAmendingNorm.getMods().getFirst();
      assertThat(mod.getTargetRefHref()).isPresent();
      assertThat(mod.getTargetRefHref().get().value())
          .contains(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml");
      assertThat(mod.getNewText()).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
      assertThat(result.targetNormZf0Xml()).isEqualTo(XmlMapper.toString(zf0Norm.getDocument()));
    }

    @Test
    void itUpdatesTheSameZf0IfMultipleModsModifyIt() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMultipleSimpleMods.xml");
      String amendingNormEli = amendingNorm.getEli();
      Norm targetNorm = NormFixtures.loadFromDisk("NormWithMultipleSimpleModsTargetNorm.xml");
      String targetNormEli = targetNorm.getEli();
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithMultipleSimpleModsTargetNorm.xml");

      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      var result =
          service.updateMods(
              new UpdateModsUseCase.Query(
                  amendingNormEli,
                  List.of(
                      new UpdateModsUseCase.NewModData(
                          "hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
                          "#meta-1_geltzeiten-1_geltungszeitgr-1"),
                      new UpdateModsUseCase.NewModData(
                          "hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1",
                          "#meta-1_geltzeiten-1_geltungszeitgr-1")),
                  false));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli)));
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli)));
      verify(loadZf0Service, times(1)).loadOrCreateZf0(any());
      verify(updateNormService, times(2)).updateActiveModifications(any());
      verify(updateNormService, times(2)).updatePassiveModifications(any());
      verify(updateNormPort, times(1)).updateNorm(any());
      verify(updateOrSaveNormPort, times(1)).updateOrSave(any());

      // TODO: what to assert, here?
      //   assertThat(result).isPresent();
    }

    @Test
    void itUpdatesUsingRref() {
      // Given
      Norm amendingNorm = NormFixtures.loadFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
      String amendingNormEli = amendingNorm.getEli();
      Norm targetNorm =
          NormFixtures.loadFromDisk("NormWithoutPassiveModsQuotedStructureAndUpTo.xml");
      String targetNormEli = targetNorm.getEli();
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModsQuotedStructureAndUpTo.xml");

      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingNorm))
          .thenReturn(Optional.of(targetNorm));
      when(loadZf0Service.loadOrCreateZf0(any())).thenReturn(zf0Norm);
      when(updateNormService.updateActiveModifications(any())).thenReturn(amendingNorm);
      when(updateNormService.updatePassiveModifications(any())).thenReturn(zf0Norm);
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(zf0Norm);

      // When
      var result =
          service.updateMods(
              new UpdateModsUseCase.Query(
                  amendingNormEli,
                  List.of(
                      new UpdateModsUseCase.NewModData(
                          "hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
                          "#meta-1_geltzeiten-1_geltungszeitgr-2"),
                      new UpdateModsUseCase.NewModData(
                          "hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
                          "#meta-1_geltzeiten-1_geltungszeitgr-2")),
                  false));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), amendingNormEli)));
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), targetNormEli)));
      verify(loadZf0Service, times(1)).loadOrCreateZf0(any());
      verify(updateNormService, times(2)).updateActiveModifications(any());
      verify(updateNormService, times(2)).updatePassiveModifications(any());
      verify(updateNormPort, times(1)).updateNorm(any());
      verify(updateOrSaveNormPort, times(1)).updateOrSave(any());

      // TODO: what to assert here?
      //   assertThat(result).isPresent();
    }
  }
}
