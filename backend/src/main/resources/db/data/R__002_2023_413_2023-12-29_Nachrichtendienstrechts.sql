-- REAPPLY
-- TARGET LAW
DELETE
FROM dokumente
WHERE eli_dokument_expression = 'eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1';

INSERT INTO dokumente (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<!-- NOTE: THIS FILES CONTAINS ERRORS AND SHOULD NOT BE USED ANYMORE. -->
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="bbb5e6be-08f2-4dc3-b17a-529e09f8da8b" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="c104c9d4-8142-4dea-bcef-acb40a3f676b" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="3d7e9482-cb45-4cb3-a823-5bafa219d505" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1990/s2954/regelungstext-1" GUID="82e40ffc-10d7-4af8-b618-255b4e6aff3d" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954" GUID="61a176a1-a19d-4034-bc19-90708d57fde3" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="e9b14511-6253-4023-8d5f-6878c4f50cc0" GUID="b75661cc-e5a9-41b4-8e31-9117076e74d2" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1990-12-20" GUID="d9799613-fe18-42d9-b5c7-89dce4217c66" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="c4224a7b-531c-422b-8e8e-bcfe2aea142e" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="a7fa3f05-85f5-4952-ab84-6ab7a055959a" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="s2954" GUID="62211365-d485-4a0c-bf4f-9fed59d02ac0" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="5b439f46-7eff-453e-8992-3b51d853f035" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="ea57df5e-acb3-4931-b6b5-c64665dbe304" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="74905066-716c-44fe-907c-35a3e0fe3d3f" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1" GUID="2f4e4c7b-1d5c-4cd5-b176-e915b1948ec2" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu" GUID="6bf04475-47d9-440a-95bd-5f53febd63c0" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c" GUID="bc763d89-b3c0-4161-974c-4b5af5b16a2a" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="e880bf9f-3289-4193-a423-67be494daf80" GUID="551508a8-489f-4c67-a1da-960ad42bad89" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="57c6d20c-4965-4ad5-9d06-a0499c32c018" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="2022-12-19" GUID="db572224-e3c3-4dfc-b34a-4363de737334" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="11672cf3-40fe-43ef-be79-a8267acdb588" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="b45f6433-16a1-44ef-bdf3-fd42f6c79ef3" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="23c24948-aed0-4b89-af42-b00fe44d54c6" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/2022-12-19/regelungstext-1.xml" GUID="53c928ea-549c-4c0d-8e95-4a28fddd22f0" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/2022-12-19/regelungstext-1.xml" GUID="58c00af5-963b-4a60-9118-2383ee0da2f2" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="2022-12-19" GUID="7d6c116a-b47b-4191-a339-967d8035a758" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="16a74fc6-b70c-4a6d-ad37-1908e440ba9d" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="84a2c889-a50a-4113-a037-7610b1dcfa56" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="525031f3-b3ff-4d3a-be31-c13326295bf5" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1970-01-01" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="996e0446-73f9-4883-8dad-9d14d7bec3bd" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="2022-12-19" refersTo="inkrafttreten" type="generation" source="attributsemantik-noch-undefiniert" GUID="7c71baca-8c04-476b-8835-eb01b9a70911" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="1ba353c4-1bac-4e39-a582-15b75f95dab1" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="6047eebb-8ae3-43c4-b171-b092af117dde" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="8f8c6f13-b93f-448c-a7dc-ca0be6f39f45" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1"/>
        </akn:temporalGroup>
      </akn:temporalData>
      <akn:proprietary source="attributsemantik-noch-undefiniert" GUID="2a01e95a-f73a-4999-aa87-6997f5c18589" eId="meta-1_proprietary-1">
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
          <meta:typ>gesetz</meta:typ>
          <meta:form>stammform</meta:form>
          <meta:fassung>verkuendungsfassung</meta:fassung>
          <meta:art>regelungstext</meta:art>
          <meta:initiant>bundesregierung</meta:initiant>
          <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
          <meta:fna>210-5</meta:fna>
          <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
          <meta:gesta>nicht-vorhanden</meta:gesta>
        </meta:legalDocML.de_metadaten>
        <meta-breg:legalDocML.de_metadaten xmlns:meta-breg="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
          <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
          <meta-breg:federfuehrung>
            <meta-breg:federfuehrend ab="2023-12-29" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta-breg:federfuehrend>
          </meta-breg:federfuehrung>
        </meta-breg:legalDocML.de_metadaten>
      </akn:proprietary>
    </akn:meta>
    <akn:preface GUID="031f77e8-9b26-43f8-88e9-6e202c73c214" eId="einleitung-1">
      <akn:longTitle GUID="bdc9dd06-06e7-4993-8bc8-6d711be8b653" eId="einleitung-1_doktitel-1">
        <akn:p GUID="68a8794e-a6e1-4a6e-b79f-52fcf656fa96" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="858461e7-348b-40aa-b863-6ef2c9b46541" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz</akn:docTitle>
          <akn:shortTitle GUID="44442327-49ed-475c-ae30-3af04da4f4bc" eId="einleitung-1_doktitel-1_text-1_kurztitel-1"> Bundesverfassungsschutzgesetz</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
      <akn:block name="attributsemantik-noch-undefiniert" GUID="1f3615bd-9343-40d2-a2c0-af7774900dd0" eId="einleitung-1_block-1">
        <akn:date refersTo="ausfertigung-datum" date="2022-12-19" GUID="856a3a7e-d3ca-467d-a007-6f374df83de7" eId="einleitung-1_block-1_datum-1">Vom 19. Dezember 2022</akn:date>
      </akn:block>
    </akn:preface>
    <akn:preamble GUID="2622ae82-b59c-4f94-b059-39c6416ece7e" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="02892b76-eb55-40f0-bfbb-95c7c9346362" eId="preambel-1_formel-1">
        <akn:p GUID="eaa05f07-95c6-42a1-9211-06ce5c810de3" eId="preambel-1_formel-1_text-1">Der <akn:organization title="Bundestag" refersTo="attributsemantik-noch-undefiniert" GUID="32c591aa-8c8e-4a70-9921-1ae7cfaa51c3" eId="preambel-1_formel-1_text-1_org-1">Bundestag</akn:organization> hat das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>
    <akn:body GUID="d49b7948-6872-4969-8727-9a2f4d66ebef" eId="hauptteil-1">
      <akn:section GUID="27e287a2-4bae-4d69-a41a-42c250987ffa" eId="hauptteil-1_abschnitt-1">
        <akn:num GUID="3e698d3f-0347-47dc-ab49-9a90122371d4" eId="hauptteil-1_abschnitt-1_bezeichnung-1"> Erster Abschnitt </akn:num>
        <akn:heading GUID="6b413a96-5664-4fef-9e7b-eea6ab9563c4" eId="hauptteil-1_abschnitt-1_überschrift-1"> Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden </akn:heading>
        <akn:article GUID="770084ea-1c9e-449e-9e12-75bc71c1bad4" eId="hauptteil-1_abschnitt-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="2a67be41-57c8-42b2-b544-4e2cb768b57c" eId="hauptteil-1_abschnitt-1_art-1_bezeichnung-1"> § 1 </akn:num>
          <akn:heading GUID="4b6783bc-ca1f-4467-a17b-04bd70ac8952" eId="hauptteil-1_abschnitt-1_art-1_überschrift-1"> Zusammenarbeitspflicht </akn:heading>
          <akn:paragraph GUID="963b3290-60d8-47b1-a93d-39a2e5fb32c9" eId="hauptteil-1_abschnitt-1_art-1_abs-1">
            <akn:num GUID="1e282f64-44aa-42bc-9057-532aa9060423" eId="hauptteil-1_abschnitt-1_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="9b19fb94-fd3d-4247-820f-a8ce751c24dd" eId="hauptteil-1_abschnitt-1_art-1_abs-1_inhalt-1">
              <akn:p GUID="bcf86c63-1c91-4ef3-b4c8-c1a0a94decb1" eId="hauptteil-1_abschnitt-1_art-1_abs-1_inhalt-1_text-1"> Der Verfassungsschutz dient dem Schutz der freiheitlichen demokratischen Grundordnung, des Bestandes und der Sicherheit des Bundes und der Länder. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="a8c724e2-e16e-4524-868a-ff1b96839b09" eId="hauptteil-1_abschnitt-1_art-1_abs-2">
            <akn:num GUID="a7ca5e71-f437-419b-8432-5b2d2ec3f9fe" eId="hauptteil-1_abschnitt-1_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="637f8be3-d441-4ccc-8c29-41c012301c5f" eId="hauptteil-1_abschnitt-1_art-1_abs-2_inhalt-1">
              <akn:p GUID="6d2e94ee-4a76-4f3b-94fc-5dc417b9d8d0" eId="hauptteil-1_abschnitt-1_art-1_abs-2_inhalt-1_text-1"> Der Bund und die Länder sind verpflichtet, in Angelegenheiten des Verfassungsschutzes zusammenzuarbeiten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="14d13f0d-68cb-4125-b691-13318b32a791" eId="hauptteil-1_abschnitt-1_art-1_abs-3">
            <akn:num GUID="f603ab6e-2eb9-494b-9e9c-78f58845b2f1" eId="hauptteil-1_abschnitt-1_art-1_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="3e8b44b5-03f3-40c5-b4c8-2edec0545cea" eId="hauptteil-1_abschnitt-1_art-1_abs-3_inhalt-1">
              <akn:p GUID="4e986212-f234-4b2d-aeeb-217b8dbbc38d" eId="hauptteil-1_abschnitt-1_art-1_abs-3_inhalt-1_text-1"> Die Zusammenarbeit besteht auch in gegenseitiger Unterstützung und Hilfeleistung. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="699489ae-be75-46fe-b90c-df43b93935c6" eId="hauptteil-1_abschnitt-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="7553b70e-976a-4aef-ad0e-ce22ecc05184" eId="hauptteil-1_abschnitt-1_art-2_bezeichnung-1"> § 2 </akn:num>
          <akn:heading GUID="00c9eeea-b400-41bd-9c9b-a02652ec7783" eId="hauptteil-1_abschnitt-1_art-2_überschrift-1"> Verfassungsschutzbehörden </akn:heading>
          <akn:paragraph GUID="95f64270-d7c4-441a-a38f-aebf8c87005d" eId="hauptteil-1_abschnitt-1_art-2_abs-1">
            <akn:num GUID="aeca0f36-0eb9-49d0-9bd5-e44ab06a66da" eId="hauptteil-1_abschnitt-1_art-2_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="9ab90d0e-71dc-4c10-b5b7-0cef88529d1d" eId="hauptteil-1_abschnitt-1_art-2_abs-1_inhalt-1">
              <akn:p GUID="6d93b8ea-647f-4ffe-8131-c5fc458fc448" eId="hauptteil-1_abschnitt-1_art-2_abs-1_inhalt-1_text-1">Für die Zusammenarbeit des Bundes mit den Ländern unterhält der Bund ein Bundesamt für Verfassungsschutz als Bundesoberbehörde. Es untersteht dem Bundesministerium des Innern, für Bau und Heimat. Das Bundesamt für Verfassungsschutz darf einer polizeilichen Dienststelle nicht angegliedert werden.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="a4774736-bf2c-4fbb-8904-d825bd25612d" eId="hauptteil-1_abschnitt-1_art-2_abs-2">
            <akn:num GUID="b90c6bd7-e2b1-435b-a474-c2e1a51e998f" eId="hauptteil-1_abschnitt-1_art-2_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="834e730f-a6a4-43d8-98a0-9ba958dc6679" eId="hauptteil-1_abschnitt-1_art-2_abs-2_inhalt-1">
              <akn:p GUID="baf783f6-b0d5-428f-a139-1269b30b0bc4" eId="hauptteil-1_abschnitt-1_art-2_abs-2_inhalt-1_text-1"> Für die Zusammenarbeit der Länder mit dem Bund und der Länder untereinander unterhält jedes Land eine Behörde zur Bearbeitung von Angelegenheiten des Verfassungsschutzes. Mehrere Länder können eine gemeinsame Behörde unterhalten. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="9a4a5150-c47d-4f97-b15b-951f05451f58" eId="hauptteil-1_abschnitt-1_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="d5e90a39-20ad-49ec-81e4-d593c3f6afc6" eId="hauptteil-1_abschnitt-1_art-3_bezeichnung-1"> § 3 </akn:num>
          <akn:heading GUID="d5f794ca-aeb8-4257-94fc-a8eb9081a29b" eId="hauptteil-1_abschnitt-1_art-3_überschrift-1"> Aufgaben der Verfassungsschutzbehörden </akn:heading>
          <akn:paragraph GUID="a09c03db-cd6e-49bf-b81a-134832669699" eId="hauptteil-1_abschnitt-1_art-3_abs-1">
            <akn:num GUID="37373f99-a5f8-4d0a-85c4-f976ba493af0" eId="hauptteil-1_abschnitt-1_art-3_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="c1274708-e074-4add-8240-653dfe010969" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1">
              <akn:intro GUID="c40e99f6-efbb-4f14-b2f7-0f44bd56308c" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_intro-1">
                <akn:p GUID="962e77b1-9711-4357-b07f-5baa87746972" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_intro-1_text-1"> Aufgabe der Verfassungsschutzbehörden des Bundes und der Länder ist die Sammlung und Auswertung von Informationen, insbesondere von sach- und personen*-bezogenen Auskünften, Nachrichten und Unterlagen, über </akn:p>
              </akn:intro>
              <akn:point GUID="5dbfc1d5-967e-4316-957b-e647516daf43" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="eae9426f-cf6a-480c-916d-c95979ab985b" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="9ee6e9ff-7cee-487d-bfb6-026e757a1f81" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="07fdea71-5826-40df-9981-e311595daa58" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> Bestrebungen, die gegen die freiheitliche demokratische Grundordnung, den Bestand oder die Sicherheit des Bundes oder eines Landes gerichtet sind oder eine ungesetzliche Beeinträchtigung der Amtsführung der Verfassungsorgane des Bundes oder eines Landes oder ihrer Mitglieder zum Ziele haben, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="7e004c6b-6e9a-4539-a1f9-571ce8d7ffdf" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="da3cfcbf-9b77-46e0-9a52-fae69bb9aa0f" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="80bdef84-a316-4825-a063-cd1f415500ab" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="ed152147-5fcc-43be-a95e-16e5739b6707" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> sicherheitsgefährdende oder geheimdienstliche Tätigkeiten im Geltungsbereich dieses Gesetzes für eine fremde Macht, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="1811e7f5-7279-4755-95a5-1d822439b746" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="f057f7be-8666-4573-a8b9-8b031a15b3c5" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="329e4276-d912-4926-9ca5-76496415af33" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="1a84e500-cf11-4cac-8139-af863c3666cd" eId="hauptteil-1_abschnitt-1_art-3_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> Bestrebungen im Geltungsbereich dieses Gesetzes, die durch Anwendung von Gewalt oder darauf gerichtete Vorbereitungshandlungen auswärtige Belange der Bundesrepublik Deutschland gefährden. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="099d132a-8c64-4c58-be3a-a64bddadde86" eId="hauptteil-1_abschnitt-1_art-3_abs-2">
            <akn:num GUID="6466d91c-763f-4cd4-8e0a-6687304e3ceb" eId="hauptteil-1_abschnitt-1_art-3_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="8700d543-731d-451e-afb2-bed5bad7c56e" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1">
              <akn:intro GUID="314e3dc3-5256-416c-892e-53dbc7827c45" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_intro-1">
                <akn:p GUID="a7d23d11-ab9e-4b67-9a4f-8d9586263c5f" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_intro-1_text-1"> Die Verfassungsschutzbehörden des Bundes und der Länder wirken mit </akn:p>
              </akn:intro>
              <akn:point GUID="cc5c4e65-cda2-4b8b-8f92-ff613ac15b2b" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1">
                <akn:num GUID="5c149f6b-8425-4226-85e3-c2e499bcde66" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="baee9d5c-ee43-43a0-8e83-5b4611a3fa1e" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="062a3933-9afb-4cb7-ae1e-67449aab639e" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-1_inhalt-1_text-1"> bei der Sicherheitsüberprüfung von Personen, denen im öffentlichen Interesse geheimhaltungsbedürftige Tatsachen, Gegenstände oder Erkenntnisse anvertraut werden, die Zugang dazu erhalten sollen oder ihn sich verschaffen können, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="c9eec98b-b2be-4e3f-881f-750f77d7e8c3" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2">
                <akn:num GUID="361d4563-0c9b-475d-8f39-33490e20a2fe" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="a8a07762-b972-48e3-82e1-0a90832f9a7c" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="34acc4f1-569e-4573-817c-5a000fbe75f6" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-2_inhalt-1_text-1"> bei der Sicherheitsüberprüfung von Personen, die an sicherheitsempfindlichen Stellen von lebens- oder verteidigungs*-wichtigen Einrichtungen beschäftigt sind oder werden sollen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="697d1567-d612-49ef-ba29-70f11d3f1464" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3">
                <akn:num GUID="dac77264-aed6-4cbd-8770-462bfbdebec4" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="cb43e04a-1205-4365-89ab-97ce3a9eedbe" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="d3c649e9-b22b-4aea-9803-e1b444f6239b" eId="hauptteil-1_abschnitt-1_art-3_abs-2_untergl-1_listenelem-3_inhalt-1_text-1"> bei technischen Sicherheitsmaßnahmen zum Schutz von im öffentlichen Interesse geheimhaltungsbedürftigen Tatsachen, Gegenständen oder Erkenntnissen gegen die Kenntnisnahme durch Unbefugte. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="bfeefe59-a12c-4806-af3f-cc1327a15001" eId="hauptteil-1_abschnitt-1_art-3_abs-3">
            <akn:num GUID="c9e6ae7c-08f6-45c2-87ac-1adbcee698e1" eId="hauptteil-1_abschnitt-1_art-3_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="b100bb3f-aead-4aa4-9fb9-0c9f3d6e08ba" eId="hauptteil-1_abschnitt-1_art-3_abs-3_inhalt-1">
              <akn:p GUID="e4ca5a76-b8ef-4ef1-aa2b-13d4febc805d" eId="hauptteil-1_abschnitt-1_art-3_abs-3_inhalt-1_text-1"> Die Verfassungsschutzbehörden sind an die allgemeinen Rechtsvorschriften gebunden (Artikel 20 des Grundgesetzes). </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="d593fd72-c6dc-42eb-8d7c-3ccb00171aca" eId="hauptteil-1_abschnitt-1_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="29b5e8ac-49dc-401e-a199-4bc7122bf7b7" eId="hauptteil-1_abschnitt-1_art-4_bezeichnung-1"> § 4 </akn:num>
          <akn:heading GUID="8563e16b-62b1-43fa-89f0-eeaca2397009" eId="hauptteil-1_abschnitt-1_art-4_überschrift-1"> Begriffsbestimmungen </akn:heading>
          <akn:paragraph GUID="5200b9a7-8907-4fe0-805f-a21ed5ad642c" eId="hauptteil-1_abschnitt-1_art-4_abs-1">
            <akn:num GUID="0ff5aaad-a677-447e-a73e-22e412078425" eId="hauptteil-1_abschnitt-1_art-4_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="d4f49171-21bd-416e-8f9f-be9b04aa9614" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1">
              <akn:intro GUID="1caa76ea-621a-4209-9250-a104b078c3a5" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_intro-1">
                <akn:p GUID="dcb3a66c-7b20-4178-a84a-1e53ed3725b9" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_intro-1_text-1"> Im Sinne dieses Gesetzes sind </akn:p>
              </akn:intro>
              <akn:point GUID="b020a850-5b25-4117-8473-efb1919dc831" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="a710bf6f-3c01-4939-9f13-086ee07d207f" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_bezeichnung-1"> a) </akn:num>
                <akn:content GUID="c4d93514-4f60-463a-b9d0-410c9aecfa10" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="a2b288db-2f81-4aa7-99e4-da065ee193e2" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> Bestrebungen gegen den Bestand des Bundes oder eines Landes solche politisch bestimmten, ziel- und zweck*-gerichteten Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, die Freiheit des Bundes oder eines Landes von fremder Herrschaft aufzuheben, ihre staatliche Einheit zu beseitigen oder ein zu ihm gehörendes Gebiet abzutrennen; </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="5f349544-433e-4c4e-8db5-d7f688a9344e" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="c4a8cc25-1ffe-4901-80be-384410c182c2" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_bezeichnung-1"> b) </akn:num>
                <akn:content GUID="28df9c80-50d8-4be6-8a70-5a46ca169f18" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="7afef6bf-9a7b-410b-9d50-49e15dcadcbd" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> Bestrebungen gegen die Sicherheit des Bundes oder eines Landes solche politisch bestimmten, ziel- und zweck*-gerichteten Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, den Bund, Länder oder deren Einrichtungen in ihrer Funktionsfähigkeit erheblich zu beeinträchtigen; </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="4875f7a5-f021-430f-bafe-171faa7cb18a" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="2c292f84-538b-4925-911f-8a858612e54a" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_bezeichnung-1"> c) </akn:num>
                <akn:content GUID="ed7b271e-b197-4a21-8fbe-d3995305c919" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="a87f6688-a7a5-4287-8453-5ac43daafefb" eId="hauptteil-1_abschnitt-1_art-4_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> Bestrebungen gegen die freiheitliche demokratische Grundordnung solche politisch bestimmten, ziel- und zweck*-gerichteten Verhaltensweisen in einem oder für einen Personenzusammenschluß, der darauf gerichtet ist, einen der in Absatz 2 genannten Verfassungsgrundsätze zu beseitigen oder außer Geltung zu setzen. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="f076adbc-61e2-4bd9-8c60-1ebe8ca7a005" eId="hauptteil-1_abschnitt-1_art-4_abs-2">
            <akn:num GUID="10d32374-e682-40f2-80a9-61ea63653f38" eId="hauptteil-1_abschnitt-1_art-4_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="20198294-f133-4015-8a6b-682355ad056b" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1">
              <akn:intro GUID="00cc1f9d-03aa-4964-9a48-08e8c97f63cc" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_intro-1">
                <akn:p GUID="96c67e92-8b3e-4fd0-b617-6e0e2f269fc6" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_intro-1_text-1"> Zur freiheitlichen demokratischen Grundordnung im Sinne dieses Gesetzes zählen: </akn:p>
              </akn:intro>
              <akn:point GUID="2c17618d-334a-417d-b40a-711ebb3088f9" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1">
                <akn:num GUID="0ec030c9-bfe1-4454-84cc-4eb580ed7e5a" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_bezeichnung-1"> a) </akn:num>
                <akn:content GUID="1d7f77ad-caa7-4e8c-9e38-a2a49c1da702" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="bfd79e80-7a0a-40f6-994b-c545c2e79254" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-1_inhalt-1_text-1"> das Recht des Volkes, die Staatsgewalt in Wahlen und Abstimmungen und durch besondere Organe der Gesetzgebung, der vollziehenden Gewalt und der Rechtsprechung auszuüben und die Volksvertretung in allgemeiner, unmittelbarer, freier, gleicher und geheimer Wahl zu wählen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="65b9ce34-748a-4148-b592-6903e7e57067" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2">
                <akn:num GUID="dc38a21b-b395-4436-bb67-7c3817f3a723" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_bezeichnung-1"> b) </akn:num>
                <akn:content GUID="e28530c0-e1d1-43a4-8917-d70b09505ef7" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="cadf654f-8c80-4055-8418-57eaafa92fcc" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-2_inhalt-1_text-1"> die Bindung der Gesetzgebung an die verfassungsmäßige Ordnung und die Bindung der vollziehenden Gewalt und der Rechtsprechung an Gesetz und Recht, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="1d25b349-03a3-4a8c-9139-16f32ff741ef" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3">
                <akn:num GUID="c17cdead-9ce5-4119-bdfb-225a65be383a" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_bezeichnung-1"> c) </akn:num>
                <akn:content GUID="60088cf5-c2df-452a-b1cc-624a12511786" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="e0805c1d-83b9-410c-b523-490bf4581121" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-3_inhalt-1_text-1"> das Recht auf Bildung und Ausübung einer parlamentarischen Opposition, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="600956e1-b5d3-4a80-9b13-a83d654b2511" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4">
                <akn:num GUID="c9d585bb-cb8b-4b9a-b224-f6537949c891" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_bezeichnung-1"> d) </akn:num>
                <akn:content GUID="833d87be-4de4-4c51-bacc-a64879e82b99" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="48e0844f-f1b4-4520-ab08-5829f9e1e6d3" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-4_inhalt-1_text-1"> die Ablösbarkeit der Regierung und ihre Verantwortlichkeit gegenüber der Volksvertretung, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="c31501a8-b006-48b1-bf47-17a0f9c4e089" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5">
                <akn:num GUID="2ac56f11-1579-487f-99eb-551f09b2f8ba" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_bezeichnung-1"> e) </akn:num>
                <akn:content GUID="5dcf9578-b340-4c96-a2c2-4de1164f4731" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="794130a9-9e4b-4679-8c2a-94d6587d8c70" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-5_inhalt-1_text-1"> die Unabhängigkeit der Gerichte, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="cf0c13eb-999c-4a89-ad1f-0732a1a9a989" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6">
                <akn:num GUID="f55f423e-ca2d-4f8e-b60b-44c4f691fe14" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_bezeichnung-1"> f) </akn:num>
                <akn:content GUID="691a1b36-ea14-48be-96b3-e0050b8879a1" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_inhalt-1">
                  <akn:p GUID="6b778fba-b8d6-47c5-8584-91f1b5fa51b4" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-6_inhalt-1_text-1"> der Ausschluß jeder Gewalt- und Willkür*-herrschaft und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="c5bcad5b-b159-4d01-8210-db9a72c65ad0" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7">
                <akn:num GUID="c8172636-f7d0-41c2-8911-c474aad8f81e" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_bezeichnung-1"> g) </akn:num>
                <akn:content GUID="742517f7-2060-47e2-9633-de2da22b4429" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_inhalt-1">
                  <akn:p GUID="6b8b861c-5d04-42cb-9ce8-25efa35fd896" eId="hauptteil-1_abschnitt-1_art-4_abs-2_untergl-1_listenelem-7_inhalt-1_text-1"> die im Grundgesetz konkretisierten Menschenrechte. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="95c972ed-1364-4420-8fac-353717911ebd" eId="hauptteil-1_abschnitt-1_art-5" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="fffcdf7e-8bce-4237-9d31-ed1cfc772685" eId="hauptteil-1_abschnitt-1_art-5_bezeichnung-1"> § 5 </akn:num>
          <akn:heading GUID="01fc1842-9085-4e10-a202-6c8f0fc1dcd7" eId="hauptteil-1_abschnitt-1_art-5_überschrift-1"> Zuständigkeiten des Bundesamtes für Verfassungsschutz </akn:heading>
          <akn:paragraph GUID="8c556b5c-2ea7-4014-b175-3239115d16f9" eId="hauptteil-1_abschnitt-1_art-5_abs-1">
            <akn:num GUID="4291c6f1-d2ba-4698-ac96-1232a952c800" eId="hauptteil-1_abschnitt-1_art-5_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="779b5269-20a1-4de1-b022-82e5c565ce83" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1">
              <akn:intro GUID="823e163d-7484-4de8-b237-08b36522b074" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_intro-1">
                <akn:p GUID="d6562ada-554e-4b9c-9066-261494cdf70c" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf in einem Lande im Benehmen mit der Landesbehörde für Verfassungsschutz Informationen, Auskünfte, Nachrichten und Unterlagen im Sinne des § 3 sammeln. Bei Bestrebungen und Tätigkeiten im Sinne des § 3 Abs. 1 Nr. 1 bis 4 ist Voraussetzung, daß </akn:p>
              </akn:intro>
              <akn:point GUID="ccb9ec29-f5ee-4fdd-be1a-00299c483b0a" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="ae1f4e2d-8fab-4ac9-9449-1a26f6ec655d" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="190feee3-8db2-4ed3-b95f-fbdeb1a1be00" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="2b1eba82-ca64-4c8d-bd9f-04649d79cf57" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> sie sich ganz oder teilweise gegen den Bund richten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="5b952496-e52d-4842-8ed2-3570cc8e2ca9" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="998a5412-ccd1-419b-b3f6-85e4525425dd" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="7438bc30-b362-48cd-a085-3fa1d4df4e6c" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="7361693e-e0d7-4a3c-bfe3-26bd74a2d247" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> sie darauf gerichtet sind, Gewalt anzuwenden, Gewaltanwendung vorzubereiten, zu unterstützen oder zu befürworten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d05a4bd7-283b-4efd-8a3f-2ae3c4175d99" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="2e09046a-06a2-4faa-91d7-5a1479cb2eb9" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="110723d5-09f3-4a76-adeb-9e12776b0df8" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="b1df0bc1-70fa-4238-8a97-798382fc0b1b" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> sie sich über den Bereich eines Landes hinaus erstrecken, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="73689619-9aeb-40e9-8885-bbe0429d8896" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4">
                <akn:num GUID="921ab2ac-72b8-413b-aabd-59f46218fe95" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="d6cea46c-f14f-4c89-9fd6-1ce0b7960c14" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="89622ef6-b1d5-4342-93e0-ca42c0d8b106" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-4_inhalt-1_text-1"> sie auswärtige Belange der Bundesrepublik Deutschland berühren oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="561ceee8-8501-416e-96bb-11876ba5cdc9" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5">
                <akn:num GUID="4c763dc0-0fef-483a-b0f8-3dae918aab86" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:content GUID="4eecded9-0f94-46e4-be43-8d084f1869f7" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="26c732c3-961b-4b33-be15-1c6c6336ddf3" eId="hauptteil-1_abschnitt-1_art-5_abs-1_untergl-1_listenelem-5_inhalt-1_text-1"> eine Landesbehörde für Verfassungsschutz das Bundesamt für Verfassungsschutz um ein Tätigwerden ersucht. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="ee2bda98-345b-42d3-aa76-4a0d0a8b9adf" eId="hauptteil-1_abschnitt-1_art-5_abs-2">
            <akn:num GUID="43ead4f7-5581-4936-be59-b7543e1e8f3d" eId="hauptteil-1_abschnitt-1_art-5_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="e7493223-5cc0-43cd-842d-d46528e5d799" eId="hauptteil-1_abschnitt-1_art-5_abs-2_inhalt-1">
              <akn:p GUID="6cedb12f-92e9-482a-af3d-337045b2510d" eId="hauptteil-1_abschnitt-1_art-5_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz wertet unbeschadet der Auswertungsverpflichtungen der Landesbehörden für Verfassungsschutz zentral alle Erkenntnisse über Bestrebungen und Tätigkeiten im Sinne des § 3 Absatz 1 aus. Es unterrichtet die Landesbehörden für Verfassungsschutz nach § 6 Absatz 1, insbesondere durch Querschnittsauswertungen in Form von Struktur- und Methodik*-berichten sowie regelmäßig durch bundesweite Lageberichte zu den wesentlichen Phänomenbereichen unter Berücksichtigung der entsprechenden Landeslageberichte. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="384a416b-ba15-41d0-9b57-b993885e7186" eId="hauptteil-1_abschnitt-1_art-5_abs-3">
            <akn:num GUID="a49cd146-ce49-4c4a-be21-c2e21d3be46c" eId="hauptteil-1_abschnitt-1_art-5_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="4ff24c07-f31e-407c-abb3-e7228956d2ba" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1">
              <akn:intro GUID="fd3c2e30-0fc9-4f27-8e43-88bf12e44328" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_intro-1">
                <akn:p GUID="026c9302-a95d-4024-ba0b-d404a3141d98" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz koordiniert die Zusammenarbeit der Verfassungsschutzbehörden. Die Koordinierung schließt insbesondere die Vereinbarung von </akn:p>
              </akn:intro>
              <akn:point GUID="c08e39f8-0e83-49a8-89a7-6d4451477250" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="e08f144b-0efd-43ad-be3f-953d16298881" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="671dc9de-a72e-4f17-b9ce-de8ab8e38190" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="77b24a61-f4bc-45d0-860b-3595fd11b928" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> einheitlichen Vorschriften zur Gewährleistung der Zusammenarbeitsfähigkeit, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="f3b80a7a-38fa-45e1-be29-8079e43e1053" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="b465c9b0-a58b-4155-a91d-aa08c6fd787a" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="ba1c8712-73ad-40d8-86d6-f5285d1d13ae" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="cee91bbc-0521-45c8-8df0-809ba43ed2d6" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> allgemeinen Arbeitsschwerpunkten und arbeitsteiliger Durchführung der Aufgaben sowie </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="32dbfb50-5320-4076-b88a-3575ee90899e" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3">
                <akn:num GUID="40356411-55ec-4d1d-ac33-1e0b0b3d2375" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="d097e10f-6112-4805-aa3c-9180b0977e12" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="bb8b0267-11a1-42e4-b345-d5f6c7530416" eId="hauptteil-1_abschnitt-1_art-5_abs-3_untergl-1_listenelem-3_inhalt-1_text-1"> Relevanzkriterien für Übermittlungen nach § 6 Absatz 1 </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="8249bd87-73ce-4ffa-a0a3-0ad472da35da" eId="hauptteil-1_abschnitt-1_art-5_abs-4">
            <akn:num GUID="1c0bd6e8-9495-46ec-9906-8f07e00573fb" eId="hauptteil-1_abschnitt-1_art-5_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:list GUID="f7924d7b-af43-408a-bef6-0ba7183fdc05" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1">
              <akn:intro GUID="36339741-b2d5-4079-b6f7-74a234a66fd0" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_intro-1">
                <akn:p GUID="97bc03bc-2795-4d02-8bf4-a65b8675e4ba" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz unterstützt als Zentralstelle die Landesbehörden für Verfassungsschutz bei der Erfüllung ihrer Aufgaben nach § 3 insbesondere durch </akn:p>
              </akn:intro>
              <akn:point GUID="0a5e9bd0-127c-4d28-98ed-53c6b3914131" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1">
                <akn:num GUID="e326e74b-a318-4bd6-a56a-8919bfa19279" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="ec549cae-6192-4d4b-b7ee-4eac7ea82a4e" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="dbcb4503-b8fa-4b70-acfd-8441f4ead866" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-1_inhalt-1_text-1"> Bereitstellung des nachrichtendienstlichen Informationssystems (§ 6 Absatz 2), </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="f33b617b-e871-431f-935f-bd526ea6a6a9" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2">
                <akn:num GUID="1a8b077c-d02c-4333-9503-145c49ec7c04" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="039ad763-120a-4222-85a0-74ee2d6d5c1e" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="0bd7c9ab-e84c-4777-a4fe-c4e063e18050" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-2_inhalt-1_text-1"> zentrale Einrichtungen im Bereich besonderer technischer und fachlicher Fähigkeiten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="1f4bb883-aa31-4083-bc91-0a60944da687" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3">
                <akn:num GUID="ead4426a-418b-459b-b95f-f52dac5bc360" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="9006a9a2-ebec-453c-9c4a-a20d6bce7353" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="895b9304-14b9-497f-a572-ab383fcb054a" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-3_inhalt-1_text-1"> Erforschung und Entwicklung von Methoden und Arbeitsweisen im Verfassungsschutz und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="6553b683-0bfb-4368-b0ed-5b80bfe3c5d4" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4">
                <akn:num GUID="634edea0-6823-4a7c-b771-a7d2338b6256" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="e1389c02-b653-4d68-8be1-7443e052cc5f" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="826c8074-5932-4604-baa8-fd7c7db9a7be" eId="hauptteil-1_abschnitt-1_art-5_abs-4_untergl-1_listenelem-4_inhalt-1_text-1"> Fortbildung in speziellen Arbeitsbereichen. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="263f64ed-474f-4523-8522-a1a4621e3264" eId="hauptteil-1_abschnitt-1_art-5_abs-5">
            <akn:num GUID="095db046-f7d9-4e58-ba05-4e9ae82f1cae" eId="hauptteil-1_abschnitt-1_art-5_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:list GUID="25143dea-7001-4c45-a818-158f7fcec2a4" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1">
              <akn:intro GUID="bd12a5ec-9363-42f6-a386-c027bb2edcb1" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_intro-1">
                <akn:p GUID="d7010cb2-7481-4ebb-841a-c741473e009e" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_intro-1_text-1"> Dem Bundesamt für Verfassungsschutz obliegt der für Aufgaben nach § 3 erforderliche Dienstverkehr mit zuständigen öffentlichen Stellen anderer Staaten. Die Landesbehörden für Verfassungsschutz können solchen Dienstverkehr führen </akn:p>
              </akn:intro>
              <akn:point GUID="bee722cf-c547-4233-9dcf-00ccf58d9d9d" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1">
                <akn:num GUID="46802acf-db10-4ef7-9476-b29ef77ddc0d" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="3d940dfd-6724-4219-b1fd-1eef58a67d64" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="d2160612-7af8-4c3f-a5d1-a2c260132ea5" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-1_inhalt-1_text-1"> mit den Dienststellen der in der Bundesrepublik Deutschland stationierten Streitkräfte, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="e474133a-19c5-4bb5-b10e-72484e40c609" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2">
                <akn:num GUID="3121fca9-ff48-4862-9dac-9d569a24073a" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="abcad350-a4e1-4e78-8dca-33e773f57d1b" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="6046248c-8b14-4780-a3bb-b9470434fa7a" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-2_inhalt-1_text-1"> mit den Nachrichtendiensten angrenzender Nachbarstaaten in regionalen Angelegenheiten oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="796fdf8f-8ec2-403d-b887-91cc6f7260cd" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3">
                <akn:num GUID="4fce88a6-07df-4328-b61e-8781224adeb2" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="21cc6bec-124b-4bb2-9598-66dbc9907f7a" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="d95b21a2-c47e-4639-88d0-6c5becfa60d2" eId="hauptteil-1_abschnitt-1_art-5_abs-5_untergl-1_listenelem-3_inhalt-1_text-1"> im Einvernehmen mit dem Bundesamt für Verfassungsschutz. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="ab402b12-1cc5-404e-a06c-1a930861f0ab" eId="hauptteil-1_abschnitt-1_art-6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8ddac5c5-9a58-4d96-a183-b4837bf80c0c" eId="hauptteil-1_abschnitt-1_art-6_bezeichnung-1"> § 6 </akn:num>
          <akn:heading GUID="3d8f3306-50db-45bf-b12b-b0c623bea010" eId="hauptteil-1_abschnitt-1_art-6_überschrift-1"> Gegenseitige Unterrichtung der Verfassungsschutzbehörden </akn:heading>
          <akn:paragraph GUID="c8a74e2b-4f55-4ea8-8811-7d9316a743d1" eId="hauptteil-1_abschnitt-1_art-6_abs-1">
            <akn:num GUID="7f4f76f6-f666-4952-9a5a-b139e27efdaf" eId="hauptteil-1_abschnitt-1_art-6_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="cc96f2d1-df89-470f-8aa2-133e48ab6b14" eId="hauptteil-1_abschnitt-1_art-6_abs-1_inhalt-1">
              <akn:p GUID="9d8c59da-1054-4e88-8ef6-22987f00d8f9" eId="hauptteil-1_abschnitt-1_art-6_abs-1_inhalt-1_text-1"> Die Landesbehörden für Verfassungsschutz und das Bundesamt für Verfassungsschutz übermitteln sich unverzüglich die für ihre Aufgaben relevanten Informationen, einschließlich der Erkenntnisse ihrer Auswertungen. Wenn eine übermittelnde Behörde sich dies vorbehält, dürfen die übermittelten Daten nur mit ihrer Zustimmung an Stellen außerhalb der Behörden für Verfassungsschutz übermittelt werden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d4763baa-b9aa-4a59-8929-4cef56338192" eId="hauptteil-1_abschnitt-1_art-6_abs-2">
            <akn:num GUID="4f48c578-1f1e-471f-bbdc-40550646418d" eId="hauptteil-1_abschnitt-1_art-6_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="02a753d0-c3b5-487c-8fce-78608ff040f3" eId="hauptteil-1_abschnitt-1_art-6_abs-2_inhalt-1">
              <akn:p GUID="32f07f24-c5b1-4ec9-9f67-2e3957aa1a93" eId="hauptteil-1_abschnitt-1_art-6_abs-2_inhalt-1_text-1">Die Verfassungsschutzbehörden verarbeiten zur Erfüllung ihrer Unterrichtungspflichten nach Absatz 1 Informationen im gemeinsamen nachrichtendienstlichen Informationssystem. Der Militärische Abschirmdienst kann zur Erfüllung der Unterrichtungspflichten nach § 3 Absatz 3 Satz 1 des MAD-Gesetzes am nachrichtendienstlichen Informationssystem teilnehmen. Der Abruf von Daten aus dem nachrichtendienstlichen Informationssystem im automatisierten Verfahren ist im Übrigen nur entsprechend den §§ 22a und 22b zulässig. Für die Verarbeitung personenbezogener Daten im nachrichtendienstlichen Informationssystem gelten die §§ 10 und 11. Die Verantwortung einer speichernden Stelle im Sinne der allgemeinen Vorschriften des Datenschutzrechts trägt jede Verfassungsschutzbehörde nur für die von ihr eingegebenen Daten; nur sie darf diese Daten verändern, die Verarbeitung einschränken oder löschen. Die eingebende Stelle muss feststellbar sein. Eine Abfrage von Daten ist nur zulässig, soweit dies zur Erfüllung von Aufgaben, mit denen der Abfragende unmittelbar betraut ist, erforderlich ist. Die Zugriffsberechtigung auf Daten, die nicht zum Auffinden von Akten und der dazu notwendigen Identifizierung von Personen erforderlich sind, ist auf Personen zu beschränken, die mit der Erfassung von Daten oder Analysen betraut sind. Die Zugriffsberechtigung auf Unterlagen, die gespeicherte Angaben belegen, ist zudem auf Personen zu beschränken, die unmittelbar mit Arbeiten in diesem
                Anwendungsgebiet betraut sind.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="e1c41284-82e7-4496-a8b2-ebf35f4aa5b9" eId="hauptteil-1_abschnitt-1_art-6_abs-3">
            <akn:num GUID="355ce5f0-a7c3-4cb9-9a88-da9ae31bba4d" eId="hauptteil-1_abschnitt-1_art-6_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="0c584fb8-1c6a-4b82-b1c1-1ecc2db4485a" eId="hauptteil-1_abschnitt-1_art-6_abs-3_inhalt-1">
              <akn:p GUID="037199ba-61aa-4bb6-93aa-7dc09671b58b" eId="hauptteil-1_abschnitt-1_art-6_abs-3_inhalt-1_text-1">Das Bundesamt für Verfassungsschutz trifft für die gemeinsamen Dateien die technischen und organisatorischen Maßnahmen entsprechend § 64 des Bundesdatenschutzgesetzes. Es hat bei jedem Zugriff für Zwecke der Datenschutzkontrolle den Zeitpunkt, die Angaben, die die Feststellung der abgefragten Datensätze ermöglichen, sowie die abfragende Stelle zu protokollieren. Die Auswertung der Protokolldaten ist nach dem Stand der Technik zu gewährleisten. Die protokollierten Daten dürfen nur für Zwecke der Datenschutzkontrolle, der Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebs der Datenverarbeitungsanlage verwendet werden. Die Protokolldaten sind am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt, zu löschen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="d4fed6c8-beca-44fa-aa48-8e5f75125b2d" eId="hauptteil-1_abschnitt-1_art-7" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="dbfb624e-a6de-4530-8b40-4f3a368545db" eId="hauptteil-1_abschnitt-1_art-7_bezeichnung-1"> § 7 </akn:num>
          <akn:heading GUID="4dfb5aa2-bfc8-4465-8daf-78cea235aa5c" eId="hauptteil-1_abschnitt-1_art-7_überschrift-1"> Weisungsrechte des Bundes </akn:heading>
          <akn:paragraph GUID="05ac784f-448b-4ebf-aaa2-65ec41274e0b" eId="hauptteil-1_abschnitt-1_art-7_abs-1">
            <akn:num GUID="a0d15f5d-a563-4758-975c-92a642836dbf" eId="hauptteil-1_abschnitt-1_art-7_abs-1_bezeichnung-1"/>
            <akn:content GUID="718cf408-a336-47b5-879e-ec37ec997796" eId="hauptteil-1_abschnitt-1_art-7_abs-1_inhalt-1">
              <akn:p GUID="71a89522-0f37-42cb-83df-9ee5f7ebe6b5" eId="hauptteil-1_abschnitt-1_art-7_abs-1_inhalt-1_text-1"> Die Bundesregierung kann, wenn ein Angriff auf die verfassungsmäßige Ordnung des Bundes erfolgt, den obersten Landesbehörden die für die Zusammenarbeit der Länder mit dem Bund auf dem Gebiete des Verfassungsschutzes erforderlichen Weisungen erteilen. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:section>
      <akn:section GUID="2517df10-0b2f-40ea-88ef-914e2c6d277b" eId="hauptteil-1_abschnitt-2">
        <akn:num GUID="ea6aa164-1a82-4e84-b332-567ac9310239" eId="hauptteil-1_abschnitt-2_bezeichnung-1"> Zweiter Abschnitt </akn:num>
        <akn:heading GUID="01972194-4c51-476c-9d5d-6508319095b9" eId="hauptteil-1_abschnitt-2_überschrift-1"> Bundesamt für Verfassungsschutz </akn:heading>
        <akn:article GUID="09484348-4ce2-4959-88a0-bc817d1d504b" eId="hauptteil-1_abschnitt-2_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="a7d915af-3b47-43bd-8384-5a251609ffc3" eId="hauptteil-1_abschnitt-2_art-1_bezeichnung-1"> § 8 </akn:num>
          <akn:heading GUID="43737aee-e995-44a1-9fd2-9956ecc994ad" eId="hauptteil-1_abschnitt-2_art-1_überschrift-1"> Befugnisse des Bundesamtes für Verfassungsschutz </akn:heading>
          <akn:paragraph GUID="14413e9e-2681-48f8-a390-ae14763dc675" eId="hauptteil-1_abschnitt-2_art-1_abs-1">
            <akn:num GUID="489705ac-36d6-46f9-9969-131a20346922" eId="hauptteil-1_abschnitt-2_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="51136215-4ede-49dc-821c-4e6b4169fbbb" eId="hauptteil-1_abschnitt-2_art-1_abs-1_inhalt-1">
              <akn:p GUID="9d31cea1-16ed-4359-9116-8461e666452d" eId="hauptteil-1_abschnitt-2_art-1_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf die zur Erfüllung seiner Aufgaben erforderlichen Informationen einschließlich personenbezogener Daten verarbeiten, soweit nicht die anzuwendenden Bestimmungen des Bundesdatenschutzgesetzes oder besondere Regelungen in diesem Gesetz entgegenstehen; die Verarbeitung ist auch zulässig, wenn der Betroffene eingewilligt hat. Ein Ersuchen des Bundesamtes für Verfassungsschutz um Übermittlung personenbezogener Daten darf nur diejenigen personenbezogenen Daten enthalten, die für die Erteilung der Auskunft unerlässlich sind. Schutzwürdige Interessen des Betroffenen dürfen nur in unvermeidbarem Umfang beeinträchtigt werden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="0da9d3d0-54a0-424e-9589-40aa4fe46e8b" eId="hauptteil-1_abschnitt-2_art-1_abs-2">
            <akn:num GUID="d4be4679-d4ab-4c2a-b9c1-ef96e2a9ffac" eId="hauptteil-1_abschnitt-2_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="f3bfdf88-0653-4aa5-9e0d-92c30e0bf9c7" eId="hauptteil-1_abschnitt-2_art-1_abs-2_inhalt-1">
              <akn:p GUID="5d95614c-cf5f-4e62-baa9-0dcaa18e69d4" eId="hauptteil-1_abschnitt-2_art-1_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf Methoden, Gegenstände und Instrumente zur heimlichen Informationsbeschaffung, wie den Einsatz von Vertrauensleuten und Gewährspersonen, Observationen, Bild- und Ton*-aufzeichnungen, Tarnpapiere und Tarnkennzeichen anwenden. In Individualrechte darf nur nach Maßgabe besonderer Befugnisse eingegriffen werden. Im Übrigen darf die Anwendung eines Mittels gemäß Satz 1 keinen Nachteil herbeiführen, der erkennbar außer Verhältnis zur Bedeutung des aufzuklärenden Sachverhalts steht. Die Mittel nach Satz 1 sind in einer Dienstvorschrift zu benennen, die auch die Zuständigkeit für die Anordnung solcher Informationsbeschaffungen und das Nähere zu Satz 3 regelt. Die Dienstvorschrift bedarf der Zustimmung des Bundesministeriums des Innern, für Bau und Heimat, das das Parlamentarische Kontrollgremium unterrichtet. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="c3988b95-20ab-49ec-9f4b-16c7986f87a7" eId="hauptteil-1_abschnitt-2_art-1_abs-3">
            <akn:num GUID="613d55f3-764d-4e94-935f-e1cce064ead5" eId="hauptteil-1_abschnitt-2_art-1_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="348b7bce-05bb-4a50-830e-6322ca61e067" eId="hauptteil-1_abschnitt-2_art-1_abs-3_inhalt-1">
              <akn:p GUID="4064975d-d2b1-4485-889f-a59c4f2ec4e2" eId="hauptteil-1_abschnitt-2_art-1_abs-3_inhalt-1_text-1"> Polizeiliche Befugnisse oder Weisungsbefugnisse stehen dem Bundesamt für Verfassungsschutz nicht zu; es darf die Polizei auch nicht im Wege der Amtshilfe um Maßnahmen ersuchen, zu denen es selbst nicht befugt ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d89e5d4e-4dd0-4b32-a11d-c50b311f1598" eId="hauptteil-1_abschnitt-2_art-1_abs-4">
            <akn:num GUID="60c828e9-5b80-4194-a9dc-91bdb197ee68" eId="hauptteil-1_abschnitt-2_art-1_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="9ab4c8ed-2b75-45b8-aabb-ffd3278d7edb" eId="hauptteil-1_abschnitt-2_art-1_abs-4_inhalt-1">
              <akn:p GUID="32f8d7e4-9331-41a2-8503-32744ff292c4" eId="hauptteil-1_abschnitt-2_art-1_abs-4_inhalt-1_text-1"> Werden personenbezogene Daten beim Betroffenen mit seiner Kenntnis erhoben, so ist der Erhebungszweck anzugeben. Der Betroffene ist auf die Freiwilligkeit seiner Angaben hinzuweisen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="9426a0dc-13fa-43a2-b75e-bfc412f2cd55" eId="hauptteil-1_abschnitt-2_art-1_abs-5">
            <akn:num GUID="ac52215e-3e3f-4218-8d41-b0e2a8df9483" eId="hauptteil-1_abschnitt-2_art-1_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="416675e6-22cb-444b-98be-96368260a62b" eId="hauptteil-1_abschnitt-2_art-1_abs-5_inhalt-1">
              <akn:p GUID="f0c2cdd0-73bb-4473-b83f-18109a890bca" eId="hauptteil-1_abschnitt-2_art-1_abs-5_inhalt-1_text-1"> Von mehreren geeigneten Maßnahmen hat das Bundesamt für Verfassungsschutz diejenige zu wählen, die den Betroffenen voraussichtlich am wenigsten beeinträchtigt. Eine Maßnahme darf keinen Nachteil herbeiführen, der erkennbar außer Verhältnis zu dem beabsichtigten Erfolg steht. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="bde87aee-1208-4046-ae60-f361dc8520df" eId="hauptteil-1_abschnitt-2_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="dca917d2-fd3f-40fe-b033-82cac10440d3" eId="hauptteil-1_abschnitt-2_art-2_bezeichnung-1"> § 8a </akn:num>
          <akn:heading GUID="30ebf38e-75b4-4104-bfcb-67ea016eed4b" eId="hauptteil-1_abschnitt-2_art-2_überschrift-1"> Besondere Auskunftsverlangen </akn:heading>
          <akn:paragraph GUID="8a373106-05f9-4607-ac08-8f45af364eb5" eId="hauptteil-1_abschnitt-2_art-2_abs-1">
            <akn:num GUID="f79070ff-b39d-4903-a608-5e9744299236" eId="hauptteil-1_abschnitt-2_art-2_abs-1_bezeichnung-1"/>
            <akn:content GUID="72206c24-d2d8-4efd-a9d6-bb6694b57fa6" eId="hauptteil-1_abschnitt-2_art-2_abs-1_inhalt-1">
              <akn:p GUID="804a2832-b2a0-4dcb-b152-90dbf9ed5f2e" eId="hauptteil-1_abschnitt-2_art-2_abs-1_inhalt-1_text-1"> (2a) Soweit dies zur Sammlung und Auswertung von Informationen erforderlich ist und Tatsachen die Annahme rechtfertigen, dass schwerwiegende Gefahren für die in § 3 Absatz 1 genannten Schutzgüter vorliegen, darf das Bundesamt für Verfassungsschutz im Einzelfall das Bundeszentralamt für Steuern ersuchen, bei den Kreditinstituten die in § 93b Absatz 1 der Abgabenordnung bezeichneten Daten abzurufen. § 93 Absatz 9 der Abgabenordnung findet keine Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="b7f64199-a44d-4b3a-808c-bb2fd344b331" eId="hauptteil-1_abschnitt-2_art-2_abs-2">
            <akn:num GUID="d03f76e4-3501-4cac-abfe-96aaaefae157" eId="hauptteil-1_abschnitt-2_art-2_abs-2_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="5542e36b-afe9-4855-bad2-3e3e8e0f06f6" eId="hauptteil-1_abschnitt-2_art-2_abs-2_inhalt-1">
              <akn:p GUID="ef53e07a-1ec4-462c-86d3-fe31883a3ac6" eId="hauptteil-1_abschnitt-2_art-2_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf im Einzelfall bei denjenigen, die geschäftsmäßig Teledienste erbringen oder daran mitwirken, Auskunft über Daten einholen, die für die Begründung, inhaltliche Ausgestaltung, Änderung oder Beendigung eines Vertragsverhältnisses über Teledienste (Bestandsdaten) gespeichert worden sind, soweit dies zur Sammlung und Auswertung von Informationen erforderlich ist und tatsächliche Anhaltspunkte für schwerwiegende Gefahren für die in § 3 Absatz 1 genannten Schutzgüter vorliegen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="11b24c2b-74e0-422b-bdf4-8d53bae2e57b" eId="hauptteil-1_abschnitt-2_art-2_abs-3">
            <akn:num GUID="fb5d2d18-32e8-468c-b101-5ae879983b2b" eId="hauptteil-1_abschnitt-2_art-2_abs-3_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="c1f19d23-430f-4c8a-8c3f-6f11f5bbe6d2" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1">
              <akn:intro GUID="ad89bfdd-4cb0-444d-92cf-eca72ad0174a" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_intro-1">
                <akn:p GUID="618a0650-7ea8-4777-8c5a-cdba43c6de0a" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf im Einzelfall Auskunft einholen bei </akn:p>
              </akn:intro>
              <akn:point GUID="b552eb05-c2a7-4762-bf59-fdf588b8cf33" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="d4dce0d3-c0cc-4da0-8ebe-1e7bde4f4c60" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="af7237ff-7c0a-498b-b0ca-e70345fa6f52" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="c0f21346-ed21-4801-b0ed-2ca915a951a3" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> Luftfahrtunternehmen sowie Betreibern von Computerreservierungssystemen und Globalen Distributionssystemen für Flüge zu Namen und Anschriften des Kunden sowie zur Inanspruchnahme und den Umständen von Transportleistungen, insbesondere zum Zeitpunkt von Abfertigung und Abflug und zum Buchungsweg, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="85417515-ed23-4b9e-88bf-7e8a24cbbdd4" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="8cffdd4e-67bf-4535-9a43-699733069166" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="a817ae0d-9b95-4dc4-81ac-1bdae5215a9a" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="ddc8606f-7c06-44af-bffb-7e7548a4becf" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> Kreditinstituten, Finanzdienstleistungsinstituten und Finanzunternehmen zu Konten, Konteninhabern und sonstigen Berechtigten sowie weiteren am Zahlungsverkehr Beteiligten und zu Geldbewegungen und Geldanlagen, insbesondere über Kontostand und Zahlungsein- und -ausgänge, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="7df283b1-d859-4923-9838-9c0d240c182e" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3">
                <akn:num GUID="7b8f7430-505b-4f9e-885a-bb6a5f6126dc" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="a8454e80-8876-4958-83d4-65ba61a85569" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="1343f972-dd66-4f22-ba5a-a1232c498d50" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-3_inhalt-1_text-1"> (weggefallen) </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d9a3caef-11ff-49d8-93c4-017d57169e53" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4">
                <akn:num GUID="af565d94-283a-4d08-bbe8-a3a475bfe994" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="1f8af8a5-dd29-4154-8723-50b4dbe4d5e5" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="425ce5aa-b8ff-4b4b-b7c9-43c708f7062f" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-4_inhalt-1_text-1"> denjenigen, die geschäftsmäßig Telekommunikationsdienste erbringen oder daran mitwirken, zu Verkehrsdaten nach § 96 Abs. 1 Nr. 1 bis 4 des Telekommunikationsgesetzes und sonstigen zum Aufbau und zur Aufrechterhaltung der Telekommunikation notwendigen Verkehrsdaten und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="8f8484a0-7a6e-4093-9722-0275265f4b68" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5">
                <akn:num GUID="0fb469fd-ae82-48c7-b7cb-6f0a51c1b6f6" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:list GUID="08a27199-7dc5-4f72-a9cb-9480cb707ff6" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1">
                  <akn:intro GUID="68c6691c-f234-4f0c-82b9-1d9a5b67d75f" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_intro-1">
                    <akn:p GUID="dd6f6253-d038-4d23-a809-f34c74eb4f43" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_intro-1_text-1"> denjenigen, die geschäftsmäßig Teledienste erbringen oder daran mitwirken, zu </akn:p>
                  </akn:intro>
                  <akn:point GUID="fae9ae95-44b6-41c6-ba47-88af2a522597" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1">
                    <akn:num GUID="3ee330ed-7ca6-4824-9baa-c83b68fb91ec" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_bezeichnung-1"> a) </akn:num>
                    <akn:content GUID="dc1dcc3d-d091-4a79-add4-5b4a4920945f" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1">
                      <akn:p GUID="25ae69a3-8b06-4369-afe9-b511ad9cfa7c" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1"> Merkmalen zur Identifikation des Nutzers eines Teledienstes, </akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="f23f4ee4-9d5b-4996-b7dd-5c43c1a5ce07" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2">
                    <akn:num GUID="510214a3-3b5c-435c-ab46-02298126e24d" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_bezeichnung-1"> b) </akn:num>
                    <akn:content GUID="345101c8-8959-4be0-b62c-3b6160d0628d" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_inhalt-1">
                      <akn:p GUID="f75933d9-5a86-4aa8-a9a3-1488edcf1934" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-2_inhalt-1_text-1"> Angaben über Beginn und Ende sowie über den Umfang der jeweiligen Nutzung und </akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="200a130c-b7a8-4d82-8d26-d36427ba6f08" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3">
                    <akn:num GUID="8d9bdf2f-cda4-4d77-9e0a-94ae67690956" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_bezeichnung-1"> c) </akn:num>
                    <akn:content GUID="958cf0b9-79c6-4812-9ce6-a0d5685ba05f" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_inhalt-1">
                      <akn:p GUID="4d34329b-af38-4180-8e16-2e441cb4eaf9" eId="hauptteil-1_abschnitt-2_art-2_abs-3_untergl-1_listenelem-5_untergl-1_listenelem-3_inhalt-1_text-1"> Angaben über die vom Nutzer in Anspruch genommenen Teledienste, </akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="d7c1b530-e160-4893-944e-078e45b7d658" eId="hauptteil-1_abschnitt-2_art-2_abs-4">
            <akn:num GUID="e6b57700-2170-41c4-8df7-6c1b309ae6c8" eId="hauptteil-1_abschnitt-2_art-2_abs-4_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="fcf30fdb-f648-4c17-b7b1-34d46b8752a4" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1">
              <akn:intro GUID="2e803827-c0a9-475e-a575-72e6e7a9e587" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_intro-1">
                <akn:p GUID="6b8e337b-df51-4352-ae83-8b31a9bc1a3c" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_intro-1_text-1"> Anordnungen nach den Absätzen 2 und 2a dürfen sich nur gegen Personen richten, bei denen </akn:p>
              </akn:intro>
              <akn:point GUID="fda35602-1718-406a-ad51-9f8d630f6b52" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1">
                <akn:num GUID="d2168479-19f8-4da6-ab11-a5e4d9c5a29a" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="69646fc1-e765-40c8-b435-8ed9b023a321" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="0b23c0b6-4062-49f4-b3e8-372bf8b14f87" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-1_inhalt-1_text-1"> tatsächliche Anhaltspunkte dafür vorliegen, dass sie die schwerwiegenden Gefahren nach den Absätzen 2 oder 2a nachdrücklich fördern, oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ea47c1f3-f187-49e9-a1c2-42de856381ed" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2">
                <akn:num GUID="f2afd8ae-d9b4-4e04-bf43-f771aff8c284" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:list GUID="6b15c508-5b83-4fd6-b31f-3e841a9fb647" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1">
                  <akn:intro GUID="e9d49059-f867-49ab-9f53-d747e4789a8d" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_intro-1">
                    <akn:p GUID="ac97b273-0383-4586-afde-8d010c2e7961" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_intro-1_text-1"> auf Grund bestimmter Tatsachen anzunehmen ist </akn:p>
                  </akn:intro>
                  <akn:point GUID="296433fa-b21b-4eb0-b1d2-bb81fb6b7314" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1">
                    <akn:num GUID="930933f5-cf1a-466b-9d9e-05a8b29459f9" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_bezeichnung-1"> a) </akn:num>
                    <akn:content GUID="2b6fabc7-5fcd-4fcd-a8f2-c661cf44aac0" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1">
                      <akn:p GUID="ff3d87d7-74be-4afd-a69f-662c35631b5a" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1"> bei Auskünften nach Absatz 2 Satz 1 Nr. 1, 2 und 5 sowie nach Absatz 2a, dass sie die Leistung für eine Person nach Nummer 1 in Anspruch nehmen, oder </akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="c2d40923-12ac-4248-8aab-6e55f904219e" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2">
                    <akn:num GUID="ff011eba-35b2-4ac3-925f-0bcf8081b4d3" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_bezeichnung-1"> b) </akn:num>
                    <akn:content GUID="e21db29f-7930-4666-94ff-115517bcf247" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1">
                      <akn:p GUID="025dec2e-e829-43a8-9ac7-20984402364c" eId="hauptteil-1_abschnitt-2_art-2_abs-4_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1"> bei Auskünften nach Absatz 2 Satz 1 Nummer 4, dass sie für eine Person nach Nummer 1 bestimmte oder von ihr herrührende Mitteilungen entgegennehmen oder weitergeben, oder dass eine Person nach Nummer 1 ihren Anschluss benutzt. </akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="4e8ed78b-24f5-46bb-95f2-1b84a60dc0a4" eId="hauptteil-1_abschnitt-2_art-2_abs-5">
            <akn:num GUID="2f07424a-537f-4ad3-a05b-bb354e9be8b8" eId="hauptteil-1_abschnitt-2_art-2_abs-5_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="242ae8be-6e5f-4bfc-accf-36f226bda783" eId="hauptteil-1_abschnitt-2_art-2_abs-5_inhalt-1">
              <akn:p GUID="1255c9d8-210c-4652-a9f4-6031107080da" eId="hauptteil-1_abschnitt-2_art-2_abs-5_inhalt-1_text-1"> bis (9) (weggefallen) </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="0a3635d7-3ac2-448e-89ae-fb5a87512a78" eId="hauptteil-1_abschnitt-2_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="3f7600c1-8f87-4a1a-8f43-f8c9ebffab17" eId="hauptteil-1_abschnitt-2_art-3_bezeichnung-1"> § 8b </akn:num>
          <akn:heading GUID="ca453996-f753-496a-8f77-1f478132cd01" eId="hauptteil-1_abschnitt-2_art-3_überschrift-1"> Verfahrensregelungen zu besonderen Auskunftsverlangen </akn:heading>
          <akn:paragraph GUID="3a388a87-5b0f-458e-a81c-2f38dfe982a4" eId="hauptteil-1_abschnitt-2_art-3_abs-1">
            <akn:num GUID="ef4b0e37-d38d-45a0-be96-584f6803ef2c" eId="hauptteil-1_abschnitt-2_art-3_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="8c7d6066-8d0a-4a41-b883-63df8255c0f6" eId="hauptteil-1_abschnitt-2_art-3_abs-1_inhalt-1">
              <akn:p GUID="a59c8909-8874-43bb-9516-474799b40c5e" eId="hauptteil-1_abschnitt-2_art-3_abs-1_inhalt-1_text-1"> Anordnungen nach § 8a Absatz 1 und 2 werden vom Behördenleiter oder seinem Vertreter beantragt; der Antrag ist schriftlich zu stellen und zu begründen. Zuständig für die Anordnungen ist das Bundesministerium des Innern, für Bau und Heimat. Die Anordnung einer Auskunft über künftig anfallende Daten ist auf höchstens drei Monate zu befristen. Die Verlängerung dieser Anordnung um jeweils nicht mehr als drei Monate ist auf Antrag zulässig, soweit die Voraussetzungen der Anordnung fortbestehen. Auf die Anordnung der Verlängerung finden die Sätze 1 und 2 Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="44bc1e45-69ec-4606-8cda-e3bca7ad0e1a" eId="hauptteil-1_abschnitt-2_art-3_abs-2">
            <akn:num GUID="7728c501-2cbf-4507-bf9b-92fb40736a54" eId="hauptteil-1_abschnitt-2_art-3_abs-2_bezeichnung-1"> (10) </akn:num>
            <akn:content GUID="cc5f412d-0cf5-4494-b4e0-69ed18843688" eId="hauptteil-1_abschnitt-2_art-3_abs-2_inhalt-1">
              <akn:p GUID="b6844c7d-e19c-48b3-be6e-48df121b563d" eId="hauptteil-1_abschnitt-2_art-3_abs-2_inhalt-1_text-1"> Die Befugnisse nach § 8a Absatz 1 Satz 1 Nummer 4 und 5 stehen den Verfassungsschutzbehörden der Länder nur dann zu, wenn das Verfahren sowie die Beteiligung der G 10-Kommission, die Verarbeitung der erhobenen Daten und die Mitteilung an den Betroffenen gleichwertig wie in Absatz 2 und ferner eine Absatz 3 gleichwertige parlamentarische Kontrolle sowie eine Verpflichtung zur Berichterstattung über die durchgeführten Maßnahmen an das Parlamentarische Kontrollgremium des Bundes unter entsprechender Anwendung des Absatzes 3 Satz 1 zweiter Halbsatz für dessen Berichte nach Absatz 3 Satz 2 durch den Landesgesetzgeber geregelt ist. Die Verpflichtungen zur gleichwertigen parlamentarischen Kontrolle nach Absatz 3 gelten auch für die Befugnisse nach § 8a Absatz 1 Satz 1 Nummer 1 und 2. Landesrecht kann für Auskünfte an die jeweilige Verfassungsschutzbehörde des Landes Regelungen vorsehen, die dem Absatz 5 entsprechen, und die auf Grund von Absatz 8 Satz 1 bis 3 erlassene Rechtsverordnung sowie die Vorgaben nach Absatz 8 Satz 4 und 5 für solche Auskünfte für anwendbar erklären. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="fc24a393-1e74-44d7-80fa-785445783105" eId="hauptteil-1_abschnitt-2_art-3_abs-3">
            <akn:num GUID="319b9fee-e219-4ca1-9baa-936be9166ba9" eId="hauptteil-1_abschnitt-2_art-3_abs-3_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="658fa711-ac49-45a6-885d-71a4cedad5a0" eId="hauptteil-1_abschnitt-2_art-3_abs-3_inhalt-1">
              <akn:p GUID="cdfe2307-0526-4404-bdd2-75fc06afdf39" eId="hauptteil-1_abschnitt-2_art-3_abs-3_inhalt-1_text-1"> Über Anordnungen nach § 8a Absatz 1 und 2 unterrichtet das Bundesministerium des Innern, für Bau und Heimat monatlich die G 10-Kommission (§ 1 Absatz 2 des Artikel 10-Gesetzes) vor deren Vollzug. Bei Gefahr im Verzug kann es den Vollzug der Entscheidung auch bereits vor der Unterrichtung der G 10-Kommission anordnen. Die G 10-Kommission prüft von Amts wegen oder auf Grund von Beschwerden die Zulässigkeit und Notwendigkeit der Einholung von Auskünften. § 15 Absatz 5 des Artikel 10-Gesetzes ist mit der Maßgabe entsprechend anzuwenden, dass die Kontrollbefugnis der Kommission sich auf die gesamte Verarbeitung der nach § 8a Absatz 1 und 2 erlangten personenbezogenen Daten erstreckt. Entscheidungen über Auskünfte, welche die G 10-Kommission für unzulässig oder nicht notwendig erklärt, hat das Bundesministerium des Innern, für Bau und Heimat unverzüglich aufzuheben. Die Daten unterliegen in diesem Falle einem absoluten Verwendungsverbot und sind unverzüglich zu löschen. Für die Verarbeitung der nach § 8a Absatz 1 und 2 erhobenen Daten ist § 4 des Artikel 10-Gesetzes entsprechend anzuwenden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="1c1b1c2e-e04b-4efb-a11a-5cf555cad1d0" eId="hauptteil-1_abschnitt-2_art-3_abs-4">
            <akn:num GUID="7de0bcc6-7fe0-4653-a3eb-9d03d0e9170b" eId="hauptteil-1_abschnitt-2_art-3_abs-4_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="22581c0c-f0d1-406a-9807-fb554beeb292" eId="hauptteil-1_abschnitt-2_art-3_abs-4_inhalt-1">
              <akn:p GUID="5ce2573c-6a72-4df3-93c1-063bfd252a98" eId="hauptteil-1_abschnitt-2_art-3_abs-4_inhalt-1_text-1"> Das Bundesministerium des Innern, für Bau und Heimat unterrichtet im Abstand von höchstens sechs Monaten das Parlamentarische Kontrollgremium über Anordnungen nach § 8a Absatz 1 und 2; dabei ist insbesondere ein Überblick über Anlass, Umfang, Dauer, Ergebnis und Kosten der im Berichtszeitraum durchgeführten Maßnahmen zu geben. Das Gremium erstattet dem Deutschen Bundestag jährlich einen Bericht über die Durchführung sowie Art, Umfang und Anordnungsgründe der Maßnahmen; dabei sind die Grundsätze des § 10 Absatz 1 des Kontrollgremiumgesetzes zu beachten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="6f2c157b-316f-4db8-8732-be79ad8ca842" eId="hauptteil-1_abschnitt-2_art-3_abs-5">
            <akn:num GUID="894fc6cc-3552-472d-b064-64907631b9dc" eId="hauptteil-1_abschnitt-2_art-3_abs-5_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="61c2ef12-580c-4d06-a7da-6cbe0946142e" eId="hauptteil-1_abschnitt-2_art-3_abs-5_inhalt-1">
              <akn:p GUID="b486b5b5-3d8a-483c-840e-00d9d88afe2e" eId="hauptteil-1_abschnitt-2_art-3_abs-5_inhalt-1_text-1"> Anordnungen sind dem Verpflichteten insoweit schriftlich mitzuteilen, als dies erforderlich ist, um ihm die Erfüllung seiner Verpflichtung zu ermöglichen. Anordnungen und übermittelte Daten dürfen dem Betroffenen oder Dritten vom Verpflichteten nicht mitgeteilt werden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="3db9952c-712e-4c8b-a286-99117df5f8f6" eId="hauptteil-1_abschnitt-2_art-3_abs-6">
            <akn:num GUID="a9a2ecc1-e98d-4221-b551-9ff573003a33" eId="hauptteil-1_abschnitt-2_art-3_abs-6_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="9922e20a-eba2-4a0d-ad5f-1b756b5913c3" eId="hauptteil-1_abschnitt-2_art-3_abs-6_inhalt-1">
              <akn:p GUID="0a415295-c24c-408e-9d69-ba9bd816573c" eId="hauptteil-1_abschnitt-2_art-3_abs-6_inhalt-1_text-1"> Dem Verpflichteten ist es verboten, allein auf Grund einer Anordnung nach § 8a Absatz 1 einseitige Handlungen vorzunehmen, die für den Betroffenen nachteilig sind und die über die Erteilung der Auskunft hinausgehen, insbesondere bestehende Verträge oder Geschäftsverbindungen zu beenden, ihren Umfang zu beschränken oder ein Entgelt zu erheben oder zu erhöhen. Die Anordnung ist mit dem ausdrücklichen Hinweis auf dieses Verbot und darauf zu verbinden, dass das Auskunftsersuchen nicht die Aussage beinhaltet, dass sich die betroffene Person rechtswidrig verhalten hat oder ein darauf gerichteter Verdacht bestehen müsse. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="ad277b09-00be-492c-bc5f-f8ba96db6d90" eId="hauptteil-1_abschnitt-2_art-3_abs-7">
            <akn:num GUID="f289c074-bccd-4306-9f6b-5f540b9c42e8" eId="hauptteil-1_abschnitt-2_art-3_abs-7_bezeichnung-1"> (6) </akn:num>
            <akn:content GUID="39d288d4-c99a-4a10-ae2d-9e1091002530" eId="hauptteil-1_abschnitt-2_art-3_abs-7_inhalt-1">
              <akn:p GUID="74276180-be45-4cec-9a9d-523eb0ee7513" eId="hauptteil-1_abschnitt-2_art-3_abs-7_inhalt-1_text-1"> Die in § 8a Absatz 1 Satz 1 genannten Stellen sind verpflichtet, die Auskunft unverzüglich und vollständig und in dem Format zu erteilen, das durch die auf Grund von Absatz 8 Satz 1 bis 3 erlassene Rechtsverordnung oder in den in Absatz 8 Satz 4 und 5 bezeichneten Rechtsvorschriften vorgeschrieben ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d666a4b3-3755-4620-a5f7-8410721e5cb3" eId="hauptteil-1_abschnitt-2_art-3_abs-8">
            <akn:num GUID="6bb351ee-1996-4ab3-8613-41f1b061d716" eId="hauptteil-1_abschnitt-2_art-3_abs-8_bezeichnung-1"> (7) </akn:num>
            <akn:content GUID="424aa709-726a-4cff-86ff-2c1c07a1b8ed" eId="hauptteil-1_abschnitt-2_art-3_abs-8_inhalt-1">
              <akn:p GUID="5754c5f9-f331-483e-b74b-f056678c481a" eId="hauptteil-1_abschnitt-2_art-3_abs-8_inhalt-1_text-1"> Für Anordnungen nach § 8a findet § 12 Absatz 1 des Artikel 10-Gesetzes entsprechende Anwendung, mit der Maßgabe, dass § 12 Absatz 1 Satz 5 des Artikel 10-Gesetzes nur für Maßnahmen nach § 8a Absatz 1 Satz 1 Nummer 4 und 5 Anwendung findet. Wurden personenbezogene Daten an eine andere Stelle übermittelt, erfolgt die Mitteilung im Benehmen mit dieser. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="300fa851-34de-4006-9585-f3f078321244" eId="hauptteil-1_abschnitt-2_art-3_abs-9">
            <akn:num GUID="396a3e2d-53f7-48a4-8f96-90d5cad8feb9" eId="hauptteil-1_abschnitt-2_art-3_abs-9_bezeichnung-1"> (8) </akn:num>
            <akn:list GUID="d2362f26-46ff-4691-afed-f6a24b71fbc2" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1">
              <akn:intro GUID="6c499b35-62b0-479c-a81d-a74ce1b9043c" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_intro-1">
                <akn:p GUID="470df4e5-aa77-4cfe-866e-53d7a0bb5860" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_intro-1_text-1"> Das Bundesministerium des Innern, für Bau und Heimat wird ermächtigt, durch Rechtsverordnung im Einvernehmen mit dem Bundeskanzleramt, dem Bundesministerium für Wirtschaft und Energie, dem Bundesministerium für Verkehr und digitale Infrastruktur, dem Bundesministerium der Justiz und für Verbraucherschutz und dem Bundesministerium der Verteidigung ohne Zustimmung des Bundesrates zu bestimmen, dass Auskünfte nach § 8a Absatz 1 mit Ausnahme der Auskünfte nach § 8a Absatz 1 Satz 1 Nummer 4, auch soweit andere Vorschriften hierauf verweisen, ganz oder teilweise auf maschinell verwertbaren Datenträgern oder durch Datenfernübertragung übermittelt werden müssen. Dabei können insbesondere geregelt werden </akn:p>
              </akn:intro>
              <akn:point GUID="df637290-7ebb-4fb0-9149-7f262ee87f98" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1">
                <akn:num GUID="f366f793-cad3-4e9c-bbee-cf2d5ec6e674" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="42882a2c-c3d6-48f1-996b-727eb2bfd30e" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="bf4013a9-9d6d-422a-9460-2f3ee31ff4d6" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-1_inhalt-1_text-1"> die Voraussetzungen für die Anwendung des Verfahrens, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="3f16851a-f9b5-452b-b129-017533bf381f" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2">
                <akn:num GUID="9fb8be11-d509-42d0-9d4c-4a546bcfba2e" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="28b82a31-cf15-4f86-b9c2-70f7e345b784" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="928952a7-0352-476e-bc77-f8812f9ef536" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-2_inhalt-1_text-1"> das Nähere über Form, Inhalt, Verarbeitung und Sicherung der zu übermittelnden Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="5d53292e-7386-4d49-a60d-a55b548cf7ec" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3">
                <akn:num GUID="5cb4012f-36a8-410b-911a-aedd6fb1aabd" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="6bd70cb3-d1bf-41f4-8473-d0fb376b0794" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="7ae3cf4e-b179-4661-9406-df9ea1db0b5d" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-3_inhalt-1_text-1"> die Art und Weise der Übermittlung der Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="a7a5a66b-3fae-4fac-86ce-6a4c63cef282" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4">
                <akn:num GUID="c52ca09e-4993-43b8-911c-0cfff5f77359" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="7fcf6645-d41b-4030-b5f2-140fef69931c" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="89e205a5-ba32-48e1-af5e-ca73ab4d78bc" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-4_inhalt-1_text-1"> die Zuständigkeit für die Entgegennahme der zu übermittelnden Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="54a8ad7c-9041-4852-a101-35c33a25bd07" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5">
                <akn:num GUID="2bfc658f-e006-4625-a172-38ebe6c13947" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:content GUID="dcd9f4f6-c491-4483-82b3-bf9bf528386a" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="aa6463aa-0f92-46d9-8e81-7079098cd691" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-5_inhalt-1_text-1"> der Umfang und die Form der für dieses Verfahren erforderlichen besonderen Erklärungspflichten des Auskunftspflichtigen und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="99bd1f0b-e514-49e8-8d3f-57bc2b8a8af9" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6">
                <akn:num GUID="9e9230f2-2c47-4533-ad3f-cc3c5f1196fc" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_bezeichnung-1"> 6. </akn:num>
                <akn:content GUID="a5463293-fec1-450f-8bdb-63f8e7612fc7" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_inhalt-1">
                  <akn:p GUID="806ce831-d6af-4d56-8087-1939d5c01129" eId="hauptteil-1_abschnitt-2_art-3_abs-9_untergl-1_listenelem-6_inhalt-1_text-1"> Tatbestände und Bemessung einer auf Grund der Auskunftserteilung an Verpflichtete zu leistenden Aufwandsentschädigung. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="54e67ee9-56cc-45dd-a31b-34c816d69762" eId="hauptteil-1_abschnitt-2_art-3_abs-10">
            <akn:num GUID="2fa88271-fc5f-4927-b1d9-4d10ffed3c21" eId="hauptteil-1_abschnitt-2_art-3_abs-10_bezeichnung-1"> (9) </akn:num>
            <akn:content GUID="3d336b51-cb08-44c4-bcec-8fac7b967b36" eId="hauptteil-1_abschnitt-2_art-3_abs-10_inhalt-1">
              <akn:p GUID="d96ccc7b-b5f7-437e-adc2-04036a5599a1" eId="hauptteil-1_abschnitt-2_art-3_abs-10_inhalt-1_text-1"> Für die Erteilung von Auskünften nach § 8a Absatz 1 Satz 1 Nummer 4 hat der Verpflichtete Anspruch auf Entschädigung entsprechend § 23 des Justizvergütungs- und -entschädigungsgesetzes. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="d44bcb76-dd88-4a82-bb59-0d1827790a07" eId="hauptteil-1_abschnitt-2_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="e96e1d29-ae4b-45ae-ac84-9808c1dd1c64" eId="hauptteil-1_abschnitt-2_art-4_bezeichnung-1"> § 8c </akn:num>
          <akn:heading GUID="32fed98d-cda8-477d-bb63-e1b1d2a1814e" eId="hauptteil-1_abschnitt-2_art-4_überschrift-1"> Einschränkungen eines Grundrechts </akn:heading>
          <akn:paragraph GUID="b80a2faf-8dc9-4c7e-bac8-3ead58285483" eId="hauptteil-1_abschnitt-2_art-4_abs-1">
            <akn:num GUID="0c3e5336-170d-4a1b-8a07-4ad389a92d78" eId="hauptteil-1_abschnitt-2_art-4_abs-1_bezeichnung-1"/>
            <akn:content GUID="8193ac90-24e0-4000-8d9a-aee39b4aa11f" eId="hauptteil-1_abschnitt-2_art-4_abs-1_inhalt-1">
              <akn:p GUID="031e6f17-fa70-4bc9-bbc9-010c265ec7d4" eId="hauptteil-1_abschnitt-2_art-4_abs-1_inhalt-1_text-1"> Das Grundrecht des Fernmeldegeheimnisses (Artikel 10 des Grundgesetzes) wird nach Maßgabe des § 8a Absatz 2 Satz 1 Nummer 4 und 5 und Absatz 3 sowie des § 8b Absatz 1, 2, 4 bis 8 und 10 eingeschränkt. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="b3979406-7798-406e-b11b-34f05ecd1ef6" eId="hauptteil-1_abschnitt-2_art-5" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="5230242b-af98-4e45-8868-4d84797ebd53" eId="hauptteil-1_abschnitt-2_art-5_bezeichnung-1"> § 8d </akn:num>
          <akn:heading GUID="751d323f-7e7c-4e39-9ef0-f4fdfde377c1" eId="hauptteil-1_abschnitt-2_art-5_überschrift-1"> Besondere Auskunftsverlangen zu Bestandsdaten </akn:heading>
          <akn:paragraph GUID="786c4a33-d333-42b7-8ed0-e5833917ab65" eId="hauptteil-1_abschnitt-2_art-5_abs-1">
            <akn:num GUID="c7135f2c-e901-4d1c-ab29-5da8ef7a8b44" eId="hauptteil-1_abschnitt-2_art-5_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="f9aab2bb-0ee0-465d-bef4-d3944aebe3c0" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1">
              <akn:intro GUID="a636a701-41d1-483b-8ae1-4e68a1cef871" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_intro-1">
                <akn:p GUID="55a1763f-58fb-472f-98dd-1ad72d4e7a1c" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_intro-1_text-1"> Soweit dies auf Grund tatsächlicher Anhaltspunkte im Einzelfall zur Aufklärung bestimmter Bestrebungen oder Tätigkeiten nach § 3 Absatz 1 erforderlich ist, darf das Bundesamt für Verfassungsschutz Auskunft verlangen von demjenigen, der geschäftsmäßig </akn:p>
              </akn:intro>
              <akn:point GUID="af8da193-af3d-454e-9809-4fe0f2a945b8" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="cb9fff58-4b18-4f94-ad46-b8221499c2e6" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="8fe33ac9-2f2c-400e-afbc-47889d5b4bbc" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="f961cff9-ff39-460d-8b35-a5b99b1c6758" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> Telekommunikationsdienste erbringt oder daran mitwirkt, über Bestandsdaten nach § 3 Nummer 6 und § 172 des Telekommunikationsgesetzes, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="bb219aad-a4fb-44c1-868b-a4ddfff1b9f3" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="c7f5e7b9-6be2-4628-ac49-51271b5cfdaf" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="b5501899-0783-4855-8008-f3a493a9a33d" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="9e568167-00fa-43ba-bec6-43b4c28b9b06" eId="hauptteil-1_abschnitt-2_art-5_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> Telemediendienste erbringt oder daran mitwirkt, über Bestandsdaten nach § 2 Absatz 2 Nummer 2 des Telekommunikation-Telemedien-Datenschutz-Gesetzes. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="4b11957b-3806-40f0-9e3d-26116545063c" eId="hauptteil-1_abschnitt-2_art-5_abs-2">
            <akn:num GUID="ee8a7a09-efa1-4fa5-8aa5-bada562b73a1" eId="hauptteil-1_abschnitt-2_art-5_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="e429a6c6-9cb1-463c-80e9-1db63914ac58" eId="hauptteil-1_abschnitt-2_art-5_abs-2_inhalt-1">
              <akn:p GUID="9c7ae872-4d7a-4286-be95-057e6f130538" eId="hauptteil-1_abschnitt-2_art-5_abs-2_inhalt-1_text-1"> Die Auskunft darf auch verlangt werden anhand einer zu einem bestimmten Zeitpunkt zugewiesenen Internetprotokoll-Adresse. Die Rechtsgrundlage und die tatsächlichen Anhaltspunkte, die das Auskunftsverlangen veranlassen, sind aktenkundig zu machen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="f090e6c4-c1b7-4bd2-82bc-2cc03a167e4b" eId="hauptteil-1_abschnitt-2_art-5_abs-3">
            <akn:num GUID="22d8e9ed-ff18-4eac-9eed-1f5fc6aaee12" eId="hauptteil-1_abschnitt-2_art-5_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="cbe083c3-c3d0-4c71-a640-3e228bc8d238" eId="hauptteil-1_abschnitt-2_art-5_abs-3_inhalt-1">
              <akn:p GUID="f97a497d-4489-4744-b6cc-164e8da21274" eId="hauptteil-1_abschnitt-2_art-5_abs-3_inhalt-1_text-1"> Die Auskunft zu Daten, mittels derer der Zugriff auf Endgeräte oder auf Speichereinrichtungen, die in diesen Endgeräten oder hiervon räumlich getrennt eingesetzt werden, geschützt wird, darf nur im Falle des Absatzes 1 Satz 1 Nummer 1 verlangt werden und nur dann verlangt werden, wenn die gesetzlichen Voraussetzungen für die Nutzung der Daten vorliegen. Für diese Auskunftsverlangen gilt § 8b Absatz 1 Satz 1 und 2 und Absatz 2 entsprechend. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="992b301c-47db-4237-a0ec-0711599cb40e" eId="hauptteil-1_abschnitt-2_art-5_abs-4">
            <akn:num GUID="9e425919-c3f9-48a3-abcf-cc73aa158afd" eId="hauptteil-1_abschnitt-2_art-5_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="3170f514-665b-44a1-bc08-1220b2ab55cb" eId="hauptteil-1_abschnitt-2_art-5_abs-4_inhalt-1">
              <akn:p GUID="204d728b-be03-4054-8760-5e42ad286ea7" eId="hauptteil-1_abschnitt-2_art-5_abs-4_inhalt-1_text-1"> Die betroffene Person ist in den Fällen der Absätze 2 und 3 über die Auskunftserteilung zu benachrichtigen. Die Benachrichtigung erfolgt, soweit und sobald eine Gefährdung des Zwecks der Auskunft und der Eintritt übergreifender Nachteile für das Wohl des Bundes oder eines Landes ausgeschlossen werden können. Die Benachrichtigung unterbleibt, wenn ihr überwiegende schutzwürdige Belange Dritter oder der betroffenen Person selbst entgegenstehen. Wird die Benachrichtigung nach Satz 2 zurückgestellt oder nach Satz 3 von ihr abgesehen, sind die Gründe aktenkundig zu machen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="40dbdf4f-1f70-4f65-9a53-5541a4458320" eId="hauptteil-1_abschnitt-2_art-5_abs-5">
            <akn:num GUID="77a796dc-2f27-4b03-af36-61df43964a34" eId="hauptteil-1_abschnitt-2_art-5_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="3d33aa93-2ff9-4e4d-aa67-f90227321670" eId="hauptteil-1_abschnitt-2_art-5_abs-5_inhalt-1">
              <akn:p GUID="69a040ae-d7f3-48fa-9599-7f5424a67352" eId="hauptteil-1_abschnitt-2_art-5_abs-5_inhalt-1_text-1"> Der auf Grund eines Auskunftsverlangens Verpflichtete hat die zur Auskunftserteilung erforderlichen Daten unverzüglich und vollständig zu übermitteln. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="42a54e51-25ca-41da-a7dc-fb445877871a" eId="hauptteil-1_abschnitt-2_art-5_abs-6">
            <akn:num GUID="b771f27c-66f4-4232-a928-9ef482905f2a" eId="hauptteil-1_abschnitt-2_art-5_abs-6_bezeichnung-1"> (6) </akn:num>
            <akn:content GUID="1bd9dbf2-f91b-4ab2-bef6-d65faf11660e" eId="hauptteil-1_abschnitt-2_art-5_abs-6_inhalt-1">
              <akn:p GUID="d35761d2-7c96-4669-8ff8-e19750c01934" eId="hauptteil-1_abschnitt-2_art-5_abs-6_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz hat den Verpflichteten für ihm erteilte Auskünfte eine Entschädigung zu gewähren. Der Umfang der Entschädigung bemisst sich nach § 23 und Anlage 3 des Justizvergütungs- und -entschädigungsgesetzes; die Vorschriften über die Verjährung in § 2 Absatz 1 und 4 des Justizvergütungs- und -entschädigungsgesetzes finden entsprechend Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="bd4edb95-a3eb-431f-b60b-fea5de92d188" eId="hauptteil-1_abschnitt-2_art-5_abs-7">
            <akn:num GUID="2c41a6b8-4046-45e9-bb81-5b694d0299b6" eId="hauptteil-1_abschnitt-2_art-5_abs-7_bezeichnung-1"> (7) </akn:num>
            <akn:content GUID="51dfba29-4b95-4f80-be33-b25c65fdeca3" eId="hauptteil-1_abschnitt-2_art-5_abs-7_inhalt-1">
              <akn:p GUID="bc511a17-76ea-459b-8de5-65dc1404af09" eId="hauptteil-1_abschnitt-2_art-5_abs-7_inhalt-1_text-1"> Das Fernmeldegeheimnis (Artikel 10 des Grundgesetzes) wird nach Maßgabe des Absatzes 2 Satz 1 eingeschränkt. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="dc7d6175-b437-423a-a9cc-b1b3767c885b" eId="hauptteil-1_abschnitt-2_art-6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8093b333-73bf-48dc-9a4e-f6ca837d9865" eId="hauptteil-1_abschnitt-2_art-6_bezeichnung-1"> § 9 </akn:num>
          <akn:heading GUID="895b0e7e-55f3-4c9c-80a9-723052bd3049" eId="hauptteil-1_abschnitt-2_art-6_überschrift-1"> Besondere Formen der Datenerhebung </akn:heading>
          <akn:paragraph GUID="f96e216c-0b85-442f-b542-fec18bc2b9ed" eId="hauptteil-1_abschnitt-2_art-6_abs-1">
            <akn:num GUID="132a084c-aea3-46bc-a72d-2dc45c2b1253" eId="hauptteil-1_abschnitt-2_art-6_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="f875a25b-6188-4f8c-8ab8-969483a30663" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1">
              <akn:intro GUID="0e295446-dc62-4d70-9da1-94751fccb721" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_intro-1">
                <akn:p GUID="129b2572-47f2-480c-bfe0-97f2fb9361ac" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf Informationen, insbesondere personenbezogene Daten, mit den Mitteln gemäß § 8 Abs. 2 erheben, wenn Tatsachen die Annahme rechtfertigen, daß </akn:p>
              </akn:intro>
              <akn:point GUID="7ca0fd48-444a-431a-b538-7d1274f88819" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="f6fda72c-d2d0-4e3c-ab90-78879593869f" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="917360bf-88f3-485d-9d70-91f332dd494a" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="1aae1d9d-e625-4d33-95c9-5e39b65ea2f6" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> auf diese Weise Erkenntnisse über Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 oder die zur Erforschung solcher Erkenntnisse erforderlichen Quellen gewonnen werden können oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="4960520a-b8a7-4010-9eed-8d0409da0881" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="7acdb5f2-b1e3-48b2-91b4-1d56b0a9db5e" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="db1f6306-92d9-4ec0-98b1-15a644c331f5" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="75c8d575-5cf1-42e8-817a-378003539958" eId="hauptteil-1_abschnitt-2_art-6_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> dies zum Schutz der Mitarbeiter, Einrichtungen, Gegenstände und Quellen des Bundesamtes für Verfassungsschutz gegen sicherheitsgefährdende oder geheimdienstliche Tätigkeiten erforderlich ist. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="3a047b8f-a66a-4a88-bd4a-c2d8c405f57e" eId="hauptteil-1_abschnitt-2_art-6_abs-2">
            <akn:num GUID="2a63d547-25e0-496c-8a6f-e35c5e18fd20" eId="hauptteil-1_abschnitt-2_art-6_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="a2b46a60-f2a0-49be-90cf-e0a67a3c0f89" eId="hauptteil-1_abschnitt-2_art-6_abs-2_inhalt-1">
              <akn:p GUID="267a6946-f0ec-46bb-8908-32c89de5d168" eId="hauptteil-1_abschnitt-2_art-6_abs-2_inhalt-1_text-1"> Das in einer Wohnung nicht öffentlich gesprochene Wort darf mit technischen Mitteln nur heimlich mitgehört oder aufgezeichnet werden, wenn es im Einzelfall zur Abwehr einer gegenwärtigen gemeinen Gefahr oder einer gegenwärtigen Lebensgefahr für einzelne Personen unerläßlich ist und geeignete polizeiliche Hilfe für das bedrohte Rechtsgut nicht rechtzeitig erlangt werden kann. Satz 1 gilt entsprechend für einen verdeckten Einsatz technischer Mittel zur Anfertigung von Bildaufnahmen und Bildaufzeichnungen. Maßnahmen nach den Sätzen 1 und 2 werden durch den Präsidenten des Bundesamtes für Verfassungsschutz oder seinen Vertreter angeordnet, wenn eine richterliche Entscheidung nicht rechtzeitig herbeigeführt werden kann. Die richterliche Entscheidung ist unverzüglich nachzuholen. Zuständig ist das Amtsgericht, in dessen Bezirk das Bundesamt für Verfassungsschutz seinen Sitz hat. Für das Verfahren gelten die Vorschriften des Gesetzes über das Verfahren in Familiensachen und in den Angelegenheiten der freiwilligen Gerichtsbarkeit entsprechend. Die erhobenen Informationen dürfen nur nach Maßgabe des § 4 Abs. 4 des Artikel 10-Gesetzes verwendet werden. § 4 Abs. 6 des Artikel 10-Gesetzes gilt entsprechend. Das Grundrecht der Unverletzlichkeit der Wohnung (Artikel 13 des Grundgesetzes) wird insoweit eingeschränkt. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="8d49643e-611f-4c9a-b71f-84b9c36514f5" eId="hauptteil-1_abschnitt-2_art-6_abs-3">
            <akn:num GUID="3b1e3c4c-03ba-41d6-b9b7-bf97cceb30a8" eId="hauptteil-1_abschnitt-2_art-6_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="246c8fbe-15f6-4ec5-b2fe-849e823130d5" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1">
              <akn:intro GUID="5c5ca78f-a4b1-40fa-9646-861385083013" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_intro-1">
                <akn:p GUID="79add839-d191-44d8-b6cf-a9fefb4ade06" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_intro-1_text-1"> Bei Erhebungen nach Absatz 2 und solchen nach Absatz 1, die in ihrer Art und Schwere einer Beschränkung des Brief-, Post- und Fernmelde*-geheimnisses gleichkommen, wozu insbesondere das Abhören und Aufzeichnen des nicht öffentlich gesprochenen Wortes mit dem verdeckten Einsatz technischer Mittel gehören, ist </akn:p>
              </akn:intro>
              <akn:point GUID="57acf68f-e530-4c78-a692-d90ef84239ef" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="69d94e58-2ed5-4926-a342-d4e09729f337" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="875c2f0b-90a5-4721-a50e-ddf05418be97" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="802a53a9-6bfe-4d05-bf52-11a8c9725eda" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> der Eingriff nach seiner Beendigung dem Betroffenen mitzuteilen, sobald eine Gefährdung des Zweckes des Eingriffs ausgeschlossen werden kann, und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="b5619aa8-2928-4e1b-a58a-d54d379d010e" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="cc7e19cd-2da7-4801-b807-d8b6a29252c4" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="b58f9392-3846-4e7b-8f1f-cba3bbd0a078" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="f99fc48e-fd27-406e-9f98-b15efa738725" eId="hauptteil-1_abschnitt-2_art-6_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> das Parlamentarische Kontrollgremium zu unterrichten. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="5413e46c-e909-4c9b-9bc7-86dbe3486608" eId="hauptteil-1_abschnitt-2_art-6_abs-4">
            <akn:num GUID="3f814f0b-180a-434a-9109-647b5bbaff90" eId="hauptteil-1_abschnitt-2_art-6_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="288fc4da-0c98-4f3d-a990-464a692f4b7b" eId="hauptteil-1_abschnitt-2_art-6_abs-4_inhalt-1">
              <akn:p GUID="eb636419-ad73-4ca1-b735-0e3830ffa35b" eId="hauptteil-1_abschnitt-2_art-6_abs-4_inhalt-1_text-1"> (weggefallen) </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="882cf5a9-dbd0-433a-bc70-e76957df3b91" eId="hauptteil-1_abschnitt-2_art-7" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="5ef83515-503b-4373-8f96-30c9ee92130e" eId="hauptteil-1_abschnitt-2_art-7_bezeichnung-1"> § 9a </akn:num>
          <akn:heading GUID="5c70b9ee-b628-4827-874b-4b11e0c327bc" eId="hauptteil-1_abschnitt-2_art-7_überschrift-1"> Verdeckte Mitarbeiter </akn:heading>
          <akn:paragraph GUID="dc3cf2bd-3359-4da0-ac7c-f83000e3a07f" eId="hauptteil-1_abschnitt-2_art-7_abs-1">
            <akn:num GUID="1562b7c4-b518-4550-8caa-7f2e8ce1fc6e" eId="hauptteil-1_abschnitt-2_art-7_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="511c8835-6527-4bec-8615-112c63d59c53" eId="hauptteil-1_abschnitt-2_art-7_abs-1_inhalt-1">
              <akn:p GUID="ed563e00-0a71-42f1-9803-5b8d096f4154" eId="hauptteil-1_abschnitt-2_art-7_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf eigene Mitarbeiter unter einer ihnen verliehenen und auf Dauer angelegten Legende (Verdeckte Mitarbeiter) zur Aufklärung von Bestrebungen unter den Voraussetzungen des § 9 Absatz 1 einsetzen. Ein dauerhafter Einsatz zur Aufklärung von Bestrebungen nach § 3 Absatz 1 Nummer 1 und 4 ist nur bei Bestrebungen von erheblicher Bedeutung zulässig, insbesondere wenn sie darauf gerichtet sind, Gewalt anzuwenden oder Gewaltanwendung vorzubereiten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d54f1d21-912c-42db-8720-9843379cd751" eId="hauptteil-1_abschnitt-2_art-7_abs-2">
            <akn:num GUID="fbe42326-5a2a-40d9-b5b0-ea8102039485" eId="hauptteil-1_abschnitt-2_art-7_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="6ae7ff43-746d-48d3-8df7-fda4ad14d542" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1">
              <akn:intro GUID="c67c24f9-09e1-4972-9615-90ac0f944c71" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_intro-1">
                <akn:p GUID="2667daab-d4b4-4fc5-9359-ca581875f73b" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_intro-1_text-1"> Verdeckte Mitarbeiter dürfen weder zur Gründung von Bestrebungen nach § 3 Absatz 1 Nummer 1, 3 oder 4 noch zur steuernden Einflussnahme auf derartige Bestrebungen eingesetzt werden. Sie dürfen in solchen Personenzusammenschlüssen oder für solche Personenzusammenschlüsse, einschließlich strafbare Vereinigungen, tätig werden, um deren Bestrebungen aufzuklären. Im Übrigen ist im Einsatz eine Beteiligung an Bestrebungen zulässig, wenn sie </akn:p>
              </akn:intro>
              <akn:point GUID="33d6571b-7544-471a-9dfc-d5450ff05eea" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1">
                <akn:num GUID="a0dd7770-7306-4077-817c-78eb8f6a2db1" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="e1ef53cb-fc0c-4443-a86f-9cb33a3b8b49" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="0ba71c07-138f-4647-927d-5923f7daba8e" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-1_inhalt-1_text-1"> nicht in Individualrechte eingreift, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="b3fa7090-58a4-49b1-9076-4858034a4b5e" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2">
                <akn:num GUID="641fae96-b63f-49c6-81e9-0cc8155d3835" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="ce056da1-acbb-4a82-ab19-77d6c6cbd341" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="539f1631-b5e2-4393-8e1d-86c778e78bb6" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-2_inhalt-1_text-1"> von den an den Bestrebungen Beteiligten derart erwartet wird, dass sie zur Gewinnung und Sicherung der Informationszugänge unumgänglich ist und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d151da68-4ca6-4e19-9487-d8701b177ada" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3">
                <akn:num GUID="dfc225f5-8968-4b7e-8f60-a6210d926f76" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="ff3f45f5-05ac-44f8-a624-ec9cb71a3942" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="8211b4fa-86bd-46dc-81a4-364971b7a9d7" eId="hauptteil-1_abschnitt-2_art-7_abs-2_untergl-1_listenelem-3_inhalt-1_text-1"> nicht außer Verhältnis zur Bedeutung des aufzuklärenden Sachverhalts steht. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="016774f2-61a7-4673-98d9-38a41025c428" eId="hauptteil-1_abschnitt-2_art-7_abs-3">
            <akn:num GUID="a41d9be0-3c3d-4d2c-a767-41e14db53d16" eId="hauptteil-1_abschnitt-2_art-7_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="f672c416-1265-4a5f-8c0b-5d29215732d9" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1">
              <akn:intro GUID="f4b814ff-daa1-473d-9492-e24504549a0e" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_intro-1">
                <akn:p GUID="5c37ac61-0d72-433d-99e5-95417a360fdc" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_intro-1_text-1"> Die Staatsanwaltschaft kann von der Verfolgung von im Einsatz begangenen Vergehen absehen oder eine bereits erhobene Klage in jeder Lage des Verfahrens zurücknehmen und das Verfahren einstellen, wenn </akn:p>
              </akn:intro>
              <akn:point GUID="e60736d9-4371-4120-a2a3-915151e75696" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="18e40157-a0b6-46f3-aae0-4b2f43dc49eb" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="bbc49aa1-a0b5-49cc-b780-4a253c5f48c8" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="a3cdfe08-328f-4424-bf94-306c22553bd1" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> der Einsatz zur Aufklärung von Bestrebungen erfolgte, die auf die Begehung von in § 3 Absatz 1 des Artikel 10-Gesetzes bezeichneten Straftaten gerichtet sind, und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="482e51ed-9aa0-44c9-84a6-89aaff1bf253" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="bdb592ca-8b15-473f-9895-5c3f398e2a17" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="b5a3017f-6449-4c41-9050-a288f4d45870" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="8751bfc2-b300-4f1f-9346-186828791617" eId="hauptteil-1_abschnitt-2_art-7_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> die Tat von an den Bestrebungen Beteiligten derart erwartet wurde, dass sie zur Gewinnung und Sicherung der Informationszugänge unumgänglich war. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="f5388daf-d023-43b0-943b-d32ff506ef6b" eId="hauptteil-1_abschnitt-2_art-8" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="6799440c-f5d4-461f-a6f4-2f1d7f3a271b" eId="hauptteil-1_abschnitt-2_art-8_bezeichnung-1"> § 9b </akn:num>
          <akn:heading GUID="33d90b7f-81ad-4687-bae9-0d3aaef49e3d" eId="hauptteil-1_abschnitt-2_art-8_überschrift-1"> Vertrauensleute </akn:heading>
          <akn:paragraph GUID="21b17d78-b873-4003-9fe4-010567d81812" eId="hauptteil-1_abschnitt-2_art-8_abs-1">
            <akn:num GUID="36fe52a2-5d40-4a07-8862-b057f51cff4f" eId="hauptteil-1_abschnitt-2_art-8_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="77e48f41-e6e4-4178-b4b4-ff73784211ef" eId="hauptteil-1_abschnitt-2_art-8_abs-1_inhalt-1">
              <akn:p GUID="2ec8b3e3-a273-479c-9856-94c5788b7577" eId="hauptteil-1_abschnitt-2_art-8_abs-1_inhalt-1_text-1"> Für den Einsatz von Privatpersonen, deren planmäßige, dauerhafte Zusammenarbeit mit dem Bundesamt für Verfassungsschutz Dritten nicht bekannt ist (Vertrauensleute), ist § 9a entsprechend anzuwenden. Die Bundesregierung trägt dem Parlamentarischen Kontrollgremium mindestens einmal im Jahr einen Lagebericht zum Einsatz von Vertrauensleuten vor. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="9af7ab0f-2d20-4045-8ce7-ab384c48b823" eId="hauptteil-1_abschnitt-2_art-8_abs-2">
            <akn:num GUID="76f41054-e8c2-465d-892c-13b171c052af" eId="hauptteil-1_abschnitt-2_art-8_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="1aa011e8-1833-4e1c-b091-2642229282e6" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1">
              <akn:intro GUID="8c22a6e1-35ab-444b-a1d6-7797f1c84d5e" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_intro-1">
                <akn:p GUID="bab198ab-9a34-4b79-8515-5c1002afe32a" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_intro-1_text-1"> Über die Verpflichtung von Vertrauensleuten entscheidet der Behördenleiter oder sein Vertreter. Als Vertrauensleute dürfen Personen nicht angeworben und eingesetzt werden, die </akn:p>
              </akn:intro>
              <akn:point GUID="07ba603b-bd15-465e-bd17-b61117afb81b" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1">
                <akn:num GUID="f15a067f-1d77-4743-a8d3-2ce2a044fabd" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="23c8b2ca-093b-4b6e-b626-a9dbd1b4b3b1" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="d3601f3f-3951-4bab-97a1-3cc0c2e30029" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-1_inhalt-1_text-1"> nicht voll geschäftsfähig, insbesondere minderjährig sind, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="83584013-2b1f-481b-87bb-44b22c2dcb47" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2">
                <akn:num GUID="4fb77f73-8e32-4ffa-8bf4-e6c656a87265" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="ce8bc109-a4e7-4e26-860a-ba6c72ff9bb1" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="fb588822-cbf4-4697-8d80-7e3f7c958ace" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-2_inhalt-1_text-1"> von den Geld- oder Sach*-zuwendungen für die Tätigkeit auf Dauer als alleinige Lebensgrundlage abhängen würden, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d613f6ed-9b03-466a-abde-60dcc0ef5e43" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3">
                <akn:num GUID="f8b0773f-7637-412e-9622-ed5b15cc8a1b" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="be798a46-463e-4667-8b52-d461635a0d48" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="1a136a82-326c-40d7-ae84-77445ffe02ad" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-3_inhalt-1_text-1"> an einem Aussteigerprogramm teilnehmen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="31094981-8e15-49d3-bca1-2e349639cf26" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4">
                <akn:num GUID="3582691b-de57-4758-a93e-f53ad94d4a4a" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="f5393ba9-f673-4b54-82da-dc1c724da760" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="2063e6ff-f47d-4ab7-8a27-0c115d4c7d1f" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-4_inhalt-1_text-1"> Mitglied des Europäischen Parlaments, des Deutschen Bundestages, eines Landesparlaments oder Mitarbeiter eines solchen Mitglieds sind oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="73cecef1-1647-48c7-8dd7-90e6f13f294a" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5">
                <akn:num GUID="1b320444-59ec-4dab-a5b8-b328252262a8" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:content GUID="eb1037b7-f256-4070-bed4-8884efb926e0" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="bdb90d99-94d8-42b1-8624-4e7f273f8518" eId="hauptteil-1_abschnitt-2_art-8_abs-2_untergl-1_listenelem-5_inhalt-1_text-1"> im Bundeszentralregister mit einer Verurteilung wegen eines Verbrechens oder zu einer Freiheitsstrafe, deren Vollstreckung nicht zur Bewährung ausgesetzt worden ist, eingetragen sind. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="98d19652-8ebc-4f50-adb4-63d1c1276ff9" eId="hauptteil-1_abschnitt-2_art-9" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="f0ec5bf8-f8e4-43df-8f53-fed0cacb1de2" eId="hauptteil-1_abschnitt-2_art-9_bezeichnung-1"> § 10 </akn:num>
          <akn:heading GUID="f7ce6c93-d271-4961-9a97-86da644eee6e" eId="hauptteil-1_abschnitt-2_art-9_überschrift-1"> Speicherung, Veränderung und Nutzung personenbezogener Daten </akn:heading>
          <akn:paragraph GUID="eb14623f-669f-4300-a90b-c96b25e93942" eId="hauptteil-1_abschnitt-2_art-9_abs-1">
            <akn:num GUID="b9d8ae9c-4162-430c-903e-7416e9466622" eId="hauptteil-1_abschnitt-2_art-9_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="64273e70-e6b5-4eea-b63c-c04ad28670aa" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1">
              <akn:intro GUID="eaee2b6d-4c2d-4f68-9f96-1aa32337cb8f" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_intro-1">
                <akn:p GUID="d254c5e6-5515-48a9-8eca-26c532385bc9" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf zur Erfüllung seiner Aufgaben personenbezogene Daten in Dateien speichern, verändern und nutzen, wenn </akn:p>
              </akn:intro>
              <akn:point GUID="7bfe94c8-f3b9-4672-88ea-94f72d555a27" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="0d0e6814-1d35-45c6-b6ca-21e4b3cc652f" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="08466290-e1bb-475c-bc01-e4021970a1c4" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="b6bf3f65-7639-4fe8-a553-06a2843c61f5" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> tatsächliche Anhaltspunkte für Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 vorliegen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ce56211f-a84e-417e-84e8-48f0f269b524" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="dcf34cd4-28ad-409e-b66f-00de563eb928" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="36295864-8ffa-49f8-a563-730b9c5273c1" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="74703338-2daf-47b8-b62e-b27ca6fa5dce" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> dies für die Erforschung und Bewertung von Bestrebungen oder Tätigkeiten nach § 3 Abs. 1 erforderlich ist oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="acc4b790-49be-4580-a905-56c3c24d0d8e" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="aac32733-27f3-4599-b12d-85982c8e4b54" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="f20b10f8-940d-4fc0-be01-3c6e2d4f42d1" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="1e889627-594d-44c6-8cfe-20dc67f883d2" eId="hauptteil-1_abschnitt-2_art-9_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> das Bundesamt für Verfassungsschutz nach § 3 Abs. 2 tätig wird. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="607b020d-2e70-47c6-9012-8da184eafd54" eId="hauptteil-1_abschnitt-2_art-9_abs-2">
            <akn:num GUID="cec3ec4e-0289-4fbd-91e2-e7fba7bd16c9" eId="hauptteil-1_abschnitt-2_art-9_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="960ffc34-f8c5-4e20-9b79-6d39ea20b529" eId="hauptteil-1_abschnitt-2_art-9_abs-2_inhalt-1">
              <akn:p GUID="c9beb23b-ed82-4f56-82da-4a7c3717470d" eId="hauptteil-1_abschnitt-2_art-9_abs-2_inhalt-1_text-1"> Unterlagen, die nach Absatz 1 gespeicherte Angaben belegen, dürfen auch gespeichert werden, wenn in ihnen weitere personenbezogene Daten Dritter enthalten sind. Eine Abfrage von Daten Dritter ist unzulässig. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="4d0effc6-ec79-4726-823a-d0554140a164" eId="hauptteil-1_abschnitt-2_art-9_abs-3">
            <akn:num GUID="9a943aed-478d-404f-b788-396baa9c2176" eId="hauptteil-1_abschnitt-2_art-9_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="333b29fa-793f-4b50-b54e-cf9a90f0c44f" eId="hauptteil-1_abschnitt-2_art-9_abs-3_inhalt-1">
              <akn:p GUID="35b5f769-7984-4ae3-a016-014f11ab2989" eId="hauptteil-1_abschnitt-2_art-9_abs-3_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz hat die Speicherungsdauer auf das für seine Aufgabenerfüllung erforderliche Maß zu beschränken. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="dd99eb58-93aa-47a4-8adc-9e7837ae041d" eId="hauptteil-1_abschnitt-2_art-10" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="74c5fbae-3dae-4274-a4f3-074675e55f52" eId="hauptteil-1_abschnitt-2_art-10_bezeichnung-1"> § 11 </akn:num>
          <akn:heading GUID="b2843e1e-a4b2-47a0-905c-ae72207ee109" eId="hauptteil-1_abschnitt-2_art-10_überschrift-1"> Speicherung, Veränderung und Nutzung personenbezogener Daten von Minderjährigen </akn:heading>
          <akn:paragraph GUID="25d9934b-54a3-4db5-bbe4-2f9513c7a376" eId="hauptteil-1_abschnitt-2_art-10_abs-1">
            <akn:num GUID="fb21233b-3aae-436e-9bf6-ee9f4a825c24" eId="hauptteil-1_abschnitt-2_art-10_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="f2195ea0-d156-4dec-8183-92c7d97c6321" eId="hauptteil-1_abschnitt-2_art-10_abs-1_inhalt-1">
              <akn:p GUID="69ae5922-c1a6-4601-9264-52cbb5296831" eId="hauptteil-1_abschnitt-2_art-10_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf unter den Voraussetzungen des § 10 Daten über Minderjährige vor Vollendung des 14. Lebensjahres in zu ihrer Person geführten Akten nur speichern, verändern und nutzen, wenn tatsächliche Anhaltspunkte dafür bestehen, dass der Minderjährige eine der in § 3 Abs. 1 des Artikel 10-Gesetzes genannten Straftaten plant, begeht oder begangen hat. In Dateien ist eine Speicherung von Daten oder über das Verhalten Minderjähriger vor Vollendung des 14. Lebensjahres nicht zulässig. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="f6b3b735-e23d-43c8-8cf8-810ed64cb6cd" eId="hauptteil-1_abschnitt-2_art-10_abs-2">
            <akn:num GUID="a18a0aeb-a45b-4194-b45e-55a9e3b4a1a9" eId="hauptteil-1_abschnitt-2_art-10_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="ebd649c2-d6fc-464f-85ef-99e4e6fbad0b" eId="hauptteil-1_abschnitt-2_art-10_abs-2_inhalt-1">
              <akn:p GUID="ec3f65fc-2940-4a68-b4a5-4a10bb54459e" eId="hauptteil-1_abschnitt-2_art-10_abs-2_inhalt-1_text-1"> In Dateien oder zu ihrer Person geführten Akten gespeicherte Daten über Minderjährige vor Vollendung des 16. Lebensjahres sind spätestens nach zwei Jahren zu löschen, es sei denn, dass weitere Erkenntnisse nach § 3 Absatz 1 angefallen sind. In Dateien oder zu ihrer Person geführten Akten gespeicherte Daten über Minderjährige ab Vollendung des 16. Lebensjahres sind nach zwei Jahren auf die Erforderlichkeit der Speicherung zu überprüfen und spätestens nach fünf Jahren zu löschen, es sei denn, dass nach Eintritt der Volljährigkeit weitere Erkenntnisse nach § 3 Absatz 1 angefallen sind. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="e5d325b7-caae-43e8-a505-4bf3d6e5980e" eId="hauptteil-1_abschnitt-2_art-11" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="f00c9661-c634-4441-afe0-0a1f6126e199" eId="hauptteil-1_abschnitt-2_art-11_bezeichnung-1"> § 12 </akn:num>
          <akn:heading GUID="850e4246-3d8b-4278-8ef7-c1d7738726dc" eId="hauptteil-1_abschnitt-2_art-11_überschrift-1"> Berichtigung, Löschung und Sperrung personenbezogener Daten in Dateien </akn:heading>
          <akn:paragraph GUID="d262ebf7-fb2c-4bac-9488-e19c3e47c89e" eId="hauptteil-1_abschnitt-2_art-11_abs-1">
            <akn:num GUID="d1f962be-f709-4539-abeb-c32a761eeb4a" eId="hauptteil-1_abschnitt-2_art-11_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="5328e945-c3f3-4bbe-9f57-025c57eace84" eId="hauptteil-1_abschnitt-2_art-11_abs-1_inhalt-1">
              <akn:p GUID="08b5dac4-d876-4272-affc-0d792aedb3ce" eId="hauptteil-1_abschnitt-2_art-11_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz hat die in Dateien gespeicherten personenbezogenen Daten zu berichtigen, wenn sie unrichtig sind. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="53513eb4-a3cb-4fb2-ae60-92c2240fbe37" eId="hauptteil-1_abschnitt-2_art-11_abs-2">
            <akn:num GUID="31a2956e-d596-426c-a58f-15520f131b7b" eId="hauptteil-1_abschnitt-2_art-11_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="d2e3dda8-4f86-47c6-9916-88fb582ee094" eId="hauptteil-1_abschnitt-2_art-11_abs-2_inhalt-1">
              <akn:p GUID="adb5bc39-b614-4b5e-99e9-20acd56088bf" eId="hauptteil-1_abschnitt-2_art-11_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz hat die in Dateien gespeicherten personenbezogenen Daten zu löschen, wenn ihre Speicherung unzulässig war oder ihre Kenntnis für die Aufgabenerfüllung nicht mehr erforderlich ist. Die Löschung unterbleibt, wenn Grund zu der Annahme besteht, daß durch sie schutzwürdige Interessen des Betroffenen beeinträchtigt würden. In diesem Falle sind die Daten zu sperren. Sie dürfen nur noch mit Einwilligung des Betroffenen übermittelt werden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="a79c63d3-d717-4ae6-9ada-efece84ce180" eId="hauptteil-1_abschnitt-2_art-11_abs-3">
            <akn:num GUID="549a4d52-be77-4da2-bc84-7c94de17189b" eId="hauptteil-1_abschnitt-2_art-11_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="9752c35e-59cc-44d3-8289-2eb1d9ab5df1" eId="hauptteil-1_abschnitt-2_art-11_abs-3_inhalt-1">
              <akn:p GUID="91d949ad-0c69-494d-ae33-5aa2718dfad1" eId="hauptteil-1_abschnitt-2_art-11_abs-3_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz prüft bei der Einzelfallbearbeitung und nach festgesetzten Fristen, spätestens nach fünf Jahren, ob gespeicherte personenbezogene Daten zu berichtigen oder zu löschen sind. Gespeicherte personenbezogene Daten über Bestrebungen nach § 3 Absatz 1 Nummer 1, 3 und 4 sind spätestens zehn Jahre nach dem Zeitpunkt der letzten gespeicherten relevanten Information zu löschen, es sei denn, die zuständige Abteilungsleitung oder deren Vertretung trifft im Einzelfall ausnahmsweise eine andere Entscheidung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="3c77d8c4-4b50-4965-ae7d-ac6acd4870bd" eId="hauptteil-1_abschnitt-2_art-11_abs-4">
            <akn:num GUID="cc53ee14-3f18-454e-8f00-d0eda26f26df" eId="hauptteil-1_abschnitt-2_art-11_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="986e17b1-ea36-4110-a2e4-2d4718ac3b99" eId="hauptteil-1_abschnitt-2_art-11_abs-4_inhalt-1">
              <akn:p GUID="75faa16f-e834-4807-aae6-54d17a215055" eId="hauptteil-1_abschnitt-2_art-11_abs-4_inhalt-1_text-1"> Personenbezogene Daten, die ausschließlich zu Zwecken der Datenschutzkontrolle, der Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebes einer Datenverarbeitungsanlage gespeichert werden, dürfen nur für diese Zwecke verwendet werden. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="5bcc9dc6-0ee9-49f9-8fb7-2d2787b2ad5d" eId="hauptteil-1_abschnitt-2_art-12" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="7956e0dc-7172-4e0f-b0b0-2caf193aa61e" eId="hauptteil-1_abschnitt-2_art-12_bezeichnung-1"> § 13 </akn:num>
          <akn:heading GUID="cc808f41-9678-42cd-9c52-a852946043f8" eId="hauptteil-1_abschnitt-2_art-12_überschrift-1"> Verwendung und Berichtigung personenbezogener Daten in Akten </akn:heading>
          <akn:paragraph GUID="8ebc1619-2d1f-47b2-9a31-d715129503e2" eId="hauptteil-1_abschnitt-2_art-12_abs-1">
            <akn:num GUID="0b24f752-4c72-4421-896a-8106515f7f74" eId="hauptteil-1_abschnitt-2_art-12_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="4d180bd9-13a6-4f2e-adc4-f35714c95d0d" eId="hauptteil-1_abschnitt-2_art-12_abs-1_inhalt-1">
              <akn:p GUID="24a52e63-433a-4f72-abd1-56c304625ae7" eId="hauptteil-1_abschnitt-2_art-12_abs-1_inhalt-1_text-1"> Stellt das Bundesamt für Verfassungsschutz fest, daß in Akten gespeicherte personenbezogene Daten unrichtig sind oder wird ihre Richtigkeit von dem Betroffenen bestritten, so ist dies in der Akte zu vermerken oder auf sonstige Weise festzuhalten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="b7664af5-1389-457f-9803-6f223ea8f2a9" eId="hauptteil-1_abschnitt-2_art-12_abs-2">
            <akn:num GUID="3bbc3ae9-f2db-4c36-8c1e-a7060d654a1f" eId="hauptteil-1_abschnitt-2_art-12_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="ceff186d-0a1c-40fc-9c83-43d128569ec1" eId="hauptteil-1_abschnitt-2_art-12_abs-2_inhalt-1">
              <akn:p GUID="30676df6-eebd-48db-b146-38189504eb89" eId="hauptteil-1_abschnitt-2_art-12_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz hat die Verarbeitung personenbezogener Daten einzuschränken, wenn es im Einzelfall feststellt, dass ohne die Einschränkung schutzwürdige Interessen des Betroffenen beeinträchtigt würden und die Daten für seine künftige Aufgabenerfüllung nicht mehr erforderlich sind. Verarbeitungseingeschränkte Daten sind mit einem entsprechenden Vermerk zu versehen; sie dürfen nicht mehr genutzt oder übermittelt werden. Eine Aufhebung der Einschränkung ist möglich, wenn ihre Voraussetzungen nachträglich entfallen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="cb12f23d-99e3-4369-b6fc-2dc7692ecb7a" eId="hauptteil-1_abschnitt-2_art-12_abs-3">
            <akn:num GUID="a47609e6-be80-4ad8-8dbc-8d1e08bf4c14" eId="hauptteil-1_abschnitt-2_art-12_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="0455fe57-8774-4eae-9f3b-647e9c07416a" eId="hauptteil-1_abschnitt-2_art-12_abs-3_inhalt-1">
              <akn:p GUID="31e66e2c-54e5-47c0-a803-470c39b593f6" eId="hauptteil-1_abschnitt-2_art-12_abs-3_inhalt-1_text-1"> Eine Akte ist zu vernichten, wenn sie insgesamt zur Erfüllung der Aufgaben des Bundesamtes für Verfassungsschutz nicht oder nicht mehr erforderlich ist. Die Erforderlichkeit ist bei der Einzelfallbearbeitung und nach festgesetzten Fristen, spätestens nach fünf Jahren, zu prüfen. Für die Vernichtung einer Akte, die zu einer Person im Sinne des § 10 Absatz 1 Nummer 1 geführt wird, gilt § 12 Absatz 3 Satz 2 entsprechend. Eine Vernichtung unterbleibt, wenn Grund zu der Annahme besteht, dass durch sie schutzwürdige Interessen des Betroffenen beeinträchtigt würden. In diesem Fall ist die Verarbeitung der in der Akte gespeicherten personenbezogenen Daten einzuschränken und mit einem entsprechenden Vermerk zu versehen. Sie dürfen nur für die Interessen nach Satz 4 verarbeitet werden oder wenn es zur Abwehr einer erheblichen Gefahr unerlässlich ist. Eine Vernichtung der Akte erfolgt nicht, wenn sie nach den Vorschriften des Bundesarchivgesetzes dem Bundesarchiv zur Übernahme anzubieten und zu übergeben ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="75019b84-caf2-4fc4-86d2-0137ee03dc4f" eId="hauptteil-1_abschnitt-2_art-12_abs-4">
            <akn:num GUID="1eac3982-2a62-488e-9d23-a9c8f363eb5c" eId="hauptteil-1_abschnitt-2_art-12_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="764e3c2c-263d-4096-a69b-0483b0c04604" eId="hauptteil-1_abschnitt-2_art-12_abs-4_inhalt-1">
              <akn:p GUID="40e6385c-ad93-4ae5-af58-4db50633b18e" eId="hauptteil-1_abschnitt-2_art-12_abs-4_inhalt-1_text-1">Akten oder Auszüge aus Akten dürfen auch in elektronischer Form geführt werden. Insoweit kommen die Regelungen über die Verwendung und Berichtigung personenbezogener Daten in Akten zur Anwendung. Eine Abfrage personenbezogener Daten ist insoweit nur zulässig, wenn die Voraussetzungen des § 10 Absatz 1 Nummer 1 oder Nummer 2 vorliegen und die Person das 14. Lebensjahr vollendet hat. Der automatisierte Abgleich dieser personenbezogenen Daten ist nur beschränkt auf Akten eng umgrenzter Anwendungsgebiete zulässig. Bei jeder Abfrage sind für Zwecke der Datenschutzkontrolle der Zeitpunkt, die Angaben, die die Feststellung der abgefragten Daten ermöglichen, sowie Angaben zur Feststellung des Abfragenden zu protokollieren. Die protokollierten Daten dürfen nur für Zwecke der Datenschutzkontrolle, der Datensicherung oder zur Sicherstellung eines ordnungsgemäßen Betriebs der Datenverarbeitungsanlage verwendet werden. Die Protokolldaten sind am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt, zu löschen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="01561c5b-76fe-4056-80ae-daf76def3306" eId="hauptteil-1_abschnitt-2_art-13" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="852b415f-cc90-4ec3-87d5-1c899616e56f" eId="hauptteil-1_abschnitt-2_art-13_bezeichnung-1"> § 14 </akn:num>
          <akn:heading GUID="c779ab53-d585-4f79-8282-e20ed4c1fdc4" eId="hauptteil-1_abschnitt-2_art-13_überschrift-1"> Dateianordnungen </akn:heading>
          <akn:paragraph GUID="5ca8774b-9201-441e-9377-1e003b0cab9a" eId="hauptteil-1_abschnitt-2_art-13_abs-1">
            <akn:num GUID="518d9367-a17f-4116-9bea-c635f6b4131b" eId="hauptteil-1_abschnitt-2_art-13_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="e3990fa8-65fc-4ac7-b0c9-62bd5b8a5510" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1">
              <akn:intro GUID="01c5fd24-74e9-4b78-84f4-5fb6c1e60a5d" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_intro-1">
                <akn:p GUID="0ff0139c-0d42-403a-be70-682ca8c750d7" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_intro-1_text-1"> Für jede automatisierte Datei beim Bundesamt für Verfassungsschutz nach § 6 oder § 10 sind in einer Dateianordnung, die der Zustimmung des Bundesministeriums des Innern, für Bau und Heimat bedarf, festzulegen: </akn:p>
              </akn:intro>
              <akn:point GUID="8e1c8d95-b412-44f4-83aa-eb852debb40c" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="9d6067d2-ce68-4305-9bad-94d3ab190267" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="0c91c77d-dfda-4535-864c-15979e88a962" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="ba158386-3269-4f7c-8531-27523e878172" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> Bezeichnung der Datei, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="a3d48f66-580b-45e0-b519-e973e51df44e" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="57927c65-350a-452d-9cfb-1557f21b11ab" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="bb97a1c3-694a-4faa-882a-7568b86e30d8" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="a75c3645-4078-427e-8914-a21f94a3d7f4" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> Zweck der Datei, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="bd280d17-eed4-4f98-bc1d-5b4b1014ce77" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="f0059813-aba1-4a7c-bdb0-a033393d6c26" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="e944dae6-050a-4bba-b410-780bcb9931d3" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="befbd24a-c048-49c8-8612-b411e9def9f3" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> Voraussetzungen der Speicherung, Übermittlung und Nutzung (betroffener Personenkreis, Arten der Daten), </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="190aadb5-df6d-413b-9ed9-b15a889b4b80" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4">
                <akn:num GUID="1c638ad7-7a10-4a98-bf48-aebce12984a6" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="00955866-3e56-4716-a843-3a0eb9a23b9f" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="66a59d60-351f-4e92-8b61-8aee44cade0e" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-4_inhalt-1_text-1"> Anlieferung oder Eingabe, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="9676700c-8b12-4610-964c-83af2d400256" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5">
                <akn:num GUID="e19d4bc1-0532-46e3-afca-7f6762e663d5" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:content GUID="96c40d8f-913a-41d8-afe3-ab7317790ab5" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="f2d907c5-c3b2-48ea-932d-f72a37c58895" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-5_inhalt-1_text-1"> Zugangsberechtigung, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="e95ef019-14d5-4e7b-9924-b3142fbcf78e" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6">
                <akn:num GUID="6059aedb-f957-4ef0-b393-74b9f84cc11f" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_bezeichnung-1"> 6. </akn:num>
                <akn:content GUID="8ede9369-09f6-4684-9d75-5209cdcf2ffd" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_inhalt-1">
                  <akn:p GUID="d10cd8c7-d012-480b-9309-be3e1e7b2e6d" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-6_inhalt-1_text-1"> Überprüfungsfristen, Speicherungsdauer, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="299cb121-1771-4eef-9694-48e2aaa4cb91" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7">
                <akn:num GUID="133b0318-58bb-414f-b548-d262f9a22890" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_bezeichnung-1"> 7. </akn:num>
                <akn:content GUID="e28095b2-f508-4fa7-b2f5-35ff24812fbe" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_inhalt-1">
                  <akn:p GUID="c9851bf0-60bf-4aa3-90d2-f02f4f7abe47" eId="hauptteil-1_abschnitt-2_art-13_abs-1_untergl-1_listenelem-7_inhalt-1_text-1"> Protokollierung. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="3709462c-ef22-49c1-8a90-71f5ad06dbb8" eId="hauptteil-1_abschnitt-2_art-13_abs-2">
            <akn:num GUID="85bae0e6-5ce3-4110-abe8-1e0812942412" eId="hauptteil-1_abschnitt-2_art-13_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="59b8c1f9-4d47-46a2-87d5-4a62423673d0" eId="hauptteil-1_abschnitt-2_art-13_abs-2_inhalt-1">
              <akn:p GUID="e389f234-989b-40ef-aaeb-e240efaff3e4" eId="hauptteil-1_abschnitt-2_art-13_abs-2_inhalt-1_text-1"> Die Speicherung personenbezogener Daten ist auf das erforderliche Maß zu beschränken. In angemessenen Abständen ist die Notwendigkeit der Weiterführung oder Änderung der Dateien zu überprüfen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="5b0071cd-0b6a-4746-80ef-3ce1f9b71135" eId="hauptteil-1_abschnitt-2_art-13_abs-3">
            <akn:num GUID="4cbeef8e-d288-48b1-8c28-3d075a0f64ba" eId="hauptteil-1_abschnitt-2_art-13_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="88bef25f-c3c2-48ab-bb12-e98e1286ed26" eId="hauptteil-1_abschnitt-2_art-13_abs-3_inhalt-1">
              <akn:p GUID="3212e091-e3e4-4fd5-b8a0-4085e7409e93" eId="hauptteil-1_abschnitt-2_art-13_abs-3_inhalt-1_text-1"> Ist im Hinblick auf die Dringlichkeit der Aufgabenerfüllung die vorherige Mitwirkung der in Absatz 1 genannten Stellen nicht möglich, so kann das Bundesamt für Verfassungsschutz eine Sofortanordnung treffen. Das Verfahren nach Absatz 1 ist unverzüglich nachzuholen. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="51a949b6-b6ef-4705-9109-9ed6a372bccb" eId="hauptteil-1_abschnitt-2_art-14" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="6a9e1bf2-f61f-4954-8317-3bf69c85386b" eId="hauptteil-1_abschnitt-2_art-14_bezeichnung-1"> § 15 </akn:num>
          <akn:heading GUID="7c230411-6de7-4f63-91ff-4cdc0da65b19" eId="hauptteil-1_abschnitt-2_art-14_überschrift-1"> Auskunft an den Betroffenen </akn:heading>
          <akn:paragraph GUID="c743010c-f482-4137-8b23-fa7924953dd6" eId="hauptteil-1_abschnitt-2_art-14_abs-1">
            <akn:num GUID="07287bc5-7cd1-425c-b77e-bdc2f80e5c81" eId="hauptteil-1_abschnitt-2_art-14_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="f4d777a0-534b-4ae2-9fd5-6023dd6886a2" eId="hauptteil-1_abschnitt-2_art-14_abs-1_inhalt-1">
              <akn:p GUID="1ef12b1f-4fb7-49ef-9520-4c4f14e4c3c2" eId="hauptteil-1_abschnitt-2_art-14_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz erteilt dem Betroffenen über zu seiner Person gespeicherte Daten auf Antrag unentgeltlich Auskunft, soweit er hierzu auf einen konkreten Sachverhalt hinweist und ein besonderes Interesse an einer Auskunft darlegt. Zu personenbezogenen Daten in Akten erstreckt sich die Auskunft auf alle Daten, die über eine Speicherung gemäß § 10 Absatz 1 auffindbar sind. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="de88cf59-f405-4668-9178-e406da929dbe" eId="hauptteil-1_abschnitt-2_art-14_abs-2">
            <akn:num GUID="fe6252a7-672e-47dd-9d71-b0a23b1c53bc" eId="hauptteil-1_abschnitt-2_art-14_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:list GUID="1fc3ef64-b58a-484a-a11a-287259014a08" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1">
              <akn:intro GUID="67006ca1-ce9e-4e3a-b152-62ff184bd3c4" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_intro-1">
                <akn:p GUID="5eebc475-2005-4b7a-bab2-7cff685c207a" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_intro-1_text-1"> Die Auskunftserteilung unterbleibt, soweit </akn:p>
              </akn:intro>
              <akn:point GUID="5f9bfe7f-95e7-4923-90fa-6bba1df91996" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1">
                <akn:num GUID="366da49f-c79b-48dd-aad1-afed40c3caca" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="aad301c0-4790-4415-8ddb-ee13bd09559a" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="0459b7c9-4f95-40fa-8792-ed9fff58ec9c" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-1_inhalt-1_text-1"> eine Gefährdung der Aufgabenerfüllung durch die Auskunftserteilung zu besorgen ist, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="716de227-03f1-4aac-8d66-5d9872518f51" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2">
                <akn:num GUID="fdc7472b-0090-4acd-8ad2-fbb35e46ea79" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="5073ffa0-0739-450c-be50-dc850675ff85" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="f5a8d7f5-6d07-4dc3-9627-7945e13f33aa" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-2_inhalt-1_text-1"> durch die Auskunftserteilung Quellen gefährdet sein können oder die Ausforschung des Erkenntnisstandes oder der Arbeitsweise des Bundesamtes für Verfassungsschutz zu befürchten ist, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="53bfaba6-7f8a-4040-9354-9bad17a4d0bb" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3">
                <akn:num GUID="b1729be5-a25f-4d27-9b9b-7d9f0ac16183" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="5b337339-6ada-4684-ba7e-2af8f0f04b30" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="8d3a6030-09e4-41ce-a5c1-54f41da7f7b3" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-3_inhalt-1_text-1"> die Auskunft die öffentliche Sicherheit gefährden oder sonst dem Wohl des Bundes oder eines Landes Nachteile bereiten würde oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="88bd3404-689e-49ac-a2b5-cd9a12dccdd4" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4">
                <akn:num GUID="7691bb2a-0e7e-4b86-9c5f-c14bc5030795" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="f6e27882-7ae9-4965-a7d2-3e141933905a" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="20554c94-9be2-433c-ae74-403cf88b165b" eId="hauptteil-1_abschnitt-2_art-14_abs-2_untergl-1_listenelem-4_inhalt-1_text-1"> die Daten oder die Tatsache der Speicherung nach einer Rechtsvorschrift oder ihrem Wesen nach, insbesondere wegen der überwiegenden berechtigten Interessen eines Dritten, geheimgehalten werden müssen. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="bcab2c4e-f6b3-4fdf-882d-858d6613cf58" eId="hauptteil-1_abschnitt-2_art-14_abs-3">
            <akn:num GUID="b718cc42-f669-4ab0-9e83-4242d0fa81f9" eId="hauptteil-1_abschnitt-2_art-14_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="6e99ef2a-9d01-4ac5-ba5a-c79da3f753ff" eId="hauptteil-1_abschnitt-2_art-14_abs-3_inhalt-1">
              <akn:p GUID="494d3a34-bf6e-499b-a7f3-014fd232b603" eId="hauptteil-1_abschnitt-2_art-14_abs-3_inhalt-1_text-1"> Die Auskunftsverpflichtung erstreckt sich nicht auf die Herkunft der Daten und die Empfänger von Übermittlungen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d76a1301-d101-41ed-bc95-8e1be00938c8" eId="hauptteil-1_abschnitt-2_art-14_abs-4">
            <akn:num GUID="b8323371-4bc1-4046-998c-7c32b23096fa" eId="hauptteil-1_abschnitt-2_art-14_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="34b45ba3-3bc2-467e-9f54-ec311efb1e36" eId="hauptteil-1_abschnitt-2_art-14_abs-4_inhalt-1">
              <akn:p GUID="5b21ce22-8936-48a8-9d57-715f31242e0f" eId="hauptteil-1_abschnitt-2_art-14_abs-4_inhalt-1_text-1"> Die Ablehnung der Auskunftserteilung bedarf keiner Begründung, soweit dadurch der Zweck der Auskunftsverweigerung gefährdet würde. Die Gründe der Auskunftsverweigerung sind aktenkundig zu machen. Wird die Auskunftserteilung abgelehnt, ist der Betroffene auf die Rechtsgrundlage für das Fehlen der Begründung und darauf hinzuweisen, daß er sich an den Bundesbeauftragten für den Datenschutz wenden kann. Dem Bundesbeauftragten für den Datenschutz ist auf sein Verlangen Auskunft zu erteilen, soweit nicht das Bundesministerium des Innern, für Bau und Heimat im Einzelfall feststellt, daß dadurch die Sicherheit des Bundes oder eines Landes gefährdet würde. Mitteilungen des Bundesbeauftragten an den Betroffenen dürfen keine Rückschlüsse auf den Erkenntnisstand des Bundesamtes für Verfassungsschutz zulassen, sofern es nicht einer weitergehenden Auskunft zustimmt. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="459eccba-1f23-4c1f-b717-ca8632209d27" eId="hauptteil-1_abschnitt-2_art-15" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="b3ee576f-70ee-4d23-a855-8ddb9dc12cf5" eId="hauptteil-1_abschnitt-2_art-15_bezeichnung-1"> § 16 </akn:num>
          <akn:heading GUID="8357284c-4aad-4cdf-a802-e6e943e548bd" eId="hauptteil-1_abschnitt-2_art-15_überschrift-1"> Verfassungsschutz durch Aufklärung der Öffentlichkeit </akn:heading>
          <akn:paragraph GUID="a528a7a8-991e-4136-81c9-63bb608c0707" eId="hauptteil-1_abschnitt-2_art-15_abs-1">
            <akn:num GUID="10e24795-45a3-4cca-863c-d4e2f5d819be" eId="hauptteil-1_abschnitt-2_art-15_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="a2f681f7-7a10-4a02-b639-1ba46aa6181a" eId="hauptteil-1_abschnitt-2_art-15_abs-1_inhalt-1">
              <akn:p GUID="4cd59d88-49f9-4ac6-9454-d00dea6135eb" eId="hauptteil-1_abschnitt-2_art-15_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz informiert die Öffentlichkeit über Bestrebungen und Tätigkeiten nach § 3 Absatz 1, soweit hinreichend gewichtige tatsächliche Anhaltspunkte hierfür vorliegen, sowie über präventiven Wirtschaftsschutz. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="a0615892-cd67-4c75-9681-edcbd78276df" eId="hauptteil-1_abschnitt-2_art-15_abs-2">
            <akn:num GUID="0d2509e8-9cbe-4192-843b-a766a67668ad" eId="hauptteil-1_abschnitt-2_art-15_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="87b8bcff-3067-4af7-b072-189ee8cd4271" eId="hauptteil-1_abschnitt-2_art-15_abs-2_inhalt-1">
              <akn:p GUID="e5ec21c6-f810-44ab-9796-f1415bc48060" eId="hauptteil-1_abschnitt-2_art-15_abs-2_inhalt-1_text-1"> Das Bundesministerium des Innern, für Bau und Heimat informiert die Öffentlichkeit über Bestrebungen und Tätigkeiten nach § 3 Absatz 1, soweit hinreichend gewichtige tatsächliche Anhaltspunkte hierfür vorliegen, mindestens einmal jährlich in einem zusammenfassenden Bericht insbesondere zu aktuellen Entwicklungen. In dem Bericht sind die Zuschüsse des Bundeshaushaltes an das Bundesamt für Verfassungsschutz und den Militärischen Abschirmdienst sowie die jeweilige Gesamtzahl ihrer Bediensteten anzugeben. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="382c4127-618f-4d38-8a28-0c2f381d4e13" eId="hauptteil-1_abschnitt-2_art-15_abs-3">
            <akn:num GUID="cc0eabe6-849c-412a-b58f-3d3b1a5e7db9" eId="hauptteil-1_abschnitt-2_art-15_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="c649db2b-a0e9-4b5c-baf0-60d0ad248322" eId="hauptteil-1_abschnitt-2_art-15_abs-3_inhalt-1">
              <akn:p GUID="21698216-ea42-4c4e-9b3d-4c8d84d388e1" eId="hauptteil-1_abschnitt-2_art-15_abs-3_inhalt-1_text-1"> Bei der Information nach den Absätzen 1 und 2 dürfen auch personenbezogene Daten bekanntgegeben werden, wenn die Bekanntgabe für das Verständnis des Zusammenhanges oder der Darstellung von Organisationen oder unorganisierten Gruppierungen erforderlich ist und die Interessen der Allgemeinheit das schutzwürdige Interesse des Betroffenen überwiegen. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:section>
      <akn:section GUID="82392026-f2ea-4ab2-b4c8-1a7cfc3bfd6a" eId="hauptteil-1_abschnitt-3">
        <akn:num GUID="d9ed0214-317e-4171-adbf-6b7191e834c3" eId="hauptteil-1_abschnitt-3_bezeichnung-1"> Dritter Abschnitt </akn:num>
        <akn:heading GUID="cdd010fa-e3b3-40e9-9f21-c21fba4b89b7" eId="hauptteil-1_abschnitt-3_überschrift-1"> Übermittlungsvorschriften </akn:heading>
        <akn:article GUID="26c3e365-6324-4e78-84d3-f6c7ef951399" eId="hauptteil-1_abschnitt-3_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="6c44a32a-dfe7-4e06-95fb-02fba33d4c25" eId="hauptteil-1_abschnitt-3_art-1_bezeichnung-1"> § 17 </akn:num>
          <akn:heading GUID="6f8ea94e-b104-417c-afb5-84c32c8b5504" eId="hauptteil-1_abschnitt-3_art-1_überschrift-1"> Zulässigkeit von Ersuchen </akn:heading>
          <akn:paragraph GUID="7689b98f-d6a2-4bc0-9197-7c60fb650e04" eId="hauptteil-1_abschnitt-3_art-1_abs-1">
            <akn:num GUID="4a47431a-e1fd-4f0a-87ea-d9f3bef34409" eId="hauptteil-1_abschnitt-3_art-1_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="ef4add20-793c-4ee3-9859-a0383a873fbc" eId="hauptteil-1_abschnitt-3_art-1_abs-1_inhalt-1">
              <akn:p GUID="c2dd08f4-c5b3-4ade-b794-19a3d97678bc" eId="hauptteil-1_abschnitt-3_art-1_abs-1_inhalt-1_text-1"> Wird nach den Bestimmungen dieses Abschnittes um Übermittlung von personenbezogenen Daten ersucht, dürfen nur die Daten übermittelt werden, die bei der ersuchten Behörde bekannt sind oder aus allgemein zugänglichen Quellen entnommen werden können. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="da7c94ea-be35-4566-8a9e-2c34353dc2a0" eId="hauptteil-1_abschnitt-3_art-1_abs-2">
            <akn:num GUID="77676bfe-4f7b-4124-be37-dc2a433c24e8" eId="hauptteil-1_abschnitt-3_art-1_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="9eaf18d2-8187-4096-9baf-6f99ae5e913d" eId="hauptteil-1_abschnitt-3_art-1_abs-2_inhalt-1">
              <akn:p GUID="a3390673-5097-412c-a0d0-87e93c09b3b2" eId="hauptteil-1_abschnitt-3_art-1_abs-2_inhalt-1_text-1"> Absatz 1 gilt nicht für besondere Ersuchen der Verfassungsschutzbehörden, des Militärischen Abschirmdienstes und des Bundesnachrichtendienstes um solche Daten, die bei der Wahrnehmung grenzpolizeilicher Aufgaben bekannt werden. Die Zulässigkeit dieser besonderen Ersuchen und ihre Erledigung regelt das Bundesministerium des Innern im Benehmen mit dem Bundeskanzleramt und dem Bundesministerium der Verteidigung in einer Dienstanweisung. Es unterrichtet das Parlamentarische Kontrollgremium über ihren Erlaß und erforderliche Änderungen. Satz 2 und 3 gilt nicht für die besonderen Ersuchen zwischen Behörden desselben Bundeslandes. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="599c5afd-1bba-4e65-83c4-8c5c3003cdd7" eId="hauptteil-1_abschnitt-3_art-1_abs-3">
            <akn:num GUID="cb0974b0-d4d9-45d0-a984-282bc1201a4c" eId="hauptteil-1_abschnitt-3_art-1_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="45460c67-82b8-4a7f-8518-f2b2fc5ffe07" eId="hauptteil-1_abschnitt-3_art-1_abs-3_inhalt-1">
              <akn:p GUID="02f8ff63-f4aa-4b28-b942-6ff42b333d10" eId="hauptteil-1_abschnitt-3_art-1_abs-3_inhalt-1_text-1"> (weggefallen) </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="57b0433d-aaf2-4040-bc59-c7f4084f5d63" eId="hauptteil-1_abschnitt-3_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="a6bb493e-c4fd-4346-ae1c-389d677fa69d" eId="hauptteil-1_abschnitt-3_art-2_bezeichnung-1"> § 18 </akn:num>
          <akn:heading GUID="bb59c890-e190-49bd-90f1-d32540c9d78d" eId="hauptteil-1_abschnitt-3_art-2_überschrift-1"> Übermittlung von Informationen an die Verfassungsschutzbehörden </akn:heading>
          <akn:paragraph GUID="2873fdcf-4346-4f0d-84fc-28c41d8369c5" eId="hauptteil-1_abschnitt-3_art-2_abs-1">
            <akn:num GUID="37ea6661-353e-432e-8b5c-0a9827f0c34d" eId="hauptteil-1_abschnitt-3_art-2_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="1d239a02-faf5-44ca-976b-0370e90ca9d9" eId="hauptteil-1_abschnitt-3_art-2_abs-1_inhalt-1">
              <akn:p GUID="8be4b3f4-0473-400b-96c6-1d77521d77ed" eId="hauptteil-1_abschnitt-3_art-2_abs-1_inhalt-1_text-1"> Die Behörden des Bundes, der bundesunmittelbaren juristischen Personen des öffentlichen Rechts, die Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die Behörden des Zollfahndungsdienstes sowie andere Zolldienststellen, soweit diese Aufgaben nach dem Bundesgrenzschutzgesetz wahrnehmen, unterrichten von sich aus das Bundesamt für Verfassungsschutz oder die Verfassungsschutzbehörde des Landes über die ihnen bekanntgewordenen Tatsachen, die sicherheitsgefährdende oder geheimdienstliche Tätigkeiten für eine fremde Macht oder Bestrebungen im Geltungsbereich dieses Gesetzes erkennen lassen, die durch Anwendung von Gewalt oder darauf gerichtete Vorbereitungshandlungen gegen die in § 3 Abs. 1 Nr. 1 und 3 genannten Schutzgüter gerichtet sind. Über Satz 1 hinausgehende Unterrichtungspflichten nach dem Gesetz über den Militärischen Abschirmdienst oder dem Gesetz über den Bundesnachrichtendienst bleiben unberührt. Auf die Übermittlung von Informationen zwischen Behörden desselben Bundeslandes findet Satz 1 keine Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="2d9d7037-383f-46b0-88b9-b57a6243b3c1" eId="hauptteil-1_abschnitt-3_art-2_abs-2">
            <akn:num GUID="fb95b64c-a144-4825-a393-43d549297350" eId="hauptteil-1_abschnitt-3_art-2_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="631d967f-ce11-4d7e-a82f-622993814f03" eId="hauptteil-1_abschnitt-3_art-2_abs-2_inhalt-1">
              <akn:p GUID="c0311b5d-d4df-469a-8c28-1997608ccf40" eId="hauptteil-1_abschnitt-3_art-2_abs-2_inhalt-1_text-1"> Die Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die Behörden des Zollfahndungsdienstes sowie andere Zolldienststellen, soweit diese Aufgaben nach dem Bundesgrenzschutzgesetz wahrnehmen, und der Bundesnachrichtendienst dürfen darüber hinaus von sich aus dem Bundesamt für Verfassungsschutz oder der Verfassungsschutzbehörde des Landes auch alle anderen ihnen bekanntgewordenen Informationen einschließlich personenbezogener Daten über Bestrebungen nach § 3 Abs. 1 übermitteln, wenn tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung für die Erfüllung der Aufgaben der Verfassungsschutzbehörde erforderlich ist. Absatz 1 Satz 3 findet Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="f30ab3e6-034e-49d0-96d1-67a2178b9b60" eId="hauptteil-1_abschnitt-3_art-2_abs-3">
            <akn:num GUID="859a3746-435d-4e7d-b1c7-1f10503146ec" eId="hauptteil-1_abschnitt-3_art-2_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="1a1dd4db-35cb-4d3a-843d-d5e5c26f3945" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1">
              <akn:intro GUID="26d60a66-e872-444a-bee0-d343ad858207" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_intro-1">
                <akn:p GUID="81806317-338f-46a8-b326-02434c399906" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf zur Erfüllung seiner Aufgaben die Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien sowie andere Behörden um Übermittlung der zur Erfüllung seiner Aufgaben erforderlichen Informationen einschließlich personenbezogener Daten ersuchen, wenn sie nicht aus allgemein zugänglichen Quellen oder nur mit übermäßigem Aufwand oder nur durch eine den Betroffenen stärker belastende Maßnahme erhoben werden können. Unter den gleichen Voraussetzungen dürfen Verfassungsschutzbehörden der Länder </akn:p>
              </akn:intro>
              <akn:point GUID="3d09ca5e-3497-4b2d-ba76-c1524661689c" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="dde99ea4-0a28-42ae-81bd-db6b2ff1105b" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="8f23253e-eb1b-4840-8816-bf1d04ea677c" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="cb8db0e6-d47a-49dc-9f28-aeb0b93175b0" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> Behörden des Bundes und der bundesunmittelbaren juristischen Personen des öffentlichen Rechts, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ececcbec-0252-4453-86fe-5fed5390b066" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="647501db-054e-4137-827e-2af8792ccb85" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="5595934c-49ad-4749-8713-d870a75d2869" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="8a296837-bff7-4c00-9c79-65e0d3a1519a" eId="hauptteil-1_abschnitt-3_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, Polizeien des Bundes und anderer Länder um die Übermittlung solcher Informationen ersuchen. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="2d0674ac-efb4-46c2-9f82-f8832062f725" eId="hauptteil-1_abschnitt-3_art-2_abs-4">
            <akn:num GUID="84b2860e-f647-4634-8ef7-66b40feea9b4" eId="hauptteil-1_abschnitt-3_art-2_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="42614870-e713-4b36-8e03-70fc8449743e" eId="hauptteil-1_abschnitt-3_art-2_abs-4_inhalt-1">
              <akn:p GUID="3a8395df-7538-46f0-be97-b41ca6611b47" eId="hauptteil-1_abschnitt-3_art-2_abs-4_inhalt-1_text-1"> Würde durch die Übermittlung nach Absatz 3 Satz 1 der Zweck der Maßnahme gefährdet oder der Betroffene unverhältnismäßig beeinträchtigt, darf das Bundesamt für Verfassungsschutz bei der Wahrnehmung der Aufgaben nach § 3 Abs. 1 Nr. 2 und 3 sowie bei der Beobachtung terroristischer Bestrebungen amtliche Register einsehen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="bef0371f-63ab-4682-8051-a8518abbb83f" eId="hauptteil-1_abschnitt-3_art-2_abs-5">
            <akn:num GUID="b7f43b01-1be4-44b7-9afb-e1046b945442" eId="hauptteil-1_abschnitt-3_art-2_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="f5f0097c-06bb-4c96-bf81-922080c87f23" eId="hauptteil-1_abschnitt-3_art-2_abs-5_inhalt-1">
              <akn:p GUID="9e2a2e33-5abe-466a-8e10-8c6cc00f1a48" eId="hauptteil-1_abschnitt-3_art-2_abs-5_inhalt-1_text-1">Die Ersuchen nach Absatz 3 sind aktenkundig zu machen. Über die Einsichtnahme nach Absatz 4 hat das Bundesamt für Verfassungsschutz einen Nachweis zu führen, aus dem der Zweck und die Veranlassung, die ersuchte Behörde und die Aktenfundstelle hervorgehen; die Nachweise sind gesondert aufzubewahren, gegen unberechtigten Zugriff zu sichern und am Ende des Kalenderjahres, das dem Jahr ihrer Erstellung folgt, zu vernichten.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="7605b755-298d-47e4-a2e4-04d76d5d4c40" eId="hauptteil-1_abschnitt-3_art-2_abs-6">
            <akn:num GUID="e007af84-8d74-4190-a988-466b860275ca" eId="hauptteil-1_abschnitt-3_art-2_abs-6_bezeichnung-1"> (6) </akn:num>
            <akn:content GUID="1a65cac8-34ad-41c0-ab7d-aa3ee1fbf079" eId="hauptteil-1_abschnitt-3_art-2_abs-6_inhalt-1">
              <akn:p GUID="f843f3d6-13fd-497a-af5b-d2af5d2d66c7" eId="hauptteil-1_abschnitt-3_art-2_abs-6_inhalt-1_text-1"> Die Übermittlung personenbezogener Daten, die auf Grund einer Maßnahme nach § 100a der Strafprozeßordnung bekanntgeworden sind, ist nach den Vorschriften der Absätze 1, 2 und 3 nur zulässig, wenn tatsächliche Anhaltspunkte dafür bestehen, daß jemand eine der in § 3 des Artikel 10-Gesetzes genannten Straftaten plant, begeht oder begangen hat. Auf die einer Verfassungsschutzbehörde nach Satz 1 übermittelten Kenntnisse und Unterlagen findet § 4 Abs. 1 und 4 des Artikel 10-Gesetzes entsprechende Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="8043b838-186c-4f4c-b6dc-d0f2132918a4" eId="hauptteil-1_abschnitt-3_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8ab16146-6795-4153-bfc2-d0829c8ebc9b" eId="hauptteil-1_abschnitt-3_art-3_bezeichnung-1"> § 19 </akn:num>
          <akn:heading GUID="a347bc90-9f8d-4f68-87c0-c973424dfad0" eId="hauptteil-1_abschnitt-3_art-3_überschrift-1"> Übermittlung personenbezogener Daten durch das Bundesamt für Verfassungsschutz </akn:heading>
          <akn:paragraph GUID="f30cf989-dc15-4203-a654-1340dc95e716" eId="hauptteil-1_abschnitt-3_art-3_abs-1">
            <akn:num GUID="630328c1-a107-406c-a0ee-7ff242332ce5" eId="hauptteil-1_abschnitt-3_art-3_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="0e5811b9-efe2-4b0a-b637-98ce0fa89a2a" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1">
              <akn:intro GUID="8e16d31e-e44f-47fc-bc3c-6864d493c2d9" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_intro-1">
                <akn:p GUID="2d1264fc-ff20-4b37-a42a-247215fabd03" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf personenbezogene Daten, die mit den Mitteln nach § 8 Absatz 2 erhoben worden sind, an die Staatsanwaltschaften, die Finanzbehörden nach § 386 Absatz 1 der Abgabenordnung, die Polizeien, die mit der Steuerfahndung betrauten Dienststellen der Landesfinanzbehörden, die Behörden des Zollfahndungsdienstes sowie andere Zolldienststellen, soweit diese Aufgaben nach dem Bundespolizeigesetz wahrnehmen, übermitteln, soweit dies erforderlich ist zur </akn:p>
              </akn:intro>
              <akn:point GUID="b60290f9-ab9e-4721-bf6f-1c08ac9ceee9" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="3b51cdec-ad35-4af4-8dc8-92fce0b2b2ca" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="a5727ef6-8604-47f6-aedb-c88d556c9b7f" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="2ba0529f-0ff5-4c6f-b2f9-c9a59ca86e86" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> Erfüllung eigener Aufgaben der Informationsgewinnung (§ 8 Absatz 1 Satz 2 und 3), </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="5bd44aad-9d6a-4876-ac06-64538dbfe075" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="51324d35-bfb0-4e10-85d0-65f81d83abce" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="c40ab128-0336-4749-83e5-dd49d9fcad5c" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="fd75ee3b-1747-44ed-8a7b-870188a62172" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> Abwehr einer im Einzelfall bestehenden Gefahr für den Bestand oder die Sicherheit des Bundes oder eines Landes oder für Leib, Leben, Gesundheit oder Freiheit einer Person oder für Sachen von erheblichem Wert, deren Erhaltung im öffentlichen Interesse geboten ist, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="55815d8f-ac74-4f6c-9f6d-7bb860bf3fa8" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="8e801fd1-f3e8-47d0-8b85-0aecc6b8d41a" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="09b572bf-0ebf-4069-aa8d-5435feb47f87" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="edf2e721-2fbc-4ed4-9d8f-9d2978a67ec1" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> Verhinderung oder sonstigen Verhütung von Straftaten von erheblicher Bedeutung oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ed91bac3-0b74-432c-9dd4-9c162b0776f5" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4">
                <akn:num GUID="13b6e0e6-d794-46ae-a5c5-09bbf1c278ae" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="77368dbf-5601-43d9-afcf-f5379246021e" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="d8cecbe0-0357-4b26-ac20-e1c485cce4a4" eId="hauptteil-1_abschnitt-3_art-3_abs-1_untergl-1_listenelem-4_inhalt-1_text-1"> Verfolgung von Straftaten von erheblicher Bedeutung; </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="5b972e0f-c299-4e2e-a479-985f7f89df95" eId="hauptteil-1_abschnitt-3_art-3_abs-2">
            <akn:num GUID="0db968fb-fd2e-4316-86dc-a9320906ae66" eId="hauptteil-1_abschnitt-3_art-3_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="f1083d3b-e1c7-4ca8-96a3-a4e8677c66d1" eId="hauptteil-1_abschnitt-3_art-3_abs-2_inhalt-1">
              <akn:p GUID="0eefa65a-5ee5-49ba-b529-b5957e28ee1c" eId="hauptteil-1_abschnitt-3_art-3_abs-2_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf personenbezogene Daten an Dienststellen der Stationierungsstreitkräfte übermitteln, soweit die Bundesrepublik Deutschland dazu im Rahmen von Artikel 3 des Zusatzabkommens zu dem Abkommen zwischen den Parteien des Nordatlantikvertrages über die Rechtsstellung ihrer Truppen hinsichtlich der in der Bundesrepublik Deutschland stationierten ausländischen Truppen vom 3. August 1959 (BGBl. 1961 II S. 1183, 1218) verpflichtet ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="bbe9a40d-d785-4032-a2a1-0d9ea348daab" eId="hauptteil-1_abschnitt-3_art-3_abs-3">
            <akn:num GUID="ba73b628-45ec-4183-8c28-58645f5c1f3d" eId="hauptteil-1_abschnitt-3_art-3_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="21867ed1-d1e2-4338-aefc-2209abff84d6" eId="hauptteil-1_abschnitt-3_art-3_abs-3_inhalt-1">
              <akn:p GUID="1cb50e56-ef75-491b-9d9d-be805c85a9be" eId="hauptteil-1_abschnitt-3_art-3_abs-3_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf personenbezogene Daten an ausländische öffentliche Stellen sowie an über- und zwischen*-staatliche Stellen übermitteln, wenn die Übermittlung zur Erfüllung seiner Aufgaben oder zur Wahrung erheblicher Sicherheitsinteressen des Empfängers erforderlich ist. Die Übermittlung unterbleibt, wenn auswärtige Belange der Bundesrepublik Deutschland oder überwiegende schutzwürdige Interessen des Betroffenen entgegenstehen. Die Übermittlung ist aktenkundig zu machen. Der Empfänger ist darauf hinzuweisen, daß die übermittelten Daten nur zu dem Zweck verwendet werden dürfen, zu dem sie ihm übermittelt wurden, und das Bundesamt für Verfassungsschutz sich vorbehält, um Auskunft über die vorgenommene Verwendung der Daten zu bitten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="ec737143-139e-4e28-bdc3-5b48f7312603" eId="hauptteil-1_abschnitt-3_art-3_abs-4">
            <akn:num GUID="67d4bb37-2e75-4f80-9a37-0caabd934dae" eId="hauptteil-1_abschnitt-3_art-3_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="fca47431-5c4e-479a-bff3-7d629cf81ee5" eId="hauptteil-1_abschnitt-3_art-3_abs-4_inhalt-1">
              <akn:p GUID="d9be1bbf-7d34-4666-be81-12f4e024e7b1" eId="hauptteil-1_abschnitt-3_art-3_abs-4_inhalt-1_text-1"> Personenbezogene Daten dürfen an andere Stellen nicht übermittelt werden, es sei denn, dass dies zum Schutz der freiheitlichen demokratischen Grundordnung, des Bestandes oder der Sicherheit des Bundes oder eines Landes erforderlich ist und der Bundesminister des Innern seine Zustimmung erteilt hat. Das Bundesamt für Verfassungsschutz führt über die Auskunft nach Satz 1 einen Nachweis, aus dem der Zweck der Übermittlung, ihre Veranlassung, die Aktenfundstelle und der Empfänger hervorgehen; die Nachweise sind gesondert aufzubewahren, gegen unberechtigten Zugriff zu sichern und am Ende des Kalenderjahres, das dem Jahr ihrer Erstellung folgt, zu vernichten. Der Empfänger darf die übermittelten Daten nur für den Zweck verwenden, zu dem sie ihm übermittelt wurden. Der Empfänger ist auf die Verwendungsbeschränkung und darauf hinzuweisen, dass das Bundesamt für Verfassungsschutz sich vorbehält, um Auskunft über die vorgenommene Verwendung der Daten zu bitten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="4dfe7883-7c10-4c25-8483-c457d4334fbc" eId="hauptteil-1_abschnitt-3_art-3_abs-5">
            <akn:num GUID="a09938f2-919b-40e2-9e82-7e45a61a30ec" eId="hauptteil-1_abschnitt-3_art-3_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="2f2de7aa-3a10-4cad-b50d-1e975251229f" eId="hauptteil-1_abschnitt-3_art-3_abs-5_inhalt-1">
              <akn:p GUID="b75ea314-b6cc-4778-8fa2-0e8c92046524" eId="hauptteil-1_abschnitt-3_art-3_abs-5_inhalt-1_text-1"> (weggefallen) </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="ff31090d-f6df-474c-88e4-f760caf30424" eId="hauptteil-1_abschnitt-3_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="4221d828-d3c9-4f60-af6e-72d0dd1b31fe" eId="hauptteil-1_abschnitt-3_art-4_bezeichnung-1"> § 20 </akn:num>
          <akn:heading GUID="c90335eb-5f7a-4c32-be90-7c47827a2ecd" eId="hauptteil-1_abschnitt-3_art-4_überschrift-1"> Übermittlung von Informationen durch das Bundesamt für Verfassungsschutz an Strafverfolgungs- und Sicherheitsbehörden in Angelegenheiten des Staats- und Verfassungsschutzes </akn:heading>
          <akn:paragraph GUID="43b9e0d6-f030-4b71-bd5a-939333eeb3f0" eId="hauptteil-1_abschnitt-3_art-4_abs-1">
            <akn:num GUID="6d794c11-dc06-448e-89dc-d6aaf2fe9348" eId="hauptteil-1_abschnitt-3_art-4_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="44ddf849-9fb2-42e8-8661-a1776b3a1f87" eId="hauptteil-1_abschnitt-3_art-4_abs-1_inhalt-1">
              <akn:p GUID="c5fe0936-d212-4860-ac43-7b4306aa84c4" eId="hauptteil-1_abschnitt-3_art-4_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz übermittelt den Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, den Polizeien von sich aus die ihm bekanntgewordenen Informationen einschließlich personenbezogener Daten, wenn tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung zur Verhinderung oder Verfolgung von Staatsschutzdelikten erforderlich ist. Delikte nach Satz 1 sind die in §§ 74a und 120 des Gerichtsverfassungsgesetzes genannten Straftaten sowie sonstige Straftaten, bei denen auf Grund ihrer Zielsetzung, des Motivs des Täters oder dessen Verbindung zu einer Organisation tatsächliche Anhaltspunkte dafür vorliegen, daß sie gegen die in Artikel 73 Nr. 10 Buchstabe b oder c des Grundgesetzes genannten Schutzgüter gerichtet sind. Das Bundesamt für Verfassungsschutz übermittelt dem Bundesnachrichtendienst von sich aus die ihm bekanntgewordenen Informationen einschließlich personenbezogener Daten, wenn tatsächliche Anhaltspunkte dafür bestehen, daß die Übermittlung für die Erfüllung der gesetzlichen Aufgaben des Empfängers erforderlich ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="62b68bf5-4734-47b3-b5f0-b64b7676597d" eId="hauptteil-1_abschnitt-3_art-4_abs-2">
            <akn:num GUID="3fb53f81-aa0b-4b45-916d-432ad53e98c8" eId="hauptteil-1_abschnitt-3_art-4_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="dfdf35d9-14b3-4220-9208-83a70dcb1c92" eId="hauptteil-1_abschnitt-3_art-4_abs-2_inhalt-1">
              <akn:p GUID="7da9d752-e628-4347-8dd1-70418189a492" eId="hauptteil-1_abschnitt-3_art-4_abs-2_inhalt-1_text-1"> Die Polizeien dürfen zur Verhinderung von Staatsschutzdelikten nach Absatz 1 Satz 2 das Bundesamt für Verfassungsschutz um Übermittlung der erforderlichen Informationen einschließlich personenbezogener Daten ersuchen. Der Bundesnachrichtendienst darf zur Erfüllung seiner Aufgaben das Bundesamt für Verfassungsschutz um die Übermittlung der erforderlichen Informationen einschließlich personenbezogener Daten ersuchen. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="caa5a11e-72e4-4430-870a-e4481cc38f44" eId="hauptteil-1_abschnitt-3_art-5" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="d565f9bf-08d0-4129-9c52-70c9c029fa4c" eId="hauptteil-1_abschnitt-3_art-5_bezeichnung-1"> § 21 </akn:num>
          <akn:heading GUID="f2b4762c-77f1-4755-98c3-0c894abfc1e8" eId="hauptteil-1_abschnitt-3_art-5_überschrift-1"> Übermittlung von Informationen durch die Verfassungsschutzbehörden der Länder an Strafverfolgungs- und Sicherheitsbehörden in Angelegenheiten des Staats- und Verfassungsschutzes </akn:heading>
          <akn:paragraph GUID="ae157d3b-7fc7-448c-bdb5-2f1b4882542d" eId="hauptteil-1_abschnitt-3_art-5_abs-1">
            <akn:num GUID="fccc95cd-2c3e-4add-9b56-da1fcda4df27" eId="hauptteil-1_abschnitt-3_art-5_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="81ed13c7-97bb-46e5-80a7-010771ba67cd" eId="hauptteil-1_abschnitt-3_art-5_abs-1_inhalt-1">
              <akn:p GUID="cb3c1705-0020-400a-912a-ab15e6c14505" eId="hauptteil-1_abschnitt-3_art-5_abs-1_inhalt-1_text-1"> Die Verfassungsschutzbehörden der Länder übermitteln den Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, den Polizeien Informationen einschließlich personenbezogener Daten unter den Voraussetzungen des § 20 Abs. 1 Satz 1 und 2 sowie Abs. 2 Satz 1. Auf die Übermittlung von Informationen zwischen Behörden desselben Bundeslandes findet Satz 1 keine Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="7d8d2ab8-de5c-43f2-9e8b-4d5d9ad2b4d1" eId="hauptteil-1_abschnitt-3_art-5_abs-2">
            <akn:num GUID="749e0f38-471b-4cc8-b2cf-b4d561c52dc7" eId="hauptteil-1_abschnitt-3_art-5_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="c748f1fd-5ee7-49e1-b345-ccb7aacfa4f0" eId="hauptteil-1_abschnitt-3_art-5_abs-2_inhalt-1">
              <akn:p GUID="130893c8-d922-4c66-98ad-34a1e07bd148" eId="hauptteil-1_abschnitt-3_art-5_abs-2_inhalt-1_text-1"> Die Verfassungsschutzbehörden der Länder übermitteln dem Bundesnachrichtendienst und dem Militärischen Abschirmdienst Informationen einschließlich personenbezogener Daten unter den Voraussetzungen des § 20 Abs. 1 Satz 3 sowie Abs. 2 Satz 2. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="b78db6ee-18a8-42cc-bf1f-751ea9a607b8" eId="hauptteil-1_abschnitt-3_art-6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="ede20c57-1a0f-45b5-b429-6d53dd3f5999" eId="hauptteil-1_abschnitt-3_art-6_bezeichnung-1"> § 22 </akn:num>
          <akn:heading GUID="a12b09c7-805e-40a2-b4ec-fe1dea110187" eId="hauptteil-1_abschnitt-3_art-6_überschrift-1"> Übermittlung von Informationen durch die Staatsanwaltschaften und Polizeien an den Militärischen Abschirmdienst </akn:heading>
          <akn:paragraph GUID="159d078e-8c53-4927-9f04-d06ff28e4287" eId="hauptteil-1_abschnitt-3_art-6_abs-1">
            <akn:num GUID="c6e616b3-69ac-47b8-8824-b7e868c4cd41" eId="hauptteil-1_abschnitt-3_art-6_abs-1_bezeichnung-1"/>
            <akn:content GUID="8bf29dbd-8e0e-4b48-bfcd-5b8ebcc2a49d" eId="hauptteil-1_abschnitt-3_art-6_abs-1_inhalt-1">
              <akn:p GUID="62997d76-b433-43b5-a3c7-acc2f941950c" eId="hauptteil-1_abschnitt-3_art-6_abs-1_inhalt-1_text-1"> Für die Übermittlung von Informationen einschließlich personenbezogener Daten durch die Staatsanwaltschaften und, vorbehaltlich der staatsanwaltschaftlichen Sachleitungsbefugnis, die Polizeien, die Behörden des Zollfahndungsdienstes sowie andere Zolldienststellen, soweit diese Aufgaben nach dem Bundespolizeigesetz wahrnehmen, an den Militärischen Abschirmdienst findet § 18 entsprechende Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="d410d381-4f82-499a-a1ee-adaddb069dbb" eId="hauptteil-1_abschnitt-3_art-7" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="ab65cb55-36f2-45f9-9bb7-af7cba36967c" eId="hauptteil-1_abschnitt-3_art-7_bezeichnung-1"> § 22a </akn:num>
          <akn:heading GUID="7da89a12-e7b4-4f81-8328-7bd416d8bfdd" eId="hauptteil-1_abschnitt-3_art-7_überschrift-1"> Projektbezogene gemeinsame Dateien </akn:heading>
          <akn:paragraph GUID="62b49147-7b7c-490b-8f68-8e6562084307" eId="hauptteil-1_abschnitt-3_art-7_abs-1">
            <akn:num GUID="c6c091ee-7a37-47d8-85c4-7f491208182c" eId="hauptteil-1_abschnitt-3_art-7_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="0fd5bd6c-3595-4958-a7c9-1dc72ee9e87d" eId="hauptteil-1_abschnitt-3_art-7_abs-1_inhalt-1">
              <akn:p GUID="17886880-0aaa-4b98-bc3f-566a36f96589" eId="hauptteil-1_abschnitt-3_art-7_abs-1_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz kann für die Dauer einer befristeten projektbezogenen Zusammenarbeit mit den Landesbehörden für Verfassungsschutz, dem Militärischen Abschirmdienst, dem Bundesnachrichtendienst, den Polizeibehörden des Bundes und der Länder und dem Zollkriminalamt eine gemeinsame Datei errichten. Die projektbezogene Zusammenarbeit bezweckt nach Maßgabe der Aufgaben und Befugnisse der in Satz 1 genannten Behörden den Austausch und die gemeinsame Auswertung von Erkenntnissen zu Bestrebungen, die durch Anwendung von Gewalt oder darauf gerichtete Vorbereitungshandlungen gegen die in § 3 Abs. 1 Nr. 1 bis 4 genannten Schutzgüter gerichtet sind. Personenbezogene Daten zu Bestrebungen nach Satz 2 dürfen unter Einsatz der gemeinsamen Datei durch die an der projektbezogenen Zusammenarbeit beteiligten Behörden im Rahmen ihrer Befugnisse verwendet werden, soweit dies in diesem Zusammenhang zur Erfüllung ihrer Aufgaben erforderlich ist. Bei der weiteren Verwendung der personenbezogenen Daten finden für die beteiligten Behörden die jeweils für sie geltenden Vorschriften über die Verwendung von Daten Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="6c2558ff-83a5-4198-8a71-4c9a773561ef" eId="hauptteil-1_abschnitt-3_art-7_abs-2">
            <akn:num GUID="a596112f-de11-4173-acb7-9ca611b3efb9" eId="hauptteil-1_abschnitt-3_art-7_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="0c5cea6c-b9bf-4833-9efa-6dc8b4c72b86" eId="hauptteil-1_abschnitt-3_art-7_abs-2_inhalt-1">
              <akn:p GUID="2c173350-ba92-48fc-9a97-189cbf2e8874" eId="hauptteil-1_abschnitt-3_art-7_abs-2_inhalt-1_text-1"> Für die Eingabe personenbezogener Daten in die gemeinsame Datei gelten die jeweiligen Übermittlungsvorschriften zugunsten der an der Zusammenarbeit beteiligten Behörden entsprechend mit der Maßgabe, dass die Eingabe nur zulässig ist, wenn die Daten allen an der projektbezogenen Zusammenarbeit teilnehmenden Behörden übermittelt werden dürfen. Eine Eingabe ist ferner nur zulässig, wenn die Behörde, die die Daten eingegeben hat, die Daten auch in eigene Dateien speichern darf. Die Behörde, die die Daten eingegeben hat, hat die Daten zu kennzeichnen. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="2697ec0a-ab13-40f4-8b0e-9ab23b92dc79" eId="hauptteil-1_abschnitt-3_art-7_abs-3">
            <akn:num GUID="e82cf46f-7690-4dcc-b996-78c4739a3bda" eId="hauptteil-1_abschnitt-3_art-7_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="0a6241ae-feb2-49a1-9de8-8bd585aca5d7" eId="hauptteil-1_abschnitt-3_art-7_abs-3_inhalt-1">
              <akn:p GUID="55a43c06-bb7b-4a04-92f1-54c99064495d" eId="hauptteil-1_abschnitt-3_art-7_abs-3_inhalt-1_text-1"> Für die Führung einer projektbezogenen gemeinsamen Datei gelten § 6 Absatz 2 Satz 5 und 6 und Absatz 3 Satz 1 und § 14 Abs. 2 entsprechend. § 15 ist mit der Maßgabe anzuwenden, dass das Bundesamt für Verfassungsschutz die Auskunft im Einvernehmen mit der Behörde erteilt, die die datenschutzrechtliche Verantwortung nach Satz 1 trägt und die beteiligte Behörde die Zulässigkeit der Auskunftserteilung nach den für sie geltenden Bestimmungen prüft. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="d5e47ce9-c98c-41cf-af81-bff8a15f6bbd" eId="hauptteil-1_abschnitt-3_art-7_abs-4">
            <akn:num GUID="eabcd6ee-c782-4117-8b8a-effa7130d0e3" eId="hauptteil-1_abschnitt-3_art-7_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="e6428832-42c0-4a4a-841b-d4d1996f163e" eId="hauptteil-1_abschnitt-3_art-7_abs-4_inhalt-1">
              <akn:p GUID="44a8f5ee-e1d1-494c-ac3a-522bf8ee5bc0" eId="hauptteil-1_abschnitt-3_art-7_abs-4_inhalt-1_text-1"> Die gemeinsame Datei nach Absatz 1 ist auf höchstens zwei Jahre zu befristen. Die Frist kann um zwei Jahre und danach um ein weiteres Jahr verlängert werden, wenn das Ziel der projektbezogenen Zusammenarbeit bei Projektende noch nicht erreicht worden ist und die Datei weiterhin für die Erreichung des Ziels erforderlich ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="ba95174c-63a6-4881-9892-94e4a9e1f236" eId="hauptteil-1_abschnitt-3_art-7_abs-5">
            <akn:num GUID="db9cc30e-520e-4da1-b4ee-df6567a7f721" eId="hauptteil-1_abschnitt-3_art-7_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:content GUID="2d336045-42f7-48ac-9854-69c0e89ecb90" eId="hauptteil-1_abschnitt-3_art-7_abs-5_inhalt-1">
              <akn:p GUID="60ee8b3b-c7fd-49f7-aa6e-43c69434f30f" eId="hauptteil-1_abschnitt-3_art-7_abs-5_inhalt-1_text-1"> Für die Berichtigung, Verarbeitungseinschränkung und Löschung der Daten zu einer Person durch die Behörde, die die Daten eingegeben hat, gelten die jeweiligen, für sie anwendbaren Vorschriften über die Berichtigung, Sperrung und Löschung der Daten entsprechend. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="66145ec2-895f-4182-a282-377aa48114d0" eId="hauptteil-1_abschnitt-3_art-7_abs-6">
            <akn:num GUID="86d3bd72-4d9d-437e-89c9-21d5a689a5d0" eId="hauptteil-1_abschnitt-3_art-7_abs-6_bezeichnung-1"> (6) </akn:num>
            <akn:list GUID="716eb916-ca25-4c63-acd3-1b1a9d36f3e8" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1">
              <akn:intro GUID="0ca56a04-5f36-4ec8-afb0-6a96eddde735" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_intro-1">
                <akn:p GUID="39f544be-b957-43c5-8309-52a0368d9e6c" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz hat für die gemeinsame Datei in einer Dateianordnung die Angaben nach § 14 Abs. 1 Satz 1 Nr. 1 bis 7 sowie weiter festzulegen: </akn:p>
              </akn:intro>
              <akn:point GUID="c2ce2c74-296c-4214-a01a-22b5dd90732a" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1">
                <akn:num GUID="2d72fbe9-6306-42de-98bf-70968ed2f309" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="53892990-67b1-4309-8fda-8e0782dfc222" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="fd0ce3e2-a4b3-47b3-8d52-a0b5fda85015" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-1_inhalt-1_text-1"> die Rechtsgrundlage der Datei, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="32b418ba-8a78-40ca-9120-2394816e308f" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2">
                <akn:num GUID="c5a59db0-3491-4621-bacd-90907c9ec955" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="2cf56fd9-20ac-4794-ab97-0fa3ecee211e" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="f6a6133b-9414-4e60-9357-47e41b249ee0" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-2_inhalt-1_text-1"> die Art der zu speichernden personenbezogenen Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="c2051fc1-4efa-46a8-8184-a669d4498f40" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3">
                <akn:num GUID="2085f793-23a9-44c1-887d-f8fdea462583" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="7d57a9e7-f8df-4183-9ab3-f2bbf94d03a5" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="9a4c72fe-e940-4578-8c6a-915ed86474d9" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-3_inhalt-1_text-1"> die Arten der personenbezogenen Daten, die der Erschließung der Datei dienen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="2391836c-de7a-4fd7-b9f8-dc4c5c0fa260" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4">
                <akn:num GUID="e4cd65df-dca3-4e5d-af99-2c797346dd9d" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="e60a5b5f-feb1-4540-a481-ec4f175b6140" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="54743a33-75a6-4fcf-a67a-cca2a3cdb2af" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-4_inhalt-1_text-1"> Voraussetzungen, unter denen in der Datei gespeicherte personenbezogene Daten an welche Empfänger und in welchen Verfahren übermittelt werden, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="b1814ea4-0c53-42af-aaa5-ada2751cc2f6" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5">
                <akn:num GUID="ed5871cb-c4d6-4c41-9901-106fc0170298" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_bezeichnung-1"> 5. </akn:num>
                <akn:content GUID="5d350fae-0520-46a8-8cc9-e8ce7f746e0a" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_inhalt-1">
                  <akn:p GUID="ec3f0127-2f67-4224-a65f-cdf6ee370c3b" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-5_inhalt-1_text-1"> im Einvernehmen mit den an der projektbezogenen Zusammenarbeit teilnehmenden Behörden deren jeweilige Organisationseinheiten, die zur Eingabe und zum Abruf befugt sind, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ff139806-3e97-4564-b925-f8d698fd537a" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6">
                <akn:num GUID="0e57798a-40f2-4956-b4d9-da6d3b987f6b" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_bezeichnung-1"> 6. </akn:num>
                <akn:content GUID="ac50ca07-bab0-4cc1-89a7-82dd187a7180" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_inhalt-1">
                  <akn:p GUID="4880f013-d95b-4728-bd96-52679285089a" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-6_inhalt-1_text-1"> die umgehende Unterrichtung der eingebenden Behörde über Anhaltspunkte für die Unrichtigkeit eingegebener Daten durch die an der gemeinsamen Datei beteiligten Behörden sowie die Prüfung und erforderlichenfalls die unverzügliche Änderung, Berichtigung oder Löschung dieser Daten durch die Behörde, die die Daten eingegeben hat, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="4046a1fc-bc4f-40cd-83f6-fabb778e3191" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7">
                <akn:num GUID="7212b80c-14cc-4e81-a0f7-6ddd3ae6b33a" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_bezeichnung-1"> 7. </akn:num>
                <akn:content GUID="f35dd5b1-ac38-4462-a987-1531068d10e7" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_inhalt-1">
                  <akn:p GUID="02413733-8acf-4065-80c6-1130058165bf" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-7_inhalt-1_text-1"> die Möglichkeit der ergänzenden Eingabe weiterer Daten zu den bereits über eine Person gespeicherten Daten durch die an der gemeinsamen Datei beteiligten Behörden, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="7fefe0fe-b507-4647-ae31-28f3195e721f" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8">
                <akn:num GUID="455795d5-996a-4489-88e5-6dfea6c9efce" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_bezeichnung-1"> 8. </akn:num>
                <akn:content GUID="c6d49ab4-33f1-403b-af42-41fb4dadd19d" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_inhalt-1">
                  <akn:p GUID="2a0b9226-5b86-4bf0-83b2-43e2d3d58529" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-8_inhalt-1_text-1"> die Protokollierung des Zeitpunkts, der Angaben zur Feststellung des aufgerufenen Datensatzes sowie der für den Abruf verantwortlichen Behörde bei jedem Abruf aus der gemeinsamen Datei durch das Bundesamt für Verfassungsschutz für Zwecke der Datenschutzkontrolle einschließlich der Zweckbestimmung der Protokolldaten sowie deren Löschfrist und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="e00fecb2-bff6-445d-b5e1-f55b9b4cfa98" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9">
                <akn:num GUID="9f0b1474-ef66-466a-bf31-1ec6448b1934" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_bezeichnung-1"> 9. </akn:num>
                <akn:content GUID="f4fe131b-2920-408f-ac20-0d7841bd9b23" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_inhalt-1">
                  <akn:p GUID="6ef9e381-e637-406f-9a16-3d2b899d42ce" eId="hauptteil-1_abschnitt-3_art-7_abs-6_untergl-1_listenelem-9_inhalt-1_text-1"> die Zuständigkeit des Bundesamtes für Verfassungsschutz für Schadensersatzansprüche des Betroffenen entsprechend § 83 des Bundesdatenschutzgesetzes. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="8e93dff1-af8d-4e3b-b492-b08b35b364a7" eId="hauptteil-1_abschnitt-3_art-8" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="0da71f36-996f-4134-8d71-7bfaacd970dd" eId="hauptteil-1_abschnitt-3_art-8_bezeichnung-1"> § 22b </akn:num>
          <akn:heading GUID="c9daa372-a983-42db-a3e4-ea1b16f2f744" eId="hauptteil-1_abschnitt-3_art-8_überschrift-1"> Errichtung gemeinsamer Dateien mit ausländischen Nachrichtendiensten </akn:heading>
          <akn:paragraph GUID="fa35b8e0-a92b-44b9-8340-d48f8de8d0fa" eId="hauptteil-1_abschnitt-3_art-8_abs-1">
            <akn:num GUID="965ca20c-ce1f-4671-96b7-824212356974" eId="hauptteil-1_abschnitt-3_art-8_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:list GUID="c0757c3d-4a63-43aa-ab03-fb26dfe1d63d" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1">
              <akn:intro GUID="7b29fe14-99f4-4225-991a-5cc1d02addc9" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_intro-1">
                <akn:p GUID="f2e3298a-98d0-4144-ad5b-76a439c3d0a7" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz kann für die Zusammenarbeit mit ausländischen öffentlichen Stellen, die mit nachrichtendienstlichen Aufgaben betraut sind (ausländische Nachrichtendienste), zur Erforschung von Bestrebungen oder Tätigkeiten, die sich auf bestimmte Ereignisse oder Personenkreise beziehen, gemeinsame Dateien einrichten, wenn </akn:p>
              </akn:intro>
              <akn:point GUID="1092eaeb-570f-4df2-aee9-bb9e6a0d9450" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="c5970d4b-f558-45c1-aed7-117ff7a97da4" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="d1d73390-fc04-4b31-8240-e824be7d7b5f" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="66b59e4c-6e48-420f-8f31-f663a72438c0" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> die Erforschung von erheblichem Sicherheitsinteresse für die Bundesrepublik Deutschland und den jeweils teilnehmenden Staat ist, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="626d9431-a84c-462c-9f13-7fcf804b416d" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="e668cbd8-13c8-47c3-8f58-ea0a0dcfbed2" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="3c819350-fe68-4b59-9501-664664b4bd3d" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="e8050736-f9b8-4b9f-880f-bc4935c19608" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> in den teilnehmenden Staaten die Einhaltung grundlegender rechtsstaatlicher Prinzipien gewährleistet ist, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="bf3d1b1d-8f4e-45bd-9f75-b23435c6a58b" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="8c176a68-a2b2-4871-aadd-87fa62b85400" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="c8936dba-2652-4299-986d-ee9734c0b026" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="a5b422be-fe89-4aff-b187-bcc0b75a62a0" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> die Festlegungen und Zusagen nach Absatz 5 Satz 1 verlässlich sind und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="6de23a3a-83c6-41b0-b5fd-8fb5778bef9c" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4">
                <akn:num GUID="e9035556-2e87-40db-b4ef-ffd6e807d1d4" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:content GUID="0567c602-5baf-49a7-bab3-07bab03f25fd" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_inhalt-1">
                  <akn:p GUID="eaa345a9-cbf1-4f47-be28-7bdf57e85113" eId="hauptteil-1_abschnitt-3_art-8_abs-1_untergl-1_listenelem-4_inhalt-1_text-1"> das Bundesministerium des Innern, für Bau und Heimat zugestimmt hat. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="5c15a269-a9c8-41c6-b7a4-978c16c8563f" eId="hauptteil-1_abschnitt-3_art-8_abs-2">
            <akn:num GUID="655469bb-fb2f-4e32-a5f7-b3eb7e739dd4" eId="hauptteil-1_abschnitt-3_art-8_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="17899cbb-7e86-4d67-9d54-833f551a6131" eId="hauptteil-1_abschnitt-3_art-8_abs-2_inhalt-1">
              <akn:p GUID="514460ae-8192-405c-8ff6-7a8175b251d9" eId="hauptteil-1_abschnitt-3_art-8_abs-2_inhalt-1_text-1"> Der Nachrichtendienst eines Staates, der weder unmittelbar an die Bundesrepublik Deutschland angrenzt noch Mitgliedstaat der Europäischen Union oder des Nordatlantikvertrages ist, kann darüber hinaus nur teilnehmen, wenn besondere Sicherheitsinteressen dies erfordern. Dies ist der Fall, wenn Bestrebungen oder Tätigkeiten erforscht werden, die auf die Begehung von schwerwiegenden Straftaten gegen den Bestand oder die Sicherheit eines Staates oder einer internationalen Organisation gerichtet sind. Schwerwiegende Straftaten sind die in § 3 Absatz 1 des Artikel 10-Gesetzes genannten Straftaten. Die Teilnahme eines solchen ausländischen Nachrichtendienstes bedarf der Zustimmung der Bundesministerin oder des Bundesministers des Innern, für Bau und Heimat. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="51037f13-3f30-4274-b207-208ea028e1a8" eId="hauptteil-1_abschnitt-3_art-8_abs-3">
            <akn:num GUID="098d51f5-0722-4723-b915-7bdd0000a067" eId="hauptteil-1_abschnitt-3_art-8_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:content GUID="1d4b6c74-fa2a-4935-b173-730cc747cd7d" eId="hauptteil-1_abschnitt-3_art-8_abs-3_inhalt-1">
              <akn:p GUID="01d9ba62-69a7-4b23-bad8-d931879d56e3" eId="hauptteil-1_abschnitt-3_art-8_abs-3_inhalt-1_text-1"> Die Datei dient der Feststellung, ob zu Personen, Objekten oder Ereignissen bei einem der beteiligten Nachrichtendienste Informationen vorhanden sind. Hierzu kann die Datei solche personenbezogene Daten enthalten, die zum Auffinden der Informationen und der dazu notwendigen Identifizierung von Personen erforderlich sind. Im Falle eines Treffers wird lediglich derjenige ausländische Nachrichtendienst angezeigt, der die Daten eingegeben hat. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="8cf47e6b-de27-4586-8c3e-229e2879479d" eId="hauptteil-1_abschnitt-3_art-8_abs-4">
            <akn:num GUID="55e58309-1445-4a2a-ad22-881cc7e531c0" eId="hauptteil-1_abschnitt-3_art-8_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="ba3b8bee-b81c-45a1-9401-29aaf339ab74" eId="hauptteil-1_abschnitt-3_art-8_abs-4_inhalt-1">
              <akn:p GUID="50f39386-3922-4e32-b5d8-6745d4a1afe0" eId="hauptteil-1_abschnitt-3_art-8_abs-4_inhalt-1_text-1"> Die Datei kann auch dem Austausch und der gemeinsamen Auswertung von Informationen und Erkenntnissen dienen, wenn dies zur Wahrung besonderer Sicherheitsinteressen (Absatz 2 Satz 2) erforderlich ist. Hierzu kann sie die zur Erforschung und Bewertung solcher Bestrebungen oder Tätigkeiten erforderlichen Daten enthalten und zu diesem Zweck genutzt werden. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="ff08b9f2-6a00-4abc-bfb4-9fc0b43db6bb" eId="hauptteil-1_abschnitt-3_art-8_abs-5">
            <akn:num GUID="532dfcab-a885-40d4-bc05-2b67c322d9fc" eId="hauptteil-1_abschnitt-3_art-8_abs-5_bezeichnung-1"> (5) </akn:num>
            <akn:list GUID="9f6fa67e-1a6f-4507-90d3-63855e76633d" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1">
              <akn:intro GUID="be800df2-1e30-40ad-9b0f-79b60b39e0ac" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_intro-1">
                <akn:p GUID="a898722d-1d60-4395-b438-8d1bbe7173e8" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_intro-1_text-1"> Die Ziele der Zusammenarbeit und das Nähere der Datenverwendung sind vor Beginn der Zusammenarbeit zwischen den teilnehmenden Nachrichtendiensten zur Gewährleistung eines angemessenen Datenschutzniveaus und zum Ausschluss unangemessener Verwendung schriftlich festzulegen, insbesondere: </akn:p>
              </akn:intro>
              <akn:point GUID="fd752096-9005-4bee-a614-efaa2624fe85" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1">
                <akn:num GUID="fd48c219-d208-408c-b030-2c7eda669c8e" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="c2cef1e0-fceb-4378-84d1-75651b36c425" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="0f2c8a16-47b1-4400-b946-5b90ab617bc8" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-1_inhalt-1_text-1"> Zweck der Datei, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d68911ae-72e5-4cdd-9a15-beb2565365e6" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2">
                <akn:num GUID="e7549c41-d5d0-43c4-84c7-99827134afe5" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="87741de0-95d3-4219-960d-11acd1bdfd8c" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="aaea6ecb-edca-4f7c-b8ff-f645bbf5c8fd" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-2_inhalt-1_text-1"> Voraussetzungen der Verwendungen von Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="59f45469-cf3f-4356-b174-39a03bf95836" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3">
                <akn:num GUID="6fc419ed-13f0-4b8a-bc90-a1985e491ebc" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="281fd3f5-b1f3-4fea-be38-a9b50c3b16c8" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="bdbc066f-7b24-4627-9316-39a89851510a" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-3_inhalt-1_text-1"> Prüfung und erforderlichenfalls unverzügliche Änderung, Berichtigung und Löschung von Daten, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="411c644d-adb7-4af6-9e31-41c9fc74faf2" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4">
                <akn:num GUID="d089554d-3fc0-4546-a122-a870f3081acb" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_bezeichnung-1"> 4. </akn:num>
                <akn:list GUID="50ec389f-3d14-433d-9cc6-b74c64ba6fd6" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1">
                  <akn:intro GUID="72a174c8-1d55-4605-98bc-c157e6bd502d" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_intro-1">
                    <akn:p GUID="9ea44ac5-b601-493b-b71a-1d8b7eed59ab" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_intro-1_text-1"> Zusage, </akn:p>
                  </akn:intro>
                  <akn:point GUID="33e77f6a-f4f9-4d87-b9d6-fa61549b668f" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1">
                    <akn:num GUID="3f6a0976-053e-474d-8cd3-3d8b38ae2d56" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_bezeichnung-1"> a) </akn:num>
                    <akn:content GUID="fc153a51-d891-4d57-81ad-0d39057a9b7a" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_inhalt-1">
                      <akn:p GUID="3d470d09-d709-422e-9496-dc39a2be5ff8" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-1_inhalt-1_text-1"> die Daten ohne Zustimmung des eingebenden Nachrichtendienstes nicht für einen anderen Zweck als den nach Nummer 1 zu verwenden oder an Dritte zu übermitteln, </akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="6587deec-816b-4a26-a504-7ea7f82b9a01" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2">
                    <akn:num GUID="7da05b3a-8e6c-4531-89a5-4f324c4a5708" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_bezeichnung-1"> b) </akn:num>
                    <akn:content GUID="6b3d5b62-627e-48d4-b290-3a711a9b6bce" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_inhalt-1">
                      <akn:p GUID="81357afa-6fa0-4ed3-9161-fa70d73a74a2" eId="hauptteil-1_abschnitt-3_art-8_abs-5_untergl-1_listenelem-4_untergl-1_listenelem-2_inhalt-1_text-1"> Auskunft über die Verwendung der Daten zu geben, die vom Auskunft erbittenden Nachrichtendienst eingegeben worden sind. </akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="e9bfe565-e2bd-47b9-91f4-c595d4ad559d" eId="hauptteil-1_abschnitt-3_art-8_abs-6">
            <akn:num GUID="f1c6315a-66c4-437f-803c-79eeb15e147a" eId="hauptteil-1_abschnitt-3_art-8_abs-6_bezeichnung-1"> (6) </akn:num>
            <akn:content GUID="6ef7f8c5-b1a3-402f-91dc-3654800ffd62" eId="hauptteil-1_abschnitt-3_art-8_abs-6_inhalt-1">
              <akn:p GUID="769632a6-60af-488b-b1c0-5ebd68832eb4" eId="hauptteil-1_abschnitt-3_art-8_abs-6_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz darf personenbezogene Daten in der gemeinsamen Datei entsprechend § 10 Absatz 1 und 3, § 11 Absatz 1 eingeben, wenn es die Daten allen teilnehmenden ausländischen Nachrichtendiensten übermitteln darf. Für die vom Bundesamt für Verfassungsschutz eingegebenen Daten gelten für die Veränderung und Nutzung § 10 Absatz 1 und § 11 Absatz 1 und für die Überprüfung, Berichtigung, Löschung und Sperrung § 11 Absatz 2 und § 12 Absatz 1 bis 3 entsprechend. Für die Verantwortung des an der Datei teilnehmenden Nachrichtendienstes gilt § 6 Absatz 2 Satz 5 und 6 entsprechend. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="7bd630ac-e5d4-4a7f-9d77-ca7971be5d9a" eId="hauptteil-1_abschnitt-3_art-8_abs-7">
            <akn:num GUID="f952d09c-91ed-46db-a2df-30dc0a8ed003" eId="hauptteil-1_abschnitt-3_art-8_abs-7_bezeichnung-1"> (7) </akn:num>
            <akn:content GUID="54c5fc95-8364-4845-ba1d-60857ae2f3db" eId="hauptteil-1_abschnitt-3_art-8_abs-7_inhalt-1">
              <akn:p GUID="b1c122b5-5421-4d2e-afcb-6227387f5b32" eId="hauptteil-1_abschnitt-3_art-8_abs-7_inhalt-1_text-1"> Das Bundesamt für Verfassungsschutz trifft für die Dateien die technischen und organisatorischen Maßnahmen entsprechend § 64 des Bundesdatenschutzgesetzes. § 6 Absatz 3 Satz 2 bis 5 und § 28 gelten nur für die vom Bundesamt für Verfassungsschutz eingegebenen Daten sowie dessen Abrufe. Das Bundesamt für Verfassungsschutz erteilt dem Betroffenen entsprechend § 15 Auskunft nur zu den vom Bundesamt für Verfassungsschutz eingegebenen Daten. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="89654b1c-5d22-4160-acd9-d1b4c9be4055" eId="hauptteil-1_abschnitt-3_art-9" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="b597830c-f68c-4254-892b-e8401cb4d28e" eId="hauptteil-1_abschnitt-3_art-9_bezeichnung-1"> § 22c </akn:num>
          <akn:heading GUID="9e194c1c-2f5c-411b-a7d8-a569b3dd435f" eId="hauptteil-1_abschnitt-3_art-9_überschrift-1"> Teilnahme an gemeinsamen Dateien mit ausländischen Nachrichtendiensten </akn:heading>
          <akn:paragraph GUID="c4e1a80b-2ff3-4975-87bf-c6e8f029e32a" eId="hauptteil-1_abschnitt-3_art-9_abs-1">
            <akn:num GUID="73ad68e3-2b84-40c3-b854-89cbacc83b10" eId="hauptteil-1_abschnitt-3_art-9_abs-1_bezeichnung-1"/>
            <akn:list GUID="5a8e0360-1d71-4a84-bd73-bc652f54126f" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1">
              <akn:intro GUID="a717fdd5-44c2-46bc-bb7d-19e6791f8439" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_intro-1">
                <akn:p GUID="69e95f09-b6a0-4f65-8ae9-f879eff60d53" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz darf an gemeinsamen Dateien, die von ausländischen Nachrichtendiensten errichtet sind, teilnehmen. § 22b Absatz 1 bis 4 und 6 gilt entsprechend. Dabei gilt § 22b Absatz 1 Nummer 3 mit der Maßgabe, dass verlässlich zuzusagen ist, dass </akn:p>
              </akn:intro>
              <akn:point GUID="6697f4d7-397f-4b26-b596-9fea013abc7c" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="9f2655b2-695d-40ff-8985-c3bbdda4e158" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="cee08fa7-2ff6-42b9-b95e-e8b677fbac22" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="e768c4ac-236f-428c-bf05-d338a73d35fb" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> die vom Bundesamt für Verfassungsschutz eingegebenen Daten ohne dessen Zustimmung nicht an Dritte übermittelt werden dürfen und nur zu dem Zweck verwendet werden dürfen, zu dem sie in die Datei eingegeben wurden, und </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="5a7becef-a65e-4e4c-8751-67b5fb1beb7f" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="cd965786-d88a-4f35-9e49-b79e5ab9da55" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="b7e25582-aee4-4d37-88da-2ded1160efb4" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="507d0bcb-0996-4b1b-832c-768df6ba0599" eId="hauptteil-1_abschnitt-3_art-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> das Bundesamt für Verfassungsschutz auf Ersuchen Auskunft über die vorgenommene Verwendung der Daten erhält. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="c3d523b6-89d0-45ff-8a81-d1c8b6c3af9d" eId="hauptteil-1_abschnitt-3_art-10" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="88399223-3786-4035-b673-b2f96adb4a86" eId="hauptteil-1_abschnitt-3_art-10_bezeichnung-1"> § 23 </akn:num>
          <akn:heading GUID="9f96f7fa-e306-4276-a540-381fdacb54a3" eId="hauptteil-1_abschnitt-3_art-10_überschrift-1"> Übermittlungsverbote </akn:heading>
          <akn:paragraph GUID="a939f108-3865-41fb-95cf-72dab6e4d918" eId="hauptteil-1_abschnitt-3_art-10_abs-1">
            <akn:num GUID="7eab575d-cd1f-48d7-bd03-c7e78842794b" eId="hauptteil-1_abschnitt-3_art-10_abs-1_bezeichnung-1"/>
            <akn:list GUID="91057c58-45e3-45f2-89ba-9ce15f616f1d" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1">
              <akn:intro GUID="3b7237e9-460b-4519-b4b1-dccd1801a7f4" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_intro-1">
                <akn:p GUID="dcee183c-1f2c-4010-bf5b-636216663bca" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_intro-1_text-1"> Die Übermittlung nach den Vorschriften dieses Abschnitts unterbleibt, wenn </akn:p>
              </akn:intro>
              <akn:point GUID="b7dfb6bb-2d65-4527-aa16-3d2cce1c80f2" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="739d8f69-7e98-43e8-9a60-b55730d8a9ec" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="a00b862e-159f-4e4f-9045-d9a239f940d5" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="232c16bf-5488-46d8-822f-e986af40b651" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> für die übermittelnde Stelle erkennbar ist, daß unter Berücksichtigung der Art der Informationen und ihrer Erhebung die schutzwürdigen Interessen des Betroffenen das Allgemeininteresse an der Übermittlung überwiegen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="e29f06aa-9671-4def-a55f-24c971a00f81" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="e58176e4-d6f1-4e85-be75-173de28f4ae9" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="2a407ccd-9753-4076-b6bb-8073fb1f32cc" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="b9447373-ce22-4032-b9fb-52767b6083c9" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> überwiegende Sicherheitsinteressen dies erfordern oder </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="35b1f458-e8c5-4d3c-9d84-9e24d41948f8" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="cc53b12c-fead-4279-a3c1-9bd59acf9e9b" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_bezeichnung-1"> 3. </akn:num>
                <akn:content GUID="05a45b88-f2e6-49bc-ba85-13b47a42fbc4" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="70c1851f-1913-4a2b-816d-0517e0c83326" eId="hauptteil-1_abschnitt-3_art-10_abs-1_untergl-1_listenelem-3_inhalt-1_text-1"> besondere gesetzliche Übermittlungsregelungen entgegenstehen; die Verpflichtung zur Wahrung gesetzlicher Geheimhaltungspflichten oder von Berufs- oder besonderen Amtsgeheimnissen, die nicht auf gesetzlichen Vorschriften beruhen, bleibt unberührt. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="b60487ca-500f-44e4-b7b9-756cf3ee08a0" eId="hauptteil-1_abschnitt-3_art-11" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="0b2a94c1-503f-48dd-bd7a-8978f7186feb" eId="hauptteil-1_abschnitt-3_art-11_bezeichnung-1"> § 24 </akn:num>
          <akn:heading GUID="45572eaa-1c3e-4960-b481-e0ea15f9787b" eId="hauptteil-1_abschnitt-3_art-11_überschrift-1"> Minderjährigenschutz </akn:heading>
          <akn:paragraph GUID="b65a7c14-982f-45a9-a0ee-b56ef96a1c64" eId="hauptteil-1_abschnitt-3_art-11_abs-1">
            <akn:num GUID="ffd3f88f-c036-4dad-a7ff-a6686ef731bf" eId="hauptteil-1_abschnitt-3_art-11_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="f1c56adc-4926-44c5-a48e-4a24109be1d7" eId="hauptteil-1_abschnitt-3_art-11_abs-1_inhalt-1">
              <akn:p GUID="794ff7dd-a817-456a-863b-403be2a8d375" eId="hauptteil-1_abschnitt-3_art-11_abs-1_inhalt-1_text-1"> Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger dürfen nach den Vorschriften dieses Gesetzes übermittelt werden, solange die Voraussetzungen der Speicherung nach § 11 Abs. 1 Satz 1 erfüllt sind. Liegen diese Voraussetzungen nicht mehr vor, bleibt eine Übermittlung nur zulässig, wenn sie zur Abwehr einer erheblichen Gefahr oder zur Verfolgung einer Straftat von erheblicher Bedeutung erforderlich ist. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="c4ddae19-d124-47f6-9356-18d70820a9ae" eId="hauptteil-1_abschnitt-3_art-11_abs-2">
            <akn:num GUID="086b9428-5ffd-4c8c-b391-4a792a665b05" eId="hauptteil-1_abschnitt-3_art-11_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="4c0927b2-7090-4f65-abb3-8fd4d811de9d" eId="hauptteil-1_abschnitt-3_art-11_abs-2_inhalt-1">
              <akn:p GUID="d4516fbd-ad63-45ad-ac3d-5f33e8a025c3" eId="hauptteil-1_abschnitt-3_art-11_abs-2_inhalt-1_text-1"> Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger vor Vollendung des 16. Lebensjahres dürfen nach den Vorschriften dieses Gesetzes nicht an ausländische sowie über- oder zwischen*-staatliche Stellen übermittelt werden. Abweichend hiervon dürfen Informationen einschließlich personenbezogener Daten über das Verhalten Minderjähriger, die das 14. Lebensjahr vollendet haben, übermittelt werden, wenn nach den Umständen des Einzelfalls nicht ausgeschlossen werden kann, dass die Übermittlung zur Abwehr einer erheblichen Gefahr für Leib oder Leben einer Person erforderlich ist oder tatsächliche Anhaltspunkte dafür vorliegen, dass die Übermittlung zur Verfolgung einer der in § 3 Abs. 1 des Artikel 10-Gesetzes genannten Straftaten erforderlich ist. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="5e11567b-e105-48c7-9caf-139dc6d24f36" eId="hauptteil-1_abschnitt-3_art-12" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="3593eb75-779c-4c07-81ce-4f15ab55dada" eId="hauptteil-1_abschnitt-3_art-12_bezeichnung-1"> § 25 </akn:num>
          <akn:heading GUID="93805b9c-a275-4be4-9e02-15ad1a3e1513" eId="hauptteil-1_abschnitt-3_art-12_überschrift-1"> Pflichten des Empfängers </akn:heading>
          <akn:paragraph GUID="ab1a30d6-fe86-4dfa-9e75-4cbcf73a428a" eId="hauptteil-1_abschnitt-3_art-12_abs-1">
            <akn:num GUID="fc779767-5f32-4dd4-960c-25dbb2bca9f1" eId="hauptteil-1_abschnitt-3_art-12_abs-1_bezeichnung-1"/>
            <akn:content GUID="9a5abc90-b046-4758-9587-6b2e316996a4" eId="hauptteil-1_abschnitt-3_art-12_abs-1_inhalt-1">
              <akn:p GUID="76df71e5-b780-45f7-a55b-46a688100942" eId="hauptteil-1_abschnitt-3_art-12_abs-1_inhalt-1_text-1"> Der Empfänger prüft, ob die nach den Vorschriften dieses Gesetzes übermittelten personenbezogenen Daten für die Erfüllung seiner Aufgaben erforderlich sind. Ergibt die Prüfung, daß sie nicht erforderlich sind, hat er die Unterlagen zu vernichten. Die Vernichtung kann unterbleiben, wenn die Trennung von anderen Informationen, die zur Erfüllung der Aufgaben erforderlich sind, nicht oder nur mit unvertretbarem Aufwand möglich ist; in diesem Fall ist die Verarbeitung der Daten einzuschränken. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="781bea44-89bf-48c2-a51c-e4fe8b7f5b89" eId="hauptteil-1_abschnitt-3_art-13" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="339d796d-ad4e-4c51-b990-5b3c61cfd105" eId="hauptteil-1_abschnitt-3_art-13_bezeichnung-1"> § 26 </akn:num>
          <akn:heading GUID="cf210947-3f99-45ee-8461-a34db274fd4b" eId="hauptteil-1_abschnitt-3_art-13_überschrift-1"> Nachberichtspflicht </akn:heading>
          <akn:paragraph GUID="58885c1a-4f2f-4306-8eda-f32803495329" eId="hauptteil-1_abschnitt-3_art-13_abs-1">
            <akn:num GUID="a54b93cd-07c7-47f6-a4d0-b73018829f63" eId="hauptteil-1_abschnitt-3_art-13_abs-1_bezeichnung-1"/>
            <akn:content GUID="ca69ad22-75b4-47ed-8e22-186311c7b6df" eId="hauptteil-1_abschnitt-3_art-13_abs-1_inhalt-1">
              <akn:p GUID="e63384f4-70b8-46cc-9e88-1d2c2f696069" eId="hauptteil-1_abschnitt-3_art-13_abs-1_inhalt-1_text-1"> Erweisen sich personenbezogene Daten nach ihrer Übermittlung nach den Vorschriften dieses Gesetzes als unvollständig oder unrichtig, so sind sie unverzüglich gegenüber dem Empfänger zu berichtigen, es sei denn, daß dies für die Beurteilung eines Sachverhalts ohne Bedeutung ist. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:section>
      <akn:section GUID="7b3c33bd-e39b-45bd-b69c-4d121e59f966" eId="hauptteil-1_abschnitt-4">
        <akn:num GUID="9aa61eb2-fa2b-4001-9339-19a309200055" eId="hauptteil-1_abschnitt-4_bezeichnung-1"> Vierter Abschnitt </akn:num>
        <akn:heading GUID="c8eb3539-0693-4755-87b8-ccea0d868526" eId="hauptteil-1_abschnitt-4_überschrift-1"> Schlußvorschriften </akn:heading>
        <akn:article GUID="af587c5c-1778-46f1-af5c-0204351cd086" eId="hauptteil-1_abschnitt-4_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="4d4ab3b2-c29d-4879-9969-1fd95c644f6a" eId="hauptteil-1_abschnitt-4_art-1_bezeichnung-1"> § 27 </akn:num>
          <akn:heading GUID="56cfe35a-215b-4f5f-abc5-ee17f13e4c73" eId="hauptteil-1_abschnitt-4_art-1_überschrift-1"> Anwendung des Bundesdatenschutzgesetzes </akn:heading>
          <akn:paragraph GUID="7e846385-c92e-467e-a990-d5b32c28aecf" eId="hauptteil-1_abschnitt-4_art-1_abs-1">
            <akn:num GUID="8dba72b4-861d-4bdf-98df-6cfe4f759649" eId="hauptteil-1_abschnitt-4_art-1_abs-1_bezeichnung-1"/>
            <akn:list GUID="b9667190-dfb5-4727-9366-7b9a6f68af41" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1">
              <akn:intro GUID="a5f3dd6b-cf82-4027-94e5-381815896b18" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_intro-1">
                <akn:p GUID="9b256749-6ee0-4d85-ab52-55b5ea1fcbc2" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_intro-1_text-1"> Bei der Erfüllung der Aufgaben nach § 3 durch das Bundesamt für Verfassungsschutz findet das Bundesdatenschutzgesetz wie folgt Anwendung: </akn:p>
              </akn:intro>
              <akn:point GUID="bcad8715-1626-403d-940f-2f0e7a5472da" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="1057dd80-fcd2-4fd7-8c9c-dbdf4a3e7808" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="2d908fd5-37a0-48dc-978a-2e25a6603cb6" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="6496002f-9279-4936-9123-189d0026462e" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"> § 1 Absatz 8, die §§ 4, 16 Absatz 1 und 4 und die §§ 17 bis 21 sowie § 85 finden keine Anwendung, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="3c92c6fd-ea67-40bd-9956-9889a3eb6024" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="405cc3e2-551e-436b-9537-a364382b768a" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="2def5cc2-eb63-42bc-952e-1556f865c774" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="8961d936-f3d6-46d3-bbf2-84ecfef28863" eId="hauptteil-1_abschnitt-4_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"> die §§ 46, 51 Absatz 1 bis 4 und die §§ 52 bis 54, 62, 64, 83, 84 sind entsprechend anzuwenden. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="84de3f52-80fd-4c82-9d6e-5d49eb7c475c" eId="hauptteil-1_abschnitt-4_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="3c0da08c-f312-4492-af81-d8906350a4ff" eId="hauptteil-1_abschnitt-4_art-2_bezeichnung-1"> § 28 </akn:num>
          <akn:heading GUID="89e5a64d-0c20-4cdc-a275-6da852331742" eId="hauptteil-1_abschnitt-4_art-2_überschrift-1"> Unabhängige Datenschutzkontrolle </akn:heading>
          <akn:paragraph GUID="30f4f829-b991-4c45-a527-ef144586f657" eId="hauptteil-1_abschnitt-4_art-2_abs-1">
            <akn:num GUID="f72eaf36-8fa4-4925-8c60-861a56a25c8d" eId="hauptteil-1_abschnitt-4_art-2_abs-1_bezeichnung-1"> (1) </akn:num>
            <akn:content GUID="f0424e62-be38-4730-b0c3-0d4f76da4cf2" eId="hauptteil-1_abschnitt-4_art-2_abs-1_inhalt-1">
              <akn:p GUID="27a85f15-57a3-4a41-846d-e08fbe808f68" eId="hauptteil-1_abschnitt-4_art-2_abs-1_inhalt-1_text-1"> Jedermann kann sich an die Bundesbeauftragte oder den Bundesbeauftragten für den Datenschutz und die Informationsfreiheit wenden, wenn er der Ansicht ist, bei der Verarbeitung seiner personenbezogenen Daten durch das Bundesamt für Verfassungsschutz in seinen Rechten verletzt worden zu sein. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="6987fc6d-12f2-4ec1-afef-5af1b9350c6d" eId="hauptteil-1_abschnitt-4_art-2_abs-2">
            <akn:num GUID="f7613f29-f247-4fe6-9818-548a2c521a79" eId="hauptteil-1_abschnitt-4_art-2_abs-2_bezeichnung-1"> (2) </akn:num>
            <akn:content GUID="1f6112a4-4b84-4f87-a513-2fb9668be665" eId="hauptteil-1_abschnitt-4_art-2_abs-2_inhalt-1">
              <akn:p GUID="e63a83f6-4052-4aec-add7-4b177299ea23" eId="hauptteil-1_abschnitt-4_art-2_abs-2_inhalt-1_text-1"> Die oder der Bundesbeauftragte für den Datenschutz und die Informationsfreiheit kontrolliert beim Bundesamt für Verfassungsschutz die Einhaltung der Vorschriften über den Datenschutz. Soweit die Einhaltung von Vorschriften der Kontrolle durch die G 10-Kommission unterliegt, unterliegt sie nicht der Kontrolle durch die Bundesbeauftragte oder den Bundesbeauftragten für den Datenschutz und die Informationsfreiheit, es sei denn, die G 10-Kommission ersucht die Bundesbeauftragte oder den Bundesbeauftragten für den Datenschutz und die Informationsfreiheit, die Einhaltung der Vorschriften über den Datenschutz bei bestimmten Vorgängen oder in bestimmten Bereichen zu kontrollieren und ausschließlich ihr darüber zu berichten. </akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="772e3fd8-1910-4fea-a5c4-83e655976f49" eId="hauptteil-1_abschnitt-4_art-2_abs-3">
            <akn:num GUID="aaeb4aeb-739b-4db3-bf66-28f92ed579b1" eId="hauptteil-1_abschnitt-4_art-2_abs-3_bezeichnung-1"> (3) </akn:num>
            <akn:list GUID="1337b365-9554-4c4e-843b-ff886b3583ab" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1">
              <akn:intro GUID="7da11529-fb7f-49aa-b83b-881db0e43782" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_intro-1">
                <akn:p GUID="1d89c1b2-34a6-41c3-8f76-27e87a0209e5" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_intro-1_text-1"> Das Bundesamt für Verfassungsschutz ist verpflichtet, die Bundesbeauftragte oder den Bundesbeauftragten für den Datenschutz und die Informationsfreiheit und ihre oder seine schriftlich besonders Beauftragten bei der Erfüllung ihrer oder seiner Aufgaben zu unterstützen. Den in Satz 1 genannten Personen ist dabei insbesondere </akn:p>
              </akn:intro>
              <akn:point GUID="3c2ec6db-3660-40a1-9926-e1cd07739be9" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="05c2a5bf-91d9-4075-9967-3cf810dc0552" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_bezeichnung-1"> 1. </akn:num>
                <akn:content GUID="74c4080f-9d09-4247-affb-dd411a26c8ac" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="e3e5189f-f207-4a5e-a5b0-6cb0a9dd1612" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-1_inhalt-1_text-1"> Auskunft zu ihren Fragen sowie Einsicht in alle Unterlagen, insbesondere in die gespeicherten Daten und in die Datenverarbeitungsprogramme, zu gewähren, die im Zusammenhang mit der Kontrolle nach Absatz 2 stehen, </akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="0ce8b852-8b46-47c1-9515-0720b9cf9e2c" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="f9066a15-8af2-4273-afef-ff3b005b79ab" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_bezeichnung-1"> 2. </akn:num>
                <akn:content GUID="aee9781e-4b54-4179-ac3e-544b6c9cac70" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="07044225-d1e6-4b9b-b119-52349d8d7190" eId="hauptteil-1_abschnitt-4_art-2_abs-3_untergl-1_listenelem-2_inhalt-1_text-1"> jederzeit Zutritt in alle Diensträume zu gewähren. </akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="3ae5154b-1afe-4f61-99aa-ed4470c1c1b6" eId="hauptteil-1_abschnitt-4_art-2_abs-4">
            <akn:num GUID="f81bcbd6-3702-4583-baf6-267a08b4155c" eId="hauptteil-1_abschnitt-4_art-2_abs-4_bezeichnung-1"> (4) </akn:num>
            <akn:content GUID="a6baaab7-3e1c-49b1-9cb8-7d79f985ae4c" eId="hauptteil-1_abschnitt-4_art-2_abs-4_inhalt-1">
              <akn:p GUID="281cb4f4-ba94-4d38-b140-7975c3edfbb4" eId="hauptteil-1_abschnitt-4_art-2_abs-4_inhalt-1_text-1"> Die Absätze 1 bis 3 gelten ohne Beschränkung auf die Erfüllung der Aufgaben nach § 3. Sie gelten entsprechend für die Verarbeitung personenbezogener Daten durch andere Stellen, wenn diese der Erfüllung der Aufgaben von Verfassungsschutzbehörden nach § 3 dient. § 16 Absatz 1 und 4 des Bundesdatenschutzgesetzes findet keine Anwendung. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="93723c9e-c8ca-4da0-8612-74d15d10de22" eId="hauptteil-1_abschnitt-4_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="12b624bc-f32e-4a90-970b-60b1ffb617e0" eId="hauptteil-1_abschnitt-4_art-3_bezeichnung-1"> § 29 </akn:num>
          <akn:heading GUID="c7027ae5-4934-4672-a333-35b07cbc0976" eId="hauptteil-1_abschnitt-4_art-3_überschrift-1"> Einschränkung von Grundrechten </akn:heading>
          <akn:paragraph GUID="8b11bb6c-091a-44e3-8ff7-2ef655396acd" eId="hauptteil-1_abschnitt-4_art-3_abs-1">
            <akn:num GUID="66b9f700-b5f4-445b-9e6b-48a54198c8da" eId="hauptteil-1_abschnitt-4_art-3_abs-1_bezeichnung-1"/>
            <akn:content GUID="ae13520d-7023-4c0d-a306-bc5b8e38daef" eId="hauptteil-1_abschnitt-4_art-3_abs-1_inhalt-1">
              <akn:p GUID="af75fb37-2fe6-4d6b-819a-e8075af38b60" eId="hauptteil-1_abschnitt-4_art-3_abs-1_inhalt-1_text-1"> Die Grundrechte der Versammlungsfreiheit (Artikel 8 des Grundgesetzes), des Brief-, Post- und Fernmelde*-geheimnisses (Artikel 10 des Grundgesetzes) und der Unverletzlichkeit der Wohnung (Artikel 13 des Grundgesetzes) werden nach Maßgabe dieses Gesetzes eingeschränkt. </akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article refersTo="geltungszeitregel" GUID="438cad24-d29f-4daa-8301-6ed486ff4332" eId="hauptteil-1_abschnitt-4_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="3cb18812-026b-4f87-9eb8-860061280af8" eId="hauptteil-1_abschnitt-4_art-4_bezeichnung-1">§ 30</akn:num>
          <akn:heading GUID="d6c15b37-0e1b-48b2-ba61-0fa601c01039" eId="hauptteil-1_abschnitt-4_art-4_überschrift-1">Inkrafttreten</akn:heading>
          <akn:paragraph GUID="b9bdb1fb-98ef-4d3b-aa5f-d7cf474d5062" eId="hauptteil-1_abschnitt-4_art-4_abs-1">
            <akn:num GUID="d6963023-922f-46e6-972d-676900525ceb" eId="hauptteil-1_abschnitt-4_art-4_abs-1_bezeichnung-1"/>
            <akn:content GUID="91fd27ba-b0f4-48b7-97ec-ee62cbe34215" eId="hauptteil-1_abschnitt-4_art-4_abs-1_inhalt-1">
              <akn:p GUID="0930619a-6b0c-4a3d-b03c-4d4cad2b0a51" eId="hauptteil-1_abschnitt-4_art-4_abs-1_inhalt-1_text-1">Platzhalter</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:section>
    </akn:body>
    <akn:conclusions GUID="2649850c-56e8-4d7f-bf64-03b4530b3a83" eId="schluss-1">
      <akn:formula refersTo="schlussformel" name="attributsemantik-noch-undefiniert" GUID="c990d40c-4b6c-44fc-b8c1-db08aff80fbe" eId="schluss-1_formel-1">
        <akn:p GUID="45f45559-1770-42c5-9333-0227d773ed1e" eId="schluss-1_formel-1_text-1">Patzhalter</akn:p>
      </akn:formula>
      <akn:blockContainer GUID="170148f8-4624-4bc8-89fb-910a881a1762" eId="schluss-1_blockcontainer-1">
        <akn:p GUID="701a2072-7076-43b9-a165-12ecd0c64fc1" eId="schluss-1_blockcontainer-1_text-1">
          <akn:location refersTo="attributsemantik-noch-undefiniert" GUID="cfdb3a21-2078-42b6-88c2-4c97269b6ce2" eId="schluss-1_blockcontainer-1_text-1_ort-1">Berlin</akn:location>
          , den
          <akn:date refersTo="ausfertigung-datum" date="2022-12-19" GUID="b53a788a-173d-4131-babc-d95c67525883" eId="schluss-1_blockcontainer-1_text-1_datum-1">12. Dezember 2022</akn:date>
        </akn:p>
        <akn:signature GUID="d4f24d4f-df43-4a8c-ac1f-d6e5cba5c081" eId="schluss-1_blockcontainer-1_signatur-1">
          <akn:role refersTo="attributsemantik-noch-undefiniert" GUID="41a83805-8985-4c3c-8498-87f72be3111e" eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1">Der Bundespräsident</akn:role>
          <akn:person refersTo="attributsemantik-noch-undefiniert" GUID="714b44b1-bf0f-44f1-8947-cf2b1aa94d93" eId="schluss-1_blockcontainer-1_signatur-1_person-1">Patzhalter</akn:person>
        </akn:signature>
      </akn:blockContainer>
    </akn:conclusions>
  </akn:act>
</akn:akomaNtoso>');
