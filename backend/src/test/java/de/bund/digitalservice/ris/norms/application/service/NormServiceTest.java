package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticleXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateModsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
      assertThat(returnedNorm).isPresent().contains(norm);
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
      assertThat(xml).isPresent();
      assertThat(xml.get()).contains("eId=\"meta-1_ident-1_frbrexpression-1_frbrthis-1\"");
    }

    @Test
    void itCallsLoadNormAndReturnsEmptyIfNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      var xml = service.loadNormXml(new LoadNormXmlUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xml).isEmpty();
    }
  }

  @Nested
  class updateNormXml {

    @Test
    void itUpdatesXml() throws UpdateNormXmlUseCase.InvalidUpdateException {
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
      assertThat(result).isPresent();
      assertThat(result.get())
          .contains("Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes");
    }

    @Test
    void itReturnsEmptyIfNormDoesNotExist() throws UpdateNormXmlUseCase.InvalidUpdateException {
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
      var result = service.updateNormXml(new UpdateNormXmlUseCase.Query(eli, newXml));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(result).isEmpty();
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
      assertThat(thrown).isInstanceOf(UpdateNormXmlUseCase.InvalidUpdateException.class);
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
      assertThat(thrown).isInstanceOf(UpdateNormXmlUseCase.InvalidUpdateException.class);
    }
  }

  @Nested
  class loadArticlesXml {

    @Test
    void loadAllArticles() {
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
                                <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                         <!-- Artikel 1 : Hauptänderung -->
                                         <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                            Some Text
                                         </akn:article>

                                         <!-- Artikel 3: Geltungszeitregel-->
                                         <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                            More Text
                                         </akn:article>
                                      </akn:body>
                             </akn:act>
                          </akn:akomaNtoso>
                        """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var xmls =
          service.loadSpecificArticles(new LoadSpecificArticleXmlFromNormUseCase.Query(eli, null));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xmls).isNotEmpty();
      assertThat(xmls.getFirst()).contains("hauptteil-1_art-1");
      assertThat(xmls.get(1)).contains("hauptteil-1_art-3");
    }

    @Test
    void itCallsLoadNormAndReturnsEmptyIfNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      var xmls =
          service.loadSpecificArticles(
              new LoadSpecificArticleXmlFromNormUseCase.Query(eli, "geltungszeitregel"));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xmls).isEmpty();
    }
  }

  @Test
  void loadSpecificArticles() {
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
                                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                                       <!-- Artikel 1 : Hauptänderung -->
                                                       <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                                          Some Text
                                                       </akn:article>

                                                       <!-- Artikel 3: Geltungszeitregel-->
                                                       <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                                          More Text
                                                       </akn:article>
                                                    </akn:body>
                                           </akn:act>
                                        </akn:akomaNtoso>
                                      """))
            .build();
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

    // When
    var xmls =
        service.loadSpecificArticles(
            new LoadSpecificArticleXmlFromNormUseCase.Query(eli, "geltungszeitregel"));

    // Then
    verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    assertThat(xmls).isNotEmpty();
    assertThat(xmls.getFirst()).contains("hauptteil-1_art-3");
  }

  @Nested
  class updateMods {
    @Test
    void itCallsLoadNormAndReturnsEmptyBecauseEliNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      var result =
          service.updateMods(
              new UpdateModsUseCase.Query(
                  eli,
                  Map.of(
                      "eid",
                      new UpdateModsUseCase.NewModData(
                          "refersTo", "time-boundary-eid", "destinanation-href", "new text"))));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(result).isEmpty();
    }

    @Test
    void itReturnsEmptyBecauseDestinationHrefIsRelative() {
      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      String eli = amendingLaw.getEli();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // When
      var result =
          service.updateMods(
              new UpdateModsUseCase.Query(
                  eli,
                  Map.of(
                      "eid",
                      new UpdateModsUseCase.NewModData(
                          "refersTo",
                          "time-boundary-eid",
                          "#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE",
                          "new text"))));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(result).isEmpty();
    }

    @Test
    void itThrowsWhenSecondTargetNormDiffers() {
      // Given
      Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadNormPort.loadNorm(any()))
          .thenReturn(Optional.of(amendingLaw))
          .thenReturn(Optional.of(targetLaw));
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);

      // When
      var query =
          new UpdateModsUseCase.Query(
              amendingLaw.getEli(),
              Map.of(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
                  new UpdateModsUseCase.NewModData(
                      "refersTo", "time-boundary-eid", "target-norm-eli-1", "new text"),
                  "eid-2",
                  new UpdateModsUseCase.NewModData(
                      "refersTo", "time-boundary-eid", "target-norm-eli-2", "new text")));
      assertThrows(IllegalArgumentException.class, () -> service.updateMods(query));

      // Then
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
      String newText = "new text";
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
              Map.of(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1", // <-
                  new UpdateModsUseCase.NewModData(
                      // this
                      // matters now
                      "refersTo",
                      newTimeBoundaryEid, // <- this will be set
                      newDestinationHref, // <- this will be set in ActivMods AND mod
                      newText)),
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
      String newText = "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3";
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
                  Map.of(
                      eId,
                      new UpdateModsUseCase.NewModData(
                          "refersTo",
                          newTimeBoundaryEid, // <- this will be set
                          newDestinationHref, // <- this will be set in ActivMods AND mod
                          newText)),
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

      assertThat(result).isPresent();
      final Document amendingXmlDocument = XmlMapper.toDocument(result.get().amendingNormXml());
      final Norm resultAmendingNorm = Norm.builder().document(amendingXmlDocument).build();

      final Mod mod = resultAmendingNorm.getMods().getFirst();
      assertThat(mod.getTargetHref()).isPresent();
      assertThat(mod.getTargetHref().get().value()).contains(newDestinationHref);
      assertThat(mod.getNewText()).contains(newText);
      assertThat(result.get().targetNormZf0Xml())
          .isEqualTo(XmlMapper.toString(zf0Norm.getDocument()));
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
                  Map.of(
                      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
                      new UpdateModsUseCase.NewModData(
                          "aenderungsbefehl-ersetzen",
                          "#meta-1_geltzeiten-1_geltungszeitgr-1",
                          "eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_para-1_abs-2_inhalt-1_text-1/29-36.xml",
                          "2. Ding"),
                      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1",
                      new UpdateModsUseCase.NewModData(
                          "aenderungsbefehl-ersetzen",
                          "#meta-1_geltzeiten-1_geltungszeitgr-1",
                          "eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_para-1_abs-3_inhalt-1_text-1/29-36.xml",
                          "3. Ding")),
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

      assertThat(result).isPresent();
    }
  }
}
