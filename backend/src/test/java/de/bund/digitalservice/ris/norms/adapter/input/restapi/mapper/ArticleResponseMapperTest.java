package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class ArticleResponseMapperTest {

  @Test
  void itMapsNormArticleCorrectly() {
    // Given
    var article1 = new Article(
      XmlMapper.toElement(
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
                 <!-- Nummer 1 wurde entfernt -->
                 <!-- Nummer 2 -->
                 <akn:point eId="art-z1_abs-n1_untergl-n1_listenelem-n2" GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                        <akn:num eId="art-z1_abs-n1_untergl-n1_listenelem-n2_bezeichnung-n1" GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">2.</akn:num>
                    <akn:content eId="art-z1_abs-n1_untergl-n1_listenelem-n2_inhalt-n1" GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                       <akn:p eId="art-z1_abs-n1_untergl-n1_listenelem-n2_inhalt-n1_text-n1" GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                          <akn:mod eId="art-z1_abs-1n_untergl-n1_listenelem-n2_inhalt-n1_text-n1_ändbefehl-n1" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="art-z1_abs-n1_untergl-n1_listenelem-n2_inhalt-n1_text-n1_ändbefehl-n1_ref-n1"
                                        GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-n1_untergl-n1_listenelem-n2_inhalt-n1_text-n1/0-0.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird die Angabe <akn:quotedText
                                eId="art-z1_abs-n1_untergl-n1_listenelem-n2_inhalt-n1_text-n1_ändbefehl-n1_quottext-n1" GUID="694459c4-ef66-4f87-bb78-a332054a2216" startQuote="„" endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die Wörter <akn:quotedText
                                eId="art-z1_abs-n1_untergl-n1_listenelem-n2_inhalt-n1_text-n1_ändbefehl-n1_quottext-n2" GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" startQuote="„" endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:quotedText> ersetzt.</akn:mod>
                       </akn:p>
                    </akn:content>
                 </akn:point>
              </akn:list>
           </akn:paragraph>
        </akn:article>
        """
      )
    );

    // When
    final ArticleResponseSchema resultArticle = ArticleResponseMapper.fromNormArticle(article1);

    // Then
    assertThat(resultArticle.getEid()).isEqualTo("art-z1");
    assertThat(resultArticle.getEnumeration()).isEqualTo("Artikel 1");
    assertThat(resultArticle.getTitle()).isEqualTo("Änderung des Vereinsgesetzes");
  }
}
