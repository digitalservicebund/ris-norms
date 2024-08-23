-- First delete announcement because of foreign key
DELETE FROM announcements where eli = 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1';

-- Amending law "Wachstumschancengesetzt"
DELETE FROM norms where guid = 'c58770da-120c-4d46-9f19-737be72590b7';
INSERT INTO norms (guid, eli, xml)
VALUES ('c58770da-120c-4d46-9f19-737be72590b7', 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
--><?xml-model href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/26e27e3a56996587fe737639fd80e2c4a8fe0311/Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
   <akn:act name="regelungstext">
<!-- Metadaten-->
      <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
         <akn:identification eId="meta-1_ident-1" GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22" source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761" value="eli/bund/bgbl-1/2004/s1673/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a90733dd-4780-472d-8371-a3690f2e869a" value="eli/bund/bgbl-1/2024/108"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b" name="übergreifende-id" value="2c1a6ec9-7161-4e2f-8999-91219180d991"></akn:FRBRalias>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811" date="2024-03-27" name="verkuendungsfassung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e" value="de"></akn:FRBRcountry>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5" value="108"></akn:FRBRnumber>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8" value="bgbl-1"></akn:FRBRname>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="af948830-e615-4dd5-8237-b8c830939f78" value="regelungstext-1"></akn:FRBRsubtype>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="85e064f4-f56f-4cef-86a4-6801cc71c712" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="e8f52739-79c1-4e59-9db2-004180c8b29c" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="c2e78146-833b-4d78-a055-27f05eeaf018" name="aktuelle-version-id" value="53d31e3c-5c46-4e96-8017-b0db064561a1"></akn:FRBRalias>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c" name="nachfolgende-version-id" value="5d84cd1d-3575-4a03-bb6c-f17834e392fd"></akn:FRBRalias>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="6aace8d0-001f-4829-9794-79857835ee45" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="a6dc32d0-140d-4147-88b2-d64639b91742" date="2004-07-16" name="verkuendung"></akn:FRBRdate>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="b872861e-0add-4bbb-ba90-a5edb3c40038" language="deu"></akn:FRBRlanguage>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc" value="1"></akn:FRBRversionNumber>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="4c881fca-0e36-440e-a5f9-513079ccc77f" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1.xml"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="d0491f91-d599-46d4-ba61-c7426b607e4f" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1.xml"></akn:FRBRuri>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612" date="2024-03-24" name="generierung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="1d015a20-823e-496e-808d-27c8fec85b51" href="recht.bund.de"></akn:FRBRauthor>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67" value="xml"></akn:FRBRformat>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle eId="meta-1_lebzykl-1" GUID="cef30a63-d738-4712-bac8-50fda44949e2" source="attributsemantik-noch-undefiniert">
<!-- 1. Originales Gesetz -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="65f3910d-4e79-45f8-a24d-44ef7523963a" date="2024-03-27" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung"></akn:eventRef>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c" date="2024-08-24" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten"></akn:eventRef>
            <!-- 2. -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="85e92fc8-b381-47bb-a476-c35fa2794336" date="2023-01-01" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"></akn:eventRef>
          </akn:lifecycle>
          <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1" GUID="4cc2a228-5c5e-44bb-b19d-e6b145efe69b">
            <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="127e593d-6ce7-4ee1-8c1d-8ab537593a25">
               <akn:textualMod type="substitution" GUID="de2d69cd-691b-4b61-b1fb-db471d9f52aa" eId="meta-1_analysis-1_activemod-1_textualmod-1">
                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" GUID="c42641b3-e987-4013-b713-52017ca0ca78"></akn:source>
                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-27_abs-6_inhalt-1_text-1/302-338.xml" GUID="e99747be-63b9-4729-adf3-b4b4299e188b"></akn:destination>
                  <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="362f30ad-343c-4b8e-8bb4-993c8a0f5256"></akn:force>
               </akn:textualMod>
               <akn:textualMod type="substitution" GUID="a9447c63-fd78-44b9-9426-48ea9e86cf68" eId="meta-1_analysis-1_activemod-1_textualmod-2">
                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="c509c75e-4813-491a-a973-9b23e2b8be03"></akn:source>
                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-71_abs-2" GUID="e1d7e59f-5c59-4834-847d-3dc1dbc26259"></akn:destination>
                  <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="5d66169e-3af1-453b-8009-b5d62ad16aee"></akn:force>
               </akn:textualMod>
            </akn:activeModifications>
          </akn:analysis>
         <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16" start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f" start="#meta-1_lebzykl-1_ereignis-3" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1" GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>neufassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
               <meta:fna>754-28-1</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
               <meta:federfuehrung>
                  <meta:federfuehrend ab="2024-03-27" bis="unbestimmt">BMF - Bundesministerium der Finanzen</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
               <meta:subtyp>ÄN</meta:subtyp>
           </meta:legalDocML.de_metadaten_ds>
         </akn:proprietary>
      </akn:meta>
      <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Gesetz zur Stärkung von Wachstumschancen, Investitionen und Innovation sowie Steuervereinfachung und Steuerfairness

<!-- Fußnote -->
                  <akn:authorialNote eId="einleitung-1_doktitel-1_text-1_doctitel-1_fnote-1" GUID="9de7999f-a222-43ac-a648-f92c5a8ee927" marker="*)" placement="bottom" placementBase="">
                     <akn:p eId="einleitung-1_doktitel-1_text-1_doctitel-1_fnote-1_text-1" GUID="7c210663-00e0-48c1-b86d-d02f1c422de0">Artikel 31 dient der Umsetzung der Richtlinie (EU) 2021/514 des Rates vom 22. März 2021 zur Änderung der Richtlinie 2011/16/EU über die
                            Zusammenarbeit der Verwaltungsbehörden im Bereich der Besteuerung (ABl. L 104 vom 25.3.2021, S. 1).
                            </akn:p>
                  </akn:authorialNote>
               </akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">(Wachstumschancengesetz)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="ce0ac10d-510e-4148-8a8d-a4406a8b0a67">
               <!-- §1 -->
               <akn:article eId="hauptteil-1_para-1" GUID="17e9c25e-251d-48a7-8e74-3f0bb4b93b6b" period="#geltungszeitgr-2">
                  <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="24f5f556-e005-4d10-8d7e-d92f141780d7">
                     <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="6fa27ec6-e4c2-476a-8729-85e16b3105d1" name="1"></akn:marker>§1</akn:num>
                  <akn:heading eId="hauptteil-1_para-1_überschrift-1" GUID="31b97e2a-1872-4a52-aba1-d075bae3c0d6">Änderung des Einkommensteuergesetzes</akn:heading>
                  <!-- Absatz 1 -->
                  <akn:paragraph eId="hauptteil-1_para-1_abs-1" GUID="58ea2242-386a-42b9-8f23-eaa1678159f1">
                     <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="c38a41af-4911-4b1a-9404-929912af983e">
                        <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="00aa91c0-6457-4200-bce9-a6350377c58f" name="1"></akn:marker>(1)</akn:num>
                                    <akn:list GUID="5d6fc824-7926-43b4-b243-b0096da183f8" eId="hauptteil-1_para-1_abs-1_untergl-1">
                                    <akn:intro GUID="5d6fc824-7926-43b4-b243-b0096da183f9" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1">
                     <akn:p GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1"> Das <akn:affectedDocument GUID="e9e5ceb0-c831-45da-8fa4-aaaa16572bee" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1">Einkommensteuergesetz in der Fassung der Bekanntmachung vom 8. Oktober 2009 (BGBl. I S. 3366, 3862),
                            das zuletzt durch Artikel 20 des Gesetzes vom 22. Dezember 2023 (BGBl. 2023 I Nr. 411) geändert worden ist</akn:affectedDocument>, wird
                            wie folgt geändert:</akn:p>
                  </akn:intro>
                  <akn:point GUID="49983c1a-c952-4ab1-b926-2f414c05da7d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="634ddcaa-4851-4656-8b65-f26c8f56d1f1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                              <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" GUID="b10ede97-383f-49b2-bf45-7deb7eeb7256" name="1"></akn:marker>1.
                           </akn:num>
                     <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1" GUID="63c7840e-f93f-497a-843f-3afbd9ec8784">
                        <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1" GUID="a8786a31-bf59-41f9-b945-7785ed93861a">
                           <akn:mod refersTo="aenderungsbefehl-ersetzen" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" GUID="a8786a31-bf59-41f9-b945-7785ed93861b">
                              In <akn:ref href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-27_abs-6_inhalt-1_text-1/302-338.xml" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" GUID="a8786a31-bf59-41f9-b945-7785ed93861c">§ 6b Absatz 6 Satz 2</akn:ref> werden die Wörter „<akn:quotedText startQuote="„" endQuote="“" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="a8786a31-bf59-41f9-b945-7785ed93861d">§ 7 Absatz 4 Satz 1, Absatz 5 und 5a</akn:quotedText>“ durch die Wörter „<akn:quotedText startQuote="„" endQuote="“" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="a8786a31-bf59-41f9-b945-7785ed93861f">§ 7 Absatz 4 Satz 1 und Absatz 5</akn:quotedText>“ ersetzt.
                           </akn:mod>
                        </akn:p>
                     </akn:content>
                     </akn:point>
                     <akn:point GUID="4bf3aae7-b83c-4429-8518-040182a228d5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="81e4878a-ad3a-4ec9-b32a-074c32367d12" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="99188b27-8978-4f86-9fdf-cec0c62a6185" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>2.</akn:num>
                        <akn:content GUID="e447e69e-5b1f-4880-b0d9-04a596ab2a01" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="0d881f49-5342-4629-9874-fc4c248b630a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                              <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="58161d8d-b02e-4d54-a362-25a734c5e79b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1">
                                 <akn:ref href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-71_abs-2" GUID="88c21ccc-f528-4dd1-ab38-48c562daf53c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1">
                                 § 19 Absatz 2 Satz 3 wird wie folgt gefasst:</akn:ref>
                                 <akn:quotedStructure endQuote="“" startQuote="„" GUID="2785f9b5-51ee-4876-af06-17219ddd9351" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1"><akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_text-1" GUID="0d7a4336-d505-4125-bdb5-13987833ce75">Der  maßgebende  Prozentsatz,  der  Höchstbetrag  des  Versorgungsfreibetrags  und  der  Zuschlag  zum
                           Versorgungsfreibetrag sind der nachstehenden Tabelle zu entnehmen:</akn:p>
                           <akn:table GUID="e6095f0b-884b-4be8-a033-58282d5dd514" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1">
                              <akn:tr GUID="990c5783-9b51-4062-87df-e7c73c6b3598" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1">
                                 <akn:th GUID="1b3dfb9b-41cf-4650-aad6-f5c250d71c26" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-1" rowspan="2">
                                    <akn:p GUID="63cbbdb0-a993-4c76-bfe1-06176e954df1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Jahr des Versorgungsbeginns</akn:p>
                                 </akn:th>
                                 <akn:th GUID="e1849850-f1bc-485e-b9cb-ecd2ac14a513" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                                    <akn:p GUID="e25d8ec3-3470-4778-beae-bb23f26ff94e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">Versorgungsfreibetrag</akn:p>
                                 </akn:th>
                                 <akn:th GUID="1512c9fd-fe29-4363-915f-f1b34f022c4f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-3" rowspan="2">
                                    <akn:p GUID="5ffcdd71-953f-4035-9f4a-cc82038f1932" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-3_text-1">Zuschlag zum Versorgungsfreibetrag in Euro</akn:p>
                                 </akn:th>
                              </akn:tr>
                              <akn:tr GUID="ce0a57b3-bcd4-4a51-bd34-bd3df5ea5e54" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2">
                                 <akn:th GUID="82a5507b-1c59-4989-9810-ea39897b3142" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-1">
                                    <akn:p GUID="f502b5ab-d7af-49a2-88ed-1cb7c380387a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-1_text-1">in % der Versorgungsbezüge</akn:p>
                                 </akn:th>
                                 <akn:th GUID="a4b33640-a58f-4fa5-9286-c6fc3a4521dc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-2">
                                    <akn:p GUID="44c877d7-4e7b-427a-9c69-58dfeaddea39" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-2_text-1">Höchstbetrag in Euro</akn:p>
                                 </akn:th>
                              </akn:tr>
                              <akn:tr GUID="c7b2cdea-4ac3-4af7-9d7d-29345f1d6ed8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3">
                                 <akn:td GUID="6135164c-082f-4f25-8146-39b2c7851f8d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                                    <akn:p GUID="1daba036-230a-4595-aca2-c72faa0dbfaf" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">bis 2005</akn:p>
                                 </akn:td>
                                 <akn:td GUID="4b1822c2-86f2-499b-9e15-3e238a9475e5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                                    <akn:p GUID="47f2763e-83f9-4a8e-81e3-188ab8ce46c6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">40,0</akn:p>
                                 </akn:td>
                                 <akn:td GUID="ff209893-13c7-4e15-a401-dd660be8c527" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-3">
                                    <akn:p GUID="95dffc04-b963-4f63-b39d-2da5ba650654" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-3_text-1">3 000</akn:p>
                                 </akn:td>
                                 <akn:td GUID="3972a0d5-7c54-4bcc-9c3c-e59630f840b0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-4">
                                    <akn:p GUID="9834fccb-d13e-4ac5-a2ff-dd242fe0f763" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-4_text-1">900</akn:p>
                                 </akn:td>
                              </akn:tr>
                              <akn:tr GUID="b671a30c-dc1a-4898-970d-e371abe9aed9" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4">
                                 <akn:td GUID="fad8cef3-00be-4d74-b23e-dcafa2516bf2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-1">
                                    <akn:p GUID="a2a1f303-9c82-4c6c-a95f-35f01941029f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1">ab 2006</akn:p>
                                 </akn:td>
                                 <akn:td GUID="e3c06299-3855-41e7-995a-f395d7f84461" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-2">
                                    <akn:p GUID="85ae462a-e205-4f7e-8532-4b993b5ea12e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1">38,4</akn:p>
                                 </akn:td>
                                 <akn:td GUID="aff4aa39-7b78-4f69-8004-a95a88c4fa9c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-3">
                                    <akn:p GUID="08bc3ade-e920-438b-8448-7fcedb6192fa" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-3_text-1">2 880</akn:p>
                                 </akn:td>
                                 <akn:td GUID="1180c599-4219-4f91-a2a0-e5bf5754430d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-4">
                                    <akn:p GUID="7a10ff40-0025-4622-97d4-0333473653f2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-4_text-1">864</akn:p>
                                 </akn:td>
                              </akn:tr>
                              <akn:tr GUID="458a49d1-b3ea-46bd-8051-10960c0f4094" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5">
                                 <akn:td GUID="dd0ed33d-b834-4e5a-906c-59474385394d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-1">
                                    <akn:p GUID="93656d01-643a-4a9a-8f9c-de0b85f43955" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1">2007</akn:p>
                                 </akn:td>
                                 <akn:td GUID="3fd9bac2-803f-42fc-938f-cbd6b58bc833" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-2">
                                    <akn:p GUID="23c53d5e-8217-4d29-a099-2575c85ae7dc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1">36,8</akn:p>
                                 </akn:td>
                                 <akn:td GUID="22bea0e1-db96-4694-98c7-c2d2574b2263" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-3">
                                    <akn:p GUID="257a1968-fe04-4180-a8cb-18c825f1c836" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-3_text-1">2 760</akn:p>
                                 </akn:td>
                                 <akn:td GUID="5e6ed51d-48da-44e9-b8bd-7453929bef81" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-4">
                                    <akn:p GUID="22751007-c55f-41c4-89ae-b7a3acef841a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-4_text-1">828</akn:p>
                                 </akn:td>
                              </akn:tr>
                              <akn:tr GUID="23fd3a0c-b7ff-471c-a6b2-10e2f25b8b5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6">
                                 <akn:td GUID="037d324d-67dd-4c6c-b74f-0da3c094d7f3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-1">
                                    <akn:p GUID="07be3174-ab04-4ae6-a8f6-0c5652ffe1ea" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1">2008</akn:p>
                                 </akn:td>
                                 <akn:td GUID="16acddc4-a085-43be-b413-a9c7240632c0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-2">
                                    <akn:p GUID="0c5a3e05-7a6c-43cd-b19e-c748e2046e02" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1">35,2</akn:p>
                                 </akn:td>
                                 <akn:td GUID="82745578-4b7e-48e3-ba66-a5e80caa6e5f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-3">
                                    <akn:p GUID="84690030-0b62-409e-acee-67b422fb4e1e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-3_text-1">2 640</akn:p>
                                 </akn:td>
                                 <akn:td GUID="8e897241-573d-4b7d-87a3-3fbfaba46602" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-4">
                                    <akn:p GUID="88ec824e-0b3c-4e6e-9e67-93b2c9ba0c07" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-4_text-1">792</akn:p>
                                 </akn:td>
                              </akn:tr>
                           </akn:table>
                        </akn:quotedStructure>
                        </akn:mod>
                        </akn:p>
                        </akn:content>
                     </akn:point>
                     </akn:list>
                  </akn:paragraph>
               </akn:article>
               <!-- §2 -->
               <akn:article eId="hauptteil-1_para-2" GUID="26eb1ba5-2f9d-4efc-8522-061bbcc4a7b9" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="928b837a-52a8-4568-839b-db2866abd202">
                           <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="39aa4be1-9228-452e-b9a0-9c13501a9b90" name="2"></akn:marker>§2</akn:num>
                  <akn:paragraph GUID="73863cd9-dd0f-472f-85fb-34fb660a2178" eId="hauptteil-1_para-2_abs-1">
                     <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="cab55e58-3901-4e19-bb04-a0a1c85ecaad">
                              <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="031c1d63-eb3c-4ce6-a370-1b83a01e1a39" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="41cf5098-928d-4027-b78d-59de7632d758" eId="hauptteil-1_para-2_abs-1_inhalt-1"><akn:p GUID="84c4afdf-9e55-43f3-970e-427b5b5f3bab" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §3 -->
               <akn:article eId="hauptteil-1_para-3" GUID="1fbffcff-3845-4175-af88-e1cac9d8053d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="eb1e352f-4e25-4eea-a1f4-d93920af7b8d">
                           <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" GUID="4fb283d9-f671-4272-86ca-e5e4cbbf0538" name="3"></akn:marker>§3</akn:num>
                  <akn:paragraph GUID="88a7bf19-3379-4bb5-883b-ff7f9873cd13" eId="hauptteil-1_para-3_abs-1">
                     <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="68464716-39a9-44dc-bb44-8d2a5ed1916b">
                              <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="143b1044-ee76-42b9-9a86-6fb3ff1a7c2c" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="748cf054-b5c6-473f-8edd-e967d7a605be" eId="hauptteil-1_para-3_abs-1_inhalt-1"><akn:p GUID="530ab977-cf41-4f6d-8db8-b7a164e2595a" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §4 -->
               <akn:article eId="hauptteil-1_para-4" GUID="5a8416ef-c656-4039-a0fb-a2463aa1e0d7" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="8222bebf-7c0a-4a6c-9795-9705c816a2c0">
                           <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1" GUID="8a0a9a47-744f-42b3-bc9e-2ce63ec5ff1d" name="4"></akn:marker>§4</akn:num>
                  <akn:paragraph GUID="45bcaf24-834f-4e5e-bbd1-9e8c127bf403" eId="hauptteil-1_para-4_abs-1">
                     <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="d791a3d8-8770-4306-ab32-786391c32eb0">
                              <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" GUID="d6db7c2e-d058-43cb-b0f4-00445997b3d6" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="8ccbd60b-4f1e-4aad-b712-cf81dc981e8b" eId="hauptteil-1_para-4_abs-1_inhalt-1"><akn:p GUID="08109b53-188a-484d-9aae-94e355cd5c96" eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §5 -->
               <akn:article eId="hauptteil-1_para-5" GUID="ec075ce0-a6a5-4075-9041-b0304da221b0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-5_bezeichnung-1" GUID="4bfef6b2-3527-4247-a8b2-dc1bc468e952">
                           <akn:marker eId="hauptteil-1_para-5_bezeichnung-1_zaehlbez-1" GUID="af885e36-4dc7-46de-bc06-68423507c9a6" name="5"></akn:marker>§5</akn:num>
                  <akn:paragraph GUID="5fdaec7c-753e-4218-8040-22fa6d9b594d" eId="hauptteil-1_para-5_abs-1">
                     <akn:num eId="hauptteil-1_para-5_abs-1_bezeichnung-1" GUID="95315d18-139e-436e-8822-ff5e81f16583">
                              <akn:marker eId="hauptteil-1_para-5_abs-1_bezeichnung-1_zaehlbez-1" GUID="603af9f3-25c9-4a5f-bc24-3c78353a34f0" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="7263cdbb-1e3d-4cd4-8491-c901ae605ee0" eId="hauptteil-1_para-5_abs-1_inhalt-1"><akn:p GUID="5740dcc2-4d32-4e55-ac8f-cc6e5886d0a2" eId="hauptteil-1_para-5_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §6 -->
               <akn:article eId="hauptteil-1_para-6" GUID="9d818ef1-7e1a-44b0-b584-0690c7eb636d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-6_bezeichnung-1" GUID="4a538475-efee-458c-9311-005f4dfb72ed">
                           <akn:marker eId="hauptteil-1_para-6_bezeichnung-1_zaehlbez-1" GUID="7456f473-2d28-4931-b850-b3c487d4f5ff" name="6"></akn:marker>§6</akn:num>
                  <akn:paragraph GUID="959ff2b2-0ae9-4687-b76c-f6b6a7020693" eId="hauptteil-1_para-6_abs-1">
                     <akn:num eId="hauptteil-1_para-6_abs-1_bezeichnung-1" GUID="3e39302a-d279-47e7-b2ba-3105b4618a43">
                              <akn:marker eId="hauptteil-1_para-6_abs-1_bezeichnung-1_zaehlbez-1" GUID="24dbaf9f-89f1-41fe-9813-a03c92eceaa7" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="ed798c34-6080-4ee1-b9e3-872936250312" eId="hauptteil-1_para-6_abs-1_inhalt-1"><akn:p GUID="cb6c9acd-327e-4eac-90ec-a1b58d58a969" eId="hauptteil-1_para-6_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §7-->
               <akn:article period="#geltungszeitgr-3" GUID="deb9ed64-5c87-4d22-a7d9-7c2e094bf2ba" eId="hauptteil-1_para-7">
                  <akn:num GUID="922ce69e-105c-4a77-aa0d-6b2925059681" eId="hauptteil-1_para-7_bezeichnung-1"><akn:marker name="7" GUID="3c65e2e2-8819-47e7-995c-3de24fe4993c" eId="hauptteil-1_para-7_bezeichnung-1_zaehlbez-1"></akn:marker>§7</akn:num>
                  <akn:paragraph GUID="d2a38244-5eaa-4d1a-be15-4eba763d7659" eId="hauptteil-1_para-7_abs-1">
                     <akn:num GUID="3628d8fb-b498-47a6-b916-976c9f22d025" eId="hauptteil-1_para-7_abs-1_bezeichnung-1"><akn:marker name="1" GUID="e092ad6a-152c-4dba-90eb-11a2ddcd8d5d" eId="hauptteil-1_para-7_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="236bc2a6-9d69-433e-b116-22faf5b6d5b9" eId="hauptteil-1_para-7_abs-1_inhalt-1"><akn:p GUID="577f917b-cf9a-4c66-a1e0-076423a0c4a1" eId="hauptteil-1_para-7_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §8-->
               <akn:article period="#geltungszeitgr-3" GUID="b0c2a2cf-4668-455a-84d3-0ef7476458e8" eId="hauptteil-1_para-8">
                  <akn:num GUID="c6898e2d-1c7e-4f4f-a47c-cecad2743c70" eId="hauptteil-1_para-8_bezeichnung-1"><akn:marker name="8" GUID="3e31e152-c02c-4776-b3d5-16fa0ed62aa7" eId="hauptteil-1_para-8_bezeichnung-1_zaehlbez-1"></akn:marker>§8</akn:num>
                  <akn:paragraph GUID="2f5a0bb3-af16-44d8-91df-a980010eeb6f" eId="hauptteil-1_para-8_abs-1">
                     <akn:num GUID="bb45e054-3012-43de-b010-6ffb31fdb29d" eId="hauptteil-1_para-8_abs-1_bezeichnung-1"><akn:marker name="1" GUID="71b460b0-5d3f-4302-89cc-45d522728db7" eId="hauptteil-1_para-8_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="dcb2efcd-482a-49e7-b3ea-91ef9c73c7d6" eId="hauptteil-1_para-8_abs-1_inhalt-1"><akn:p GUID="8ad8a537-eb73-45ad-8305-d1e670309b09" eId="hauptteil-1_para-8_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §9-->
               <akn:article period="#geltungszeitgr-3" GUID="5963c0d6-561b-4441-bd69-0b3ce41ee36d" eId="hauptteil-1_para-9">
                  <akn:num GUID="5bdfd052-4d09-466f-b6fb-e4a64c53073b" eId="hauptteil-1_para-9_bezeichnung-1"><akn:marker name="9" GUID="92a3b84e-e8c3-412f-85bb-2dd003b24931" eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1"></akn:marker>§9</akn:num>
                  <akn:paragraph GUID="6e827ef6-9f28-4127-90a9-be4d4abd53e5" eId="hauptteil-1_para-9_abs-1">
                     <akn:num GUID="550a8be0-3c59-414e-9727-7387b55d0241" eId="hauptteil-1_para-9_abs-1_bezeichnung-1"><akn:marker name="1" GUID="e156d650-10a2-4e12-8acc-9a662b51e4b8" eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="6187f38c-523a-4a7d-a26e-df66e2869eb6" eId="hauptteil-1_para-9_abs-1_inhalt-1"><akn:p GUID="4565efc7-8e35-4a80-9970-463075f1d3bd" eId="hauptteil-1_para-9_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §10-->
               <akn:article period="#geltungszeitgr-3" GUID="58a9a808-3396-4807-a4f7-8b8147584e12" eId="hauptteil-1_para-10">
                  <akn:num GUID="f080e674-5202-4d39-9e01-1a4466334aa2" eId="hauptteil-1_para-10_bezeichnung-1"><akn:marker name="10" GUID="6762ddbd-101c-4158-b0ad-c3d1e935f6ff" eId="hauptteil-1_para-10_bezeichnung-1_zaehlbez-1"></akn:marker>§10</akn:num>
                  <akn:paragraph GUID="97bf6fc9-29a0-4045-a6c8-e6c4dfd1657a" eId="hauptteil-1_para-10_abs-1">
                     <akn:num GUID="10990a49-5cf0-43ad-8e8c-70aa4e338b08" eId="hauptteil-1_para-10_abs-1_bezeichnung-1"><akn:marker name="1" GUID="9f0cf218-9757-4787-a835-33f5efffdb77" eId="hauptteil-1_para-10_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="5b8748c7-5ed5-48b3-99c8-54968c230c0b" eId="hauptteil-1_para-10_abs-1_inhalt-1"><akn:p GUID="fef2d619-2805-438e-9183-85eb9b0ebe73" eId="hauptteil-1_para-10_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §11-->
               <akn:article period="#geltungszeitgr-3" GUID="eb131280-ceb5-46c1-8e37-ea4ac53ed8f5" eId="hauptteil-1_para-11">
                  <akn:num GUID="73ba14c3-fdc9-436b-93ee-d87dfba8c3f2" eId="hauptteil-1_para-11_bezeichnung-1"><akn:marker name="11" GUID="b0b9143c-69ec-4d7b-9f0b-382870eac376" eId="hauptteil-1_para-11_bezeichnung-1_zaehlbez-1"></akn:marker>§11</akn:num>
                  <akn:paragraph GUID="4f57c794-5934-4e25-a57b-6ccefc2ac328" eId="hauptteil-1_para-11_abs-1">
                     <akn:num GUID="9240ad45-231c-4226-ba21-6a6700cc8b63" eId="hauptteil-1_para-11_abs-1_bezeichnung-1"><akn:marker name="1" GUID="12f504f2-3ab7-4849-82bc-c403d78f2dbd" eId="hauptteil-1_para-11_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="77dc0ccf-60d2-4037-8441-79911c6467a3" eId="hauptteil-1_para-11_abs-1_inhalt-1"><akn:p GUID="202438e6-1f09-4d2d-a2d3-137484399c94" eId="hauptteil-1_para-11_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §12-->
               <akn:article eId="hauptteil-1_para-12" GUID="170c345b-dd19-4baf-8a5e-9244d025938a" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-12_bezeichnung-1" GUID="4542cf12-8686-4160-917c-d5db81e78cf2">
                     <akn:marker eId="hauptteil-1_para-12_bezeichnung-1_zaehlbez-1" GUID="6b3e057a-0a92-4b64-abe9-a8e2b38e1158" name="12"></akn:marker>§12</akn:num>
                  <akn:paragraph GUID="f14b9add-3dc6-4f6e-a4e2-2f321b9f5dc6" eId="hauptteil-1_para-12_abs-1">
                     <akn:num eId="hauptteil-1_para-12_abs-1_bezeichnung-1" GUID="9a666831-6b7e-42fe-86b2-c459ac6b5074">
                        <akn:marker eId="hauptteil-1_para-12_abs-1_bezeichnung-1_zaehlbez-1" GUID="76cb03da-02ea-4b8b-9290-94cc996b4057" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="aed8b9e3-3557-46bf-8972-9e1f0308b6c8" eId="hauptteil-1_para-12_abs-1_inhalt-1"><akn:p GUID="564042f6-0177-4efd-8157-4f0a6eb9dec2" eId="hauptteil-1_para-12_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §13-->
               <akn:article eId="hauptteil-1_para-13" GUID="705a74fe-0a6b-4b6d-a6f2-5f5cf11ef46e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-13_bezeichnung-1" GUID="35a91591-8b58-47be-8e39-8b4df23eece5">
                     <akn:marker eId="hauptteil-1_para-13_bezeichnung-1_zaehlbez-1" GUID="df6f2f3a-54bf-414b-a78d-f39dccce241e" name="13"></akn:marker>§13</akn:num>
                  <akn:paragraph GUID="0fd92ffa-88be-4e0d-83a4-15616e79b778" eId="hauptteil-1_para-13_abs-1">
                     <akn:num eId="hauptteil-1_para-13_abs-1_bezeichnung-1" GUID="f4b56718-f4d3-413a-aa99-d9280c8ead98">
                        <akn:marker eId="hauptteil-1_para-13_abs-1_bezeichnung-1_zaehlbez-1" GUID="7808cf99-9318-47f6-bb02-eb228c4439ae" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="16e5195d-e15a-4b7c-b4d5-b79c3f8a5798" eId="hauptteil-1_para-13_abs-1_inhalt-1"><akn:p GUID="a3d8cf95-fba1-4a77-a4dc-f5530cacd554" eId="hauptteil-1_para-13_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §14-->
               <akn:article eId="hauptteil-1_para-14" GUID="7dd53189-857c-42c7-ba02-41c3368b2937" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-14_bezeichnung-1" GUID="979f9d20-5f33-4905-9e64-4d1da92e6bdf">
                     <akn:marker eId="hauptteil-1_para-14_bezeichnung-1_zaehlbez-1" GUID="ba42342c-a9ae-4d2d-a555-a3f54e6a0ad1" name="14"></akn:marker>§14</akn:num>
                  <akn:paragraph GUID="0012dc4e-1463-40d8-a479-b84252bd5243" eId="hauptteil-1_para-14_abs-1">
                     <akn:num eId="hauptteil-1_para-14_abs-1_bezeichnung-1" GUID="79629d3f-9c50-495c-bd5b-30e65724dad9">
                        <akn:marker eId="hauptteil-1_para-14_abs-1_bezeichnung-1_zaehlbez-1" GUID="9ec1af32-b50c-427a-8339-ccf2f4e3fed7" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="fb70326c-4fab-4610-9ff5-8aab75afbe98" eId="hauptteil-1_para-14_abs-1_inhalt-1"><akn:p GUID="e08f2997-6017-47fd-8aa6-e2f13c9d3afa" eId="hauptteil-1_para-14_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §15-->
               <akn:article eId="hauptteil-1_para-15" GUID="a8d84868-36a7-4625-9b07-fe4458f66ca1" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-15_bezeichnung-1" GUID="16715d45-84a1-46b6-b7e3-91ede92b9823">
                     <akn:marker eId="hauptteil-1_para-15_bezeichnung-1_zaehlbez-1" GUID="b1222588-8a37-4449-950e-f8063e9bbd3f" name="15"></akn:marker>§15</akn:num>
                  <akn:paragraph GUID="69eee9d9-22e7-4572-bca2-eccbe582baf6" eId="hauptteil-1_para-15_abs-1">
                     <akn:num eId="hauptteil-1_para-15_abs-1_bezeichnung-1" GUID="6b0a3008-7bd6-4bef-9c27-35186519359c">
                        <akn:marker eId="hauptteil-1_para-15_abs-1_bezeichnung-1_zaehlbez-1" GUID="273d6ab0-9a79-48b7-a4b3-b487720e6772" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="6fd066ef-31ec-480e-bd49-05095f6cbd48" eId="hauptteil-1_para-15_abs-1_inhalt-1"><akn:p GUID="c0bf44a0-a33e-4e7a-ac27-f24f152141a9" eId="hauptteil-1_para-15_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §16-->
               <akn:article eId="hauptteil-1_para-16" GUID="cd51af28-5db1-4d4c-ae23-ac5b3742613e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-16_bezeichnung-1" GUID="80d8f934-8f58-4ae8-a8f9-843f66390fec">
                     <akn:marker eId="hauptteil-1_para-16_bezeichnung-1_zaehlbez-1" GUID="b6b32743-dc1a-4d3a-8bd2-6f307394a485" name="16"></akn:marker>§16</akn:num>
                  <akn:paragraph GUID="66eb1100-279b-48b8-8419-425192bc9c53" eId="hauptteil-1_para-16_abs-1">
                     <akn:num eId="hauptteil-1_para-16_abs-1_bezeichnung-1" GUID="e4ac9fad-2131-48a6-9831-794023c22f31">
                        <akn:marker eId="hauptteil-1_para-16_abs-1_bezeichnung-1_zaehlbez-1" GUID="12595933-9cfb-4e0b-8299-1d0960e55a16" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="2b4fdbe0-f388-4337-998e-22adeaace890" eId="hauptteil-1_para-16_abs-1_inhalt-1"><akn:p GUID="0fa7e6e5-9645-4fe3-bc5d-030e0c2ce9c9" eId="hauptteil-1_para-16_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §17-->
               <akn:article eId="hauptteil-1_para-17" GUID="56bc720f-e450-4288-b024-0c7a6d0a86a6" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-17_bezeichnung-1" GUID="106acd9d-deb4-45c1-99be-8a81b07dd3de">
                     <akn:marker eId="hauptteil-1_para-17_bezeichnung-1_zaehlbez-1" GUID="e2aa6a48-1145-4668-a6a9-ee213372b6b2" name="17"></akn:marker>§17</akn:num>
                  <akn:paragraph GUID="b1fd674e-f956-4ab5-800e-fbd9fb130e39" eId="hauptteil-1_para-17_abs-1">
                     <akn:num eId="hauptteil-1_para-17_abs-1_bezeichnung-1" GUID="a59bd695-5a5c-40a4-a3a4-dd8de608250f">
                        <akn:marker eId="hauptteil-1_para-17_abs-1_bezeichnung-1_zaehlbez-1" GUID="7971a6b1-7119-40c1-b690-b8aa0fd70565" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="fc1bc033-49c3-4674-b8f5-923bba3620e8" eId="hauptteil-1_para-17_abs-1_inhalt-1"><akn:p GUID="3f23ef34-e2be-4f48-945b-95031cb81c6d" eId="hauptteil-1_para-17_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §18-->
               <akn:article eId="hauptteil-1_para-18" GUID="4b386140-6c09-47d2-8c2b-c9d4061b1d16" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-18_bezeichnung-1" GUID="12f88467-2c2c-49bd-959d-e264b0b90374">
                     <akn:marker eId="hauptteil-1_para-18_bezeichnung-1_zaehlbez-1" GUID="c173ef78-10d0-4c3c-9332-df3cecc655c0" name="18"></akn:marker>§18</akn:num>
                  <akn:paragraph GUID="69ad2f8a-1074-4f5b-9429-1a34220e67f0" eId="hauptteil-1_para-18_abs-1">
                     <akn:num eId="hauptteil-1_para-18_abs-1_bezeichnung-1" GUID="e8498a3b-261f-4e60-ae89-96020d20f710">
                        <akn:marker eId="hauptteil-1_para-18_abs-1_bezeichnung-1_zaehlbez-1" GUID="9709cc18-71b2-41d2-85ec-afaabc2a7781" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="90c86a81-0448-4fac-855b-9877b7873345" eId="hauptteil-1_para-18_abs-1_inhalt-1"><akn:p GUID="9ec30711-98f1-43a9-94b0-1f6d3e1a6eb6" eId="hauptteil-1_para-18_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §19-->
               <akn:article eId="hauptteil-1_para-19" GUID="954deb64-7f39-49a4-9646-372d3f1a2d0d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-19_bezeichnung-1" GUID="7716eb01-457e-4237-a8c4-efd97a7adb42">
                     <akn:marker eId="hauptteil-1_para-19_bezeichnung-1_zaehlbez-1" GUID="52ac1e49-fd0f-440d-939c-83407a0c4ddf" name="19"></akn:marker>§19</akn:num>
                  <akn:paragraph GUID="f88df7aa-09b8-4ac4-a60f-4399ac8c0ea7" eId="hauptteil-1_para-19_abs-1">
                     <akn:num eId="hauptteil-1_para-19_abs-1_bezeichnung-1" GUID="8e9c9fff-fd18-44a5-b2a7-24c7b4aeabef">
                        <akn:marker eId="hauptteil-1_para-19_abs-1_bezeichnung-1_zaehlbez-1" GUID="6284a6c3-6e28-4607-b7ec-a557cbbd2623" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="1b39fc5f-95d6-44a7-9fc5-7fa7dcc35401" eId="hauptteil-1_para-19_abs-1_inhalt-1"><akn:p GUID="3002b828-7e20-4d0a-b6c9-34446dea8150" eId="hauptteil-1_para-19_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §20-->
               <akn:article eId="hauptteil-1_para-20" GUID="ae509f70-aa93-4169-8e31-277c7a0cd762" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="d98ea318-c296-473e-be51-e08903bdd26c">
                     <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" GUID="bfa7e7f1-81c6-4419-a9aa-21e5dddc24e4" name="20"></akn:marker>§20</akn:num>
                  <akn:paragraph GUID="0f540208-d535-4df1-97fd-2ebcec47c073" eId="hauptteil-1_para-20_abs-1">
                     <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="7ee7f421-8684-4796-b7fd-b3758ca2598c">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" GUID="21b1dc7c-ed36-4d5c-a10f-666ecdd80c5f" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="b6634e8f-c8f1-4d7f-9c33-e70692be3c9c" eId="hauptteil-1_para-20_abs-1_inhalt-1"><akn:p GUID="16c99b24-06be-4af4-97b8-a9a6c773c22f" eId="hauptteil-1_para-20_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §21-->
               <akn:article eId="hauptteil-1_para-21" GUID="424939b2-67e3-4185-b146-42d71c631fe5" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-21_bezeichnung-1" GUID="f3bf0469-d948-4d12-8b9a-c768c13ade2d">
                     <akn:marker eId="hauptteil-1_para-21_bezeichnung-1_zaehlbez-1" GUID="12f2206e-a73f-43e4-b060-fbe2e1297ce4" name="21"></akn:marker>§21</akn:num>
                  <akn:paragraph GUID="feea1193-b843-45ef-82d9-9e20f1453bc1" eId="hauptteil-1_para-21_abs-1">
                     <akn:num eId="hauptteil-1_para-21_abs-1_bezeichnung-1" GUID="5c2d60ba-ea74-44c3-9518-aa26178b94bf">
                        <akn:marker eId="hauptteil-1_para-21_abs-1_bezeichnung-1_zaehlbez-1" GUID="db08acbc-72ea-4c43-a690-07a5d4b778b0" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="1f916138-af53-4f17-94ff-cb4a9d63f4fb" eId="hauptteil-1_para-21_abs-1_inhalt-1"><akn:p GUID="7bbc1faa-6c4f-487b-9ae7-6b1aa0f4b35f" eId="hauptteil-1_para-21_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §22-->
               <akn:article eId="hauptteil-1_para-22" GUID="a4c0c103-ed32-4ed6-81b6-b41957c782d0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-22_bezeichnung-1" GUID="2fbcd6d1-8720-4d57-ba14-28985f926966">
                     <akn:marker eId="hauptteil-1_para-22_bezeichnung-1_zaehlbez-1" GUID="acdf3def-f910-44e0-97a5-ddf70aa63f79" name="22"></akn:marker>§22</akn:num>
                  <akn:paragraph GUID="c61ea3f8-baab-4f81-a0c2-adb2381f5e70" eId="hauptteil-1_para-22_abs-1">
                     <akn:num eId="hauptteil-1_para-22_abs-1_bezeichnung-1" GUID="2cdca59c-e3d1-4fde-95ef-ad0521eb8c2c">
                        <akn:marker eId="hauptteil-1_para-22_abs-1_bezeichnung-1_zaehlbez-1" GUID="b78ac3e1-2466-46fe-a6fd-e90762ad575d" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="3eb5536e-29cb-4533-89e5-f0b309c73397" eId="hauptteil-1_para-22_abs-1_inhalt-1"><akn:p GUID="fcda42f9-8d60-4f85-b18d-6b1e614e8528" eId="hauptteil-1_para-22_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §23-->
               <akn:article eId="hauptteil-1_para-23" GUID="e405acf2-b145-4ec6-b368-28586c3c2c5e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-23_bezeichnung-1" GUID="1ff4d744-7446-49dc-a4ba-8cb48e80127c">
                     <akn:marker eId="hauptteil-1_para-23_bezeichnung-1_zaehlbez-1" GUID="ce3d18cb-c329-4c3d-9a6a-83c10ac8e6f5" name="23"></akn:marker>§23</akn:num>
                  <akn:paragraph GUID="e59512ac-e2c4-4d61-9fcd-49ccc58e7751" eId="hauptteil-1_para-23_abs-1">
                     <akn:num eId="hauptteil-1_para-23_abs-1_bezeichnung-1" GUID="697904a4-a568-4780-8f9a-abcfcd31e51b">
                        <akn:marker eId="hauptteil-1_para-23_abs-1_bezeichnung-1_zaehlbez-1" GUID="eb61c7e7-f341-49a7-be79-f0204fbb2c89" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="81c478aa-4ef7-408a-be13-09015f11209d" eId="hauptteil-1_para-23_abs-1_inhalt-1"><akn:p GUID="9c3aa6cf-be45-424a-bca9-3040c03dd973" eId="hauptteil-1_para-23_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §24-->
               <akn:article eId="hauptteil-1_para-24" GUID="53edf023-d23e-4c3f-aadc-d317342e0295" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-24_bezeichnung-1" GUID="b911ed14-7e1b-4c92-bbab-95a0d63f13bc">
                     <akn:marker eId="hauptteil-1_para-24_bezeichnung-1_zaehlbez-1" GUID="d5e49400-64b9-485c-b6ae-79acc786bfe1" name="24"></akn:marker>§24</akn:num>
                  <akn:paragraph GUID="14ef0b6d-a177-4cee-bdc5-8df5e82c5599" eId="hauptteil-1_para-24_abs-1">
                     <akn:num eId="hauptteil-1_para-24_abs-1_bezeichnung-1" GUID="cd05cc93-3e85-42ea-b8d6-143d8de153d6">
                        <akn:marker eId="hauptteil-1_para-24_abs-1_bezeichnung-1_zaehlbez-1" GUID="744a977d-2979-4d89-a787-c3c7129a3399" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="51db4766-dfd1-41b5-aa14-963e237ec73a" eId="hauptteil-1_para-24_abs-1_inhalt-1"><akn:p GUID="df45acbd-b294-455e-8ac4-0dd688221178" eId="hauptteil-1_para-24_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §25-->
               <akn:article eId="hauptteil-1_para-25" GUID="fbab827d-776f-4959-b700-b99558a3219d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-25_bezeichnung-1" GUID="98d1047f-8d44-4c90-81c6-3f65fd8e2487">
                     <akn:marker eId="hauptteil-1_para-25_bezeichnung-1_zaehlbez-1" GUID="4c41e917-a382-43ba-955b-c812b0dfff31" name="25"></akn:marker>§25</akn:num>
                  <akn:paragraph GUID="cfcc9b5f-e67f-4409-93b6-5473fb9c015f" eId="hauptteil-1_para-25_abs-1">
                     <akn:num eId="hauptteil-1_para-25_abs-1_bezeichnung-1" GUID="c6a22e73-283a-4a3a-b4c6-6cdfe9f43a9d">
                        <akn:marker eId="hauptteil-1_para-25_abs-1_bezeichnung-1_zaehlbez-1" GUID="c4c2bdb1-67c4-4e11-8ec4-6437a5f4dfea" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="068a1f6e-c65c-4146-a1d8-09150bd9b44b" eId="hauptteil-1_para-25_abs-1_inhalt-1"><akn:p GUID="6d28c311-fb08-465e-81bd-626ac712e520" eId="hauptteil-1_para-25_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §26-->
               <akn:article eId="hauptteil-1_para-26" GUID="6c7b37c6-7dc8-4a83-bcf0-acf67b69f377" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-26_bezeichnung-1" GUID="4e9e2071-ab50-40f4-8bdf-ba8c88a19ddc">
                     <akn:marker eId="hauptteil-1_para-26_bezeichnung-1_zaehlbez-1" GUID="66adfd43-ecc7-4740-a6f5-98d812130a8b" name="26"></akn:marker>§26</akn:num>
                  <akn:paragraph GUID="0dbd7eb7-7bf9-43d5-b731-0be947f85105" eId="hauptteil-1_para-26_abs-1">
                     <akn:num eId="hauptteil-1_para-26_abs-1_bezeichnung-1" GUID="a9de9165-dbe0-410d-aca8-f5df6716e780">
                        <akn:marker eId="hauptteil-1_para-26_abs-1_bezeichnung-1_zaehlbez-1" GUID="bc76c077-e269-4521-b2d6-25ccb3a31d83" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="ceb3a98e-abdb-44af-ab4b-42a446d0edc2" eId="hauptteil-1_para-26_abs-1_inhalt-1"><akn:p GUID="4f717e61-7a14-4e83-8c6d-45aba2f14ed9" eId="hauptteil-1_para-26_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §27-->
               <akn:article eId="hauptteil-1_para-27" GUID="06bef0e9-0e24-4e6e-ae67-0f1427d2596f" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-27_bezeichnung-1" GUID="280b8ba7-c0c4-4642-a41f-80d74195a764">
                     <akn:marker eId="hauptteil-1_para-27_bezeichnung-1_zaehlbez-1" GUID="696158fb-0e1c-45f7-b6f3-947fea7dc3fa" name="27"></akn:marker>§27</akn:num>
                  <akn:paragraph GUID="1a841b5c-8791-4fa0-9710-b7ab07b906c4" eId="hauptteil-1_para-27_abs-1">
                     <akn:num eId="hauptteil-1_para-27_abs-1_bezeichnung-1" GUID="07e714b7-1489-42e8-b5c1-e9af4a251c30">
                        <akn:marker eId="hauptteil-1_para-27_abs-1_bezeichnung-1_zaehlbez-1" GUID="83732175-acbb-4d9f-b03b-2442db59c96c" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="6dc35edf-93af-4fc3-9dd1-df7029f789bd" eId="hauptteil-1_para-27_abs-1_inhalt-1"><akn:p GUID="753b51f7-a2b0-4e55-aff2-4b1cf5cce4e1" eId="hauptteil-1_para-27_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §28-->
               <akn:article eId="hauptteil-1_para-28" GUID="d2c46e2c-de1a-4e54-958a-8c8e570110ea" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-28_bezeichnung-1" GUID="4d6b057a-0b99-4088-84bb-7b02321af0d4">
                     <akn:marker eId="hauptteil-1_para-28_bezeichnung-1_zaehlbez-1" GUID="45c0510e-9e41-459c-892c-4a41af0f7a8e" name="28"></akn:marker>§28</akn:num>
                  <akn:paragraph GUID="3888693f-5462-4c48-86f2-7516e96cfce7" eId="hauptteil-1_para-28_abs-1">
                     <akn:num eId="hauptteil-1_para-28_abs-1_bezeichnung-1" GUID="429dc118-90f1-4dfe-a447-d4532f2c6c90">
                        <akn:marker eId="hauptteil-1_para-28_abs-1_bezeichnung-1_zaehlbez-1" GUID="c178da80-c277-4e07-abb9-93c4b43b36d5" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="bd15e8e4-a7ff-4bc4-b493-8dfb3447a8a6" eId="hauptteil-1_para-28_abs-1_inhalt-1"><akn:p GUID="428f0cab-cdb9-4898-99a5-41df17de33e5" eId="hauptteil-1_para-28_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §29-->
               <akn:article eId="hauptteil-1_para-29" GUID="d3840b94-8be7-4736-8b39-7756d99ff954" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-29_bezeichnung-1" GUID="57dcb275-6ed6-4097-99d1-657a266d2cfa">
                     <akn:marker eId="hauptteil-1_para-29_bezeichnung-1_zaehlbez-1" GUID="d40848e1-c685-4980-b552-bd6aa70b222a" name="29"></akn:marker>§29</akn:num>
                  <akn:paragraph GUID="23f19f61-aef6-4ce5-8c2a-730fccd75ddb" eId="hauptteil-1_para-29_abs-1">
                     <akn:num eId="hauptteil-1_para-29_abs-1_bezeichnung-1" GUID="d11410b3-4bb7-4a14-9796-d80a0100af16">
                        <akn:marker eId="hauptteil-1_para-29_abs-1_bezeichnung-1_zaehlbez-1" GUID="0c57e283-d109-47ce-8966-cabceb7c8d5c" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="75953a36-b846-4fe2-b92b-992c9eb19ec3" eId="hauptteil-1_para-29_abs-1_inhalt-1"><akn:p GUID="dcbb1e9e-5045-4e14-ae01-250dedead34c" eId="hauptteil-1_para-29_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §30-->
               <akn:article eId="hauptteil-1_para-30" GUID="6176b12f-d195-416a-bd86-598491247bf1" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-30_bezeichnung-1" GUID="852b74af-e8f6-4dc5-9fda-52de493d1623">
                     <akn:marker eId="hauptteil-1_para-30_bezeichnung-1_zaehlbez-1" GUID="3b55f5e2-bb8f-4817-9063-ef12cf3ee4c1" name="30"></akn:marker>§30</akn:num>
                  <akn:paragraph GUID="f9a5eed2-d614-4837-9997-73649a09f181" eId="hauptteil-1_para-30_abs-1">
                     <akn:num eId="hauptteil-1_para-30_abs-1_bezeichnung-1" GUID="bf080f64-7355-4a12-b4fc-8db75d19d646">
                        <akn:marker eId="hauptteil-1_para-30_abs-1_bezeichnung-1_zaehlbez-1" GUID="76a7749b-9c90-4573-9797-5a7a4a388e9f" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="dcf89e59-3ae6-4f53-ac3b-e401514c9e0c" eId="hauptteil-1_para-30_abs-1_inhalt-1"><akn:p GUID="61c62683-143f-4785-a136-bd1ad8be85af" eId="hauptteil-1_para-30_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §31-->
               <akn:article eId="hauptteil-1_para-31" GUID="3fc41b0d-6e69-45eb-b5ac-a9cee3d09bdc" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-31_bezeichnung-1" GUID="9b42ead4-c029-457d-8875-e8b94d173c8b">
                     <akn:marker eId="hauptteil-1_para-31_bezeichnung-1_zaehlbez-1" GUID="ea9704a3-4666-49e9-a04c-5840379cc89b" name="31"></akn:marker>§31</akn:num>
                  <akn:paragraph GUID="7baf2c99-1570-402e-8775-ca46f7b7a6f1" eId="hauptteil-1_para-31_abs-1">
                     <akn:num eId="hauptteil-1_para-31_abs-1_bezeichnung-1" GUID="3451469e-67e9-4004-872c-b9ce16db321f">
                        <akn:marker eId="hauptteil-1_para-31_abs-1_bezeichnung-1_zaehlbez-1" GUID="abbb5fc3-bf80-421e-aa10-2a564431a086" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="58ae1998-52be-462d-95e7-a2f202b21156" eId="hauptteil-1_para-31_abs-1_inhalt-1"><akn:p GUID="9fe672a1-c968-4d40-b4a2-8ae628538760" eId="hauptteil-1_para-31_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §32-->
               <akn:article eId="hauptteil-1_para-32" GUID="034c835c-9b84-4c7d-a8c3-c6c573f588e1" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-32_bezeichnung-1" GUID="9f88776d-cde4-423d-a236-99073e9f9a94">
                     <akn:marker eId="hauptteil-1_para-32_bezeichnung-1_zaehlbez-1" GUID="8423561e-d11a-4e1a-937c-ee20fe7f08c3" name="32"></akn:marker>§32</akn:num>
                  <akn:paragraph GUID="96825283-3ebe-4f1f-a5ac-ba67fb76032c" eId="hauptteil-1_para-32_abs-1">
                     <akn:num eId="hauptteil-1_para-32_abs-1_bezeichnung-1" GUID="814fc782-8cf6-4fdc-81f5-1e0cfb2e37e7">
                        <akn:marker eId="hauptteil-1_para-32_abs-1_bezeichnung-1_zaehlbez-1" GUID="4c320631-f373-4432-9614-78681524d723" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="5c01e3a1-baff-4912-b05c-192f571930eb" eId="hauptteil-1_para-32_abs-1_inhalt-1"><akn:p GUID="5e4ed861-e220-4147-9fce-12701c5c1a66" eId="hauptteil-1_para-32_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §33-->
               <akn:article eId="hauptteil-1_para-33" GUID="001dd2f4-e68d-4a33-a98c-160aaa3e3902" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-33_bezeichnung-1" GUID="78b1519b-4e86-46d5-9251-cc6543727a02">
                     <akn:marker eId="hauptteil-1_para-33_bezeichnung-1_zaehlbez-1" GUID="bb96d820-6ce0-4b15-bfc5-44a1525b3a7f" name="33"></akn:marker>§33</akn:num>
                  <akn:paragraph GUID="08718c19-89f7-45f0-b52c-0fa47c372f12" eId="hauptteil-1_para-33_abs-1">
                     <akn:num eId="hauptteil-1_para-33_abs-1_bezeichnung-1" GUID="d9e879f2-9c55-4480-90a2-8ff2882e8f1a">
                        <akn:marker eId="hauptteil-1_para-33_abs-1_bezeichnung-1_zaehlbez-1" GUID="0c8cf6e1-06dd-4d1b-823c-ca34c411f4e6" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="e4af8797-dfb5-475e-b4a4-2e16653b9434" eId="hauptteil-1_para-33_abs-1_inhalt-1"><akn:p GUID="4dba2879-0de4-49e7-bf37-f1678cb06e65" eId="hauptteil-1_para-33_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §34-->
               <akn:article eId="hauptteil-1_para-34" GUID="71a583ce-51ab-4acf-ab5f-c0b18f7e59a0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-34_bezeichnung-1" GUID="00ba8f12-feff-4ecc-a317-8ea45f8f2363">
                     <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1" GUID="a702503e-2c0d-4bc3-8eef-48a8b3d828dd" name="34"></akn:marker>§34</akn:num>
                  <akn:paragraph GUID="be298799-bd2f-4f2f-bf7b-d052f3ed73d3" eId="hauptteil-1_para-34_abs-1">
                     <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1" GUID="0cbbb0b7-01a4-41c7-8e88-0ab8e789ca3b">
                        <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1" GUID="6a93535b-e0f5-4b06-8345-548d5c25d2c1" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="38ee5b8a-7eba-4793-9a2e-a7ee67c77247" eId="hauptteil-1_para-34_abs-1_inhalt-1"><akn:p GUID="06048738-e2aa-4eab-a13a-7b9d2f73d19d" eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §335-->
               <akn:article eId="hauptteil-1_para-35" GUID="1af21459-b9df-47aa-a084-435df14c546b" period="#geltungszeitgr-3" refersTo="geltungszeitregel">
                  <akn:num eId="hauptteil-1_para-35_bezeichnung-1" GUID="518b00f3-3f1c-41b2-8075-3bc644ed58b4">
                     <akn:marker eId="hauptteil-1_para-35_bezeichnung-1_zaehlbez-1" GUID="3f788588-1f18-4776-92c4-5465580dae24" name="35"></akn:marker>§35</akn:num>
                  <akn:paragraph GUID="7c715b4f-67d0-4665-bc12-de453e8a7119" eId="hauptteil-1_para-35_abs-1">
                     <akn:num eId="hauptteil-1_para-35_abs-1_bezeichnung-1" GUID="8373fc61-cc1b-4588-aa1f-8c29a2bc644e">
                        <akn:marker eId="hauptteil-1_para-35_abs-1_bezeichnung-1_zaehlbez-1" GUID="bfb2cc73-4acd-4bab-a382-fff76266e0f0" name="1"></akn:marker>(1)</akn:num>
                     <akn:list GUID="0af5ebc9-805e-40d7-b5c2-392977595974" eId="hauptteil-1_para-35_abs-1_untergl-1">
                        <akn:intro GUID="59c94bfd-0014-44df-a916-35254a314936" eId="hauptteil-1_para-35_abs-1_untergl-1_intro-1"><akn:p GUID="82015b74-1c1d-4354-a7d8-482a904bbe14" eId="hauptteil-1_para-35_abs-1_untergl-1_intro-1_text-1"></akn:p></akn:intro>
                        <akn:point GUID="9a8983f6-c5a5-4554-8231-cfd33319e0a9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1">
                           <akn:num GUID="e48171db-d319-4b3c-8b6f-a305e401d87f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="6f2bb4ba-76ab-4561-b9ff-7f19841e188f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                           <akn:content GUID="86f1d04f-cf83-4851-a463-83753ba33208" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_inhalt-1"><akn:p GUID="d917836c-1926-479d-b14f-824dd03d3633" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                              Dieses Gesetz tritt vorbehaltlich der Absätze 2 bis 9 am Tag nach der Verkündung in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="f61ff441-1986-481d-894e-94e9e250519e" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2">
                           <akn:num GUID="cbbf73d6-c5bc-4f04-80d0-bee5183a5168" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="bfa00b26-6e7d-45d2-bb9a-c1ab69a8b8b1" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>(2)</akn:num>
                           <akn:content GUID="d89f2a49-aee7-4ca8-9d56-4b69f7de13b4" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="aa246a74-02d6-420c-89b4-6375d86547ff" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                              Artikel 26 tritt mit Wirkung vom 1. Januar 2020 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="c32b1cd9-e6f1-4bbe-aed4-9c19c9dd5bf6" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3">
                           <akn:num GUID="39e1f85f-f341-45c4-a46e-90cd72665f67" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="ee64b4b2-6e17-43c9-b8a9-16ab02976905" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1"></akn:marker>(3)</akn:num>
                           <akn:content GUID="30323067-5d43-4859-8f98-3d5c6ce98fe5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_inhalt-1"><akn:p GUID="1d983209-4248-4f09-ad59-e78fc4c8fd83" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                              Die Artikel 1 und 7 treten mit Wirkung vom 1. Januar 2023 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="4c623505-79dd-48d2-91e6-b9a658c01b3f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4">
                           <akn:num GUID="9ececa14-a57d-45bf-89c8-141c42d48368" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_bezeichnung-1"><akn:marker name="4" GUID="b6a42b99-b077-4ae0-b50b-c8008b429884" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_bezeichnung-1_zaehlbez-1"></akn:marker>(4)</akn:num>
                           <akn:content GUID="96e7836c-a6b9-4f70-93dc-87073d22ed40" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_inhalt-1"><akn:p GUID="c8425bf7-47c2-4da7-9904-1e1a72bb7586" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                              Die Artikel 2, 12, 20, 31 und 32 Nummer 2 treten mit Wirkung vom 1. Januar 2024 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="db3679b0-8b1e-44eb-a15e-cfabca814a21" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5">
                           <akn:num GUID="6a285e06-75c5-4070-bb47-cff4cb5c26a5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_bezeichnung-1"><akn:marker name="5" GUID="be9cd5b6-7704-449a-ad6f-c59a78d1dcbf" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_bezeichnung-1_zaehlbez-1"></akn:marker>(5)</akn:num>
                           <akn:content GUID="f0f6f73d-6d93-43e1-84c4-798d56ed716c" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_inhalt-1"><akn:p GUID="edefafec-29f3-475f-a03b-69d216a42d93" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">
                              Die Artikel 4 und 22 treten am 1. April 2024 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="45f70562-f0c9-46ae-bffb-d4d2e763629a" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6">
                           <akn:num GUID="ce2a08f8-1099-4cff-9ed3-4bb4bd386434" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_bezeichnung-1"><akn:marker name="6" GUID="0d569870-d35f-4bc1-94d3-364c38209b83" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_bezeichnung-1_zaehlbez-1"></akn:marker>(6)</akn:num>
                           <akn:content GUID="48da3995-398d-478e-9aa9-5a0ce3904442" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_inhalt-1"><akn:p GUID="0a7edbf9-612c-43ad-b728-d8e37e2e6c68" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_inhalt-1_text-1">
                              Die Artikel 5, 9, 23, 24 und 27 Nummer 6 treten am 1. Januar 2025 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="8f6b3b22-c532-429b-b674-220ee7372c12" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7">
                           <akn:num GUID="ad245874-a7bd-41d5-97d3-d316a417bc9a" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_bezeichnung-1"><akn:marker name="7" GUID="b667b6f1-737c-445d-a3fa-6dc12be50c36" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_bezeichnung-1_zaehlbez-1"></akn:marker>(7)</akn:num>
                           <akn:content GUID="5f406ea7-324c-4a90-ba13-6d9d239e8ee9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_inhalt-1"><akn:p GUID="d38908ad-6bbd-4d1a-88e6-f697ec00cf12" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_inhalt-1_text-1">
                              Artikel 32 Nummer 3 und 4 tritt am 1. Juli 2025 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="61b0fc82-738c-4ebb-bdff-9f1eea4e96b9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8">
                           <akn:num GUID="634aa27a-df2f-4bb6-9fab-418965ae7217" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_bezeichnung-1"><akn:marker name="8" GUID="d3b417fa-0747-47db-8280-23c24de06f1d" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_bezeichnung-1_zaehlbez-1"></akn:marker>(8)</akn:num>
                           <akn:content GUID="3d7a8657-95eb-4e6d-9ad0-53e7e31883bc" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_inhalt-1"><akn:p GUID="dd2b467b-54b3-48e3-8e62-cf232df38975" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_inhalt-1_text-1">
                              Die Artikel 14 und 16 treten am 1. Januar 2027 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="545da796-e05b-491e-a64d-c4ed9ee5fb09" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9">
                           <akn:num GUID="c5174939-4a22-49b4-8e91-f29f25997c75" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_bezeichnung-1"><akn:marker name="9" GUID="2c26734b-75ac-4744-a815-5880760418b5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_bezeichnung-1_zaehlbez-1"></akn:marker>(9)</akn:num>
                           <akn:content GUID="2430d65b-f2f5-4cf9-a78d-997de8bb1bb1" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_inhalt-1"><akn:p GUID="e37f15d9-0e99-4719-948b-d18a0945f0ac" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_inhalt-1_text-1">
                              Artikel 6 tritt am 1. Januar 2028 in Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="1861791b-36e7-4742-8e81-909e63735de5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10">
                           <akn:num GUID="569c8de9-11ba-45eb-8c09-d325e6a9f373" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_bezeichnung-1"><akn:marker name="10" GUID="8711e29d-2080-46af-bac7-4a623ce1af18" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_bezeichnung-1_zaehlbez-1"></akn:marker>(10)</akn:num>
                           <akn:content GUID="d199a332-8e7d-46c6-b3c5-84cce841fc29" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_inhalt-1"><akn:p GUID="29b55044-6570-485f-97d1-7db6e17ec6c7" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_inhalt-1_text-1">
                              Die §§ 124 und 125 des Vierten Buches Sozialgesetzbuch, § 202a des Fünften Buches Sozialgesetzbuch
sowie § 55b Absatz 2 des Elften Buches Sozialgesetzbuch treten am 1. Juli 2026 außer Kraft.
                           </akn:p></akn:content>
                        </akn:point>
                     </akn:list>
                </akn:paragraph>
               </akn:article>
      </akn:body>
   </akn:act>
</akn:akomaNtoso>');

-- Target law
DELETE FROM norms where guid = 'c2e78146-833b-4d78-a055-27f05eeaf018';
INSERT INTO norms (guid, eli, xml)
VALUES ('c2e78146-833b-4d78-a055-27f05eeaf018', 'eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
--><?xml-model href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/26e27e3a56996587fe737639fd80e2c4a8fe0311/Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
   <akn:act name="regelungstext">
<!-- Metadaten-->
      <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
         <akn:identification eId="meta-1_ident-1" GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22" source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761" value="eli/bund/bgbl-1/2009/s3366/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a90733dd-4780-472d-8371-a3690f2e869a" value="eli/bund/bgbl-1/2009/s3366"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b" name="übergreifende-id" value="2c1a6ec9-7161-4e2f-8999-91219180d992"></akn:FRBRalias>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811" date="2009-10-08" name="verkuendungsfassung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e" value="de"></akn:FRBRcountry>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5" value="S3366"></akn:FRBRnumber>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8" value="bgbl-1"></akn:FRBRname>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="af948830-e615-4dd5-8237-b8c830939f78" value="regelungstext-1"></akn:FRBRsubtype>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="85e064f4-f56f-4cef-86a4-6801cc71c712" value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="e8f52739-79c1-4e59-9db2-004180c8b29c" value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="c2e78146-833b-4d78-a055-27f05eeaf018" name="aktuelle-version-id" value="53d31e3c-5c46-4e96-8017-b0db064561a2"></akn:FRBRalias>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c" name="nachfolgende-version-id" value="5d84cd1d-3575-4a03-bb6c-f17834e392fe"></akn:FRBRalias>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="6aace8d0-001f-4829-9794-79857835ee45" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="a6dc32d0-140d-4147-88b2-d64639b91742" date="2023-12-23" name="verkuendung"></akn:FRBRdate>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="b872861e-0add-4bbb-ba90-a5edb3c40038" language="deu"></akn:FRBRlanguage>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc" value="1"></akn:FRBRversionNumber>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="4c881fca-0e36-440e-a5f9-513079ccc77f" value="eli/bund/bgbl-1/2009/s3366/1/deu/regelungstext-1.xml"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="d0491f91-d599-46d4-ba61-c7426b607e4f" value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1.xml"></akn:FRBRuri>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612" date="2023-12-23" name="generierung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="1d015a20-823e-496e-808d-27c8fec85b51" href="recht.bund.de"></akn:FRBRauthor>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67" value="xml"></akn:FRBRformat>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle eId="meta-1_lebzykl-1" GUID="cef30a63-d738-4712-bac8-50fda44949e2" source="attributsemantik-noch-undefiniert">
<!-- 1. Originales Gesetz -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="65f3910d-4e79-45f8-a24d-44ef7523963a" date="1934-10-16" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung"></akn:eventRef>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c" date="1934-10-16" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten"></akn:eventRef>
            <!-- Neufassung -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="85e92fc8-b381-47bb-a476-c35fa2794336" date="2009-10-08" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="neufassung"></akn:eventRef>
            <!-- Letzte Änderung -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="85e92fc8-b381-47bb-a476-c35fa2794337" date="2023-12-24" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"></akn:eventRef>
         </akn:lifecycle>
         <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16" start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f" start="#meta-1_lebzykl-1_ereignis-3" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c5">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac755" start="#meta-1_lebzykl-1_ereignis-4" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1" GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>neufassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
               <meta:fna>754-28-2</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
               <meta:federfuehrung>
                  <meta:federfuehrend ab="1934-10-16" bis="unbestimmt">BMF - Bundesministerium der Finanzen</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
               <meta:subtyp>SN</meta:subtyp>
           </meta:legalDocML.de_metadaten_ds>
         </akn:proprietary>
      </akn:meta>
      <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Einkommensteuergesetz
               </akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">
                  <akn:inline refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" GUID="3d5ecf29-792c-4854-99e4-0cf18b5c0c9c">EStG</akn:inline>
               </akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="7b7416d7-35a0-41cf-902f-583b9cf51a26">
         <akn:article eId="hauptteil-1_para-1" GUID="e9a8d5e4-77b8-447f-9227-63f687a31f8f" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="049c11bd-5ed2-4423-a7c3-54dce98c414d">
                     <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="4aca37a6-2df2-41e0-9f13-f837150b3005" name="1"></akn:marker>§1</akn:num>
            <akn:paragraph GUID="abd48a54-ccc6-4cb9-8627-9d7092b5263f" eId="hauptteil-1_para-1_abs-1">
               <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="375c9de0-2d35-447b-b9ce-a6e0243b81a2">
                        <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="4a047cd5-a44b-4b25-bd6b-c3bcf95d76e2" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="a1609cb8-1045-4e45-a6f9-c7bdf4187249" eId="hauptteil-1_para-1_abs-1_inhalt-1"><akn:p GUID="74b7302d-a4fc-4f69-a362-97a3ed8c4e77" eId="hauptteil-1_para-1_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-2" GUID="4b09bb18-3bda-4603-a0a3-b62083262a8a" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="42e575d9-f64a-4ad5-85d1-47f134576923">
                     <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="4bd6f909-053c-4461-a162-a4cb058db29d" name="1"></akn:marker>§1</akn:num>
            <akn:paragraph GUID="a0271610-a340-4e5c-b592-4b60ba92a657" eId="hauptteil-1_para-2_abs-1">
               <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="ff409ec8-0df2-40cf-8605-d7cca3052297">
                        <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="a3c3fcbd-f9a2-425c-b7ce-a4fc13f63483" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="cddf665c-ac79-4e28-bc12-7adb861a840c" eId="hauptteil-1_para-2_abs-1_inhalt-1"><akn:p GUID="800c9235-db46-493d-a64c-c1b5de499927" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-3" GUID="dcf7124f-ae5b-4a0e-9bc9-d2df8bed7016" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="0ca92be9-2775-4848-a942-0d96397b6a3f">
                     <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" GUID="a5bed3be-f422-4d9a-8c31-0a6923fcaf20" name="2"></akn:marker>§1a</akn:num>
            <akn:paragraph GUID="519025c1-72da-42a2-87a9-056c27ca72ae" eId="hauptteil-1_para-3_abs-1">
               <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="3811c99a-49e8-4672-8274-01296a218392">
                        <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="9f3c89f3-09b3-48ee-9090-f9e0874bedcb" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="6f4b3b85-3c26-44e6-afde-1b8cf2dbbd75" eId="hauptteil-1_para-3_abs-1_inhalt-1"><akn:p GUID="55cc5a1c-cee3-4972-9fb8-e3082f3bc6e6" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-4" GUID="11f31a4b-36f5-4163-b5a0-528087d9072d" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="e3ee7c20-d068-424e-acd2-4444c3a59e43">
                     <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1" GUID="9abcd42d-9927-4cd3-ace5-de9899d4b724" name="3"></akn:marker>§2</akn:num>
            <akn:paragraph GUID="b0d3737b-4055-4c02-a758-cda1a41542cc" eId="hauptteil-1_para-4_abs-1">
               <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="2524812c-b34c-42fc-ab21-899c83aa3986">
                        <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" GUID="c0e9858b-8009-4340-ad82-5ea4e25d89bf" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="c490f063-95f1-4617-84ec-3889a6a44965" eId="hauptteil-1_para-4_abs-1_inhalt-1"><akn:p GUID="5694f377-df54-4e19-88ef-93175d6eb4a7" eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-5" GUID="e253b2d4-f268-46fd-a8ff-79d3ee8d6184" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-5_bezeichnung-1" GUID="4161e762-5411-4b8a-86d5-32a5c3880b12">
                     <akn:marker eId="hauptteil-1_para-5_bezeichnung-1_zaehlbez-1" GUID="8295e6c3-b181-4943-97f9-429e743c7677" name="4"></akn:marker>§2a</akn:num>
            <akn:paragraph GUID="4988d145-1105-4008-ae2e-dfc8ff0a7536" eId="hauptteil-1_para-5_abs-1">
               <akn:num eId="hauptteil-1_para-5_abs-1_bezeichnung-1" GUID="1284cd07-2751-4f40-a1da-4cba63d4b375">
                        <akn:marker eId="hauptteil-1_para-5_abs-1_bezeichnung-1_zaehlbez-1" GUID="9b7a439e-73e5-452b-81d0-8abc727c2f86" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="966d355b-246e-4d21-8006-0662496fc52f" eId="hauptteil-1_para-5_abs-1_inhalt-1"><akn:p GUID="3c6ab1cd-14fd-4db8-ac00-964e5102b02f" eId="hauptteil-1_para-5_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-6" GUID="5d51449c-88e9-4411-ab65-e754e5eedf4d" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-6_bezeichnung-1" GUID="243f406d-cd7d-4e7a-8e99-3277133b2a58">
                     <akn:marker eId="hauptteil-1_para-6_bezeichnung-1_zaehlbez-1" GUID="5ff0b7f4-2842-46a5-a8ff-a53f732c56ec" name="1"></akn:marker>§3</akn:num>
            <akn:paragraph GUID="d0e332d8-c3b2-47db-8904-6d03452ca2b8" eId="hauptteil-1_para-6_abs-1">
               <akn:num eId="hauptteil-1_para-6_abs-1_bezeichnung-1" GUID="adfab363-827a-4a34-be0e-c7584ee23dbe">
                        <akn:marker eId="hauptteil-1_para-6_abs-1_bezeichnung-1_zaehlbez-1" GUID="92af5bbd-4e03-4858-bcf6-bf061ac9e30f" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="f2d23018-7d93-40d7-a5df-c981db4a4534" eId="hauptteil-1_para-6_abs-1_inhalt-1"><akn:p GUID="029568a7-2147-487a-9ad4-5df622b67232" eId="hauptteil-1_para-6_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-7" GUID="9f671cd2-bf8c-4ca3-adc3-dd9cd3cf5478" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-7_bezeichnung-1" GUID="cb798a77-b613-4e93-b9b6-24ac18a3810d">
                     <akn:marker eId="hauptteil-1_para-7_bezeichnung-1_zaehlbez-1" GUID="abfd8fd5-c2be-4cfe-9805-b7a72954f985" name="1"></akn:marker>§3a</akn:num>
            <akn:paragraph GUID="c7032ec4-11ce-4473-886c-9340c19bce13" eId="hauptteil-1_para-7_abs-1">
               <akn:num eId="hauptteil-1_para-7_abs-1_bezeichnung-1" GUID="31464e5b-4298-4fd7-8c2d-4963e5b154e8">
                        <akn:marker eId="hauptteil-1_para-7_abs-1_bezeichnung-1_zaehlbez-1" GUID="1ed6bc06-ec45-4ac5-ac3f-01a2c6e8dc1f" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="b4a15eb8-63cc-4a0b-aa74-38a60b2847fb" eId="hauptteil-1_para-7_abs-1_inhalt-1"><akn:p GUID="5fcffa08-6a7a-4f13-8103-1c110fe49ec9" eId="hauptteil-1_para-7_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-8" GUID="9fa71e02-b0b6-429f-96fc-34aad39a0f21" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-8_bezeichnung-1" GUID="7887a4de-740b-4ed7-ab4b-61dea64d4acd">
                     <akn:marker eId="hauptteil-1_para-8_bezeichnung-1_zaehlbez-1" GUID="92ed7a54-d67c-4453-8d03-5263c39f496e" name="1"></akn:marker>§3b</akn:num>
            <akn:paragraph GUID="68e04f75-528e-4e1c-bbaf-803139717cda" eId="hauptteil-1_para-8_abs-1">
               <akn:num eId="hauptteil-1_para-8_abs-1_bezeichnung-1" GUID="460de7b9-d739-4324-bde9-aeccce7d59d3">
                        <akn:marker eId="hauptteil-1_para-8_abs-1_bezeichnung-1_zaehlbez-1" GUID="48a14c5d-4960-4f94-88ab-20c901f4201e" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="044dfd14-cae9-48c2-964b-7da039d9d1ca" eId="hauptteil-1_para-8_abs-1_inhalt-1"><akn:p GUID="d415fb58-cdc0-4cd6-a293-4e8b980bae54" eId="hauptteil-1_para-8_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-9" GUID="ae491455-e299-409a-928b-b47cbb04040b" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-9_bezeichnung-1" GUID="4d53114a-f191-4600-8f28-5dba72cdd003">
                     <akn:marker eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1" GUID="b94918cc-dca4-408b-b4b2-c6ad3a80ea07" name="1"></akn:marker>§3c</akn:num>
            <akn:paragraph GUID="6ef7bb31-8d25-43ca-9c4c-0ccff9072392" eId="hauptteil-1_para-9_abs-1">
               <akn:num eId="hauptteil-1_para-9_abs-1_bezeichnung-1" GUID="3340cdfa-b701-49c5-9425-6e0d60e028a7">
                        <akn:marker eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1" GUID="53e0f9b8-680d-417d-ad3b-e9fbe563017f" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="0488e21a-2f30-41fe-981f-d7bc4c5f021b" eId="hauptteil-1_para-9_abs-1_inhalt-1"><akn:p GUID="fb2277f3-115b-4194-ba46-5bf2c1d075f9" eId="hauptteil-1_para-9_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-10" GUID="ca31c6f1-432d-4411-b3ca-7f771632ed35" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-10_bezeichnung-1" GUID="41ccf863-0dac-459c-8794-31da0bc36604">
                     <akn:marker eId="hauptteil-1_para-10_bezeichnung-1_zaehlbez-1" GUID="1300cb06-274f-4db0-b33c-ccc8cf825712" name="1"></akn:marker>§4</akn:num>
            <akn:paragraph GUID="cb830efc-607a-4ebf-bc2c-567428ac75b1" eId="hauptteil-1_para-10_abs-1">
               <akn:num eId="hauptteil-1_para-10_abs-1_bezeichnung-1" GUID="85b961f5-d462-4a75-8f33-f2662b844e9a">
                        <akn:marker eId="hauptteil-1_para-10_abs-1_bezeichnung-1_zaehlbez-1" GUID="3e33ba0a-5cd0-4779-8494-284fba328681" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="b7b65065-40eb-43f3-a524-8cecbf4a2424" eId="hauptteil-1_para-10_abs-1_inhalt-1"><akn:p GUID="8140032e-77b5-4890-b39e-63a66147a1fc" eId="hauptteil-1_para-10_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-11" GUID="7348fc55-e767-4d23-ab05-31a4e7ae470c" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-11_bezeichnung-1" GUID="08753f4f-ec16-4cbc-91d8-9057cb06ba4c">
                     <akn:marker eId="hauptteil-1_para-11_bezeichnung-1_zaehlbez-1" GUID="2d428604-d7e6-441d-b431-00d4bfbd0bcb" name="1"></akn:marker>§4a</akn:num>
            <akn:paragraph GUID="429c9203-e0e0-469f-8eed-760997f38cdb" eId="hauptteil-1_para-11_abs-1">
               <akn:num eId="hauptteil-1_para-11_abs-1_bezeichnung-1" GUID="0bb680a0-49fb-4ba6-b7ee-24da6d3024e1">
                        <akn:marker eId="hauptteil-1_para-11_abs-1_bezeichnung-1_zaehlbez-1" GUID="48e5a333-550e-430f-9e0a-46ddadb6eace" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="2189b6d1-244f-4158-9858-6dd967636c6a" eId="hauptteil-1_para-11_abs-1_inhalt-1"><akn:p GUID="0248aafc-b33f-44c3-b776-777086e865b8" eId="hauptteil-1_para-11_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-12" GUID="8ba4d8a5-9140-44fb-b7eb-922bde2f77d0" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-12_bezeichnung-1" GUID="c918ccd0-08ab-48e7-988b-f18f09a6ec40">
                     <akn:marker eId="hauptteil-1_para-12_bezeichnung-1_zaehlbez-1" GUID="b2f7f100-8fb9-4e95-b9b9-77657f408b70" name="1"></akn:marker>§4b</akn:num>
            <akn:paragraph GUID="bd6b33a9-84a8-4a2c-9dd5-0cce26664599" eId="hauptteil-1_para-12_abs-1">
               <akn:num eId="hauptteil-1_para-12_abs-1_bezeichnung-1" GUID="7d3809ab-2691-4495-8442-d00a982fb46b">
                        <akn:marker eId="hauptteil-1_para-12_abs-1_bezeichnung-1_zaehlbez-1" GUID="498b04fb-dc70-40de-b20b-b35fe8a71bc7" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="79373c7f-82b0-48ff-8423-237333bdacc9" eId="hauptteil-1_para-12_abs-1_inhalt-1"><akn:p GUID="88ee4693-5a25-4b4f-b959-0c1455d7ff53" eId="hauptteil-1_para-12_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-13" GUID="9a25387c-d6af-4ac3-bb10-2ddc1adecedc" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-13_bezeichnung-1" GUID="6358cd19-b9f5-473e-9192-eac12373d000">
                     <akn:marker eId="hauptteil-1_para-13_bezeichnung-1_zaehlbez-1" GUID="8af374b2-aa38-42e8-bfbb-295eca208489" name="1"></akn:marker>§4c</akn:num>
            <akn:paragraph GUID="e7d210d4-edb1-47cc-a074-2b394bc04270" eId="hauptteil-1_para-13_abs-1">
               <akn:num eId="hauptteil-1_para-13_abs-1_bezeichnung-1" GUID="632066c0-1f65-4319-ad6a-d3b14e110e6b">
                        <akn:marker eId="hauptteil-1_para-13_abs-1_bezeichnung-1_zaehlbez-1" GUID="6c30fea1-0d95-4b6c-b0f6-0094011bf066" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="cb55652a-83d1-4bd1-abba-bb636565c559" eId="hauptteil-1_para-13_abs-1_inhalt-1"><akn:p GUID="8ec8d90b-ea2f-4218-89b5-37ba0ea20218" eId="hauptteil-1_para-13_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-14" GUID="17e8f2c2-9298-426d-8a0a-5852124239c0" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-14_bezeichnung-1" GUID="1bbc3481-c35f-45c3-b397-c8cb6d6b2c8f">
                     <akn:marker eId="hauptteil-1_para-14_bezeichnung-1_zaehlbez-1" GUID="a9be80c2-88ac-47c2-9e77-8703a2c80ecc" name="1"></akn:marker>§4d</akn:num>
            <akn:paragraph GUID="b8596272-3803-49de-a7f0-1d002a1efd61" eId="hauptteil-1_para-14_abs-1">
               <akn:num eId="hauptteil-1_para-14_abs-1_bezeichnung-1" GUID="7b771214-c3a3-4b56-b3bb-44c3d3b9dd31">
                        <akn:marker eId="hauptteil-1_para-14_abs-1_bezeichnung-1_zaehlbez-1" GUID="228d4678-0678-4b0d-a18d-a06747a52cd9" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="34c7d9ef-cc24-4706-a784-28269373326d" eId="hauptteil-1_para-14_abs-1_inhalt-1"><akn:p GUID="9d535547-49a5-4b09-9475-bead23a521e9" eId="hauptteil-1_para-14_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-15" GUID="adafbf82-2f5a-42c3-82c9-2339f0a382cd" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-15_bezeichnung-1" GUID="32dfd6b3-4c8b-4dde-861b-07821708c297">
                     <akn:marker eId="hauptteil-1_para-15_bezeichnung-1_zaehlbez-1" GUID="0e4efa11-3d48-4582-a4c6-a0cb2b0941f7" name="1"></akn:marker>§4e</akn:num>
            <akn:paragraph GUID="44e2537a-a248-4dfe-9d3a-3c1c975deeca" eId="hauptteil-1_para-15_abs-1">
               <akn:num eId="hauptteil-1_para-15_abs-1_bezeichnung-1" GUID="e5dec8c4-c0ea-4e86-9607-40663f4e7be4">
                        <akn:marker eId="hauptteil-1_para-15_abs-1_bezeichnung-1_zaehlbez-1" GUID="3979da44-89fc-458e-b436-fb3efb1f9e65" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="c9f4e348-d2fb-4415-8bfa-8d292a9ce5c6" eId="hauptteil-1_para-15_abs-1_inhalt-1"><akn:p GUID="1d748807-46a7-41af-9cc4-1e693921631b" eId="hauptteil-1_para-15_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-16" GUID="e92b3ffb-be5e-447d-ac59-48815e56c124" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-16_bezeichnung-1" GUID="4e87a3d9-9280-42b7-9d5e-0b4f0ee9170e">
                     <akn:marker eId="hauptteil-1_para-16_bezeichnung-1_zaehlbez-1" GUID="efec926d-c990-4f6e-9936-fe2b4022ee21" name="1"></akn:marker>§4f</akn:num>
            <akn:paragraph GUID="645cb034-5847-4c08-a795-d7b396db503c" eId="hauptteil-1_para-16_abs-1">
               <akn:num eId="hauptteil-1_para-16_abs-1_bezeichnung-1" GUID="476a88f0-edf6-4778-ab59-7a1c659fb335">
                        <akn:marker eId="hauptteil-1_para-16_abs-1_bezeichnung-1_zaehlbez-1" GUID="3d2ff31c-7683-4589-9cad-d2ec55bf36d7" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="49bfc624-9922-4049-bac0-88af08b2cc98" eId="hauptteil-1_para-16_abs-1_inhalt-1"><akn:p GUID="528cebe9-ece1-495a-a811-71f325b9c0a7" eId="hauptteil-1_para-16_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-17" GUID="ffcb8103-08cb-4ab9-895e-2cc19974d4bc" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-17_bezeichnung-1" GUID="bbc37521-f05f-4df9-8496-7b71863460cc">
                     <akn:marker eId="hauptteil-1_para-17_bezeichnung-1_zaehlbez-1" GUID="a60b907e-18ff-4aea-aa6e-b68f9909eeba" name="1"></akn:marker>§4g</akn:num>
            <akn:paragraph GUID="4f78186c-f8db-4f43-ab36-437d536e2c92" eId="hauptteil-1_para-17_abs-1">
               <akn:num eId="hauptteil-1_para-17_abs-1_bezeichnung-1" GUID="ccf50628-68c5-4a0c-ac55-8f6024412b7e">
                        <akn:marker eId="hauptteil-1_para-17_abs-1_bezeichnung-1_zaehlbez-1" GUID="369e146e-6b73-4806-9f4a-cd7cd574a8ac" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="4b3a87be-ddc0-460c-ba81-00e274b4a18a" eId="hauptteil-1_para-17_abs-1_inhalt-1"><akn:p GUID="749155b9-8125-4755-a8ae-3eda62e43865" eId="hauptteil-1_para-17_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-18" GUID="91883758-dd57-41ca-887c-76054b2480f6" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-18_bezeichnung-1" GUID="32109de1-9a81-43ce-b167-210b45fde026">
                     <akn:marker eId="hauptteil-1_para-18_bezeichnung-1_zaehlbez-1" GUID="6523375c-5f97-40f8-b879-0eb0dc056d8e" name="1"></akn:marker>§4h</akn:num>
            <akn:paragraph GUID="b2f41e83-2550-4447-a3c5-8ad7670ad7b7" eId="hauptteil-1_para-18_abs-1">
               <akn:num eId="hauptteil-1_para-18_abs-1_bezeichnung-1" GUID="32628015-7b68-4669-879c-7401a08a179a">
                        <akn:marker eId="hauptteil-1_para-18_abs-1_bezeichnung-1_zaehlbez-1" GUID="2f1380a5-2921-4cc7-96dc-d4d3360e5842" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="df3f282b-6ae3-420a-947f-d59f5a701934" eId="hauptteil-1_para-18_abs-1_inhalt-1"><akn:p GUID="e435d25d-2dd9-4197-8773-3cb03ade58f2" eId="hauptteil-1_para-18_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-19" GUID="49482d93-6969-4b41-9646-e38843f35c84" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-19_bezeichnung-1" GUID="7fd28ebf-fb70-4939-b9be-474a44d48322">
                     <akn:marker eId="hauptteil-1_para-19_bezeichnung-1_zaehlbez-1" GUID="d50d3c38-9bf8-4c29-adcf-5beb49fb8900" name="1"></akn:marker>§4i</akn:num>
            <akn:paragraph GUID="8c567484-3c5c-479f-be0c-ced07e3eb5fb" eId="hauptteil-1_para-19_abs-1">
               <akn:num eId="hauptteil-1_para-19_abs-1_bezeichnung-1" GUID="7f1e1e8f-ff99-4206-82bf-b87134eda77a">
                        <akn:marker eId="hauptteil-1_para-19_abs-1_bezeichnung-1_zaehlbez-1" GUID="e077a48c-173a-4d2e-95ee-325873c3e544" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="d98913b8-a9c3-427c-8f65-a7a4afa50455" eId="hauptteil-1_para-19_abs-1_inhalt-1"><akn:p GUID="a68d7e61-e06c-41f3-9e33-0264cbc3810f" eId="hauptteil-1_para-19_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-20" GUID="4d3efe94-417b-4251-a63b-73cf014ffd98" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="89416443-6452-4cfb-a6c9-16cbb46d6117">
                     <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" GUID="e4bf5787-a6be-415d-8825-6e44f2f6f0d6" name="1"></akn:marker>§4j</akn:num>
            <akn:paragraph GUID="f941ca6a-22a9-4bde-90ac-7ff187ca53bb" eId="hauptteil-1_para-20_abs-1">
               <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="d782c8e4-b40b-45ee-81da-e853f80af1d7">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" GUID="b1a15b8f-9b6a-4bd5-9cf9-8a6daec6794a" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="e4726b7e-a690-4490-a1b9-93c02cbaa49a" eId="hauptteil-1_para-20_abs-1_inhalt-1"><akn:p GUID="9a373fe2-417e-4872-a112-0b6b6cc1b889" eId="hauptteil-1_para-20_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-21" GUID="b55b4921-23af-4f55-ad3e-8cc9f8c2ff83" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-21_bezeichnung-1" GUID="0619603b-18e4-42ee-9c99-91eae90469c3">
                     <akn:marker eId="hauptteil-1_para-21_bezeichnung-1_zaehlbez-1" GUID="c47fc7cf-245c-47bf-b313-5c48051cc3c4" name="1"></akn:marker>§4k</akn:num>
            <akn:paragraph GUID="a720dea6-624a-49d5-aa8f-0664dff9cf97" eId="hauptteil-1_para-21_abs-1">
               <akn:num eId="hauptteil-1_para-21_abs-1_bezeichnung-1" GUID="cff64b2d-fc9d-40df-95c4-aa2f9dc10284">
                        <akn:marker eId="hauptteil-1_para-21_abs-1_bezeichnung-1_zaehlbez-1" GUID="ddd318ee-b0b4-43f1-af67-fb55ada83ff5" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="17a4585c-dcce-4a91-85d4-c35293c60ae6" eId="hauptteil-1_para-21_abs-1_inhalt-1"><akn:p GUID="fa0c022f-1d67-4792-a34a-54fa0b417c1e" eId="hauptteil-1_para-21_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-22" GUID="fe149939-6ef4-42d2-b8c9-4ba28a842406" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-22_bezeichnung-1" GUID="8eeb97eb-a22c-4abf-b3fb-e499f4d372a0">
                     <akn:marker eId="hauptteil-1_para-22_bezeichnung-1_zaehlbez-1" GUID="8b80eb21-624e-4f78-aaa0-fa75566c276c" name="1"></akn:marker>§5</akn:num>
            <akn:paragraph GUID="1a286587-87d7-4f01-96fb-9f3aef04553c" eId="hauptteil-1_para-22_abs-1">
               <akn:num eId="hauptteil-1_para-22_abs-1_bezeichnung-1" GUID="11143729-dab9-4626-aff7-0b39071369d8">
                        <akn:marker eId="hauptteil-1_para-22_abs-1_bezeichnung-1_zaehlbez-1" GUID="2797747d-dcf2-4f11-85f1-aa1d3040b4a6" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="fc68edff-d4e5-41d9-ae24-6afb84b1162e" eId="hauptteil-1_para-22_abs-1_inhalt-1"><akn:p GUID="0ea02c00-8977-4dd3-9093-b7909037dc8e" eId="hauptteil-1_para-22_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-23" GUID="6f9ae82c-e11a-42ec-9c79-c6643b5030ad" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-23_bezeichnung-1" GUID="af736792-d39f-43b1-9750-40a568247bb4">
                     <akn:marker eId="hauptteil-1_para-23_bezeichnung-1_zaehlbez-1" GUID="e2f1b21d-aec2-457e-bac2-098f2050965f" name="1"></akn:marker>§5a</akn:num>
            <akn:paragraph GUID="228220d1-14df-49d2-8f6d-2408da4bdf6f" eId="hauptteil-1_para-23_abs-1">
               <akn:num eId="hauptteil-1_para-23_abs-1_bezeichnung-1" GUID="6cbb9061-752e-4e89-9b65-4bc8b163aaba">
                        <akn:marker eId="hauptteil-1_para-23_abs-1_bezeichnung-1_zaehlbez-1" GUID="551f7f5e-7211-45a7-bd06-80784c010cd0" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="9794bd61-40ce-4c4f-8224-2e1998fc2fbb" eId="hauptteil-1_para-23_abs-1_inhalt-1"><akn:p GUID="da2878c4-e1ab-4704-afd3-a6a765db86f3" eId="hauptteil-1_para-23_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-24" GUID="c7570a10-24ba-4556-9330-d58b5ea7e6a6" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-24_bezeichnung-1" GUID="d71ce5b3-1737-44a7-b34f-f9d11ccf8778">
                     <akn:marker eId="hauptteil-1_para-24_bezeichnung-1_zaehlbez-1" GUID="a0fd6927-e1fd-4c35-acbc-b9e2dd35977b" name="1"></akn:marker>§5b</akn:num>
            <akn:paragraph GUID="27bbb686-45a4-430c-aa90-310298267769" eId="hauptteil-1_para-24_abs-1">
               <akn:num eId="hauptteil-1_para-24_abs-1_bezeichnung-1" GUID="6c6dd7bf-2965-4d05-b668-bc73c76980c2">
                        <akn:marker eId="hauptteil-1_para-24_abs-1_bezeichnung-1_zaehlbez-1" GUID="16a1c40f-c7e5-426a-b376-6cbce96796c3" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="c74d084f-c04a-4499-a4c1-f0aaf4d0421d" eId="hauptteil-1_para-24_abs-1_inhalt-1"><akn:p GUID="6f7e184b-a27f-407f-9f0f-5f0240857533" eId="hauptteil-1_para-24_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-25" GUID="28ca0cbd-5c5e-494e-a300-45f9d92f9922" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-25_bezeichnung-1" GUID="443e6a57-1e65-4cd7-a89f-c947e6d68832">
                     <akn:marker eId="hauptteil-1_para-25_bezeichnung-1_zaehlbez-1" GUID="f9e3e582-c96e-436c-ae85-d4ebc7e11145" name="1"></akn:marker>§6</akn:num>
            <akn:paragraph GUID="899c959d-83f2-4134-b0ba-20328dcc16fb" eId="hauptteil-1_para-25_abs-1">
               <akn:num eId="hauptteil-1_para-25_abs-1_bezeichnung-1" GUID="c6322471-caec-4888-af78-84e1f11ab3c6">
                        <akn:marker eId="hauptteil-1_para-25_abs-1_bezeichnung-1_zaehlbez-1" GUID="d0840ddc-f170-4aa6-b6ad-82c4f9cba765" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="d32da15e-f80f-4336-af14-170ee79b407d" eId="hauptteil-1_para-25_abs-1_inhalt-1"><akn:p GUID="1b56777b-a99f-4e99-b8b5-eb433b608f08" eId="hauptteil-1_para-25_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-26" GUID="fb0dbfa8-c9f4-4072-abac-d5ae46dc4ffa" period="#geltungszeitgr-3">
            <akn:num eId="hauptteil-1_para-26_bezeichnung-1" GUID="3413c785-2aac-4ec8-ac64-df43b167bd6a">
                     <akn:marker eId="hauptteil-1_para-26_bezeichnung-1_zaehlbez-1" GUID="fe8ff656-3fbc-4923-adef-deeff1994c46" name="1"></akn:marker>§6a</akn:num>
            <akn:paragraph GUID="56fac6cb-a2f5-47a8-b70a-db2400c300d3" eId="hauptteil-1_para-26_abs-1">
               <akn:num eId="hauptteil-1_para-26_abs-1_bezeichnung-1" GUID="34da4ee7-b9cd-442a-9da8-27519427c2b7">
                        <akn:marker eId="hauptteil-1_para-26_abs-1_bezeichnung-1_zaehlbez-1" GUID="fd432f5e-fe3e-4e93-8978-7afe6c45676f" name="1"></akn:marker>(1)</akn:num>
                        <akn:content GUID="485daf4e-2cb4-4080-9f06-f42b4b138fe2" eId="hauptteil-1_para-26_abs-1_inhalt-1"><akn:p GUID="110761f1-afce-4817-aac6-20666db7e130" eId="hauptteil-1_para-26_abs-1_inhalt-1_text-1"></akn:p></akn:content>
            </akn:paragraph>
         </akn:article>

               <!-- §6b -->
               <akn:article eId="hauptteil-1_para-27" GUID="17e9c25e-251d-48a7-8e74-3f0bb4b93b6b" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-27_bezeichnung-1" GUID="24f5f556-e005-4d10-8d7e-d92f141780d7">
                     <akn:marker eId="hauptteil-1_para-27_bezeichnung-1_zaehlbez-1" GUID="6fa27ec6-e4c2-476a-8729-85e16b3105d1" name="1"></akn:marker>§6b</akn:num>
                  <akn:heading eId="hauptteil-1_para-27_überschrift-1" GUID="31b97e2a-1872-4a52-aba1-d075bae3c0d6">Übertragung stiller Reserven bei der Veräußerung bestimmter Anlagegüter</akn:heading>
                  <!-- Absatz 1 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-1" GUID="1106c825-938a-4763-aaa4-0a81b658528b">
                     <akn:num eId="hauptteil-1_para-27_abs-1_bezeichnung-1" GUID="df7997ae-03fc-4073-8e7b-d6885b62dbbd">
                        <akn:marker eId="hauptteil-1_para-27_abs-1_bezeichnung-1_zaehlbez-1" GUID="f3536fd7-6d90-419b-a20e-e015d08470ea" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="8c1e9b59-fc85-45ae-834b-cd6b594ce514" eId="hauptteil-1_para-27_abs-1_inhalt-1"><akn:p GUID="29acbe3f-1605-4736-97b4-4bbf2c00dbc6" eId="hauptteil-1_para-27_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
                  <!-- Absatz 2 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-2" GUID="f889003b-0740-444a-b6a3-fb45635b32ed">
                     <akn:num eId="hauptteil-1_para-27_abs-2_bezeichnung-1" GUID="e12b64e2-c3a2-4254-b75b-6c695f3f7568">
                        <akn:marker eId="hauptteil-1_para-27_abs-2_bezeichnung-1_zaehlbez-1" GUID="af5703ff-006b-4b81-a28a-7d0ba4d106dd" name="2"></akn:marker>(2)</akn:num>
                     <akn:content GUID="ba4d6792-d900-4d6e-8d80-8dde96cdf276" eId="hauptteil-1_para-27_abs-2_inhalt-1"><akn:p GUID="6cfdd68b-06f5-4055-8f1b-6ecb31f9bc67" eId="hauptteil-1_para-27_abs-2_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
                  <!-- Absatz 3 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-3" GUID="982f7be1-2a0f-4a76-bc30-4522746e908c">
                     <akn:num eId="hauptteil-1_para-27_abs-3_bezeichnung-1" GUID="4da41dcf-d101-44a8-9c1f-580cefb69d69">
                        <akn:marker eId="hauptteil-1_para-27_abs-3_bezeichnung-1_zaehlbez-1" GUID="076ca318-1d44-450e-9804-f458ffb43715" name="3"></akn:marker>(3)</akn:num>
                     <akn:content GUID="abc34096-ae69-4ec4-a904-c4ddcc504401" eId="hauptteil-1_para-27_abs-3_inhalt-1"><akn:p GUID="491ea8dd-a734-4001-8558-7a70b632c037" eId="hauptteil-1_para-27_abs-3_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
                  <!-- Absatz 4 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-4" GUID="b7c29666-0b97-48cd-961d-d9a0d02c887a">
                     <akn:num eId="hauptteil-1_para-27_abs-4_bezeichnung-1" GUID="84ab35ff-8f39-4436-97d0-2c065ea1db0f">
                        <akn:marker eId="hauptteil-1_para-27_abs-4_bezeichnung-1_zaehlbez-1" GUID="f2cc7079-5f14-4822-90d3-54523ae0ba6f" name="4"></akn:marker>(4)</akn:num>
                     <akn:content GUID="23298ae2-6be8-4456-a2f1-5d33a87221f1" eId="hauptteil-1_para-27_abs-4_inhalt-1"><akn:p GUID="473b61da-0d84-4143-bfd3-2cce6da64254" eId="hauptteil-1_para-27_abs-4_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
                  <!-- Absatz 5 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-5" GUID="6fcbce33-7bb2-4bc5-b081-9c2aebcfe466">
                     <akn:num eId="hauptteil-1_para-27_abs-5_bezeichnung-1" GUID="01a7810b-792b-4a60-a9e0-c592993dbc3e">
                        <akn:marker eId="hauptteil-1_para-27_abs-5_bezeichnung-1_zaehlbez-1" GUID="ca0a2ee9-02c8-4493-9ad7-39ed1a8a7bac" name="5"></akn:marker>(5)</akn:num>
                     <akn:content GUID="8cd7c2d2-796f-47e7-9db5-f29ef5a6d6cb" eId="hauptteil-1_para-27_abs-5_inhalt-1"><akn:p GUID="b3de9223-4577-4c1d-8d12-3261004910b3" eId="hauptteil-1_para-27_abs-5_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
                  <!-- Absatz 6 -->
                  <akn:paragraph eId="hauptteil-1_para-27_abs-6" GUID="58ea2242-386a-42b9-8f23-eaa1678159f1">
                     <akn:num eId="hauptteil-1_para-27_abs-6_bezeichnung-1" GUID="c38a41af-4911-4b1a-9404-929912af983e">
                        <akn:marker eId="hauptteil-1_para-27_abs-6_bezeichnung-1_zaehlbez-1" GUID="00aa91c0-6457-4200-bce9-a6350377c58f" name="6"></akn:marker>(6)</akn:num>
                     <akn:content eId="hauptteil-1_para-27_abs-6_inhalt-1" GUID="88b5a47f-40ad-485a-9745-5caa81f93e44">
                        <akn:p GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5" eId="hauptteil-1_para-27_abs-6_inhalt-1_text-1">Ist ein Betrag nach Absatz 1 oder 3 abgezogen worden, so tritt für die Absetzungen für Abnutzung oder Substanzverringerung oder in den Fällen des § 6 Absatz 2 und Absatz 2a im Wirtschaftsjahr des Abzugs der verbleibende Betrag an die Stelle der Anschaffungs- oder Herstellungskosten. In den Fällen des § 7 Absatz 4 Satz 1, Absatz 5 und 5a sind die um den Abzugsbetrag nach Absatz 1 oder 3 geminderten Anschaffungs- oder Herstellungskosten maßgebend.</akn:p>
                     </akn:content>
                  </akn:paragraph>
               </akn:article>

               <akn:article eId="hauptteil-1_para-28" GUID="2a63a350-9d16-4587-b1f5-4da210fa3bec" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-28_bezeichnung-1" GUID="449e293a-3038-4bc0-bd7b-4f7f1353df93">
                           <akn:marker eId="hauptteil-1_para-28_bezeichnung-1_zaehlbez-1" GUID="40e1d025-a277-41a2-af1d-76427c5d99d0" name="1"></akn:marker>§6c</akn:num>
                  <akn:paragraph GUID="1418ccdb-59ff-482d-9a90-129ea202e563" eId="hauptteil-1_para-28_abs-1">
                     <akn:num eId="hauptteil-1_para-28_abs-1_bezeichnung-1" GUID="39ae2bbf-3709-4ae4-a749-b09404e49006">
                              <akn:marker eId="hauptteil-1_para-28_abs-1_bezeichnung-1_zaehlbez-1" GUID="89e39c65-4119-49b8-897d-9d1f663a8719" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="fde1aced-d2a7-409b-9a5b-cc97cd8d6e49" eId="hauptteil-1_para-28_abs-1_inhalt-1"><akn:p GUID="f715da45-d8c8-4ab7-bf00-26cc8ad2dd08" eId="hauptteil-1_para-28_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-29" GUID="504c662d-9018-4f1a-ad4e-84cae20bd885" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-29_bezeichnung-1" GUID="cf2c337c-83eb-409e-b310-1bebd1e3ad0c">
                           <akn:marker eId="hauptteil-1_para-29_bezeichnung-1_zaehlbez-1" GUID="99f1d666-05a9-4b48-97b6-b0e4de93c5b8" name="1"></akn:marker>§6d</akn:num>
                  <akn:paragraph GUID="3983bc04-5941-4605-a90e-5caeff00eea9" eId="hauptteil-1_para-29_abs-1">
                     <akn:num eId="hauptteil-1_para-29_abs-1_bezeichnung-1" GUID="f093656e-d154-4326-bd55-2c14e9b32b2b">
                              <akn:marker eId="hauptteil-1_para-29_abs-1_bezeichnung-1_zaehlbez-1" GUID="3f07db52-d330-4885-9ae4-f58ebbd48c8f" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="bc6a27ea-6c14-481f-8e6d-bae1a86aac5e" eId="hauptteil-1_para-29_abs-1_inhalt-1"><akn:p GUID="62d6cc8e-837e-4931-936a-4a18f4bfae92" eId="hauptteil-1_para-29_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-30" GUID="af8c737c-ba14-4f65-92ca-5c0ae45fa719" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-30_bezeichnung-1" GUID="114896af-7883-451b-9f2c-d1a415c314c1">
                           <akn:marker eId="hauptteil-1_para-30_bezeichnung-1_zaehlbez-1" GUID="8fa3954e-5382-4416-b80d-cda005a29847" name="1"></akn:marker>§6e</akn:num>
                  <akn:paragraph GUID="fb130f72-2180-4ea8-b3ff-938f0af0d220" eId="hauptteil-1_para-30_abs-1">
                     <akn:num eId="hauptteil-1_para-30_abs-1_bezeichnung-1" GUID="064f9219-38a1-4c1e-bf8d-1db70fa695f4">
                              <akn:marker eId="hauptteil-1_para-30_abs-1_bezeichnung-1_zaehlbez-1" GUID="ca46f27e-a108-4791-bde5-990f8d4c6afe" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="6fa29af6-0da4-4872-9498-425070196c59" eId="hauptteil-1_para-30_abs-1_inhalt-1"><akn:p GUID="a6354f0a-1a2f-402c-949d-fec4e0b8ea0b" eId="hauptteil-1_para-30_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-31" GUID="95eb7919-b14a-4d4f-9d09-84770d49ee28" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-31_bezeichnung-1" GUID="8b50a05e-79c5-4b5c-8600-6ad645fdf1bf">
                           <akn:marker eId="hauptteil-1_para-31_bezeichnung-1_zaehlbez-1" GUID="e2565fa8-3461-41c3-83ee-911ef3185173" name="1"></akn:marker>§7</akn:num>
                  <akn:paragraph GUID="7f9fc745-1f6e-4305-9d51-13e54ccfff81" eId="hauptteil-1_para-31_abs-1">
                     <akn:num eId="hauptteil-1_para-31_abs-1_bezeichnung-1" GUID="023c24d7-7dbd-4f79-b7de-0ff17caab320">
                              <akn:marker eId="hauptteil-1_para-31_abs-1_bezeichnung-1_zaehlbez-1" GUID="11a70d90-0fcd-40d7-a00d-df3ea32f3492" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="3c29f352-99e5-4fd9-a3d1-2a3483a24339" eId="hauptteil-1_para-31_abs-1_inhalt-1"><akn:p GUID="cc8c7eee-8281-4b07-a94f-a2e19541dc3d" eId="hauptteil-1_para-31_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-32" GUID="e0418ed8-210c-4df5-831d-84402c78fcd6" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-32_bezeichnung-1" GUID="e0da07d3-19b9-46ba-8c7f-cfa5232f256a">
                           <akn:marker eId="hauptteil-1_para-32_bezeichnung-1_zaehlbez-1" GUID="5e386e8d-1d08-4082-aa82-1c8170423c4c" name="1"></akn:marker>§7a</akn:num>
                  <akn:paragraph GUID="d65c9dc8-190a-4e44-b4ff-f8b58898d9de" eId="hauptteil-1_para-32_abs-1">
                     <akn:num eId="hauptteil-1_para-32_abs-1_bezeichnung-1" GUID="5210ea3c-4c94-4e3e-b998-8023eb85a7b8">
                              <akn:marker eId="hauptteil-1_para-32_abs-1_bezeichnung-1_zaehlbez-1" GUID="0c296795-0b59-4d32-9a0a-d6022457d252" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="221b988f-0964-4c84-82c3-8d42f7841f78" eId="hauptteil-1_para-32_abs-1_inhalt-1"><akn:p GUID="4813520d-d8ad-4941-8e18-2203419ac49d" eId="hauptteil-1_para-32_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-33" GUID="7f050d76-a8ed-4c15-8c5f-bc63cc2d592e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-33_bezeichnung-1" GUID="f13bced6-228d-422f-8b93-cb5917cb5040">
                           <akn:marker eId="hauptteil-1_para-33_bezeichnung-1_zaehlbez-1" GUID="3dbf1ac0-e48d-4b56-9689-b0b77757f220" name="1"></akn:marker>§7b</akn:num>
                  <akn:paragraph GUID="e2828978-41cc-4330-a736-825c5e1a4b60" eId="hauptteil-1_para-33_abs-1">
                     <akn:num eId="hauptteil-1_para-33_abs-1_bezeichnung-1" GUID="922f9819-c8b5-4fca-85c1-4b879c005347">
                              <akn:marker eId="hauptteil-1_para-33_abs-1_bezeichnung-1_zaehlbez-1" GUID="f8b3aa45-0092-42e1-ab29-4c7af5d98352" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="28f8eefc-f066-4a55-9223-c5b7dd60e8f6" eId="hauptteil-1_para-33_abs-1_inhalt-1"><akn:p GUID="2e2d643a-a604-4b42-b50c-f4449d332c31" eId="hauptteil-1_para-33_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-34" GUID="89f36695-4829-4639-98b3-7b87a3ee2664" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-34_bezeichnung-1" GUID="b418b0f3-70fb-447e-a394-105e76200573">
                           <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1" GUID="ded3aa35-2165-44b7-a34f-411a4a72a3c4" name="1"></akn:marker>§7c</akn:num>
                  <akn:paragraph GUID="2edaf508-548e-4197-b9dd-632dcf46ad68" eId="hauptteil-1_para-34_abs-1">
                     <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1" GUID="31ca8e8e-b7e2-4ff9-9f2b-9a016f5053cc">
                              <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1" GUID="01a9c4a7-8474-4273-82b2-0161ef08df5a" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="d9220821-868e-45e9-ad99-16c45037ac4e" eId="hauptteil-1_para-34_abs-1_inhalt-1"><akn:p GUID="42bbf761-e56c-4432-b794-674cdde1eac7" eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-35" GUID="c677c2a7-38bc-4c4d-988c-9db5ba9947ce" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-35_bezeichnung-1" GUID="bd8f2e0e-bea3-4d9e-898b-d8c0d5532808">
                           <akn:marker eId="hauptteil-1_para-35_bezeichnung-1_zaehlbez-1" GUID="176b6bca-d80c-4095-ab7c-249606da691a" name="1"></akn:marker>§7d</akn:num>
                  <akn:paragraph GUID="a890abf2-9911-4490-9f32-a5ae7830af9b" eId="hauptteil-1_para-35_abs-1">
                     <akn:num eId="hauptteil-1_para-35_abs-1_bezeichnung-1" GUID="e8181952-69ee-4847-8728-003ee791bd03">
                              <akn:marker eId="hauptteil-1_para-35_abs-1_bezeichnung-1_zaehlbez-1" GUID="80f658f3-1840-42c2-9a48-08e1a223a099" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="55e6cf69-6364-4d58-a18a-a74d7aa80dba" eId="hauptteil-1_para-35_abs-1_inhalt-1"><akn:p GUID="562c588e-5249-4c78-822e-f7ee654865ab" eId="hauptteil-1_para-35_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-36" GUID="7f4daf11-04cd-4d22-8586-1ec56b7b52df" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-36_bezeichnung-1" GUID="312541bc-1a0e-49aa-85b1-234d0378532b">
                           <akn:marker eId="hauptteil-1_para-36_bezeichnung-1_zaehlbez-1" GUID="c0cb7478-70a9-43e1-99d1-edf664a2ba7b" name="1"></akn:marker>§7e</akn:num>
                  <akn:paragraph GUID="51eb6574-ad7a-473b-826c-ff7d40dc394f" eId="hauptteil-1_para-36_abs-1">
                     <akn:num eId="hauptteil-1_para-36_abs-1_bezeichnung-1" GUID="9ea30b93-8c7e-4ee7-95b9-99df93524c0f">
                              <akn:marker eId="hauptteil-1_para-36_abs-1_bezeichnung-1_zaehlbez-1" GUID="054776b4-7cfa-45b6-b753-619a31be5f8e" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="a254f8eb-ebd6-44e9-83c8-acf217d2e207" eId="hauptteil-1_para-36_abs-1_inhalt-1"><akn:p GUID="46daee2c-4060-4553-baad-7fadb08e8553" eId="hauptteil-1_para-36_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-37" GUID="91d0fb1b-f8ff-4203-827f-96c52887c3a0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-37_bezeichnung-1" GUID="435a7e1a-a86b-4c80-8fd3-01edc555d79e">
                           <akn:marker eId="hauptteil-1_para-37_bezeichnung-1_zaehlbez-1" GUID="7cf4f72d-cdd6-435d-b5f8-308ab1e03c09" name="1"></akn:marker>§7f</akn:num>
                  <akn:paragraph GUID="00d9e791-1a3f-43c4-9ebd-3a6aeecb2b89" eId="hauptteil-1_para-37_abs-1">
                     <akn:num eId="hauptteil-1_para-37_abs-1_bezeichnung-1" GUID="96883ae8-097e-40b6-83e0-fbba493d667e">
                              <akn:marker eId="hauptteil-1_para-37_abs-1_bezeichnung-1_zaehlbez-1" GUID="6f585554-1c78-4c27-b8c7-3ba7e5639045" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="395a9397-6659-4c5d-a1d0-a8d6051725b5" eId="hauptteil-1_para-37_abs-1_inhalt-1"><akn:p GUID="f30b63e6-2f36-467c-92d7-7586ef9b2d02" eId="hauptteil-1_para-37_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-38" GUID="1db3fb3f-6b3b-4eb6-bb0e-4372b70d4ed8" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-38_bezeichnung-1" GUID="5e3ae644-efb8-4c15-a5fe-05d3251290bf">
                           <akn:marker eId="hauptteil-1_para-38_bezeichnung-1_zaehlbez-1" GUID="7b80fe8f-7c9d-4f8e-ab73-4929779edd66" name="1"></akn:marker>§7g</akn:num>
                  <akn:paragraph GUID="59aec1b9-5713-44a1-bfb3-17b49718ec2a" eId="hauptteil-1_para-38_abs-1">
                     <akn:num eId="hauptteil-1_para-38_abs-1_bezeichnung-1" GUID="d348e45b-e7d8-461b-9258-5e5dc2e5e1f9">
                              <akn:marker eId="hauptteil-1_para-38_abs-1_bezeichnung-1_zaehlbez-1" GUID="7449663a-d19f-4209-ba02-9bb0f3711536" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="17bf7389-796c-40bc-8fb0-20eb0b89f846" eId="hauptteil-1_para-38_abs-1_inhalt-1"><akn:p GUID="3c0ff811-1dc5-41dc-91cb-0ce5a49d92e4" eId="hauptteil-1_para-38_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-39" GUID="12f09447-ea90-4e64-9c81-77b458a96b23" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-39_bezeichnung-1" GUID="99de84a4-0cef-455b-aa00-6a56ad07a9a9">
                           <akn:marker eId="hauptteil-1_para-39_bezeichnung-1_zaehlbez-1" GUID="242b52d5-4839-489c-92eb-10cff5856ebe" name="1"></akn:marker>§7h</akn:num>
                  <akn:paragraph GUID="4d67a40a-c18d-4349-b22d-f7138258b314" eId="hauptteil-1_para-39_abs-1">
                     <akn:num eId="hauptteil-1_para-39_abs-1_bezeichnung-1" GUID="33ba0c61-1a73-4303-8db6-db75175396ac">
                              <akn:marker eId="hauptteil-1_para-39_abs-1_bezeichnung-1_zaehlbez-1" GUID="b8e00553-5b5c-4117-9e93-d98dd139afaa" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="356c3785-8371-4dee-b85c-c4022ca07036" eId="hauptteil-1_para-39_abs-1_inhalt-1"><akn:p GUID="59849e73-9b5e-44c7-9022-6e91851aa6cd" eId="hauptteil-1_para-39_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-40" GUID="579c9f9a-ba48-49cb-a60c-a571318be3d0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-40_bezeichnung-1" GUID="766a9b2e-e83e-4ffd-b2d8-9fe19a201370">
                           <akn:marker eId="hauptteil-1_para-40_bezeichnung-1_zaehlbez-1" GUID="91981426-20c2-4aa5-aa51-56d65ef40210" name="1"></akn:marker>§7i</akn:num>
                  <akn:paragraph GUID="814e4ff1-1f54-4cca-8689-707bacf4d37b" eId="hauptteil-1_para-40_abs-1">
                     <akn:num eId="hauptteil-1_para-40_abs-1_bezeichnung-1" GUID="dc302bd5-22f4-4123-a6bb-cd2146e8e3a0">
                              <akn:marker eId="hauptteil-1_para-40_abs-1_bezeichnung-1_zaehlbez-1" GUID="9d9ef951-c080-4557-8949-31226e341096" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="3016ef4b-1741-4d9a-a5ee-1a474e32cf3e" eId="hauptteil-1_para-40_abs-1_inhalt-1"><akn:p GUID="b96b09fb-fae0-495c-9a4d-78397519096a" eId="hauptteil-1_para-40_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-41" GUID="b2e0df87-d52f-458b-8901-6fa8a72c28c0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-41_bezeichnung-1" GUID="772fc8c6-2009-4c91-980f-1811ec8c98b7">
                           <akn:marker eId="hauptteil-1_para-41_bezeichnung-1_zaehlbez-1" GUID="9ffb0adb-680c-4cda-9026-ac447adbea78" name="1"></akn:marker>§7j</akn:num>
                  <akn:paragraph GUID="a7f2ed2e-0a5e-41e3-ba37-dd696cb41c13" eId="hauptteil-1_para-41_abs-1">
                     <akn:num eId="hauptteil-1_para-41_abs-1_bezeichnung-1" GUID="1ab126b7-a2ba-4165-85dc-3ba92d68f050">
                              <akn:marker eId="hauptteil-1_para-41_abs-1_bezeichnung-1_zaehlbez-1" GUID="fb0cec87-96c2-45e0-a06c-27630c9beccd" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="f461fe66-d927-4625-bf13-f60ec5f74b46" eId="hauptteil-1_para-41_abs-1_inhalt-1"><akn:p GUID="ee202292-1ffb-4e83-9f31-a402a563acbd" eId="hauptteil-1_para-41_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-42" GUID="f1631d4d-2c49-4de0-99e8-0313a98bc973" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-42_bezeichnung-1" GUID="70ddcd29-2719-479f-a2ce-9fba1f8513af">
                           <akn:marker eId="hauptteil-1_para-42_bezeichnung-1_zaehlbez-1" GUID="0a5aa72d-13e3-4b17-a656-3ca6543978d2" name="1"></akn:marker>§7k</akn:num>
                  <akn:paragraph GUID="b4dce994-2a44-4519-baca-61df6649312d" eId="hauptteil-1_para-42_abs-1">
                     <akn:num eId="hauptteil-1_para-42_abs-1_bezeichnung-1" GUID="bf9d2e42-b77b-4d1e-9036-7eb9ed3fdb23">
                              <akn:marker eId="hauptteil-1_para-42_abs-1_bezeichnung-1_zaehlbez-1" GUID="e241ced3-16a6-41bf-bb3f-feedaf2cedd3" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="c702ccb3-2ab8-4f9d-bc6a-10d62451b45c" eId="hauptteil-1_para-42_abs-1_inhalt-1"><akn:p GUID="828cac8a-2233-405d-bf8f-0e7012c97cf4" eId="hauptteil-1_para-42_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-43" GUID="5f555a46-b98c-4ca5-85eb-5d8c9bcc773b" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-43_bezeichnung-1" GUID="e0d7f9ef-6310-43ea-8ee8-7206f80a10cf">
                           <akn:marker eId="hauptteil-1_para-43_bezeichnung-1_zaehlbez-1" GUID="da45966d-0d32-453f-8396-72a1f90ad772" name="1"></akn:marker>§8</akn:num>
                  <akn:paragraph GUID="8f1eeb6c-8765-47d8-9256-2fdb06c25c21" eId="hauptteil-1_para-43_abs-1">
                     <akn:num eId="hauptteil-1_para-43_abs-1_bezeichnung-1" GUID="5894d7f0-88b4-4ceb-99fc-b78f7682ddf3">
                              <akn:marker eId="hauptteil-1_para-43_abs-1_bezeichnung-1_zaehlbez-1" GUID="e140a46b-28c4-4360-84dc-4b02ef8639b3" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="ec74badd-2483-4036-8475-d9ea34b0cd7c" eId="hauptteil-1_para-43_abs-1_inhalt-1"><akn:p GUID="aac2e07d-b31b-45ce-9730-39f0b35a4756" eId="hauptteil-1_para-43_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-44" GUID="76c34087-8785-4f65-bfcf-b21d3cce5447" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-44_bezeichnung-1" GUID="74b7793f-b3aa-407f-bc35-a7c275405e5a">
                           <akn:marker eId="hauptteil-1_para-44_bezeichnung-1_zaehlbez-1" GUID="1ba8c8d2-c155-4d92-a196-efb029a8aa07" name="1"></akn:marker>§9</akn:num>
                  <akn:paragraph GUID="03a3e523-3769-4e43-9044-c7354ed1ec61" eId="hauptteil-1_para-44_abs-1">
                     <akn:num eId="hauptteil-1_para-44_abs-1_bezeichnung-1" GUID="f3555f68-2e82-45c3-80e2-30568ff3e977">
                              <akn:marker eId="hauptteil-1_para-44_abs-1_bezeichnung-1_zaehlbez-1" GUID="e7522b84-b956-4ec1-8dfc-e7314502a7df" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="6ee4bd25-6e3e-4508-a4cd-4c27cfc0285c" eId="hauptteil-1_para-44_abs-1_inhalt-1"><akn:p GUID="114c7481-6682-456f-bd7a-cbe95d23c479" eId="hauptteil-1_para-44_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-45" GUID="ee674638-ebcd-4afb-8d67-507ec4590dc3" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-45_bezeichnung-1" GUID="c34dab0f-0b9f-4507-8fda-c611098eff33">
                           <akn:marker eId="hauptteil-1_para-45_bezeichnung-1_zaehlbez-1" GUID="5a510f04-234b-4e98-9f3d-a6d0a0b85c45" name="1"></akn:marker>§9a</akn:num>
                  <akn:paragraph GUID="67af4843-ce5c-45f0-bb29-785a233fe79a" eId="hauptteil-1_para-45_abs-1">
                     <akn:num eId="hauptteil-1_para-45_abs-1_bezeichnung-1" GUID="91917c04-2e02-48d8-9472-712b51c46f39">
                              <akn:marker eId="hauptteil-1_para-45_abs-1_bezeichnung-1_zaehlbez-1" GUID="6d9fbe8d-05f4-49e9-bb20-8f5546d9c7d1" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="763a8a04-b258-41aa-89a9-f6bcdc3ca691" eId="hauptteil-1_para-45_abs-1_inhalt-1"><akn:p GUID="81bde952-65db-475c-9ceb-2b4abf18adac" eId="hauptteil-1_para-45_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-46" GUID="ccf524cc-5857-417b-8874-ac15bafe9870" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-46_bezeichnung-1" GUID="bdd35c51-f64b-4c4f-ad08-d66a499b7c4a">
                           <akn:marker eId="hauptteil-1_para-46_bezeichnung-1_zaehlbez-1" GUID="9a3d4f9d-0534-4da6-8a4c-2804a4d70af9" name="1"></akn:marker>§9b</akn:num>
                  <akn:paragraph GUID="f1ce8898-144a-41c4-a797-bf158c785e64" eId="hauptteil-1_para-46_abs-1">
                     <akn:num eId="hauptteil-1_para-46_abs-1_bezeichnung-1" GUID="ae91a07a-3c2f-4e1b-9190-998aed9d9878">
                              <akn:marker eId="hauptteil-1_para-46_abs-1_bezeichnung-1_zaehlbez-1" GUID="26da12f6-64d1-4949-ba2c-580bf20167fb" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="f09a4a6d-1021-45e9-9f39-3bba81890bac" eId="hauptteil-1_para-46_abs-1_inhalt-1"><akn:p GUID="64022ca3-9601-4030-9996-b8660ccbfa31" eId="hauptteil-1_para-46_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-47" GUID="db7f88c9-f304-4564-a294-9a6b0c8d3567" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-47_bezeichnung-1" GUID="5d697e3e-bcc8-4c77-9b84-513cf64747bc">
                           <akn:marker eId="hauptteil-1_para-47_bezeichnung-1_zaehlbez-1" GUID="431400b6-2f44-4984-b9cb-2bf306a27c2b" name="1"></akn:marker>§10</akn:num>
                  <akn:paragraph GUID="8e573156-d96e-40be-9341-1c48c0f6d24f" eId="hauptteil-1_para-47_abs-1">
                     <akn:num eId="hauptteil-1_para-47_abs-1_bezeichnung-1" GUID="626b6481-44ce-4574-9048-175189c2a1a6">
                              <akn:marker eId="hauptteil-1_para-47_abs-1_bezeichnung-1_zaehlbez-1" GUID="d5782f9b-5865-47dd-8efe-a6872e8488b9" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="90c69d6e-3325-4e18-8c96-e92588aea0fb" eId="hauptteil-1_para-47_abs-1_inhalt-1"><akn:p GUID="02742a1f-daab-43cb-8b3e-006d89b1fc4f" eId="hauptteil-1_para-47_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-48" GUID="e14bd426-f9e7-480b-9069-0d9cb0fd7b80" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-48_bezeichnung-1" GUID="1832f4b2-6856-412e-8012-44f9b885c69c">
                           <akn:marker eId="hauptteil-1_para-48_bezeichnung-1_zaehlbez-1" GUID="a74d27ee-1639-4440-b476-ed7d2808afaf" name="1"></akn:marker>§10a</akn:num>
                  <akn:paragraph GUID="a23ffb18-d9c9-4030-a54e-168f1d6b9122" eId="hauptteil-1_para-48_abs-1">
                     <akn:num eId="hauptteil-1_para-48_abs-1_bezeichnung-1" GUID="f0b9f3d9-6c74-4938-bb6e-b0b7b4bf729a">
                              <akn:marker eId="hauptteil-1_para-48_abs-1_bezeichnung-1_zaehlbez-1" GUID="cdbe98ba-554f-4ee3-89fc-200913caca2c" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="2348a890-8255-4978-ae9e-256907b3822f" eId="hauptteil-1_para-48_abs-1_inhalt-1"><akn:p GUID="73beeb6d-b875-46f5-91fc-cdb9276369b3" eId="hauptteil-1_para-48_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-49" GUID="43bb65f4-9998-49ff-b685-e297fdc6ed39" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-49_bezeichnung-1" GUID="c51df148-7d09-40fe-92f8-a3c29f6f3cee">
                           <akn:marker eId="hauptteil-1_para-49_bezeichnung-1_zaehlbez-1" GUID="ea0ec36f-3ff3-439e-b617-dddcb8585a0f" name="1"></akn:marker>§10b</akn:num>
                  <akn:paragraph GUID="dbdb0ed2-eb51-4ddb-8192-5fffc282e463" eId="hauptteil-1_para-49_abs-1">
                     <akn:num eId="hauptteil-1_para-49_abs-1_bezeichnung-1" GUID="58ef9d6d-8c69-445b-aa29-aaca7021836b">
                              <akn:marker eId="hauptteil-1_para-49_abs-1_bezeichnung-1_zaehlbez-1" GUID="3ebc15d5-a2cf-4a68-b105-ac1369c05cda" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="4fc7d436-f74d-49fa-a67a-52c972fcf1d9" eId="hauptteil-1_para-49_abs-1_inhalt-1"><akn:p GUID="22031202-2172-49bd-accd-ce5dc3da4149" eId="hauptteil-1_para-49_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-50" GUID="bc497bd8-d49f-418d-9c2c-88f67f86daaf" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-50_bezeichnung-1" GUID="9138e716-266f-4509-9cd5-ccda34ee11b8">
                           <akn:marker eId="hauptteil-1_para-50_bezeichnung-1_zaehlbez-1" GUID="c58e7125-a26e-4e0d-95af-063b654ba5ee" name="1"></akn:marker>§10c</akn:num>
                  <akn:paragraph GUID="05906cf6-4591-4f6b-806d-3e2f42700e2c" eId="hauptteil-1_para-50_abs-1">
                     <akn:num eId="hauptteil-1_para-50_abs-1_bezeichnung-1" GUID="2c09968b-876d-46e6-9106-bf108008e114">
                              <akn:marker eId="hauptteil-1_para-50_abs-1_bezeichnung-1_zaehlbez-1" GUID="874efebd-bfb1-46ad-925e-2a67ed2b4d51" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="349f137e-c17a-4a22-a0eb-ca4f7658ab0d" eId="hauptteil-1_para-50_abs-1_inhalt-1"><akn:p GUID="17b21830-5240-4a8f-a911-9ef537d9f451" eId="hauptteil-1_para-50_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-51" GUID="9200365a-1df4-4983-85c8-451e421e9d0d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-51_bezeichnung-1" GUID="a1e1b0f3-7799-4648-b3e4-3b0deecd05f7">
                           <akn:marker eId="hauptteil-1_para-51_bezeichnung-1_zaehlbez-1" GUID="7ae45d32-486c-446e-b736-7b0ad304077f" name="1"></akn:marker>§10d</akn:num>
                  <akn:paragraph GUID="804b00e6-00a4-434e-99e9-643ed7684804" eId="hauptteil-1_para-51_abs-1">
                     <akn:num eId="hauptteil-1_para-51_abs-1_bezeichnung-1" GUID="93397acf-6c68-401c-bdba-d4a6a5c03fe5">
                              <akn:marker eId="hauptteil-1_para-51_abs-1_bezeichnung-1_zaehlbez-1" GUID="946772cf-4c61-4108-8dde-3f6687eb3c4f" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="24c2d356-954b-4ea5-9230-a6900ead68c0" eId="hauptteil-1_para-51_abs-1_inhalt-1"><akn:p GUID="d0b50a0e-fad2-47c9-9bc6-298deffbf4c3" eId="hauptteil-1_para-51_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-52" GUID="706cf9b3-20fb-4952-80f7-fcf52afa5532" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-52_bezeichnung-1" GUID="74fa2557-59aa-43b8-b990-720ad6813f93">
                           <akn:marker eId="hauptteil-1_para-52_bezeichnung-1_zaehlbez-1" GUID="9bce7946-fc0f-480f-8a4f-2f5e58636142" name="1"></akn:marker>§10e</akn:num>
                  <akn:paragraph GUID="2c34eaa0-9445-45ff-ae73-e4f903602624" eId="hauptteil-1_para-52_abs-1">
                     <akn:num eId="hauptteil-1_para-52_abs-1_bezeichnung-1" GUID="e76ba7c6-e8b7-43ef-9e44-08702928fb47">
                              <akn:marker eId="hauptteil-1_para-52_abs-1_bezeichnung-1_zaehlbez-1" GUID="a7bc2898-67f9-4fac-9cce-25baff77faf4" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="d5404cfe-bbdd-4198-91ee-e0c0ff4c205c" eId="hauptteil-1_para-52_abs-1_inhalt-1"><akn:p GUID="a0761643-a5ac-4590-ba3f-1cd8abec189f" eId="hauptteil-1_para-52_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-53" GUID="85c19e43-7ae6-4051-b7d0-6cbeb0a2a3ae" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-53_bezeichnung-1" GUID="d7233aa6-cb7e-4eae-967c-a39570c4b680">
                           <akn:marker eId="hauptteil-1_para-53_bezeichnung-1_zaehlbez-1" GUID="8d93f760-aa32-44ab-915b-1001da584bdf" name="1"></akn:marker>§10f</akn:num>
                  <akn:paragraph GUID="fb771acf-763d-4f65-bc3e-6c47244df2e9" eId="hauptteil-1_para-53_abs-1">
                     <akn:num eId="hauptteil-1_para-53_abs-1_bezeichnung-1" GUID="d8da0052-d1cb-4dfc-97c3-0d433b5e7a8b">
                              <akn:marker eId="hauptteil-1_para-53_abs-1_bezeichnung-1_zaehlbez-1" GUID="52078241-2148-41ff-8322-9ebb7cae11b3" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="85b183d9-d114-4fd5-8293-a458f48d6c85" eId="hauptteil-1_para-53_abs-1_inhalt-1"><akn:p GUID="36ae4b5d-b7f9-4c95-852a-01433026b3c3" eId="hauptteil-1_para-53_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-54" GUID="8b3b9885-e5cf-49d1-aec4-c851ffa8e612" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-54_bezeichnung-1" GUID="831a216d-a89e-4fcc-883c-552d56aa2c4c">
                           <akn:marker eId="hauptteil-1_para-54_bezeichnung-1_zaehlbez-1" GUID="3b9ded1f-ed9e-4300-b2a2-18bcc27602fc" name="1"></akn:marker>§10g</akn:num>
                  <akn:paragraph GUID="fe4a8d3d-9623-4556-bf68-753aa26d75f3" eId="hauptteil-1_para-54_abs-1">
                     <akn:num eId="hauptteil-1_para-54_abs-1_bezeichnung-1" GUID="639909a0-b708-446c-844e-817979d52c92">
                              <akn:marker eId="hauptteil-1_para-54_abs-1_bezeichnung-1_zaehlbez-1" GUID="58446e4c-5f64-414e-b45c-2c395be72495" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="3a5712a6-b844-451d-8e3d-14f2bb1921c8" eId="hauptteil-1_para-54_abs-1_inhalt-1"><akn:p GUID="13458f97-90b0-4ebf-99fa-de6ae5e13e18" eId="hauptteil-1_para-54_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-55" GUID="d1e43ff6-aa11-45a8-9eda-e4c5f2ba8aaa" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-55_bezeichnung-1" GUID="56a7b2bd-b247-4389-a8c1-a2b4fba9a577">
                           <akn:marker eId="hauptteil-1_para-55_bezeichnung-1_zaehlbez-1" GUID="4d3ea4ea-200b-48d2-bf7c-e6e7ee854653" name="1"></akn:marker>§10h</akn:num>
                  <akn:paragraph GUID="d948e95d-14c4-4e96-b636-7f2b6f72a42f" eId="hauptteil-1_para-55_abs-1">
                     <akn:num eId="hauptteil-1_para-55_abs-1_bezeichnung-1" GUID="1220162c-2074-499a-9302-a83f3550a531">
                              <akn:marker eId="hauptteil-1_para-55_abs-1_bezeichnung-1_zaehlbez-1" GUID="162a175c-52b8-43a1-88c3-3074be962051" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="cf3f2eed-722b-4996-9d06-c6c714838033" eId="hauptteil-1_para-55_abs-1_inhalt-1"><akn:p GUID="3bc0ec8b-828f-4d29-8262-01b3c16524b0" eId="hauptteil-1_para-55_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-56" GUID="7b4fb4ab-e1b3-45a7-8749-5df3e89a06f1" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-56_bezeichnung-1" GUID="468d82d5-8be5-4a63-aaa9-c3ea18f63bef">
                           <akn:marker eId="hauptteil-1_para-56_bezeichnung-1_zaehlbez-1" GUID="74ae4f81-5524-4b5d-9316-86b6fbbe85dd" name="1"></akn:marker>§10i</akn:num>
                  <akn:paragraph GUID="54f67d49-a7d1-44a9-8e8e-32d42e3efe9d" eId="hauptteil-1_para-56_abs-1">
                     <akn:num eId="hauptteil-1_para-56_abs-1_bezeichnung-1" GUID="46463b98-9582-4d79-9855-0de6b6f55bff">
                              <akn:marker eId="hauptteil-1_para-56_abs-1_bezeichnung-1_zaehlbez-1" GUID="65931160-1d8f-4e49-ba60-8a4540d05304" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="de1e05c8-4c38-443b-825f-5426bd934b62" eId="hauptteil-1_para-56_abs-1_inhalt-1"><akn:p GUID="900efd8d-62d9-4839-84fa-815dca262549" eId="hauptteil-1_para-56_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-57" GUID="52869254-3d60-4278-976c-12c6caa0e9ca" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-57_bezeichnung-1" GUID="2940f21e-162f-4af9-87d7-ea5e77640759">
                           <akn:marker eId="hauptteil-1_para-57_bezeichnung-1_zaehlbez-1" GUID="36c9469a-fd93-4895-94ce-ddce4cf24fb0" name="1"></akn:marker>§11</akn:num>
                  <akn:paragraph GUID="4b7bd11e-d8be-4e17-b856-afabf5503eee" eId="hauptteil-1_para-57_abs-1">
                     <akn:num eId="hauptteil-1_para-57_abs-1_bezeichnung-1" GUID="9176b31d-1277-4ccb-a851-06655a932c02">
                              <akn:marker eId="hauptteil-1_para-57_abs-1_bezeichnung-1_zaehlbez-1" GUID="9b6dd744-dc1a-4385-b18c-10ae62fb0d5c" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="932128d1-8e6f-4bfa-85c0-5210b4f20662" eId="hauptteil-1_para-57_abs-1_inhalt-1"><akn:p GUID="39cee6c0-4513-4d55-9eb7-0ed16f3436ec" eId="hauptteil-1_para-57_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-58" GUID="f8d485e1-0153-4460-b509-9577af35aa1f" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-58_bezeichnung-1" GUID="2e13a1c9-a506-4dc1-9efa-aad0c2573f7b">
                           <akn:marker eId="hauptteil-1_para-58_bezeichnung-1_zaehlbez-1" GUID="e71f7e8f-3672-4a05-8df2-49076d04a854" name="1"></akn:marker>§11a</akn:num>
                  <akn:paragraph GUID="d7452750-46cf-4299-be42-aff8774f0739" eId="hauptteil-1_para-58_abs-1">
                     <akn:num eId="hauptteil-1_para-58_abs-1_bezeichnung-1" GUID="f775f2da-f619-4c6c-962b-9421023e84c1">
                              <akn:marker eId="hauptteil-1_para-58_abs-1_bezeichnung-1_zaehlbez-1" GUID="0f80a7f9-18f3-408e-80b3-4908514898c3" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="dccbe848-f55f-48bc-99ea-ace45ade406d" eId="hauptteil-1_para-58_abs-1_inhalt-1"><akn:p GUID="2eaf494d-ffdd-4aea-9740-899c04070c01" eId="hauptteil-1_para-58_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-59" GUID="7f87a5ce-4eac-4618-8b88-11ad9a73f094" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-59_bezeichnung-1" GUID="458930e8-7825-4f8b-af4c-18afcae80c99">
                           <akn:marker eId="hauptteil-1_para-59_bezeichnung-1_zaehlbez-1" GUID="c93e130b-b767-4b45-90d1-68fe92d61236" name="1"></akn:marker>§11b</akn:num>
                  <akn:paragraph GUID="a7c97c41-a018-48b6-bfdd-28f6209659cf" eId="hauptteil-1_para-59_abs-1">
                     <akn:num eId="hauptteil-1_para-59_abs-1_bezeichnung-1" GUID="f7eab3e9-6018-4bea-a5c8-3f28c62c2f37">
                              <akn:marker eId="hauptteil-1_para-59_abs-1_bezeichnung-1_zaehlbez-1" GUID="f43a3121-f668-4b7b-bf39-3e8e042fa933" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="350aad01-5c46-4090-b7f4-0eb16d7f02d0" eId="hauptteil-1_para-59_abs-1_inhalt-1"><akn:p GUID="1b26c17a-9243-4082-acd8-6f5a2ffa47dc" eId="hauptteil-1_para-59_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-60" GUID="d5cc84de-4a2f-4112-b873-535d0767fc5c" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-60_bezeichnung-1" GUID="48b761b6-4cc8-40f2-9ef2-9314b83baf65">
                           <akn:marker eId="hauptteil-1_para-60_bezeichnung-1_zaehlbez-1" GUID="f88103bc-2915-42fa-a37b-fe82afe95894" name="1"></akn:marker>§12</akn:num>
                  <akn:paragraph GUID="0254c66d-ae15-48ca-abad-803687cac3e0" eId="hauptteil-1_para-60_abs-1">
                     <akn:num eId="hauptteil-1_para-60_abs-1_bezeichnung-1" GUID="3a9c0aa6-6a01-4a29-ab37-44f584030095">
                              <akn:marker eId="hauptteil-1_para-60_abs-1_bezeichnung-1_zaehlbez-1" GUID="b274638d-dcf5-44a1-a9f5-4c895a1de763" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="7853e7ce-759e-415b-bfe1-c05e3b443f9c" eId="hauptteil-1_para-60_abs-1_inhalt-1"><akn:p GUID="ceef25f2-a8b3-4775-a43d-958a54607fa8" eId="hauptteil-1_para-60_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-61" GUID="8066551b-deb0-434a-8fcb-5f5a28237b11" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-61_bezeichnung-1" GUID="7fa61a6b-ab26-4d3d-ae4a-ba5a19f5e585">
                           <akn:marker eId="hauptteil-1_para-61_bezeichnung-1_zaehlbez-1" GUID="89ce6989-add4-42dd-b52e-eb2e631d8b9b" name="1"></akn:marker>§13</akn:num>
                  <akn:paragraph GUID="2fe3952d-4d95-4fd6-94b0-5f1ad3c40bed" eId="hauptteil-1_para-61_abs-1">
                     <akn:num eId="hauptteil-1_para-61_abs-1_bezeichnung-1" GUID="87bfb2f0-c3fe-4351-b2c6-abba45d8a49e">
                              <akn:marker eId="hauptteil-1_para-61_abs-1_bezeichnung-1_zaehlbez-1" GUID="1cfc7439-eee5-4030-b6ce-c0de16f1d139" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="1b452dde-d8f0-4e31-b268-4a5add6410f5" eId="hauptteil-1_para-61_abs-1_inhalt-1"><akn:p GUID="a6e1370d-4c4f-4fdc-8e70-021a7c46485d" eId="hauptteil-1_para-61_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-62" GUID="58b72cdd-9361-4f44-adf4-9e46b4dd411b" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-62_bezeichnung-1" GUID="7d236b8f-87eb-4b7f-bb32-f621a43b96d5">
                           <akn:marker eId="hauptteil-1_para-62_bezeichnung-1_zaehlbez-1" GUID="cc700ea8-2378-42c8-8520-02f378ddf72d" name="1"></akn:marker>§13a</akn:num>
                  <akn:paragraph GUID="15351e75-b249-43e6-90ac-9732977790de" eId="hauptteil-1_para-62_abs-1">
                     <akn:num eId="hauptteil-1_para-62_abs-1_bezeichnung-1" GUID="26357c0a-3921-4630-be95-2ccefb23d4f3">
                              <akn:marker eId="hauptteil-1_para-62_abs-1_bezeichnung-1_zaehlbez-1" GUID="33fa504a-2dc0-484d-86b3-a8d8bc88c396" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="886c6bfe-40f2-4bbb-8764-fbdcdab02969" eId="hauptteil-1_para-62_abs-1_inhalt-1"><akn:p GUID="02fe82fd-f46e-4fd1-a2f4-a74b72e052d4" eId="hauptteil-1_para-62_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-63" GUID="2603ad73-00f3-4f3e-aa0d-096b46899c2e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-63_bezeichnung-1" GUID="2d2fa553-9a11-47da-8331-079042c8f3a8">
                           <akn:marker eId="hauptteil-1_para-63_bezeichnung-1_zaehlbez-1" GUID="6748e5ec-813b-49e1-afaa-5e353da80005" name="1"></akn:marker>§14</akn:num>
                  <akn:paragraph GUID="dfc62626-d0ec-4642-9bf9-5e284a62ea16" eId="hauptteil-1_para-63_abs-1">
                     <akn:num eId="hauptteil-1_para-63_abs-1_bezeichnung-1" GUID="0e7d6304-4621-4df4-8570-aae3de882855">
                              <akn:marker eId="hauptteil-1_para-63_abs-1_bezeichnung-1_zaehlbez-1" GUID="5cd85416-f93a-4b66-8b2c-a07f769e2163" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="8e30d562-065b-4cc0-ba23-04533003726b" eId="hauptteil-1_para-63_abs-1_inhalt-1"><akn:p GUID="d384eaed-1725-4394-8ea5-7c74f47d8eac" eId="hauptteil-1_para-63_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-64" GUID="bf3fe544-55f5-44f1-b40e-9e318cc8e4c2" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-64_bezeichnung-1" GUID="cbadeaa2-e413-4873-ba67-93aa9b108e01">
                           <akn:marker eId="hauptteil-1_para-64_bezeichnung-1_zaehlbez-1" GUID="e47f6707-d744-49c4-86db-0d872aee0669" name="1"></akn:marker>§14a</akn:num>
                  <akn:paragraph GUID="97834b4e-b5b7-4800-a7bd-fc0ddc47c596" eId="hauptteil-1_para-64_abs-1">
                     <akn:num eId="hauptteil-1_para-64_abs-1_bezeichnung-1" GUID="800aefa7-bd3c-49be-80b4-3015561c0c1a">
                              <akn:marker eId="hauptteil-1_para-64_abs-1_bezeichnung-1_zaehlbez-1" GUID="f8f10835-87ee-4569-8e4f-9c716cba1c4b" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="3a00eb3d-1aae-4ab5-85df-fbb50b91d87b" eId="hauptteil-1_para-64_abs-1_inhalt-1"><akn:p GUID="3e19a349-dd89-4f93-8dc7-b2bc9c96157e" eId="hauptteil-1_para-64_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-65" GUID="e0f00c8d-6e4b-41e3-8146-bf18ab653a61" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-65_bezeichnung-1" GUID="943249dd-a5f5-44a6-be1c-b765b12ffb7c">
                           <akn:marker eId="hauptteil-1_para-65_bezeichnung-1_zaehlbez-1" GUID="c23b8b25-4052-4c1b-8c60-ba291ee502bf" name="1"></akn:marker>§15</akn:num>
                  <akn:paragraph GUID="53270dfd-3d76-468d-8f3a-180a5cd03dd1" eId="hauptteil-1_para-65_abs-1">
                     <akn:num eId="hauptteil-1_para-65_abs-1_bezeichnung-1" GUID="91455560-b3bf-4a6c-8103-8a74bd38f828">
                              <akn:marker eId="hauptteil-1_para-65_abs-1_bezeichnung-1_zaehlbez-1" GUID="4a936f87-5789-47ca-a128-dcf883ad97ea" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="ad504722-5457-4949-a9c3-07198f633303" eId="hauptteil-1_para-65_abs-1_inhalt-1"><akn:p GUID="77621563-5a54-4dcf-ad05-7ee370cd3a78" eId="hauptteil-1_para-65_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-66" GUID="59f33977-e133-403d-87f2-a8a19e660126" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-66_bezeichnung-1" GUID="b79815cd-e9b2-4a49-a8aa-3fa7bf441e8b">
                           <akn:marker eId="hauptteil-1_para-66_bezeichnung-1_zaehlbez-1" GUID="a7514c8a-cf9d-4438-b494-aab1fa9c3c6c" name="1"></akn:marker>§15a</akn:num>
                  <akn:paragraph GUID="b71f169d-5a18-43f7-ab2a-4ec4d7bdcce2" eId="hauptteil-1_para-66_abs-1">
                     <akn:num eId="hauptteil-1_para-66_abs-1_bezeichnung-1" GUID="40688c5e-d183-4970-8df8-a6018e5ed41e">
                              <akn:marker eId="hauptteil-1_para-66_abs-1_bezeichnung-1_zaehlbez-1" GUID="907d59f6-912d-4adb-9958-9a8221dea937" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="6a3dc9a2-c698-4fa8-abc3-6c0ee92b867e" eId="hauptteil-1_para-66_abs-1_inhalt-1"><akn:p GUID="5cd1f012-7fa3-4438-ac39-6e46c2a9cdec" eId="hauptteil-1_para-66_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-67" GUID="d39e85a0-e42d-42e5-ae6e-3e4b01d7c125" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-67_bezeichnung-1" GUID="bf7dee3c-4eae-4e52-82a4-9989306fa60b">
                           <akn:marker eId="hauptteil-1_para-67_bezeichnung-1_zaehlbez-1" GUID="210cc7b2-7e99-45d3-8d78-02b83bccbd46" name="1"></akn:marker>§15b</akn:num>
                  <akn:paragraph GUID="ee1207d0-5a50-4491-8f3d-aff67f7e8cc3" eId="hauptteil-1_para-67_abs-1">
                     <akn:num eId="hauptteil-1_para-67_abs-1_bezeichnung-1" GUID="9269d744-58f9-4702-a381-ae4e0b019fe7">
                              <akn:marker eId="hauptteil-1_para-67_abs-1_bezeichnung-1_zaehlbez-1" GUID="28b55eff-51b5-4ea2-9662-e6334977a7ff" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="fc89e421-a91b-4ef6-8e50-a03610d792be" eId="hauptteil-1_para-67_abs-1_inhalt-1"><akn:p GUID="9cd7acc7-1ca0-4bca-8163-b9631ef4039f" eId="hauptteil-1_para-67_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-68" GUID="13a6f767-98dd-4d47-a578-29ab52eade2f" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-68_bezeichnung-1" GUID="cc243a4b-5f4c-4a8b-9266-afe19faf7c50">
                           <akn:marker eId="hauptteil-1_para-68_bezeichnung-1_zaehlbez-1" GUID="e2614a95-34e0-4e67-bb42-ae8917071478" name="1"></akn:marker>§16</akn:num>
                  <akn:paragraph GUID="69ec5e14-a326-40d7-89cf-a0050a92960b" eId="hauptteil-1_para-68_abs-1">
                     <akn:num eId="hauptteil-1_para-68_abs-1_bezeichnung-1" GUID="5e0ff084-7c12-4f2d-a4c7-a50c3eba46f9">
                              <akn:marker eId="hauptteil-1_para-68_abs-1_bezeichnung-1_zaehlbez-1" GUID="a5eaf1ec-2b98-47ea-9d14-585b4893d610" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="8bf5fdd9-b927-4ca3-96cd-6e6d2594ab8b" eId="hauptteil-1_para-68_abs-1_inhalt-1"><akn:p GUID="08d880ae-2418-4381-a3b1-c2c724671d79" eId="hauptteil-1_para-68_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article eId="hauptteil-1_para-69" GUID="6457e6d5-1210-461c-b1d5-c1790b26e838" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-69_bezeichnung-1" GUID="72285626-56c2-4d70-87fc-6e763ce3b2b7">
                           <akn:marker eId="hauptteil-1_para-69_bezeichnung-1_zaehlbez-1" GUID="85ce7e7b-b1f9-4d9a-88cb-9db3342b607e" name="1"></akn:marker>§17</akn:num>
                  <akn:paragraph GUID="162917c4-6e74-40e1-97e9-f32a319ccc1e" eId="hauptteil-1_para-69_abs-1">
                     <akn:num eId="hauptteil-1_para-69_abs-1_bezeichnung-1" GUID="4765be64-12f6-4d50-a18b-84337ea8c600">
                              <akn:marker eId="hauptteil-1_para-69_abs-1_bezeichnung-1_zaehlbez-1" GUID="e4f2edd1-565c-48ff-8e07-04c041c6f792" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="8f5410e5-178c-4c0e-8f33-c32d4e2bce23" eId="hauptteil-1_para-69_abs-1_inhalt-1"><akn:p GUID="1963d605-9a42-4cf2-b89c-9da40b5a865e" eId="hauptteil-1_para-69_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
            <akn:article eId="hauptteil-1_para-70" GUID="59e3f34c-4f28-4e34-86ae-f2d45181d5b6" period="#geltungszeitgr-3">
               <akn:num eId="hauptteil-1_para-70_bezeichnung-1" GUID="805050c7-e327-4bcb-b1a8-c14e98e5c372">
                        <akn:marker eId="hauptteil-1_para-70_bezeichnung-1_zaehlbez-1" GUID="cafa575a-ed61-46e0-9690-1bd374c6767e" name="1"></akn:marker>§18</akn:num>
               <akn:paragraph GUID="e02d01a5-30e7-4ca5-8535-a5f8762b9bd0" eId="hauptteil-1_para-70_abs-1">
                  <akn:num eId="hauptteil-1_para-70_abs-1_bezeichnung-1" GUID="738a4278-5552-4b36-b607-bc6fca803a36">
                           <akn:marker eId="hauptteil-1_para-70_abs-1_bezeichnung-1_zaehlbez-1" GUID="897eeba8-24e0-4db5-96da-0c2d4e88a0b4" name="1"></akn:marker>(1)</akn:num>
                           <akn:content GUID="5a567605-e8a5-4a20-a9ed-423db6cce51a" eId="hauptteil-1_para-70_abs-1_inhalt-1"><akn:p GUID="b1691777-4124-4097-99e0-b00073b7db53" eId="hauptteil-1_para-70_abs-1_inhalt-1_text-1"></akn:p></akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-71" GUID="a726a175-3a70-40e7-852f-9d08d1a26a13" period="#geltungszeitgr-3">
               <akn:num eId="hauptteil-1_para-71_bezeichnung-1" GUID="0298199b-a8d3-4f7b-b5e5-16b4dc2501d1">
                        <akn:marker eId="hauptteil-1_para-71_bezeichnung-1_zaehlbez-1" GUID="500625c3-e8c9-4226-99b9-8b175c0d7635" name="1"></akn:marker>§19</akn:num>
               <!--Absatz 1-->
               <akn:paragraph GUID="eb932754-5119-4fc1-a167-4dfc5381d2ca" eId="hauptteil-1_para-71_abs-1">
                  <akn:num eId="hauptteil-1_para-71_abs-1_bezeichnung-1" GUID="8280d4ef-4a96-44b7-81b9-f30f1006a2fe">
                           <akn:marker eId="hauptteil-1_para-71_abs-1_bezeichnung-1_zaehlbez-1" GUID="0115df75-44e1-46e5-ac41-46c03643a0a6" name="1"></akn:marker>(1)</akn:num>
                           <akn:content GUID="eeeac3d5-4e22-4b6f-9d91-ab70a47f3640" eId="hauptteil-1_para-71_abs-1_inhalt-1">
                              <akn:p GUID="2d1e76a2-d330-49d5-b2e4-4f9feea44f85" eId="hauptteil-1_para-71_abs-1_inhalt-1_text-1"></akn:p>
                           </akn:content>
               </akn:paragraph>
               <!--Absatz 2-->
               <akn:paragraph GUID="cd17b333-e7d5-4f0f-9406-5d50eb12f783" eId="hauptteil-1_para-71_abs-2">
                  <akn:num eId="hauptteil-1_para-71_abs-2_bezeichnung-1" GUID="48d155ca-ec9e-4136-a14a-96d4f7e7a017">
                           <akn:marker eId="hauptteil-1_para-71_abs-2_bezeichnung-1_zaehlbez-1" GUID="68f1d541-9740-43e2-950d-4c8bfb38a3ab" name="2"></akn:marker>(2)</akn:num>
                           <akn:list GUID="3035f805-a24d-45c3-9cb6-785fa2cc9812" eId="hauptteil-1_para-71_abs-2_untergl-1">
                              <akn:intro GUID="244de4e1-4976-4959-b274-22a0e4253bea" eId="hauptteil-1_para-71_abs-2_untergl-1_intro-1">
                                 <akn:p GUID="9092b8f3-d2b4-4df0-8e7d-3232808365a6" eId="hauptteil-1_para-71_abs-2_untergl-1_intro-1_text-1">Von Versorgungsbezügen bleiben ein nach einem Prozentsatz ermittelter, auf einen Höchstbetrag begrenzter Betrag (Versorgungsfreibetrag) und ein Zuschlag zum Versorgungsfreibetrag steuerfrei. Versorgungsbezüge sind
                                 </akn:p>
                                 </akn:intro>
                              <akn:point GUID="26764f22-3d0e-47c5-9830-31a8ca6dfa35" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1">
                                 <akn:num GUID="fa106c10-a7e9-4093-80bb-a061c7bd51e0" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="d88258ea-1997-4940-bf99-39312a8e12dd" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>1.</akn:num>
                                 <akn:list GUID="c121d47b-788e-4003-9ff0-5581d604b91a" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1">
                                    <akn:intro GUID="a0fe69a4-2a37-4d9e-9de6-237bd313b1fc" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1"><akn:p GUID="fa011bbb-c56e-426c-a571-2e3d9e9f2cda" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1_text-1">das Ruhegehalt, Witwen- oder Waisengeld, der Unterhaltsbeitrag oder ein gleichartiger Bezug
                                       </akn:p>
                                    </akn:intro>
                                    <akn:point GUID="b0d6890a-8224-4fc3-b7c6-9790277b1499" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1">
                                       <akn:num GUID="ec3231f1-9d02-4d1e-86b5-68e75664e626" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="a" GUID="5c8a3f40-9a46-4e4b-a385-fa16cc529998" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>a)</akn:num>
                                       <akn:content GUID="a1802bf9-25cc-465f-bd96-7aceae04c366" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1"><akn:p GUID="2e5cea34-e8ce-4f7c-8673-0a36f845f38b" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">Grund beamtenrechtlicher oder entsprechender gesetzlicher Vorschriften,
                                       </akn:p></akn:content>
                                    </akn:point>
                                    <akn:point GUID="7cbb02b7-2053-41e2-88aa-a6aa26a72233" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2">
                                       <akn:num GUID="e1953bdd-0564-4853-8ed0-dac771a181d2" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="b" GUID="7f1e41e0-bb47-4e10-b193-1a98eff460f8" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>b)</akn:num>
                                       <akn:content GUID="a2903657-6784-4eef-961b-f3c53bed78c6" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="c2cd5917-b4ce-449d-8103-9f34fa0d34f7" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">nach beamtenrechtlichen Grundsätzen von Körperschaften, Anstalten oder Stiftungen des öffentlichen Rechts oder öffentlich-rechtlichen Verbänden von Körperschaften
                                       </akn:p></akn:content>
                                    </akn:point>
                                    <akn:wrapUp GUID="0405677d-8718-4564-9dec-b3bf832f04e4" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1"><akn:p GUID="ad110215-c861-4a60-8987-523952f68e21" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1_text-1">oder</akn:p></akn:wrapUp>
                                 </akn:list>
                              </akn:point>
                              <akn:point GUID="accde094-d21b-4a86-a8cd-f9123606c726" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2">
                                 <akn:num GUID="0521715f-1e31-4e38-b8e4-84c4d23e0f0b" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="7da84244-df58-498a-84b9-5860c4d8b338" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>2.</akn:num>
                                 <akn:content GUID="5484bc1b-b351-41f4-93d1-f2256ca61bea" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="e0aeb3f1-50a0-4f60-98d9-910a54143dca" eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                    in anderen Fällen Bezüge und Vorteile aus früheren Dienstleistungen wegen Erreichens einer Altersgrenze, verminderter Erwerbsfähigkeit oder Hinterbliebenenbezüge; Bezüge wegen Erreichens einer Altersgrenze gelten erst dann als Versorgungsbezüge, wenn der Steuerpflichtige das 63. Lebensjahr oder, wenn er schwerbehindert ist, das 60. Lebensjahr vollendet hat.
                                 </akn:p></akn:content>
                              </akn:point>
                           </akn:list>
               </akn:paragraph>
            </akn:article>
         </akn:body>
   </akn:act>
