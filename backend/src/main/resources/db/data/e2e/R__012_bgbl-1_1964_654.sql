-- Target law current expression
-- TARGET LAW
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/1964-10-01/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/1964-10-01/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/1964-10-01/1/deu';

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
    <akn:meta GUID="852670f5-6956-4f67-8cc4-73c2fb65ede8" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="a93a037c-629b-4a72-8748-16184847cb01" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="c34cfd40-3c72-45fc-9126-21b59590944c" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/regelungstext-1" GUID="08a14ae4-7e20-441e-8a34-fba039b95c9b" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654" GUID="22391fab-2157-44eb-8df3-d343d04f0fc0" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="47199822-af62-4edc-b9a4-70ba1c9a511d" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="238f4dee-7dfe-43b2-a88e-82fcb5911a4a" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="6dbbf724-914b-4ba9-bf7d-8a4eb0bd3bc3" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="654" GUID="c1e6b2e4-30f9-4657-ada5-03bb4dea4367" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="8b037eb4-4316-42b3-9d2d-054d24b05c4b" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="3591e0c3-ea43-491d-a59e-15c113c71d83" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="ad06c44f-687f-494f-942e-f110a5a4c0c8" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/1964-09-21/1/deu/regelungstext-1" GUID="7553f60a-8441-4724-80a7-1655790b420f" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/1964-09-21/1/deu" GUID="64bb67e1-49ac-4343-b41e-09225d53162c" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="001e8fc7-42da-475b-9daf-ac788907354f" GUID="7187a2e7-b3e4-4a77-9003-8ff6cdfca81c" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="32887bb3-e6c3-4283-b821-2f597b6affab" GUID="e83d7134-7d3a-4bd7-9235-b6b49d445229" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="dcab0550-b0e9-4e68-b81c-1fad17747e5a" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="66cb0b9b-b84e-4eb6-8078-e4195ea9fe90" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="dec08a8a-49b6-45ac-af6d-5873dff8847d" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="cff947ad-4a8c-4780-971d-7c0b61cef528" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="a8273bda-d49b-4936-bc78-e16db8fc70b4" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/1964-09-21/1/deu/1964-08-05/regelungstext-1.xml" GUID="3ac8192e-a967-46bf-b92a-d0af52ad1960" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/1964-09-21/1/deu/1964-08-05/regelungstext-1.xml" GUID="e8d8cb53-b484-4d43-9202-9b66fb78ce41" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="66b38897-a643-4b7d-9f0a-1a1dc5eb1d8b" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="0220e653-5627-42e0-b6e3-ef5764612226" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="e8400fb3-0130-41da-bd25-cf28aaf7d032" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="c615d488-6efa-4ee6-b77f-a0404705e843" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="19b8ffbf-41fb-4069-9aa1-47d37cdbd817" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="fd279f22-30a1-4650-8570-920a81d5e861" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="fb4fc147-3ab4-4b47-8239-3d838bc8775b" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="08de4618-34da-4b10-99c8-507c80c5c7f1" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="e5fad3ce-182e-4dd4-a9b9-d1b992f77b0b" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="8f453e26-1a3e-4b9c-963c-7e99a98791f5" eId="meta-1_proprietary-1">
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
    <akn:preface GUID="f3ad0a73-4c92-4bbc-a624-f88de3e2b03b" eId="einleitung-1">
      <akn:longTitle GUID="f1b9256f-1fca-4037-8fc6-fe6fdb219a73" eId="einleitung-1_doktitel-1">
        <akn:p GUID="0bc2a78f-ee61-4a43-be45-350d3e6f7af7" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="59466118-0c9e-4768-beac-3b957ea5bd03" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="30a946e5-46bf-414f-a116-2e0949fec6ff" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="cba830e9-b384-4e8f-a478-165fa83ae914" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="d64fddc9-e2e8-4965-9101-631788b8ac27" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="9c297683-f278-4989-bc1e-650dee58e929" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="f519cfbf-79ac-403e-a92a-4ef8b25510f3" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="5bac3699-364f-4dfb-bdd9-fea15f98120e" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="8b12c72a-a117-4f5c-865f-13bc43b50669" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="8f30779a-26a3-40d2-bf13-742aad0fbb47" eId="preambel-1_formel-1">
        <akn:p GUID="a42d61a9-436a-4e78-9922-bcca04492329" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="f61d1983-cb95-43c4-923f-3919b50cc53e" eId="hauptteil-1">
      <akn:article GUID="e8837709-e0e5-471f-9b3d-9158d0f59b7d" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="68a465b6-764a-4b7d-a25c-391259b88104" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="d029bfe6-5fc1-4810-81dc-32006396bbe5" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="88ef8e90-f2c6-44a9-b8e9-698645fc46ff" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="dc60c0e8-397c-4f7f-b9b6-27fe86cd79ea" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="05c15fc0-e659-4e31-aa73-da6be3692442" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="87ce56b2-f155-441b-9d61-ebcf85293cf3" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="bb374b76-4234-4b56-9466-b4210140aa7b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="992e0d2c-04e4-4088-8519-a50e4c9802b5" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="af38afdc-a118-42f4-a3f0-d2e8b6413953" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="06a73c72-9643-491c-bd8e-34d727ee9ed3" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="1aea907f-1fa6-44dd-be98-ee0d573a1917" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="a25082dc-6a80-453d-8100-ac5e223ba481" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="b539727a-4ee6-4355-b701-f520e28c5053" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="72d2a46d-14b3-4800-92bf-c6bcbdac24cf" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="b6697467-2d47-4c57-be36-1002166edf9f" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="49b02074-b296-4b2f-829c-bae0a729b75c" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="1a3bd840-714d-4470-a902-377ff98f4482" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="3b96ea0d-bd5b-4a5c-a1fd-e32d49450ed6" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="b9fe77db-3d39-4c5f-a3b8-55b8d8c95a6e" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="3a520703-35ed-4a5e-97d3-6f78205e6320" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="e4aa4ed6-27b2-4495-94de-26d6569f2d86" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="109d14a0-1422-47c4-8ac6-5fa90efcfd49" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="c883f1a3-17a6-4ec5-8e28-dc887e548966" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="3fbc34fd-544e-4ae2-92a5-285832842ba9" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="be163017-10a3-4a02-8345-258e1fa192e0" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="0bc79eac-15dd-415c-9594-6aad43c81f03" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="888fc575-44f0-4e50-8c47-d9d0a8f0c1c7" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="40f4fd4d-7b9d-4457-ada4-8fd0719f1382" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="e134b244-2fa1-4f63-bbd5-4ee9971b2294" eId="schluss-1_formel-1">
        <akn:p GUID="5b4854b4-23bd-4d7e-b2f9-db46596e3406" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="a7f04d5a-25d3-49a3-a473-d5fe1f670351" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="916942d3-41bb-4946-9e34-6e2cf16371f2" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="269e00ba-5e09-4cf2-a5e0-df156d0bcbc1" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="6822b705-b6c0-47ef-acbd-3c06a6d67474" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="e32ad1ea-db54-4507-bc04-72e3f68d788f" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="3f7e1077-85df-4ee0-91fb-499505616281" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="b990a2c7-a010-4c23-a4b8-abaafc386e3b" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="5783f055-18b9-4e0c-8894-4f989205a779" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="1f4e62fc-23e6-4b8e-9e83-09b8e589270a" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="47d2cb56-8f4c-48ec-9de8-abb26dc91beb" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="1659a8b3-ec98-47e6-9e1d-5e3692de1d1f" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="4ea3691d-2ca2-4259-9d0e-21c5bb59c6ba" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="e7acabd1-b79c-4225-a724-5ea418a47022" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="b6837eac-1583-49f7-8a9e-371f9209e6a2" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="84026c47-3731-4ce4-89fe-b7e9d5b77a2d" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="d0339b7a-a7a8-4050-bd0f-928c09999ea8" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/1964-09-21/1/deu';

