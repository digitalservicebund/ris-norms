-- Target law current expression
-- TARGET LAW
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/1964-10-01/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/1964-10-01/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/1964-10-01/1/deu';

INSERT INTO dokumente (xml)
VALUES ('<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
	##################################################################################
	Projekt E&#x2D;Gesetzgebung
	Nicht&#x2D;normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021&#x2D;2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC&#x2D;BY&#x2D;3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <!-- Metadaten -->
    <akn:meta GUID="e4e9224c-a2ff-46af-b390-eef666ee6706" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="1376c800-fdb8-4453-b639-eb0cc643f2a1" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="e54cd6ad-77fe-4b34-ab01-0caed4232c79" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/regelungstext-1" GUID="c2453919-5318-4700-b157-b4056d562d03" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321" GUID="4ee0c19e-ac74-48d0-9d2f-14afa746ae9f" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="84613506-2f8d-4f76-81d7-8ada58f55f5c" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="82367bb7-e9d4-4022-80b3-a54f78c8ad48" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="27ea99b4-2ece-45ec-92e2-14a6a3be52b5" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="321" GUID="4b9547eb-436b-4c36-ad7e-d1eafb060068" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="02b00ee4-9736-4eb0-87ab-bde4552362ac" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="330cdb26-d13e-4c68-b8aa-a4bb390edd3d" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="3342438f-d645-46bc-b636-8ff93339040e" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/1964-09-21/1/deu/regelungstext-1" GUID="34ce9521-41a7-46c7-8bb2-b77a0e140307" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/1964-09-21/1/deu" GUID="38417cc3-ce84-4a4b-ac19-e9b80ae0c13f" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="d04791fc-dcdc-47e6-aefb-bc2f7aaee151" GUID="32bd238d-e176-473b-a86c-1be978ccc905" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="90d353f8-19dc-4d64-9c89-e21c1e90b2ac" GUID="38a1a0b5-8291-4b91-8ca0-8d12bf990a12" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="b03544ab-bd92-48e3-8cd4-5f46e11536fc" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="d421ea74-0c09-4cd0-ad55-e305dee5535e" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="36a30909-bcad-4823-aff1-c7c051236e81" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="ffe56bfc-2e61-43e4-8415-3489690736a6" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="5f065d15-4927-417d-82d9-cf0ee3a671d1" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/1964-09-21/1/deu/1964-08-05/regelungstext-1.xml" GUID="30296030-d46d-4a53-8146-04e7035b20a6" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/1964-09-21/1/deu/1964-08-05/regelungstext-1.xml" GUID="01653b47-2a85-4902-a6ea-b538df135ea4" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="5873cf64-9760-46c3-be8a-369bb012fbd1" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="af66d7d4-ea9f-4e76-9fb9-772d81303737" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="4a91792a-6937-4646-a135-5f381c6a380a" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="3038994a-fd58-40be-b889-7311dff78c53" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="d80168e8-1a8b-4375-a0e5-7eb493f12844" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="625942c8-6976-42ea-adb4-7bb88c31cafb" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="e7000b51-6244-4272-9ccd-7c6919cbcbea" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="c5ec70fe-13ce-4ce0-8f95-ba743fb6a50a" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="c348c8f9-c3dc-4197-9f7e-e79b3560f1b8" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="da72a3ac-309a-41e5-80ff-8cbbb385c0ac" eId="meta-1_proprietary-1">
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
          <meta:typ>gesetz</meta:typ>
          <meta:form>stammform</meta:form>
          <meta:fassung>verkuendungsfassung</meta:fassung>
          <meta:art>regelungstext</meta:art>
          <meta:initiant>nicht-vorhanden</meta:initiant>
          <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
          <meta:fna>754-28-1</meta:fna>
          <meta:gesta>nicht-vorhanden</meta:gesta>
        </meta:legalDocML.de_metadaten>
        <meta-breg:legalDocML.de_metadaten xmlns:meta-breg="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
          <meta-breg:federfuehrung>
            <meta-breg:federfuehrend ab="2001-01-01" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta-breg:federfuehrend>
            <meta-breg:federfuehrend ab="1964-08-05" bis="2000-12-31">BMJ - Bundesministerium der Justiz</meta-breg:federfuehrend>
          </meta-breg:federfuehrung>
        </meta-breg:legalDocML.de_metadaten>
      </akn:proprietary>
    </akn:meta>
    <akn:preface GUID="22428132-df14-4e05-b9e6-b1ebf5edb8e0" eId="einleitung-1">
      <akn:longTitle GUID="16fa8f8c-4bfb-4d2f-8ef8-d760d39ec00d" eId="einleitung-1_doktitel-1">
        <akn:p GUID="04d093fd-a6e4-4efe-b222-6b7034b4cff9" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="84300801-de40-441b-8c51-f972c1c2255f" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="6d7cb6cf-658e-4d97-8b58-dcbce840324f" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="f1fbf8ff-9c88-4758-9559-0daad8544d3f" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="eecaf59e-e522-436e-8162-adffb2fe5f46" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="b6b8dfe2-c527-409c-a744-8c06f07c355b" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="53e9bb84-0521-450d-9e93-d865f77b87a4" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="7c05dffe-9b46-4f15-ad6e-d48518f5b652" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="123dd4b1-4339-4519-b82b-9d65c926b7e2" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="9bdfa156-d812-42ed-831c-925bbea1d74d" eId="preambel-1_formel-1">
        <akn:p GUID="003985eb-a28f-40d7-a7a7-883da1291b3a" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="2f383077-df9d-4a22-ba93-fc361f7f19d9" eId="hauptteil-1">
      <akn:article GUID="4656681f-8cfe-4054-bc90-090a3aebc4c5" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4f8146cf-91a3-41dc-94cb-53f035f2d639" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="74d279c8-ea67-4cb9-849f-a2f410898b75" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="252d6c6b-7a41-49f7-923a-bcdd333ecadd" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="ee8f83bc-8461-40e4-9c06-1fed230e960d" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="1a184fd8-a242-4933-9bc0-0bb22d29433d" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="4d610a27-9922-46fc-922b-358e38167561" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="cc8c85f9-4798-47ec-ac4b-2c34e68c52bb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="1654a12b-a8bb-4aec-9757-60b2d7f194ab" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="bcf89e7a-e5e7-4888-b9c5-485c60e2329b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="f290fbd4-fa84-4f81-8a0d-df512bc96743" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="4a82cd1e-586d-4bd5-93f2-b88287764f0d" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="cb0e299b-1a1f-4c25-a491-801d17def3dc" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="bd505b2d-e298-437c-bbfe-c9be0556a69c" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="f617023d-536f-4296-bcb4-0129a04130eb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="d1ba059a-83a4-4aeb-b1a7-d29c86b82582" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="efec9ea2-2e1d-407a-b36f-9d85cd7e124a" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="03053c2e-2a03-4433-a800-5258552a73f1" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="8ece77f5-d54f-447a-8c66-a892591b2e31" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="4aa02272-2fb8-417e-ba15-c31ed52923d4" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="ebf37aef-d32d-455e-b036-066e5bb1a9b7" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="90a5a01e-c1f3-4b62-9216-3d351a8a33dc" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4fbff511-421c-4405-9721-6c442d001dd1" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="c0105710-4a9e-4076-9db1-bc5c566473ae" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="2641f19a-3d41-4cde-95ee-10a23237d7ed" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="62938ed6-9984-470d-ba36-70b4dee7d267" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="329c7f9a-451d-4549-957b-c50868186895" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="ff087a7f-645e-454f-b123-d562edf0b2de" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="89366ad9-8560-4093-88b8-e2eae463c8a5" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="9270f240-086a-431c-a019-2b849d2c2ecf" eId="schluss-1_formel-1">
        <akn:p GUID="0519b3f5-7420-4256-a32a-5f35ac7774f4" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="2a029067-54cc-47c3-88db-ee9526ee2ca4" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="4bc6dc3c-c8a7-4d99-a135-9c8e0ba3529c" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="04818365-83d0-4f21-a3ab-044656ed8f49" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="8b09e194-b691-43a5-b633-90b12a1ebcfa" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="e41b52b8-2491-4ebb-add3-0d4fb6258656" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="518f3acb-d5f7-4f3e-aa6f-c6dcef7e60f2" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="037948ae-cf0c-4826-87f6-efd3c480cf4e" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="a411f3e7-b2f5-40b2-93c0-2d54359f1a7c" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="89b83e9c-e9c5-44dc-a992-6fe25647ebf5" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="cb23ba19-1a4d-4426-93d7-c76b1ca10aef" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="281d0349-2dab-4286-b083-1bdb8a375979" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="cac0e064-b667-4853-8ed8-8cb99b5998f3" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="89279918-e448-42d8-bf9f-a37c29369e21" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="da696510-a9d2-4ba2-8d77-a06807bca987" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="026abeec-501b-4729-bd69-a4c895cf66a0" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="fa5a6cbe-0d1c-4b07-b5f6-bf33309ce9f5" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/1964-09-21/1/deu';

-- Future expression of target law for 2017-03-16
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-03-16/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-03-16/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-03-16/1/deu';

INSERT INTO dokumente (xml)
VALUES ('<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
	##################################################################################
	Projekt E&#x2D;Gesetzgebung
	Nicht&#x2D;normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021&#x2D;2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC&#x2D;BY&#x2D;3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <!-- Metadaten -->
    <akn:meta GUID="e4e9224c-a2ff-46af-b390-eef666ee6706" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="1376c800-fdb8-4453-b639-eb0cc643f2a1" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="e54cd6ad-77fe-4b34-ab01-0caed4232c79" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/regelungstext-1" GUID="c2453919-5318-4700-b157-b4056d562d03" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321" GUID="4ee0c19e-ac74-48d0-9d2f-14afa746ae9f" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="84613506-2f8d-4f76-81d7-8ada58f55f5c" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="82367bb7-e9d4-4022-80b3-a54f78c8ad48" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="27ea99b4-2ece-45ec-92e2-14a6a3be52b5" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="321" GUID="4b9547eb-436b-4c36-ad7e-d1eafb060068" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="02b00ee4-9736-4eb0-87ab-bde4552362ac" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="330cdb26-d13e-4c68-b8aa-a4bb390edd3d" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="3342438f-d645-46bc-b636-8ff93339040e" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-1" GUID="34ce9521-41a7-46c7-8bb2-b77a0e140307" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/2017-03-16/1/deu" GUID="38417cc3-ce84-4a4b-ac19-e9b80ae0c13f" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="90d353f8-19dc-4d64-9c89-e21c1e90b2ac" GUID="32bd238d-e176-473b-a86c-1be978ccc905" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="vorherige-version-id" value="d04791fc-dcdc-47e6-aefb-bc2f7aaee151" GUID="38a1a0b5-8291-4b91-8ca0-8d12bf990a12" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="6edc7bd8-ed44-4449-8619-ab0ae2b4fb26" GUID="38a1a0b5-8291-4b91-8ca0-8d12bf990a12" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="b03544ab-bd92-48e3-8cd4-5f46e11536fc" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="d421ea74-0c09-4cd0-ad55-e305dee5535e" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="36a30909-bcad-4823-aff1-c7c051236e81" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="ffe56bfc-2e61-43e4-8415-3489690736a6" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="5f065d15-4927-417d-82d9-cf0ee3a671d1" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/2017-12-12/regelungstext-1.xml" GUID="30296030-d46d-4a53-8146-04e7035b20a6" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/2017-12-12/regelungstext-1.xml" GUID="01653b47-2a85-4902-a6ea-b538df135ea4" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="5873cf64-9760-46c3-be8a-369bb012fbd1" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="af66d7d4-ea9f-4e76-9fb9-772d81303737" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="4a91792a-6937-4646-a135-5f381c6a380a" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="3038994a-fd58-40be-b889-7311dff78c53" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="d80168e8-1a8b-4375-a0e5-7eb493f12844" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="625942c8-6976-42ea-adb4-7bb88c31cafb" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="e7000b51-6244-4272-9ccd-7c6919cbcbea" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="c5ec70fe-13ce-4ce0-8f95-ba743fb6a50a" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="c348c8f9-c3dc-4197-9f7e-e79b3560f1b8" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="da72a3ac-309a-41e5-80ff-8cbbb385c0ac" eId="meta-1_proprietary-1">
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
          <meta:typ>gesetz</meta:typ>
          <meta:form>stammform</meta:form>
          <meta:fassung>verkuendungsfassung</meta:fassung>
          <meta:art>regelungstext</meta:art>
          <meta:initiant>nicht-vorhanden</meta:initiant>
          <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
          <meta:fna>754-28-1</meta:fna>
          <meta:gesta>nicht-vorhanden</meta:gesta>
        </meta:legalDocML.de_metadaten>
        <meta-breg:legalDocML.de_metadaten xmlns:meta-breg="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
          <meta-breg:federfuehrung>
            <meta-breg:federfuehrend ab="2001-01-01" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta-breg:federfuehrend>
            <meta-breg:federfuehrend ab="1964-08-05" bis="2000-12-31">BMJ - Bundesministerium der Justiz</meta-breg:federfuehrend>
          </meta-breg:federfuehrung>
        </meta-breg:legalDocML.de_metadaten>
      </akn:proprietary>
    </akn:meta>
    <akn:preface GUID="22428132-df14-4e05-b9e6-b1ebf5edb8e0" eId="einleitung-1">
      <akn:longTitle GUID="16fa8f8c-4bfb-4d2f-8ef8-d760d39ec00d" eId="einleitung-1_doktitel-1">
        <akn:p GUID="04d093fd-a6e4-4efe-b222-6b7034b4cff9" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="84300801-de40-441b-8c51-f972c1c2255f" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="6d7cb6cf-658e-4d97-8b58-dcbce840324f" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="f1fbf8ff-9c88-4758-9559-0daad8544d3f" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="eecaf59e-e522-436e-8162-adffb2fe5f46" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="b6b8dfe2-c527-409c-a744-8c06f07c355b" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="53e9bb84-0521-450d-9e93-d865f77b87a4" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="7c05dffe-9b46-4f15-ad6e-d48518f5b652" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="123dd4b1-4339-4519-b82b-9d65c926b7e2" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="9bdfa156-d812-42ed-831c-925bbea1d74d" eId="preambel-1_formel-1">
        <akn:p GUID="003985eb-a28f-40d7-a7a7-883da1291b3a" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="2f383077-df9d-4a22-ba93-fc361f7f19d9" eId="hauptteil-1">
      <akn:article GUID="4656681f-8cfe-4054-bc90-090a3aebc4c5" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4f8146cf-91a3-41dc-94cb-53f035f2d639" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="74d279c8-ea67-4cb9-849f-a2f410898b75" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="252d6c6b-7a41-49f7-923a-bcdd333ecadd" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="ee8f83bc-8461-40e4-9c06-1fed230e960d" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="1a184fd8-a242-4933-9bc0-0bb22d29433d" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="4d610a27-9922-46fc-922b-358e38167561" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="cc8c85f9-4798-47ec-ac4b-2c34e68c52bb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="1654a12b-a8bb-4aec-9757-60b2d7f194ab" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="bcf89e7a-e5e7-4888-b9c5-485c60e2329b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="f290fbd4-fa84-4f81-8a0d-df512bc96743" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="4a82cd1e-586d-4bd5-93f2-b88287764f0d" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="cb0e299b-1a1f-4c25-a491-801d17def3dc" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="bd505b2d-e298-437c-bbfe-c9be0556a69c" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="f617023d-536f-4296-bcb4-0129a04130eb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="d1ba059a-83a4-4aeb-b1a7-d29c86b82582" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="efec9ea2-2e1d-407a-b36f-9d85cd7e124a" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="03053c2e-2a03-4433-a800-5258552a73f1" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="8ece77f5-d54f-447a-8c66-a892591b2e31" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="4aa02272-2fb8-417e-ba15-c31ed52923d4" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="ebf37aef-d32d-455e-b036-066e5bb1a9b7" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="90a5a01e-c1f3-4b62-9216-3d351a8a33dc" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4fbff511-421c-4405-9721-6c442d001dd1" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="c0105710-4a9e-4076-9db1-bc5c566473ae" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="2641f19a-3d41-4cde-95ee-10a23237d7ed" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="62938ed6-9984-470d-ba36-70b4dee7d267" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="329c7f9a-451d-4549-957b-c50868186895" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="ff087a7f-645e-454f-b123-d562edf0b2de" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="89366ad9-8560-4093-88b8-e2eae463c8a5" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="9270f240-086a-431c-a019-2b849d2c2ecf" eId="schluss-1_formel-1">
        <akn:p GUID="0519b3f5-7420-4256-a32a-5f35ac7774f4" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="2a029067-54cc-47c3-88db-ee9526ee2ca4" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="4bc6dc3c-c8a7-4d99-a135-9c8e0ba3529c" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="04818365-83d0-4f21-a3ab-044656ed8f49" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="8b09e194-b691-43a5-b633-90b12a1ebcfa" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="e41b52b8-2491-4ebb-add3-0d4fb6258656" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="518f3acb-d5f7-4f3e-aa6f-c6dcef7e60f2" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="037948ae-cf0c-4826-87f6-efd3c480cf4e" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="a411f3e7-b2f5-40b2-93c0-2d54359f1a7c" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="89b83e9c-e9c5-44dc-a992-6fe25647ebf5" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="cb23ba19-1a4d-4426-93d7-c76b1ca10aef" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="281d0349-2dab-4286-b083-1bdb8a375979" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="cac0e064-b667-4853-8ed8-8cb99b5998f3" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="89279918-e448-42d8-bf9f-a37c29369e21" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="da696510-a9d2-4ba2-8d77-a06807bca987" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="026abeec-501b-4729-bd69-a4c895cf66a0" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="fa5a6cbe-0d1c-4b07-b5f6-bf33309ce9f5" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-03-16/1/deu';

-- Future expression of target law for 2017-05-01
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-05-01/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-05-01/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-05-01/1/deu';

INSERT INTO dokumente (xml)
VALUES ('<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
	##################################################################################
	Projekt E&#x2D;Gesetzgebung
	Nicht&#x2D;normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021&#x2D;2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC&#x2D;BY&#x2D;3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <!-- Metadaten -->
    <akn:meta GUID="e4e9224c-a2ff-46af-b390-eef666ee6706" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="1376c800-fdb8-4453-b639-eb0cc643f2a1" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="e54cd6ad-77fe-4b34-ab01-0caed4232c79" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/regelungstext-1" GUID="c2453919-5318-4700-b157-b4056d562d03" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321" GUID="4ee0c19e-ac74-48d0-9d2f-14afa746ae9f" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="84613506-2f8d-4f76-81d7-8ada58f55f5c" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="82367bb7-e9d4-4022-80b3-a54f78c8ad48" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="27ea99b4-2ece-45ec-92e2-14a6a3be52b5" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="321" GUID="4b9547eb-436b-4c36-ad7e-d1eafb060068" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="02b00ee4-9736-4eb0-87ab-bde4552362ac" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="330cdb26-d13e-4c68-b8aa-a4bb390edd3d" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="3342438f-d645-46bc-b636-8ff93339040e" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/2017-05-01/1/deu/regelungstext-1" GUID="34ce9521-41a7-46c7-8bb2-b77a0e140307" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/2017-05-01/1/deu" GUID="38417cc3-ce84-4a4b-ac19-e9b80ae0c13f" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="6edc7bd8-ed44-4449-8619-ab0ae2b4fb26" GUID="32bd238d-e176-473b-a86c-1be978ccc905" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="vorherige-version-id" value="90d353f8-19dc-4d64-9c89-e21c1e90b2ac" GUID="38a1a0b5-8291-4b91-8ca0-8d12bf990a12" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="b03544ab-bd92-48e3-8cd4-5f46e11536fc" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="d421ea74-0c09-4cd0-ad55-e305dee5535e" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="36a30909-bcad-4823-aff1-c7c051236e81" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="ffe56bfc-2e61-43e4-8415-3489690736a6" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="5f065d15-4927-417d-82d9-cf0ee3a671d1" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/321/2017-05-01/1/deu/2017-12-12/regelungstext-1.xml" GUID="30296030-d46d-4a53-8146-04e7035b20a6" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/321/2017-05-01/1/deu/2017-12-12/regelungstext-1.xml" GUID="01653b47-2a85-4902-a6ea-b538df135ea4" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="5873cf64-9760-46c3-be8a-369bb012fbd1" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="af66d7d4-ea9f-4e76-9fb9-772d81303737" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="4a91792a-6937-4646-a135-5f381c6a380a" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="3038994a-fd58-40be-b889-7311dff78c53" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="d80168e8-1a8b-4375-a0e5-7eb493f12844" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="625942c8-6976-42ea-adb4-7bb88c31cafb" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="e7000b51-6244-4272-9ccd-7c6919cbcbea" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="c5ec70fe-13ce-4ce0-8f95-ba743fb6a50a" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="c348c8f9-c3dc-4197-9f7e-e79b3560f1b8" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="da72a3ac-309a-41e5-80ff-8cbbb385c0ac" eId="meta-1_proprietary-1">
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
          <meta:typ>gesetz</meta:typ>
          <meta:form>stammform</meta:form>
          <meta:fassung>verkuendungsfassung</meta:fassung>
          <meta:art>regelungstext</meta:art>
          <meta:initiant>nicht-vorhanden</meta:initiant>
          <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
          <meta:fna>754-28-1</meta:fna>
          <meta:gesta>nicht-vorhanden</meta:gesta>
        </meta:legalDocML.de_metadaten>
        <meta-breg:legalDocML.de_metadaten xmlns:meta-breg="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
          <meta-breg:federfuehrung>
            <meta-breg:federfuehrend ab="2001-01-01" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta-breg:federfuehrend>
            <meta-breg:federfuehrend ab="1964-08-05" bis="2000-12-31">BMJ - Bundesministerium der Justiz</meta-breg:federfuehrend>
          </meta-breg:federfuehrung>
        </meta-breg:legalDocML.de_metadaten>
      </akn:proprietary>
    </akn:meta>
    <akn:preface GUID="22428132-df14-4e05-b9e6-b1ebf5edb8e0" eId="einleitung-1">
      <akn:longTitle GUID="16fa8f8c-4bfb-4d2f-8ef8-d760d39ec00d" eId="einleitung-1_doktitel-1">
        <akn:p GUID="04d093fd-a6e4-4efe-b222-6b7034b4cff9" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="84300801-de40-441b-8c51-f972c1c2255f" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="6d7cb6cf-658e-4d97-8b58-dcbce840324f" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="f1fbf8ff-9c88-4758-9559-0daad8544d3f" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="eecaf59e-e522-436e-8162-adffb2fe5f46" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="b6b8dfe2-c527-409c-a744-8c06f07c355b" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="53e9bb84-0521-450d-9e93-d865f77b87a4" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="7c05dffe-9b46-4f15-ad6e-d48518f5b652" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="123dd4b1-4339-4519-b82b-9d65c926b7e2" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="9bdfa156-d812-42ed-831c-925bbea1d74d" eId="preambel-1_formel-1">
        <akn:p GUID="003985eb-a28f-40d7-a7a7-883da1291b3a" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="2f383077-df9d-4a22-ba93-fc361f7f19d9" eId="hauptteil-1">
      <akn:article GUID="4656681f-8cfe-4054-bc90-090a3aebc4c5" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4f8146cf-91a3-41dc-94cb-53f035f2d639" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="74d279c8-ea67-4cb9-849f-a2f410898b75" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="252d6c6b-7a41-49f7-923a-bcdd333ecadd" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="ee8f83bc-8461-40e4-9c06-1fed230e960d" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="1a184fd8-a242-4933-9bc0-0bb22d29433d" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="4d610a27-9922-46fc-922b-358e38167561" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="cc8c85f9-4798-47ec-ac4b-2c34e68c52bb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="1654a12b-a8bb-4aec-9757-60b2d7f194ab" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="bcf89e7a-e5e7-4888-b9c5-485c60e2329b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="f290fbd4-fa84-4f81-8a0d-df512bc96743" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="4a82cd1e-586d-4bd5-93f2-b88287764f0d" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="cb0e299b-1a1f-4c25-a491-801d17def3dc" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="bd505b2d-e298-437c-bbfe-c9be0556a69c" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="f617023d-536f-4296-bcb4-0129a04130eb" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="d1ba059a-83a4-4aeb-b1a7-d29c86b82582" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="efec9ea2-2e1d-407a-b36f-9d85cd7e124a" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="03053c2e-2a03-4433-a800-5258552a73f1" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="8ece77f5-d54f-447a-8c66-a892591b2e31" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="4aa02272-2fb8-417e-ba15-c31ed52923d4" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="ebf37aef-d32d-455e-b036-066e5bb1a9b7" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="90a5a01e-c1f3-4b62-9216-3d351a8a33dc" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="4fbff511-421c-4405-9721-6c442d001dd1" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="c0105710-4a9e-4076-9db1-bc5c566473ae" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="2641f19a-3d41-4cde-95ee-10a23237d7ed" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="62938ed6-9984-470d-ba36-70b4dee7d267" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="329c7f9a-451d-4549-957b-c50868186895" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="ff087a7f-645e-454f-b123-d562edf0b2de" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="89366ad9-8560-4093-88b8-e2eae463c8a5" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="9270f240-086a-431c-a019-2b849d2c2ecf" eId="schluss-1_formel-1">
        <akn:p GUID="0519b3f5-7420-4256-a32a-5f35ac7774f4" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="2a029067-54cc-47c3-88db-ee9526ee2ca4" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="4bc6dc3c-c8a7-4d99-a135-9c8e0ba3529c" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="04818365-83d0-4f21-a3ab-044656ed8f49" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="8b09e194-b691-43a5-b633-90b12a1ebcfa" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="e41b52b8-2491-4ebb-add3-0d4fb6258656" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="518f3acb-d5f7-4f3e-aa6f-c6dcef7e60f2" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="037948ae-cf0c-4826-87f6-efd3c480cf4e" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="a411f3e7-b2f5-40b2-93c0-2d54359f1a7c" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="89b83e9c-e9c5-44dc-a992-6fe25647ebf5" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="cb23ba19-1a4d-4426-93d7-c76b1ca10aef" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="281d0349-2dab-4286-b083-1bdb8a375979" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="cac0e064-b667-4853-8ed8-8cb99b5998f3" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="89279918-e448-42d8-bf9f-a37c29369e21" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="da696510-a9d2-4ba2-8d77-a06807bca987" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="026abeec-501b-4729-bd69-a4c895cf66a0" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="fa5a6cbe-0d1c-4b07-b5f6-bf33309ce9f5" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/321/2017-05-01/1/deu';

