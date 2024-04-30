package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Node;

class TimeMachineServiceTest {

  @Nested
  class applyPassiveMods {
    @Test
    void returnUnchangedIfNoPassiveMods() {
      // given
      final var xmlDocumentService = mock(XmlDocumentService.class);
      final var timeMachineService = new TimeMachineService(xmlDocumentService);

      final var norm =
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
                      <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                          <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                          <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                          <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                       </akn:FRBRWork>
                  </akn:identification>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
          """))
              .build();

      // when
      Norm result = timeMachineService.applyPassiveModifications(norm);

      // then
      assertThat(result).isEqualTo(norm);
    }

    @Test
    void applyOnePassiveModification() {
      // given
      final var xmlDocumentService = mock(XmlDocumentService.class);
      final var normService = mock(NormService.class);
      final var timeMachineService = new TimeMachineService(xmlDocumentService);

      final var norm =
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
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                              <akn:analysis eId="meta-1_analysis-1"
                       GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4"
                       source="attributsemantik-noch-undefiniert">
                                <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1"
                                                          GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                                   <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                                                   GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                                                   type="substitution">
                                      <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                                                  GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                                                  href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                                      <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                                       GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                                       href="#para-20_abs-1/100-126"/>
                                      <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                                                 GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                                                 period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                   </akn:textualMod>
                                 </akn:passiveModifications>
                               </akn:analysis>
                            </akn:meta>

                            <akn:body eId="hauptteil-1" GUID="6c5d6dc7-f14b-4e1d-8f8b-570a13aeff9c">
                      <!-- § 9 -->
                               <akn:article eId="hauptteil-1_para-20"
                      GUID="b1b4bd3b-e007-4d84-af83-b8e36a0ae50b"
                      period="#geltungszeitgr-1">
                                    <akn:num eId="hauptteil-1_para-20_bezeichnung-1"
                                             GUID="f82ab983-5498-49ab-918f-5cf5e730e5ec">
                                       <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1"
                                                   GUID="b4b8fb7e-bf8b-45b9-9118-fd797600d2fa"
                                                   name="20"/>§ 20</akn:num>
                                    <akn:paragraph eId="hauptteil-1_para-20_abs-1"
                                                   GUID="6aa3a7ca-f30a-43b6-950b-b1e942fd1842"
                                                   period="#geltungszeitgr-2">
                                       <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1"
                                                GUID="e363f12d-7918-435c-b3a1-182c5e03ff43">
                                          <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1"
                                                      GUID="e5d8cf58-1ed0-45e4-9450-254738249dda"
                                                      name="1"/>(1) </akn:num>
                                       <akn:list eId="hauptteil-1_para-20_abs-1_untergl-1"
                                                 GUID="97e930bf-49f8-472a-a1fa-3c3a401caa13">
                                          <akn:intro eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1"
                                                     GUID="e2261ee5-feda-4691-b1a0-8a18f43720e7">
                                             <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1_text-1"
                                                    GUID="f5b78960-3376-4f19-a1de-05e24443a141">Wer</akn:p>
                                          </akn:intro>
                                          <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1"
                                                     GUID="6f1b22e6-2fcc-4e29-b3c1-fd67d8cee45c">
                                             <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1"
                                                      GUID="7947817d-e127-49ca-82c3-a1dfeaa73748">
                                                <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"
                                                            GUID="aff56862-ab0e-4db9-add5-26cbaaaa40ef"
                                                            name="1"/>1. </akn:num>
                                             <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1"
                                                          GUID="fff000f9-bed8-41eb-b26b-93a5da40ff5f">
                                                <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"
                                                       GUID="6ad07a0f-46f6-45b5-8d1a-ac79b8f704fe">entgegen einem
                                                   vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als
                                                   Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
                                             </akn:content>
                                          </akn:point>
                                          <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2"
                                                     GUID="94af45e6-d49b-4bcc-84b9-5e5c01d5a3ba">
                                             <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                                                      GUID="18a496f3-c0e7-4fb3-a41d-5c9fd75374c7">
                                                <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                                            GUID="0e883c7b-3c72-4a41-a182-a44b26763e75"
                                                            name="2"/>2. </akn:num>
                                             <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1"
                                                          GUID="7e835e4b-52eb-4fa1-9698-d7a42589d715">
                                                <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                                                       GUID="0ba9a471-e9ef-44c4-b5da-f69f068a4483">entgegen § 9 Abs. 1 Satz 2, Abs. 2
                                                   Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
                                             </akn:content>
                                          </akn:point>
                                          <akn:wrapUp eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1"
                                                      GUID="107ed2fd-e041-4ee4-9eee-b559f84a4ce8">
                                             <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1_text-1"
                                                    GUID="53af6200-56c2-4fed-bd27-25ddefe7b204">wird mit Gefängnis bis zu
                                                einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit
                                                schwerer Strafe bedroht ist.</akn:p>
                                          </akn:wrapUp>
                                       </akn:list>
                                    </akn:paragraph>
                                  </akn:article>
                                </akn:body>

                         </akn:act>
                      </akn:akomaNtoso>
                      """))
              .build();

