-- REAPPLY
-- TARGET LAW
DELETE
FROM norms
where eli_expression = 'eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1';

INSERT INTO norms (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
-->
<?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.7.1/ ../metadata.xsd">
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
                             value="eli/bund/bgbl-1/2009/s3366/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            GUID="a90733dd-4780-472d-8371-a3690f2e869a"
                            value="eli/bund/bgbl-1/2009/s3366"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b"
                              name="übergreifende-id"
                              value="2c1a6ec9-7161-4e2f-8999-91219180d992"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811"
                             date="2009-10-08"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e"
                                value="de"/>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5"
                               value="s3366"/>
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
                             value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            GUID="e8f52739-79c1-4e59-9db2-004180c8b29c"
                            value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              GUID="c2e78146-833b-4d78-a055-27f05eeaf018"
                              name="aktuelle-version-id"
                              value="53d31e3c-5c46-4e96-8017-b0db064561a2"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               GUID="6aace8d0-001f-4829-9794-79857835ee45"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             GUID="a6dc32d0-140d-4147-88b2-d64639b91742"
                             date="2023-12-23"
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
                             value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/2023-12-23/regelungstext-1.xml"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            GUID="d0491f91-d599-46d4-ba61-c7426b607e4f"
                            value="eli/bund/bgbl-1/2009/s3366/2023-12-23/1/deu/2023-12-23/regelungstext-1.xml"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612"
                             date="2023-12-23"
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
                          date="1934-10-16"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="ausfertigung"/>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
                          GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c"
                          date="1934-10-16"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="inkrafttreten"/>
            <!-- Neufassung -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3"
                          GUID="85e92fc8-b381-47bb-a476-c35fa2794336"
                          date="2009-10-08"
                          source="attributsemantik-noch-undefiniert"
                          type="amendment"
                          refersTo="neufassung"/>
            <!-- Letzte Änderung -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4"
                          GUID="85e92fc8-b381-47bb-a476-c35fa2794337"
                          date="2023-12-24"
                          source="attributsemantik-noch-undefiniert"
                          type="amendment"
                          refersTo="inkrafttreten"/>
         </akn:lifecycle>
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
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3"
                               GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c5">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1"
                                 GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac755"
                                 start="#meta-1_lebzykl-1_ereignis-4"
                                 refersTo="geltungszeit"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1"
                          GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7"
                          source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.1/">
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
               <meta:federfuehrung>
                  <meta:federfuehrend ab="1934-10-16" bis="unbestimmt">BMF - Bundesministerium der Finanzen</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/">
               <ris:subtyp>SN</ris:subtyp>
            </ris:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>
      <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
         <akn:longTitle eId="einleitung-1_doktitel-1"
                        GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
            <akn:p eId="einleitung-1_doktitel-1_text-1"
                   GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1"
                             GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Einkommensteuergesetz
               </akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1"
                               GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">
                  <akn:inline refersTo="amtliche-abkuerzung"
                              name="attributsemantik-noch-undefiniert"
                              eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1"
                              GUID="3d5ecf29-792c-4854-99e4-0cf18b5c0c9c">EStG</akn:inline>
               </akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="7b7416d7-35a0-41cf-902f-583b9cf51a26">
         <akn:article eId="hauptteil-1_art-1"
                      GUID="e9a8d5e4-77b8-447f-9227-63f687a31f8f"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                     GUID="049c11bd-5ed2-4423-a7c3-54dce98c414d">
                     §1</akn:num>
            <akn:paragraph GUID="abd48a54-ccc6-4cb9-8627-9d7092b5263f"
                           eId="hauptteil-1_art-1_abs-1">
               <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1"
                        GUID="375c9de0-2d35-447b-b9ce-a6e0243b81a2">
                        (1)</akn:num>
               <akn:content GUID="a1609cb8-1045-4e45-a6f9-c7bdf4187249"
                            eId="hauptteil-1_art-1_abs-1_inhalt-1">
                  <akn:p GUID="74b7302d-a4fc-4f69-a362-97a3ed8c4e77"
                         eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-2"
                      GUID="4b09bb18-3bda-4603-a0a3-b62083262a8a"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-2_bezeichnung-1"
                     GUID="42e575d9-f64a-4ad5-85d1-47f134576923">
                     §1</akn:num>
            <akn:paragraph GUID="a0271610-a340-4e5c-b592-4b60ba92a657"
                           eId="hauptteil-1_art-2_abs-1">
               <akn:num eId="hauptteil-1_art-2_abs-1_bezeichnung-1"
                        GUID="ff409ec8-0df2-40cf-8605-d7cca3052297">
                        (1)</akn:num>
               <akn:content GUID="cddf665c-ac79-4e28-bc12-7adb861a840c"
                            eId="hauptteil-1_art-2_abs-1_inhalt-1">
                  <akn:p GUID="800c9235-db46-493d-a64c-c1b5de499927"
                         eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-3"
                      GUID="dcf7124f-ae5b-4a0e-9bc9-d2df8bed7016"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-3_bezeichnung-1"
                     GUID="0ca92be9-2775-4848-a942-0d96397b6a3f">
                     §1a</akn:num>
            <akn:paragraph GUID="519025c1-72da-42a2-87a9-056c27ca72ae"
                           eId="hauptteil-1_art-3_abs-1">
               <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1"
                        GUID="3811c99a-49e8-4672-8274-01296a218392">
                        (1)</akn:num>
               <akn:content GUID="6f4b3b85-3c26-44e6-afde-1b8cf2dbbd75"
                            eId="hauptteil-1_art-3_abs-1_inhalt-1">
                  <akn:p GUID="55cc5a1c-cee3-4972-9fb8-e3082f3bc6e6"
                         eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-4"
                      GUID="11f31a4b-36f5-4163-b5a0-528087d9072d"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-4_bezeichnung-1"
                     GUID="e3ee7c20-d068-424e-acd2-4444c3a59e43">
                     §2</akn:num>
            <akn:paragraph GUID="b0d3737b-4055-4c02-a758-cda1a41542cc"
                           eId="hauptteil-1_art-4_abs-1">
               <akn:num eId="hauptteil-1_art-4_abs-1_bezeichnung-1"
                        GUID="2524812c-b34c-42fc-ab21-899c83aa3986">
                        (1)</akn:num>
               <akn:content GUID="c490f063-95f1-4617-84ec-3889a6a44965"
                            eId="hauptteil-1_art-4_abs-1_inhalt-1">
                  <akn:p GUID="5694f377-df54-4e19-88ef-93175d6eb4a7"
                         eId="hauptteil-1_art-4_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-5"
                      GUID="e253b2d4-f268-46fd-a8ff-79d3ee8d6184"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-5_bezeichnung-1"
                     GUID="4161e762-5411-4b8a-86d5-32a5c3880b12">
                     §2a</akn:num>
            <akn:paragraph GUID="4988d145-1105-4008-ae2e-dfc8ff0a7536"
                           eId="hauptteil-1_art-5_abs-1">
               <akn:num eId="hauptteil-1_art-5_abs-1_bezeichnung-1"
                        GUID="1284cd07-2751-4f40-a1da-4cba63d4b375">
                        (1)</akn:num>
               <akn:content GUID="966d355b-246e-4d21-8006-0662496fc52f"
                            eId="hauptteil-1_art-5_abs-1_inhalt-1">
                  <akn:p GUID="3c6ab1cd-14fd-4db8-ac00-964e5102b02f"
                         eId="hauptteil-1_art-5_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-6"
                      GUID="5d51449c-88e9-4411-ab65-e754e5eedf4d"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-6_bezeichnung-1"
                     GUID="243f406d-cd7d-4e7a-8e99-3277133b2a58">
                     §3</akn:num>
            <akn:paragraph GUID="d0e332d8-c3b2-47db-8904-6d03452ca2b8"
                           eId="hauptteil-1_art-6_abs-1">
               <akn:num eId="hauptteil-1_art-6_abs-1_bezeichnung-1"
                        GUID="adfab363-827a-4a34-be0e-c7584ee23dbe">
                        (1)</akn:num>
               <akn:content GUID="f2d23018-7d93-40d7-a5df-c981db4a4534"
                            eId="hauptteil-1_art-6_abs-1_inhalt-1">
                  <akn:p GUID="029568a7-2147-487a-9ad4-5df622b67232"
                         eId="hauptteil-1_art-6_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-7"
                      GUID="9f671cd2-bf8c-4ca3-adc3-dd9cd3cf5478"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-7_bezeichnung-1"
                     GUID="cb798a77-b613-4e93-b9b6-24ac18a3810d">
                     §3a</akn:num>
            <akn:paragraph GUID="c7032ec4-11ce-4473-886c-9340c19bce13"
                           eId="hauptteil-1_art-7_abs-1">
               <akn:num eId="hauptteil-1_art-7_abs-1_bezeichnung-1"
                        GUID="31464e5b-4298-4fd7-8c2d-4963e5b154e8">
                        (1)</akn:num>
               <akn:content GUID="b4a15eb8-63cc-4a0b-aa74-38a60b2847fb"
                            eId="hauptteil-1_art-7_abs-1_inhalt-1">
                  <akn:p GUID="5fcffa08-6a7a-4f13-8103-1c110fe49ec9"
                         eId="hauptteil-1_art-7_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-8"
                      GUID="9fa71e02-b0b6-429f-96fc-34aad39a0f21"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-8_bezeichnung-1"
                     GUID="7887a4de-740b-4ed7-ab4b-61dea64d4acd">
                     §3b</akn:num>
            <akn:paragraph GUID="68e04f75-528e-4e1c-bbaf-803139717cda"
                           eId="hauptteil-1_art-8_abs-1">
               <akn:num eId="hauptteil-1_art-8_abs-1_bezeichnung-1"
                        GUID="460de7b9-d739-4324-bde9-aeccce7d59d3">
                        (1)</akn:num>
               <akn:content GUID="044dfd14-cae9-48c2-964b-7da039d9d1ca"
                            eId="hauptteil-1_art-8_abs-1_inhalt-1">
                  <akn:p GUID="d415fb58-cdc0-4cd6-a293-4e8b980bae54"
                         eId="hauptteil-1_art-8_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-9"
                      GUID="ae491455-e299-409a-928b-b47cbb04040b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-9_bezeichnung-1"
                     GUID="4d53114a-f191-4600-8f28-5dba72cdd003">
                     §3c</akn:num>
            <akn:paragraph GUID="6ef7bb31-8d25-43ca-9c4c-0ccff9072392"
                           eId="hauptteil-1_art-9_abs-1">
               <akn:num eId="hauptteil-1_art-9_abs-1_bezeichnung-1"
                        GUID="3340cdfa-b701-49c5-9425-6e0d60e028a7">
                        (1)</akn:num>
               <akn:content GUID="0488e21a-2f30-41fe-981f-d7bc4c5f021b"
                            eId="hauptteil-1_art-9_abs-1_inhalt-1">
                  <akn:p GUID="fb2277f3-115b-4194-ba46-5bf2c1d075f9"
                         eId="hauptteil-1_art-9_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-10"
                      GUID="ca31c6f1-432d-4411-b3ca-7f771632ed35"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-10_bezeichnung-1"
                     GUID="41ccf863-0dac-459c-8794-31da0bc36604">
                     §4</akn:num>
            <akn:paragraph GUID="cb830efc-607a-4ebf-bc2c-567428ac75b1"
                           eId="hauptteil-1_art-10_abs-1">
               <akn:num eId="hauptteil-1_art-10_abs-1_bezeichnung-1"
                        GUID="85b961f5-d462-4a75-8f33-f2662b844e9a">
                        (1)</akn:num>
               <akn:content GUID="b7b65065-40eb-43f3-a524-8cecbf4a2424"
                            eId="hauptteil-1_art-10_abs-1_inhalt-1">
                  <akn:p GUID="8140032e-77b5-4890-b39e-63a66147a1fc"
                         eId="hauptteil-1_art-10_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-11"
                      GUID="7348fc55-e767-4d23-ab05-31a4e7ae470c"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-11_bezeichnung-1"
                     GUID="08753f4f-ec16-4cbc-91d8-9057cb06ba4c">
                     §4a</akn:num>
            <akn:paragraph GUID="429c9203-e0e0-469f-8eed-760997f38cdb"
                           eId="hauptteil-1_art-11_abs-1">
               <akn:num eId="hauptteil-1_art-11_abs-1_bezeichnung-1"
                        GUID="0bb680a0-49fb-4ba6-b7ee-24da6d3024e1">
                        (1)</akn:num>
               <akn:content GUID="2189b6d1-244f-4158-9858-6dd967636c6a"
                            eId="hauptteil-1_art-11_abs-1_inhalt-1">
                  <akn:p GUID="0248aafc-b33f-44c3-b776-777086e865b8"
                         eId="hauptteil-1_art-11_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-12"
                      GUID="8ba4d8a5-9140-44fb-b7eb-922bde2f77d0"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-12_bezeichnung-1"
                     GUID="c918ccd0-08ab-48e7-988b-f18f09a6ec40">
                     §4b</akn:num>
            <akn:paragraph GUID="bd6b33a9-84a8-4a2c-9dd5-0cce26664599"
                           eId="hauptteil-1_art-12_abs-1">
               <akn:num eId="hauptteil-1_art-12_abs-1_bezeichnung-1"
                        GUID="7d3809ab-2691-4495-8442-d00a982fb46b">
                        (1)</akn:num>
               <akn:content GUID="79373c7f-82b0-48ff-8423-237333bdacc9"
                            eId="hauptteil-1_art-12_abs-1_inhalt-1">
                  <akn:p GUID="88ee4693-5a25-4b4f-b959-0c1455d7ff53"
                         eId="hauptteil-1_art-12_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-13"
                      GUID="9a25387c-d6af-4ac3-bb10-2ddc1adecedc"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-13_bezeichnung-1"
                     GUID="6358cd19-b9f5-473e-9192-eac12373d000">
                     §4c</akn:num>
            <akn:paragraph GUID="e7d210d4-edb1-47cc-a074-2b394bc04270"
                           eId="hauptteil-1_art-13_abs-1">
               <akn:num eId="hauptteil-1_art-13_abs-1_bezeichnung-1"
                        GUID="632066c0-1f65-4319-ad6a-d3b14e110e6b">
                        (1)</akn:num>
               <akn:content GUID="cb55652a-83d1-4bd1-abba-bb636565c559"
                            eId="hauptteil-1_art-13_abs-1_inhalt-1">
                  <akn:p GUID="8ec8d90b-ea2f-4218-89b5-37ba0ea20218"
                         eId="hauptteil-1_art-13_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-14"
                      GUID="17e8f2c2-9298-426d-8a0a-5852124239c0"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-14_bezeichnung-1"
                     GUID="1bbc3481-c35f-45c3-b397-c8cb6d6b2c8f">
                     §4d</akn:num>
            <akn:paragraph GUID="b8596272-3803-49de-a7f0-1d002a1efd61"
                           eId="hauptteil-1_art-14_abs-1">
               <akn:num eId="hauptteil-1_art-14_abs-1_bezeichnung-1"
                        GUID="7b771214-c3a3-4b56-b3bb-44c3d3b9dd31">
                        (1)</akn:num>
               <akn:content GUID="34c7d9ef-cc24-4706-a784-28269373326d"
                            eId="hauptteil-1_art-14_abs-1_inhalt-1">
                  <akn:p GUID="9d535547-49a5-4b09-9475-bead23a521e9"
                         eId="hauptteil-1_art-14_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-15"
                      GUID="adafbf82-2f5a-42c3-82c9-2339f0a382cd"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-15_bezeichnung-1"
                     GUID="32dfd6b3-4c8b-4dde-861b-07821708c297">
                     §4e</akn:num>
            <akn:paragraph GUID="44e2537a-a248-4dfe-9d3a-3c1c975deeca"
                           eId="hauptteil-1_art-15_abs-1">
               <akn:num eId="hauptteil-1_art-15_abs-1_bezeichnung-1"
                        GUID="e5dec8c4-c0ea-4e86-9607-40663f4e7be4">
                        (1)</akn:num>
               <akn:content GUID="c9f4e348-d2fb-4415-8bfa-8d292a9ce5c6"
                            eId="hauptteil-1_art-15_abs-1_inhalt-1">
                  <akn:p GUID="1d748807-46a7-41af-9cc4-1e693921631b"
                         eId="hauptteil-1_art-15_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-16"
                      GUID="e92b3ffb-be5e-447d-ac59-48815e56c124"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-16_bezeichnung-1"
                     GUID="4e87a3d9-9280-42b7-9d5e-0b4f0ee9170e">
                     §4f</akn:num>
            <akn:paragraph GUID="645cb034-5847-4c08-a795-d7b396db503c"
                           eId="hauptteil-1_art-16_abs-1">
               <akn:num eId="hauptteil-1_art-16_abs-1_bezeichnung-1"
                        GUID="476a88f0-edf6-4778-ab59-7a1c659fb335">
                        (1)</akn:num>
               <akn:content GUID="49bfc624-9922-4049-bac0-88af08b2cc98"
                            eId="hauptteil-1_art-16_abs-1_inhalt-1">
                  <akn:p GUID="528cebe9-ece1-495a-a811-71f325b9c0a7"
                         eId="hauptteil-1_art-16_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-17"
                      GUID="ffcb8103-08cb-4ab9-895e-2cc19974d4bc"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-17_bezeichnung-1"
                     GUID="bbc37521-f05f-4df9-8496-7b71863460cc">
                     §4g</akn:num>
            <akn:paragraph GUID="4f78186c-f8db-4f43-ab36-437d536e2c92"
                           eId="hauptteil-1_art-17_abs-1">
               <akn:num eId="hauptteil-1_art-17_abs-1_bezeichnung-1"
                        GUID="ccf50628-68c5-4a0c-ac55-8f6024412b7e">
                        (1)</akn:num>
               <akn:content GUID="4b3a87be-ddc0-460c-ba81-00e274b4a18a"
                            eId="hauptteil-1_art-17_abs-1_inhalt-1">
                  <akn:p GUID="749155b9-8125-4755-a8ae-3eda62e43865"
                         eId="hauptteil-1_art-17_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-18"
                      GUID="91883758-dd57-41ca-887c-76054b2480f6"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-18_bezeichnung-1"
                     GUID="32109de1-9a81-43ce-b167-210b45fde026">
                     §4h</akn:num>
            <akn:paragraph GUID="b2f41e83-2550-4447-a3c5-8ad7670ad7b7"
                           eId="hauptteil-1_art-18_abs-1">
               <akn:num eId="hauptteil-1_art-18_abs-1_bezeichnung-1"
                        GUID="32628015-7b68-4669-879c-7401a08a179a">
                        (1)</akn:num>
               <akn:content GUID="df3f282b-6ae3-420a-947f-d59f5a701934"
                            eId="hauptteil-1_art-18_abs-1_inhalt-1">
                  <akn:p GUID="e435d25d-2dd9-4197-8773-3cb03ade58f2"
                         eId="hauptteil-1_art-18_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-19"
                      GUID="49482d93-6969-4b41-9646-e38843f35c84"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-19_bezeichnung-1"
                     GUID="7fd28ebf-fb70-4939-b9be-474a44d48322">
                     §4i</akn:num>
            <akn:paragraph GUID="8c567484-3c5c-479f-be0c-ced07e3eb5fb"
                           eId="hauptteil-1_art-19_abs-1">
               <akn:num eId="hauptteil-1_art-19_abs-1_bezeichnung-1"
                        GUID="7f1e1e8f-ff99-4206-82bf-b87134eda77a">
                        (1)</akn:num>
               <akn:content GUID="d98913b8-a9c3-427c-8f65-a7a4afa50455"
                            eId="hauptteil-1_art-19_abs-1_inhalt-1">
                  <akn:p GUID="a68d7e61-e06c-41f3-9e33-0264cbc3810f"
                         eId="hauptteil-1_art-19_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-20"
                      GUID="4d3efe94-417b-4251-a63b-73cf014ffd98"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-20_bezeichnung-1"
                     GUID="89416443-6452-4cfb-a6c9-16cbb46d6117">
                     §4j</akn:num>
            <akn:paragraph GUID="f941ca6a-22a9-4bde-90ac-7ff187ca53bb"
                           eId="hauptteil-1_art-20_abs-1">
               <akn:num eId="hauptteil-1_art-20_abs-1_bezeichnung-1"
                        GUID="d782c8e4-b40b-45ee-81da-e853f80af1d7">
                        (1)</akn:num>
               <akn:content GUID="e4726b7e-a690-4490-a1b9-93c02cbaa49a"
                            eId="hauptteil-1_art-20_abs-1_inhalt-1">
                  <akn:p GUID="9a373fe2-417e-4872-a112-0b6b6cc1b889"
                         eId="hauptteil-1_art-20_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-21"
                      GUID="b55b4921-23af-4f55-ad3e-8cc9f8c2ff83"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-21_bezeichnung-1"
                     GUID="0619603b-18e4-42ee-9c99-91eae90469c3">
                     §4k</akn:num>
            <akn:paragraph GUID="a720dea6-624a-49d5-aa8f-0664dff9cf97"
                           eId="hauptteil-1_art-21_abs-1">
               <akn:num eId="hauptteil-1_art-21_abs-1_bezeichnung-1"
                        GUID="cff64b2d-fc9d-40df-95c4-aa2f9dc10284">
                        (1)</akn:num>
               <akn:content GUID="17a4585c-dcce-4a91-85d4-c35293c60ae6"
                            eId="hauptteil-1_art-21_abs-1_inhalt-1">
                  <akn:p GUID="fa0c022f-1d67-4792-a34a-54fa0b417c1e"
                         eId="hauptteil-1_art-21_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-22"
                      GUID="fe149939-6ef4-42d2-b8c9-4ba28a842406"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-22_bezeichnung-1"
                     GUID="8eeb97eb-a22c-4abf-b3fb-e499f4d372a0">
                     §5</akn:num>
            <akn:paragraph GUID="1a286587-87d7-4f01-96fb-9f3aef04553c"
                           eId="hauptteil-1_art-22_abs-1">
               <akn:num eId="hauptteil-1_art-22_abs-1_bezeichnung-1"
                        GUID="11143729-dab9-4626-aff7-0b39071369d8">
                        (1)</akn:num>
               <akn:content GUID="fc68edff-d4e5-41d9-ae24-6afb84b1162e"
                            eId="hauptteil-1_art-22_abs-1_inhalt-1">
                  <akn:p GUID="0ea02c00-8977-4dd3-9093-b7909037dc8e"
                         eId="hauptteil-1_art-22_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-23"
                      GUID="6f9ae82c-e11a-42ec-9c79-c6643b5030ad"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-23_bezeichnung-1"
                     GUID="af736792-d39f-43b1-9750-40a568247bb4">
                     §5a</akn:num>
            <akn:paragraph GUID="228220d1-14df-49d2-8f6d-2408da4bdf6f"
                           eId="hauptteil-1_art-23_abs-1">
               <akn:num eId="hauptteil-1_art-23_abs-1_bezeichnung-1"
                        GUID="6cbb9061-752e-4e89-9b65-4bc8b163aaba">
                        (1)</akn:num>
               <akn:content GUID="9794bd61-40ce-4c4f-8224-2e1998fc2fbb"
                            eId="hauptteil-1_art-23_abs-1_inhalt-1">
                  <akn:p GUID="da2878c4-e1ab-4704-afd3-a6a765db86f3"
                         eId="hauptteil-1_art-23_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-24"
                      GUID="c7570a10-24ba-4556-9330-d58b5ea7e6a6"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-24_bezeichnung-1"
                     GUID="d71ce5b3-1737-44a7-b34f-f9d11ccf8778">
                     §5b</akn:num>
            <akn:paragraph GUID="27bbb686-45a4-430c-aa90-310298267769"
                           eId="hauptteil-1_art-24_abs-1">
               <akn:num eId="hauptteil-1_art-24_abs-1_bezeichnung-1"
                        GUID="6c6dd7bf-2965-4d05-b668-bc73c76980c2">
                        (1)</akn:num>
               <akn:content GUID="c74d084f-c04a-4499-a4c1-f0aaf4d0421d"
                            eId="hauptteil-1_art-24_abs-1_inhalt-1">
                  <akn:p GUID="6f7e184b-a27f-407f-9f0f-5f0240857533"
                         eId="hauptteil-1_art-24_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-25"
                      GUID="28ca0cbd-5c5e-494e-a300-45f9d92f9922"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-25_bezeichnung-1"
                     GUID="443e6a57-1e65-4cd7-a89f-c947e6d68832">
                     §6</akn:num>
            <akn:paragraph GUID="899c959d-83f2-4134-b0ba-20328dcc16fb"
                           eId="hauptteil-1_art-25_abs-1">
               <akn:num eId="hauptteil-1_art-25_abs-1_bezeichnung-1"
                        GUID="c6322471-caec-4888-af78-84e1f11ab3c6">
                        (1)</akn:num>
               <akn:content GUID="d32da15e-f80f-4336-af14-170ee79b407d"
                            eId="hauptteil-1_art-25_abs-1_inhalt-1">
                  <akn:p GUID="1b56777b-a99f-4e99-b8b5-eb433b608f08"
                         eId="hauptteil-1_art-25_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-26"
                      GUID="fb0dbfa8-c9f4-4072-abac-d5ae46dc4ffa"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-26_bezeichnung-1"
                     GUID="3413c785-2aac-4ec8-ac64-df43b167bd6a">
                     §6a</akn:num>
            <akn:paragraph GUID="56fac6cb-a2f5-47a8-b70a-db2400c300d3"
                           eId="hauptteil-1_art-26_abs-1">
               <akn:num eId="hauptteil-1_art-26_abs-1_bezeichnung-1"
                        GUID="34da4ee7-b9cd-442a-9da8-27519427c2b7">
                        (1)</akn:num>
               <akn:content GUID="485daf4e-2cb4-4080-9f06-f42b4b138fe2"
                            eId="hauptteil-1_art-26_abs-1_inhalt-1">
                  <akn:p GUID="110761f1-afce-4817-aac6-20666db7e130"
                         eId="hauptteil-1_art-26_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <!-- §6b -->
         <akn:article eId="hauptteil-1_art-27"
                      GUID="17e9c25e-251d-48a7-8e74-3f0bb4b93b6b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-27_bezeichnung-1"
                     GUID="24f5f556-e005-4d10-8d7e-d92f141780d7">
                     §6b</akn:num>
            <akn:heading eId="hauptteil-1_art-27_überschrift-1"
                         GUID="31b97e2a-1872-4a52-aba1-d075bae3c0d6">Übertragung stiller Reserven bei der Veräußerung bestimmter Anlagegüter</akn:heading>
            <!-- Absatz 1 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-1"
                           GUID="1106c825-938a-4763-aaa4-0a81b658528b">
               <akn:num eId="hauptteil-1_art-27_abs-1_bezeichnung-1"
                        GUID="df7997ae-03fc-4073-8e7b-d6885b62dbbd">
                        (1)</akn:num>
               <akn:content GUID="8c1e9b59-fc85-45ae-834b-cd6b594ce514"
                            eId="hauptteil-1_art-27_abs-1_inhalt-1">
                  <akn:p GUID="29acbe3f-1605-4736-97b4-4bbf2c00dbc6"
                         eId="hauptteil-1_art-27_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 2 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-2"
                           GUID="f889003b-0740-444a-b6a3-fb45635b32ed">
               <akn:num eId="hauptteil-1_art-27_abs-2_bezeichnung-1"
                        GUID="e12b64e2-c3a2-4254-b75b-6c695f3f7568">
                        (2)</akn:num>
               <akn:content GUID="ba4d6792-d900-4d6e-8d80-8dde96cdf276"
                            eId="hauptteil-1_art-27_abs-2_inhalt-1">
                  <akn:p GUID="6cfdd68b-06f5-4055-8f1b-6ecb31f9bc67"
                         eId="hauptteil-1_art-27_abs-2_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 3 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-3"
                           GUID="982f7be1-2a0f-4a76-bc30-4522746e908c">
               <akn:num eId="hauptteil-1_art-27_abs-3_bezeichnung-1"
                        GUID="4da41dcf-d101-44a8-9c1f-580cefb69d69">
                        (3)</akn:num>
               <akn:content GUID="abc34096-ae69-4ec4-a904-c4ddcc504401"
                            eId="hauptteil-1_art-27_abs-3_inhalt-1">
                  <akn:p GUID="491ea8dd-a734-4001-8558-7a70b632c037"
                         eId="hauptteil-1_art-27_abs-3_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 4 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-4"
                           GUID="b7c29666-0b97-48cd-961d-d9a0d02c887a">
               <akn:num eId="hauptteil-1_art-27_abs-4_bezeichnung-1"
                        GUID="84ab35ff-8f39-4436-97d0-2c065ea1db0f">
                        (4)</akn:num>
               <akn:content GUID="23298ae2-6be8-4456-a2f1-5d33a87221f1"
                            eId="hauptteil-1_art-27_abs-4_inhalt-1">
                  <akn:p GUID="473b61da-0d84-4143-bfd3-2cce6da64254"
                         eId="hauptteil-1_art-27_abs-4_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 5 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-5"
                           GUID="6fcbce33-7bb2-4bc5-b081-9c2aebcfe466">
               <akn:num eId="hauptteil-1_art-27_abs-5_bezeichnung-1"
                        GUID="01a7810b-792b-4a60-a9e0-c592993dbc3e">
                        (5)</akn:num>
               <akn:content GUID="8cd7c2d2-796f-47e7-9db5-f29ef5a6d6cb"
                            eId="hauptteil-1_art-27_abs-5_inhalt-1">
                  <akn:p GUID="b3de9223-4577-4c1d-8d12-3261004910b3"
                         eId="hauptteil-1_art-27_abs-5_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 6 -->
            <akn:paragraph eId="hauptteil-1_art-27_abs-6"
                           GUID="58ea2242-386a-42b9-8f23-eaa1678159f1">
               <akn:num eId="hauptteil-1_art-27_abs-6_bezeichnung-1"
                        GUID="c38a41af-4911-4b1a-9404-929912af983e">
                        (6)</akn:num>
               <akn:content eId="hauptteil-1_art-27_abs-6_inhalt-1"
                            GUID="88b5a47f-40ad-485a-9745-5caa81f93e44">
                  <akn:p GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"
                         eId="hauptteil-1_art-27_abs-6_inhalt-1_text-1">Ist ein Betrag nach Absatz 1 oder 3 abgezogen worden, so tritt für die Absetzungen für Abnutzung oder Substanzverringerung oder in den Fällen des § 6 Absatz 2 und Absatz 2a im Wirtschaftsjahr des Abzugs der verbleibende Betrag an die Stelle der Anschaffungs- oder Herstellungskosten. In den Fällen des § 7 Absatz 4 Satz 1, Absatz 5 und 5a sind die um den Abzugsbetrag nach Absatz 1 oder 3 geminderten Anschaffungs- oder Herstellungskosten maßgebend.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-28"
                      GUID="2a63a350-9d16-4587-b1f5-4da210fa3bec"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-28_bezeichnung-1"
                     GUID="449e293a-3038-4bc0-bd7b-4f7f1353df93">
                           §6c</akn:num>
            <akn:paragraph GUID="1418ccdb-59ff-482d-9a90-129ea202e563"
                           eId="hauptteil-1_art-28_abs-1">
               <akn:num eId="hauptteil-1_art-28_abs-1_bezeichnung-1"
                        GUID="39ae2bbf-3709-4ae4-a749-b09404e49006">
                              (1)</akn:num>
               <akn:content GUID="fde1aced-d2a7-409b-9a5b-cc97cd8d6e49"
                            eId="hauptteil-1_art-28_abs-1_inhalt-1">
                  <akn:p GUID="f715da45-d8c8-4ab7-bf00-26cc8ad2dd08"
                         eId="hauptteil-1_art-28_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-29"
                      GUID="504c662d-9018-4f1a-ad4e-84cae20bd885"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-29_bezeichnung-1"
                     GUID="cf2c337c-83eb-409e-b310-1bebd1e3ad0c">
                           §6d</akn:num>
            <akn:paragraph GUID="3983bc04-5941-4605-a90e-5caeff00eea9"
                           eId="hauptteil-1_art-29_abs-1">
               <akn:num eId="hauptteil-1_art-29_abs-1_bezeichnung-1"
                        GUID="f093656e-d154-4326-bd55-2c14e9b32b2b">
                              (1)</akn:num>
               <akn:content GUID="bc6a27ea-6c14-481f-8e6d-bae1a86aac5e"
                            eId="hauptteil-1_art-29_abs-1_inhalt-1">
                  <akn:p GUID="62d6cc8e-837e-4931-936a-4a18f4bfae92"
                         eId="hauptteil-1_art-29_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-30"
                      GUID="af8c737c-ba14-4f65-92ca-5c0ae45fa719"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-30_bezeichnung-1"
                     GUID="114896af-7883-451b-9f2c-d1a415c314c1">
                           §6e</akn:num>
            <akn:paragraph GUID="fb130f72-2180-4ea8-b3ff-938f0af0d220"
                           eId="hauptteil-1_art-30_abs-1">
               <akn:num eId="hauptteil-1_art-30_abs-1_bezeichnung-1"
                        GUID="064f9219-38a1-4c1e-bf8d-1db70fa695f4">
                              (1)</akn:num>
               <akn:content GUID="6fa29af6-0da4-4872-9498-425070196c59"
                            eId="hauptteil-1_art-30_abs-1_inhalt-1">
                  <akn:p GUID="a6354f0a-1a2f-402c-949d-fec4e0b8ea0b"
                         eId="hauptteil-1_art-30_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-31"
                      GUID="95eb7919-b14a-4d4f-9d09-84770d49ee28"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-31_bezeichnung-1"
                     GUID="8b50a05e-79c5-4b5c-8600-6ad645fdf1bf">
                           §7</akn:num>
            <akn:paragraph GUID="7f9fc745-1f6e-4305-9d51-13e54ccfff81"
                           eId="hauptteil-1_art-31_abs-1">
               <akn:num eId="hauptteil-1_art-31_abs-1_bezeichnung-1"
                        GUID="023c24d7-7dbd-4f79-b7de-0ff17caab320">
                              (1)</akn:num>
               <akn:content GUID="3c29f352-99e5-4fd9-a3d1-2a3483a24339"
                            eId="hauptteil-1_art-31_abs-1_inhalt-1">
                  <akn:p GUID="cc8c7eee-8281-4b07-a94f-a2e19541dc3d"
                         eId="hauptteil-1_art-31_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-32"
                      GUID="e0418ed8-210c-4df5-831d-84402c78fcd6"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-32_bezeichnung-1"
                     GUID="e0da07d3-19b9-46ba-8c7f-cfa5232f256a">
                           §7a</akn:num>
            <akn:paragraph GUID="d65c9dc8-190a-4e44-b4ff-f8b58898d9de"
                           eId="hauptteil-1_art-32_abs-1">
               <akn:num eId="hauptteil-1_art-32_abs-1_bezeichnung-1"
                        GUID="5210ea3c-4c94-4e3e-b998-8023eb85a7b8">
                              (1)</akn:num>
               <akn:content GUID="221b988f-0964-4c84-82c3-8d42f7841f78"
                            eId="hauptteil-1_art-32_abs-1_inhalt-1">
                  <akn:p GUID="4813520d-d8ad-4941-8e18-2203419ac49d"
                         eId="hauptteil-1_art-32_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-33"
                      GUID="7f050d76-a8ed-4c15-8c5f-bc63cc2d592e"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-33_bezeichnung-1"
                     GUID="f13bced6-228d-422f-8b93-cb5917cb5040">
                           §7b</akn:num>
            <akn:paragraph GUID="e2828978-41cc-4330-a736-825c5e1a4b60"
                           eId="hauptteil-1_art-33_abs-1">
               <akn:num eId="hauptteil-1_art-33_abs-1_bezeichnung-1"
                        GUID="922f9819-c8b5-4fca-85c1-4b879c005347">
                              (1)</akn:num>
               <akn:content GUID="28f8eefc-f066-4a55-9223-c5b7dd60e8f6"
                            eId="hauptteil-1_art-33_abs-1_inhalt-1">
                  <akn:p GUID="2e2d643a-a604-4b42-b50c-f4449d332c31"
                         eId="hauptteil-1_art-33_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-34"
                      GUID="89f36695-4829-4639-98b3-7b87a3ee2664"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-34_bezeichnung-1"
                     GUID="b418b0f3-70fb-447e-a394-105e76200573">
                           §7c</akn:num>
            <akn:paragraph GUID="2edaf508-548e-4197-b9dd-632dcf46ad68"
                           eId="hauptteil-1_art-34_abs-1">
               <akn:num eId="hauptteil-1_art-34_abs-1_bezeichnung-1"
                        GUID="31ca8e8e-b7e2-4ff9-9f2b-9a016f5053cc">
                              (1)</akn:num>
               <akn:content GUID="d9220821-868e-45e9-ad99-16c45037ac4e"
                            eId="hauptteil-1_art-34_abs-1_inhalt-1">
                  <akn:p GUID="42bbf761-e56c-4432-b794-674cdde1eac7"
                         eId="hauptteil-1_art-34_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-35"
                      GUID="c677c2a7-38bc-4c4d-988c-9db5ba9947ce"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-35_bezeichnung-1"
                     GUID="bd8f2e0e-bea3-4d9e-898b-d8c0d5532808">
                           §7d</akn:num>
            <akn:paragraph GUID="a890abf2-9911-4490-9f32-a5ae7830af9b"
                           eId="hauptteil-1_art-35_abs-1">
               <akn:num eId="hauptteil-1_art-35_abs-1_bezeichnung-1"
                        GUID="e8181952-69ee-4847-8728-003ee791bd03">
                              (1)</akn:num>
               <akn:content GUID="55e6cf69-6364-4d58-a18a-a74d7aa80dba"
                            eId="hauptteil-1_art-35_abs-1_inhalt-1">
                  <akn:p GUID="562c588e-5249-4c78-822e-f7ee654865ab"
                         eId="hauptteil-1_art-35_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-36"
                      GUID="7f4daf11-04cd-4d22-8586-1ec56b7b52df"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-36_bezeichnung-1"
                     GUID="312541bc-1a0e-49aa-85b1-234d0378532b">
                           §7e</akn:num>
            <akn:paragraph GUID="51eb6574-ad7a-473b-826c-ff7d40dc394f"
                           eId="hauptteil-1_art-36_abs-1">
               <akn:num eId="hauptteil-1_art-36_abs-1_bezeichnung-1"
                        GUID="9ea30b93-8c7e-4ee7-95b9-99df93524c0f">
                              (1)</akn:num>
               <akn:content GUID="a254f8eb-ebd6-44e9-83c8-acf217d2e207"
                            eId="hauptteil-1_art-36_abs-1_inhalt-1">
                  <akn:p GUID="46daee2c-4060-4553-baad-7fadb08e8553"
                         eId="hauptteil-1_art-36_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-37"
                      GUID="91d0fb1b-f8ff-4203-827f-96c52887c3a0"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-37_bezeichnung-1"
                     GUID="435a7e1a-a86b-4c80-8fd3-01edc555d79e">
                           §7f</akn:num>
            <akn:paragraph GUID="00d9e791-1a3f-43c4-9ebd-3a6aeecb2b89"
                           eId="hauptteil-1_art-37_abs-1">
               <akn:num eId="hauptteil-1_art-37_abs-1_bezeichnung-1"
                        GUID="96883ae8-097e-40b6-83e0-fbba493d667e">
                              (1)</akn:num>
               <akn:content GUID="395a9397-6659-4c5d-a1d0-a8d6051725b5"
                            eId="hauptteil-1_art-37_abs-1_inhalt-1">
                  <akn:p GUID="f30b63e6-2f36-467c-92d7-7586ef9b2d02"
                         eId="hauptteil-1_art-37_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-38"
                      GUID="1db3fb3f-6b3b-4eb6-bb0e-4372b70d4ed8"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-38_bezeichnung-1"
                     GUID="5e3ae644-efb8-4c15-a5fe-05d3251290bf">
                           §7g</akn:num>
            <akn:paragraph GUID="59aec1b9-5713-44a1-bfb3-17b49718ec2a"
                           eId="hauptteil-1_art-38_abs-1">
               <akn:num eId="hauptteil-1_art-38_abs-1_bezeichnung-1"
                        GUID="d348e45b-e7d8-461b-9258-5e5dc2e5e1f9">
                              (1)</akn:num>
               <akn:content GUID="17bf7389-796c-40bc-8fb0-20eb0b89f846"
                            eId="hauptteil-1_art-38_abs-1_inhalt-1">
                  <akn:p GUID="3c0ff811-1dc5-41dc-91cb-0ce5a49d92e4"
                         eId="hauptteil-1_art-38_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-39"
                      GUID="12f09447-ea90-4e64-9c81-77b458a96b23"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-39_bezeichnung-1"
                     GUID="99de84a4-0cef-455b-aa00-6a56ad07a9a9">
                           §7h</akn:num>
            <akn:paragraph GUID="4d67a40a-c18d-4349-b22d-f7138258b314"
                           eId="hauptteil-1_art-39_abs-1">
               <akn:num eId="hauptteil-1_art-39_abs-1_bezeichnung-1"
                        GUID="33ba0c61-1a73-4303-8db6-db75175396ac">
                              (1)</akn:num>
               <akn:content GUID="356c3785-8371-4dee-b85c-c4022ca07036"
                            eId="hauptteil-1_art-39_abs-1_inhalt-1">
                  <akn:p GUID="59849e73-9b5e-44c7-9022-6e91851aa6cd"
                         eId="hauptteil-1_art-39_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-40"
                      GUID="579c9f9a-ba48-49cb-a60c-a571318be3d0"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-40_bezeichnung-1"
                     GUID="766a9b2e-e83e-4ffd-b2d8-9fe19a201370">
                           §7i</akn:num>
            <akn:paragraph GUID="814e4ff1-1f54-4cca-8689-707bacf4d37b"
                           eId="hauptteil-1_art-40_abs-1">
               <akn:num eId="hauptteil-1_art-40_abs-1_bezeichnung-1"
                        GUID="dc302bd5-22f4-4123-a6bb-cd2146e8e3a0">
                              (1)</akn:num>
               <akn:content GUID="3016ef4b-1741-4d9a-a5ee-1a474e32cf3e"
                            eId="hauptteil-1_art-40_abs-1_inhalt-1">
                  <akn:p GUID="b96b09fb-fae0-495c-9a4d-78397519096a"
                         eId="hauptteil-1_art-40_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-41"
                      GUID="b2e0df87-d52f-458b-8901-6fa8a72c28c0"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-41_bezeichnung-1"
                     GUID="772fc8c6-2009-4c91-980f-1811ec8c98b7">
                           §7j</akn:num>
            <akn:paragraph GUID="a7f2ed2e-0a5e-41e3-ba37-dd696cb41c13"
                           eId="hauptteil-1_art-41_abs-1">
               <akn:num eId="hauptteil-1_art-41_abs-1_bezeichnung-1"
                        GUID="1ab126b7-a2ba-4165-85dc-3ba92d68f050">
                              (1)</akn:num>
               <akn:content GUID="f461fe66-d927-4625-bf13-f60ec5f74b46"
                            eId="hauptteil-1_art-41_abs-1_inhalt-1">
                  <akn:p GUID="ee202292-1ffb-4e83-9f31-a402a563acbd"
                         eId="hauptteil-1_art-41_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-42"
                      GUID="f1631d4d-2c49-4de0-99e8-0313a98bc973"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-42_bezeichnung-1"
                     GUID="70ddcd29-2719-479f-a2ce-9fba1f8513af">
                           §7k</akn:num>
            <akn:paragraph GUID="b4dce994-2a44-4519-baca-61df6649312d"
                           eId="hauptteil-1_art-42_abs-1">
               <akn:num eId="hauptteil-1_art-42_abs-1_bezeichnung-1"
                        GUID="bf9d2e42-b77b-4d1e-9036-7eb9ed3fdb23">
                              (1)</akn:num>
               <akn:content GUID="c702ccb3-2ab8-4f9d-bc6a-10d62451b45c"
                            eId="hauptteil-1_art-42_abs-1_inhalt-1">
                  <akn:p GUID="828cac8a-2233-405d-bf8f-0e7012c97cf4"
                         eId="hauptteil-1_art-42_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-43"
                      GUID="5f555a46-b98c-4ca5-85eb-5d8c9bcc773b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-43_bezeichnung-1"
                     GUID="e0d7f9ef-6310-43ea-8ee8-7206f80a10cf">
                           §8</akn:num>
            <akn:paragraph GUID="8f1eeb6c-8765-47d8-9256-2fdb06c25c21"
                           eId="hauptteil-1_art-43_abs-1">
               <akn:num eId="hauptteil-1_art-43_abs-1_bezeichnung-1"
                        GUID="5894d7f0-88b4-4ceb-99fc-b78f7682ddf3">
                              (1)</akn:num>
               <akn:content GUID="ec74badd-2483-4036-8475-d9ea34b0cd7c"
                            eId="hauptteil-1_art-43_abs-1_inhalt-1">
                  <akn:p GUID="aac2e07d-b31b-45ce-9730-39f0b35a4756"
                         eId="hauptteil-1_art-43_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-44"
                      GUID="76c34087-8785-4f65-bfcf-b21d3cce5447"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-44_bezeichnung-1"
                     GUID="74b7793f-b3aa-407f-bc35-a7c275405e5a">
                           §9</akn:num>
            <akn:paragraph GUID="03a3e523-3769-4e43-9044-c7354ed1ec61"
                           eId="hauptteil-1_art-44_abs-1">
               <akn:num eId="hauptteil-1_art-44_abs-1_bezeichnung-1"
                        GUID="f3555f68-2e82-45c3-80e2-30568ff3e977">
                              (1)</akn:num>
               <akn:content GUID="6ee4bd25-6e3e-4508-a4cd-4c27cfc0285c"
                            eId="hauptteil-1_art-44_abs-1_inhalt-1">
                  <akn:p GUID="114c7481-6682-456f-bd7a-cbe95d23c479"
                         eId="hauptteil-1_art-44_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-45"
                      GUID="ee674638-ebcd-4afb-8d67-507ec4590dc3"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-45_bezeichnung-1"
                     GUID="c34dab0f-0b9f-4507-8fda-c611098eff33">
                           §9a</akn:num>
            <akn:paragraph GUID="67af4843-ce5c-45f0-bb29-785a233fe79a"
                           eId="hauptteil-1_art-45_abs-1">
               <akn:num eId="hauptteil-1_art-45_abs-1_bezeichnung-1"
                        GUID="91917c04-2e02-48d8-9472-712b51c46f39">
                              (1)</akn:num>
               <akn:content GUID="763a8a04-b258-41aa-89a9-f6bcdc3ca691"
                            eId="hauptteil-1_art-45_abs-1_inhalt-1">
                  <akn:p GUID="81bde952-65db-475c-9ceb-2b4abf18adac"
                         eId="hauptteil-1_art-45_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-46"
                      GUID="ccf524cc-5857-417b-8874-ac15bafe9870"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-46_bezeichnung-1"
                     GUID="bdd35c51-f64b-4c4f-ad08-d66a499b7c4a">
                           §9b</akn:num>
            <akn:paragraph GUID="f1ce8898-144a-41c4-a797-bf158c785e64"
                           eId="hauptteil-1_art-46_abs-1">
               <akn:num eId="hauptteil-1_art-46_abs-1_bezeichnung-1"
                        GUID="ae91a07a-3c2f-4e1b-9190-998aed9d9878">
                              (1)</akn:num>
               <akn:content GUID="f09a4a6d-1021-45e9-9f39-3bba81890bac"
                            eId="hauptteil-1_art-46_abs-1_inhalt-1">
                  <akn:p GUID="64022ca3-9601-4030-9996-b8660ccbfa31"
                         eId="hauptteil-1_art-46_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-47"
                      GUID="db7f88c9-f304-4564-a294-9a6b0c8d3567"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-47_bezeichnung-1"
                     GUID="5d697e3e-bcc8-4c77-9b84-513cf64747bc">
                           §10</akn:num>
            <akn:paragraph GUID="8e573156-d96e-40be-9341-1c48c0f6d24f"
                           eId="hauptteil-1_art-47_abs-1">
               <akn:num eId="hauptteil-1_art-47_abs-1_bezeichnung-1"
                        GUID="626b6481-44ce-4574-9048-175189c2a1a6">
                              (1)</akn:num>
               <akn:content GUID="90c69d6e-3325-4e18-8c96-e92588aea0fb"
                            eId="hauptteil-1_art-47_abs-1_inhalt-1">
                  <akn:p GUID="02742a1f-daab-43cb-8b3e-006d89b1fc4f"
                         eId="hauptteil-1_art-47_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-48"
                      GUID="e14bd426-f9e7-480b-9069-0d9cb0fd7b80"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-48_bezeichnung-1"
                     GUID="1832f4b2-6856-412e-8012-44f9b885c69c">
                           §10a</akn:num>
            <akn:paragraph GUID="a23ffb18-d9c9-4030-a54e-168f1d6b9122"
                           eId="hauptteil-1_art-48_abs-1">
               <akn:num eId="hauptteil-1_art-48_abs-1_bezeichnung-1"
                        GUID="f0b9f3d9-6c74-4938-bb6e-b0b7b4bf729a">
                              (1)</akn:num>
               <akn:content GUID="2348a890-8255-4978-ae9e-256907b3822f"
                            eId="hauptteil-1_art-48_abs-1_inhalt-1">
                  <akn:p GUID="73beeb6d-b875-46f5-91fc-cdb9276369b3"
                         eId="hauptteil-1_art-48_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-49"
                      GUID="43bb65f4-9998-49ff-b685-e297fdc6ed39"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-49_bezeichnung-1"
                     GUID="c51df148-7d09-40fe-92f8-a3c29f6f3cee">
                           §10b</akn:num>
            <akn:paragraph GUID="dbdb0ed2-eb51-4ddb-8192-5fffc282e463"
                           eId="hauptteil-1_art-49_abs-1">
               <akn:num eId="hauptteil-1_art-49_abs-1_bezeichnung-1"
                        GUID="58ef9d6d-8c69-445b-aa29-aaca7021836b">
                              (1)</akn:num>
               <akn:content GUID="4fc7d436-f74d-49fa-a67a-52c972fcf1d9"
                            eId="hauptteil-1_art-49_abs-1_inhalt-1">
                  <akn:p GUID="22031202-2172-49bd-accd-ce5dc3da4149"
                         eId="hauptteil-1_art-49_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-50"
                      GUID="bc497bd8-d49f-418d-9c2c-88f67f86daaf"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-50_bezeichnung-1"
                     GUID="9138e716-266f-4509-9cd5-ccda34ee11b8">
                           §10c</akn:num>
            <akn:paragraph GUID="05906cf6-4591-4f6b-806d-3e2f42700e2c"
                           eId="hauptteil-1_art-50_abs-1">
               <akn:num eId="hauptteil-1_art-50_abs-1_bezeichnung-1"
                        GUID="2c09968b-876d-46e6-9106-bf108008e114">
                              (1)</akn:num>
               <akn:content GUID="349f137e-c17a-4a22-a0eb-ca4f7658ab0d"
                            eId="hauptteil-1_art-50_abs-1_inhalt-1">
                  <akn:p GUID="17b21830-5240-4a8f-a911-9ef537d9f451"
                         eId="hauptteil-1_art-50_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-51"
                      GUID="9200365a-1df4-4983-85c8-451e421e9d0d"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-51_bezeichnung-1"
                     GUID="a1e1b0f3-7799-4648-b3e4-3b0deecd05f7">
                           §10d</akn:num>
            <akn:paragraph GUID="804b00e6-00a4-434e-99e9-643ed7684804"
                           eId="hauptteil-1_art-51_abs-1">
               <akn:num eId="hauptteil-1_art-51_abs-1_bezeichnung-1"
                        GUID="93397acf-6c68-401c-bdba-d4a6a5c03fe5">
                              (1)</akn:num>
               <akn:content GUID="24c2d356-954b-4ea5-9230-a6900ead68c0"
                            eId="hauptteil-1_art-51_abs-1_inhalt-1">
                  <akn:p GUID="d0b50a0e-fad2-47c9-9bc6-298deffbf4c3"
                         eId="hauptteil-1_art-51_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-52"
                      GUID="706cf9b3-20fb-4952-80f7-fcf52afa5532"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-52_bezeichnung-1"
                     GUID="74fa2557-59aa-43b8-b990-720ad6813f93">
                           §10e</akn:num>
            <akn:paragraph GUID="2c34eaa0-9445-45ff-ae73-e4f903602624"
                           eId="hauptteil-1_art-52_abs-1">
               <akn:num eId="hauptteil-1_art-52_abs-1_bezeichnung-1"
                        GUID="e76ba7c6-e8b7-43ef-9e44-08702928fb47">
                              (1)</akn:num>
               <akn:content GUID="d5404cfe-bbdd-4198-91ee-e0c0ff4c205c"
                            eId="hauptteil-1_art-52_abs-1_inhalt-1">
                  <akn:p GUID="a0761643-a5ac-4590-ba3f-1cd8abec189f"
                         eId="hauptteil-1_art-52_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-53"
                      GUID="85c19e43-7ae6-4051-b7d0-6cbeb0a2a3ae"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-53_bezeichnung-1"
                     GUID="d7233aa6-cb7e-4eae-967c-a39570c4b680">
                           §10f</akn:num>
            <akn:paragraph GUID="fb771acf-763d-4f65-bc3e-6c47244df2e9"
                           eId="hauptteil-1_art-53_abs-1">
               <akn:num eId="hauptteil-1_art-53_abs-1_bezeichnung-1"
                        GUID="d8da0052-d1cb-4dfc-97c3-0d433b5e7a8b">
                              (1)</akn:num>
               <akn:content GUID="85b183d9-d114-4fd5-8293-a458f48d6c85"
                            eId="hauptteil-1_art-53_abs-1_inhalt-1">
                  <akn:p GUID="36ae4b5d-b7f9-4c95-852a-01433026b3c3"
                         eId="hauptteil-1_art-53_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-54"
                      GUID="8b3b9885-e5cf-49d1-aec4-c851ffa8e612"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-54_bezeichnung-1"
                     GUID="831a216d-a89e-4fcc-883c-552d56aa2c4c">
                           §10g</akn:num>
            <akn:paragraph GUID="fe4a8d3d-9623-4556-bf68-753aa26d75f3"
                           eId="hauptteil-1_art-54_abs-1">
               <akn:num eId="hauptteil-1_art-54_abs-1_bezeichnung-1"
                        GUID="639909a0-b708-446c-844e-817979d52c92">
                              (1)</akn:num>
               <akn:content GUID="3a5712a6-b844-451d-8e3d-14f2bb1921c8"
                            eId="hauptteil-1_art-54_abs-1_inhalt-1">
                  <akn:p GUID="13458f97-90b0-4ebf-99fa-de6ae5e13e18"
                         eId="hauptteil-1_art-54_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-55"
                      GUID="d1e43ff6-aa11-45a8-9eda-e4c5f2ba8aaa"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-55_bezeichnung-1"
                     GUID="56a7b2bd-b247-4389-a8c1-a2b4fba9a577">
                           §10h</akn:num>
            <akn:paragraph GUID="d948e95d-14c4-4e96-b636-7f2b6f72a42f"
                           eId="hauptteil-1_art-55_abs-1">
               <akn:num eId="hauptteil-1_art-55_abs-1_bezeichnung-1"
                        GUID="1220162c-2074-499a-9302-a83f3550a531">
                              (1)</akn:num>
               <akn:content GUID="cf3f2eed-722b-4996-9d06-c6c714838033"
                            eId="hauptteil-1_art-55_abs-1_inhalt-1">
                  <akn:p GUID="3bc0ec8b-828f-4d29-8262-01b3c16524b0"
                         eId="hauptteil-1_art-55_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-56"
                      GUID="7b4fb4ab-e1b3-45a7-8749-5df3e89a06f1"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-56_bezeichnung-1"
                     GUID="468d82d5-8be5-4a63-aaa9-c3ea18f63bef">
                           §10i</akn:num>
            <akn:paragraph GUID="54f67d49-a7d1-44a9-8e8e-32d42e3efe9d"
                           eId="hauptteil-1_art-56_abs-1">
               <akn:num eId="hauptteil-1_art-56_abs-1_bezeichnung-1"
                        GUID="46463b98-9582-4d79-9855-0de6b6f55bff">
                              (1)</akn:num>
               <akn:content GUID="de1e05c8-4c38-443b-825f-5426bd934b62"
                            eId="hauptteil-1_art-56_abs-1_inhalt-1">
                  <akn:p GUID="900efd8d-62d9-4839-84fa-815dca262549"
                         eId="hauptteil-1_art-56_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-57"
                      GUID="52869254-3d60-4278-976c-12c6caa0e9ca"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-57_bezeichnung-1"
                     GUID="2940f21e-162f-4af9-87d7-ea5e77640759">
                           §11</akn:num>
            <akn:paragraph GUID="4b7bd11e-d8be-4e17-b856-afabf5503eee"
                           eId="hauptteil-1_art-57_abs-1">
               <akn:num eId="hauptteil-1_art-57_abs-1_bezeichnung-1"
                        GUID="9176b31d-1277-4ccb-a851-06655a932c02">
                              (1)</akn:num>
               <akn:content GUID="932128d1-8e6f-4bfa-85c0-5210b4f20662"
                            eId="hauptteil-1_art-57_abs-1_inhalt-1">
                  <akn:p GUID="39cee6c0-4513-4d55-9eb7-0ed16f3436ec"
                         eId="hauptteil-1_art-57_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-58"
                      GUID="f8d485e1-0153-4460-b509-9577af35aa1f"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-58_bezeichnung-1"
                     GUID="2e13a1c9-a506-4dc1-9efa-aad0c2573f7b">
                           §11a</akn:num>
            <akn:paragraph GUID="d7452750-46cf-4299-be42-aff8774f0739"
                           eId="hauptteil-1_art-58_abs-1">
               <akn:num eId="hauptteil-1_art-58_abs-1_bezeichnung-1"
                        GUID="f775f2da-f619-4c6c-962b-9421023e84c1">
                              (1)</akn:num>
               <akn:content GUID="dccbe848-f55f-48bc-99ea-ace45ade406d"
                            eId="hauptteil-1_art-58_abs-1_inhalt-1">
                  <akn:p GUID="2eaf494d-ffdd-4aea-9740-899c04070c01"
                         eId="hauptteil-1_art-58_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-59"
                      GUID="7f87a5ce-4eac-4618-8b88-11ad9a73f094"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-59_bezeichnung-1"
                     GUID="458930e8-7825-4f8b-af4c-18afcae80c99">
                           §11b</akn:num>
            <akn:paragraph GUID="a7c97c41-a018-48b6-bfdd-28f6209659cf"
                           eId="hauptteil-1_art-59_abs-1">
               <akn:num eId="hauptteil-1_art-59_abs-1_bezeichnung-1"
                        GUID="f7eab3e9-6018-4bea-a5c8-3f28c62c2f37">
                              (1)</akn:num>
               <akn:content GUID="350aad01-5c46-4090-b7f4-0eb16d7f02d0"
                            eId="hauptteil-1_art-59_abs-1_inhalt-1">
                  <akn:p GUID="1b26c17a-9243-4082-acd8-6f5a2ffa47dc"
                         eId="hauptteil-1_art-59_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-60"
                      GUID="d5cc84de-4a2f-4112-b873-535d0767fc5c"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-60_bezeichnung-1"
                     GUID="48b761b6-4cc8-40f2-9ef2-9314b83baf65">
                           §12</akn:num>
            <akn:paragraph GUID="0254c66d-ae15-48ca-abad-803687cac3e0"
                           eId="hauptteil-1_art-60_abs-1">
               <akn:num eId="hauptteil-1_art-60_abs-1_bezeichnung-1"
                        GUID="3a9c0aa6-6a01-4a29-ab37-44f584030095">
                              (1)</akn:num>
               <akn:content GUID="7853e7ce-759e-415b-bfe1-c05e3b443f9c"
                            eId="hauptteil-1_art-60_abs-1_inhalt-1">
                  <akn:p GUID="ceef25f2-a8b3-4775-a43d-958a54607fa8"
                         eId="hauptteil-1_art-60_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-61"
                      GUID="8066551b-deb0-434a-8fcb-5f5a28237b11"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-61_bezeichnung-1"
                     GUID="7fa61a6b-ab26-4d3d-ae4a-ba5a19f5e585">
                           §13</akn:num>
            <akn:paragraph GUID="2fe3952d-4d95-4fd6-94b0-5f1ad3c40bed"
                           eId="hauptteil-1_art-61_abs-1">
               <akn:num eId="hauptteil-1_art-61_abs-1_bezeichnung-1"
                        GUID="87bfb2f0-c3fe-4351-b2c6-abba45d8a49e">
                              (1)</akn:num>
               <akn:content GUID="1b452dde-d8f0-4e31-b268-4a5add6410f5"
                            eId="hauptteil-1_art-61_abs-1_inhalt-1">
                  <akn:p GUID="a6e1370d-4c4f-4fdc-8e70-021a7c46485d"
                         eId="hauptteil-1_art-61_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-62"
                      GUID="58b72cdd-9361-4f44-adf4-9e46b4dd411b"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-62_bezeichnung-1"
                     GUID="7d236b8f-87eb-4b7f-bb32-f621a43b96d5">
                           §13a</akn:num>
            <akn:paragraph GUID="15351e75-b249-43e6-90ac-9732977790de"
                           eId="hauptteil-1_art-62_abs-1">
               <akn:num eId="hauptteil-1_art-62_abs-1_bezeichnung-1"
                        GUID="26357c0a-3921-4630-be95-2ccefb23d4f3">
                              (1)</akn:num>
               <akn:content GUID="886c6bfe-40f2-4bbb-8764-fbdcdab02969"
                            eId="hauptteil-1_art-62_abs-1_inhalt-1">
                  <akn:p GUID="02fe82fd-f46e-4fd1-a2f4-a74b72e052d4"
                         eId="hauptteil-1_art-62_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-63"
                      GUID="2603ad73-00f3-4f3e-aa0d-096b46899c2e"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-63_bezeichnung-1"
                     GUID="2d2fa553-9a11-47da-8331-079042c8f3a8">
                           §14</akn:num>
            <akn:paragraph GUID="dfc62626-d0ec-4642-9bf9-5e284a62ea16"
                           eId="hauptteil-1_art-63_abs-1">
               <akn:num eId="hauptteil-1_art-63_abs-1_bezeichnung-1"
                        GUID="0e7d6304-4621-4df4-8570-aae3de882855">
                              (1)</akn:num>
               <akn:content GUID="8e30d562-065b-4cc0-ba23-04533003726b"
                            eId="hauptteil-1_art-63_abs-1_inhalt-1">
                  <akn:p GUID="d384eaed-1725-4394-8ea5-7c74f47d8eac"
                         eId="hauptteil-1_art-63_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-64"
                      GUID="bf3fe544-55f5-44f1-b40e-9e318cc8e4c2"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-64_bezeichnung-1"
                     GUID="cbadeaa2-e413-4873-ba67-93aa9b108e01">
                           §14a</akn:num>
            <akn:paragraph GUID="97834b4e-b5b7-4800-a7bd-fc0ddc47c596"
                           eId="hauptteil-1_art-64_abs-1">
               <akn:num eId="hauptteil-1_art-64_abs-1_bezeichnung-1"
                        GUID="800aefa7-bd3c-49be-80b4-3015561c0c1a">
                              (1)</akn:num>
               <akn:content GUID="3a00eb3d-1aae-4ab5-85df-fbb50b91d87b"
                            eId="hauptteil-1_art-64_abs-1_inhalt-1">
                  <akn:p GUID="3e19a349-dd89-4f93-8dc7-b2bc9c96157e"
                         eId="hauptteil-1_art-64_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-65"
                      GUID="e0f00c8d-6e4b-41e3-8146-bf18ab653a61"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-65_bezeichnung-1"
                     GUID="943249dd-a5f5-44a6-be1c-b765b12ffb7c">
                           §15</akn:num>
            <akn:paragraph GUID="53270dfd-3d76-468d-8f3a-180a5cd03dd1"
                           eId="hauptteil-1_art-65_abs-1">
               <akn:num eId="hauptteil-1_art-65_abs-1_bezeichnung-1"
                        GUID="91455560-b3bf-4a6c-8103-8a74bd38f828">
                              (1)</akn:num>
               <akn:content GUID="ad504722-5457-4949-a9c3-07198f633303"
                            eId="hauptteil-1_art-65_abs-1_inhalt-1">
                  <akn:p GUID="77621563-5a54-4dcf-ad05-7ee370cd3a78"
                         eId="hauptteil-1_art-65_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-66"
                      GUID="59f33977-e133-403d-87f2-a8a19e660126"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-66_bezeichnung-1"
                     GUID="b79815cd-e9b2-4a49-a8aa-3fa7bf441e8b">
                           §15a</akn:num>
            <akn:paragraph GUID="b71f169d-5a18-43f7-ab2a-4ec4d7bdcce2"
                           eId="hauptteil-1_art-66_abs-1">
               <akn:num eId="hauptteil-1_art-66_abs-1_bezeichnung-1"
                        GUID="40688c5e-d183-4970-8df8-a6018e5ed41e">
                              (1)</akn:num>
               <akn:content GUID="6a3dc9a2-c698-4fa8-abc3-6c0ee92b867e"
                            eId="hauptteil-1_art-66_abs-1_inhalt-1">
                  <akn:p GUID="5cd1f012-7fa3-4438-ac39-6e46c2a9cdec"
                         eId="hauptteil-1_art-66_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-67"
                      GUID="d39e85a0-e42d-42e5-ae6e-3e4b01d7c125"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-67_bezeichnung-1"
                     GUID="bf7dee3c-4eae-4e52-82a4-9989306fa60b">
                           §15b</akn:num>
            <akn:paragraph GUID="ee1207d0-5a50-4491-8f3d-aff67f7e8cc3"
                           eId="hauptteil-1_art-67_abs-1">
               <akn:num eId="hauptteil-1_art-67_abs-1_bezeichnung-1"
                        GUID="9269d744-58f9-4702-a381-ae4e0b019fe7">
                              (1)</akn:num>
               <akn:content GUID="fc89e421-a91b-4ef6-8e50-a03610d792be"
                            eId="hauptteil-1_art-67_abs-1_inhalt-1">
                  <akn:p GUID="9cd7acc7-1ca0-4bca-8163-b9631ef4039f"
                         eId="hauptteil-1_art-67_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-68"
                      GUID="13a6f767-98dd-4d47-a578-29ab52eade2f"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-68_bezeichnung-1"
                     GUID="cc243a4b-5f4c-4a8b-9266-afe19faf7c50">
                           §16</akn:num>
            <akn:paragraph GUID="69ec5e14-a326-40d7-89cf-a0050a92960b"
                           eId="hauptteil-1_art-68_abs-1">
               <akn:num eId="hauptteil-1_art-68_abs-1_bezeichnung-1"
                        GUID="5e0ff084-7c12-4f2d-a4c7-a50c3eba46f9">
                              (1)</akn:num>
               <akn:content GUID="8bf5fdd9-b927-4ca3-96cd-6e6d2594ab8b"
                            eId="hauptteil-1_art-68_abs-1_inhalt-1">
                  <akn:p GUID="08d880ae-2418-4381-a3b1-c2c724671d79"
                         eId="hauptteil-1_art-68_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-69"
                      GUID="6457e6d5-1210-461c-b1d5-c1790b26e838"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-69_bezeichnung-1"
                     GUID="72285626-56c2-4d70-87fc-6e763ce3b2b7">
                           §17</akn:num>
            <akn:paragraph GUID="162917c4-6e74-40e1-97e9-f32a319ccc1e"
                           eId="hauptteil-1_art-69_abs-1">
               <akn:num eId="hauptteil-1_art-69_abs-1_bezeichnung-1"
                        GUID="4765be64-12f6-4d50-a18b-84337ea8c600">
                              (1)</akn:num>
               <akn:content GUID="8f5410e5-178c-4c0e-8f33-c32d4e2bce23"
                            eId="hauptteil-1_art-69_abs-1_inhalt-1">
                  <akn:p GUID="1963d605-9a42-4cf2-b89c-9da40b5a865e"
                         eId="hauptteil-1_art-69_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-70"
                      GUID="59e3f34c-4f28-4e34-86ae-f2d45181d5b6"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-70_bezeichnung-1"
                     GUID="805050c7-e327-4bcb-b1a8-c14e98e5c372">
                        §18</akn:num>
            <akn:paragraph GUID="e02d01a5-30e7-4ca5-8535-a5f8762b9bd0"
                           eId="hauptteil-1_art-70_abs-1">
               <akn:num eId="hauptteil-1_art-70_abs-1_bezeichnung-1"
                        GUID="738a4278-5552-4b36-b607-bc6fca803a36">
                           (1)</akn:num>
               <akn:content GUID="5a567605-e8a5-4a20-a9ed-423db6cce51a"
                            eId="hauptteil-1_art-70_abs-1_inhalt-1">
                  <akn:p GUID="b1691777-4124-4097-99e0-b00073b7db53"
                         eId="hauptteil-1_art-70_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-71"
                      GUID="a726a175-3a70-40e7-852f-9d08d1a26a13"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-3">
            <akn:num eId="hauptteil-1_art-71_bezeichnung-1"
                     GUID="0298199b-a8d3-4f7b-b5e5-16b4dc2501d1">
                        §19</akn:num>
            <!--Absatz 1-->
            <akn:paragraph GUID="eb932754-5119-4fc1-a167-4dfc5381d2ca"
                           eId="hauptteil-1_art-71_abs-1">
               <akn:num eId="hauptteil-1_art-71_abs-1_bezeichnung-1"
                        GUID="8280d4ef-4a96-44b7-81b9-f30f1006a2fe">
                           (1)</akn:num>
               <akn:content GUID="eeeac3d5-4e22-4b6f-9d91-ab70a47f3640"
                            eId="hauptteil-1_art-71_abs-1_inhalt-1">
                  <akn:p GUID="2d1e76a2-d330-49d5-b2e4-4f9feea44f85"
                         eId="hauptteil-1_art-71_abs-1_inhalt-1_text-1"/>
               </akn:content>
            </akn:paragraph>
            <!--Absatz 2-->
            <akn:paragraph GUID="cd17b333-e7d5-4f0f-9406-5d50eb12f783"
                           eId="hauptteil-1_art-71_abs-2">
               <akn:num eId="hauptteil-1_art-71_abs-2_bezeichnung-1"
                        GUID="48d155ca-ec9e-4136-a14a-96d4f7e7a017">
                           (2)</akn:num>
               <akn:list GUID="3035f805-a24d-45c3-9cb6-785fa2cc9812"
                         eId="hauptteil-1_art-71_abs-2_untergl-1">
                  <akn:intro GUID="244de4e1-4976-4959-b274-22a0e4253bea"
                             eId="hauptteil-1_art-71_abs-2_untergl-1_intro-1">
                     <akn:p GUID="9092b8f3-d2b4-4df0-8e7d-3232808365a6"
                            eId="hauptteil-1_art-71_abs-2_untergl-1_intro-1_text-1">Von Versorgungsbezügen bleiben ein nach einem Prozentsatz ermittelter, auf einen Höchstbetrag begrenzter Betrag (Versorgungsfreibetrag) und ein Zuschlag zum Versorgungsfreibetrag steuerfrei. Versorgungsbezüge sind
                                 </akn:p>
                  </akn:intro>
                  <akn:point GUID="26764f22-3d0e-47c5-9830-31a8ca6dfa35"
                             eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1">
                     <akn:num GUID="fa106c10-a7e9-4093-80bb-a061c7bd51e0"
                              eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                     <akn:list GUID="c121d47b-788e-4003-9ff0-5581d604b91a"
                               eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1">
                        <akn:intro GUID="a0fe69a4-2a37-4d9e-9de6-237bd313b1fc"
                                   eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1">
                           <akn:p GUID="fa011bbb-c56e-426c-a571-2e3d9e9f2cda"
                                  eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_intro-1_text-1">das Ruhegehalt, Witwen- oder Waisengeld, der Unterhaltsbeitrag oder ein gleichartiger Bezug
                                       </akn:p>
                        </akn:intro>
                        <akn:point GUID="b0d6890a-8224-4fc3-b7c6-9790277b1499"
                                   eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1">
                           <akn:num GUID="ec3231f1-9d02-4d1e-86b5-68e75664e626"
                                    eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                           <akn:content GUID="a1802bf9-25cc-465f-bd96-7aceae04c366"
                                        eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                              <akn:p GUID="2e5cea34-e8ce-4f7c-8673-0a36f845f38b"
                                     eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">Grund beamtenrechtlicher oder entsprechender gesetzlicher Vorschriften,
                                       </akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="7cbb02b7-2053-41e2-88aa-a6aa26a72233"
                                   eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2">
                           <akn:num GUID="e1953bdd-0564-4853-8ed0-dac771a181d2"
                                    eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                           <akn:content GUID="a2903657-6784-4eef-961b-f3c53bed78c6"
                                        eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                              <akn:p GUID="c2cd5917-b4ce-449d-8103-9f34fa0d34f7"
                                     eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">nach beamtenrechtlichen Grundsätzen von Körperschaften, Anstalten oder Stiftungen des öffentlichen Rechts oder öffentlich-rechtlichen Verbänden von Körperschaften
                                       </akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:wrapUp GUID="0405677d-8718-4564-9dec-b3bf832f04e4"
                                    eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1">
                           <akn:p GUID="ad110215-c861-4a60-8987-523952f68e21"
                                  eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-1_untergl-1_schlusstext-1_text-1">oder</akn:p>
                        </akn:wrapUp>
                     </akn:list>
                  </akn:point>
                  <akn:point GUID="accde094-d21b-4a86-a8cd-f9123606c726"
                             eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-2">
                     <akn:num GUID="0521715f-1e31-4e38-b8e4-84c4d23e0f0b"
                              eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                     <akn:content GUID="5484bc1b-b351-41f4-93d1-f2256ca61bea"
                                  eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="e0aeb3f1-50a0-4f60-98d9-910a54143dca"
                               eId="hauptteil-1_art-71_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                    in anderen Fällen Bezüge und Vorteile aus früheren Dienstleistungen wegen Erreichens einer Altersgrenze, verminderter Erwerbsfähigkeit oder Hinterbliebenenbezüge; Bezüge wegen Erreichens einer Altersgrenze gelten erst dann als Versorgungsbezüge, wenn der Steuerpflichtige das 63. Lebensjahr oder, wenn er schwerbehindert ist, das 60. Lebensjahr vollendet hat.
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
