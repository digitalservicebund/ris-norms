package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import org.junit.jupiter.api.Test;

class ArticleTest {

  @Test
  void getGuid() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung"></akn:article>
      """;

    var article = new Article(toElement(articleString));
    var expectedGuid = "cdbfc728-a070-42d9-ba2f-357945afef06";

    // when
    var guid = article.getGuid().get();

    // then
    assertThat(guid).isEqualTo(expectedGuid);
  }

  @Test
  void getEnumeration() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
        <akn:num eId="art-z1_bezeichnung-n1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
          Artikel 1
        </akn:num>
      </akn:article>
      """;

    var article = new Article(toElement(articleString));

    // when
    var enumeration = article.getEnumeration();

    // then
    assertThat(enumeration).contains("Artikel 1");
  }

  @Test
  void getEid() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
      </akn:article>
      """;

    var article = new Article(toElement(articleString));
    var expectedEid = new EId("art-z1");

    // when
    var eid = article.getEid();

    // then
    assertThat(eid).isEqualTo(expectedEid);
  }

  @Test
  void getMandatoryEid() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
          </akn:article>
          """;

    var article = new Article(toElement(articleString));
    var expectedEid = new EId("art-z1");

    // when
    var eid = article.getEid();

    // then
    assertThat(eid).isEqualTo(expectedEid);
  }

  @Test
  void getHeading() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
         <akn:heading eId="art-z1_überschrift-n1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
      </akn:article>
      """;

    var article = new Article(toElement(articleString));
    var expectedHeading = "Änderung des Vereinsgesetzes";

    // when
    var heading = article.getHeading().get();

    // then
    assertThat(heading).isEqualTo(expectedHeading);
  }

  @Test
  void getAffectedDocumentEli() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
            <akn:num eId="art-z1_bezeichnung-n1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">Artikel 1</akn:num>
         <akn:heading eId="art-z1_überschrift-n1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
         <!-- Absatz (1) -->
         <akn:paragraph eId="art-z1_abs-n1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
            <akn:num eId="art-z1_abs-n1_bezeichnung-n1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                </akn:num>
            <akn:list eId="art-z1_abs-n1_untergl-n1" GUID="41675622-ed62-46e3-869f-94d99908b010">
               <akn:intro eId="art-z1_abs-n1_untergl-n1_intro-n1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                  <akn:p eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1_bezugsdoc-n1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                        href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
               </akn:intro>
            </akn:list>
         </akn:paragraph>
      </akn:article>
      """;

    var article = new Article(toElement(articleString));

    // when
    var eli = article.getAffectedDocumentEli().get();

    // then
    assertThat(eli).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void setAffectedDocumentEli() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
                <akn:num eId="art-z1_bezeichnung-n1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">Artikel 1</akn:num>
             <akn:heading eId="art-z1_überschrift-n1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
             <!-- Absatz (1) -->
             <akn:paragraph eId="art-z1_abs-n1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                <akn:num eId="art-z1_abs-n1_bezeichnung-n1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                    </akn:num>
                <akn:list eId="art-z1_abs-n1_untergl-n1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                   <akn:intro eId="art-z1_abs-n1_untergl-n1_intro-n1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                      <akn:p eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1_bezugsdoc-n1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                            href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                   </akn:intro>
                </akn:list>
             </akn:paragraph>
          </akn:article>
          """;

    var article = new Article(toElement(articleString));
    // when
    article.setAffectedDocumentEli(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/regelungstext-verkuendung-1"
    );

    // then
    var eli = article.getAffectedDocumentEli().get();
    assertThat(eli).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void getRefersTo() {
    // given
    String articleString =
      """
      <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="art-z1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#meta-n1_geltzeiten-n1_geltungszeitgr-n1" refersTo="hauptaenderung">
                <akn:num eId="art-z1_bezeichnung-n1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">Artikel 1</akn:num>
             <akn:heading eId="art-z1_überschrift-n1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
             <!-- Absatz (1) -->
             <akn:paragraph eId="art-z1_abs-n1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                <akn:num eId="art-z1_abs-n1_bezeichnung-n1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                    </akn:num>
                <akn:list eId="art-z1_abs-n1_untergl-n1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                   <akn:intro eId="art-z1_abs-n1_untergl-n1_intro-n1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                      <akn:p eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="art-z1_abs-n1_untergl-n1_intro-n1_text-n1_bezugsdoc-n1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                            href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                   </akn:intro>
                </akn:list>
             </akn:paragraph>
          </akn:article>
          """;

    var article = new Article(toElement(articleString));
    var expectedRefersTo = "hauptaenderung";

    // when
    var eli = article.getRefersTo().get();

    // then
    assertThat(eli).isEqualTo(expectedRefersTo);
  }
}
