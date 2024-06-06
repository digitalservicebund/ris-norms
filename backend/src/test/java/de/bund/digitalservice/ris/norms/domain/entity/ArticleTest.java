package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toNode;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ArticleTest {
  @Test
  void getGuid() {
    // given
    String articleString =
        """
                <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung"></akn:article>
                """;

    var article = new Article(toNode(articleString));
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
                <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                  <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                    <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />
                    Artikel 1
                  </akn:num>
                </akn:article>
                """;

    var article = new Article(toNode(articleString));

    // when
    var enumeration = article.getEnumeration();

    // then
    assertThat(enumeration).contains("1");
  }

  @Test
  void getEid() {
    // given
    String articleString =
        """
                <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                </akn:article>
                """;

    var article = new Article(toNode(articleString));
    var expectedEid = "hauptteil-1_art-1";

    // when
    var eid = article.getEid().get();

    // then
    assertThat(eid).isEqualTo(expectedEid);
  }

  @Test
  void getHeading() {
    // given
    String articleString =
        """
                <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                   <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                </akn:article>
                """;

    var article = new Article(toNode(articleString));
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
                <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                  <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                      <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                   <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                   <!-- Absatz (1) -->
                   <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                      <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                         <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                      </akn:num>
                      <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                         <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                            <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                  href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                         </akn:intro>
                      </akn:list>
                   </akn:paragraph>
                </akn:article>
                """;

    var article = new Article(toNode(articleString));
    var expectedEli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

    // when
    var eli = article.getAffectedDocumentEli().get();

    // then
    assertThat(eli).isEqualTo(expectedEli);
  }

  @Test
  void setAffectedDocumentEli() {
    // given
    String articleString =
        """
                    <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                          <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                       <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                       <!-- Absatz (1) -->
                       <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                          <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                             <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                          </akn:num>
                          <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                             <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                      href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                             </akn:intro>
                          </akn:list>
                       </akn:paragraph>
                    </akn:article>
                    """;

    var article = new Article(toNode(articleString));
    var expectedEli = "newEli";

    // when
    article.setAffectedDocumentEli("newEli");

    // then
    var eli = article.getAffectedDocumentEli().get();
    assertThat(eli).isEqualTo(expectedEli);
  }

  @Test
  void getRefersTo() {
    // given
    String articleString =
        """
                    <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                          <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                       <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                       <!-- Absatz (1) -->
                       <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                          <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                             <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                          </akn:num>
                          <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                             <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                      href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                             </akn:intro>
                          </akn:list>
                       </akn:paragraph>
                    </akn:article>
                    """;

    var article = new Article(toNode(articleString));
    var expectedRefersTo = "hauptaenderung";

    // when
    var eli = article.getRefersTo().get();

    // then
    assertThat(eli).isEqualTo(expectedRefersTo);
  }

  @Test
  void getMods() {
    // given
    String articleString =
        """
                  <akn:article eId="hauptteil-1_art-1"
                             GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                             period="#geltungszeitgr-1"
                             refersTo="hauptaenderung">
                    <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                             GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                        <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1"
                                    GUID="81c9c481-9427-4f03-9f51-099aa9b2201e"
                                    name="1"/>Artikel 1
                    </akn:num>
                    <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                                 GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                    </akn:heading>
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
                                       GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5">Das <akn:affectedDocument
                                        eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                        GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                        href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom
                                    5. August 1964 (BGBl. I S. 593), das zuletzt
                                    durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                                </akn:p>
                            </akn:intro>

                            <!-- Nummer 2 -->
                            <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
                                       GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                                <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                                         GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                                    <akn:marker
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                            GUID="5d7d54f0-8a4e-4d8f-b5d0-93d0ca393e82"
                                            name="2"/>2.
                                </akn:num>
                                <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1"
                                             GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                                    <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                                           GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                                        <akn:mod
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                                                GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                                refersTo="aenderungsbefehl-ersetzen">In <akn:ref
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                                href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/100-126.xml">
                                            § 20 Absatz 1 Satz 2
                                        </akn:ref> wird
                                            die Angabe <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                                    GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2
                                            </akn:quotedText> durch die
                                            Wörter
                                            <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                                    GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3
                                            </akn:quotedText>
                                            ersetzt.
                                        </akn:mod>
                                    </akn:p>
                                </akn:content>
                            </akn:point>
                        </akn:list>
                    </akn:paragraph>
                </akn:article>
                """;

    var article = new Article(toNode(articleString));
    var expectedModEId =
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1";

    // when
    var mod = article.getMods();

    // then
    assertThat(mod).isNotEmpty();
    assertThat(mod.get(0).getEid()).contains(expectedModEId);
  }

  @Test
  void get2Mods() {
    // given
    String articleString =
        """
                  <akn:article eId="hauptteil-1_art-1"
                             GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                             period="#geltungszeitgr-1"
                             refersTo="hauptaenderung">
                    <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                             GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                        <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1"
                                    GUID="81c9c481-9427-4f03-9f51-099aa9b2201e"
                                    name="1"/>Artikel 1
                    </akn:num>
                    <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                                 GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                    </akn:heading>
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
                                       GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5">Das <akn:affectedDocument
                                        eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                        GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                        href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom
                                    5. August 1964 (BGBl. I S. 593), das zuletzt
                                    durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                                </akn:p>
                            </akn:intro>

                            <!-- Nummer 2 -->
                            <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
                                       GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                                <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                                         GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                                    <akn:marker
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                            GUID="5d7d54f0-8a4e-4d8f-b5d0-93d0ca393e82"
                                            name="2"/>2.
                                </akn:num>
                                <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1"
                                             GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                                    <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                                           GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                                        <akn:mod
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                                                GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                                refersTo="aenderungsbefehl-ersetzen">In <akn:ref
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                                href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/100-126.xml">
                                            § 20 Absatz 1 Satz 2
                                        </akn:ref> wird
                                            die Angabe <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                                    GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2
                                            </akn:quotedText> durch die
                                            Wörter
                                            <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                                    GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3
                                            </akn:quotedText>
                                            ersetzt.
                                        </akn:mod>
                                        <akn:mod
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-2"
                                                GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                                refersTo="aenderungsbefehl-ersetzen">In <akn:ref
                                                eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                                href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/100-126.xml">
                                            § 20 Absatz 1 Satz 2
                                        </akn:ref> wird
                                            die Angabe <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                                    GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2
                                            </akn:quotedText> durch die
                                            Wörter
                                            <akn:quotedText
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                                    GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                                    startQuote="„"
                                                    endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3
                                            </akn:quotedText>
                                            ersetzt.
                                        </akn:mod>
                                    </akn:p>
                                </akn:content>
                            </akn:point>
                        </akn:list>
                    </akn:paragraph>
                </akn:article>
                """;

    var article = new Article(toNode(articleString));
    var expectedModEId =
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1";

    // when
    var mods = article.getMods();

    // then
    assertThat(mods).isNotEmpty().hasSize(2);
    assertThat(mods.get(0).getEid()).contains(expectedModEId);
  }
}
