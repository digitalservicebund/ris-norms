DELETE FROM announcements WHERE id = 'e68bb04b-ccb0-42bb-be5b-595eb691158b';
DELETE FROM norms WHERE guid = '01eadc2f-a602-417f-ab00-4e70a3d77ad7';
DELETE FROM norms WHERE guid = '526c42a0-63e2-4f77-a60d-0e351f4a7a61';

-- Amending law
INSERT INTO norms (guid, eli, xml)
VALUES ('01eadc2f-a602-417f-ab00-4e70a3d77ad7', 'eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd                                                                                                                                              http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    <akn:act name="regelungstext">
        <akn:meta GUID="1d1d770a-9ff0-45e6-878d-911c98dd9cd4" eId="meta-1">
            <akn:identification GUID="4dcd65df-8560-4d97-9325-e32deba192cf" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork GUID="7ae2285c-9020-4036-9bce-4e3be460a29a" eId="meta-1_ident-1_frbrwork-1">
                    <akn:FRBRthis GUID="cc954333-2c08-4f2d-800e-baab97a61115" eId="meta-1_ident-1_frbrwork-1_frbrthis-1" value="eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1"/>
                    <akn:FRBRuri GUID="57356a8b-5983-4efe-9f7f-7a507d23df13" eId="meta-1_ident-1_frbrwork-1_frbruri-1" value="eli/bund/bgbl-1/2002/22"/>
                    <akn:FRBRalias GUID="88509c2e-eb73-466b-8388-009e23ece0d7" eId="meta-1_ident-1_frbrwork-1_frbralias-1" name="übergreifende-id" value="7be4bf9d-13ea-4c53-9d87-40cf5d7b09ff"/>
                    <akn:FRBRdate GUID="ec0d1b3e-fc1e-472f-a4b2-0fc54b5d0224" date="2002-02-20" eId="meta-1_ident-1_frbrwork-1_frbrdate-1" name="verkuendungsfassung"/>
                    <akn:FRBRauthor GUID="7c4cffcc-061a-4826-a605-efc024553b9c" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" href="recht.bund.de/institution/bundesregierung"/>
                    <akn:FRBRcountry GUID="60aab79e-4b89-4d48-99b6-529c474398c1" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" value="de"/>
                    <akn:FRBRnumber GUID="12ec247c-a819-47e0-8570-1b8942f40820" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" value="22"/>
                    <akn:FRBRname GUID="99698450-cd5f-4fcc-b2ca-c24bc6216ea5" eId="meta-1_ident-1_frbrwork-1_frbrname-1" value="bgbl-1"/>
                    <akn:FRBRsubtype GUID="6f9b66b3-db19-4543-9db0-6a3bfe6a8929" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" value="regelungstext-1"/>
                </akn:FRBRWork>
                <akn:FRBRExpression GUID="b7a74512-05c5-48f4-af1e-11578e5ecec2" eId="meta-1_ident-1_frbrexpression-1">
                    <akn:FRBRthis GUID="a5dfab3c-a646-4633-9fc2-7496e14fb28f" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1"/>
                    <akn:FRBRuri GUID="ef04bbf0-f502-4b53-9863-ed3193a4b1bd" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" value="eli/bund/bgbl-1/2002/22/2002-02-20/1/deu"/>
                    <akn:FRBRalias GUID="031754e0-3f61-4484-8185-8c59ff4244fa" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="01eadc2f-a602-417f-ab00-4e70a3d77ad7"/>
                    <akn:FRBRalias GUID="fa481503-200a-4bee-95d8-eee4dfd31695" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="5773cb41-adfe-47ca-b684-dde0c83c7b39"/>
                    <akn:FRBRauthor GUID="631e316b-540e-48ec-9532-08fa8e250ec8" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" href="recht.bund.de/institution/bundesregierung"/>
                    <akn:FRBRdate GUID="46ba1df6-0764-4d9c-a5b3-e7a6df746230" date="2002-02-20" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" name="verkuendung"/>
                    <akn:FRBRlanguage GUID="fdd84eea-0f30-4898-9689-00a122fd9dcb" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" language="deu"/>
                    <akn:FRBRversionNumber GUID="41e042d3-23f7-4142-94b2-1f7cb2e94322" eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" value="1"/>
                </akn:FRBRExpression>
                <akn:FRBRManifestation GUID="966416cc-1a99-4496-aaca-6a84a67cdc8e" eId="meta-1_ident-1_frbrmanifestation-1">
                    <akn:FRBRthis GUID="b98122db-6c85-4047-b910-5872f1f58d5d" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" value="eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1.xml"/>
                    <akn:FRBRuri GUID="586db454-7dde-44de-b21e-290bfcf12da3" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" value="eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1.xml"/>
                    <akn:FRBRdate GUID="c764890a-3801-481d-9171-b25a374c3fa2" date="2002-02-20" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" name="generierung"/>
                    <akn:FRBRauthor GUID="262a2241-a71c-4d1f-a97a-e77731a25194" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" href="recht.bund.de"/>
                    <akn:FRBRformat GUID="a4ff1d85-5499-4089-bd1b-38e9c622dba3" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" value="xml"/>
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle GUID="dcfa52f5-a67a-4306-8e37-8ad190788de9" eId="meta-1_lebzykl-1" source="attributsemantik-noch-undefiniert">
                <akn:eventRef GUID="d4e59051-fd39-4caf-bb5d-bdcd2dde7d1c" date="2002-02-20" eId="meta-1_lebzykl-1_ereignis-1" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" type="generation"/>
                <akn:eventRef GUID="25276ab9-c149-4fc9-9cef-0181244833e1" date="2002-02-21" eId="meta-1_lebzykl-1_ereignis-2" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="generation"/>
            </akn:lifecycle>
            <akn:analysis GUID="72cd555b-de7d-4d5e-ba2e-4dc50800400f" eId="meta-1_analysis-1" source="attributsemantik-noch-undefiniert">
                <akn:activeModifications GUID="ca13a0cc-8f37-42c7-920b-f0d2fb59c81c" eId="meta-1_analysis-1_activemod-1">
                    <akn:textualMod GUID="ae8e4880-4385-4e54-9b7c-1337d8015d33" eId="meta-1_analysis-1_activemod-1_textualmod-1" type="substitution">
                        <akn:source GUID="30406542-d2e5-41fb-81ae-da19efa66aae" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
                        <akn:destination GUID="dc780027-452c-41eb-850c-af483bbdc2dc" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-1.xml" upTo="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-2.xml"/>
                        <akn:force GUID="9a8da48a-b837-45ea-8395-09bc895df4b0" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                    </akn:textualMod>
                    <akn:textualMod GUID="c339ce6b-ca1c-4a3e-a563-821f15c3c492" eId="meta-1_analysis-1_activemod-1_textualmod-2" type="substitution">
                        <akn:source GUID="0af0b6f9-b4fa-47bb-b4fb-18cde81905b8" eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" href="#hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                        <akn:destination GUID="6d354a73-d442-4a14-a5a9-c848474d5cfc" eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" href="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b.xml" upTo="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f.xml"/>
                        <akn:force GUID="155388d0-9171-482d-bb02-2267acbadbed" eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                    </akn:textualMod>
                </akn:activeModifications>
            </akn:analysis>
            <akn:temporalData GUID="3f4c6b1a-7626-43f3-9bde-a5c6b5ec77a1" eId="meta-1_geltzeiten-1" source="attributsemantik-noch-undefiniert">
                <akn:temporalGroup GUID="a1d55356-5204-4dbd-8da2-3938dca0dac7" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
                    <akn:timeInterval GUID="e0121343-fc18-4cc9-adf2-e053ab8ae34b" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-1"/>
                </akn:temporalGroup>
                <akn:temporalGroup GUID="2300a585-4522-446d-8555-31462886d6e5" eId="meta-1_geltzeiten-1_geltungszeitgr-2">
                    <akn:timeInterval GUID="3e1a2cfe-49f7-40b2-9c10-e960fae34de9" eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
                </akn:temporalGroup>
            </akn:temporalData>
        </akn:meta>

        <akn:preface GUID="0c50befb-5451-4192-a052-e2efbd25347d" eId="einleitung-1">
            <akn:longTitle GUID="f18e2df5-e8a3-4046-ad76-4753d6b9507c" eId="einleitung-1_doktitel-1">
                <akn:p GUID="7d999f27-335d-4fa5-964f-9b761ebe4cdb" eId="einleitung-1_doktitel-1_text-1">
                    <akn:docTitle GUID="214a4065-2b9f-4f02-9113-89787e9cf8b8" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Erstes Gesetz zur Änderung des Strukturänderungsgesetzes</akn:docTitle>
                </akn:p>
            </akn:longTitle>
        </akn:preface>

        <akn:preamble GUID="fad0459d-f995-4f1e-8ec7-340e8a0da839" eId="preambel-1">
            <akn:formula GUID="0e220f68-9fd9-465a-9222-c45413d82991" eId="preambel-1_formel-1" name="attributsemantik-noch-undefiniert" refersTo="eingangsformel">
                <akn:p GUID="9109bafb-dba9-4154-a073-e6d32530ac57" eId="preambel-1_formel-1_text-1"> Der <akn:organization GUID="0da4c1af-0d07-47bd-ad87-734e2357e2de" eId="preambel-1_formel-1_text-1_org-1" refersTo="attributsemantik-noch-undefiniert" title="Bundestag">Bundestag</akn:organization> hat das folgende Gesetz beschlossen:</akn:p>
            </akn:formula>
        </akn:preamble>

        <akn:body GUID="e6720f3f-951d-45cc-8edd-b05480727c71" eId="hauptteil-1">
            <akn:article GUID="4d5d8b32-f818-4407-ae56-3bc283acf4fa" eId="hauptteil-1_para-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="eingebundene-stammform">
                <akn:num GUID="f234ff66-06fa-4b72-8555-23a6559acf3d" eId="hauptteil-1_para-1_bezeichnung-1">
                    <akn:marker GUID="0642872b-5dbc-45ef-8f07-76a3c11e9abd" eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" name="1"/></akn:num>
                <akn:heading GUID="68b539af-5c6a-47ef-99d8-8f4574dfa729" eId="hauptteil-1_para-1_überschrift-1">Artikel 1</akn:heading>
                <akn:paragraph GUID="de615c8e-7045-4067-8d79-fcb80714e476" eId="hauptteil-1_para-1_abs-1">
                    <akn:num GUID="d789f737-763e-40ce-b12a-aa68a1c6e9b6" eId="hauptteil-1_para-1_abs-1_bezeichnung-1">
                        <akn:marker GUID="5aef21a6-8619-43ba-8ab5-55bafda33517" eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" name="1"/>
                    </akn:num>
                    <akn:list GUID="f3e39c51-760e-418f-b8c5-57170d3480a3" eId="hauptteil-1_para-1_abs-1_untergl-1">
                        <akn:intro GUID="40067ab2-e780-40db-bd02-2813f1036ac8" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1">
                            <akn:p GUID="5ea6d74d-6330-43d0-9f25-5569c627a549" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1"> Das
                                <akn:affectedDocument GUID="6af56003-a242-480c-a51c-c56f7642b4b7" eId="hauptteil-1_para-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" href="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1">Bereichgesetz vom 1. Januar 1999 (BGBl. 1999 I Nr. 66)</akn:affectedDocument> wird wie folgt geändert:
                            </akn:p>
                        </akn:intro>
                        <akn:point GUID="8f5e1c26-85e9-4609-a619-4eb1881582aa" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1">
                            <akn:num GUID="ebb6f4f1-2da0-4ba8-ad6d-77d131603494" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1">
                                <akn:marker GUID="2b6b9149-dc18-4024-b99c-79ab86a27cb7" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" name="1"/>1.</akn:num>
                            <akn:content GUID="d9a3cdef-a1ac-40c7-bbee-c44b2c202b1b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                                <akn:p GUID="4d2ed12d-9234-4a9b-aa85-ef356b33a540" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                    <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
                                        <akn:rref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-1.xml" upTo="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-2.xml">§ 2 Absätze 1 bis 2</akn:rref> des Gesetzes werden ersetzt durch:
                                        <akn:quotedStructure GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1" endQuote="“" startQuote="„">
                                            <akn:paragraph GUID="69243922-59f2-4733-b590-55d7e8cfdf03" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-1">
                                                <akn:num GUID="2626b5a4-2b15-43d1-9297-3949099ca6dc" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-1_bezeichnung-1">
                                                    <akn:marker GUID="f87d5214-4fc2-48d8-8c9b-9a0875666c8b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-1_bezeichnung-1_zaehlbez-1" name="1"/>(1)</akn:num>
                                                <akn:content GUID="f5e3fa13-3ed9-41c4-a4ee-91db57abdd51" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-1_inhalt-1">
                                                    <akn:p GUID="fabee05a-1bba-4898-bd30-3472717fc013" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-1_inhalt-1_text-1">Dieses Gesetz findet keine Anwendung.</akn:p>
                                                </akn:content>
                                            </akn:paragraph>
                                            <akn:paragraph GUID="0c404488-4159-4756-8467-2400daa1c489" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-2">
                                                <akn:num GUID="f092a2b3-3000-49b7-b3ef-33ae42b5d9e5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-2_bezeichnung-1">
                                                    <akn:marker GUID="9f5566d7-3df4-43f6-851a-2499b7582580" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-2_bezeichnung-1_zaehlbez-1" name="2"/>(2)</akn:num>
                                                <akn:content GUID="7633905f-63df-4490-ad09-e13b67c6118a" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-2_inhalt-1">
                                                    <akn:p GUID="be070e72-ff49-4b99-98da-45e3a6154faf" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_para-2_inhalt-1_text-1">Die mathematische Formel wurde entfernt.</akn:p>
                                                </akn:content>
                                            </akn:paragraph>
                                        </akn:quotedStructure>
                                    </akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                        <akn:point GUID="c87a5344-e030-4471-a8a5-2deb4712cbe6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2">
                            <akn:num GUID="30a34413-955a-4eab-9354-b569cf0fa40b" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1">
                                <akn:marker GUID="1ed68726-6cd0-43f4-bbd5-56e7c5c8a7d9" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" name="2"/>2.</akn:num>
                            <akn:content GUID="ead90607-aeca-434f-a4f8-9e9536830ac3" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1">
                                <akn:p GUID="32c46b5b-9276-4b81-8b06-e29d4dbab8f5" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                    <akn:mod GUID="ba11efa1-41d3-44ee-8c53-deb51d1fa1e8" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
                                        <akn:rref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-b.xml" upTo="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1/hauptteil-1_para-2_abs-3_untergl-1_listenelem-1_untergl-a_listenelem-f.xml">§ 2 Absatz 3 Nummer 1 Buchstaben b) bis f)</akn:rref> des Gesetzes werden ersetzt durch:
                                        <akn:quotedStructure GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1" endQuote="“" startQuote="„">
                                            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-b" GUID="a847f9da-9a41-4d4e-beab-874fb6beec7f">
                                                <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-b_bezeichnung-1" GUID="a0419aca-7a16-4cb3-a57d-003ed60a4e69">
                                                    <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-b_bezeichnung-1_zaehlbez-1" GUID="0fe58896-51b7-4264-97b6-c3b366268a3b" name="b" />b)</akn:num>
                                                <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-b_inhalt-1" GUID="2746d6e9-1b49-43fb-9054-0bc34c0ac0f2">
                                                    <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-b_inhalt-1_text-1" GUID="e15cee5a-8ec9-4eee-afbe-2ee07c8fc5f9">Zeitschriften,</akn:p>
                                                </akn:content>
                                            </akn:point>
                                            <akn:point eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-c" GUID="a847f9da-9a41-4d4e-beab-874fb6beec7f">
                                                <akn:num eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-c_bezeichnung-1" GUID="a0419aca-7a16-4cb3-a57d-003ed60a4e69">
                                                    <akn:marker eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-c_bezeichnung-1_zaehlbez-1" GUID="0fe58896-51b7-4264-97b6-c3b366268a3b" name="c" />c)</akn:num>
                                                <akn:content eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-c_inhalt-1" GUID="2746d6e9-1b49-43fb-9054-0bc34c0ac0f2">
                                                    <akn:p eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_listenelem-c_inhalt-1_text-1" GUID="e15cee5a-8ec9-4eee-afbe-2ee07c8fc5f9">Spiele.</akn:p>
                                                </akn:content>
                                            </akn:point>
                                        </akn:quotedStructure>
                                    </akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>
            <akn:article GUID="628b7653-68d5-483f-b265-be0a6a5c7aea" eId="hauptteil-1_para-2" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                <akn:num GUID="41f85859-9eed-4b05-8d59-4d92983fdd0c" eId="hauptteil-1_para-2_bezeichnung-1">
                    <akn:marker GUID="b47cfc67-7544-44a6-ad27-5da7ea7f053d" eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" name="2"/></akn:num>
                <akn:heading GUID="e696f540-586c-4725-ae49-654bb28918ab" eId="hauptteil-1_para-2_überschrift-1">Inkrafttreten</akn:heading>
                <akn:paragraph GUID="c0a4c9d2-dc0d-4b59-be88-7663f1e32bae" eId="hauptteil-1_para-2_abs-1">
                    <akn:num GUID="df00a213-4a1d-44fa-9df5-27979493b0f1" eId="hauptteil-1_para-2_abs-1_bezeichnung-1">
                        <akn:marker GUID="f03f382c-d9f5-4a24-bf3b-b3695d57e86a" eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" name="1"/>
                    </akn:num>
                    <akn:content GUID="0f24a464-a987-4eab-be22-2badb8cc3321" eId="hauptteil-1_para-2_abs-1_inhalt-1">
                        <akn:p GUID="de1b70eb-c26f-4223-bfa4-ed0ffd974314" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"> Dieses Gesetz tritt <akn:date GUID="28f26c1e-2329-4082-887a-a3c1b19a1b7b" date="1002-01-02" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1_datum-1" refersTo="inkrafttreten-datum">am Tag nach der Verkündung</akn:date> in Kraft. </akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
        </akn:body>
    </akn:act>
</akn:akomaNtoso>
');

-- Target law
INSERT INTO norms (guid, eli, xml)
VALUES ('526c42a0-63e2-4f77-a60d-0e351f4a7a61', 'eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    <akn:act name="regelungstext">
        <akn:meta eId="meta-1" GUID="e4589a5f-34d9-4a3b-bcdb-558e1c36b69b">
            <akn:identification eId="meta-1_ident-1" GUID="f3e17ed2-7f8b-4658-858b-c73bb1c6ca24" source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="12f4b363-48d8-4fd3-aee2-a5f525ea14b4">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="b7d16407-5d18-4af1-aa67-731a7a7758ac" value="eli/bund/bgbl-1/1999/66/regelungstext-1" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="6130a9a2-85bd-483c-b326-31095830276e" value="eli/bund/bgbl-1/1999/66" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="43863563-e2e9-4d93-9249-239a45127274" name="übergreifende-id" value="f96cfae4-4fce-4c72-9186-0d84778dc11c" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5fd62927-fb0e-4167-b073-2b2577ac0ecf" date="1999-01-01" name="verkuendungsfassung" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="a551da99-a71a-4179-8f53-4a31a7e64a30" href="recht.bund.de/institution/bundesregierung" />
                    <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="e386ef70-4f71-4590-b940-093207a82c47" value="de" />
                    <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="af7bebd5-85de-42d9-b5b0-519798d65112" value="1" />
                    <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="1d5fac51-3d5e-4f07-9f79-92c32dd42cbb" value="bgbl-1" />
                    <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="6d2b4986-4728-4e62-9326-fb337a6a43ea" value="regelungstext-1" />
                </akn:FRBRWork>
                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="67247b7c-2285-490d-a0f2-a1c805f2f140">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="e3e82a45-e271-4f24-936d-94cae114db58" value="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="a6997dd0-1a12-43df-a02a-7c2d70b29f1a" value="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="43bd6e06-3017-470c-a91f-18318154885a" name="aktuelle-version-id" value="63ef9358-8755-46e4-bf6a-21f379014597" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="1700bef9-6d4d-48c1-b0bc-f066dd9d3bf8" name="nachfolgende-version-id" value="e4e034dd-61b9-43aa-b4f9-b778dc6adfda" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="7f11bff8-c09d-40d0-a0bb-3f2542514116" href="recht.bund.de/institution/bundesregierung" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="31cc9f31-f6b7-45d2-ac42-383d694afa9a" date="1999-01-01" name="verkuendung" />
                    <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="256f67ea-2aa5-4e3f-959e-807b3f7dd934" language="deu" />
                    <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="202c3b86-b8a4-4cd5-ac59-f8dd36151c57" value="1" />
                </akn:FRBRExpression>
                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="321497df-3c8b-4168-a50a-ac359fc62ea3">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="b73c60f8-03fb-4a98-9bd1-990ff334dbd9" value="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1.xml" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="3346b7be-37e3-4e26-8cd5-1d4fb70def3b" value="eli/bund/bgbl-1/1999/66/1999-01-01/1/deu/regelungstext-1.xml" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="ef351e86-937d-455d-81d0-8b7688470a4d" date="1999-01-01" name="generierung" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="c3ca80c3-4273-43a9-8072-94659d7b57cb" href="recht.bund.de" />
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="80b4c27c-9973-43e7-986a-1b36d88309dd" value="xml" />
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="58320eb1-0dba-47a3-9195-5d1e09ab2d56" eId="meta-1_lebzykl-1">
                <akn:eventRef date="1999-01-01" source="attributsemantik-noch-undefiniert" refersTo="ausfertigung" type="generation" eId="meta-1_lebzykl-1_ereignis-1" GUID="cd6cd3ee-7628-43ca-862a-bb9201bf0102" />
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
                    <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="a3fe0152-c96a-4fe2-a37d-d4aa5614df4a">Gesetz für das Ersetzen von Strukturen mit einem Bereich</akn:docTitle>
                    <akn:shortTitle GUID="820e7af3-fd8c-4409-949a-1e40ec2cc8e6" eId="einleitung-1_doktitel-1_text-1_kurztitel-1"> (Bereichgesetz) </akn:shortTitle>
                </akn:p>
            </akn:longTitle>
        </akn:preface>


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
                        <akn:p eId="hauptteil-1_para-2_abs-2_inhalt-1_text-1" GUID="6c34ea8a-808c-4f63-a233-348fdba174b8">Die Berechnung der Anwendung erfolgt ohne Mathematik. </akn:p>
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
</akn:akomaNtoso>
');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('e68bb04b-ccb0-42bb-be5b-595eb691158b', 'eli/bund/bgbl-1/2002/22/2002-02-20/1/deu/regelungstext-1', NULL);
