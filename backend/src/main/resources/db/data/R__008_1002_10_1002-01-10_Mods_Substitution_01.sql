DELETE FROM announcements WHERE id = 'c7fa8ba0-34c9-43ed-87ad-9bdf177126fc';
DELETE FROM norms WHERE guid = '63ef9358-8755-46e4-bf6a-21f379014597';
DELETE FROM norms WHERE guid = 'c4166ebb-b6df-4f61-8ac1-1d6399cc80ef';
DELETE FROM norms WHERE guid = 'e4e034dd-61b9-43aa-b4f9-b778dc6adfda';

-- Amending law
INSERT INTO norms (guid, eli, xml)
VALUES ('c4166ebb-b6df-4f61-8ac1-1d6399cc80ef', 'eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                                                                                                                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="c405932a-632b-4516-8e75-69fb2490e1f9" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="d626ed30-bb7e-41bf-baf5-73f77b30a26a" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="0606429d-9370-4ece-93ae-2e9090884214" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/10/regelungstext-1" GUID="058bf926-fec3-4669-89fa-9dc80cf5d0b4" eId="meta-1_ident-1_frbrwork-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/10" GUID="e0968637-081c-445a-a78f-7e2fca69cf6e" eId="meta-1_ident-1_frbrwork-1_frbruri-1" />
          <akn:FRBRalias name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" GUID="67751521-631d-420e-aaa1-35592af1ed1b" eId="meta-1_ident-1_frbrwork-1_frbralias-1" />
          <akn:FRBRdate name="verkuendungsfassung" date="1002-01-10" GUID="613143bd-ec3d-4c59-a5a7-6440454cc815" eId="meta-1_ident-1_frbrwork-1_frbrdate-1" />
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="25e944de-3679-4695-9f8d-16a123f513f0" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" />
          <akn:FRBRcountry value="de" GUID="90a22901-7a6f-4ee8-8c7c-98117ca55f47" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" />
          <akn:FRBRnumber value="10" GUID="269b1a56-d2fe-4238-8e6c-f26b4bcea972" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" />
          <akn:FRBRname value="bgbl-1" GUID="34de0312-1ebe-456d-9245-f9f69a188065" eId="meta-1_ident-1_frbrwork-1_frbrname-1" />
          <akn:FRBRsubtype value="regelungstext-1" GUID="dda67c22-c486-48de-8f2c-0ab309b1e21b" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" />
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="7d3a9c5a-021d-4f6a-91d0-e800e990d28b" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1" GUID="18616da9-5143-4e6e-a46e-7c2bd4da52d3" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu" GUID="43ccf8ce-6f76-446d-ac33-608d3eabe692" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" />
          <akn:FRBRalias name="aktuelle-version-id" value="c4166ebb-b6df-4f61-8ac1-1d6399cc80ef" GUID="54a15b82-1970-4e87-9275-a1e8bed8d9c0" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" />
          <akn:FRBRalias name="nachfolgende-version-id" value="5773cb41-adfe-47ca-b684-dde0c83c7b39" GUID="87ecc00e-db8a-4934-89c9-4f9fb6837438" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" />
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="95e462c3-9dc2-4e58-97c9-42206eced13e" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" />
          <akn:FRBRdate name="verkuendung" date="1002-01-10" GUID="4a80bbfa-20bb-4ea7-b1f9-f452cd310487" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" />
          <akn:FRBRlanguage language="deu" GUID="4617a58d-d2cf-4649-a5fc-b28fc8a03105" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" />
          <akn:FRBRversionNumber value="1" GUID="40713be4-b3bb-41bc-963f-e95ab78abdfe" eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" />
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="573b26ea-40e1-448b-86c9-1b3f3d09d5b8" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1.xml" GUID="189c8da2-4493-4964-a540-94994f901c6f" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1.xml" GUID="e444831e-8819-4c1a-bb9e-9b5ac7ad6971" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" />
          <akn:FRBRdate name="generierung" date="1002-01-10" GUID="1978de76-b0a5-448f-ae73-81976ef31b27" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" />
          <akn:FRBRauthor href="recht.bund.de" GUID="029a15b2-7227-451f-8d42-629177541ba6" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" />
          <akn:FRBRformat value="xml" GUID="46b42396-64a7-4bde-921c-54aa3d8b6621" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" />
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="f34316f3-0edc-491a-9a8c-aa7356de468c" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1002-01-10" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="ccb37d46-3ae0-4d6e-9116-eda9f054bfa2" eId="meta-1_lebzykl-1_ereignis-1" />
        <akn:eventRef date="1002-01-11" refersTo="inkrafttreten" type="generation" source="attributsemantik-noch-undefiniert" GUID="cbb76abb-df73-4906-a66c-6081a41fdcaa" eId="meta-1_lebzykl-1_ereignis-2" />
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" GUID="5fce0464-0a94-43f8-b7de-820e05a6d12c" eId="meta-1_analysis-1">
        <akn:activeModifications GUID="dd491d9a-7436-4fa4-b16e-7df740035402" eId="meta-1_analysis-1_activemod-1">
          <akn:textualMod type="substitution" GUID="ef980441-3fb0-4182-9809-acde7a0416f6" eId="meta-1_analysis-1_activemod-1_textualmod-1">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" GUID="345a0e3b-b09d-4e18-affa-5525e2b5033b" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml" GUID="4fef7c8c-4043-4631-9bc9-a2cdbd177d18" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="6266feca-ed68-4f75-b200-b090aae33f10" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="da8eaf31-e897-41f4-9582-84e908b80eb6" eId="meta-1_analysis-1_activemod-1_textualmod-2">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="f3d6ccad-44e5-4437-9a7a-48b76af11727" eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_ernormen-1.xml" GUID="8d58c041-5418-4654-a06a-da51c579e411" eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="81743875-4e54-4b71-aebb-39e393fd22b1" eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="d590feef-1bf8-457f-87ab-1a72f6c5f12c" eId="meta-1_analysis-1_activemod-1_textualmod-3">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1" GUID="311023a4-373a-4731-81e9-09ad61a1a889" eId="meta-1_analysis-1_activemod-1_textualmod-3_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_präambeln-1.xml" GUID="d931d6d5-9c8f-4324-bfdb-03a100748477" eId="meta-1_analysis-1_activemod-1_textualmod-3_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="27f7f8c9-cf5a-4e82-9031-e5ebc27e5733" eId="meta-1_analysis-1_activemod-1_textualmod-3_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="b6ac2184-553a-4376-bfd8-bfbd60e402b2" eId="meta-1_analysis-1_activemod-1_textualmod-4">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1" GUID="61e79017-e734-4950-aafb-0af076a32809" eId="meta-1_analysis-1_activemod-1_textualmod-4_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_blockcontainer-1.xml" GUID="a752337c-5295-4da8-b058-804ca00a437e" eId="meta-1_analysis-1_activemod-1_textualmod-4_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="1c231697-af4c-4723-a6b1-393bb2a4916b" eId="meta-1_analysis-1_activemod-1_textualmod-4_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="83e992d9-4a06-45dd-9fb5-c64995c0e445" eId="meta-1_analysis-1_activemod-1_textualmod-5">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1" GUID="91701411-8ca3-4e00-9621-4d3706152a80" eId="meta-1_analysis-1_activemod-1_textualmod-5_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-1.xml" GUID="5863f809-fe54-4d29-af1d-e8c8b525a096" eId="meta-1_analysis-1_activemod-1_textualmod-5_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="1136c885-7931-4bae-8f7c-35c47bb82a79" eId="meta-1_analysis-1_activemod-1_textualmod-5_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="75a2b778-877e-47b6-943c-6e6d146d10b7" eId="meta-1_analysis-1_activemod-1_textualmod-6">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1" GUID="c8568958-cd55-442b-80d5-ab47feb80cf7" eId="meta-1_analysis-1_activemod-1_textualmod-6_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-2_inhalt-1_exmarkup-1.xml" GUID="fac18924-49b0-4a44-b84a-98f5ffaa9975" eId="meta-1_analysis-1_activemod-1_textualmod-6_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="95b35b07-8a4b-4359-b1d2-f84744e70bd9" eId="meta-1_analysis-1_activemod-1_textualmod-6_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="df96f947-41ac-44e8-8eb7-245837133950" eId="meta-1_analysis-1_activemod-1_textualmod-7">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1" GUID="fb36c17d-f0ff-4942-8bb4-6b0e585546ac" eId="meta-1_analysis-1_activemod-1_textualmod-7_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2.xml" GUID="fc9e7b53-502b-466b-9226-5dc0e23bd53e" eId="meta-1_analysis-1_activemod-1_textualmod-7_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="c0e48a45-7c3d-4f90-8935-ab1e322206c4" eId="meta-1_analysis-1_activemod-1_textualmod-7_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="39694efe-1150-4674-bc57-a7ed67ad0227" eId="meta-1_analysis-1_activemod-1_textualmod-8">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1" GUID="50001590-541a-4c6e-8622-7a00b11bdd76" eId="meta-1_analysis-1_activemod-1_textualmod-8_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-3_abs-2.xml" upTo="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-3_abs-3.xml" GUID="1c510d89-bc58-451c-aeab-be103d242c4f" eId="meta-1_analysis-1_activemod-1_textualmod-8_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="568a13c2-eb82-4beb-b119-4e58e1af6410" eId="meta-1_analysis-1_activemod-1_textualmod-8_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="64a478a6-e6ae-41ad-b395-cc3ece3ac12f" eId="meta-1_analysis-1_activemod-1_textualmod-9">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1" GUID="2a4cf853-c3f9-409f-8628-6488225b787e" eId="meta-1_analysis-1_activemod-1_textualmod-9_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2.xml" upTo="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3.xml" GUID="e13cbed9-77cc-4655-9635-fd0dd46602eb" eId="meta-1_analysis-1_activemod-1_textualmod-9_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="7a88b131-ed58-4487-8da5-d90f2c6e9dbb" eId="meta-1_analysis-1_activemod-1_textualmod-9_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="cfed6dd1-1f2e-4d87-a01e-17aa421e7aa5" eId="meta-1_analysis-1_activemod-1_textualmod-10">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1" GUID="265d5973-c57e-434d-8e2b-996f55ae499b" eId="meta-1_analysis-1_activemod-1_textualmod-10_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-3.xml" GUID="61274d14-9640-4729-8489-d5390e888ca7" eId="meta-1_analysis-1_activemod-1_textualmod-10_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="d78d6d77-ca58-4cdf-a143-0b722579d348" eId="meta-1_analysis-1_activemod-1_textualmod-10_gelzeitnachw-1" />
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="7e91b3c5-f2d0-46ae-8470-810cb3621b46" eId="meta-1_analysis-1_activemod-1_textualmod-11">
            <akn:source href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1" GUID="f20db698-a93d-4d2e-a50f-b5e0312e5076" eId="meta-1_analysis-1_activemod-1_textualmod-11_source-1" />
            <akn:destination href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-4_kapitel-1.xml" GUID="62533c27-7616-4f3f-bf77-1cfe37bdc4b2" eId="meta-1_analysis-1_activemod-1_textualmod-11_destination-1" />
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" GUID="ab0e97e7-feb0-4af2-87f8-355d0a3ba134" eId="meta-1_analysis-1_activemod-1_textualmod-11_gelzeitnachw-1" />
          </akn:textualMod>
        </akn:activeModifications>
      </akn:analysis>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="73e878c8-b171-4365-85f6-30c9fe0c7e56" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="88ec84ce-105a-42a7-9819-68894b4e4733" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="eed6015e-6a5a-4661-b449-1e706b9ae54f" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1" />
        </akn:temporalGroup>
        <akn:temporalGroup GUID="d6933f1c-8e1c-4212-add6-c5ad9ecc73d5" eId="meta-1_geltzeiten-1_geltungszeitgr-2">
          <akn:timeInterval refersTo="geltungszeit" GUID="11755d0e-9032-4e3c-94af-b048684fe356" eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-2" />
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>

    <akn:preface GUID="f6c89a26-4253-4f33-a880-8246bf805d14" eId="einleitung-1">
      <akn:longTitle GUID="fb8a5faa-cf3c-4290-8321-369773345a43" eId="einleitung-1_doktitel-1">
        <akn:p GUID="2b0d5cbf-371f-48e1-b71e-4fb61cfa31f6" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="54ad6cea-4904-4476-a068-9be3f8c3d112" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Erstes Gesetz zur Änderung des Strukturänderungsgesetzes</akn:docTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>

    <akn:preamble GUID="a0643968-0ca1-41c1-a106-28cacf90d4b1" eId="preambel-1">
      <akn:formula refersTo="eingangsformel" name="attributsemantik-noch-undefiniert" GUID="c7e6607a-5cfa-43f2-9c14-9fb327d0748d" eId="preambel-1_formel-1">
        <akn:p GUID="8a2d9809-80d1-4869-888e-1bafe56ab68f" eId="preambel-1_formel-1_text-1">Der <akn:organization title="Bundestag" refersTo="attributsemantik-noch-undefiniert" GUID="417266cf-923c-4de0-b466-964a38225b4f" eId="preambel-1_formel-1_text-1_org-1">Bundestag</akn:organization> hat das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>

    <akn:body GUID="364482b7-50b4-498a-b083-5340b5a7255f" eId="hauptteil-1">
      <akn:article refersTo="eingebundene-stammform" GUID="4fdfc366-8714-4e98-b417-2dbfe8cd3dc1" eId="hauptteil-1_para-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2">
        <akn:num GUID="3475b26b-106e-4f13-b3bb-8dcb5bd920a3" eId="hauptteil-1_para-1_bezeichnung-1"><akn:marker name="1" GUID="df97e29d-b6cd-40ff-afb6-e86c32bbba52" eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" /></akn:num>
        <akn:heading GUID="7efa43b6-f04b-48c9-8261-e6cc8e2ba92b" eId="hauptteil-1_para-1_überschrift-1">Artikel 1</akn:heading>
        <akn:paragraph GUID="9c799140-aceb-4c66-886c-3d957331c12e" eId="hauptteil-1_para-1_abs-1">
          <akn:num GUID="89c39207-a102-4a4b-afe0-1188ec1e59ed" eId="hauptteil-1_para-1_abs-1_bezeichnung-1"><akn:marker name="1" GUID="9fa4ce22-9cb2-40eb-8aa3-80e97e29c41d" eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
          <akn:list GUID="fbc3b366-d3d6-4dd6-b252-2e02cbc76445" eId="hauptteil-1_para-1_abs-1_untergl-1">
            <akn:intro GUID="b30c07a4-6837-4a6c-a216-ac77f8e01975" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1">
              <akn:p GUID="9149772a-d950-4fb9-933c-a6ab829c0d1d" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1">Das <akn:affectedDocument href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1" GUID="2d22fed2-9c94-4299-a569-0752f84b0a9a" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1">Strukturänderungsgesetz vom 1. Januar 1002 (BGBl. 1002 I Nr. 1)</akn:affectedDocument> wird wie folgt geändert:</akn:p>
            </akn:intro>
            <akn:point GUID="05fd9548-f29a-4a27-bc84-c310d91234a4" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="2a55daef-fdca-40f7-a6cf-94915f85dd0e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="60306c98-d3a1-4169-9c30-098ce3bface2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
              <akn:content GUID="888d01c9-af78-44db-8c70-a4cfe70d1b0f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                <akn:p GUID="e362b1ec-17dc-4900-b2d1-83d6c5412b5a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="d63a2fa5-b9bf-4f79-8652-f9d4b780bd18" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1">Der <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml" GUID="2c1d387e-ceaa-4a4c-a4d3-7137ea7ffc30" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1">Titel</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="8e1b47e2-a88c-4b9f-bb82-dc95080ce372" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:longTitle GUID="8b035ea3-f957-481e-9ba9-972dd3934c3c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1">
                        <akn:p GUID="8bb512b7-b785-469d-adeb-59ddb7f34733" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1">
                          <akn:docTitle GUID="72ace7a0-7559-48f1-afa8-bec04c884e3a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_doctitel-1">Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen</akn:docTitle>
                          <akn:shortTitle GUID="a28a8a5f-852d-48b2-9b49-2634bf5a75da" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_kurztitel-1">(Strukturänderungsgesetz)</akn:shortTitle>
                        </akn:p>
                      </akn:longTitle>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="c79378f8-938b-471e-baa8-40923db1aff7" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="efb90653-b23f-4c46-a880-441c47703dac" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="d4f8d115-4087-4429-a5af-4d83343a891b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
              <akn:content GUID="e3863192-6d7f-43f3-b5a3-c45a5f46017b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="b54e3ed6-ef2d-4fa2-99ff-13b991cd5a81" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="d5781c9e-a428-4e7a-8d5f-aac85e387d4e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_ernormen-1.xml" GUID="70079daa-677a-419e-a5c1-f8d0f11b1c18" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1">Ermächtigung</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="d3694072-5154-4a71-a820-7ff49ec4109f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:citations GUID="b635efe2-3e60-4302-b66a-b912b7c56e98" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1">
                        <akn:citation GUID="322769a7-d35c-441b-9c57-ba980cab9660" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1_ernorm-1">
                          <akn:p GUID="89372bdb-c3f0-4810-b92e-55cc02daef78" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1_ernorm-1_text-1">Aufgrund der Spezifikation von Änderungsbefehlen können Strukturen und Gliederungseinheiten ersetzt werden. </akn:p>
                        </akn:citation>
                      </akn:citations>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="fb7d1fdc-6122-40f6-bb88-a6e82eddf353" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3">
              <akn:num GUID="0dadb669-b57e-4e41-bd97-2822140d284f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="aaffb062-18db-4d52-8e8e-862416acf3be" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
              <akn:content GUID="1d9ba0c4-938d-4be4-80c4-478934f9c72a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="d0d748d6-d8b4-4395-81bd-75bb4a5db4a0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="2d4f90ba-3aac-4f50-b2f9-0458e49d5d0b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_präambeln-1.xml" GUID="3543de4e-4c70-4251-b2ec-ec9628ce10f0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_ref-1">Präambel</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="8f22e774-60ff-43e5-a470-c72545ae03d9" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:recitals GUID="5c0e37e1-2d59-4041-aed2-9b82f583e9cd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1">
                        <akn:heading GUID="1f6d61e5-6722-4d49-81a2-3fadd4f3b581" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_überschrift-1">Präambel</akn:heading>
                        <akn:recital GUID="6ddf9efe-0e09-4947-88e3-849152f077d2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_präambelinh-1">
                          <akn:p GUID="7b7cf1c0-add6-48e4-afbe-693d0c685174" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_präambelinh-1_text-1">Im Bewusstsein der Herausforderungen bei der Umsetzung von Änderungsbefehlen hat der Deutsche Bundestag das nachstehende Gesetz beschlossen.</akn:p>
                        </akn:recital>
                      </akn:recitals>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="69f51e44-d744-419b-ab77-fdcf7985ba89" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4">
              <akn:num GUID="e6701a76-6864-45f7-92d9-d46ed0766f1f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_bezeichnung-1"><akn:marker name="4" GUID="9ee86136-f546-4dde-acc9-f8c2c74f6934" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_bezeichnung-1_zaehlbez-1" />4.</akn:num>
              <akn:content GUID="577cc681-8fcf-4ed1-af51-fb66e6713851" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="cbe124ac-5497-4b34-a18b-fe5c8c3d3d42" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="d535c79f-32cc-49f8-9cd4-43e609fe6151" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_blockcontainer-1.xml" GUID="3cb71b98-6b0f-49f0-920e-f3e3ee207491" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_ref-1">Inhaltsübersicht</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="4d94c313-4404-4d9e-8e3d-eb8133893a0c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:blockContainer refersTo="inhaltsuebersicht" GUID="fe63b2cb-196d-41ae-baea-4e4ea9026c19" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1">
                        <akn:heading GUID="255a4008-a35c-4e92-a3bd-cd53188fc97e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_überschrift-1">Inhaltsübersicht</akn:heading>
                        <akn:toc GUID="1f8997b5-2fe2-4c62-98dd-5ebb719c2bdb" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1">
                          <akn:tocItem level="1" href="" GUID="50b4265c-b2ad-4e08-abcc-109098d3253f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1_eintrag-1">
                            <akn:span GUID="2390afe8-be75-4d91-96d8-3f6864c056e9" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1">Neue Inhaltsübersicht.</akn:span>
                          </akn:tocItem>
                        </akn:toc>
                      </akn:blockContainer>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="69eec497-c080-4797-84ab-c575c80e194e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5">
              <akn:num GUID="4dd1d778-b535-47b4-934d-23ba34f2ba71" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_bezeichnung-1"><akn:marker name="5" GUID="e24961d9-983b-4dbb-baee-edf7bdc8d036" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_bezeichnung-1_zaehlbez-1" />5.</akn:num>
              <akn:list GUID="d672141d-6886-462b-9ca3-17b9ecdeca63" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1">
                <akn:intro GUID="7b6f16e3-4a28-4470-9ebd-8deda2687a59" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1">
                  <akn:p GUID="fb146142-2c15-4a04-a341-a959a2fd5821" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1_text-1"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1.xml" GUID="0724d25d-240c-4f03-8756-f0c1f60d46bd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1_text-1_ref-1">§ 1</akn:ref> wird wie folgt geändert:</akn:p>
                </akn:intro>
                <akn:point GUID="d1420b67-9d58-4da4-a6cd-f5ece0c6cefc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a">
                  <akn:num GUID="062b065d-7232-45e7-b812-66842158106f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_bezeichnung-1"><akn:marker name="a" GUID="e3639781-320d-4d8c-a352-10de06c810be" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" />a)</akn:num>
                  <akn:content GUID="d6cc256a-459d-412e-bbd9-d41c67ce8ed2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1">
                    <akn:p GUID="20cf0248-ff8d-4f7f-b8dc-04fe5b8a2c60" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1">
                      <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="0b7c433d-261e-41cc-8a81-dfef0bf405cb" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-1.xml" GUID="c448b96f-eebd-4400-b4bf-c203a38286a3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_ref-1">Absatz 1</akn:ref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="ddf50ad9-38e1-421d-ae21-a63face49fc8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:paragraph GUID="e7183ec8-2222-435d-81d8-daac83f3c437" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1">
                            <akn:num GUID="422fc1cf-fb56-4b99-8e56-c59d1130cf53" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_bezeichnung-1"><akn:marker name="1" GUID="cab1de0c-3ccd-43d8-ad17-7d6536dc59d7" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
                            <akn:content GUID="d2071a7d-cb3b-4414-aea7-7117bef8ac62" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_inhalt-1">
                              <akn:p GUID="a35ec4d3-2265-42d9-a9ad-d8712709dccf" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_inhalt-1_text-1">Dieses Gesetz findet Anwendung auf fast alle definierten Struktur und Gliederungsebenen.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="16979bcc-6b02-46c6-8c08-157dfa9780d2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b">
                  <akn:num GUID="67a8a594-8837-4a4f-afbd-8c924a528612" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_bezeichnung-1"><akn:marker name="b" GUID="ff8cf407-d616-4338-be8e-20335796557c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" />b)</akn:num>
                  <akn:content GUID="95951a6e-921a-40a4-a19a-1f19ef87cafa" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1">
                    <akn:p GUID="c138c109-8321-483c-93c6-0cc653c9a21d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1">
                      <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="b944592a-28d3-4ccc-a8f6-3a5ccf47593a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-2_inhalt-1_exmarkup-1.xml" GUID="cf6a3797-2ddb-46e4-8350-4146955473bd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_ref-1">Formel</akn:ref> in <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-2.xml" GUID="cf253c14-2f4f-4b39-ade5-f27e968275dd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_ref-2">Absatz 2</akn:ref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="e2fc4408-9553-4155-82fe-f821e7ca02c8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:foreign GUID="7ae563a6-496f-47ad-a4ab-dde35230eb8a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_quotstruct-1_exmarkup-1">
                            <math xmlns="http://www.w3.org/1998/Math/MathML" display="block">
                              <mrow>
                                <mi>e</mi>
                                <mo>=</mo>
                                <mi>m</mi>
                                <msup>
                                  <mi>c</mi>
                                  <mn>2</mn>
                                </msup>
                              </mrow>
                            </math>
                          </akn:foreign>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="9a091e3c-1b35-47f5-acaf-c5ddf11fc242" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c">
                  <akn:num GUID="2883b77c-7323-4c76-bf62-5343b72f901f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_bezeichnung-1"><akn:marker name="c" GUID="6492221f-2f3a-4f7a-a8a1-fe170148469e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" />c)</akn:num>
                  <akn:content GUID="f2e3c8df-6d31-48ca-8e2f-a61af2a3195e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1">
                    <akn:p GUID="2d2bacb9-12d2-4729-b280-dbe0054a45fc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1">
                      <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="1978e541-942e-4850-98f2-7a86d16ff8a1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1">
                        <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2.xml" GUID="81405edc-690e-4787-8dde-dd592e5979d2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_ref-1">Absatz 3 Nummer 2</akn:ref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="68a797f3-3488-4973-927a-dee9f5fee65e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:point GUID="97f917dc-025c-4e81-bb50-6c79d9562725" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2">
                            <akn:num GUID="1792443d-9507-4aa3-abcc-600284cdddde" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="5c5e9285-7c0e-4270-b2bb-126011cb23bd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                            <akn:list GUID="b333476a-a520-4d56-a9ba-8da9e813443f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1">
                              <akn:intro GUID="46e043a2-cd25-43d5-8eb5-8760b394f82e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_intro-1">
                                <akn:p GUID="10be2d30-4c51-4981-9c0d-9f530bda7abb" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_intro-1_text-1">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                              </akn:intro>
                              <akn:point GUID="e3cd7b63-a0e5-4e4b-88d0-bc47945b3ac8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b">
                                <akn:num GUID="0a23803e-5440-43a1-8fd6-4d4a2431d59e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1"><akn:marker name="b" GUID="3ae4a9f0-1a9e-429f-a48a-fa028dc86872" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" />b)</akn:num>
                                <akn:content GUID="797e6117-2005-4c24-8d65-672c72313502" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_inhalt-1">
                                  <akn:p GUID="84e0af27-02d7-4493-848e-580df5ca2956" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_inhalt-1_text-1"> Zeilen, </akn:p>
                                </akn:content>
                              </akn:point>
                              <akn:point GUID="a59dc357-dce4-4eca-b631-0c988b4e8723" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c">
                                <akn:num GUID="fc14a057-19cc-4d85-8cc0-f4cf437a9f6b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1"><akn:marker name="c" GUID="35a2c0db-32cd-401c-a523-7dbeeb68fb5f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" />c)</akn:num>
                                <akn:content GUID="08bfacb3-adae-497e-95d1-5b37848fc3ba" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_inhalt-1">
                                  <akn:p GUID="cf94a01f-60b8-4b01-a3d7-6892214d535f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_inhalt-1_text-1">oder Zellen.</akn:p>
                                </akn:content>
                              </akn:point>
                            </akn:list>
                          </akn:point>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="527c272f-e4fa-462c-840c-d04a6bf0fef3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6">
              <akn:num GUID="37d28945-03b8-4c3c-ba7e-2b00270f8a41" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_bezeichnung-1"><akn:marker name="6" GUID="d56cb0eb-f633-4caa-8772-f9a93a5911c8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_bezeichnung-1_zaehlbez-1" />6.</akn:num>
              <akn:list GUID="d0376fe7-c769-40c5-87d9-0bee03ee0fa8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1">
                <akn:intro GUID="c5f5c292-74c0-4730-b9fe-c6c9873cd755" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_intro-1">
                  <akn:p GUID="0fe95a87-7181-47b1-bfab-7cb71f006fb6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_intro-1_text-1"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-3.xml" GUID="f8681454-6d4c-4f2e-83de-09588d15bb31" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_intro-1_text-1_ref-1">§ 3</akn:ref> wird wie folgt geändert:</akn:p>
                </akn:intro>
                <akn:point GUID="58e7e9e3-f9de-4402-a11a-acd1449bfd8f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a">
                  <akn:num GUID="38a22aa0-ae6a-4b43-80c7-654b2e3dc6bb" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_bezeichnung-1"><akn:marker name="a" GUID="ac66f43d-57bc-4b92-9bdb-c8af53a78361" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" />a)</akn:num>
                  <akn:content GUID="f2060c7e-9369-4808-934a-a3f9434708d7" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1">
                    <akn:p GUID="fffc4bb1-c1bf-40c1-8291-9f22f9d91862" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1">
                      <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="245b613d-53c4-4104-a13d-3ca41f07d961" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1"><akn:rref eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_bref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-3_abs-2.xml" upTo="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-3_abs-3.xml">Absatz 2 bis Absatz 3</akn:rref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="a1550357-1b9b-4a1e-b20c-3150807da927" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:paragraph GUID="938a814c-0df3-4a2f-aa32-a99a46ea9967" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2">
                            <akn:num GUID="e2d46501-b0ee-4a8f-92ee-273bdfdff31b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_bezeichnung-1"><akn:marker name="2" GUID="6069492f-d09f-4d11-8c05-2b408de38008" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
                            <akn:content GUID="c07a8d53-e9c7-45dd-9abc-aa9372c0638a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_inhalt-1">
                              <akn:p GUID="db281afd-0a33-4c13-acb8-a2023f1a74f3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_inhalt-1_text-1">Zur Unterstützung der Implementierung wird eine zentrale Koordinierungsstelle eingerichtet, die alle relevanten Aufgaben übernimmt.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                          <akn:paragraph GUID="c882aaef-86d6-4cae-af65-fdd1cec34721" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3">
                            <akn:num GUID="d00ebe70-f9fa-456f-92ac-bb72ae72b742" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_bezeichnung-1"><akn:marker name="3" GUID="edeebaf0-a873-43cf-b6f7-2c449657af7e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_bezeichnung-1_zaehlbez-1" />(3)</akn:num>
                            <akn:content GUID="3108dab6-e3d5-40ac-9ca3-44d06b945a6c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_inhalt-1">
                              <akn:p GUID="5232cf08-8f32-4400-b5fe-1f5dfb798157" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="a6f1240a-aca2-4bbb-a6d0-538bf5c97a1c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7">
              <akn:num GUID="3cc5dd23-5eaf-4f2e-9a5a-973834060f28" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_bezeichnung-1"><akn:marker name="7" GUID="17e12479-b54d-4fd6-98d7-30a78bf41aec" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_bezeichnung-1_zaehlbez-1" />7.</akn:num>
              <akn:list GUID="033d4154-8e3f-419d-9ee3-aff1a311ed1f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1">
                <akn:intro GUID="8f44475a-4366-4ef2-9451-65139e649096" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_intro-1">
                  <akn:p GUID="a1b61361-36f8-47a2-918e-75f0da5d598b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_intro-1_text-1"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-4.xml" GUID="a4716ab5-66c5-46ad-8ed8-9bcff7e07917" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_intro-1_text-1_ref-1">§ 4</akn:ref> wird wie folgt geändert:</akn:p>
                </akn:intro>
                <akn:point GUID="cf4d5927-f223-461d-aa47-343690442c04" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a">
                  <akn:num GUID="ca518905-7110-410b-a037-3b00cb8c2d3b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_bezeichnung-1"><akn:marker name="a" GUID="7c2668a9-9cbe-4c34-89af-a4cfe9b5376b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" />a)</akn:num>
                  <akn:content GUID="0ef8df55-5534-4d3d-8687-b40414e1f48a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1">
                    <akn:p GUID="622e3b48-0153-4bcb-b429-25196ec1b1b8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1">
                      <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="b0b52677-7bff-47c8-bc93-909ba23b119d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1"><akn:rref eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_bref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2.xml" upTo="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3.xml">Absatz 2 Nummer 2 bis Nummer 3</akn:rref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="ca3cda40-e1e9-4934-b907-217af4babd19" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:point GUID="3d20c8f3-1fd4-4f07-bf21-20adb00ce91b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2">
                            <akn:num GUID="dc98b33b-2e72-4703-a798-674d76df3e4b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="01517851-108e-42a6-985a-e692a26b2212" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                            <akn:content GUID="2013080f-7a1a-42bc-9fee-3f7a62ea52aa" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_inhalt-1">
                              <akn:p GUID="60beb19d-9100-4664-a8ea-f4aba292492f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-7_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_inhalt-1_text-1">den spezifischen regionalen Anforderungen.</akn:p>
                            </akn:content>
                          </akn:point>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="db5af8b9-7a46-4b65-8cc3-b87e8e7674eb" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8">
              <akn:num GUID="cc554b9a-e137-48cd-b735-0b6774e6779e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_bezeichnung-1"><akn:marker name="8" GUID="4158a840-27d5-4eee-aa0e-6a5994768cf7" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_bezeichnung-1_zaehlbez-1" />8.</akn:num>
              <akn:content GUID="2fe9739e-c809-4139-91fa-e111a7674e5b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1">
                <akn:p GUID="5675e613-f44b-4383-a825-1da8673206ae" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="2cbbf69b-9fe9-4996-b989-c10481119472" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1">Das <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-3.xml" GUID="908f56a9-218c-478f-8bf4-7f915bf6ea59" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_ref-1">3. Buch</akn:ref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="5287afa2-cdd1-4d8c-b027-8ca653aa8f19" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:book GUID="bff63266-ee36-4d78-9110-675239af599f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3">
                        <akn:num GUID="ddec4bf9-36d3-4af4-a607-a0acb1ee1bea" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_bezeichnung-1"><akn:marker name="3" GUID="d24ffb96-8213-430b-b7d7-dba7fc6ab1f3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_bezeichnung-1_zaehlbez-1" />3. Buch</akn:num>
                        <akn:heading GUID="0384394e-3c9f-4355-8580-70280688c64f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_überschrift-1">Beispiele Teil I</akn:heading>
                        <akn:article GUID="c31f4462-545e-4064-af55-d42bb34dacde" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6" period="#meta-1_geltzeiten-1_geltungszeitgr-2">
                          <akn:num GUID="6b1085ed-6ac5-4ff3-ac2c-79602af6446f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_bezeichnung-1"><akn:marker name="6" GUID="ed9a1419-e55d-445b-a1ed-c3906c7cb4bc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_bezeichnung-1_zaehlbez-1" />§ 6</akn:num>
                          <akn:heading GUID="e3f17909-13f5-47e0-8467-e4906b90be2b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_überschrift-1">Beispielartikel (neu)</akn:heading>
                          <akn:paragraph GUID="d4cfcdd1-d1fd-408a-bf51-0720c852f6b5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_abs-1">
                            <akn:num GUID="9819cc84-b783-43e7-8185-b00f73748bdd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_abs-1_bezeichnung-1"><akn:marker name="1" GUID="6e7d886d-34fd-4009-bbe8-06c7ecb57edc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
                            <akn:content GUID="debcb0c7-991d-4823-9397-d55544fb70b0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_abs-1_inhalt-1">
                              <akn:p GUID="f6aa96b1-0d31-4b72-8651-5a6bc2b3b1c8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-8_inhalt-1_text-1_ändbefehl-1_quotstruct-1_buch-3_para-6_abs-1_inhalt-1_text-1">Im Rahmen des Gesetzes werden klare Regelungen für die Inanspruchnahme von Rechtsmitteln festgelegt.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                        </akn:article>
                      </akn:book>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="0f0384e5-311b-4db2-9d10-740a5450bad0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9">
              <akn:num GUID="d88a6962-f945-4461-9921-06c43a32248d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_bezeichnung-1"><akn:marker name="9" GUID="070ef5f4-1772-46e4-890d-0102d2e9c141" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_bezeichnung-1_zaehlbez-1" />9.</akn:num>
              <akn:content GUID="13d7f1c1-8ee4-4a44-8578-9a332ffa165d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1">
                <akn:p GUID="f98f34f2-a8d0-40a7-ac97-8bc5bb81bc9f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1">
                  <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="f7e5f595-9c76-4e1a-a8d7-9b887ec2e5bd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1">Das <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_buch-4_kapitel-1.xml" GUID="e2c988e7-e48d-46af-a9bd-526ebbc43f30" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_ref-1">Beispielkapitel</akn:ref> wird ersetzt durch: <akn:quotedStructure startQuote="„" endQuote="“" GUID="71c54d79-f3e3-4504-a03e-2f0fc262eb20" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:chapter GUID="b067a470-3afd-453e-b189-35d90b3b2ccc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1">
                        <akn:num GUID="b41ce32c-045d-46f6-a3f1-340d663f5537" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_bezeichnung-1"><akn:marker name="1" GUID="c74b8767-0184-41c6-bc39-8fcb044d97e0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_bezeichnung-1_zaehlbez-1" /></akn:num>
                        <akn:heading GUID="18ab159f-f624-4b96-a410-6bffc0059069" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_überschrift-1">Beispielkapitel (neu)</akn:heading>
                        <akn:article GUID="ae0a3b00-fb78-4822-bc2b-9201e65a82d5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7" period="#meta-1_geltzeiten-1_geltungszeitgr-2">
                          <akn:num GUID="9187186f-7f6b-4c60-aabd-4bcb33f225a5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_bezeichnung-1"><akn:marker name="7" GUID="532e328b-bc30-4ae5-bc44-c14cd1ccfcf0" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_bezeichnung-1_zaehlbez-1" />§ 7</akn:num>
                          <akn:heading GUID="e89fb113-5f67-4418-8010-5d1a023220e6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_überschrift-1">Beispielartikel</akn:heading>
                          <akn:paragraph GUID="a0abdae7-7930-4f36-811d-86838bd5fcc8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_abs-1">
                            <akn:num GUID="b7927670-dbe9-428d-a5e6-86a2d8857d1d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_abs-1_bezeichnung-1"><akn:marker name="1" GUID="5fedfec4-374d-485c-89ef-f95d237fe2e2" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
                            <akn:content GUID="0ae4cc08-45f6-4bd2-923c-7be9bd74db07" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_abs-1_inhalt-1">
                              <akn:p GUID="7b45dc0a-eccc-4321-a724-3202a0e0cd65" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-9_inhalt-1_text-1_ändbefehl-1_quotstruct-1_kapitel-1_para-7_abs-1_inhalt-1_text-1">Das Gesetz legt großen Wert auf die Einbeziehung der Öffentlichkeit und relevanter Interessengruppen durch umfassende Beteiligungsverfahren. Diese Verfahren sollen sicherstellen, dass die Meinungen und Bedürfnisse der Bürger sowie die Expertise von Fachleuten und Interessensvertretern in die Entscheidungsprozesse einfließen.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                        </akn:article>
                      </akn:chapter>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
          </akn:list>
        </akn:paragraph>
      </akn:article>

      <akn:article refersTo="geltungszeitregel" GUID="7a5f088c-df2d-401e-a8b5-971fa457b2c4" eId="hauptteil-1_para-3" period="#meta-1_geltzeiten-1_geltungszeitgr-2">
        <akn:num GUID="0f2820c4-dcb9-4448-98c9-245f621f9641" eId="hauptteil-1_para-3_bezeichnung-1"><akn:marker name="3" GUID="bf88f5f6-7f3f-4543-a588-bafd75d0a4e2" eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" /></akn:num>
        <akn:heading GUID="70eec902-81de-4bf2-8407-48ba00163d66" eId="hauptteil-1_para-3_überschrift-1">Inkrafttreten</akn:heading>
        <akn:paragraph GUID="8f343606-21e7-4466-8d0c-d2c1ea162226" eId="hauptteil-1_para-3_abs-1">
          <akn:num GUID="c4880ea2-fc58-459f-83f6-3f9a31b2b4eb" eId="hauptteil-1_para-3_abs-1_bezeichnung-1"><akn:marker name="1" GUID="e9e05356-ef76-48c9-9d63-b6e8a763fa55" eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
          <akn:content GUID="2a16c4a4-6d6f-4cec-b5ce-ed484ce1a26d" eId="hauptteil-1_para-3_abs-1_inhalt-1">
            <akn:p GUID="885d6c53-6cd1-412c-a2a5-446e1f222e87" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1">Dieses Gesetz tritt <akn:date date="1002-01-02" refersTo="inkrafttreten-datum" GUID="61dc73b5-4f2d-4090-b96f-7c1fd50699bc" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1_datum-1">am Tag nach der Verkündung</akn:date> in Kraft.</akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');

