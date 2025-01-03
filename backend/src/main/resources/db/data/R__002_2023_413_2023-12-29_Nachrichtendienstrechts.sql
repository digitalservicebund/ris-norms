-- REAPPLY
-- TARGET LAW
DELETE
FROM norms
where eli_expression = 'eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1';

INSERT INTO norms (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../schema/legalDocML.de-metadaten.xsd                         http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
      <akn:meta GUID="731ba8ee-6f44-4a86-8682-eab3a376f16a" eId="meta-1">
         <akn:identification GUID="e0548b32-89df-433d-bf28-3337f7753882"
                             eId="meta-1_ident-1"
                             source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork GUID="65979527-4a22-4c42-8833-d4e1bf61268e"
                          eId="meta-1_ident-1_frbrwork-1">
               <akn:FRBRthis GUID="f7a087a0-854d-4043-ae55-22d33534491c"
                             eId="meta-1_ident-1_frbrwork-1_frbrthis-1"
                             value="eli/bund/bgbl-1/1990/s2954/regelungstext-1"/>
               <akn:FRBRuri GUID="ff394b68-3b79-4fe5-a5cb-c830dd193e0c"
                            eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            value="eli/bund/bgbl-1/1990/s2954"/>
               <akn:FRBRalias GUID="49c35c31-563b-40ae-aec1-6312de584200"
                              eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              name="übergreifende-id"
                              value="e9b14511-6253-4023-8d5f-6878c4f50cc0"/>
               <akn:FRBRdate GUID="a8a28ba1-d7ad-466e-86ac-dfc8d94ebcd9"
                             date="1990-12-20"
                             eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor GUID="659b24ec-67ed-4a22-9dbf-ed6292df0273"
                               eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry GUID="f0d73f13-4ae6-4709-bb8b-b1cd4e725015"
                                eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                value="de"/>
               <akn:FRBRnumber GUID="a741f1cc-50a0-4804-9d9b-e8f8c21340d8"
                               eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               value="s2954"/>
               <akn:FRBRname GUID="4e339d68-47f2-49cc-b0aa-2b399a588571"
                             eId="meta-1_ident-1_frbrwork-1_frbrname-1"
                             value="bgbl-1"/>
               <akn:FRBRsubtype GUID="9087ed75-faf3-420c-afbe-da0af7152a09"
                                eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression GUID="fbaf99a3-249e-4eff-87bc-a704d8be0d18"
                                eId="meta-1_ident-1_frbrexpression-1">
               <akn:FRBRthis GUID="5bab7a19-8165-40eb-a7e7-a4e9dfe6b3fc"
                             eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                             value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"/>
               <akn:FRBRuri GUID="af634c88-ad5f-45b3-99ac-6b2d42b45d1d"
                            eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu"/>
               <akn:FRBRalias GUID="af17d907-a88a-4081-a13a-fd4522cd5d1e"
                              eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              name="vorherige-version-id"
                              value="49eec691-392b-4d77-abaf-23eb871132ad"/>
               <akn:FRBRalias GUID="9c086b80-be09-49e6-9230-4932cfe88c83"
                              eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
                              name="aktuelle-version-id"
                              value="77167d15-511d-4927-adf3-3c8b0464423c"/>
               <akn:FRBRalias GUID="9b5c6034-62e6-488e-ac30-806147079ee1"
                              eId="meta-1_ident-1_frbrexpression-1_frbralias-3"
                              name="nachfolgende-version-id"
                              value="e880bf9f-3289-4193-a423-67be494daf80"/>
               <akn:FRBRauthor GUID="edfa3b7d-cd57-4c0e-92e2-fa188c470b2b"
                               eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate GUID="d9e2bf81-2be6-4b99-8101-b551cd273ca2"
                             date="1990-12-20"
                             eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             name="verkuendung"/>
               <akn:FRBRlanguage GUID="0ce6d9b4-7641-47b4-8163-0914d634567f"
                                 eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                 language="deu"/>
               <akn:FRBRversionNumber GUID="4fb34a00-8a92-44bd-bc5f-c0fb6cfe791e"
                                      eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"
                                      value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation GUID="3485a797-2673-47ae-884a-980b35bd1a7b"
                                   eId="meta-1_ident-1_frbrmanifestation-1">
               <akn:FRBRthis GUID="810aa0ae-2aca-470d-9913-5afae54b00e0"
                             eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                             value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/1990-12-20/regelungstext-1.xml"/>
               <akn:FRBRuri GUID="a0596502-d5fd-4d2e-b42f-29bc43b63c76"
                            eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/1990-12-20/regelungstext-1.xml"/>
               <akn:FRBRdate GUID="eb8ea1a5-4f95-4ef0-8056-8ad624f23fee"
                             date="1990-12-20"
                             eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             name="generierung"/>
               <akn:FRBRauthor GUID="84eb9f54-57e5-4633-97b8-f6ee5f2d231d"
                               eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                               href="recht.bund.de"/>
               <akn:FRBRformat GUID="6e453631-d290-4dbb-9310-27007fed31ac"
                               eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                               value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle GUID="8c8fccc7-8f59-45af-8550-b5725ffd0c82"
                        eId="meta-1_lebzykl-1"
                        source="attributsemantik-noch-undefiniert">
            <akn:eventRef GUID="9c2e7385-3a0f-44c0-aa2d-5db2bf265cf9"
                          date="1970-01-01"
                          eId="meta-1_lebzykl-1_ereignis-1"
                          refersTo="ausfertigung"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"/>
         </akn:lifecycle>
         <akn:temporalData GUID="9a82ff0d-bf6d-4631-a9f6-2bf2344ba315"
                           eId="meta-1_geltzeiten-1"
                           source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup GUID="6a27d3bd-ddc4-4f5d-9c78-ac82c18350f5"
                               eId="meta-1_geltzeiten-1_geltungszeitgr-1">
               <akn:timeInterval GUID="6f0603c6-6721-44b0-a391-5e056f821ec6"
                                 eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                 refersTo="geltungszeit"
                                 start="#meta-1_lebzykl-1_ereignis-1"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <akn:proprietary GUID="33fc7615-4c37-4101-9184-a14185ee3ec2"
                          eId="meta-1_proprietary-1"
                          source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>bundesregierung</meta:initiant>
               <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
               <meta:fna>210-5</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.1/">
               <meta:federfuehrung>
                  <meta:federfuehrend ab="2023-12-29" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>
      <akn:preface GUID="59a7267f-11bf-464e-a884-15c0bbd17416" eId="einleitung-1">
         <akn:longTitle GUID="d5ff5e89-7289-481a-9637-ac07858edd35"
                        eId="einleitung-1_doktitel-1">
            <akn:p GUID="3a51c54e-9417-47a8-ae0d-e3f2619e7947"
                   eId="einleitung-1_doktitel-1_text-1">
               <akn:docTitle GUID="2a98ce01-3ffb-4aed-b38f-9f2a11df6e9a"
                             eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz über die
                        Zusammenarbeit des
                        Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz</akn:docTitle>
               <akn:shortTitle GUID="ec41d543-ce9e-4326-89dd-08c7ee57a96d"
                               eId="einleitung-1_doktitel-1_text-1_kurztitel-1">
                        Bundesverfassungsschutzgesetz</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <akn:preamble GUID="584b2ec8-410e-4b70-9c5a-c7902c73d8f0" eId="preambel-1">

        </akn:preamble>
      <akn:body GUID="1bd61b70-9b79-42c7-b781-c050181f17d6" eId="hauptteil-1">
         <akn:section GUID="851e76da-dea2-4d9b-a85a-a90c31358e6e"
                      eId="hauptteil-1_abschnitt-1">
            <akn:num GUID="15376a15-dcf4-4c11-ad20-f09b134db7f9"
                     eId="hauptteil-1_abschnitt-1_bezeichnung-1">
                    Erster
                    Abschnitt </akn:num>
            <akn:heading GUID="a803190c-9626-421f-9fd4-a904f9e572dd"
                         eId="hauptteil-1_abschnitt-1_überschrift-1">
                    Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden
                </akn:heading>
            <akn:article GUID="aa1b7398-9bf7-4ac1-8c50-d7fe3c5d9172"
                         eId="hauptteil-1_abschnitt-1_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="7b338a03-8c9d-41bd-a09c-6692ec0a0db1"
                        eId="hauptteil-1_abschnitt-1_art-1_bezeichnung-1">
                         § 1 </akn:num>
               <akn:heading GUID="6c705fe3-aa48-40da-b40b-0b11038c58d7"
                            eId="hauptteil-1_abschnitt-1_art-1_überschrift-1">
                        Zusammenarbeitspflicht
                    </akn:heading>
               <akn:paragraph GUID="81366f50-18fd-4775-8925-91720db260ac"
                              eId="hauptteil-1_abschnitt-1_art-1_abs-1">
                  <akn:num GUID="b7a56c9a-e24a-472d-9548-2f202a14c00f"
                           eId="hauptteil-1_abschnitt-1_art-1_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="ebb15a7e-4cca-4715-8486-371a8de4346e"
                               eId="hauptteil-1_abschnitt-1_art-1_abs-1_inhalt-1">
                     <akn:p GUID="ec280a55-a38a-48df-944a-e56fed42d5fb"
                            eId="hauptteil-1_abschnitt-1_art-1_abs-1_inhalt-1_text-1">
                                Der Verfassungsschutz dient dem Schutz der freiheitlichen demokratischen Grundordnung, des Bestandes und der
                                Sicherheit des Bundes und
                                der Länder.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="6057cb44-4267-4cb5-b421-2f6a01006aa5"
                              eId="hauptteil-1_abschnitt-1_art-1_abs-2">
                  <akn:num GUID="761fe79c-7e76-4df9-9879-a7565efb00fa"
                           eId="hauptteil-1_abschnitt-1_art-1_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="51627e8f-d4b7-4812-866a-36c67d568f45"
                               eId="hauptteil-1_abschnitt-1_art-1_abs-2_inhalt-1">
                     <akn:p GUID="20f9666f-05c0-431c-b179-64d25bbd91f6"
                            eId="hauptteil-1_abschnitt-1_art-1_abs-2_inhalt-1_text-1">
                                Der Bund und die Länder sind verpflichtet, in Angelegenheiten des Verfassungsschutzes zusammenzuarbeiten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="c7b21b26-2e70-4d0a-ab57-1fdfa6e22500"
                              eId="hauptteil-1_abschnitt-1_art-1_abs-3">
                  <akn:num GUID="5211270b-1d05-4c60-b028-8da8dcbcc7b2"
                           eId="hauptteil-1_abschnitt-1_art-1_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="181430c8-03f5-4015-a06d-c115423220c2"
                               eId="hauptteil-1_abschnitt-1_art-1_abs-3_inhalt-1">
                     <akn:p GUID="96503362-cd13-4e81-9ae2-8228ecf47914"
                            eId="hauptteil-1_abschnitt-1_art-1_abs-3_inhalt-1_text-1">
                                Die Zusammenarbeit besteht auch in gegenseitiger Unterstützung und Hilfeleistung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="2b8fd763-85df-4002-89b2-7b8caefc1628"
                         eId="hauptteil-1_abschnitt-1_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="c9307274-b2c0-4e6e-96d1-4ec39eb64b13"
                        eId="hauptteil-1_abschnitt-1_art-2_bezeichnung-1">
                         § 2 </akn:num>
               <akn:heading GUID="e357194c-50f7-4bd9-aaf5-7e03bbc2b15d"
                            eId="hauptteil-1_abschnitt-1_art-2_überschrift-1">
                        Verfassungsschutzbehörden
                    </akn:heading>
               <akn:paragraph GUID="bbd652e6-2649-4208-8dc5-719816a2d3e4"
                              eId="hauptteil-1_abschnitt-1_art-2_abs-1">
                  <akn:num GUID="9cbce162-19f6-4bc3-ab08-b92df211c097"
                           eId="hauptteil-1_abschnitt-1_art-2_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="ff56d90a-4212-4e1b-af21-6f69802cf985"
                               eId="hauptteil-1_abschnitt-1_art-2_abs-1_inhalt-1">
                     <akn:p GUID="49f99c1e-8f78-4f2c-ab4c-8ecda1bb40f3"
                            eId="hauptteil-1_abschnitt-1_art-2_abs-1_inhalt-1_text-1">Für die Zusammenarbeit des Bundes mit den Ländern unterhält der Bund ein Bundesamt für Verfassungsschutz als Bundesoberbehörde. Es untersteht dem Bundesministerium des Innern, für Bau und Heimat. Das Bundesamt für Verfassungsschutz darf einer polizeilichen Dienststelle nicht angegliedert werden.</akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="f02b5576-bddb-48ee-b22e-f8d99a59a180"
                              eId="hauptteil-1_abschnitt-1_art-2_abs-2">
                  <akn:num GUID="1c2fb506-3238-4f40-8cb3-53534ad90c4e"
                           eId="hauptteil-1_abschnitt-1_art-2_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="ff6deb17-78b2-4626-bf79-599b8261ff39"
                               eId="hauptteil-1_abschnitt-1_art-2_abs-2_inhalt-1">
                     <akn:p GUID="cf8d11c9-be51-4da2-ac06-ebef46b30f17"
                            eId="hauptteil-1_abschnitt-1_art-2_abs-2_inhalt-1_text-1">
                                Für die Zusammenarbeit der Länder mit dem Bund und der Länder untereinander unterhält jedes Land eine Behörde zur
                                Bearbeitung von
                                Angelegenheiten des Verfassungsschutzes. Mehrere Länder können eine gemeinsame Behörde unterhalten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="6d0326ad-2600-4dda-98cd-192ed138e722"
                         eId="hauptteil-1_abschnitt-1_art-3"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="fd1d2764-a223-49e9-9c59-a9040b8bd46c"
                        eId="hauptteil-1_abschnitt-1_art-3_bezeichnung-1">
                         § 3 </akn:num>
               <akn:heading GUID="ca710f2a-a972-41ad-bd47-c027b17ec69c"
                            eId="hauptteil-1_abschnitt-1_art-3_überschrift-1">
                        Aufgaben der Verfassungsschutzbehörden
                    </akn:heading>
               <akn:paragraph GUID="89d20ea6-105a-47b5-a610-ecf11d137205"
                              eId="hauptteil-1_abschnitt-1_art-3_abs-1">
                  <akn:num GUID="98166b6b-e95b-4d15-8bfa-b0e94b3b3ddb"
                           eId="hauptteil-1_abschnitt-1_art-3_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="a9d18ee0-f44c-4890-8c36-8192639a8b16"
                            eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1">
                     <akn:intro GUID="7386b304-4b44-4f61-9b6e-f6438dcfc236"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_intro-1">
                        <akn:p GUID="28c1db65-fe37-4cfb-9af6-96c8ec03d086"
                               eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_intro-1_text-1">
                                    Aufgabe der Verfassungsschutzbehörden des Bundes und der Länder ist die Sammlung und Auswertung von Informationen,
                                    insbesondere von
                                    sach- und personen*-bezogenen Auskünften, Nachrichten und Unterlagen, über
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="e4fd103f-9c8e-43e2-a363-0ef52c6b9d34"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="40a445cb-a537-49b9-9f4d-9ed95180d91f"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="f40f4851-51a2-4a7a-8b23-1f8a20e02300"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="2b74c190-948a-4b5a-a927-4106408faa5e"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Bestrebungen, die gegen die freiheitliche demokratische Grundordnung, den Bestand oder die Sicherheit des
                                        Bundes oder eines Landes
                                        gerichtet sind oder eine ungesetzliche Beeinträchtigung der Amtsführung der Verfassungsorgane des Bundes oder
                                        eines Landes oder
                                        ihrer Mitglieder zum Ziele haben,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="ba40053c-0000-4a3d-83e9-066bf859fb67"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="d341f9bb-45d0-46e1-a78e-d2c5139f8b61"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="157a1c53-ee96-48a4-a95f-2f863a704894"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="183669ae-9553-4c75-a6db-753f83d4ac83"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        sicherheitsgefährdende oder geheimdienstliche Tätigkeiten im Geltungsbereich dieses Gesetzes für eine fremde
                                        Macht,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="e3d8b432-8aef-4593-b1f5-82a8a1a9901f"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="e2d0a945-78f9-47b2-926f-4c148670c26c"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="e07aa714-43ec-4302-873d-c07af12b5363"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="a80badab-6497-4f26-810d-20b6c3421876"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Bestrebungen im Geltungsbereich dieses Gesetzes, die durch Anwendung von Gewalt oder darauf gerichtete
                                        Vorbereitungshandlungen
                                        auswärtige Belange der Bundesrepublik Deutschland gefährden.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="29a8263c-cd3e-4f8c-9c24-f9af4f799114"
                              eId="hauptteil-1_abschnitt-1_art-3_abs-2">
                  <akn:num GUID="9f9cc0ec-a3bc-436e-a866-eaea26c26385"
                           eId="hauptteil-1_abschnitt-1_art-3_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="2487bb53-d48f-4f91-a666-13a030b8978a"
                            eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1">
                     <akn:intro GUID="0248c453-754b-41ca-a4d4-fb20f586571f"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_intro-1">
                        <akn:p GUID="a0bd5f43-0ca5-40cc-8649-595a15608d96"
                               eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_intro-1_text-1">
                                    Die Verfassungsschutzbehörden des Bundes und der Länder wirken mit
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="a3a39dce-09d2-44fe-a3db-8523504d92e2"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1">
                        <akn:num GUID="42479a55-21d9-487f-9517-6545296b7a55"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="cccbac2c-c899-4265-83f5-19b901fc09c5"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="8dbc4550-ca18-488d-927e-d80d7f0fbde8"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                        bei der Sicherheitsüberprüfung von Personen, denen im öffentlichen Interesse geheimhaltungsbedürftige
                                        Tatsachen, Gegenstände oder
                                        Erkenntnisse anvertraut werden, die Zugang dazu erhalten sollen oder ihn sich verschaffen können,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="eb7ccfa3-b112-4e17-96d2-6cb866f4067e"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2">
                        <akn:num GUID="8c91c9a2-5588-4f75-8ae3-3bf8bb619125"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="38490983-e201-4764-a71b-942f8ed9caee"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="53b3cfdf-6958-439d-8b0b-332c28013723"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                        bei der Sicherheitsüberprüfung von Personen, die an sicherheitsempfindlichen Stellen von lebens- oder
                                        verteidigungs*-wichtigen
                                        Einrichtungen beschäftigt sind oder werden sollen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="b6d113fb-e807-4aa5-8d59-1be8f26a15b2"
                                eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3">
                        <akn:num GUID="56ce9c8f-7a43-4827-9644-24264c439685"
                                 eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="11f25f89-7277-4cde-bc7b-8640dca8963f"
                                     eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="65af49f5-4140-466e-ad58-ef980c1b4985"
                                  eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">
                                        bei technischen Sicherheitsmaßnahmen zum Schutz von im öffentlichen Interesse geheimhaltungsbedürftigen
                                        Tatsachen, Gegenständen
                                        oder Erkenntnissen gegen die Kenntnisnahme durch Unbefugte.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="80a4dc1d-1602-4470-8095-4c143845681f"
                              eId="hauptteil-1_abschnitt-1_art-3_abs-3">
                  <akn:num GUID="f3b6082d-6f02-4a06-bb8b-65b298652a10"
                           eId="hauptteil-1_abschnitt-1_art-3_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="048d4dff-ba9c-43e6-93ac-e03cceca3c9c"
                               eId="hauptteil-1_abschnitt-1_art-3_abs-3_inhalt-1">
                     <akn:p GUID="890b2db7-1d11-48ff-ae74-75ddfa4b0fed"
                            eId="hauptteil-1_abschnitt-1_art-3_abs-3_inhalt-1_text-1">
                                Die Verfassungsschutzbehörden sind an die allgemeinen Rechtsvorschriften gebunden (Artikel 20 des Grundgesetzes).
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="98a7102f-b453-4f04-9c1a-9eb92fdccdd0"
                         eId="hauptteil-1_abschnitt-1_art-4"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="c1cc0856-9383-424b-9425-5bc20fd622da"
                        eId="hauptteil-1_abschnitt-1_art-4_bezeichnung-1">
                         § 4 </akn:num>
               <akn:heading GUID="25fbd75d-f2cb-4289-a718-8d9e64cf0f8a"
                            eId="hauptteil-1_abschnitt-1_art-4_überschrift-1">
                        Begriffsbestimmungen
                    </akn:heading>
               <akn:paragraph GUID="0fa9a20c-ba40-45c6-ba40-c6a58656d745"
                              eId="hauptteil-1_abschnitt-1_art-4_abs-1">
                  <akn:num GUID="681ea1e3-2ca9-47c3-a1c1-c9a94da3e399"
                           eId="hauptteil-1_abschnitt-1_art-4_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="21ed3ca5-4805-4865-963f-36346ccb6e51"
                            eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1">
                     <akn:intro GUID="94fee73b-4d85-4945-ae49-af836155de21"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_intro-1">
                        <akn:p GUID="a604698a-440c-4e4a-a733-7c4ff48ec3d7"
                               eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_intro-1_text-1">
                                    Im Sinne dieses Gesetzes sind
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="b786e7aa-a176-4131-a041-a487a72f969a"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="0c161a3b-65f4-4535-b006-0d2261c3d2c2"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     a) </akn:num>
                        <akn:content GUID="53556843-5e20-4055-abfe-dd47fc80cae3"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="5b9f776e-08e7-45dd-a033-bf801dd10c78"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Bestrebungen gegen den Bestand des Bundes oder eines Landes solche politisch bestimmten, ziel- und
                                        zweck*-gerichteten
                                        Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, die Freiheit des
                                        Bundes oder eines
                                        Landes von fremder Herrschaft aufzuheben, ihre staatliche Einheit zu beseitigen oder ein zu ihm gehörendes
                                        Gebiet abzutrennen;
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3f9e2c55-193d-4d09-b725-16075469347f"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="3d3ab192-ee25-4f60-90a8-acfc9e4b04ea"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     b) </akn:num>
                        <akn:content GUID="cab32063-5e89-43b9-908b-b92e4d66a5f5"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="c5abe4d1-a3df-4391-85d3-d0fd3964b6ba"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Bestrebungen gegen die Sicherheit des Bundes oder eines Landes solche politisch bestimmten, ziel- und
                                        zweck*-gerichteten
                                        Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, den Bund, Länder
                                        oder deren
                                        Einrichtungen in ihrer Funktionsfähigkeit erheblich zu beeinträchtigen;
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="afbdaa6b-93de-4428-96bd-0538dd11b6f0"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="c38710cf-a021-49b1-989a-58978e059ab2"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     c) </akn:num>
                        <akn:content GUID="fcd19c8d-ebf4-4c96-b523-90bdb3e77c89"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="3987aa30-fc2a-47ff-8a26-fe60d6de7a00"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Bestrebungen gegen die freiheitliche demokratische Grundordnung solche politisch bestimmten, ziel- und
                                        zweck*-gerichteten
                                        Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, einen der in Absatz
                                        2 genannten
                                        Verfassungsgrundsätze zu beseitigen oder außer Geltung zu setzen.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="1cd1b3ca-9d46-43e6-a5f3-66707155d3c5"
                              eId="hauptteil-1_abschnitt-1_art-4_abs-2">
                  <akn:num GUID="b1c3f7ea-3e44-4b8c-8f8a-37a595e6023d"
                           eId="hauptteil-1_abschnitt-1_art-4_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="ae036925-84e3-4e61-99e1-0ce1a767030b"
                            eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1">
                     <akn:intro GUID="17cfd3a7-25e9-4946-a03b-2b15a391bd0b"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_intro-1">
                        <akn:p GUID="190c48f6-3441-4217-8b10-342b8a5bd842"
                               eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_intro-1_text-1">
                                    Zur freiheitlichen demokratischen Grundordnung im Sinne dieses Gesetzes zählen:
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="dbf81662-d839-4550-9373-99bc6210b0a1"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1">
                        <akn:num GUID="4544555a-ca74-4788-8eaa-2fa543e8d891"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_bezeichnung-1">
                                     a) </akn:num>
                        <akn:content GUID="326152ba-d0bd-49ce-963f-397abc33d171"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="c7d39a03-69bc-4498-829b-d7d0e205ff59"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                        das Recht des Volkes, die Staatsgewalt in Wahlen und Abstimmungen und durch besondere Organe der Gesetzgebung,
                                        der vollziehenden
                                        Gewalt und der Rechtsprechung auszuüben und die Volksvertretung in allgemeiner, unmittelbarer, freier,
                                        gleicher und geheimer Wahl
                                        zu wählen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="9237f120-6dfb-42b4-b103-5cb354c44437"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2">
                        <akn:num GUID="5e5664fe-4c2a-4f12-969b-95e352095fc6"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_bezeichnung-1">
                                     b) </akn:num>
                        <akn:content GUID="1045b56a-f121-4bb1-9c17-cf06ac9ad12a"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="dd6da436-d716-459e-a84b-4d20868def53"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                        die Bindung der Gesetzgebung an die verfassungsmäßige Ordnung und die Bindung der vollziehenden Gewalt und der
                                        Rechtsprechung an
                                        Gesetz und Recht,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3f399c1d-024f-4aa1-9dd1-b39e1aa2b449"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3">
                        <akn:num GUID="daf2be95-903a-4132-92a8-1ec477841e49"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_bezeichnung-1">
                                     c) </akn:num>
                        <akn:content GUID="7ca6f252-efcf-4ddd-bba2-68cd922e2892"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="2922df53-bdc6-42e4-af73-48cc67126ca7"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">
                                        das Recht auf Bildung und Ausübung einer parlamentarischen Opposition,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="e26be229-b04d-4d8d-b498-eb4614c8092c"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4">
                        <akn:num GUID="9e1423a9-1ca9-4e99-86ad-29a2c693d54c"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_bezeichnung-1">
                                     d) </akn:num>
                        <akn:content GUID="a3798e29-13f6-49b3-a3e9-5216ec6a2cf3"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="615145d7-fd44-40dd-997d-c6bd8cc7152e"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">
                                        die Ablösbarkeit der Regierung und ihre Verantwortlichkeit gegenüber der Volksvertretung,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="61d72e04-52e2-4d23-8d25-f84b07fc3420"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5">
                        <akn:num GUID="5fbb6f97-d6ad-49e5-9974-64c67681ae01"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_bezeichnung-1">
                                     e) </akn:num>
                        <akn:content GUID="46b57266-1ab4-4185-83df-b13dca0997d7"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="6d0067a0-6681-43a6-918f-eb588b2c19a3"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_inhalt-1_text-1">
                                        die Unabhängigkeit der Gerichte,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="04322b6f-21fa-4ce4-9dcb-be64aac24516"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6">
                        <akn:num GUID="c44d589c-8bff-472e-a9ea-3992d85d2d10"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_bezeichnung-1">
                                     f) </akn:num>
                        <akn:content GUID="049236ca-8c88-4054-8fac-b22b146589a6"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_inhalt-1">
                           <akn:p GUID="20459819-af8b-4200-9efb-12d70e2fb4f7"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_inhalt-1_text-1">
                                        der Ausschluß jeder Gewalt- und Willkür*-herrschaft und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a3ed4022-bef5-4931-9e0c-a91161dfdd11"
                                eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7">
                        <akn:num GUID="de4a910a-1ac8-4126-b9a7-0eef7fa2e570"
                                 eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_bezeichnung-1">
                                     g) </akn:num>
                        <akn:content GUID="6ccba3ed-4c7a-4a9d-a47d-b011f9a73dc5"
                                     eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_inhalt-1">
                           <akn:p GUID="7645d3d2-6d5e-450f-b91a-23444cb85d00"
                                  eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_inhalt-1_text-1">
                                        die im Grundgesetz konkretisierten Menschenrechte.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="ce96f30d-48cf-4d25-9331-b107d123bc1b"
                         eId="hauptteil-1_abschnitt-1_art-5"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="ea53d2cd-c947-4ac3-81ba-7e2f070c06a2"
                        eId="hauptteil-1_abschnitt-1_art-5_bezeichnung-1">
                         § 5 </akn:num>
               <akn:heading GUID="ebeecb34-226f-4734-9f73-88b3249b60f4"
                            eId="hauptteil-1_abschnitt-1_art-5_überschrift-1">
                        Zuständigkeiten des Bundesamtes für Verfassungsschutz
                    </akn:heading>
               <akn:paragraph GUID="9c531c76-142b-4bbf-9d79-e3c10c137a87"
                              eId="hauptteil-1_abschnitt-1_art-5_abs-1">
                  <akn:num GUID="1abf5b4c-7cea-4283-ab5d-89db31546c15"
                           eId="hauptteil-1_abschnitt-1_art-5_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="74dbdc64-842d-45f9-bc90-0e4d54b1e544"
                            eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1">
                     <akn:intro GUID="622fd9bb-6574-42bf-a8ec-b800e3b8dce4"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_intro-1">
                        <akn:p GUID="32d6c00e-eedb-49d6-a27b-96dd61fa2e7a"
                               eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf in einem Lande im Benehmen mit der Landesbehörde für Verfassungsschutz
                                    Informationen,
                                    Auskünfte, Nachrichten und Unterlagen im Sinne des § 3 sammeln. Bei Bestrebungen und Tätigkeiten im Sinne des § 3
                                    Abs. 1 Nr. 1 bis 4
                                    ist Voraussetzung, daß
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="b5ffb96e-b8ba-4f9d-b11a-98260b6bb1d6"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="2de7a6b9-00d4-40ff-9e6d-57205ea29526"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="16791357-011c-4478-bc75-20696f973dbe"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="e6336294-a0e6-4616-b7af-27fcb9e08f37"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        sie sich ganz oder teilweise gegen den Bund richten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3d453bcc-bf2d-430d-85c3-9a77080744cb"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="01b28924-4836-44ad-a7d5-88cac4713c06"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="dbde63ae-f8e1-4ef4-8f76-cd2359d0ceaf"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="46fa8727-814b-44fc-840c-93ae950da1ae"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        sie darauf gerichtet sind, Gewalt anzuwenden, Gewaltanwendung vorzubereiten, zu unterstützen oder zu
                                        befürworten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="1a10b328-a54b-462a-b078-ca432a6f784b"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="3d46ba0b-0f72-4791-ba60-e483b272f3fb"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="17b766c0-0a79-47ce-aedc-f318cb88a82d"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="1f3a4648-92a9-4659-811b-838d3e42be42"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        sie sich über den Bereich eines Landes hinaus erstrecken,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="d4071f27-03de-4312-a654-3bb20d58ab19"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4">
                        <akn:num GUID="ed0d3566-d6b0-4282-89e8-21ac783200d2"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="207a59bc-00f3-424f-ba22-38f5eca25466"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="4ca834fa-2147-486a-9937-09ded1910a88"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                        sie auswärtige Belange der Bundesrepublik Deutschland berühren oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="583ae658-2cc3-4b09-b16e-896d8ca5920f"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5">
                        <akn:num GUID="f502fce4-fb73-4332-9a2e-686838b0c3e7"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:content GUID="be7c23b2-185c-47c9-8d4b-ef83339d37e6"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="a60b19a3-b035-4bc1-8b2a-867cba2a3517"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">
                                        eine Landesbehörde für Verfassungsschutz das Bundesamt für Verfassungsschutz um ein Tätigwerden ersucht.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="5db5502f-9978-45a1-a488-448f296d18a6"
                              eId="hauptteil-1_abschnitt-1_art-5_abs-2">
                  <akn:num GUID="a8597ddf-5f48-4552-96f8-ac913e9f2cbc"
                           eId="hauptteil-1_abschnitt-1_art-5_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="3b2a505a-18c5-4107-8b20-01d66f5fa454"
                               eId="hauptteil-1_abschnitt-1_art-5_abs-2_inhalt-1">
                     <akn:p GUID="542bf994-32fc-45d1-9f4e-7bc5aeabed8f"
                            eId="hauptteil-1_abschnitt-1_art-5_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz wertet unbeschadet der Auswertungsverpflichtungen der Landesbehörden für
                                Verfassungsschutz zentral
                                alle Erkenntnisse über Bestrebungen und Tätigkeiten im Sinne des § 3 Absatz 1 aus. Es unterrichtet die Landesbehörden
                                für
                                Verfassungsschutz nach § 6 Absatz 1, insbesondere durch Querschnittsauswertungen in Form von Struktur- und
                                Methodik*-berichten sowie
                                regelmäßig durch bundesweite Lageberichte zu den wesentlichen Phänomenbereichen unter Berücksichtigung der
                                entsprechenden
                                Landeslageberichte.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="1617e6c5-5d6f-4b79-b566-62d6d1cb2f4c"
                              eId="hauptteil-1_abschnitt-1_art-5_abs-3">
                  <akn:num GUID="1bf7d9f4-d1cf-4478-ab0f-3446b1b14964"
                           eId="hauptteil-1_abschnitt-1_art-5_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="4a7c1167-be39-486e-a3a2-f5839c8e126d"
                            eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1">
                     <akn:intro GUID="6c767dcd-4b07-43e6-b735-273489bc81f4"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_intro-1">
                        <akn:p GUID="117dffea-986a-42ca-9cd2-4373e661b923"
                               eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz koordiniert die Zusammenarbeit der Verfassungsschutzbehörden. Die
                                    Koordinierung schließt
                                    insbesondere die Vereinbarung von
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="93014157-f09b-4f3d-8f4f-0d50a5c622d4"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="d62553c4-0520-4f63-a6c4-c5a32e4b17d7"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="1a5f7258-ea37-4db7-be1d-5bd490850a14"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="c63a9f7e-6786-4552-b384-c54e64d668c3"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        einheitlichen Vorschriften zur Gewährleistung der Zusammenarbeitsfähigkeit,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3db94e30-96ba-4141-a08b-cffbaf8aad6b"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="843ad110-e1e5-49a7-9176-131021eae650"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="b9559d44-f5a2-4527-89d4-3768565f1b96"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="bb959020-68d0-4e2e-a0fe-708c35879d4a"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        allgemeinen Arbeitsschwerpunkten und arbeitsteiliger Durchführung der Aufgaben sowie
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3069977c-2fdc-4c56-abe2-827d7d6200ec"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3">
                        <akn:num GUID="081bb76c-2e57-4075-b910-10c04ec80135"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="86232261-b67b-4b63-87b7-767ef677b65b"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="f18f42f7-727d-409a-a716-b045f973a645"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Relevanzkriterien für Übermittlungen nach § 6 Absatz 1
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="c84b4468-58ba-44ee-9ea0-88aa84ac71f3"
                              eId="hauptteil-1_abschnitt-1_art-5_abs-4">
                  <akn:num GUID="ad9eefa3-0b11-4872-ba4d-a7f87f7bfa43"
                           eId="hauptteil-1_abschnitt-1_art-5_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:list GUID="4e2b6154-8dd9-4505-826f-1f36e267b63d"
                            eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1">
                     <akn:intro GUID="ad88e018-4393-4230-8e01-1232b5868737"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_intro-1">
                        <akn:p GUID="c64748ed-ceda-41ab-9795-fa49330e9950"
                               eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz unterstützt als Zentralstelle die Landesbehörden für Verfassungsschutz bei der
                                    Erfüllung ihrer
                                    Aufgaben nach § 3 insbesondere durch
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="d23d4a37-7583-409c-9ba6-2bf8a19c9f32"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1">
                        <akn:num GUID="51930d8d-b828-434b-b272-a9e2e33715a0"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="f63272b1-8ee4-4589-861e-3ab207436486"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="c691d0f9-0159-4a95-87d5-9b7ae4d8ba14"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Bereitstellung des nachrichtendienstlichen Informationssystems (§ 6 Absatz 2),
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="ce532aa4-fe2f-45d7-b674-b6b871041de9"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2">
                        <akn:num GUID="880e2fb1-cefe-428f-8c14-d2b0633d1fdc"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="c01ec301-5da0-4be7-b0a0-4c3858c88089"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="beee6cbc-3e62-4d5e-aa7f-bbebdd391097"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1_text-1">
                                        zentrale Einrichtungen im Bereich besonderer technischer und fachlicher Fähigkeiten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="cb740c45-9e83-4338-8954-371db65e5280"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3">
                        <akn:num GUID="21184d63-afd6-4e18-b189-5bedcbe616f0"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="a69f87cb-e288-4000-856e-24bc044004ac"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="132886ac-2dec-4977-83da-3b92664ee142"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Erforschung und Entwicklung von Methoden und Arbeitsweisen im Verfassungsschutz und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="bce950a3-aa73-44f4-b9d9-cb9ccbca7e83"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4">
                        <akn:num GUID="e87bfcf3-2299-47a7-9896-7e9d56c4cb17"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="0d63a140-6f24-4530-9abd-4945721f5f83"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="4a5550b6-742c-4d30-85b2-ee544b7a5469"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_inhalt-1_text-1">
                                        Fortbildung in speziellen Arbeitsbereichen.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="37118730-68f3-43b4-b51e-c5b7b98ecefa"
                              eId="hauptteil-1_abschnitt-1_art-5_abs-5">
                  <akn:num GUID="f553e665-a51f-4100-95d1-f5230d7da37e"
                           eId="hauptteil-1_abschnitt-1_art-5_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:list GUID="72eefe07-1b45-4163-a378-b971d82dd247"
                            eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1">
                     <akn:intro GUID="be732cba-5dc6-4a8d-b4db-839f3fbd180e"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_intro-1">
                        <akn:p GUID="f038e1e7-831b-4bc6-9ef6-28ce8244c04b"
                               eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_intro-1_text-1">
                                    Dem Bundesamt für Verfassungsschutz obliegt der für Aufgaben nach § 3 erforderliche Dienstverkehr mit zuständigen
                                    öffentlichen
                                    Stellen anderer Staaten. Die Landesbehörden für Verfassungsschutz können solchen Dienstverkehr führen
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="721a9fe9-6cf6-477e-bbd4-d5ba717f4ba1"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1">
                        <akn:num GUID="0c046f5f-9440-4e1a-a734-011ed525a7d0"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="e7d57c75-aa0d-4838-951c-05796149dc63"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="04195ffa-6405-4441-8cc9-d61ecbdb9b07"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_inhalt-1_text-1">
                                        mit den Dienststellen der in der Bundesrepublik Deutschland stationierten Streitkräfte,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="28009724-7a0d-4715-a18d-791cb116b6ec"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2">
                        <akn:num GUID="ac03e07e-a169-4c8c-8b4f-98c15cffea1d"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="f0541212-58e1-475e-ae46-0dce2c398529"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="cc936fe3-4dc6-4c0e-b1a9-7666237aa329"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_inhalt-1_text-1">
                                        mit den Nachrichtendiensten angrenzender Nachbarstaaten in regionalen Angelegenheiten oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="c1f98c57-bd8b-45f8-92d5-fd17d185110e"
                                eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3">
                        <akn:num GUID="1f9f3320-e42e-4f7d-95cd-c86d51e205cb"
                                 eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="93c008bd-4cfb-4f55-9b45-f65cb9eb6dbf"
                                     eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="1a0a945c-e92d-4989-a38e-5ba6597259b7"
                                  eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_inhalt-1_text-1">
                                        im Einvernehmen mit dem Bundesamt für Verfassungsschutz.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="d3df6a47-3749-4013-a7fe-3e25f711265d"
                         eId="hauptteil-1_abschnitt-1_art-6"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="ab2c6746-ce3f-48cd-8bc1-df112856b96e"
                        eId="hauptteil-1_abschnitt-1_art-6_bezeichnung-1">
                         § 6 </akn:num>
               <akn:heading GUID="621e15f3-d823-49f6-9f4b-8f46f0dc2aa1"
                            eId="hauptteil-1_abschnitt-1_art-6_überschrift-1">
                        Gegenseitige Unterrichtung der Verfassungsschutzbehörden
                    </akn:heading>
               <akn:paragraph GUID="18c0263c-9969-416b-b853-dc81e235466a"
                              eId="hauptteil-1_abschnitt-1_art-6_abs-1">
                  <akn:num GUID="ad6330d7-7038-4663-b484-027a3af78bc9"
                           eId="hauptteil-1_abschnitt-1_art-6_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="aff07b87-42a0-457a-a2f2-2d2e2c03ea82"
                               eId="hauptteil-1_abschnitt-1_art-6_abs-1_inhalt-1">
                     <akn:p GUID="2259b926-cf83-4c52-96ce-34873f39cb8c"
                            eId="hauptteil-1_abschnitt-1_art-6_abs-1_inhalt-1_text-1">
                                Die Landesbehörden für Verfassungsschutz und das Bundesamt für Verfassungsschutz übermitteln sich unverzüglich die für
                                ihre Aufgaben
                                relevanten Informationen, einschließlich der Erkenntnisse ihrer Auswertungen. Wenn eine übermittelnde Behörde sich
                                dies vorbehält,
                                dürfen die übermittelten Daten nur mit ihrer Zustimmung an Stellen außerhalb der Behörden für Verfassungsschutz
                                übermittelt werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="0608c620-5ae0-4a59-bb32-597d0854a355"
                              eId="hauptteil-1_abschnitt-1_art-6_abs-2">
                  <akn:num GUID="d8004821-9cf2-4a02-982d-b71a8cbe7855"
                           eId="hauptteil-1_abschnitt-1_art-6_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="3fbb5431-d57b-48dd-b216-d6a76d1acf7c"
                               eId="hauptteil-1_abschnitt-1_art-6_abs-2_inhalt-1">
                     <akn:p GUID="6e1fc50b-33c8-42ca-8bd5-c9e8d63e6480"
                            eId="hauptteil-1_abschnitt-1_art-6_abs-2_inhalt-1_text-1">
                                Die Verfassungsschutzbehörden verarbeiten zur Erfüllung ihrer Unterrichtungspflichten nach Absatz 1 Informationen im
                                gemeinsamen
                                nachrichtendienstlichen Informationssystem. Der Militärische Abschirmdienst kann zur Erfüllung der
                                Unterrichtungspflichten nach § 3
                                Absatz 3 Satz 1 des MAD-Gesetzes am nachrichtendienstlichen Informationssystem teilnehmen. Der Abruf von Daten aus dem
                                nachrichtendienstlichen Informationssystem im automatisierten Verfahren ist im Übrigen nur entsprechend den §§ 22a und
                                22b zulässig.
                                Für die Verarbeitung personenbezogener Daten im nachrichtendienstlichen Informationssystem gelten die §§ 10 und 11.
                                Die Verantwortung
                                einer speichernden Stelle im Sinne der allgemeinen Vorschriften des Datenschutzrechts trägt jede
                                Verfassungsschutzbehörde nur für die
                                von ihr eingegebenen Daten; nur sie darf diese Daten verändern, die Verarbeitung einschränken oder löschen. Die
                                eingebende Stelle muss
                                feststellbar sein. Eine Abfrage von Daten ist nur zulässig, soweit dies zur Erfüllung von Aufgaben, mit denen der
                                Abfragende
                                unmittelbar betraut ist, erforderlich ist. Die Zugriffsberechtigung auf Daten, die nicht zum Auffinden von Akten und
                                der dazu
                                notwendigen Identifizierung von Personen erforderlich sind, ist auf Personen zu beschränken, die mit der Erfassung von
                                Daten oder
                                Analysen betraut sind. Die Zugriffsberechtigung auf Unterlagen, die gespeicherte Angaben belegen, ist zudem auf
                                Personen zu
                                beschränken, die unmittelbar mit Arbeiten in diesem Anwendungsgebiet betraut sind.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="b8addf84-2541-43d1-b7d1-8fa31ba77618"
                              eId="hauptteil-1_abschnitt-1_art-6_abs-3">
                  <akn:num GUID="850a006f-9d24-48a5-8df1-20f3db53d947"
                           eId="hauptteil-1_abschnitt-1_art-6_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="944063df-58ac-4d96-a701-76ddb0f2092a"
                               eId="hauptteil-1_abschnitt-1_art-6_abs-3_inhalt-1">
                     <akn:p GUID="f0f1dad3-1995-40f4-a92b-4d807f22a6bd"
                            eId="hauptteil-1_abschnitt-1_art-6_abs-3_inhalt-1_text-1">Das Bundesamt für Verfassungsschutz trifft für die gemeinsamen Dateien die technischen und organisatorischen Maßnahmen entsprechend § 64 des Bundesdatenschutzgesetzes. Es hat bei jedem Zugriff für Zwecke der Datenschutzkontrolle den Zeitpunkt, die Angaben, die die Feststellung der abgefragten Datensätze ermöglichen, sowie die abfragende Stelle zu protokollieren. Die Auswertung der Protokolldaten ist nach dem Stand der Technik zu gewährleisten. Die protokollierten Daten dürfen nur für Zwecke der Datenschutzkontrolle, der Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebs der Datenverarbeitungsanlage verwendet werden. Die Protokolldaten sind am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt, zu löschen.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="794dca08-2521-494b-a12f-a633f23f0a44"
                         eId="hauptteil-1_abschnitt-1_art-7"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="4844fc0c-0f6b-4aba-ae16-4821c5dfe85d"
                        eId="hauptteil-1_abschnitt-1_art-7_bezeichnung-1">
                         § 7 </akn:num>
               <akn:heading GUID="eb394492-ff7c-49f8-86c0-b856b8d56d14"
                            eId="hauptteil-1_abschnitt-1_art-7_überschrift-1">
                        Weisungsrechte des Bundes
                    </akn:heading>
               <akn:paragraph GUID="8336bb65-3362-45e6-a1ac-802b560192f8"
                              eId="hauptteil-1_abschnitt-1_art-7_abs-1">
                  <akn:num GUID="4fe5c311-cc0e-4388-99be-6c92954f4fa5"
                           eId="hauptteil-1_abschnitt-1_art-7_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="6036c24f-0239-46a3-a690-655248e700c6"
                               eId="hauptteil-1_abschnitt-1_art-7_abs-1_inhalt-1">
                     <akn:p GUID="924f380e-6531-47d4-8c17-2a4816547632"
                            eId="hauptteil-1_abschnitt-1_art-7_abs-1_inhalt-1_text-1">
                                Die Bundesregierung kann, wenn ein Angriff auf die verfassungsmäßige Ordnung des Bundes erfolgt, den obersten
                                Landesbehörden die für
                                die Zusammenarbeit der Länder mit dem Bund auf dem Gebiete des Verfassungsschutzes erforderlichen Weisungen erteilen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:section>
         <akn:section GUID="9538a079-426e-4984-8a29-9b6531ee6523"
                      eId="hauptteil-1_abschnitt-2">
            <akn:num GUID="9bc90def-ef52-4aeb-bc3b-ea1b239eac26"
                     eId="hauptteil-1_abschnitt-2_bezeichnung-1">
                    Zweiter Abschnitt </akn:num>
            <akn:heading GUID="4668db97-ccbf-4dd0-a5c7-574a09cfcd46"
                         eId="hauptteil-1_abschnitt-2_überschrift-1">
                    Bundesamt für Verfassungsschutz
                </akn:heading>
            <akn:article GUID="7e641254-5c02-4699-9752-5041ec2643bb"
                         eId="hauptteil-1_abschnitt-2_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="02e5aace-7051-4212-895e-881756f47e2a"
                        eId="hauptteil-1_abschnitt-2_art-1_bezeichnung-1">
                         § 8 </akn:num>
               <akn:heading GUID="fee23e2f-6a41-4d41-9b2f-aa8274cb31d5"
                            eId="hauptteil-1_abschnitt-2_art-1_überschrift-1">
                        Befugnisse des Bundesamtes für Verfassungsschutz
                    </akn:heading>
               <akn:paragraph GUID="f5d1f23c-07ec-4b55-b1cd-1deadeea095a"
                              eId="hauptteil-1_abschnitt-2_art-1_abs-1">
                  <akn:num GUID="6d2bbf9b-9d1d-47df-a6c7-dd3962b3d22f"
                           eId="hauptteil-1_abschnitt-2_art-1_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="7645601e-0650-4c7d-ac54-78c3b123a31c"
                               eId="hauptteil-1_abschnitt-2_art-1_abs-1_inhalt-1">
                     <akn:p GUID="9752963a-43af-47ea-9044-1d71e8270d83"
                            eId="hauptteil-1_abschnitt-2_art-1_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf die zur Erfüllung seiner Aufgaben erforderlichen Informationen einschließlich
                                personenbezogener Daten verarbeiten, soweit nicht die anzuwendenden Bestimmungen des Bundesdatenschutzgesetzes oder
                                besondere
                                Regelungen in diesem Gesetz entgegenstehen; die Verarbeitung ist auch zulässig, wenn der Betroffene eingewilligt hat.
                                Ein Ersuchen des
                                Bundesamtes für Verfassungsschutz um Übermittlung personenbezogener Daten darf nur diejenigen personenbezogenen Daten
                                enthalten, die
                                für die Erteilung der Auskunft unerlässlich sind. Schutzwürdige Interessen des Betroffenen dürfen nur in
                                unvermeidbarem Umfang
                                beeinträchtigt werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="24215ee0-a217-4bb9-8598-2c27a86b82b0"
                              eId="hauptteil-1_abschnitt-2_art-1_abs-2">
                  <akn:num GUID="d51ae6f8-6d61-4641-b697-130e76e607fd"
                           eId="hauptteil-1_abschnitt-2_art-1_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="cf8ebf32-50c7-4879-9f69-a9b04de5e75a"
                               eId="hauptteil-1_abschnitt-2_art-1_abs-2_inhalt-1">
                     <akn:p GUID="027e892d-c578-4df7-a72a-928a69b2d152"
                            eId="hauptteil-1_abschnitt-2_art-1_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf Methoden, Gegenstände und Instrumente zur heimlichen Informationsbeschaffung,
                                wie den Einsatz
                                von Vertrauensleuten und Gewährspersonen, Observationen, Bild- und Ton*-aufzeichnungen, Tarnpapiere und
                                Tarnkennzeichen anwenden. In
                                Individualrechte darf nur nach Maßgabe besonderer Befugnisse eingegriffen werden. Im Übrigen darf die Anwendung eines
                                Mittels gemäß
                                Satz 1 keinen Nachteil herbeiführen, der erkennbar außer Verhältnis zur Bedeutung des aufzuklärenden Sachverhalts
                                steht. Die Mittel
                                nach Satz 1 sind in einer Dienstvorschrift zu benennen, die auch die Zuständigkeit für die Anordnung solcher
                                Informationsbeschaffungen
                                und das Nähere zu Satz 3 regelt. Die Dienstvorschrift bedarf der Zustimmung des Bundesministeriums des Innern, für Bau
                                und Heimat, das
                                das Parlamentarische Kontrollgremium unterrichtet.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="2d74bdcd-663d-467d-ae81-9d15628f94e6"
                              eId="hauptteil-1_abschnitt-2_art-1_abs-3">
                  <akn:num GUID="66c3b0be-602e-4740-a822-0f9bfccb4b13"
                           eId="hauptteil-1_abschnitt-2_art-1_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="5ab02939-3025-4588-9b1d-d60dfebc7cb9"
                               eId="hauptteil-1_abschnitt-2_art-1_abs-3_inhalt-1">
                     <akn:p GUID="87db2f22-4426-424d-89d8-1e1cd1eba3f6"
                            eId="hauptteil-1_abschnitt-2_art-1_abs-3_inhalt-1_text-1">
                                Polizeiliche Befugnisse oder Weisungsbefugnisse stehen dem Bundesamt für Verfassungsschutz nicht zu; es darf die
                                Polizei auch nicht im
                                Wege der Amtshilfe um Maßnahmen ersuchen, zu denen es selbst nicht befugt ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="2f544347-bdc1-4ec0-bf97-365d0f8f1ec2"
                              eId="hauptteil-1_abschnitt-2_art-1_abs-4">
                  <akn:num GUID="4a226b2c-c2bc-4cf6-9949-224e6611c3d9"
                           eId="hauptteil-1_abschnitt-2_art-1_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="2581804e-a597-4006-a47d-e8b4ba3e71c1"
                               eId="hauptteil-1_abschnitt-2_art-1_abs-4_inhalt-1">
                     <akn:p GUID="b8a4e3c4-40fe-40ce-9a85-c15a4416f4bb"
                            eId="hauptteil-1_abschnitt-2_art-1_abs-4_inhalt-1_text-1">
                                Werden personenbezogene Daten beim Betroffenen mit seiner Kenntnis erhoben, so ist der Erhebungszweck anzugeben. Der
                                Betroffene ist
                                auf die Freiwilligkeit seiner Angaben hinzuweisen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="fa16e32f-6b8f-40c7-b042-7052bf0ef694"
                              eId="hauptteil-1_abschnitt-2_art-1_abs-5">
                  <akn:num GUID="de7c86a4-cbdf-4daa-ad47-7e1831ec6617"
                           eId="hauptteil-1_abschnitt-2_art-1_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="3067c3fe-c52f-4b5c-96ce-d13005865011"
                               eId="hauptteil-1_abschnitt-2_art-1_abs-5_inhalt-1">
                     <akn:p GUID="714d15aa-9ee0-4ee2-bbbe-578b0c8fc759"
                            eId="hauptteil-1_abschnitt-2_art-1_abs-5_inhalt-1_text-1">
                                Von mehreren geeigneten Maßnahmen hat das Bundesamt für Verfassungsschutz diejenige zu wählen, die den Betroffenen
                                voraussichtlich am
                                wenigsten beeinträchtigt. Eine Maßnahme darf keinen Nachteil herbeiführen, der erkennbar außer Verhältnis zu dem
                                beabsichtigten Erfolg
                                steht.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="b8ed01df-2e54-4dc7-a4a4-b076c4fd9192"
                         eId="hauptteil-1_abschnitt-2_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="06fb0e04-cbb1-4437-aad3-997db30c58ba"
                        eId="hauptteil-1_abschnitt-2_art-2_bezeichnung-1">
                         § 8a </akn:num>
               <akn:heading GUID="9ec878ba-660c-4b92-a432-be2ee28dd422"
                            eId="hauptteil-1_abschnitt-2_art-2_überschrift-1">
                        Besondere Auskunftsverlangen
                    </akn:heading>
               <akn:paragraph GUID="6c4b0b14-9367-483a-9ff1-d0c028246c4e"
                              eId="hauptteil-1_abschnitt-2_art-2_abs-1">
                  <akn:num GUID="358a5a4d-a02d-4ed1-8b81-d0c37f652ee5"
                           eId="hauptteil-1_abschnitt-2_art-2_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="98e8613e-c7f4-4aca-94a1-e35cb4df0138"
                               eId="hauptteil-1_abschnitt-2_art-2_abs-1_inhalt-1">
                     <akn:p GUID="1ea8c1d0-ebf8-48ed-94b3-9255b7d851bf"
                            eId="hauptteil-1_abschnitt-2_art-2_abs-1_inhalt-1_text-1">
                                (2a) Soweit dies zur Sammlung und Auswertung von Informationen erforderlich ist und Tatsachen die Annahme
                                rechtfertigen, dass
                                schwerwiegende Gefahren für die in § 3 Absatz 1 genannten Schutzgüter vorliegen, darf das Bundesamt für
                                Verfassungsschutz im
                                Einzelfall das Bundeszentralamt für Steuern ersuchen, bei den Kreditinstituten die in § 93b Absatz 1 der
                                Abgabenordnung bezeichneten
                                Daten abzurufen. § 93 Absatz 9 der Abgabenordnung findet keine Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="6db28bfc-9534-49ce-b37f-58b237a7c446"
                              eId="hauptteil-1_abschnitt-2_art-2_abs-2">
                  <akn:num GUID="b84ba72c-6199-4ddb-8108-927adf8ae61e"
                           eId="hauptteil-1_abschnitt-2_art-2_abs-2_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="47b4364c-5a54-45c0-ac64-ac777ddefc9f"
                               eId="hauptteil-1_abschnitt-2_art-2_abs-2_inhalt-1">
                     <akn:p GUID="2407861a-2fb7-4f59-b446-d531aa831cc6"
                            eId="hauptteil-1_abschnitt-2_art-2_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf im Einzelfall bei denjenigen, die geschäftsmäßig Teledienste erbringen oder
                                daran mitwirken,
                                Auskunft über Daten einholen, die für die Begründung, inhaltliche Ausgestaltung, Änderung oder Beendigung eines
                                Vertragsverhältnisses
                                über Teledienste (Bestandsdaten) gespeichert worden sind, soweit dies zur Sammlung und Auswertung von Informationen
                                erforderlich ist
                                und tatsächliche Anhaltspunkte für schwerwiegende Gefahren für die in § 3 Absatz 1 genannten Schutzgüter vorliegen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="1a87c7f9-4cc4-4d0b-98b9-ac80bf5f78ed"
                              eId="hauptteil-1_abschnitt-2_art-2_abs-3">
                  <akn:num GUID="9cb656af-b5bb-4659-ac41-70cbf16be062"
                           eId="hauptteil-1_abschnitt-2_art-2_abs-3_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="f08d8f2c-986c-46f0-aca7-c79848775682"
                            eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1">
                     <akn:intro GUID="6f774efd-49d3-47dd-bf3e-2c13c517a947"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_intro-1">
                        <akn:p GUID="27aee29f-7825-4d8c-8dd5-e32d47b4b640"
                               eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf im Einzelfall Auskunft einholen bei
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="378c2688-837d-4936-a6e5-c0ed8a3fe14f"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="0835c7a3-b34c-424f-8b2a-23e046712c58"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="c98466d6-431a-47dc-a745-2e9897a093e9"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="2c75e08d-5085-49b7-af66-f7806c2a6fc2"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Luftfahrtunternehmen sowie Betreibern von Computerreservierungssystemen und Globalen Distributionssystemen für
                                        Flüge zu Namen und
                                        Anschriften des Kunden sowie zur Inanspruchnahme und den Umständen von Transportleistungen, insbesondere zum
                                        Zeitpunkt von
                                        Abfertigung und Abflug und zum Buchungsweg,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="036c74bb-15e1-45fa-912d-a94432664c73"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="9c4b8673-2e11-400e-8b65-64dabdaafb6e"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="17a7975f-c16e-41d4-bc6b-45876d2578b5"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="09fd7905-93c3-41ab-8c0c-7385f61785ff"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Kreditinstituten, Finanzdienstleistungsinstituten und Finanzunternehmen zu Konten, Konteninhabern und
                                        sonstigen Berechtigten sowie
                                        weiteren am Zahlungsverkehr Beteiligten und zu Geldbewegungen und Geldanlagen, insbesondere über Kontostand
                                        und Zahlungsein- und
                                        -ausgänge,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="8fa72f6c-a5ff-4d51-825c-28d7ea66018a"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3">
                        <akn:num GUID="b640a9d1-5ff7-4ff4-8529-de7b3f673cf9"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="d3ba1ba2-8531-4f07-8f52-84b51ea8442c"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="16e7e13f-988d-4d99-a4be-0dd8ba64a36c"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_inhalt-1_text-1">
                                        (weggefallen)
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="cc28b3e6-fcc1-4e60-a050-0ca182d91e6b"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4">
                        <akn:num GUID="b51ba84a-dfdf-4424-ba57-988526a5191f"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="2eae5705-8bc0-45af-b20a-cc621d3ccc70"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="df70c8ad-13ae-43b2-a96b-fdc42977e1b7"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_inhalt-1_text-1">
                                        denjenigen, die geschäftsmäßig Telekommunikationsdienste erbringen oder daran mitwirken, zu Verkehrsdaten nach
                                        § 96 Abs. 1 Nr. 1
                                        bis 4 des Telekommunikationsgesetzes und sonstigen zum Aufbau und zur Aufrechterhaltung der Telekommunikation
                                        notwendigen
                                        Verkehrsdaten und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a3bc5722-0e5d-434b-a196-d6fe9bc3b07b"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5">
                        <akn:num GUID="8f81a6e1-60c5-48d7-96b1-0192b59b847e"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:list GUID="42998ee5-c75a-4512-bc63-37fc7513a23f"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1">
                           <akn:intro GUID="6d1231a7-0dc5-49dd-820b-e6f4d9bbfd78"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_intro-1">
                              <akn:p GUID="7b8c199b-d6af-4aac-9e65-4a57a3ee9d75"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_intro-1_text-1">
                                            denjenigen, die geschäftsmäßig Teledienste erbringen oder daran mitwirken, zu
                                        </akn:p>
                           </akn:intro>
                           <akn:point GUID="58b556a4-d304-4904-9d7a-1baa50a1b853"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1">
                              <akn:num GUID="ab5e7d38-707f-4983-8d6b-d7b9877afc8d"
                                       eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_bezeichnung-1">
                                             a) </akn:num>
                              <akn:content GUID="bc5fefdf-62a5-4af3-8d5a-301f561f97d3"
                                           eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1">
                                 <akn:p GUID="862b705d-2a3b-4e18-b0af-37fb6105982f"
                                        eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1">
                                                Merkmalen zur Identifikation des Nutzers eines Teledienstes,
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="adf73706-7a86-4163-af04-09131d4b8984"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2">
                              <akn:num GUID="a16c07bb-23e6-4f92-ad8d-846ca5acece2"
                                       eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_bezeichnung-1">
                                             b) </akn:num>
                              <akn:content GUID="6f87d7a5-b00c-438b-94cf-a4ab7ea3ee08"
                                           eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_inhalt-1">
                                 <akn:p GUID="4b642504-54fd-4c92-a7b1-e84973017bc4"
                                        eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_inhalt-1_text-1">
                                                Angaben über Beginn und Ende sowie über den Umfang der jeweiligen Nutzung und
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="3e9f2c49-bfdb-44c3-ada8-3a7b0f3d3ec0"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3">
                              <akn:num GUID="fc3448fe-99a6-4100-b544-9ffc6dd56cbe"
                                       eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_bezeichnung-1">
                                             c) </akn:num>
                              <akn:content GUID="66685f64-96b4-42ec-a661-ca6189272bc4"
                                           eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_inhalt-1">
                                 <akn:p GUID="b5017dbc-a9e3-4ba7-a7c2-387f473ebeec"
                                        eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_inhalt-1_text-1">
                                                Angaben über die vom Nutzer in Anspruch genommenen Teledienste,
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                        </akn:list>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="3bb86278-b050-4450-9586-f2f8340a39f7"
                              eId="hauptteil-1_abschnitt-2_art-2_abs-4">
                  <akn:num GUID="0d26a5f7-dbbc-4f9b-9c67-e5ece0f8352c"
                           eId="hauptteil-1_abschnitt-2_art-2_abs-4_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="3a135dcf-a3ba-41f4-834b-1128b696d465"
                            eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1">
                     <akn:intro GUID="a995e298-ad9c-44dc-a900-89c0c04397f0"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_intro-1">
                        <akn:p GUID="95f43352-d8a6-42e9-81fb-c66dbdd1e071"
                               eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_intro-1_text-1">
                                    Anordnungen nach den Absätzen 2 und 2a dürfen sich nur gegen Personen richten, bei denen
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="ba423af3-d4a8-4040-8d39-333d9e3917fb"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1">
                        <akn:num GUID="eb4a14b9-d116-41d9-af71-0d4eda9ba1c4"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="9046dd6d-5ba9-4434-8ebe-12d6ad130d47"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="6d942cc5-4a18-4c32-9682-29a5b67bf7b9"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_inhalt-1_text-1">
                                        tatsächliche Anhaltspunkte dafür vorliegen, dass sie die schwerwiegenden Gefahren nach den Absätzen 2 oder 2a
                                        nachdrücklich
                                        fördern, oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="9f813a09-5a06-4eff-8141-b10f976ef21b"
                                eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2">
                        <akn:num GUID="307faf78-08e8-4de6-a814-8674ec2a4361"
                                 eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:list GUID="716c048a-6f26-4847-ba9f-bfe460edeb79"
                                  eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1">
                           <akn:intro GUID="cf945319-7b7e-4b7a-9df9-770418c0a1a8"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_intro-1">
                              <akn:p GUID="aa89f305-12a7-4800-b401-105f574bb6ec"
                                     eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_intro-1_text-1">
                                            auf Grund bestimmter Tatsachen anzunehmen ist
                                        </akn:p>
                           </akn:intro>
                           <akn:point GUID="ce12666a-3bb2-4cb1-b0b4-b051a4471a0b"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1">
                              <akn:num GUID="4b48877d-ff5d-4d05-8bff-5a1a4caae917"
                                       eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_bezeichnung-1">
                                             a) </akn:num>
                              <akn:content GUID="a90b03e4-09ba-4d70-b1af-22b13b539948"
                                           eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1">
                                 <akn:p GUID="ca6a72b4-5167-4a30-8312-86ff3b0b9db8"
                                        eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                                bei Auskünften nach Absatz 2 Satz 1 Nr. 1, 2 und 5 sowie nach Absatz 2a, dass sie die Leistung für
                                                eine Person nach Nummer 1
                                                in Anspruch nehmen, oder
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="e62ed652-7362-46f0-9a92-7e65ce80646c"
                                      eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2">
                              <akn:num GUID="0910da4a-d4bb-4353-8182-289da4a8e284"
                                       eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_bezeichnung-1">
                                             b) </akn:num>
                              <akn:content GUID="ab04c86b-a8b8-4d7a-b27e-a9b061ec6fd6"
                                           eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1">
                                 <akn:p GUID="05679c4f-34d8-4af0-a794-6b4b125f2d73"
                                        eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                                bei Auskünften nach Absatz 2 Satz 1 Nummer 4, dass sie für eine Person nach Nummer 1 bestimmte oder
                                                von ihr herrührende
                                                Mitteilungen entgegennehmen oder weitergeben, oder dass eine Person nach Nummer 1 ihren Anschluss
                                                benutzt.
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                        </akn:list>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="af2d0bef-fbb8-4ab7-931a-5fe719df8250"
                              eId="hauptteil-1_abschnitt-2_art-2_abs-5">
                  <akn:num GUID="4f8c7f58-c212-4a53-98a1-64bc0f4bc3a2"
                           eId="hauptteil-1_abschnitt-2_art-2_abs-5_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="6c2cd0bd-a625-468e-9551-bd5aa0593b87"
                               eId="hauptteil-1_abschnitt-2_art-2_abs-5_inhalt-1">
                     <akn:p GUID="ac8d26d4-e285-4c18-a97f-f940d1c2e90f"
                            eId="hauptteil-1_abschnitt-2_art-2_abs-5_inhalt-1_text-1">
                                bis (9) (weggefallen)
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="74366ccd-cfeb-4718-ad41-c0eae5251294"
                         eId="hauptteil-1_abschnitt-2_art-3"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="63e2654c-4df4-4bf6-9d41-c3c2d436838f"
                        eId="hauptteil-1_abschnitt-2_art-3_bezeichnung-1">
                         § 8b </akn:num>
               <akn:heading GUID="c97d350c-698c-460d-a5be-fda8dd351103"
                            eId="hauptteil-1_abschnitt-2_art-3_überschrift-1">
                        Verfahrensregelungen zu besonderen Auskunftsverlangen
                    </akn:heading>
               <akn:paragraph GUID="321c4504-8814-4a2b-9765-dcd328ac995d"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-1">
                  <akn:num GUID="cd814eab-e738-488c-97ee-3c76026fbfa5"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="377fdc40-8b3a-4898-b9d3-ceda14f13ead"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-1_inhalt-1">
                     <akn:p GUID="6d18ed08-685d-4e80-a673-3f638f189c40"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-1_inhalt-1_text-1">
                                Anordnungen nach § 8a Absatz 1 und 2 werden vom Behördenleiter oder seinem Vertreter beantragt; der Antrag ist
                                schriftlich zu stellen
                                und zu begründen. Zuständig für die Anordnungen ist das Bundesministerium des Innern, für Bau und Heimat. Die
                                Anordnung einer Auskunft
                                über künftig anfallende Daten ist auf höchstens drei Monate zu befristen. Die Verlängerung dieser Anordnung um jeweils
                                nicht mehr als
                                drei Monate ist auf Antrag zulässig, soweit die Voraussetzungen der Anordnung fortbestehen. Auf die Anordnung der
                                Verlängerung finden
                                die Sätze 1 und 2 Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="61f169fb-531e-489e-a316-0de5756cf10b"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-2">
                  <akn:num GUID="195b4584-444b-4843-aad4-27e0bd16071e"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-2_bezeichnung-1">
                             (10) </akn:num>
                  <akn:content GUID="75445c92-9132-4c7a-899c-7db9563971c0"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-2_inhalt-1">
                     <akn:p GUID="2f6e07f9-279b-426b-87f7-99f2cc507fc1"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-2_inhalt-1_text-1">
                                Die Befugnisse nach § 8a Absatz 1 Satz 1 Nummer 4 und 5 stehen den Verfassungsschutzbehörden der Länder nur dann zu,
                                wenn das
                                Verfahren sowie die Beteiligung der G 10-Kommission, die Verarbeitung der erhobenen Daten und die Mitteilung an den
                                Betroffenen
                                gleichwertig wie in Absatz 2 und ferner eine Absatz 3 gleichwertige parlamentarische Kontrolle sowie eine
                                Verpflichtung zur
                                Berichterstattung über die durchgeführten Maßnahmen an das Parlamentarische Kontrollgremium des Bundes unter
                                entsprechender Anwendung
                                des Absatzes 3 Satz 1 zweiter Halbsatz für dessen Berichte nach Absatz 3 Satz 2 durch den Landesgesetzgeber geregelt
                                ist. Die
                                Verpflichtungen zur gleichwertigen parlamentarischen Kontrolle nach Absatz 3 gelten auch für die Befugnisse nach § 8a
                                Absatz 1 Satz 1
                                Nummer 1 und 2. Landesrecht kann für Auskünfte an die jeweilige Verfassungsschutzbehörde des Landes Regelungen
                                vorsehen, die dem
                                Absatz 5 entsprechen, und die auf Grund von Absatz 8 Satz 1 bis 3 erlassene Rechtsverordnung sowie die Vorgaben nach
                                Absatz 8 Satz 4
                                und 5 für solche Auskünfte für anwendbar erklären.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="ace6052d-50e0-4063-9d94-ac1c59ec03a5"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-3">
                  <akn:num GUID="f2379822-e0c5-4160-ba14-ed39f3e6207b"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-3_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="53e6d0f6-b3c9-4620-9c06-78cfcaf4dce4"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-3_inhalt-1">
                     <akn:p GUID="7b6f2a3f-58ed-4239-868c-847f5137129a"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-3_inhalt-1_text-1">
                                Über Anordnungen nach § 8a Absatz 1 und 2 unterrichtet das Bundesministerium des Innern, für Bau und Heimat monatlich
                                die
                                G 10-Kommission (§ 1 Absatz 2 des Artikel 10-Gesetzes) vor deren Vollzug. Bei Gefahr im Verzug kann es den Vollzug der
                                Entscheidung
                                auch bereits vor der Unterrichtung der G 10-Kommission anordnen. Die G 10-Kommission prüft von Amts wegen oder auf
                                Grund von
                                Beschwerden die Zulässigkeit und Notwendigkeit der Einholung von Auskünften. § 15 Absatz 5 des Artikel 10-Gesetzes ist
                                mit der Maßgabe
                                entsprechend anzuwenden, dass die Kontrollbefugnis der Kommission sich auf die gesamte Verarbeitung der nach § 8a
                                Absatz 1 und 2
                                erlangten personenbezogenen Daten erstreckt. Entscheidungen über Auskünfte, welche die G 10-Kommission für unzulässig
                                oder nicht
                                notwendig erklärt, hat das Bundesministerium des Innern, für Bau und Heimat unverzüglich aufzuheben. Die Daten
                                unterliegen in diesem
                                Falle einem absoluten Verwendungsverbot und sind unverzüglich zu löschen. Für die Verarbeitung der nach § 8a Absatz 1
                                und 2 erhobenen
                                Daten ist § 4 des Artikel 10-Gesetzes entsprechend anzuwenden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="d9987502-bb68-4c14-967c-5975f3c22ad0"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-4">
                  <akn:num GUID="3bdac535-d952-4dec-94fc-574216316fd9"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-4_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="d8c86970-53ee-41f6-aa6d-830a7c4cbdfe"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-4_inhalt-1">
                     <akn:p GUID="9a650964-9d67-4bdc-89e0-95df5e74b1de"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-4_inhalt-1_text-1">
                                Das Bundesministerium des Innern, für Bau und Heimat unterrichtet im Abstand von höchstens sechs Monaten das
                                Parlamentarische
                                Kontrollgremium über Anordnungen nach § 8a Absatz 1 und 2; dabei ist insbesondere ein Überblick über Anlass, Umfang,
                                Dauer, Ergebnis
                                und Kosten der im Berichtszeitraum durchgeführten Maßnahmen zu geben. Das Gremium erstattet dem Deutschen Bundestag
                                jährlich einen
                                Bericht über die Durchführung sowie Art, Umfang und Anordnungsgründe der Maßnahmen; dabei sind die Grundsätze des § 10
                                Absatz 1 des
                                Kontrollgremiumgesetzes zu beachten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="2c336a4b-ffd7-40d3-86c5-dba4e8a7a9a8"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-5">
                  <akn:num GUID="ee604273-c920-47f5-a753-ec73d7b5a1e7"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-5_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="85e95b4c-4138-4dc6-bd01-d24523af9021"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-5_inhalt-1">
                     <akn:p GUID="d6910d08-f875-4ba1-acb3-05b2bb85ac0f"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-5_inhalt-1_text-1">
                                Anordnungen sind dem Verpflichteten insoweit schriftlich mitzuteilen, als dies erforderlich ist, um ihm die Erfüllung
                                seiner
                                Verpflichtung zu ermöglichen. Anordnungen und übermittelte Daten dürfen dem Betroffenen oder Dritten vom
                                Verpflichteten nicht
                                mitgeteilt werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="54e4961c-0df4-4592-ae71-577305af2af7"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-6">
                  <akn:num GUID="94a2b6ef-c4fe-4393-8d59-07988b1e9255"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-6_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="59480a77-d1c1-43be-b3a2-a5c1d92d2d39"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-6_inhalt-1">
                     <akn:p GUID="a38a5a11-1ea2-41f6-9391-eedb50359787"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-6_inhalt-1_text-1">
                                Dem Verpflichteten ist es verboten, allein auf Grund einer Anordnung nach § 8a Absatz 1 einseitige Handlungen
                                vorzunehmen, die für den
                                Betroffenen nachteilig sind und die über die Erteilung der Auskunft hinausgehen, insbesondere bestehende Verträge oder
                                Geschäftsverbindungen zu beenden, ihren Umfang zu beschränken oder ein Entgelt zu erheben oder zu erhöhen. Die
                                Anordnung ist mit dem
                                ausdrücklichen Hinweis auf dieses Verbot und darauf zu verbinden, dass das Auskunftsersuchen nicht die Aussage
                                beinhaltet, dass sich
                                die betroffene Person rechtswidrig verhalten hat oder ein darauf gerichteter Verdacht bestehen müsse.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="72ca4af4-773d-4394-b8b5-24aef6114943"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-7">
                  <akn:num GUID="00890457-5199-490c-8f41-ebce3c32c443"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-7_bezeichnung-1">
                             (6) </akn:num>
                  <akn:content GUID="e141bd44-6b86-4042-be9f-5cdde36407d0"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-7_inhalt-1">
                     <akn:p GUID="e17fabe2-dddb-4732-94d7-a197e9c393e0"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-7_inhalt-1_text-1">
                                Die in § 8a Absatz 1 Satz 1 genannten Stellen sind verpflichtet, die Auskunft unverzüglich und vollständig und in dem
                                Format zu
                                erteilen, das durch die auf Grund von Absatz 8 Satz 1 bis 3 erlassene Rechtsverordnung oder in den in Absatz 8 Satz 4
                                und 5
                                bezeichneten Rechtsvorschriften vorgeschrieben ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="968e2d31-4d7e-4bae-ab84-bb1bc83f9bde"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-8">
                  <akn:num GUID="93993a16-f925-4fb0-972c-3c5d013db307"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-8_bezeichnung-1">
                             (7) </akn:num>
                  <akn:content GUID="e8c299fb-3c96-4601-a0e3-4f3f70aba534"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-8_inhalt-1">
                     <akn:p GUID="98153a8f-40d2-41e3-b73c-78f29dfc2eb2"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-8_inhalt-1_text-1">
                                Für Anordnungen nach § 8a findet § 12 Absatz 1 des Artikel 10-Gesetzes entsprechende Anwendung, mit der Maßgabe, dass
                                § 12 Absatz 1
                                Satz 5 des Artikel 10-Gesetzes nur für Maßnahmen nach § 8a Absatz 1 Satz 1 Nummer 4 und 5 Anwendung findet. Wurden
                                personenbezogene
                                Daten an eine andere Stelle übermittelt, erfolgt die Mitteilung im Benehmen mit dieser.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="41d2de58-d9b1-4121-948c-679b9056db9e"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-9">
                  <akn:num GUID="410810a3-9995-4fa2-84ad-0b35ffd95402"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-9_bezeichnung-1">
                             (8) </akn:num>
                  <akn:list GUID="5ce31094-6d14-43fc-ae4c-94a7f34587bf"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1">
                     <akn:intro GUID="6ce4217a-29ee-4974-9b8d-85a775fbfb46"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_intro-1">
                        <akn:p GUID="326fea46-8df5-4e06-b815-99d225318251"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_intro-1_text-1">
                                    Das Bundesministerium des Innern, für Bau und Heimat wird ermächtigt, durch Rechtsverordnung im Einvernehmen mit
                                    dem
                                    Bundeskanzleramt, dem Bundesministerium für Wirtschaft und Energie, dem Bundesministerium für Verkehr und digitale
                                    Infrastruktur,
                                    dem Bundesministerium der Justiz und für Verbraucherschutz und dem Bundesministerium der Verteidigung ohne
                                    Zustimmung des
                                    Bundesrates zu bestimmen, dass Auskünfte nach § 8a Absatz 1 mit Ausnahme der Auskünfte nach § 8a Absatz 1 Satz 1
                                    Nummer 4, auch
                                    soweit andere Vorschriften hierauf verweisen, ganz oder teilweise auf maschinell verwertbaren Datenträgern oder
                                    durch
                                    Datenfernübertragung übermittelt werden müssen. Dabei können insbesondere geregelt werden
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="7e4750e0-bb9f-4ac8-b7f9-8b26fdcf0d27"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1">
                        <akn:num GUID="914a5a87-d021-4163-8e3d-9e9f81eb6ce5"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="ae09583b-b047-4e33-a261-53f423f0d2e3"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="59b12b56-df92-4cf8-a520-6023b98be0f2"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_inhalt-1_text-1">
                                        die Voraussetzungen für die Anwendung des Verfahrens,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a3e940cb-9bc4-4870-a10b-886baf4f0457"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2">
                        <akn:num GUID="eb567533-e3f7-4a23-a7d5-9d4ab0007a5d"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="82dd8da9-ca7c-4381-8beb-b5241def1926"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="6e258176-13c8-4691-9077-481e996f7e71"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_inhalt-1_text-1">
                                        das Nähere über Form, Inhalt, Verarbeitung und Sicherung der zu übermittelnden Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="f965610d-f8ed-4be5-a9bc-9d8583910410"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3">
                        <akn:num GUID="45e888de-6dae-4c0a-9caf-32e7ba90a3a3"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="624296bb-6d34-449f-a673-c8eed99542f9"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="b374f273-b073-47c6-a37d-1933edd20acc"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_inhalt-1_text-1">
                                        die Art und Weise der Übermittlung der Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="749ea0a5-96e2-46ec-aedc-97db815e850e"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4">
                        <akn:num GUID="57f3e8a8-d159-4e58-a89c-67991cc95cdb"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="4fec4521-55b2-44c8-ad80-16db43342f43"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="477a817a-d431-42a4-9daf-e92405711710"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_inhalt-1_text-1">
                                        die Zuständigkeit für die Entgegennahme der zu übermittelnden Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="e928ae4d-2181-44e7-bf7e-ae4bfafba33f"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5">
                        <akn:num GUID="9db40de5-e769-417e-9edf-2828b39721c6"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:content GUID="22502a67-1259-42c0-a01d-1baec187daa0"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="fadc1e90-a4e2-4d11-b7e6-ca2c4d498296"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_inhalt-1_text-1">
                                        der Umfang und die Form der für dieses Verfahren erforderlichen besonderen Erklärungspflichten des
                                        Auskunftspflichtigen und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a51d15aa-e813-4cb7-be5d-8eb97d19dcb7"
                                eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6">
                        <akn:num GUID="cdde7ac5-b6a0-463e-91f3-6a666f098ce0"
                                 eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_bezeichnung-1">
                                     6. </akn:num>
                        <akn:content GUID="11d99cf8-0133-4eb5-99a9-3a92f2b076f1"
                                     eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_inhalt-1">
                           <akn:p GUID="c78bc25e-82af-4f19-a15b-1edffabfb3f3"
                                  eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_inhalt-1_text-1">
                                        Tatbestände und Bemessung einer auf Grund der Auskunftserteilung an Verpflichtete zu leistenden
                                        Aufwandsentschädigung.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="6f8758b6-7a12-4609-9b0b-536cbae55236"
                              eId="hauptteil-1_abschnitt-2_art-3_abs-10">
                  <akn:num GUID="213be306-a7aa-44a7-b5bf-23a7b9769b1c"
                           eId="hauptteil-1_abschnitt-2_art-3_abs-10_bezeichnung-1">
                             (9) </akn:num>
                  <akn:content GUID="7d3182d9-bc59-433f-b12f-edbbccca8778"
                               eId="hauptteil-1_abschnitt-2_art-3_abs-10_inhalt-1">
                     <akn:p GUID="c6a43aee-f8fa-4044-8e68-ba11836039d7"
                            eId="hauptteil-1_abschnitt-2_art-3_abs-10_inhalt-1_text-1">
                                Für die Erteilung von Auskünften nach § 8a Absatz 1 Satz 1 Nummer 4 hat der Verpflichtete Anspruch auf Entschädigung
                                entsprechend § 23
                                des Justizvergütungs- und -entschädigungsgesetzes.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="ecd8871e-cb79-4626-b8b0-347c16593e19"
                         eId="hauptteil-1_abschnitt-2_art-4"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="55d17f65-23f0-4a34-8fb6-053e0c4d9d93"
                        eId="hauptteil-1_abschnitt-2_art-4_bezeichnung-1">
                         § 8c </akn:num>
               <akn:heading GUID="e94988fc-5030-4d9c-8b12-08a80fbefee7"
                            eId="hauptteil-1_abschnitt-2_art-4_überschrift-1">
                        Einschränkungen eines Grundrechts
                    </akn:heading>
               <akn:paragraph GUID="796aa05c-a4ae-410e-bc20-e86100b9a232"
                              eId="hauptteil-1_abschnitt-2_art-4_abs-1">
                  <akn:num GUID="7cb361cc-befd-4e4c-9dbe-47cd32056e2b"
                           eId="hauptteil-1_abschnitt-2_art-4_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="ec2e3a21-e27c-4529-ab10-3e2717650c69"
                               eId="hauptteil-1_abschnitt-2_art-4_abs-1_inhalt-1">
                     <akn:p GUID="45af6883-f6d0-4745-a0f6-e994410b79e4"
                            eId="hauptteil-1_abschnitt-2_art-4_abs-1_inhalt-1_text-1">
                                Das Grundrecht des Fernmeldegeheimnisses (Artikel 10 des Grundgesetzes) wird nach Maßgabe des § 8a Absatz 2 Satz 1
                                Nummer 4 und 5 und
                                Absatz 3 sowie des § 8b Absatz 1, 2, 4 bis 8 und 10 eingeschränkt.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="12d66bae-26a9-4a28-a322-19d0527bfb5a"
                         eId="hauptteil-1_abschnitt-2_art-5"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="a71fb92d-12b8-4701-8787-2601d1c5902b"
                        eId="hauptteil-1_abschnitt-2_art-5_bezeichnung-1">
                         § 8d </akn:num>
               <akn:heading GUID="5fc240b4-3ecc-48cf-9d83-bc1e7be6b3d0"
                            eId="hauptteil-1_abschnitt-2_art-5_überschrift-1">
                        Besondere Auskunftsverlangen zu Bestandsdaten
                    </akn:heading>
               <akn:paragraph GUID="fd704936-a4e8-4f30-a6e5-ccd1360cc321"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-1">
                  <akn:num GUID="cc6cddb5-4837-4919-99bf-e80cfa71df46"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="52e1f657-5153-4bc5-84f9-e1fb948ca1b5"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1">
                     <akn:intro GUID="83b8c0e6-9c67-45ec-8599-c575b087d2f2"
                                eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_intro-1">
                        <akn:p GUID="57e582c9-c313-4b64-b781-0ec0160ab13f"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_intro-1_text-1">
                                    Soweit dies auf Grund tatsächlicher Anhaltspunkte im Einzelfall zur Aufklärung bestimmter Bestrebungen oder
                                    Tätigkeiten nach § 3
                                    Absatz 1 erforderlich ist, darf das Bundesamt für Verfassungsschutz Auskunft verlangen von demjenigen, der
                                    geschäftsmäßig
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="57b4625e-0cde-40f0-b9c2-e2a72b691b74"
                                eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="73c7e3ab-fa55-486a-8b4d-9b3db4661bb1"
                                 eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="7080bd30-f0eb-4e23-9903-e2e596f88a66"
                                     eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="d07d139d-9ce5-4eab-acd2-ce72a26360e7"
                                  eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Telekommunikationsdienste erbringt oder daran mitwirkt, über Bestandsdaten nach § 3 Nummer 6 und § 172 des
                                        Telekommunikationsgesetzes,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="d99fc8ae-9aee-49f0-9afb-788d574f5401"
                                eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="a6e516ac-6517-4d46-9433-5f5ae74f4b2a"
                                 eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="a2036a09-1704-437e-bd0e-239c5fbf1f41"
                                     eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="c4e88445-94ba-45a0-ad41-34d0f9d32669"
                                  eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Telemediendienste erbringt oder daran mitwirkt, über Bestandsdaten nach § 2 Absatz 2 Nummer 2 des
                                        Telekommunikation-Telemedien-Datenschutz-Gesetzes.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="269cdb6c-229e-4918-8599-45a86872f4fb"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-2">
                  <akn:num GUID="a4282bee-8236-4f90-98a8-6c18317bc723"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="d89b6d6c-db29-4208-9ac9-4c062d31f257"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-2_inhalt-1">
                     <akn:p GUID="1978fcd8-09c6-43d3-b89e-70a50a496c43"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-2_inhalt-1_text-1">
                                Die Auskunft darf auch verlangt werden anhand einer zu einem bestimmten Zeitpunkt zugewiesenen
                                Internetprotokoll-Adresse. Die
                                Rechtsgrundlage und die tatsächlichen Anhaltspunkte, die das Auskunftsverlangen veranlassen, sind aktenkundig zu
                                machen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="f881e7c3-05b1-403a-97e1-dfc98b9717f5"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-3">
                  <akn:num GUID="1d91f349-87f2-416b-9230-7a38c6423fd5"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="63103731-78a7-4a15-b3ed-ffcdb3d62dbc"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-3_inhalt-1">
                     <akn:p GUID="1367c75f-fb70-441e-aa0a-73bb5a275a69"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-3_inhalt-1_text-1">
                                Die Auskunft zu Daten, mittels derer der Zugriff auf Endgeräte oder auf Speichereinrichtungen, die in diesen
                                Endgeräten oder hiervon
                                räumlich getrennt eingesetzt werden, geschützt wird, darf nur im Falle des Absatzes 1 Satz 1 Nummer 1 verlangt werden
                                und nur dann
                                verlangt werden, wenn die gesetzlichen Voraussetzungen für die Nutzung der Daten vorliegen. Für diese
                                Auskunftsverlangen gilt § 8b
                                Absatz 1 Satz 1 und 2 und Absatz 2 entsprechend.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="94448876-6712-4c29-8021-549fac7106ee"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-4">
                  <akn:num GUID="80945462-2d01-41f4-b8dc-ce6525755c77"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="7496a087-429f-4c93-96bb-7b001a6ab025"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-4_inhalt-1">
                     <akn:p GUID="3acc249e-94e6-459f-9f75-add6fa87a370"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-4_inhalt-1_text-1">
                                Die betroffene Person ist in den Fällen der Absätze 2 und 3 über die Auskunftserteilung zu benachrichtigen. Die
                                Benachrichtigung
                                erfolgt, soweit und sobald eine Gefährdung des Zwecks der Auskunft und der Eintritt übergreifender Nachteile für das
                                Wohl des Bundes
                                oder eines Landes ausgeschlossen werden können. Die Benachrichtigung unterbleibt, wenn ihr überwiegende schutzwürdige
                                Belange Dritter
                                oder der betroffenen Person selbst entgegenstehen. Wird die Benachrichtigung nach Satz 2 zurückgestellt oder nach Satz
                                3 von ihr
                                abgesehen, sind die Gründe aktenkundig zu machen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="7249dfe7-61a2-4af9-b5c8-ce2f25c215ee"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-5">
                  <akn:num GUID="c7bd6f83-ff70-4277-ab83-14a6affb321f"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="b95b071b-7aec-4f8e-89ce-cbace7638268"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-5_inhalt-1">
                     <akn:p GUID="392844be-c75a-4eb4-9165-f267815c59ac"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-5_inhalt-1_text-1">
                                Der auf Grund eines Auskunftsverlangens Verpflichtete hat die zur Auskunftserteilung erforderlichen Daten unverzüglich
                                und vollständig
                                zu übermitteln.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="a46db979-37d4-4b8c-ae80-f815c8d425ae"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-6">
                  <akn:num GUID="6477c0cf-a9c1-476d-9c86-d7a2f5a95f66"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-6_bezeichnung-1">
                             (6) </akn:num>
                  <akn:content GUID="e9673b43-6222-4bbd-8b69-0163c2567753"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-6_inhalt-1">
                     <akn:p GUID="8569c3ef-8356-4483-8d47-1b07674925d2"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-6_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz hat den Verpflichteten für ihm erteilte Auskünfte eine Entschädigung zu gewähren.
                                Der Umfang der
                                Entschädigung bemisst sich nach § 23 und Anlage 3 des Justizvergütungs- und -entschädigungsgesetzes; die Vorschriften
                                über die
                                Verjährung in § 2 Absatz 1 und 4 des Justizvergütungs- und -entschädigungsgesetzes finden entsprechend Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="b0dee209-9121-47cc-a73e-a3167d938ab8"
                              eId="hauptteil-1_abschnitt-2_art-5_abs-7">
                  <akn:num GUID="d1b28bf8-fe78-4cf1-9fec-b5d458a8f9a8"
                           eId="hauptteil-1_abschnitt-2_art-5_abs-7_bezeichnung-1">
                             (7) </akn:num>
                  <akn:content GUID="fa047682-7a14-421d-bf60-3e3c4f8914be"
                               eId="hauptteil-1_abschnitt-2_art-5_abs-7_inhalt-1">
                     <akn:p GUID="16026404-a7f0-4a3a-ab19-c742454c1725"
                            eId="hauptteil-1_abschnitt-2_art-5_abs-7_inhalt-1_text-1">
                                Das Fernmeldegeheimnis (Artikel 10 des Grundgesetzes) wird nach Maßgabe des Absatzes 2 Satz 1 eingeschränkt.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="adff46c2-635e-4885-bdf2-2ed54355ea2d"
                         eId="hauptteil-1_abschnitt-2_art-6"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="cc50804b-f6eb-40bf-b177-fa6d3eb8f6e9"
                        eId="hauptteil-1_abschnitt-2_art-6_bezeichnung-1">
                         § 9 </akn:num>
               <akn:heading GUID="2b1967c4-3d6e-49f3-96f0-ad36f220712e"
                            eId="hauptteil-1_abschnitt-2_art-6_überschrift-1">
                        Besondere Formen der Datenerhebung
                    </akn:heading>
               <akn:paragraph GUID="1107c9ab-fa9a-4840-a11d-3dda398ef615"
                              eId="hauptteil-1_abschnitt-2_art-6_abs-1">
                  <akn:num GUID="ea3f560d-b6f5-47aa-80fd-d8dd0572c318"
                           eId="hauptteil-1_abschnitt-2_art-6_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="a0fdb061-8b6b-4dde-8251-da293d398347"
                            eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1">
                     <akn:intro GUID="6dc00e34-d8ab-4e10-83a2-890870372129"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_intro-1">
                        <akn:p GUID="aeb10239-0cd8-47af-b5d6-a73f61cf603f"
                               eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf Informationen, insbesondere personenbezogene Daten, mit den Mitteln gemäß
                                    § 8 Abs. 2
                                    erheben, wenn Tatsachen die Annahme rechtfertigen, daß
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="717682e4-a49b-4249-b859-1d4947ccc5bd"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="cefbb54a-d537-41d0-8dff-8157d5a44e32"
                                 eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="9dd05fc5-1259-4f30-b877-a8d2cb501e29"
                                     eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="1f4b3acc-e6ce-4d87-9da4-d903858b2f9f"
                                  eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        auf diese Weise Erkenntnisse über Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 oder die zur Erforschung
                                        solcher Erkenntnisse
                                        erforderlichen Quellen gewonnen werden können oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a195de88-8b08-4878-ab86-972dd542b941"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="550a75de-bbf5-4d6a-9929-3bc827c48109"
                                 eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="96320238-4e56-457a-98af-5b1c99897d12"
                                     eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="1b5339b3-29bd-4dd9-97b7-f91ca5376501"
                                  eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        dies zum Schutz der Mitarbeiter, Einrichtungen, Gegenstände und Quellen des Bundesamtes für Verfassungsschutz
                                        gegen
                                        sicherheitsgefährdende oder geheimdienstliche Tätigkeiten erforderlich ist.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="b49ef70f-f0c5-4f4e-a982-9c4a9646ed73"
                              eId="hauptteil-1_abschnitt-2_art-6_abs-2">
                  <akn:num GUID="4e8cf35d-d899-4c0b-991c-5d6d78a6f20e"
                           eId="hauptteil-1_abschnitt-2_art-6_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="45e22dc5-0292-45cf-90ac-5d57a0153282"
                               eId="hauptteil-1_abschnitt-2_art-6_abs-2_inhalt-1">
                     <akn:p GUID="d8b0ad0a-1cd8-4f6f-be21-574fdd16eda4"
                            eId="hauptteil-1_abschnitt-2_art-6_abs-2_inhalt-1_text-1">
                                Das in einer Wohnung nicht öffentlich gesprochene Wort darf mit technischen Mitteln nur heimlich mitgehört oder
                                aufgezeichnet werden,
                                wenn es im Einzelfall zur Abwehr einer gegenwärtigen gemeinen Gefahr oder einer gegenwärtigen Lebensgefahr für
                                einzelne Personen
                                unerläßlich ist und geeignete polizeiliche Hilfe für das bedrohte Rechtsgut nicht rechtzeitig erlangt werden kann.
                                Satz 1 gilt
                                entsprechend für einen verdeckten Einsatz technischer Mittel zur Anfertigung von Bildaufnahmen und Bildaufzeichnungen.
                                Maßnahmen nach
                                den Sätzen 1 und 2 werden durch den Präsidenten des Bundesamtes für Verfassungsschutz oder seinen Vertreter
                                angeordnet, wenn eine
                                richterliche Entscheidung nicht rechtzeitig herbeigeführt werden kann. Die richterliche Entscheidung ist unverzüglich
                                nachzuholen.
                                Zuständig ist das Amtsgericht, in dessen Bezirk das Bundesamt für Verfassungsschutz seinen Sitz hat. Für das Verfahren
                                gelten die
                                Vorschriften des Gesetzes über das Verfahren in Familiensachen und in den Angelegenheiten der freiwilligen
                                Gerichtsbarkeit
                                entsprechend. Die erhobenen Informationen dürfen nur nach Maßgabe des § 4 Abs. 4 des Artikel 10-Gesetzes verwendet
                                werden. § 4 Abs. 6
                                des Artikel 10-Gesetzes gilt entsprechend. Das Grundrecht der Unverletzlichkeit der Wohnung (Artikel 13 des
                                Grundgesetzes) wird
                                insoweit eingeschränkt.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="5632bfbf-cb49-450d-a2de-5b88980c1574"
                              eId="hauptteil-1_abschnitt-2_art-6_abs-3">
                  <akn:num GUID="3f5f2963-09a4-409a-b76c-938d644c9953"
                           eId="hauptteil-1_abschnitt-2_art-6_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="c86b7a96-fc4f-4887-b746-d65bd4b0eaeb"
                            eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1">
                     <akn:intro GUID="d9595293-e3ff-4a8c-8eb6-ea483b49715b"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_intro-1">
                        <akn:p GUID="254fb60f-dfe1-4797-b99f-975582266d4e"
                               eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_intro-1_text-1">
                                    Bei Erhebungen nach Absatz 2 und solchen nach Absatz 1, die in ihrer Art und Schwere einer Beschränkung des
                                    Brief-, Post- und
                                    Fernmelde*-geheimnisses gleichkommen, wozu insbesondere das Abhören und Aufzeichnen des nicht öffentlich
                                    gesprochenen Wortes mit dem
                                    verdeckten Einsatz technischer Mittel gehören, ist
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="5d0252b8-24a2-49d5-a15d-ffd872f07319"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="835e84ce-9ee5-4002-9c56-c9c4d6f765f0"
                                 eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="1a44bdec-8a3e-45e5-a2f2-3a9d81a667bb"
                                     eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="c8b43850-f909-428a-813e-4bb18d180f64"
                                  eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        der Eingriff nach seiner Beendigung dem Betroffenen mitzuteilen, sobald eine Gefährdung des Zweckes des
                                        Eingriffs ausgeschlossen
                                        werden kann, und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="23fa66bf-2e08-41b7-845f-e8e05f86d4e5"
                                eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="0b082ed0-7149-4e3a-a9ed-41bfeff4d884"
                                 eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="97350850-33d0-416d-a911-acdf4a4489b7"
                                     eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="fdd22fb2-1580-4499-8b13-6859e9e82966"
                                  eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        das Parlamentarische Kontrollgremium zu unterrichten.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="ab60279a-2996-42eb-9c1a-24c0ec161a38"
                              eId="hauptteil-1_abschnitt-2_art-6_abs-4">
                  <akn:num GUID="819c1f71-b7f1-4cbc-a286-3ea60cf3a938"
                           eId="hauptteil-1_abschnitt-2_art-6_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="98a3a63d-ff53-4a37-a7d4-6cbb6fa69726"
                               eId="hauptteil-1_abschnitt-2_art-6_abs-4_inhalt-1">
                     <akn:p GUID="09e25a6f-340c-49dd-8ae6-ed9a6069285b"
                            eId="hauptteil-1_abschnitt-2_art-6_abs-4_inhalt-1_text-1">
                                (weggefallen)
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="2711e8d0-c919-462d-9b3a-ab9c256675bb"
                         eId="hauptteil-1_abschnitt-2_art-7"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="ec658814-1cf1-4312-83ae-98e6ad22474c"
                        eId="hauptteil-1_abschnitt-2_art-7_bezeichnung-1">
                         § 9a </akn:num>
               <akn:heading GUID="e2f25b5d-6334-4745-9bd1-a384c7b64177"
                            eId="hauptteil-1_abschnitt-2_art-7_überschrift-1">
                        Verdeckte Mitarbeiter
                    </akn:heading>
               <akn:paragraph GUID="653de627-455d-415f-85b5-a3bef3a6dab4"
                              eId="hauptteil-1_abschnitt-2_art-7_abs-1">
                  <akn:num GUID="f7bd7720-4c80-4b81-a638-80c542f108e8"
                           eId="hauptteil-1_abschnitt-2_art-7_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="a9d401b2-9f9d-4f60-a6d1-cb8b70e617be"
                               eId="hauptteil-1_abschnitt-2_art-7_abs-1_inhalt-1">
                     <akn:p GUID="db6a9baa-c77c-42dc-9877-ef54d547abd7"
                            eId="hauptteil-1_abschnitt-2_art-7_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf eigene Mitarbeiter unter einer ihnen verliehenen und auf Dauer angelegten
                                Legende (Verdeckte
                                Mitarbeiter) zur Aufklärung von Bestrebungen unter den Voraussetzungen des § 9 Absatz 1 einsetzen. Ein dauerhafter
                                Einsatz zur
                                Aufklärung von Bestrebungen nach § 3 Absatz 1 Nummer 1 und 4 ist nur bei Bestrebungen von erheblicher Bedeutung
                                zulässig, insbesondere
                                wenn sie darauf gerichtet sind, Gewalt anzuwenden oder Gewaltanwendung vorzubereiten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="f8347510-2f27-45b3-ae1b-bdea5e8cce87"
                              eId="hauptteil-1_abschnitt-2_art-7_abs-2">
                  <akn:num GUID="03aaf0dd-508a-4948-a740-111382dd38e4"
                           eId="hauptteil-1_abschnitt-2_art-7_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="71c2d4a1-7fc5-451e-b48d-6ba3bba2ad11"
                            eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1">
                     <akn:intro GUID="6acb45eb-527e-4b98-abb8-986065712cfd"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_intro-1">
                        <akn:p GUID="13b7f9fb-4147-480a-913e-394dfc035e58"
                               eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_intro-1_text-1">
                                    Verdeckte Mitarbeiter dürfen weder zur Gründung von Bestrebungen nach § 3 Absatz 1 Nummer 1, 3 oder 4 noch zur
                                    steuernden
                                    Einflussnahme auf derartige Bestrebungen eingesetzt werden. Sie dürfen in solchen Personenzusammenschlüssen oder
                                    für solche
                                    Personenzusammenschlüsse, einschließlich strafbare Vereinigungen, tätig werden, um deren Bestrebungen aufzuklären.
                                    Im Übrigen ist im
                                    Einsatz eine Beteiligung an Bestrebungen zulässig, wenn sie
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="1ef5be09-f1bb-4e59-824c-7291db7869eb"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1">
                        <akn:num GUID="80d469f2-95e7-4950-acc6-ca0124ce97bf"
                                 eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="461e2c41-b636-4da8-8d4f-fe6d929756ee"
                                     eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="4d4c7aa2-19ed-4230-b7ef-80ae3c7518f1"
                                  eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                        nicht in Individualrechte eingreift,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="6ebbf6eb-b93d-40d3-a974-4d3d7b34c671"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2">
                        <akn:num GUID="458a2a4d-b7d2-4413-8c21-e7002c6c8842"
                                 eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="9a6116c3-54a0-4bb6-8025-210320512a38"
                                     eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="71d0cfd0-e9bd-402c-ae76-706c4c83b424"
                                  eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                        von den an den Bestrebungen Beteiligten derart erwartet wird, dass sie zur Gewinnung und Sicherung der
                                        Informationszugänge
                                        unumgänglich ist und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="c2cddc27-b49f-4231-9c3e-c4ab778687eb"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3">
                        <akn:num GUID="83e451d3-76c0-4728-bc81-c4c2d9dad603"
                                 eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="b2624312-f56d-4bd5-93f1-49426f73a9dc"
                                     eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="2f0d9941-aa0f-4a49-9f7d-429d19251bdc"
                                  eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">
                                        nicht außer Verhältnis zur Bedeutung des aufzuklärenden Sachverhalts steht.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="7baf17fa-8926-4e72-9e40-e29dfda75d99"
                              eId="hauptteil-1_abschnitt-2_art-7_abs-3">
                  <akn:num GUID="4e1a9802-2b54-4b07-be71-724899b9fb01"
                           eId="hauptteil-1_abschnitt-2_art-7_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="29d63613-36d9-425f-bd69-32d07989cc93"
                            eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1">
                     <akn:intro GUID="25560d05-b94d-477e-b824-9136141e5caf"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_intro-1">
                        <akn:p GUID="982e3e36-9801-48f9-abaa-2c69dfe50524"
                               eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_intro-1_text-1">
                                    Die Staatsanwaltschaft kann von der Verfolgung von im Einsatz begangenen Vergehen absehen oder eine bereits
                                    erhobene Klage in jeder
                                    Lage des Verfahrens zurücknehmen und das Verfahren einstellen, wenn
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="2234f0d9-e363-43a9-b77e-37cfbf8bfccf"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="cc0d857c-a46b-45f2-8937-f655ff4a8152"
                                 eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="58a2e6bf-4f33-4581-aba4-9be322938efa"
                                     eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="e2e37b62-d985-4e92-884f-e58123c668be"
                                  eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        der Einsatz zur Aufklärung von Bestrebungen erfolgte, die auf die Begehung von in § 3 Absatz 1 des Artikel
                                        10-Gesetzes
                                        bezeichneten Straftaten gerichtet sind, und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="fe538d85-e051-47a0-a443-98ac185a7d7d"
                                eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="0bfdbbf3-4a4f-4e85-a713-443c275ce55f"
                                 eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="5058b36c-270d-40f1-87b8-e7625c43389b"
                                     eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="3231ee50-997a-489b-9d5a-22f959f6061f"
                                  eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        die Tat von an den Bestrebungen Beteiligten derart erwartet wurde, dass sie zur Gewinnung und Sicherung der
                                        Informationszugänge
                                        unumgänglich war.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="716d2ad3-12d7-4656-a987-3a4e4be150b6"
                         eId="hauptteil-1_abschnitt-2_art-8"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="792c38a4-e9d1-49a5-8613-099a70c16f42"
                        eId="hauptteil-1_abschnitt-2_art-8_bezeichnung-1">
                         § 9b </akn:num>
               <akn:heading GUID="f5ad7e44-8b08-4519-8d49-5ae6b4fdfd18"
                            eId="hauptteil-1_abschnitt-2_art-8_überschrift-1">
                        Vertrauensleute
                    </akn:heading>
               <akn:paragraph GUID="2733ae91-6eb4-4ffa-b16f-bfbf1f45b796"
                              eId="hauptteil-1_abschnitt-2_art-8_abs-1">
                  <akn:num GUID="38bb9b5e-a825-4d21-9cab-d6c37327df0f"
                           eId="hauptteil-1_abschnitt-2_art-8_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="9ddef866-bb3e-4e41-8bcf-2873275a306d"
                               eId="hauptteil-1_abschnitt-2_art-8_abs-1_inhalt-1">
                     <akn:p GUID="83fc9ca3-9cd4-4f1d-8ac9-9ffdd5bcf8d2"
                            eId="hauptteil-1_abschnitt-2_art-8_abs-1_inhalt-1_text-1">
                                Für den Einsatz von Privatpersonen, deren planmäßige, dauerhafte Zusammenarbeit mit dem Bundesamt für
                                Verfassungsschutz Dritten nicht
                                bekannt ist (Vertrauensleute), ist § 9a entsprechend anzuwenden. Die Bundesregierung trägt dem Parlamentarischen
                                Kontrollgremium
                                mindestens einmal im Jahr einen Lagebericht zum Einsatz von Vertrauensleuten vor.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="2a8f8a23-8fe2-461c-adb3-78d5345e2ca9"
                              eId="hauptteil-1_abschnitt-2_art-8_abs-2">
                  <akn:num GUID="9c2e60a1-93ac-48a4-a496-f537696754b0"
                           eId="hauptteil-1_abschnitt-2_art-8_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="9c536957-0e06-421f-8dc2-b3ca44f35603"
                            eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1">
                     <akn:intro GUID="a9bb0437-c0ae-46f1-89b4-64b2c97f723a"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_intro-1">
                        <akn:p GUID="43e5ae27-e043-437b-b759-083076dd2b03"
                               eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_intro-1_text-1">
                                    Über die Verpflichtung von Vertrauensleuten entscheidet der Behördenleiter oder sein Vertreter. Als
                                    Vertrauensleute dürfen Personen
                                    nicht angeworben und eingesetzt werden, die
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="5930cd49-1552-47ea-a749-2cfa2648880c"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1">
                        <akn:num GUID="63085c34-6c30-44af-93c7-15fc84b763d9"
                                 eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="75da51a4-142f-4d40-9b4f-41f411a2d3ec"
                                     eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="e2187e92-b5dc-463b-81c4-46d8cc878efd"
                                  eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                        nicht voll geschäftsfähig, insbesondere minderjährig sind,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="d63a61e0-75cc-4725-8748-88392b9e3066"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2">
                        <akn:num GUID="4e14b5fc-7621-4c9f-83e2-f375cd4a9e35"
                                 eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="0438aaf6-674e-4145-8610-126c14d34e31"
                                     eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="56819f88-991b-4beb-ad4f-509ad6723637"
                                  eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                        von den Geld- oder Sach*-zuwendungen für die Tätigkeit auf Dauer als alleinige Lebensgrundlage abhängen
                                        würden,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="61c8ea7e-1439-4b38-90d2-2d631b9cd43b"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3">
                        <akn:num GUID="3bd75e11-5cc5-410b-9c0d-60ccb05a6b8a"
                                 eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="7f9f3e6d-6bde-4357-a8b6-a6d04dcb2c61"
                                     eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="fa175239-857c-412d-bd74-fbd31e49f7df"
                                  eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">
                                        an einem Aussteigerprogramm teilnehmen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="d75e9102-850b-466c-aadc-f036a550917d"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4">
                        <akn:num GUID="3c89b703-ee5e-45f1-9259-785b83ba1dca"
                                 eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="f7dcf8f4-638a-42f6-9c23-ff245fcac9fc"
                                     eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="d6383444-e49b-4514-a12b-1944712662c5"
                                  eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">
                                        Mitglied des Europäischen Parlaments, des Deutschen Bundestages, eines Landesparlaments oder Mitarbeiter eines
                                        solchen Mitglieds
                                        sind oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="6d127cd8-536e-42c3-9e05-11dae7208015"
                                eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5">
                        <akn:num GUID="26a89e4f-6855-486b-a983-fdd15180c3ac"
                                 eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:content GUID="8defec14-8a3e-4723-ad3d-8378f34d8296"
                                     eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="39f4001c-d189-44cf-bfd7-ef7addff408f"
                                  eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_inhalt-1_text-1">
                                        im Bundeszentralregister mit einer Verurteilung wegen eines Verbrechens oder zu einer Freiheitsstrafe, deren
                                        Vollstreckung nicht
                                        zur Bewährung ausgesetzt worden ist, eingetragen sind.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="d4443fd7-0e23-4768-b1f2-b5b76e1316ad"
                         eId="hauptteil-1_abschnitt-2_art-9"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="df491653-9cc3-48fc-b747-b45cb02281ff"
                        eId="hauptteil-1_abschnitt-2_art-9_bezeichnung-1">
                         § 10 </akn:num>
               <akn:heading GUID="66cb9ead-1f1c-48b0-9ce1-fa769b4254c1"
                            eId="hauptteil-1_abschnitt-2_art-9_überschrift-1">
                        Speicherung, Veränderung und Nutzung personenbezogener Daten
                    </akn:heading>
               <akn:paragraph GUID="f034034c-e8dd-4e9f-a26f-e02cfe79144c"
                              eId="hauptteil-1_abschnitt-2_art-9_abs-1">
                  <akn:num GUID="bc93c93e-2a67-4480-be82-1eb3857e8b6a"
                           eId="hauptteil-1_abschnitt-2_art-9_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="fb64e61e-0330-419a-804d-d5474eda7056"
                            eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1">
                     <akn:intro GUID="b5c5382e-4c4f-4392-a61b-4875e4df518b"
                                eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_intro-1">
                        <akn:p GUID="c14204a4-8a44-4461-b262-85c3581e8663"
                               eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf zur Erfüllung seiner Aufgaben personenbezogene Daten in Dateien
                                    speichern, verändern und
                                    nutzen, wenn
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="39c6c0e4-7d92-4c25-94a5-0d373c6634af"
                                eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="8df60e15-fa67-4ffa-9f13-bc436c0e8ae4"
                                 eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="e07a0040-c4ff-4f16-a596-93a5f5cd7703"
                                     eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="93b617d7-5543-4780-96ef-c8904ae0d83b"
                                  eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        tatsächliche Anhaltspunkte für Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 vorliegen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="99b712fb-8ff6-4bcf-aedc-834b242fab1c"
                                eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="0a03e61a-5ba5-482b-859d-daed58f1ae65"
                                 eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="154aa409-2e36-43b9-b5fd-1d26c7ba19ad"
                                     eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="9cdf1acf-8ad1-44e2-9b37-73fae0b34a3a"
                                  eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        dies für die Erforschung und Bewertung von Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 erforderlich ist oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="b4920e5c-842a-4790-a5c2-5ddccbd66639"
                                eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="951a087d-f586-43c6-a621-d154b6877ebb"
                                 eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="59e76eca-b730-4e65-a2af-8e460ad223b0"
                                     eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="fdb467cf-e24d-417b-9b39-31df5e6bf437"
                                  eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        das Bundesamt für Verfassungsschutz nach § 3 Abs. 2 tätig wird.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="62726d01-652c-47eb-9e41-0e40fd2e4aa7"
                              eId="hauptteil-1_abschnitt-2_art-9_abs-2">
                  <akn:num GUID="46a9e072-9ddd-4351-bf20-ba896b1742c7"
                           eId="hauptteil-1_abschnitt-2_art-9_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="500dfc23-2c08-487b-929a-755583254a47"
                               eId="hauptteil-1_abschnitt-2_art-9_abs-2_inhalt-1">
                     <akn:p GUID="1031e606-552b-41bb-bb77-58ae999277ab"
                            eId="hauptteil-1_abschnitt-2_art-9_abs-2_inhalt-1_text-1">
                                Unterlagen, die nach Absatz 1 gespeicherte Angaben belegen, dürfen auch gespeichert werden, wenn in ihnen weitere
                                personenbezogene
                                Daten Dritter enthalten sind. Eine Abfrage von Daten Dritter ist unzulässig.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="3da5dd7d-dcf2-4778-8c28-ee192ec73fb2"
                              eId="hauptteil-1_abschnitt-2_art-9_abs-3">
                  <akn:num GUID="72fa5c76-d3fb-495c-95d7-e9015ae1c4ec"
                           eId="hauptteil-1_abschnitt-2_art-9_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="3a1e7842-83e8-4577-a48d-99e81498914f"
                               eId="hauptteil-1_abschnitt-2_art-9_abs-3_inhalt-1">
                     <akn:p GUID="f0c690eb-7fbf-4553-9f90-a71e14d67df3"
                            eId="hauptteil-1_abschnitt-2_art-9_abs-3_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz hat die Speicherungsdauer auf das für seine Aufgabenerfüllung erforderliche Maß zu
                                beschränken.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="4d3ef409-525a-4e45-9c67-dea39e91013d"
                         eId="hauptteil-1_abschnitt-2_art-10"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="d15b7d8a-24cb-4699-b1a7-5f9408bb2f7f"
                        eId="hauptteil-1_abschnitt-2_art-10_bezeichnung-1">
                         § 11 </akn:num>
               <akn:heading GUID="dc88114f-7d25-4f28-a6ab-b21ef00f6aae"
                            eId="hauptteil-1_abschnitt-2_art-10_überschrift-1">
                        Speicherung, Veränderung und Nutzung personenbezogener Daten von Minderjährigen
                    </akn:heading>
               <akn:paragraph GUID="0b45e7dc-634f-4892-9b08-9775bd6aef8d"
                              eId="hauptteil-1_abschnitt-2_art-10_abs-1">
                  <akn:num GUID="5ef203bd-0b7b-42f5-9d25-d0de1462102c"
                           eId="hauptteil-1_abschnitt-2_art-10_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="9e849091-3845-4b0e-b177-e27dada6697b"
                               eId="hauptteil-1_abschnitt-2_art-10_abs-1_inhalt-1">
                     <akn:p GUID="40c511b3-8747-4ab5-bc99-d293013a06e5"
                            eId="hauptteil-1_abschnitt-2_art-10_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf unter den Voraussetzungen des § 10 Daten über Minderjährige vor Vollendung
                                des 14.
                                Lebensjahres in zu ihrer Person geführten Akten nur speichern, verändern und nutzen, wenn tatsächliche Anhaltspunkte
                                dafür bestehen,
                                dass der Minderjährige eine der in § 3 Abs. 1 des Artikel 10-Gesetzes genannten Straftaten plant, begeht oder begangen
                                hat. In Dateien
                                ist eine Speicherung von Daten oder über das Verhalten Minderjähriger vor Vollendung des 14. Lebensjahres nicht
                                zulässig.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="343168cb-4811-4147-93e5-bd6bf505fa8c"
                              eId="hauptteil-1_abschnitt-2_art-10_abs-2">
                  <akn:num GUID="309c7586-1813-4191-93a9-2b21670a83ab"
                           eId="hauptteil-1_abschnitt-2_art-10_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="64ee5b7b-c36d-4c83-9cb4-c8c8740faa13"
                               eId="hauptteil-1_abschnitt-2_art-10_abs-2_inhalt-1">
                     <akn:p GUID="99528542-1dc5-4333-a7f2-1edb25529ba4"
                            eId="hauptteil-1_abschnitt-2_art-10_abs-2_inhalt-1_text-1">
                                In Dateien oder zu ihrer Person geführten Akten gespeicherte Daten über Minderjährige vor Vollendung des 16.
                                Lebensjahres sind
                                spätestens nach zwei Jahren zu löschen, es sei denn, dass weitere Erkenntnisse nach § 3 Absatz 1 angefallen sind. In
                                Dateien oder zu
                                ihrer Person geführten Akten gespeicherte Daten über Minderjährige ab Vollendung des 16. Lebensjahres sind nach zwei
                                Jahren auf die
                                Erforderlichkeit der Speicherung zu überprüfen und spätestens nach fünf Jahren zu löschen, es sei denn, dass nach
                                Eintritt der
                                Volljährigkeit weitere Erkenntnisse nach § 3 Absatz 1 angefallen sind.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="f9649416-6ab4-464d-b139-cd4dd8bafd68"
                         eId="hauptteil-1_abschnitt-2_art-11"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="1b2ac1c5-bce7-47ba-9c5c-b6e5af00009a"
                        eId="hauptteil-1_abschnitt-2_art-11_bezeichnung-1">
                         § 12 </akn:num>
               <akn:heading GUID="1036bc9d-6309-4350-8116-2863211198a0"
                            eId="hauptteil-1_abschnitt-2_art-11_überschrift-1">
                        Berichtigung, Löschung und Sperrung personenbezogener Daten in Dateien
                    </akn:heading>
               <akn:paragraph GUID="8f418f9d-37c6-4aad-b501-a0c6393de891"
                              eId="hauptteil-1_abschnitt-2_art-11_abs-1">
                  <akn:num GUID="68fed43e-a66f-493d-b281-fe82eea9bf1f"
                           eId="hauptteil-1_abschnitt-2_art-11_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="26e7c3a6-b279-43cc-9cb2-d5a271c58495"
                               eId="hauptteil-1_abschnitt-2_art-11_abs-1_inhalt-1">
                     <akn:p GUID="18396bb2-6c57-455d-a55f-7a02932ddad1"
                            eId="hauptteil-1_abschnitt-2_art-11_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz hat die in Dateien gespeicherten personenbezogenen Daten zu berichtigen, wenn sie
                                unrichtig sind.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="b9b62b7c-e068-4c33-939a-a08568dfdfee"
                              eId="hauptteil-1_abschnitt-2_art-11_abs-2">
                  <akn:num GUID="64213851-dea7-4e6d-a10c-e53e9fe9fbc3"
                           eId="hauptteil-1_abschnitt-2_art-11_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="4aee2c42-264c-464a-9062-0d1ff0b4cbdc"
                               eId="hauptteil-1_abschnitt-2_art-11_abs-2_inhalt-1">
                     <akn:p GUID="d4efb572-45ae-4919-ba98-06434e7c01a0"
                            eId="hauptteil-1_abschnitt-2_art-11_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz hat die in Dateien gespeicherten personenbezogenen Daten zu löschen, wenn ihre
                                Speicherung
                                unzulässig war oder ihre Kenntnis für die Aufgabenerfüllung nicht mehr erforderlich ist. Die Löschung unterbleibt,
                                wenn Grund zu der
                                Annahme besteht, daß durch sie schutzwürdige Interessen des Betroffenen beeinträchtigt würden. In diesem Falle sind
                                die Daten zu
                                sperren. Sie dürfen nur noch mit Einwilligung des Betroffenen übermittelt werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="fc691304-20b1-4c91-9bf5-5961c7417104"
                              eId="hauptteil-1_abschnitt-2_art-11_abs-3">
                  <akn:num GUID="32023ed4-5867-4011-a154-54933359057f"
                           eId="hauptteil-1_abschnitt-2_art-11_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="72377b5b-db25-44f8-9975-c94582755570"
                               eId="hauptteil-1_abschnitt-2_art-11_abs-3_inhalt-1">
                     <akn:p GUID="7412a65c-acaf-40c8-9f00-8a65b61cbd63"
                            eId="hauptteil-1_abschnitt-2_art-11_abs-3_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz prüft bei der Einzelfallbearbeitung und nach festgesetzten Fristen, spätestens
                                nach fünf Jahren,
                                ob gespeicherte personenbezogene Daten zu berichtigen oder zu löschen sind. Gespeicherte personenbezogene Daten über
                                Bestrebungen nach
                                § 3 Absatz 1 Nummer 1, 3 und 4 sind spätestens zehn Jahre nach dem Zeitpunkt der letzten gespeicherten relevanten
                                Information zu
                                löschen, es sei denn, die zuständige Abteilungsleitung oder deren Vertretung trifft im Einzelfall ausnahmsweise eine
                                andere
                                Entscheidung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="0fe21bc5-a6b0-4c2a-a85b-6ba628b2f346"
                              eId="hauptteil-1_abschnitt-2_art-11_abs-4">
                  <akn:num GUID="3be9b05c-8cc1-43cf-a100-18d21c969fbd"
                           eId="hauptteil-1_abschnitt-2_art-11_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="378c0466-b43a-4019-981e-aecd28fae326"
                               eId="hauptteil-1_abschnitt-2_art-11_abs-4_inhalt-1">
                     <akn:p GUID="6d26baa3-65d7-44dd-b039-dc9fe007e8a6"
                            eId="hauptteil-1_abschnitt-2_art-11_abs-4_inhalt-1_text-1">
                                Personenbezogene Daten, die ausschließlich zu Zwecken der Datenschutzkontrolle, der Datensicherung oder zur
                                Sicherstellung eines
                                ordnungsgemäßen Betriebes einer Datenverarbeitungsanlage gespeichert werden, dürfen nur für diese Zwecke verwendet
                                werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="42d151a8-c6ae-48c6-885c-29787675f53e"
                         eId="hauptteil-1_abschnitt-2_art-12"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="321f5f57-8fb6-4299-abdc-dd241361648c"
                        eId="hauptteil-1_abschnitt-2_art-12_bezeichnung-1">
                         § 13 </akn:num>
               <akn:heading GUID="1cccca99-1ef1-47ee-9492-fc1d84386462"
                            eId="hauptteil-1_abschnitt-2_art-12_überschrift-1">
                        Verwendung und Berichtigung personenbezogener Daten in Akten
                    </akn:heading>
               <akn:paragraph GUID="834b3159-380a-48e2-b9e6-e1afabd15494"
                              eId="hauptteil-1_abschnitt-2_art-12_abs-1">
                  <akn:num GUID="114d5df1-1450-4666-bb1a-b4a06f9b7724"
                           eId="hauptteil-1_abschnitt-2_art-12_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="1856d2ca-1288-4a29-b052-453831616de8"
                               eId="hauptteil-1_abschnitt-2_art-12_abs-1_inhalt-1">
                     <akn:p GUID="41974ff9-2f29-40b3-9bfd-055dc09fc0da"
                            eId="hauptteil-1_abschnitt-2_art-12_abs-1_inhalt-1_text-1">
                                Stellt das Bundesamt für Verfassungsschutz fest, daß in Akten gespeicherte personenbezogene Daten unrichtig sind oder
                                wird ihre
                                Richtigkeit von dem Betroffenen bestritten, so ist dies in der Akte zu vermerken oder auf sonstige Weise festzuhalten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="0511a4b6-967a-402d-9d4c-5d9ff121d671"
                              eId="hauptteil-1_abschnitt-2_art-12_abs-2">
                  <akn:num GUID="a9c5237b-6edd-41ae-a6cd-1e18b756220f"
                           eId="hauptteil-1_abschnitt-2_art-12_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="ac66b28d-8a99-4060-ba0f-2c6669201138"
                               eId="hauptteil-1_abschnitt-2_art-12_abs-2_inhalt-1">
                     <akn:p GUID="7e10b617-5910-42d0-82e1-e666c452921c"
                            eId="hauptteil-1_abschnitt-2_art-12_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz hat die Verarbeitung personenbezogener Daten einzuschränken, wenn es im Einzelfall
                                feststellt,
                                dass ohne die Einschränkung schutzwürdige Interessen des Betroffenen beeinträchtigt würden und die Daten für seine
                                künftige
                                Aufgabenerfüllung nicht mehr erforderlich sind. Verarbeitungseingeschränkte Daten sind mit einem entsprechenden
                                Vermerk zu versehen;
                                sie dürfen nicht mehr genutzt oder übermittelt werden. Eine Aufhebung der Einschränkung ist möglich, wenn ihre
                                Voraussetzungen
                                nachträglich entfallen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="430fe50b-9da2-4579-83f1-80bd01bbd630"
                              eId="hauptteil-1_abschnitt-2_art-12_abs-3">
                  <akn:num GUID="17b66807-5bd4-4cc3-94b6-5680ba5ef71b"
                           eId="hauptteil-1_abschnitt-2_art-12_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="71a2dde4-38cb-47cc-96ab-04dd0889b38c"
                               eId="hauptteil-1_abschnitt-2_art-12_abs-3_inhalt-1">
                     <akn:p GUID="52714ba6-0474-44f8-8e47-85e5f62de2f3"
                            eId="hauptteil-1_abschnitt-2_art-12_abs-3_inhalt-1_text-1">
                                Eine Akte ist zu vernichten, wenn sie insgesamt zur Erfüllung der Aufgaben des Bundesamtes für Verfassungsschutz nicht
                                oder nicht mehr
                                erforderlich ist. Die Erforderlichkeit ist bei der Einzelfallbearbeitung und nach festgesetzten Fristen, spätestens
                                nach fünf Jahren,
                                zu prüfen. Für die Vernichtung einer Akte, die zu einer Person im Sinne des § 10 Absatz 1 Nummer 1 geführt wird, gilt
                                § 12 Absatz 3
                                Satz 2 entsprechend. Eine Vernichtung unterbleibt, wenn Grund zu der Annahme besteht, dass durch sie schutzwürdige
                                Interessen des
                                Betroffenen beeinträchtigt würden. In diesem Fall ist die Verarbeitung der in der Akte gespeicherten personenbezogenen
                                Daten
                                einzuschränken und mit einem entsprechenden Vermerk zu versehen. Sie dürfen nur für die Interessen nach Satz 4
                                verarbeitet werden oder
                                wenn es zur Abwehr einer erheblichen Gefahr unerlässlich ist. Eine Vernichtung der Akte erfolgt nicht, wenn sie nach
                                den Vorschriften
                                des Bundesarchivgesetzes dem Bundesarchiv zur Übernahme anzubieten und zu übergeben ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="16fcf74a-ed67-4acd-a203-755644f11f81"
                              eId="hauptteil-1_abschnitt-2_art-12_abs-4">
                  <akn:num GUID="63d45ea4-55d3-440d-8efb-25928edc006a"
                           eId="hauptteil-1_abschnitt-2_art-12_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="87ff2c76-def4-4c71-b419-fbf215c2488e"
                               eId="hauptteil-1_abschnitt-2_art-12_abs-4_inhalt-1">
                     <akn:p GUID="88265645-538e-4d2a-8a77-2f6721b061bd"
                            eId="hauptteil-1_abschnitt-2_art-12_abs-4_inhalt-1_text-1">Akten oder Auszüge aus Akten dürfen auch in elektronischer Form geführt werden. Insoweit kommen die Regelungen über die Verwendung und Berichtigung personenbezogener Daten in Akten zur Anwendung. Eine Abfrage personenbezogener Daten ist insoweit nur zulässig, wenn die Voraussetzungen des § 10 Absatz 1 Nummer 1 oder Nummer 2 vorliegen und die Person das 14. Lebensjahr vollendet hat. Der automatisierte Abgleich dieser personenbezogenen Daten ist nur beschränkt auf Akten eng umgrenzter Anwendungsgebiete zulässig. Bei jeder Abfrage sind für Zwecke der Datenschutzkontrolle der Zeitpunkt, die Angaben, die die Feststellung der abgefragten Daten ermöglichen, sowie Angaben zur Feststellung des Abfragenden zu protokollieren. Die protokollierten Daten dürfen nur für Zwecke der Datenschutzkontrolle, der Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebs der Datenverarbeitungsanlage verwendet werden. Die Protokolldaten sind am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt, zu löschen.</akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="53aa8c64-61f0-49de-9d88-5c093494f452"
                         eId="hauptteil-1_abschnitt-2_art-13"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="075bb256-deab-40e0-a2d8-008b5834c674"
                        eId="hauptteil-1_abschnitt-2_art-13_bezeichnung-1">
                         § 14 </akn:num>
               <akn:heading GUID="3503a7b3-b18a-491c-b67e-aef5cee02220"
                            eId="hauptteil-1_abschnitt-2_art-13_überschrift-1">
                        Dateianordnungen
                    </akn:heading>
               <akn:paragraph GUID="a3fcec22-9057-4164-bcb5-498a0ffa2d46"
                              eId="hauptteil-1_abschnitt-2_art-13_abs-1">
                  <akn:num GUID="15b12143-42ed-4412-9d6f-ead64d863fc8"
                           eId="hauptteil-1_abschnitt-2_art-13_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="92bf02fe-25d6-4fb4-a725-f0cffb05febb"
                            eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1">
                     <akn:intro GUID="e49bc459-3762-4f82-94ef-dbbd65f2f2ff"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_intro-1">
                        <akn:p GUID="3bf663fb-94af-4696-8041-07a035e55f5e"
                               eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_intro-1_text-1">
                                    Für jede automatisierte Datei beim Bundesamt für Verfassungsschutz nach § 6 oder § 10 sind in einer
                                    Dateianordnung, die der
                                    Zustimmung des Bundesministeriums des Innern, für Bau und Heimat bedarf, festzulegen:
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="b815238a-f3fe-4a2a-a4f9-f9f2f1dd09b4"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="c04f97a8-4ea3-4a06-8de4-66505e1dc856"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="a72312f6-5584-4ac6-9114-5037fa3b3564"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="31f76228-9d8c-4978-b98d-4c3bfcad922c"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Bezeichnung der Datei,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a165b1a8-0ba7-4871-a5e2-c90d0b5fc255"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="059fa93e-416a-4ddf-9718-a449b619ec40"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="01061723-9009-4b30-b1bd-10569be8b3ac"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="4193b47e-5646-4d7e-bbbf-46c3e5054c9d"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Zweck der Datei,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="f785778b-637d-42fa-881e-475fa5aaa923"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="8611c6b4-712c-4dbb-90f2-25ed4e970d61"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="b3e59276-2824-4631-b203-1ad822d2ecbb"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="3a63a671-6c25-475b-8a79-f7d173a77478"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Voraussetzungen der Speicherung, Übermittlung und Nutzung (betroffener Personenkreis, Arten der Daten),
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="db980a54-4328-4470-80df-4cd2d4c1dc99"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4">
                        <akn:num GUID="1d7c7465-a9a5-4422-8cba-60b24ea8e039"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="90ce5cae-96ae-495c-a5d4-a15a8f39abfa"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="a79a705c-e034-4ccd-b4aa-c722bbe8878d"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                        Anlieferung oder Eingabe,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="8995d279-bc2e-4fe0-b9e8-5ab0cee8d0a2"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5">
                        <akn:num GUID="42544b10-3546-43a0-ac52-e1ebedb6f9f7"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:content GUID="168ec17f-67ee-457d-aaa2-bf8803c391de"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="36da0cae-e36a-4547-b508-7a50031b97ee"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">
                                        Zugangsberechtigung,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="13a99deb-e34c-4703-bf7e-f806311d6557"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6">
                        <akn:num GUID="3ede2906-626d-405e-9957-f15c0d7ed518"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_bezeichnung-1">
                                     6. </akn:num>
                        <akn:content GUID="65c55f10-289e-426b-a167-eb3a742be04b"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_inhalt-1">
                           <akn:p GUID="cd06e65c-3583-4945-877e-a721efced546"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_inhalt-1_text-1">
                                        Überprüfungsfristen, Speicherungsdauer,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="94ad1f2e-dffc-4bfa-8e30-18228da11204"
                                eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7">
                        <akn:num GUID="e96c3b5b-2f85-4cfb-9d97-0f5e751b0765"
                                 eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_bezeichnung-1">
                                     7. </akn:num>
                        <akn:content GUID="974f97a7-33a5-47ab-af69-403f9c8b959a"
                                     eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_inhalt-1">
                           <akn:p GUID="19cc6ee0-40b2-4221-8604-7121ce57e3fd"
                                  eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_inhalt-1_text-1">
                                        Protokollierung.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="7e90c53b-5e94-420b-ad6c-bd373fa2240f"
                              eId="hauptteil-1_abschnitt-2_art-13_abs-2">
                  <akn:num GUID="ecb0fcd9-1165-48de-a555-4d426765b2b7"
                           eId="hauptteil-1_abschnitt-2_art-13_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="3ffd6a48-fbed-4e4c-bf9f-5faa039d69c9"
                               eId="hauptteil-1_abschnitt-2_art-13_abs-2_inhalt-1">
                     <akn:p GUID="8e512734-deb6-44c3-ba1c-680dab3a097d"
                            eId="hauptteil-1_abschnitt-2_art-13_abs-2_inhalt-1_text-1">
                                Die Speicherung personenbezogener Daten ist auf das erforderliche Maß zu beschränken. In angemessenen Abständen ist
                                die Notwendigkeit
                                der Weiterführung oder Änderung der Dateien zu überprüfen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="ae18b039-75c4-4b52-9dd4-3db06660a13d"
                              eId="hauptteil-1_abschnitt-2_art-13_abs-3">
                  <akn:num GUID="f1770ae1-b492-4462-b986-7afa70109b4e"
                           eId="hauptteil-1_abschnitt-2_art-13_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="bee7af1c-371b-48aa-9a40-2ba17f1159dd"
                               eId="hauptteil-1_abschnitt-2_art-13_abs-3_inhalt-1">
                     <akn:p GUID="4b8ff1d6-5d93-4742-9943-dc3dcd153722"
                            eId="hauptteil-1_abschnitt-2_art-13_abs-3_inhalt-1_text-1">
                                Ist im Hinblick auf die Dringlichkeit der Aufgabenerfüllung die vorherige Mitwirkung der in Absatz 1 genannten Stellen
                                nicht möglich,
                                so kann das Bundesamt für Verfassungsschutz eine Sofortanordnung treffen. Das Verfahren nach Absatz 1 ist unverzüglich
                                nachzuholen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="a08f4b61-e345-47fe-9adf-3524896ca464"
                         eId="hauptteil-1_abschnitt-2_art-14"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="091cdd36-497e-4eb9-a5e2-4eb2cb25c635"
                        eId="hauptteil-1_abschnitt-2_art-14_bezeichnung-1">
                         § 15 </akn:num>
               <akn:heading GUID="1de4211d-22e2-451e-95ea-794b40dd81eb"
                            eId="hauptteil-1_abschnitt-2_art-14_überschrift-1">
                        Auskunft an den Betroffenen
                    </akn:heading>
               <akn:paragraph GUID="d8797c73-e5af-4589-8d85-d3a90adb9c86"
                              eId="hauptteil-1_abschnitt-2_art-14_abs-1">
                  <akn:num GUID="514a19d9-ad1b-4749-85ed-6f0ffa96e5ee"
                           eId="hauptteil-1_abschnitt-2_art-14_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="5efa619a-80ac-462a-a752-d01549224d68"
                               eId="hauptteil-1_abschnitt-2_art-14_abs-1_inhalt-1">
                     <akn:p GUID="8da711b2-a746-438a-a6e7-ae4e6b1206c9"
                            eId="hauptteil-1_abschnitt-2_art-14_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz erteilt dem Betroffenen über zu seiner Person gespeicherte Daten auf Antrag
                                unentgeltlich
                                Auskunft, soweit er hierzu auf einen konkreten Sachverhalt hinweist und ein besonderes Interesse an einer Auskunft
                                darlegt. Zu
                                personenbezogenen Daten in Akten erstreckt sich die Auskunft auf alle Daten, die über eine Speicherung gemäß § 10
                                Absatz 1 auffindbar
                                sind.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="ba33deb5-634f-4f12-8354-d76bbdca5747"
                              eId="hauptteil-1_abschnitt-2_art-14_abs-2">
                  <akn:num GUID="27c3c01e-4dd6-4698-9433-cbe4c72e1888"
                           eId="hauptteil-1_abschnitt-2_art-14_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:list GUID="880888ca-8b0e-4550-9ca5-85d143314bc1"
                            eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1">
                     <akn:intro GUID="7f88ce8c-bb17-45f2-923e-38ce7bda281a"
                                eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_intro-1">
                        <akn:p GUID="518d32ba-dd03-4327-873a-0ad2da976e3c"
                               eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_intro-1_text-1">
                                    Die Auskunftserteilung unterbleibt, soweit
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="cf3529ea-28f0-4250-be87-765daf5d9955"
                                eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1">
                        <akn:num GUID="bb76a4db-a76d-49fd-ab7f-1e77394e57e0"
                                 eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="b20a260c-9ddb-4e5f-8f39-2d9e1ef3f62a"
                                     eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="1f851e86-3334-4447-bb74-37f3968c78b1"
                                  eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">
                                        eine Gefährdung der Aufgabenerfüllung durch die Auskunftserteilung zu besorgen ist,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="0ee873bf-f014-482e-81e1-b2eb8f2f3803"
                                eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2">
                        <akn:num GUID="4894107b-d8fb-4fc3-8d9e-a798d98885a9"
                                 eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="0957a2d7-0f60-49c7-ae0e-35858b78ee94"
                                     eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="4a91cb5f-d97d-4c47-8f5c-8fcccfd30ee8"
                                  eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">
                                        durch die Auskunftserteilung Quellen gefährdet sein können oder die Ausforschung des Erkenntnisstandes oder
                                        der Arbeitsweise des
                                        Bundesamtes für Verfassungsschutz zu befürchten ist,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="a6ee5449-87f3-4b42-a527-457cbd19eace"
                                eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3">
                        <akn:num GUID="3c1b1cb6-4711-42ad-aa9a-9d40d5528773"
                                 eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="3237590e-b722-45f0-8854-f6f793645c74"
                                     eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="7263be10-91e4-476a-94f2-b8e747342871"
                                  eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">
                                        die Auskunft die öffentliche Sicherheit gefährden oder sonst dem Wohl des Bundes oder eines Landes Nachteile
                                        bereiten würde oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="62d1c9dd-33b7-49da-b580-2ad80317773d"
                                eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4">
                        <akn:num GUID="555bfbcc-ddaa-4bbb-9689-1ad3676bbf09"
                                 eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="35c40c00-0db3-483a-b68e-36d0b2208d95"
                                     eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="50743d02-f558-4d6d-90bb-8d006ff6a4fa"
                                  eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">
                                        die Daten oder die Tatsache der Speicherung nach einer Rechtsvorschrift oder ihrem Wesen nach, insbesondere
                                        wegen der
                                        überwiegenden berechtigten Interessen eines Dritten, geheimgehalten werden müssen.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="564441d8-f473-414c-bacd-333595be701b"
                              eId="hauptteil-1_abschnitt-2_art-14_abs-3">
                  <akn:num GUID="9b552cdd-0722-43dd-85d4-a5fd634fd82a"
                           eId="hauptteil-1_abschnitt-2_art-14_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="b5323b47-6ab0-4b4d-8910-505ff7777542"
                               eId="hauptteil-1_abschnitt-2_art-14_abs-3_inhalt-1">
                     <akn:p GUID="eb4b215d-3f2a-4f07-a755-1a02d21d1324"
                            eId="hauptteil-1_abschnitt-2_art-14_abs-3_inhalt-1_text-1">
                                Die Auskunftsverpflichtung erstreckt sich nicht auf die Herkunft der Daten und die Empfänger von Übermittlungen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="b65414b7-b98f-48a3-99d2-101c58416699"
                              eId="hauptteil-1_abschnitt-2_art-14_abs-4">
                  <akn:num GUID="c7c622d7-ffd1-4d5b-848d-da4657295529"
                           eId="hauptteil-1_abschnitt-2_art-14_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="8e2b2a57-b913-4c36-a861-9c636beb1695"
                               eId="hauptteil-1_abschnitt-2_art-14_abs-4_inhalt-1">
                     <akn:p GUID="07ebb6fc-17e9-4fda-9056-5564957ec8cd"
                            eId="hauptteil-1_abschnitt-2_art-14_abs-4_inhalt-1_text-1">
                                Die Ablehnung der Auskunftserteilung bedarf keiner Begründung, soweit dadurch der Zweck der Auskunftsverweigerung
                                gefährdet würde. Die
                                Gründe der Auskunftsverweigerung sind aktenkundig zu machen. Wird die Auskunftserteilung abgelehnt, ist der Betroffene
                                auf die
                                Rechtsgrundlage für das Fehlen der Begründung und darauf hinzuweisen, daß er sich an den Bundesbeauftragten für den
                                Datenschutz wenden
                                kann. Dem Bundesbeauftragten für den Datenschutz ist auf sein Verlangen Auskunft zu erteilen, soweit nicht das
                                Bundesministerium des
                                Innern, für Bau und Heimat im Einzelfall feststellt, daß dadurch die Sicherheit des Bundes oder eines Landes gefährdet
                                würde.
                                Mitteilungen des Bundesbeauftragten an den Betroffenen dürfen keine Rückschlüsse auf den Erkenntnisstand des
                                Bundesamtes für
                                Verfassungsschutz zulassen, sofern es nicht einer weitergehenden Auskunft zustimmt.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="d0ce486c-9aed-4bf1-8130-332032b673f0"
                         eId="hauptteil-1_abschnitt-2_art-15"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="a375b60d-decf-4859-b807-1ad594d3e9f3"
                        eId="hauptteil-1_abschnitt-2_art-15_bezeichnung-1">
                         § 16 </akn:num>
               <akn:heading GUID="49f3c389-97bf-4aa6-827f-58e73efd7ea5"
                            eId="hauptteil-1_abschnitt-2_art-15_überschrift-1">
                        Verfassungsschutz durch Aufklärung der Öffentlichkeit
                    </akn:heading>
               <akn:paragraph GUID="536eb567-f7c7-4e48-85b4-e207e01960bd"
                              eId="hauptteil-1_abschnitt-2_art-15_abs-1">
                  <akn:num GUID="affcce3d-08e5-4d0e-b9bf-af7755b670ca"
                           eId="hauptteil-1_abschnitt-2_art-15_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="09ae7d3f-95b1-4937-8b34-460185660321"
                               eId="hauptteil-1_abschnitt-2_art-15_abs-1_inhalt-1">
                     <akn:p GUID="1608609b-ba32-42d0-ba6e-e6d5adf3a2bc"
                            eId="hauptteil-1_abschnitt-2_art-15_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz informiert die Öffentlichkeit über Bestrebungen und Tätigkeiten nach § 3 Absatz 1,
                                soweit
                                hinreichend gewichtige tatsächliche Anhaltspunkte hierfür vorliegen, sowie über präventiven Wirtschaftsschutz.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="5f9263a5-d94e-4ace-b1ed-6f7377d2af40"
                              eId="hauptteil-1_abschnitt-2_art-15_abs-2">
                  <akn:num GUID="12104e5a-f562-4043-9b6d-3f8fc65c5209"
                           eId="hauptteil-1_abschnitt-2_art-15_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="b53ef91a-519f-4708-b7a0-2e247a1865eb"
                               eId="hauptteil-1_abschnitt-2_art-15_abs-2_inhalt-1">
                     <akn:p GUID="a8403426-17cd-4ecf-9899-59cb05a60952"
                            eId="hauptteil-1_abschnitt-2_art-15_abs-2_inhalt-1_text-1">
                                Das Bundesministerium des Innern, für Bau und Heimat informiert die Öffentlichkeit über Bestrebungen und Tätigkeiten
                                nach § 3 Absatz
                                1, soweit hinreichend gewichtige tatsächliche Anhaltspunkte hierfür vorliegen, mindestens einmal jährlich in einem
                                zusammenfassenden
                                Bericht insbesondere zu aktuellen Entwicklungen. In dem Bericht sind die Zuschüsse des Bundeshaushaltes an das
                                Bundesamt für
                                Verfassungsschutz und den Militärischen Abschirmdienst sowie die jeweilige Gesamtzahl ihrer Bediensteten anzugeben.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="f77331af-fd96-4322-aa7e-a3d913b2e6a8"
                              eId="hauptteil-1_abschnitt-2_art-15_abs-3">
                  <akn:num GUID="bb239fc9-dd7f-4309-b5af-cb73c354b952"
                           eId="hauptteil-1_abschnitt-2_art-15_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="d9b8cd58-50e8-4170-ab8c-8af74db01f21"
                               eId="hauptteil-1_abschnitt-2_art-15_abs-3_inhalt-1">
                     <akn:p GUID="dd33b4fa-96ab-49c3-a985-5b59c1bab97a"
                            eId="hauptteil-1_abschnitt-2_art-15_abs-3_inhalt-1_text-1">
                                Bei der Information nach den Absätzen 1 und 2 dürfen auch personenbezogene Daten bekanntgegeben werden, wenn die
                                Bekanntgabe für das
                                Verständnis des Zusammenhanges oder der Darstellung von Organisationen oder unorganisierten Gruppierungen erforderlich
                                ist und die
                                Interessen der Allgemeinheit das schutzwürdige Interesse des Betroffenen überwiegen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:section>
         <akn:section GUID="67c9c1a6-d54f-4827-b829-a61ffff19316"
                      eId="hauptteil-1_abschnitt-3">
            <akn:num GUID="3a26ca4d-e658-4ef0-9506-a3caa71edee7"
                     eId="hauptteil-1_abschnitt-3_bezeichnung-1">
                    Dritter Abschnitt </akn:num>
            <akn:heading GUID="c42dd268-719b-40d0-8cca-53209fb38e53"
                         eId="hauptteil-1_abschnitt-3_überschrift-1">
                    Übermittlungsvorschriften
                </akn:heading>
            <akn:article GUID="cb58e404-3bd9-462e-aa16-a191f32aae4a"
                         eId="hauptteil-1_abschnitt-3_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="181fca1a-a319-40c8-a2e5-ece2f1ea0db1"
                        eId="hauptteil-1_abschnitt-3_art-1_bezeichnung-1">
                         § 17 </akn:num>
               <akn:heading GUID="f4c3e374-2be9-4ccd-b827-02958927c76d"
                            eId="hauptteil-1_abschnitt-3_art-1_überschrift-1">
                        Zulässigkeit von Ersuchen
                    </akn:heading>
               <akn:paragraph GUID="484a4587-cb09-4250-8a8d-e2b3780357ee"
                              eId="hauptteil-1_abschnitt-3_art-1_abs-1">
                  <akn:num GUID="c6eef02c-c3bb-4a43-aa47-8dd39960fbfb"
                           eId="hauptteil-1_abschnitt-3_art-1_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="20b43dd4-b28d-4d6c-b905-616c0af839cc"
                               eId="hauptteil-1_abschnitt-3_art-1_abs-1_inhalt-1">
                     <akn:p GUID="3b694f45-32e7-4a2e-81ff-022b0a64f1c0"
                            eId="hauptteil-1_abschnitt-3_art-1_abs-1_inhalt-1_text-1">
                                Wird nach den Bestimmungen dieses Abschnittes um Übermittlung von personenbezogenen Daten ersucht, dürfen nur die
                                Daten übermittelt
                                werden, die bei der ersuchten Behörde bekannt sind oder aus allgemein zugänglichen Quellen entnommen werden können.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="60f3beb2-4ca6-41a6-a20a-5be3c379ee88"
                              eId="hauptteil-1_abschnitt-3_art-1_abs-2">
                  <akn:num GUID="618a6ea8-e2bd-41ac-a413-d2368b6ccbc2"
                           eId="hauptteil-1_abschnitt-3_art-1_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="110cc833-eaf4-4038-b113-d1fbc2eed3f5"
                               eId="hauptteil-1_abschnitt-3_art-1_abs-2_inhalt-1">
                     <akn:p GUID="2edb610a-0577-4c0f-a95e-6c8ceb6b48cf"
                            eId="hauptteil-1_abschnitt-3_art-1_abs-2_inhalt-1_text-1">
                                Absatz 1 gilt nicht für besondere Ersuchen der Verfassungsschutzbehörden, des Militärischen Abschirmdienstes und des
                                Bundesnachrichtendienstes um solche Daten, die bei der Wahrnehmung grenzpolizeilicher Aufgaben bekannt werden. Die
                                Zulässigkeit dieser
                                besonderen Ersuchen und ihre Erledigung regelt das Bundesministerium des Innern im Benehmen mit dem Bundeskanzleramt
                                und dem
                                Bundesministerium der Verteidigung in einer Dienstanweisung. Es unterrichtet das Parlamentarische Kontrollgremium über
                                ihren Erlaß und
                                erforderliche Änderungen. Satz 2 und 3 gilt nicht für die besonderen Ersuchen zwischen Behörden desselben
                                Bundeslandes.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="74f42245-94d6-4982-bf04-57c6c33286dd"
                              eId="hauptteil-1_abschnitt-3_art-1_abs-3">
                  <akn:num GUID="bc29e3f4-0cd4-4fee-9e24-6cd721371657"
                           eId="hauptteil-1_abschnitt-3_art-1_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="ebc45035-2ed4-4036-a133-271cba0e686f"
                               eId="hauptteil-1_abschnitt-3_art-1_abs-3_inhalt-1">
                     <akn:p GUID="e66f40f7-8957-4f47-9f55-f7ef683693fe"
                            eId="hauptteil-1_abschnitt-3_art-1_abs-3_inhalt-1_text-1">
                                (weggefallen)
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="c06a1656-865d-45eb-a984-b40728c03016"
                         eId="hauptteil-1_abschnitt-3_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="3e7e9886-0ab9-4065-b81a-ab9e0d1a7943"
                        eId="hauptteil-1_abschnitt-3_art-2_bezeichnung-1">
                         § 18 </akn:num>
               <akn:heading GUID="3ea384a1-ce91-4abb-b127-7ca71bf36f88"
                            eId="hauptteil-1_abschnitt-3_art-2_überschrift-1">
                        Übermittlung von Informationen an die Verfassungsschutzbehörden
                    </akn:heading>
               <akn:paragraph GUID="a8ad6fdd-a9db-4341-9ac8-ddf2c2bd4a8f"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-1">
                  <akn:num GUID="e0ed3e20-bdf5-4d6c-af11-7b86fe3f0cd2"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="a59c0e91-62a5-4f09-8371-c5a082fe5723"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-1_inhalt-1">
                     <akn:p GUID="899c4c03-6100-4304-8b7b-de3c8d978b19"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-1_inhalt-1_text-1">
                                Die Behörden des Bundes, der bundesunmittelbaren juristischen Personen des öffentlichen Rechts, die
                                Staatsanwaltschaften und,
                                vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die Behörden des Zollfahndungsdienstes
                                sowie andere
                                Zolldienststellen, soweit diese Aufgaben nach dem Bundesgrenzschutzgesetz wahrnehmen, unterrichten von sich aus das
                                Bundesamt für
                                Verfassungsschutz oder die Verfassungsschutzbehörde des Landes über die ihnen bekanntgewordenen Tatsachen, die
                                sicherheitsgefährdende
                                oder geheimdienstliche Tätigkeiten für eine fremde Macht oder Bestrebungen im Geltungsbereich dieses Gesetzes erkennen
                                lassen, die
                                durch Anwendung von Gewalt oder darauf gerichtete Vorbereitungshandlungen gegen die in § 3 Abs. 1 Nr. 1 und 3
                                genannten Schutzgüter
                                gerichtet sind. Über Satz 1 hinausgehende Unterrichtungspflichten nach dem Gesetz über den Militärischen
                                Abschirmdienst oder dem
                                Gesetz über den Bundesnachrichtendienst bleiben unberührt. Auf die Übermittlung von Informationen zwischen Behörden
                                desselben
                                Bundeslandes findet Satz 1 keine Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="e804ff8a-d5d7-4bdc-a118-4c7efd966285"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-2">
                  <akn:num GUID="0b54b3c0-a652-483c-a28d-a4e13e607b9c"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="fd81ad4f-7fc5-4018-a944-2a124f626203"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-2_inhalt-1">
                     <akn:p GUID="ef5ff6c9-361a-4176-bb09-cc0fcf19337b"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-2_inhalt-1_text-1">
                                Die Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die
                                Behörden des
                                Zollfahndungsdienstes sowie andere Zolldienststellen, soweit diese Aufgaben nach dem Bundesgrenzschutzgesetz
                                wahrnehmen, und der
                                Bundesnachrichtendienst dürfen darüber hinaus von sich aus dem Bundesamt für Verfassungsschutz oder der
                                Verfassungsschutzbehörde des
                                Landes auch alle anderen ihnen bekanntgewordenen Informationen einschließlich personenbezogener Daten über
                                Bestrebungen nach § 3 Abs.
                                1 übermitteln, wenn tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung für die Erfüllung der Aufgaben der
                                Verfassungsschutzbehörde erforderlich ist. Absatz 1 Satz 3 findet Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="335663a7-c26a-43f9-b149-fc974d8ffc0d"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-3">
                  <akn:num GUID="acdb2cd6-e94d-445e-ab60-6e8335e0e0a6"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="48a4cb26-41b6-4d2c-9b7b-96444693fb85"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1">
                     <akn:intro GUID="7b29aba8-cf5d-49d3-9a76-41e2b5a849b3"
                                eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_intro-1">
                        <akn:p GUID="00056980-f61f-49c9-9aa4-a5285a0e7603"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf zur Erfüllung seiner Aufgaben die Staatsanwaltschaften und, vorbehaltlich
                                    der
                                    staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien sowie andere Behörden um Übermittlung der zur
                                    Erfüllung seiner Aufgaben
                                    erforderlichen Informationen einschließlich personenbezogener Daten ersuchen, wenn sie nicht aus allgemein
                                    zugänglichen Quellen oder
                                    nur mit übermäßigem Aufwand oder nur durch eine den Betroffenen stärker belastende Maßnahme erhoben werden können.
                                    Unter den
                                    gleichen Voraussetzungen dürfen Verfassungsschutzbehörden der Länder
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="2155634a-6b60-4f4a-b018-0ddfc64700a6"
                                eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="b485cd15-d15b-43ea-9d69-6a7fbc8ffe9c"
                                 eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="ccdb2109-384e-4e84-b621-557349e51775"
                                     eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="a1cb9363-1737-4ae9-a611-3fab507c2956"
                                  eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Behörden des Bundes und der bundesunmittelbaren juristischen Personen des öffentlichen Rechts,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="0f389dfa-8d9b-400b-97bc-8977c38a8cd3"
                                eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="909d6711-136a-449b-9ca3-383de1e6c7ae"
                                 eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="3272a4a4-e40e-4ac7-8416-0bc0601b82bd"
                                     eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="48c5584c-02f3-457b-9e3d-1b72a2a753a7"
                                  eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, Polizeien des
                                        Bundes und anderer Länder
                                        um die Übermittlung solcher Informationen ersuchen.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="8e23461b-734d-4773-8bff-142f50c0af1f"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-4">
                  <akn:num GUID="ae657926-4ac7-4024-a0c6-45610467cfcf"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="a8164c7c-3568-442d-bdc1-dd42b3a147cf"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-4_inhalt-1">
                     <akn:p GUID="df4534a7-ceb7-4574-80d3-9e9437653f4e"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-4_inhalt-1_text-1">
                                Würde durch die Übermittlung nach Absatz 3 Satz 1 der Zweck der Maßnahme gefährdet oder der Betroffene
                                unverhältnismäßig
                                beeinträchtigt, darf das Bundesamt für Verfassungsschutz bei der Wahrnehmung der Aufgaben nach § 3 Abs. 1 Nr. 2 und 3
                                sowie bei der
                                Beobachtung terroristischer Bestrebungen amtliche Register einsehen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="e2405704-f351-4282-be4e-cbc5e36218c5"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-5">
                  <akn:num GUID="01ca7cf3-d50d-4221-b01f-4c2396ce3923"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="06ff6fd7-fd9f-49d1-bad1-57d05bdab422"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-5_inhalt-1">
                     <akn:p GUID="4d2f53ba-ca68-49b8-aef8-bc98d1272a6e"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-5_inhalt-1_text-1">Die Ersuchen nach Absatz 3 sind aktenkundig zu machen. Über die Einsichtnahme nach Absatz 4 hat das Bundesamt für Verfassungsschutz einen Nachweis zu führen, aus dem der Zweck und die Veranlassung, die ersuchte Behörde und die Aktenfundstelle hervorgehen; die Nachweise sind gesondert aufzubewahren, gegen unberechtigten Zugriff zu sichern und am Ende des Kalenderjahres, das dem Jahr ihrer Erstellung folgt, zu vernichten.</akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="6f45d666-7c50-431a-9f19-5d4f7ee7aea8"
                              eId="hauptteil-1_abschnitt-3_art-2_abs-6">
                  <akn:num GUID="1c09db9b-8ccb-4971-a697-4874a4381808"
                           eId="hauptteil-1_abschnitt-3_art-2_abs-6_bezeichnung-1">
                             (6) </akn:num>
                  <akn:content GUID="6f66cc77-2973-4b42-8cd7-c1363970bf93"
                               eId="hauptteil-1_abschnitt-3_art-2_abs-6_inhalt-1">
                     <akn:p GUID="b4163cf7-91da-49c8-a03a-f4318b047991"
                            eId="hauptteil-1_abschnitt-3_art-2_abs-6_inhalt-1_text-1">
                                Die Übermittlung personenbezogener Daten, die auf Grund einer Maßnahme nach § 100a der Strafprozeßordnung
                                bekanntgeworden sind, ist
                                nach den Vorschriften der Absätze 1, 2 und 3 nur zulässig, wenn tatsächliche Anhaltspunkte dafür bestehen, daß jemand
                                eine der in § 3
                                des Artikel 10-Gesetzes genannten Straftaten plant, begeht oder begangen hat. Auf die einer Verfassungsschutzbehörde
                                nach Satz 1
                                übermittelten Kenntnisse und Unterlagen findet § 4 Abs. 1 und 4 des Artikel 10-Gesetzes entsprechende Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="c0f73679-84d0-4809-8595-84ee952d3f97"
                         eId="hauptteil-1_abschnitt-3_art-3"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="9deff5f9-064a-45c3-baf5-cb684ba48329"
                        eId="hauptteil-1_abschnitt-3_art-3_bezeichnung-1">
                         § 19 </akn:num>
               <akn:heading GUID="c2414e9e-49b7-4b61-b730-92091a6d29d5"
                            eId="hauptteil-1_abschnitt-3_art-3_überschrift-1">
                        Übermittlung personenbezogener Daten durch das Bundesamt für Verfassungsschutz
                    </akn:heading>
               <akn:paragraph GUID="ca22005b-939c-4c38-b41e-c7d082bd56fb"
                              eId="hauptteil-1_abschnitt-3_art-3_abs-1">
                  <akn:num GUID="9f92944d-8cb3-46e5-aa8e-553482cd95b7"
                           eId="hauptteil-1_abschnitt-3_art-3_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="d0a48277-01e5-41c1-9809-adf1407dc203"
                            eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1">
                     <akn:intro GUID="7fe8237c-b24c-4657-ace5-7dc805b6a3b8"
                                eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_intro-1">
                        <akn:p GUID="013aa35d-df89-48ce-aee0-3fa4c882c4b2"
                               eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf personenbezogene Daten, die mit den Mitteln nach § 8 Absatz 2 erhoben
                                    worden sind, an die
                                    Staatsanwaltschaften, die Finanzbehörden nach § 386 Absatz 1 der Abgabenordnung, die Polizeien, die mit der
                                    Steuerfahndung betrauten
                                    Dienststellen der Landesfinanzbehörden, die Behörden des Zollfahndungsdienstes sowie andere Zolldienststellen,
                                    soweit diese Aufgaben
                                    nach dem Bundespolizeigesetz wahrnehmen, übermitteln, soweit dies erforderlich ist zur
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="b2953e31-ebe6-4ff9-890a-84acd829b182"
                                eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="72fb11aa-99e1-4ed1-88f2-8f9857f670a9"
                                 eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="7662ab40-180f-4bd1-9672-13404edf6464"
                                     eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="8edf1801-2e1a-466e-91f2-1072f5452ed9"
                                  eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Erfüllung eigener Aufgaben der Informationsgewinnung (§ 8 Absatz 1 Satz 2 und 3),
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="759a2e35-8d44-4f1b-8142-6504d8c1bf6b"
                                eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="e30b0661-6a4a-44a6-85d7-f1cdbc10ff0d"
                                 eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="340f6d76-e305-4ea4-ab21-8ac51c71aed0"
                                     eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="502bed71-908d-42e1-90da-d1cd14d7d750"
                                  eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Abwehr einer im Einzelfall bestehenden Gefahr für den Bestand oder die Sicherheit des Bundes oder eines Landes
                                        oder für Leib,
                                        Leben, Gesundheit oder Freiheit einer Person oder für Sachen von erheblichem Wert, deren Erhaltung im
                                        öffentlichen Interesse
                                        geboten ist,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="1ca22868-0d26-40d2-8fb5-8380c2ae8afe"
                                eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="545d57a8-09ff-4ae1-b04f-9aef57e2e2b0"
                                 eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="2e13a044-fdff-499c-9f1a-0cbb63009022"
                                     eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="8245396b-f63c-4117-83dd-e82546f32234"
                                  eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Verhinderung oder sonstigen Verhütung von Straftaten von erheblicher Bedeutung oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3cd6858f-8a61-4661-a0e3-381522900e7d"
                                eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4">
                        <akn:num GUID="20586230-e86e-410a-b8aa-b71a534467b0"
                                 eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="bd057567-0a52-4ab4-80c0-ad6072d6cb3a"
                                     eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="4022c5d0-4e0d-428b-b015-c55c338b3da4"
                                  eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                        Verfolgung von Straftaten von erheblicher Bedeutung;
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="d735de2a-2f3c-46fe-903c-2c77e4006134"
                              eId="hauptteil-1_abschnitt-3_art-3_abs-2">
                  <akn:num GUID="c580213b-17b0-4e4e-95d9-cc4a704e25c8"
                           eId="hauptteil-1_abschnitt-3_art-3_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="cdf1a8aa-032d-4374-849e-8a169d07017f"
                               eId="hauptteil-1_abschnitt-3_art-3_abs-2_inhalt-1">
                     <akn:p GUID="9b426fe9-ed11-4baa-8a45-9d04e044cd61"
                            eId="hauptteil-1_abschnitt-3_art-3_abs-2_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf personenbezogene Daten an Dienststellen der Stationierungsstreitkräfte
                                übermitteln, soweit
                                die Bundesrepublik Deutschland dazu im Rahmen von Artikel 3 des Zusatzabkommens zu dem Abkommen zwischen den Parteien
                                des
                                Nordatlantikvertrages über die Rechtsstellung ihrer Truppen hinsichtlich der in der Bundesrepublik Deutschland
                                stationierten
                                ausländischen Truppen vom 3. August 1959 (BGBl. 1961 II S. 1183, 1218) verpflichtet ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="b525a5f4-359b-4851-a2c7-7c428e1e0c90"
                              eId="hauptteil-1_abschnitt-3_art-3_abs-3">
                  <akn:num GUID="e1158c6e-9abd-411a-8382-fd7f07163a0b"
                           eId="hauptteil-1_abschnitt-3_art-3_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="06ecd6ec-1de4-4595-bed9-d6f87d855fc8"
                               eId="hauptteil-1_abschnitt-3_art-3_abs-3_inhalt-1">
                     <akn:p GUID="17017161-9c01-435f-94c7-c04fd1ca771e"
                            eId="hauptteil-1_abschnitt-3_art-3_abs-3_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf personenbezogene Daten an ausländische öffentliche Stellen sowie an über- und
                                zwischen*-staatliche Stellen übermitteln, wenn die Übermittlung zur Erfüllung seiner Aufgaben oder zur Wahrung
                                erheblicher
                                Sicherheitsinteressen des Empfängers erforderlich ist. Die Übermittlung unterbleibt, wenn auswärtige Belange der
                                Bundesrepublik
                                Deutschland oder überwiegende schutzwürdige Interessen des Betroffenen entgegenstehen. Die Übermittlung ist
                                aktenkundig zu machen. Der
                                Empfänger ist darauf hinzuweisen, daß die übermittelten Daten nur zu dem Zweck verwendet werden dürfen, zu dem sie ihm
                                übermittelt
                                wurden, und das Bundesamt für Verfassungsschutz sich vorbehält, um Auskunft über die vorgenommene Verwendung der Daten
                                zu bitten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="3efb5435-0093-438d-8860-bbcc765245b7"
                              eId="hauptteil-1_abschnitt-3_art-3_abs-4">
                  <akn:num GUID="75e99e21-5958-4ed9-b401-90f1e93d2204"
                           eId="hauptteil-1_abschnitt-3_art-3_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="15954190-c7dd-42b3-bb4a-0b93ec593f94"
                               eId="hauptteil-1_abschnitt-3_art-3_abs-4_inhalt-1">
                     <akn:p GUID="804b39bf-e26d-427a-83a8-50fc5957325f"
                            eId="hauptteil-1_abschnitt-3_art-3_abs-4_inhalt-1_text-1">
                                Personenbezogene Daten dürfen an andere Stellen nicht übermittelt werden, es sei denn, dass dies zum Schutz der
                                freiheitlichen
                                demokratischen Grundordnung, des Bestandes oder der Sicherheit des Bundes oder eines Landes erforderlich ist und der
                                Bundesminister
                                des Innern seine Zustimmung erteilt hat. Das Bundesamt für Verfassungsschutz führt über die Auskunft nach Satz 1 einen
                                Nachweis, aus
                                dem der Zweck der Übermittlung, ihre Veranlassung, die Aktenfundstelle und der Empfänger hervorgehen; die Nachweise
                                sind gesondert
                                aufzubewahren, gegen unberechtigten Zugriff zu sichern und am Ende des Kalenderjahres, das dem Jahr ihrer Erstellung
                                folgt, zu
                                vernichten. Der Empfänger darf die übermittelten Daten nur für den Zweck verwenden, zu dem sie ihm übermittelt wurden.
                                Der Empfänger
                                ist auf die Verwendungsbeschränkung und darauf hinzuweisen, dass das Bundesamt für Verfassungsschutz sich vorbehält,
                                um Auskunft über
                                die vorgenommene Verwendung der Daten zu bitten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="ee29d180-0b39-4ae3-a9fe-ddfdf5e87440"
                              eId="hauptteil-1_abschnitt-3_art-3_abs-5">
                  <akn:num GUID="531fe5dd-da23-40b4-869d-03324c6e2a9b"
                           eId="hauptteil-1_abschnitt-3_art-3_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="06b66a99-9e0a-4a09-a7ed-72b14b4de092"
                               eId="hauptteil-1_abschnitt-3_art-3_abs-5_inhalt-1">
                     <akn:p GUID="0d632037-d65e-4860-9fcb-087dd7f60b5c"
                            eId="hauptteil-1_abschnitt-3_art-3_abs-5_inhalt-1_text-1">
                                (weggefallen)
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="2699705b-51cd-43e8-aa08-36df6ca67d7e"
                         eId="hauptteil-1_abschnitt-3_art-4"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="d6c4a1ec-ecdd-4976-a4c1-c969ffedf8f7"
                        eId="hauptteil-1_abschnitt-3_art-4_bezeichnung-1">
                         § 20 </akn:num>
               <akn:heading GUID="7ba89a5e-fbdf-4073-bee6-0ac928be70b6"
                            eId="hauptteil-1_abschnitt-3_art-4_überschrift-1">
                        Übermittlung von Informationen durch das Bundesamt für Verfassungsschutz an Strafverfolgungs- und Sicherheitsbehörden in
                        Angelegenheiten
                        des Staats- und Verfassungsschutzes
                    </akn:heading>
               <akn:paragraph GUID="5ae0f2d7-d473-4c34-a506-98c79f0e1cc3"
                              eId="hauptteil-1_abschnitt-3_art-4_abs-1">
                  <akn:num GUID="09352b20-3bc7-4750-9084-f9d2e0519034"
                           eId="hauptteil-1_abschnitt-3_art-4_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="ee965b3a-8369-407d-9dc1-990b21c0c7ec"
                               eId="hauptteil-1_abschnitt-3_art-4_abs-1_inhalt-1">
                     <akn:p GUID="44cac1e1-b55e-451b-b3d6-9246df37c52e"
                            eId="hauptteil-1_abschnitt-3_art-4_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz übermittelt den Staatsanwaltschaften und, vorbehaltlich der
                                staatsanwaltschaftlichen
                                Sachleitungsbefugnis, den Polizeien von sich aus die ihm bekanntgewordenen Informationen einschließlich
                                personenbezogener Daten, wenn
                                tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung zur Verhinderung oder Verfolgung von
                                Staatsschutzdelikten erforderlich
                                ist. Delikte nach Satz 1 sind die in §§ 74a und 120 des Gerichtsverfassungsgesetzes genannten Straftaten sowie
                                sonstige Straftaten,
                                bei denen auf Grund ihrer Zielsetzung, des Motivs des Täters oder dessen Verbindung zu einer Organisation tatsächliche
                                Anhaltspunkte
                                dafür vorliegen, daß sie gegen die in Artikel 73 Nr. 10 Buchstabe b oder c des Grundgesetzes genannten Schutzgüter
                                gerichtet sind. Das
                                Bundesamt für Verfassungsschutz übermittelt dem Bundesnachrichtendienst von sich aus die ihm bekanntgewordenen
                                Informationen
                                einschließlich personenbezogener Daten, wenn tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung für die
                                Erfüllung der
                                gesetzlichen Aufgaben des Empfängers erforderlich ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="c08ba265-0a80-4b8b-bcd6-1acabce71b1c"
                              eId="hauptteil-1_abschnitt-3_art-4_abs-2">
                  <akn:num GUID="682571ac-d3ab-427c-ab20-050b108ca082"
                           eId="hauptteil-1_abschnitt-3_art-4_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="123a49f7-30c7-4a5b-8ab0-37a5351ce12e"
                               eId="hauptteil-1_abschnitt-3_art-4_abs-2_inhalt-1">
                     <akn:p GUID="d70176b1-4bbf-47d1-a970-a35d230844eb"
                            eId="hauptteil-1_abschnitt-3_art-4_abs-2_inhalt-1_text-1">
                                Die Polizeien dürfen zur Verhinderung von Staatsschutzdelikten nach Absatz 1 Satz 2 das Bundesamt für
                                Verfassungsschutz um
                                Übermittlung der erforderlichen Informationen einschließlich personenbezogener Daten ersuchen. Der
                                Bundesnachrichtendienst darf zur
                                Erfüllung seiner Aufgaben das Bundesamt für Verfassungsschutz um die Übermittlung der erforderlichen Informationen
                                einschließlich
                                personenbezogener Daten ersuchen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="f708a4a2-e8e4-4244-a609-3ae8f59541f2"
                         eId="hauptteil-1_abschnitt-3_art-5"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="52c26f0f-d5ed-4923-9b75-f1b7c0c666b8"
                        eId="hauptteil-1_abschnitt-3_art-5_bezeichnung-1">
                         § 21 </akn:num>
               <akn:heading GUID="45138971-86c8-472f-bc1a-3a5362a819ea"
                            eId="hauptteil-1_abschnitt-3_art-5_überschrift-1">
                        Übermittlung von Informationen durch die Verfassungsschutzbehörden der Länder an Strafverfolgungs- und Sicherheitsbehörden in
                        Angelegenheiten des Staats- und Verfassungsschutzes
                    </akn:heading>
               <akn:paragraph GUID="4f942e2e-d3a1-4cfa-8ba6-ad5289df3056"
                              eId="hauptteil-1_abschnitt-3_art-5_abs-1">
                  <akn:num GUID="57708ac0-ea3f-4496-9d12-56b028c673bb"
                           eId="hauptteil-1_abschnitt-3_art-5_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="f26d68af-b038-4e80-b0ba-9a3f306474eb"
                               eId="hauptteil-1_abschnitt-3_art-5_abs-1_inhalt-1">
                     <akn:p GUID="b4f450dd-694d-4b25-9aa4-c519557471c6"
                            eId="hauptteil-1_abschnitt-3_art-5_abs-1_inhalt-1_text-1">
                                Die Verfassungsschutzbehörden der Länder übermitteln den Staatsanwaltschaften und, vorbehaltlich der
                                staatsanwaltschaftlichen
                                Sachleitungsbefugnis, den Polizeien Informationen einschließlich personenbezogener Daten unter den Voraussetzungen des
                                § 20 Abs. 1
                                Satz 1 und 2 sowie Abs. 2 Satz 1. Auf die Übermittlung von Informationen zwischen Behörden desselben Bundeslandes
                                findet Satz 1 keine
                                Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="e26f51ba-3828-4638-a5b2-4959848a3d3e"
                              eId="hauptteil-1_abschnitt-3_art-5_abs-2">
                  <akn:num GUID="902f293c-9850-4a81-885e-6726d289cb3f"
                           eId="hauptteil-1_abschnitt-3_art-5_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="6d5b10de-e088-4fc4-a85f-0f758538be23"
                               eId="hauptteil-1_abschnitt-3_art-5_abs-2_inhalt-1">
                     <akn:p GUID="d4b15d24-1550-4a8c-b0ff-6eb2dca0b704"
                            eId="hauptteil-1_abschnitt-3_art-5_abs-2_inhalt-1_text-1">
                                Die Verfassungsschutzbehörden der Länder übermitteln dem Bundesnachrichtendienst und dem Militärischen Abschirmdienst
                                Informationen
                                einschließlich personenbezogener Daten unter den Voraussetzungen des § 20 Abs. 1 Satz 3 sowie Abs. 2 Satz 2.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="908abe20-d28f-4faa-9310-e540accbdee9"
                         eId="hauptteil-1_abschnitt-3_art-6"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="dfbc84ab-3fff-406b-9f10-94c8b4fd10c7"
                        eId="hauptteil-1_abschnitt-3_art-6_bezeichnung-1">
                         § 22 </akn:num>
               <akn:heading GUID="bc858863-54c4-4aac-8e1a-c7d83296c3a0"
                            eId="hauptteil-1_abschnitt-3_art-6_überschrift-1">
                        Übermittlung von Informationen durch die Staatsanwaltschaften und Polizeien an den Militärischen Abschirmdienst
                    </akn:heading>
               <akn:paragraph GUID="79b0d10d-fa99-4554-84bc-6726f2d0eb11"
                              eId="hauptteil-1_abschnitt-3_art-6_abs-1">
                  <akn:num GUID="5e7ae2b2-2161-47ac-9f1c-ec8d6f87b71b"
                           eId="hauptteil-1_abschnitt-3_art-6_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="6e6a6212-1904-427a-a08f-7050b404994f"
                               eId="hauptteil-1_abschnitt-3_art-6_abs-1_inhalt-1">
                     <akn:p GUID="de8151f4-10cb-4dbb-828d-9d4e327899e4"
                            eId="hauptteil-1_abschnitt-3_art-6_abs-1_inhalt-1_text-1">
                                Für die Übermittlung von Informationen einschließlich personenbezogener Daten durch die Staatsanwaltschaften und,
                                vorbehaltlich der
                                staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die Behörden des Zollfahndungsdienstes sowie andere
                                Zolldienststellen,
                                soweit diese Aufgaben nach dem Bundespolizeigesetz wahrnehmen, an den Militärischen Abschirmdienst findet § 18
                                entsprechende
                                Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="5ae96cd3-2e6c-495b-ba75-d74dcc209733"
                         eId="hauptteil-1_abschnitt-3_art-7"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="09a05edb-aaf2-48dd-a37f-98bedaaf24ce"
                        eId="hauptteil-1_abschnitt-3_art-7_bezeichnung-1">
                         § 22a </akn:num>
               <akn:heading GUID="1a3d01b9-a6b4-4e7d-8176-d0b0ced2bfd0"
                            eId="hauptteil-1_abschnitt-3_art-7_überschrift-1">
                        Projektbezogene gemeinsame Dateien
                    </akn:heading>
               <akn:paragraph GUID="d4700d60-3482-4de0-b86b-5533d44e7c13"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-1">
                  <akn:num GUID="ad2b6992-7840-4aac-a713-ba1e037c819f"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="f2ceecb3-9ba1-45fe-9748-3ac1a78b9eff"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-1_inhalt-1">
                     <akn:p GUID="e16f4820-38b7-4bf2-936c-87ab869570b9"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-1_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz kann für die Dauer einer befristeten projektbezogenen Zusammenarbeit mit den
                                Landesbehörden für
                                Verfassungsschutz, dem Militärischen Abschirmdienst, dem Bundesnachrichtendienst, den Polizeibehörden des Bundes und
                                der Länder und
                                dem Zollkriminalamt eine gemeinsame Datei errichten. Die projektbezogene Zusammenarbeit bezweckt nach Maßgabe der
                                Aufgaben und
                                Befugnisse der in Satz 1 genannten Behörden den Austausch und die gemeinsame Auswertung von Erkenntnissen zu
                                Bestrebungen, die durch
                                Anwendung von Gewalt oder darauf gerichtete Vorbereitungshandlungen gegen die in § 3 Abs. 1 Nr. 1 bis 4 genannten
                                Schutzgüter
                                gerichtet sind. Personenbezogene Daten zu Bestrebungen nach Satz 2 dürfen unter Einsatz der gemeinsamen Datei durch
                                die an der
                                projektbezogenen Zusammenarbeit beteiligten Behörden im Rahmen ihrer Befugnisse verwendet werden, soweit dies in
                                diesem Zusammenhang
                                zur Erfüllung ihrer Aufgaben erforderlich ist. Bei der weiteren Verwendung der personenbezogenen Daten finden für die
                                beteiligten
                                Behörden die jeweils für sie geltenden Vorschriften über die Verwendung von Daten Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="76dceff4-63c2-4c42-8970-be5f5432d887"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-2">
                  <akn:num GUID="2233e696-e277-41e4-83a7-1e45fd0fed01"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="64a5d423-7abc-43ba-a35e-4b35988a2517"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-2_inhalt-1">
                     <akn:p GUID="4b000662-4ee3-4992-846f-1bee69af4c92"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-2_inhalt-1_text-1">
                                Für die Eingabe personenbezogener Daten in die gemeinsame Datei gelten die jeweiligen Übermittlungsvorschriften
                                zugunsten der an der
                                Zusammenarbeit beteiligten Behörden entsprechend mit der Maßgabe, dass die Eingabe nur zulässig ist, wenn die Daten
                                allen an der
                                projektbezogenen Zusammenarbeit teilnehmenden Behörden übermittelt werden dürfen. Eine Eingabe ist ferner nur
                                zulässig, wenn die
                                Behörde, die die Daten eingegeben hat, die Daten auch in eigene Dateien speichern darf. Die Behörde, die die Daten
                                eingegeben hat, hat
                                die Daten zu kennzeichnen.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="0673fb91-fe6e-4056-83ce-fd6746085808"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-3">
                  <akn:num GUID="432fdbc1-c33c-4cd0-a81a-47f231decf20"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="fa5585ed-ede4-4d2b-afd8-de754d8a997e"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-3_inhalt-1">
                     <akn:p GUID="f85f7b50-6121-4b2c-9a35-0c43f55b5038"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-3_inhalt-1_text-1">
                                Für die Führung einer projektbezogenen gemeinsamen Datei gelten § 6 Absatz 2 Satz 5 und 6 und Absatz 3 Satz 1 und § 14
                                Abs. 2
                                entsprechend. § 15 ist mit der Maßgabe anzuwenden, dass das Bundesamt für Verfassungsschutz die Auskunft im
                                Einvernehmen mit der
                                Behörde erteilt, die die datenschutzrechtliche Verantwortung nach Satz 1 trägt und die beteiligte Behörde die
                                Zulässigkeit der
                                Auskunftserteilung nach den für sie geltenden Bestimmungen prüft.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="c45256f0-b5f7-4f18-b53b-048a4a065a0b"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-4">
                  <akn:num GUID="3b96e837-1f51-4d77-9a7f-6be4608847ad"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="66878b8d-c764-46f1-a8dc-ca7842236576"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-4_inhalt-1">
                     <akn:p GUID="97cc47ca-67b0-4e6c-a0b9-29c88bf6566a"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-4_inhalt-1_text-1">
                                Die gemeinsame Datei nach Absatz 1 ist auf höchstens zwei Jahre zu befristen. Die Frist kann um zwei Jahre und danach
                                um ein weiteres
                                Jahr verlängert werden, wenn das Ziel der projektbezogenen Zusammenarbeit bei Projektende noch nicht erreicht worden
                                ist und die Datei
                                weiterhin für die Erreichung des Ziels erforderlich ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="62ff0838-127a-47fc-a9cd-f9655e3e6a3c"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-5">
                  <akn:num GUID="c94c36d7-5122-4d3b-b534-b4a57a7efc34"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:content GUID="cf6c4652-dc2b-4b99-b39e-d22e1416a234"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-5_inhalt-1">
                     <akn:p GUID="ae197691-d34c-4c27-b21a-b2e45d3383c5"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-5_inhalt-1_text-1">
                                Für die Berichtigung, Verarbeitungseinschränkung und Löschung der Daten zu einer Person durch die Behörde, die die
                                Daten eingegeben
                                hat, gelten die jeweiligen, für sie anwendbaren Vorschriften über die Berichtigung, Sperrung und Löschung der Daten
                                entsprechend.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="0fcecfa2-f4e2-4ccd-93cf-b6726449111a"
                              eId="hauptteil-1_abschnitt-3_art-7_abs-6">
                  <akn:num GUID="5271d675-e206-45a2-9923-b7151b6d9032"
                           eId="hauptteil-1_abschnitt-3_art-7_abs-6_bezeichnung-1">
                             (6) </akn:num>
                  <akn:list GUID="edb43e59-90de-47aa-af38-625183459c6c"
                            eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1">
                     <akn:intro GUID="c663488a-e76d-4d42-b300-a2048ebdd12a"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_intro-1">
                        <akn:p GUID="d7bc7221-2058-4a1d-856b-9de3bb033b62"
                               eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz hat für die gemeinsame Datei in einer Dateianordnung die Angaben nach § 14
                                    Abs. 1 Satz 1 Nr. 1
                                    bis 7 sowie weiter festzulegen:
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="b629ad09-c362-4db0-8bb2-360f948643c9"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1">
                        <akn:num GUID="8707816a-9bc6-4c04-9cef-4bb452890f7e"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="bdc1b65f-7ad2-48d6-af38-cd62272810ee"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="6afb430c-1e80-489a-a85b-9982fbe2a8e9"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_inhalt-1_text-1">
                                        die Rechtsgrundlage der Datei,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="6cbd5f2a-999b-44a7-95c7-c7ffc216c89e"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2">
                        <akn:num GUID="dcc23ce5-07ce-4d56-a1bd-22ed3050ebe8"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="8af87c16-beff-4dd1-8fc6-52b91a2c8a34"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="32216b6d-b1cc-4341-81bf-47e214c03849"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_inhalt-1_text-1">
                                        die Art der zu speichernden personenbezogenen Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3b91bac2-1c3e-48b1-8a65-86bba3dc8cfd"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3">
                        <akn:num GUID="b414e244-cddf-44cb-ba0c-180719abff72"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="784fb28b-030c-4ae9-9595-9f8047ecc4e9"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="bb8e9a62-ef65-4020-87e1-080e844d06b2"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_inhalt-1_text-1">
                                        die Arten der personenbezogenen Daten, die der Erschließung der Datei dienen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="25f0132d-743b-4180-a044-8a8714d3af2d"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4">
                        <akn:num GUID="2b7123b6-b73a-4dd4-86c5-ae48fe88f4f1"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="fb52dacd-21dc-4899-b120-c1e0e36a98c9"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="1e29194d-0235-4e2f-988a-54ce967ba19d"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_inhalt-1_text-1">
                                        Voraussetzungen, unter denen in der Datei gespeicherte personenbezogene Daten an welche Empfänger und in
                                        welchen Verfahren
                                        übermittelt werden,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="7992a19d-4c43-4707-ad46-f9bf68704af8"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5">
                        <akn:num GUID="e11efb83-feab-4a56-aa61-b7d4ee8e879e"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_bezeichnung-1">
                                     5. </akn:num>
                        <akn:content GUID="11315122-4d76-4079-aeb6-771de3632ede"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_inhalt-1">
                           <akn:p GUID="baf28f3f-59d5-4ddb-b83d-1ba57e7d2d85"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_inhalt-1_text-1">
                                        im Einvernehmen mit den an der projektbezogenen Zusammenarbeit teilnehmenden Behörden deren jeweilige
                                        Organisationseinheiten, die
                                        zur Eingabe und zum Abruf befugt sind,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3cb48678-23c4-4897-96c1-30f1c69a39ad"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6">
                        <akn:num GUID="5330790d-700e-4ae6-96ae-eddd75d5f659"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_bezeichnung-1">
                                     6. </akn:num>
                        <akn:content GUID="5bd92e2c-a0a0-42c8-8ff6-097e97d85ccf"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_inhalt-1">
                           <akn:p GUID="5a355935-39f2-40fa-b3ea-3aa817eede7a"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_inhalt-1_text-1">
                                        die umgehende Unterrichtung der eingebenden Behörde über Anhaltspunkte für die Unrichtigkeit eingegebener
                                        Daten durch die an der
                                        gemeinsamen Datei beteiligten Behörden sowie die Prüfung und erforderlichenfalls die unverzügliche Änderung,
                                        Berichtigung oder
                                        Löschung dieser Daten durch die Behörde, die die Daten eingegeben hat,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="ee98ba54-2f30-4b31-a883-112b5c84b156"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7">
                        <akn:num GUID="6ad7eff8-66ae-407d-866a-d759ddf920d0"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_bezeichnung-1">
                                     7. </akn:num>
                        <akn:content GUID="59615ee6-9a7a-4894-9bbb-487e0c3de045"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_inhalt-1">
                           <akn:p GUID="c1ff2123-09ee-422a-b792-85157d99ffd6"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_inhalt-1_text-1">
                                        die Möglichkeit der ergänzenden Eingabe weiterer Daten zu den bereits über eine Person gespeicherten Daten
                                        durch die an der
                                        gemeinsamen Datei beteiligten Behörden,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="3f7c4bee-6734-457c-a8fc-aa2049251b8e"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8">
                        <akn:num GUID="b290c85a-975f-4c9d-b7a3-d4b67258e3d7"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_bezeichnung-1">
                                     8. </akn:num>
                        <akn:content GUID="4e8c113f-3ad8-483e-9d83-4eacc996302d"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_inhalt-1">
                           <akn:p GUID="84aa9ac4-c3fd-4f2e-92c5-20d800dbbb28"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_inhalt-1_text-1">
                                        die Protokollierung des Zeitpunkts, der Angaben zur Feststellung des aufgerufenen Datensatzes sowie der für
                                        den Abruf
                                        verantwortlichen Behörde bei jedem Abruf aus der gemeinsamen Datei durch das Bundesamt für Verfassungsschutz
                                        für Zwecke der
                                        Datenschutzkontrolle einschließlich der Zweckbestimmung der Protokolldaten sowie deren Löschfrist und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="c87420ea-4e82-42fc-9cc6-08e69f54d545"
                                eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9">
                        <akn:num GUID="62851244-8e48-4952-a835-80ba504993dd"
                                 eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_bezeichnung-1">
                                     9. </akn:num>
                        <akn:content GUID="472c6e4c-2dd1-4bce-b8bd-764d42fd60b2"
                                     eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_inhalt-1">
                           <akn:p GUID="c0798559-d7e9-4f52-94b0-1d23a4506471"
                                  eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_inhalt-1_text-1">
                                        die Zuständigkeit des Bundesamtes für Verfassungsschutz für Schadensersatzansprüche des Betroffenen
                                        entsprechend § 83 des
                                        Bundesdatenschutzgesetzes.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="616e8641-1e26-4e3a-a961-3db9b87a1e53"
                         eId="hauptteil-1_abschnitt-3_art-8"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="1240d7c9-fa3d-417c-b5ab-ad33dc8e46ea"
                        eId="hauptteil-1_abschnitt-3_art-8_bezeichnung-1">
                         § 22b </akn:num>
               <akn:heading GUID="f5e476b4-8551-4b15-b8f6-d6b4040f063f"
                            eId="hauptteil-1_abschnitt-3_art-8_überschrift-1">
                        Errichtung gemeinsamer Dateien mit ausländischen Nachrichtendiensten
                    </akn:heading>
               <akn:paragraph GUID="d0d49ace-e84a-4282-ab8a-199d9cfd41db"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-1">
                  <akn:num GUID="2751bd7c-e9ae-4949-9e6a-2bcc6c4100c9"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:list GUID="fee1de89-193a-4298-bddf-afdcacf1d4d6"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1">
                     <akn:intro GUID="c30e27fb-51d7-49ac-8b6e-3f36f59a2e50"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_intro-1">
                        <akn:p GUID="eec3a3ff-30ec-4909-8965-f5cf3956b074"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz kann für die Zusammenarbeit mit ausländischen öffentlichen Stellen, die mit
                                    nachrichtendienstlichen Aufgaben betraut sind (ausländische Nachrichtendienste), zur Erforschung von Bestrebungen
                                    oder Tätigkeiten,
                                    die sich auf bestimmte Ereignisse oder Personenkreise beziehen, gemeinsame Dateien einrichten, wenn
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="6cd1e4e7-44dd-414c-9b5a-2061bcdc189e"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="c5eb9470-9c52-4d51-affe-5875eb2d14d0"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="e7c155b7-97d3-4e1d-9ed4-cc5c43e69f18"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="02a344ce-a5ff-4562-a673-46abe11c967d"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        die Erforschung von erheblichem Sicherheitsinteresse für die Bundesrepublik Deutschland und den jeweils
                                        teilnehmenden Staat ist,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="4efb70ac-8782-4019-869f-758b87f1a18b"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="0420081f-beae-40ea-9674-1b99fee1bd86"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="25380396-f919-4f65-ad40-8f984ac42171"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="ad9db439-68e4-4052-835a-7b1c98b0ee0f"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        in den teilnehmenden Staaten die Einhaltung grundlegender rechtsstaatlicher Prinzipien gewährleistet ist,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="bf3f7688-0016-476a-9181-0273fc9824e4"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="7a81e7ba-c691-4535-867d-c3c0eca56054"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="f7652117-8cf7-41ca-9e1f-0a8afda2f551"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="751ccbeb-dafc-434b-a5a2-ff36f0997dfc"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        die Festlegungen und Zusagen nach Absatz 5 Satz 1 verlässlich sind und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="28b13b38-b6e8-46f5-b300-0fb634ddb264"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4">
                        <akn:num GUID="562cf134-7969-4f91-a6d3-c3355c584126"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:content GUID="f939e747-4c91-427e-944e-5db2fe618f2a"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_inhalt-1">
                           <akn:p GUID="a0010c50-c566-477a-8314-b4b6e645f392"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                        das Bundesministerium des Innern, für Bau und Heimat zugestimmt hat.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="d8e4b736-d1bb-4276-a999-cbf986af17b3"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-2">
                  <akn:num GUID="1823cbf6-f6c5-4817-a355-b3eb467aed95"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="aefae98e-fc26-4e36-890c-fff219881c15"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-2_inhalt-1">
                     <akn:p GUID="859aa5da-a8f9-43b9-be3d-3d585e55143e"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-2_inhalt-1_text-1">
                                Der Nachrichtendienst eines Staates, der weder unmittelbar an die Bundesrepublik Deutschland angrenzt noch
                                Mitgliedstaat der
                                Europäischen Union oder des Nordatlantikvertrages ist, kann darüber hinaus nur teilnehmen, wenn besondere
                                Sicherheitsinteressen dies
                                erfordern. Dies ist der Fall, wenn Bestrebungen oder Tätigkeiten erforscht werden, die auf die Begehung von
                                schwerwiegenden Straftaten
                                gegen den Bestand oder die Sicherheit eines Staates oder einer internationalen Organisation gerichtet sind.
                                Schwerwiegende Straftaten
                                sind die in § 3 Absatz 1 des Artikel 10-Gesetzes genannten Straftaten. Die Teilnahme eines solchen ausländischen
                                Nachrichtendienstes
                                bedarf der Zustimmung der Bundesministerin oder des Bundesministers des Innern, für Bau und Heimat.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="722b8a81-c514-47c1-81a2-8c790600698f"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-3">
                  <akn:num GUID="7058e804-2210-4cc8-870b-35ebb624dcc3"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:content GUID="0e4fda92-03a7-416c-84a8-9cc81e8e8b66"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-3_inhalt-1">
                     <akn:p GUID="5faf777e-4014-47e3-a8ad-83c338488b49"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-3_inhalt-1_text-1">
                                Die Datei dient der Feststellung, ob zu Personen, Objekten oder Ereignissen bei einem der beteiligten
                                Nachrichtendienste Informationen
                                vorhanden sind. Hierzu kann die Datei solche personenbezogene Daten enthalten, die zum Auffinden der Informationen und
                                der dazu
                                notwendigen Identifizierung von Personen erforderlich sind. Im Falle eines Treffers wird lediglich derjenige
                                ausländische
                                Nachrichtendienst angezeigt, der die Daten eingegeben hat.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="99323b9f-ee2b-4056-a9fb-89db93586108"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-4">
                  <akn:num GUID="d850a516-5253-4d5e-aac9-f723e89e8ea0"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="805144a6-5dff-43e7-b89f-126f981839c0"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-4_inhalt-1">
                     <akn:p GUID="23f222e8-34b8-41dc-a9c8-589a85c7b968"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-4_inhalt-1_text-1">
                                Die Datei kann auch dem Austausch und der gemeinsamen Auswertung von Informationen und Erkenntnissen dienen, wenn dies
                                zur Wahrung
                                besonderer Sicherheitsinteressen (Absatz 2 Satz 2) erforderlich ist. Hierzu kann sie die zur Erforschung und Bewertung
                                solcher
                                Bestrebungen oder Tätigkeiten erforderlichen Daten enthalten und zu diesem Zweck genutzt werden.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="ef9e9301-c5f6-491a-8cfc-3a79d5e05729"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-5">
                  <akn:num GUID="bfc15c89-e887-4c4b-aa6d-6bcbac6e577d"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-5_bezeichnung-1">
                             (5) </akn:num>
                  <akn:list GUID="039fe32e-c501-43a3-8eeb-436bdce1a2b9"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1">
                     <akn:intro GUID="f75dcd2c-25a3-4234-8f86-64bf145f8a09"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_intro-1">
                        <akn:p GUID="7ccbcc17-9702-4f16-8a2c-a9817e44a907"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_intro-1_text-1">
                                    Die Ziele der Zusammenarbeit und das Nähere der Datenverwendung sind vor Beginn der Zusammenarbeit zwischen den
                                    teilnehmenden
                                    Nachrichtendiensten zur Gewährleistung eines angemessenen Datenschutzniveaus und zum Ausschluss unangemessener
                                    Verwendung
                                    schriftlich festzulegen, insbesondere:
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="d19a8ce2-f74d-48ee-96f9-35d174219e03"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1">
                        <akn:num GUID="c7652286-bd27-409e-8a2d-2853e0b43779"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="513f74ce-42f4-4bc4-b005-802c76facee6"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="6bad98b4-e1eb-45f0-8c5a-b7fdb3bfba72"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Zweck der Datei,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="f4074efd-f5a5-472a-af77-20763ddf3660"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2">
                        <akn:num GUID="45f587eb-3ced-4996-92cf-b2b48e790dda"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="6c680034-4c52-4f26-9ad3-d28c7a9b8f30"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="bad24104-5258-4b65-93e5-c41121ee3959"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_inhalt-1_text-1">
                                        Voraussetzungen der Verwendungen von Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="c9a46b27-7649-4daf-9ab4-c4ed732b6052"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3">
                        <akn:num GUID="36c6dcfd-1302-4dfe-9d2f-96b045465273"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="44cfc4df-71f2-4e04-8093-ede0f443724e"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="a9d0c476-c908-4720-a6ad-e210f4b0a1b6"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_inhalt-1_text-1">
                                        Prüfung und erforderlichenfalls unverzügliche Änderung, Berichtigung und Löschung von Daten,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="bed8aab3-55eb-443e-826b-b04960ab7744"
                                eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4">
                        <akn:num GUID="c41fc892-3514-4b36-a850-c8569cfd4bf0"
                                 eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_bezeichnung-1">
                                     4. </akn:num>
                        <akn:list GUID="ae3476a0-22cb-4f24-8fe5-c902e6a21019"
                                  eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1">
                           <akn:intro GUID="357976e0-590e-4992-b89c-af30a9364723"
                                      eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_intro-1">
                              <akn:p GUID="905900dd-5fc8-448d-9a96-6bc99da08c1f"
                                     eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_intro-1_text-1">
                                            Zusage,
                                        </akn:p>
                           </akn:intro>
                           <akn:point GUID="4ccaa872-4fe9-4f07-b114-24b977217438"
                                      eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1">
                              <akn:num GUID="48a99430-bde3-44fd-b86a-89d3c6d1fbc2"
                                       eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_bezeichnung-1">
                                             a) </akn:num>
                              <akn:content GUID="669d7334-5622-4d9c-8295-b74b2d14ce06"
                                           eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_inhalt-1">
                                 <akn:p GUID="da4d032c-9ff9-40e8-9a14-a65bec6c6b90"
                                        eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_inhalt-1_text-1">
                                                die Daten ohne Zustimmung des eingebenden Nachrichtendienstes nicht für einen anderen Zweck als den
                                                nach Nummer 1 zu verwenden
                                                oder an Dritte zu übermitteln,
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                           <akn:point GUID="d1dbfbcd-12de-4cc7-b18e-7a20ff0aa150"
                                      eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2">
                              <akn:num GUID="ccfb83fb-fdc6-4eee-a9f6-400cd8f10c39"
                                       eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_bezeichnung-1">
                                             b) </akn:num>
                              <akn:content GUID="7509cbdd-1f0c-4943-8a80-f8842e444fbc"
                                           eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_inhalt-1">
                                 <akn:p GUID="6ecd8bf4-0201-4c4c-bce0-8040c6b805ee"
                                        eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_inhalt-1_text-1">
                                                Auskunft über die Verwendung der Daten zu geben, die vom Auskunft erbittenden Nachrichtendienst
                                                eingegeben worden sind.
                                            </akn:p>
                              </akn:content>
                           </akn:point>
                        </akn:list>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="665763bb-39d7-4e49-89d2-0779eae99060"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-6">
                  <akn:num GUID="59cc2129-455f-4804-8e12-cf5459091137"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-6_bezeichnung-1">
                             (6) </akn:num>
                  <akn:content GUID="24ea7b54-1f9f-4e60-a9ce-c16402d5571e"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-6_inhalt-1">
                     <akn:p GUID="04b5f569-13b5-46ee-9175-5aa2661afd54"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-6_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz darf personenbezogene Daten in der gemeinsamen Datei entsprechend § 10 Absatz 1
                                und 3, § 11 Absatz
                                1 eingeben, wenn es die Daten allen teilnehmenden ausländischen Nachrichtendiensten übermitteln darf. Für die vom
                                Bundesamt für
                                Verfassungsschutz eingegebenen Daten gelten für die Veränderung und Nutzung § 10 Absatz 1 und § 11 Absatz 1 und für
                                die Überprüfung,
                                Berichtigung, Löschung und Sperrung § 11 Absatz 2 und § 12 Absatz 1 bis 3 entsprechend. Für die Verantwortung des an
                                der Datei
                                teilnehmenden Nachrichtendienstes gilt § 6 Absatz 2 Satz 5 und 6 entsprechend.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="d99e7eec-2b81-4310-9faf-3408a15cfd1b"
                              eId="hauptteil-1_abschnitt-3_art-8_abs-7">
                  <akn:num GUID="1e004e8f-e193-4066-89b7-cae19693b138"
                           eId="hauptteil-1_abschnitt-3_art-8_abs-7_bezeichnung-1">
                             (7) </akn:num>
                  <akn:content GUID="d70298e2-0a38-4740-bd64-df98ef3876bf"
                               eId="hauptteil-1_abschnitt-3_art-8_abs-7_inhalt-1">
                     <akn:p GUID="7fccc1b2-9fe7-4437-b3f0-402bf93241a2"
                            eId="hauptteil-1_abschnitt-3_art-8_abs-7_inhalt-1_text-1">
                                Das Bundesamt für Verfassungsschutz trifft für die Dateien die technischen und organisatorischen Maßnahmen
                                entsprechend § 64 des
                                Bundesdatenschutzgesetzes. § 6 Absatz 3 Satz 2 bis 5 und § 28 gelten nur für die vom Bundesamt für Verfassungsschutz
                                eingegebenen
                                Daten sowie dessen Abrufe. Das Bundesamt für Verfassungsschutz erteilt dem Betroffenen entsprechend § 15 Auskunft nur
                                zu den vom
                                Bundesamt für Verfassungsschutz eingegebenen Daten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="61025bbc-3aff-48bf-bdf9-3983035b4bdf"
                         eId="hauptteil-1_abschnitt-3_art-9"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="d2c0f820-78e9-441b-b47d-cf7781a262f9"
                        eId="hauptteil-1_abschnitt-3_art-9_bezeichnung-1">
                         § 22c </akn:num>
               <akn:heading GUID="ab79cff6-7778-4e0d-9427-75e923e66d65"
                            eId="hauptteil-1_abschnitt-3_art-9_überschrift-1">
                        Teilnahme an gemeinsamen Dateien mit ausländischen Nachrichtendiensten
                    </akn:heading>
               <akn:paragraph GUID="fab68df4-b057-4fbc-b0dc-62fc65d360b2"
                              eId="hauptteil-1_abschnitt-3_art-9_abs-1">
                  <akn:num GUID="77c0a836-c9d2-420f-82f9-0dab6838c810"
                           eId="hauptteil-1_abschnitt-3_art-9_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:list GUID="a58a862c-033f-4f4d-8278-c3b0143a0008"
                            eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1">
                     <akn:intro GUID="de7ade85-9fe5-46b5-97ec-65276364b676"
                                eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_intro-1">
                        <akn:p GUID="243daa60-9af7-4a41-bb14-dd68bb1235c4"
                               eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz darf an gemeinsamen Dateien, die von ausländischen Nachrichtendiensten
                                    errichtet sind,
                                    teilnehmen. § 22b Absatz 1 bis 4 und 6 gilt entsprechend. Dabei gilt § 22b Absatz 1 Nummer 3 mit der Maßgabe, dass
                                    verlässlich
                                    zuzusagen ist, dass
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="1cd70539-3988-4e3e-a23e-128067f61369"
                                eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="18f34a0b-499e-4deb-a440-90aa3d043fde"
                                 eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="6cb2f616-0db5-4eae-80d0-af2a370ac721"
                                     eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="14d093be-e6e4-4e34-8073-d2ee12134052"
                                  eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        die vom Bundesamt für Verfassungsschutz eingegebenen Daten ohne dessen Zustimmung nicht an Dritte übermittelt
                                        werden dürfen und
                                        nur zu dem Zweck verwendet werden dürfen, zu dem sie in die Datei eingegeben wurden, und
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="441b0a61-0e33-4156-bc2a-f6921c8421ff"
                                eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="681842e3-f1c1-437f-b200-ef294e943ba3"
                                 eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="39c52d6b-ba96-42f6-998a-6f5d1eafefd8"
                                     eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="abb61a41-5aec-4783-8962-a6791718c18a"
                                  eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        das Bundesamt für Verfassungsschutz auf Ersuchen Auskunft über die vorgenommene Verwendung der Daten erhält.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="5933f18c-c0ba-4062-980f-ad7821c379b9"
                         eId="hauptteil-1_abschnitt-3_art-10"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="b06c4403-d0ee-4c5e-b70e-b54b64e447d7"
                        eId="hauptteil-1_abschnitt-3_art-10_bezeichnung-1">
                         § 23 </akn:num>
               <akn:heading GUID="9bdeaf55-f971-4c56-8297-89ee0e34a30e"
                            eId="hauptteil-1_abschnitt-3_art-10_überschrift-1">
                        Übermittlungsverbote
                    </akn:heading>
               <akn:paragraph GUID="3da8bd1f-904d-4fab-9c7c-d18d1a155419"
                              eId="hauptteil-1_abschnitt-3_art-10_abs-1">
                  <akn:num GUID="0cda2bee-191e-4c29-a0f5-23ff38380e74"
                           eId="hauptteil-1_abschnitt-3_art-10_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:list GUID="bc349b0c-b4f7-42ed-9af4-bda07d61dcc6"
                            eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1">
                     <akn:intro GUID="4eb4e9d9-bfa0-4242-b115-b13c556413f4"
                                eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_intro-1">
                        <akn:p GUID="cb969444-53f3-44ce-b88e-2dc2396d9a83"
                               eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_intro-1_text-1">
                                    Die Übermittlung nach den Vorschriften dieses Abschnitts unterbleibt, wenn
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="03c21024-9f1a-45c5-9c5d-f7ee5a922d2a"
                                eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="ad699284-542a-4ab4-bb3b-8bb5eae75821"
                                 eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="b5f6d767-304e-49f3-bee4-25f3006d9861"
                                     eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="c353ba4e-9148-40f7-84c6-26d72ca5c591"
                                  eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        für die übermittelnde Stelle erkennbar ist, daß unter Berücksichtigung der Art der Informationen und ihrer
                                        Erhebung die
                                        schutzwürdigen Interessen des Betroffenen das Allgemeininteresse an der Übermittlung überwiegen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="05766df1-4c11-489b-8133-34c92b6f08e9"
                                eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="eb80ae07-4954-486f-a2db-d7fa3447b6cd"
                                 eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="5f289b99-1f9b-4cda-86fc-a58c8e382f74"
                                     eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="84ee7180-72fb-406f-b00e-bc85fd21548f"
                                  eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        überwiegende Sicherheitsinteressen dies erfordern oder
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="8befc5a8-f94d-434f-ba6e-7eec33c4c633"
                                eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3">
                        <akn:num GUID="3290db0c-78c9-45fd-86c1-01178eb08741"
                                 eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_bezeichnung-1">
                                     3. </akn:num>
                        <akn:content GUID="ce89839a-3409-4b66-9023-e5a1708303af"
                                     eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_inhalt-1">
                           <akn:p GUID="c9925b58-6bc4-4d15-bc6e-e2266d7824cf"
                                  eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                        besondere gesetzliche Übermittlungsregelungen entgegenstehen; die Verpflichtung zur Wahrung gesetzlicher
                                        Geheimhaltungspflichten
                                        oder von Berufs- oder besonderen Amtsgeheimnissen, die nicht auf gesetzlichen Vorschriften beruhen, bleibt
                                        unberührt.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="0ef84377-ae42-4795-a28e-8312bbdc3358"
                         eId="hauptteil-1_abschnitt-3_art-11"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="313388e4-b66f-4488-941c-417273c9ea3d"
                        eId="hauptteil-1_abschnitt-3_art-11_bezeichnung-1">
                         § 24 </akn:num>
               <akn:heading GUID="246d3f46-b13c-4b4a-a7da-f8f4d6713eda"
                            eId="hauptteil-1_abschnitt-3_art-11_überschrift-1">
                        Minderjährigenschutz
                    </akn:heading>
               <akn:paragraph GUID="6bf82f2d-6e88-4cf9-a64e-477cbc0c15c9"
                              eId="hauptteil-1_abschnitt-3_art-11_abs-1">
                  <akn:num GUID="3a7d86a5-fa04-41a4-816f-bac3f2f68478"
                           eId="hauptteil-1_abschnitt-3_art-11_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="1603df18-2c60-44cd-ab16-eb1d4150227d"
                               eId="hauptteil-1_abschnitt-3_art-11_abs-1_inhalt-1">
                     <akn:p GUID="68715265-fa04-4f7b-b6fd-6d52cd07776b"
                            eId="hauptteil-1_abschnitt-3_art-11_abs-1_inhalt-1_text-1">
                                Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger dürfen nach den Vorschriften
                                dieses Gesetzes
                                übermittelt werden, solange die Voraussetzungen der Speicherung nach § 11 Abs. 1 Satz 1 erfüllt sind. Liegen diese
                                Voraussetzungen
                                nicht mehr vor, bleibt eine Übermittlung nur zulässig, wenn sie zur Abwehr einer erheblichen Gefahr oder zur
                                Verfolgung einer Straftat
                                von erheblicher Bedeutung erforderlich ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="e3402c88-b6b5-498d-983d-4b7c6b603724"
                              eId="hauptteil-1_abschnitt-3_art-11_abs-2">
                  <akn:num GUID="02802add-8215-443a-a914-598fedfb7cad"
                           eId="hauptteil-1_abschnitt-3_art-11_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="2cc915e9-25a6-4c3f-b188-ca9e392563d7"
                               eId="hauptteil-1_abschnitt-3_art-11_abs-2_inhalt-1">
                     <akn:p GUID="b662d1fc-fd9d-4e51-aac7-05f189711318"
                            eId="hauptteil-1_abschnitt-3_art-11_abs-2_inhalt-1_text-1">
                                Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger vor Vollendung des 16.
                                Lebensjahres dürfen nach
                                den Vorschriften dieses Gesetzes nicht an ausländische sowie über- oder zwischen*-staatliche Stellen übermittelt
                                werden. Abweichend
                                hiervon dürfen Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger, die das 14.
                                Lebensjahr
                                vollendet haben, übermittelt werden, wenn nach den Umständen des Einzelfalls nicht ausgeschlossen werden kann, dass
                                die Übermittlung
                                zur Abwehr einer erheblichen Gefahr für Leib oder Leben einer Person erforderlich ist oder tatsächliche Anhaltspunkte
                                dafür vorliegen,
                                dass die Übermittlung zur Verfolgung einer der in § 3 Abs. 1 des Artikel 10-Gesetzes genannten Straftaten erforderlich
                                ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="6b11f9fd-2948-4a9d-9ecf-240169bce043"
                         eId="hauptteil-1_abschnitt-3_art-12"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="5e62ee90-063a-4365-9a15-02bf596e6ea2"
                        eId="hauptteil-1_abschnitt-3_art-12_bezeichnung-1">
                         § 25 </akn:num>
               <akn:heading GUID="bf96a84f-661c-41dc-a249-11aebba82e7d"
                            eId="hauptteil-1_abschnitt-3_art-12_überschrift-1">
                        Pflichten des Empfängers
                    </akn:heading>
               <akn:paragraph GUID="c15420e0-2fee-4239-90d7-228fb92804df"
                              eId="hauptteil-1_abschnitt-3_art-12_abs-1">
                  <akn:num GUID="425bca3a-ac48-4433-8412-f767e10bb2b6"
                           eId="hauptteil-1_abschnitt-3_art-12_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="1f5b99d4-03ab-44d6-a69c-72ae2de37319"
                               eId="hauptteil-1_abschnitt-3_art-12_abs-1_inhalt-1">
                     <akn:p GUID="4b5ca496-e67d-4a70-9b64-8b27a545abff"
                            eId="hauptteil-1_abschnitt-3_art-12_abs-1_inhalt-1_text-1">
                                Der Empfänger prüft, ob die nach den Vorschriften dieses Gesetzes übermittelten personenbezogenen Daten für die
                                Erfüllung seiner
                                Aufgaben erforderlich sind. Ergibt die Prüfung, daß sie nicht erforderlich sind, hat er die Unterlagen zu vernichten.
                                Die Vernichtung
                                kann unterbleiben, wenn die Trennung von anderen Informationen, die zur Erfüllung der Aufgaben erforderlich sind,
                                nicht oder nur mit
                                unvertretbarem Aufwand möglich ist; in diesem Fall ist die Verarbeitung der Daten einzuschränken.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="92d7468f-3e83-4e73-98db-6985145ce27d"
                         eId="hauptteil-1_abschnitt-3_art-13"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="ae3bc826-b77e-4316-bf5a-05dd8c3c0135"
                        eId="hauptteil-1_abschnitt-3_art-13_bezeichnung-1">
                         § 26 </akn:num>
               <akn:heading GUID="660ca9fa-9824-4722-8470-0d37c6e977e2"
                            eId="hauptteil-1_abschnitt-3_art-13_überschrift-1">
                        Nachberichtspflicht
                    </akn:heading>
               <akn:paragraph GUID="e52070cc-6072-4bbe-a65d-0002b1cfa957"
                              eId="hauptteil-1_abschnitt-3_art-13_abs-1">
                  <akn:num GUID="5e92cb49-657f-4d10-b8e0-7b682b2f7c8b"
                           eId="hauptteil-1_abschnitt-3_art-13_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="e180ca9d-5eff-4220-aa36-4ab91c24d98b"
                               eId="hauptteil-1_abschnitt-3_art-13_abs-1_inhalt-1">
                     <akn:p GUID="c77ff85b-3ad2-475c-93dd-a5c1606e86c1"
                            eId="hauptteil-1_abschnitt-3_art-13_abs-1_inhalt-1_text-1">
                                Erweisen sich personenbezogene Daten nach ihrer Übermittlung nach den Vorschriften dieses Gesetzes als unvollständig
                                oder unrichtig,
                                so sind sie unverzüglich gegenüber dem Empfänger zu berichtigen, es sei denn, daß dies für die Beurteilung eines
                                Sachverhalts ohne
                                Bedeutung ist.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:section>
         <akn:section GUID="3e4b09f0-5577-497d-a247-030f450df5d9"
                      eId="hauptteil-1_abschnitt-4">
            <akn:num GUID="30a362fc-e472-4570-b24d-d6982be81755"
                     eId="hauptteil-1_abschnitt-4_bezeichnung-1">
                    Vierter Abschnitt </akn:num>
            <akn:heading GUID="184ea736-2615-46a3-af25-66b116bd4cbe"
                         eId="hauptteil-1_abschnitt-4_überschrift-1">
                    Schlußvorschriften
                </akn:heading>
            <akn:article GUID="5f007fcd-6a6a-4a71-96e2-fc11956c312b"
                         eId="hauptteil-1_abschnitt-4_art-1"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="47a1fa65-c087-42bb-95a1-5475a3e89259"
                        eId="hauptteil-1_abschnitt-4_art-1_bezeichnung-1">
                         § 27 </akn:num>
               <akn:heading GUID="4dadd9fa-43ba-410e-b98a-7c9fa012516e"
                            eId="hauptteil-1_abschnitt-4_art-1_überschrift-1">
                        Anwendung des Bundesdatenschutzgesetzes
                    </akn:heading>
               <akn:paragraph GUID="0d97ac37-9778-4577-acce-9976e234e86d"
                              eId="hauptteil-1_abschnitt-4_art-1_abs-1">
                  <akn:num GUID="79e34597-b78a-47d9-9f95-2a277d2e48e7"
                           eId="hauptteil-1_abschnitt-4_art-1_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:list GUID="5d0aca4c-956f-403f-a127-0594cb2e389a"
                            eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1">
                     <akn:intro GUID="13f1c484-45f3-4f8a-8dc9-a9b914b15e04"
                                eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_intro-1">
                        <akn:p GUID="65c72437-55ed-431b-80bb-551417f9c3c4"
                               eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_intro-1_text-1">
                                    Bei der Erfüllung der Aufgaben nach § 3 durch das Bundesamt für Verfassungsschutz findet das
                                    Bundesdatenschutzgesetz wie folgt
                                    Anwendung:
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="a7b9e4b9-aa39-4a91-8bce-2818a29e8c1d"
                                eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1">
                        <akn:num GUID="cacf45c1-8a35-462d-838a-fbc5dea5491c"
                                 eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="a77bd552-3b4e-454f-bc67-3bb469f9fbe4"
                                     eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="e417452d-5bab-4355-9167-87b2c9be71a7"
                                  eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        § 1 Absatz 8, die §§ 4, 16 Absatz 1 und 4 und die §§ 17 bis 21 sowie § 85 finden keine Anwendung,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="897dc57c-4c39-4f04-94da-b2fd761f87bf"
                                eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2">
                        <akn:num GUID="bbf56241-9a6e-4db3-80c2-5074585c437c"
                                 eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="c326ba98-093d-4919-98d8-df323aa47472"
                                     eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="73cfc354-7301-466d-81f9-4d7f8b651528"
                                  eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        die §§ 46, 51 Absatz 1 bis 4 und die §§ 52 bis 54, 62, 64, 83, 84 sind entsprechend anzuwenden.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="a580debe-40aa-4716-b996-6daa27bd2462"
                         eId="hauptteil-1_abschnitt-4_art-2"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="1f1591ad-b1d2-4e3d-91b2-bc7aca152e9d"
                        eId="hauptteil-1_abschnitt-4_art-2_bezeichnung-1">
                         § 28 </akn:num>
               <akn:heading GUID="72d01181-2c99-4961-8e8e-d72d5544a7b3"
                            eId="hauptteil-1_abschnitt-4_art-2_überschrift-1">
                        Unabhängige Datenschutzkontrolle
                    </akn:heading>
               <akn:paragraph GUID="41d1e195-b14d-4459-97da-f79a396ed754"
                              eId="hauptteil-1_abschnitt-4_art-2_abs-1">
                  <akn:num GUID="26c93a3e-88f6-4f12-86e7-2cfbe892dc7f"
                           eId="hauptteil-1_abschnitt-4_art-2_abs-1_bezeichnung-1">
                             (1) </akn:num>
                  <akn:content GUID="2014887e-6749-40f1-809f-0aeac64f6bbb"
                               eId="hauptteil-1_abschnitt-4_art-2_abs-1_inhalt-1">
                     <akn:p GUID="a37d5bf2-48ae-4767-807f-b99f26ddd350"
                            eId="hauptteil-1_abschnitt-4_art-2_abs-1_inhalt-1_text-1">
                                Jedermann kann sich an die Bundesbeauftragte oder den Bundesbeauftragten für den Datenschutz und die
                                Informationsfreiheit wenden, wenn
                                er der Ansicht ist, bei der Verarbeitung seiner personenbezogenen Daten durch das Bundesamt für Verfassungsschutz in
                                seinen Rechten
                                verletzt worden zu sein.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="7edf4544-ff5d-4153-b7f5-cf00652b5cd6"
                              eId="hauptteil-1_abschnitt-4_art-2_abs-2">
                  <akn:num GUID="921717af-8c9d-4d29-b856-2f381274b9b8"
                           eId="hauptteil-1_abschnitt-4_art-2_abs-2_bezeichnung-1">
                             (2) </akn:num>
                  <akn:content GUID="516d7700-757d-4b66-b5f3-fc3936e23cfa"
                               eId="hauptteil-1_abschnitt-4_art-2_abs-2_inhalt-1">
                     <akn:p GUID="35910567-f005-475f-92da-184c5d2bc419"
                            eId="hauptteil-1_abschnitt-4_art-2_abs-2_inhalt-1_text-1">
                                Die oder der Bundesbeauftragte für den Datenschutz und die Informationsfreiheit kontrolliert beim Bundesamt für
                                Verfassungsschutz die
                                Einhaltung der Vorschriften über den Datenschutz. Soweit die Einhaltung von Vorschriften der Kontrolle durch die G
                                10-Kommission
                                unterliegt, unterliegt sie nicht der Kontrolle durch die Bundesbeauftragte oder den Bundesbeauftragten für den
                                Datenschutz und die
                                Informationsfreiheit, es sei denn, die G 10-Kommission ersucht die Bundesbeauftragte oder den Bundesbeauftragten für
                                den Datenschutz
                                und die Informationsfreiheit, die Einhaltung der Vorschriften über den Datenschutz bei bestimmten Vorgängen oder in
                                bestimmten
                                Bereichen zu kontrollieren und ausschließlich ihr darüber zu berichten.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
               <akn:paragraph GUID="c5bda567-14ab-4cd7-8857-e064a0abc3e8"
                              eId="hauptteil-1_abschnitt-4_art-2_abs-3">
                  <akn:num GUID="d03edc3e-f5cb-4add-9935-48097a6e464b"
                           eId="hauptteil-1_abschnitt-4_art-2_abs-3_bezeichnung-1">
                             (3) </akn:num>
                  <akn:list GUID="a9bcea6f-6912-4bb7-86f7-f2b5bf8afe65"
                            eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1">
                     <akn:intro GUID="be130590-bcbe-4eb0-ba2d-eb3fb0f2f42f"
                                eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_intro-1">
                        <akn:p GUID="4fae8d33-71cf-4d42-8d5c-8f117a87ae69"
                               eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_intro-1_text-1">
                                    Das Bundesamt für Verfassungsschutz ist verpflichtet, die Bundesbeauftragte oder den Bundesbeauftragten für den
                                    Datenschutz und die
                                    Informationsfreiheit und ihre oder seine schriftlich besonders Beauftragten bei der Erfüllung ihrer oder seiner
                                    Aufgaben zu
                                    unterstützen. Den in Satz 1 genannten Personen ist dabei insbesondere
                                </akn:p>
                     </akn:intro>
                     <akn:point GUID="edc8b613-2079-4353-bc90-14954863ca6c"
                                eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1">
                        <akn:num GUID="6ee22a1e-8ca5-4862-8973-9549f43081d3"
                                 eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1">
                                     1. </akn:num>
                        <akn:content GUID="ded7c8ee-1fa0-4d21-9db0-fd04a2bf012a"
                                     eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                           <akn:p GUID="046bf98e-6ccf-4e75-9a4b-d21dd8583803"
                                  eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1">
                                        Auskunft zu ihren Fragen sowie Einsicht in alle Unterlagen, insbesondere in die gespeicherten Daten und in die
                                        Datenverarbeitungsprogramme, zu gewähren, die im Zusammenhang mit der Kontrolle nach Absatz 2 stehen,
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                     <akn:point GUID="246b4d8c-4154-4100-87a0-655125d0ea5a"
                                eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2">
                        <akn:num GUID="531a13db-f318-4363-bccb-d6c30bacb2de"
                                 eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1">
                                     2. </akn:num>
                        <akn:content GUID="75c52e9e-df94-4ad6-af5b-d76115bf2c6d"
                                     eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                           <akn:p GUID="a9b7a6c4-d5b7-490f-8f0c-f71321c704eb"
                                  eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1">
                                        jederzeit Zutritt in alle Diensträume zu gewähren.
                                    </akn:p>
                        </akn:content>
                     </akn:point>
                  </akn:list>
               </akn:paragraph>
               <akn:paragraph GUID="9a51c63d-a037-48de-aa3e-ef9dabca652c"
                              eId="hauptteil-1_abschnitt-4_art-2_abs-4">
                  <akn:num GUID="1f404111-e1dc-4626-8581-0072945a0768"
                           eId="hauptteil-1_abschnitt-4_art-2_abs-4_bezeichnung-1">
                             (4) </akn:num>
                  <akn:content GUID="79c7b7be-1031-4f02-b0f5-03d8146a9eab"
                               eId="hauptteil-1_abschnitt-4_art-2_abs-4_inhalt-1">
                     <akn:p GUID="3af58edd-7e0c-4aa4-a2a4-58a461d15efb"
                            eId="hauptteil-1_abschnitt-4_art-2_abs-4_inhalt-1_text-1">
                                Die Absätze 1 bis 3 gelten ohne Beschränkung auf die Erfüllung der Aufgaben nach § 3. Sie gelten entsprechend für die
                                Verarbeitung
                                personenbezogener Daten durch andere Stellen, wenn diese der Erfüllung der Aufgaben von Verfassungsschutzbehörden nach
                                § 3 dient. § 16
                                Absatz 1 und 4 des Bundesdatenschutzgesetzes findet keine Anwendung.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
            <akn:article GUID="edc18b55-c99e-4720-95da-338b4fd1ce44"
                         eId="hauptteil-1_abschnitt-4_art-3"
                         period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                         refersTo="stammform">
               <akn:num GUID="1141258a-93df-4b07-bfad-fadbe57e9f3e"
                        eId="hauptteil-1_abschnitt-4_art-3_bezeichnung-1">
                         § 29 </akn:num>
               <akn:heading GUID="007138a0-b030-4600-ada3-5c5f53a971a7"
                            eId="hauptteil-1_abschnitt-4_art-3_überschrift-1">
                        Einschränkung von Grundrechten
                    </akn:heading>
               <akn:paragraph GUID="955ca69f-7476-478d-91dd-ddc0463f89a4"
                              eId="hauptteil-1_abschnitt-4_art-3_abs-1">
                  <akn:num GUID="455e9f75-b7fb-4c46-9e65-b4e185baedd9"
                           eId="hauptteil-1_abschnitt-4_art-3_abs-1_bezeichnung-1">

                        </akn:num>
                  <akn:content GUID="82c28366-037e-4809-b0ee-1ef6162e4f12"
                               eId="hauptteil-1_abschnitt-4_art-3_abs-1_inhalt-1">
                     <akn:p GUID="5edc9be9-91b3-4a65-8e56-25939172afa7"
                            eId="hauptteil-1_abschnitt-4_art-3_abs-1_inhalt-1_text-1">
                                Die Grundrechte der Versammlungsfreiheit (Artikel 8 des Grundgesetzes), des Brief-, Post- und Fernmelde*-geheimnisses
                                (Artikel 10 des
                                Grundgesetzes) und der Unverletzlichkeit der Wohnung (Artikel 13 des Grundgesetzes) werden nach Maßgabe dieses
                                Gesetzes eingeschränkt.
                            </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
         </akn:section>
      </akn:body>
   </akn:act>
</akn:akomaNtoso>');
