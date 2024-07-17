DELETE FROM announcements WHERE id = 'c7fa8ba0-34c9-43ed-87ad-9bdf177126fc';
DELETE FROM norms WHERE guid = '63ef9358-8755-46e4-bf6a-21f379014597';
DELETE FROM norms WHERE guid = 'c4166ebb-b6df-4f61-8ac1-1d6399cc80ef';

-- Amending law
INSERT INTO norms (guid, eli, xml)
VALUES ('c4166ebb-b6df-4f61-8ac1-1d6399cc80ef', 'eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                                                                                                                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta eId="meta-1" GUID="1d1d770a-9ff0-45e6-878d-911c98dd9cd4">
      <akn:identification eId="meta-1_ident-1" GUID="4dcd65df-8560-4d97-9325-e32deba192cf" source="attributsemantik-noch-undefiniert">
        <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="7ae2285c-9020-4036-9bce-4e3be460a29a">
          <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="cc954333-2c08-4f2d-800e-baab97a61115" value="eli/bund/bgbl-1/1002/10/regelungstext-1" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="57356a8b-5983-4efe-9f7f-7a507d23df13" value="eli/bund/bgbl-1/1002/10" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="88509c2e-eb73-466b-8388-009e23ece0d7" name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="ec0d1b3e-fc1e-472f-a4b2-0fc54b5d0224" date="1002-01-10" name="verkuendungsfassung" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="7c4cffcc-061a-4826-a605-efc024553b9c" href="recht.bund.de/institution/bundesregierung" />
          <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="60aab79e-4b89-4d48-99b6-529c474398c1" value="de" />
          <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="12ec247c-a819-47e0-8570-1b8942f40820" value="10" />
          <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="99698450-cd5f-4fcc-b2ca-c24bc6216ea5" value="bgbl-1" />
          <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="6f9b66b3-db19-4543-9db0-6a3bfe6a8929" value="regelungstext-1" />
        </akn:FRBRWork>
        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="b7a74512-05c5-48f4-af1e-11578e5ecec2">
          <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="a5dfab3c-a646-4633-9fc2-7496e14fb28f" value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="ef04bbf0-f502-4b53-9863-ed3193a4b1bd" value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="031754e0-3f61-4484-8185-8c59ff4244fa" name="aktuelle-version-id" value="c4166ebb-b6df-4f61-8ac1-1d6399cc80ef" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="fa481503-200a-4bee-95d8-eee4dfd31695" name="nachfolgende-version-id" value="5773cb41-adfe-47ca-b684-dde0c83c7b39" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="631e316b-540e-48ec-9532-08fa8e250ec8" href="recht.bund.de/institution/bundesregierung" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="46ba1df6-0764-4d9c-a5b3-e7a6df746230" date="1002-01-10" name="verkuendung" />
          <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="fdd84eea-0f30-4898-9689-00a122fd9dcb" language="deu" />
          <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="41e042d3-23f7-4142-94b2-1f7cb2e94322" value="1" />
        </akn:FRBRExpression>
        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="966416cc-1a99-4496-aaca-6a84a67cdc8e">
          <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="b98122db-6c85-4047-b910-5872f1f58d5d" value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1.xml" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="586db454-7dde-44de-b21e-290bfcf12da3" value="eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1.xml" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="c764890a-3801-481d-9171-b25a374c3fa2" date="1002-01-10" name="generierung" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="262a2241-a71c-4d1f-a97a-e77731a25194" href="recht.bund.de" />
          <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="a4ff1d85-5499-4089-bd1b-38e9c622dba3" value="xml" />
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="dcfa52f5-a67a-4306-8e37-8ad190788de9" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1002-01-10" source="attributsemantik-noch-undefiniert" refersTo="ausfertigung" type="generation" eId="meta-1_lebzykl-1_ereignis-1" GUID="d4e59051-fd39-4caf-bb5d-bdcd2dde7d1c" />
        <akn:eventRef date="1002-01-11" source="attributsemantik-noch-undefiniert" refersTo="inkrafttreten" type="generation" eId="meta-1_lebzykl-1_ereignis-2" GUID="25276ab9-c149-4fc9-9cef-0181244833e1" />
      </akn:lifecycle>
      <akn:analysis GUID="72cd555b-de7d-4d5e-ba2e-4dc50800400f" eId="meta-1_analysis-1" source="attributsemantik-noch-undefiniert">
        <akn:activeModifications GUID="ca13a0cc-8f37-42c7-920b-f0d2fb59c81c" eId="meta-1_analysis-1_activemod-1">
          <akn:textualMod GUID="e3bf66b0-20be-4624-a169-3e039c82c570" eId="meta-1_analysis-1_activemod-1_textualmod-1" type="substitution">
            <akn:source GUID="27ea9f31-b2c4-4676-be56-e11f66e1e290" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="a11bbebf-a4ce-4eec-9fbf-235c67a200ab" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml" />
            <akn:force GUID="6a886205-ba36-49e7-9ed4-64651834d621" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="caaf8130-ad4c-4356-b648-35c5df54786f" eId="meta-1_analysis-1_activemod-1_textualmod-2" type="substitution">
            <akn:source GUID="907a4e8c-cfa2-469a-8cf0-e9981daed1cb" eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="f16cdf54-e9e9-4927-aab0-cc95cc887617" eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_ernormen-1.xml" />
            <akn:force GUID="e9d88628-9659-4d7b-8440-6c0f9903d0e1" eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="9c42431b-96c0-46db-afd4-ceb9fbd56613" eId="meta-1_analysis-1_activemod-1_textualmod-3" type="substitution">
            <akn:source GUID="03f884ea-8b3d-43f7-a30d-5a3b525b40c4" eId="meta-1_analysis-1_activemod-1_textualmod-3_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="453e43cd-820d-4318-857e-1b332b99ddd5" eId="meta-1_analysis-1_activemod-1_textualmod-3_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_präambeln-1.xml" />
            <akn:force GUID="01f75234-e2bb-472f-8bcf-22b3cb677e4d" eId="meta-1_analysis-1_activemod-1_textualmod-3_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="ae8e4880-4385-4e54-9b7c-1337d8015d33" eId="meta-1_analysis-1_activemod-1_textualmod-4" type="substitution">
            <akn:source GUID="30406542-d2e5-41fb-81ae-da19efa66aae" eId="meta-1_analysis-1_activemod-1_textualmod-4_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="dc780027-452c-41eb-850c-af483bbdc2dc" eId="meta-1_analysis-1_activemod-1_textualmod-4_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_blockcontainer-1.xml" />
            <akn:force GUID="9a8da48a-b837-45ea-8395-09bc895df4b0" eId="meta-1_analysis-1_activemod-1_textualmod-4_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="9232fb7b-20ad-478f-8c04-a956e9b9d2b9" eId="meta-1_analysis-1_activemod-1_textualmod-5" type="substitution">
            <akn:source GUID="8aa0c8a5-63c3-446f-818b-3d8a27663a17" eId="meta-1_analysis-1_activemod-1_textualmod-5_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="332ef748-f2c0-4cb2-8b76-8266876d8bc4" eId="meta-1_analysis-1_activemod-1_textualmod-5_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-1.xml" />
            <akn:force GUID="e2b0a428-a6bc-4455-a605-6f3eecf5119a" eId="meta-1_analysis-1_activemod-1_textualmod-5_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="4bb7b3b5-c022-4df5-a14d-52ce66c330b1" eId="meta-1_analysis-1_activemod-1_textualmod-6" type="substitution">
            <akn:source GUID="4eae7856-ad4f-4897-b47f-8658b6b55c8b" eId="meta-1_analysis-1_activemod-1_textualmod-6_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="6b9d1419-0ac3-4443-baa2-c0b3be7caf5a" eId="meta-1_analysis-1_activemod-1_textualmod-6_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-2_inhalt-1_exmarkup-1.xml" />
            <akn:force GUID="8b83a895-60f7-4787-b259-0a7f0c078d03" eId="meta-1_analysis-1_activemod-1_textualmod-6_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
          <akn:textualMod GUID="2b4008ad-8432-4b2c-9fec-1cb1fe9e8ce3" eId="meta-1_analysis-1_activemod-1_textualmod-7" type="substitution">
            <akn:source GUID="e579beee-8194-4c33-9eb3-d0d08c0bb209" eId="meta-1_analysis-1_activemod-1_textualmod-7_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1" />
            <akn:destination GUID="5e2f3a4d-89c9-438d-842f-7e82b1b70a2d" eId="meta-1_analysis-1_activemod-1_textualmod-7_destination-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-2.xml" />
            <akn:force GUID="d23b859c-1f25-48fa-ab0e-2c7391df581e" eId="meta-1_analysis-1_activemod-1_textualmod-7_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
          </akn:textualMod>
        </akn:activeModifications>
      </akn:analysis>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="3f4c6b1a-7626-43f3-9bde-a5c6b5ec77a1" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="a1d55356-5204-4dbd-8da2-3938dca0dac7">
          <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-1" refersTo="geltungszeit" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="e0121343-fc18-4cc9-adf2-e053ab8ae34b" />
        </akn:temporalGroup>
        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="2300a585-4522-446d-8555-31462886d6e5">
          <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit" eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="3e1a2cfe-49f7-40b2-9c10-e960fae34de9" />
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>

    <akn:preface eId="einleitung-1" GUID="0c50befb-5451-4192-a052-e2efbd25347d">
      <akn:longTitle eId="einleitung-1_doktitel-1" GUID="f18e2df5-e8a3-4046-ad76-4753d6b9507c">
        <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="7d999f27-335d-4fa5-964f-9b761ebe4cdb">
          <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="214a4065-2b9f-4f02-9113-89787e9cf8b8">Erstes Gesetz zur Änderung des Strukturänderungsgesetzes</akn:docTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>

    <akn:preamble eId="preambel-1" GUID="fad0459d-f995-4f1e-8ec7-340e8a0da839">
      <akn:formula eId="preambel-1_formel-1" GUID="0e220f68-9fd9-465a-9222-c45413d82991" refersTo="eingangsformel" name="attributsemantik-noch-undefiniert">
        <akn:p eId="preambel-1_formel-1_text-1" GUID="9109bafb-dba9-4154-a073-e6d32530ac57"> Der <akn:organization eId="preambel-1_formel-1_text-1_org-1" GUID="0da4c1af-0d07-47bd-ad87-734e2357e2de" refersTo="attributsemantik-noch-undefiniert" title="Bundestag">Bundestag</akn:organization> hat das folgende Gesetz beschlossen:</akn:p>
      </akn:formula>
    </akn:preamble>

    <akn:body eId="hauptteil-1" GUID="e6720f3f-951d-45cc-8edd-b05480727c71">
      <akn:article eId="hauptteil-1_para-1" GUID="4d5d8b32-f818-4407-ae56-3bc283acf4fa" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="eingebundene-stammform">
        <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="f234ff66-06fa-4b72-8555-23a6559acf3d">
          <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="0642872b-5dbc-45ef-8f07-76a3c11e9abd" name="1" /></akn:num>
        <akn:heading eId="hauptteil-1_para-1_überschrift-1" GUID="68b539af-5c6a-47ef-99d8-8f4574dfa729">Artikel 1</akn:heading>
        <akn:paragraph eId="hauptteil-1_para-1_abs-1" GUID="de615c8e-7045-4067-8d79-fcb80714e476">
          <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="d789f737-763e-40ce-b12a-aa68a1c6e9b6">
            <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="5aef21a6-8619-43ba-8ab5-55bafda33517" name="1" />
          </akn:num>
          <akn:list eId="hauptteil-1_para-1_abs-1_untergl-1" GUID="f3e39c51-760e-418f-b8c5-57170d3480a3">
            <akn:intro eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1" GUID="40067ab2-e780-40db-bd02-2813f1036ac8">
              <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1" GUID="5ea6d74d-6330-43d0-9f25-5569c627a549"> Das <akn:affectedDocument eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="6af56003-a242-480c-a51c-c56f7642b4b7" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1">Strukturänderungsgesetz vom 1. Januar 1002 (BGBl. 1002 I Nr. 1)</akn:affectedDocument> wird wie folgt geändert:</akn:p>
            </akn:intro>
            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1" GUID="8f5e1c26-85e9-4609-a619-4eb1881582aa">
              <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1" GUID="ebb6f4f1-2da0-4ba8-ad6d-77d131603494">
                <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" GUID="2b6b9149-dc18-4024-b99c-79ab86a27cb7" name="1" />1.</akn:num>
              <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1" GUID="d9a3cdef-a1ac-40c7-bbee-c44b2c202b1b">
                <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1" GUID="4d2ed12d-9234-4a9b-aa85-ef356b33a540">
                  <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" refersTo="aenderungsbefehl-ersetzen"> Der <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml" GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1">Titel</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:longTitle eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1" GUID="0505f7b3-54c8-4c9d-b456-cd84adfb98f1">
                        <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1" GUID="6ad3f708-b3be-4dbf-b149-a61e72678105">
                          <akn:docTitle eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_doctitel-1" GUID="ab481c1a-db58-4b6a-886c-1e9301952c34">Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen</akn:docTitle>
                          <akn:shortTitle GUID="820e7af3-fd8c-4409-949a-1e40ec2cc8e6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_kurztitel-1"> (Strukturänderungsgesetz) </akn:shortTitle>
                        </akn:p>
                      </akn:longTitle>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2" GUID="5159b313-7578-4f36-bcd9-08461e65f27c">
              <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1" GUID="efbf64f3-08ad-4bfd-a88f-be0f9203108b">
                <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="0656dc02-9a5f-4770-b265-ac817c8807d0" name="2" />2.</akn:num>
              <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1" GUID="3d1ec988-e011-4a90-bdfa-0f52164c5eec">
                <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="967d4a7f-ccd5-41ee-adab-d7323e4c4f09">
                  <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="2be5dd9e-3e74-4c47-a302-4502d0f1254c" refersTo="aenderungsbefehl-ersetzen"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_ernormen-1.xml" GUID="04400a25-5dc7-40e4-9c2e-ad9c67fe48d4" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1">Ermächtigung</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="f9c4f89e-22f5-49f5-bdbf-04541ec34c13" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:citations eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1" GUID="76484d80-b068-4a0f-8824-5ec531c9fc80">
                        <akn:citation eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1_ernorm-1" GUID="f3fa42fb-9719-4fc8-8235-d88ce9ff6fb7">
                          <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_ernormen-1_ernorm-1_text-1" GUID="63ed510d-c024-4b6f-8dbd-b63153e0b3ea">Aufgrund der Spezifikation von Änderungsbefehlen können Strukturen und Gliederungseinheiten ersetzt werden. </akn:p>
                        </akn:citation>
                      </akn:citations>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3" GUID="c995f4f4-4219-405e-ac70-8735e2dcf90a">
              <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_bezeichnung-1" GUID="a650098b-cd82-4174-b0ca-c0a12296200a">
                <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1" GUID="ff91f0ea-cdbe-49eb-8b03-abf3b7a12c9a" name="3" />3.</akn:num>
              <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1" GUID="89226cd3-e206-4a1e-bbd9-238a8e73998e">
                <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1" GUID="c7b11acd-e66a-456f-a4d6-d28aa977ecd2">
                  <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1" GUID="d64fe480-3580-4da3-8ed4-769876ecf5a4" refersTo="aenderungsbefehl-ersetzen"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_präambeln-1.xml" GUID="b16dc941-a6a7-4bd7-b65b-77f365bc26bd" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_ref-1">Präambel</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="41552184-4898-42bf-8673-8dfcc06df3ae" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:recitals eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1" GUID="a741307a-ed30-4a99-8ea7-00c742c1d4d1">
                        <akn:heading eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_überschrift-1" GUID="678c3511-17fb-46a4-ba3e-5567f48465b4">Präambel</akn:heading>
                        <akn:recital eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_präambelinh-1" GUID="00e6ce61-eaaa-41b9-b34d-47e9e78eae0d">
                          <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-3_inhalt-1_text-1_ändbefehl-1_quotstruct-1_präambeln-1_präambelinh-1_text-1" GUID="ba0ed903-a454-4ddb-a0ba-deebe4c3ea69">Im Bewusstsein der Herausforderungen bei der Umsetzung von Änderungsbefehlen hat der Deutsche Bundestag das nachstehende Gesetz beschlossen.</akn:p>
                        </akn:recital>
                      </akn:recitals>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4" GUID="04a19415-316b-4bb2-a8d7-86d139cad3e6">
              <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_bezeichnung-1" GUID="23caf715-28d6-4f36-8fb4-9754fadef94d">
                <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_bezeichnung-1_zaehlbez-1" GUID="4d35d310-85ff-485f-992d-4be2c27ad55e" name="4" />4.</akn:num>
              <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1" GUID="73f1d11d-7b62-4160-b020-1787bbf4e912">
                <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1" GUID="cd026a00-80ed-42d2-ae05-fc4a0bb569dd">
                  <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1" GUID="4cef7af8-9296-4422-9251-4eac240d384e" refersTo="aenderungsbefehl-ersetzen"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/preambel-1_blockcontainer-1.xml" GUID="4bdd604b-1402-4413-a6b4-4f55482d902a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_ref-1">Inhaltsübersicht</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="42ba57e8-fe25-494f-be99-b42a9c5dceca" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                      <akn:blockContainer eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1" GUID="df13f34a-0f17-4ede-9b6f-eeda741edb2c" refersTo="inhaltsuebersicht">
                        <akn:heading eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_überschrift-1" GUID="5628fd2f-9914-4315-8a36-6e1270f176bc">Inhaltsübersicht</akn:heading>
                        <akn:toc eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1" GUID="ae6bd1b8-fd58-40ed-91d9-dc5b052e1638">
                          <akn:tocItem eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1_eintrag-1" GUID="d9361ca1-dc5b-4ef4-a547-21a17805f9a2" level="1" href="">
                            <akn:span eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-4_inhalt-1_text-1_ändbefehl-1_quotstruct-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1" GUID="a2d6d84c-ea62-4bca-8515-bc0f93b0d9ed">Neue Inhaltsübersicht.</akn:span>
                          </akn:tocItem>
                        </akn:toc>
                      </akn:blockContainer>
                    </akn:quotedStructure>
                  </akn:mod>
                </akn:p>
              </akn:content>
            </akn:point>
            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5" GUID="a8dc3914-85f8-4661-af89-7c5723c024a7">
              <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_bezeichnung-1" GUID="d3797736-8104-4aee-96c9-9b39168b8aa3">
                <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_bezeichnung-1_zaehlbez-1" GUID="30f71e76-0732-490e-b40e-96282d2c435c" name="5" />5.</akn:num>
              <akn:list GUID="83a2fb8b-fc93-4d8a-b353-e2670c4200e9" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1">
                <akn:intro GUID="37fbd50f-8833-4aa4-9ade-8b6269f5b707" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1">
                  <akn:p GUID="dc6d126a-c019-4495-a4b4-10869956dc3e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1_text-1"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2.xml" GUID="9979a407-44d8-4cef-a51a-717057b8d70c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_intro-1_text-1_ref-1">§ 2</akn:ref> wird wie folgt geändert:</akn:p>
                </akn:intro>
                <akn:point GUID="0b865178-7c63-4112-92a8-2cafa337b349" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a">
                  <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_bezeichnung-1" GUID="31168a4b-81ff-425a-9bce-e75a46ce6936">
                    <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_bezeichnung-1_zaehlbez-1" GUID="85c41b19-bb27-40d0-8c56-7bc6ff1b8bbf" name="a" />a)</akn:num>
                  <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1" GUID="06122377-6a36-46bb-9f0d-8a5117f64dbd">
                    <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1" GUID="581ab082-6bc4-4c9f-8368-c9508eb18b3a">
                      <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1" GUID="af2483d2-e64a-4217-bb7a-cd297cdac0ef" refersTo="aenderungsbefehl-ersetzen"><akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-1.xml" GUID="d5812441-185f-4e8a-beb0-067d433b1093" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_ref-1">Absatz 1</akn:ref> wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="8bfa7127-4a05-41d0-ac7b-b729f3d53e18" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:paragraph eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1" GUID="dd5a0f07-bb0c-4d37-b211-8681175cb2d3">
                            <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_bezeichnung-1" GUID="7124be75-25e4-4739-a6a1-a5be97757e4f">
                              <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="f87d5214-4fc2-48d8-8c9b-9a0875666c8b" name="1" />(1) </akn:num>
                            <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_inhalt-1" GUID="f5e3fa13-3ed9-41c4-a4ee-91db57abdd51">
                              <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_inhalt-1_text-1" GUID="fabee05a-1bba-4898-bd30-3472717fc013">Dieses Gesetz findet Anwendung auf fast alle definierten Struktur und Gliederungsebenen.</akn:p>
                            </akn:content>
                          </akn:paragraph>
                        </akn:quotedStructure>
                      </akn:mod>
                    </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="94ea482a-3e05-4194-ba04-d039775d5834" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b">
                  <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_bezeichnung-1" GUID="9b0cb37d-56ca-49a4-8255-4a6e126cbb42">
                    <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" GUID="59f06d63-73a3-406f-9857-aec81e1649e9" name="b" />b)</akn:num>
                  <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1" GUID="1945db7a-7db1-4e98-91dd-95f0acd05cc0">
                    <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1" GUID="8befe73a-7b94-41f7-89d6-2e81c0ebb453">
                      <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1" GUID="00f9c1da-643f-418f-9484-e65b8ea72edf" refersTo="aenderungsbefehl-ersetzen"> Die <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-2_inhalt-1_exmarkup-1.xml" GUID="8bbe8b42-1faa-4986-8311-908202f88d8a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_ref-1">Formel</akn:ref> in <akn:ref href="hauptteil-1_para-2_abs-2" GUID="0f127060-8c4f-46d8-b7e7-f7df7ade223f" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_ref-2">Absatz 2</akn:ref> wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="0677c8bb-e953-4c67-acb5-7ee0dc864533" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:foreign eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-b_inhalt-1_text-1_ändbefehl-1_quotstruct-1_exmarkup-1" GUID="1c657e5a-f938-4fcb-9d10-4730be379907">
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
                <akn:point GUID="6c8b4258-0797-41b7-83c5-3d716e0479fa" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c">
                  <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_bezeichnung-1" GUID="e6b145cd-d6ad-486c-aae4-2a643066e7b0">
                    <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" GUID="bf426539-29c5-4380-814e-a946a2711dd8" name="c" />c)</akn:num>
                  <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1" GUID="4c64154c-8fce-46eb-b97b-2886d99fef1e">
                    <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1" GUID="529ca863-c7b2-4f7d-b74e-7209b74b7d32">
                      <akn:mod eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1" GUID="d351ba17-8ac9-4c49-981c-7175b5575e99" refersTo="aenderungsbefehl-ersetzen">
                        <akn:ref href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-2.xml" GUID="9f512228-08c9-4278-a8e6-3edf5e189a8c" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_ref-1">Absatz 3 Nummer 2</akn:ref> wird ersetzt durch: <akn:quotedStructure endQuote="“" startQuote="„" GUID="b2fbc227-6aa8-49bc-8d07-e1004f2cd25e" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                          <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2" GUID="5b9b2a46-f9a9-4b29-885b-aef6a75e4474">
                            <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1" GUID="2e8c1cc6-5a17-48d9-b084-23d7691d4391">
                              <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="83c24d77-eb17-4bf4-970d-22f85d3ee592" name="2" />2.</akn:num>
                            <akn:list eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1" GUID="9766b5b5-b379-4768-befd-ae5bf2d1d843">
                              <akn:intro eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_intro-1" GUID="8b172a1f-9f58-4ffb-849e-d26f39a56f92">
                                <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_intro-1_text-1" GUID="64182da3-fa83-4e3d-896d-7085fdd555c1">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                              </akn:intro>
                              <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b" GUID="f9b3f75b-ea7f-493c-8fc1-a4731f4b6088">
                                <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1" GUID="92596b77-9e50-43a5-9b8a-6e095a05268b">
                                  <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_bezeichnung-1_zaehlbez-1" GUID="ce4b05ef-c522-4e71-8bce-277b1ded670a" name="b" />b)</akn:num>
                                <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_inhalt-1" GUID="497d8b76-e360-4fcb-b7cd-8726bcd03725">
                                  <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-b_inhalt-1_text-1" GUID="a4f02fa6-aca2-45ff-8e37-eeb88642ee43"> Zeilen, </akn:p>
                                </akn:content>
                              </akn:point>
                              <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c" GUID="212317f9-6f40-4c18-9fea-a042473793a5">
                                <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1" GUID="ccad2a48-fd23-47dc-bdbb-3e48ec4e7c7c">
                                  <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_bezeichnung-1_zaehlbez-1" GUID="9e5b1ebb-10df-4e80-90de-e9babbe39fd6" name="c" />c)</akn:num>
                                <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_inhalt-1" GUID="5e406eeb-4f84-45be-b473-ccfd8891904f">
                                  <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-c_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-2_untergl-1_listenelem-c_inhalt-1_text-1" GUID="cd03e1ae-b5c1-4def-bc10-6990bc93680c">oder Zellen.</akn:p>
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
          </akn:list>
        </akn:paragraph>
      </akn:article>

      <akn:article eId="hauptteil-1_para-2" GUID="628b7653-68d5-483f-b265-be0a6a5c7aea" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
        <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="41f85859-9eed-4b05-8d59-4d92983fdd0c">
          <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="b47cfc67-7544-44a6-ad27-5da7ea7f053d" name="2" /></akn:num>
        <akn:heading eId="hauptteil-1_para-2_überschrift-1" GUID="e696f540-586c-4725-ae49-654bb28918ab">Inkrafttreten</akn:heading>
        <akn:paragraph eId="hauptteil-1_para-2_abs-1" GUID="c0a4c9d2-dc0d-4b59-be88-7663f1e32bae">
          <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="df00a213-4a1d-44fa-9df5-27979493b0f1">
            <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="f03f382c-d9f5-4a24-bf3b-b3695d57e86a" name="1" />
          </akn:num>
          <akn:content eId="hauptteil-1_para-2_abs-1_inhalt-1" GUID="0f24a464-a987-4eab-be22-2badb8cc3321">
            <akn:p eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1" GUID="de1b70eb-c26f-4223-bfa4-ed0ffd974314"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1_datum-1" GUID="28f26c1e-2329-4082-887a-a3c1b19a1b7b" date="1002-01-02" refersTo="inkrafttreten-datum">am Tag nach der Verkündung</akn:date> in Kraft. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');

-- Target law
INSERT INTO norms (guid, eli, xml)
VALUES ('63ef9358-8755-46e4-bf6a-21f379014597', 'eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd
                                                                                                                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta eId="meta-1" GUID="e4589a5f-34d9-4a3b-bcdb-558e1c36b69b">
      <akn:identification eId="meta-1_ident-1" GUID="f3e17ed2-7f8b-4658-858b-c73bb1c6ca24" source="attributsemantik-noch-undefiniert">
        <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="12f4b363-48d8-4fd3-aee2-a5f525ea14b4">
          <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="b7d16407-5d18-4af1-aa67-731a7a7758ac" value="eli/bund/bgbl-1/1002/1/regelungstext-1" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="6130a9a2-85bd-483c-b326-31095830276e" value="eli/bund/bgbl-1/1002/1" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="43863563-e2e9-4d93-9249-239a45127274" name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5fd62927-fb0e-4167-b073-2b2577ac0ecf" date="1002-01-01" name="verkuendungsfassung" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="a551da99-a71a-4179-8f53-4a31a7e64a30" href="recht.bund.de/institution/bundesregierung" />
          <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="e386ef70-4f71-4590-b940-093207a82c47" value="de" />
          <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="af7bebd5-85de-42d9-b5b0-519798d65112" value="1" />
          <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="1d5fac51-3d5e-4f07-9f79-92c32dd42cbb" value="bgbl-1" />
          <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="6d2b4986-4728-4e62-9326-fb337a6a43ea" value="regelungstext-1" />
        </akn:FRBRWork>
        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="67247b7c-2285-490d-a0f2-a1c805f2f140">
          <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="e3e82a45-e271-4f24-936d-94cae114db58" value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="a6997dd0-1a12-43df-a02a-7c2d70b29f1a" value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="43bd6e06-3017-470c-a91f-18318154885a" name="aktuelle-version-id" value="63ef9358-8755-46e4-bf6a-21f379014597" />
          <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="1700bef9-6d4d-48c1-b0bc-f066dd9d3bf8" name="nachfolgende-version-id" value="e4e034dd-61b9-43aa-b4f9-b778dc6adfda" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="7f11bff8-c09d-40d0-a0bb-3f2542514116" href="recht.bund.de/institution/bundesregierung" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="31cc9f31-f6b7-45d2-ac42-383d694afa9a" date="1002-01-01" name="verkuendung" />
          <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="256f67ea-2aa5-4e3f-959e-807b3f7dd934" language="deu" />
          <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="202c3b86-b8a4-4cd5-ac59-f8dd36151c57" value="1" />
        </akn:FRBRExpression>
        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="321497df-3c8b-4168-a50a-ac359fc62ea3">
          <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="b73c60f8-03fb-4a98-9bd1-990ff334dbd9" value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1.xml" />
          <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="3346b7be-37e3-4e26-8cd5-1d4fb70def3b" value="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1.xml" />
          <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="ef351e86-937d-455d-81d0-8b7688470a4d" date="1002-01-01" name="generierung" />
          <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="c3ca80c3-4273-43a9-8072-94659d7b57cb" href="recht.bund.de" />
          <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="80b4c27c-9973-43e7-986a-1b36d88309dd" value="xml" />
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="58320eb1-0dba-47a3-9195-5d1e09ab2d56" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1002-01-01" source="attributsemantik-noch-undefiniert" refersTo="ausfertigung" type="generation" eId="meta-1_lebzykl-1_ereignis-1" GUID="cd6cd3ee-7628-43ca-862a-bb9201bf0102" />
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1" GUID="290ce609-5578-4403-a49e-54c5b5bceefa" />
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="3311a6c3-e607-49c1-9c45-580bd47c9e97" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="91740dce-e847-4c2b-9eaa-4829080f84ce">
          <akn:timeInterval start="#meta-1_lebzykl-1_ereignis-1" refersTo="geltungszeit" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="e782673b-9174-4723-ae56-3570fbe9c777" />
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>

    <akn:preface eId="einleitung-1" GUID="da7b34fb-69e8-4ede-abd1-a047ea34f576">
      <akn:longTitle eId="einleitung-1_doktitel-1" GUID="f5ae76f0-b1bc-47e5-9431-8c972acdc95d">
        <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="b899a440-1b6c-4347-b444-35e88aff1593">
          <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="a3fe0152-c96a-4fe2-a37d-d4aa5614df4a">Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten</akn:docTitle>
          <akn:shortTitle GUID="6dd93158-ed25-4498-b751-406cd2844489" eId="einleitung-1_doktitel-1_text-1_kurztitel-1"> (Strukturänderungsgesetz) </akn:shortTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>

    <akn:preamble eId="preambel-1" GUID="71ae51ad-9f44-4638-844c-c942b7a69d8b">
      <akn:citations eId="preambel-1_ernormen-1" GUID="c053cf65-d974-441d-b4c5-a2c731b19dd4">
        <akn:citation eId="preambel-1_ernormen-1_ernorm-1" GUID="5e3b534d-c1b1-4fc5-ae75-2b655128333b">
          <akn:p eId="preambel-1_ernormen-1_ernorm-1_text-1" GUID="23574046-b394-4229-adfa-00b6a4ea6be7">Die Bundesregierung wird ermächtigt, durch Rechtsverordnung ohne Zustimmung des Bundesrates die zur Durchführung dieses Gesetzes erforderlichen Rechtsvorschriften zu erlassen. Hierzu gehören insbesondere Regelungen über die organisatorische Umsetzung der in diesem Gesetz festgelegten Maßnahmen, die Festlegung technischer Standards sowie Bestimmungen zur Überwachung und Kontrolle der Einhaltung der gesetzlichen Vorgaben. </akn:p>
        </akn:citation>
      </akn:citations>
      <akn:recitals eId="preambel-1_präambeln-1" GUID="51e46c5b-c4bb-45d1-acf1-25e0a53964b3">
        <akn:heading eId="preambel-1_präambeln-1_überschrift-1" GUID="7e84b63e-37c4-460a-b67f-7866a653353f">Präambel</akn:heading>
        <akn:recital eId="preambel-1_präambeln-1_präambelinh-1" GUID="43fc850d-4e0b-4912-a4b2-3a1093aec7e3">
          <akn:p eId="preambel-1_präambeln-1_präambelinh-1_text-1" GUID="d6bf49e6-aac7-404a-bd04-a342b59149a9">Im Bewusstsein der Verantwortung gegenüber den Bürgerinnen und Bürgern, sowie im Bestreben, die Effizienz und Transparenz der staatlichen Strukturen zu erhöhen, hat der Deutsche Bundestag das nachstehende Gesetz beschlossen. Ziel dieses Gesetzes ist es, bestehende Strukturen und Gliederungseinheiten in Dokumenten unter Berücksichtigung moderner Technologien zu ersetzen, zu vereinheitlichen und zu optimieren. Dabei sollen sowohl die Interessen der Bürgerinnen und Bürger als auch die Anforderungen an eine zukunftsfähige und digitalisierte Verwaltung gleichermaßen gewahrt werden.</akn:p>
          <akn:p eId="preambel-1_präambeln-1_präambelinh-1_text-2" GUID="f8a5e105-ead0-498b-90a1-8c620c518af2">Dieses Gesetz beruht auf den Grundsätzen der Rechtstaatlichkeit, der Wirtschaftlichkeit und der Bürgernähe. Es stellt sicher, dass die Anpassungen an Gesetzen und Verordnungen, transparent und effektiv durchgeführt werden.</akn:p>
        </akn:recital>
      </akn:recitals>
      <akn:blockContainer eId="preambel-1_blockcontainer-1" GUID="2ba7f606-03c7-4ec4-a59b-556c0ea71d48" refersTo="inhaltsuebersicht">
        <akn:heading eId="preambel-1_blockcontainer-1_überschrift-1" GUID="452faca7-0338-4e5e-998a-71ae39461177">Inhaltsübersicht</akn:heading>
        <akn:toc eId="preambel-1_blockcontainer-1_inhuebs-1" GUID="7b739da3-76cd-424f-a5cc-54aca2cadf57">
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1" GUID="3c30b450-5184-4fc3-9605-3ebed57b8e56" level="1" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1" GUID="0233dbe9-3262-43f9-9e12-2bfd4ae9a32b">Abschnitt 1</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-2" GUID="43378668-b4bd-4b49-9c57-9b9f9f714bd3">Allgemeine Vorschriften</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2" GUID="4f8a4ba6-899d-4fbc-834a-28e31ff1e81a" level="2" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-1" GUID="7c32fd7a-2620-477a-9957-f1a1d658b414">§ 1</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-2_span-2" GUID="1ec98a37-e0c1-4b59-b43d-7241e30fc925">Abfallwirtschaftliche Ziele</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3" GUID="55e0edcd-581b-410b-a0b1-19e95cbafbc1" level="2" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-1" GUID="e9b4bacd-f798-4fa0-bcbc-0dac6dd99173">§ 2</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-3_span-2" GUID="7daec9c6-59bc-4c22-8d94-cd3ed54bfde0">Anwendungsbereich</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4" GUID="63a969e9-d599-4092-b473-f5fdf4354fb4" level="2" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-1" GUID="c1d18001-5b86-44a4-a16e-f62bc1eead19">§ 3</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-4_span-2" GUID="e4c72c65-63a3-492a-ae82-f624b7e9537e">Begriffsbestimmungen</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5" GUID="5454c562-ab9b-4664-85be-79148724b0d0" level="1" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-1" GUID="df745f1a-83c4-48d1-8559-33685d4c2bab">Abschnitt 2</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-5_span-2" GUID="49058523-e45e-4b5f-b102-6a8d14cb5b2b">Pflichten beim Inverkehrbringen von Elektro- und Elektronikgeräten</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6" GUID="cd997e76-22b3-438b-8071-09326072f225" level="2" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-1" GUID="3267672a-fbff-45fb-b52e-107deabc3f09">§ 4</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-6_span-2" GUID="3721594c-add4-49a6-8a0b-cfd984dc0fbf">Produktionskonzeption</akn:span>
          </akn:tocItem>
          <akn:tocItem eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7" GUID="27a19912-53d5-4757-98e0-66e5ecaf55d6" level="2" href="">
            <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-1" GUID="afd13c7e-5c04-47e9-80cf-2534bae3dace">§ 5</akn:span>
                  <akn:span eId="preambel-1_blockcontainer-1_inhuebs-1_eintrag-7_span-2" GUID="648160b4-5820-4e34-aca7-2d955fcd3b9e">Einrichten der Gemeinsamen Stelle</akn:span>
          </akn:tocItem>
        </akn:toc>
      </akn:blockContainer>
    </akn:preamble>

    <akn:body eId="hauptteil-1" GUID="7d6a4198-6a65-4f9c-aeff-d9e57c361102">
      <akn:article eId="hauptteil-1_para-1" GUID="4e450050-0ada-4f9a-ab3e-34d48ed7dded" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="eingebundene-stammform">
        <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="11b74348-a016-45de-bf6b-4ec15bc96a96">
          <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="ba3fa73a-2f79-486e-b238-d69a7e534d5a" name="1" />§ 1</akn:num>
        <akn:heading eId="hauptteil-1_para-1_überschrift-1" GUID="adbfd292-9813-44c8-be67-4f2a57810e15">Gesetz zur Einbindung eines Regelungstextes in Stammform</akn:heading>
        <akn:componentRef eId="hauptteil-1_para-1_stfmverweis-1" GUID="45725af6-3346-4828-980d-9e010ce4abd3" src="01-06_instanz_01_regelungstext-eingebundene-stammform.xml" showAs="regelungstext-eingebundene-stammform" />
      </akn:article>

      <akn:article eId="hauptteil-1_para-2" GUID="bfc6170d-9ae5-49e6-bae9-c1255efe03b6" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="568e52b1-eeee-46d6-b5f0-c81a677fb275">
          <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="207fdb16-d646-4ec7-936e-4b0a0215acd3" name="2" /> § 2 </akn:num>
        <akn:heading eId="hauptteil-1_para-2_überschrift-1" GUID="85ad062d-4e0e-4730-9131-c6c89752a732"> Anwendung in Absätzen und Aufzählungen </akn:heading>

        <akn:paragraph eId="hauptteil-1_para-2_abs-1" GUID="cbf61857-bcd3-4ca3-b3f2-20e7ad0b8661">
          <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="fbd88274-37a4-43c5-b821-ef4b71b2ce80">
            <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="488d48dc-5621-4be9-801c-154c3814cce4" name="1" />(1) </akn:num>
          <akn:content eId="hauptteil-1_para-2_abs-1_inhalt-1" GUID="d9776e83-53f7-4363-8aee-56dd2130e85b">
            <akn:p eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1" GUID="70440b91-57ac-4951-8663-695ff0966f19">Dieses Gesetz findet Anwendung auf alle definierten Struktur und Gliederungsebenen.</akn:p>
          </akn:content>
        </akn:paragraph>

        <akn:paragraph eId="hauptteil-1_para-2_abs-2" GUID="f0730675-7e3f-45bb-a2ee-46c1d89ffd94">
          <akn:num eId="hauptteil-1_para-2_abs-2_bezeichnung-1" GUID="29201137-5d19-46bf-96ba-ee0855731641">
            <akn:marker eId="hauptteil-1_para-2_abs-2_bezeichnung-1_zaehlbez-1" GUID="39deda28-4526-4dd9-be07-d99f95d54705" name="2" />(2) </akn:num>
          <akn:content eId="hauptteil-1_para-2_abs-2_inhalt-1" GUID="1d16bd72-6671-4127-afd1-ed574c0260b9">
            <akn:p eId="hauptteil-1_para-2_abs-2_inhalt-1_text-1" GUID="6c34ea8a-808c-4f63-a233-348fdba174b8">Die Berechnung der Anwendung erfolgt nach folgender Formel: </akn:p>
            <akn:foreign eId="hauptteil-1_para-2_abs-2_inhalt-1_exmarkup-1" GUID="81b97a2e-a68c-486d-a6b6-7eee1b28e863">
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

        <akn:paragraph eId="hauptteil-1_para-2_abs-3" GUID="9707ddb3-6b7d-4d38-84fa-6f3d38aa6cd6">
          <akn:num eId="hauptteil-1_para-2_abs-3_bezeichnung-1" GUID="e88eccd4-3982-40f9-859c-a1dbeb041049">
            <akn:marker eId="hauptteil-1_para-2_abs-3_bezeichnung-1_zaehlbez-1" GUID="34ba6bde-f0c9-40ce-a503-bedaaf024248" name="3" />(3) </akn:num>
          <akn:list GUID="77d313f3-e315-4e7f-be96-425910ecfce9" eId="hauptteil-1_para-2_abs-3_untergl-1">
            <akn:intro eId="hauptteil-1_para-2_abs-3_untergl-1_intro-1" GUID="22ebfacf-261e-4eee-bedb-abbbd4d6b1ff">
              <akn:p GUID="fdfd2276-28cb-4024-9c84-2e91df5195fb" eId="hauptteil-1_para-2_abs-3_untergl-1_intro-1_text-1">Die Bestimmungen dieses Gesetzes gelten insbesondere für:</akn:p>
            </akn:intro>
            <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1" GUID="42f4f096-fba5-4b1a-bc4d-6121f65df406">
              <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_bezeichnung-1" GUID="1d789e82-5784-4be9-9073-d7b48ebef194">
                <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" GUID="925e7d6a-7ea6-4f45-b4b7-c81693daec44" name="1" />1.</akn:num>
              <akn:list eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a" GUID="c8e81179-ed0b-4bfe-8d3c-6be1e6033c16">
                <akn:intro eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_intro-1" GUID="2348ae40-4138-47a7-927a-ea372fa6ac38">
                  <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_intro-1_text-1" GUID="7f2242ae-101e-47b9-b5d2-56744245d60a">Gliederungen im Regelungstext wie zum Beispiel</akn:p>
                </akn:intro>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-a" GUID="30fb6904-1965-49fc-a829-f7821c8b2291">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-a_bezeichnung-1" GUID="2c944bf4-f610-425a-b0bf-fc14211bad91">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-a_bezeichnung-1_zaehlbez-1" GUID="d40dca54-8a7b-4024-aef1-f8a116654aa0" name="a" />a)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-a_inhalt-1" GUID="c5d96ead-a686-4179-8c70-5f5c53af0934">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-a_inhalt-1_text-1" GUID="4e3a68e1-a6dc-48a4-85ee-692473dd6231"> Bücher, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b" GUID="a847f9da-9a41-4d4e-beab-874fb6beec7f">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b_bezeichnung-1" GUID="a0419aca-7a16-4cb3-a57d-003ed60a4e69">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b_bezeichnung-1_zaehlbez-1" GUID="0fe58896-51b7-4264-97b6-c3b366268a3b" name="b" />b)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b_inhalt-1" GUID="2746d6e9-1b49-43fb-9054-0bc34c0ac0f2">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b_inhalt-1_text-1" GUID="e15cee5a-8ec9-4eee-afbe-2ee07c8fc5f9"> Teile, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-c" GUID="a878aadd-56bd-4c34-86ba-a0849c7bfbf3">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-c_bezeichnung-1" GUID="3c013250-64a7-4535-b757-3c6ac877cb94">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-c_bezeichnung-1_zaehlbez-1" GUID="9c6c63cc-c502-41c0-9a65-24b49aafc131" name="c" />c)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-c_inhalt-1" GUID="c5d5a246-43e4-40db-a91e-06a086d981a6">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-c_inhalt-1_text-1" GUID="f1ba5f60-e071-47d6-97d6-491d30be51cf">Kapitel, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-d" GUID="f2c786e3-3c26-4dc1-9599-1d522bcc8af9">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-d_bezeichnung-1" GUID="807913f9-728a-4a3f-a772-35b9fa2bcd25">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-d_bezeichnung-1_zaehlbez-1" GUID="494fbd20-bd1a-4bda-b49d-be44add49ba1" name="d" />d)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-d_inhalt-1" GUID="c6983d2a-af15-4e40-9c26-a378470c111d">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-d_inhalt-1_text-1" GUID="c57bf26a-9405-4c66-83ae-3b9bf55503e4">Unterkapitel, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-e" GUID="9f0d217e-4a6d-421d-9599-f1a879b63860">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-e_bezeichnung-1" GUID="e423719f-f908-4bb3-a290-32aa8fa63683">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-e_bezeichnung-1_zaehlbez-1" GUID="4c6b8150-4adc-4220-9f63-8cf2784f1076" name="e" />e)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-e_inhalt-1" GUID="f6977c7a-a812-4d72-9cff-da1293d7ae5e">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-e_inhalt-1_text-1" GUID="13e13f1f-ea29-42b2-b88c-7af9e5fa3366">Abschnitte, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f" GUID="f00ee52f-b58f-46d4-91e8-05de4a81718f">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f_bezeichnung-1" GUID="b09cb1f7-62c9-4563-9a0c-f9d4fb2cda8b">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f_bezeichnung-1_zaehlbez-1" GUID="dd6a6b5f-89a9-4b3b-8b6e-3643a6c6d528" name="f" />f)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f_inhalt-1" GUID="f1c373e0-1507-4ea3-9bd2-312bb8d5f592">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f_inhalt-1_text-1" GUID="e547d37f-1ceb-4de5-9a6b-8acc741fddce">oder Unterabschnitte.</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2" GUID="99159f32-63e4-4da6-80d0-407ea9bad874">
              <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_bezeichnung-1" GUID="fb3d9d69-af4e-402c-ae99-56a2777bbdbf">
                <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="ebfbc46d-6ea6-4237-b594-646f6b0110c3" name="2" />2.</akn:num>
              <akn:list eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a" GUID="52e88b8c-b605-49b2-a142-76614e7d7048">
                <akn:intro eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_intro-1" GUID="cef19ac0-b58a-49a7-bd56-cbcba149cab6">
                  <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_intro-1_text-1" GUID="36a5e8be-4f2a-4226-90b6-7280b48c66d8">Elemente zur Darstellung von Tabellen wie zum Beispiel</akn:p>
                </akn:intro>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-a" GUID="b5455df0-61fe-4644-8b97-121c1b3988d0">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-a_bezeichnung-1" GUID="7e242cff-afeb-4e80-b301-f7d1bc55ce15">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-a_bezeichnung-1_zaehlbez-1" GUID="cce256e5-d7dd-44c9-b71d-bc9817c3bfef" name="a" />a)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-a_inhalt-1" GUID="bf6c6b01-19dd-497a-b84d-8eda925fb9e5">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-a_inhalt-1_text-1" GUID="247a6350-eadd-457d-9ef1-c50e3ab056ae"> Überschriften, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-b" GUID="59b76042-31e3-41e5-9875-d887a10ce13c">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-b_bezeichnung-1" GUID="04c17bf2-b9bc-4387-b143-ac18cc55e0bd">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-b_bezeichnung-1_zaehlbez-1" GUID="f4a1cdd8-fc4e-42a5-9e2d-8f12a62900b0" name="b" />b)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-b_inhalt-1" GUID="d21f7fa8-943a-49c2-bb79-a2a8e9bb98ff">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-b_inhalt-1_text-1" GUID="e4ecb7b1-382d-418e-b36d-64bd0bf29901"> Zeilen, </akn:p>
                  </akn:content>
                </akn:point>
                <akn:point eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-c" GUID="81d88f81-0bbb-43a2-91fe-2b38762c9310">
                  <akn:num eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-c_bezeichnung-1" GUID="346c9516-c4cd-4884-a7d0-37ad7a7a576e">
                    <akn:marker eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-c_bezeichnung-1_zaehlbez-1" GUID="929b8a67-edbf-49d6-b623-c2466373e806" name="c" />c)</akn:num>
                  <akn:content eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-c_inhalt-1" GUID="5225e0b8-74c8-42bc-bc32-61df705ead79">
                    <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_listenelem-2_untergl-a_listenelem-c_inhalt-1_text-1" GUID="7371ea25-4a4c-46d4-8c67-4eb3da863ccd">oder Zellen.</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:wrapUp GUID="fb9193e9-cec8-4cf9-8339-71aa2d7239fb" eId="hauptteil-1_para-2_abs-3_untergl-1_schlusstext-1">
              <akn:p eId="hauptteil-1_para-2_abs-3_untergl-1_schlusstext-1_text-1" GUID="e58e82ff-beb0-480a-ba3e-adf1250698e8"> In besonderen Fällen können auch Teile der Präambel, Inhaltsübersicht, Anlagen- oder Stammformverweise geändert werden. </akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
      </akn:article>

      <akn:article eId="hauptteil-1_para-3" GUID="1be51a4a-f9ca-41c3-b255-183ddaebf165" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="5f9595ec-0d72-4563-8e05-e245317c8d64">
          <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" GUID="0086831f-7ce6-478d-808a-b25361c6b026" name="3" /> § 3 </akn:num>
        <akn:heading eId="hauptteil-1_para-3_überschrift-1" GUID="83ee0040-d389-4a69-9d91-d80a2442ece2"> Anwendung in Tabellen </akn:heading>

        <akn:paragraph eId="hauptteil-1_para-3_abs-1" GUID="2c762004-0463-414e-93c3-01598c8e6519">
          <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="f2ac24a6-6a1f-41fb-8fd5-380e63e301f9">
            <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="d245d7fa-3ed0-4b7c-b249-fbe270ef2784" name="1" /></akn:num>
          <akn:content eId="hauptteil-1_para-3_abs-1_inhalt-1" GUID="76713fcf-afac-4f86-8fe1-903050a7c39b">
            <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1" GUID="077810b3-5796-4b43-a8bc-b47414c865b6"> Folgende Strukturelemente eines Regelungstextes können mittels aenderungZitatStruktur verändert werden: </akn:p>
            <akn:table eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1" GUID="9b450a5f-ba43-4088-b4ab-92e358e914ea" border="0">
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1" GUID="1e43716f-b64a-4aca-bc39-bfadb2657e69">
                <akn:th eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1" GUID="9035b4d9-1682-4f28-8b77-209464555e6d" style="width:200">
                  <akn:p GUID="d15585de-744e-4dd9-bd17-8f93aeff5443" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Fachliche Klasse </akn:p>
                </akn:th>
                <akn:th eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2" GUID="dd686009-d4dd-4bc7-a51e-407a1130c40c" style="width:200">
                  <akn:p GUID="b1e0c172-e3e4-41e5-8bb3-eda56cb5e4a2" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">AKN-Entsprechung </akn:p>
                </akn:th>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2" GUID="612bcaf3-7f85-4999-a7c0-fca028c51993">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1" GUID="4a6e041c-df9e-4137-a9ce-d0bf40fbbe9f" style="text-align:right">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1_text-1" GUID="d82adbfe-f933-4c61-8185-cb5f0113f876">dokumentenkopfTitel</akn:p>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2" GUID="48164833-3c55-4627-9902-98cee075a499">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2_text-1" GUID="3afc5301-7879-4dd9-b8bc-c7f495bcd8da">akn:longTitle</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3" GUID="56c81c6f-ffc0-40d5-ac32-d34fec63a356">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1" GUID="4f342221-2eef-4ff0-9804-8d805452f6b8" style="text-align:right">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1" GUID="4d947a92-29da-4c35-9fb6-a3d462ce5127">ermaechtigungsnormen</akn:p>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2" GUID="8d0660d7-74da-495a-a683-dde825c3a964">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1" GUID="059a38eb-24eb-4544-a5e7-a9154018f6ba">akn:citations</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-4" GUID="f8cf0e64-93e2-45d3-849e-72b6e3f8fadf">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1" GUID="9cf3f1df-a214-4f97-9f0d-6a1301c29142" style="text-align:right">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-1_text-1" GUID="1396a17a-24ae-460a-98d0-96fc2ef21413">praeambel</akn:p>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2" GUID="b2878fcc-f3ce-449e-9a2a-15c786897a21">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-4_tabelleinh-2_text-1" GUID="6b77e4f4-f2de-45c7-bee6-f9442748c103">akn:recitals</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-5" GUID="d7ecbbdc-8d51-4b21-8e54-1eb50a304cb2">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1" GUID="1d54244e-a376-4f1c-bae1-d08d5bd213fd" style="text-align:right">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-1_text-1" GUID="64498d60-03aa-4e0f-afaa-2ee42990af72">praeambelInhalt</akn:p>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2" GUID="eb72efe3-2410-4d4e-be89-300375ad02db">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-5_tabelleinh-2_text-1" GUID="6d3b0e32-b5ea-46e2-92ce-a461a637b538">akn:recital</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-6" GUID="2af10492-8451-4b94-a640-8a6d81cd7052">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1" GUID="7bae2b81-6000-4ca4-b7a8-1fbcbca7377c" style="text-align:right">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-1_text-1" GUID="5cb295d9-c775-4005-a3f7-0b93a9da5263">verzeichniscontainer</akn:p>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2" GUID="cf57c02b-f5c1-4df4-98a5-bbbd15b82cf2">
                  <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-6_tabelleinh-2_text-1" GUID="bd4a6c9b-9ff3-482d-9637-a2989e1d1f67">akn:blockContainer</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7" GUID="be2d6154-6351-4621-b3e8-bba038210e22">
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1" GUID="b5840f8a-171c-4a92-9ce9-0a8c3afe186a" style="text-align:right">
                  <akn:ul GUID="95d8b684-796a-4252-9b8a-fe4bc5918a9c" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1">
                    <akn:li GUID="d456d8ca-0e63-4edc-8dee-bad65d30a3c9" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-1" value="•">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-1_text-1" GUID="8a12e0f5-1f44-4613-8519-571c7b685398">gliederungBuch</akn:p>
                    </akn:li>
                    <akn:li GUID="348fc9f9-39b7-4469-a836-c75a4852b265" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-2" value="•">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-2_text-1" GUID="4e46962c-1223-43fc-bf7c-365e6600393c">gliederungTeil</akn:p>
                    </akn:li>
                    <akn:li GUID="fc725abb-9609-44ee-8f90-9324571b94e3" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-3" value="•">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-3_text-1" GUID="67579455-65ff-4851-bf8d-afa67e97633e">gliederungKapitel</akn:p>
                    </akn:li>
                    <akn:li GUID="24d9fc13-a026-40a0-87d9-45c145b91bb3" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-4" value="•">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-1_listeunge-1_listenelem-4_text-1" GUID="ceff1546-c4ef-43b0-bf65-a00e99c9e65e">gliederungUnterkapitel</akn:p>
                    </akn:li>
                  </akn:ul>
                </akn:td>
                <akn:td eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2" GUID="7a0eb58d-fb0d-4b31-8674-6c324e4bbaf0">
                  <akn:ol GUID="6efdf889-580b-44f9-af7c-b677f60693b7" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1">
                    <akn:li GUID="1d44dcd9-0303-4364-a263-e7f423f768f8" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-1" value="1">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-1_text-1" GUID="62e16ea4-1924-4d37-8f0d-a3bc08985894">akn:book</akn:p>
                    </akn:li>
                    <akn:li GUID="8cff81e4-7cb4-419a-9c38-93fc6282f798" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-2" value="2">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-2_text-1" GUID="3ff6fbfc-cc8e-444d-8c0e-df1d9be1dff5">akn:part</akn:p>
                    </akn:li>
                    <akn:li GUID="51d2335e-32c7-4f2d-af05-ebcbe760e94c" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-3" value="3">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-3_text-1" GUID="426b882d-fd9f-453f-a9fc-461b94730dcd">akn:chapter</akn:p>
                    </akn:li>
                    <akn:li GUID="5a9bc50b-4ce6-4764-bc31-d9f4825ee79b" eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-4" value="4">
                      <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_tabelle-1_tabellereihe-7_tabelleinh-2_listegeor-1_listenelem-4_text-1" GUID="3025d90a-9e95-4b14-8715-d7cda785629c">akn:subchapter</akn:p>
                    </akn:li>
                  </akn:ol>
                </akn:td>
              </akn:tr>
            </akn:table>
            <akn:p eId="hauptteil-1_para-3_abs-1_inhalt-1_text-2" GUID="01a085ad-aa4a-4380-b9d3-92d1fc0f4eb5"> Die Tabelle ist nur ein Auszug aus der Spezifikation und erhebt keinen Anspruch auf Vollständigkeit. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>

      <akn:article eId="hauptteil-1_para-4" GUID="4064775d-5f92-4f12-ae03-602bb4f536c4" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
        <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="3fb666fa-e062-4b19-a629-161bf005f660">
          <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1" GUID="0324f9a6-d049-466f-a674-35cb4e93a38b" name="4" />§ 4</akn:num>
        <akn:heading eId="hauptteil-1_para-4_überschrift-1" GUID="c03385cc-38d4-4312-93b0-9576d8c5f90b">Inkrafttreten</akn:heading>
        <akn:paragraph eId="hauptteil-1_para-4_abs-1" GUID="78aee261-bbd0-4d63-9802-c535ca881a6c">
          <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="79d2bbe5-c1b4-4b3c-bbb7-a7ca53182cb5">
            <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" GUID="ffeb8e25-22c7-424b-bd64-796d9a8e39c0" name="1" />
          </akn:num>
          <akn:content eId="hauptteil-1_para-4_abs-1_inhalt-1" GUID="9b9f7291-af45-4ef1-9aaa-0747f62bd4c2">
            <akn:p eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1" GUID="63a5a73b-e6ba-4281-bccd-051a622de97f"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1_datum-1" GUID="b51ab039-7f0a-401a-86b8-506484cba0a6" date="1002-01-02" refersTo="inkrafttreten-datum">am Tag nach der Verkündung</akn:date> in Kraft. </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('c7fa8ba0-34c9-43ed-87ad-9bdf177126fc', 'eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1', NULL);
