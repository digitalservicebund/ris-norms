DELETE FROM announcements WHERE id = '30f69b20-892e-4381-9c38-53ab71edfcf6';
DELETE FROM norms WHERE guid = 'f260b43f-6218-4bd7-ac1a-f5f46d190bfb';
DELETE FROM norms WHERE guid = 'e7abd358-32cb-4fc2-8a1a-b033961f3708';
DELETE
FROM norms
WHERE guid = '3d5ac81e-6499-4005-926c-04a99410d361';

-- Amending law
INSERT INTO norms (guid, eli, xml)
VALUES ('e7abd358-32cb-4fc2-8a1a-b033961f3708', 'eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                      http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    <akn:act name="regelungstext">
        <akn:meta eId="meta-1" GUID="2481817a-0bb8-4acc-a107-f7b1ca005bd5">
            <akn:identification eId="meta-1_ident-1" GUID="0f860fc3-2320-4bdb-95fd-39cc7c74c74a" source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="a99d08e2-c8e1-454a-a744-a900d114a09f">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="f878644d-a0cd-4395-8068-5dd57a20211a" value="eli/bund/bgbl-1/1001/2/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a933d5e0-f55b-4e47-8099-dfc16e9bd246" value="eli/bund/bgbl-1/1001/2"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="e60ca8fb-0e4a-4597-864f-3d3e451baa76" name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c"></akn:FRBRalias>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="f6db6ec9-0d77-465c-b3d9-6b362fd12172" date="1001-02-01" name="verkuendungsfassung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="a9212ee8-b829-458a-8fe7-209afb86f830" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="8717ed8b-17bb-4be0-9cc1-697f323fa8e0" value="de"></akn:FRBRcountry>
                    <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="e19a8ae2-2214-48f4-94a4-7a56e0dc4972" value="2"></akn:FRBRnumber>
                    <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a4ce4461-b0c0-4072-8308-6d71f532add8" value="bgbl-1"></akn:FRBRname>
                    <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="854c6c39-5bc4-4685-bc3a-5d250f5bf4c9" value="regelungstext-1"></akn:FRBRsubtype>
                </akn:FRBRWork>
                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="3a260f57-c05b-4829-b1af-d90a2155f830">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="48ea5be1-ede2-4d25-826e-8580b20856c2" value="eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="ed14cdff-f228-45a8-8273-c69b9249e268" value="eli/bund/bgbl-1/1001/2/1001-02-01/1/deu"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="c8020f26-0573-4fbd-9206-b7e94254e5a1" name="vorherige-version-id" value="f260b43f-6218-4bd7-ac1a-f5f46d190bfb"></akn:FRBRalias>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="3525add7-7254-4e86-b42c-09e21dda2337" name="aktuelle-version-id" value="e7abd358-32cb-4fc2-8a1a-b033961f3708"></akn:FRBRalias>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="d9e42e16-093c-4f59-9ab1-8c22a88b0145" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="eb396949-e619-4d8c-a1d8-437a54d12e0f" date="1001-02-01" name="verkuendung"></akn:FRBRdate>
                    <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="98969700-0dd6-4ecf-9586-a57b78fec36c" language="deu"></akn:FRBRlanguage>
                    <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="77c781e0-c3d1-4353-84db-8ae9592259bf" value="1"></akn:FRBRversionNumber>
                </akn:FRBRExpression>
                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="98aa282b-b68b-438f-b12e-19a5d0504a62">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d7e17b04-d21a-49a1-bd17-8dbc6e438ab1" value="eli/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1.xml"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="73b70a33-ba32-4ed8-acad-31588cb67270" value="eli/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1.xml"></akn:FRBRuri>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="1b64cc67-d985-452a-ba9c-76f132474003" date="1001-02-01" name="generierung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="4dcee985-9163-4f95-8587-e4531da25e6b" href="recht.bund.de"></akn:FRBRauthor>
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="5012d395-a7e4-4f81-9ed5-3f2d3f238772" value="xml"></akn:FRBRformat>
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="cb34c0a4-cc3b-4aa8-ad37-6d3c1ad62034" eId="meta-1_lebzykl-1">
                <akn:eventRef date="1001-02-01" source="attributsemantik-noch-undefiniert" refersTo="ausfertigung" type="generation" eId="meta-1_lebzykl-1_ereignis-1" GUID="82e43683-5f23-40cb-a10a-63717e935fff"></akn:eventRef>
                <akn:eventRef date="1001-03-01" source="attributsemantik-noch-undefiniert" refersTo="inkrafttreten" type="generation" eId="meta-1_lebzykl-1_ereignis-2" GUID="052e1cf2-9b4f-44de-8270-0817e5cc6bd6"></akn:eventRef>
            </akn:lifecycle>
            <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1" GUID="72dea665-8b66-44c3-bba1-6843491a19df">
                <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="fa86151f-b89f-4ce8-981f-919ddfb496a8">
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1" GUID="9b71b8e2-83d8-436f-9d57-0968b0cd4231" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" GUID="ca72970e-2b7a-4b8a-9145-9ea9633c2935" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" GUID="4fe75978-b19d-4b5c-9116-f0570be39741" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="493eb720-ae47-4db6-a2b8-9700f61a0860" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="fbf0225d-37c3-4603-b851-627ba9f90f30" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="828bede0-5d47-4f38-812a-5d37bdd92d82" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="66594ba2-0d9a-4680-b1a2-48a134ba013e" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-2_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="6c653919-35ad-47fc-ae34-ce2b175ea298" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-3" GUID="02165fe9-81f8-4306-a9f5-777a46c0c351" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-3_source-1" GUID="89b038cc-f255-4cc5-8f22-0bb0a0e5b998" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-3_destination-1" GUID="98b8c5de-9cfa-4cf6-a3ee-96b6d15331b9" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-3_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-3_gelzeitnachw-1" GUID="70fdf551-ceb7-4678-be64-3e3a66d89a18" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-4" GUID="b971343a-e55e-4022-8a5e-843c028598ad" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-4_source-1" GUID="c68e47d1-ef47-4cf8-86f0-2307d4463b82" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-4_destination-1" GUID="338edcbf-f159-46b3-a2d6-f030e56db0dc" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-4_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-4_gelzeitnachw-1" GUID="1450ed7e-7582-45e1-b87d-415996a6708a" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-5" GUID="f4f28141-ef8c-4f03-a00f-c42f270a7d65" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-5_source-1" GUID="b8a91795-278e-45c9-bfd9-05c2bc601dce" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-5_destination-1" GUID="15d145bc-6276-4ff6-8038-1720d2057856" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-5_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-5_gelzeitnachw-1" GUID="481379f7-57f8-4fbc-9e40-e682bb3b3620" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-6" GUID="7dedbf83-0a23-4d6e-9775-7490dd17030a" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-6_source-1" GUID="37f758a4-edf7-482e-a60b-42ca741a646a" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-6_destination-1" GUID="61b33a97-5a4a-42bd-8327-2544ab4f2eb5" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-6_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-6_gelzeitnachw-1" GUID="afeb1c64-cb4b-4ff4-923c-f84bfd9c61b3" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-7" GUID="26c21156-f877-40bc-88b2-22c92819e694" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-7_source-1" GUID="20b75521-c023-4769-b759-f04e9eca4b8b" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-7_destination-1" GUID="d176c546-27c7-4ee8-a244-67b4b11bc624" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-7_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-7_gelzeitnachw-1" GUID="f36eec63-c115-477f-b47c-2ccd38311596" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-8" GUID="41e5b919-8db8-4a3b-8521-baf0882ec430" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-8_source-1" GUID="f659a5a6-97fb-4855-8a72-34da38c8942a" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-8_destination-1" GUID="0447cf32-ab6c-42ac-b30d-47f9f79555c3" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-8_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-8_gelzeitnachw-1" GUID="cb945cd1-1521-4a37-80d7-ef5e3ac91669" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-9" GUID="cb8aae73-3bcf-4abc-99c4-cd19b587b439" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-9_source-1" GUID="dbbbefb3-c857-4024-a03a-264515105be1" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-9_destination-1" GUID="d934e0e5-6ec7-4c84-a911-2923b4a73339" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-9_inhalt-1_text-1/29-36.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-9_gelzeitnachw-1" GUID="7c76d439-ea69-48aa-b927-a56a8995608b" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-10" GUID="2b00350d-6b68-4a4a-9761-edbe89c0a15f" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-10_source-1" GUID="fbfdf6c4-ee9c-41cc-9cc4-a3a11bd18d3c" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1_ändbefehl-1"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-10_destination-1" GUID="9af11846-3f34-4adb-8e94-d542cd31a735" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-10_inhalt-1_text-1/29-37.xml"></akn:destination>
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-10_gelzeitnachw-1" GUID="cd00feef-9296-4698-9421-f4d2d3ba0d42" period="#meta-1_geltzeiten-1_geltungszeitgr-1"></akn:force>
                    </akn:textualMod>
                </akn:activeModifications>
            </akn:analysis>
            <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="bab788c9-65b2-43f8-9c70-6223806966d7" eId="meta-1_geltzeiten-1">
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="8ceeab6a-175d-42ec-b1a5-f2e90e626939">
                    <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="b5f2b610-ea4e-43dc-9262-8afe1a812145"></akn:timeInterval>
                </akn:temporalGroup>
            </akn:temporalData>
            <akn:proprietary eId="meta-1_proprietary-1" GUID="fe419055-3201-41b1-b096-402eabcbe6a1" source="attributsemantik-noch-undefiniert">
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                    <meta:typ>gesetz</meta:typ>
                    <meta:form>mantelform</meta:form>
                    <meta:fassung>verkuendungsfassung</meta:fassung>
                    <meta:art>regelungstext</meta:art>
                    <meta:initiant>bundesregierung</meta:initiant>
                    <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
                    <meta:fna>nicht-vorhanden</meta:fna>
                </meta:legalDocML.de_metadaten>
            </akn:proprietary>
        </akn:meta>

        <akn:preface eId="einleitung-1" GUID="5d844674-c11c-45bf-9bb8-4d54fe108c66">
            <akn:longTitle eId="einleitung-1_doktitel-1" GUID="3d58e9de-af6a-42ab-b038-82471f592194">
                <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="306cb459-2f68-4a8d-9faf-9ef782e210fc">
                    <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="d7bc7faa-6219-4d40-a9f8-bfba2c880544">Gesetz zur Änderung des Beispielgesetzes</akn:docTitle>
                </akn:p>
            </akn:longTitle>
            <akn:block eId="einleitung-1_block-1" GUID="b4e33ab5-76e4-4ad6-bf3a-75801880174a" name="attributsemantik-noch-undefiniert">
                <akn:date eId="einleitung-1_block-1_datum-1" GUID="9cb899ea-35f5-45ae-95b6-4fe8090cba07" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.02.1001</akn:date>
            </akn:block>
        </akn:preface>

        <akn:preamble eId="preambel-1" GUID="24c95676-f0c1-4a63-aa7f-122a1994e4a5">
            <akn:formula eId="preambel-1_formel-1" GUID="65d5c349-3e37-4552-8bbb-8c0f184e3ea4" refersTo="eingangsformel" name="attributsemantik-noch-undefiniert">
                <akn:p eId="preambel-1_formel-1_text-1" GUID="ea9e8d66-5e35-4287-baac-b03ed88a5afc"> Der <akn:organization eId="preambel-1_formel-1_text-1_org-1" GUID="75b540f6-10bb-4c62-88f8-2884220b77b2" refersTo="attributsemantik-noch-undefiniert" title="Bundestag">Bundestag</akn:organization> hat das
                    folgende Gesetz beschlossen:</akn:p>
            </akn:formula>
        </akn:preamble>

        <akn:body eId="hauptteil-1" GUID="7053de15-c6f1-4a0e-888f-abe626a12306">
            <akn:article eId="hauptteil-1_art-1" GUID="6654b284-d298-4bde-af54-e237551c8ebe" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="078ca3ce-a2be-4237-b012-2f5d83401d09">
                    <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="11766493-ae2b-4ea6-9845-3f6fca5c3cd8" name="1"></akn:marker></akn:num>
                <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="62620100-65c6-4bed-a49c-fb0a60414f80">Artikel 1</akn:heading>
                <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="c80e5e74-a3b1-4c98-a668-95ab790c6b07">
                    <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="e7fd99c2-3965-482b-a8ce-1eec51983bca">
                        <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="326a02c2-43e1-4552-8d0a-8ff0797fc832" name="1"></akn:marker>
                    </akn:num>
                    <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="1f152276-7132-44a7-8835-381fcb042827">
                        <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="2ec2c333-f252-4b1f-b9ce-61371e8b4337">
                            <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="d4ce4527-3d4d-4f86-b911-ec20b54dcd23"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="873bf566-fe0c-4a39-9d25-7d90e281837b"
                                                                                                                                                                  href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1">Beispielgesetz vom 1. Januar 1001 (BGBl. I S. 10)</akn:affectedDocument> wird wie folgt geändert:</akn:p>
                        </akn:intro>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1" GUID="07514672-034e-4d17-bfe1-d7fdc6b1b52a">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1" GUID="92bc9cfc-fef7-4599-aaa7-c391085b3ec0">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" GUID="e7b01902-84a6-42e1-861c-251ffec75327" name="1"></akn:marker>1.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1" GUID="05bedf71-934f-4925-9046-c7c44be11778">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1" GUID="64c3a0f5-b7b9-43c3-8c76-02753ca6a84c">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" GUID="90d43fa8-59db-41da-a4bb-06df093f5323" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="514f37b3-5f75-4ee4-a110-6bad8c5a46c3" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_inhalt-1_text-1/29-36.xml">§ 1 Absatz 1 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="c8d35a88-9471-46c9-86fa-f201fddda9e4" startQuote="„" endQuote="“">1. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="c43a085c-536b-457c-b74f-3af9b137b62b" startQuote="„" endQuote="“">1. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2" GUID="231446e8-cd2d-4c2e-9d04-f3df902be2dd">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1" GUID="024bd384-3bbb-40eb-9611-70c38397e1ee">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="869c5374-bdae-4153-9d48-f014eb2db80d" name="2"></akn:marker>2.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1" GUID="daae2886-c9e5-4068-a237-2a14a1d5907a">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="c38b4914-2e0a-456e-ac2a-bdd3415ca3fc">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="d9607550-e423-4481-9f3f-ce82c48563aa" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="92ed9e1b-32e1-4e12-b3f9-6e65edc70242" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-2_inhalt-1_text-1/29-36.xml">§ 1 Absatz 2 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="a500e095-4d6b-41c6-8930-ae4e87e53d87" startQuote="„" endQuote="“">2. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="bb592cca-f4b4-414a-ae70-474871d00775" startQuote="„" endQuote="“">2. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3" GUID="9f48626c-4679-4449-9855-5a8ae47cf844">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_bezeichnung-1" GUID="b2e67d53-a134-402d-a4d1-51b8aff8323c">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" GUID="78b1c6d9-1446-4c92-a59e-bf6a495b5280" name="3"></akn:marker>3.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1" GUID="80243272-1015-4ec4-a1c9-e1c7676d6d25">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1" GUID="995f12fc-74e6-4f47-98f3-680775930dbd">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1" GUID="850b2615-23bd-41ae-b8b8-a4fb1768ec75" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="7f7099ea-20f2-43e6-a226-c71012e278e6" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-3_inhalt-1_text-1/29-36.xml">§ 1 Absatz 3 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="393d336d-c81f-431c-9f41-86a539f00431" startQuote="„" endQuote="“">3. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="72628c63-031b-49bd-8701-605b0693b6b5" startQuote="„" endQuote="“">3. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4" GUID="bba7bf88-16c8-4ed1-be35-e3b0d20ce897">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_bezeichnung-1" GUID="3ff2e28e-e4a3-4e74-9849-1567999a51ec">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_bezeichnung-1_zaehlbez-1" GUID="1207841b-e72e-4e03-959a-ca6d85ec4264" name="4"></akn:marker>4.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1" GUID="47a13ccc-a885-4d80-a414-43f8e80b6a1f">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1" GUID="1edc9c2a-e0f6-4635-89da-f5eecfc2bbc0">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1" GUID="f4987ca1-3e43-40e6-8f60-73871c9ea995" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="0d368345-c11a-4c1c-a418-13e8918a98ad" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-4_inhalt-1_text-1/29-36.xml">§ 1 Absatz 4 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="17b96ffc-11bd-4e13-a075-b402125bd3a2" startQuote="„" endQuote="“">4. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="573f48e7-614b-420d-b358-82ac9de2625a" startQuote="„" endQuote="“">4. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5" GUID="d8ceda4a-4df1-490e-a6e8-734c550b69c3">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_bezeichnung-1" GUID="208f8c69-69e9-4661-8228-56a91ab7bbbb">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_bezeichnung-1_zaehlbez-1" GUID="68c3cf46-b1d2-4bac-9ad7-9c18239b13fc" name="5"></akn:marker>5.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1" GUID="158b878d-f5d4-4f3b-b2eb-9289988eb924">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1" GUID="1e00bde0-4c8d-45b9-96af-7c7aceaaa011">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1_ändbefehl-1" GUID="012cdd97-b40d-4e71-8236-7ea39693ffea" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="1829154b-6828-4ea6-bda9-aff89f42f7ec" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-5_inhalt-1_text-1/29-36.xml">§ 1 Absatz 5 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="4f7d3011-e16b-4995-8225-57266ea3073f" startQuote="„" endQuote="“">5. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="6b8cdccb-a349-4961-8bb7-fcb6ecec2821" startQuote="„" endQuote="“">5. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6" GUID="ea9f53e5-0fe4-4ca0-8281-415087bec0bd">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_bezeichnung-1" GUID="4360e720-79cd-4155-b833-ee2c0e2d372e">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_bezeichnung-1_zaehlbez-1" GUID="f0b52058-cba7-4a65-aee1-26caa3f1c504" name="6"></akn:marker>6.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1" GUID="3d4132a4-b2c7-4056-a2d6-c6ca14970078">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1" GUID="d75a698a-6489-40f9-bc6a-0dd4346c21af">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1_ändbefehl-1" GUID="e65aa3f2-c616-49db-bc80-9553cde2849d" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="013b4926-cca2-4a4f-b9c5-1bcbdc6d1111" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-6_inhalt-1_text-1/29-36.xml">§ 1 Absatz 6 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="1e5665a1-d10a-42f8-b607-4c424c2fcae8" startQuote="„" endQuote="“">6. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="cb88922c-cfd7-47b0-a0b4-d192612526f5" startQuote="„" endQuote="“">6. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7" GUID="7234b5b2-1c53-4567-a239-cbf5f68effec">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_bezeichnung-1" GUID="2122179b-5b4c-460e-a979-38373dc4128e">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_bezeichnung-1_zaehlbez-1" GUID="5d14cdbe-8a70-4a8e-a3f9-199eee778c6e" name="7"></akn:marker>7.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1" GUID="2b6cac76-075a-439a-9a19-53590f72f299">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1" GUID="6e8dd529-65ea-4736-a733-ad91ab7a671d">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1_ändbefehl-1" GUID="f937ee3d-0eb3-45e6-a8b4-00ba5a2c23a6" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="2084c585-3b6b-4963-aa4b-b2fe5beedc9d" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-7_inhalt-1_text-1/29-36.xml">§ 1 Absatz 7 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="f2cd277a-5504-40cb-8561-857bbfdc7a95" startQuote="„" endQuote="“">7. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-7_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="0f27541d-574f-4883-a389-b5a02abf8e65" startQuote="„" endQuote="“">7. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8" GUID="f263a245-d60b-41b9-b357-557f202b61e3">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_bezeichnung-1" GUID="0055fef2-f8c8-4eac-afab-644813d46998">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_bezeichnung-1_zaehlbez-1" GUID="d5626c6a-9f3e-48a1-85b5-0756f0e2c608" name="8"></akn:marker>8.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1" GUID="149eeaa4-bc48-43b6-924b-7bb43d34338c">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1" GUID="1c097541-9838-4f7a-a075-3df1c9398383">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1" GUID="a019ce3b-91bf-4790-aeb7-d793de707205" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="ce16fea0-b41a-4ef3-82cf-97b5a0d318f5" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-8_inhalt-1_text-1/29-36.xml">§ 1 Absatz 8 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="4fbbe90c-22f6-4443-b8fc-8394a8264cd6" startQuote="„" endQuote="“">8. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="7b744694-aaed-4891-96b0-3a01f021f8d4" startQuote="„" endQuote="“">8. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9" GUID="b0cb5696-7ff5-4907-9833-391758be316d">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_bezeichnung-1" GUID="b4083dd9-6091-4832-9253-5962d8d59903">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_bezeichnung-1_zaehlbez-1" GUID="361dacc4-d0b8-4a63-b20e-34960b2fc9e6" name="9"></akn:marker>9.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1" GUID="21605ae8-8204-4a14-a609-50dac255e89d">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1" GUID="4b3a74e4-96f4-4746-9e22-e2708ec2d3d2">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1" GUID="2e6e38c7-fa1b-4565-ae86-96541438d492" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                            GUID="05213bd2-9906-429d-92c2-acdbbbb0f11a" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-9_inhalt-1_text-1/29-36.xml">§ 1 Absatz 9 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="2847e61a-d047-4bc8-8b2e-fe038fdf2e42" startQuote="„" endQuote="“">9. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="7588921a-276d-4ec5-b120-1a7332aa8c87" startQuote="„" endQuote="“">9. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10" GUID="1fd7287a-69a5-4490-bd89-934f80cf9cf4">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_bezeichnung-1" GUID="51768445-f9bd-4a88-9362-485c99adb535">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_bezeichnung-1_zaehlbez-1" GUID="cc0e03dd-782c-482e-8ec1-ac5de52728a8" name="10"></akn:marker>10.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1" GUID="664dd536-49f2-4560-9437-33b18b0256ae">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1" GUID="e404a24a-319b-433f-9e34-aab4424fc13e">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1_ändbefehl-1" GUID="1f473077-c03f-4abd-83d0-b539c02b27f1" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                             GUID="2fde90e8-0036-4df0-b50e-1e4186f657cf" href="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1/hauptteil-1_art-1_abs-10_inhalt-1_text-1/29-37.xml">§ 1 Absatz 10 Satz 1</akn:ref> werden die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="cc60d52f-3ea5-4d35-9cac-a364aae6821d" startQuote="„" endQuote="“">10. Fall</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-10_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="047a65f0-8c7c-44a8-8d95-26fbe20901f1" startQuote="„" endQuote="“">10. Beispiel</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>

            <akn:article eId="hauptteil-1_art-2" GUID="db1c822f-5626-41ae-afc0-6b3249915504" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                <akn:num eId="hauptteil-1_art-2_bezeichnung-1" GUID="b78a76fc-5760-427b-aa31-fdab68178d18">
                    <akn:marker eId="hauptteil-1_art-2_bezeichnung-1_zaehlbez-1" GUID="af366809-ad76-463a-81e8-ac3b9a15113d" name="3"></akn:marker>Artikel 2</akn:num>
                <akn:heading eId="hauptteil-1_art-2_überschrift-1" GUID="085ab201-8f3f-45b1-89fd-1667187d521a">Inkrafttreten</akn:heading>
                <akn:paragraph eId="hauptteil-1_art-2_abs-1" GUID="9a26af8c-128d-4d39-91c6-180c7d62d24a">
                    <akn:num eId="hauptteil-1_art-2_abs-1_bezeichnung-1" GUID="71de5c66-3092-42bc-bd0b-3b7f1fac4b2b">
                        <akn:marker eId="hauptteil-1_art-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="df020fbc-a262-4f2c-a833-c06f6a5aadc3" name="1"></akn:marker>
                    </akn:num>
                    <akn:content eId="hauptteil-1_art-2_abs-1_inhalt-1" GUID="d45e1330-98dd-47a9-9fd8-140c791fe11f">
                        <akn:p eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1" GUID="17bcf744-c7d1-48a8-bd64-456b683892f8"> Dieses Gesetz tritt am <akn:date eId="hauptteil-1_art-2_abs-1_inhalt-1_text-1_datum-1" GUID="0a9f074a-a72c-45b9-ae11-99ca687dd52c" date="1001-03-01" refersTo="inkrafttreten-datum">
                            01.03.1001</akn:date> in Kraft. </akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
        </akn:body>
    </akn:act>
</akn:akomaNtoso>
');

-- Target law
INSERT INTO norms (guid, eli, xml)
VALUES ('f260b43f-6218-4bd7-ac1a-f5f46d190bfb', 'eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                      http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta eId="meta-1" GUID="f8ec5da9-48c6-495b-a7d2-f6dc12a743ae">
      <akn:identification eId="meta-1_ident-1" GUID="8bb935bf-0ebd-4a1c-a209-e00b3bd9518c" source="attributsemantik-noch-undefiniert">
        <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="5a7925ba-3d40-4fe5-8ee0-d42e7f11dee6">
          <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="207c039b-0601-42b4-896f-6ead92bc4e7a" value="eli/bund/bgbl-1/1001/1/regelungstext-1"></akn:FRBRthis>
          <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="cdff61ec-cf3c-4d1f-9511-9f3a532b29a8" value="eli/bund/bgbl-1/1001/1"></akn:FRBRuri>
          <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="77b0ad71-d11e-4a4a-9d52-d7d6779a82c0" name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c"></akn:FRBRalias>
          <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="7199d1d6-9715-47a0-93d3-c3a8cdbc65d0" date="1001-01-01" name="verkuendungsfassung"></akn:FRBRdate>
          <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="48d164a9-c933-4b82-abdb-ad67003b6460" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
          <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="e6cfbc75-63a6-4a60-8285-97d4c3d53621" value="de"></akn:FRBRcountry>
          <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="ccd134a1-c64f-45fc-9a16-9b62e0e33afd" value="1"></akn:FRBRnumber>
          <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="49b03295-ff7c-4219-8f2f-489ac1ef744e" value="bgbl-1"></akn:FRBRname>
          <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="480a140a-7a1a-4264-b0b6-e2a9dba88163" value="regelungstext-1"></akn:FRBRsubtype>
        </akn:FRBRWork>
        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="1ee2c30e-66fa-4c5d-ada6-9df44cb8b2de">
          <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="b0d55769-88c4-4624-ad42-613c08ed41c1" value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1"></akn:FRBRthis>
          <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="6340f500-b55e-4f57-ac06-ef605bba4d09" value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu"></akn:FRBRuri>
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="5f41ecba-3ee1-4a34-8f05-1260529f0a34" name="aktuelle-version-id" value="f260b43f-6218-4bd7-ac1a-f5f46d190bfb"></akn:FRBRalias>
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="4c58dd0f-4362-46df-ac5b-66820c0faf1e" name="nachfolgende-version-id" value="3d5ac81e-6499-4005-926c-04a99410d361"></akn:FRBRalias>
          <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="965a8fcc-591f-44e2-b0b0-f0becdea95ff" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
          <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="07e23128-9b2c-4c7d-ae1e-0080b0ad8f88" date="1001-01-01" name="verkuendung"></akn:FRBRdate>
          <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="1afc4148-8434-4696-87bf-e0dd9ab6551b" language="deu"></akn:FRBRlanguage>
          <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="895ed32c-bac4-4101-a898-3df54fac54c4" value="1"></akn:FRBRversionNumber>
        </akn:FRBRExpression>
        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="7ee1fffa-b7be-4b43-8b71-4e384ca83376">
          <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d12a389d-6fce-4f6a-99b2-b98a5f2295b6" value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1.xml"></akn:FRBRthis>
          <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="2b36729b-4c33-4427-9396-1d0ed9a3af48" value="eli/bund/bgbl-1/1001/1/1001-01-01/1/deu/regelungstext-1.xml"></akn:FRBRuri>
          <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="4bb717ae-8d7f-4655-9aa4-1a4315b10229" date="1001-01-01" name="generierung"></akn:FRBRdate>
          <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="d4ef8a82-998b-4e5d-a9b0-151e911276be" href="recht.bund.de"></akn:FRBRauthor>
          <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="afa63058-68b8-4d77-a2c2-e338f20eb652" value="xml"></akn:FRBRformat>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="7d0cca7c-997b-4124-b627-b471398212e4" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1001-01-01" source="attributsemantik-noch-undefiniert" refersTo="ausfertigung" type="generation" eId="meta-1_lebzykl-1_ereignis-1" GUID="d4821011-a051-43fc-8356-2874509781fa"></akn:eventRef>
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1" GUID="d6bf2953-40a2-4e53-847d-9009c7362503"></akn:analysis>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="2403f879-25e1-4736-addc-5020535cacb4" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="a31d6fec-4a66-4835-ae1a-fed12fdd92bb">
          <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-1" refersTo="geltungszeit" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="f11ec4d9-a419-4288-b47c-0b0341a3cf74"></akn:timeInterval>
        </akn:temporalGroup>
      </akn:temporalData>
      <akn:proprietary eId="meta-1_proprietary-1" GUID="fe419055-3201-41b1-b096-402eabcbe6a1" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
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
      <akn:longTitle eId="einleitung-1_doktitel-1" GUID="add85c05-3518-4bd0-b05f-8a09edb7c43d">
        <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="f98eb7af-6156-4b99-92b2-c668195de345">
          <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="47622498-565f-44e7-96b4-52d5f0b8d2b2">Fiktives Beispielgesetz</akn:docTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>

    <akn:body eId="hauptteil-1" GUID="2e4a7f34-0b58-4ffc-a8db-5f933ba1c267">
      <akn:article eId="hauptteil-1_para-1" GUID="e7f236c8-e1a7-4a62-959e-d39f77fd39b9" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="6a5d55fa-176f-4f46-9141-b6bef0d791b9">
          <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="4c4d371b-b20b-4a3a-91f8-41964adb6ed0" name="1"></akn:marker> § 1 </akn:num>
        <akn:heading eId="hauptteil-1_para-1_überschrift-1" GUID="1969ccba-12d4-4825-810b-2350b629b8ba"> Anwendungsbereich von Beispielen </akn:heading>

        <akn:paragraph eId="hauptteil-1_para-1_abs-1" GUID="d79db39b-609a-49cc-968e-95ddfd4bf984">
          <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="a38ae4be-8b33-44f2-9a53-11020f433b20">
            <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="01ad1282-cd9e-43c6-82bc-974a328f1738" name="1"></akn:marker>(1) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-1_inhalt-1" GUID="90ca46bd-1408-4772-a0d4-3490a29847e0">
            <akn:p eId="hauptteil-1_para-1_abs-1_inhalt-1_text-1" GUID="cd9f08c0-347f-4abb-8b87-2f00011565a2">Hier steht eine Regelung zum 1. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-2" GUID="39814158-6eb1-40ba-84ce-f07fd970dd67">
          <akn:num eId="hauptteil-1_para-1_abs-2_bezeichnung-1" GUID="fbb68565-7cf4-40b1-aa17-9059f43285b9">
            <akn:marker eId="hauptteil-1_para-1_abs-2_bezeichnung-1_zaehlbez-1" GUID="797826c4-511b-43bd-958a-6d98c96b8f89" name="2"></akn:marker>(2) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-2_inhalt-1" GUID="4afc73b3-9db9-406a-afe7-c51232112402">
            <akn:p eId="hauptteil-1_para-1_abs-2_inhalt-1_text-1" GUID="7be47602-89d2-4585-b177-e705434e46a7">Hier steht eine Regelung zum 2. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-3" GUID="a31a9e7c-63c2-406b-a25a-fec44cae63ea">
          <akn:num eId="hauptteil-1_para-1_abs-3_bezeichnung-1" GUID="c4b97719-80b8-40d6-ab48-573e07e12f2f">
            <akn:marker eId="hauptteil-1_para-1_abs-3_bezeichnung-1_zaehlbez-1" GUID="e14cfdf1-5038-465b-94e6-6075b71b40ec" name="3"></akn:marker>(3) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-3_inhalt-1" GUID="b9f1140e-d9e9-46d1-8462-18c7fb7aa729">
            <akn:p eId="hauptteil-1_para-1_abs-3_inhalt-1_text-1" GUID="01df7717-080d-4db1-ace3-e0a5a7254e79">Hier steht eine Regelung zum 3. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-4" GUID="41215469-4cc5-41b4-a2b5-83ca227739d8">
          <akn:num eId="hauptteil-1_para-1_abs-4_bezeichnung-1" GUID="6ba7aae4-aa4b-4585-9b78-1d99eab2b76f">
            <akn:marker eId="hauptteil-1_para-1_abs-4_bezeichnung-1_zaehlbez-1" GUID="49077804-742e-4671-9f61-03678c90d727" name="4"></akn:marker>(4) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-4_inhalt-1" GUID="7283d4af-b8fd-47e6-8381-68a310ce121c">
            <akn:p eId="hauptteil-1_para-1_abs-4_inhalt-1_text-1" GUID="5e187ae3-9434-4fd9-a32b-36d73cf08abe">Hier steht eine Regelung zum 4. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-5" GUID="1b13f48b-078f-4c49-9902-b403f0086c36">
          <akn:num eId="hauptteil-1_para-1_abs-5_bezeichnung-1" GUID="ed95939e-9883-49c4-8155-f63a4ef95e16">
            <akn:marker eId="hauptteil-1_para-1_abs-5_bezeichnung-1_zaehlbez-1" GUID="1d349201-2639-410d-a3bf-67003fbe5abd" name="5"></akn:marker>(5) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-5_inhalt-1" GUID="4693f7d5-f8a8-4857-8880-8c483b901ad9">
            <akn:p eId="hauptteil-1_para-1_abs-5_inhalt-1_text-1" GUID="9eb5da95-8a9d-4359-9e8a-dd55c8aa6eb2">Hier steht eine Regelung zum 5. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-6" GUID="60af902f-b5e1-4723-80ea-cbea0f93a3b8">
          <akn:num eId="hauptteil-1_para-1_abs-6_bezeichnung-1" GUID="142ec15b-f23a-4edc-ad0e-ca63964fc901">
            <akn:marker eId="hauptteil-1_para-1_abs-6_bezeichnung-1_zaehlbez-1" GUID="f46288b8-5936-4eaf-8010-c07bc2c76553" name="6"></akn:marker>(6) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-6_inhalt-1" GUID="7f2652e3-3cda-46f8-8a44-23764d743002">
            <akn:p eId="hauptteil-1_para-1_abs-6_inhalt-1_text-1" GUID="48608e6f-7168-4c71-8a18-6dae882b68a4">Hier steht eine Regelung zum 6. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-7" GUID="71733c36-3f00-444c-9ee3-609e1495436d">
          <akn:num eId="hauptteil-1_para-1_abs-7_bezeichnung-1" GUID="3e79e30d-e7d4-40fa-9b09-1d937ad10fb6">
            <akn:marker eId="hauptteil-1_para-1_abs-7_bezeichnung-1_zaehlbez-1" GUID="edf6b06e-b099-48e7-b916-9abd506da0a1" name="7"></akn:marker>(7) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-7_inhalt-1" GUID="63187c16-ffbd-4c30-be70-0cfb10bbb793">
            <akn:p eId="hauptteil-1_para-1_abs-7_inhalt-1_text-1" GUID="239e9552-eda5-4e9e-9ddf-535d59f5129d">Hier steht eine Regelung zum 7. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-8" GUID="70b66fd6-573c-4e42-b653-6f99a2970ed4">
          <akn:num eId="hauptteil-1_para-1_abs-8_bezeichnung-1" GUID="ef9e55fd-1288-47c3-95c7-0bf1b0b2423f">
            <akn:marker eId="hauptteil-1_para-1_abs-8_bezeichnung-1_zaehlbez-1" GUID="8ef41886-a9ce-460d-91ee-7fa28d4a51e7" name="8"></akn:marker>(8) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-8_inhalt-1" GUID="4a2c19ac-91fe-4e6d-aa5b-c4df2f902305">
            <akn:p eId="hauptteil-1_para-1_abs-8_inhalt-1_text-1" GUID="ab6b6400-a2dd-41ac-9c90-502a48bb6f9f">Hier steht eine Regelung zum 8. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-9" GUID="2644e506-3876-49ab-9d33-4c9afb741a86">
          <akn:num eId="hauptteil-1_para-1_abs-9_bezeichnung-1" GUID="f49a4c52-0b38-49ef-b3ce-e80a9a646a9e">
            <akn:marker eId="hauptteil-1_para-1_abs-9_bezeichnung-1_zaehlbez-1" GUID="994b74b7-3d16-4698-a5f6-ececbe0d3663" name="9"></akn:marker>(9) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-9_inhalt-1" GUID="f95d273e-6309-4756-973b-e1facbe872b6">
            <akn:p eId="hauptteil-1_para-1_abs-9_inhalt-1_text-1" GUID="593a4c93-fa52-4438-bcc9-a03b14caeafd">Hier steht eine Regelung zum 9. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-1_abs-10" GUID="35d45ed5-73cd-4df5-a9e0-43179c44ed1e">
          <akn:num eId="hauptteil-1_para-1_abs-10_bezeichnung-1" GUID="0fa24f06-469e-4f15-83df-60889b632c08">
            <akn:marker eId="hauptteil-1_para-1_abs-10_bezeichnung-1_zaehlbez-1" GUID="77dd20b8-77e0-44e8-9d98-f3ad2ec342f3" name="10"></akn:marker>(10) </akn:num>
          <akn:content eId="hauptteil-1_para-1_abs-10_inhalt-1" GUID="768223b2-2f75-4833-8119-188918698417">
            <akn:p eId="hauptteil-1_para-1_abs-10_inhalt-1_text-1" GUID="f88f9550-6a8b-4556-ab4a-e197a55b1560">Hier steht eine Regelung zum 10. Fall die es stehts zu beachten gilt. Die Gebühren, die daraus, sind nicht erstattungsfähig.</akn:p>
          </akn:content>
        </akn:paragraph>

      </akn:article>

      <akn:article eId="hauptteil-1_para-2" GUID="350e50ca-3938-43ea-807e-cc023ced19fa" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
        <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="d2fcc3a9-1196-477d-8508-83cff3c167ba">
          <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="c62c3cad-a20f-4efb-b80f-2b188b76dfc9" name="3"></akn:marker>Artikel 2</akn:num>
        <akn:heading eId="hauptteil-1_para-2_überschrift-1" GUID="93b0e7e3-36e2-46d8-893e-d24b2001197b">Inkrafttreten</akn:heading>
        <akn:paragraph eId="hauptteil-1_para-2_abs-1" GUID="8fc0669c-7480-4dde-bebc-d5d83704e822">
          <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="bcb266d9-7d6d-4c54-b36c-3483640ab7aa">
            <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="b3a6c363-c5e0-401c-86d5-f31bc298cf88" name="1"></akn:marker>
          </akn:num>
          <akn:content eId="hauptteil-1_para-2_abs-1_inhalt-1" GUID="5b03cb2e-7335-44f2-94b5-7bb05859078e">
            <akn:p eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1" GUID="36690fc7-bd2b-4450-acaa-8e4110e39335"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1_datum-1" GUID="b7a170d0-b5fa-4864-80fd-4864324479fb" date="1001-01-02" refersTo="inkrafttreten-datum">am Tag nach
              der Verkündung</akn:date> in Kraft. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('30f69b20-892e-4381-9c38-53ab71edfcf6', 'eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1', NULL);

-- Re-seeding
