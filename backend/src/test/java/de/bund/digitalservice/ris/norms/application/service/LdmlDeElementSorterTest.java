package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class LdmlDeElementSorterTest {

  private final LdmlDeElementSorter ldmlDeElementSorter = new LdmlDeElementSorter(
    Fixtures.getXsdSchemaService()
  );

  @Test
  void itSortsElements() {
    var elementNode = """
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="art-z1"
                      GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                      period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1"
                      refersTo="hauptaenderung">
                      <akn:heading eId="art-z1_überschrift-n1"
                          GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                      </akn:heading>
                      <akn:num eId="art-z1_bezeichnung-n1"
                          GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                              Artikel 1 </akn:num>
                  </akn:article>
      """;
    var element = XmlMapper.toElement(elementNode);

    ldmlDeElementSorter.sortElements(element);

    final Diff diff = DiffBuilder.compare(
      Input.from(
        """
            <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="art-z1"
                        GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                        period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1"
                        refersTo="hauptaenderung">
                        <akn:num eId="art-z1_bezeichnung-n1"
                            GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                Artikel 1 </akn:num>
                        <akn:heading eId="art-z1_überschrift-n1"
                            GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                        </akn:heading>
                    </akn:article>
        """
      )
    )
      .withTest(Input.from(element))
      .normalizeWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itSortsElementsWithDifferentNamespaces() {
    var elementNode = """
      <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
         <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
               <norms:geltungszeiten>
                  <norms:geltungszeit id="cd95811a-63ac-4710-b8aa-fdae33131399" art="inkraft">2017-03-16</norms:geltungszeit>
               </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
            <ris:fna>310-5</ris:fna>
         </ris:legalDocML.de_metadaten>
         <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
            <regtxt:form>stammform</regtxt:form>
            <regtxt:typ>gesetz</regtxt:typ>
         </regtxt:legalDocML.de_metadaten>
      </akn:proprietary>
      """;
    var element = XmlMapper.toElement(elementNode);

    ldmlDeElementSorter.sortElements(element);

    final Diff diff = DiffBuilder.compare(
      Input.from(
        """
            <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
             <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                <ris:fna>310-5</ris:fna>
                <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
                   <norms:geltungszeiten>
                      <norms:geltungszeit id="cd95811a-63ac-4710-b8aa-fdae33131399" art="inkraft">2017-03-16</norms:geltungszeit>
                   </norms:geltungszeiten>
                </norms:legalDocML.de_metadaten>
             </ris:legalDocML.de_metadaten>
             <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
                <regtxt:typ>gesetz</regtxt:typ>
                <regtxt:form>stammform</regtxt:form>
             </regtxt:legalDocML.de_metadaten>
            </akn:proprietary>
        """
      )
    )
      .withTest(Input.from(element))
      .normalizeWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
    strings = {
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml",
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml",
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml",
    }
  )
  void correctlySortedNormsStayTheSame(String regelungstextFile) {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(regelungstextFile);

    ldmlDeElementSorter.sortElements(regelungstext.getDocument().getDocumentElement());

    final Diff diff = DiffBuilder.compare(
      Input.from(Fixtures.loadRegelungstextFromDisk(regelungstextFile).getDocument())
    )
      .withTest(Input.from(regelungstext.getDocument()))
      .normalizeWhitespace()
      .ignoreComments()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }
}
