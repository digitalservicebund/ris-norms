-- REAPPLY
-- TARGET LAW
DELETE
FROM norms
WHERE eli_dokument_expression = 'eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1';

INSERT INTO norms (publish_state, xml)
VALUES ('PUBLISHED', '<?xml version="1.0" encoding="UTF-8"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="97a3f3ca-95eb-4537-9abe-7842c6f44bbe" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="f1e6d9db-8b03-45de-a571-f4de1431e186" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="feac1789-29f1-4818-8c8e-01a09daf8ca6" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1001/1/regelungstext-1" GUID="fdd7e642-59fc-4887-9b12-fb45d66ba1dc" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1001/1" GUID="8eb79158-8d3f-47e1-be7d-f73a97eeb570" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" GUID="4420cb2e-e03f-4842-be47-8782c5728d33" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1001-01-01" GUID="198739d4-dbe1-4a35-8db7-142470fff196" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="064fc8f0-ba87-4b7e-8f60-a81b6ac2cdd1" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="ddcee214-f809-416b-883c-9e424ce73204" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="1" GUID="9a02c0ce-046c-4c40-bcfd-251277e9710a" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="f9886a16-9214-4b4a-8d71-df34ed5803ea" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="0c88bcfb-b64e-4903-8616-40c2e6c5bbc5" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="9251a402-8f00-4685-a6b2-781eddbdcd41" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1" GUID="e79ea384-c092-4303-ac10-00c4f2855ea9" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu" GUID="0c12fc86-2aad-4c73-8d55-0682b7236d1b" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="f260b43f-6218-4bd7-ac1a-f5f46d190bfb" GUID="98cdb195-6eaf-43fa-890f-2ebef2fffc90" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="3d5ac81e-6499-4005-926c-04a99410d361" GUID="50f1efa1-6f42-4ea7-bc2a-017ff36e2c58" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="74bf6136-453d-487a-b82c-41ad39a4c915" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1001-01-01" GUID="ae9666e9-698e-4e5d-a95d-e890d4a8ad9b" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="d23d9e75-c2cc-43cb-a848-2784827f8b26" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="36d0ad5c-7d4b-44d2-8e41-8916cf427eeb" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="be211aed-da83-4bd0-8a75-ae4fa08a3dad" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/1001-01-01/regelungstext-1.xml" GUID="c8637b43-dbdf-431f-8fa6-c66757785233" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/1001-01-01/regelungstext-1.xml" GUID="5e08e15f-5ff4-44a3-90da-5dec9f25f945" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1001-01-01" GUID="4355a851-1bf2-42b6-ab82-aab59a5aa25f" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="97e77c10-90a0-40a5-9562-d03eb71d46f3" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="73e39d7f-b8de-419f-9b85-fde223870bde" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="5f274dcb-0953-4248-8da1-64ff92746288" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1001-01-01" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="c5b8c8de-32bf-4cf3-873d-46dc84abf9a1" eId="meta-1_lebzykl-1_ereignis-1"/>
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" GUID="e4adb88e-baf7-488a-b486-fd20baef8201" eId="meta-1_analysis-1"/>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="1605339d-a500-4f42-b53f-79237ccb0ca5" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="44b111f8-60bd-4bf8-8fa8-11d80f8398e5" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="119ba77d-84e7-4d4f-a842-70675b0b79da" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1"/>
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>
    <akn:preface GUID="dfa143c6-c4ec-4215-a3a8-d8f94ff49c04" eId="einleitung-1">
      <akn:longTitle GUID="39e024ba-8992-4049-9f0d-bdad6b13b7c9" eId="einleitung-1_doktitel-1">
        <akn:p GUID="9555a258-c3cc-4260-b338-34cad6d5119b" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="fb188f10-ebc4-4cb2-add5-aa3a354b4269" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Fiktives Beispielgesetz</akn:docTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>
    <akn:body GUID="a40479ed-ae69-40a5-ad0f-0c596948df44" eId="hauptteil-1">
      <akn:article GUID="1040f1ed-7e2d-454e-b8ed-daffc09d0c25" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="dc138b94-3bf5-4349-9126-0c67720b97ec" eId="hauptteil-1_art-1_bezeichnung-1">§ 1</akn:num>
        <akn:heading GUID="8d70d4ee-5ec7-4af5-85ae-dd5be6552d0c" eId="hauptteil-1_art-1_überschrift-1"> Anwendungsbereich von Beispielen </akn:heading>
        <akn:paragraph GUID="b76fd60a-ad8a-4dcc-a56e-cbe14c8eca26" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="8ccc077c-e49f-4c32-95e3-b7c24b2de012" eId="hauptteil-1_art-1_abs-1_bezeichnung-1">(1)</akn:num>
          <akn:content GUID="34e3d94e-f42c-4ec6-9cc5-086abd369938" eId="hauptteil-1_art-1_abs-1_inhalt-1">
            <akn:p GUID="65c0948d-1969-4817-8265-33a451d3b98d" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1">Hier steht eine Regelung zum 1. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="0ce10c95-1f26-49fc-b777-c97cdb1f21dd" eId="hauptteil-1_art-1_abs-2">
          <akn:num GUID="a1e166b3-bcee-4ca9-a450-441259b7cca5" eId="hauptteil-1_art-1_abs-2_bezeichnung-1">(2)</akn:num>
          <akn:content GUID="61a228a5-8b24-46c7-aebf-c4575a616095" eId="hauptteil-1_art-1_abs-2_inhalt-1">
            <akn:p GUID="32a7bd28-6af2-41ed-97ef-3461f9d85acb" eId="hauptteil-1_art-1_abs-2_inhalt-1_text-1">Hier steht eine Regelung zum 2. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="c6e82239-9ed6-44b3-a4da-020f50295dfd" eId="hauptteil-1_art-1_abs-3">
          <akn:num GUID="8adc8fff-34a8-4724-96db-f17f6bf01fb7" eId="hauptteil-1_art-1_abs-3_bezeichnung-1">(3)</akn:num>
          <akn:content GUID="ca487a2d-b286-4936-9422-77cd6d150b5f" eId="hauptteil-1_art-1_abs-3_inhalt-1">
            <akn:p GUID="e1e21c6e-0031-436f-a75d-fe0eee1a85aa" eId="hauptteil-1_art-1_abs-3_inhalt-1_text-1">Hier steht eine Regelung zum 3. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="7323db13-0e75-4440-9bd3-bde8d2e61bb5" eId="hauptteil-1_art-1_abs-4">
          <akn:num GUID="58738e1f-6927-4ca3-b325-c9adab855551" eId="hauptteil-1_art-1_abs-4_bezeichnung-1">(4)</akn:num>
          <akn:content GUID="5f89d391-26cc-4b05-a487-996474d4af25" eId="hauptteil-1_art-1_abs-4_inhalt-1">
            <akn:p GUID="ea270ff3-867d-4c48-b72d-09401c4e28f3" eId="hauptteil-1_art-1_abs-4_inhalt-1_text-1">Hier steht eine Regelung zum 4. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="3d9d424a-2ed8-4257-9bc8-43c6b8ce1897" eId="hauptteil-1_art-1_abs-5">
          <akn:num GUID="cf2052b9-ad14-4c91-9211-fad37b3e26ab" eId="hauptteil-1_art-1_abs-5_bezeichnung-1">(5)</akn:num>
          <akn:content GUID="fdd3a56e-345b-4207-b1f6-d0beac131e08" eId="hauptteil-1_art-1_abs-5_inhalt-1">
            <akn:p GUID="e6fefa84-b4a6-4dd8-9daa-8afe1a8c5c17" eId="hauptteil-1_art-1_abs-5_inhalt-1_text-1">Hier steht eine Regelung zum 5. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="ac410055-791c-4a46-a34e-c84084ddfda7" eId="hauptteil-1_art-1_abs-6">
          <akn:num GUID="15e20fc5-94cb-4cde-84a6-c3e4d53b3a20" eId="hauptteil-1_art-1_abs-6_bezeichnung-1">(6)</akn:num>
          <akn:content GUID="79a19a88-e245-4357-967f-f0d187c8625c" eId="hauptteil-1_art-1_abs-6_inhalt-1">
            <akn:p GUID="1c62708a-00f3-4948-96ca-3df15f932f22" eId="hauptteil-1_art-1_abs-6_inhalt-1_text-1">Hier steht eine Regelung zum 6. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="090e1f34-7f04-4010-bf27-3fa6a6e27a3b" eId="hauptteil-1_art-1_abs-7">
          <akn:num GUID="615a6f9f-4c60-4c2e-ad46-d5cc9bd54b12" eId="hauptteil-1_art-1_abs-7_bezeichnung-1">(7)</akn:num>
          <akn:content GUID="3ee8c3a7-5c1f-4b70-9afe-40ca02f56b5d" eId="hauptteil-1_art-1_abs-7_inhalt-1">
            <akn:p GUID="af8be153-b819-4e12-b79b-94708df7b909" eId="hauptteil-1_art-1_abs-7_inhalt-1_text-1">Hier steht eine Regelung zum 7. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="ea32f3fc-1372-42d2-8592-f218a5419668" eId="hauptteil-1_art-1_abs-8">
          <akn:num GUID="d6622011-e3d4-4ef2-9547-e10ba82fa175" eId="hauptteil-1_art-1_abs-8_bezeichnung-1">(8)</akn:num>
          <akn:content GUID="14913aeb-7195-4bb7-8adc-9119e8e7edd8" eId="hauptteil-1_art-1_abs-8_inhalt-1">
            <akn:p GUID="638019a9-374b-4503-9c7a-9234d1203774" eId="hauptteil-1_art-1_abs-8_inhalt-1_text-1">Hier steht eine Regelung zum 8. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="13420d8a-9ffe-4694-a737-6741fa7a024b" eId="hauptteil-1_art-1_abs-9">
          <akn:num GUID="0fff0111-f814-477b-aa56-774e33d7e7ca" eId="hauptteil-1_art-1_abs-9_bezeichnung-1">(9)</akn:num>
          <akn:content GUID="9f7556f0-1bae-4c13-a278-bc41de0c762b" eId="hauptteil-1_art-1_abs-9_inhalt-1">
            <akn:p GUID="6993155b-a9b7-4f66-a868-74541562f19f" eId="hauptteil-1_art-1_abs-9_inhalt-1_text-1">Hier steht eine Regelung zum 9. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="ab91b57e-b62e-4307-ba7b-57ebc417d5d9" eId="hauptteil-1_art-1_abs-10">
          <akn:num GUID="9f9f33b2-04ec-493a-acb1-e86980dfecec" eId="hauptteil-1_art-1_abs-10_bezeichnung-1">(10)</akn:num>
          <akn:content GUID="5fd01d58-e3bf-45a1-839a-2a653891d475" eId="hauptteil-1_art-1_abs-10_inhalt-1">
            <akn:p GUID="fa5bc2e9-c018-427c-843f-add1bb35f14b" eId="hauptteil-1_art-1_abs-10_inhalt-1_text-1">Hier steht eine Regelung zum 10. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article refersTo="geltungszeitregel" GUID="f92487f0-8dac-4b56-9538-1dbeb5062483" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="75e63538-8d99-4e6b-8ac3-9058ef79aebf" eId="hauptteil-1_art-2_bezeichnung-1"> Artikel 2</akn:num>
        <akn:heading GUID="913f14a6-00c1-40ca-b5fe-19580ef3d16a" eId="hauptteil-1_art-2_überschrift-1">Inkrafttreten</akn:heading>
        <akn:paragraph GUID="025f5b72-d2eb-48ff-bb97-cda270b700c6" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="847ece9d-bfc9-4382-a14a-e5a40b93cb00" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:content GUID="f8a07fe3-deff-4373-b78e-0cc5c3014bc7" eId="hauptteil-1_art-2_abs-1_inhalt-1">
            <akn:p GUID="3e10fde0-5ece-405e-bc09-809bca823c0c" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1"> Dieses Gesetz tritt <akn:date date="1001-01-02" refersTo="inkrafttreten-datum" GUID="839c57b8-737d-4d52-b583-b44b61c2db63" eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1_datum-1">am Tag nach der Verkündung</akn:date> in Kraft. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');