      var amendingLaw =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
          <?xml version="1.0" encoding="UTF-8"?>
<!--
	##################################################################################
	Projekt E-Gesetzgebung
	Nicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

          	Veröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
<!-- Metadaten -->
      <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
         <akn:identification eId="meta-1_ident-1"
                             GUID="be8ecb75-0f1a-4209-b3a4-17d55bdffb47"
                             source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1"
                          GUID="d4f77434-7c1f-4496-917a-262a82a7070c">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1"
                             GUID="ad47b5be-0012-447a-90db-71438b38650e"
                             value="eli/bund/bgbl-1/2017/s419/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            GUID="a739e012-fe1d-4411-91b8-58e0de76fc28"
                            value="eli/bund/bgbl-1/2017/s419"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              GUID="42ae272d-4b4a-4d25-9965-79e76c741b5b"
                              name="übergreifende-id"
                              value="c53036e4-14ac-420f-b01a-bae05a0a9756"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             GUID="525ff48c-a66e-45f6-b036-884935f7ba7d"
                             date="2017-03-15"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               GUID="27fa3047-26e1-4c59-8701-76dd34043d71"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                GUID="fa3d22d4-4f01-4486-9d45-c1edcf50729e"
                                value="de"/>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               GUID="565c2f06-c2c9-4a27-aeb3-ca34199ce08c"
                               value="s419"/>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1"
                             GUID="7219aecc-e1eb-49a1-abf5-bba8a8be721c"
                             value="bgbl-1"/>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                GUID="c5bc9d46-575f-4808-90e8-a354a227d701"
                                value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                GUID="4c69a6d2-8988-4581-bfa9-df9e8e24f321">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                             GUID="f3805314-bbb6-4def-b82b-8b7f0b126197"
                             value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            GUID="5a2c4542-56cc-4c70-8b80-e2041b5b75e1"
                            value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              GUID="6c99101d-6bca-41ae-9794-250bd096fead"
                              name="aktuelle-version-id"
                              value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
                              GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37"
                              name="nachfolgende-version-id"
                              value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               GUID="9063f5e7-c3c5-4ab4-8e15-459b11d7a9f2"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             GUID="1e8f33a8-d124-48c3-a864-7968701816ee"
                             date="2017-03-15"
                             name="verkuendung"/>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                 GUID="9c61581b-ce24-4589-8db8-533262149b90"
                                 language="deu"/>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1"
                                      GUID="de475d52-7263-4c05-8014-e92a7785b784"
                                      value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                   GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                             GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                             value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                            value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                             date="2022-08-23"
                             name="generierung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                               GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                               href="recht.bund.de"/>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                               GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                               value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>

      </akn:meta>

                <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
<!-- Artikel 1 : Hauptänderung -->
         <akn:article eId="hauptteil-1_art-1"
                      GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                      period="#geltungszeitgr-1"
                      refersTo="hauptaenderung">
            <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                     GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
               <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1"
                           GUID="81c9c481-9427-4f03-9f51-099aa9b2201e"
                           name="1"/>Artikel 1</akn:num>
            <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                         GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
            <!-- Absatz (1) -->
            <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                           GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
               <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1"
                        GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                  <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1"
                              GUID="eab5a7e7-b649-4c23-b495-648b8ec71843"
                              name="1"/>
               </akn:num>
               <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1"
                         GUID="41675622-ed62-46e3-869f-94d99908b010">
                  <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1"
                             GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                     <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1"
                            GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                              GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                              href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt
                           durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                  </akn:intro>

                  <!-- Nummer 2 -->
                  <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
                             GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                     <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                              GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                        <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                    GUID="5d7d54f0-8a4e-4d8f-b5d0-93d0ca393e82"
                                    name="2"/>2.</akn:num>
                     <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1"
                                  GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                        <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                               GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                           <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                                    GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                       GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird
                              die Angabe <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                              GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                              startQuote="„"
                                              endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die
                              Wörter <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                              GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                              startQuote="„"
                                              endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:quotedText>
                              ersetzt.</akn:mod>
                        </akn:p>
                     </akn:content>
                  </akn:point>
               </akn:list>
            </akn:paragraph>
         </akn:article>

                </akn:body>

             </akn:act>
</akn:akomaNtoso>