</akn:akomaNtoso>');

-- ZF0
DELETE FROM norms where guid = '5d84cd1d-3575-4a03-bb6c-f17834e392fe';
INSERT INTO norms (guid, eli, xml)
VALUES ('5d84cd1d-3575-4a03-bb6c-f17834e392fe', 'eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
--><?xml-model href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/26e27e3a56996587fe737639fd80e2c4a8fe0311/Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
    <akn:act name="regelungstext">
        <!-- Metadaten-->
        <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
            <akn:identification eId="meta-1_ident-1" GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22"
                                source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761"
                                  value="eli/bund/bgbl-1/2009/s3366/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a90733dd-4780-472d-8371-a3690f2e869a"
                                 value="eli/bund/bgbl-1/2009/s3366"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                                   GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b" name="übergreifende-id"
                                   value="2c1a6ec9-7161-4e2f-8999-91219180d992"></akn:FRBRalias>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811"
                                  date="2009-10-08" name="verkuendungsfassung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                                    GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1"
                                    href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                     GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e" value="de"></akn:FRBRcountry>
                    <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                                    GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5" value="S3366"></akn:FRBRnumber>
                    <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8"
                                  value="bgbl-1"></akn:FRBRname>
                    <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                     GUID="af948830-e615-4dd5-8237-b8c830939f78"
                                     value="regelungstext-1"></akn:FRBRsubtype>
                </akn:FRBRWork>
                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                                  GUID="85e064f4-f56f-4cef-86a4-6801cc71c712"
                                  value="eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                                 GUID="e8f52739-79c1-4e59-9db2-004180c8b29c"
                                 value="eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                                   GUID="c2e78146-833b-4d78-a055-27f05eeaf018" name="aktuelle-version-id"
                                   value="53d31e3c-5c46-4e96-8017-b0db064561a2"></akn:FRBRalias>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
                                   GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c" name="nachfolgende-version-id"
                                   value="5d84cd1d-3575-4a03-bb6c-f17834e392fe"></akn:FRBRalias>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                                    GUID="6aace8d0-001f-4829-9794-79857835ee45"
                                    href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                                  GUID="a6dc32d0-140d-4147-88b2-d64639b91742" date="2024-03-27"
                                  name="verkuendung"></akn:FRBRdate>
                    <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                      GUID="b872861e-0add-4bbb-ba90-a5edb3c40038" language="deu"></akn:FRBRlanguage>
                    <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1"
                                           GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc"
                                           value="1"></akn:FRBRversionNumber>
                </akn:FRBRExpression>
                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                       GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                  GUID="4c881fca-0e36-440e-a5f9-513079ccc77f"
                                  value="eli/bund/bgbl-1/2009/s3366/1/deu/regelungstext-1.xml"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                 GUID="d0491f91-d599-46d4-ba61-c7426b607e4f"
                                 value="eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1.xml"></akn:FRBRuri>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                  GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612" date="2024-03-27"
                                  name="generierung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                    GUID="1d015a20-823e-496e-808d-27c8fec85b51" href="recht.bund.de"></akn:FRBRauthor>
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                    GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67" value="xml"></akn:FRBRformat>
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle eId="meta-1_lebzykl-1" GUID="cef30a63-d738-4712-bac8-50fda44949e2"
                           source="attributsemantik-noch-undefiniert">
                <!-- 1. Originales Gesetz -->
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="65f3910d-4e79-45f8-a24d-44ef7523963a"
                              date="1934-10-16" source="attributsemantik-noch-undefiniert" type="generation"
                              refersTo="ausfertigung"></akn:eventRef>
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c"
                              date="1934-10-16" source="attributsemantik-noch-undefiniert" type="generation"
                              refersTo="inkrafttreten"></akn:eventRef>
                <!-- Neufassung -->
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="85e92fc8-b381-47bb-a476-c35fa2794336"
                              date="2009-10-08" source="attributsemantik-noch-undefiniert" type="amendment"
                              refersTo="neufassung"></akn:eventRef>
                <!-- Letzte Änderung -->
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="85e92fc8-b381-47bb-a476-c35fa2794337"
                              date="2023-12-24" source="attributsemantik-noch-undefiniert" type="amendment"
                              refersTo="inkrafttreten"></akn:eventRef>
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-5" GUID="8dd1686c-7288-4790-b218-961d020c55f9"
                              date="2023-01-01" source="attributsemantik-noch-undefiniert" type="amendment"
                              refersTo="inkrafttreten"></akn:eventRef>
            </akn:lifecycle>
            <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1"
                          GUID="4cc2a228-5c5e-44bb-b19d-e6b145efe69b">
                <akn:passiveModifications eId="meta-1_analysis-1_passivemod-1"
                                          GUID="127e593d-6ce7-4ee1-8c1d-8ab537593a25">
                    <akn:textualMod type="substitution" GUID="de2d69cd-691b-4b61-b1fb-db471d9f52aa"
                                    eId="meta-1_analysis-1_passivemod-1_textualmod-1">
                        <akn:source eId="meta-1_analysis-1_passivemod-1_textualmod-1_source-1"
                                    href="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml"
                                    GUID="c42641b3-e987-4013-b713-52017ca0ca78"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_passivemod-1_textualmod-1_destination-1"
                                         href="#hauptteil-1_para-27_abs-6_inhalt-1_text-1/302-338"
                                         GUID="e99747be-63b9-4729-adf3-b4b4299e188b"></akn:destination>
                        <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2"
                                   eId="meta-1_analysis-1_passivemod-1_textualmod-1_gelzeitnachw-1"
                                   GUID="362f30ad-343c-4b8e-8bb4-993c8a0f5256"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod type="substitution" GUID="a9447c63-fd78-44b9-9426-48ea9e86cf68"
                                    eId="meta-1_analysis-1_passivemod-1_textualmod-2">
                        <akn:source eId="meta-1_analysis-1_passivemod-1_textualmod-2_source-1"
                                    href="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1/hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"
                                    GUID="c509c75e-4813-491a-a973-9b23e2b8be03"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_passivemod-1_textualmod-2_destination-1"
                                         href="#hauptteil-1_para-71_abs-2"
                                         GUID="e1d7e59f-5c59-4834-847d-3dc1dbc26259"></akn:destination>
                        <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2"
                                   eId="meta-1_analysis-1_passivemod-1_textualmod-2_gelzeitnachw-1"
                                   GUID="5d66169e-3af1-453b-8009-b5d62ad16aee"></akn:force>
                    </akn:textualMod>
                </akn:passiveModifications>
            </akn:analysis>
            <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d"
                              source="attributsemantik-noch-undefiniert">
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1"
                                   GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                      GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16" start="#meta-1_lebzykl-1_ereignis-2"
                                      refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2"
                                   GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1"
                                      GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f" start="#meta-1_lebzykl-1_ereignis-3"
                                      refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3"
                                   GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c5">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1"
                                      GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac755" start="#meta-1_lebzykl-1_ereignis-4"
                                      refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4"
                                   GUID="6f15889e-a5f2-41b4-b401-efe6716642be">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1"
                                      GUID="a6cd7c5d-9feb-4eae-9aad-602fd09dc33a" start="#meta-1_lebzykl-1_ereignis-5"
                                      refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
            </akn:temporalData>
            <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
            <akn:proprietary eId="meta-1_proprietary-1" GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7"
                             source="attributsemantik-noch-undefiniert">
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                    <meta:typ>gesetz</meta:typ>
                    <meta:form>stammform</meta:form>
                    <meta:fassung>neufassung</meta:fassung>
                    <meta:art>regelungstext</meta:art>
                    <meta:initiant>nicht-vorhanden</meta:initiant>
                    <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
                    <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
                    <meta:fna>754-28-2</meta:fna>
                    <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
                    <meta:gesta>nicht-vorhanden</meta:gesta>
                    <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
                    <meta:federfuehrung>
                        <meta:federfuehrend ab="1934-10-16" bis="2009-10-07">BMF - Bundesministerium der Finanzen</meta:federfuehrend>
                        <meta:federfuehrend ab="2009-10-08" bis="2022-12-31">BMWSB - Bundesministerium für Wohnen, Stadtentwicklung und Bauwesen</meta:federfuehrend>
                        <meta:federfuehrend ab="2023-01-01" bis="2023-12-23">BMDV - Bundesministerium für Digitales und Verkehr</meta:federfuehrend>
                        <meta:federfuehrend ab="2023-12-24" bis="unbestimmt">BMG - Bundesministerium für Gesundheit</meta:federfuehrend>
                    </meta:federfuehrung>
                </meta:legalDocML.de_metadaten>
                 <!-- Metadaten vom DS definiert -->
                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                   <meta:subtyp>SN</meta:subtyp>
                   <meta:fna start="2009-10-08" end="2022-12-31">111-11-1</meta:fna>
                   <meta:fna start="2023-01-01" end="2023-12-23">222-22-2</meta:fna>
                   <meta:fna start="2023-12-24" end="unbestimmt">333-33-3</meta:fna>
                    <meta:art start="2009-10-08" end="2022-12-31">regelungstext</meta:art>
                    <meta:typ start="2009-10-08" end="2022-12-31">verwaltungsvorschrift</meta:typ>
                    <meta:subtyp start="2009-10-08" end="2022-12-31">Verwaltungsvorschrift</meta:subtyp>
                    <meta:art start="2023-01-01" end="unbestimmt">regelungstext</meta:art>
                    <meta:typ start="2023-01-01" end="unbestimmt">gesetz</meta:typ>
                    <meta:subtyp start="2023-01-01" end="unbestimmt">Rechtsverordnung</meta:subtyp>
                    <meta:artDerNorm start="2009-10-08" end="2022-12-31">SN,ÜN</meta:artDerNorm>
                    <meta:bezeichnungInVorlage start="2009-10-08" end="2022-12-31">Testbezeichnung 1 nach meiner Vorlage</meta:bezeichnungInVorlage>
                    <meta:artDerNorm start="2023-01-01" end="2023-12-23">ÄN,ÜN</meta:artDerNorm>
                    <meta:bezeichnungInVorlage start="2023-01-01" end="2023-12-23">Testbezeichnung 2 nach meiner Vorlage</meta:bezeichnungInVorlage>
                     <meta:artDerNorm start="2023-12-24" end="unbestimmt">SN,ÄN,ÜN</meta:artDerNorm>
                    <meta:bezeichnungInVorlage start="2023-12-24" end="unbestimmt">Testbezeichnung 3 nach meiner Vorlage</meta:bezeichnungInVorlage>
                    <meta:normgeber start="2009-10-08" end="2022-12-31">MV - Land Mecklenburg-Vorpommern</meta:normgeber>
                    <meta:beschliessendesOrgan start="2009-10-08" end="2022-12-31" qualifizierteMehrheit="true">BT - Bundestag</meta:beschliessendesOrgan>
                    <meta:normgeber start="2023-01-01" end="2023-12-23">PR - Preußen</meta:normgeber>
                    <meta:beschliessendesOrgan start="2023-01-01" end="2023-12-23" qualifizierteMehrheit="false">OFD - Oberfinanzdirektion</meta:beschliessendesOrgan>
                    <meta:normgeber start="2023-12-24" end="unbestimmt">EA - Euratom</meta:normgeber>
                    <meta:beschliessendesOrgan start="2023-12-24" end="unbestimmt" qualifizierteMehrheit="true">BMinI - Bundesministerium des Innern</meta:beschliessendesOrgan>
                </meta:legalDocML.de_metadaten_ds>
            </akn:proprietary>
        </akn:meta>
        <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
            <akn:longTitle eId="einleitung-1_doktitel-1" GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
                <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
                    <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1"
                                  GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Einkommensteuergesetz
                    </akn:docTitle>
                    <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1"
                                    GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">
                        <akn:inline refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert"
                                    eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1"
                                    GUID="3d5ecf29-792c-4854-99e4-0cf18b5c0c9c">EStG
                        </akn:inline>
                    </akn:shortTitle>
                </akn:p>
            </akn:longTitle>
        </akn:preface>
        <!-- Hauptteil -->
        <akn:body eId="hauptteil-1" GUID="7b7416d7-35a0-41cf-902f-583b9cf51a26">
            <akn:article eId="hauptteil-1_para-1" GUID="e9a8d5e4-77b8-447f-9227-63f687a31f8f"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="049c11bd-5ed2-4423-a7c3-54dce98c414d">
                    <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1"
                                GUID="4aca37a6-2df2-41e0-9f13-f837150b3005" name="1"></akn:marker>§1
                </akn:num>
                <akn:paragraph GUID="abd48a54-ccc6-4cb9-8627-9d7092b5263f" eId="hauptteil-1_para-1_abs-1">
                    <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="375c9de0-2d35-447b-b9ce-a6e0243b81a2">
                        <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="4a047cd5-a44b-4b25-bd6b-c3bcf95d76e2" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="a1609cb8-1045-4e45-a6f9-c7bdf4187249" eId="hauptteil-1_para-1_abs-1_inhalt-1">
                        <akn:p GUID="74b7302d-a4fc-4f69-a362-97a3ed8c4e77"
                               eId="hauptteil-1_para-1_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-2" GUID="4b09bb18-3bda-4603-a0a3-b62083262a8a"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="42e575d9-f64a-4ad5-85d1-47f134576923">
                    <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1"
                                GUID="4bd6f909-053c-4461-a162-a4cb058db29d" name="1"></akn:marker>§1
                </akn:num>
                <akn:paragraph GUID="a0271610-a340-4e5c-b592-4b60ba92a657" eId="hauptteil-1_para-2_abs-1">
                    <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="ff409ec8-0df2-40cf-8605-d7cca3052297">
                        <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="a3c3fcbd-f9a2-425c-b7ce-a4fc13f63483" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="cddf665c-ac79-4e28-bc12-7adb861a840c" eId="hauptteil-1_para-2_abs-1_inhalt-1">
                        <akn:p GUID="800c9235-db46-493d-a64c-c1b5de499927"
                               eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-3" GUID="dcf7124f-ae5b-4a0e-9bc9-d2df8bed7016"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="0ca92be9-2775-4848-a942-0d96397b6a3f">
                    <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1"
                                GUID="a5bed3be-f422-4d9a-8c31-0a6923fcaf20" name="2"></akn:marker>§1a
                </akn:num>
                <akn:paragraph GUID="519025c1-72da-42a2-87a9-056c27ca72ae" eId="hauptteil-1_para-3_abs-1">
                    <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="3811c99a-49e8-4672-8274-01296a218392">
                        <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="9f3c89f3-09b3-48ee-9090-f9e0874bedcb" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="6f4b3b85-3c26-44e6-afde-1b8cf2dbbd75" eId="hauptteil-1_para-3_abs-1_inhalt-1">
                        <akn:p GUID="55cc5a1c-cee3-4972-9fb8-e3082f3bc6e6"
                               eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-4" GUID="11f31a4b-36f5-4163-b5a0-528087d9072d"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="e3ee7c20-d068-424e-acd2-4444c3a59e43">
                    <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1"
                                GUID="9abcd42d-9927-4cd3-ace5-de9899d4b724" name="3"></akn:marker>§2
                </akn:num>
                <akn:paragraph GUID="b0d3737b-4055-4c02-a758-cda1a41542cc" eId="hauptteil-1_para-4_abs-1">
                    <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="2524812c-b34c-42fc-ab21-899c83aa3986">
                        <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="c0e9858b-8009-4340-ad82-5ea4e25d89bf" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="c490f063-95f1-4617-84ec-3889a6a44965" eId="hauptteil-1_para-4_abs-1_inhalt-1">
                        <akn:p GUID="5694f377-df54-4e19-88ef-93175d6eb4a7"
                               eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-5" GUID="e253b2d4-f268-46fd-a8ff-79d3ee8d6184"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-5_bezeichnung-1" GUID="4161e762-5411-4b8a-86d5-32a5c3880b12">
                    <akn:marker eId="hauptteil-1_para-5_bezeichnung-1_zaehlbez-1"
                                GUID="8295e6c3-b181-4943-97f9-429e743c7677" name="4"></akn:marker>§2a
                </akn:num>
                <akn:paragraph GUID="4988d145-1105-4008-ae2e-dfc8ff0a7536" eId="hauptteil-1_para-5_abs-1">
                    <akn:num eId="hauptteil-1_para-5_abs-1_bezeichnung-1" GUID="1284cd07-2751-4f40-a1da-4cba63d4b375">
                        <akn:marker eId="hauptteil-1_para-5_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="9b7a439e-73e5-452b-81d0-8abc727c2f86" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="966d355b-246e-4d21-8006-0662496fc52f" eId="hauptteil-1_para-5_abs-1_inhalt-1">
                        <akn:p GUID="3c6ab1cd-14fd-4db8-ac00-964e5102b02f"
                               eId="hauptteil-1_para-5_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-6" GUID="5d51449c-88e9-4411-ab65-e754e5eedf4d"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-6_bezeichnung-1" GUID="243f406d-cd7d-4e7a-8e99-3277133b2a58">
                    <akn:marker eId="hauptteil-1_para-6_bezeichnung-1_zaehlbez-1"
                                GUID="5ff0b7f4-2842-46a5-a8ff-a53f732c56ec" name="1"></akn:marker>§3
                </akn:num>
                <akn:paragraph GUID="d0e332d8-c3b2-47db-8904-6d03452ca2b8" eId="hauptteil-1_para-6_abs-1">
                    <akn:num eId="hauptteil-1_para-6_abs-1_bezeichnung-1" GUID="adfab363-827a-4a34-be0e-c7584ee23dbe">
                        <akn:marker eId="hauptteil-1_para-6_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="92af5bbd-4e03-4858-bcf6-bf061ac9e30f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="f2d23018-7d93-40d7-a5df-c981db4a4534" eId="hauptteil-1_para-6_abs-1_inhalt-1">
                        <akn:p GUID="029568a7-2147-487a-9ad4-5df622b67232"
                               eId="hauptteil-1_para-6_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-7" GUID="9f671cd2-bf8c-4ca3-adc3-dd9cd3cf5478"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-7_bezeichnung-1" GUID="cb798a77-b613-4e93-b9b6-24ac18a3810d">
                    <akn:marker eId="hauptteil-1_para-7_bezeichnung-1_zaehlbez-1"
                                GUID="abfd8fd5-c2be-4cfe-9805-b7a72954f985" name="3a"></akn:marker>§3a
                </akn:num>
                <akn:paragraph GUID="c7032ec4-11ce-4473-886c-9340c19bce13" eId="hauptteil-1_para-7_abs-1">
                    <akn:num eId="hauptteil-1_para-7_abs-1_bezeichnung-1" GUID="31464e5b-4298-4fd7-8c2d-4963e5b154e8">
                        <akn:marker eId="hauptteil-1_para-7_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="1ed6bc06-ec45-4ac5-ac3f-01a2c6e8dc1f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="b4a15eb8-63cc-4a0b-aa74-38a60b2847fb" eId="hauptteil-1_para-7_abs-1_inhalt-1">
                        <akn:p GUID="5fcffa08-6a7a-4f13-8103-1c110fe49ec9"
                               eId="hauptteil-1_para-7_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-8" GUID="9fa71e02-b0b6-429f-96fc-34aad39a0f21"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-8_bezeichnung-1" GUID="7887a4de-740b-4ed7-ab4b-61dea64d4acd">
                    <akn:marker eId="hauptteil-1_para-8_bezeichnung-1_zaehlbez-1"
                                GUID="92ed7a54-d67c-4453-8d03-5263c39f496e" name="1"></akn:marker>§3b
                </akn:num>
                <akn:paragraph GUID="68e04f75-528e-4e1c-bbaf-803139717cda" eId="hauptteil-1_para-8_abs-1">
                    <akn:num eId="hauptteil-1_para-8_abs-1_bezeichnung-1" GUID="460de7b9-d739-4324-bde9-aeccce7d59d3">
                        <akn:marker eId="hauptteil-1_para-8_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="48a14c5d-4960-4f94-88ab-20c901f4201e" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="044dfd14-cae9-48c2-964b-7da039d9d1ca" eId="hauptteil-1_para-8_abs-1_inhalt-1">
                        <akn:p GUID="d415fb58-cdc0-4cd6-a293-4e8b980bae54"
                               eId="hauptteil-1_para-8_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-9" GUID="ae491455-e299-409a-928b-b47cbb04040b"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-9_bezeichnung-1" GUID="4d53114a-f191-4600-8f28-5dba72cdd003">
                    <akn:marker eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1"
                                GUID="b94918cc-dca4-408b-b4b2-c6ad3a80ea07" name="1"></akn:marker>§3c
                </akn:num>
                <akn:paragraph GUID="6ef7bb31-8d25-43ca-9c4c-0ccff9072392" eId="hauptteil-1_para-9_abs-1">
                    <akn:num eId="hauptteil-1_para-9_abs-1_bezeichnung-1" GUID="3340cdfa-b701-49c5-9425-6e0d60e028a7">
                        <akn:marker eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="53e0f9b8-680d-417d-ad3b-e9fbe563017f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="0488e21a-2f30-41fe-981f-d7bc4c5f021b" eId="hauptteil-1_para-9_abs-1_inhalt-1">
                        <akn:p GUID="fb2277f3-115b-4194-ba46-5bf2c1d075f9"
                               eId="hauptteil-1_para-9_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-10" GUID="ca31c6f1-432d-4411-b3ca-7f771632ed35"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-10_bezeichnung-1" GUID="41ccf863-0dac-459c-8794-31da0bc36604">
                    <akn:marker eId="hauptteil-1_para-10_bezeichnung-1_zaehlbez-1"
                                GUID="1300cb06-274f-4db0-b33c-ccc8cf825712" name="1"></akn:marker>§4
                </akn:num>
                <akn:paragraph GUID="cb830efc-607a-4ebf-bc2c-567428ac75b1" eId="hauptteil-1_para-10_abs-1">
                    <akn:num eId="hauptteil-1_para-10_abs-1_bezeichnung-1" GUID="85b961f5-d462-4a75-8f33-f2662b844e9a">
                        <akn:marker eId="hauptteil-1_para-10_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="3e33ba0a-5cd0-4779-8494-284fba328681" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="b7b65065-40eb-43f3-a524-8cecbf4a2424" eId="hauptteil-1_para-10_abs-1_inhalt-1">
                        <akn:p GUID="8140032e-77b5-4890-b39e-63a66147a1fc"
                               eId="hauptteil-1_para-10_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-11" GUID="7348fc55-e767-4d23-ab05-31a4e7ae470c"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-11_bezeichnung-1" GUID="08753f4f-ec16-4cbc-91d8-9057cb06ba4c">
                    <akn:marker eId="hauptteil-1_para-11_bezeichnung-1_zaehlbez-1"
                                GUID="2d428604-d7e6-441d-b431-00d4bfbd0bcb" name="1"></akn:marker>§4a
                </akn:num>
                <akn:paragraph GUID="429c9203-e0e0-469f-8eed-760997f38cdb" eId="hauptteil-1_para-11_abs-1">
                    <akn:num eId="hauptteil-1_para-11_abs-1_bezeichnung-1" GUID="0bb680a0-49fb-4ba6-b7ee-24da6d3024e1">
                        <akn:marker eId="hauptteil-1_para-11_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="48e5a333-550e-430f-9e0a-46ddadb6eace" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="2189b6d1-244f-4158-9858-6dd967636c6a" eId="hauptteil-1_para-11_abs-1_inhalt-1">
                        <akn:p GUID="0248aafc-b33f-44c3-b776-777086e865b8"
                               eId="hauptteil-1_para-11_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-12" GUID="8ba4d8a5-9140-44fb-b7eb-922bde2f77d0"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-12_bezeichnung-1" GUID="c918ccd0-08ab-48e7-988b-f18f09a6ec40">
                    <akn:marker eId="hauptteil-1_para-12_bezeichnung-1_zaehlbez-1"
                                GUID="b2f7f100-8fb9-4e95-b9b9-77657f408b70" name="1"></akn:marker>§4b
                </akn:num>
                <akn:paragraph GUID="bd6b33a9-84a8-4a2c-9dd5-0cce26664599" eId="hauptteil-1_para-12_abs-1">
                    <akn:num eId="hauptteil-1_para-12_abs-1_bezeichnung-1" GUID="7d3809ab-2691-4495-8442-d00a982fb46b">
                        <akn:marker eId="hauptteil-1_para-12_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="498b04fb-dc70-40de-b20b-b35fe8a71bc7" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="79373c7f-82b0-48ff-8423-237333bdacc9" eId="hauptteil-1_para-12_abs-1_inhalt-1">
                        <akn:p GUID="88ee4693-5a25-4b4f-b959-0c1455d7ff53"
                               eId="hauptteil-1_para-12_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-13" GUID="9a25387c-d6af-4ac3-bb10-2ddc1adecedc"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-13_bezeichnung-1" GUID="6358cd19-b9f5-473e-9192-eac12373d000">
                    <akn:marker eId="hauptteil-1_para-13_bezeichnung-1_zaehlbez-1"
                                GUID="8af374b2-aa38-42e8-bfbb-295eca208489" name="1"></akn:marker>§4c
                </akn:num>
                <akn:paragraph GUID="e7d210d4-edb1-47cc-a074-2b394bc04270" eId="hauptteil-1_para-13_abs-1">
                    <akn:num eId="hauptteil-1_para-13_abs-1_bezeichnung-1" GUID="632066c0-1f65-4319-ad6a-d3b14e110e6b">
                        <akn:marker eId="hauptteil-1_para-13_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="6c30fea1-0d95-4b6c-b0f6-0094011bf066" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="cb55652a-83d1-4bd1-abba-bb636565c559" eId="hauptteil-1_para-13_abs-1_inhalt-1">
                        <akn:p GUID="8ec8d90b-ea2f-4218-89b5-37ba0ea20218"
                               eId="hauptteil-1_para-13_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-14" GUID="17e8f2c2-9298-426d-8a0a-5852124239c0"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-14_bezeichnung-1" GUID="1bbc3481-c35f-45c3-b397-c8cb6d6b2c8f">
                    <akn:marker eId="hauptteil-1_para-14_bezeichnung-1_zaehlbez-1"
                                GUID="a9be80c2-88ac-47c2-9e77-8703a2c80ecc" name="1"></akn:marker>§4d
                </akn:num>
                <akn:paragraph GUID="b8596272-3803-49de-a7f0-1d002a1efd61" eId="hauptteil-1_para-14_abs-1">
                    <akn:num eId="hauptteil-1_para-14_abs-1_bezeichnung-1" GUID="7b771214-c3a3-4b56-b3bb-44c3d3b9dd31">
                        <akn:marker eId="hauptteil-1_para-14_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="228d4678-0678-4b0d-a18d-a06747a52cd9" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="34c7d9ef-cc24-4706-a784-28269373326d" eId="hauptteil-1_para-14_abs-1_inhalt-1">
                        <akn:p GUID="9d535547-49a5-4b09-9475-bead23a521e9"
                               eId="hauptteil-1_para-14_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-15" GUID="adafbf82-2f5a-42c3-82c9-2339f0a382cd"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-15_bezeichnung-1" GUID="32dfd6b3-4c8b-4dde-861b-07821708c297">
                    <akn:marker eId="hauptteil-1_para-15_bezeichnung-1_zaehlbez-1"
                                GUID="0e4efa11-3d48-4582-a4c6-a0cb2b0941f7" name="1"></akn:marker>§4e
                </akn:num>
                <akn:paragraph GUID="44e2537a-a248-4dfe-9d3a-3c1c975deeca" eId="hauptteil-1_para-15_abs-1">
                    <akn:num eId="hauptteil-1_para-15_abs-1_bezeichnung-1" GUID="e5dec8c4-c0ea-4e86-9607-40663f4e7be4">
                        <akn:marker eId="hauptteil-1_para-15_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="3979da44-89fc-458e-b436-fb3efb1f9e65" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="c9f4e348-d2fb-4415-8bfa-8d292a9ce5c6" eId="hauptteil-1_para-15_abs-1_inhalt-1">
                        <akn:p GUID="1d748807-46a7-41af-9cc4-1e693921631b"
                               eId="hauptteil-1_para-15_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-16" GUID="e92b3ffb-be5e-447d-ac59-48815e56c124"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-16_bezeichnung-1" GUID="4e87a3d9-9280-42b7-9d5e-0b4f0ee9170e">
                    <akn:marker eId="hauptteil-1_para-16_bezeichnung-1_zaehlbez-1"
                                GUID="efec926d-c990-4f6e-9936-fe2b4022ee21" name="1"></akn:marker>§4f
                </akn:num>
                <akn:paragraph GUID="645cb034-5847-4c08-a795-d7b396db503c" eId="hauptteil-1_para-16_abs-1">
                    <akn:num eId="hauptteil-1_para-16_abs-1_bezeichnung-1" GUID="476a88f0-edf6-4778-ab59-7a1c659fb335">
                        <akn:marker eId="hauptteil-1_para-16_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="3d2ff31c-7683-4589-9cad-d2ec55bf36d7" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="49bfc624-9922-4049-bac0-88af08b2cc98" eId="hauptteil-1_para-16_abs-1_inhalt-1">
                        <akn:p GUID="528cebe9-ece1-495a-a811-71f325b9c0a7"
                               eId="hauptteil-1_para-16_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-17" GUID="ffcb8103-08cb-4ab9-895e-2cc19974d4bc"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-17_bezeichnung-1" GUID="bbc37521-f05f-4df9-8496-7b71863460cc">
                    <akn:marker eId="hauptteil-1_para-17_bezeichnung-1_zaehlbez-1"
                                GUID="a60b907e-18ff-4aea-aa6e-b68f9909eeba" name="1"></akn:marker>§4g
                </akn:num>
                <akn:paragraph GUID="4f78186c-f8db-4f43-ab36-437d536e2c92" eId="hauptteil-1_para-17_abs-1">
                    <akn:num eId="hauptteil-1_para-17_abs-1_bezeichnung-1" GUID="ccf50628-68c5-4a0c-ac55-8f6024412b7e">
                        <akn:marker eId="hauptteil-1_para-17_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="369e146e-6b73-4806-9f4a-cd7cd574a8ac" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="4b3a87be-ddc0-460c-ba81-00e274b4a18a" eId="hauptteil-1_para-17_abs-1_inhalt-1">
                        <akn:p GUID="749155b9-8125-4755-a8ae-3eda62e43865"
                               eId="hauptteil-1_para-17_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-18" GUID="91883758-dd57-41ca-887c-76054b2480f6"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-18_bezeichnung-1" GUID="32109de1-9a81-43ce-b167-210b45fde026">
                    <akn:marker eId="hauptteil-1_para-18_bezeichnung-1_zaehlbez-1"
                                GUID="6523375c-5f97-40f8-b879-0eb0dc056d8e" name="1"></akn:marker>§4h
                </akn:num>
                <akn:paragraph GUID="b2f41e83-2550-4447-a3c5-8ad7670ad7b7" eId="hauptteil-1_para-18_abs-1">
                    <akn:num eId="hauptteil-1_para-18_abs-1_bezeichnung-1" GUID="32628015-7b68-4669-879c-7401a08a179a">
                        <akn:marker eId="hauptteil-1_para-18_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="2f1380a5-2921-4cc7-96dc-d4d3360e5842" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="df3f282b-6ae3-420a-947f-d59f5a701934" eId="hauptteil-1_para-18_abs-1_inhalt-1">
                        <akn:p GUID="e435d25d-2dd9-4197-8773-3cb03ade58f2"
                               eId="hauptteil-1_para-18_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-19" GUID="49482d93-6969-4b41-9646-e38843f35c84"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-19_bezeichnung-1" GUID="7fd28ebf-fb70-4939-b9be-474a44d48322">
                    <akn:marker eId="hauptteil-1_para-19_bezeichnung-1_zaehlbez-1"
                                GUID="d50d3c38-9bf8-4c29-adcf-5beb49fb8900" name="1"></akn:marker>§4i
                </akn:num>
                <akn:paragraph GUID="8c567484-3c5c-479f-be0c-ced07e3eb5fb" eId="hauptteil-1_para-19_abs-1">
                    <akn:num eId="hauptteil-1_para-19_abs-1_bezeichnung-1" GUID="7f1e1e8f-ff99-4206-82bf-b87134eda77a">
                        <akn:marker eId="hauptteil-1_para-19_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="e077a48c-173a-4d2e-95ee-325873c3e544" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="d98913b8-a9c3-427c-8f65-a7a4afa50455" eId="hauptteil-1_para-19_abs-1_inhalt-1">
                        <akn:p GUID="a68d7e61-e06c-41f3-9e33-0264cbc3810f"
                               eId="hauptteil-1_para-19_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-20" GUID="4d3efe94-417b-4251-a63b-73cf014ffd98"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="89416443-6452-4cfb-a6c9-16cbb46d6117">
                    <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1"
                                GUID="e4bf5787-a6be-415d-8825-6e44f2f6f0d6" name="1"></akn:marker>§4j
                </akn:num>
                <akn:paragraph GUID="f941ca6a-22a9-4bde-90ac-7ff187ca53bb" eId="hauptteil-1_para-20_abs-1">
                    <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="d782c8e4-b40b-45ee-81da-e853f80af1d7">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="b1a15b8f-9b6a-4bd5-9cf9-8a6daec6794a" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="e4726b7e-a690-4490-a1b9-93c02cbaa49a" eId="hauptteil-1_para-20_abs-1_inhalt-1">
                        <akn:p GUID="9a373fe2-417e-4872-a112-0b6b6cc1b889"
                               eId="hauptteil-1_para-20_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-21" GUID="b55b4921-23af-4f55-ad3e-8cc9f8c2ff83"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-21_bezeichnung-1" GUID="0619603b-18e4-42ee-9c99-91eae90469c3">
                    <akn:marker eId="hauptteil-1_para-21_bezeichnung-1_zaehlbez-1"
                                GUID="c47fc7cf-245c-47bf-b313-5c48051cc3c4" name="1"></akn:marker>§4k
                </akn:num>
                <akn:paragraph GUID="a720dea6-624a-49d5-aa8f-0664dff9cf97" eId="hauptteil-1_para-21_abs-1">
                    <akn:num eId="hauptteil-1_para-21_abs-1_bezeichnung-1" GUID="cff64b2d-fc9d-40df-95c4-aa2f9dc10284">
                        <akn:marker eId="hauptteil-1_para-21_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="ddd318ee-b0b4-43f1-af67-fb55ada83ff5" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="17a4585c-dcce-4a91-85d4-c35293c60ae6" eId="hauptteil-1_para-21_abs-1_inhalt-1">
                        <akn:p GUID="fa0c022f-1d67-4792-a34a-54fa0b417c1e"
                               eId="hauptteil-1_para-21_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-22" GUID="fe149939-6ef4-42d2-b8c9-4ba28a842406"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-22_bezeichnung-1" GUID="8eeb97eb-a22c-4abf-b3fb-e499f4d372a0">
                    <akn:marker eId="hauptteil-1_para-22_bezeichnung-1_zaehlbez-1"
                                GUID="8b80eb21-624e-4f78-aaa0-fa75566c276c" name="1"></akn:marker>§5
                </akn:num>
                <akn:paragraph GUID="1a286587-87d7-4f01-96fb-9f3aef04553c" eId="hauptteil-1_para-22_abs-1">
                    <akn:num eId="hauptteil-1_para-22_abs-1_bezeichnung-1" GUID="11143729-dab9-4626-aff7-0b39071369d8">
                        <akn:marker eId="hauptteil-1_para-22_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="2797747d-dcf2-4f11-85f1-aa1d3040b4a6" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="fc68edff-d4e5-41d9-ae24-6afb84b1162e" eId="hauptteil-1_para-22_abs-1_inhalt-1">
                        <akn:p GUID="0ea02c00-8977-4dd3-9093-b7909037dc8e"
                               eId="hauptteil-1_para-22_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-23" GUID="6f9ae82c-e11a-42ec-9c79-c6643b5030ad"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-23_bezeichnung-1" GUID="af736792-d39f-43b1-9750-40a568247bb4">
                    <akn:marker eId="hauptteil-1_para-23_bezeichnung-1_zaehlbez-1"
                                GUID="e2f1b21d-aec2-457e-bac2-098f2050965f" name="1"></akn:marker>§5a
                </akn:num>
                <akn:paragraph GUID="228220d1-14df-49d2-8f6d-2408da4bdf6f" eId="hauptteil-1_para-23_abs-1">
                    <akn:num eId="hauptteil-1_para-23_abs-1_bezeichnung-1" GUID="6cbb9061-752e-4e89-9b65-4bc8b163aaba">
                        <akn:marker eId="hauptteil-1_para-23_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="551f7f5e-7211-45a7-bd06-80784c010cd0" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="9794bd61-40ce-4c4f-8224-2e1998fc2fbb" eId="hauptteil-1_para-23_abs-1_inhalt-1">
                        <akn:p GUID="da2878c4-e1ab-4704-afd3-a6a765db86f3"
                               eId="hauptteil-1_para-23_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-24" GUID="c7570a10-24ba-4556-9330-d58b5ea7e6a6"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-24_bezeichnung-1" GUID="d71ce5b3-1737-44a7-b34f-f9d11ccf8778">
                    <akn:marker eId="hauptteil-1_para-24_bezeichnung-1_zaehlbez-1"
                                GUID="a0fd6927-e1fd-4c35-acbc-b9e2dd35977b" name="1"></akn:marker>§5b
                </akn:num>
                <akn:paragraph GUID="27bbb686-45a4-430c-aa90-310298267769" eId="hauptteil-1_para-24_abs-1">
                    <akn:num eId="hauptteil-1_para-24_abs-1_bezeichnung-1" GUID="6c6dd7bf-2965-4d05-b668-bc73c76980c2">
                        <akn:marker eId="hauptteil-1_para-24_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="16a1c40f-c7e5-426a-b376-6cbce96796c3" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="c74d084f-c04a-4499-a4c1-f0aaf4d0421d" eId="hauptteil-1_para-24_abs-1_inhalt-1">
                        <akn:p GUID="6f7e184b-a27f-407f-9f0f-5f0240857533"
                               eId="hauptteil-1_para-24_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-25" GUID="28ca0cbd-5c5e-494e-a300-45f9d92f9922"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-25_bezeichnung-1" GUID="443e6a57-1e65-4cd7-a89f-c947e6d68832">
                    <akn:marker eId="hauptteil-1_para-25_bezeichnung-1_zaehlbez-1"
                                GUID="f9e3e582-c96e-436c-ae85-d4ebc7e11145" name="1"></akn:marker>§6
                </akn:num>
                <akn:paragraph GUID="899c959d-83f2-4134-b0ba-20328dcc16fb" eId="hauptteil-1_para-25_abs-1">
                    <akn:num eId="hauptteil-1_para-25_abs-1_bezeichnung-1" GUID="c6322471-caec-4888-af78-84e1f11ab3c6">
                        <akn:marker eId="hauptteil-1_para-25_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="d0840ddc-f170-4aa6-b6ad-82c4f9cba765" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="d32da15e-f80f-4336-af14-170ee79b407d" eId="hauptteil-1_para-25_abs-1_inhalt-1">
                        <akn:p GUID="1b56777b-a99f-4e99-b8b5-eb433b608f08"
                               eId="hauptteil-1_para-25_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-26" GUID="fb0dbfa8-c9f4-4072-abac-d5ae46dc4ffa"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-26_bezeichnung-1" GUID="3413c785-2aac-4ec8-ac64-df43b167bd6a">
                    <akn:marker eId="hauptteil-1_para-26_bezeichnung-1_zaehlbez-1"
                                GUID="fe8ff656-3fbc-4923-adef-deeff1994c46" name="6a"></akn:marker>§6a
                </akn:num>
                <akn:paragraph GUID="56fac6cb-a2f5-47a8-b70a-db2400c300d3" eId="hauptteil-1_para-26_abs-1">
                    <akn:num eId="hauptteil-1_para-26_abs-1_bezeichnung-1" GUID="34da4ee7-b9cd-442a-9da8-27519427c2b7">
                        <akn:marker eId="hauptteil-1_para-26_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="fd432f5e-fe3e-4e93-8978-7afe6c45676f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="485daf4e-2cb4-4080-9f06-f42b4b138fe2" eId="hauptteil-1_para-26_abs-1_inhalt-1">
                        <akn:p GUID="110761f1-afce-4817-aac6-20666db7e130"
                               eId="hauptteil-1_para-26_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>

            <!-- §6b -->
            <akn:article eId="hauptteil-1_para-27" GUID="17e9c25e-251d-48a7-8e74-3f0bb4b93b6b"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-27_bezeichnung-1" GUID="24f5f556-e005-4d10-8d7e-d92f141780d7">
                    <akn:marker eId="hauptteil-1_para-27_bezeichnung-1_zaehlbez-1"
                                GUID="6fa27ec6-e4c2-476a-8729-85e16b3105d1" name="1"></akn:marker>§6b
                </akn:num>
                <akn:heading eId="hauptteil-1_para-27_überschrift-1" GUID="31b97e2a-1872-4a52-aba1-d075bae3c0d6">
                    Übertragung stiller Reserven bei der Veräußerung bestimmter Anlagegüter
                </akn:heading>
                <!-- Absatz 1 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-1" GUID="1106c825-938a-4763-aaa4-0a81b658528b">
                    <akn:num eId="hauptteil-1_para-27_abs-1_bezeichnung-1" GUID="df7997ae-03fc-4073-8e7b-d6885b62dbbd">
                        <akn:marker eId="hauptteil-1_para-27_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="f3536fd7-6d90-419b-a20e-e015d08470ea" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="8c1e9b59-fc85-45ae-834b-cd6b594ce514" eId="hauptteil-1_para-27_abs-1_inhalt-1">
                        <akn:p GUID="29acbe3f-1605-4736-97b4-4bbf2c00dbc6"
                               eId="hauptteil-1_para-27_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!-- Absatz 2 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-2" GUID="f889003b-0740-444a-b6a3-fb45635b32ed">
                    <akn:num eId="hauptteil-1_para-27_abs-2_bezeichnung-1" GUID="e12b64e2-c3a2-4254-b75b-6c695f3f7568">
                        <akn:marker eId="hauptteil-1_para-27_abs-2_bezeichnung-1_zaehlbez-1"
                                    GUID="af5703ff-006b-4b81-a28a-7d0ba4d106dd" name="2"></akn:marker>(2)
                    </akn:num>
                    <akn:content GUID="ba4d6792-d900-4d6e-8d80-8dde96cdf276" eId="hauptteil-1_para-27_abs-2_inhalt-1">
                        <akn:p GUID="6cfdd68b-06f5-4055-8f1b-6ecb31f9bc67"
                               eId="hauptteil-1_para-27_abs-2_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!-- Absatz 3 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-3" GUID="982f7be1-2a0f-4a76-bc30-4522746e908c">
                    <akn:num eId="hauptteil-1_para-27_abs-3_bezeichnung-1" GUID="4da41dcf-d101-44a8-9c1f-580cefb69d69">
                        <akn:marker eId="hauptteil-1_para-27_abs-3_bezeichnung-1_zaehlbez-1"
                                    GUID="076ca318-1d44-450e-9804-f458ffb43715" name="3"></akn:marker>(3)
                    </akn:num>
                    <akn:content GUID="abc34096-ae69-4ec4-a904-c4ddcc504401" eId="hauptteil-1_para-27_abs-3_inhalt-1">
                        <akn:p GUID="491ea8dd-a734-4001-8558-7a70b632c037"
                               eId="hauptteil-1_para-27_abs-3_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!-- Absatz 4 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-4" GUID="b7c29666-0b97-48cd-961d-d9a0d02c887a">
                    <akn:num eId="hauptteil-1_para-27_abs-4_bezeichnung-1" GUID="84ab35ff-8f39-4436-97d0-2c065ea1db0f">
                        <akn:marker eId="hauptteil-1_para-27_abs-4_bezeichnung-1_zaehlbez-1"
                                    GUID="f2cc7079-5f14-4822-90d3-54523ae0ba6f" name="4"></akn:marker>(4)
                    </akn:num>
                    <akn:content GUID="23298ae2-6be8-4456-a2f1-5d33a87221f1" eId="hauptteil-1_para-27_abs-4_inhalt-1">
                        <akn:p GUID="473b61da-0d84-4143-bfd3-2cce6da64254"
                               eId="hauptteil-1_para-27_abs-4_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!-- Absatz 5 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-5" GUID="6fcbce33-7bb2-4bc5-b081-9c2aebcfe466">
                    <akn:num eId="hauptteil-1_para-27_abs-5_bezeichnung-1" GUID="01a7810b-792b-4a60-a9e0-c592993dbc3e">
                        <akn:marker eId="hauptteil-1_para-27_abs-5_bezeichnung-1_zaehlbez-1"
                                    GUID="ca0a2ee9-02c8-4493-9ad7-39ed1a8a7bac" name="5"></akn:marker>(5)
                    </akn:num>
                    <akn:content GUID="8cd7c2d2-796f-47e7-9db5-f29ef5a6d6cb" eId="hauptteil-1_para-27_abs-5_inhalt-1">
                        <akn:p GUID="b3de9223-4577-4c1d-8d12-3261004910b3"
                               eId="hauptteil-1_para-27_abs-5_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!-- Absatz 6 -->
                <akn:paragraph eId="hauptteil-1_para-27_abs-6" GUID="58ea2242-386a-42b9-8f23-eaa1678159f1">
                    <akn:num eId="hauptteil-1_para-27_abs-6_bezeichnung-1" GUID="c38a41af-4911-4b1a-9404-929912af983e">
                        <akn:marker eId="hauptteil-1_para-27_abs-6_bezeichnung-1_zaehlbez-1"
                                    GUID="00aa91c0-6457-4200-bce9-a6350377c58f" name="6"></akn:marker>(6)
                    </akn:num>
                    <akn:content eId="hauptteil-1_para-27_abs-6_inhalt-1" GUID="88b5a47f-40ad-485a-9745-5caa81f93e44">
                        <akn:p GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"
                               eId="hauptteil-1_para-27_abs-6_inhalt-1_text-1">Ist ein Betrag nach Absatz 1 oder 3 abgezogen worden, so tritt für die Absetzungen für Abnutzung oder Substanzverringerung oder in den Fällen des § 6 Absatz 2 und Absatz 2a im Wirtschaftsjahr des Abzugs der verbleibende Betrag an die Stelle der Anschaffungs- oder Herstellungskosten. In den Fällen des § 7 Absatz 4 Satz 1, Absatz 5 und 5a sind die um den Abzugsbetrag nach Absatz 1 oder 3 geminderten Anschaffungs- oder Herstellungskosten maßgebend.</akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>

            <akn:article eId="hauptteil-1_para-28" GUID="2a63a350-9d16-4587-b1f5-4da210fa3bec"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-28_bezeichnung-1" GUID="449e293a-3038-4bc0-bd7b-4f7f1353df93">
                    <akn:marker eId="hauptteil-1_para-28_bezeichnung-1_zaehlbez-1"
                                GUID="40e1d025-a277-41a2-af1d-76427c5d99d0" name="1"></akn:marker>§6c
                </akn:num>
                <akn:paragraph GUID="1418ccdb-59ff-482d-9a90-129ea202e563" eId="hauptteil-1_para-28_abs-1">
                    <akn:num eId="hauptteil-1_para-28_abs-1_bezeichnung-1" GUID="39ae2bbf-3709-4ae4-a749-b09404e49006">
                        <akn:marker eId="hauptteil-1_para-28_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="89e39c65-4119-49b8-897d-9d1f663a8719" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="fde1aced-d2a7-409b-9a5b-cc97cd8d6e49" eId="hauptteil-1_para-28_abs-1_inhalt-1">
                        <akn:p GUID="f715da45-d8c8-4ab7-bf00-26cc8ad2dd08"
                               eId="hauptteil-1_para-28_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-29" GUID="504c662d-9018-4f1a-ad4e-84cae20bd885"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-29_bezeichnung-1" GUID="cf2c337c-83eb-409e-b310-1bebd1e3ad0c">
                    <akn:marker eId="hauptteil-1_para-29_bezeichnung-1_zaehlbez-1"
                                GUID="99f1d666-05a9-4b48-97b6-b0e4de93c5b8" name="1"></akn:marker>§6d
                </akn:num>
                <akn:paragraph GUID="3983bc04-5941-4605-a90e-5caeff00eea9" eId="hauptteil-1_para-29_abs-1">
                    <akn:num eId="hauptteil-1_para-29_abs-1_bezeichnung-1" GUID="f093656e-d154-4326-bd55-2c14e9b32b2b">
                        <akn:marker eId="hauptteil-1_para-29_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="3f07db52-d330-4885-9ae4-f58ebbd48c8f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="bc6a27ea-6c14-481f-8e6d-bae1a86aac5e" eId="hauptteil-1_para-29_abs-1_inhalt-1">
                        <akn:p GUID="62d6cc8e-837e-4931-936a-4a18f4bfae92"
                               eId="hauptteil-1_para-29_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-30" GUID="af8c737c-ba14-4f65-92ca-5c0ae45fa719"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-30_bezeichnung-1" GUID="114896af-7883-451b-9f2c-d1a415c314c1">
                    <akn:marker eId="hauptteil-1_para-30_bezeichnung-1_zaehlbez-1"
                                GUID="8fa3954e-5382-4416-b80d-cda005a29847" name="1"></akn:marker>§6e
                </akn:num>
                <akn:paragraph GUID="fb130f72-2180-4ea8-b3ff-938f0af0d220" eId="hauptteil-1_para-30_abs-1">
                    <akn:num eId="hauptteil-1_para-30_abs-1_bezeichnung-1" GUID="064f9219-38a1-4c1e-bf8d-1db70fa695f4">
                        <akn:marker eId="hauptteil-1_para-30_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="ca46f27e-a108-4791-bde5-990f8d4c6afe" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="6fa29af6-0da4-4872-9498-425070196c59" eId="hauptteil-1_para-30_abs-1_inhalt-1">
                        <akn:p GUID="a6354f0a-1a2f-402c-949d-fec4e0b8ea0b"
                               eId="hauptteil-1_para-30_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-31" GUID="95eb7919-b14a-4d4f-9d09-84770d49ee28"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-31_bezeichnung-1" GUID="8b50a05e-79c5-4b5c-8600-6ad645fdf1bf">
                    <akn:marker eId="hauptteil-1_para-31_bezeichnung-1_zaehlbez-1"
                                GUID="e2565fa8-3461-41c3-83ee-911ef3185173" name="1"></akn:marker>§7
                </akn:num>
                <akn:paragraph GUID="7f9fc745-1f6e-4305-9d51-13e54ccfff81" eId="hauptteil-1_para-31_abs-1">
                    <akn:num eId="hauptteil-1_para-31_abs-1_bezeichnung-1" GUID="023c24d7-7dbd-4f79-b7de-0ff17caab320">
                        <akn:marker eId="hauptteil-1_para-31_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="11a70d90-0fcd-40d7-a00d-df3ea32f3492" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="3c29f352-99e5-4fd9-a3d1-2a3483a24339" eId="hauptteil-1_para-31_abs-1_inhalt-1">
                        <akn:p GUID="cc8c7eee-8281-4b07-a94f-a2e19541dc3d"
                               eId="hauptteil-1_para-31_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-32" GUID="e0418ed8-210c-4df5-831d-84402c78fcd6"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-32_bezeichnung-1" GUID="e0da07d3-19b9-46ba-8c7f-cfa5232f256a">
                    <akn:marker eId="hauptteil-1_para-32_bezeichnung-1_zaehlbez-1"
                                GUID="5e386e8d-1d08-4082-aa82-1c8170423c4c" name="1"></akn:marker>§7a
                </akn:num>
                <akn:paragraph GUID="d65c9dc8-190a-4e44-b4ff-f8b58898d9de" eId="hauptteil-1_para-32_abs-1">
                    <akn:num eId="hauptteil-1_para-32_abs-1_bezeichnung-1" GUID="5210ea3c-4c94-4e3e-b998-8023eb85a7b8">
                        <akn:marker eId="hauptteil-1_para-32_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="0c296795-0b59-4d32-9a0a-d6022457d252" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="221b988f-0964-4c84-82c3-8d42f7841f78" eId="hauptteil-1_para-32_abs-1_inhalt-1">
                        <akn:p GUID="4813520d-d8ad-4941-8e18-2203419ac49d"
                               eId="hauptteil-1_para-32_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-33" GUID="7f050d76-a8ed-4c15-8c5f-bc63cc2d592e"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-33_bezeichnung-1" GUID="f13bced6-228d-422f-8b93-cb5917cb5040">
                    <akn:marker eId="hauptteil-1_para-33_bezeichnung-1_zaehlbez-1"
                                GUID="3dbf1ac0-e48d-4b56-9689-b0b77757f220" name="1"></akn:marker>§7b
                </akn:num>
                <akn:paragraph GUID="e2828978-41cc-4330-a736-825c5e1a4b60" eId="hauptteil-1_para-33_abs-1">
                    <akn:num eId="hauptteil-1_para-33_abs-1_bezeichnung-1" GUID="922f9819-c8b5-4fca-85c1-4b879c005347">
                        <akn:marker eId="hauptteil-1_para-33_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="f8b3aa45-0092-42e1-ab29-4c7af5d98352" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="28f8eefc-f066-4a55-9223-c5b7dd60e8f6" eId="hauptteil-1_para-33_abs-1_inhalt-1">
                        <akn:p GUID="2e2d643a-a604-4b42-b50c-f4449d332c31"
                               eId="hauptteil-1_para-33_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-34" GUID="89f36695-4829-4639-98b3-7b87a3ee2664"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-34_bezeichnung-1" GUID="b418b0f3-70fb-447e-a394-105e76200573">
                    <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1"
                                GUID="ded3aa35-2165-44b7-a34f-411a4a72a3c4" name="1"></akn:marker>§7c
                </akn:num>
                <akn:paragraph GUID="2edaf508-548e-4197-b9dd-632dcf46ad68" eId="hauptteil-1_para-34_abs-1">
                    <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1" GUID="31ca8e8e-b7e2-4ff9-9f2b-9a016f5053cc">
                        <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="01a9c4a7-8474-4273-82b2-0161ef08df5a" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="d9220821-868e-45e9-ad99-16c45037ac4e" eId="hauptteil-1_para-34_abs-1_inhalt-1">
                        <akn:p GUID="42bbf761-e56c-4432-b794-674cdde1eac7"
                               eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-35" GUID="c677c2a7-38bc-4c4d-988c-9db5ba9947ce"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-35_bezeichnung-1" GUID="bd8f2e0e-bea3-4d9e-898b-d8c0d5532808">
                    <akn:marker eId="hauptteil-1_para-35_bezeichnung-1_zaehlbez-1"
                                GUID="176b6bca-d80c-4095-ab7c-249606da691a" name="1"></akn:marker>§7d
                </akn:num>
                <akn:paragraph GUID="a890abf2-9911-4490-9f32-a5ae7830af9b" eId="hauptteil-1_para-35_abs-1">
                    <akn:num eId="hauptteil-1_para-35_abs-1_bezeichnung-1" GUID="e8181952-69ee-4847-8728-003ee791bd03">
                        <akn:marker eId="hauptteil-1_para-35_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="80f658f3-1840-42c2-9a48-08e1a223a099" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="55e6cf69-6364-4d58-a18a-a74d7aa80dba" eId="hauptteil-1_para-35_abs-1_inhalt-1">
                        <akn:p GUID="562c588e-5249-4c78-822e-f7ee654865ab"
                               eId="hauptteil-1_para-35_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-36" GUID="7f4daf11-04cd-4d22-8586-1ec56b7b52df"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-36_bezeichnung-1" GUID="312541bc-1a0e-49aa-85b1-234d0378532b">
                    <akn:marker eId="hauptteil-1_para-36_bezeichnung-1_zaehlbez-1"
                                GUID="c0cb7478-70a9-43e1-99d1-edf664a2ba7b" name="1"></akn:marker>§7e
                </akn:num>
                <akn:paragraph GUID="51eb6574-ad7a-473b-826c-ff7d40dc394f" eId="hauptteil-1_para-36_abs-1">
                    <akn:num eId="hauptteil-1_para-36_abs-1_bezeichnung-1" GUID="9ea30b93-8c7e-4ee7-95b9-99df93524c0f">
                        <akn:marker eId="hauptteil-1_para-36_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="054776b4-7cfa-45b6-b753-619a31be5f8e" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="a254f8eb-ebd6-44e9-83c8-acf217d2e207" eId="hauptteil-1_para-36_abs-1_inhalt-1">
                        <akn:p GUID="46daee2c-4060-4553-baad-7fadb08e8553"
                               eId="hauptteil-1_para-36_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-37" GUID="91d0fb1b-f8ff-4203-827f-96c52887c3a0"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-37_bezeichnung-1" GUID="435a7e1a-a86b-4c80-8fd3-01edc555d79e">
                    <akn:marker eId="hauptteil-1_para-37_bezeichnung-1_zaehlbez-1"
                                GUID="7cf4f72d-cdd6-435d-b5f8-308ab1e03c09" name="1"></akn:marker>§7f
                </akn:num>
                <akn:paragraph GUID="00d9e791-1a3f-43c4-9ebd-3a6aeecb2b89" eId="hauptteil-1_para-37_abs-1">
                    <akn:num eId="hauptteil-1_para-37_abs-1_bezeichnung-1" GUID="96883ae8-097e-40b6-83e0-fbba493d667e">
                        <akn:marker eId="hauptteil-1_para-37_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="6f585554-1c78-4c27-b8c7-3ba7e5639045" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="395a9397-6659-4c5d-a1d0-a8d6051725b5" eId="hauptteil-1_para-37_abs-1_inhalt-1">
                        <akn:p GUID="f30b63e6-2f36-467c-92d7-7586ef9b2d02"
                               eId="hauptteil-1_para-37_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-38" GUID="1db3fb3f-6b3b-4eb6-bb0e-4372b70d4ed8"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-38_bezeichnung-1" GUID="5e3ae644-efb8-4c15-a5fe-05d3251290bf">
                    <akn:marker eId="hauptteil-1_para-38_bezeichnung-1_zaehlbez-1"
                                GUID="7b80fe8f-7c9d-4f8e-ab73-4929779edd66" name="1"></akn:marker>§7g
                </akn:num>
                <akn:paragraph GUID="59aec1b9-5713-44a1-bfb3-17b49718ec2a" eId="hauptteil-1_para-38_abs-1">
                    <akn:num eId="hauptteil-1_para-38_abs-1_bezeichnung-1" GUID="d348e45b-e7d8-461b-9258-5e5dc2e5e1f9">
                        <akn:marker eId="hauptteil-1_para-38_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="7449663a-d19f-4209-ba02-9bb0f3711536" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="17bf7389-796c-40bc-8fb0-20eb0b89f846" eId="hauptteil-1_para-38_abs-1_inhalt-1">
                        <akn:p GUID="3c0ff811-1dc5-41dc-91cb-0ce5a49d92e4"
                               eId="hauptteil-1_para-38_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-39" GUID="12f09447-ea90-4e64-9c81-77b458a96b23"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-39_bezeichnung-1" GUID="99de84a4-0cef-455b-aa00-6a56ad07a9a9">
                    <akn:marker eId="hauptteil-1_para-39_bezeichnung-1_zaehlbez-1"
                                GUID="242b52d5-4839-489c-92eb-10cff5856ebe" name="1"></akn:marker>§7h
                </akn:num>
                <akn:paragraph GUID="4d67a40a-c18d-4349-b22d-f7138258b314" eId="hauptteil-1_para-39_abs-1">
                    <akn:num eId="hauptteil-1_para-39_abs-1_bezeichnung-1" GUID="33ba0c61-1a73-4303-8db6-db75175396ac">
                        <akn:marker eId="hauptteil-1_para-39_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="b8e00553-5b5c-4117-9e93-d98dd139afaa" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="356c3785-8371-4dee-b85c-c4022ca07036" eId="hauptteil-1_para-39_abs-1_inhalt-1">
                        <akn:p GUID="59849e73-9b5e-44c7-9022-6e91851aa6cd"
                               eId="hauptteil-1_para-39_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-40" GUID="579c9f9a-ba48-49cb-a60c-a571318be3d0"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-40_bezeichnung-1" GUID="766a9b2e-e83e-4ffd-b2d8-9fe19a201370">
                    <akn:marker eId="hauptteil-1_para-40_bezeichnung-1_zaehlbez-1"
                                GUID="91981426-20c2-4aa5-aa51-56d65ef40210" name="1"></akn:marker>§7i
                </akn:num>
                <akn:paragraph GUID="814e4ff1-1f54-4cca-8689-707bacf4d37b" eId="hauptteil-1_para-40_abs-1">
                    <akn:num eId="hauptteil-1_para-40_abs-1_bezeichnung-1" GUID="dc302bd5-22f4-4123-a6bb-cd2146e8e3a0">
                        <akn:marker eId="hauptteil-1_para-40_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="9d9ef951-c080-4557-8949-31226e341096" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="3016ef4b-1741-4d9a-a5ee-1a474e32cf3e" eId="hauptteil-1_para-40_abs-1_inhalt-1">
                        <akn:p GUID="b96b09fb-fae0-495c-9a4d-78397519096a"
                               eId="hauptteil-1_para-40_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-41" GUID="b2e0df87-d52f-458b-8901-6fa8a72c28c0"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-41_bezeichnung-1" GUID="772fc8c6-2009-4c91-980f-1811ec8c98b7">
                    <akn:marker eId="hauptteil-1_para-41_bezeichnung-1_zaehlbez-1"
                                GUID="9ffb0adb-680c-4cda-9026-ac447adbea78" name="1"></akn:marker>§7j
                </akn:num>
                <akn:paragraph GUID="a7f2ed2e-0a5e-41e3-ba37-dd696cb41c13" eId="hauptteil-1_para-41_abs-1">
                    <akn:num eId="hauptteil-1_para-41_abs-1_bezeichnung-1" GUID="1ab126b7-a2ba-4165-85dc-3ba92d68f050">
                        <akn:marker eId="hauptteil-1_para-41_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="fb0cec87-96c2-45e0-a06c-27630c9beccd" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="f461fe66-d927-4625-bf13-f60ec5f74b46" eId="hauptteil-1_para-41_abs-1_inhalt-1">
                        <akn:p GUID="ee202292-1ffb-4e83-9f31-a402a563acbd"
                               eId="hauptteil-1_para-41_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-42" GUID="f1631d4d-2c49-4de0-99e8-0313a98bc973"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-42_bezeichnung-1" GUID="70ddcd29-2719-479f-a2ce-9fba1f8513af">
                    <akn:marker eId="hauptteil-1_para-42_bezeichnung-1_zaehlbez-1"
                                GUID="0a5aa72d-13e3-4b17-a656-3ca6543978d2" name="1"></akn:marker>§7k
                </akn:num>
                <akn:paragraph GUID="b4dce994-2a44-4519-baca-61df6649312d" eId="hauptteil-1_para-42_abs-1">
                    <akn:num eId="hauptteil-1_para-42_abs-1_bezeichnung-1" GUID="bf9d2e42-b77b-4d1e-9036-7eb9ed3fdb23">
                        <akn:marker eId="hauptteil-1_para-42_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="e241ced3-16a6-41bf-bb3f-feedaf2cedd3" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="c702ccb3-2ab8-4f9d-bc6a-10d62451b45c" eId="hauptteil-1_para-42_abs-1_inhalt-1">
                        <akn:p GUID="828cac8a-2233-405d-bf8f-0e7012c97cf4"
                               eId="hauptteil-1_para-42_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-43" GUID="5f555a46-b98c-4ca5-85eb-5d8c9bcc773b"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-43_bezeichnung-1" GUID="e0d7f9ef-6310-43ea-8ee8-7206f80a10cf">
                    <akn:marker eId="hauptteil-1_para-43_bezeichnung-1_zaehlbez-1"
                                GUID="da45966d-0d32-453f-8396-72a1f90ad772" name="1"></akn:marker>§8
                </akn:num>
                <akn:paragraph GUID="8f1eeb6c-8765-47d8-9256-2fdb06c25c21" eId="hauptteil-1_para-43_abs-1">
                    <akn:num eId="hauptteil-1_para-43_abs-1_bezeichnung-1" GUID="5894d7f0-88b4-4ceb-99fc-b78f7682ddf3">
                        <akn:marker eId="hauptteil-1_para-43_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="e140a46b-28c4-4360-84dc-4b02ef8639b3" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="ec74badd-2483-4036-8475-d9ea34b0cd7c" eId="hauptteil-1_para-43_abs-1_inhalt-1">
                        <akn:p GUID="aac2e07d-b31b-45ce-9730-39f0b35a4756"
                               eId="hauptteil-1_para-43_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-44" GUID="76c34087-8785-4f65-bfcf-b21d3cce5447"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-44_bezeichnung-1" GUID="74b7793f-b3aa-407f-bc35-a7c275405e5a">
                    <akn:marker eId="hauptteil-1_para-44_bezeichnung-1_zaehlbez-1"
                                GUID="1ba8c8d2-c155-4d92-a196-efb029a8aa07" name="1"></akn:marker>§9
                </akn:num>
                <akn:paragraph GUID="03a3e523-3769-4e43-9044-c7354ed1ec61" eId="hauptteil-1_para-44_abs-1">
                    <akn:num eId="hauptteil-1_para-44_abs-1_bezeichnung-1" GUID="f3555f68-2e82-45c3-80e2-30568ff3e977">
                        <akn:marker eId="hauptteil-1_para-44_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="e7522b84-b956-4ec1-8dfc-e7314502a7df" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="6ee4bd25-6e3e-4508-a4cd-4c27cfc0285c" eId="hauptteil-1_para-44_abs-1_inhalt-1">
                        <akn:p GUID="114c7481-6682-456f-bd7a-cbe95d23c479"
                               eId="hauptteil-1_para-44_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-45" GUID="ee674638-ebcd-4afb-8d67-507ec4590dc3"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-45_bezeichnung-1" GUID="c34dab0f-0b9f-4507-8fda-c611098eff33">
                    <akn:marker eId="hauptteil-1_para-45_bezeichnung-1_zaehlbez-1"
                                GUID="5a510f04-234b-4e98-9f3d-a6d0a0b85c45" name="1"></akn:marker>§9a
                </akn:num>
                <akn:paragraph GUID="67af4843-ce5c-45f0-bb29-785a233fe79a" eId="hauptteil-1_para-45_abs-1">
                    <akn:num eId="hauptteil-1_para-45_abs-1_bezeichnung-1" GUID="91917c04-2e02-48d8-9472-712b51c46f39">
                        <akn:marker eId="hauptteil-1_para-45_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="6d9fbe8d-05f4-49e9-bb20-8f5546d9c7d1" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="763a8a04-b258-41aa-89a9-f6bcdc3ca691" eId="hauptteil-1_para-45_abs-1_inhalt-1">
                        <akn:p GUID="81bde952-65db-475c-9ceb-2b4abf18adac"
                               eId="hauptteil-1_para-45_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-46" GUID="ccf524cc-5857-417b-8874-ac15bafe9870"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-46_bezeichnung-1" GUID="bdd35c51-f64b-4c4f-ad08-d66a499b7c4a">
                    <akn:marker eId="hauptteil-1_para-46_bezeichnung-1_zaehlbez-1"
                                GUID="9a3d4f9d-0534-4da6-8a4c-2804a4d70af9" name="1"></akn:marker>§9b
                </akn:num>
                <akn:paragraph GUID="f1ce8898-144a-41c4-a797-bf158c785e64" eId="hauptteil-1_para-46_abs-1">
                    <akn:num eId="hauptteil-1_para-46_abs-1_bezeichnung-1" GUID="ae91a07a-3c2f-4e1b-9190-998aed9d9878">
                        <akn:marker eId="hauptteil-1_para-46_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="26da12f6-64d1-4949-ba2c-580bf20167fb" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="f09a4a6d-1021-45e9-9f39-3bba81890bac" eId="hauptteil-1_para-46_abs-1_inhalt-1">
                        <akn:p GUID="64022ca3-9601-4030-9996-b8660ccbfa31"
                               eId="hauptteil-1_para-46_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-47" GUID="db7f88c9-f304-4564-a294-9a6b0c8d3567"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-47_bezeichnung-1" GUID="5d697e3e-bcc8-4c77-9b84-513cf64747bc">
                    <akn:marker eId="hauptteil-1_para-47_bezeichnung-1_zaehlbez-1"
                                GUID="431400b6-2f44-4984-b9cb-2bf306a27c2b" name="1"></akn:marker>§10
                </akn:num>
                <akn:paragraph GUID="8e573156-d96e-40be-9341-1c48c0f6d24f" eId="hauptteil-1_para-47_abs-1">
                    <akn:num eId="hauptteil-1_para-47_abs-1_bezeichnung-1" GUID="626b6481-44ce-4574-9048-175189c2a1a6">
                        <akn:marker eId="hauptteil-1_para-47_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="d5782f9b-5865-47dd-8efe-a6872e8488b9" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="90c69d6e-3325-4e18-8c96-e92588aea0fb" eId="hauptteil-1_para-47_abs-1_inhalt-1">
                        <akn:p GUID="02742a1f-daab-43cb-8b3e-006d89b1fc4f"
                               eId="hauptteil-1_para-47_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-48" GUID="e14bd426-f9e7-480b-9069-0d9cb0fd7b80"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-48_bezeichnung-1" GUID="1832f4b2-6856-412e-8012-44f9b885c69c">
                    <akn:marker eId="hauptteil-1_para-48_bezeichnung-1_zaehlbez-1"
                                GUID="a74d27ee-1639-4440-b476-ed7d2808afaf" name="1"></akn:marker>§10a
                </akn:num>
                <akn:paragraph GUID="a23ffb18-d9c9-4030-a54e-168f1d6b9122" eId="hauptteil-1_para-48_abs-1">
                    <akn:num eId="hauptteil-1_para-48_abs-1_bezeichnung-1" GUID="f0b9f3d9-6c74-4938-bb6e-b0b7b4bf729a">
                        <akn:marker eId="hauptteil-1_para-48_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="cdbe98ba-554f-4ee3-89fc-200913caca2c" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="2348a890-8255-4978-ae9e-256907b3822f" eId="hauptteil-1_para-48_abs-1_inhalt-1">
                        <akn:p GUID="73beeb6d-b875-46f5-91fc-cdb9276369b3"
                               eId="hauptteil-1_para-48_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-49" GUID="43bb65f4-9998-49ff-b685-e297fdc6ed39"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-49_bezeichnung-1" GUID="c51df148-7d09-40fe-92f8-a3c29f6f3cee">
                    <akn:marker eId="hauptteil-1_para-49_bezeichnung-1_zaehlbez-1"
                                GUID="ea0ec36f-3ff3-439e-b617-dddcb8585a0f" name="1"></akn:marker>§10b
                </akn:num>
                <akn:paragraph GUID="dbdb0ed2-eb51-4ddb-8192-5fffc282e463" eId="hauptteil-1_para-49_abs-1">
                    <akn:num eId="hauptteil-1_para-49_abs-1_bezeichnung-1" GUID="58ef9d6d-8c69-445b-aa29-aaca7021836b">
                        <akn:marker eId="hauptteil-1_para-49_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="3ebc15d5-a2cf-4a68-b105-ac1369c05cda" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="4fc7d436-f74d-49fa-a67a-52c972fcf1d9" eId="hauptteil-1_para-49_abs-1_inhalt-1">
                        <akn:p GUID="22031202-2172-49bd-accd-ce5dc3da4149"
                               eId="hauptteil-1_para-49_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-50" GUID="bc497bd8-d49f-418d-9c2c-88f67f86daaf"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-50_bezeichnung-1" GUID="9138e716-266f-4509-9cd5-ccda34ee11b8">
                    <akn:marker eId="hauptteil-1_para-50_bezeichnung-1_zaehlbez-1"
                                GUID="c58e7125-a26e-4e0d-95af-063b654ba5ee" name="1"></akn:marker>§10c
                </akn:num>
                <akn:paragraph GUID="05906cf6-4591-4f6b-806d-3e2f42700e2c" eId="hauptteil-1_para-50_abs-1">
                    <akn:num eId="hauptteil-1_para-50_abs-1_bezeichnung-1" GUID="2c09968b-876d-46e6-9106-bf108008e114">
                        <akn:marker eId="hauptteil-1_para-50_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="874efebd-bfb1-46ad-925e-2a67ed2b4d51" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="349f137e-c17a-4a22-a0eb-ca4f7658ab0d" eId="hauptteil-1_para-50_abs-1_inhalt-1">
                        <akn:p GUID="17b21830-5240-4a8f-a911-9ef537d9f451"
                               eId="hauptteil-1_para-50_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-51" GUID="9200365a-1df4-4983-85c8-451e421e9d0d"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-51_bezeichnung-1" GUID="a1e1b0f3-7799-4648-b3e4-3b0deecd05f7">
                    <akn:marker eId="hauptteil-1_para-51_bezeichnung-1_zaehlbez-1"
                                GUID="7ae45d32-486c-446e-b736-7b0ad304077f" name="1"></akn:marker>§10d
                </akn:num>
                <akn:paragraph GUID="804b00e6-00a4-434e-99e9-643ed7684804" eId="hauptteil-1_para-51_abs-1">
                    <akn:num eId="hauptteil-1_para-51_abs-1_bezeichnung-1" GUID="93397acf-6c68-401c-bdba-d4a6a5c03fe5">
                        <akn:marker eId="hauptteil-1_para-51_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="946772cf-4c61-4108-8dde-3f6687eb3c4f" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="24c2d356-954b-4ea5-9230-a6900ead68c0" eId="hauptteil-1_para-51_abs-1_inhalt-1">
                        <akn:p GUID="d0b50a0e-fad2-47c9-9bc6-298deffbf4c3"
                               eId="hauptteil-1_para-51_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-52" GUID="706cf9b3-20fb-4952-80f7-fcf52afa5532"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-52_bezeichnung-1" GUID="74fa2557-59aa-43b8-b990-720ad6813f93">
                    <akn:marker eId="hauptteil-1_para-52_bezeichnung-1_zaehlbez-1"
                                GUID="9bce7946-fc0f-480f-8a4f-2f5e58636142" name="1"></akn:marker>§10e
                </akn:num>
                <akn:paragraph GUID="2c34eaa0-9445-45ff-ae73-e4f903602624" eId="hauptteil-1_para-52_abs-1">
                    <akn:num eId="hauptteil-1_para-52_abs-1_bezeichnung-1" GUID="e76ba7c6-e8b7-43ef-9e44-08702928fb47">
                        <akn:marker eId="hauptteil-1_para-52_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="a7bc2898-67f9-4fac-9cce-25baff77faf4" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="d5404cfe-bbdd-4198-91ee-e0c0ff4c205c" eId="hauptteil-1_para-52_abs-1_inhalt-1">
                        <akn:p GUID="a0761643-a5ac-4590-ba3f-1cd8abec189f"
                               eId="hauptteil-1_para-52_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-53" GUID="85c19e43-7ae6-4051-b7d0-6cbeb0a2a3ae"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-53_bezeichnung-1" GUID="d7233aa6-cb7e-4eae-967c-a39570c4b680">
                    <akn:marker eId="hauptteil-1_para-53_bezeichnung-1_zaehlbez-1"
                                GUID="8d93f760-aa32-44ab-915b-1001da584bdf" name="1"></akn:marker>§10f
                </akn:num>
                <akn:paragraph GUID="fb771acf-763d-4f65-bc3e-6c47244df2e9" eId="hauptteil-1_para-53_abs-1">
                    <akn:num eId="hauptteil-1_para-53_abs-1_bezeichnung-1" GUID="d8da0052-d1cb-4dfc-97c3-0d433b5e7a8b">
                        <akn:marker eId="hauptteil-1_para-53_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="52078241-2148-41ff-8322-9ebb7cae11b3" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="85b183d9-d114-4fd5-8293-a458f48d6c85" eId="hauptteil-1_para-53_abs-1_inhalt-1">
                        <akn:p GUID="36ae4b5d-b7f9-4c95-852a-01433026b3c3"
                               eId="hauptteil-1_para-53_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-54" GUID="8b3b9885-e5cf-49d1-aec4-c851ffa8e612"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-54_bezeichnung-1" GUID="831a216d-a89e-4fcc-883c-552d56aa2c4c">
                    <akn:marker eId="hauptteil-1_para-54_bezeichnung-1_zaehlbez-1"
                                GUID="3b9ded1f-ed9e-4300-b2a2-18bcc27602fc" name="1"></akn:marker>§10g
                </akn:num>
                <akn:paragraph GUID="fe4a8d3d-9623-4556-bf68-753aa26d75f3" eId="hauptteil-1_para-54_abs-1">
                    <akn:num eId="hauptteil-1_para-54_abs-1_bezeichnung-1" GUID="639909a0-b708-446c-844e-817979d52c92">
                        <akn:marker eId="hauptteil-1_para-54_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="58446e4c-5f64-414e-b45c-2c395be72495" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="3a5712a6-b844-451d-8e3d-14f2bb1921c8" eId="hauptteil-1_para-54_abs-1_inhalt-1">
                        <akn:p GUID="13458f97-90b0-4ebf-99fa-de6ae5e13e18"
                               eId="hauptteil-1_para-54_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-55" GUID="d1e43ff6-aa11-45a8-9eda-e4c5f2ba8aaa"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-55_bezeichnung-1" GUID="56a7b2bd-b247-4389-a8c1-a2b4fba9a577">
                    <akn:marker eId="hauptteil-1_para-55_bezeichnung-1_zaehlbez-1"
                                GUID="4d3ea4ea-200b-48d2-bf7c-e6e7ee854653" name="1"></akn:marker>§10h
                </akn:num>
                <akn:paragraph GUID="d948e95d-14c4-4e96-b636-7f2b6f72a42f" eId="hauptteil-1_para-55_abs-1">
                    <akn:num eId="hauptteil-1_para-55_abs-1_bezeichnung-1" GUID="1220162c-2074-499a-9302-a83f3550a531">
                        <akn:marker eId="hauptteil-1_para-55_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="162a175c-52b8-43a1-88c3-3074be962051" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="cf3f2eed-722b-4996-9d06-c6c714838033" eId="hauptteil-1_para-55_abs-1_inhalt-1">
                        <akn:p GUID="3bc0ec8b-828f-4d29-8262-01b3c16524b0"
                               eId="hauptteil-1_para-55_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-56" GUID="7b4fb4ab-e1b3-45a7-8749-5df3e89a06f1"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-56_bezeichnung-1" GUID="468d82d5-8be5-4a63-aaa9-c3ea18f63bef">
                    <akn:marker eId="hauptteil-1_para-56_bezeichnung-1_zaehlbez-1"
                                GUID="74ae4f81-5524-4b5d-9316-86b6fbbe85dd" name="1"></akn:marker>§10i
                </akn:num>
                <akn:paragraph GUID="54f67d49-a7d1-44a9-8e8e-32d42e3efe9d" eId="hauptteil-1_para-56_abs-1">
                    <akn:num eId="hauptteil-1_para-56_abs-1_bezeichnung-1" GUID="46463b98-9582-4d79-9855-0de6b6f55bff">
                        <akn:marker eId="hauptteil-1_para-56_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="65931160-1d8f-4e49-ba60-8a4540d05304" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="de1e05c8-4c38-443b-825f-5426bd934b62" eId="hauptteil-1_para-56_abs-1_inhalt-1">
                        <akn:p GUID="900efd8d-62d9-4839-84fa-815dca262549"
                               eId="hauptteil-1_para-56_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-57" GUID="52869254-3d60-4278-976c-12c6caa0e9ca"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-57_bezeichnung-1" GUID="2940f21e-162f-4af9-87d7-ea5e77640759">
                    <akn:marker eId="hauptteil-1_para-57_bezeichnung-1_zaehlbez-1"
                                GUID="36c9469a-fd93-4895-94ce-ddce4cf24fb0" name="1"></akn:marker>§11
                </akn:num>
                <akn:paragraph GUID="4b7bd11e-d8be-4e17-b856-afabf5503eee" eId="hauptteil-1_para-57_abs-1">
                    <akn:num eId="hauptteil-1_para-57_abs-1_bezeichnung-1" GUID="9176b31d-1277-4ccb-a851-06655a932c02">
                        <akn:marker eId="hauptteil-1_para-57_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="9b6dd744-dc1a-4385-b18c-10ae62fb0d5c" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="932128d1-8e6f-4bfa-85c0-5210b4f20662" eId="hauptteil-1_para-57_abs-1_inhalt-1">
                        <akn:p GUID="39cee6c0-4513-4d55-9eb7-0ed16f3436ec"
                               eId="hauptteil-1_para-57_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-58" GUID="f8d485e1-0153-4460-b509-9577af35aa1f"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-58_bezeichnung-1" GUID="2e13a1c9-a506-4dc1-9efa-aad0c2573f7b">
                    <akn:marker eId="hauptteil-1_para-58_bezeichnung-1_zaehlbez-1"
                                GUID="e71f7e8f-3672-4a05-8df2-49076d04a854" name="1"></akn:marker>§11a
                </akn:num>
                <akn:paragraph GUID="d7452750-46cf-4299-be42-aff8774f0739" eId="hauptteil-1_para-58_abs-1">
                    <akn:num eId="hauptteil-1_para-58_abs-1_bezeichnung-1" GUID="f775f2da-f619-4c6c-962b-9421023e84c1">
                        <akn:marker eId="hauptteil-1_para-58_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="0f80a7f9-18f3-408e-80b3-4908514898c3" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="dccbe848-f55f-48bc-99ea-ace45ade406d" eId="hauptteil-1_para-58_abs-1_inhalt-1">
                        <akn:p GUID="2eaf494d-ffdd-4aea-9740-899c04070c01"
                               eId="hauptteil-1_para-58_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-59" GUID="7f87a5ce-4eac-4618-8b88-11ad9a73f094"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-59_bezeichnung-1" GUID="458930e8-7825-4f8b-af4c-18afcae80c99">
                    <akn:marker eId="hauptteil-1_para-59_bezeichnung-1_zaehlbez-1"
                                GUID="c93e130b-b767-4b45-90d1-68fe92d61236" name="1"></akn:marker>§11b
                </akn:num>
                <akn:paragraph GUID="a7c97c41-a018-48b6-bfdd-28f6209659cf" eId="hauptteil-1_para-59_abs-1">
                    <akn:num eId="hauptteil-1_para-59_abs-1_bezeichnung-1" GUID="f7eab3e9-6018-4bea-a5c8-3f28c62c2f37">
                        <akn:marker eId="hauptteil-1_para-59_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="f43a3121-f668-4b7b-bf39-3e8e042fa933" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="350aad01-5c46-4090-b7f4-0eb16d7f02d0" eId="hauptteil-1_para-59_abs-1_inhalt-1">
                        <akn:p GUID="1b26c17a-9243-4082-acd8-6f5a2ffa47dc"
                               eId="hauptteil-1_para-59_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-60" GUID="d5cc84de-4a2f-4112-b873-535d0767fc5c"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-60_bezeichnung-1" GUID="48b761b6-4cc8-40f2-9ef2-9314b83baf65">
                    <akn:marker eId="hauptteil-1_para-60_bezeichnung-1_zaehlbez-1"
                                GUID="f88103bc-2915-42fa-a37b-fe82afe95894" name="1"></akn:marker>§12
                </akn:num>
                <akn:paragraph GUID="0254c66d-ae15-48ca-abad-803687cac3e0" eId="hauptteil-1_para-60_abs-1">
                    <akn:num eId="hauptteil-1_para-60_abs-1_bezeichnung-1" GUID="3a9c0aa6-6a01-4a29-ab37-44f584030095">
                        <akn:marker eId="hauptteil-1_para-60_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="b274638d-dcf5-44a1-a9f5-4c895a1de763" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="7853e7ce-759e-415b-bfe1-c05e3b443f9c" eId="hauptteil-1_para-60_abs-1_inhalt-1">
                        <akn:p GUID="ceef25f2-a8b3-4775-a43d-958a54607fa8"
                               eId="hauptteil-1_para-60_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-61" GUID="8066551b-deb0-434a-8fcb-5f5a28237b11"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-61_bezeichnung-1" GUID="7fa61a6b-ab26-4d3d-ae4a-ba5a19f5e585">
                    <akn:marker eId="hauptteil-1_para-61_bezeichnung-1_zaehlbez-1"
                                GUID="89ce6989-add4-42dd-b52e-eb2e631d8b9b" name="1"></akn:marker>§13
                </akn:num>
                <akn:paragraph GUID="2fe3952d-4d95-4fd6-94b0-5f1ad3c40bed" eId="hauptteil-1_para-61_abs-1">
                    <akn:num eId="hauptteil-1_para-61_abs-1_bezeichnung-1" GUID="87bfb2f0-c3fe-4351-b2c6-abba45d8a49e">
                        <akn:marker eId="hauptteil-1_para-61_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="1cfc7439-eee5-4030-b6ce-c0de16f1d139" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="1b452dde-d8f0-4e31-b268-4a5add6410f5" eId="hauptteil-1_para-61_abs-1_inhalt-1">
                        <akn:p GUID="a6e1370d-4c4f-4fdc-8e70-021a7c46485d"
                               eId="hauptteil-1_para-61_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-62" GUID="58b72cdd-9361-4f44-adf4-9e46b4dd411b"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-62_bezeichnung-1" GUID="7d236b8f-87eb-4b7f-bb32-f621a43b96d5">
                    <akn:marker eId="hauptteil-1_para-62_bezeichnung-1_zaehlbez-1"
                                GUID="cc700ea8-2378-42c8-8520-02f378ddf72d" name="1"></akn:marker>§13a
                </akn:num>
                <akn:paragraph GUID="15351e75-b249-43e6-90ac-9732977790de" eId="hauptteil-1_para-62_abs-1">
                    <akn:num eId="hauptteil-1_para-62_abs-1_bezeichnung-1" GUID="26357c0a-3921-4630-be95-2ccefb23d4f3">
                        <akn:marker eId="hauptteil-1_para-62_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="33fa504a-2dc0-484d-86b3-a8d8bc88c396" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="886c6bfe-40f2-4bbb-8764-fbdcdab02969" eId="hauptteil-1_para-62_abs-1_inhalt-1">
                        <akn:p GUID="02fe82fd-f46e-4fd1-a2f4-a74b72e052d4"
                               eId="hauptteil-1_para-62_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-63" GUID="2603ad73-00f3-4f3e-aa0d-096b46899c2e"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-63_bezeichnung-1" GUID="2d2fa553-9a11-47da-8331-079042c8f3a8">
                    <akn:marker eId="hauptteil-1_para-63_bezeichnung-1_zaehlbez-1"
                                GUID="6748e5ec-813b-49e1-afaa-5e353da80005" name="1"></akn:marker>§14
                </akn:num>
                <akn:paragraph GUID="dfc62626-d0ec-4642-9bf9-5e284a62ea16" eId="hauptteil-1_para-63_abs-1">
                    <akn:num eId="hauptteil-1_para-63_abs-1_bezeichnung-1" GUID="0e7d6304-4621-4df4-8570-aae3de882855">
                        <akn:marker eId="hauptteil-1_para-63_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="5cd85416-f93a-4b66-8b2c-a07f769e2163" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="8e30d562-065b-4cc0-ba23-04533003726b" eId="hauptteil-1_para-63_abs-1_inhalt-1">
                        <akn:p GUID="d384eaed-1725-4394-8ea5-7c74f47d8eac"
                               eId="hauptteil-1_para-63_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-64" GUID="bf3fe544-55f5-44f1-b40e-9e318cc8e4c2"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-64_bezeichnung-1" GUID="cbadeaa2-e413-4873-ba67-93aa9b108e01">
                    <akn:marker eId="hauptteil-1_para-64_bezeichnung-1_zaehlbez-1"
                                GUID="e47f6707-d744-49c4-86db-0d872aee0669" name="1"></akn:marker>§14a
                </akn:num>
                <akn:paragraph GUID="97834b4e-b5b7-4800-a7bd-fc0ddc47c596" eId="hauptteil-1_para-64_abs-1">
                    <akn:num eId="hauptteil-1_para-64_abs-1_bezeichnung-1" GUID="800aefa7-bd3c-49be-80b4-3015561c0c1a">
                        <akn:marker eId="hauptteil-1_para-64_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="f8f10835-87ee-4569-8e4f-9c716cba1c4b" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="3a00eb3d-1aae-4ab5-85df-fbb50b91d87b" eId="hauptteil-1_para-64_abs-1_inhalt-1">
                        <akn:p GUID="3e19a349-dd89-4f93-8dc7-b2bc9c96157e"
                               eId="hauptteil-1_para-64_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-65" GUID="e0f00c8d-6e4b-41e3-8146-bf18ab653a61"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-65_bezeichnung-1" GUID="943249dd-a5f5-44a6-be1c-b765b12ffb7c">
                    <akn:marker eId="hauptteil-1_para-65_bezeichnung-1_zaehlbez-1"
                                GUID="c23b8b25-4052-4c1b-8c60-ba291ee502bf" name="1"></akn:marker>§15
                </akn:num>
                <akn:paragraph GUID="53270dfd-3d76-468d-8f3a-180a5cd03dd1" eId="hauptteil-1_para-65_abs-1">
                    <akn:num eId="hauptteil-1_para-65_abs-1_bezeichnung-1" GUID="91455560-b3bf-4a6c-8103-8a74bd38f828">
                        <akn:marker eId="hauptteil-1_para-65_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="4a936f87-5789-47ca-a128-dcf883ad97ea" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="ad504722-5457-4949-a9c3-07198f633303" eId="hauptteil-1_para-65_abs-1_inhalt-1">
                        <akn:p GUID="77621563-5a54-4dcf-ad05-7ee370cd3a78"
                               eId="hauptteil-1_para-65_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-66" GUID="59f33977-e133-403d-87f2-a8a19e660126"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-66_bezeichnung-1" GUID="b79815cd-e9b2-4a49-a8aa-3fa7bf441e8b">
                    <akn:marker eId="hauptteil-1_para-66_bezeichnung-1_zaehlbez-1"
                                GUID="a7514c8a-cf9d-4438-b494-aab1fa9c3c6c" name="1"></akn:marker>§15a
                </akn:num>
                <akn:paragraph GUID="b71f169d-5a18-43f7-ab2a-4ec4d7bdcce2" eId="hauptteil-1_para-66_abs-1">
                    <akn:num eId="hauptteil-1_para-66_abs-1_bezeichnung-1" GUID="40688c5e-d183-4970-8df8-a6018e5ed41e">
                        <akn:marker eId="hauptteil-1_para-66_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="907d59f6-912d-4adb-9958-9a8221dea937" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="6a3dc9a2-c698-4fa8-abc3-6c0ee92b867e" eId="hauptteil-1_para-66_abs-1_inhalt-1">
                        <akn:p GUID="5cd1f012-7fa3-4438-ac39-6e46c2a9cdec"
                               eId="hauptteil-1_para-66_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-67" GUID="d39e85a0-e42d-42e5-ae6e-3e4b01d7c125"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-67_bezeichnung-1" GUID="bf7dee3c-4eae-4e52-82a4-9989306fa60b">
                    <akn:marker eId="hauptteil-1_para-67_bezeichnung-1_zaehlbez-1"
                                GUID="210cc7b2-7e99-45d3-8d78-02b83bccbd46" name="1"></akn:marker>§15b
                </akn:num>
                <akn:paragraph GUID="ee1207d0-5a50-4491-8f3d-aff67f7e8cc3" eId="hauptteil-1_para-67_abs-1">
                    <akn:num eId="hauptteil-1_para-67_abs-1_bezeichnung-1" GUID="9269d744-58f9-4702-a381-ae4e0b019fe7">
                        <akn:marker eId="hauptteil-1_para-67_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="28b55eff-51b5-4ea2-9662-e6334977a7ff" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="fc89e421-a91b-4ef6-8e50-a03610d792be" eId="hauptteil-1_para-67_abs-1_inhalt-1">
                        <akn:p GUID="9cd7acc7-1ca0-4bca-8163-b9631ef4039f"
                               eId="hauptteil-1_para-67_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-68" GUID="13a6f767-98dd-4d47-a578-29ab52eade2f"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-68_bezeichnung-1" GUID="cc243a4b-5f4c-4a8b-9266-afe19faf7c50">
                    <akn:marker eId="hauptteil-1_para-68_bezeichnung-1_zaehlbez-1"
                                GUID="e2614a95-34e0-4e67-bb42-ae8917071478" name="1"></akn:marker>§16
                </akn:num>
                <akn:paragraph GUID="69ec5e14-a326-40d7-89cf-a0050a92960b" eId="hauptteil-1_para-68_abs-1">
                    <akn:num eId="hauptteil-1_para-68_abs-1_bezeichnung-1" GUID="5e0ff084-7c12-4f2d-a4c7-a50c3eba46f9">
                        <akn:marker eId="hauptteil-1_para-68_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="a5eaf1ec-2b98-47ea-9d14-585b4893d610" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="8bf5fdd9-b927-4ca3-96cd-6e6d2594ab8b" eId="hauptteil-1_para-68_abs-1_inhalt-1">
                        <akn:p GUID="08d880ae-2418-4381-a3b1-c2c724671d79"
                               eId="hauptteil-1_para-68_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-69" GUID="6457e6d5-1210-461c-b1d5-c1790b26e838"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-69_bezeichnung-1" GUID="72285626-56c2-4d70-87fc-6e763ce3b2b7">
                    <akn:marker eId="hauptteil-1_para-69_bezeichnung-1_zaehlbez-1"
                                GUID="85ce7e7b-b1f9-4d9a-88cb-9db3342b607e" name="1"></akn:marker>§17
                </akn:num>
                <akn:paragraph GUID="162917c4-6e74-40e1-97e9-f32a319ccc1e" eId="hauptteil-1_para-69_abs-1">
                    <akn:num eId="hauptteil-1_para-69_abs-1_bezeichnung-1" GUID="4765be64-12f6-4d50-a18b-84337ea8c600">
                        <akn:marker eId="hauptteil-1_para-69_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="e4f2edd1-565c-48ff-8e07-04c041c6f792" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="8f5410e5-178c-4c0e-8f33-c32d4e2bce23" eId="hauptteil-1_para-69_abs-1_inhalt-1">
                        <akn:p GUID="1963d605-9a42-4cf2-b89c-9da40b5a865e"
                               eId="hauptteil-1_para-69_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-70" GUID="59e3f34c-4f28-4e34-86ae-f2d45181d5b6"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-70_bezeichnung-1" GUID="805050c7-e327-4bcb-b1a8-c14e98e5c372">
                    <akn:marker eId="hauptteil-1_para-70_bezeichnung-1_zaehlbez-1"
                                GUID="cafa575a-ed61-46e0-9690-1bd374c6767e" name="1"></akn:marker>§18
                </akn:num>
                <akn:paragraph GUID="e02d01a5-30e7-4ca5-8535-a5f8762b9bd0" eId="hauptteil-1_para-70_abs-1">
                    <akn:num eId="hauptteil-1_para-70_abs-1_bezeichnung-1" GUID="738a4278-5552-4b36-b607-bc6fca803a36">
                        <akn:marker eId="hauptteil-1_para-70_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="897eeba8-24e0-4db5-96da-0c2d4e88a0b4" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="5a567605-e8a5-4a20-a9ed-423db6cce51a" eId="hauptteil-1_para-70_abs-1_inhalt-1">
                        <akn:p GUID="b1691777-4124-4097-99e0-b00073b7db53"
                               eId="hauptteil-1_para-70_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
            <akn:article eId="hauptteil-1_para-71" GUID="a726a175-3a70-40e7-852f-9d08d1a26a13"
                         period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-71_bezeichnung-1" GUID="0298199b-a8d3-4f7b-b5e5-16b4dc2501d1">
                    <akn:marker eId="hauptteil-1_para-71_bezeichnung-1_zaehlbez-1"
                                GUID="500625c3-e8c9-4226-99b9-8b175c0d7635" name="19"></akn:marker>§19
                </akn:num>
                <!--Absatz 1-->
                <akn:paragraph GUID="eb932754-5119-4fc1-a167-4dfc5381d2ca" eId="hauptteil-1_para-71_abs-1">
                    <akn:num eId="hauptteil-1_para-71_abs-1_bezeichnung-1" GUID="8280d4ef-4a96-44b7-81b9-f30f1006a2fe">
                        <akn:marker eId="hauptteil-1_para-71_abs-1_bezeichnung-1_zaehlbez-1"
                                    GUID="0115df75-44e1-46e5-ac41-46c03643a0a6" name="1"></akn:marker>(1)
                    </akn:num>
                    <akn:content GUID="eeeac3d5-4e22-4b6f-9d91-ab70a47f3640" eId="hauptteil-1_para-71_abs-1_inhalt-1">
                        <akn:p GUID="2d1e76a2-d330-49d5-b2e4-4f9feea44f85"
                               eId="hauptteil-1_para-71_abs-1_inhalt-1_text-1"></akn:p>
                    </akn:content>
                </akn:paragraph>
                <!--Absatz 2-->
                <akn:paragraph GUID="cd17b333-e7d5-4f0f-9406-5d50eb12f783" eId="hauptteil-1_para-71_abs-2">
                    <akn:num eId="hauptteil-1_para-71_abs-2_bezeichnung-1" GUID="48d155ca-ec9e-4136-a14a-96d4f7e7a017">
                        <akn:marker eId="hauptteil-1_para-71_abs-2_bezeichnung-1_zaehlbez-1"
                                    GUID="68f1d541-9740-43e2-950d-4c8bfb38a3ab" name="2"></akn:marker>(2)
                    </akn:num>
                    <akn:list GUID="3035f805-a24d-45c3-9cb6-785fa2cc9812" eId="hauptteil-1_para-71_abs-2_untergl-1">
                        <akn:intro GUID="244de4e1-4976-4959-b274-22a0e4253bea"
                                   eId="hauptteil-1_para-71_abs-2_untergl-1_intro-1">
                            <akn:p GUID="9092b8f3-d2b4-4df0-8e7d-3232808365a6"
                                   eId="hauptteil-1_para-71_abs-2_untergl-1_intro-1_text-1">Von Versorgungsbezügen
                                bleiben ein nach einem Prozentsatz ermittelter, auf einen Höchstbetrag begrenzter Betrag
                                (Versorgungsfreibetrag) und ein Zuschlag zum Versorgungsfreibetrag steuerfrei.
                                Versorgungsbezüge sind
                            </akn:p>
                        </akn:intro>
                        <akn:point GUID="26764f22-3d0e-47c5-9830-31a8ca6dfa35"
                                   eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1">
                            <akn:num GUID="fa106c10-a7e9-4093-80bb-a061c7bd51e0"
                                     eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_bezeichnung-1"><akn:marker
                                    name="1" GUID="d88258ea-1997-4940-bf99-39312a8e12dd"
                                    eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>
                                1.
                            </akn:num>
                            <akn:list GUID="c121d47b-788e-4003-9ff0-5581d604b91a"
                                      eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1">
                                <akn:intro GUID="a0fe69a4-2a37-4d9e-9de6-237bd313b1fc"
                                           eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1">
                                    <akn:p GUID="fa011bbb-c56e-426c-a571-2e3d9e9f2cda"
                                           eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1_text-1">
                                        das Ruhegehalt, Witwen- oder Waisengeld, der Unterhaltsbeitrag oder ein
                                        gleichartiger Bezug
                                    </akn:p>
                                </akn:intro>
                                <akn:point GUID="b0d6890a-8224-4fc3-b7c6-9790277b1499"
                                           eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1">
                                    <akn:num GUID="ec3231f1-9d02-4d1e-86b5-68e75664e626"
                                             eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1">
                                        <akn:marker name="a" GUID="5c8a3f40-9a46-4e4b-a385-fa16cc529998"
                                                    eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>
                                        a)
                                    </akn:num>
                                    <akn:content GUID="a1802bf9-25cc-465f-bd96-7aceae04c366"
                                                 eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                                        <akn:p GUID="2e5cea34-e8ce-4f7c-8673-0a36f845f38b"
                                               eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                            Grund beamtenrechtlicher oder entsprechender gesetzlicher Vorschriften,
                                        </akn:p>
                                    </akn:content>
                                </akn:point>
                                <akn:point GUID="7cbb02b7-2053-41e2-88aa-a6aa26a72233"
                                           eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2">
                                    <akn:num GUID="e1953bdd-0564-4853-8ed0-dac771a181d2"
                                             eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1">
                                        <akn:marker name="b" GUID="7f1e41e0-bb47-4e10-b193-1a98eff460f8"
                                                    eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>
                                        b)
                                    </akn:num>
                                    <akn:content GUID="a2903657-6784-4eef-961b-f3c53bed78c6"
                                                 eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                                        <akn:p GUID="c2cd5917-b4ce-449d-8103-9f34fa0d34f7"
                                               eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                            nach beamtenrechtlichen Grundsätzen von Körperschaften, Anstalten oder
                                            Stiftungen des öffentlichen Rechts oder öffentlich-rechtlichen Verbänden von
                                            Körperschaften
                                        </akn:p>
                                    </akn:content>
                                </akn:point>
                                <akn:wrapUp GUID="0405677d-8718-4564-9dec-b3bf832f04e4"
                                            eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1">
                                    <akn:p GUID="ad110215-c861-4a60-8987-523952f68e21"
                                           eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1_text-1">
                                        oder
                                    </akn:p>
                                </akn:wrapUp>
                            </akn:list>
                        </akn:point>
                        <akn:point GUID="accde094-d21b-4a86-a8cd-f9123606c726"
                                   eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2">
                            <akn:num GUID="0521715f-1e31-4e38-b8e4-84c4d23e0f0b"
                                     eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_bezeichnung-1"><akn:marker
                                    name="2" GUID="7da84244-df58-498a-84b9-5860c4d8b338"
                                    eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>
                                2.
                            </akn:num>
                            <akn:content GUID="5484bc1b-b351-41f4-93d1-f2256ca61bea"
                                         eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_inhalt-1">
                                <akn:p GUID="e0aeb3f1-50a0-4f60-98d9-910a54143dca"
                                       eId="hauptteil-1_para-71_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                    in anderen Fällen Bezüge und Vorteile aus früheren Dienstleistungen wegen Erreichens
                                    einer Altersgrenze, verminderter Erwerbsfähigkeit oder Hinterbliebenenbezüge; Bezüge
                                    wegen Erreichens einer Altersgrenze gelten erst dann als Versorgungsbezüge, wenn der
                                    Steuerpflichtige das 63. Lebensjahr oder, wenn er schwerbehindert ist, das 60.
                                    Lebensjahr vollendet hat.
                                </akn:p>
                            </akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>
        </akn:body>
    </akn:act>
</akn:akomaNtoso>

');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('1ad41e24-a486-4df8-ad5d-3d1d46a42c2d', 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1', NULL);
