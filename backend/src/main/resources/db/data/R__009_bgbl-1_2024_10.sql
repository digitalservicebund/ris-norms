-- REAPPLY
-- TARGET LAW
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu';

INSERT INTO dokumente (xml)
VALUES ('<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="24742477-cfd0-4df5-8df1-8da2ed8100e9" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="13a74d12-5e00-4389-a066-554cfeb701d2" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="30e03a94-5d59-4446-b916-1f0fbf8d237a" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/2021/s818/regelungstext-1" GUID="fdebe962-76b1-4cd6-902f-0de5efa511ba" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/2021/s818" GUID="e3d14018-3855-4f93-a6da-a7a6887ee0cd" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="d68b8898-791c-405b-b949-d1ae95afcd4e" GUID="b3e12015-f24c-4e14-87b1-932b71b0438e" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="2021-04-16" GUID="cd994298-da98-48be-af8a-e4466ea2d312" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="9127781e-1cdf-4257-9b45-d3494ca0ae68" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="8e0a35e1-6cc0-48a0-b7da-5aaa9291af9b" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="s818" GUID="ea6280f8-ef98-45b8-98a3-735b720eb6e9" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="ce030454-dc23-4523-9b45-ce90978cf938" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="a1f79a15-4103-4e4c-a13e-125e6e5299ca" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="c8ccac77-fa9b-45c1-b15c-18273e96544f" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/regelungstext-1" GUID="270fc375-a816-4358-a3a0-2ce4e571863e" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu" GUID="81e95a53-811c-4429-bcfe-acb1e6bacc22" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="aea98f68-4597-4602-bb38-e0091e9d449c" GUID="956994a0-f959-4c22-bc01-834cedf91153" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="4ecd475f-0111-43a8-8a7f-1a88327fb8bb" GUID="5d675fd4-a03e-477a-a93c-e17595e5b2c6" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="c88a6420-1443-4c93-95ac-b11b94b30b5e" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="2021-04-16" GUID="5e54d5f1-6338-42a8-b4cb-0d41e9e40521" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="e956bad1-d808-4fed-a260-67599d719eb6" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="7e40965a-55d5-4fab-84a2-3401bcc5473d" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="710d8859-ca77-443e-a897-a7442b8aac94" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-1.xml" GUID="a4dfeea3-4105-40dc-86f6-d6473a44b024" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-1.xml" GUID="272372dd-805e-44f6-ae5b-af4e45a80ce5" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="2021-04-16" GUID="0d0da0f3-491d-46d5-a13a-b3b9a45b1777" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="70dc29c0-b84a-4bad-ab13-b9d862e6db50" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="2312ed1a-3aef-48bc-9da7-5e606161abd2" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="9a901140-6aba-4ca5-8235-0b3b94c310ab" eId="meta-1_lebzykl-1">
        <akn:eventRef date="2021-04-16" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="0ad6006b-882f-4286-8c2c-7d81fc673764" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="2022-01-01" refersTo="inkrafttreten" type="generation" source="attributsemantik-noch-undefiniert" GUID="1507e4cc-9f73-4c4b-833c-bae49fb094b2" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="55b9dee2-6fd2-4191-8e5b-b9c61a21351e" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="5816d83f-3d19-4ecb-a91a-eb5bc25bb026" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="1c522ed8-f3a4-41dd-a09c-8aa681ac6d1b" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1"/>
        </akn:temporalGroup>
        <akn:temporalGroup GUID="8d152419-7522-4afe-afd1-5a524e9ba4ef" eId="meta-1_geltzeiten-1_geltungszeitgr-2">
          <akn:timeInterval refersTo="geltungszeit" GUID="ddce2b2f-9b70-43ed-97f6-a31e53c337cb" eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="6c70b4b7-ad96-4a65-a752-829195eb359d" eId="meta-1_proprietary-1">
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
          <meta:typ>gesetz</meta:typ>
          <meta:form>stammform</meta:form>
          <meta:fassung>verkuendungsfassung</meta:fassung>
          <meta:art>regelungstext</meta:art>
          <meta:initiant>bundesregierung</meta:initiant>
          <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
          <meta:fna>nicht-vorhanden</meta:fna>
          <meta:gesta>nicht-vorhanden</meta:gesta>
        </meta:legalDocML.de_metadaten>
        <meta-breg:legalDocML.de_metadaten xmlns:meta-breg="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
          <meta-breg:federfuehrung>
            <meta-breg:federfuehrend ab="2021-04-16" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta-breg:federfuehrend>
          </meta-breg:federfuehrung>
        </meta-breg:legalDocML.de_metadaten>
      </akn:proprietary>
    </akn:meta>
    <akn:preface GUID="45f1672f-2be9-47d5-a350-888b1d66a61b" eId="einleitung-1">
      <akn:longTitle GUID="3b62ca26-170c-43b7-b1fa-01169fadc48b" eId="einleitung-1_doktitel-1">
        <akn:p GUID="723ee51e-1090-4c3e-b1cc-972b80e575ad" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="e09dcd47-4b9b-4e42-a187-952c8972f7db" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Einführung eines Lobbyregisters für die Interessenvertretung gegenüber dem Deutschen Bundestag und gegenüber der Bundesregierung</akn:docTitle>
          <akn:shortTitle GUID="a8466da3-260d-436b-a4d0-d00dd4275a9a" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(Lobbyregistergesetz - <akn:inline refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert" GUID="bf54ad6d-d91c-44c6-90b5-ee84383c43b6" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">LobbyRG</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="d909906d-84d1-41ae-aec6-642daddb852c" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="2021-04-16" GUID="9e01d8d3-f897-4b25-b59f-0d1f1a595607" eId="einleitung-1_block-1_datum-1">Vom 16. April 2021</akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="c65a4b76-4619-4596-8a6f-0cdb4fd37e77" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="c73dc2c0-bb77-4282-9460-bacd420e469b" eId="preambel-1_formel-1">
        <akn:p GUID="5b96ca92-3763-43a3-a46b-3c98c213cc01" eId="preambel-1_formel-1_text-1">Der <akn:organization title="Bundestag" refersTo="attributsemantik-noch-undefiniert" GUID="f450af89-9fc1-41c0-bea8-cfa941411ccb" eId="preambel-1_formel-1_text-1_org-1">Bundestag</akn:organization> hat das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="430ec2ad-f01a-46e1-88f5-ba825e050025" eId="hauptteil-1">
      <akn:article GUID="cffb7e69-13e7-436a-95ef-5670a3d33ba9" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="d59f2e6c-8077-44e7-a58e-506038727ac1" eId="hauptteil-1_art-1_bezeichnung-1">§ 1</akn:num>
        <akn:heading GUID="be5695ab-78c7-441e-a576-35178895b7ff" eId="hauptteil-1_art-1_überschrift-1">Anwendungsbereich</akn:heading>
        <akn:paragraph GUID="8ba351c6-135c-4349-8cb2-c9e2656a029c" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="abb6ae02-7b61-4ca6-b231-2748f3218b42" eId="hauptteil-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="844f54c0-a79f-428f-9c4b-8c1e23547982" eId="hauptteil-1_art-1_abs-1_inhalt-1">
            <akn:p GUID="c1d41b6b-bb20-4466-9889-db449f06fc5c" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1">Dieses Gesetz gilt für die Interessenvertretung gegenüber den Organen, Mitgliedern, Fraktionen oder Gruppen des Deutschen Bundestages und für die Interessenvertretung gegenüber der Bundesregierung.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="276a742e-807a-4e27-8c4b-7e38a4ab3abf" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="277fc7f9-6256-44b1-a187-3963ef047e17" eId="hauptteil-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="a40c8feb-38fb-481c-a6f7-e0e45e20b5ed" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="5d03615b-5c2e-4911-ac82-c6735c0448eb" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">Die Regelungen für die Bundesregierung gelten ebenfalls für die Parlamentarischen Staatssekretärin nen und Parlamentarischen Staatssekretäre, die Staatssekretärinnen und Staatssekretäre, die Abtei lungsleiterinnen und Abteilungsleiter sowie die Unter abteilungsleiterinnen und Unterabteilungsleiter.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="11aac200-6cfc-4603-ad30-3b79418f0d83" eId="hauptteil-1_art-1_abs-3">
          <akn:num GUID="27a43666-96c6-4211-a476-5ac6dc115ab6" eId="hauptteil-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="73a57e3d-7772-4ec8-957c-d47c97fdd0fb" eId="hauptteil-1_art-1_abs-3_inhalt-1">
            <akn:p GUID="891bc66f-6f97-4c97-86ae-5ecacbaee79d" eId="hauptteil-1_art-1_abs-3_inhalt-1_text-1">Interessenvertretung ist jede Kontaktaufnahme zum Zweck der unmittelbaren oder mittelbaren Ein flussnahme auf den Willensbildungs- oder Entscheidungsprozess der Organe, Mitglieder, Fraktionen oder Gruppen des Deutschen Bundestages oder zum Zweck der unmittelbaren oder mittelbaren Einflussnahme auf den Willensbildungs- oder Entscheidungs prozess der Bundesregierung.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="e17c4ef9-31b8-4618-9cd2-7cd4753b6687" eId="hauptteil-1_art-1_abs-4">
          <akn:num GUID="74c18617-ec80-403d-82da-0bb4ab6aa683" eId="hauptteil-1_art-1_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="4a0d4b06-7882-4be6-923a-058a81fe9a2c" eId="hauptteil-1_art-1_abs-4_inhalt-1">
            <akn:p GUID="783cd6ee-f1c4-4f1b-857a-2a6c14a691c9" eId="hauptteil-1_art-1_abs-4_inhalt-1_text-1">Interessenvertreterinnen oder Interessenvertreter sind alle natürlichen oder juristischen Personen, Personengesellschaften oder sonstigen Organisationen, auch in Form von Netzwerken, Plattformen oder anderen Formen kollektiver Tätigkeiten, die Interessenvertretung nach <akn:ref href="#hauptteil-1_art-1_abs-3" GUID="c2693ab2-4f0c-495e-9ea3-44213006a86e" eId="hauptteil-1_art-1_abs-4_inhalt-1_text-1_ref-1">Absatz 3</akn:ref> selbst betreiben oder in Auftrag geben.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="66ff7734-1aed-49dc-bab9-31d020532dcc" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="d7cfc6de-cc01-46f1-ad93-762018eedef0" eId="hauptteil-1_art-2_bezeichnung-1">§ 2</akn:num>
        <akn:heading GUID="9e1ba61e-33d4-4e0d-9e3f-9bddea9ab3d0" eId="hauptteil-1_art-2_überschrift-1">Registrierungspflicht</akn:heading>
        <akn:paragraph GUID="2eef5a8f-32be-4e23-b8c3-75528452d446" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="d128a7d8-55a7-47ab-84a3-aa488a3ac654" eId="hauptteil-1_art-2_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:list GUID="6d4675ed-e359-47af-9795-5955861130ec" eId="hauptteil-1_art-2_abs-1_untergl-1">
            <akn:intro GUID="538340e2-6385-44d2-b472-98fdbcffaacd" eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1">
              <akn:p GUID="c14283f6-ec0f-40ab-92df-6e4d75ee5e43" eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1_text-1">Interessenvertreterinnen oder Interessenvertreter nach § 1 Absatz 4 müssen die Angaben nach § 3 Absatz 1 in einem öffentlichen Verzeichnis (Lobbyregister) gemäß Satz 2 eintragen, wenn</akn:p>
            </akn:intro>
            <akn:point GUID="1203ead7-db08-4ff9-bdd4-99205e687343" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="b85f6a02-f961-44f4-965b-0a234395bdaa" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:content GUID="e319d6c5-366a-475c-8b52-03c92129af11" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="0f189e06-71f3-4dda-a207-6c85c77fae44" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">die Interessenvertretung regelmäßig betrieben wird,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="56ccbf67-aebb-4739-bbef-5cd90e0a5df4" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="53eb0ea6-aaa3-49c5-a62f-e5b4fa14eb11" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:content GUID="8b0f1e00-c2e7-41db-ae85-234fe3e60f04" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="81a6262e-8628-4a3e-bc23-67b7f62e1377" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">die Interessenvertretung auf Dauer angelegt ist,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="136d5466-c103-4e47-ad62-170cf0e0ad58" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3">
              <akn:num GUID="1548fdd3-33fc-4b32-8236-2b3cc858d2eb" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
              <akn:content GUID="f7132baf-6ca4-4585-9f66-2d8313e8cbc6" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="f6f3027f-91c1-451c-96a7-cc191ad7a096" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">die Interessenvertretung geschäftsmäßig für Dritte betrieben wird oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="adab4575-28a4-4210-bb50-7f366e1846e6" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4">
              <akn:num GUID="ce272fc8-c45e-443a-8364-0d3eb4871b0f" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_bezeichnung-1">4.</akn:num>
              <akn:content GUID="b59871b2-f5c8-431b-8594-bdc72bf0aeef" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="30775dc6-e584-4042-bc72-e48eccf739c6" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">innerhalb der jeweils letzten drei Monate mehr als 50 unterschiedliche Interessenvertretungskontakte aufgenommen wurden.</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="d9140f57-d66a-4719-8d4a-21fd7c3bc887" eId="hauptteil-1_art-2_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="139d2e26-4405-4c20-b54b-1585d64bbfeb" eId="hauptteil-1_art-2_abs-1_untergl-1_schlusstext-1_text-1">Die Eintragung ist unverzüglich vorzunehmen, sobald eine der in Satz 1 genannten Voraussetzungen vorliegt.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="241dbe96-f259-47c5-a3ca-5ccac190fa70" eId="hauptteil-1_art-2_abs-2">
          <akn:num GUID="d2518aeb-c2ae-4b8a-8638-d6eea03c0e4f" eId="hauptteil-1_art-2_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:list GUID="1ee39ff9-d1ff-417a-984d-ee6cdba99faa" eId="hauptteil-1_art-2_abs-2_untergl-1">
            <akn:intro GUID="c3afc322-2a94-4908-ba6b-545366ab32af" eId="hauptteil-1_art-2_abs-2_untergl-1_intro-1">
              <akn:p GUID="7cdee7b9-56fa-43d8-9499-a25d4c904cde" eId="hauptteil-1_art-2_abs-2_untergl-1_intro-1_text-1">Interessenvertreterinnen oder Interessenvertreter nach <akn:ref href="#hauptteil-1_art-2_abs-1" GUID="69da1e42-004e-431c-8b50-cf905f7952c2" eId="hauptteil-1_art-2_abs-2_untergl-1_intro-1_text-1_ref-1">Absatz 1</akn:ref> müssen sich bei Interessenvertretung gegenüber den Organen, Mitgliedern, Fraktionen oder Gruppen des Deutschen Bundestages nicht eintragen, wenn und soweit sie</akn:p>
            </akn:intro>
            <akn:point GUID="b96518d5-1b5e-44c6-9361-2cd871e852fa" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-1">
              <akn:num GUID="aa84ee10-209e-4329-9783-60b427afde40" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:content GUID="5a683931-9d70-436b-9e69-dca1627f30e3" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="4265217a-5e24-4925-9936-8e05193d3a8f" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">natürliche Personen sind, die mit ihrer Eingabe ausschließlich persönliche Interessen formulieren, unabhängig davon, ob es sich zugleich um unternehmerische oder sonstige Interessen handelt,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="8e10d714-d923-47e2-95db-ac416abaa76b" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-2">
              <akn:num GUID="be800b28-d7a1-4b18-b891-8f935a8a6786" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:content GUID="e74bc35e-33f3-4dc6-b35e-3d3915ce9955" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="5e662f85-c3cc-460e-bceb-59106e4f241d" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">Anliegen von ausschließlich lokalem Charakter geltend machen, soweit nicht mehr als zwei Wahlkreise unmittelbar betroffen sind,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="69bde502-733a-4520-adca-486f8edeed36" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-3">
              <akn:num GUID="4f73d2fc-77d1-4e6f-b725-cb4b45092020" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
              <akn:content GUID="8107be4f-17a0-468b-beb1-fdaa5366ef01" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="011b44e9-925b-4441-b3e1-8acb6de47ba5" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">eine Petition nach <akn:ref href="" GUID="bc1aed73-b068-4b19-b256-c8ef09b028f3" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1_text-1_ref-1">Artikel 17</akn:ref> des Grundgesetzes einreichen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="3d627eb6-ef74-459d-8196-d865a9fe373d" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-4">
              <akn:num GUID="acaa28c3-405c-45fa-8712-0d7e824c20e6" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-4_bezeichnung-1">4.</akn:num>
              <akn:content GUID="bb37fa72-87bc-4c78-9d7f-86f63387e1d3" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="e051dffb-5822-49f2-a087-14fe347baaf3" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">an öffentlichen Anhörungen der Ausschüsse, öffentlichen Kongressen oder anderen öffentlichen Veranstaltungen der Organe, Mitglieder, Fraktionen oder Gruppen des Deutschen Bundestages teilnehmen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="20dc44a7-df5c-49aa-bf8b-1077bc30ecf3" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-5">
              <akn:num GUID="9b5f1420-1761-454d-b943-c552bd1899e4" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-5_bezeichnung-1">5.</akn:num>
              <akn:content GUID="a85bb5f9-65f1-4490-9469-f5c14ec3e5eb" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-5_inhalt-1">
                <akn:p GUID="20e106c2-2e38-40cd-9b2f-12da9869d56b" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-5_inhalt-1_text-1">direkten und individuellen Ersuchen der Organe, Mitglieder, Fraktionen oder Gruppen des Deutschen Bundestages um Sachinformationen, Daten oder Fachwissen nachkommen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="d3c76441-95d8-4206-b245-dd78fd8fed4b" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-6">
              <akn:num GUID="3c544a80-e2ec-44a6-92dd-ef4000b965fa" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-6_bezeichnung-1">6.</akn:num>
              <akn:content GUID="d460b0a5-38e3-4712-8651-6ddb49a6d0b7" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-6_inhalt-1">
                <akn:p GUID="08870e67-057d-442a-b01d-d3a356334817" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-6_inhalt-1_text-1">ein öffentliches Amt oder Mandat wahrnehmen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="80af699c-9ac6-4112-948d-80f45d0d5fec" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-7">
              <akn:num GUID="a23d8398-908e-46e3-b88b-c2accd4b9152" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-7_bezeichnung-1">7.</akn:num>
              <akn:content GUID="dabc6f7c-a686-4c4d-bc70-880dda282ee8" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-7_inhalt-1">
                <akn:p GUID="60c9d2f7-99b3-47a5-a186-8fd74c8579b1" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-7_inhalt-1_text-1">als Arbeitgeber- oder Arbeitnehmerverband (<akn:ref href="" GUID="8feb8053-83ec-4a30-adee-313cc877a56f" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-7_inhalt-1_text-1_ref-1">Artikel 9 Absatz 3</akn:ref> des Grundgesetzes) Einfluss auf Arbeits- und Wirtschaftsbedingungen nehmen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="90adfd51-c945-4f37-98f5-40949101b997" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-8">
              <akn:num GUID="f6313bc4-2133-4ca5-81a7-2fa3eb3d7e19" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-8_bezeichnung-1">8.</akn:num>
              <akn:content GUID="685b4aeb-e29a-489e-9e4e-766c7c6434a5" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-8_inhalt-1">
                <akn:p GUID="6746c228-f429-4671-8eca-d576a8af2756" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-8_inhalt-1_text-1">Rechtsberatung für einen Dritten oder sich selbst, einschließlich der Erstattung wissenschaftlicher Gutachten oder an die Allgemeinheit gerichteter Darstellung und Erörterung von Rechtsfragen erbringen, sowie Tätigkeiten, die nicht auf Erlass, Änderung oder Unterlassung einer rechtlichen Regelung durch den Deutschen Bundestag oder die Bundesregierung gerichtet sind, erbringen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="151f92c0-16ba-4e77-9583-aa6c03929f87" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-9">
              <akn:num GUID="d46a692e-5211-4a20-8b92-76e7d5531d18" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-9_bezeichnung-1">9.</akn:num>
              <akn:content GUID="aa4bedc7-8ecd-4795-a199-32137a398339" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-9_inhalt-1">
                <akn:p GUID="2d9a92d3-b342-405b-bfbd-2d7d43225967" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-9_inhalt-1_text-1">als politische Parteien nach dem Parteiengesetz tätig werden,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="bbd0ccf5-d9b9-4b1d-976a-bbe2f09eb88e" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-10">
              <akn:num GUID="61ab07c3-1920-4d82-a5c3-213a20c38129" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-10_bezeichnung-1">10.</akn:num>
              <akn:content GUID="ee62637b-e043-4093-a8d1-876277d002b1" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-10_inhalt-1">
                <akn:p GUID="65061134-4277-44a7-ad6b-214853e49169" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-10_inhalt-1_text-1">als Einrichtungen zur gesellschaftspolitischen und demokratischen Bildungsarbeit (politische Stiftungen) tätig werden, soweit der jeweilige Haushaltsgesetzgeber Globalzuschüsse zur Erfüllung ihrer satzungsmäßigen Aufgaben gewährt,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="ff706ea8-82f2-4df8-a804-1ac90ef2e95c" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-11">
              <akn:num GUID="325fdde5-f143-4d94-8501-ba78c29dc15a" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-11_bezeichnung-1">11.</akn:num>
              <akn:content GUID="1745529c-cd3d-481a-898a-363fc0a6ad76" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-11_inhalt-1">
                <akn:p GUID="781ab044-fa3e-4c12-96a1-962ff7203fdd" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-11_inhalt-1_text-1">als Mittlerorganisationen der auswärtigen Kultur- und Bildungspolitik tätig werden, soweit sie institutionell mit Mitteln des Bundeshaushaltes gefördert werden,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="554c1b1e-2aa4-475a-aaa5-94fe47ff5f1d" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-12">
              <akn:num GUID="cf4c4f9a-06d7-42ce-9e4e-56719d02a6a2" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-12_bezeichnung-1">12.</akn:num>
              <akn:content GUID="709169d3-b94e-4dcb-bc1d-6267b63266f9" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-12_inhalt-1">
                <akn:p GUID="ca87a5ac-31cd-4ffa-98a5-de28b7e23135" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-12_inhalt-1_text-1">als Kirche, andere Religionsgemeinschaft oder Weltanschauungsgemeinschaft tätig werden,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="d35be5ff-b056-4e9a-82b4-255c6ac50099" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-13">
              <akn:num GUID="60de59b8-b3ff-42b6-85a7-d702f7c57568" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-13_bezeichnung-1">13.</akn:num>
              <akn:content GUID="d52cfe91-5e8e-4a4f-b96f-8b7934aa2111" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-13_inhalt-1">
                <akn:p GUID="91aa63b4-9832-4876-8921-7a76abf65ced" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-13_inhalt-1_text-1">einer nach <akn:ref href="" GUID="b6c36bab-0a70-4c49-a355-0d3766efa7c0" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-13_inhalt-1_text-1_ref-1">Artikel 5 Absatz 1 Satz 2</akn:ref> des Grundgesetzes geschützten Tätigkeit nachgehen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="970e2839-e905-4b85-b7be-911a7f84e77d" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-14">
              <akn:num GUID="358196b1-5962-4260-a35d-1cf2fc87b3a0" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-14_bezeichnung-1">14.</akn:num>
              <akn:content GUID="c8924c2a-bfe4-43f4-9fb9-dd2fd88b36f8" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-14_inhalt-1">
                <akn:p GUID="a891c3e6-664e-4bbd-9230-0ca59c69736a" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-14_inhalt-1_text-1">als kommunaler Spitzenverband auf Bundes- oder Landesebene tätig sind,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="1f55701a-465b-466f-b110-48e17ce81f6a" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-15">
              <akn:num GUID="b18cabb5-38aa-48c9-a906-c2e569aba003" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-15_bezeichnung-1">15.</akn:num>
              <akn:content GUID="a179c360-6518-43d8-b518-6d6cee7112d1" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-15_inhalt-1">
                <akn:p GUID="fab4092b-48c4-4b61-9242-cf0c45f17a80" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-15_inhalt-1_text-1">als eine in Deutschland anerkannte nationale Minderheit, als niederdeutsche Sprechergruppe, als deutsche Minderheit in Dänemark oder als Organisation oder Einrichtung der vorgenannten Gruppen tätig werden oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="ffb04fa1-059c-42f5-b730-837a4634c989" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-16">
              <akn:num GUID="41c6e074-2aa9-4084-9f08-0ebbede1ee92" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-16_bezeichnung-1">16.</akn:num>
              <akn:content GUID="38195bdb-d9fd-4fba-841c-408bfc2b3236" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-16_inhalt-1">
                <akn:p GUID="21d521ab-7fa6-4c02-94b7-4853454b01cc" eId="hauptteil-1_art-2_abs-2_untergl-1_listenelem-16_inhalt-1_text-1">über keine dauerhafte Vertretung in Deutschland verfügen und sich für Menschenrechte, Demokratie, Rechtsstaatlichkeit, humanitäre Belange oder Fragen der Nachhaltigkeit einsetzen und ihr Wirken primär auf andere Länder oder Weltregionen ausgerichtet ist.</akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="7e52b89e-8523-4eed-a808-24f807451243" eId="hauptteil-1_art-2_abs-3">
          <akn:num GUID="3c759681-8e6f-4501-a505-f98ac5c47cfe" eId="hauptteil-1_art-2_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:list GUID="e92ad226-a1ae-4318-b672-e11dd0dd1fb6" eId="hauptteil-1_art-2_abs-3_untergl-1">
            <akn:intro GUID="0c037e63-884c-4733-920d-2ea00acf9cb5" eId="hauptteil-1_art-2_abs-3_untergl-1_intro-1">
              <akn:p GUID="67dd7c27-f6fe-4080-80ad-e397d09218ee" eId="hauptteil-1_art-2_abs-3_untergl-1_intro-1_text-1">Interessenvertreterinnen oder Interessenvertreter müssen sich bei Interessenvertretung gegenüber der Bundesregierung nicht eintragen, wenn und soweit sie</akn:p>
            </akn:intro>
            <akn:point GUID="8fed3e96-be06-4b49-b6d2-0e136c66c07f" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-1">
              <akn:num GUID="f1f51431-26d9-40be-a5da-3f314f82efc3" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:content GUID="f4bcc8a4-7b93-4ef2-a858-6dc5962b6bee" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="3bf185c8-1335-4f0b-bf2c-ffdf16a45520" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> einen Anspruch auf gesetzlich geregelten Informationszugang geltend machen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="fbb41e50-43e1-499c-8b07-4c9767f4ef1c" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-2">
              <akn:num GUID="ffed4aa0-560e-4117-a6f1-6fb148ad6ee6" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:content GUID="eae01a33-8533-438a-a04d-60ee467004ff" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="007af134-dc66-413a-a341-d068b2aa1641" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">eine Bürgeranfrage stellen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="5960c559-a807-4b42-8531-4f5ae6b0f1fc" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-3">
              <akn:num GUID="95944f78-86d2-4601-a890-1e33ee7a86d2" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
              <akn:content GUID="de3462b2-69ed-4005-afad-5386279dc76c" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="ff9ca23f-ad71-4196-8385-4dfab26b0541" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-3_inhalt-1_text-1">an Besuchsprogrammen, Vorträgen, Konferenzen und sonstigen öffentlichen Veranstaltungen der Bundesregierung teilnehmen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="36d4730f-5f63-4346-803c-b911281cd829" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-4">
              <akn:num GUID="eb930ad4-5454-426e-9ffb-174e9cb40853" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-4_bezeichnung-1">4.</akn:num>
              <akn:content GUID="1283bb04-f40d-4dd5-830c-b99e0df77cd5" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="76ef632c-2c3a-464b-bcab-97d2a82fa7ab" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-4_inhalt-1_text-1">für die von der Bundesregierung eingerichteten Sachverständigenräte und sonstigen Expertengremien tätig sind,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="4c683f48-0780-4253-b56a-921cec45669b" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-5">
              <akn:num GUID="ac7a58fe-0d55-4c53-89d3-05861ab6d14f" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-5_bezeichnung-1">5.</akn:num>
              <akn:content GUID="76cdef13-0036-4343-9d5c-f08319c98dff" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-5_inhalt-1">
                <akn:p GUID="eda3f8a8-6750-4517-8700-1bc0a407be03" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-5_inhalt-1_text-1">diplomatische oder konsularische Tätigkeiten wahrnehmen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="074968e7-3a7c-4a69-84c1-2939be3e97a0" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-6">
              <akn:num GUID="cd5c844e-8df3-4a93-ac88-9416ac6973e5" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-6_bezeichnung-1">6.</akn:num>
              <akn:content GUID="cc742ba3-f8f4-4b77-8ab6-4a381964a65c" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-6_inhalt-1">
                <akn:p GUID="787f01e2-1062-45d4-87b1-3986ac7eaedd" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-6_inhalt-1_text-1">direkten und individuellen Ersuchen der Bundesregierung um Sachinformationen, Daten oder Fachwissen nachkommen oder </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="a5a9eb94-4fdf-43c6-93b3-53ef62f2a56d" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-7">
              <akn:num GUID="f10c6cef-4603-4011-927d-30bc67174e94" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-7_bezeichnung-1">7.</akn:num>
              <akn:content GUID="278f948c-cbf2-49ba-8f4d-b578405e3cec" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-7_inhalt-1">
                <akn:p GUID="68e2c94f-e1f0-407a-bfa1-e16759a98082" eId="hauptteil-1_art-2_abs-3_untergl-1_listenelem-7_inhalt-1_text-1">einer der in Absatz 2 Nummer 1 oder 6 bis 16 genannten Tätigkeiten nachgehen.</akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="ac8ec20c-7ede-41df-a93a-cd8a2fce3e3b" eId="hauptteil-1_art-2_abs-4">
          <akn:num GUID="d83b34a0-2cfa-4401-b318-1e2ae113ad14" eId="hauptteil-1_art-2_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="b17d93c1-c5af-4785-bcbd-45b68fc19429" eId="hauptteil-1_art-2_abs-4_inhalt-1">
            <akn:p GUID="03b374be-7abf-4ac4-bfde-dd896afac155" eId="hauptteil-1_art-2_abs-4_inhalt-1_text-1">Der Eintragungspflicht unterliegt auch nicht, wer für die unter Absatz 2 Nummer 7, 11, 12, 15 oder 16 genannten Interessenvertreterinnen und Interessenvertreter im Rahmen ihrer dort bezeichneten Tätigkeiten tätig wird.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="1b43dbc3-9418-404f-a12d-48f81abb0c31" eId="hauptteil-1_art-2_abs-5">
          <akn:num GUID="4a2b218f-236a-407e-9c5d-e2c3a5958046" eId="hauptteil-1_art-2_abs-5_bezeichnung-1">(5)</akn:num>
          <akn:content GUID="bac992d7-479e-4d91-a642-8e6a357a9c3a" eId="hauptteil-1_art-2_abs-5_inhalt-1">
            <akn:p GUID="867b0e07-d31e-4d8b-b180-3ea43d5e0f6c" eId="hauptteil-1_art-2_abs-5_inhalt-1_text-1">Interessenvertreterinnen und Interessenvertreter, die von der Registrierungspflicht ausgenommen sind, können sich freiwillig registrieren. Bei der freiwilligen Registrierung nach Satz 1 müssen die Interessenvertreterinnen und Interessenvertreter die Angaben nach § 3 Absatz 1 im Lobbyregister eintragen.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="c9684698-eadb-4961-9c47-f230b9a009d8" eId="hauptteil-1_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="d4d52fe0-a886-49ab-af2d-69a4bd8f6dec" eId="hauptteil-1_art-3_bezeichnung-1">§ 3</akn:num>
        <akn:heading GUID="e0ec27e9-779d-4f16-9226-7d649148573a" eId="hauptteil-1_art-3_überschrift-1">Registerinhalt</akn:heading>
        <akn:paragraph GUID="919a97c2-ac60-40f7-b701-aa4f07c8ce70" eId="hauptteil-1_art-3_abs-1">
          <akn:num GUID="d9fb901d-fa5e-48c0-8425-728e442ddc48" eId="hauptteil-1_art-3_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:list GUID="601e107a-370d-48bd-801b-da53a08657e6" eId="hauptteil-1_art-3_abs-1_untergl-1">
            <akn:intro GUID="a89f54e6-6706-47d0-940a-7c371a6c0538" eId="hauptteil-1_art-3_abs-1_untergl-1_intro-1">
              <akn:p GUID="4deca8d7-8c59-401f-bc9a-5a2c51150cdc" eId="hauptteil-1_art-3_abs-1_untergl-1_intro-1_text-1">Interessenvertreterinnen und Interessenvertreter stellen im Lobbyregister die folgenden Informationen bereit: </akn:p>
            </akn:intro>
            <akn:point GUID="43fca950-2c17-400a-a12d-a72507b84d6f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="565b1965-0708-463f-a47e-44842b47c21c" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:list GUID="babdb8e9-27aa-4edb-9521-b015e4e52742" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1">
                <akn:intro GUID="5338b853-f8d9-4542-b292-526b912a56d8" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_intro-1">
                  <akn:p GUID="d7742b9e-d648-493f-afcf-2f3dc7a696cf" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_intro-1_text-1">wenn sie natürliche Personen sind,</akn:p>
                </akn:intro>
                <akn:point GUID="b4d05356-802e-49e5-bcd5-65c650cbf3c4" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1">
                  <akn:num GUID="d1409a11-8660-4af4-a4e2-9753e6385b5a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                  <akn:content GUID="d87e2b3f-5f60-42b5-a715-bac9014c7b0a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="d4fdc16c-121e-4e6d-8d15-f5d13617008e" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">Familienname, Geburtsname, Vornamen, akademischer Grad (optional),</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="fc6a1757-63aa-4a16-a3e0-0a2beca28bf1" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2">
                  <akn:num GUID="8a96a2df-bcf3-4820-875e-08d49221748f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                  <akn:content GUID="f3aeb98a-56d7-43f8-832e-f2fcfe04260b" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="6feb914c-f19e-4317-a2d5-48c588a7de3d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">Geburtsdatum und Geburtsort, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="ee58ce1e-4033-41de-8776-88293bf48fc1" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3">
                  <akn:num GUID="2eefbc21-8155-400f-a570-6b22629f40d4" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                  <akn:content GUID="394526e9-88ee-43fb-8a87-969ae54df9cb" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="33da31ec-b511-4b66-ac49-c2b84da32ba8" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1_text-1">Anschrift,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="fa65903a-d813-4db3-aa5d-a5943ac16d24" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-4">
                  <akn:num GUID="3471d30c-4db4-42f5-af18-b42f1f7f6e7e" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-4_bezeichnung-1">d)</akn:num>
                  <akn:content GUID="2b40240c-0b37-4bf6-99c3-715671c643ac" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1">
                    <akn:p GUID="9142e3ef-06e3-46f8-a313-d00659fd3093" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1_text-1">elektronische Kontaktdaten,</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="7a8f3a91-db30-4ea3-b2a5-cdc5545b66f0" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="f3981139-5cfe-42d6-bc53-b47c2de90d7f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:list GUID="2010f22b-7b8d-4d37-bca4-ec762281f722" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1">
                <akn:intro GUID="ad22e549-b405-4d5d-b7ec-93704ca6d685" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_intro-1">
                  <akn:p GUID="90e22599-9a06-4a19-b7e3-e4e30a9a6593" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_intro-1_text-1">wenn sie juristische Personen, Personengesellschaften oder sonstige Organisationen sind</akn:p>
                </akn:intro>
                <akn:point GUID="4d09dc51-c25f-4089-9ee5-72220ee0f306" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-1">
                  <akn:num GUID="ef84ec22-13b5-4d01-a09b-6c555b1e0d91" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                  <akn:content GUID="aa4065d5-c302-4971-b770-18defc492e2a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="4b67d822-a4c0-49c6-a966-946ef8dff1b4" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1">Firma, Name oder Bezeichnung der Organisation, deren Webseite, E-Mail-Adresse und Anschrift, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="dfaf7722-9069-47cb-8004-f6ed53e0ca39" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-2">
                  <akn:num GUID="f3b1c03f-411d-4051-a0d4-6632aa66ae57" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                  <akn:content GUID="b638cd21-696c-4db3-9609-f39f67dc5b5d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="56efc9f8-e520-4bd7-b073-7691270071d4" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1">Rechtsform oder Art der Organisation,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="12e0502d-0f28-46e2-9532-00016f3a9c9f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-3">
                  <akn:num GUID="74774475-77d8-4c4e-8f9c-d95bf8b92364" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                  <akn:content GUID="d30055b1-97d0-4d46-8a7d-1dacb6e749b0" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="168b410b-fcea-43be-a342-8ae243e45f35" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1_text-1">Familienname, Vornamen, akademischer Grad (optional) und elektronische Kontaktdaten aller gesetzlichen Vertretungen oder sonstigen vertretungsberechtigen Personen, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="dc9fb669-706f-4ca0-8fcc-d21b03f56a8d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-4">
                  <akn:num GUID="e6b698e1-c9e1-4194-8ffe-de2094e74a8d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-4_bezeichnung-1">d)</akn:num>
                  <akn:content GUID="0164a362-79be-43b6-a62a-9b300fb81413" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-4_inhalt-1">
                    <akn:p GUID="3383f19b-04a1-43fe-af7f-887f99c38425" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-4_inhalt-1_text-1">Familienname, Geburtsname, Vornamen, akademischer Grad (optional) der Beschäftigten, die die Interessenvertretung unmittelbar ausüben, soweit nicht nach Buchstabe c erfasst,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="1850a725-74c3-4a40-914c-3ac711896a24" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-5">
                  <akn:num GUID="2cd9fda9-fe5b-41ff-bf29-a59a3a40ef3e" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-5_bezeichnung-1">e)</akn:num>
                  <akn:content GUID="c9d09a5e-ee29-4e4f-a668-7299855d899a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-5_inhalt-1">
                    <akn:p GUID="bde28346-2645-424d-9982-a8bb79c793fd" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-2_untergl-1_listenelem-5_inhalt-1_text-1">Mitgliederzahl und Mitgliedschaften, </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="cb0227f9-5be7-4431-a204-ac093563fb5d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-3">
              <akn:num GUID="08a6d0e3-3ee6-4931-8790-89dcb252f877" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
              <akn:content GUID="87983a86-aed0-47f2-bced-80e4a15c32a7" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="1ee43f9f-7a9e-4cb2-a499-303edc6ba6dc" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> Interessen- und Vorhabenbereich sowie Beschreibung der Tätigkeit, </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="9f9b1608-7a23-450e-a171-bd7d3bdc6fc5" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-4">
              <akn:num GUID="36577f67-7cd9-4c3c-ae03-3a785cc9dd7e" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-4_bezeichnung-1">4.</akn:num>
              <akn:content GUID="1c65926e-a3b7-4238-b632-d5da8922d526" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="dc458448-8fa2-4d68-824f-e568b8161ebe" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">Angaben zur Identität von Auftraggeberinnen und Auftraggebern, für welche Interessenvertretung betrieben wird; die Nummern 1 und 2 Buchstabe a bis c gelten entsprechend, </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="8ddb3608-5f94-4ea0-a2b5-115cf230f96f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-5">
              <akn:num GUID="52fd5b56-4879-4188-91a0-189c76fd1312" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-5_bezeichnung-1">5.</akn:num>
              <akn:content GUID="07b13218-b386-446c-b6ed-08603c9a0663" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-5_inhalt-1">
                <akn:p GUID="e3a50c85-b61b-4818-b7de-51fe2d307f42" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">Anzahl der Beschäftigten in Stufen von jeweils zehn Beschäftigten im Bereich der Interessenvertretung,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="7461bc00-ebee-491d-b26e-55b21ed06e29" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-6">
              <akn:num GUID="15b74077-cfb9-4a53-9af9-eba0d74be6fb" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-6_bezeichnung-1">6.</akn:num>
              <akn:content GUID="8911693f-e229-49e6-b265-93495ade2e38" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-6_inhalt-1">
                <akn:p GUID="a34415c6-d202-4b81-bb27-4011bdb8da4c" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-6_inhalt-1_text-1">Angaben zu den jährlichen finanziellen Aufwendungen im Bereich der Interessenvertretung in Stufen von jeweils 10 000 Euro, </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="ef7d70a7-dc21-493b-bbba-4be7565c9273" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7">
              <akn:num GUID="57293600-cccb-46fe-8afa-1e39f06b2c6a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_bezeichnung-1">7.</akn:num>
              <akn:list GUID="9ad56c6f-fa7c-4258-9a7d-a6daa1b31b51" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1">
                <akn:intro GUID="1cad020b-1d05-4afc-b1f6-8ccd0e421f09" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_intro-1">
                  <akn:p GUID="846c4737-05a0-4243-8907-0136fc6924fa" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_intro-1_text-1">Angaben zu einzelnen Zuwendungen und Zuschüssen der öffentlichen Hand sowie zu einzelnen Schenkungen Dritter in Stufen von jeweils 10 000 Euro, sofern jeweils ein Betrag von 20 000 Euro oder der Gesamtwert von 20 000 Euro bezogen auf eine Geberin oder einen Geber in einem Kalenderjahr überschritten wird, nämlich </akn:p>
                </akn:intro>
                <akn:point GUID="28a739ff-b9b8-4aef-bc07-90a2cfe5d90c" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-1">
                  <akn:num GUID="6a6c959a-3592-4e7d-ab03-1ef40bae6c8d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                  <akn:content GUID="0a09e1bb-0e1c-49f5-b350-21cd305818a1" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="e5e9ce76-34b7-4f8d-8fa4-fe64cb3e7b55" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-1_inhalt-1_text-1">Name, Firma oder Bezeichnung der Geberin oder des Gebers, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="0f4d6fb7-46ca-43af-90d0-7491cbcd7984" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-2">
                  <akn:num GUID="467ebef2-c7da-47b6-9e5f-8908922fe5e3" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                  <akn:content GUID="13c223ab-3bff-4f86-80cb-bdc248645a9f" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="34f59996-4cb5-42b2-b15c-be65ae0bca14" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-2_inhalt-1_text-1">Wohnort oder Sitz der Geberin oder des Gebers,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="ea728ae6-73e7-4b54-b0c4-9ac2d7fa8a39" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-3">
                  <akn:num GUID="5ddf5d98-259f-4932-827f-965bee3bf32d" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                  <akn:content GUID="92d7beb2-5618-414d-96b0-f901d39948ce" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="2b47dec1-e09d-4749-b174-a8063e878db8" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-3_inhalt-1_text-1">eine kurze Beschreibung der Leistung,</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="69f4033d-0763-49b2-9935-7f6866fbc870" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-8">
              <akn:num GUID="4346ca49-8282-4101-b7c3-0296953e9d6a" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-8_bezeichnung-1">8.</akn:num>
              <akn:content GUID="ec144654-7992-47a6-b658-e3731d9c7ef8" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-8_inhalt-1">
                <akn:p GUID="5ba0e1b6-5e95-414e-a5ac-00408b16366e" eId="hauptteil-1_art-3_abs-1_untergl-1_listenelem-8_inhalt-1_text-1">Jahresabschlüsse oder Rechenschaftsberichte von juristischen Personen, falls keine handelsrechtlichen Offenlegungspflichten bestehen.</akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="4234723d-a337-4e23-8530-e462253c2e1c" eId="hauptteil-1_art-3_abs-2">
          <akn:num GUID="323e1a6d-4482-4446-bfce-2c236a3e00f2" eId="hauptteil-1_art-3_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="4624e8d9-2c30-4317-b7f1-457f38bf5c85" eId="hauptteil-1_art-3_abs-2_inhalt-1">
            <akn:p GUID="c7bbafef-e043-4045-b3f2-b613b41afc5d" eId="hauptteil-1_art-3_abs-2_inhalt-1_text-1">Die Angaben nach Absatz 1 Nummer 6 bis 8 können verweigert werden. Die Verweigerung wird im Lobbyregister vermerkt. Zudem erfolgt eine Ausweisung der die Angaben verweigernden Interessenvertreterinnen und Interessenvertreter in einer gesonderten öffentlichen Liste im Lobbyregister.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="45ac16a3-272b-4805-ab77-4bad56bf7e85" eId="hauptteil-1_art-3_abs-3">
          <akn:num GUID="f1fe4083-e3ce-4869-a0c0-e44e187da577" eId="hauptteil-1_art-3_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="15d4ae93-88b4-4a0d-afa7-5afaf5d23b88" eId="hauptteil-1_art-3_abs-3_inhalt-1">
            <akn:p GUID="29de303e-6b37-4058-9ba4-7f4e554d6408" eId="hauptteil-1_art-3_abs-3_inhalt-1_text-1">Die Interessenvertreterinnen und Interessenvertreter haben die Angaben nach Absatz 1 mindestens einmal jährlich zu aktualisieren. Änderungen bei Angaben nach Absatz 1 Nummer 1 Buchstabe a, c und d und Nummer 2 Buchstabe a bis d sind spätestens bis Ende des auf den Eintritt der Änderung folgenden Quartals einzutragen. Änderungen nach Absatz 1 Nummer 4 sind unverzüglich einzutragen. Soweit die Angaben nach Absatz 1 Nummer 6 bis 8 nicht verweigert werden, sind diese spätestens sechs Monate nach dem Ende des Geschäftsjahres für das abgelaufene Geschäftsjahr zu aktualisieren. Dies gilt auch für die Angaben nach Absatz 1 Nummer 2 Buchstabe e.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="3e6ea21c-8143-4e22-93de-47c2b5a91e4d" eId="hauptteil-1_art-3_abs-4">
          <akn:num GUID="fc8da6da-e175-46b3-980e-04c65617bfa5" eId="hauptteil-1_art-3_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="d816a820-ba91-4c9c-bf46-861caa5b44a9" eId="hauptteil-1_art-3_abs-4_inhalt-1">
            <akn:p GUID="405f51f7-60dc-4c41-b13c-379e3a11149e" eId="hauptteil-1_art-3_abs-4_inhalt-1_text-1">Im Lobbyregister wird eine Liste früherer Interessenvertreterinnen und Interessenvertreter im zuletzt aktualisierten Datenumfang geführt und entsprechend veröffentlicht. In diese werden Interessenvertreterinnen und Interessenvertreter eingetragen, die dem Deutschen Bundestag anzeigen, dass sie keine Interessenvertretung mehr betreiben oder deren Eintrag gemäß § 4 Absatz 4 Satz 3 in diese Liste übertragen wird. Die Entfernung aus der Liste erfolgt nach Ablauf von 18 Monaten, die Daten werden weitere 18 Monate bei der registerführenden Stelle gespeichert.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="92fc7462-0a66-4670-bd4e-d0a5dffa9f8d" eId="hauptteil-1_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="e7eb16b1-ee35-462f-a40d-e888b915603a" eId="hauptteil-1_art-4_bezeichnung-1">§ 4</akn:num>
        <akn:heading GUID="1f29c6ac-76b2-448e-8530-57faf23cd4a3" eId="hauptteil-1_art-4_überschrift-1">Registereinrichtung und Registerführung</akn:heading>
        <akn:paragraph GUID="075e0a84-8607-4da1-8ed3-8d05cd8b5cb9" eId="hauptteil-1_art-4_abs-1">
          <akn:num GUID="181a75a5-845a-4fda-9e6d-40cdc3a13593" eId="hauptteil-1_art-4_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="703e3b06-b8d7-4fb5-a67b-7f57d5142172" eId="hauptteil-1_art-4_abs-1_inhalt-1">
            <akn:p GUID="5bab3208-5920-4f68-b7cd-d294eb1b7811" eId="hauptteil-1_art-4_abs-1_inhalt-1_text-1">Das Lobbyregister wird elektronisch beim Deutschen Bundestag eingerichtet und geführt. Der Deutsche Bundestag und die Bundesregierung schließen eine Verwaltungsvereinbarung über die Einzelheiten der Führung des Lobbyregisters.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="3b06519c-32ee-46b6-89f9-96ada08a8694" eId="hauptteil-1_art-4_abs-2">
          <akn:num GUID="4ea187b1-71e7-460f-8caf-7e8409b1535d" eId="hauptteil-1_art-4_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="1200ac89-a1b2-423e-8910-280edac144a6" eId="hauptteil-1_art-4_abs-2_inhalt-1">
            <akn:p GUID="7944bd09-176d-45c1-b6bb-4901201543ec" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1">Die Interessenvertreterinnen und Interessenvertreter nehmen die Eintragung elektronisch unter Nutzung des im Internet angebotenen Zugangs beim Deutschen Bundestag vor. Die Eintragungen werden maschinenlesbar und mit einer Suchfunktion veröffentlicht, mit Ausnahme der Angaben nach § 3 Absatz 1 Nummer 1 Buchstabe b bis d und Nummer 7 Buchstabe b sowie des Geburtsnamens und weiterer Vornamen, wenn es sich um eine natürliche Person handelt</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="01b72cdc-89fc-4933-aca9-b43ba3a66283" eId="hauptteil-1_art-4_abs-3">
          <akn:num GUID="d08209bc-7db3-4a54-a114-50d8243b9479" eId="hauptteil-1_art-4_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="4ad949a0-3208-4722-a44a-e8fc6aa6a32b" eId="hauptteil-1_art-4_abs-3_inhalt-1">
            <akn:p GUID="3581b871-d650-420a-bdd6-cf8a26bf0400" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1">Der Zeitpunkt der Eintragung in das Lobbyregister und der Zeitpunkt der letzten Aktualisierung werden automatisch ausgewiesen.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="a5f2bae0-f7a9-45e3-8939-84a629de3b4a" eId="hauptteil-1_art-4_abs-4">
          <akn:num GUID="8f845b09-852e-4d6b-befb-6979103e89e9" eId="hauptteil-1_art-4_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="0efa92c8-c4e9-4b8a-86ab-732d17858157" eId="hauptteil-1_art-4_abs-4_inhalt-1">
            <akn:p GUID="1cd4039f-b71a-4bda-b5fe-90770e70d460" eId="hauptteil-1_art-4_abs-4_inhalt-1_text-1">Werden die Angaben nach § 3 Absatz 1 länger als ein Jahr nicht aktualisiert, werden die betroffenen Interessenvertreterinnen und Interessenvertreter durch elektronische Benachrichtigung aufgefordert, die Eintragung zu aktualisieren. Nehmen sie darauf nicht innerhalb von drei Wochen eine Aktualisierung vor, wird die Eintragung als „nicht aktualisiert“ gekennzeichnet. Aktualisieren die Interessenvertreterinnen und Interessenvertreter die Angaben innerhalb von sechs Monaten nach der Benachrichtigung nach Satz 1 nicht, werden die betroffenen Interessenvertreterinnen und Interessenvertreter elektronisch darüber benachrichtigt, dass die Eintragung in einem Monat vom aktiven Lobbyregister in die Liste nach § 3 Absatz 4 übertragen wird.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="603cd0f4-326a-4ff3-b788-bedb9456ada6" eId="hauptteil-1_art-4_abs-5">
          <akn:num GUID="7c2c722a-0c0b-44d1-8143-e15b7dc412d7" eId="hauptteil-1_art-4_abs-5_bezeichnung-1">(5)</akn:num>
          <akn:content GUID="5c99e452-1c74-4b56-98f9-1c0680d26294" eId="hauptteil-1_art-4_abs-5_inhalt-1">
            <akn:p GUID="e3e0a529-6a2a-437d-9eb0-a3a6793ff859" eId="hauptteil-1_art-4_abs-5_inhalt-1_text-1">Über die Begrenzung des Absatzes 2 Satz 2 hinaus beschränkt die registerführende Stelle auf Antrag die Veröffentlichung der eingetragenen Angaben (§ 3 Absatz 1) vollständig oder teilweise, wenn ihr die Interessenvertreterin oder der Interessenvertreter darlegt, dass der Veröffentlichung unter Berücksichtigung aller Umstände des Einzelfalls überwiegende schutzwürdige Interessen der Interessenvertreterin oder des Interessenvertreters oder der nach § 3 Absatz 1 Nummer 2 oder 4 einzutragenden Personen entgegenstehen. Schutzwürdige Interessen liegen vor, wenn Tatsachen die Annahme rechtfertigen, dass die Veröffentlichung in Satz 1 genannte Personen der Gefahr aussetzen würde, Opfer eines Verbrechens oder eines Vergehens nach den §§ 124, 223, 224, 240 oder 241 des Strafgesetzbuches zu werden. </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="2f0fb992-87b3-4da6-b596-967894c94f86" eId="hauptteil-1_art-4_abs-6">
          <akn:num GUID="00b25d18-efad-45ad-8b81-ff9e273c3e21" eId="hauptteil-1_art-4_abs-6_bezeichnung-1">(6)</akn:num>
          <akn:content GUID="16682de1-da1d-4980-b596-6ecc697add96" eId="hauptteil-1_art-4_abs-6_inhalt-1">
            <akn:p GUID="d909afd9-8490-4426-a04c-d2ea7d945896" eId="hauptteil-1_art-4_abs-6_inhalt-1_text-1">Bei der Führung des Registers wird durch geeignete technische und organisatorische Maßnahmen sichergestellt, dass die Vertraulichkeit nicht öffentlicher Angaben gewahrt wird. Eine Nutzung bleibt unberührt, soweit dieses zur ordnungsgemäßen Registerführung und für Verfahren nach § 7 erforderlich ist. Auf individuelle Anfrage von Mitgliedern des Deutschen Bundestages und Bundesministerien darf Auskunft darüber erteilt werden, ob eine Eintragung vorliegt. Im Übrigen bestehen keine Informationszugangsansprüche auf Grundlage anderer Rechtsvorschriften in Bezug auf die nicht öffentlichen Inhalte des Registers und sonstige hiermit in Verbindung stehenden Informationen. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="1acb20c7-dc30-44ce-8a53-24dfe439ef61" eId="hauptteil-1_art-5" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="faed353b-5904-415a-849b-445473ff3766" eId="hauptteil-1_art-5_bezeichnung-1">§ 5</akn:num>
        <akn:heading GUID="c85a1b2e-9eaf-4509-81ec-8a5a5914789b" eId="hauptteil-1_art-5_überschrift-1">Grundsätze integrer Interessenvertretung</akn:heading>
        <akn:paragraph GUID="aaff12d4-59c4-47c8-b03c-3daf6fd62e5b" eId="hauptteil-1_art-5_abs-1">
          <akn:num GUID="1629efea-ede9-4f0e-b532-08f0a77bf357" eId="hauptteil-1_art-5_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="d3f23263-a62f-49b7-a528-680fa671dd6b" eId="hauptteil-1_art-5_abs-1_inhalt-1">
            <akn:p GUID="eca261d3-5592-4bad-9af9-2f578dce8b25" eId="hauptteil-1_art-5_abs-1_inhalt-1_text-1">Interessenvertretung im Sinne des Gesetzes darf nur auf Basis von Offenheit, Transparenz, Ehrlichkeit und Integrität stattfinden.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="804ccd44-1629-4e33-8da7-278a7ddc44a1" eId="hauptteil-1_art-5_abs-2">
          <akn:num GUID="493e522b-705b-4cc1-b107-9136234a5318" eId="hauptteil-1_art-5_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="84247fa1-46ba-432a-ba71-9aca04b2b9e6" eId="hauptteil-1_art-5_abs-2_inhalt-1">
            <akn:p GUID="4d4abc3e-8ae2-4cf7-9b40-2b1f43175af3" eId="hauptteil-1_art-5_abs-2_inhalt-1_text-1">Der Deutsche Bundestag und die Bundesregierung legen unter Beteiligung der Zivilgesellschaft einen Verhaltenskodex fest, der Vorgaben für eine Ausübung von Interessenvertretung auf der Grundlage der in Absatz 1 genannten Grundsätze enthält. </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="328f4dc3-9a02-4c37-8faa-e7d2353ef2e1" eId="hauptteil-1_art-5_abs-3">
          <akn:num GUID="96fba014-f27e-4c09-b31f-33bd905319a8" eId="hauptteil-1_art-5_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="5807b1ec-be45-4040-bc1b-f58d4ea1bec2" eId="hauptteil-1_art-5_abs-3_inhalt-1">
            <akn:p GUID="3f99c574-700e-4018-92fc-1a23e326407e" eId="hauptteil-1_art-5_abs-3_inhalt-1_text-1">Interessenvertreterinnen und Interessenvertreter akzeptieren diesen Verhaltenskodex durch ihre Eintragung im Lobbyregister. Die Angabe weiterer Verhaltenskodizes als ergänzende Grundlage für die Interessenvertretung ist möglich. </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="ebfbf038-dc7e-4b16-ab30-ec613ae039cd" eId="hauptteil-1_art-5_abs-4">
          <akn:num GUID="4d7eab28-b28f-40bf-a51f-9152be012cfa" eId="hauptteil-1_art-5_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:list GUID="c3d6690d-a3e6-4b37-9c95-a76d0a96ef78" eId="hauptteil-1_art-5_abs-4_untergl-1">
            <akn:intro GUID="1183e89a-0342-4b4a-aebb-95b211323d7a" eId="hauptteil-1_art-5_abs-4_untergl-1_intro-1">
              <akn:p GUID="82a5fde6-1dd5-4da9-bf4d-182de1a4e395" eId="hauptteil-1_art-5_abs-4_untergl-1_intro-1_text-1">Interessenvertretung muss bei jedem Kontakt gegenüber den Organen, Mitgliedern, Fraktionen oder Gruppen des Deutschen Bundestages oder der Bundesregierung transparent erfolgen. Interessenvertreterinnen und Interessenvertreter müssen</akn:p>
            </akn:intro>
            <akn:point GUID="d8bdcba6-82be-480a-af8a-6ab563c113e1" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-1">
              <akn:num GUID="1ef44326-a42b-4761-85a6-ae2bdc927bc1" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:content GUID="47b4ae64-906f-4b57-98dc-63ac8ae03723" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="466b3453-8c67-4cf1-a82e-869ac4042059" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1_text-1">ihre Identität und ihr Anliegen sowie gegebenenfalls die Identität und das Anliegen ihrer Auftraggeberin oder ihres Auftraggebers offenlegen,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="8122ce98-ffbb-43c5-a867-e41bb302a199" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-2">
              <akn:num GUID="418b01b6-3dcc-4f03-bd4e-b7dc299caebb" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:content GUID="6060abbb-9cc6-42da-a0ed-a52e417455dd" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="f92033d2-07e3-4ea9-bc23-92f9504822b8" eId="hauptteil-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1_text-1">über sich und ihren Auftrag bei der Interessenvertretung zutreffende Angaben machen.</akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="147aa775-3534-4447-a0f6-3a8294ab1fa3" eId="hauptteil-1_art-5_abs-5">
          <akn:num GUID="a86e8b0c-4db9-4035-ab9b-1a780574b94e" eId="hauptteil-1_art-5_abs-5_bezeichnung-1">(5)</akn:num>
          <akn:content GUID="65f389e8-6176-4f3e-b5eb-87ffabefb273" eId="hauptteil-1_art-5_abs-5_inhalt-1">
            <akn:p GUID="576f0a5a-5863-4843-8534-5b3c3faa7550" eId="hauptteil-1_art-5_abs-5_inhalt-1_text-1">Eingetragene Interessenvertreterinnen und Interessenvertreter haben auf ihre Eintragung bei dem erstmaligen Kontakt mit den jeweiligen Organen, Mitgliedern, Fraktionen oder Gruppen des Deutschen Bundestages oder mit den jeweiligen Mitgliedern der Bundesregierung hinzuweisen sowie die Verhaltenskodizes zu benennen, auf deren Grundlage Interessenvertretung betrieben wird. Es ist zudem darauf hinzuweisen, wenn einzelne Angaben nach § 3 Absatz 1 Nummer 6 bis 8 verweigert wurden.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="c9292dab-94ce-4b53-9191-e06daeeaf42a" eId="hauptteil-1_art-5_abs-6">
          <akn:num GUID="fb453efc-08a3-4ac4-ba61-1f1dd1a9a15e" eId="hauptteil-1_art-5_abs-6_bezeichnung-1">(6)</akn:num>
          <akn:content GUID="259686b9-634f-452c-8ab2-7de8cca5848b" eId="hauptteil-1_art-5_abs-6_inhalt-1">
            <akn:p GUID="80df7895-336a-4bbb-b5a6-bd91737639fa" eId="hauptteil-1_art-5_abs-6_inhalt-1_text-1">Vereinbarungen, durch die eine Vergütung oder ihre Höhe vom Erfolg der Interessenvertretung abhängig gemacht wird (Erfolgshonorar), sind unzulässig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="67b6e2cf-1bcd-49e4-9d69-4a3eceb5245d" eId="hauptteil-1_art-5_abs-7">
          <akn:num GUID="bc1887a6-25ae-44e3-b6a5-810f7f8fa291" eId="hauptteil-1_art-5_abs-7_bezeichnung-1">(7)</akn:num>
          <akn:content GUID="2987a489-a05a-418d-a9a2-9f1005858268" eId="hauptteil-1_art-5_abs-7_inhalt-1">
            <akn:p GUID="c67e9347-96a4-44ba-a583-2ec1cbc13db4" eId="hauptteil-1_art-5_abs-7_inhalt-1_text-1">Interessenvertreterinnen und Interessenvertreter stellen sicher, dass sämtliche Informationen, die bei der Registrierung und danach im Rahmen der in den Anwendungsbereich des Registers fallenden Tätigkeiten bereitgestellt werden, richtig, vollständig, aktuell und nicht irreführend sind und dass notwendige ergänzende Informationen und Aktualisierungen, die von der registerführenden Stelle angefordert werden, unverzüglich zur Verfügung gestellt werden. </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="126d27aa-ca2e-453b-900d-b3cef2c30fc0" eId="hauptteil-1_art-5_abs-8">
          <akn:num GUID="62c9ef75-2426-4e23-80d5-4a4d2ed28f0e" eId="hauptteil-1_art-5_abs-8_bezeichnung-1">(8)</akn:num>
          <akn:content GUID="a4fa3464-033c-4ffc-9109-e370c3d99564" eId="hauptteil-1_art-5_abs-8_inhalt-1">
            <akn:p GUID="f64d26b7-937e-4b14-994b-53826f37bd9e" eId="hauptteil-1_art-5_abs-8_inhalt-1_text-1">Stellt die registerführende Stelle nach Durchführung eines entsprechenden Prüfverfahrens fest, dass eine Interessenvertreterin oder ein Interessenvertreter nicht unerheblich gegen den Verhaltenskodex nach Absatz 2 verstoßen hat, wird diese Feststellung im Register veröffentlicht.
               Eine Löschung dieses Hinweises im Register erfolgt nach Ablauf von 24 Monaten nach Veröffentlichung des Verstoßes.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="d9993287-ca61-4f93-adf1-44b07c69aa02" eId="hauptteil-1_art-5_abs-9">
          <akn:num GUID="76474799-87e6-4001-b748-253d8eb5b897" eId="hauptteil-1_art-5_abs-9_bezeichnung-1">(9)</akn:num>
          <akn:content GUID="6c298fc1-1c43-4d17-a8ea-c8586c1fd910" eId="hauptteil-1_art-5_abs-9_inhalt-1">
            <akn:p GUID="bad31bd3-dece-4fba-b902-65cc4634d0e4" eId="hauptteil-1_art-5_abs-9_inhalt-1_text-1">Eingetragene Interessenvertreterinnen und Interessenvertreter können öffentlich die Bezeichnung „registrierte Interessenvertreterin“ oder „registrierter Interessenvertreter“ verwenden, wenn die Eintragung der Angaben nach § 3 Absatz 1 erfolgt ist, keine Angaben verweigert wurden, die Eintragung keine Kennzeichnung „nicht aktualisiert“ enthält und im Register kein Hinweis auf einen Verstoß nach § 5 Absatz 8 veröffentlicht ist.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="03e318c3-5c11-43a6-b29e-761684485b10" eId="hauptteil-1_art-6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="8346c7ef-d347-44ed-9d44-9fee7b03fe05" eId="hauptteil-1_art-6_bezeichnung-1">§ 6</akn:num>
        <akn:heading GUID="1d8e0f74-c050-4c38-8f58-2dc72573f5a5" eId="hauptteil-1_art-6_überschrift-1">Zugang zu den Gebäuden des Deutschen Bundestages und Teilnahme an öffentlichen Anhörungen</akn:heading>
        <akn:paragraph GUID="ed5eddec-ed70-411b-a355-ca1d1def4f4a" eId="hauptteil-1_art-6_abs-1">
          <akn:num GUID="be1d35d5-0807-4d26-a620-7667b7f74ac2" eId="hauptteil-1_art-6_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="20d51843-28ce-4fb3-9620-40dcef480ce8" eId="hauptteil-1_art-6_abs-1_inhalt-1">
            <akn:p GUID="913a6c72-0b7f-4b2f-bb42-b633e83212f1" eId="hauptteil-1_art-6_abs-1_inhalt-1_text-1">Der Deutsche Bundestag kann sich vorbehalten, Zugangsberechtigungen für Interessenvertreterinnen und Interessenvertreter nur zu erteilen, wenn eine entsprechende Eintragung der Angaben nach § 3 Absatz 1 erfolgt ist und die Eintragung keine Kennzeichnung „nicht aktualisiert“ und keine Feststellung eines Verstoßes nach § 5 Absatz 8 enthält. Ein Anspruch auf die Erteilung von Zugangsberechtigungen besteht nicht. Den Zugang regelt der Präsident des Deutschen Bundestages.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="4afae0f7-64c8-4d42-8a7d-c2b2101d6a63" eId="hauptteil-1_art-6_abs-2">
          <akn:num GUID="9ac0aebf-9918-4e98-b2ce-6c8a5ca7a2ed" eId="hauptteil-1_art-6_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="6ade94e3-684d-4e2f-a0f4-5c27ae35aad0" eId="hauptteil-1_art-6_abs-2_inhalt-1">
            <akn:p GUID="247a0fa6-6564-4a43-be0f-09c25a47e2fc" eId="hauptteil-1_art-6_abs-2_inhalt-1_text-1">Eine Teilnahme an öffentlichen Anhörungen der Ausschüsse des Deutschen Bundestages als Auskunftsperson soll bei eingetragenen Interessenvertreterinnen und Interessenvertretern nur stattfinden, wenn Angaben nach § 3 Absatz 1 Nummer 6 bis 8 nicht verweigert worden sind und die Eintragung keine Kennzeichnung „nicht aktualisiert“ und keine Feststellung eines Verstoßes nach § 5 Absatz 8 enthält.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="c9b0d654-e089-477e-b18b-dcab9e9c3a69" eId="hauptteil-1_art-6_abs-3">
          <akn:num GUID="ca8f5f5b-0c2c-4198-a46a-cefee3edf4b2" eId="hauptteil-1_art-6_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="37bc201e-7f99-40b2-b523-157276f6071a" eId="hauptteil-1_art-6_abs-3_inhalt-1">
            <akn:p GUID="75293cbd-0add-4e66-ab63-f5e451e999ef" eId="hauptteil-1_art-6_abs-3_inhalt-1_text-1">Eine Beteiligung nach § 47 der Gemeinsamen Geschäftsordnung der Bundesministerien soll bei eingetragenen Interessenvertreterinnen und Interessenvertretern nicht durchgeführt werden, wenn die Angaben nach § 3 Absatz 1 Nummer 6 bis 8 verweigert worden sind, die Eintragung die Kennzeichnung „nicht aktualisiert“ oder die Feststellung eines Verstoßes nach § 5 Absatz 8 enthält.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="6037fe81-e4fb-4c81-a897-c4ca9e7956eb" eId="hauptteil-1_art-7" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="78ccae5a-8444-42e2-9f1a-28a6ee20bc0e" eId="hauptteil-1_art-7_bezeichnung-1">§ 7</akn:num>
        <akn:heading GUID="a230bbc6-fcbd-4f5f-9399-b6c6c5ea7494" eId="hauptteil-1_art-7_überschrift-1">Bußgeldvorschriften</akn:heading>
        <akn:paragraph GUID="44def928-47a0-4fd6-b335-e952516b3b63" eId="hauptteil-1_art-7_abs-1">
          <akn:num GUID="87707f68-5b66-4a05-9ae5-54425c3814df" eId="hauptteil-1_art-7_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:list GUID="18595e70-49d1-4777-8d50-5a1bd455199b" eId="hauptteil-1_art-7_abs-1_untergl-1">
            <akn:intro GUID="65c554f8-f85e-42ce-8801-d46bd2d94581" eId="hauptteil-1_art-7_abs-1_untergl-1_intro-1">
              <akn:p GUID="f8871e8c-7024-4015-928b-ab05a191861e" eId="hauptteil-1_art-7_abs-1_untergl-1_intro-1_text-1">Ordnungswidrig handelt, wer</akn:p>
            </akn:intro>
            <akn:point GUID="c6c12465-fdd1-409b-b355-917e198b519f" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="caf562a3-ce99-4b86-aebd-12b9ef424abf" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
              <akn:content GUID="951c4957-f727-4293-b0d3-999d2879a5d6" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="6754dfce-98ea-436a-bef6-6113da7bcd44" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen § 2 Absatz 1 Satz 1 eine Angabe nicht, nicht richtig, nicht vollständig oder nicht rechtzeitig einträgt,</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="cca5d166-8f24-4cee-8443-1bb32d9c62da" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="7dda396a-7135-46b6-988f-fc1564a0fc8c" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
              <akn:content GUID="75437d55-c5bd-4b91-bbf0-7149766e3ef0" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="7615c310-f3a9-4d52-a8e3-195003aaa230" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 2 Absatz 5 Satz 2 eine Angabe nicht richtig oder nicht vollständig einträgt oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="d5081b95-4d28-4446-af79-5a706f95624a" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-3">
              <akn:num GUID="1de4879f-a262-4ad2-bf8b-90801b699e31" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
              <akn:content GUID="4277d665-72f1-4d5e-967c-fc576950addf" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="f2a0f6ac-4967-4a9c-907c-2eeee51ed00f" eId="hauptteil-1_art-7_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">entgegen § 3 Absatz 3 Satz 1, auch in Verbindung mit den Sätzen 2 bis 5, eine Angabe nicht, nicht richtig, nicht vollständig oder nicht rechtzeitig aktualisiert. </akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="bf948cf7-1d06-40f9-a09d-8114a85bfe12" eId="hauptteil-1_art-7_abs-2">
          <akn:num GUID="5089dc21-117e-402e-a47d-de21dd6ceb8d" eId="hauptteil-1_art-7_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="43c849c4-bdcc-4107-96ec-e6f4705b8fc7" eId="hauptteil-1_art-7_abs-2_inhalt-1">
            <akn:p GUID="5f141fe0-1f1c-4da1-802e-bf0a6adf4c96" eId="hauptteil-1_art-7_abs-2_inhalt-1_text-1">Ordnungswidrig handelt, wer eine in Absatz 1 bezeichnete Handlung fahrlässig begeht. </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="b60e9188-4c91-41b9-b0e7-e0109e1cbaba" eId="hauptteil-1_art-7_abs-3">
          <akn:num GUID="ca92c406-bf80-403d-815f-107c0435e50d" eId="hauptteil-1_art-7_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="628d3808-da6d-4264-9f38-a9aa7de130ac" eId="hauptteil-1_art-7_abs-3_inhalt-1">
            <akn:p GUID="5c5a474a-89dc-4c74-b530-8ece7777541a" eId="hauptteil-1_art-7_abs-3_inhalt-1_text-1">Die Ordnungswidrigkeit kann in den Fällen des Absatzes 1 mit einer Geldbuße bis zu fünfzigtausend Euro und in den Fällen des Absatzes 2 mit einer Geldbuße bis zu zwanzigtausend Euro geahndet werden.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="ec670b40-1295-4370-a821-1996e343e5f0" eId="hauptteil-1_art-7_abs-4">
          <akn:num GUID="b6250442-70b0-4340-84bd-175a6512a84f" eId="hauptteil-1_art-7_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="2491c3dd-0f13-4cbd-95c6-e5b6c6a88154" eId="hauptteil-1_art-7_abs-4_inhalt-1">
            <akn:p GUID="0e07306b-1fdc-4fee-80eb-4088c4088d1d" eId="hauptteil-1_art-7_abs-4_inhalt-1_text-1">Verwaltungsbehörde im Sinne des § 36 Absatz 1 Nummer 1 des Gesetzes über Ordnungswidrigkeiten ist der Direktor beim Deutschen Bundestag.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="73db0532-b6bd-4780-ad8a-76728dfba2cb" eId="hauptteil-1_art-8" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="867613f8-023c-488f-a6d4-9bc671c3219f" eId="hauptteil-1_art-8_bezeichnung-1">§ 8</akn:num>
        <akn:heading GUID="7f108760-43a9-4bb0-af7c-214f71120fe6" eId="hauptteil-1_art-8_überschrift-1">Übergangsvorschrift</akn:heading>
        <akn:paragraph GUID="af00e1b7-0ded-4781-8801-bf9d8d29a23b" eId="hauptteil-1_art-8_abs-1">
          <akn:num GUID="c36450e9-d91d-4839-a95e-bb9e401acf92" eId="hauptteil-1_art-8_abs-1_bezeichnung-1"/>
          <akn:content GUID="293143cc-dfd2-4d60-97b1-969adbcfb1f2" eId="hauptteil-1_art-8_abs-1_inhalt-1">
            <akn:p GUID="71736fb3-014c-45b1-acd0-0307d11a5a05" eId="hauptteil-1_art-8_abs-1_inhalt-1_text-1">Eintragungen nach § 2 Absatz 1, die innerhalb von zwei Monaten nach Inkrafttreten des Gesetzes vorgenommen werden, gelten als unverzüglich im Sinne des § 2 Absatz 1 Satz 2. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="2d0d1a73-cad3-4abd-acac-5251d7d3cbaa" eId="hauptteil-1_art-9" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="41feab3f-c88d-400e-92d1-f9b5d5436807" eId="hauptteil-1_art-9_bezeichnung-1">§ 9</akn:num>
        <akn:heading GUID="806ab06f-67f3-4687-8b98-1471e3f74458" eId="hauptteil-1_art-9_überschrift-1">Bericht und Evaluierung</akn:heading>
        <akn:paragraph GUID="044b87e9-39a1-4351-8cfe-e946de58a0cd" eId="hauptteil-1_art-9_abs-1">
          <akn:num GUID="ff0342a7-373e-45f8-a650-72bc61846bb0" eId="hauptteil-1_art-9_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="4b554ce6-699d-4b1a-ade8-684b0d88a41c" eId="hauptteil-1_art-9_abs-1_inhalt-1">
            <akn:p GUID="d4e398f2-060f-4723-b02c-666bf877bae1" eId="hauptteil-1_art-9_abs-1_inhalt-1_text-1">Der Deutsche Bundestag und die Bundesregierung veröffentlichen alle zwei Jahre einen Bericht über die Anwendung des Lobbyregisters, erstmalig zum 31. März 2024 für die vergangenen zwei Kalenderjahre.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="83b928ee-7c3d-490c-9008-2217ccd52a42" eId="hauptteil-1_art-9_abs-2">
          <akn:num GUID="eb0c69c2-86e9-4d8e-aa9d-49d3bd595d4d" eId="hauptteil-1_art-9_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="c005699b-89e4-4729-a6d7-0377224d3133" eId="hauptteil-1_art-9_abs-2_inhalt-1">
            <akn:p GUID="7b21edf9-8040-4d99-b0c2-c1d07a530e09" eId="hauptteil-1_art-9_abs-2_inhalt-1_text-1">Der Deutsche Bundestag und die Bundesregierung überprüfen die Auswirkungen dieses Gesetzes erstmalig fünf Jahre nach Inkrafttreten des Gesetzes und veröffentlichen die Ergebnisse der Überprüfung. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article refersTo="geltungszeitregel" GUID="e8f27756-725e-417c-801d-6eaef825e2ed" eId="hauptteil-1_art-10" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="22137baa-309a-421c-8e8c-9b79708fc276" eId="hauptteil-1_art-10_bezeichnung-1">§ 10</akn:num>
        <akn:heading GUID="bbd5e43c-1c6f-457c-8bc5-7a75e33c79f0" eId="hauptteil-1_art-10_überschrift-1">Inkrafttreten</akn:heading>
        <akn:paragraph GUID="5df6c2ef-5c32-444c-ba1e-0678418c6d49" eId="hauptteil-1_art-10_abs-1">
          <akn:num GUID="fa706514-ac73-4446-b6d9-b460ba892a6f" eId="hauptteil-1_art-10_abs-1_bezeichnung-1"/>
          <akn:content GUID="f2127c41-0715-4d31-a202-69e2fd920c39" eId="hauptteil-1_art-10_abs-1_inhalt-1">
            <akn:p GUID="5feca2c0-d184-40c8-9bf2-60742e487ebf" eId="hauptteil-1_art-10_abs-1_inhalt-1_text-1">Dieses Gesetz tritt am <akn:date date="2022-01-01" refersTo="inkrafttreten-datum" GUID="d084a756-e58c-4f89-a226-795a0eb387a0" eId="hauptteil-1_art-10_abs-1_inhalt-1_text-1_datum-1">1. Januar 2022</akn:date> in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="0760065f-ed8c-44df-a26c-30f16ebca789" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="01c92508-54d3-41e5-b5ff-8e93811dd0f2" eId="schluss-1_formel-1">
        <akn:p GUID="58fd9ad5-8912-4873-8a96-16fffadbe50d" eId="schluss-1_formel-1_text-1">Die verfassungsmäßigen Rechte des Bundesrates sind gewahrt.</akn:p>
        <akn:p GUID="921345f5-5f0e-4213-9d7f-5501f6836495" eId="schluss-1_formel-1_text-2">Das vorstehende Gesetz wird hiermit ausgefertigt. Es ist im Bundesgesetzblatt zu verkünden. </akn:p>
      </akn:formula>
      <akn:blockContainer GUID="d6956e4a-3479-47c1-b7d1-7db845d23694" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="e348478d-7168-4b38-ac9a-54b7b4789f52" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="3f5bf850-fc74-4a11-900d-18f7108b7881" eId="schluss-1_blockcontainer-1_text-1_ort-1">Berlin</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="2021-04-16" GUID="f7055fba-c88e-41d8-8f4a-1c0516e39ee4" eId="schluss-1_blockcontainer-1_text-1_datum-1">16. April 2021</akn:date>
        </akn:p>
        <akn:signature GUID="85b0f98e-8f9a-4d27-a412-966a9a03f10d" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="053d3828-4d84-4cc5-a3a6-3b1f9339375d" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="8cb2877f-7d95-4d9e-a6e0-f7a13b1aa261" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Steinmeier</akn:person>
        </akn:signature>
        <akn:signature GUID="28934663-f4c7-4412-a6c8-342fbfbf3d53" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="e31e13f8-ef1b-4692-af41-6c4f17ecc355" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Die Bundeskanzlerin</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="d0a34f02-a4ae-4d12-877a-e87905d5b9be" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Dr. Angela Merkel</akn:person>
        </akn:signature>
        <akn:signature GUID="acd822c5-437c-4c1c-9c71-cebd509640bb" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="a32c5700-b05f-463e-90f5-90f589712ac5" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern, für Bau und Heimat</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="f8b98e03-c32b-4dcf-b3ad-6c1042b3d4ae" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Horst Seehofer</akn:person>
        </akn:signature>
        <akn:signature GUID="74b952ff-637e-4b8f-8f7e-d80216658a5f" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="2e449461-f486-456b-a0e5-9ab0d5afdfb6" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Die Bundesministerin der Justiz und für Verbraucherschutz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="847604ba-d62d-4142-822e-7352445a1eec" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Christine Lambrecht</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu';