-- Future expression of target law for 2017-01-01
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-01-01/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-01-01/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-01-01/1/deu';

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
    <akn:meta GUID="48abb6db-2724-4eab-8add-49db4e960d0d" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="36686a74-3653-451a-8deb-7b33d2a73bd6" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="2fffc6b3-bace-4c6c-9d38-d49ad7afb9e7" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/regelungstext-1" GUID="9b14f076-7b0f-4a21-acdc-d941d790020b" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654" GUID="85215fe7-6454-4bd2-8268-d5dc379bfca6" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="a07e3525-149d-413a-9326-abb2008155cf" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="9763bcf6-1a05-44ca-ad9c-87cfe471148a" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="4e1533c1-3383-4617-b0f0-4af2cb1bbd9f" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="654" GUID="c46da287-cdec-4d0d-857b-3650860d8c16" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="f0a09872-e7b3-4656-816f-f708770ae535" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="a599eecd-6594-4471-8065-1ec5336da900" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="ea0fe07f-2150-4005-b54c-fadb8044d967" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-01-01/1/deu/regelungstext-1" GUID="8bb18ac2-fb6b-4700-9f81-ba9eecd29f2a" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-01-01/1/deu" GUID="a833f696-36a2-4904-9464-fdd329130c8c" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="32887bb3-e6c3-4283-b821-2f597b6affab" GUID="13c8d9fa-af6a-496d-b85b-5a14006963bd" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="vorherige-version-id" value="001e8fc7-42da-475b-9daf-ac788907354f" GUID="1e527d59-ae87-421d-88e5-386982de287e" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="c62c278f-49dc-4ae2-b912-ab7418442485" GUID="7c49e1d0-5b21-405b-941b-956614909642" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="b4b4fe0a-92a8-42cb-a91f-ce7f6fed3fc9" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="59c7171c-1237-4506-9965-4713228bedc1" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="95c4edae-9acc-4a2f-89e2-0fe48aa7bbf6" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="c89164e4-0bcd-495f-a44c-f0832bc42b79" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="b20c5a2a-891d-43a2-be37-d9869ff80e4f" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-01-01/1/deu/2017-02-02/regelungstext-1.xml" GUID="a337b175-1409-4b79-be11-f801a811714a" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-01-01/1/deu/2017-02-02/regelungstext-1.xml" GUID="884ea8ac-889b-4167-9cf6-19bf5bbffb6a" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="d15ad1d4-17f7-4faa-a4f9-c63ba176ef62" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="17de5bf3-dc6e-4564-90b3-331bc605a29f" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="3a5821a6-acf7-43ae-bd43-665288ca4469" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="f85bd3cb-fa6a-45ea-b2e0-c1b1e15a632f" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="0f04ba5c-d240-49c3-968d-b4ef8e0cea71" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="37b1905b-0ea6-430b-ac9c-b4b2eb4efa1a" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="7b8bd5bd-d9d7-4ff8-beaf-9662c603329b" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="8c9232a4-23aa-4fc8-b9ae-81df31804907" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="2f934bba-0754-4d67-be00-2a8627cb9809" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="8d1917a6-1f7d-45c4-9e08-889e999d3175" eId="meta-1_proprietary-1">
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
    <akn:preface GUID="c1c8c577-3f57-4905-85e1-7e08166f3442" eId="einleitung-1">
      <akn:longTitle GUID="0c83b019-deb6-4fc5-96dd-f402e2a1e6f5" eId="einleitung-1_doktitel-1">
        <akn:p GUID="9c925366-7e1c-4d6b-a06c-b805cfd973bb" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="76c5d027-638a-48c5-9673-c4d4d6f8b617" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="c2e7e445-ccf4-4afd-9d3d-144c41c710b6" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="c9d926c8-fef1-4d14-8779-3009132ad77a" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="1ad9ce7f-1d2b-457c-8517-5556da120d75" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="63871ab7-fef4-4570-bd11-348089b202b6" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="7bb4ce4f-a172-47e7-a83c-6a94f35fd6a4" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="48f3f849-bc39-4293-8071-f49e7870184d" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="6492fbac-1c97-40b1-b3fa-854d67cd4634" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="49c48cc6-55bc-4bce-91fe-d9a3e2a5b6ca" eId="preambel-1_formel-1">
        <akn:p GUID="dfdf0e6a-a229-4349-8907-b4719db26e06" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="826611d7-9e33-4ae0-aedb-335ff4740f33" eId="hauptteil-1">
      <akn:article GUID="ca88e7f6-421b-4cbc-93eb-df12c6fa2150" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="1505baab-fe80-4000-92e6-3501ec48f9f2" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="21e61ed6-ea6b-46f4-96f7-0c736bc689cf" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="39fb4003-ada7-4a61-8d6b-8e79e924eafa" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="43b08deb-d754-4c17-8227-448ba822bf1b" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="b2e06f37-acbf-4bf9-a0d6-d0a936d87d5e" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="4e415566-c621-43f0-8625-e51886c623bf" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="d7aee1ef-937a-4de1-82cf-574d34c39217" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="865b4414-020e-417d-b378-37062d138c8b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="2e563b7e-d040-4b6b-9233-0f19be695649" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="c719c810-be00-419b-b2ff-0c736f2cae32" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="50121d03-0ba8-4891-8994-d7b5816539e9" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="ed7b2b30-c8ea-403d-b73c-bcead02cbafe" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="9d17f9d0-66fb-4dda-b400-7f1cbfabe7e2" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="988259bb-29f6-4f91-88b4-0205b916119a" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="e9d94c8e-c415-4c79-8e20-02f54510d928" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="573dc1de-f8ea-4b4b-9795-313b4db19e2d" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="25343921-f513-44ad-80ee-834d522e56ce" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="7d3f990c-0063-48b0-a5a9-9338a66d3d70" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="305f79fc-4ca0-432b-9af8-c7739d538c3c" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="8d01173f-cda0-4f1e-9810-78faf514fbe8" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="24e27c42-ae14-4bc1-ade8-508f001cf1ff" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="6d0a7dc2-d90a-4e24-8b16-f079c5d91e86" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="4b31cf37-357a-4396-ab76-96f90524e84f" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="171e68a0-665e-47ea-8fca-ac496383c32e" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="b46dc31c-fef1-4f85-bb47-1113e5df7420" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="780f3faa-5b04-49c5-83ce-83902186f289" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="c24ee1ad-34b2-4259-b8d4-1cd6fcaae107" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="74e3022f-c711-4825-9b30-4b9a1bafd8d0" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="f61b2d2b-443b-4ca4-badb-ada0aa4dfc57" eId="schluss-1_formel-1">
        <akn:p GUID="f9716a12-2ee2-4b5f-b68b-641d9e7a1426" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="2b86b369-6ae6-465f-9fdd-d8dced8cf846" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="b0e3bd23-455a-48cf-acd2-9c9c781dc3ec" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="0119278b-b3ae-43fa-ad1d-3a13f7c9f4ae" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="d54d6c53-31aa-4a1b-9c0d-acca809d6e41" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="3a75d05b-ba4b-4360-bcdc-b38712e77e3c" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="6e8e3dea-dac7-408e-b322-3e567862399d" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="cd8ecb23-479f-4bb4-adaa-e106f09be8bb" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="74f6feac-d749-48c6-899a-fd3b21003118" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="4d1e70cf-3be4-4285-bccf-a2545b340405" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="0f5a9015-772a-490f-91d8-01ecfa66e2d0" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="cb655495-cb3c-4b56-ae01-7ef0e2dccb6f" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="c2e54719-f779-416a-803c-c52297612182" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="ca930a56-e352-4dc8-a81b-686f88b95ee3" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="851bd2ab-b61c-4c20-975d-4da63b795e5b" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="3614d21b-ff93-40f1-98c9-a816feab6a4d" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="10796b68-b5ad-470d-9b2a-4f7347c0b229" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'UNPUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-01-01/1/deu';

