DELETE
FROM norms
WHERE eli_expression = 'eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1';

-- Target law
INSERT INTO norms (guid, xml)
VALUES ('63ef9358-8755-46e4-bf6a-21f379014597', '<?xml version="1.0" encoding="UTF-8"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd                                                                                                                                              http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
      <akn:meta GUID="7c364d8d-7867-46f5-bfed-f5ad8b4ab89e" eId="meta-1">
         <akn:identification source="attributsemantik-noch-undefiniert"
                             GUID="8bda99ab-c051-41d6-9b40-b34555c39d31"
                             eId="meta-1_ident-1">
            <akn:FRBRWork GUID="2ba53b48-1b11-43fb-8b20-074b2c567f33"
                          eId="meta-1_ident-1_frbrwork-1">
               <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/regelungstext-1"
                             GUID="81998cf6-1e6a-4a23-8230-a6eefa61a253"
                             eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1002/1"
                            GUID="1383239a-f26a-48a5-9761-e06eb05abc95"
                            eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
               <akn:FRBRalias name="übergreifende-id"
                              value="f96cfae4-4fce-4c72-9186-0d84778dc11c"
                              GUID="c9b3e387-d85b-4db3-adf7-4f0db2a77b91"
                              eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
               <akn:FRBRdate name="verkuendungsfassung"
                             date="1002-01-01"
                             GUID="cca99ae9-3da3-43e3-a25d-dde986cc43ef"
                             eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
               <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung"
                               GUID="7ca858b0-959f-4066-866a-35b01f4c4b2c"
                               eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
               <akn:FRBRcountry value="de"
                                GUID="89a1bb9e-a263-47c8-896d-c072ff4ade68"
                                eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
               <akn:FRBRnumber value="1"
                               GUID="03104316-8ff4-424a-a5a6-91d3470db7db"
                               eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
               <akn:FRBRname value="bgbl-1"
                             GUID="53679c01-90f5-430b-861e-d39904e657e5"
                             eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
               <akn:FRBRsubtype value="regelungstext-1"
                                GUID="bd02fcf8-5d97-48e4-844f-03ce1a14b0cf"
                                eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression GUID="516fab5c-88ab-4292-ab39-c9e681790f33"
                                eId="meta-1_ident-1_frbrexpression-1">
               <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1"
                             GUID="5cdf307b-8dec-494f-8035-469132f1414b"
                             eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu"
                            GUID="f5aeabef-d78d-4268-8819-5f1170e9f8ea"
                            eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
               <akn:FRBRalias name="aktuelle-version-id"
                              value="63ef9358-8755-46e4-bf6a-21f379014597"
                              GUID="5652140a-7c00-43f9-84f7-641db77f6389"
                              eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
               <akn:FRBRalias name="nachfolgende-version-id"
                              value="702f994d-c8da-4ca2-b592-e8196a9cef18"
                              GUID="10ef604c-d561-4550-a137-f7e3b11812d9"
                              eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
               <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung"
                               GUID="bb00fcde-3d40-45a7-bcc3-609f2cb81ad8"
                               eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
               <akn:FRBRdate name="verkuendung"
                             date="1002-01-01"
                             GUID="0bc6b5c1-d4c5-47de-81d9-63453d0b9acb"
                             eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
               <akn:FRBRlanguage language="deu"
                                 GUID="873de5b6-2064-4f9b-8b0e-4ef1f8405b87"
                                 eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
               <akn:FRBRversionNumber value="1"
                                      GUID="ca48bdf4-5631-4c45-a161-61ccd849cd1b"
                                      eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation GUID="10f71315-c390-4799-8166-2e53c672c368"
                                   eId="meta-1_ident-1_frbrmanifestation-1">
               <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/1002-01-01/regelungstext-1.xml"
                             GUID="9c599b8f-5da2-47a8-8620-5b01ad2f1b6a"
                             eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/1002-01-01/regelungstext-1.xml"
                            GUID="807ed403-0f2e-4bff-927d-99eaccb0b1b8"
                            eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
               <akn:FRBRdate name="generierung"
                             date="1002-01-01"
                             GUID="5862c4ff-7af5-4b8a-bc42-19cc0fa797a1"
                             eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
               <akn:FRBRauthor href="recht.bund.de"
                               GUID="7a3922c6-4dfc-43c3-8ab6-9b33f79c5f5b"
                               eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
               <akn:FRBRformat value="xml"
                               GUID="d47436ef-a59a-4c61-859c-61f1d74e5bf7"
                               eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle source="attributsemantik-noch-undefiniert"
                        GUID="d4e71855-22b4-4dfd-b36a-ad6d9ca4fc95"
                        eId="meta-1_lebzykl-1">
            <akn:eventRef date="1002-01-01"
                          refersTo="ausfertigung"
                          type="generation"
                          source="attributsemantik-noch-undefiniert"
                          GUID="2eabf2a9-130e-442a-83c7-478fd0af952e"
                          eId="meta-1_lebzykl-1_ereignis-1"/>
         </akn:lifecycle>
         <akn:analysis source="attributsemantik-noch-undefiniert"
                       GUID="64b2888e-d5c4-48c5-91ae-5a345ade9044"
                       eId="meta-1_analysis-1"/>
         <akn:temporalData source="attributsemantik-noch-undefiniert"
                           GUID="4110fd7b-10b9-407a-98f9-6694e54f4aa4"
                           eId="meta-1_geltzeiten-1">
            <akn:temporalGroup GUID="d06431e9-9430-4826-bba4-28f4cb244f96"
                               eId="meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:timeInterval refersTo="geltungszeit"
                                 GUID="c3b6dfca-35c4-4da3-9665-d16eaf5a9b35"
                                 eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                 start="#meta-1_lebzykl-1_ereignis-1"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <akn:proprietary eId="meta-1_proprietary-1"
                          GUID="fe419055-3201-41b1-b096-402eabcbe6a1"
                          source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>bundesregierung</meta:initiant>
               <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
               <meta:fna>nicht-vorhanden</meta:fna>
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>
      <akn:preface GUID="558a7994-288c-4675-8bac-bd66bee0f936" eId="einleitung-1">
         <akn:longTitle GUID="dc689db1-5222-4ce9-8b27-eda52a45de2c"
                        eId="einleitung-1_doktitel-1">
            <akn:p GUID="1da23b6e-1074-44e9-825a-e89954dfba55"
                   eId="einleitung-1_doktitel-1_text-1">
               <akn:docTitle GUID="492ae182-a8e7-4b78-98e5-69db635f94ef"
                             eId="einleitung-1_doktitel-1_text-1_doctitel-1">Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten</akn:docTitle>
               <akn:shortTitle GUID="c440c4fa-e725-40fb-b087-346d4216e3d7"
                               eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(Strukturänderungsgesetz)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <akn:preamble GUID="c3b0b356-a440-4343-82c4-65f95c9516a7" eId="preambel-1">
         <akn:citations GUID="d8d6d35f-a93b-43ac-a0bd-5e881adab346" eId="preambel-1_ernormen-1">
            <akn:citation GUID="ee4a2977-d5d8-4431-bc8e-8bbd79879218"
                          eId="preambel-1_ernormen-1_ernorm-1">
               <akn:p GUID="e8aa2c58-58d2-4d90-a90b-4109432d3930"
                      eId="preambel-1_ernormen-1_ernorm-1_text-1">Die Bundesregierung wird ermächtigt, durch Rechtsverordnung ohne Zustimmung des Bundesrates die zur Durchführung dieses Gesetzes erforderlichen Rechtsvorschriften zu erlassen. Hierzu gehören insbesondere Regelungen über die organisatorische Umsetzung der in diesem Gesetz festgelegten Maßnahmen, die Festlegung technischer Standards sowie Bestimmungen zur Überwachung und Kontrolle der Einhaltung der gesetzlichen Vorgaben.</akn:p>
            </akn:citation>
         </akn:citations>
         <akn:recitals GUID="c3dc0f83-d136-4a04-9bb8-bcdbb4eb0c40"
                       eId="preambel-1_präambeln-1">
            <akn:heading GUID="7a1845b5-962c-4f56-9b7b-4304341a9e18"
                         eId="preambel-1_präambeln-1_überschrift-1">Präambel</akn:heading>
            <akn:recital GUID="bac17f14-45b1-4c58-bf95-f5c46b6cad7d"
                         eId="preambel-1_präambeln-1_präambelinh-1">
               <akn:p GUID="ec5b59eb-cf30-424b-b0c4-e7178c4b0409"
                      eId="preambel-1_präambeln-1_präambelinh-1_text-1">Im Bewusstsein der Verantwortung gegenüber den Bürgerinnen und Bürgern, sowie im Bestreben, die Effizienz und Transparenz der staatlichen Strukturen zu erhöhen, hat der Deutsche Bundestag das nachstehende Gesetz beschlossen. Ziel dieses Gesetzes ist es, bestehende Strukturen und Gliederungseinheiten in Dokumenten unter Berücksichtigung moderner Technologien zu ersetzen, zu vereinheitlichen und zu optimieren. Dabei sollen sowohl die Interessen der Bürgerinnen und Bürger als auch die Anforderungen an eine zukunftsfähige und digitalisierte Verwaltung gleichermaßen gewahrt werden.</akn:p>
               <akn:p GUID="8756158f-0dd3-41e7-b1c4-270203c8f3e9"
                      eId="preambel-1_präambeln-1_präambelinh-1_text-2">Dieses Gesetz beruht auf den Grundsätzen der Rechtstaatlichkeit, der Wirtschaftlichkeit und der Bürgernähe. Es stellt sicher, dass die Anpassungen an Gesetzen und Verordnungen, transparent und effektiv durchgeführt werden.</akn:p>
            </akn:recital>
         </akn:recitals>
         <akn:blockContainer refersTo="inhaltsuebersicht"
                             GUID="ea5bfdf7-6765-4df9-a764-0c586784aad0"
                             eId="preambel-1_blockcontainer-1">
            <akn:heading GUID="a9f860f6-19e9-4828-ae18-4020d1043a9a"
                         eId="preambel-1_blockcontainer-1_überschrift-1">Inhaltsübersicht</akn:heading>
            <akn:toc GUID="ba86f68e-5b14-4e08-a752-88b9996fef6a"
                     eId="preambel-1_blockcontainer-1_inhuebs-1">
               <akn:tocItem level="1"
                            href=""
                            GUID="8b8205eb-5a7a-4d3a-a9b6-20ab89c8b174"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1">
                  <akn:span GUID="fd086af7-215c-4ddf-aa67-019350f6b623"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1">Abschnitt 1</akn:span>
                  <akn:span GUID="2403f040-8ca9-4a97-b12b-d427accdd06d"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-2">Allgemeine Vorschriften</akn:span>
               </akn:tocItem>
               <akn:tocItem level="2"
                            href=""
                            GUID="1b3fffbf-34ec-47eb-b03c-177fcd21f0f1"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2">
                  <akn:span GUID="8fd3fc0d-51e5-4605-8e44-dcdf4b544fef"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-1">§ 1</akn:span>
                  <akn:span GUID="b2d3dedc-de13-45aa-95eb-d766711f1a4e"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-2">Abfallwirtschaftliche Ziele</akn:span>
               </akn:tocItem>
               <akn:tocItem level="2"
                            href=""
                            GUID="5af17eb2-c69c-475a-b7e8-395203acb2cb"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3">
                  <akn:span GUID="87e658cf-9195-4fd1-98e6-8476179f0fe5"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-1">§ 2</akn:span>
                  <akn:span GUID="e645cec7-be92-4f7d-9448-657c98489dbf"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-2">Anwendungsbereich</akn:span>
               </akn:tocItem>
               <akn:tocItem level="2"
                            href=""
                            GUID="f7540d1c-a28c-4bc2-82a8-69a95e1e7adc"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4">
                  <akn:span GUID="e9682a1d-57fd-4319-b605-ccdfafd85e32"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-1">§ 3</akn:span>
                  <akn:span GUID="1f89f6c0-d97d-4ee0-a9af-96eee4c4e6f8"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-2">Begriffsbestimmungen</akn:span>
               </akn:tocItem>
               <akn:tocItem level="1"
                            href=""
                            GUID="beaeeb2d-0a2e-43eb-b0c4-37c766017565"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5">
                  <akn:span GUID="b4cf00c0-cae4-4b2b-aec0-672648aa89a9"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-1">Abschnitt 2</akn:span>
                  <akn:span GUID="03a2350d-2910-4e71-86c1-89f83b795cb1"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-2">Pflichten beim Inverkehrbringen von Elektro- und Elektronikgeräten</akn:span>
               </akn:tocItem>
               <akn:tocItem level="2"
                            href=""
                            GUID="2c4358fa-9433-488b-9123-0dc5beac4225"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6">
                  <akn:span GUID="c9ccede0-c91b-4197-bb5d-5c04218df217"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-1">§ 4</akn:span>
                  <akn:span GUID="9505898b-c5d6-425c-809d-84c88d07526c"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-2">Produktionskonzeption</akn:span>
               </akn:tocItem>
               <akn:tocItem level="2"
                            href=""
                            GUID="9456f1c3-bea6-4a10-b34e-65a7712a52f7"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7">
                  <akn:span GUID="a93a0838-8061-4778-b63a-29b149b26b76"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-1">§ 5</akn:span>
                  <akn:span GUID="6adf9a55-3a4d-4702-b7ef-615ada1dd6ad"
                            eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-2">Einrichten der Gemeinsamen Stelle</akn:span>
               </akn:tocItem>
            </akn:toc>
         </akn:blockContainer>
      </akn:preamble>
      <akn:body GUID="d4e8f7e2-a3d1-4524-83f0-6830f2d5602f" eId="hauptteil-1">
         <akn:book GUID="a0dde363-c30a-4a5d-8eb4-40e2c0bc83ae" eId="hauptteil-1_buch-1">
            <akn:num GUID="a4b90106-5bfb-47b3-9e78-301bc0ec52fa"
                     eId="hauptteil-1_buch-1_bezeichnung-1">1. Buch</akn:num>
            <akn:heading GUID="b2166332-861f-4ec3-91cd-29f2ae6d8712"
                         eId="hauptteil-1_buch-1_überschrift-1">Strukturen und Gliederungsebenen</akn:heading>
            <akn:article GUID="dc72bc52-88bf-49e5-b286-ee3657e21802"
                         eId="hauptteil-1_buch-1_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:num GUID="104e165a-8492-4230-ad5e-b50ef6d5aba2"
                        eId="hauptteil-1_buch-1_art-1_bezeichnung-1">§ 1</akn:num>
               <akn:heading GUID="dd735f0c-7213-4b61-8901-b982d574553c"
                            eId="hauptteil-1_buch-1_art-1_überschrift-1">Anwendung in Absätzen und Aufzählungen</akn:heading>
               <akn:paragraph GUID="e5ff6e2e-4ef9-40b7-a9fe-94486309e8f7"
                              eId="hauptteil-1_buch-1_art-1_abs-1">
                  <akn:num GUID="acc7ea3d-cc3e-4541-9350-3e823b4795b3"
                           eId="hauptteil-1_buch-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
                  <akn:content GUID="d689e436-8890-4df2-ba02-ced8f9565523"
                               eId="hauptteil-1_buch-1_art-1_abs-1_inhalt-1">
                     <akn:p GUID="ecd911e1-4b21-4571-9db2-caa70b4f6a18"
                            eId="hauptteil-1_buch-1_art-1_abs-1_inhalt-1_text-1">Dieses Gesetz findet Anwendung auf alle definierten Struktur und Gliederungsebenen.</akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="6de01720-488f-45e1-bbad-39b5a810e6c6"
                              eId="hauptteil-1_buch-1_art-1_abs-2">
                  <akn:num GUID="161e1e19-7f8a-4772-a845-57e80fda9d5d"
                           eId="hauptteil-1_buch-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
                  <akn:content GUID="6141ee06-9498-4c20-b720-a458ec45eba1"
                               eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1">
                     <akn:p GUID="6c3549e8-d7e0-4270-a831-508a1bfb18c5"
                            eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1_text-1">Die Berechnung der Anwendung erfolgt nach folgender Formel: </akn:p>
                     <akn:foreign GUID="0caafe15-6a72-43a2-b217-e07a3d7690b4"
                                  eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1_exmarkup-1">
                        <math xmlns="http://www.w3.org/1998/Math/MathML"
                              xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                              display="block">
                           <mrow>
                              <mi>f</mi>
                              <mo>(</mo>
                              <mi>x</mi>
                              <mo>)</mo>
                              <mo>=</mo>
                              <mfrac>
                                 <mrow>
                                    <mi>a</mi>
                                    <msup>
                                       <mi>x</mi>
                                       <mn>3</mn>
                                    </msup>
                                    <mo>+</mo>
                                    <mi>b</mi>
                                    <msup>
                                       <mi>x</mi>
                                       <mn>2</mn>
                                    </msup>
                                    <mo>+</mo>
                                    <mi>c</mi>
                                    <mi>x</mi>
                                    <mo>+</mo>
                                    <mi>d</mi>
                                 </mrow>
                                 <mrow>
                                    <msqrt>
                                       <mi>e</mi>
                                       <mi>x</mi>
                                       <mo>+</mo>
                                       <mn>1</mn>
                                    </msqrt>
                                 </mrow>
                              </mfrac>
                              <mo>+</mo>
                              <msup>
                                 <mrow>
                                    <mo>(</mo>
                                    <mi>g</mi>
                                    <mi>x</mi>
                                    <mo>+</mo>
                                    <mi>h</mi>
                                    <mo>)</mo>
                                 </mrow>
                                 <mn>2</mn>
                              </msup>
                              <mo>=</mo>
                              <munderover>
                                 <mo>∫</mo>
                                 <mn>0</mn>
                                 <mn>∞</mn>
                              </munderover>
                              <mfrac>
                                 <mrow>
                                    <mi>k</mi>
                                    <mo>+</mo>
                                    <mi>l</mi>
                                    <mi>t</mi>
                                 </mrow>
                                 <msup>
                                    <mrow>
                                       <mo>(</mo>
                                       <mi>m</mi>
                                       <mi>t</mi>
                                       <mo>+</mo>
                                       <mn>1</mn>
                                       <mo>)</mo>
                                    </mrow>
                                    <mn>3</mn>
                                 </msup>
                              </mfrac>
                              <mo>d</mo>
                              <mi>t</mi>
                           </mrow>
                        </math>
                     </akn:foreign>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="829ff310-a716-4c51-a1c8-809fa278eaa5"
                              eId="hauptteil-1_buch-1_art-1_abs-3">
                  <akn:num GUID="05487656-77f3-4aed-acb3-d8c38c1c4893"
                           eId="hauptteil-1_buch-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
                  <akn:list GUID="bd7b64aa-eebe-4630-858e-1c9a9530e2aa"
                            eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1">
                     <akn:intro GUID="e282eee1-3233-4724-b776-477e00e3ee3a"
                                eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_intro-1">
                        <akn:p GUID="d48853c4-1c43-4b2d-8b9b-221a96348130"
                               eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_intro-1_text-1">Die Bestimmungen dieses Gesetzes gelten insbesondere für:</akn:p>
                     </akn:intro>
                     <akn:point GUID="aa63a962-55b1-4478-99f7-25e8778ca805"
                                eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="91dfc6eb-ac3c-4d47-ae26-09ed89c39c62"
                                 eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                        <akn:list GUID="25a4692a-5743-4e45-bac6-3a0bc18babf0"
                                  eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1">
                           <akn:intro GUID="6227f929-a66b-40db-a284-d7bbfbba9ea1"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1">
                              <akn:p GUID="931db155-1643-41df-af6c-c65ef9b9c808"
                                     eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1_text-1">Gliederungen im Regelungstext wie zum Beispiel</akn:p>
                           </akn:intro>
                           <akn:point GUID="e407dbd3-3b67-41b6-836f-a28431acff17"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1">
                              <akn:num GUID="8c7dbdc0-e8ad-46ea-a0e7-8219607a913a"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                              <akn:content GUID="406e624b-6672-48ec-9442-d9a637709436"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                                 <akn:p GUID="3d97774d-e07a-4a9f-a8e9-87d88a97e342"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">Bücher,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="7bb528b5-c7af-4063-bc67-5c5b8e097f8c"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2">
                              <akn:num GUID="a2173120-2d57-4fba-bacc-a32be17c32da"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                              <akn:content GUID="5889b17a-8c03-4e4d-9191-e81e4a7fc577"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                                 <akn:p GUID="98971529-8183-4423-84a0-ec27b37dff5d"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">Teile,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="8cfde2b7-24c8-4dc3-a7b0-2cf6f5995c80"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3">
                              <akn:num GUID="e275d052-d1de-469e-8265-0150672b3f99"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                              <akn:content GUID="4f1445b5-5c41-4142-9fe4-14082daad33c"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1">
                                 <akn:p GUID="6879af35-0b71-4ae8-9202-5faaf100a152"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1_text-1">Kapitel,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="a813dc1d-f3dd-4442-9cd6-af0dc433dde9"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4">
                              <akn:num GUID="cd4d60d6-76e1-477c-911e-7e221bffa8ca"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_bezeichnung-1">d)</akn:num>
                              <akn:content GUID="b348c506-b38d-4d34-a3f3-4cf8dcf125cc"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1">
                                 <akn:p GUID="78fd9e67-383c-4f87-aa7e-06e6a5d1e887"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1_text-1">Unterkapitel,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="fe17c2e5-f07e-461a-886a-2f7bb1b65461"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5">
                              <akn:num GUID="10c4bff9-8ed4-40f0-ab65-97d498b4aec6"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_bezeichnung-1">e)</akn:num>
                              <akn:content GUID="3a49a36b-6a84-45cd-a78f-40f833872ca4"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_inhalt-1">
                                 <akn:p GUID="56999aee-0003-4d6d-a4bc-4f96222c8754"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_inhalt-1_text-1">Abschnitte,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="b18200eb-a5b9-4deb-86ea-85552a535e1a"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6">
                              <akn:num GUID="6750b1c9-0601-4146-8b54-ca49ddc0aad2"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_bezeichnung-1">f)</akn:num>
                              <akn:content GUID="60101cf8-80fb-4d7a-b901-a32dd5c60241"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_inhalt-1">
                                 <akn:p GUID="5668b6e2-5316-4f3c-b69a-e776b68953ee"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_inhalt-1_text-1">oder Unterabschnitte.</akn:p>
                              </akn:content>
                           </akn:point>
                        </akn:list>
                     </akn:point>
                     <akn:point GUID="2b2e3aff-b6f5-416e-b33b-580c245b8845"
                                eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="d238a1bd-79c0-42f9-b296-48a32e5a90c3"
                                 eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                        <akn:list GUID="42d5f215-3873-4ffa-b715-38e2664198b0"
                                  eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1">
                           <akn:intro GUID="b715aa2f-6c62-4aa7-a3a5-b9142a9778e2"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1">
                              <akn:p GUID="1b5e63b1-6b18-42ad-b50d-eed3e8b9c271"
                                     eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1_text-1">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                           </akn:intro>
                           <akn:point GUID="6d307694-2145-40e1-90b9-34f7b627e7aa"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1">
                              <akn:num GUID="48c2bafd-54c4-4e24-a292-19910257b724"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                              <akn:content GUID="b5b0b480-5991-4033-a36c-fbbb7cb775bc"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1">
                                 <akn:p GUID="92ce014f-8724-4b63-9eab-19f90026c779"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1">Überschriften,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="5d605c7d-e355-4ebb-aa91-e7858b17003a"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2">
                              <akn:num GUID="33a848f5-33bb-4784-81c5-305275952094"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                              <akn:content GUID="a0429ea1-90d0-45e8-8922-eaefd34deb7f"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1">
                                 <akn:p GUID="d5835f32-c4f6-47c3-b7ad-5b47d0cf9e82"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1">Zeilen,</akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="64fb8111-cb36-43bc-b42f-84e478683109"
                                      eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3">
                              <akn:num GUID="42548fb0-d1c1-46b8-90a6-6bba7628c016"
                                       eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                              <akn:content GUID="a82767d8-316a-4252-b52e-ed0b0219a587"
                                           eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1">
                                 <akn:p GUID="f29282a0-f091-4857-8097-ab75f769288b"
                                        eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1_text-1">oder Zellen.</akn:p>
                              </akn:content>
                           </akn:point>
                        </akn:list>
                     </akn:point>
                     <akn:wrapUp GUID="60a9393c-da39-45d1-a789-4c0bd4e9230b"
                                 eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_schlusstext-1">
                        <akn:p GUID="520d8c54-6e45-4e07-88f3-56f6c741f500"
                               eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_schlusstext-1_text-1">In besonderen Fällen können auch Teile der Präambel, Inhaltsübersicht, Anlagen- oder Stammformverweise geändert werden.</akn:p>
                     </akn:wrapUp>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="162dc967-07e6-4ac4-829f-a79e756c7e46"
                              eId="hauptteil-1_buch-1_art-1_abs-4">
                  <akn:num GUID="ffebfd14-77a7-4f65-ab74-956c07dc5d9c"
                           eId="hauptteil-1_buch-1_art-1_abs-4_bezeichnung-1">(4)</akn:num>
                  <akn:content GUID="c4bccd98-c7f1-4b98-8ca7-a0f2572d1ee4"
                               eId="hauptteil-1_buch-1_art-1_abs-4_inhalt-1">
                     <akn:p GUID="b8f6450d-d877-499f-a2a5-370d81c92bbc"
                            eId="hauptteil-1_buch-1_art-1_abs-4_inhalt-1_text-1">Alle staatlichen und kommunalen Verwaltungen sind verpflichtet, die in diesem Gesetz festgelegten Maßnahmen zum Ersetzen von Strukturen und Gliederungseinheiten umzusetzen.</akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="40d46aca-0515-427a-a8cb-f33656f42a64"
                              eId="hauptteil-1_buch-1_art-1_abs-5">
                  <akn:num GUID="c7ea9d71-4f9a-4425-9244-0c1c2b078fcf"
                           eId="hauptteil-1_buch-1_art-1_abs-5_bezeichnung-1">(5)</akn:num>
                  <akn:list GUID="8bfff8a7-f083-47b1-a697-52a693f8ab65"
                            eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1">
                     <akn:intro GUID="a9e5b492-c9c4-4871-a6d6-0cbd43fdf24f"
                                eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_intro-1">
                        <akn:p GUID="f46adb3d-dcfd-4864-932b-ff948a3e7352"
                               eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_intro-1_text-1">Jede Verwaltungseinheit hat sicherzustellen, dass:</akn:p>
                     </akn:intro>
                     <akn:point GUID="7973f712-76ea-4437-887a-9b49b7b520fb"
                                eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1">
                        <akn:num GUID="8bf3d468-2cf8-4d20-9816-1aa9f7eede3f"
                                 eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                        <akn:content GUID="f583edbe-e1d6-4c43-ab1d-150a2ffc3e2c"
                                     eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="afa394b2-e688-4761-a136-b7715a8b5909"
                                  eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_inhalt-1_text-1">alle Mitarbeiterinnen und Mitarbeiter angemessen geschult und weitergebildet werden,</akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="c829ed84-24f9-432a-bf39-b17c3729460f"
                                eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2">
                        <akn:num GUID="986f7080-c429-4bec-aa72-e38f1db7b571"
                                 eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                        <akn:content GUID="3d20448d-9c18-428a-a5a6-c2c24304bc0e"
                                     eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="fd9daf92-2843-4fab-88dd-c9d8bb9174e5"
                                  eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_inhalt-1_text-1">die eingesetzten Technologien und Verfahren regelmäßig überprüft und bei Bedarf aktualisiert werden,</akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="bca05894-3a24-40c8-bcfb-4f170fad54f2"
                                eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3">
                        <akn:num GUID="2a9c7f15-2dd5-45b1-b3f4-da8b7d63aea3"
                                 eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                        <akn:content GUID="15744540-acf1-462d-b0dd-dd81bbdc40ca"
                                     eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="ceb53121-46d3-48b8-9c1f-3286d83e5080"
                                  eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_inhalt-1_text-1">ein kontinuierlicher Verbesserungsprozess etabliert wird, um die Effizienz und Effektivität der Verwaltung zu steigern.</akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="4c36cac1-8079-4ab5-8b30-117496e5acd9"
                         eId="hauptteil-1_buch-1_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:num GUID="8f853443-d3b7-4040-b3e6-1c5e0bd59b9a"
                        eId="hauptteil-1_buch-1_art-2_bezeichnung-1">§ 2</akn:num>
               <akn:heading GUID="336c97ab-8f2d-48f4-a809-178bbf1cd3f0"
                            eId="hauptteil-1_buch-1_art-2_überschrift-1">Anwendung in Tabellen</akn:heading>
               <akn:paragraph GUID="c5043cc7-bb6b-4c97-978c-155d404a0632"
                              eId="hauptteil-1_buch-1_art-2_abs-1">
                  <akn:num GUID="740c983f-f1d5-4196-8715-9b76a797c3c4"
                           eId="hauptteil-1_buch-1_art-2_abs-1_bezeichnung-1"/>
                  <akn:content GUID="4471f722-04cb-4986-813e-67087309d0b8"
                               eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1">
                     <akn:p GUID="c25072b0-5b8b-411f-b8f6-01281ebf5c0b"
                            eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_text-1">Folgende Strukturelemente eines Regelungstextes können mittels aenderungZitatStruktur verändert werden:</akn:p>
                     <akn:table border="0"
                                GUID="dd4c64da-68ef-4125-bb64-050dd11bd68c"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1">
                        <akn:tr GUID="33f6fd58-f713-4020-a61a-6cae9e15baa8"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1">
                           <akn:th style="width:200"
                                   GUID="90fd60ce-9ec8-4751-bb3c-1c672569e5ee"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1">
                              <akn:p GUID="f35a7912-1f2d-4b53-a3c0-9dd985229a82"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Fachliche Klasse </akn:p>
                           </akn:th>
                           <akn:th style="width:200"
                                   GUID="39cd0e89-eb74-4a07-9e7f-05d4edef5af0"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                              <akn:p GUID="d2ec3798-e1f9-4d6a-9f5d-fd2c6529441e"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">AKN-Entsprechung </akn:p>
                           </akn:th>
                        </akn:tr>
                        <akn:tr GUID="5a89d7ac-a84e-428b-95ba-fb7335e258fa"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2">
                           <akn:td style="text-align:right"
                                   GUID="4b56f337-4a50-4a22-9423-280e55b1d9a1"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1">
                              <akn:p GUID="4eea0d77-4159-4a8d-ac86-4cee85b90ffb"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1_text-1">dokumentenkopfTitel</akn:p>
                           </akn:td>
                           <akn:td GUID="c8f4215d-169f-4f6f-b83c-696ca6c26dfd"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2">
                              <akn:p GUID="f1f6c9ec-7ab3-4220-a5ff-f347bad4e828"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2_text-1">akn:longTitle</akn:p>
                           </akn:td>
                        </akn:tr>
                        <akn:tr GUID="a991db7f-f1ac-4781-bf1a-cb036b806618"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3">
                           <akn:td style="text-align:right"
                                   GUID="2f30595d-4798-45c8-b58d-6e5df6ae059e"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                              <akn:p GUID="da841f9d-0936-43ec-99f0-9ad30e553008"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">ermaechtigungsnormen</akn:p>
                           </akn:td>
                           <akn:td GUID="6527a4a8-8f65-4157-ad3f-c97d6005f7ce"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                              <akn:p GUID="f658ebda-15c5-410a-a28e-a4978ba1a46f"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">akn:citations</akn:p>
                           </akn:td>
                        </akn:tr>
                        <akn:tr GUID="f40503d0-c683-4d50-842f-2d19ecbcb5e8"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4">
                           <akn:td style="text-align:right"
                                   GUID="cb62b54b-babc-4219-8562-e12bf3680707"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1">
                              <akn:p GUID="bb8510c8-6a93-4db8-8417-254ca06c915f"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1">praeambel</akn:p>
                           </akn:td>
                           <akn:td GUID="d7958bd6-eace-4d91-8263-cbb16bd60ba4"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2">
                              <akn:p GUID="1fc1ce1f-bbdb-4f16-8196-95c26cc5cce9"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1">akn:recitals</akn:p>
                           </akn:td>
                        </akn:tr>
                        <akn:tr GUID="599ad3ad-88a1-4249-a793-99c10f52fb3b"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5">
                           <akn:td style="text-align:right"
                                   GUID="ad6c5b7a-36dd-48e3-a30b-7711fe5f8052"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1">
                              <akn:p GUID="f49c31d4-4d84-43c6-931d-be45b8ad7384"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1">praeambelInhalt</akn:p>
                           </akn:td>
                           <akn:td GUID="ba6836a8-e50c-4f59-887b-9aea2b14c563"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2">
                              <akn:p GUID="6524f0f9-992d-4c30-b576-da6d42ac7243"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1">akn:recital</akn:p>
                           </akn:td>
                        </akn:tr>
                        <akn:tr GUID="c0a6a0b0-a4c4-4ec9-9440-6c6104652310"
                                eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6">
                           <akn:td style="text-align:right"
                                   GUID="f2d15f3e-6c54-4f91-ba72-ce8aa6268744"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1">
                              <akn:p GUID="477c0165-6fcb-4d5c-a676-d87cc1b25f98"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1">verzeichniscontainer</akn:p>
                           </akn:td>
                           <akn:td GUID="3c0964c4-4152-46e8-a500-8b23ce90153d"
                                   eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2">
                              <akn:p GUID="98067c96-4c16-4f56-8f6b-6aaca1eb657e"
                                     eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1">akn:blockContainer</akn:p>
                           </akn:td>
                        </akn:tr>
                     </akn:table>
                     <akn:p GUID="5b06d8ee-e918-4376-871a-5b36cdb3219e"
                            eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_text-2">Die Tabelle ist nur ein Auszug aus der Spezifikation und erhebt keinen Anspruch auf Vollständigkeit.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:book>
         <akn:book GUID="7ced0227-a342-405e-a29c-3f2aeca57b54" eId="hauptteil-1_buch-2">
            <akn:num GUID="407cbe29-91f4-416e-8d2d-05ce124c623e"
                     eId="hauptteil-1_buch-2_bezeichnung-1">2. Buch</akn:num>
            <akn:heading GUID="d82b4f90-487a-4f92-b3af-67a7b778bb3a"
                         eId="hauptteil-1_buch-2_überschrift-1">Beispiele für Strukturen</akn:heading>
            <akn:chapter GUID="786c34af-047c-4e03-881b-3104241f2093"
                         eId="hauptteil-1_buch-2_kapitel-1">
               <akn:num GUID="e5c861dd-dc27-40a4-8471-a79fb7d6ced0"
                        eId="hauptteil-1_buch-2_kapitel-1_bezeichnung-1"/>
               <akn:heading GUID="0035e452-84cc-47ed-96f5-928be77e1b27"
                            eId="hauptteil-1_buch-2_kapitel-1_überschrift-1">Implementierung</akn:heading>
               <akn:article GUID="124ed2cf-0734-4e90-8ded-26d88ba5fc9b"
                            eId="hauptteil-1_buch-2_kapitel-1_art-1"
                            period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                  <akn:num GUID="863d1daa-4bb0-479e-917f-50c64a487c40"
                           eId="hauptteil-1_buch-2_kapitel-1_art-1_bezeichnung-1">§ 3</akn:num>
                  <akn:heading GUID="940e9c4e-161e-4e1c-a410-cd2a3cd65cdf"
                               eId="hauptteil-1_buch-2_kapitel-1_art-1_überschrift-1">Implementierung der neuen Strukturen</akn:heading>
                  <akn:paragraph GUID="f61015ee-74cf-47ff-ac97-1aac9f3136e2"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1">
                     <akn:num GUID="86d775d9-a8d7-4f5f-8209-04bba841e907"
                              eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
                     <akn:content GUID="6d35767d-745c-4a47-a23c-9617e8028b9a"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_inhalt-1">
                        <akn:p GUID="d67055e6-15ed-49a6-8999-7b1fba4841fb"
                               eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_inhalt-1_text-1">Die Implementierung der in diesem Gesetz vorgesehenen neuen Strukturen und Gliederungseinheiten erfolgt schrittweise und in enger Abstimmung mit den betroffenen Verwaltungseinheiten.</akn:p>
                     </akn:content>
                  </akn:paragraph>
                  <akn:paragraph GUID="0f901c0e-b213-42b9-b3da-63ad88b250c7"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2">
                     <akn:num GUID="1f2ba0b6-18da-4bc9-91fe-aabe104c6af6"
                              eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
                     <akn:list GUID="ea667012-e361-42c4-809d-8854f2ed4da6"
                               eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1">
                        <akn:intro GUID="8b236976-3469-4263-895e-4f279e97f724"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_intro-1">
                           <akn:p GUID="f88f5b95-28da-46c8-9c05-6fcc84b20c89"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_intro-1_text-1">Zur Unterstützung der Implementierung wird eine zentrale Koordinierungsstelle eingerichtet, die die folgenden Aufgaben übernimmt:</akn:p>
                        </akn:intro>
                        <akn:point GUID="e729b66d-7fa3-4eb4-8c16-07e1d51f8ebd"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1">
                           <akn:num GUID="15681be1-6bc6-4f89-ae1d-6784e3a8907b"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                           <akn:content GUID="916c9b13-6486-4010-818c-e43755d40c08"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_inhalt-1">
                              <akn:p GUID="952b166d-0d71-4793-9bd3-21e8531a5949"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">Erstellung eines detaillierten Implementierungsplans, der die einzelnen Schritte und Zeitrahmen festlegt,</akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="d4792db6-47c3-430d-ac3e-9dce6b40ed41"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2">
                           <akn:num GUID="e9e424b7-44bf-4e41-80cf-ab0f1bb9c82f"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                           <akn:content GUID="970bb94b-1ebf-4cbb-ab9b-9c5d287772c1"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_inhalt-1">
                              <akn:p GUID="c28f32e0-1efa-455a-817c-439faefa18ba"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">Bereitstellung von Ressourcen und Schulungen für die betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="7aae333a-0e7b-406a-97e5-d4ce5615f22a"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3">
                           <akn:num GUID="1f171620-1079-4ab5-bd85-30de98ac7107"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                           <akn:content GUID="bd92f48c-944e-4238-9256-02e8276c3808"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_inhalt-1">
                              <akn:p GUID="4d9bea9e-3c33-40df-83da-9d5709327d7d"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">Beratung und Unterstützung der Verwaltungseinheiten bei der Umsetzung der neuen Strukturen und Prozesse,</akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="271a6e62-0c85-47e7-a03a-787de96feb2e"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4">
                           <akn:num GUID="b0cb1259-63d7-4cce-9aad-9c359102dbb3"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_bezeichnung-1">3.</akn:num>
                           <akn:content GUID="ba810a82-4b6d-498f-9937-53822de8d173"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_inhalt-1">
                              <akn:p GUID="69088a50-760d-433b-9b3b-1d970965d3dc"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">Überwachung und Bewertung des Fortschritts der Implementierung und Anpassung des Plans bei Bedarf.</akn:p>
                           </akn:content>
                        </akn:point>
                     </akn:list>
                  </akn:paragraph>
                  <akn:paragraph GUID="05093fbb-bc4e-4f3d-b89a-e38368d60217"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3">
                     <akn:num GUID="f1db9c1f-6097-4456-8219-3a523d1b201c"
                              eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
                     <akn:content GUID="1c771420-63f6-452e-af87-139f6eb5d67d"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_inhalt-1">
                        <akn:p GUID="78eed4f3-6adf-435d-be68-de4fa8a21c65"
                               eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
                     </akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article GUID="af57a792-7960-438f-ab52-c39781730550"
                            eId="hauptteil-1_buch-2_kapitel-1_art-2"
                            period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                  <akn:num GUID="c2707ee7-c833-4651-862a-4060e22be2f0"
                           eId="hauptteil-1_buch-2_kapitel-1_art-2_bezeichnung-1">§ 4</akn:num>
                  <akn:heading GUID="7e411859-66de-4ebf-a788-8a87f2561beb"
                               eId="hauptteil-1_buch-2_kapitel-1_art-2_überschrift-1">Finanzielle Unterstützung</akn:heading>
                  <akn:paragraph GUID="1540c03e-72bc-4edb-9c75-0faa2461f7d0"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1">
                     <akn:num GUID="fe9c05b9-9392-48b3-95af-894e3ec6c16e"
                              eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_bezeichnung-1">(1)</akn:num>
                     <akn:content GUID="085f17e6-eb15-48bf-9573-e8b1e6447c43"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_inhalt-1">
                        <akn:p GUID="3985e834-8044-45fe-8ef7-fc94ab141c6c"
                               eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_inhalt-1_text-1">Zur Finanzierung der Maßnahmen nach diesem Gesetz stellt der Bund den Ländern und Kommunen zweckgebundene Finanzmittel zur Verfügung.</akn:p>
                     </akn:content>
                  </akn:paragraph>
                  <akn:paragraph GUID="a4b1e6c6-0565-4a5f-8deb-82e123b40ad9"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2">
                     <akn:num GUID="c0f5d74c-9273-4bb3-a38f-6e0030acb51f"
                              eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_bezeichnung-1">(2)</akn:num>
                     <akn:list GUID="50f8db22-12dc-494d-82e4-11f4cd1578e1"
                               eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1">
                        <akn:intro GUID="7f4f2b9e-4297-4baa-badd-f7152a4f4080"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_intro-1">
                           <akn:p GUID="2b90e9af-d1b8-4ebe-886c-5280c1bc7495"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_intro-1_text-1">Die Höhe der Finanzmittel wird jährlich im Bundeshaushalt festgelegt und richtet sich nach:</akn:p>
                        </akn:intro>
                        <akn:point GUID="f79e151e-9a65-45d6-a573-86fba957cea5"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1">
                           <akn:num GUID="13b49196-caa2-4198-beec-b1308cee4cef"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                           <akn:content GUID="f5c9d2d1-925d-431e-be08-eefba237fe5e"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1">
                              <akn:p GUID="76b1fdaf-f9b3-45d3-ad97-694fa08abdf0"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">dem Umfang der erforderlichen Maßnahmen in den jeweiligen Verwaltungseinheiten,</akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="31ebe8e3-6e16-4f3b-88b2-7abc21abc9d3"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2">
                           <akn:num GUID="fff7cfe4-7f7d-4e62-a9f9-102d180c7a3a"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                           <akn:content GUID="fd982005-f8e3-49d5-a474-afef7d18d756"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1">
                              <akn:p GUID="b4e30d2c-00d3-41fa-b96d-630d5b79d450"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">der Anzahl der betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                           </akn:content>
                        </akn:point>
                        <akn:point GUID="c69618f2-149a-463d-90b8-498a95574bb9"
                                   eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3">
                           <akn:num GUID="c05f8ec4-23db-44da-8346-5f34769f074e"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                           <akn:content GUID="730c850f-7360-4be3-b9bd-43457d064b83"
                                        eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1">
                              <akn:p GUID="85bdc50e-afa3-4eab-976d-91c642c8cb4c"
                                     eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">den spezifischen regionalen Anforderungen und Besonderheiten. </akn:p>
                           </akn:content>
                        </akn:point>
                     </akn:list>
                  </akn:paragraph>
                  <akn:paragraph GUID="ad760523-d320-41a6-be13-61746d65b9b6"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3">
                     <akn:num GUID="b98e90b5-6cb0-4b60-929b-588bea1a0578"
                              eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_bezeichnung-1">(3)</akn:num>
                     <akn:content GUID="b659512f-11b9-49d6-8e98-7974ba9d39bd"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1">
                        <akn:p GUID="9503bad3-301a-4f36-af90-d17b2e0b65bd"
                               eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1_text-1">Die Verwendung der Finanzmittel ist zweckgebunden und muss im Rahmen der jährlichen Berichterstattung nach <akn:ref href="#hauptteil-1_buch-2_kapitel-1_art-3"
                                    GUID="443cd856-bae6-470c-8e5f-b22d1f62cf95"
                                    eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1_text-1_ref-1">Artikel 5</akn:ref> detailliert nachgewiesen werden.</akn:p>
                     </akn:content>
                  </akn:paragraph>
               </akn:article>
               <akn:article GUID="556d5740-71d9-4c67-bf8d-4b67eab04673"
                            eId="hauptteil-1_buch-2_kapitel-1_art-3"
                            period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                  <akn:num GUID="546a3bb6-0f04-4890-a835-8f99d67d010e"
                           eId="hauptteil-1_buch-2_kapitel-1_art-3_bezeichnung-1">§ 5</akn:num>
                  <akn:heading GUID="948a5265-3ac1-43ba-8862-d3a7893413df"
                               eId="hauptteil-1_buch-2_kapitel-1_art-3_überschrift-1">Evaluierung und Anpassung</akn:heading>
                  <akn:paragraph GUID="3fb65334-c8c2-49b6-886b-ef8c06a1cacf"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1">
                     <akn:num GUID="a9c32af4-9960-44c7-a00f-9122e46d7214"
                              eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_bezeichnung-1">(1)</akn:num>
                     <akn:content GUID="eec02832-8af4-4e3d-8529-d84d460585c6"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_inhalt-1">
                        <akn:p GUID="8d361d3d-f8e6-4adc-8ace-cf1e72584bcb"
                               eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_inhalt-1_text-1">Die Wirksamkeit der durch dieses Gesetz eingeführten Maßnahmen wird regelmäßig durch unabhängige Gutachter überprüft.</akn:p>
                     </akn:content>
                  </akn:paragraph>
                  <akn:paragraph GUID="b3399d37-04a7-41b1-8dc3-ef1f0ba9aa15"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2">
                     <akn:num GUID="07680e28-b66a-468c-bd21-be2300cbbd45"
                              eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_bezeichnung-1">(2)</akn:num>
                     <akn:content GUID="76da6af0-94a8-40d2-8926-908bdaa56d7d"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_inhalt-1">
                        <akn:p GUID="0e365a0f-2381-45b8-816c-20610f3d324f"
                               eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_inhalt-1_text-1">Die erste Evaluierung erfolgt zwei Jahre nach Inkrafttreten dieses Gesetzes, weitere Evaluierungen folgen alle drei Jahre.</akn:p>
                     </akn:content>
                  </akn:paragraph>
                  <akn:paragraph GUID="3b2e7f7c-babf-4ab6-a329-a4017659cde0"
                                 eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3">
                     <akn:num GUID="80d56e95-869a-43e5-9bc4-2cea32eff1c7"
                              eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_bezeichnung-1">(3)</akn:num>
                     <akn:content GUID="753c9514-1a54-414c-9117-50d8dfa55c71"
                                  eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_inhalt-1">
                        <akn:p GUID="8681866d-7711-4a90-bb18-1afd414c1475"
                               eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_inhalt-1_text-1">Basierend auf den Ergebnissen der Evaluierungen kann der Gesetzgeber Anpassungen und Verbesserungen dieses Gesetzes vornehmen, um die angestrebten Ziele effektiver zu erreichen.</akn:p>
                     </akn:content>
                  </akn:paragraph>
               </akn:article>
            </akn:chapter>
         </akn:book>
         <akn:book GUID="89d3df71-bdbc-4674-8c6e-318acd064f35" eId="hauptteil-1_buch-3">
            <akn:num GUID="137a3d6d-e093-40f0-aa2c-2dcb16c74b4e"
                     eId="hauptteil-1_buch-3_bezeichnung-1">3. Buch</akn:num>
            <akn:heading GUID="d634a4fa-676e-4065-af5f-b04afd157cff"
                         eId="hauptteil-1_buch-3_überschrift-1">Beispiele Teil I</akn:heading>
            <akn:article GUID="7a98e300-c1c9-47c1-b0f1-114c085463ca"
                         eId="hauptteil-1_buch-3_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:num GUID="92de17ac-1f72-4932-9eac-d7087e33d481"
                        eId="hauptteil-1_buch-3_art-1_bezeichnung-1">§ 6</akn:num>
               <akn:heading GUID="696c59ca-09ce-4ab1-a165-60ef132b4b5e"
                            eId="hauptteil-1_buch-3_art-1_überschrift-1">Beispielartikel</akn:heading>
               <akn:paragraph GUID="0b2bbb4c-117d-44c4-946b-656e0dc93cae"
                              eId="hauptteil-1_buch-3_art-1_abs-1">
                  <akn:num GUID="c08fb9b1-bbc7-4ce9-beea-4fa36fa6c912"
                           eId="hauptteil-1_buch-3_art-1_abs-1_bezeichnung-1"/>
                  <akn:content GUID="c337175d-a06a-4e81-a438-8b338cdd802b"
                               eId="hauptteil-1_buch-3_art-1_abs-1_inhalt-1">
                     <akn:p GUID="adc609b2-0171-4c85-83ae-fb07b84e6fa1"
                            eId="hauptteil-1_buch-3_art-1_abs-1_inhalt-1_text-1">Die Koordinierungsstelle, die im Rahmen des Gesetzes eingerichtet wird, spielt eine zentrale Rolle bei der Umsetzung der Reformen. Sie fungiert als zentrale Anlaufstelle für alle staatlichen und kommunalen Verwaltungen, die Unterstützung bei der Neustrukturierung ihrer organisatorischen Einheiten benötigen. Zu den Hauptaufgaben der Koordinierungsstelle gehören die Erstellung detaillierter Implementierungspläne, die Bereitstellung von Ressourcen und Schulungen sowie die Beratung der Verwaltungseinheiten bei der Einführung neuer Technologien und Prozesse. Zudem überwacht die Koordinierungsstelle den Fortschritt der Reformen und passt die Maßnahmen bei Bedarf an, um eine effiziente und effektive Umsetzung sicherzustellen.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:book>
         <akn:book GUID="76df0530-c77a-4335-86b0-9a9151b1e46c" eId="hauptteil-1_buch-4">
            <akn:num GUID="f783b2f0-4473-4794-9cc3-9e5617b3f67b"
                     eId="hauptteil-1_buch-4_bezeichnung-1">4. Buch</akn:num>
            <akn:heading GUID="457ccc63-05ce-4677-a9cc-bd244cdce140"
                         eId="hauptteil-1_buch-4_überschrift-1">Beispiel Teil II</akn:heading>
            <akn:chapter GUID="e3c056bb-449a-4430-8052-d4eac7614967"
                         eId="hauptteil-1_buch-4_kapitel-1">
               <akn:num GUID="0ffa799c-7545-4c98-b7ba-11471fb78cc5"
                        eId="hauptteil-1_buch-4_kapitel-1_bezeichnung-1"/>
               <akn:heading GUID="7a6484b0-ded8-45d5-b16f-1574891dab47"
                            eId="hauptteil-1_buch-4_kapitel-1_überschrift-1">Beispielkapitel</akn:heading>
               <akn:article GUID="1f46ba41-50a0-4c77-9041-cd3d918d2c42"
                            eId="hauptteil-1_buch-4_kapitel-1_art-1"
                            period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                  <akn:num GUID="da5f5eee-79d8-4057-bf02-bdc7c4121897"
                           eId="hauptteil-1_buch-4_kapitel-1_art-1_bezeichnung-1">§ 7</akn:num>
                  <akn:heading GUID="95718678-008a-4441-afa4-0527a17ab363"
                               eId="hauptteil-1_buch-4_kapitel-1_art-1_überschrift-1">Beispielartikel</akn:heading>
                  <akn:paragraph GUID="860300e4-a96e-4fab-ab68-33da10b19677"
                                 eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1">
                     <akn:num GUID="0c86397f-56cc-4d7b-8172-54ee31b052cf"
                              eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_bezeichnung-1"/>
                     <akn:content GUID="47e8b7c5-d8c9-4ebe-b262-acea52635811"
                                  eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_inhalt-1">
                        <akn:p GUID="933bf6c3-ca24-4155-a80e-080632840a4e"
                               eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_inhalt-1_text-1">Der Implementierungsplan ist ein zentraler Bestandteil des Gesetzes und dient als Leitfaden für die schrittweise Umsetzung der vorgesehenen Reformen. Dieser Plan wird von der zentralen Koordinierungsstelle in enger Zusammenarbeit mit den betroffenen Verwaltungseinheiten entwickelt. Er umfasst detaillierte Zeitpläne, klare Meilensteine und spezifische Maßnahmen, die zur Modernisierung der Strukturen notwendig sind.</akn:p>
                     </akn:content>
                  </akn:paragraph>
               </akn:article>
            </akn:chapter>
         </akn:book>
         <akn:book GUID="71e18ea7-8f69-45ce-9565-918e2211d41b" eId="hauptteil-1_buch-5">
            <akn:num GUID="0c09b417-e57a-4d08-a3df-51e9e2edbfea"
                     eId="hauptteil-1_buch-5_bezeichnung-1">5. Buch</akn:num>
            <akn:heading GUID="c043ecb0-4389-4f78-b850-5229573e4790"
                         eId="hauptteil-1_buch-5_überschrift-1">Übergangsregelungen und Geltungszeiten</akn:heading>
            <akn:article GUID="f74e0f1a-a9b8-4dcf-83ff-da99829b8009"
                         eId="hauptteil-1_buch-5_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:num GUID="8c2cca2e-e8c6-4d9c-987e-e95077d07943"
                        eId="hauptteil-1_buch-5_art-1_bezeichnung-1">§ 8</akn:num>
               <akn:heading GUID="2c380b4d-2a92-4950-89b2-e501b46de3c5"
                            eId="hauptteil-1_buch-5_art-1_überschrift-1">Übergangsregelungen</akn:heading>
               <akn:paragraph GUID="e46dc027-aefe-4bf9-af07-240a1706883c"
                              eId="hauptteil-1_buch-5_art-1_abs-1">
                  <akn:num GUID="ea8f0493-2a89-49a7-acb4-6481de112181"
                           eId="hauptteil-1_buch-5_art-1_abs-1_bezeichnung-1">(1)</akn:num>
                  <akn:list GUID="c39f53ce-5600-44ef-a81c-c00d8a417c26"
                            eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1">
                     <akn:intro GUID="172b1ad2-a3cd-4b97-b06b-5b3325237e01"
                                eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_intro-1">
                        <akn:p GUID="12eee750-6089-4228-81b3-2e214fcc6c27"
                               eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_intro-1_text-1">Während der Übergangsphase, die bis zu fünf Jahre nach Inkrafttreten dieses Gesetzes dauern kann, gelten folgende Regelungen:</akn:p>
                     </akn:intro>
                     <akn:point GUID="c90c9b23-5c38-4dc0-9e5e-e625fd629f2e"
                                eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="08d45688-c273-4dbb-b05a-d7fedec4248a"
                                 eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                        <akn:content GUID="625a0feb-4cfb-4617-9c8e-96252c20e4ac"
                                     eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="89371c1f-93ea-4a9b-ac4c-d48d4df5a27a"
                                  eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">Bestehende Strukturen und Gliederungseinheiten dürfen parallel zu den neuen Einheiten weitergeführt werden, sofern dies zur Sicherstellung der Kontinuität der Verwaltungsabläufe erforderlich ist,</akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="193d1e66-cac4-442c-a9dc-3628e4294bbe"
                                eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="57e063e4-9dd4-41ea-b207-d3eaca6a20cf"
                                 eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                        <akn:content GUID="b57e0bb0-7136-4b29-8526-f4485cbec1f6"
                                     eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="c2e08b00-0604-4626-ac80-70ca5795d15d"
                                  eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">Verwaltungseinheiten, die Schwierigkeiten bei der Umsetzung der neuen Strukturen haben, können bei der zentralen Koordinierungsstelle Unterstützung beantragen,</akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="4de4d9d7-2638-4902-b583-74a21173552d"
                                eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="2da86f68-bb42-48b9-8143-5a58ed650373"
                                 eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                        <akn:content GUID="fcde9c7e-372b-4b4a-a8b7-47dee8a7f477"
                                     eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="08cfdd8e-5da8-4a2e-8dce-b03029d918c5"
                                  eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">Mitarbeiterinnen und Mitarbeiter, deren Aufgaben sich durch die neuen Strukturen ändern, haben Anspruch auf Weiterbildungs- und Umschulungsmaßnahmen.</akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="38f46bbf-9b8a-407e-a35b-cbb0dd30b865"
                              eId="hauptteil-1_buch-5_art-1_abs-2">
                  <akn:num GUID="acef7e7d-2a6e-4125-bcbc-1a622998d65d"
                           eId="hauptteil-1_buch-5_art-1_abs-2_bezeichnung-1">(2)</akn:num>
                  <akn:content GUID="7f8d72ff-327e-4fba-bf8f-71fc706cb90a"
                               eId="hauptteil-1_buch-5_art-1_abs-2_inhalt-1">
                     <akn:p GUID="73cc3748-de33-4f83-b2a9-756ca54ae74d"
                            eId="hauptteil-1_buch-5_art-1_abs-2_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article refersTo="geltungszeitregel"
                         GUID="e1ea4bcc-fe9c-44de-8478-288220156ff3"
                         eId="hauptteil-1_buch-5_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:num GUID="70c3f946-0f1e-486d-bb26-1abee3e9cf43"
                        eId="hauptteil-1_buch-5_art-2_bezeichnung-1">§ 9</akn:num>
               <akn:heading GUID="a25192d9-4e56-42a6-9e94-7500685523fd"
                            eId="hauptteil-1_buch-5_art-2_überschrift-1">Inkrafttreten</akn:heading>
               <akn:paragraph GUID="c1893fbc-081f-436d-8d82-33417a44293d"
                              eId="hauptteil-1_buch-5_art-2_abs-1">
                  <akn:num GUID="4c634a60-9680-46b1-ac23-a0f0ca2c750f"
                           eId="hauptteil-1_buch-5_art-2_abs-1_bezeichnung-1"/>
                  <akn:content GUID="cc2700df-62d9-41a9-94d8-4caac51402ab"
                               eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1">
                     <akn:p GUID="64ae21cd-4d8a-45ae-9344-009103c3e983"
                            eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt <akn:date date="1002-01-02"
                                  refersTo="inkrafttreten-datum"
                                  GUID="24589ea7-c00c-4468-87eb-576ba9b37865"
                                  eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1_text-1_datum-1">am Tag nach der Verkündung</akn:date> in Kraft.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:book>
      </akn:body>
   </akn:act>
</akn:akomaNtoso>');
