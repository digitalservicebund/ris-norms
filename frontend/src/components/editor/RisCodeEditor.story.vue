<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { logEvent } from "histoire/client"

const SAMPLE_AMENDING_LAW = `<?xml version="1.0" encoding="UTF-8"?>
<!--
\t##################################################################################
\tProjekt E-Gesetzgebung
\tNicht-normative Exemplifikation für den Standard LegalDocML.de 1.5 (Mai 2023)
\t
\t2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
\tReferat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
\tVerwaltungsarbeit
\t
\tVeröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
\t##################################################################################
-->
<?xml-  el href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.5/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.5/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.5/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
<!-- Regelungstext des Stammgesetzes nach der Ausfertigung des Änderungsgesetzes. Da die Änderungsbefehle noch nicht in Kraft getreten sind, werden diese lediglich in den Metadaten
       hinterlegt. Sie stellen nun schwebende Änderungen dar, bis sie in Kraft treten und angewendet werden. -->
   <akn:act name="regelungstext">
<!-- Metadaten -->
      <akn:meta>
         <akn:identification source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork>
               <akn:FRBRthis value="eli/bund/bgbl-1/1964/s593/regelungstext-1"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>
               <akn:FRBRalias name="übergreifende-id" value="fbccff4a-b7b6-440f-a04b-5b2bef0f7aee"/>
               <akn:FRBRdate date="1964-08-21" name="verkuendungsfassung"/>
               <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry value="de"/>
               <akn:FRBRnumber value="s593"/>
               <akn:FRBRname value="bgbl-1"/>
               <akn:FRBRsubtype value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression>
               <akn:FRBRthis value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"/>
               <akn:FRBRalias name="vorherige-version-id"
                              value="052b9279-343e-4a6d-901f-74c4e9c6d2ae"/>
               <akn:FRBRalias name="aktuelle-version-id" value="b2414c09-5e56-4ceb-87ba-ee9f644f7a90"/>
               <akn:FRBRalias name="nachfolgende-version-id"
                              value="8de2c22c-d706-498a-9d96-930d7a03d224"/>
               <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate date="1964-08-05" name="verkuendung"/>
               <akn:FRBRlanguage language="deu"/>
               <akn:FRBRversionNumber value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation>
               <akn:FRBRthis value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml"/>
               <akn:FRBRuri value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml"/>
               <akn:FRBRdate date="1964-08-05" name="generierung"/>
               <akn:FRBRauthor href="recht.bund.de"/>
               <akn:FRBRformat value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle source="attributsemantik-noch-undefiniert">
            <akn:eventRef date="1964-08-05"
                          eId="ereignis-1"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="ausfertigung"/>
            <akn:eventRef date="1964-09-21"
                          eId="ereignis-2"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="inkrafttreten"/>
         </akn:lifecycle>
         <akn:temporalData source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup GUID="7b13adb9-ef62-43c4-bf1b-155561edf89b" eId="geltungszeitgr-1">
               <akn:timeInterval refersTo="geltungszeit" start="#ereignis-2"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron-Validierung. -->
         <akn:proprietary source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.5/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
               <meta:fna>754-28-1</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
               <meta:federfuehrung>
                  <meta:federfuehrend ab="2002-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                  <meta:federfuehrend ab="2002-10-01" bis="2002-12-01">Bundesministerium der Justiz</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>
      <!-- Dokumentenkopf Regelungstext -->
      <akn:preface>
         <akn:longTitle GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6" eId="doktitel-1">
            <akn:p GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0" eId="doktitel-1_text-1">
               <akn:docStage/>
               <akn:docProponent/>
               <akn:docTitle>Gesetz zur Regelungs des öffenltichen Vereinsrechts</akn:docTitle>
               <akn:shortTitle GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716"
                               eId="doktitel-1_text-1_kurztitel-1">(


                  <akn:inline refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert">Vereinsgesetz</akn:inline>)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
         <akn:block GUID="010d9df0-817a-49b6-a121-d0a1d412a3e3"
                    eId="block-1"
                    name="attributsemantik-noch-undefiniert">
            <akn:date GUID="b9407154-03fb-44f2-933e-5cf8c4015cda"
                      eId="block-1_datum-1"
                      refersTo="ausfertigung-datum"
                      date="1964-08-05">Vom 5. August 1964 </akn:date>
         </akn:block>
      </akn:preface>
      <!-- Eingangsformel + Inhaltsübersicht -->
      <akn:preamble>
<!-- Eingangsformel -->
         <akn:formula GUID="1b476669-1cbb-4633-a0ed-04783680b841"
                      eId="formel-1"
                      refersTo="eingangsformel"
                      name="attributsemantik-noch-undefiniert">
            <akn:p GUID="c1793533-b05a-4fc4-be62-bd6c3449c53a" eId="formel-1_text-1">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz
               beschlossen:</akn:p>
         </akn:formula>
      </akn:preamble>
      <!-- Hauptteil -->
      <akn:body GUID="6c5d6dc7-f14b-4e1d-8f8b-570a13aeff9c" eId="hauptteil">
<!-- § 9 -->
         <akn:article period="#geltungszeitgr-1"
                      GUID="b2390631-4ed0-45f6-af24-dd4deeaeae06"
                      eId="para-9">
            <akn:num GUID="5ef4111b-874d-4843-8467-bbccf6bceb96" eId="para-9_bezeichnung-1">
               <akn:marker name="9"/>§ 9</akn:num>
            <akn:heading GUID="c1717cbd-7785-4127-a9d5-b6423c49ab3d" eId="para-9_überschrift-1">Kennzeichenverbot</akn:heading>
            <!-- Absatz (1) -->
            <akn:paragraph period="#geltungszeitgr-1"
                           GUID="15f33532-babe-4c6f-817c-ccfd7e369191"
                           eId="para-9_abs-1">
               <akn:num GUID="5df1db91-e49a-4195-9552-3dddc4304912"
                        eId="para-9_abs-1_bezeichnung-1">
                  <akn:marker name="1"/>
               </akn:num>
               <akn:list GUID="582db518-f7ad-493f-b8b1-e21e4ebbb014"
                         eId="para-9_abs-1_untergl-1">
                  <akn:intro GUID="f8685276-25df-43a8-81f2-90fe48c30639"
                             eId="para-9_abs-1_untergl-1_intro-1">
                     <akn:p GUID="54473d0a-ad82-48c5-b537-7ea9cb49f91d"
                            eId="para-9_abs-1_untergl-1_intro-1_text-1">Kennzeichen des verbotenen Vereins dürfen
                        für die Dauer der Vollziehbarkeit des Verbots nicht mehr</akn:p>
                  </akn:intro>
                  <akn:point GUID="862be77d-ac5b-4dd7-8509-beb2d3620dda"
                             eId="para-9_abs-1_untergl-1_listenelem-1">
                     <akn:num GUID="a5e0f415-85f8-4e67-a944-5de26305f657"
                              eId="para-9_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                        <akn:marker name="1"/>
                     </akn:num>
                     <akn:content GUID="d9a79058-1058-477f-957d-0802540a3026"
                                  eId="para-9_abs-1_untergl-1_listenelem-1_inhalt-1">
                        <akn:p GUID="976fe7d2-fd6e-4428-b3e3-e9bf8ea6bac3"
                               eId="para-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">öffentlich, in einer
                           Versammlung oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="9bf3bfd3-9cc9-4a79-aaed-ca1306bbaa19"
                             eId="para-9_abs-1_untergl-1_listenelem-2">
                     <akn:num GUID="4633e320-e1b0-4e29-a642-f5cb7f65f6c6"
                              eId="para-9_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                        <akn:marker name="2"/>
                     </akn:num>
                     <akn:content GUID="c2146cd4-efb0-4dee-b3df-ee153329acc8"
                                  eId="para-9_abs-1_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="e37987b6-5b22-4c0c-b4af-079722c45811"
                               eId="para-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">in Schriften,
                           Schallaufnahmen, Abbildungen oder Darstellungen, die verbreitet werden oder zur Verbreitung
                           bestimmt sind,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp GUID="5d4ecf82-556b-4235-8103-f8ff4cfb13a6"
                              eId="para-9_abs-1_untergl-1_schlusstext-1">
                     <akn:p GUID="f5d07efa-ed20-4317-85ff-11501845009d"
                            eId="para-9_abs-1_untergl-1_schlusstext-1_text-1">verwendet werden. Ausgenommen ist eine
                        Verwendung von Kennzeichen im Rahmen der staatsbürgerlichen Aufklärung, der Abwehr
                        verfassungswidriger Bestrebungen und ähnlicher
                        Zwecke.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <!-- Absatz 2 -->
            <akn:paragraph period="#geltungszeitgr-1"
                           GUID="402455e1-4290-4ef6-9046-14c349d4ead3"
                           eId="para-9_abs-2">
               <akn:num GUID="705fa60b-a782-48a3-b42d-115e20f4a0fc"
                        eId="para-9_abs-2_bezeichnung-1">
                  <akn:marker name="2"/>(2) </akn:num>
               <akn:content GUID="b516d734-edc0-4497-bd56-950241dbbcc3" eId="para-9_abs-2_inhalt-1">
                  <akn:p GUID="5b5cb25e-3d9f-44dc-a49e-c800df5e34f4"
                         eId="para-9_abs-2_inhalt-1_text-1">Kennzeichen im Sinnde des Absatzes 1 sind insbesondere
                     Fahnen, Abzeichen, Uniformstücke, Parolen und Grußformen.</akn:p>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 3 -->
            <akn:paragraph period="#geltungszeitgr-1"
                           GUID="87634142-4c46-456e-8f22-631810e868ff"
                           eId="para-9_abs-3">
               <akn:num GUID="0e865617-4a90-4431-aeca-bdb70408008e"
                        eId="para-9_abs-3_bezeichnung-1">
                  <akn:marker name="3"/>(3) </akn:num>
                  <akn:content GUID="b516ebae-eaa5-4f06-954a-963b4f07fc24" eId="para-9_abs-3_inhalt-1">
                     <akn:p GUID="3662b21f-73d9-4a93-b57c-399fea9cd4e6"
                            eId="para-9_abs-3_inhalt-1_text-1">Diese Vorschriften gelten auch für die Verwendung von Kennzeichen einer Ersatzorganisation
                        für die Dauer der
                        Vollziehbarkeit einer Verfügung nach § 8 Abs. 2 Satz 1.</akn:p>
                  </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article period="#geltungszeitgr-1"
                      GUID="b1b4bd3b-e007-4d84-af83-b8e36a0ae50b"
                      eId="para-20">
            <akn:num GUID="f82ab983-5498-49ab-918f-5cf5e730e5ec" eId="para-20_bezeichnung-1">
               <akn:marker name="20"/>§ 20</akn:num>
            <akn:paragraph period="#geltungszeitgr-2"
                           GUID="6aa3a7ca-f30a-43b6-950b-b1e942fd1842"
                           eId="para-20_abs-1">
               <akn:num GUID="e363f12d-7918-435c-b3a1-182c5e03ff43"
                        eId="para-20_abs-1_bezeichnung-1">
                  <akn:marker name="1"/>(1) </akn:num>
               <akn:list GUID="97e930bf-49f8-472a-a1fa-3c3a401caa13"
                         eId="para-20_abs-1_untergl-1">
                  <akn:intro GUID="e2261ee5-feda-4691-b1a0-8a18f43720e7"
                             eId="para-20_abs-1_untergl-1_intro-1">
                     <akn:p GUID="f5b78960-3376-4f19-a1de-05e24443a141"
                            eId="para-20_abs-1_untergl-1_intro-1_text-1">Wer</akn:p>
                  </akn:intro>
                  <akn:point GUID="6f1b22e6-2fcc-4e29-b3c1-fd67d8cee45c"
                             eId="para-20_abs-1_untergl-1_listenelem-1">
                     <akn:num GUID="7947817d-e127-49ca-82c3-a1dfeaa73748"
                              eId="para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                        <akn:marker name="1"/>1. </akn:num>
                     <akn:content GUID="fff000f9-bed8-41eb-b26b-93a5da40ff5f"
                                  eId="para-20_abs-1_untergl-1_listenelem-1_inhalt-1">
                        <akn:p GUID="6ad07a0f-46f6-45b5-8d1a-ac79b8f704fe"
                               eId="para-20_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">entgegen einem
                           vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf
                           andere Weise aufrechterhält, sich an ihm als
                           Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18
                           Satz 2) oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point GUID="94af45e6-d49b-4bcc-84b9-5e5c01d5a3ba"
                             eId="para-20_abs-1_untergl-1_listenelem-2">
                     <akn:num GUID="18a496f3-c0e7-4fb3-a41d-5c9fd75374c7"
                              eId="para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                        <akn:marker name="2"/>2. </akn:num>
                     <akn:content GUID="7e835e4b-52eb-4fa1-9698-d7a42589d715"
                                  eId="para-20_abs-1_untergl-1_listenelem-2_inhalt-1">
                        <akn:p GUID="0ba9a471-e9ef-44c4-b5da-f69f068a4483"
                               eId="para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">entgegen § 9 Abs. 1 Satz
                           2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation
                           verwendet,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp GUID="107ed2fd-e041-4ee4-9eee-b559f84a4ce8"
                              eId="para-20_abs-1_untergl-1_schlusstext-1">
                     <akn:p GUID="53af6200-56c2-4fed-bd27-25ddefe7b204"
                            eId="para-20_abs-1_untergl-1_schlusstext-1_text-1">wird mit Gefängnis bis zu einem Jahr
                        oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a,
                        128 oder 129 des Strafgesetzbuches, mit schwerer
                        Strafe bedroht ist.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <akn:paragraph period="#geltungszeitgr-1"
                           GUID="f0f31024-8381-4a62-8980-5f432d03a925"
                           eId="para-20_abs-2">
               <akn:num GUID="c0ea5d8c-8d91-4e0c-812e-429a7a24a1a2"
                        eId="para-20_abs-2_bezeichnung-1">
                  <akn:marker name="2"/>(2) </akn:num>
               <akn:content GUID="8b51c358-ca54-4949-b957-bcbe5e55f33d"
                            eId="para-20_abs-2_inhalt-1">
                  <akn:p GUID="924d7140-bdfd-4a50-9b7f-feb006d19aae"
                         eId="para-20_abs-2_inhalt-1_text-1">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5
                     und 6 des Strafgesetzbuches entsprechend.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article period="#geltungszeitgr-1"
                      refersTo="geltungszeitregel"
                      GUID="9aefac39-6322-462f-9b93-7d1a046f2913"
                      eId="para-34">
            <akn:num GUID="6ef44f75-f570-49f2-8d4d-1deeb5eacdbc" eId="para-34_bezeichnung-1">
               <akn:marker name="34"/>§ 34 </akn:num>
            <akn:paragraph period="#geltungszeitgr-1"
                           GUID="33e50f7e-4639-4c56-8f7d-d53a423b305d"
                           eId="para-34_abs-1">
               <akn:num GUID="573d539f-44e1-47a9-8259-307f8536005e"
                        eId="para-34_abs-1_bezeichnung-1">
                  <akn:marker name="1"/>
               </akn:num>
               <akn:content GUID="1a983020-0a5c-4e20-8d65-7908affd6afc"
                            eId="para-34_abs-1_inhalt-1">
                  <akn:p GUID="7f60908e-6cd0-445b-903a-f56dec498743"
                         eId="para-34_abs-1_inhalt-1_text-1">Dieses Gesetz tritt


                     <akn:date GUID="c374dd52-4cd1-4396-9ec2-39613d5850e9"
                               date="1964-09-05"
                               eId="para-34_abs-1_inhalt-1_text-1_datum-1"
                               refersTo="inkrafttreten-datum">einen Monat nach seiner Verkündung</akn:date> in Kraft.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
      </akn:body>
      <akn:conclusions>
         <akn:formula GUID="cb33e6f0-b38b-4be9-a0a3-84ce4d7d0097"
                      eId="formel-1"
                      refersTo="schlussformel"
                      name="attributsemantik-noch-undefiniert">
            <akn:p GUID="332b89f7-7f46-43eb-b660-9b325ed52b57" eId="formel-1_text-1">Das vorstehende Gesetz wird hiermit verkündet.</akn:p>
         </akn:formula>
         <!-- Signaturen -->
         <akn:block GUID="35e11af5-ff7c-4422-af51-87cff3cc7ceb"
                    eId="block-1"
                    name="attributsemantik-noch-undefiniert">
            <akn:location GUID="19ffc7e1-add6-40a4-b9af-2f0927e46e9c"
                          eId="block-1_ort-1"
                          refersTo="">Bonn</akn:location>
            <akn:date GUID="a698ed17-c4f1-4c2f-b8a7-a9adef0d4a83"
                      eId="block-1_datum-1"
                      refersTo="ausfertigung-datum"
                      date="1964-08-05">, den 5. August 1964 </akn:date>
            <akn:signature GUID="b092cd1d-2f77-4fe6-a63d-5bcabeb410ad" eId="block-1_signatur-1">
               <akn:role GUID="1cd9ff70-4004-48dc-8c76-7fb295852717"
                         eId="block-1_signatur-1_fktbez-1"
                         refersTo="">Der Bundespräsident</akn:role>
               <akn:person refersTo="">Lübke</akn:person>
            </akn:signature>
            <akn:signature GUID="2c6c0dc9-32b6-46ee-a1fa-9b8a0bc7c193" eId="block-1_signatur-2">
               <akn:role GUID="bea9417f-96d8-4a0b-a923-4541ed04b921"
                         eId="block-1_signatur-2_fktbez-1">Der Stellvertreter des Bundeskanzlers</akn:role>
               <akn:person refersTo="">Mende</akn:person>
            </akn:signature>
            <akn:signature GUID="669a4ce1-2782-4623-8af7-83e7bcf0917a" eId="block-1_signatur-3">
               <akn:role GUID="0732a41f-abe1-4774-b6c0-b4934082ca2c"
                         eId="block-1_signatur-3_fktbez-1"
                         refersTo="">Der Bundesminister des Innern</akn:role>
               <akn:person refersTo="">Hermann Höcherl</akn:person>
            </akn:signature>
            <akn:signature GUID="22a6ce3d-3ae9-4189-bfed-d544efe7e50b" eId="block-1_signatur-4">
               <akn:role GUID="cacb2add-a766-4f2e-ac4a-7b437b226d0d"
                         eId="block-1_signatur-4_fktbez-1"
                         refersTo="">Der Bundesminister der Justiz</akn:role>
               <akn:person refersTo="">Bucher</akn:person>
            </akn:signature>
         </akn:block>
      </akn:conclusions>
   </akn:act>
</akn:akomaNtoso>
`
</script>

<template>
  <Story>
    <RisCodeEditor
      :initial-content="SAMPLE_AMENDING_LAW"
      @change="logEvent('change', $event)"
    />
  </Story>
</template>