-- Future expression of target law for 2017-03-16
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-03-16/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-03-16/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-03-16/1/deu';

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
    <akn:meta GUID="df4caf45-5fec-4f66-9032-164454d8d29c" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="88e89e24-488d-485e-a500-98c1c8eb0416" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="eb661d83-1fcc-4c87-bb1e-44e52d1bfb2b" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/regelungstext-1" GUID="3074e681-3506-4337-b6f0-41efceb2d077" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654" GUID="003a6192-2c72-4a07-ada9-fd11d2911a69" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="7e1297b4-1bb3-4e17-9025-2a6a164b9fb4" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="e88349ec-44c1-4799-9183-60d4021c77a1" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="314d6f30-fe60-481f-ae9f-12242b2e356d" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="654" GUID="fb94d9f9-e271-4ffb-9e46-435835671b1b" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="7c5c3ef9-7c43-4c2d-835d-d7c9d64500ad" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="2ad4f027-dbce-4b02-90ab-da60830165f8" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="ebe516bd-1d9d-4c43-a849-53c69f96bfa9" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-03-16/1/deu/regelungstext-1" GUID="b67214e0-4f9e-460d-b24c-d86f8d5e5061" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-03-16/1/deu" GUID="e2376a6f-f354-475f-8f12-cfefc4ca8566" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="c62c278f-49dc-4ae2-b912-ab7418442485" GUID="b96d1a2f-5045-43e1-ba29-9b9417a05e60" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="vorherige-version-id" value="32887bb3-e6c3-4283-b821-2f597b6affab" GUID="1340682f-832f-4326-b518-c577d8b19f63" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="2690c664-0c5f-41e4-9b41-b0557a36d212" GUID="f650b5d4-6c23-41d9-a394-e36c5ea43435" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="1ad8bc65-32b6-4650-a932-a2c5e4292ab4" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="197317d7-9eb0-4e37-a31e-cd6b60672ea2" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="be8d16c1-c559-4748-88c3-a42c2f6596a7" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="128f9e06-336c-4581-b296-452ba42ae2e3" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="8ac67d13-6228-431c-ace7-d4efc2399351" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-03-16/1/deu/2017-03-17/regelungstext-1.xml" GUID="ce30c159-c0e9-4dde-84fc-ce69dc85f8df" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-03-16/1/deu/2017-03-17/regelungstext-1.xml" GUID="1a9dfad3-bf17-4c8d-8b52-6ac0b6e19bba" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="65717575-502a-4593-b5f0-b18d3ec3d2ee" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="169bc9fd-be06-479c-bf09-e5ede9f2c713" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="caab3128-5efb-427a-af47-6d7ed463755e" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="c558155f-735e-4851-a29e-a9b08d4a9d73" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="fa915efb-5315-4804-801f-94c8130e4821" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="1d10e539-d157-4b38-9078-ed35800279e2" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="3bea5f18-1fae-4f0f-871a-54d4d70b420a" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="328578c1-91f8-4eaf-80a6-e9f487e9e1fe" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="ce78333c-0a08-4d46-8fef-4dc1e21e9fb1" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="33105863-f864-472c-9efc-752005338647" eId="meta-1_proprietary-1">
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
    <akn:preface GUID="e1606a9f-f7e5-4ea8-9919-7c74c036fb47" eId="einleitung-1">
      <akn:longTitle GUID="03739d1b-0f66-4686-b474-6dd99fc5b5af" eId="einleitung-1_doktitel-1">
        <akn:p GUID="d7df248e-5282-4eb1-adcf-38daa674f2e3" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="6824a91e-812d-4f64-8d01-1b61d4fec340" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="8991922d-c40a-46f6-a8c8-3b3cc8a3cad7" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="0d5a7fe3-0eed-4648-a314-c69c9ea2afc7" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="7feac0c4-4f6b-43b1-9392-a050aaf0ed53" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="d7ac7242-a4cd-42ec-bd45-ab4d2e74ca03" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="58f25e3c-514f-4f49-bd40-aaea07c31d36" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="304f116a-409c-4ee5-aa0f-721f5f83b38f" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="57bc6957-c0b4-4353-869d-82f6ca3f76a7" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="0ad84b31-e083-4179-82e2-acdeaf713af1" eId="preambel-1_formel-1">
        <akn:p GUID="78f7fd6d-7e5d-4018-841e-04c43b04ba8f" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="0a2cf039-ee80-4b66-8679-c0f2fe74eb83" eId="hauptteil-1">
      <akn:article GUID="c216d88c-debc-4e3a-8077-3f33d778c258" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="2830a418-fdb7-41b4-9141-048ddfe46755" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="141fe9c3-dae6-47a9-aa33-b0200c9882ee" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="be8f7e05-c43f-42d7-8f8e-aa98a1e2c6b9" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="78933db3-be78-4d55-b2d7-93b90d853405" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="f23acefa-0707-41d1-bae2-918198ae8acb" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="a4f860c1-6c0f-4935-aa3a-74152dc8e657" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="7966a7b6-215b-4bcf-98ed-2158f128ba7b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="bf81582f-d128-4309-95e8-50ff3837f612" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="e83d3d4e-70d0-4f0f-b31d-28ef84848fff" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="4b4586a0-a14b-4467-80d6-0ce714cf167e" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="e58ffe1b-1176-4f93-971b-8272250e0051" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="dabefb57-0e36-4540-b800-d0fb6b8b6f73" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="fba44fb7-4c45-4718-9836-f8054445c5b0" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="017e4c17-128e-4f4a-a4a9-d7515daebee2" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="93b63020-06be-4fbc-9e72-55ab7b36142e" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="88fb4b85-9fde-4f65-b83d-ca946346aad7" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="c1cc86ef-2cf6-4ae0-b604-131614d525b6" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="265ec9ef-bd2f-4e52-ae73-96ba97c1a9ba" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="88fcef78-0816-426c-ab9c-55ec980234a4" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="0de92209-7b40-4f98-9b52-5bd0d267ac58" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="72bbeeea-7280-4056-b08c-1342a3c6696c" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="26362e68-499d-4bcf-8c91-e6de9f1c1ffc" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="75172a73-cb40-4dd4-9ff7-87a09e55d71b" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="46887c49-d595-4832-bf36-72ff7171ea1e" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="5fe93da0-b7df-4d84-9ff5-ef68283ea6da" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="01e1e6cb-b774-4dd5-92c0-a96644d5c7e2" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="258dfec5-4963-47e4-9422-9a6dda63b74f" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="90aa9aed-3cd2-4167-a01e-d5244254284a" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="0771811b-6fec-45c1-adab-e35d01a98e98" eId="schluss-1_formel-1">
        <akn:p GUID="ce459ec2-eef4-4a80-a9ba-0bea0151960d" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="c5d40bfd-cd98-4bd1-ab7a-7de92990eda6" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="de530719-4d8c-4d9d-bc8a-1e09c7b6b8b3" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="f480c323-cd49-43aa-8cb9-7f6ff644f917" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="5b9fb65a-9e0f-4580-8d40-10ff3e24e7a3" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="1945d644-213e-464e-ac87-3a57a56dd64f" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="e37b156a-9d58-4e33-a49a-b7f5c348a4ba" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="d9f0547e-9bbe-4ae6-83b9-78d4a493d19a" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="e485b020-c121-418a-b656-18bb2568bacf" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="c51c9a2b-e519-4484-9a4f-6c124bea025b" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="4695b50a-f232-4e67-bf5b-be5275d13833" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="c28a7d66-1c79-4385-926e-ad559233549a" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="41f77af1-fd41-4683-bf6d-e313d1f0e292" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="285ce1c0-2cef-4186-8255-da512726432a" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="af75fcbd-f94e-4772-8d27-5773e53d04f8" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="ddfd279a-f203-441c-b95a-f8e688be6da7" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="a5022314-00fb-403e-be4e-e0be9b8dbcbf" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'UNPUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-03-16/1/deu';

