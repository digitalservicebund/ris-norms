-- REAPPLY
-- TARGET LAW
DELETE
FROM dokumente
WHERE eli_dokument_expression = 'eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1';

INSERT INTO dokumente (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="c89f51aa-42ff-4b41-a8c8-6346a55e9689" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="87664fdd-3016-40ff-8175-0ebcb20d80d4" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="5aa91f7e-c7d4-48a6-92d6-2ce0e2119be1" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/regelungstext-1" GUID="4db8a6b0-db10-4e3b-b087-7c7468a67935" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1" GUID="03296505-eeb1-4a07-a46a-eb45d0c67ecc" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="c278eb02-1e96-45f3-8a4d-cbb4dc8bd527" GUID="da9b353f-b37b-491b-ba19-673e4509353d" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1002-01-01" GUID="ab982f9c-62c4-495f-acbd-7240f0ada6a2" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="c3285b42-8db9-4b57-b3a9-480ceb3e9779" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="39d42d39-00ad-4838-b37b-d0fc2637fa23" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="1" GUID="831842f1-57a9-4bed-9344-caf86d5177ef" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="093d7ba5-a454-48c8-9dfe-135f2c45e0c2" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="d99ddfc7-bb3e-4aae-aeda-59381f166f14" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="4fa6adfd-3a99-4e33-a11b-eec2b98b54ff" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1" GUID="35ec72ba-8bd5-461a-911f-0ebf3f9870e2" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu" GUID="43a1b5e6-ce8e-45ce-bf13-006f5ba6e564" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="63ef9358-8755-46e4-bf6a-21f379014597" GUID="f417584a-5946-4f73-83c3-84d4c11113db" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="e4e034dd-61b9-43aa-b4f9-b778dc6adfda" GUID="a5fb1fb1-aac8-478f-b9c5-5e492a89567d" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="06f6cb13-7cbb-4fa3-a998-fe23c6121feb" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1002-01-01" GUID="b292d8ed-d424-4eb2-b8f7-c0f2d7bec38b" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="aaf4600b-55e7-40ed-ac93-af4214ba019a" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="8f759110-62b3-4e90-b111-fe2bea103793" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="af225980-0c45-4b7e-a283-468f4e592a5f" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/1002-01-01/regelungstext-1.xml" GUID="c1e01457-29ea-4c76-b0c0-5d32a2a26f3c" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/1002-01-01/regelungstext-1.xml" GUID="cc058950-8d04-4bbe-9af7-f1fd1c73e871" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1002-01-01" GUID="cf4ce566-2941-45e1-adfa-fa19059d50ca" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="aa81fa1d-6c34-4415-a4c6-45d077921d15" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="efeef16f-84c6-4cb4-8e85-b715e95c73b9" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="c7c3c3bb-736c-46e2-a377-b55bfcb0e73b" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1002-01-01" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="26450520-ea84-4149-a0cb-fa8fb9b5e64b" eId="meta-1_lebzykl-1_ereignis-1"/>
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" GUID="f4996a06-fe13-4d2d-8c88-456b0dff209f" eId="meta-1_analysis-1"/>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="b79bb0f8-82cb-4d43-83e6-315e9949ad00" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="61462625-2218-41fb-9b09-6e43709f4f32" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="2fb2d7fc-ccd3-49e1-a874-fcda74c8ad47" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1"/>
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>
    <akn:preface GUID="1d7f71b4-7be1-4899-9c07-532d8da589f9" eId="einleitung-1">
      <akn:longTitle GUID="08b4891b-4b81-46b6-afbc-bcfa922316ff" eId="einleitung-1_doktitel-1">
        <akn:p GUID="7f5954b4-fb6c-4937-a116-3c984ae7c8ad" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="cc8b6b9f-f99f-477c-b953-5776127b579f" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten</akn:docTitle>
          <akn:shortTitle GUID="8218d5c1-8137-434c-bd4d-05ed097aa8de" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(Strukturänderungsgesetz)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>
    <akn:preamble GUID="49567b3e-ae07-444e-8135-554582cffe8d" eId="preambel-1">
      <akn:citations GUID="a88e9437-387d-44f9-a37d-b1d7d3b46ab1" eId="preambel-1_ernormen-1">
        <akn:citation GUID="7061e0f5-1a6b-4bec-867a-d793074459d8" eId="preambel-1_ernormen-1_ernorm-1">
          <akn:p GUID="9ad298ab-ccd1-42b6-be7b-b97b77ce2c25" eId="preambel-1_ernormen-1_ernorm-1_text-1">Die Bundesregierung wird ermächtigt, durch Rechtsverordnung ohne Zustimmung des Bundesrates die zur Durchführung dieses Gesetzes erforderlichen Rechtsvorschriften zu erlassen. Hierzu gehören insbesondere Regelungen über die organisatorische Umsetzung der in diesem Gesetz festgelegten Maßnahmen, die Festlegung technischer Standards sowie Bestimmungen zur Überwachung und Kontrolle der Einhaltung der gesetzlichen Vorgaben.</akn:p>
        </akn:citation>
      </akn:citations>
      <akn:recitals GUID="323e2a64-f82d-468f-b58e-7cdab215245b" eId="preambel-1_präambeln-1">
        <akn:heading GUID="0693d14b-eab0-4694-a4a4-40960765999d" eId="preambel-1_präambeln-1_überschrift-1">Präambel</akn:heading>
        <akn:recital GUID="da2b8e79-e992-48d1-a91b-4f182f99bf21" eId="preambel-1_präambeln-1_präambelinh-1">
          <akn:p GUID="3b26a3a1-d7c1-43f3-9825-e056946974dd" eId="preambel-1_präambeln-1_präambelinh-1_text-1">Im Bewusstsein der Verantwortung gegenüber den Bürgerinnen und Bürgern, sowie im Bestreben, die Effizienz und Transparenz der staatlichen Strukturen zu erhöhen, hat der Deutsche Bundestag das nachstehende Gesetz beschlossen. Ziel dieses Gesetzes ist es, bestehende Strukturen und Gliederungseinheiten in Dokumenten unter Berücksichtigung moderner Technologien zu ersetzen, zu vereinheitlichen und zu optimieren. Dabei sollen sowohl die Interessen der Bürgerinnen und Bürger als auch die Anforderungen an eine zukunftsfähige und digitalisierte Verwaltung gleichermaßen gewahrt werden.</akn:p>
          <akn:p GUID="1c209cb9-1532-4d4f-b87e-62232fdf87ef" eId="preambel-1_präambeln-1_präambelinh-1_text-2">Dieses Gesetz beruht auf den Grundsätzen der Rechtstaatlichkeit, der Wirtschaftlichkeit und der Bürgernähe. Es stellt sicher, dass die Anpassungen an Gesetzen und Verordnungen, transparent und effektiv durchgeführt werden.</akn:p>
        </akn:recital>
      </akn:recitals>
      <akn:blockContainer refersTo="inhaltsuebersicht" GUID="1388a35c-c7db-49c1-a6c5-51dad05d86eb" eId="preambel-1_blockcontainer-1">
        <akn:heading GUID="42cd1a29-d147-454e-b58f-bb9345814a12" eId="preambel-1_blockcontainer-1_überschrift-1">Inhaltsübersicht</akn:heading>
        <akn:toc GUID="453209e5-1485-4cd2-9674-aed8b18ee290" eId="preambel-1_blockcontainer-1_inhuebs-1">
          <akn:tocItem href="#" level="1" GUID="0125d258-b9b9-45c8-8df2-90f566cfae86" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1">
            <akn:span GUID="90169c9e-8644-4c73-bfd6-5689e9f0b427" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1">§ 1</akn:span>
            <akn:span GUID="5dfbc43e-74a6-4740-9a90-069c6d007ba5" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-2">Anwendung in Absätzen und Aufzählungen</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="68e69fcc-4ac6-451d-b803-d3333186f4b4" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2">
            <akn:span GUID="0592f8ba-b6c9-4b39-b8d0-c30d391a0540" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-1">§ 2</akn:span>
            <akn:span GUID="5bef94f0-f511-4d20-892f-a40a8ec623ff" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-2">Anwendung in Tabellen</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="03987933-32b2-457b-a488-ce7bcaa4ad4a" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3">
            <akn:span GUID="b81bbd21-7811-4f55-a93e-9806fdc6198b" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-1">§ 3</akn:span>
            <akn:span GUID="29782f96-0303-4b3a-9369-b5082bd8a586" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-2">Implementierung der neuen Strukturen</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="a590d9ca-7b4d-4a1e-b2f0-4684399b77d3" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4">
            <akn:span GUID="e5670849-2eb8-4a1f-9f60-0e26ba071190" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-1">§ 4</akn:span>
            <akn:span GUID="1a93ee21-f7bf-4b31-8531-fa434cd478e4" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-2">Finanzielle Unterstützung</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="93911de7-44bd-4f94-bcf3-cf7232702c3d" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5">
            <akn:span GUID="3afe984a-cddf-4a6f-a20e-018cd49895e7" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-1">§ 5</akn:span>
            <akn:span GUID="ccf4ddc3-53f7-4ca8-8ba6-38927323e43a" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-2">Evaluierung und Anpassung</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="7199ec59-ece3-42c2-b666-2113d92c9460" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6">
            <akn:span GUID="7576bb31-b308-44e0-847f-741e82de90d8" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-1">§ 5a</akn:span>
            <akn:span GUID="f34b4abf-0494-4730-9398-585bcd80f49c" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-2">Stammverweis</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="147cd0a2-e143-4f93-983a-c372ab659d60" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7">
            <akn:span GUID="fd74161d-8801-4f6d-b97c-22c9799bbe9d" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-1">§ 6</akn:span>
            <akn:span GUID="126e9ef1-7771-401a-b0b0-6273f6958a32" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-2">Beispielartikel (neu)</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="b12aa6ae-2ea0-4f56-9dbb-2371542c9277" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-8">
            <akn:span GUID="05b054c6-f10b-47e4-a4f7-4775c07aeeb6" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-8_span-1">§ 7</akn:span>
            <akn:span GUID="84e8a023-325c-4599-88b1-24369e5187cf" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-8_span-2">Beispielartikel</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="a90eb304-3277-48e7-ba85-8fb3bc3f3344" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-9">
            <akn:span GUID="1c374ef2-43a4-4f43-bdc2-b4a2d7baf5c6" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-9_span-1">§ 7a</akn:span>
            <akn:span GUID="94b97376-6a5c-46d3-baaa-0a7e41988337" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-9_span-2">Beispiel</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="af06634f-8238-4ac6-aae0-c7a20f06b740" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-10">
            <akn:span GUID="fab5cf60-d1f8-458e-b39e-677297dba3bc" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-10_span-1">§ 7b</akn:span>
            <akn:span GUID="3eb5c245-5bea-4bfa-8a0c-0047a73a9415" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-10_span-2">Beispiel</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="3cef93cc-1a04-44aa-a517-7507a6d8aa5c" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-11">
            <akn:span GUID="340b828a-3da1-46af-a305-6d5537db30bf" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-11_span-1">§ 7c</akn:span>
            <akn:span GUID="3475c648-e4ef-4747-876f-8f731d2724e2" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-11_span-2">Beispiel</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="a04ddb79-708f-46ae-a57d-3e95791675cd" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-12">
            <akn:span GUID="3fcf1524-13aa-4fd2-9ad3-54b3e19437c6" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-12_span-1">§ 8</akn:span>
            <akn:span GUID="4993b4b9-3c94-4395-9237-5723efbc4faf" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-12_span-2">Übergangsregelungen</akn:span>
          </akn:tocItem>
          <akn:tocItem href="#" level="1" GUID="424ec23d-4764-4164-8c57-b731cc4a3075" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-13">
            <akn:span GUID="5c31e97e-caa3-46f9-9d5d-4f52f3ab48c6" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-13_span-1">§ 9</akn:span>
            <akn:span GUID="a7032cab-9c21-4c64-8a9a-10a7abc21b91" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-13_span-2">Inkrafttreten</akn:span>
          </akn:tocItem>
        </akn:toc>
      </akn:blockContainer>
    </akn:preamble>
    <akn:body GUID="dfe61cc3-2518-4dcd-b76c-b780460cbe6b" eId="hauptteil-1">
      <akn:book GUID="b4d62dcd-295b-4d09-8cb4-76dfb902060d" eId="hauptteil-1_buch-1">
        <akn:num GUID="c508b7a7-f49e-415e-be49-27fde1fb1f0b" eId="hauptteil-1_buch-1_bezeichnung-1">1. Buch</akn:num>
        <akn:heading GUID="967469ff-e5cc-4a1e-8394-2a46f5ac5c40" eId="hauptteil-1_buch-1_überschrift-1">Strukturen und Gliederungsebenen</akn:heading>
        <akn:article GUID="0c7bdaca-30f3-4063-afeb-61c948c7b511" eId="hauptteil-1_buch-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="4383497d-e6eb-4cf9-a8a8-de40bc04aefb" eId="hauptteil-1_buch-1_art-1_bezeichnung-1">§ 1</akn:num>
          <akn:heading GUID="10c06184-7929-424f-9ccf-9d45e18f6630" eId="hauptteil-1_buch-1_art-1_überschrift-1">Anwendung in Absätzen und Aufzählungen</akn:heading>
          <akn:paragraph GUID="6d61b7bc-3638-4b80-bcba-65e4b6f910ef" eId="hauptteil-1_buch-1_art-1_abs-1">
            <akn:num GUID="c1d92ad6-1049-4e0e-b0e7-de0d57b6cba7" eId="hauptteil-1_buch-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
            <akn:content GUID="609b8246-1eff-4a48-b941-482e1277407c" eId="hauptteil-1_buch-1_art-1_abs-1_inhalt-1">
              <akn:p GUID="b3d09357-d415-448c-95e6-6a264fcc660d" eId="hauptteil-1_buch-1_art-1_abs-1_inhalt-1_text-1">Dieses Gesetz findet Anwendung auf alle definierten Struktur und Gliederungsebenen.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="cdfe8d48-9f23-4771-81b9-badd28f1b798" eId="hauptteil-1_buch-1_art-1_abs-2">
            <akn:num GUID="32aad989-f718-4946-9128-ef9f862019b1" eId="hauptteil-1_buch-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
            <akn:content GUID="f39097d0-7aa2-4171-9f5c-052ada62a684" eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1">
              <akn:p GUID="b677ab3d-dc4b-4c8a-b533-bb96949202b2" eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1_text-1">Die Berechnung der Anwendung erfolgt nach folgender Formel: </akn:p>
              <akn:foreign GUID="4eb29632-77d7-4394-b9cd-b4e1e446d828" eId="hauptteil-1_buch-1_art-1_abs-2_inhalt-1_exmarkup-1">
                <math xmlns="http://www.w3.org/1998/Math/MathML" display="block">
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
          <akn:paragraph GUID="fbb374bf-891c-4921-8781-0f7171b6f66c" eId="hauptteil-1_buch-1_art-1_abs-3">
            <akn:num GUID="66f47a2e-1cc1-4018-a9c9-b8370c846517" eId="hauptteil-1_buch-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
            <akn:list GUID="6e453756-14c0-4741-89ad-0d28ab87016f" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1">
              <akn:intro GUID="2f041e5d-f886-4ec8-befb-c4425dbc8d87" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_intro-1">
                <akn:p GUID="91e9c586-3632-431d-aab7-493d29e719bc" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_intro-1_text-1">Die Bestimmungen dieses Gesetzes gelten insbesondere für:</akn:p>
              </akn:intro>
              <akn:point GUID="47883317-0347-4b49-a3d9-e7d2d63bb95d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="7f05537d-cf46-4f2a-af35-14401702d562" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                <akn:list GUID="4c7cc56f-cf37-4b94-8ba1-2e959345f5a0" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1">
                  <akn:intro GUID="d7e12254-7e62-417f-9d0c-2614e9a9193d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1">
                    <akn:p GUID="e3b966cc-40c9-438f-8341-a119e3f9be11" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1_text-1">Gliederungen im Regelungstext wie zum Beispiel</akn:p>
                  </akn:intro>
                  <akn:point GUID="26f87518-93ae-4850-9c69-ba71bce4d962" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1">
                    <akn:num GUID="72c45430-b61a-4906-85c3-fb2e3702bc88" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                    <akn:content GUID="9740f2cd-7463-492e-b79e-1d961b9a0885" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                      <akn:p GUID="c4e5a220-cc08-4b16-b6d0-5ce9810e5e99" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">Bücher,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="4a0b4136-f4f5-4366-b0ae-a231ed7c04d0" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2">
                    <akn:num GUID="730d5ee8-164c-4c87-b65f-d90c11d6d653" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                    <akn:content GUID="69d9920f-2564-4b1f-a712-a8ce6e5ea84d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                      <akn:p GUID="025d1fd2-d0bd-43e8-ba93-6318ce8863a4" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">Teile,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="2a39b359-b93f-44f5-aa97-688839c52300" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3">
                    <akn:num GUID="02becf54-98b3-465e-9341-6a3bc2885d73" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                    <akn:content GUID="5c198579-c673-4c20-9f59-a3aec34f23e4" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1">
                      <akn:p GUID="438e7e63-eff3-4fde-8604-6f19dc467263" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1_text-1">Kapitel,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="c73b076c-e316-4535-b129-6a70dc3a9f0d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4">
                    <akn:num GUID="b3751366-7c53-4574-a853-42f059cc245c" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_bezeichnung-1">d)</akn:num>
                    <akn:content GUID="45191822-ceb4-4fca-a026-470c55be2002" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1">
                      <akn:p GUID="c185e2bb-a564-423a-9acf-078d04ada7d6" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-4_inhalt-1_text-1">Unterkapitel,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="5c935f57-6839-4878-a3d3-d775fa1d0921" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5">
                    <akn:num GUID="5400423d-2fd1-4769-9a0d-b9805c79bad0" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_bezeichnung-1">e)</akn:num>
                    <akn:content GUID="2c325fd5-5622-4bd8-a785-2f7783a67f16" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_inhalt-1">
                      <akn:p GUID="967fec85-6afa-4d88-a5bc-2d0418f9cfca" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-5_inhalt-1_text-1">Abschnitte,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="22ff2fb4-d600-4acd-988b-7241cef3acb4" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6">
                    <akn:num GUID="dac10e4a-6fd0-443d-a5f1-a01f5dbef135" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_bezeichnung-1">f)</akn:num>
                    <akn:content GUID="3fdf1820-87eb-4f16-909d-6dc113d7f304" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_inhalt-1">
                      <akn:p GUID="8fbead81-e858-4ebc-9097-23ac2923113b" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-6_inhalt-1_text-1">oder Unterabschnitte.</akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
              <akn:point GUID="22c1199f-14cf-4aca-a5ad-9a8d229b680b" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="340e6ddf-f79b-4ab7-be76-7d2d1f3cb690" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                <akn:list GUID="81eac59a-ea34-444d-bfe0-77c677b16b84" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1">
                  <akn:intro GUID="bbcb009e-bda2-4039-835e-4051e3f04c2f" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1">
                    <akn:p GUID="000c025f-a6af-4295-9b0a-b34880ca1256" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1_text-1">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                  </akn:intro>
                  <akn:point GUID="833a2a7c-0c04-4916-8b7f-c18877e1a4d5" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1">
                    <akn:num GUID="e9323c66-4d8c-45c0-89bd-f2f48cd5d9b2" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_bezeichnung-1">a)</akn:num>
                    <akn:content GUID="60aac569-dda7-497d-a64a-7090044a5f48" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1">
                      <akn:p GUID="c250bea4-249b-4e4d-985f-b6be98d67ccb" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1">Überschriften,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="82e2d981-70d0-43a2-8c53-fc977ac2846f" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2">
                    <akn:num GUID="4db0c95c-fb3f-4ba3-9cd6-6d08ec2e962d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_bezeichnung-1">b)</akn:num>
                    <akn:content GUID="b6ed379b-6d01-412b-b2f7-8e9e7d3bad74" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1">
                      <akn:p GUID="b2068696-482a-4a02-8fb5-3930909c569c" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1">Zeilen,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="8cc9b1a6-8fbf-4d9c-86d2-868e7c651a1c" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3">
                    <akn:num GUID="30585c2a-cc65-4ecd-978b-c30fffe245c0" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_bezeichnung-1">c)</akn:num>
                    <akn:content GUID="d16edc41-4524-4694-97fa-75cf5021ab32" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1">
                      <akn:p GUID="2440af32-b29e-48b7-9057-9faadb4035da" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-3_inhalt-1_text-1">oder Zellen.</akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
              <akn:wrapUp GUID="4b9d05c0-5c6b-4f1f-8813-695e6a3282ca" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_schlusstext-1">
                <akn:p GUID="ddeb9b4f-9962-4f1e-9b2a-de4381789a0d" eId="hauptteil-1_buch-1_art-1_abs-3_untergl-1_schlusstext-1_text-1">In besonderen Fällen können auch Teile der Präambel, Inhaltsübersicht, Anlagen- oder Stammformverweise geändert werden.</akn:p>
              </akn:wrapUp>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="0e8eb095-23f1-45e0-8c3c-fff597e727a4" eId="hauptteil-1_buch-1_art-1_abs-4">
            <akn:num GUID="2a5c58d9-050c-4cf7-bf02-d9b866343368" eId="hauptteil-1_buch-1_art-1_abs-4_bezeichnung-1">(4)</akn:num>
            <akn:content GUID="85d8973f-2cb3-4da6-90a7-d3ad121f5ce5" eId="hauptteil-1_buch-1_art-1_abs-4_inhalt-1">
              <akn:p GUID="768593a7-7b19-4137-b03c-21abaac54bc0" eId="hauptteil-1_buch-1_art-1_abs-4_inhalt-1_text-1">Alle staatlichen und kommunalen Verwaltungen sind verpflichtet, die in diesem Gesetz festgelegten Maßnahmen zum Ersetzen von Strukturen und Gliederungseinheiten umzusetzen.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="8ca97abb-49ca-44fa-8810-058798764702" eId="hauptteil-1_buch-1_art-1_abs-5">
            <akn:num GUID="784bc155-b806-42bc-8612-cefcd389200e" eId="hauptteil-1_buch-1_art-1_abs-5_bezeichnung-1">(5)</akn:num>
            <akn:list GUID="86d0671d-0e9a-41ea-9f2d-d7a77cb96eac" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1">
              <akn:intro GUID="879a3a88-7c1a-4596-99a2-6e03c6bd1d54" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_intro-1">
                <akn:p GUID="eb4f8775-e400-4eec-8ecb-e63ac18cd16b" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_intro-1_text-1">Jede Verwaltungseinheit hat sicherzustellen, dass:</akn:p>
              </akn:intro>
              <akn:point GUID="20dfe395-2381-4131-8d0c-9fee3121448b" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1">
                <akn:num GUID="db297a3b-378f-4200-8285-920885833556" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                <akn:content GUID="ab0c47ce-4d19-41cf-9dc2-a32d7fad20d3" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="74baa9a5-55cd-45ba-9995-beb2cde10fb2" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-1_inhalt-1_text-1">alle Mitarbeiterinnen und Mitarbeiter angemessen geschult und weitergebildet werden,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="d47597eb-7488-491c-85a8-9547911748af" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2">
                <akn:num GUID="eb85d279-6216-4dee-81b4-998356664abe" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                <akn:content GUID="8dd753a3-7822-47b0-a184-6de039125d71" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="aa2effeb-04ab-4776-a023-558207b93ccf" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-2_inhalt-1_text-1">die eingesetzten Technologien und Verfahren regelmäßig überprüft und bei Bedarf aktualisiert werden,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="54e971dc-39f5-4f24-b418-8510bd8404f5" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3">
                <akn:num GUID="1c7b22f8-058e-4b8b-8903-f68d9aa69afb" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                <akn:content GUID="0513cd4a-cccd-40c9-ab52-d94709ef3380" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="126628ed-fbb4-4967-8173-1a94f09ae36e" eId="hauptteil-1_buch-1_art-1_abs-5_untergl-1_listenelem-3_inhalt-1_text-1">ein kontinuierlicher Verbesserungsprozess etabliert wird, um die Effizienz und Effektivität der Verwaltung zu steigern.</akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="e193cbdc-4d36-4cd8-bebb-b2c3c451606c" eId="hauptteil-1_buch-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="259b109a-f47b-4851-a415-7e282ee89de5" eId="hauptteil-1_buch-1_art-2_bezeichnung-1">§ 2</akn:num>
          <akn:heading GUID="2eb4173c-3f34-43e6-869e-3aaee1d4a7d8" eId="hauptteil-1_buch-1_art-2_überschrift-1">Anwendung in Tabellen</akn:heading>
          <akn:paragraph GUID="45cbd633-209b-4d92-a9cb-bde392060c47" eId="hauptteil-1_buch-1_art-2_abs-1">
            <akn:num GUID="5873e7a4-1f63-4499-9a33-a4280b24c671" eId="hauptteil-1_buch-1_art-2_abs-1_bezeichnung-1"/>
            <akn:content GUID="74d7eac1-eebd-4f88-94e6-dcf79eb17567" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1">
              <akn:p GUID="b5d10c06-ef58-4cd0-9dd5-8bf98bec6791" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_text-1">Folgende Strukturelemente eines Regelungstextes können mittels aenderungZitatStruktur verändert werden:</akn:p>
              <akn:table border="0" GUID="48e1230a-11d7-483c-943b-295461e9c105" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1">
                <akn:tr GUID="6baf5a84-9d74-4afe-8a4a-d6c0b1b7e147" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1">
                  <akn:th style="width:200" GUID="34646d93-37f4-4423-a71b-590b0d469c3d" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1">
                    <akn:p GUID="7cdb5386-8c6d-40ae-9201-2b34ceb9b750" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Fachliche Klasse </akn:p>
                  </akn:th>
                  <akn:th style="width:200" GUID="1b578782-b5ca-4e8f-81ab-4f91097b787e" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                    <akn:p GUID="81a8ba3e-db41-468a-8107-5fb2a0d89817" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">AKN-Entsprechung </akn:p>
                  </akn:th>
                </akn:tr>
                <akn:tr GUID="ee744b34-922e-449e-a431-7e8e209197ff" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2">
                  <akn:td style="text-align:right" GUID="d1955e4d-ee10-436d-ba70-b98f8dd75434" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1">
                    <akn:p GUID="c7b3def7-b2f7-4982-a28a-5c76680b8b79" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1_text-1">dokumentenkopfTitel</akn:p>
                  </akn:td>
                  <akn:td GUID="080f05d2-188a-4939-866f-8834f83d32be" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2">
                    <akn:p GUID="7eb4d169-3ef6-4143-85e2-9380a84148b7" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2_text-1">akn:longTitle</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="880a96a9-7318-4863-9a6b-b4d3a6bdb941" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3">
                  <akn:td style="text-align:right" GUID="a7818096-a201-4266-bf8e-cee819bd5a94" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                    <akn:p GUID="424bb9e6-e851-402c-8f2d-3918187cf806" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">ermaechtigungsnormen</akn:p>
                  </akn:td>
                  <akn:td GUID="cada41e9-5b07-447c-a84d-9bbe594e6808" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                    <akn:p GUID="a8d1776d-69ad-4ac2-9e99-66161fc29adb" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">akn:citations</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="cfe1826d-6995-4102-958e-2de8064fbe92" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4">
                  <akn:td style="text-align:right" GUID="537d2ba4-eb40-4e93-a574-f369ad13f381" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1">
                    <akn:p GUID="a28e60bf-af23-4428-b10e-781189bc19e6" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1">praeambel</akn:p>
                  </akn:td>
                  <akn:td GUID="6e7b1488-f65b-495b-9a7b-ba2f1ef94748" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2">
                    <akn:p GUID="4f24972f-f210-42ad-bc59-f9824bb21e16" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1">akn:recitals</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="196321c1-4e58-4987-a80e-8b42940ceb9e" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5">
                  <akn:td style="text-align:right" GUID="7555600b-576e-484c-bc09-a2e4d7561b95" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1">
                    <akn:p GUID="632708da-8035-42ee-9741-66360ec57739" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1">praeambelInhalt</akn:p>
                  </akn:td>
                  <akn:td GUID="b4a897d3-347c-4499-9235-aac7bec77314" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2">
                    <akn:p GUID="0f0f62f5-e032-4127-b4e7-10ff5d91969b" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1">akn:recital</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="e633ee12-1180-4818-8d54-5664499a5a95" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6">
                  <akn:td style="text-align:right" GUID="822434fd-8abf-4b20-8eed-a477d831296a" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1">
                    <akn:p GUID="051a27ce-e0d0-42f1-890e-f7ba8961de67" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1">verzeichniscontainer</akn:p>
                  </akn:td>
                  <akn:td GUID="6800dcf3-6aa0-4494-905c-8153f18b8d30" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2">
                    <akn:p GUID="aa2915bd-be21-4da7-960a-55e01c383b98" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1">akn:blockContainer</akn:p>
                  </akn:td>
                </akn:tr>
              </akn:table>
              <akn:p GUID="8e1e171d-5189-4a98-8019-e4b60521e36c" eId="hauptteil-1_buch-1_art-2_abs-1_inhalt-1_text-2">Die Tabelle ist nur ein Auszug aus der Spezifikation und erhebt keinen Anspruch auf Vollständigkeit.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
      <akn:book GUID="a129be96-a16c-461a-a365-60dc78c1bd50" eId="hauptteil-1_buch-2">
        <akn:num GUID="19042493-7d77-4553-8e7d-2676a398cb32" eId="hauptteil-1_buch-2_bezeichnung-1">2. Buch</akn:num>
        <akn:heading GUID="e2c55f1b-f3f4-4c11-adc6-ee5a7735e3c9" eId="hauptteil-1_buch-2_überschrift-1">Beispiele für Strukturen</akn:heading>
        <akn:chapter GUID="a3eaffeb-4203-4045-8e1b-4474b32f83ef" eId="hauptteil-1_buch-2_kapitel-1">
          <akn:num GUID="36fe5375-4a3e-4732-b340-dd73d8e5c174" eId="hauptteil-1_buch-2_kapitel-1_bezeichnung-1"/>
          <akn:heading GUID="1366de71-40f5-463f-af84-0d750a653c8c" eId="hauptteil-1_buch-2_kapitel-1_überschrift-1">Implementierung</akn:heading>
          <akn:article GUID="aa5586c7-ebd7-461e-aae6-03e031407ee6" eId="hauptteil-1_buch-2_kapitel-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="196221bc-1011-4c4b-ae7e-8fd7e6220271" eId="hauptteil-1_buch-2_kapitel-1_art-1_bezeichnung-1">§ 3</akn:num>
            <akn:heading GUID="3b95fdd6-8d90-4a96-b894-1a3b6d447b67" eId="hauptteil-1_buch-2_kapitel-1_art-1_überschrift-1">Implementierung der neuen Strukturen</akn:heading>
            <akn:paragraph GUID="1c3875fe-8d90-44b2-932a-c5d8fb40c0f2" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1">
              <akn:num GUID="cb125ca2-eef8-4425-b592-b0b7a040626c" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
              <akn:content GUID="cbdc76dc-684b-4126-9564-3d76767a3e76" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_inhalt-1">
                <akn:p GUID="f77faf5e-d1ba-400d-b812-507effc9888c" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-1_inhalt-1_text-1">Die Implementierung der in diesem Gesetz vorgesehenen neuen Strukturen und Gliederungseinheiten erfolgt schrittweise und in enger Abstimmung mit den betroffenen Verwaltungseinheiten.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="7b9e8d71-881b-4bea-b8af-779180f19a6b" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2">
              <akn:num GUID="2ceb63ec-b286-4eba-acfe-6261f7a6657d" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
              <akn:list GUID="974446da-684c-493e-9e4a-133751410987" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1">
                <akn:intro GUID="d229b155-6258-4331-ad2e-94a9885c25a0" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_intro-1">
                  <akn:p GUID="cbf51d3e-e3af-418e-85f6-8618d3374ef4" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_intro-1_text-1">Zur Unterstützung der Implementierung wird eine zentrale Koordinierungsstelle eingerichtet, die die folgenden Aufgaben übernimmt:</akn:p>
                </akn:intro>
                <akn:point GUID="d2cf2ca3-59da-471c-9583-4fc634d6cdcb" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1">
                  <akn:num GUID="52490aeb-24ec-4c4d-890f-748956bee998" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                  <akn:content GUID="31aa21cc-de90-43f0-bd6a-ca0b3edb918d" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="f0f598ec-bfa6-45ec-a1e1-24c00d05f937" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">Erstellung eines detaillierten Implementierungsplans, der die einzelnen Schritte und Zeitrahmen festlegt,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="57a5e0d5-f889-4511-b432-804583035606" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2">
                  <akn:num GUID="b99612a4-c9db-4c40-9c9a-847b950bc3d9" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                  <akn:content GUID="5b11a9b8-144b-4455-a063-5075ca5a9412" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="7eb2e76c-6b14-439b-90f4-5f99853077e8" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">Bereitstellung von Ressourcen und Schulungen für die betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="62bf7281-18da-43af-ace2-312f6b41152d" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3">
                  <akn:num GUID="8afea7c3-928b-4f0a-ae29-5342c403c79c" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                  <akn:content GUID="ea643a63-c11b-4ce9-9c07-d891b917a8d0" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="3355fa8e-b7fd-4b8e-8f9e-2850d3fd7ff3" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">Beratung und Unterstützung der Verwaltungseinheiten bei der Umsetzung der neuen Strukturen und Prozesse,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="04792bbb-0fb8-4eb1-8487-34f64b09de5a" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4">
                  <akn:num GUID="5938d974-b74c-4bd3-b6ca-6938e4126ee7" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_bezeichnung-1">3.</akn:num>
                  <akn:content GUID="c90dc80b-da00-4ea3-95f4-9d1183e58e8d" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_inhalt-1">
                    <akn:p GUID="f813bcb9-4276-4ec3-849f-641e5a1df80c" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-2_untergl-1_listenelem-4_inhalt-1_text-1">Überwachung und Bewertung des Fortschritts der Implementierung und Anpassung des Plans bei Bedarf.</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:paragraph>
            <akn:paragraph GUID="8769ffbd-cf68-472c-a1a4-729f4df2aabb" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3">
              <akn:num GUID="e114e8bb-ca15-4073-8354-ae96370a242e" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
              <akn:content GUID="ee4a28a3-3ed2-4295-816b-ce5d40b074df" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_inhalt-1">
                <akn:p GUID="fe09ee0c-7a0f-4a65-b02f-f4665d495ce9" eId="hauptteil-1_buch-2_kapitel-1_art-1_abs-3_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
          <akn:article GUID="de68d3f9-51d1-4bf5-ada5-67b57d2fb071" eId="hauptteil-1_buch-2_kapitel-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="65d947a1-3abd-48b3-84e8-75ead8a292d1" eId="hauptteil-1_buch-2_kapitel-1_art-2_bezeichnung-1">§ 4</akn:num>
            <akn:heading GUID="4bc35f4a-ed82-49f8-8273-9c6dd2c600a8" eId="hauptteil-1_buch-2_kapitel-1_art-2_überschrift-1">Finanzielle Unterstützung</akn:heading>
            <akn:paragraph GUID="5e0333ee-9c78-4eb5-91c0-55f819601584" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1">
              <akn:num GUID="b56490ca-13ca-432d-a0f3-e4a766564912" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_bezeichnung-1">(1)</akn:num>
              <akn:content GUID="bebffd41-856c-4a85-8fd1-cde5ce92c8d6" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_inhalt-1">
                <akn:p GUID="a4de923c-913e-44f2-a4f4-358fd3391088" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-1_inhalt-1_text-1">Zur Finanzierung der Maßnahmen nach diesem Gesetz stellt der Bund den Ländern und Kommunen zweckgebundene Finanzmittel zur Verfügung.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="6a66e950-42f8-441b-bab3-079aa7a681af" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2">
              <akn:num GUID="a1575091-e950-4560-9eff-e6ff2b1e2ee7" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_bezeichnung-1">(2)</akn:num>
              <akn:list GUID="7a1f750c-ed9a-466e-8803-dc2639b2a43f" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1">
                <akn:intro GUID="f3aca098-230b-498d-b1aa-c360f83ccff2" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_intro-1">
                  <akn:p GUID="b6b8afa7-10c6-4e65-ad67-4878a51623ce" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_intro-1_text-1">Die Höhe der Finanzmittel wird jährlich im Bundeshaushalt festgelegt und richtet sich nach:</akn:p>
                </akn:intro>
                <akn:point GUID="191e7b4d-c033-4eae-a307-7e8fab71ea0f" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1">
                  <akn:num GUID="7d01e5c4-6cdb-4b3e-b7e6-2f0bd0efd742" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                  <akn:content GUID="45875ee2-291d-425b-8f4d-1f0496fd4b08" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="3adbd55c-5a39-47e4-a87f-56cc7397ff49" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">dem Umfang der erforderlichen Maßnahmen in den jeweiligen Verwaltungseinheiten,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="71b8826a-0859-4f98-aa0f-a133e0c05fe3" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2">
                  <akn:num GUID="d1d5b436-0549-4bbd-9453-cefcb31eb729" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                  <akn:content GUID="5a25606c-3116-4339-8d85-dd6c4e5f2273" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="2ac69f83-ec8b-4400-be8e-f17cecde70b6" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">der Anzahl der betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="778e55ed-afbb-457c-ac16-3864a72ac13a" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3">
                  <akn:num GUID="c4fc8bee-2c46-4ac3-910a-d3854a6e5071" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                  <akn:content GUID="8f319aa7-3f6b-459f-803c-1e9208507114" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="5fb951bd-68ac-4f48-add8-5a158e7e1af8" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">den spezifischen regionalen Anforderungen und Besonderheiten. </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:paragraph>
            <akn:paragraph GUID="f49fa6a5-a461-4ff1-940b-dde64dea325c" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3">
              <akn:num GUID="4ad3d189-0cdf-4da5-b516-303604c3b1f1" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_bezeichnung-1">(3)</akn:num>
              <akn:content GUID="85faacaa-c581-429c-828c-c88f05126022" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1">
                <akn:p GUID="f5fddc9e-cf9d-4bf5-932c-f57c57f09a50" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1_text-1">Die Verwendung der Finanzmittel ist zweckgebunden und muss im Rahmen der jährlichen Berichterstattung nach <akn:ref href="#hauptteil-1_buch-2_kapitel-1_art-3" GUID="7cd498d4-0036-4468-a76c-cc1407525847" eId="hauptteil-1_buch-2_kapitel-1_art-2_abs-3_inhalt-1_text-1_ref-1">Artikel 5</akn:ref> detailliert nachgewiesen werden.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
          <akn:article GUID="99c6adbd-633e-4947-85c5-bd584074ff99" eId="hauptteil-1_buch-2_kapitel-1_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="03fa6456-85ca-46c4-8b9c-95d576768886" eId="hauptteil-1_buch-2_kapitel-1_art-3_bezeichnung-1">§ 5</akn:num>
            <akn:heading GUID="336bc39d-fca7-490d-957b-06efaaade77b" eId="hauptteil-1_buch-2_kapitel-1_art-3_überschrift-1">Evaluierung und Anpassung</akn:heading>
            <akn:paragraph GUID="0af48e72-b5ed-47dd-9a91-20e350c80141" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1">
              <akn:num GUID="0898bc5b-386b-4609-80f7-f654d0f97cc1" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_bezeichnung-1">(1)</akn:num>
              <akn:content GUID="11b8df23-aa8d-4823-b327-23c7b87b531c" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_inhalt-1">
                <akn:p GUID="69d40bc6-98e8-4185-a9cc-66f991a95f3f" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-1_inhalt-1_text-1">Die Wirksamkeit der durch dieses Gesetz eingeführten Maßnahmen wird regelmäßig durch unabhängige Gutachter überprüft.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="99cf651b-7cf3-44aa-b10d-8823a17043fe" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2">
              <akn:num GUID="f1d6f4fa-bc3b-4cfb-869e-822d12330c7b" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_bezeichnung-1">(2)</akn:num>
              <akn:content GUID="8178100f-e1c0-4df8-af5b-e1404671e502" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_inhalt-1">
                <akn:p GUID="5010cd60-3cf3-4e7a-8fee-86ca5673897e" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-2_inhalt-1_text-1">Die erste Evaluierung erfolgt zwei Jahre nach Inkrafttreten dieses Gesetzes, weitere Evaluierungen folgen alle drei Jahre.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="e941385b-a3c3-4212-be8b-f142de30ce41" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3">
              <akn:num GUID="fb7dd420-0992-4fd3-8f8c-ee4cb3aaa784" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_bezeichnung-1">(3)</akn:num>
              <akn:content GUID="467a81c1-e7b1-42ff-a13a-c833dca470cf" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_inhalt-1">
                <akn:p GUID="3e0917f7-7ed1-4247-b3a3-855ff9aed571" eId="hauptteil-1_buch-2_kapitel-1_art-3_abs-3_inhalt-1_text-1">Basierend auf den Ergebnissen der Evaluierungen kann der Gesetzgeber Anpassungen und Verbesserungen dieses Gesetzes vornehmen, um die angestrebten Ziele effektiver zu erreichen.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
          <akn:article GUID="d42417a8-68bd-4d58-bc72-0be833b67cd7" eId="hauptteil-1_buch-2_kapitel-1_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="592085fd-1057-4657-b76b-a0dc5fa9f10b" eId="hauptteil-1_buch-2_kapitel-1_art-4_bezeichnung-1">§ 5a</akn:num>
            <akn:heading GUID="3ee186ed-0378-408c-af0c-a1141976ce8a" eId="hauptteil-1_buch-2_kapitel-1_art-4_überschrift-1">Stammverweis</akn:heading>
            <akn:componentRef src="eli-noch-undefiniert" showAs="regelungstext-eingebundene-stammform" GUID="b7a86030-d74b-4baa-b0e3-ff5a09f7147d" eId="hauptteil-1_buch-2_kapitel-1_art-4_stfmverweis-1"/>
          </akn:article>
        </akn:chapter>
      </akn:book>
      <akn:book GUID="1978c63c-7284-4a1e-aafd-03b771d4465a" eId="hauptteil-1_buch-3">
        <akn:num GUID="82f5582d-4137-4acb-915f-6c6bf1a983a0" eId="hauptteil-1_buch-3_bezeichnung-1">3. Buch</akn:num>
        <akn:heading GUID="22844bcd-ca7d-4124-b970-b66fb07393bf" eId="hauptteil-1_buch-3_überschrift-1">Beispiele Teil I</akn:heading>
        <akn:article GUID="3234d0c5-4db7-4b07-a68d-6bd7ff2e9c1d" eId="hauptteil-1_buch-3_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="24b6f7e3-bb90-4063-a4f3-b05756dfc821" eId="hauptteil-1_buch-3_art-1_bezeichnung-1">§ 6</akn:num>
          <akn:heading GUID="7cb430a1-0721-414d-8f73-42573dd3a644" eId="hauptteil-1_buch-3_art-1_überschrift-1">Beispielartikel</akn:heading>
          <akn:paragraph GUID="3daeeed8-49ec-4200-af9b-0ee4fb2c3c4a" eId="hauptteil-1_buch-3_art-1_abs-1">
            <akn:num GUID="63e1ef3c-5a33-4fce-88a9-5e72e99031b0" eId="hauptteil-1_buch-3_art-1_abs-1_bezeichnung-1"/>
            <akn:content GUID="dcccbc8d-1732-4ea9-bf13-0f9a6d85b190" eId="hauptteil-1_buch-3_art-1_abs-1_inhalt-1">
              <akn:p GUID="10116248-7a32-46ce-b7f8-efbff8f5f8d3" eId="hauptteil-1_buch-3_art-1_abs-1_inhalt-1_text-1">Die Koordinierungsstelle, die im Rahmen des Gesetzes eingerichtet wird, spielt eine zentrale Rolle bei der Umsetzung der Reformen. Sie fungiert als zentrale Anlaufstelle für alle staatlichen und kommunalen Verwaltungen, die Unterstützung bei der Neustrukturierung ihrer organisatorischen Einheiten benötigen. Zu den Hauptaufgaben der Koordinierungsstelle gehören die Erstellung detaillierter Implementierungspläne, die Bereitstellung von Ressourcen und Schulungen sowie die Beratung der Verwaltungseinheiten bei der Einführung neuer Technologien und Prozesse. Zudem überwacht die Koordinierungsstelle den Fortschritt der Reformen und passt die Maßnahmen bei Bedarf an, um eine effiziente und effektive Umsetzung sicherzustellen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
      <akn:book GUID="af8330ff-d691-4c49-b8cc-6b1a48dce58f" eId="hauptteil-1_buch-4">
        <akn:num GUID="594930dd-ce37-4114-96bc-50297c191402" eId="hauptteil-1_buch-4_bezeichnung-1">4. Buch</akn:num>
        <akn:heading GUID="e8239b5e-ea66-4ed7-a433-be5d4b3ebccd" eId="hauptteil-1_buch-4_überschrift-1">Beispielbuch</akn:heading>
        <akn:chapter GUID="427921a0-b680-4fed-9f47-397bae3acc81" eId="hauptteil-1_buch-4_kapitel-1">
          <akn:num GUID="bca93698-8445-4448-8905-9881e415281d" eId="hauptteil-1_buch-4_kapitel-1_bezeichnung-1"/>
          <akn:heading GUID="77cacd70-bd66-47de-8f72-70147c361b3c" eId="hauptteil-1_buch-4_kapitel-1_überschrift-1">Beispielkapitel 1</akn:heading>
          <akn:article GUID="ef19a749-b18c-4eeb-80b2-14db99dccf04" eId="hauptteil-1_buch-4_kapitel-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="85601be4-2a7b-42b2-bd6e-42a33d8b8427" eId="hauptteil-1_buch-4_kapitel-1_art-1_bezeichnung-1">§ 7</akn:num>
            <akn:heading GUID="970cbe0b-74b7-4cf5-a46b-f9798caf9003" eId="hauptteil-1_buch-4_kapitel-1_art-1_überschrift-1">Beispielartikel</akn:heading>
            <akn:paragraph GUID="87ea1435-253b-4e20-a738-188baa23e74d" eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1">
              <akn:num GUID="0e73f09e-4b9e-4d8b-a5dd-4b798a5aed99" eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_bezeichnung-1"/>
              <akn:content GUID="e78d9d6a-4b92-471d-a05b-8265c0af95ac" eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_inhalt-1">
                <akn:p GUID="03e6f786-de97-4d3f-bf2a-d4bca076c3a9" eId="hauptteil-1_buch-4_kapitel-1_art-1_abs-1_inhalt-1_text-1">Der Implementierungsplan ist ein zentraler Bestandteil des Gesetzes und dient als Leitfaden für die schrittweise Umsetzung der vorgesehenen Reformen. Dieser Plan wird von der zentralen Koordinierungsstelle in enger Zusammenarbeit mit den betroffenen Verwaltungseinheiten entwickelt. Er umfasst detaillierte Zeitpläne, klare Meilensteine und spezifische Maßnahmen, die zur Modernisierung der Strukturen notwendig sind.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
        </akn:chapter>
        <akn:chapter GUID="1287e61d-ad05-45d2-8104-d156934a1e47" eId="hauptteil-1_buch-4_kapitel-2">
          <akn:num GUID="46268b8d-787f-4bf2-972a-245ba0d5f8fb" eId="hauptteil-1_buch-4_kapitel-2_bezeichnung-1"/>
          <akn:heading GUID="7aa66f9e-27e6-4693-ac59-d1ab25a2d3ce" eId="hauptteil-1_buch-4_kapitel-2_überschrift-1">Beispielkapitel 2</akn:heading>
          <akn:subchapter GUID="07758e78-55af-4511-95ce-985ab5b2f3cf" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1">
            <akn:num GUID="0d1faff3-3043-4dbe-9615-76359376cbbb" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_bezeichnung-1"/>
            <akn:heading GUID="ef97d84e-3f24-4b3b-b971-5fc75e758dde" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_überschrift-1">Beispielunterkapitel 2.1</akn:heading>
            <akn:article GUID="746efd29-e92e-45f7-990d-6e0a1536e45f" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
              <akn:num GUID="589a65a7-3e9d-43b3-91f3-71161bec3790" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_bezeichnung-1">§ 7a</akn:num>
              <akn:heading GUID="bfb642fd-06df-4415-bf4b-6b0234bdc72c" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_überschrift-1">Beispiel</akn:heading>
              <akn:paragraph GUID="22411706-d900-41f4-930a-379b7ecb258f" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_abs-1">
                <akn:num GUID="b7a4bf05-1c47-4c67-b18f-59aa57f273d0" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_abs-1_bezeichnung-1"/>
                <akn:content GUID="858f8199-33aa-4701-8ed9-85b6bd5e33a2" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_abs-1_inhalt-1">
                  <akn:p GUID="2167e9d4-f902-4f55-8a6e-96b61e8a8d88" eId="hauptteil-1_buch-4_kapitel-2_ukapitel-1_art-1_abs-1_inhalt-1_text-1">Beispielinhalt für den Paragraphen § 7a.</akn:p>
                </akn:content>
              </akn:paragraph>
            </akn:article>
          </akn:subchapter>
        </akn:chapter>
        <akn:chapter GUID="84ecb593-fdc8-4c53-833f-1fdae51b5411" eId="hauptteil-1_buch-4_kapitel-3">
          <akn:num GUID="0134a0cf-c082-4bc3-b60c-ca48abf52f62" eId="hauptteil-1_buch-4_kapitel-3_bezeichnung-1"/>
          <akn:heading GUID="89b757a8-4b85-47a3-8b9f-b05cb3e66800" eId="hauptteil-1_buch-4_kapitel-3_überschrift-1">Beispielkapitel 3</akn:heading>
          <akn:subchapter GUID="2dc7e271-b2e8-433d-8683-0a673efed6e9" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1">
            <akn:num GUID="f49acfe2-9180-403f-ae24-954eaf129d84" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_bezeichnung-1"/>
            <akn:heading GUID="0c9d1caf-a078-45f7-bea7-0bde92f15667" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_überschrift-1">Beispielunterkapitel 3.1</akn:heading>
            <akn:title GUID="a09b03fe-dec6-4aaa-9e16-879df3fe7e7c" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1">
              <akn:num GUID="12533cdc-48c1-48d2-8985-329b701ab6eb" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_bezeichnung-1"/>
              <akn:heading GUID="4975c224-5103-4d48-87a6-75f8ddedfbec" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_überschrift-1">Beispieltitel 3.1.1</akn:heading>
              <akn:article GUID="39ed860b-8ad2-4725-a416-b457539ca985" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                <akn:num GUID="1367d04b-03f0-4948-a36e-0f9a7485fc49" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_bezeichnung-1">§ 7b</akn:num>
                <akn:heading GUID="1f26350c-257c-4b48-9cf6-1184855046f7" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_überschrift-1">Beispiel</akn:heading>
                <akn:paragraph GUID="e30e53fd-ea56-45ca-8932-0bf8a96f3ac9" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_abs-1">
                  <akn:num GUID="b44c25eb-0458-47e4-aab6-c60882dcc734" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_abs-1_bezeichnung-1"/>
                  <akn:content GUID="bdcd1af4-4110-42f8-b6ee-81e4fe5309a3" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_abs-1_inhalt-1">
                    <akn:p GUID="6e637bb7-20c0-49e9-b94c-12464d5ea516" eId="hauptteil-1_buch-4_kapitel-3_ukapitel-1_titel-1_art-1_abs-1_inhalt-1_text-1">Beispielinhalt für den Paragraphen § 7b.</akn:p>
                  </akn:content>
                </akn:paragraph>
              </akn:article>
            </akn:title>
          </akn:subchapter>
        </akn:chapter>
        <akn:chapter GUID="51e96dd5-f889-470b-895b-a127ceb4bd23" eId="hauptteil-1_buch-4_kapitel-4">
          <akn:num GUID="b2487f9d-62f5-43c0-a0f1-1ab98e928d7f" eId="hauptteil-1_buch-4_kapitel-4_bezeichnung-1"/>
          <akn:heading GUID="460c3b42-16aa-4fde-a209-e79543cc8cb0" eId="hauptteil-1_buch-4_kapitel-4_überschrift-1">Beispielkapitel 4</akn:heading>
          <akn:subchapter GUID="fa8c3d57-001c-4f4e-92f7-5a6b1d96bd93" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1">
            <akn:num GUID="d88c7021-6378-4205-914c-e252d45f6087" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_bezeichnung-1"/>
            <akn:heading GUID="f9bb9fb3-d451-4c6f-a706-58b1e033dd2f" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_überschrift-1">Beispielunterkapitel 4.1</akn:heading>
            <akn:title GUID="3a68a825-154b-4b60-abbd-2c3a4ed45dd3" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1">
              <akn:num GUID="a2463a91-4f34-4c80-ad7b-1148bd269472" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_bezeichnung-1"/>
              <akn:heading GUID="76aff387-ae57-4d28-a80b-fe6cd5af0b92" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_überschrift-1">Beispieltitel 4.1.1</akn:heading>
              <akn:subtitle GUID="9a6e760a-bbc5-4a25-86fa-7577b16bea91" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1">
                <akn:num GUID="1ff33308-3045-4b2c-9b9e-3adb00124ba1" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_bezeichnung-1"/>
                <akn:heading GUID="8905080b-717d-4e7b-82de-23bfeb469f05" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_überschrift-1">Beispieluntertitel 4.1.1.1</akn:heading>
                <akn:article GUID="3ec95995-0bc2-4122-a6ad-2f56c35d1b29" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
                  <akn:num GUID="3a2f69de-032f-4129-9201-012e7f1d30b2" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_bezeichnung-1">§ 7c</akn:num>
                  <akn:heading GUID="aa967fd9-54aa-4471-9219-073d889bd478" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_überschrift-1">Beispiel</akn:heading>
                  <akn:paragraph GUID="082f0f34-85d7-481b-9641-8126a2a13f9f" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_abs-1">
                    <akn:num GUID="0c4f96b9-f1a8-419c-a113-0f9d5786c99b" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_abs-1_bezeichnung-1"/>
                    <akn:content GUID="8a5bba34-1121-40fa-b832-7524885549e7" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_abs-1_inhalt-1">
                      <akn:p GUID="3b67b303-bbf0-4573-aa6b-40670dbd2360" eId="hauptteil-1_buch-4_kapitel-4_ukapitel-1_titel-1_utitel-1_art-1_abs-1_inhalt-1_text-1">Beispielinhalt für den Paragraphen § 7c.</akn:p>
                    </akn:content>
                  </akn:paragraph>
                </akn:article>
              </akn:subtitle>
            </akn:title>
          </akn:subchapter>
        </akn:chapter>
      </akn:book>
      <akn:book GUID="887b1c64-8a1a-4c2c-8e66-49ae51e9f046" eId="hauptteil-1_buch-5">
        <akn:num GUID="66c96a40-5e10-4465-a643-a4707e395bcc" eId="hauptteil-1_buch-5_bezeichnung-1">5. Buch</akn:num>
        <akn:heading GUID="09522162-c170-455c-a959-f72cbca349fa" eId="hauptteil-1_buch-5_überschrift-1">Übergangsregelungen und Geltungszeiten</akn:heading>
        <akn:article GUID="0df9062c-aa4d-4b1f-ae2f-22cb5b0be73a" eId="hauptteil-1_buch-5_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8e6bbf83-c28b-48b6-b1b7-e8c7515c9b47" eId="hauptteil-1_buch-5_art-1_bezeichnung-1">§ 8</akn:num>
          <akn:heading GUID="b8964bd5-3447-4f39-8af6-438ac7ee928b" eId="hauptteil-1_buch-5_art-1_überschrift-1">Übergangsregelungen</akn:heading>
          <akn:paragraph GUID="17f2610e-aa83-4a2e-9e3a-8b41c14d3bec" eId="hauptteil-1_buch-5_art-1_abs-1">
            <akn:num GUID="b71f74d9-ae2a-4c96-b590-5a293a7b50ed" eId="hauptteil-1_buch-5_art-1_abs-1_bezeichnung-1">(1)</akn:num>
            <akn:list GUID="1a0d72f8-4418-4b49-a60e-e3db3f9d2a00" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1">
              <akn:intro GUID="060b71bd-4027-4422-b4f5-ae6cb051e73c" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_intro-1">
                <akn:p GUID="eadee712-f479-4521-9cc4-fe94bbd83d01" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_intro-1_text-1">Während der Übergangsphase, die bis zu fünf Jahre nach Inkrafttreten dieses Gesetzes dauern kann, gelten folgende Regelungen:</akn:p>
              </akn:intro>
              <akn:point GUID="6eef87c4-13c0-4937-b0ef-a412dde43aa1" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="e5b2eb23-90a1-423c-9454-cabd27dd65ae" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">1.</akn:num>
                <akn:content GUID="9ed5855c-b013-4e30-b2bc-dc72b75924f0" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="536a6802-5a37-4e5a-8f78-9f940933516a" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">Bestehende Strukturen und Gliederungseinheiten dürfen parallel zu den neuen Einheiten weitergeführt werden, sofern dies zur Sicherstellung der Kontinuität der Verwaltungsabläufe erforderlich ist,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="ff1c1671-23f0-479b-9d92-3863a5d8282b" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="baddb093-f92d-4833-9ccd-12c93c3ef4ec" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1">2.</akn:num>
                <akn:content GUID="fef0c2c2-98cf-45d0-a2a8-218252bebb1d" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="424304b1-7cd6-4a0d-a4f7-60a85b55e2f0" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">Verwaltungseinheiten, die Schwierigkeiten bei der Umsetzung der neuen Strukturen haben, können bei der zentralen Koordinierungsstelle Unterstützung beantragen,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="31a53540-acca-4c6a-bbf4-93fe87c92880" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="6a91ba4a-5eaf-484b-9318-bae6f1714f76" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_bezeichnung-1">3.</akn:num>
                <akn:content GUID="b8501399-d9d1-4ed7-b4a4-7ab77e7ad9ab" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="3e855124-d7db-4692-a341-57fe171e40ae" eId="hauptteil-1_buch-5_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">Mitarbeiterinnen und Mitarbeiter, deren Aufgaben sich durch die neuen Strukturen ändern, haben Anspruch auf Weiterbildungs- und Umschulungsmaßnahmen.</akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="3c04cde5-0fb2-456e-a35a-a3fa31e2a90e" eId="hauptteil-1_buch-5_art-1_abs-2">
            <akn:num GUID="6b7eff32-d318-4545-8803-5682c1245381" eId="hauptteil-1_buch-5_art-1_abs-2_bezeichnung-1">(2)</akn:num>
            <akn:content GUID="1d7e64ec-6434-4628-be53-3fcd4ebccbfb" eId="hauptteil-1_buch-5_art-1_abs-2_inhalt-1">
              <akn:p GUID="053265a2-e82f-4477-b261-7c1bd05e1abd" eId="hauptteil-1_buch-5_art-1_abs-2_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article refersTo="geltungszeitregel" GUID="c2113830-34ee-4970-8f96-b0327faabc10" eId="hauptteil-1_buch-5_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="7f5c489a-31cd-40bb-bca5-ea2401e7cb2e" eId="hauptteil-1_buch-5_art-2_bezeichnung-1">§ 9</akn:num>
          <akn:heading GUID="c4e66c1f-192c-4ffb-ad61-bd2daf43a335" eId="hauptteil-1_buch-5_art-2_überschrift-1">Inkrafttreten</akn:heading>
          <akn:paragraph GUID="c8920cdb-4e58-48b3-a371-560bc14f71e7" eId="hauptteil-1_buch-5_art-2_abs-1">
            <akn:num GUID="d346f33d-98a9-408a-bbb6-6f174f2f65a6" eId="hauptteil-1_buch-5_art-2_abs-1_bezeichnung-1"/>
            <akn:content GUID="04ff09ff-6ef2-49e9-b968-a4da83c91cdb" eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1">
              <akn:p GUID="d8dcffe5-2667-4393-8282-df608f3bd085" eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1_text-1">Dieses Gesetz tritt <akn:date date="1002-01-02" refersTo="inkrafttreten-datum" GUID="b47cd63d-1506-4206-8648-4df7569760fa" eId="hauptteil-1_buch-5_art-2_abs-1_inhalt-1_text-1_datum-1">am Tag nach der Verkündung</akn:date> in Kraft.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
    </akn:body>
    <akn:attachments GUID="c064f624-4722-4d13-9533-e4cb65ce129f" eId="anlagen-1">
      <akn:attachment GUID="972b19e1-0e01-4125-82de-34ae1b8c5b57" eId="anlagen-1_anlage-1">
        <akn:documentRef href="eli-noch-undefiniert" showAs="offene-struktur" GUID="abeb80ea-b0bc-414b-b605-fbf0939c3933" eId="anlagen-1_anlage-1_verweis-1"/>
      </akn:attachment>
    </akn:attachments>
  </akn:act>
</akn:akomaNtoso>');
