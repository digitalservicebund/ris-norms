-- REAPPLY
-- TARGET LAW
DELETE FROM dokumente WHERE eli_norm_expression = 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu';
DELETE FROM norm_manifestation WHERE eli_norm_expression = 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu';
DELETE FROM norm_expression WHERE eli_norm_expression = 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu';

INSERT INTO dokumente (xml)
VALUES ('<?xml version="1.0" encoding="UTF-8"?>
<?xml-model href="../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
        <!-- Metadaten-->
      <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
         <akn:identification eId="meta-1_ident-1"
                             GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22"
                             source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1"
                          GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1"
                             GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761"
                             value="eli/bund/bgbl-1/2024/108/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            GUID="a90733dd-4780-472d-8371-a3690f2e869a"
                            value="eli/bund/bgbl-1/2024/108"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b"
                              name="übergreifende-id"
                              value="2c1a6ec9-7161-4e2f-8999-91219180d991"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811"
                             date="2024-03-27"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e"
                                value="de"/>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5"
                               value="108"/>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1"
                             GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8"
                             value="bgbl-1"/>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                GUID="af948830-e615-4dd5-8237-b8c830939f78"
                                value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                             GUID="85e064f4-f56f-4cef-86a4-6801cc71c712"
                             value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            GUID="e8f52739-79c1-4e59-9db2-004180c8b29c"
                            value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              GUID="c2e78146-833b-4d78-a055-27f05eeaf018"
                              name="aktuelle-version-id"
                              value="53d31e3c-5c46-4e96-8017-b0db064561a1"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
                              GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c"
                              name="nachfolgende-version-id"
                              value="5d84cd1d-3575-4a03-bb6c-f17834e392fd"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               GUID="6aace8d0-001f-4829-9794-79857835ee45"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             GUID="a6dc32d0-140d-4147-88b2-d64639b91742"
                             date="2024-03-27"
                             name="verkuendung"/>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                 GUID="b872861e-0add-4bbb-ba90-a5edb3c40038"
                                 language="deu"/>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"
                                      GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc"
                                      value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                   GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                             GUID="4c881fca-0e36-440e-a5f9-513079ccc77f"
                             value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/2024-03-27/regelungstext-1.xml"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            GUID="d0491f91-d599-46d4-ba61-c7426b607e4f"
                            value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/2024-03-27/regelungstext-1.xml"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612"
                             date="2024-03-27"
                             name="generierung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                               GUID="1d015a20-823e-496e-808d-27c8fec85b51"
                               href="recht.bund.de"/>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                               GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67"
                               value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle eId="meta-1_lebzykl-1"
                        GUID="cef30a63-d738-4712-bac8-50fda44949e2"
                        source="attributsemantik-noch-undefiniert">
                <!-- 1. Originales Gesetz -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1"
                          GUID="65f3910d-4e79-45f8-a24d-44ef7523963a"
                          date="2024-03-27"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="ausfertigung"/>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
                          GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c"
                          date="2024-08-24"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="inkrafttreten"/>
            <!-- 2. -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3"
                          GUID="85e92fc8-b381-47bb-a476-c35fa2794336"
                          date="2024-10-01"
                          source="attributsemantik-noch-undefiniert"
                          type="amendment"
                          refersTo="inkrafttreten"/>
         </akn:lifecycle>
         <akn:analysis source="attributsemantik-noch-undefiniert"
                       eId="meta-1_analysis-1"
                       GUID="4cc2a228-5c5e-44bb-b19d-e6b145efe69b">
            <akn:activeModifications eId="meta-1_analysis-1_activemod-1"
                                     GUID="127e593d-6ce7-4ee1-8c1d-8ab537593a25">
               <akn:textualMod type="substitution"
                               GUID="de2d69cd-691b-4b61-b1fb-db471d9f52aa"
                               eId="meta-1_analysis-1_activemod-1_textualmod-1">
                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                              href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                              GUID="c42641b3-e987-4013-b713-52017ca0ca78"/>
                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                                   href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_art-27_abs-6_inhalt-1_text-1/302-338.xml"
                                   GUID="e99747be-63b9-4729-adf3-b4b4299e188b"/>
                  <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2"
                             eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                             GUID="362f30ad-343c-4b8e-8bb4-993c8a0f5256"/>
               </akn:textualMod>
               <akn:textualMod type="substitution"
                               GUID="a9447c63-fd78-44b9-9426-48ea9e86cf68"
                               eId="meta-1_analysis-1_activemod-1_textualmod-2">
                  <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1"
                              href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                              GUID="c509c75e-4813-491a-a973-9b23e2b8be03"/>
                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1"
                                   href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_art-71_abs-1_inhalt-1_text-1"
                                   upTo=""
                                   GUID="e1d7e59f-5c59-4834-847d-3dc1dbc26259"/>
                  <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2"
                             eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1"
                             GUID="5d66169e-3af1-453b-8009-b5d62ad16aee"/>
               </akn:textualMod>
            </akn:activeModifications>
         </akn:analysis>
         <akn:temporalData eId="meta-1_geltzeiten-1"
                           GUID="58a31120-e277-4a33-a093-6a3637fd603d"
                           source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1"
                               GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                 GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16"
                                 start="#meta-1_lebzykl-1_ereignis-2"
                                 refersTo="geltungszeit"/>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2"
                               GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1"
                                 GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f"
                                 start="#meta-1_lebzykl-1_ereignis-3"
                                 refersTo="geltungszeit"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1"
                          GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7"
                          source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>mantelform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
               <meta:fna>754-28-1</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>
      <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
         <akn:longTitle eId="einleitung-1_doktitel-1"
                        GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
            <akn:p eId="einleitung-1_doktitel-1_text-1"
                   GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1"
                             GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Gesetz zur Stärkung von Wachstumschancen, Investitionen und Innovation sowie Steuervereinfachung und Steuerfairness

                        <!-- Fußnote -->
                        <akn:authorialNote eId="einleitung-1_doktitel-1_text-1_doctitel-1_amtlfnote-1"
                                     GUID="9de7999f-a222-43ac-a648-f92c5a8ee927"
                                     marker="*)"
                                     placement="bottom"
                                     placementBase="">
                     <akn:p eId="einleitung-1_doktitel-1_text-1_doctitel-1_amtlfnote-1_text-1"
                            GUID="7c210663-00e0-48c1-b86d-d02f1c422de0">Artikel 31 dient der Umsetzung der Richtlinie (EU) 2021/514 des Rates vom 22. März 2021 zur Änderung der Richtlinie 2011/16/EU über die
                                Zusammenarbeit der Verwaltungsbehörden im Bereich der Besteuerung (ABl. L 104 vom 25.3.2021, S. 1).
                            </akn:p>
                  </akn:authorialNote>
               </akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1"
                               GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">(Wachstumschancengesetz)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
         <akn:block eId="einleitung-1_block-1"
                    GUID="a0973d49-d628-42f7-a1da-b004bc980a44"
                    name="attributsemantik-noch-undefiniert">
            <akn:date eId="einleitung-1_block-1_datum-1"
                      GUID="f20d437a-3058-4747-8b8b-9b1e06c17273"
                      refersTo="ausfertigung-datum"
                      date="2024-03-27">Vom 27.03.2024</akn:date>
         </akn:block>
      </akn:preface>
      <akn:preamble eId="preambel-1" GUID="7eae9fd3-d601-40ba-a4a1-f9416d89e586">
         <akn:formula eId="preambel-1_formel-1"
                      GUID="a7bbd756-c50a-4944-8f64-49134e1166cc"
                      refersTo="eingangsformel"
                      name="attributsemantik-noch-undefiniert">
            <akn:p eId="preambel-1_formel-1_text-1"
                   GUID="7e0cf2ac-b45a-40b8-8369-c5013ada9f48"> Der <akn:organization eId="preambel-1_formel-1_text-1_org-1"
                                 GUID="05004de4-44d4-4e59-a1ef-5912003a6f36"
                                 refersTo="attributsemantik-noch-undefiniert"
                                 title="Bundestag">Bundestag</akn:organization> hat
                    das folgende Gesetz beschlossen:</akn:p>
         </akn:formula>
      </akn:preamble>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="ce0ac10d-510e-4148-8a8d-a4406a8b0a67">
            <!-- §1 -->
         <akn:article eId="hauptteil-1_art-1"
                      GUID="17e9c25e-251d-48a7-8e74-3f0bb4b93b6b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-2"
                      refersTo="hauptaenderung">
            <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                     GUID="24f5f556-e005-4d10-8d7e-d92f141780d7">
                    §1</akn:num>
            <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                         GUID="31b97e2a-1872-4a52-aba1-d075bae3c0d6">Änderung des Einkommensteuergesetzes</akn:heading>
            <!-- Absatz 1 -->
            <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                           GUID="58ea2242-386a-42b9-8f23-eaa1678159f1">
               <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1"
                        GUID="c38a41af-4911-4b1a-9404-929912af983e">
                        (1)</akn:num>
               <akn:list GUID="5d6fc824-7926-43b4-b243-b0096da183f8"
                         eId="hauptteil-1_art-1_abs-1_untergl-1">
                  <akn:intro GUID="5d6fc824-7926-43b4-b243-b0096da183f9"
                             eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1">
                     <akn:p GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"
                            eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1"> Das <akn:affectedDocument GUID="e9e5ceb0-c831-45da-8fa4-aaaa16572bee"
                                              eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                              href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1">Einkommensteuergesetz in der Fassung der Bekanntmachung vom 8. Oktober 2009 (BGBl. I S. 3366, 3862),
                                das zuletzt durch Artikel 20 des Gesetzes vom 22. Dezember 2023 (BGBl. 2023 I Nr. 411) geändert worden ist</akn:affectedDocument>, wird
                                wie folgt geändert:</akn:p>
                  </akn:intro>
                  <akn:point GUID="49983c1a-c952-4ab1-b926-2f414c05da7d"
                             eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
                     <akn:num GUID="634ddcaa-4851-4656-8b65-f26c8f56d1f1"
                              eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                1.
                            </akn:num>
                     <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1"
                                  GUID="63c7840e-f93f-497a-843f-3afbd9ec8784">
                        <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"
                               GUID="a8786a31-bf59-41f9-b945-7785ed93861a">
                           <akn:mod refersTo="aenderungsbefehl-ersetzen"
                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                                    GUID="a8786a31-bf59-41f9-b945-7785ed93861b">
                                        In <akn:ref
                                   href="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_art-27_abs-6_inhalt-1_text-1/302-338.xml"
                                       eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1"
                                       GUID="a8786a31-bf59-41f9-b945-7785ed93861c">§ 6b Absatz 6 Satz 2</akn:ref> werden die Wörter „<akn:quotedText startQuote="„"
                                              endQuote="“"
                                              eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                              GUID="a8786a31-bf59-41f9-b945-7785ed93861d">§ 7 Absatz 4 Satz 1, Absatz 5 und 5a</akn:quotedText>“ durch die Wörter „<akn:quotedText startQuote="„"
                                              endQuote="“"
                                              eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                              GUID="a8786a31-bf59-41f9-b945-7785ed93861f">§ 7 Absatz 4 Satz 1 und Absatz 5</akn:quotedText>“ ersetzt.
                                    </akn:mod>
                        </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="4bf3aae7-b83c-4429-8518-040182a228d5"
                             eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2">
                     <akn:num GUID="81e4878a-ad3a-4ec9-b32a-074c32367d12"
                              eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                     <akn:content GUID="e447e69e-5b1f-4880-b0d9-04a596ab2a01"
                                  eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="0d881f49-5342-4629-9874-fc4c248b630a"
                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                           <akn:mod refersTo="aenderungsbefehl-ersetzen"
                                    GUID="58161d8d-b02e-4d54-a362-25a734c5e79b"
                                    eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1">
                              <akn:rref GUID="9ba5f8e5-7cf9-4333-b83a-70c59d89a6a9"
                                        eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_bref-1"
                                        from="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1/hauptteil-1_art-71_abs-1_inhalt-1_text-1"
                                        upTo="">
                                            § 19 Absatz 2 Satz 3 wird wie folgt gefasst:</akn:rref>
                              <akn:quotedStructure endQuote="“"
                                                   startQuote="„"
                                                   GUID="2785f9b5-51ee-4876-af06-17219ddd9351"
                                                   eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                                 <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_text-1"
                                        GUID="0d7a4336-d505-4125-bdb5-13987833ce75">Der  maßgebende  Prozentsatz,  der  Höchstbetrag  des  Versorgungsfreibetrags  und  der  Zuschlag  zum
                                            Versorgungsfreibetrag sind der nachstehenden Tabelle zu entnehmen:</akn:p>
                                 <akn:table GUID="e6095f0b-884b-4be8-a033-58282d5dd514"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1">
                                    <akn:tr GUID="990c5783-9b51-4062-87df-e7c73c6b3598"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1">
                                       <akn:th GUID="1b3dfb9b-41cf-4650-aad6-f5c250d71c26"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-1"
                                               rowspan="2">
                                          <akn:p GUID="63cbbdb0-a993-4c76-bfe1-06176e954df1"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Jahr des Versorgungsbeginns</akn:p>
                                       </akn:th>
                                       <akn:th GUID="e1849850-f1bc-485e-b9cb-ecd2ac14a513"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                                          <akn:p GUID="e25d8ec3-3470-4778-beae-bb23f26ff94e"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">Versorgungsfreibetrag</akn:p>
                                       </akn:th>
                                       <akn:th GUID="1512c9fd-fe29-4363-915f-f1b34f022c4f"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-3"
                                               rowspan="2">
                                          <akn:p GUID="5ffcdd71-953f-4035-9f4a-cc82038f1932"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-1_tabellekopf-3_text-1">Zuschlag zum Versorgungsfreibetrag in Euro</akn:p>
                                       </akn:th>
                                    </akn:tr>
                                    <akn:tr GUID="ce0a57b3-bcd4-4a51-bd34-bd3df5ea5e54"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2">
                                       <akn:th GUID="82a5507b-1c59-4989-9810-ea39897b3142"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-1">
                                          <akn:p GUID="f502b5ab-d7af-49a2-88ed-1cb7c380387a"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-1_text-1">in % der Versorgungsbezüge</akn:p>
                                       </akn:th>
                                       <akn:th GUID="a4b33640-a58f-4fa5-9286-c6fc3a4521dc"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-2">
                                          <akn:p GUID="44c877d7-4e7b-427a-9c69-58dfeaddea39"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-2_tabellekopf-2_text-1">Höchstbetrag in Euro</akn:p>
                                       </akn:th>
                                    </akn:tr>
                                    <akn:tr GUID="c7b2cdea-4ac3-4af7-9d7d-29345f1d6ed8"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3">
                                       <akn:td GUID="6135164c-082f-4f25-8146-39b2c7851f8d"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                                          <akn:p GUID="1daba036-230a-4595-aca2-c72faa0dbfaf"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">bis 2005</akn:p>
                                       </akn:td>
                                       <akn:td GUID="4b1822c2-86f2-499b-9e15-3e238a9475e5"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                                          <akn:p GUID="47f2763e-83f9-4a8e-81e3-188ab8ce46c6"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">40,0</akn:p>
                                       </akn:td>
                                       <akn:td GUID="ff209893-13c7-4e15-a401-dd660be8c527"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-3">
                                          <akn:p GUID="95dffc04-b963-4f63-b39d-2da5ba650654"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-3_text-1">3 000</akn:p>
                                       </akn:td>
                                       <akn:td GUID="3972a0d5-7c54-4bcc-9c3c-e59630f840b0"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-4">
                                          <akn:p GUID="9834fccb-d13e-4ac5-a2ff-dd242fe0f763"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-3_tabelleinh-4_text-1">900</akn:p>
                                       </akn:td>
                                    </akn:tr>
                                    <akn:tr GUID="b671a30c-dc1a-4898-970d-e371abe9aed9"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4">
                                       <akn:td GUID="fad8cef3-00be-4d74-b23e-dcafa2516bf2"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-1">
                                          <akn:p GUID="a2a1f303-9c82-4c6c-a95f-35f01941029f"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1">ab 2006</akn:p>
                                       </akn:td>
                                       <akn:td GUID="e3c06299-3855-41e7-995a-f395d7f84461"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-2">
                                          <akn:p GUID="85ae462a-e205-4f7e-8532-4b993b5ea12e"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1">38,4</akn:p>
                                       </akn:td>
                                       <akn:td GUID="aff4aa39-7b78-4f69-8004-a95a88c4fa9c"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-3">
                                          <akn:p GUID="08bc3ade-e920-438b-8448-7fcedb6192fa"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-3_text-1">2 880</akn:p>
                                       </akn:td>
                                       <akn:td GUID="1180c599-4219-4f91-a2a0-e5bf5754430d"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-4">
                                          <akn:p GUID="7a10ff40-0025-4622-97d4-0333473653f2"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-4_tabelleinh-4_text-1">864</akn:p>
                                       </akn:td>
                                    </akn:tr>
                                    <akn:tr GUID="458a49d1-b3ea-46bd-8051-10960c0f4094"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5">
                                       <akn:td GUID="dd0ed33d-b834-4e5a-906c-59474385394d"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-1">
                                          <akn:p GUID="93656d01-643a-4a9a-8f9c-de0b85f43955"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1">2007</akn:p>
                                       </akn:td>
                                       <akn:td GUID="3fd9bac2-803f-42fc-938f-cbd6b58bc833"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-2">
                                          <akn:p GUID="23c53d5e-8217-4d29-a099-2575c85ae7dc"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1">36,8</akn:p>
                                       </akn:td>
                                       <akn:td GUID="22bea0e1-db96-4694-98c7-c2d2574b2263"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-3">
                                          <akn:p GUID="257a1968-fe04-4180-a8cb-18c825f1c836"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-3_text-1">2 760</akn:p>
                                       </akn:td>
                                       <akn:td GUID="5e6ed51d-48da-44e9-b8bd-7453929bef81"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-4">
                                          <akn:p GUID="22751007-c55f-41c4-89ae-b7a3acef841a"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-5_tabelleinh-4_text-1">828</akn:p>
                                       </akn:td>
                                    </akn:tr>
                                    <akn:tr GUID="23fd3a0c-b7ff-471c-a6b2-10e2f25b8b5d"
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6">
                                       <akn:td GUID="037d324d-67dd-4c6c-b74f-0da3c094d7f3"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-1">
                                          <akn:p GUID="07be3174-ab04-4ae6-a8f6-0c5652ffe1ea"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1">2008</akn:p>
                                       </akn:td>
                                       <akn:td GUID="16acddc4-a085-43be-b413-a9c7240632c0"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-2">
                                          <akn:p GUID="0c5a3e05-7a6c-43cd-b19e-c748e2046e02"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1">35,2</akn:p>
                                       </akn:td>
                                       <akn:td GUID="82745578-4b7e-48e3-ba66-a5e80caa6e5f"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-3">
                                          <akn:p GUID="84690030-0b62-409e-acee-67b422fb4e1e"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-3_text-1">2 640</akn:p>
                                       </akn:td>
                                       <akn:td GUID="8e897241-573d-4b7d-87a3-3fbfaba46602"
                                               eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-4">
                                          <akn:p GUID="88ec824e-0b3c-4e6e-9e67-93b2c9ba0c07"
                                                 eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_tabelle-1_tabellereihe-6_tabelleinh-4_text-1">792</akn:p>
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
         <!-- §2-->
         <akn:article eId="hauptteil-1_art-2"
                      GUID="1af21459-b9df-47aa-a084-435df14c546b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                      refersTo="geltungszeitregel">
            <akn:num eId="hauptteil-1_art-2_bezeichnung-1"
                     GUID="518b00f3-3f1c-41b2-8075-3bc644ed58b4">
                    §2</akn:num>
            <akn:paragraph GUID="7c715b4f-67d0-4665-bc12-de453e8a7119"
                           eId="hauptteil-1_art-2_abs-1">
               <akn:num eId="hauptteil-1_art-2_abs-1_bezeichnung-1"
                        GUID="8373fc61-cc1b-4588-aa1f-8c29a2bc644e">
                        (1)</akn:num>
               <akn:list GUID="0af5ebc9-805e-40d7-b5c2-392977595974"
                         eId="hauptteil-1_art-2_abs-1_untergl-1">
                  <akn:intro GUID="59c94bfd-0014-44df-a916-35254a314936"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1">
                     <akn:p GUID="82015b74-1c1d-4354-a7d8-482a904bbe14"
                            eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1_text-1"/>
                  </akn:intro>
                  <akn:point GUID="9a8983f6-c5a5-4554-8231-cfd33319e0a9"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1">
                     <akn:num GUID="e48171db-d319-4b3c-8b6f-a305e401d87f"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_bezeichnung-1">(1)</akn:num>
                     <akn:content GUID="86f1d04f-cf83-4851-a463-83753ba33208"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_inhalt-1">
                        <akn:p GUID="d917836c-1926-479d-b14f-824dd03d3633"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                Dieses Gesetz tritt vorbehaltlich der Absätze 2 bis 9 am Tag nach der Verkündung in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="f61ff441-1986-481d-894e-94e9e250519e"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2">
                     <akn:num GUID="cbbf73d6-c5bc-4f04-80d0-bee5183a5168"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_bezeichnung-1">(2)</akn:num>
                     <akn:content GUID="d89f2a49-aee7-4ca8-9d56-4b69f7de13b4"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="aa246a74-02d6-420c-89b4-6375d86547ff"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                Artikel 26 tritt mit Wirkung vom 1. Januar 2020 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="c32b1cd9-e6f1-4bbe-aed4-9c19c9dd5bf6"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3">
                     <akn:num GUID="39e1f85f-f341-45c4-a46e-90cd72665f67"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_bezeichnung-1">(3)</akn:num>
                     <akn:content GUID="30323067-5d43-4859-8f98-3d5c6ce98fe5"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1">
                        <akn:p GUID="1d983209-4248-4f09-ad59-e78fc4c8fd83"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                Die Artikel 1 und 7 treten mit Wirkung vom 1. Januar 2023 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="4c623505-79dd-48d2-91e6-b9a658c01b3f"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4">
                     <akn:num GUID="9ececa14-a57d-45bf-89c8-141c42d48368"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_bezeichnung-1">(4)</akn:num>
                     <akn:content GUID="96e7836c-a6b9-4f70-93dc-87073d22ed40"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1">
                        <akn:p GUID="c8425bf7-47c2-4da7-9904-1e1a72bb7586"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                Die Artikel 2, 12, 20, 31 und 32 Nummer 2 treten mit Wirkung vom 1. Januar 2024 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="db3679b0-8b1e-44eb-a15e-cfabca814a21"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-5">
                     <akn:num GUID="6a285e06-75c5-4070-bb47-cff4cb5c26a5"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-5_bezeichnung-1">(5)</akn:num>
                     <akn:content GUID="f0f6f73d-6d93-43e1-84c4-798d56ed716c"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-5_inhalt-1">
                        <akn:p GUID="edefafec-29f3-475f-a03b-69d216a42d93"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">
                                Die Artikel 4 und 22 treten am 1. April 2024 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="45f70562-f0c9-46ae-bffb-d4d2e763629a"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-6">
                     <akn:num GUID="ce2a08f8-1099-4cff-9ed3-4bb4bd386434"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-6_bezeichnung-1">(6)</akn:num>
                     <akn:content GUID="48da3995-398d-478e-9aa9-5a0ce3904442"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-6_inhalt-1">
                        <akn:p GUID="0a7edbf9-612c-43ad-b728-d8e37e2e6c68"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-6_inhalt-1_text-1">
                                Die Artikel 5, 9, 23, 24 und 27 Nummer 6 treten am 1. Januar 2025 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="8f6b3b22-c532-429b-b674-220ee7372c12"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-7">
                     <akn:num GUID="ad245874-a7bd-41d5-97d3-d316a417bc9a"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-7_bezeichnung-1">(7)</akn:num>
                     <akn:content GUID="5f406ea7-324c-4a90-ba13-6d9d239e8ee9"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-7_inhalt-1">
                        <akn:p GUID="d38908ad-6bbd-4d1a-88e6-f697ec00cf12"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-7_inhalt-1_text-1">
                                Artikel 32 Nummer 3 und 4 tritt am 1. Juli 2025 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="61b0fc82-738c-4ebb-bdff-9f1eea4e96b9"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-8">
                     <akn:num GUID="634aa27a-df2f-4bb6-9fab-418965ae7217"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-8_bezeichnung-1">(8)</akn:num>
                     <akn:content GUID="3d7a8657-95eb-4e6d-9ad0-53e7e31883bc"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-8_inhalt-1">
                        <akn:p GUID="dd2b467b-54b3-48e3-8e62-cf232df38975"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-8_inhalt-1_text-1">
                                Die Artikel 14 und 16 treten am 1. Januar 2027 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="545da796-e05b-491e-a64d-c4ed9ee5fb09"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-9">
                     <akn:num GUID="c5174939-4a22-49b4-8e91-f29f25997c75"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-9_bezeichnung-1">(9)</akn:num>
                     <akn:content GUID="2430d65b-f2f5-4cf9-a78d-997de8bb1bb1"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-9_inhalt-1">
                        <akn:p GUID="e37f15d9-0e99-4719-948b-d18a0945f0ac"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-9_inhalt-1_text-1">
                                Artikel 6 tritt am 1. Januar 2028 in Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="1861791b-36e7-4742-8e81-909e63735de5"
                             eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-10">
                     <akn:num GUID="569c8de9-11ba-45eb-8c09-d325e6a9f373"
                              eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-10_bezeichnung-1">(10)</akn:num>
                     <akn:content GUID="d199a332-8e7d-46c6-b3c5-84cce841fc29"
                                  eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-10_inhalt-1">
                        <akn:p GUID="29b55044-6570-485f-97d1-7db6e17ec6c7"
                               eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-10_inhalt-1_text-1">
                                Die §§ 124 und 125 des Vierten Buches Sozialgesetzbuch, § 202a des Fünften Buches Sozialgesetzbuch
                                sowie § 55b Absatz 2 des Elften Buches Sozialgesetzbuch treten am 1. Juli 2026 außer Kraft.
                            </akn:p>
                     </akn:content>
                  </akn:point>
               </akn:list>
            </akn:paragraph>
         </akn:article>
      </akn:body>
      <!-- Schluss -->
      <akn:conclusions eId="schluss-1" GUID="5814d07a-3869-489d-b03e-239304e841cb">
         <akn:formula eId="schluss-1_formel-1"
                      GUID="a269a1a9-e8ca-471a-a9d4-ce61e9af05aa"
                      name="attributsemantik-noch-undefiniert"
                      refersTo="schlussformel">
            <akn:p eId="schluss-1_formel-1_text-1"
                   GUID="1354658b-fdb7-46b4-96e2-e194b16ee103">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
         </akn:formula>
         <akn:blockContainer eId="schluss-1_blockcontainer-1"
                             GUID="35e11af5-ff7c-4422-af51-87cff3cc7ceb">
            <akn:p eId="schluss-1_blockcontainer-1_text-1"
                   GUID="b55d6230-d71c-49d5-bf86-dab1db53ba6f">
               <akn:location eId="schluss-1_blockcontainer-1_text-1_ort-1"
                             GUID="19ffc7e1-add6-40a4-b9af-2f0927e46e9c"
                             refersTo="attributsemantik-noch-undefiniert">Bonn</akn:location>, den <akn:date eId="schluss-1_blockcontainer-1_text-1_datum-1"
                         GUID="34cf44aa-73a4-4dee-9827-4d890da7aac7"
                         refersTo="ausfertigung-datum"
                         date="1964-08-05">5. August 1964</akn:date>
            </akn:p>
            <akn:signature eId="schluss-1_blockcontainer-1_signatur-1"
                           GUID="b092cd1d-2f77-4fe6-a63d-5bcabeb410ad">
               <akn:role eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1"
                         GUID="1cd9ff70-4004-48dc-8c76-7fb295852717"
                         refersTo="attributsemantik-noch-undefiniert">Der Bundespräsident</akn:role>
               <akn:person eId="schluss-1_blockcontainer-1_signatur-1_person-1"
                           GUID="3d296b9f-6b7d-4c34-a091-23291a39917a"
                           refersTo="attributsemantik-noch-undefiniert">Lübke</akn:person>
            </akn:signature>
         </akn:blockContainer>
      </akn:conclusions>
   </akn:act>
</akn:akomaNtoso>');

UPDATE norm_manifestation SET publish_state = 'PUBLISHED' WHERE eli_norm_expression = 'eli/bund/bgbl-1/2024/108/2024-03-27/1/deu';
