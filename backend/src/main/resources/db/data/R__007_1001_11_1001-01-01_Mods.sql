-- TARGET LAW
DELETE
FROM norms
WHERE eli_expression = 'eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1';

INSERT INTO norms (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd                       http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
      <akn:meta eId="meta-1" GUID="f8ec5da9-48c6-495b-a7d2-f6dc12a743ae">
         <akn:identification eId="meta-1_ident-1"
                             GUID="8bb935bf-0ebd-4a1c-a209-e00b3bd9518c"
                             source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1"
                          GUID="5a7925ba-3d40-4fe5-8ee0-d42e7f11dee6">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1"
                             GUID="207c039b-0601-42b4-896f-6ead92bc4e7a"
                             value="eli/bund/bgbl-1/1001/1/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            GUID="cdff61ec-cf3c-4d1f-9511-9f3a532b29a8"
                            value="eli/bund/bgbl-1/1001/1"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              GUID="77b0ad71-d11e-4a4a-9d52-d7d6779a82c0"
                              name="übergreifende-id"
                              value="f96cfae4-4fce-4c72-9186-0d84778dc11c"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             GUID="7199d1d6-9715-47a0-93d3-c3a8cdbc65d0"
                             date="1001-01-01"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               GUID="48d164a9-c933-4b82-abdb-ad67003b6460"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                GUID="e6cfbc75-63a6-4a60-8285-97d4c3d53621"
                                value="de"/>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               GUID="ccd134a1-c64f-45fc-9a16-9b62e0e33afd"
                               value="1"/>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1"
                             GUID="49b03295-ff7c-4219-8f2f-489ac1ef744e"
                             value="bgbl-1"/>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                GUID="480a140a-7a1a-4264-b0b6-e2a9dba88163"
                                value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                GUID="1ee2c30e-66fa-4c5d-ada6-9df44cb8b2de">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                             GUID="b0d55769-88c4-4624-ad42-613c08ed41c1"
                             value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            GUID="6340f500-b55e-4f57-ac06-ef605bba4d09"
                            value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              GUID="5f41ecba-3ee1-4a34-8f05-1260529f0a34"
                              name="aktuelle-version-id"
                              value="f260b43f-6218-4bd7-ac1a-f5f46d190bfb"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               GUID="965a8fcc-591f-44e2-b0b0-f0becdea95ff"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             GUID="07e23128-9b2c-4c7d-ae1e-0080b0ad8f88"
                             date="1001-01-01"
                             name="verkuendung"/>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                 GUID="1afc4148-8434-4696-87bf-e0dd9ab6551b"
                                 language="deu"/>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"
                                      GUID="895ed32c-bac4-4101-a898-3df54fac54c4"
                                      value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                   GUID="7ee1fffa-b7be-4b43-8b71-4e384ca83376">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                             GUID="d12a389d-6fce-4f6a-99b2-b98a5f2295b6"
                             value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/1001-01-01/regelungstext-1.xml"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            GUID="2b36729b-4c33-4427-9396-1d0ed9a3af48"
                            value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/1001-01-01/regelungstext-1.xml"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             GUID="4bb717ae-8d7f-4655-9aa4-1a4315b10229"
                             date="1001-01-01"
                             name="generierung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                               GUID="d4ef8a82-998b-4e5d-a9b0-151e911276be"
                               href="recht.bund.de"/>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                               GUID="afa63058-68b8-4d77-a2c2-e338f20eb652"
                               value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle source="attributsemantik-noch-undefiniert"
                        GUID="7d0cca7c-997b-4124-b627-b471398212e4"
                        eId="meta-1_lebzykl-1">
            <akn:eventRef date="1001-01-01"
                          source="attributsemantik-noch-undefiniert"
                          refersTo="ausfertigung"
                          type="generation"
                          eId="meta-1_lebzykl-1_ereignis-1"
                          GUID="d4821011-a051-43fc-8356-2874509781fa"/>
         </akn:lifecycle>
         <akn:analysis source="attributsemantik-noch-undefiniert"
                       eId="meta-1_analysis-1"
                       GUID="d6bf2953-40a2-4e53-847d-9009c7362503"/>
         <akn:temporalData source="attributsemantik-noch-undefiniert"
                           GUID="2403f879-25e1-4736-addc-5020535cacb4"
                           eId="meta-1_geltzeiten-1">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1"
                               GUID="a31d6fec-4a66-4835-ae1a-fed12fdd92bb">
               <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-1"
                                 refersTo="geltungszeit"
                                 eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                 GUID="f11ec4d9-a419-4288-b47c-0b0341a3cf74"/>
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
      <akn:preface eId="einleitung-1" GUID="e1d90925-6093-41d9-84e1-df810c83a024">
         <akn:longTitle eId="einleitung-1_doktitel-1"
                        GUID="add85c05-3518-4bd0-b05f-8a09edb7c43d">
            <akn:p eId="einleitung-1_doktitel-1_text-1"
                   GUID="f98eb7af-6156-4b99-92b2-c668195de345">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1"
                             GUID="47622498-565f-44e7-96b4-52d5f0b8d2b2">Fiktives Beispielgesetz</akn:docTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <akn:body eId="hauptteil-1" GUID="2e4a7f34-0b58-4ffc-a8db-5f933ba1c267">
         <akn:article eId="hauptteil-1_art-1"
                      GUID="e7f236c8-e1a7-4a62-959e-d39f77fd39b9"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                     GUID="6a5d55fa-176f-4f46-9141-b6bef0d791b9">
           § 1 </akn:num>
            <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                         GUID="1969ccba-12d4-4825-810b-2350b629b8ba"> Anwendungsbereich von Beispielen </akn:heading>
            <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                           GUID="d79db39b-609a-49cc-968e-95ddfd4bf984">
               <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1"
                        GUID="a38ae4be-8b33-44f2-9a53-11020f433b20">
            (1) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-1_inhalt-1"
                            GUID="90ca46bd-1408-4772-a0d4-3490a29847e0">
                  <akn:p eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1"
                         GUID="cd9f08c0-347f-4abb-8b87-2f00011565a2">Hier steht eine Regelung zum 1. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-2"
                           GUID="39814158-6eb1-40ba-84ce-f07fd970dd67">
               <akn:num eId="hauptteil-1_art-1_abs-2_bezeichnung-1"
                        GUID="fbb68565-7cf4-40b1-aa17-9059f43285b9">
            (2) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-2_inhalt-1"
                            GUID="4afc73b3-9db9-406a-afe7-c51232112402">
                  <akn:p eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1"
                         GUID="7be47602-89d2-4585-b177-e705434e46a7">Hier steht eine Regelung zum 2. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-3"
                           GUID="a31a9e7c-63c2-406b-a25a-fec44cae63ea">
               <akn:num eId="hauptteil-1_art-1_abs-3_bezeichnung-1"
                        GUID="c4b97719-80b8-40d6-ab48-573e07e12f2f">
            (3) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-3_inhalt-1"
                            GUID="b9f1140e-d9e9-46d1-8462-18c7fb7aa729">
                  <akn:p eId="hauptteil-1_art-1_abs-3_inhalt-1_text-1"
                         GUID="01df7717-080d-4db1-ace3-e0a5a7254e79">Hier steht eine Regelung zum 3. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-4"
                           GUID="41215469-4cc5-41b4-a2b5-83ca227739d8">
               <akn:num eId="hauptteil-1_art-1_abs-4_bezeichnung-1"
                        GUID="6ba7aae4-aa4b-4585-9b78-1d99eab2b76f">
            (4) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-4_inhalt-1"
                            GUID="7283d4af-b8fd-47e6-8381-68a310ce121c">
                  <akn:p eId="hauptteil-1_art-1_abs-4_inhalt-1_text-1"
                         GUID="5e187ae3-9434-4fd9-a32b-36d73cf08abe">Hier steht eine Regelung zum 4. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-5"
                           GUID="1b13f48b-078f-4c49-9902-b403f0086c36">
               <akn:num eId="hauptteil-1_art-1_abs-5_bezeichnung-1"
                        GUID="ed95939e-9883-49c4-8155-f63a4ef95e16">
            (5) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-5_inhalt-1"
                            GUID="4693f7d5-f8a8-4857-8880-8c483b901ad9">
                  <akn:p eId="hauptteil-1_art-1_abs-5_inhalt-1_text-1"
                         GUID="9eb5da95-8a9d-4359-9e8a-dd55c8aa6eb2">Hier steht eine Regelung zum 5. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-6"
                           GUID="60af902f-b5e1-4723-80ea-cbea0f93a3b8">
               <akn:num eId="hauptteil-1_art-1_abs-6_bezeichnung-1"
                        GUID="142ec15b-f23a-4edc-ad0e-ca63964fc901">
            (6) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-6_inhalt-1"
                            GUID="7f2652e3-3cda-46f8-8a44-23764d743002">
                  <akn:p eId="hauptteil-1_art-1_abs-6_inhalt-1_text-1"
                         GUID="48608e6f-7168-4c71-8a18-6dae882b68a4">Hier steht eine Regelung zum 6. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-7"
                           GUID="71733c36-3f00-444c-9ee3-609e1495436d">
               <akn:num eId="hauptteil-1_art-1_abs-7_bezeichnung-1"
                        GUID="3e79e30d-e7d4-40fa-9b09-1d937ad10fb6">
            (7) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-7_inhalt-1"
                            GUID="63187c16-ffbd-4c30-be70-0cfb10bbb793">
                  <akn:p eId="hauptteil-1_art-1_abs-7_inhalt-1_text-1"
                         GUID="239e9552-eda5-4e9e-9ddf-535d59f5129d">Hier steht eine Regelung zum 7. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-8"
                           GUID="70b66fd6-573c-4e42-b653-6f99a2970ed4">
               <akn:num eId="hauptteil-1_art-1_abs-8_bezeichnung-1"
                        GUID="ef9e55fd-1288-47c3-95c7-0bf1b0b2423f">
            (8) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-8_inhalt-1"
                            GUID="4a2c19ac-91fe-4e6d-aa5b-c4df2f902305">
                  <akn:p eId="hauptteil-1_art-1_abs-8_inhalt-1_text-1"
                         GUID="ab6b6400-a2dd-41ac-9c90-502a48bb6f9f">Hier steht eine Regelung zum 8. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-9"
                           GUID="2644e506-3876-49ab-9d33-4c9afb741a86">
               <akn:num eId="hauptteil-1_art-1_abs-9_bezeichnung-1"
                        GUID="f49a4c52-0b38-49ef-b3ce-e80a9a646a9e">
            (9) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-9_inhalt-1"
                            GUID="f95d273e-6309-4756-973b-e1facbe872b6">
                  <akn:p eId="hauptteil-1_art-1_abs-9_inhalt-1_text-1"
                         GUID="593a4c93-fa52-4438-bcc9-a03b14caeafd">Hier steht eine Regelung zum 9. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_art-1_abs-10"
                           GUID="35d45ed5-73cd-4df5-a9e0-43179c44ed1e">
               <akn:num eId="hauptteil-1_art-1_abs-10_bezeichnung-1"
                        GUID="0fa24f06-469e-4f15-83df-60889b632c08">
            (10) </akn:num>
               <akn:content eId="hauptteil-1_art-1_abs-10_inhalt-1"
                            GUID="768223b2-2f75-4833-8119-188918698417">
                  <akn:p eId="hauptteil-1_art-1_abs-10_inhalt-1_text-1"
                         GUID="f88f9550-6a8b-4556-ab4a-e197a55b1560">Hier steht eine Regelung zum 10. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_art-2"
                      GUID="350e50ca-3938-43ea-807e-cc023ced19fa"
                      period="#geltungszeitgr-1"
                      refersTo="geltungszeitregel">
            <akn:num eId="hauptteil-1_art-2_bezeichnung-1"
                     GUID="d2fcc3a9-1196-477d-8508-83cff3c167ba">
          Artikel 2</akn:num>
            <akn:heading eId="hauptteil-1_art-2_überschrift-1"
                         GUID="93b0e7e3-36e2-46d8-893e-d24b2001197b">Inkrafttreten</akn:heading>
            <akn:paragraph eId="hauptteil-1_art-2_abs-1"
                           GUID="8fc0669c-7480-4dde-bebc-d5d83704e822">
               <akn:num eId="hauptteil-1_art-2_abs-1_bezeichnung-1"
                        GUID="bcb266d9-7d6d-4c54-b36c-3483640ab7aa">
          </akn:num>
               <akn:content eId="hauptteil-1_art-2_abs-1_inhalt-1"
                            GUID="5b03cb2e-7335-44f2-94b5-7bb05859078e">
                  <akn:p eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1"
                         GUID="36690fc7-bd2b-4450-acaa-8e4110e39335"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1_datum-1"
                               GUID="b7a170d0-b5fa-4864-80fd-4864324479fb"
                               date="1001-01-02"
                               refersTo="inkrafttreten-datum">am Tag nach
              der Verkündung</akn:date> in Kraft. </akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
      </akn:body>
   </akn:act>
</akn:akomaNtoso>');