-- Future expression of target law for 2017-05-01
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-05-01/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-05-01/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-05-01/1/deu';

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
    <akn:meta GUID="2548434d-53f9-4a51-8517-f5e2323b80ee" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="31b3d220-16d8-4eae-952c-511fb0ce248a" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="de13e6e8-23ea-4816-83a5-057816f7c40f" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/regelungstext-1" GUID="d2f705e6-bc9e-4842-8bd7-0e0a311d7826" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654" GUID="e046daad-3c67-44d3-a17f-afe272f7d560" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" GUID="30eb52ba-b1c2-4302-9633-4ed01e1f1b09" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1964-08-05" GUID="42484e3a-f8ea-4670-8b0c-8eb0aa0b5251" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="31437268-expression8ab9-4156-a464-a1ca68c40a75" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="71e4f2a0-3e37-4967-8f66-0497daebfaf8" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="654" GUID="9d026936-dc52-4308-9511-fea241b82098" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="9649e6c5-6780-43ba-908e-b5e215eb1102" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="8659afa7-b9fc-482c-ac76-6bf00e0e7f6d" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="0fa76e5f-b7fe-451e-914e-a7a4f12a1816" eId="meta-1_ident-1_frbr-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-05-01/1/deu/regelungstext-1" GUID="fe215c6a-9e24-4054-a21f-b893ebd75c53" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-05-01/1/deu" GUID="d2c74c59-e9ed-4a7b-a1e1-ce620da593ba" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="2690c664-0c5f-41e4-9b41-b0557a36d212" GUID="e5cf0e7c-f534-40a9-9209-603a24da5026" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="vorherige-version-id" value="c62c278f-49dc-4ae2-b912-ab7418442485" GUID="6ec751bb-c197-4f9f-956f-132fa5e25545" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="d97b657b-2d7e-40f4-bc6e-e00862eb2e38" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1964-08-05" GUID="0bd0e3ff-9a53-4cef-b781-fa11d26f9701" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="4c4279f4-c355-4e8c-bf9f-6e7a023d3f78" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="50e6e0b5-7d96-4d10-a596-cd878ede94e3" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="5deb9e40-7474-4b94-8a63-c8128f21f41a" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1964/654/2017-05-01/1/deu/2017-05-01/regelungstext-1.xml" GUID="d4ed99a5-0fe6-487d-a976-d53c8247fe76" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1964/654/2017-05-01/1/deu/2017-05-01/regelungstext-1.xml" GUID="4ccbaca7-6f01-485f-aa66-bf390b3b2626" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1964-08-05" GUID="f3b1e42a-1bb5-4966-830b-ca1205f3f994" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="0db98622-66ce-42ff-a2b5-ef703f1e245d" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="8381dfc8-d5f9-4c1d-bd1b-b116a8b10f03" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="a3d7c585-1b51-4c81-8485-b326957cd3f9" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1964-08-05" type="generation" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" GUID="7e38848d-465b-4123-9d7c-972f726c90f1" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1964-09-21" type="generation" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" GUID="56ed76fb-b3c2-4a1f-a4bf-71d8f0630130" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="6e515506-ee08-4c5b-ad27-13a796598d66" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="843b4b33-9922-4c1d-a6fb-dae13e99ed9d" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="c190a920-e438-4853-b36c-3b9954a5b3d5" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung besitzen keine fachliche Korrektheit. -->
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="af7fbf41-211a-4d8a-bd19-d832b2cdf38e" eId="meta-1_proprietary-1">
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
    <akn:preface GUID="e303a2bb-6aad-4339-8830-790b48c047e5" eId="einleitung-1">
      <akn:longTitle GUID="192b6385-8278-4d9a-badd-22fd6ef5f858" eId="einleitung-1_doktitel-1">
        <akn:p GUID="6430da88-6df6-4199-9d9b-1d5af2e812b3" eId="einleitung-1_doktitel-1_text-1">
          <akn:docStage GUID="4bdd6027-08c0-418b-a069-5ffc3c72bbb1" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
          <akn:docProponent GUID="f2758d46-c294-4894-858e-58d822efba23" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
          <akn:docTitle GUID="47a53f27-e038-4d4a-b4a1-643fff6d45ac" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
          <akn:shortTitle GUID="b6d25e65-9e31-402e-9a7e-3b645ea52a3d" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(<akn:inline name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung" GUID="272f3e70-ebeb-4ca5-8b34-7b22af3a8055" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1">Vereinsgesetz</akn:inline>)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="5b62ada8-dd4d-4403-bbcf-39abae1ff0b8" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="ae68a8b5-258f-4b77-84d1-1761b187144b" eId="einleitung-1_block-1_datum-1">Vom 5. August 1964 </akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="0a07b3be-9c4c-4a28-baf6-fe49b0279675" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="4178d836-d4dd-46c4-9a47-68e129aa449e" eId="preambel-1_formel-1">
        <akn:p GUID="34458810-74e9-45d1-8966-0b64d293b341" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="af6373e9-b4d1-49f9-9fae-e3102a79a6df" eId="hauptteil-1">
      <akn:article GUID="8d0fbc11-229a-4c68-af3d-5da89ec803a4" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="568223c7-1f51-4191-93d2-e845273b42da" eId="hauptteil-1_art-1_bezeichnung-1"> § 20</akn:num>
        <akn:paragraph GUID="bd381e53-d9e4-43bd-be08-929280378770" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="c48c313c-5b11-4052-8c05-35d590df3a85" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
          <akn:list GUID="85ee9a46-5cf1-4c48-9fa1-b8f2f2de738c" eId="hauptteil-1_art-1_abs-1_untergl-1">
            <akn:intro GUID="9c29b813-e537-4120-b60b-ea1beaf4ac63" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="069d36e3-5405-42ed-bb43-4e1b0eaf266f" eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
            </akn:intro>
            <akn:point GUID="76a91f38-1fe7-4b50-9ac1-241b0659306a" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="65344aaa-e697-4e40-ab8f-e02e953fce2f" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
              <akn:content GUID="c1b8644f-0b4c-4f5b-8ddf-9452612b15ef" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="140d0f59-84ef-43d3-8e39-1f9ae7c2ebe7" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="dcb13cf2-2d31-42a6-8b76-b959d53d5508" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="b53d8480-50e1-48f2-b76e-017ce41930c4" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
              <akn:content GUID="73ecad8e-d4b9-4482-887c-945c4fdbf4a3" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="6aee556e-7bd5-41d0-9305-31b1dd9726de" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="654416d8-eb75-4da9-a39d-d68e9d66565c" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="96be7818-80c7-4be2-afec-f83e1f007a58" eId="hauptteil-1_art-1_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit schwerer Strafe bedroht ist.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
        <akn:paragraph GUID="a86b4d7e-7884-4b27-9ae8-7f442c53586b" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="582b1584-e907-4488-99d1-6e2b777f69fc" eId="hauptteil-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
          <akn:content GUID="1cef1e5a-c012-436d-999f-9af481852a4f" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="e30c4a91-45ab-468f-b26c-1135606b26d9" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <!-- Artikel 34: Geltungszeitregel-->
      <akn:article refersTo="geltungszeitregel" GUID="d2edeaf3-3f73-426d-bbce-71f4ea537f69" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="602e3d1c-1a52-4822-a9bb-265aebba47cf" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 34</akn:num>
        <akn:heading GUID="3b9f634e-8ebf-4e3f-b05f-377a21a4a007" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <!-- Absatz (1) -->
        <akn:paragraph GUID="b7c1df96-67bc-4922-bb91-dfd51b70ab35" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="2a3e3ff4-de94-4110-bd5f-aafa04f34446" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="c32f7a29-e4ec-4b3c-b1a9-4a7e5a87f8dd" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="f157bd9f-7a7f-40dc-9b03-37f1066dcf26" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
    <akn:conclusions GUID="ea0a6e83-72b8-493c-b349-a04606a5ab55" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="e41752f0-2698-48e6-8544-826916fd5bdb" eId="schluss-1_formel-1">
        <akn:p GUID="07a1233c-8944-4bb9-a567-3503c3dab6c4" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="e769d4a8-52a6-491f-9a52-f2231e7ea429" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="e038ae79-474f-4964-9b80-30d5a973b8f8" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="08b7b507-c6f6-45cf-9592-ac30fde37a52" eId="schluss-1_blockcontainer-1_text-1_ort-1">Bonn</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="1964-08-05" GUID="25e9b36a-a8a5-4ffa-b122-0e37f71649c0" eId="schluss-1_blockcontainer-1_text-1_datum-1">5. August 1964</akn:date>
        </akn:p>
        <akn:signature GUID="cb36e2a9-e8e3-4b61-837d-ee30cf50783e" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="431a6a4d-5a3c-45ca-a228-be78b2fcbd7c" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="87d5994b-974f-41c9-8c79-7994ecb9e92d" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Lübke</akn:person>
        </akn:signature>
        <akn:signature GUID="559ec394-3ad8-4180-9226-da8fa02fb06d" eId="schluss-1_blockcontainer-1_signatur-2">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="85a15f41-0461-45a5-9cc8-77f220c3e510" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="263bb55c-a650-4fa1-84ce-6ad11c69ff49" eId="schluss-1_blockcontainer-1_signatur-2_person-1">Mende</akn:person>
        </akn:signature>
        <akn:signature GUID="7205a179-1d6e-4e7d-aca4-7a4ab81af07e" eId="schluss-1_blockcontainer-1_signatur-3">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="3758a081-92b6-4768-9d8e-86a1f2023277" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1">Der Bundesminister des Innern</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="7963c16d-12a9-4d22-9913-b4b534af695e" eId="schluss-1_blockcontainer-1_signatur-3_person-1">Hermann Höcherl</akn:person>
        </akn:signature>
        <akn:signature GUID="480f336d-2b04-4827-9be6-78365f62df1c" eId="schluss-1_blockcontainer-1_signatur-4">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="de0778fe-6ee1-42ea-9ce6-3a849fab9323" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1">Der Bundesminister der Justiz</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="8a8ef26a-03d5-495a-93cf-a4f04e6c6585" eId="schluss-1_blockcontainer-1_signatur-4_person-1">Bucher</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>
');

UPDATE norm_manifestation SET publish_state = 'UNPUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/1964/654/2017-05-01/1/deu';