          """))
              .build();

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result = timeMachineService.applyPassiveModifications(norm);

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }
    // return unchanged if no passive mods pass the date filter
  }

  @Nested
  class applyAmendingLawOnTargetLaw {

    @Test
    void allRelevantMethodsAreCalled() {
      // given
      final XmlDocumentService xmlDocumentService = mock(XmlDocumentService.class);
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);

      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";
      String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";
      Node firstModificationNodeInAmendingLaw = mock(Node.class);
      Node targetNode = mock(Node.class);
      XmlDocumentService.ReplacementPair replacementPair =
          new XmlDocumentService.ReplacementPair("old", "new");

      when(xmlDocumentService.getFirstModification(any()))
          .thenReturn(firstModificationNodeInAmendingLaw);
      when(xmlDocumentService.getReplacementPair(any())).thenReturn(replacementPair);
      when(xmlDocumentService.findTargetLawNodeToBeModified(any(), any())).thenReturn(targetNode);
      when(targetNode.getTextContent()).thenReturn("old");

      // when
      timeMachineService.apply(amendingLawString, targetLawString);

      // then
      verify(xmlDocumentService, times(1)).getFirstModification(any());
      verify(xmlDocumentService, times(1)).getReplacementPair(any());
      verify(xmlDocumentService, times(1)).findTargetLawNodeToBeModified(any(), any());
    }

    @Test
    void oldTextIsReplacedByNewText() {
      // given
      final XmlDocumentService xmlDocumentService = new XmlDocumentService();
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);

      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>In <akn:ref"
              + " href=\"eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml\">paragraph 2</akn:ref> "
              + "<akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.</akn:mod></amendingLaw>";
      String targetLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">old text</akn:p></targetLaw>";

      // when
      String result = timeMachineService.apply(amendingLawString, targetLawString);

      // then
      assertThat(result)
          .isEqualTo(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">new text</akn:p></targetLaw>");
    }

    @Test
    void XmlProcessingExceptionIsThrownWhenAmendingLawXmlIsInvalid() {
      // given
      final XmlDocumentService xmlDocumentService = mock(XmlDocumentService.class);
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);
      String amendingLawString = "SomeRandomText";
      String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawString, targetLawString));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void XmlProcessingExceptionIsThrownWhenTargetLawXmlIsInvalid() {
      // given
      final XmlDocumentService xmlDocumentService = mock(XmlDocumentService.class);
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);
      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw></amendingLaw>";

      String targetLawString = "randomString";

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawString, targetLawString));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
          """
                 only one quotedText

                 <akn:mod>
                   In <akn:ref
            href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
            2</akn:ref> replace with <akn:quotedText>new</akn:quotedText>.
                 </akn:mod>
               """,
          """
                 eId THREE not found in target law

                 <akn:mod>
                   In <akn:ref
            href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/THREE/9-34.xml">paragraph
            2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
            <akn:quotedText>new</akn:quotedText>.
                 </akn:mod>
               """,
          """
                 no href attribute in "ref" tag

                 <akn:mod>
                   In <akn:ref>paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
            <akn:quotedText>new</akn:quotedText>.
                 </akn:mod>
               """,
          """
                 can't get eId from href

                 <akn:mod>
                   In <akn:ref href="invalid-eli-href">paragraph 2</akn:ref> replace
            <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
                 </akn:mod>
               """
        })
    void throwModificationExceptionOnMissingParts(String modificationNodeText) {
      // given
      final XmlDocumentService xmlDocumentService = new XmlDocumentService();
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);

      final String amendingLawXmlText =
          """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
          """
              + modificationNodeText
              + """

                </akn:body>
              """;
      final String targetLawXmlText =
          """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">old text</akn:p>
            </akn:body>
          """;

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawXmlText, targetLawXmlText));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void throwModificationExceptionIfAmendingLawHasNoModifications() {
      // given
      final XmlDocumentService xmlDocumentService = new XmlDocumentService();
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);

      final String amendingLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>";
      final String targetLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>";

      // when
      Throwable thrown = catchThrowable(() -> timeMachineService.apply(amendingLaw, targetLaw));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void targetLawToContainTheNewTextInPlaceOfTheOldOne() {
      // given
      final XmlDocumentService xmlDocumentService = new XmlDocumentService();
      final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);
      final String amendingLawXmlText =
          """
                 <?xml version="1.0" encoding="UTF-8"?>
                 <akn:body>
                     <akn:mod>
                      In <akn:ref
          href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
          2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
          <akn:quotedText>new</akn:quotedText>.
                     </akn:mod>

                     "old" -> "new"

                   </akn:body>
                 """
              .strip();
      final String targetLawXmlText =
          """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">old text</akn:p>
            </akn:body>
          """;

      final String expectedResultingLawXmlText =
          """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">new text</akn:p>
            </akn:body>
          """
              .replaceAll("[\\n ]", "");

      // when applying the TimeMachine
      final String resultingLaw = timeMachineService.apply(amendingLawXmlText, targetLawXmlText);

      // the result contains the new text in place of the old text
      assertThat(resultingLaw.replaceAll("[\\n ]", "")).isEqualTo(expectedResultingLawXmlText);
    }
  }
}
