export const targetLawXml = `<?xml version="1.0" encoding="UTF-8"?><!--
   This is a modified example from the LDML.de specification.
--><!--
\t##################################################################################
\tProjekt E-Gesetzgebung
\tNicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

\t2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
\tReferat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
\tVerwaltungsarbeit

\tVeröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
\t##################################################################################
--><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
      <!-- Metadaten -->
      <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
         <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51" eId="meta-1_ident-1_frbrwork-1">
               <akn:FRBRthis GUID="7ec6fe21-8b58-4e10-8b5d-6c9e179f725b" eId="meta-1_ident-1_frbrwork-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/regelungstext-1"/>
               <akn:FRBRuri GUID="c2182564-fee7-45f3-bcf2-df42bcb5ab25" eId="meta-1_ident-1_frbrwork-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593"/>
               <akn:FRBRalias GUID="84dd7529-36d6-4504-8c1c-6298f2610873" eId="meta-1_ident-1_frbrwork-1_frbralias-1" name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a"/>
               <akn:FRBRdate GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" eId="meta-1_ident-1_frbrwork-1_frbrdate-1" name="verkuendungsfassung"/>
               <akn:FRBRauthor GUID="b6ee5aa8-8950-4b81-be73-0f468c62c509" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry GUID="6c0a9858-7faf-4d88-b944-e60270d687a9" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" value="de"/>
               <akn:FRBRnumber GUID="b82cc174-8fff-43bf-a434-5646de09e807" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" value="s593"/>
               <akn:FRBRname GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" eId="meta-1_ident-1_frbrwork-1_frbrname-1" value="bgbl-1"/>
               <akn:FRBRsubtype GUID="f0d6366e-854f-4901-ab66-b054509e9364" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
               <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
               <akn:FRBRuri GUID="ad94eb82-1a61-4210-939f-7e423fbc78d4" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"/>
               <akn:FRBRalias GUID="76d63725-d669-4189-995d-92b9f8e4dadb" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="vorherige-version-id" value="30c19ca3-cf77-4ff9-8623-0cf60abac28e"/>
               <akn:FRBRalias GUID="519cad64-9102-4808-99ba-ea64988745bd" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="aktuelle-version-id" value="d04791fc-dcdc-47e6-aefb-bc2f7aaee151"/>
               <akn:FRBRalias GUID="849f7a4f-0477-4e00-8631-ca8232b32d2f" eId="meta-1_ident-1_frbrexpression-1_frbralias-3" name="nachfolgende-version-id" value="a0bdb90e-31ca-4a48-b773-89cf858208fa"/>
               <akn:FRBRauthor GUID="e4334f35-501a-46f0-a8f5-a02d9a03aca3" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate GUID="1bfbd095-93f3-4674-8096-be18041cbc65" date="1964-08-05" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" name="verkuendung"/>
               <akn:FRBRlanguage GUID="69cb29f5-3d31-48a4-8b48-14b1f23b64fb" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" language="deu"/>
               <akn:FRBRversionNumber GUID="e346f244-30e0-40fa-81d0-ff21c4f34fb3" eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation GUID="c2bb71f6-2e7f-4ba2-9f6e-fd4834114338" eId="meta-1_ident-1_frbrmanifestation-1">
               <akn:FRBRthis GUID="e3e3a77d-4027-4d7f-9e8d-66393d4f3a26" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml"/>
               <akn:FRBRuri GUID="a1118c13-2463-4c73-9375-dd39eab2e701" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml"/>
               <akn:FRBRdate GUID="fbdae376-ccbe-4097-9bbe-4e91203b15d8" date="1964-08-05" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" name="generierung"/>
               <akn:FRBRauthor GUID="5bbaf2cc-d861-4a57-b019-c4596b816673" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" href="recht.bund.de"/>
               <akn:FRBRformat GUID="6b4cbf44-5fd0-416c-9206-c0a48f778d29" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>

         <akn:lifecycle GUID="206a6403-76aa-4c9a-b03b-8c3d510d81db" eId="meta-1_lebzykl-1" source="attributsemantik-noch-undefiniert">
            <akn:eventRef GUID="e545b87d-3f02-4fd1-b199-089f19c395c3" date="1964-08-05" eId="meta-1_lebzykl-1_ereignis-1" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" type="generation"/>
            <akn:eventRef GUID="3f84ea91-54e6-44c7-8e24-009d68a7c911" date="1964-09-21" eId="meta-1_lebzykl-1_ereignis-2" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="generation"/>
         </akn:lifecycle>

         <akn:temporalData GUID="0b03ee18-0131-47ec-bd46-519d60209cc7" eId="meta-1_geltzeiten-1" source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup GUID="2b728878-4b15-4b14-89cd-ecc270202a81" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:timeInterval GUID="10819a15-54fe-4924-a469-4a097f9c0e41" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
            </akn:temporalGroup>
         </akn:temporalData>

         <!-- Diese Metadaten sind die Konstituenten für die Schematron-Validierung besitzen keine fachliche Korrektheit. -->
         <akn:proprietary GUID="7bd9d0dd-6dfe-4307-a699-fcce5ea3f03c" eId="meta-1_proprietary-1" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <meta:fna>754-28-1</meta:fna>
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <meta:federfuehrung>
                  <meta:federfuehrend ab="1964-08-05" bis="unbestimmt">nicht-vorhanden</meta:federfuehrend>
                  <meta:federfuehrend ab="1964-08-05" bis="unbestimmt">nicht-vorhanden</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>

      <!-- Dokumentenkopf Regelungstext -->
      <akn:preface GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea" eId="einleitung-1">
         <akn:longTitle GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6" eId="einleitung-1_doktitel-1">
            <akn:p GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0" eId="einleitung-1_doktitel-1_text-1">
               <akn:docStage GUID="3b355cab-ce10-45b5-9cde-cc618fbf491f" eId="einleitung-1_doktitel-1_text-1_docstadium-1"/>
               <akn:docProponent GUID="c83abe1e-5fde-4e4e-a9b5-7293505ffeff" eId="einleitung-1_doktitel-1_text-1_docproponent-1"/>
               <akn:docTitle GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
               <akn:shortTitle GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">( <akn:inline GUID="bdff7240-266e-4ff3-b311-60342bd1afa2" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" name="attributsemantik-noch-undefiniert" refersTo="amtliche-abkuerzung">Vereinsgesetz</akn:inline>)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
         <akn:block GUID="010d9df0-817a-49b6-a121-d0a1d412a3e3" eId="einleitung-1_block-1" name="attributsemantik-noch-undefiniert">
            <akn:date GUID="28fafbe4-403d-4436-8d0d-7241cbbdade0" date="1964-08-05" eId="einleitung-1_block-1_datum-1" refersTo="ausfertigung-datum">Vom 5. August 1964 </akn:date>
         </akn:block>
      </akn:preface>

      <!-- Eingangsformel + Inhaltsübersicht -->
      <akn:preamble GUID="d3023c46-1e74-4c1f-b101-b29898267f71" eId="preambel-1">
         <akn:formula GUID="1b476669-1cbb-4633-a0ed-04783680b841" eId="preambel-1_formel-1" name="attributsemantik-noch-undefiniert" refersTo="eingangsformel">
            <akn:p GUID="c1793533-b05a-4fc4-be62-bd6c3449c53a" eId="preambel-1_formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
         </akn:formula>
      </akn:preamble>

      <!-- Hauptteil -->
      <akn:body GUID="2094abe2-16e6-44d5-9198-3ef9486833e9" eId="hauptteil-1">
         <akn:article GUID="b1b4bd3b-e007-4d84-af83-b8e36a0ae50b" eId="hauptteil-1_para-20" period="#geltungszeitgr-1">
            <akn:num GUID="f82ab983-5498-49ab-918f-5cf5e730e5ec" eId="hauptteil-1_para-20_bezeichnung-1">
               <akn:marker GUID="24098f2f-05e0-4add-8941-286c8b239194" eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" name="20"/>§ 20</akn:num>
            <akn:paragraph GUID="6aa3a7ca-f30a-43b6-950b-b1e942fd1842" eId="hauptteil-1_para-20_abs-1" period="#geltungszeitgr-2">
               <akn:num GUID="e363f12d-7918-435c-b3a1-182c5e03ff43" eId="hauptteil-1_para-20_abs-1_bezeichnung-1">
                  <akn:marker GUID="3d4cac8d-fd22-4944-a767-3bf0e5f2c5b0" eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" name="1"/>(1) </akn:num>
               <akn:list GUID="97e930bf-49f8-472a-a1fa-3c3a401caa13" eId="hauptteil-1_para-20_abs-1_untergl-1">
                  <akn:intro GUID="e2261ee5-feda-4691-b1a0-8a18f43720e7" eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1">
                     <akn:p GUID="f5b78960-3376-4f19-a1de-05e24443a141" eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
                  </akn:intro>
                  <akn:point GUID="6f1b22e6-2fcc-4e29-b3c1-fd67d8cee45c" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1">
                     <akn:num GUID="7947817d-e127-49ca-82c3-a1dfeaa73748" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                        <akn:marker GUID="7b98614e-78d8-4c77-98ed-d00841545900" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" name="1"/>1. </akn:num>
                     <akn:content GUID="fff000f9-bed8-41eb-b26b-93a5da40ff5f" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1">
                        <akn:p GUID="6ad07a0f-46f6-45b5-8d1a-ac79b8f704fe" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied
                           beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="94af45e6-d49b-4bcc-84b9-5e5c01d5a3ba" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2">
                     <akn:num GUID="18a496f3-c0e7-4fb3-a41d-5c9fd75374c7" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                        <akn:marker GUID="f026a323-e5ff-4784-80f9-dbb1c2c3bad5" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" name="2"/>2. </akn:num>
                     <akn:content GUID="7e835e4b-52eb-4fa1-9698-d7a42589d715" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="0ba9a471-e9ef-44c4-b5da-f69f068a4483" eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp GUID="107ed2fd-e041-4ee4-9eee-b559f84a4ce8" eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1">
                     <akn:p GUID="53af6200-56c2-4fed-bd27-25ddefe7b204" eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit
                        schwerer Strafe bedroht ist.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <akn:paragraph GUID="f0f31024-8381-4a62-8980-5f432d03a925" eId="hauptteil-1_para-20_abs-2" period="#geltungszeitgr-1">
               <akn:num GUID="c0ea5d8c-8d91-4e0c-812e-429a7a24a1a2" eId="hauptteil-1_para-20_abs-2_bezeichnung-1">
                  <akn:marker GUID="a78eb14d-6ba5-429d-bd48-3cbb0789f9c0" eId="hauptteil-1_para-20_abs-2_bezeichnung-1_zaehlbez-1" name="2"/>(2) </akn:num>
               <akn:content GUID="8b51c358-ca54-4949-b957-bcbe5e55f33d" eId="hauptteil-1_para-20_abs-2_inhalt-1">
                  <akn:p GUID="924d7140-bdfd-4a50-9b7f-feb006d19aae" eId="hauptteil-1_para-20_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>

         <!-- Artikel 34: Geltungszeitregel-->
         <akn:article GUID="6574da6e-2a23-4280-80dd-b4f4c5416cdf" eId="hauptteil-1_para-34" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
            <akn:num GUID="19b65c8b-44ae-4092-be09-7de64ac623e5" eId="hauptteil-1_para-34_bezeichnung-1">
               <akn:marker GUID="e175bd87-32c5-45ae-ae76-b6c78b446729" eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1" name="34"/>Artikel 34</akn:num>
            <akn:heading GUID="772db065-a163-4453-a22f-269f6aebddaa" eId="hauptteil-1_para-34_überschrift-1">Inkrafttreten</akn:heading>
            <!-- Absatz (1) -->
            <akn:paragraph GUID="38c00dca-43e1-4cb5-aa69-bd03cc486c99" eId="hauptteil-1_para-34_abs-1">
               <akn:num GUID="b5758ec8-bcb9-4b69-8764-fb86911e937c" eId="hauptteil-1_para-34_abs-1_bezeichnung-1">
                  <akn:marker GUID="20787d69-f97d-4451-9afa-d643d503ade6" eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1" name="1"/>
               </akn:num>
               <akn:content GUID="45ca26fa-b7c0-416b-9a0a-7f2bd7c86873" eId="hauptteil-1_para-34_abs-1_inhalt-1">
                  <akn:p GUID="aa475227-3bb4-4568-99b3-98467b8daf91" eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
      </akn:body>

      <!-- Schluss -->
      <akn:conclusions GUID="5814d07a-3869-489d-b03e-239304e841cb" eId="schluss-1">
         <akn:formula GUID="a269a1a9-e8ca-471a-a9d4-ce61e9af05aa" eId="schluss-1_formel-1" name="attributsemantik-noch-undefiniert" refersTo="schlussformel">
            <akn:p GUID="1354658b-fdb7-46b4-96e2-e194b16ee103" eId="schluss-1_formel-1_text-1">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
         </akn:formula>
         <!-- Signaturen -->
         <akn:blockContainer GUID="35e11af5-ff7c-4422-af51-87cff3cc7ceb" eId="schluss-1_blockcontainer-1">
            <akn:p GUID="b55d6230-d71c-49d5-bf86-dab1db53ba6f" eId="schluss-1_blockcontainer-1_text-1">
               <akn:location GUID="19ffc7e1-add6-40a4-b9af-2f0927e46e9c" eId="schluss-1_blockcontainer-1_text-1_ort-1" refersTo="attributsemantik-noch-undefiniert">Bonn</akn:location>, den <akn:date GUID="34cf44aa-73a4-4dee-9827-4d890da7aac7" date="1964-08-05" eId="schluss-1_blockcontainer-1_text-1_datum-1" refersTo="ausfertigung-datum">5. August 1964</akn:date>
            </akn:p>
            <akn:signature GUID="b092cd1d-2f77-4fe6-a63d-5bcabeb410ad" eId="schluss-1_blockcontainer-1_signatur-1">
               <akn:role GUID="1cd9ff70-4004-48dc-8c76-7fb295852717" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1" refersTo="attributsemantik-noch-undefiniert">Der Bundespräsident</akn:role>
               <akn:person GUID="3d296b9f-6b7d-4c34-a091-23291a39917a" eId="schluss-1_blockcontainer-1_signatur-1_person-1" refersTo="attributsemantik-noch-undefiniert">Lübke</akn:person>
            </akn:signature>
            <akn:signature GUID="2c6c0dc9-32b6-46ee-a1fa-9b8a0bc7c193" eId="schluss-1_blockcontainer-1_signatur-2">
               <akn:role GUID="bea9417f-96d8-4a0b-a923-4541ed04b921" eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1" refersTo="attributsemantik-noch-undefiniert">Der Stellvertreter des Bundeskanzlers</akn:role>
               <akn:person GUID="33105c66-afdb-4a8d-b397-59c357bf2713" eId="schluss-1_blockcontainer-1_signatur-2_person-1" refersTo="attributsemantik-noch-undefiniert">Mende</akn:person>
            </akn:signature>
            <akn:signature GUID="669a4ce1-2782-4623-8af7-83e7bcf0917a" eId="schluss-1_blockcontainer-1_signatur-3">
               <akn:role GUID="0732a41f-abe1-4774-b6c0-b4934082ca2c" eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1" refersTo="attributsemantik-noch-undefiniert">Der Bundesminister des Innern</akn:role>
               <akn:person GUID="a8095a73-f0f1-44ec-8c4e-92bc59ff44a7" eId="schluss-1_blockcontainer-1_signatur-3_person-1" refersTo="attributsemantik-noch-undefiniert">Hermann Höcherl</akn:person>
            </akn:signature>
            <akn:signature GUID="22a6ce3d-3ae9-4189-bfed-d544efe7e50b" eId="schluss-1_blockcontainer-1_signatur-4">
               <akn:role GUID="cacb2add-a766-4f2e-ac4a-7b437b226d0d" eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1" refersTo="attributsemantik-noch-undefiniert">Der Bundesminister der Justiz</akn:role>
               <akn:person GUID="44bb454b-33df-4ee6-a29a-048d7b7f1148" eId="schluss-1_blockcontainer-1_signatur-4_person-1" refersTo="attributsemantik-noch-undefiniert">Bucher</akn:person>
            </akn:signature>
         </akn:blockContainer>
      </akn:conclusions>
   </akn:act>
</akn:akomaNtoso>`
