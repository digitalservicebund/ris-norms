package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class EidConsistencyGuardianTest {

  @Test
  void itDoesNotCorrectAnything() {
    var sampleXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-1_text-2">
                   <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
          """;

    // When
    final Document document = XmlMapper.toDocument(sampleXml);
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(document))
      .ignoreWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidGaps() {
    var sampleXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-1_text-3">
                   <akn:ref eId="meta-1_text-3_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-3_ref-4"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
          """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-1_text-2">
                   <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
          """;

    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidOrder() {
    var sampleXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p eId="meta-1_text-2">
                       <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                       <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
                   </akn:p>
                   <akn:p eId="meta-1_text-1">
                     <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                     <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p eId="meta-1_text-1">
                       <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                       <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
                   </akn:p>
                   <akn:p eId="meta-1_text-2">
                       <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                       <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidTypes() {
    var sampleXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p eId="meta-1_text-1">
                       <akn:ref eId="meta-1_text-1_text-1"></akn:ref>
                       <akn:ref eId="meta-1_text-1_text-2"></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p eId="meta-1_text-1">
                       <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                       <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsMissingEids() {
    var sampleXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p>
                       <akn:ref></akn:ref>
                       <akn:ref></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
              <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="meta-1">
                   <akn:p eId="meta-1_text-1">
                       <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                       <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
                   </akn:p>
                </akn:meta>
              </root>
              """;

    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectComplexLDML() {
    // Given
    var text =
      """
      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                  <akn:lifecycle eId="meta-1_lebzykl-1" GUID="a1ce9b04-8071-43c2-be56-10e7049ab500" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="e3ac3efd-b9fc-4e84-b494-f28937a89855" date="1964-08-05" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung"/>
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="7174df87-6418-4d47-ac4e-5fb87c2caa50" date="1964-09-21" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten"/>
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="c271d75e-bd26-4f5a-8544-0eea1c410fbf" date="2017-03-15" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="ausfertigung"/>
                      <!-- ereignis-4 removed -->
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-5" GUID="d0acc7c7-3c5e-44fa-bcb7-0fe2788876ea" date="2019-03-03" source="attributsemantik-noch-undefiniert" type="repeal" refersTo="ausserkrafttreten"/>
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-6" GUID="a20f1fcc-fd43-403d-ab8f-8d950fafa633" date="1964-09-01" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"/>
                  </akn:lifecycle>
                  <akn:temporalData eId="meta-1_geltzeiten-1" GUID="33875608-d668-4be0-a830-8ec5d0a421c8" source="attributsemantik-noch-undefiniert">
                      <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="1a5d8eba-b899-4152-852d-91a7df39e3d9">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="6fc42c43-6598-4c95-ac57-da132b047ec1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
                      </akn:temporalGroup>
                      <!-- ereignis-4 removed -->
                      <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="08308b59-4541-4643-8d55-f7950e515553">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="b382ce1b-7b4f-4bb6-a878-7a37c11c3854" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-5"/>
                      </akn:temporalGroup>
                      <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="b4392765-b70b-4d6a-be2d-6e971acb6c8a">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="c7b1d9de-a146-4748-8e60-c2e08beab5d7" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-6"/>
                      </akn:temporalGroup>
                  </akn:temporalData>
                  <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                      <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                          <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-1" GUID="06fb52c3-fce1-4be8-accc-3035452378ff" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-1_source-1" GUID="5384f580-110b-4f8a-8794-8b85c29aabdf" href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"/>
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-1_destination-1" GUID="2c26512f-fb04-45f2-8283-660274e52fdb" href="#para-9_abs-3"/>
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-1_gelzeitnachw-1" GUID="45331583-4386-4e3f-b68f-5af327347874" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                          </akn:textualMod>
                          <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126"/>
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-3"/>
                          </akn:textualMod>
                      </akn:passiveModifications>
                  </akn:analysis>
                                                          <akn:proprietary source="attributsemantik-noch-undefiniert" eId="meta-1_proprietary-2" GUID="d03c4c44-85ae-4f95-9d3e-063d9feba23f">
                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                      <meta:celex start="non-existing-id-1">celex number</meta:celex>
                                      <meta:subtyp start="non-existing-id-2">SN</meta:subtyp>
                                      <meta:aktenzeichen start="meta-1_lebzykl-1_ereignis-2">123456</meta:aktenzeichen>
                                  </meta:legalDocML.de_metadaten_ds>
                              </akn:proprietary>
              </akn:meta>
          </akn:act>
      </akn:akomaNtoso>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(text);
    EidConsistencyGuardian.eliminateDeadReferences(correctedDocument);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var exectedResult =
      """
      <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                                                                          http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                  <akn:lifecycle GUID="a1ce9b04-8071-43c2-be56-10e7049ab500" eId="meta-1_lebzykl-1" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef GUID="e3ac3efd-b9fc-4e84-b494-f28937a89855" date="1964-08-05" eId="meta-1_lebzykl-1_ereignis-1" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" type="generation"/>
                      <akn:eventRef GUID="7174df87-6418-4d47-ac4e-5fb87c2caa50" date="1964-09-21" eId="meta-1_lebzykl-1_ereignis-2" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="generation"/>
                      <akn:eventRef GUID="c271d75e-bd26-4f5a-8544-0eea1c410fbf" date="2017-03-15" eId="meta-1_lebzykl-1_ereignis-3" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" type="amendment"/>
                      <!-- ereignis-4 removed -->
                      <akn:eventRef GUID="d0acc7c7-3c5e-44fa-bcb7-0fe2788876ea" date="2019-03-03" eId="meta-1_lebzykl-1_ereignis-4" refersTo="ausserkrafttreten" source="attributsemantik-noch-undefiniert" type="repeal"/>
                      <akn:eventRef GUID="a20f1fcc-fd43-403d-ab8f-8d950fafa633" date="1964-09-01" eId="meta-1_lebzykl-1_ereignis-5" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="amendment"/>
                  </akn:lifecycle>
                  <akn:temporalData GUID="33875608-d668-4be0-a830-8ec5d0a421c8" eId="meta-1_geltzeiten-1" source="attributsemantik-noch-undefiniert">
                      <akn:temporalGroup GUID="1a5d8eba-b899-4152-852d-91a7df39e3d9" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
                          <akn:timeInterval GUID="6fc42c43-6598-4c95-ac57-da132b047ec1" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
                      </akn:temporalGroup>
                      <!-- ereignis-4 removed -->
                      <akn:temporalGroup GUID="08308b59-4541-4643-8d55-f7950e515553" eId="meta-1_geltzeiten-1_geltungszeitgr-2">
                          <akn:timeInterval GUID="b382ce1b-7b4f-4bb6-a878-7a37c11c3854" eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4"/>
                      </akn:temporalGroup>
                      <akn:temporalGroup GUID="b4392765-b70b-4d6a-be2d-6e971acb6c8a" eId="meta-1_geltzeiten-1_geltungszeitgr-3">
                          <akn:timeInterval GUID="c7b1d9de-a146-4748-8e60-c2e08beab5d7" eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-5"/>
                      </akn:temporalGroup>
                  </akn:temporalData>
                  <akn:analysis GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" eId="meta-1_analysis-1" source="attributsemantik-noch-undefiniert">
                      <akn:passiveModifications GUID="77aae58f-06c9-4189-af80-a5f3ada6432c" eId="meta-1_analysis-1_pasmod-1">
                          <akn:textualMod GUID="06fb52c3-fce1-4be8-accc-3035452378ff" eId="meta-1_analysis-1_pasmod-1_textualmod-1" type="substitution">
                              <akn:source GUID="5384f580-110b-4f8a-8794-8b85c29aabdf" eId="meta-1_analysis-1_pasmod-1_textualmod-1_source-1" href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"/>
                              <akn:destination GUID="2c26512f-fb04-45f2-8283-660274e52fdb" eId="meta-1_analysis-1_pasmod-1_textualmod-1_destination-1" href="#para-9_abs-3"/>
                              <akn:force GUID="45331583-4386-4e3f-b68f-5af327347874" eId="meta-1_analysis-1_pasmod-1_textualmod-1_gelzeitnachw-1" period=""/>
                          </akn:textualMod>
                          <akn:textualMod GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" eId="meta-1_analysis-1_pasmod-1_textualmod-2" type="substitution">
                              <akn:source GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                              <akn:destination GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" href="#para-20_abs-1/100-126"/>
                              <akn:force GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                          </akn:textualMod>
                      </akn:passiveModifications>
                  </akn:analysis>
                              <akn:proprietary source="attributsemantik-noch-undefiniert" eId="meta-1_proprietary-1" GUID="d03c4c44-85ae-4f95-9d3e-063d9feba23f">
                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                      <meta:celex start="">celex number</meta:celex>
                                      <meta:subtyp start="">SN</meta:subtyp>
                                      <meta:aktenzeichen start="meta-1_lebzykl-1_ereignis-2">123456</meta:aktenzeichen>
                                  </meta:legalDocML.de_metadaten_ds>
                              </akn:proprietary>
              </akn:meta>
          </akn:act>
      </akn:akomaNtoso>
      """;
    final Diff diff = DiffBuilder
      .compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(exectedResult)))
      .ignoreWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }
}