-- Target law
INSERT INTO norms (guid, eli, xml)
VALUES ('63ef9358-8755-46e4-bf6a-21f379014597', 'eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                                                                                                                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="7c364d8d-7867-46f5-bfed-f5ad8b4ab89e" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="8bda99ab-c051-41d6-9b40-b34555c39d31" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="2ba53b48-1b11-43fb-8b20-074b2c567f33" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/regelungstext-1" GUID="81998cf6-1e6a-4a23-8230-a6eefa61a253" eId="meta-1_ident-1_frbrwork-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1" GUID="1383239a-f26a-48a5-9761-e06eb05abc95" eId="meta-1_ident-1_frbrwork-1_frbruri-1" />
          <akn:FRBRalias name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" GUID="c9b3e387-d85b-4db3-adf7-4f0db2a77b91" eId="meta-1_ident-1_frbrwork-1_frbralias-1" />
          <akn:FRBRdate name="verkuendungsfassung" date="1002-01-01" GUID="cca99ae9-3da3-43e3-a25d-dde986cc43ef" eId="meta-1_ident-1_frbrwork-1_frbrdate-1" />
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="7ca858b0-959f-4066-866a-35b01f4c4b2c" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" />
          <akn:FRBRcountry value="de" GUID="89a1bb9e-a263-47c8-896d-c072ff4ade68" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" />
          <akn:FRBRnumber value="1" GUID="03104316-8ff4-424a-a5a6-91d3470db7db" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" />
          <akn:FRBRname value="bgbl-1" GUID="53679c01-90f5-430b-861e-d39904e657e5" eId="meta-1_ident-1_frbrwork-1_frbrname-1" />
          <akn:FRBRsubtype value="regelungstext-1" GUID="bd02fcf8-5d97-48e4-844f-03ce1a14b0cf" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" />
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="516fab5c-88ab-4292-ab39-c9e681790f33" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1" GUID="5cdf307b-8dec-494f-8035-469132f1414b" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu" GUID="f5aeabef-d78d-4268-8819-5f1170e9f8ea" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" />
          <akn:FRBRalias name="aktuelle-version-id" value="63ef9358-8755-46e4-bf6a-21f379014597" GUID="5652140a-7c00-43f9-84f7-641db77f6389" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" />
          <akn:FRBRalias name="nachfolgende-version-id" value="e4e034dd-61b9-43aa-b4f9-b778dc6adfda" GUID="10ef604c-d561-4550-a137-f7e3b11812d9" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" />
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="bb00fcde-3d40-45a7-bcc3-609f2cb81ad8" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" />
          <akn:FRBRdate name="verkuendung" date="1002-01-01" GUID="0bc6b5c1-d4c5-47de-81d9-63453d0b9acb" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" />
          <akn:FRBRlanguage language="deu" GUID="873de5b6-2064-4f9b-8b0e-4ef1f8405b87" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" />
          <akn:FRBRversionNumber value="1" GUID="ca48bdf4-5631-4c45-a161-61ccd849cd1b" eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" />
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="10f71315-c390-4799-8166-2e53c672c368" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1.xml" GUID="9c599b8f-5da2-47a8-8620-5b01ad2f1b6a" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" />
          <akn:FRBRuri value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1.xml" GUID="807ed403-0f2e-4bff-927d-99eaccb0b1b8" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" />
          <akn:FRBRdate name="generierung" date="1002-01-01" GUID="5862c4ff-7af5-4b8a-bc42-19cc0fa797a1" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" />
          <akn:FRBRauthor href="recht.bund.de" GUID="7a3922c6-4dfc-43c3-8ab6-9b33f79c5f5b" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" />
          <akn:FRBRformat value="xml" GUID="d47436ef-a59a-4c61-859c-61f1d74e5bf7" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" />
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="d4e71855-22b4-4dfd-b36a-ad6d9ca4fc95" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1002-01-01" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="2eabf2a9-130e-442a-83c7-478fd0af952e" eId="meta-1_lebzykl-1_ereignis-1" />
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" GUID="64b2888e-d5c4-48c5-91ae-5a345ade9044" eId="meta-1_analysis-1" />
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="4110fd7b-10b9-407a-98f9-6694e54f4aa4" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="d06431e9-9430-4826-bba4-28f4cb244f96" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="c3b6dfca-35c4-4da3-9665-d16eaf5a9b35" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1" />
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>

    <akn:preface GUID="558a7994-288c-4675-8bac-bd66bee0f936" eId="einleitung-1">
      <akn:longTitle GUID="dc689db1-5222-4ce9-8b27-eda52a45de2c" eId="einleitung-1_doktitel-1">
        <akn:p GUID="1da23b6e-1074-44e9-825a-e89954dfba55" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="492ae182-a8e7-4b78-98e5-69db635f94ef" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten</akn:docTitle>
          <akn:shortTitle GUID="c440c4fa-e725-40fb-b087-346d4216e3d7" eId="einleitung-1_doktitel-1_text-1_kurztitel-1">(Strukturänderungsgesetz)</akn:shortTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>

    <akn:preamble GUID="c3b0b356-a440-4343-82c4-65f95c9516a7" eId="preambel-1">
      <akn:citations GUID="d8d6d35f-a93b-43ac-a0bd-5e881adab346" eId="preambel-1_ernormen-1">
        <akn:citation GUID="ee4a2977-d5d8-4431-bc8e-8bbd79879218" eId="preambel-1_ernormen-1_ernorm-1">
          <akn:p GUID="e8aa2c58-58d2-4d90-a90b-4109432d3930" eId="preambel-1_ernormen-1_ernorm-1_text-1">Die Bundesregierung wird ermächtigt, durch Rechtsverordnung ohne Zustimmung des Bundesrates die zur Durchführung dieses Gesetzes erforderlichen Rechtsvorschriften zu erlassen. Hierzu gehören insbesondere Regelungen über die organisatorische Umsetzung der in diesem Gesetz festgelegten Maßnahmen, die Festlegung technischer Standards sowie Bestimmungen zur Überwachung und Kontrolle der Einhaltung der gesetzlichen Vorgaben.</akn:p>
        </akn:citation>
      </akn:citations>
      <akn:recitals GUID="c3dc0f83-d136-4a04-9bb8-bcdbb4eb0c40" eId="preambel-1_präambeln-1">
        <akn:heading GUID="7a1845b5-962c-4f56-9b7b-4304341a9e18" eId="preambel-1_präambeln-1_überschrift-1">Präambel</akn:heading>
        <akn:recital GUID="bac17f14-45b1-4c58-bf95-f5c46b6cad7d" eId="preambel-1_präambeln-1_präambelinh-1">
          <akn:p GUID="ec5b59eb-cf30-424b-b0c4-e7178c4b0409" eId="preambel-1_präambeln-1_präambelinh-1_text-1">Im Bewusstsein der Verantwortung gegenüber den Bürgerinnen und Bürgern, sowie im Bestreben, die Effizienz und Transparenz der staatlichen Strukturen zu erhöhen, hat der Deutsche Bundestag das nachstehende Gesetz beschlossen. Ziel dieses Gesetzes ist es, bestehende Strukturen und Gliederungseinheiten in Dokumenten unter Berücksichtigung moderner Technologien zu ersetzen, zu vereinheitlichen und zu optimieren. Dabei sollen sowohl die Interessen der Bürgerinnen und Bürger als auch die Anforderungen an eine zukunftsfähige und digitalisierte Verwaltung gleichermaßen gewahrt werden.</akn:p>
          <akn:p GUID="8756158f-0dd3-41e7-b1c4-270203c8f3e9" eId="preambel-1_präambeln-1_präambelinh-1_text-2">Dieses Gesetz beruht auf den Grundsätzen der Rechtstaatlichkeit, der Wirtschaftlichkeit und der Bürgernähe. Es stellt sicher, dass die Anpassungen an Gesetzen und Verordnungen, transparent und effektiv durchgeführt werden.</akn:p>
        </akn:recital>
      </akn:recitals>
      <akn:blockContainer refersTo="inhaltsuebersicht" GUID="ea5bfdf7-6765-4df9-a764-0c586784aad0" eId="preambel-1_blockcontainer-1">
        <akn:heading GUID="a9f860f6-19e9-4828-ae18-4020d1043a9a" eId="preambel-1_blockcontainer-1_überschrift-1">Inhaltsübersicht</akn:heading>
        <akn:toc GUID="ba86f68e-5b14-4e08-a752-88b9996fef6a" eId="preambel-1_blockcontainer-1_inhuebs-1">
          <akn:tocItem level="1" href="" GUID="8b8205eb-5a7a-4d3a-a9b6-20ab89c8b174" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1">
            <akn:span GUID="fd086af7-215c-4ddf-aa67-019350f6b623" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1">Abschnitt 1</akn:span>
            <akn:span GUID="2403f040-8ca9-4a97-b12b-d427accdd06d" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-2">Allgemeine Vorschriften</akn:span>
          </akn:tocItem>
          <akn:tocItem level="2" href="" GUID="1b3fffbf-34ec-47eb-b03c-177fcd21f0f1" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2">
            <akn:span GUID="8fd3fc0d-51e5-4605-8e44-dcdf4b544fef" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-1">§ 1</akn:span>
            <akn:span GUID="b2d3dedc-de13-45aa-95eb-d766711f1a4e" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-2">Abfallwirtschaftliche Ziele</akn:span>
          </akn:tocItem>
          <akn:tocItem level="2" href="" GUID="5af17eb2-c69c-475a-b7e8-395203acb2cb" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3">
            <akn:span GUID="87e658cf-9195-4fd1-98e6-8476179f0fe5" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-1">§ 2</akn:span>
            <akn:span GUID="e645cec7-be92-4f7d-9448-657c98489dbf" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-2">Anwendungsbereich</akn:span>
          </akn:tocItem>
          <akn:tocItem level="2" href="" GUID="f7540d1c-a28c-4bc2-82a8-69a95e1e7adc" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4">
            <akn:span GUID="e9682a1d-57fd-4319-b605-ccdfafd85e32" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-1">§ 3</akn:span>
            <akn:span GUID="1f89f6c0-d97d-4ee0-a9af-96eee4c4e6f8" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-2">Begriffsbestimmungen</akn:span>
          </akn:tocItem>
          <akn:tocItem level="1" href="" GUID="beaeeb2d-0a2e-43eb-b0c4-37c766017565" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5">
            <akn:span GUID="b4cf00c0-cae4-4b2b-aec0-672648aa89a9" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-1">Abschnitt 2</akn:span>
            <akn:span GUID="03a2350d-2910-4e71-86c1-89f83b795cb1" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-2">Pflichten beim Inverkehrbringen von Elektro- und Elektronikgeräten</akn:span>
          </akn:tocItem>
          <akn:tocItem level="2" href="" GUID="2c4358fa-9433-488b-9123-0dc5beac4225" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6">
            <akn:span GUID="c9ccede0-c91b-4197-bb5d-5c04218df217" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-1">§ 4</akn:span>
            <akn:span GUID="9505898b-c5d6-425c-809d-84c88d07526c" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-2">Produktionskonzeption</akn:span>
          </akn:tocItem>
          <akn:tocItem level="2" href="" GUID="9456f1c3-bea6-4a10-b34e-65a7712a52f7" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7">
            <akn:span GUID="a93a0838-8061-4778-b63a-29b149b26b76" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-1">§ 5</akn:span>
            <akn:span GUID="6adf9a55-3a4d-4702-b7ef-615ada1dd6ad" eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-2">Einrichten der Gemeinsamen Stelle</akn:span>
          </akn:tocItem>
        </akn:toc>
      </akn:blockContainer>
    </akn:preamble>

    <akn:body GUID="d4e8f7e2-a3d1-4524-83f0-6830f2d5602f" eId="hauptteil-1">
      <akn:book GUID="a0dde363-c30a-4a5d-8eb4-40e2c0bc83ae" eId="hauptteil-1_buch-1">
        <akn:num GUID="a4b90106-5bfb-47b3-9e78-301bc0ec52fa" eId="hauptteil-1_buch-1_bezeichnung-1"><akn:marker name="1" GUID="d6d70edf-d03c-422c-b531-6d56d73c8f17" eId="hauptteil-1_buch-1_bezeichnung-1_zaehlbez-1" />1. Buch</akn:num>
        <akn:heading GUID="b2166332-861f-4ec3-91cd-29f2ae6d8712" eId="hauptteil-1_buch-1_überschrift-1">Strukturen und Gliederungsebenen</akn:heading>
        <akn:article GUID="dc72bc52-88bf-49e5-b286-ee3657e21802" eId="hauptteil-1_buch-1_para-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="104e165a-8492-4230-ad5e-b50ef6d5aba2" eId="hauptteil-1_buch-1_para-1_bezeichnung-1"><akn:marker name="1" GUID="276e00bf-8a3e-4ae3-ae86-136c22abfeef" eId="hauptteil-1_buch-1_para-1_bezeichnung-1_zaehlbez-1" />§ 1</akn:num>
          <akn:heading GUID="dd735f0c-7213-4b61-8901-b982d574553c" eId="hauptteil-1_buch-1_para-1_überschrift-1">Anwendung in Absätzen und Aufzählungen</akn:heading>
          <akn:paragraph GUID="e5ff6e2e-4ef9-40b7-a9fe-94486309e8f7" eId="hauptteil-1_buch-1_para-1_abs-1">
            <akn:num GUID="acc7ea3d-cc3e-4541-9350-3e823b4795b3" eId="hauptteil-1_buch-1_para-1_abs-1_bezeichnung-1"><akn:marker name="1" GUID="e06808a1-b648-4562-b74e-0b418eed8264" eId="hauptteil-1_buch-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
            <akn:content GUID="d689e436-8890-4df2-ba02-ced8f9565523" eId="hauptteil-1_buch-1_para-1_abs-1_inhalt-1">
              <akn:p GUID="ecd911e1-4b21-4571-9db2-caa70b4f6a18" eId="hauptteil-1_buch-1_para-1_abs-1_inhalt-1_text-1">Dieses Gesetz findet Anwendung auf alle definierten Struktur und Gliederungsebenen.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="6de01720-488f-45e1-bbad-39b5a810e6c6" eId="hauptteil-1_buch-1_para-1_abs-2">
            <akn:num GUID="161e1e19-7f8a-4772-a845-57e80fda9d5d" eId="hauptteil-1_buch-1_para-1_abs-2_bezeichnung-1"><akn:marker name="2" GUID="1f3f2ed4-cfff-4067-ab66-658e55466c47" eId="hauptteil-1_buch-1_para-1_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
            <akn:content GUID="6141ee06-9498-4c20-b720-a458ec45eba1" eId="hauptteil-1_buch-1_para-1_abs-2_inhalt-1">
              <akn:p GUID="6c3549e8-d7e0-4270-a831-508a1bfb18c5" eId="hauptteil-1_buch-1_para-1_abs-2_inhalt-1_text-1">Die Berechnung der Anwendung erfolgt nach folgender Formel: </akn:p>
              <akn:foreign GUID="0caafe15-6a72-43a2-b217-e07a3d7690b4" eId="hauptteil-1_buch-1_para-1_abs-2_inhalt-1_exmarkup-1">
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
          <akn:paragraph GUID="829ff310-a716-4c51-a1c8-809fa278eaa5" eId="hauptteil-1_buch-1_para-1_abs-3">
            <akn:num GUID="05487656-77f3-4aed-acb3-d8c38c1c4893" eId="hauptteil-1_buch-1_para-1_abs-3_bezeichnung-1"><akn:marker name="3" GUID="739ad485-253c-4f6b-b5d2-2c63f5a932bb" eId="hauptteil-1_buch-1_para-1_abs-3_bezeichnung-1_zaehlbez-1" />(3)</akn:num>
            <akn:list GUID="bd7b64aa-eebe-4630-858e-1c9a9530e2aa" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1">
              <akn:intro GUID="e282eee1-3233-4724-b776-477e00e3ee3a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_intro-1">
                <akn:p GUID="d48853c4-1c43-4b2d-8b9b-221a96348130" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_intro-1_text-1">Die Bestimmungen dieses Gesetzes gelten insbesondere für:</akn:p>
              </akn:intro>
              <akn:point GUID="aa63a962-55b1-4478-99f7-25e8778ca805" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1">
                <akn:num GUID="91dfc6eb-ac3c-4d47-ae26-09ed89c39c62" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="37ab20ce-97ce-43ef-afd2-638b6ea1454b" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
                <akn:list GUID="25a4692a-5743-4e45-bac6-3a0bc18babf0" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1">
                  <akn:intro GUID="6227f929-a66b-40db-a284-d7bbfbba9ea1" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1">
                    <akn:p GUID="931db155-1643-41df-af6c-c65ef9b9c808" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_intro-1_text-1">Gliederungen im Regelungstext wie zum Beispiel</akn:p>
                  </akn:intro>
                  <akn:point GUID="e407dbd3-3b67-41b6-836f-a28431acff17" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-a">
                    <akn:num GUID="8c7dbdc0-e8ad-46ea-a0e7-8219607a913a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-a_bezeichnung-1"><akn:marker name="a" GUID="4063f566-2e0f-471c-b6ea-7720a777d055" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" />a)</akn:num>
                    <akn:content GUID="406e624b-6672-48ec-9442-d9a637709436" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-a_inhalt-1">
                      <akn:p GUID="3d97774d-e07a-4a9f-a8e9-87d88a97e342" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-a_inhalt-1_text-1">Bücher,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="7bb528b5-c7af-4063-bc67-5c5b8e097f8c" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b">
                    <akn:num GUID="a2173120-2d57-4fba-bacc-a32be17c32da" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b_bezeichnung-1"><akn:marker name="b" GUID="6c4d3e4a-dcf8-4110-a15c-7211a233bc0a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" />b)</akn:num>
                    <akn:content GUID="5889b17a-8c03-4e4d-9191-e81e4a7fc577" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b_inhalt-1">
                      <akn:p GUID="98971529-8183-4423-84a0-ec27b37dff5d" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-b_inhalt-1_text-1">Teile,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="8cfde2b7-24c8-4dc3-a7b0-2cf6f5995c80" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-c">
                    <akn:num GUID="e275d052-d1de-469e-8265-0150672b3f99" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-c_bezeichnung-1"><akn:marker name="c" GUID="e74a5717-be01-4914-af5e-63888a178676" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" />c)</akn:num>
                    <akn:content GUID="4f1445b5-5c41-4142-9fe4-14082daad33c" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-c_inhalt-1">
                      <akn:p GUID="6879af35-0b71-4ae8-9202-5faaf100a152" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-c_inhalt-1_text-1">Kapitel,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="a813dc1d-f3dd-4442-9cd6-af0dc433dde9" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-d">
                    <akn:num GUID="cd4d60d6-76e1-477c-911e-7e221bffa8ca" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-d_bezeichnung-1"><akn:marker name="d" GUID="409c700a-4713-43ee-bfc6-21be8bd56c74" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-d_bezeichnung-1_zaehlbez-1" />d)</akn:num>
                    <akn:content GUID="b348c506-b38d-4d34-a3f3-4cf8dcf125cc" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-d_inhalt-1">
                      <akn:p GUID="78fd9e67-383c-4f87-aa7e-06e6a5d1e887" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-d_inhalt-1_text-1">Unterkapitel,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="fe17c2e5-f07e-461a-886a-2f7bb1b65461" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-e">
                    <akn:num GUID="10c4bff9-8ed4-40f0-ab65-97d498b4aec6" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-e_bezeichnung-1"><akn:marker name="e" GUID="4c3d532f-209b-41db-964c-1d39e76280d6" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-e_bezeichnung-1_zaehlbez-1" />e)</akn:num>
                    <akn:content GUID="3a49a36b-6a84-45cd-a78f-40f833872ca4" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-e_inhalt-1">
                      <akn:p GUID="56999aee-0003-4d6d-a4bc-4f96222c8754" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-e_inhalt-1_text-1">Abschnitte,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="b18200eb-a5b9-4deb-86ea-85552a535e1a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f">
                    <akn:num GUID="6750b1c9-0601-4146-8b54-ca49ddc0aad2" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f_bezeichnung-1"><akn:marker name="f" GUID="fb311374-a54a-4c2c-b5f5-d73246830c25" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f_bezeichnung-1_zaehlbez-1" />f)</akn:num>
                    <akn:content GUID="60101cf8-80fb-4d7a-b901-a32dd5c60241" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f_inhalt-1">
                      <akn:p GUID="5668b6e2-5316-4f3c-b69a-e776b68953ee" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-f_inhalt-1_text-1">oder Unterabschnitte.</akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
              <akn:point GUID="2b2e3aff-b6f5-416e-b33b-580c245b8845" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2">
                <akn:num GUID="d238a1bd-79c0-42f9-b296-48a32e5a90c3" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="34894018-e823-4857-bb27-47cda6bd606a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                <akn:list GUID="42d5f215-3873-4ffa-b715-38e2664198b0" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1">
                  <akn:intro GUID="b715aa2f-6c62-4aa7-a3a5-b9142a9778e2" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1">
                    <akn:p GUID="1b5e63b1-6b18-42ad-b50d-eed3e8b9c271" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_intro-1_text-1">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                  </akn:intro>
                  <akn:point GUID="6d307694-2145-40e1-90b9-34f7b627e7aa" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-a">
                    <akn:num GUID="48c2bafd-54c4-4e24-a292-19910257b724" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-a_bezeichnung-1"><akn:marker name="a" GUID="bc406144-b91e-4bed-ad9f-6eb5f93ca58a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" />a)</akn:num>
                    <akn:content GUID="b5b0b480-5991-4033-a36c-fbbb7cb775bc" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-a_inhalt-1">
                      <akn:p GUID="92ce014f-8724-4b63-9eab-19f90026c779" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-a_inhalt-1_text-1">Überschriften,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="5d605c7d-e355-4ebb-aa91-e7858b17003a" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-b">
                    <akn:num GUID="33a848f5-33bb-4784-81c5-305275952094" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1"><akn:marker name="b" GUID="e77ace5d-f5e8-4fc5-ac51-684c207c966c" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" />b)</akn:num>
                    <akn:content GUID="a0429ea1-90d0-45e8-8922-eaefd34deb7f" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-b_inhalt-1">
                      <akn:p GUID="d5835f32-c4f6-47c3-b7ad-5b47d0cf9e82" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-b_inhalt-1_text-1">Zeilen,</akn:p>
                    </akn:content>
                  </akn:point>
                  <akn:point GUID="64fb8111-cb36-43bc-b42f-84e478683109" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-c">
                    <akn:num GUID="42548fb0-d1c1-46b8-90a6-6bba7628c016" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1"><akn:marker name="c" GUID="0b2a396b-a27d-423a-b973-d93efe4183e5" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" />c)</akn:num>
                    <akn:content GUID="a82767d8-316a-4252-b52e-ed0b0219a587" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-c_inhalt-1">
                      <akn:p GUID="f29282a0-f091-4857-8097-ab75f769288b" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-c_inhalt-1_text-1">oder Zellen.</akn:p>
                    </akn:content>
                  </akn:point>
                </akn:list>
              </akn:point>
              <akn:wrapUp GUID="60a9393c-da39-45d1-a789-4c0bd4e9230b" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_schlusstext-1">
                <akn:p GUID="520d8c54-6e45-4e07-88f3-56f6c741f500" eId="hauptteil-1_buch-1_para-1_abs-3_untergl-1_schlusstext-1_text-1">In besonderen Fällen können auch Teile der Präambel, Inhaltsübersicht, Anlagen- oder Stammformverweise geändert werden.</akn:p>
              </akn:wrapUp>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="162dc967-07e6-4ac4-829f-a79e756c7e46" eId="hauptteil-1_buch-1_para-1_abs-4">
            <akn:num GUID="ffebfd14-77a7-4f65-ab74-956c07dc5d9c" eId="hauptteil-1_buch-1_para-1_abs-4_bezeichnung-1"><akn:marker name="4" GUID="308bb675-0cde-4eaa-9570-31e660ad4c2d" eId="hauptteil-1_buch-1_para-1_abs-4_bezeichnung-1_zaehlbez-1" />(4)</akn:num>
            <akn:content GUID="c4bccd98-c7f1-4b98-8ca7-a0f2572d1ee4" eId="hauptteil-1_buch-1_para-1_abs-4_inhalt-1">
              <akn:p GUID="b8f6450d-d877-499f-a2a5-370d81c92bbc" eId="hauptteil-1_buch-1_para-1_abs-4_inhalt-1_text-1">Alle staatlichen und kommunalen Verwaltungen sind verpflichtet, die in diesem Gesetz festgelegten Maßnahmen zum Ersetzen von Strukturen und Gliederungseinheiten umzusetzen.</akn:p>
            </akn:content>
          </akn:paragraph>
          <akn:paragraph GUID="40d46aca-0515-427a-a8cb-f33656f42a64" eId="hauptteil-1_buch-1_para-1_abs-5">
            <akn:num GUID="c7ea9d71-4f9a-4425-9244-0c1c2b078fcf" eId="hauptteil-1_buch-1_para-1_abs-5_bezeichnung-1"><akn:marker name="5" GUID="22166437-b77f-4104-aadc-b8ddca4bc711" eId="hauptteil-1_buch-1_para-1_abs-5_bezeichnung-1_zaehlbez-1" />(5)</akn:num>
            <akn:list GUID="8bfff8a7-f083-47b1-a697-52a693f8ab65" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1">
              <akn:intro GUID="a9e5b492-c9c4-4871-a6d6-0cbd43fdf24f" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_intro-1">
                <akn:p GUID="f46adb3d-dcfd-4864-932b-ff948a3e7352" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_intro-1_text-1">Jede Verwaltungseinheit hat sicherzustellen, dass:</akn:p>
              </akn:intro>
              <akn:point GUID="7973f712-76ea-4437-887a-9b49b7b520fb" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-1">
                <akn:num GUID="8bf3d468-2cf8-4d20-9816-1aa9f7eede3f" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="303f8e21-fd61-4b7e-93b1-4a8bbf7626c9" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
                <akn:content GUID="f583edbe-e1d6-4c43-ab1d-150a2ffc3e2c" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="afa394b2-e688-4761-a136-b7715a8b5909" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-1_inhalt-1_text-1">alle Mitarbeiterinnen und Mitarbeiter angemessen geschult und weitergebildet werden,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="c829ed84-24f9-432a-bf39-b17c3729460f" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-2">
                <akn:num GUID="986f7080-c429-4bec-aa72-e38f1db7b571" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="1e217a51-80ad-45bc-9af4-e9fc06b19ef1" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                <akn:content GUID="3d20448d-9c18-428a-a5a6-c2c24304bc0e" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="fd9daf92-2843-4fab-88dd-c9d8bb9174e5" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-2_inhalt-1_text-1">die eingesetzten Technologien und Verfahren regelmäßig überprüft und bei Bedarf aktualisiert werden,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="bca05894-3a24-40c8-bcfb-4f170fad54f2" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-3">
                <akn:num GUID="2a9c7f15-2dd5-45b1-b3f4-da8b7d63aea3" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="aaacc87e-a71a-47aa-9637-f269aaef1a29" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
                <akn:content GUID="15744540-acf1-462d-b0dd-dd81bbdc40ca" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="ceb53121-46d3-48b8-9c1f-3286d83e5080" eId="hauptteil-1_buch-1_para-1_abs-5_untergl-1_listenelem-3_inhalt-1_text-1">ein kontinuierlicher Verbesserungsprozess etabliert wird, um die Effizienz und Effektivität der Verwaltung zu steigern.</akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
        </akn:article>
        <akn:article GUID="4c36cac1-8079-4ab5-8b30-117496e5acd9" eId="hauptteil-1_buch-1_para-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8f853443-d3b7-4040-b3e6-1c5e0bd59b9a" eId="hauptteil-1_buch-1_para-2_bezeichnung-1"><akn:marker name="2" GUID="9a0acbf0-c3e0-4faa-b20d-704636c1f951" eId="hauptteil-1_buch-1_para-2_bezeichnung-1_zaehlbez-1" />§ 2</akn:num>
          <akn:heading GUID="336c97ab-8f2d-48f4-a809-178bbf1cd3f0" eId="hauptteil-1_buch-1_para-2_überschrift-1">Anwendung in Tabellen</akn:heading>
          <akn:paragraph GUID="c5043cc7-bb6b-4c97-978c-155d404a0632" eId="hauptteil-1_buch-1_para-2_abs-1">
            <akn:num GUID="740c983f-f1d5-4196-8715-9b76a797c3c4" eId="hauptteil-1_buch-1_para-2_abs-1_bezeichnung-1"><akn:marker name="1" GUID="c2bec62b-b97d-47b7-aa64-30445a5f3f4c" eId="hauptteil-1_buch-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
            <akn:content GUID="4471f722-04cb-4986-813e-67087309d0b8" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1">
              <akn:p GUID="c25072b0-5b8b-411f-b8f6-01281ebf5c0b" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_text-1">Folgende Strukturelemente eines Regelungstextes können mittels aenderungZitatStruktur verändert werden:</akn:p>
              <akn:table border="0" GUID="dd4c64da-68ef-4125-bb64-050dd11bd68c" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1">
                <akn:tr GUID="33f6fd58-f713-4020-a61a-6cae9e15baa8" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1">
                  <akn:th style="width:200" GUID="90fd60ce-9ec8-4751-bb3c-1c672569e5ee" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1">
                    <akn:p GUID="f35a7912-1f2d-4b53-a3c0-9dd985229a82" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Fachliche Klasse </akn:p>
                  </akn:th>
                  <akn:th style="width:200" GUID="39cd0e89-eb74-4a07-9e7f-05d4edef5af0" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                    <akn:p GUID="d2ec3798-e1f9-4d6a-9f5d-fd2c6529441e" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">AKN-Entsprechung </akn:p>
                  </akn:th>
                </akn:tr>
                <akn:tr GUID="5a89d7ac-a84e-428b-95ba-fb7335e258fa" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2">
                  <akn:td style="text-align:right" GUID="4b56f337-4a50-4a22-9423-280e55b1d9a1" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1">
                    <akn:p GUID="4eea0d77-4159-4a8d-ac86-4cee85b90ffb" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1_text-1">dokumentenkopfTitel</akn:p>
                  </akn:td>
                  <akn:td GUID="c8f4215d-169f-4f6f-b83c-696ca6c26dfd" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2">
                    <akn:p GUID="f1f6c9ec-7ab3-4220-a5ff-f347bad4e828" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2_text-1">akn:longTitle</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="a991db7f-f1ac-4781-bf1a-cb036b806618" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3">
                  <akn:td style="text-align:right" GUID="2f30595d-4798-45c8-b58d-6e5df6ae059e" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                    <akn:p GUID="da841f9d-0936-43ec-99f0-9ad30e553008" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">ermaechtigungsnormen</akn:p>
                  </akn:td>
                  <akn:td GUID="6527a4a8-8f65-4157-ad3f-c97d6005f7ce" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                    <akn:p GUID="f658ebda-15c5-410a-a28e-a4978ba1a46f" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">akn:citations</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="f40503d0-c683-4d50-842f-2d19ecbcb5e8" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4">
                  <akn:td style="text-align:right" GUID="cb62b54b-babc-4219-8562-e12bf3680707" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1">
                    <akn:p GUID="bb8510c8-6a93-4db8-8417-254ca06c915f" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1">praeambel</akn:p>
                  </akn:td>
                  <akn:td GUID="d7958bd6-eace-4d91-8263-cbb16bd60ba4" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2">
                    <akn:p GUID="1fc1ce1f-bbdb-4f16-8196-95c26cc5cce9" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1">akn:recitals</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="599ad3ad-88a1-4249-a793-99c10f52fb3b" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5">
                  <akn:td style="text-align:right" GUID="ad6c5b7a-36dd-48e3-a30b-7711fe5f8052" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1">
                    <akn:p GUID="f49c31d4-4d84-43c6-931d-be45b8ad7384" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1">praeambelInhalt</akn:p>
                  </akn:td>
                  <akn:td GUID="ba6836a8-e50c-4f59-887b-9aea2b14c563" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2">
                    <akn:p GUID="6524f0f9-992d-4c30-b576-da6d42ac7243" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1">akn:recital</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="c0a6a0b0-a4c4-4ec9-9440-6c6104652310" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6">
                  <akn:td style="text-align:right" GUID="f2d15f3e-6c54-4f91-ba72-ce8aa6268744" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1">
                    <akn:p GUID="477c0165-6fcb-4d5c-a676-d87cc1b25f98" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1">verzeichniscontainer</akn:p>
                  </akn:td>
                  <akn:td GUID="3c0964c4-4152-46e8-a500-8b23ce90153d" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2">
                    <akn:p GUID="98067c96-4c16-4f56-8f6b-6aaca1eb657e" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1">akn:blockContainer</akn:p>
                  </akn:td>
                </akn:tr>
                <akn:tr GUID="7ce0be4d-fd15-4e0c-9d80-707d333b24c5" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7">
                  <akn:td style="text-align:right" GUID="3d8f04c3-0bb0-4a6f-9587-6ffbb1af4aff" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1">
                    <akn:ul GUID="ab46e079-1927-4d2c-8540-9d9493de0862" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1">
                      <akn:li value="•" GUID="9b1364dc-b5cd-4a0e-a9b8-e81f53ecd971" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-1">
                        <akn:p GUID="296a9794-8c56-464f-a8ff-5f816b35fcfe" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-1_text-1">gliederungBuch</akn:p>
                      </akn:li>
                      <akn:li value="•" GUID="07d0d8d4-c49a-4674-8f1b-dd8f00fbf0d2" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-2">
                        <akn:p GUID="cdc8b322-8629-4af4-b8c6-c90897bd5704" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-2_text-1">gliederungTeil</akn:p>
                      </akn:li>
                      <akn:li value="•" GUID="5c82762b-d5f3-41e7-a7d6-90c84089412a" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-3">
                        <akn:p GUID="8402a074-1351-4497-aaf1-89b49f957205" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-3_text-1">gliederungKapitel</akn:p>
                      </akn:li>
                      <akn:li value="•" GUID="0a5adbdd-382f-4ed2-b618-233ddc0403e8" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-4">
                        <akn:p GUID="297a90f8-6904-42fd-b944-8adc36d0802a" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-4_text-1">gliederungUnterkapitel</akn:p>
                      </akn:li>
                    </akn:ul>
                  </akn:td>
                  <akn:td GUID="7b672cbe-6f7a-4d21-ab0d-63ef4b0a479b" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2">
                    <akn:ol GUID="6a16e988-9b05-4af5-8fb0-1673c16c7aad" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1">
                      <akn:li value="1" GUID="7a4afa38-2747-4f61-ad44-c01aa1f47ac3" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-1">
                        <akn:p GUID="bad18ef9-57a8-4c37-b803-5355a9384afb" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-1_text-1">akn:book</akn:p>
                      </akn:li>
                      <akn:li value="2" GUID="a75153c0-0807-4a1e-a894-bc3622be1d9d" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-2">
                        <akn:p GUID="b9e11d78-aa60-4d03-8449-d45b3eb0524f" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-2_text-1">akn:part</akn:p>
                      </akn:li>
                      <akn:li value="3" GUID="875c0462-fc8c-49ff-90b0-9b89fb1ddd4a" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-3">
                        <akn:p GUID="643d833d-6ed6-4416-9e67-df6175ea9297" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-3_text-1">akn:chapter</akn:p>
                      </akn:li>
                      <akn:li value="4" GUID="8607dc53-b864-45d7-b7f8-d96d7abd80ad" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-4">
                        <akn:p GUID="cb895b10-98cc-4a16-be5b-7d6c17263fcc" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-4_text-1">akn:subchapter</akn:p>
                      </akn:li>
                    </akn:ol>
                  </akn:td>
                </akn:tr>
              </akn:table>
              <akn:p GUID="5b06d8ee-e918-4376-871a-5b36cdb3219e" eId="hauptteil-1_buch-1_para-2_abs-1_inhalt-1_text-2">Die Tabelle ist nur ein Auszug aus der Spezifikation und erhebt keinen Anspruch auf Vollständigkeit.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
      <akn:book GUID="7ced0227-a342-405e-a29c-3f2aeca57b54" eId="hauptteil-1_buch-2">
        <akn:num GUID="407cbe29-91f4-416e-8d2d-05ce124c623e" eId="hauptteil-1_buch-2_bezeichnung-1"><akn:marker name="2" GUID="ba88d3e9-80f4-4be2-a6d2-0f648c9c6e6e" eId="hauptteil-1_buch-2_bezeichnung-1_zaehlbez-1" />2. Buch</akn:num>
        <akn:heading GUID="d82b4f90-487a-4f92-b3af-67a7b778bb3a" eId="hauptteil-1_buch-2_überschrift-1">Beispiele für Strukturen</akn:heading>
        <akn:chapter GUID="786c34af-047c-4e03-881b-3104241f2093" eId="hauptteil-1_buch-2_kapitel-1">
          <akn:num GUID="e5c861dd-dc27-40a4-8471-a79fb7d6ced0" eId="hauptteil-1_buch-2_kapitel-1_bezeichnung-1"><akn:marker name="1" GUID="0778909f-06fd-4a2d-b750-554077cfcd30" eId="hauptteil-1_buch-2_kapitel-1_bezeichnung-1_zaehlbez-1" /></akn:num>
          <akn:heading GUID="0035e452-84cc-47ed-96f5-928be77e1b27" eId="hauptteil-1_buch-2_kapitel-1_überschrift-1">Implementierung</akn:heading>
          <akn:article GUID="124ed2cf-0734-4e90-8ded-26d88ba5fc9b" eId="hauptteil-1_buch-2_kapitel-1_para-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="863d1daa-4bb0-479e-917f-50c64a487c40" eId="hauptteil-1_buch-2_kapitel-1_para-3_bezeichnung-1"><akn:marker name="3" GUID="9ad66de1-7833-4c5c-8f80-315411db4817" eId="hauptteil-1_buch-2_kapitel-1_para-3_bezeichnung-1_zaehlbez-1" />§ 3</akn:num>
            <akn:heading GUID="940e9c4e-161e-4e1c-a410-cd2a3cd65cdf" eId="hauptteil-1_buch-2_kapitel-1_para-3_überschrift-1">Implementierung der neuen Strukturen</akn:heading>
            <akn:paragraph GUID="f61015ee-74cf-47ff-ac97-1aac9f3136e2" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-1">
              <akn:num GUID="86d775d9-a8d7-4f5f-8209-04bba841e907" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-1_bezeichnung-1"><akn:marker name="1" GUID="d6b51cb2-0631-461f-b012-cd9820e7b0d5" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
              <akn:content GUID="6d35767d-745c-4a47-a23c-9617e8028b9a" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-1_inhalt-1">
                <akn:p GUID="d67055e6-15ed-49a6-8999-7b1fba4841fb" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-1_inhalt-1_text-1">Die Implementierung der in diesem Gesetz vorgesehenen neuen Strukturen und Gliederungseinheiten erfolgt schrittweise und in enger Abstimmung mit den betroffenen Verwaltungseinheiten.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="0f901c0e-b213-42b9-b3da-63ad88b250c7" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2">
              <akn:num GUID="1f2ba0b6-18da-4bc9-91fe-aabe104c6af6" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_bezeichnung-1"><akn:marker name="2" GUID="fed90b37-4452-4223-ae5c-cfc52e007f9f" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
              <akn:list GUID="ea667012-e361-42c4-809d-8854f2ed4da6" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1">
                <akn:intro GUID="8b236976-3469-4263-895e-4f279e97f724" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_intro-1">
                  <akn:p GUID="f88f5b95-28da-46c8-9c05-6fcc84b20c89" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_intro-1_text-1">Zur Unterstützung der Implementierung wird eine zentrale Koordinierungsstelle eingerichtet, die die folgenden Aufgaben übernimmt:</akn:p>
                </akn:intro>
                <akn:point GUID="e729b66d-7fa3-4eb4-8c16-07e1d51f8ebd" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-1">
                  <akn:num GUID="15681be1-6bc6-4f89-ae1d-6784e3a8907b" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="f8a0241a-0f1d-446e-a5e3-b7b27cf8fe0b" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
                  <akn:content GUID="916c9b13-6486-4010-818c-e43755d40c08" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="952b166d-0d71-4793-9bd3-21e8531a5949" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">Erstellung eines detaillierten Implementierungsplans, der die einzelnen Schritte und Zeitrahmen festlegt,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="d4792db6-47c3-430d-ac3e-9dce6b40ed41" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-2">
                  <akn:num GUID="e9e424b7-44bf-4e41-80cf-ab0f1bb9c82f" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="32fb7d44-c8e6-4a1c-9ee2-40330c338a6f" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                  <akn:content GUID="970bb94b-1ebf-4cbb-ab9b-9c5d287772c1" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="c28f32e0-1efa-455a-817c-439faefa18ba" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">Bereitstellung von Ressourcen und Schulungen für die betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="7aae333a-0e7b-406a-97e5-d4ce5615f22a" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3">
                  <akn:num GUID="1f171620-1079-4ab5-bd85-30de98ac7107" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="24aec440-b466-4b53-9d08-f290de7ffee5" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
                  <akn:content GUID="bd92f48c-944e-4238-9256-02e8276c3808" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="4d9bea9e-3c33-40df-83da-9d5709327d7d" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">Beratung und Unterstützung der Verwaltungseinheiten bei der Umsetzung der neuen Strukturen und Prozesse,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="271a6e62-0c85-47e7-a03a-787de96feb2e" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3">
                  <akn:num GUID="b0cb1259-63d7-4cce-9aad-9c359102dbb3" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="7012a5c1-c6cd-4d4c-b461-d53f58a59799" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
                  <akn:content GUID="ba810a82-4b6d-498f-9937-53822de8d173" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="69088a50-760d-433b-9b3b-1d970965d3dc" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">Überwachung und Bewertung des Fortschritts der Implementierung und Anpassung des Plans bei Bedarf.</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:paragraph>
            <akn:paragraph GUID="05093fbb-bc4e-4f3d-b89a-e38368d60217" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-3">
              <akn:num GUID="f1db9c1f-6097-4456-8219-3a523d1b201c" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-3_bezeichnung-1"><akn:marker name="3" GUID="a3d5242f-bf3b-4d8f-a6cf-3ac4c1847140" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-3_bezeichnung-1_zaehlbez-1" />(3)</akn:num>
              <akn:content GUID="1c771420-63f6-452e-af87-139f6eb5d67d" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-3_inhalt-1">
                <akn:p GUID="78eed4f3-6adf-435d-be68-de4fa8a21c65" eId="hauptteil-1_buch-2_kapitel-1_para-3_abs-3_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
          <akn:article GUID="af57a792-7960-438f-ab52-c39781730550" eId="hauptteil-1_buch-2_kapitel-1_para-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="c2707ee7-c833-4651-862a-4060e22be2f0" eId="hauptteil-1_buch-2_kapitel-1_para-4_bezeichnung-1"><akn:marker name="4" GUID="906ba0b4-4ec6-4b76-b6f6-88a3b5998d77" eId="hauptteil-1_buch-2_kapitel-1_para-4_bezeichnung-1_zaehlbez-1" />§ 4</akn:num>
            <akn:heading GUID="7e411859-66de-4ebf-a788-8a87f2561beb" eId="hauptteil-1_buch-2_kapitel-1_para-4_überschrift-1">Finanzielle Unterstützung</akn:heading>
            <akn:paragraph GUID="1540c03e-72bc-4edb-9c75-0faa2461f7d0" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-1">
              <akn:num GUID="fe9c05b9-9392-48b3-95af-894e3ec6c16e" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-1_bezeichnung-1"><akn:marker name="1" GUID="b3c91ba7-2c77-49d6-b32a-709d43103dd3" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
              <akn:content GUID="085f17e6-eb15-48bf-9573-e8b1e6447c43" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-1_inhalt-1">
                <akn:p GUID="3985e834-8044-45fe-8ef7-fc94ab141c6c" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-1_inhalt-1_text-1">Zur Finanzierung der Maßnahmen nach diesem Gesetz stellt der Bund den Ländern und Kommunen zweckgebundene Finanzmittel zur Verfügung.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="a4b1e6c6-0565-4a5f-8deb-82e123b40ad9" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2">
              <akn:num GUID="c0f5d74c-9273-4bb3-a38f-6e0030acb51f" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_bezeichnung-1"><akn:marker name="2" GUID="0a894dca-a19a-4942-a985-be1b5f00e71c" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
              <akn:list GUID="50f8db22-12dc-494d-82e4-11f4cd1578e1" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1">
                <akn:intro GUID="7f4f2b9e-4297-4baa-badd-f7152a4f4080" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_intro-1">
                  <akn:p GUID="2b90e9af-d1b8-4ebe-886c-5280c1bc7495" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_intro-1_text-1">Die Höhe der Finanzmittel wird jährlich im Bundeshaushalt festgelegt und richtet sich nach:</akn:p>
                </akn:intro>
                <akn:point GUID="f79e151e-9a65-45d6-a573-86fba957cea5" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-1">
                  <akn:num GUID="13b49196-caa2-4198-beec-b1308cee4cef" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="3b750e50-7e84-47bc-9b5b-7a8e46cb9083" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
                  <akn:content GUID="f5c9d2d1-925d-431e-be08-eefba237fe5e" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="76b1fdaf-f9b3-45d3-ad97-694fa08abdf0" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-1_inhalt-1_text-1">dem Umfang der erforderlichen Maßnahmen in den jeweiligen Verwaltungseinheiten,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="31ebe8e3-6e16-4f3b-88b2-7abc21abc9d3" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2">
                  <akn:num GUID="fff7cfe4-7f7d-4e62-a9f9-102d180c7a3a" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="03682f89-f171-49d2-8ed2-292a1b2ba448" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                  <akn:content GUID="fd982005-f8e3-49d5-a474-afef7d18d756" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="b4e30d2c-00d3-41fa-b96d-630d5b79d450" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-2_inhalt-1_text-1">der Anzahl der betroffenen Mitarbeiterinnen und Mitarbeiter,</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="c69618f2-149a-463d-90b8-498a95574bb9" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3">
                  <akn:num GUID="c05f8ec4-23db-44da-8346-5f34769f074e" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="68de971a-4568-4476-96e1-75a92b11a5af" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
                  <akn:content GUID="730c850f-7360-4be3-b9bd-43457d064b83" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="85bdc50e-afa3-4eab-976d-91c642c8cb4c" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-2_untergl-1_listenelem-3_inhalt-1_text-1">den spezifischen regionalen Anforderungen und Besonderheiten. </akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:paragraph>
            <akn:paragraph GUID="ad760523-d320-41a6-be13-61746d65b9b6" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3">
              <akn:num GUID="b98e90b5-6cb0-4b60-929b-588bea1a0578" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3_bezeichnung-1"><akn:marker name="3" GUID="9b76d3ea-e8ad-4e12-b01e-423d37c7f704" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3_bezeichnung-1_zaehlbez-1" />(3)</akn:num>
              <akn:content GUID="b659512f-11b9-49d6-8e98-7974ba9d39bd" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3_inhalt-1">
                <akn:p GUID="9503bad3-301a-4f36-af90-d17b2e0b65bd" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3_inhalt-1_text-1">Die Verwendung der Finanzmittel ist zweckgebunden und muss im Rahmen der jährlichen Berichterstattung nach <akn:ref href="#hauptteil-1_buch-2_kapitel-1_para-5" GUID="443cd856-bae6-470c-8e5f-b22d1f62cf95" eId="hauptteil-1_buch-2_kapitel-1_para-4_abs-3_inhalt-1_text-1_ref-1">Artikel 5</akn:ref> detailliert nachgewiesen werden.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
          <akn:article GUID="556d5740-71d9-4c67-bf8d-4b67eab04673" eId="hauptteil-1_buch-2_kapitel-1_para-5" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="546a3bb6-0f04-4890-a835-8f99d67d010e" eId="hauptteil-1_buch-2_kapitel-1_para-5_bezeichnung-1"><akn:marker name="5" GUID="018f0d71-2eff-4f37-a590-93471b453b88" eId="hauptteil-1_buch-2_kapitel-1_para-5_bezeichnung-1_zaehlbez-1" />§ 5</akn:num>
            <akn:heading GUID="948a5265-3ac1-43ba-8862-d3a7893413df" eId="hauptteil-1_buch-2_kapitel-1_para-5_überschrift-1">Evaluierung und Anpassung</akn:heading>
            <akn:paragraph GUID="3fb65334-c8c2-49b6-886b-ef8c06a1cacf" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-1">
              <akn:num GUID="a9c32af4-9960-44c7-a00f-9122e46d7214" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-1_bezeichnung-1"><akn:marker name="1" GUID="581f681a-908a-40a6-a975-52b4c6426709" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
              <akn:content GUID="eec02832-8af4-4e3d-8529-d84d460585c6" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-1_inhalt-1">
                <akn:p GUID="8d361d3d-f8e6-4adc-8ace-cf1e72584bcb" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-1_inhalt-1_text-1">Die Wirksamkeit der durch dieses Gesetz eingeführten Maßnahmen wird regelmäßig durch unabhängige Gutachter überprüft.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="b3399d37-04a7-41b1-8dc3-ef1f0ba9aa15" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-2">
              <akn:num GUID="07680e28-b66a-468c-bd21-be2300cbbd45" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-2_bezeichnung-1"><akn:marker name="2" GUID="4fa3dce0-d532-4036-b71e-946b142bc2e9" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
              <akn:content GUID="76da6af0-94a8-40d2-8926-908bdaa56d7d" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-2_inhalt-1">
                <akn:p GUID="0e365a0f-2381-45b8-816c-20610f3d324f" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-2_inhalt-1_text-1">Die erste Evaluierung erfolgt zwei Jahre nach Inkrafttreten dieses Gesetzes, weitere Evaluierungen folgen alle drei Jahre.</akn:p>
              </akn:content>
            </akn:paragraph>
            <akn:paragraph GUID="3b2e7f7c-babf-4ab6-a329-a4017659cde0" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-3">
              <akn:num GUID="80d56e95-869a-43e5-9bc4-2cea32eff1c7" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-3_bezeichnung-1"><akn:marker name="3" GUID="af311800-8519-4d64-bc9f-b31b0633fdfd" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-3_bezeichnung-1_zaehlbez-1" />(3)</akn:num>
              <akn:content GUID="753c9514-1a54-414c-9117-50d8dfa55c71" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-3_inhalt-1">
                <akn:p GUID="8681866d-7711-4a90-bb18-1afd414c1475" eId="hauptteil-1_buch-2_kapitel-1_para-5_abs-3_inhalt-1_text-1">Basierend auf den Ergebnissen der Evaluierungen kann der Gesetzgeber Anpassungen und Verbesserungen dieses Gesetzes vornehmen, um die angestrebten Ziele effektiver zu erreichen.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
        </akn:chapter>
      </akn:book>
      <akn:book GUID="89d3df71-bdbc-4674-8c6e-318acd064f35" eId="hauptteil-1_buch-3">
        <akn:num GUID="137a3d6d-e093-40f0-aa2c-2dcb16c74b4e" eId="hauptteil-1_buch-3_bezeichnung-1"><akn:marker name="3" GUID="296bfb70-d719-407b-b58c-16dfbf5c7fc8" eId="hauptteil-1_buch-3_bezeichnung-1_zaehlbez-1" />3. Buch</akn:num>
        <akn:heading GUID="d634a4fa-676e-4065-af5f-b04afd157cff" eId="hauptteil-1_buch-3_überschrift-1">Beispiele Teil I</akn:heading>
        <akn:article GUID="7a98e300-c1c9-47c1-b0f1-114c085463ca" eId="hauptteil-1_buch-3_para-6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="92de17ac-1f72-4932-9eac-d7087e33d481" eId="hauptteil-1_buch-3_para-6_bezeichnung-1"><akn:marker name="6" GUID="5124f3bf-4913-4c46-9f05-9cea44547d7d" eId="hauptteil-1_buch-3_para-6_bezeichnung-1_zaehlbez-1" />§ 6</akn:num>
          <akn:heading GUID="696c59ca-09ce-4ab1-a165-60ef132b4b5e" eId="hauptteil-1_buch-3_para-6_überschrift-1">Beispielartikel</akn:heading>
          <akn:paragraph GUID="0b2bbb4c-117d-44c4-946b-656e0dc93cae" eId="hauptteil-1_buch-3_para-6_abs-1">
            <akn:num GUID="c08fb9b1-bbc7-4ce9-beea-4fa36fa6c912" eId="hauptteil-1_buch-3_para-6_abs-1_bezeichnung-1"><akn:marker name="1" GUID="9559b9a7-efd0-4026-a804-7c4347656fbf" eId="hauptteil-1_buch-3_para-6_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
            <akn:content GUID="c337175d-a06a-4e81-a438-8b338cdd802b" eId="hauptteil-1_buch-3_para-6_abs-1_inhalt-1">
              <akn:p GUID="adc609b2-0171-4c85-83ae-fb07b84e6fa1" eId="hauptteil-1_buch-3_para-6_abs-1_inhalt-1_text-1">Die Koordinierungsstelle, die im Rahmen des Gesetzes eingerichtet wird, spielt eine zentrale Rolle bei der Umsetzung der Reformen. Sie fungiert als zentrale Anlaufstelle für alle staatlichen und kommunalen Verwaltungen, die Unterstützung bei der Neustrukturierung ihrer organisatorischen Einheiten benötigen. Zu den Hauptaufgaben der Koordinierungsstelle gehören die Erstellung detaillierter Implementierungspläne, die Bereitstellung von Ressourcen und Schulungen sowie die Beratung der Verwaltungseinheiten bei der Einführung neuer Technologien und Prozesse. Zudem überwacht die Koordinierungsstelle den Fortschritt der Reformen und passt die Maßnahmen bei Bedarf an, um eine effiziente und effektive Umsetzung sicherzustellen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
      <akn:book GUID="76df0530-c77a-4335-86b0-9a9151b1e46c" eId="hauptteil-1_buch-4">
        <akn:num GUID="f783b2f0-4473-4794-9cc3-9e5617b3f67b" eId="hauptteil-1_buch-4_bezeichnung-1"><akn:marker name="4" GUID="87224d1c-6078-472c-80ad-ed59cf348881" eId="hauptteil-1_buch-4_bezeichnung-1_zaehlbez-1" />4. Buch</akn:num>
        <akn:heading GUID="457ccc63-05ce-4677-a9cc-bd244cdce140" eId="hauptteil-1_buch-4_überschrift-1">Beispiel Teil II</akn:heading>
        <akn:chapter GUID="e3c056bb-449a-4430-8052-d4eac7614967" eId="hauptteil-1_buch-4_kapitel-1">
          <akn:num GUID="0ffa799c-7545-4c98-b7ba-11471fb78cc5" eId="hauptteil-1_buch-4_kapitel-1_bezeichnung-1"><akn:marker name="1" GUID="68c9de8a-85e8-430d-9aa0-0780a978c142" eId="hauptteil-1_buch-4_kapitel-1_bezeichnung-1_zaehlbez-1" /></akn:num>
          <akn:heading GUID="7a6484b0-ded8-45d5-b16f-1574891dab47" eId="hauptteil-1_buch-4_kapitel-1_überschrift-1">Beispielkapitel</akn:heading>
          <akn:article GUID="1f46ba41-50a0-4c77-9041-cd3d918d2c42" eId="hauptteil-1_buch-4_kapitel-1_para-7" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:num GUID="da5f5eee-79d8-4057-bf02-bdc7c4121897" eId="hauptteil-1_buch-4_kapitel-1_para-7_bezeichnung-1"><akn:marker name="7" GUID="6a48acee-7d88-4539-992a-c7dd63498df5" eId="hauptteil-1_buch-4_kapitel-1_para-7_bezeichnung-1_zaehlbez-1" />§ 7</akn:num>
            <akn:heading GUID="95718678-008a-4441-afa4-0527a17ab363" eId="hauptteil-1_buch-4_kapitel-1_para-7_überschrift-1">Beispielartikel</akn:heading>
            <akn:paragraph GUID="860300e4-a96e-4fab-ab68-33da10b19677" eId="hauptteil-1_buch-4_kapitel-1_para-7_abs-1">
              <akn:num GUID="0c86397f-56cc-4d7b-8172-54ee31b052cf" eId="hauptteil-1_buch-4_kapitel-1_para-7_abs-1_bezeichnung-1"><akn:marker name="1" GUID="b8ac4a8a-990f-40b7-9512-d2913ccb45ea" eId="hauptteil-1_buch-4_kapitel-1_para-7_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
              <akn:content GUID="47e8b7c5-d8c9-4ebe-b262-acea52635811" eId="hauptteil-1_buch-4_kapitel-1_para-7_abs-1_inhalt-1">
                <akn:p GUID="933bf6c3-ca24-4155-a80e-080632840a4e" eId="hauptteil-1_buch-4_kapitel-1_para-7_abs-1_inhalt-1_text-1">Der Implementierungsplan ist ein zentraler Bestandteil des Gesetzes und dient als Leitfaden für die schrittweise Umsetzung der vorgesehenen Reformen. Dieser Plan wird von der zentralen Koordinierungsstelle in enger Zusammenarbeit mit den betroffenen Verwaltungseinheiten entwickelt. Er umfasst detaillierte Zeitpläne, klare Meilensteine und spezifische Maßnahmen, die zur Modernisierung der Strukturen notwendig sind.</akn:p>
              </akn:content>
            </akn:paragraph>
          </akn:article>
        </akn:chapter>
      </akn:book>
      <akn:book GUID="71e18ea7-8f69-45ce-9565-918e2211d41b" eId="hauptteil-1_buch-5">
        <akn:num GUID="0c09b417-e57a-4d08-a3df-51e9e2edbfea" eId="hauptteil-1_buch-5_bezeichnung-1"><akn:marker name="5" GUID="f780fa58-0a94-4e7a-89ec-1343f34232f9" eId="hauptteil-1_buch-5_bezeichnung-1_zaehlbez-1" />5. Buch</akn:num>
        <akn:heading GUID="c043ecb0-4389-4f78-b850-5229573e4790" eId="hauptteil-1_buch-5_überschrift-1">Übergangsregelungen und Geltungszeiten</akn:heading>
        <akn:article GUID="f74e0f1a-a9b8-4dcf-83ff-da99829b8009" eId="hauptteil-1_buch-5_para-8" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="8c2cca2e-e8c6-4d9c-987e-e95077d07943" eId="hauptteil-1_buch-5_para-8_bezeichnung-1"><akn:marker name="8" GUID="f32d144a-47fa-4f1e-8560-ea8595099225" eId="hauptteil-1_buch-5_para-8_bezeichnung-1_zaehlbez-1" />§ 8</akn:num>
          <akn:heading GUID="2c380b4d-2a92-4950-89b2-e501b46de3c5" eId="hauptteil-1_buch-5_para-8_überschrift-1">Übergangsregelungen</akn:heading>
          <akn:paragraph GUID="e46dc027-aefe-4bf9-af07-240a1706883c" eId="hauptteil-1_buch-5_para-8_abs-1">
            <akn:num GUID="ea8f0493-2a89-49a7-acb4-6481de112181" eId="hauptteil-1_buch-5_para-8_abs-1_bezeichnung-1"><akn:marker name="1" GUID="ee2504dc-e949-4419-b402-c4de20def384" eId="hauptteil-1_buch-5_para-8_abs-1_bezeichnung-1_zaehlbez-1" />(1)</akn:num>
            <akn:list GUID="c39f53ce-5600-44ef-a81c-c00d8a417c26" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1">
              <akn:intro GUID="172b1ad2-a3cd-4b97-b06b-5b3325237e01" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_intro-1">
                <akn:p GUID="12eee750-6089-4228-81b3-2e214fcc6c27" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_intro-1_text-1">Während der Übergangsphase, die bis zu fünf Jahre nach Inkrafttreten dieses Gesetzes dauern kann, gelten folgende Regelungen:</akn:p>
              </akn:intro>
              <akn:point GUID="c90c9b23-5c38-4dc0-9e5e-e625fd629f2e" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-1">
                <akn:num GUID="08d45688-c273-4dbb-b05a-d7fedec4248a" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="974a4c42-415c-4b59-8df7-64f75cdac1d1" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" />1.</akn:num>
                <akn:content GUID="625a0feb-4cfb-4617-9c8e-96252c20e4ac" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-1_inhalt-1">
                  <akn:p GUID="89371c1f-93ea-4a9b-ac4c-d48d4df5a27a" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">Bestehende Strukturen und Gliederungseinheiten dürfen parallel zu den neuen Einheiten weitergeführt werden, sofern dies zur Sicherstellung der Kontinuität der Verwaltungsabläufe erforderlich ist,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="193d1e66-cac4-442c-a9dc-3628e4294bbe" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-2">
                <akn:num GUID="57e063e4-9dd4-41ea-b207-d3eaca6a20cf" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="4f68b284-923d-4919-93e9-e0f1ef29d46d" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" />2.</akn:num>
                <akn:content GUID="b57e0bb0-7136-4b29-8526-f4485cbec1f6" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-2_inhalt-1">
                  <akn:p GUID="c2e08b00-0604-4626-ac80-70ca5795d15d" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">Verwaltungseinheiten, die Schwierigkeiten bei der Umsetzung der neuen Strukturen haben, können bei der zentralen Koordinierungsstelle Unterstützung beantragen,</akn:p>
                </akn:content>
              </akn:point>
              <akn:point GUID="4de4d9d7-2638-4902-b583-74a21173552d" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-3">
                <akn:num GUID="2da86f68-bb42-48b9-8143-5a58ed650373" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="d70a4825-f212-4c76-a356-4b9131d9243a" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" />3.</akn:num>
                <akn:content GUID="fcde9c7e-372b-4b4a-a8b7-47dee8a7f477" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-3_inhalt-1">
                  <akn:p GUID="08cfdd8e-5da8-4a2e-8dce-b03029d918c5" eId="hauptteil-1_buch-5_para-8_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">Mitarbeiterinnen und Mitarbeiter, deren Aufgaben sich durch die neuen Strukturen ändern, haben Anspruch auf Weiterbildungs- und Umschulungsmaßnahmen.</akn:p>
                </akn:content>
              </akn:point>
            </akn:list>
          </akn:paragraph>
          <akn:paragraph GUID="38f46bbf-9b8a-407e-a35b-cbb0dd30b865" eId="hauptteil-1_buch-5_para-8_abs-2">
            <akn:num GUID="acef7e7d-2a6e-4125-bcbc-1a622998d65d" eId="hauptteil-1_buch-5_para-8_abs-2_bezeichnung-1"><akn:marker name="2" GUID="1ce25411-1fd5-4383-87b7-42f793c7e439" eId="hauptteil-1_buch-5_para-8_abs-2_bezeichnung-1_zaehlbez-1" />(2)</akn:num>
            <akn:content GUID="7f8d72ff-327e-4fba-bf8f-71fc706cb90a" eId="hauptteil-1_buch-5_para-8_abs-2_inhalt-1">
              <akn:p GUID="73cc3748-de33-4f83-b2a9-756ca54ae74d" eId="hauptteil-1_buch-5_para-8_abs-2_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet, vollständig auf die neuen Strukturen und Gliederungseinheiten umzustellen.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
        <akn:article refersTo="geltungszeitregel" GUID="e1ea4bcc-fe9c-44de-8478-288220156ff3" eId="hauptteil-1_buch-5_para-9" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:num GUID="70c3f946-0f1e-486d-bb26-1abee3e9cf43" eId="hauptteil-1_buch-5_para-9_bezeichnung-1"><akn:marker name="9" GUID="3b4ba74c-c0c1-4795-b891-d85cc036b3f5" eId="hauptteil-1_buch-5_para-9_bezeichnung-1_zaehlbez-1" />§ 9</akn:num>
          <akn:heading GUID="a25192d9-4e56-42a6-9e94-7500685523fd" eId="hauptteil-1_buch-5_para-9_überschrift-1">Inkrafttreten</akn:heading>
          <akn:paragraph GUID="c1893fbc-081f-436d-8d82-33417a44293d" eId="hauptteil-1_buch-5_para-9_abs-1">
            <akn:num GUID="4c634a60-9680-46b1-ac23-a0f0ca2c750f" eId="hauptteil-1_buch-5_para-9_abs-1_bezeichnung-1"><akn:marker name="1" GUID="d8d816b4-40fc-444a-991b-80dca36b6366" eId="hauptteil-1_buch-5_para-9_abs-1_bezeichnung-1_zaehlbez-1" /></akn:num>
            <akn:content GUID="cc2700df-62d9-41a9-94d8-4caac51402ab" eId="hauptteil-1_buch-5_para-9_abs-1_inhalt-1">
              <akn:p GUID="64ae21cd-4d8a-45ae-9344-009103c3e983" eId="hauptteil-1_buch-5_para-9_abs-1_inhalt-1_text-1">Dieses Gesetz tritt <akn:date date="1002-01-02" refersTo="inkrafttreten-datum" GUID="24589ea7-c00c-4468-87eb-576ba9b37865" eId="hauptteil-1_buch-5_para-9_abs-1_inhalt-1_text-1_datum-1">am Tag nach der Verkündung</akn:date> in Kraft.</akn:p>
            </akn:content>
          </akn:paragraph>
        </akn:article>
      </akn:book>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('c7fa8ba0-34c9-43ed-87ad-9bdf177126fc', 'eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1', NULL);
