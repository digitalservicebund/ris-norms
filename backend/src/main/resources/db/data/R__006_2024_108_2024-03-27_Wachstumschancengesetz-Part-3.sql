-- First delete announcement because of foreign key
DELETE FROM announcements where eli = 'eli/bund/bgbl-1/2024/108/2024-03-27/2/deu/regelungstext-1';

-- Amending law "Wachstumschancengesetzt" Part 3
DELETE FROM norms where guid = '53d31e3c-5c46-4e96-8017-b0db064561a2';
INSERT INTO norms (guid, eli, xml)
VALUES ('53d31e3c-5c46-4e96-8017-b0db064561a2', 'eli/bund/bgbl-1/2024/108/2024-03-27/2/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
--><?xml-model href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/26e27e3a56996587fe737639fd80e2c4a8fe0311/Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
    <akn:act name="regelungstext">
        <!-- Metadaten-->
        <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
            <akn:identification eId="meta-1_ident-1" GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22" source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761" value="eli/bund/bgbl-1/2004/s1673/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a90733dd-4780-472d-8371-a3690f2e869a" value="eli/bund/bgbl-1/2024/108"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b" name="übergreifende-id" value="2c1a6ec9-7161-4e2f-8999-91219180d991"></akn:FRBRalias>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811" date="2024-03-27" name="verkuendungsfassung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e" value="de"></akn:FRBRcountry>
                    <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5" value="108"></akn:FRBRnumber>
                    <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8" value="bgbl-1"></akn:FRBRname>
                    <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="af948830-e615-4dd5-8237-b8c830939f78" value="regelungstext-1"></akn:FRBRsubtype>
                </akn:FRBRWork>
                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="85e064f4-f56f-4cef-86a4-6801cc71c712" value="eli/bund/bgbl-1/2024/108/2024-03-27/2/deu/regelungstext-1"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="e8f52739-79c1-4e59-9db2-004180c8b29c" value="eli/bund/bgbl-1/2024/108/2024-03-27/2/deu"></akn:FRBRuri>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="c2e78146-833b-4d78-a055-27f05eeaf018" name="aktuelle-version-id" value="53d31e3c-5c46-4e96-8017-b0db064561a2"></akn:FRBRalias>
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c" name="nachfolgende-version-id" value="5d84cd1d-3575-4a03-bb6c-f17834e392fe"></akn:FRBRalias>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="6aace8d0-001f-4829-9794-79857835ee45" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="a6dc32d0-140d-4147-88b2-d64639b91742" date="2004-07-16" name="verkuendung"></akn:FRBRdate>
                    <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="b872861e-0add-4bbb-ba90-a5edb3c40038" language="deu"></akn:FRBRlanguage>
                    <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc" value="2"></akn:FRBRversionNumber>
                </akn:FRBRExpression>
                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="4c881fca-0e36-440e-a5f9-513079ccc77f" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1.xml"></akn:FRBRthis>
                    <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="d0491f91-d599-46d4-ba61-c7426b607e4f" value="eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1.xml"></akn:FRBRuri>
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612" date="2024-03-24" name="generierung"></akn:FRBRdate>
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="1d015a20-823e-496e-808d-27c8fec85b51" href="recht.bund.de"></akn:FRBRauthor>
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67" value="xml"></akn:FRBRformat>
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle eId="meta-1_lebzykl-1" GUID="cef30a63-d738-4712-bac8-50fda44949e2" source="attributsemantik-noch-undefiniert">
                <!-- 1. Originales Gesetz -->
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="65f3910d-4e79-45f8-a24d-44ef7523963a" date="2024-03-27" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung"></akn:eventRef>
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c" date="2024-08-24" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten"></akn:eventRef>
                <!-- 2. -->
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="85e92fc8-b381-47bb-a476-c35fa2794336" date="2023-01-01" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"></akn:eventRef>
            </akn:lifecycle>
            <akn:analysis source="attributsemantik-noch-undefiniert" eId="meta-1_analysis-1" GUID="574fd083-03f8-4fad-9063-be1be66db787">
                <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="c83d50dc-38d1-495c-a4a4-12d50b7909c3">
                    <akn:textualMod type="substitution" GUID="1378474b-426f-4488-83e1-6adf3be72fea" eId="meta-1_analysis-1_activemod-1_textualmod-1">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="46928a7e-8665-41bd-923c-9617211239d5"></akn:source>
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-15_abs-2/221-998.xml" GUID="1e15b64a-c408-4146-936f-faaf4d5fb36e"></akn:destination>
                        <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-2" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="7441cd9c-d0cd-41e1-b702-5355342ee068"></akn:force>
                    </akn:textualMod>
                </akn:activeModifications>
            </akn:analysis>
            <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16" start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f" start="#meta-1_lebzykl-1_ereignis-3" refersTo="geltungszeit"></akn:timeInterval>
                </akn:temporalGroup>
            </akn:temporalData>
            <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
            <akn:proprietary eId="meta-1_proprietary-1" GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7" source="attributsemantik-noch-undefiniert">
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                    <meta:typ>gesetz</meta:typ>
                    <meta:form>stammform</meta:form>
                    <meta:fassung>neufassung</meta:fassung>
                    <meta:art>regelungstext</meta:art>
                    <meta:initiant>nicht-vorhanden</meta:initiant>
                    <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
                    <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
                    <meta:fna>754-28-1</meta:fna>
                    <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
                    <meta:gesta>nicht-vorhanden</meta:gesta>
                    <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
                    <meta:federfuehrung>
                        <meta:federfuehrend ab="2024-03-27" bis="unbestimmt">Bundesministerium für Finanzen</meta:federfuehrend>
                    </meta:federfuehrung>
                </meta:legalDocML.de_metadaten>
                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                    <meta:subtyp>ÄN</meta:subtyp>
                </meta:legalDocML.de_metadaten_ds>
            </akn:proprietary>
        </akn:meta>
        <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
            <akn:longTitle eId="einleitung-1_doktitel-1" GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
                <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
                    <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Gesetz zur Stärkung von Wachstumschancen, Investitionen und Innovation sowie Steuervereinfachung und Steuerfairness

                        <!-- Fußnote -->
                        <akn:authorialNote eId="einleitung-1_doktitel-1_text-1_doctitel-1_fnote-1" GUID="9de7999f-a222-43ac-a648-f92c5a8ee927" marker="*)" placement="bottom" placementBase="">
                            <akn:p eId="einleitung-1_doktitel-1_text-1_doctitel-1_fnote-1_text-1" GUID="7c210663-00e0-48c1-b86d-d02f1c422de0">Artikel 31 dient der Umsetzung der Richtlinie (EU) 2021/514 des Rates vom 22. März 2021 zur Änderung der Richtlinie 2011/16/EU über die
                                Zusammenarbeit der Verwaltungsbehörden im Bereich der Besteuerung (ABl. L 104 vom 25.3.2021, S. 1).
                            </akn:p>
                        </akn:authorialNote>
                    </akn:docTitle>
                    <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">(Wachstumschancengesetz)</akn:shortTitle>
                </akn:p>
            </akn:longTitle>
        </akn:preface>
        <!-- Hauptteil -->
        <akn:body eId="hauptteil-1" GUID="ce0ac10d-510e-4148-8a8d-a4406a8b0a67">
            <!-- §1 -->
            <akn:article eId="hauptteil-1_para-1" GUID="80d1826b-4225-4019-80c7-a10654008b9c" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="a7d5ccda-e00c-4aef-a99f-809b8eafdd71">
                    <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="e2a587cd-af6f-4ca7-846e-50a1ba0b6520" name="1"></akn:marker>§1</akn:num>
                <akn:paragraph GUID="7e004c65-aaf4-4f88-94ce-9a7651de7593" eId="hauptteil-1_para-1_abs-1">
                    <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="87a0e6d5-2b76-4034-aa20-ef2260f8353f">
                        <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="5820c1f0-ed8e-4a9e-848d-b33ae2e8ef54" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="155de595-1a58-45b5-95a7-7ce0ee6e103b" eId="hauptteil-1_para-1_abs-1_inhalt-1"><akn:p GUID="592e2d87-7843-4f41-b076-5031d254cff2" eId="hauptteil-1_para-1_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §2 -->
            <akn:article eId="hauptteil-1_para-2" GUID="26eb1ba5-2f9d-4efc-8522-061bbcc4a7b9" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="928b837a-52a8-4568-839b-db2866abd202">
                    <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="39aa4be1-9228-452e-b9a0-9c13501a9b90" name="2"></akn:marker>§2</akn:num>
                <akn:paragraph GUID="73863cd9-dd0f-472f-85fb-34fb660a2178" eId="hauptteil-1_para-2_abs-1">
                    <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="cab55e58-3901-4e19-bb04-a0a1c85ecaad">
                        <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="031c1d63-eb3c-4ce6-a370-1b83a01e1a39" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="41cf5098-928d-4027-b78d-59de7632d758" eId="hauptteil-1_para-2_abs-1_inhalt-1"><akn:p GUID="84c4afdf-9e55-43f3-970e-427b5b5f3bab" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §3 -->
            <akn:article eId="hauptteil-1_para-3" GUID="1fbffcff-3845-4175-af88-e1cac9d8053d" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="eb1e352f-4e25-4eea-a1f4-d93920af7b8d">
                    <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" GUID="4fb283d9-f671-4272-86ca-e5e4cbbf0538" name="3"></akn:marker>§3</akn:num>
                <akn:paragraph GUID="88a7bf19-3379-4bb5-883b-ff7f9873cd13" eId="hauptteil-1_para-3_abs-1">
                    <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="68464716-39a9-44dc-bb44-8d2a5ed1916b">
                        <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="143b1044-ee76-42b9-9a86-6fb3ff1a7c2c" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="748cf054-b5c6-473f-8edd-e967d7a605be" eId="hauptteil-1_para-3_abs-1_inhalt-1"><akn:p GUID="530ab977-cf41-4f6d-8db8-b7a164e2595a" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §4 -->
            <akn:article eId="hauptteil-1_para-4" GUID="5a8416ef-c656-4039-a0fb-a2463aa1e0d7" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="8222bebf-7c0a-4a6c-9795-9705c816a2c0">
                    <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1" GUID="8a0a9a47-744f-42b3-bc9e-2ce63ec5ff1d" name="4"></akn:marker>§4</akn:num>
                <akn:paragraph GUID="45bcaf24-834f-4e5e-bbd1-9e8c127bf403" eId="hauptteil-1_para-4_abs-1">
                    <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="d791a3d8-8770-4306-ab32-786391c32eb0">
                        <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" GUID="d6db7c2e-d058-43cb-b0f4-00445997b3d6" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="8ccbd60b-4f1e-4aad-b712-cf81dc981e8b" eId="hauptteil-1_para-4_abs-1_inhalt-1"><akn:p GUID="08109b53-188a-484d-9aae-94e355cd5c96" eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §5 -->
            <akn:article eId="hauptteil-1_para-5" GUID="ec075ce0-a6a5-4075-9041-b0304da221b0" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-5_bezeichnung-1" GUID="4bfef6b2-3527-4247-a8b2-dc1bc468e952">
                    <akn:marker eId="hauptteil-1_para-5_bezeichnung-1_zaehlbez-1" GUID="af885e36-4dc7-46de-bc06-68423507c9a6" name="5"></akn:marker>§5</akn:num>
                <akn:paragraph GUID="5fdaec7c-753e-4218-8040-22fa6d9b594d" eId="hauptteil-1_para-5_abs-1">
                    <akn:num eId="hauptteil-1_para-5_abs-1_bezeichnung-1" GUID="95315d18-139e-436e-8822-ff5e81f16583">
                        <akn:marker eId="hauptteil-1_para-5_abs-1_bezeichnung-1_zaehlbez-1" GUID="603af9f3-25c9-4a5f-bc24-3c78353a34f0" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="7263cdbb-1e3d-4cd4-8491-c901ae605ee0" eId="hauptteil-1_para-5_abs-1_inhalt-1"><akn:p GUID="5740dcc2-4d32-4e55-ac8f-cc6e5886d0a2" eId="hauptteil-1_para-5_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §6 -->
            <akn:article eId="hauptteil-1_para-6" GUID="9d818ef1-7e1a-44b0-b584-0690c7eb636d" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-6_bezeichnung-1" GUID="4a538475-efee-458c-9311-005f4dfb72ed">
                    <akn:marker eId="hauptteil-1_para-6_bezeichnung-1_zaehlbez-1" GUID="7456f473-2d28-4931-b850-b3c487d4f5ff" name="6"></akn:marker>§6</akn:num>
                <akn:paragraph GUID="959ff2b2-0ae9-4687-b76c-f6b6a7020693" eId="hauptteil-1_para-6_abs-1">
                    <akn:num eId="hauptteil-1_para-6_abs-1_bezeichnung-1" GUID="3e39302a-d279-47e7-b2ba-3105b4618a43">
                        <akn:marker eId="hauptteil-1_para-6_abs-1_bezeichnung-1_zaehlbez-1" GUID="24dbaf9f-89f1-41fe-9813-a03c92eceaa7" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="ed798c34-6080-4ee1-b9e3-872936250312" eId="hauptteil-1_para-6_abs-1_inhalt-1"><akn:p GUID="cb6c9acd-327e-4eac-90ec-a1b58d58a969" eId="hauptteil-1_para-6_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §7-->
            <akn:article eId="hauptteil-1_para-7" GUID="1ceb0d54-0dda-4661-906b-4e2e90b0cd09" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-7_bezeichnung-1" GUID="256fe6ee-be7d-4e06-af6d-f724771935fe">
                    <akn:marker eId="hauptteil-1_para-7_bezeichnung-1_zaehlbez-1" GUID="5f3c4413-476a-4661-9bd3-b7fbc49a01fd" name="7"></akn:marker>§7</akn:num>
                <akn:paragraph GUID="5a6bdfeb-f784-43ef-9eb9-ea4f4fd33405" eId="hauptteil-1_para-7_abs-1">
                    <akn:num eId="hauptteil-1_para-7_abs-1_bezeichnung-1" GUID="cc85ce13-9624-4abb-a77c-3f3433e8e347">
                        <akn:marker eId="hauptteil-1_para-7_abs-1_bezeichnung-1_zaehlbez-1" GUID="2aa1c4aa-722c-4cf4-9507-5e722e07a143" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="149587bf-3cee-471d-8c1f-74f55a352078" eId="hauptteil-1_para-7_abs-1_inhalt-1"><akn:p GUID="90b03eef-fed8-4017-8e8b-c550561cad01" eId="hauptteil-1_para-7_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §8-->
            <akn:article eId="hauptteil-1_para-8" GUID="64c63f88-1f83-4044-ae82-5fccbf0bb3bb" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-8_bezeichnung-1" GUID="d6cc2523-3235-4ee6-bb3a-cdc79ff360c7">
                    <akn:marker eId="hauptteil-1_para-8_bezeichnung-1_zaehlbez-1" GUID="514ba761-7761-4c21-9844-a06c89e0778b" name="5"></akn:marker>§8</akn:num>
                <akn:paragraph GUID="2a13a905-8e98-45f8-b5de-a60028163988" eId="hauptteil-1_para-8_abs-1">
                    <akn:num eId="hauptteil-1_para-8_abs-1_bezeichnung-1" GUID="c4fa4971-7380-4b42-a50c-ecaa77faf949">
                        <akn:marker eId="hauptteil-1_para-8_abs-1_bezeichnung-1_zaehlbez-1" GUID="1b1c875e-c5a5-4422-812c-6efc16a0c837" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="5d7d02d5-e68b-4c7b-b629-29eb7fd372b1" eId="hauptteil-1_para-8_abs-1_inhalt-1"><akn:p GUID="a9dc09f9-b174-4e2d-83bc-3b99402da470" eId="hauptteil-1_para-8_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §9-->
            <akn:article eId="hauptteil-1_para-9" GUID="50052489-c09c-43e4-a6bf-875714309d37" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-9_bezeichnung-1" GUID="3c7ad660-ac0f-404a-88b1-2c48cc167018">
                    <akn:marker eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1" GUID="6844ba8d-9c9f-42fe-8520-b89f1314ad9e" name="5"></akn:marker>§9</akn:num>
                <akn:paragraph GUID="3275e8b5-c1c5-4c8c-a51a-cd68700f131a" eId="hauptteil-1_para-9_abs-1">
                    <akn:num eId="hauptteil-1_para-9_abs-1_bezeichnung-1" GUID="f62c19e9-8257-4fb6-a597-92293bb0790a">
                        <akn:marker eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1" GUID="d3364ba1-546d-4ad4-b940-50c704d21e7c" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="8ab7b65f-52cf-44ea-bff0-64ef54cd65fb" eId="hauptteil-1_para-9_abs-1_inhalt-1"><akn:p GUID="80c3148a-c174-4f8b-afce-3c2f6a9fcb84" eId="hauptteil-1_para-9_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §10-->
            <akn:article eId="hauptteil-1_para-10" GUID="6c753154-bb8f-484c-afe4-0987272b0983" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-10_bezeichnung-1" GUID="8a6c51e3-fed3-4bf7-b76b-8c52156f9e5c">
                    <akn:marker eId="hauptteil-1_para-10_bezeichnung-1_zaehlbez-1" GUID="9e9e981d-9de9-4cfb-af9e-b57695c4cbca" name="5"></akn:marker>§10</akn:num>
                <akn:paragraph GUID="3f29a241-3bc5-4f75-80c1-b73be9c1e737" eId="hauptteil-1_para-10_abs-1">
                    <akn:num eId="hauptteil-1_para-10_abs-1_bezeichnung-1" GUID="ebd5f8ce-8a47-4980-80df-6075047e9c85">
                        <akn:marker eId="hauptteil-1_para-10_abs-1_bezeichnung-1_zaehlbez-1" GUID="d335d6e2-2e33-41c8-b7d7-3a97d5444314" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="1b2ff163-ca8e-4824-acc6-3ae8802b5042" eId="hauptteil-1_para-10_abs-1_inhalt-1"><akn:p GUID="47c5c44a-fd23-44a5-93e6-d3b80e5b03a5" eId="hauptteil-1_para-10_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
                <!-- §11-->
            </akn:article>
            <akn:article eId="hauptteil-1_para-11" GUID="2b6de71c-46d1-4968-9440-90db2bd14091" period="#geltungszeitgr-2">
                <akn:num eId="hauptteil-1_para-11_bezeichnung-1" GUID="bca90922-5294-4247-86f3-ed722c5e619f">
                    <akn:marker eId="hauptteil-1_para-11_bezeichnung-1_zaehlbez-1" GUID="9d6b2e01-847d-43be-a05e-692c7667fa7d" name="11"></akn:marker>§11</akn:num>
                <akn:heading GUID="dd4c9569-c091-4d4a-9d03-023f9f04c8e5" eId="hauptteil-1_para-11_überschrift-1">Änderung des Umwandlungssteuergesetzes</akn:heading>
                <akn:paragraph GUID="c1832865-d3ef-4e67-b65a-738fa6c2e3a4" eId="hauptteil-1_para-11_abs-1">
                    <akn:num GUID="8acfa447-cdd5-4209-8a8f-52026e670a31" eId="hauptteil-1_para-11_abs-1_bezeichnung-1"><akn:marker name="1" GUID="19933170-f403-434f-868c-3cc4c020d8cb" eId="hauptteil-1_para-11_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker></akn:num>
                    <akn:list GUID="bd77a96e-a767-480a-836e-1ef7ea0ac537" eId="hauptteil-1_para-11_abs-1_untergl-1">
                        <akn:intro GUID="71cac91e-38bf-4b75-8701-3a9008977cf7" eId="hauptteil-1_para-11_abs-1_untergl-1_intro-1"><akn:p GUID="05fa855b-e83f-4ac1-8d35-7d24173083a2" eId="hauptteil-1_para-11_abs-1_untergl-1_intro-1_text-1">
                            Das <akn:affectedDocument href="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1" GUID="a198b868-0ede-4b0d-a759-eb5a829aae81" eId="hauptteil-1_para-11_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"> vom 7. Dezember 2006 (BGBl. I S. 2782, 2791), das zuletzt durch Artikel 34 Absatz 8 des Gesetzes vom 22. Dezember 2023 (BGBl. 2023 I Nr. 411) geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                        </akn:p></akn:intro>
                        <akn:point GUID="81a11c64-1827-456c-b662-14ff7f645bd7" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1">
                            <akn:num GUID="6725cfbc-94ab-44a0-9a06-8adc5d52fc47" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="72ff9eac-4ddc-4ede-af79-d2130a97dd60" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>1.</akn:num>
                            <akn:list GUID="27282132-136c-4df1-8bb7-a135afced654" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1">
                                <akn:intro GUID="134793d9-7678-4af2-9dac-f314697f943c" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_intro-1"><akn:p GUID="c7966d1f-9aa1-4fe3-9bea-331d0cae07db" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_intro-1_text-1">
                                    <akn:ref href="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-15_abs-2.xml" GUID="0d8cdcbb-29fc-4e94-a697-57422f664f95" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_intro-1_text-1_ref-1">§ 15 Absatz 2 wird wie folgt geändert:</akn:ref>
                                </akn:p></akn:intro>
                                <akn:point GUID="36ff8fcb-4828-4548-be28-0373c7e3e9e2" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1">
                                    <akn:num GUID="edca222d-f23e-495a-8419-32daa119c08c" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="a" GUID="cdcf2794-ed1d-47f9-b7f0-47065c6c9891" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>a)</akn:num>
                                    <akn:content GUID="dd7cca4a-4b1f-4bc2-971f-053cad9cbe0b" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1"><akn:p GUID="ccc65174-571c-4039-8e65-949761fcee0c" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                        <akn:mod GUID="fc56ab94-6f00-435a-8b02-69dcd180828f" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-einfuegen">
                                            In <akn:ref href="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-15_abs-2/302-443.xml" GUID="6ab6c29c-88ba-415a-ae13-4e05acba1d5a" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1"> Satz 2</akn:ref>  werden nach dem Wort <akn:quotedText eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="25234249-339a-49ad-911c-2b1783917dc8" startQuote="„" endQuote="“">vollzogen</akn:quotedText> die Wörter <akn:quotedText eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="2515db83-2088-4d91-b80d-90f81a334bce" startQuote="„" endQuote="“">oder vorbereitet</akn:quotedText> eingefügt.
                                        </akn:mod>
                                    </akn:p></akn:content>
                                </akn:point>
                                <akn:point GUID="78ac89a9-4dde-483a-9b5b-70d7de9f8cf4" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2">
                                    <akn:num GUID="bd457a3c-5542-413b-975a-239b3bf199b5" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="b" GUID="d4a20509-0125-436f-83cb-375d160238b6" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>b)</akn:num>
                                    <akn:content GUID="1878c3dc-0c46-48bf-946e-f3b56932381b" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="6ceb1172-3f90-4b27-9891-b2c9538c6bec" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                        <akn:mod GUID="45352185-9cda-4cef-867b-2df508ace8a4" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
                                            <akn:ref href="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1/hauptteil-1_para-15_abs-2/221-998.xml" GUID="79ea3bd6-a074-4903-bf87-6caa1d205330" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"> Sätze 3 und 4</akn:ref> werden durch die folgenden Sätze ersetzt:
                                            Die Sätze <akn:quotedText eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="071d21c0-8846-415f-917c-7c17a057ae27" startQuote="„" endQuote="“">Als außenstehende Personen gelten Personen, die nicht ununterbrochen fünf Jahre vor der Spaltung an der übertragenden Körperschaft beteiligt waren. In den Fällen der Vorbereitung einer Veräußerung kommt Satz 2 nur zur Anwendung, wenn innerhalb von fünf Jahren nach dem steuerlichen Übertragungsstichtag eine Veräußerung mindestens eines Anteils an einer an der Spaltung beteiligten Körperschaft an außenstehende Personen erfolgt; die Veräußerung des Anteils gilt als rückwirkendes Ereignis im Sinne von § 175 Absatz 1 Satz 1 Nummer 2 der Abgabenordnung.</akn:quotedText> werden durch folgenden Text ersetzt
                                            <akn:quotedText eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="d53991a3-16d3-4481-9f3a-05687e9697b2" startQuote="„" endQuote="“">Als außenstehende Personen gelten Personen, die nicht ununterbrochen fünf Jahre vor der Spaltung an der
                                                übertragenden Körperschaft beteiligt waren. In den Fällen der Vorbereitung einer Veräußerung kommt Satz 2
                                                nur zur Anwendung, wenn innerhalb von fünf Jahren nach dem steuerlichen Übertragungsstichtag eine
                                                Veräußerung mindestens eines Anteils an einer an der Spaltung beteiligten Körperschaft an außenstehende
                                                Personen erfolgt; die Veräußerung des Anteils gilt als rückwirkendes Ereignis im Sinne von § 175 Absatz 1
                                                Satz  1  Nummer  2  der  Abgabenordnung.  Werden  innerhalb  von  fünf  Jahren  nach  dem  steuerlichen
                                                Übertragungsstichtag Anteile an einer an der Spaltung beteiligten Körperschaft, die mehr als 20 Prozent
                                                des  Wertes  der  Anteile  an  der  übertragenden  Körperschaft  am  steuerlichen  Übertragungsstichtag
                                                ausmachen,  an  außenstehende  Personen  veräußert,  ist  unwiderlegbar  zu  vermuten,  dass  durch  die

                                                Spaltung  eine  Veräußerung  im  Sinne  des  Satzes  2  vorbereitet  wurde.  Sind  an  der  übertragenden
                                                Körperschaft  außenstehende  Personen  beteiligt,  gilt  die  Spaltung  nur  dann  als  Veräußerung  an
                                                außenstehende Personen im Sinne des Satzes 2, wenn die Spaltung zu einer Wertverschiebung zugunsten
                                                dieser Personen führt. Verbundene Unternehmen im Sinne des § 271 Absatz 2 des Handelsgesetzbuchs
                                                gelten nicht als außenstehende Personen im Sinne dieses Absatzes; als Veräußerung eines Anteils an
                                                einer an der Spaltung beteiligten Körperschaft im Sinne des Satzes 4 gilt auch die mittelbare Veräußerung
                                                dieses Anteils durch ein verbundenes Unternehmen.</akn:quotedText>
                                        </akn:mod>
                                    </akn:p></akn:content>
                                </akn:point>
                            </akn:list>
                        </akn:point>
                        <akn:point GUID="6aac1aac-d487-4665-a8a8-92a7c0d7d113" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-2">
                            <akn:num GUID="55791135-27b1-4342-8477-e46e502ba45c" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="e13b8f7a-e820-47f6-961f-30a57e2a6ddb" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>2.</akn:num>
                            <akn:content GUID="24d595a0-5126-4327-9466-567ee1824dd3" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="ae487685-573f-4121-a1c1-3825aa369d63" eId="hauptteil-1_para-11_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                Dem § 27 wird folgender Absatz 19 angefügt:
                                „(19) § 15 Absatz 2 in der Fassung des Artikels 11 des Gesetzes vom 27. März 2024 (BGBl. 2024 I Nr. 108) ist
                                erstmals auf Spaltungen anzuwenden, bei denen die Anmeldung zur Eintragung in das für die Wirksamkeit des

                                jeweiligen Vorgangs maßgebende öffentliche Register nach dem 14. Juli 2023 erfolgt.“
                            </akn:p></akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>
            <!-- §12-->
            <akn:article eId="hauptteil-1_para-12" GUID="170c345b-dd19-4baf-8a5e-9244d025938a" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-12_bezeichnung-1" GUID="4542cf12-8686-4160-917c-d5db81e78cf2">
                    <akn:marker eId="hauptteil-1_para-12_bezeichnung-1_zaehlbez-1" GUID="6b3e057a-0a92-4b64-abe9-a8e2b38e1158" name="12"></akn:marker>§12</akn:num>
                <akn:paragraph GUID="f14b9add-3dc6-4f6e-a4e2-2f321b9f5dc6" eId="hauptteil-1_para-12_abs-1">
                    <akn:num eId="hauptteil-1_para-12_abs-1_bezeichnung-1" GUID="9a666831-6b7e-42fe-86b2-c459ac6b5074">
                        <akn:marker eId="hauptteil-1_para-12_abs-1_bezeichnung-1_zaehlbez-1" GUID="76cb03da-02ea-4b8b-9290-94cc996b4057" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="aed8b9e3-3557-46bf-8972-9e1f0308b6c8" eId="hauptteil-1_para-12_abs-1_inhalt-1"><akn:p GUID="564042f6-0177-4efd-8157-4f0a6eb9dec2" eId="hauptteil-1_para-12_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §13-->
            <akn:article eId="hauptteil-1_para-13" GUID="705a74fe-0a6b-4b6d-a6f2-5f5cf11ef46e" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-13_bezeichnung-1" GUID="35a91591-8b58-47be-8e39-8b4df23eece5">
                    <akn:marker eId="hauptteil-1_para-13_bezeichnung-1_zaehlbez-1" GUID="df6f2f3a-54bf-414b-a78d-f39dccce241e" name="13"></akn:marker>§13</akn:num>
                <akn:paragraph GUID="0fd92ffa-88be-4e0d-83a4-15616e79b778" eId="hauptteil-1_para-13_abs-1">
                    <akn:num eId="hauptteil-1_para-13_abs-1_bezeichnung-1" GUID="f4b56718-f4d3-413a-aa99-d9280c8ead98">
                        <akn:marker eId="hauptteil-1_para-13_abs-1_bezeichnung-1_zaehlbez-1" GUID="7808cf99-9318-47f6-bb02-eb228c4439ae" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="16e5195d-e15a-4b7c-b4d5-b79c3f8a5798" eId="hauptteil-1_para-13_abs-1_inhalt-1"><akn:p GUID="a3d8cf95-fba1-4a77-a4dc-f5530cacd554" eId="hauptteil-1_para-13_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §14-->
            <akn:article eId="hauptteil-1_para-14" GUID="7dd53189-857c-42c7-ba02-41c3368b2937" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-14_bezeichnung-1" GUID="979f9d20-5f33-4905-9e64-4d1da92e6bdf">
                    <akn:marker eId="hauptteil-1_para-14_bezeichnung-1_zaehlbez-1" GUID="ba42342c-a9ae-4d2d-a555-a3f54e6a0ad1" name="14"></akn:marker>§14</akn:num>
                <akn:paragraph GUID="0012dc4e-1463-40d8-a479-b84252bd5243" eId="hauptteil-1_para-14_abs-1">
                    <akn:num eId="hauptteil-1_para-14_abs-1_bezeichnung-1" GUID="79629d3f-9c50-495c-bd5b-30e65724dad9">
                        <akn:marker eId="hauptteil-1_para-14_abs-1_bezeichnung-1_zaehlbez-1" GUID="9ec1af32-b50c-427a-8339-ccf2f4e3fed7" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="fb70326c-4fab-4610-9ff5-8aab75afbe98" eId="hauptteil-1_para-14_abs-1_inhalt-1"><akn:p GUID="e08f2997-6017-47fd-8aa6-e2f13c9d3afa" eId="hauptteil-1_para-14_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §15-->
            <akn:article eId="hauptteil-1_para-15" GUID="a8d84868-36a7-4625-9b07-fe4458f66ca1" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-15_bezeichnung-1" GUID="16715d45-84a1-46b6-b7e3-91ede92b9823">
                    <akn:marker eId="hauptteil-1_para-15_bezeichnung-1_zaehlbez-1" GUID="b1222588-8a37-4449-950e-f8063e9bbd3f" name="15"></akn:marker>§15</akn:num>
                <akn:paragraph GUID="69eee9d9-22e7-4572-bca2-eccbe582baf6" eId="hauptteil-1_para-15_abs-1">
                    <akn:num eId="hauptteil-1_para-15_abs-1_bezeichnung-1" GUID="6b0a3008-7bd6-4bef-9c27-35186519359c">
                        <akn:marker eId="hauptteil-1_para-15_abs-1_bezeichnung-1_zaehlbez-1" GUID="273d6ab0-9a79-48b7-a4b3-b487720e6772" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="6fd066ef-31ec-480e-bd49-05095f6cbd48" eId="hauptteil-1_para-15_abs-1_inhalt-1"><akn:p GUID="c0bf44a0-a33e-4e7a-ac27-f24f152141a9" eId="hauptteil-1_para-15_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §16-->
            <akn:article eId="hauptteil-1_para-16" GUID="cd51af28-5db1-4d4c-ae23-ac5b3742613e" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-16_bezeichnung-1" GUID="80d8f934-8f58-4ae8-a8f9-843f66390fec">
                    <akn:marker eId="hauptteil-1_para-16_bezeichnung-1_zaehlbez-1" GUID="b6b32743-dc1a-4d3a-8bd2-6f307394a485" name="16"></akn:marker>§16</akn:num>
                <akn:paragraph GUID="66eb1100-279b-48b8-8419-425192bc9c53" eId="hauptteil-1_para-16_abs-1">
                    <akn:num eId="hauptteil-1_para-16_abs-1_bezeichnung-1" GUID="e4ac9fad-2131-48a6-9831-794023c22f31">
                        <akn:marker eId="hauptteil-1_para-16_abs-1_bezeichnung-1_zaehlbez-1" GUID="12595933-9cfb-4e0b-8299-1d0960e55a16" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="2b4fdbe0-f388-4337-998e-22adeaace890" eId="hauptteil-1_para-16_abs-1_inhalt-1"><akn:p GUID="0fa7e6e5-9645-4fe3-bc5d-030e0c2ce9c9" eId="hauptteil-1_para-16_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §17-->
            <akn:article eId="hauptteil-1_para-17" GUID="56bc720f-e450-4288-b024-0c7a6d0a86a6" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-17_bezeichnung-1" GUID="106acd9d-deb4-45c1-99be-8a81b07dd3de">
                    <akn:marker eId="hauptteil-1_para-17_bezeichnung-1_zaehlbez-1" GUID="e2aa6a48-1145-4668-a6a9-ee213372b6b2" name="17"></akn:marker>§17</akn:num>
                <akn:paragraph GUID="b1fd674e-f956-4ab5-800e-fbd9fb130e39" eId="hauptteil-1_para-17_abs-1">
                    <akn:num eId="hauptteil-1_para-17_abs-1_bezeichnung-1" GUID="a59bd695-5a5c-40a4-a3a4-dd8de608250f">
                        <akn:marker eId="hauptteil-1_para-17_abs-1_bezeichnung-1_zaehlbez-1" GUID="7971a6b1-7119-40c1-b690-b8aa0fd70565" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="fc1bc033-49c3-4674-b8f5-923bba3620e8" eId="hauptteil-1_para-17_abs-1_inhalt-1"><akn:p GUID="3f23ef34-e2be-4f48-945b-95031cb81c6d" eId="hauptteil-1_para-17_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §18-->
            <akn:article eId="hauptteil-1_para-18" GUID="4b386140-6c09-47d2-8c2b-c9d4061b1d16" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-18_bezeichnung-1" GUID="12f88467-2c2c-49bd-959d-e264b0b90374">
                    <akn:marker eId="hauptteil-1_para-18_bezeichnung-1_zaehlbez-1" GUID="c173ef78-10d0-4c3c-9332-df3cecc655c0" name="18"></akn:marker>§18</akn:num>
                <akn:paragraph GUID="69ad2f8a-1074-4f5b-9429-1a34220e67f0" eId="hauptteil-1_para-18_abs-1">
                    <akn:num eId="hauptteil-1_para-18_abs-1_bezeichnung-1" GUID="e8498a3b-261f-4e60-ae89-96020d20f710">
                        <akn:marker eId="hauptteil-1_para-18_abs-1_bezeichnung-1_zaehlbez-1" GUID="9709cc18-71b2-41d2-85ec-afaabc2a7781" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="90c86a81-0448-4fac-855b-9877b7873345" eId="hauptteil-1_para-18_abs-1_inhalt-1"><akn:p GUID="9ec30711-98f1-43a9-94b0-1f6d3e1a6eb6" eId="hauptteil-1_para-18_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §19-->
            <akn:article eId="hauptteil-1_para-19" GUID="954deb64-7f39-49a4-9646-372d3f1a2d0d" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-19_bezeichnung-1" GUID="7716eb01-457e-4237-a8c4-efd97a7adb42">
                    <akn:marker eId="hauptteil-1_para-19_bezeichnung-1_zaehlbez-1" GUID="52ac1e49-fd0f-440d-939c-83407a0c4ddf" name="19"></akn:marker>§19</akn:num>
                <akn:paragraph GUID="f88df7aa-09b8-4ac4-a60f-4399ac8c0ea7" eId="hauptteil-1_para-19_abs-1">
                    <akn:num eId="hauptteil-1_para-19_abs-1_bezeichnung-1" GUID="8e9c9fff-fd18-44a5-b2a7-24c7b4aeabef">
                        <akn:marker eId="hauptteil-1_para-19_abs-1_bezeichnung-1_zaehlbez-1" GUID="6284a6c3-6e28-4607-b7ec-a557cbbd2623" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="1b39fc5f-95d6-44a7-9fc5-7fa7dcc35401" eId="hauptteil-1_para-19_abs-1_inhalt-1"><akn:p GUID="3002b828-7e20-4d0a-b6c9-34446dea8150" eId="hauptteil-1_para-19_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §20-->
            <akn:article eId="hauptteil-1_para-20" GUID="ae509f70-aa93-4169-8e31-277c7a0cd762" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="d98ea318-c296-473e-be51-e08903bdd26c">
                    <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" GUID="bfa7e7f1-81c6-4419-a9aa-21e5dddc24e4" name="20"></akn:marker>§20</akn:num>
                <akn:paragraph GUID="0f540208-d535-4df1-97fd-2ebcec47c073" eId="hauptteil-1_para-20_abs-1">
                    <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="7ee7f421-8684-4796-b7fd-b3758ca2598c">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" GUID="21b1dc7c-ed36-4d5c-a10f-666ecdd80c5f" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="b6634e8f-c8f1-4d7f-9c33-e70692be3c9c" eId="hauptteil-1_para-20_abs-1_inhalt-1"><akn:p GUID="16c99b24-06be-4af4-97b8-a9a6c773c22f" eId="hauptteil-1_para-20_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §21-->
            <akn:article eId="hauptteil-1_para-21" GUID="424939b2-67e3-4185-b146-42d71c631fe5" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-21_bezeichnung-1" GUID="f3bf0469-d948-4d12-8b9a-c768c13ade2d">
                    <akn:marker eId="hauptteil-1_para-21_bezeichnung-1_zaehlbez-1" GUID="12f2206e-a73f-43e4-b060-fbe2e1297ce4" name="21"></akn:marker>§21</akn:num>
                <akn:paragraph GUID="feea1193-b843-45ef-82d9-9e20f1453bc1" eId="hauptteil-1_para-21_abs-1">
                    <akn:num eId="hauptteil-1_para-21_abs-1_bezeichnung-1" GUID="5c2d60ba-ea74-44c3-9518-aa26178b94bf">
                        <akn:marker eId="hauptteil-1_para-21_abs-1_bezeichnung-1_zaehlbez-1" GUID="db08acbc-72ea-4c43-a690-07a5d4b778b0" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="1f916138-af53-4f17-94ff-cb4a9d63f4fb" eId="hauptteil-1_para-21_abs-1_inhalt-1"><akn:p GUID="7bbc1faa-6c4f-487b-9ae7-6b1aa0f4b35f" eId="hauptteil-1_para-21_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §22-->
            <akn:article eId="hauptteil-1_para-22" GUID="a4c0c103-ed32-4ed6-81b6-b41957c782d0" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-22_bezeichnung-1" GUID="2fbcd6d1-8720-4d57-ba14-28985f926966">
                    <akn:marker eId="hauptteil-1_para-22_bezeichnung-1_zaehlbez-1" GUID="acdf3def-f910-44e0-97a5-ddf70aa63f79" name="22"></akn:marker>§22</akn:num>
                <akn:paragraph GUID="c61ea3f8-baab-4f81-a0c2-adb2381f5e70" eId="hauptteil-1_para-22_abs-1">
                    <akn:num eId="hauptteil-1_para-22_abs-1_bezeichnung-1" GUID="2cdca59c-e3d1-4fde-95ef-ad0521eb8c2c">
                        <akn:marker eId="hauptteil-1_para-22_abs-1_bezeichnung-1_zaehlbez-1" GUID="b78ac3e1-2466-46fe-a6fd-e90762ad575d" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="3eb5536e-29cb-4533-89e5-f0b309c73397" eId="hauptteil-1_para-22_abs-1_inhalt-1"><akn:p GUID="fcda42f9-8d60-4f85-b18d-6b1e614e8528" eId="hauptteil-1_para-22_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §23-->
            <akn:article eId="hauptteil-1_para-23" GUID="e405acf2-b145-4ec6-b368-28586c3c2c5e" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-23_bezeichnung-1" GUID="1ff4d744-7446-49dc-a4ba-8cb48e80127c">
                    <akn:marker eId="hauptteil-1_para-23_bezeichnung-1_zaehlbez-1" GUID="ce3d18cb-c329-4c3d-9a6a-83c10ac8e6f5" name="23"></akn:marker>§23</akn:num>
                <akn:paragraph GUID="e59512ac-e2c4-4d61-9fcd-49ccc58e7751" eId="hauptteil-1_para-23_abs-1">
                    <akn:num eId="hauptteil-1_para-23_abs-1_bezeichnung-1" GUID="697904a4-a568-4780-8f9a-abcfcd31e51b">
                        <akn:marker eId="hauptteil-1_para-23_abs-1_bezeichnung-1_zaehlbez-1" GUID="eb61c7e7-f341-49a7-be79-f0204fbb2c89" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="81c478aa-4ef7-408a-be13-09015f11209d" eId="hauptteil-1_para-23_abs-1_inhalt-1"><akn:p GUID="9c3aa6cf-be45-424a-bca9-3040c03dd973" eId="hauptteil-1_para-23_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §24-->
            <akn:article eId="hauptteil-1_para-24" GUID="53edf023-d23e-4c3f-aadc-d317342e0295" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-24_bezeichnung-1" GUID="b911ed14-7e1b-4c92-bbab-95a0d63f13bc">
                    <akn:marker eId="hauptteil-1_para-24_bezeichnung-1_zaehlbez-1" GUID="d5e49400-64b9-485c-b6ae-79acc786bfe1" name="24"></akn:marker>§24</akn:num>
                <akn:paragraph GUID="14ef0b6d-a177-4cee-bdc5-8df5e82c5599" eId="hauptteil-1_para-24_abs-1">
                    <akn:num eId="hauptteil-1_para-24_abs-1_bezeichnung-1" GUID="cd05cc93-3e85-42ea-b8d6-143d8de153d6">
                        <akn:marker eId="hauptteil-1_para-24_abs-1_bezeichnung-1_zaehlbez-1" GUID="744a977d-2979-4d89-a787-c3c7129a3399" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="51db4766-dfd1-41b5-aa14-963e237ec73a" eId="hauptteil-1_para-24_abs-1_inhalt-1"><akn:p GUID="df45acbd-b294-455e-8ac4-0dd688221178" eId="hauptteil-1_para-24_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §25-->
            <akn:article eId="hauptteil-1_para-25" GUID="fbab827d-776f-4959-b700-b99558a3219d" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-25_bezeichnung-1" GUID="98d1047f-8d44-4c90-81c6-3f65fd8e2487">
                    <akn:marker eId="hauptteil-1_para-25_bezeichnung-1_zaehlbez-1" GUID="4c41e917-a382-43ba-955b-c812b0dfff31" name="25"></akn:marker>§25</akn:num>
                <akn:paragraph GUID="cfcc9b5f-e67f-4409-93b6-5473fb9c015f" eId="hauptteil-1_para-25_abs-1">
                    <akn:num eId="hauptteil-1_para-25_abs-1_bezeichnung-1" GUID="c6a22e73-283a-4a3a-b4c6-6cdfe9f43a9d">
                        <akn:marker eId="hauptteil-1_para-25_abs-1_bezeichnung-1_zaehlbez-1" GUID="c4c2bdb1-67c4-4e11-8ec4-6437a5f4dfea" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="068a1f6e-c65c-4146-a1d8-09150bd9b44b" eId="hauptteil-1_para-25_abs-1_inhalt-1"><akn:p GUID="6d28c311-fb08-465e-81bd-626ac712e520" eId="hauptteil-1_para-25_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §26-->
            <akn:article eId="hauptteil-1_para-26" GUID="6c7b37c6-7dc8-4a83-bcf0-acf67b69f377" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-26_bezeichnung-1" GUID="4e9e2071-ab50-40f4-8bdf-ba8c88a19ddc">
                    <akn:marker eId="hauptteil-1_para-26_bezeichnung-1_zaehlbez-1" GUID="66adfd43-ecc7-4740-a6f5-98d812130a8b" name="26"></akn:marker>§26</akn:num>
                <akn:paragraph GUID="0dbd7eb7-7bf9-43d5-b731-0be947f85105" eId="hauptteil-1_para-26_abs-1">
                    <akn:num eId="hauptteil-1_para-26_abs-1_bezeichnung-1" GUID="a9de9165-dbe0-410d-aca8-f5df6716e780">
                        <akn:marker eId="hauptteil-1_para-26_abs-1_bezeichnung-1_zaehlbez-1" GUID="bc76c077-e269-4521-b2d6-25ccb3a31d83" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="ceb3a98e-abdb-44af-ab4b-42a446d0edc2" eId="hauptteil-1_para-26_abs-1_inhalt-1"><akn:p GUID="4f717e61-7a14-4e83-8c6d-45aba2f14ed9" eId="hauptteil-1_para-26_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §27-->
            <akn:article eId="hauptteil-1_para-27" GUID="06bef0e9-0e24-4e6e-ae67-0f1427d2596f" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-27_bezeichnung-1" GUID="280b8ba7-c0c4-4642-a41f-80d74195a764">
                    <akn:marker eId="hauptteil-1_para-27_bezeichnung-1_zaehlbez-1" GUID="696158fb-0e1c-45f7-b6f3-947fea7dc3fa" name="27"></akn:marker>§27</akn:num>
                <akn:paragraph GUID="1a841b5c-8791-4fa0-9710-b7ab07b906c4" eId="hauptteil-1_para-27_abs-1">
                    <akn:num eId="hauptteil-1_para-27_abs-1_bezeichnung-1" GUID="07e714b7-1489-42e8-b5c1-e9af4a251c30">
                        <akn:marker eId="hauptteil-1_para-27_abs-1_bezeichnung-1_zaehlbez-1" GUID="83732175-acbb-4d9f-b03b-2442db59c96c" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="6dc35edf-93af-4fc3-9dd1-df7029f789bd" eId="hauptteil-1_para-27_abs-1_inhalt-1"><akn:p GUID="753b51f7-a2b0-4e55-aff2-4b1cf5cce4e1" eId="hauptteil-1_para-27_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §28-->
            <akn:article eId="hauptteil-1_para-28" GUID="d2c46e2c-de1a-4e54-958a-8c8e570110ea" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-28_bezeichnung-1" GUID="4d6b057a-0b99-4088-84bb-7b02321af0d4">
                    <akn:marker eId="hauptteil-1_para-28_bezeichnung-1_zaehlbez-1" GUID="45c0510e-9e41-459c-892c-4a41af0f7a8e" name="28"></akn:marker>§28</akn:num>
                <akn:paragraph GUID="3888693f-5462-4c48-86f2-7516e96cfce7" eId="hauptteil-1_para-28_abs-1">
                    <akn:num eId="hauptteil-1_para-28_abs-1_bezeichnung-1" GUID="429dc118-90f1-4dfe-a447-d4532f2c6c90">
                        <akn:marker eId="hauptteil-1_para-28_abs-1_bezeichnung-1_zaehlbez-1" GUID="c178da80-c277-4e07-abb9-93c4b43b36d5" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="bd15e8e4-a7ff-4bc4-b493-8dfb3447a8a6" eId="hauptteil-1_para-28_abs-1_inhalt-1"><akn:p GUID="428f0cab-cdb9-4898-99a5-41df17de33e5" eId="hauptteil-1_para-28_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §29-->
            <akn:article eId="hauptteil-1_para-29" GUID="d3840b94-8be7-4736-8b39-7756d99ff954" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-29_bezeichnung-1" GUID="57dcb275-6ed6-4097-99d1-657a266d2cfa">
                    <akn:marker eId="hauptteil-1_para-29_bezeichnung-1_zaehlbez-1" GUID="d40848e1-c685-4980-b552-bd6aa70b222a" name="29"></akn:marker>§29</akn:num>
                <akn:paragraph GUID="23f19f61-aef6-4ce5-8c2a-730fccd75ddb" eId="hauptteil-1_para-29_abs-1">
                    <akn:num eId="hauptteil-1_para-29_abs-1_bezeichnung-1" GUID="d11410b3-4bb7-4a14-9796-d80a0100af16">
                        <akn:marker eId="hauptteil-1_para-29_abs-1_bezeichnung-1_zaehlbez-1" GUID="0c57e283-d109-47ce-8966-cabceb7c8d5c" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="75953a36-b846-4fe2-b92b-992c9eb19ec3" eId="hauptteil-1_para-29_abs-1_inhalt-1"><akn:p GUID="dcbb1e9e-5045-4e14-ae01-250dedead34c" eId="hauptteil-1_para-29_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §30-->
            <akn:article eId="hauptteil-1_para-30" GUID="6176b12f-d195-416a-bd86-598491247bf1" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-30_bezeichnung-1" GUID="852b74af-e8f6-4dc5-9fda-52de493d1623">
                    <akn:marker eId="hauptteil-1_para-30_bezeichnung-1_zaehlbez-1" GUID="3b55f5e2-bb8f-4817-9063-ef12cf3ee4c1" name="30"></akn:marker>§30</akn:num>
                <akn:paragraph GUID="f9a5eed2-d614-4837-9997-73649a09f181" eId="hauptteil-1_para-30_abs-1">
                    <akn:num eId="hauptteil-1_para-30_abs-1_bezeichnung-1" GUID="bf080f64-7355-4a12-b4fc-8db75d19d646">
                        <akn:marker eId="hauptteil-1_para-30_abs-1_bezeichnung-1_zaehlbez-1" GUID="76a7749b-9c90-4573-9797-5a7a4a388e9f" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="dcf89e59-3ae6-4f53-ac3b-e401514c9e0c" eId="hauptteil-1_para-30_abs-1_inhalt-1"><akn:p GUID="61c62683-143f-4785-a136-bd1ad8be85af" eId="hauptteil-1_para-30_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §31-->
            <akn:article eId="hauptteil-1_para-31" GUID="3fc41b0d-6e69-45eb-b5ac-a9cee3d09bdc" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-31_bezeichnung-1" GUID="9b42ead4-c029-457d-8875-e8b94d173c8b">
                    <akn:marker eId="hauptteil-1_para-31_bezeichnung-1_zaehlbez-1" GUID="ea9704a3-4666-49e9-a04c-5840379cc89b" name="31"></akn:marker>§31</akn:num>
                <akn:paragraph GUID="7baf2c99-1570-402e-8775-ca46f7b7a6f1" eId="hauptteil-1_para-31_abs-1">
                    <akn:num eId="hauptteil-1_para-31_abs-1_bezeichnung-1" GUID="3451469e-67e9-4004-872c-b9ce16db321f">
                        <akn:marker eId="hauptteil-1_para-31_abs-1_bezeichnung-1_zaehlbez-1" GUID="abbb5fc3-bf80-421e-aa10-2a564431a086" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="58ae1998-52be-462d-95e7-a2f202b21156" eId="hauptteil-1_para-31_abs-1_inhalt-1"><akn:p GUID="9fe672a1-c968-4d40-b4a2-8ae628538760" eId="hauptteil-1_para-31_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §32-->
            <akn:article eId="hauptteil-1_para-32" GUID="034c835c-9b84-4c7d-a8c3-c6c573f588e1" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-32_bezeichnung-1" GUID="9f88776d-cde4-423d-a236-99073e9f9a94">
                    <akn:marker eId="hauptteil-1_para-32_bezeichnung-1_zaehlbez-1" GUID="8423561e-d11a-4e1a-937c-ee20fe7f08c3" name="32"></akn:marker>§32</akn:num>
                <akn:paragraph GUID="96825283-3ebe-4f1f-a5ac-ba67fb76032c" eId="hauptteil-1_para-32_abs-1">
                    <akn:num eId="hauptteil-1_para-32_abs-1_bezeichnung-1" GUID="814fc782-8cf6-4fdc-81f5-1e0cfb2e37e7">
                        <akn:marker eId="hauptteil-1_para-32_abs-1_bezeichnung-1_zaehlbez-1" GUID="4c320631-f373-4432-9614-78681524d723" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="5c01e3a1-baff-4912-b05c-192f571930eb" eId="hauptteil-1_para-32_abs-1_inhalt-1"><akn:p GUID="5e4ed861-e220-4147-9fce-12701c5c1a66" eId="hauptteil-1_para-32_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §33-->
            <akn:article eId="hauptteil-1_para-33" GUID="001dd2f4-e68d-4a33-a98c-160aaa3e3902" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-33_bezeichnung-1" GUID="78b1519b-4e86-46d5-9251-cc6543727a02">
                    <akn:marker eId="hauptteil-1_para-33_bezeichnung-1_zaehlbez-1" GUID="bb96d820-6ce0-4b15-bfc5-44a1525b3a7f" name="33"></akn:marker>§33</akn:num>
                <akn:paragraph GUID="08718c19-89f7-45f0-b52c-0fa47c372f12" eId="hauptteil-1_para-33_abs-1">
                    <akn:num eId="hauptteil-1_para-33_abs-1_bezeichnung-1" GUID="d9e879f2-9c55-4480-90a2-8ff2882e8f1a">
                        <akn:marker eId="hauptteil-1_para-33_abs-1_bezeichnung-1_zaehlbez-1" GUID="0c8cf6e1-06dd-4d1b-823c-ca34c411f4e6" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="e4af8797-dfb5-475e-b4a4-2e16653b9434" eId="hauptteil-1_para-33_abs-1_inhalt-1"><akn:p GUID="4dba2879-0de4-49e7-bf37-f1678cb06e65" eId="hauptteil-1_para-33_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §34-->
            <akn:article eId="hauptteil-1_para-34" GUID="71a583ce-51ab-4acf-ab5f-c0b18f7e59a0" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-34_bezeichnung-1" GUID="00ba8f12-feff-4ecc-a317-8ea45f8f2363">
                    <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1" GUID="a702503e-2c0d-4bc3-8eef-48a8b3d828dd" name="34"></akn:marker>§34</akn:num>
                <akn:paragraph GUID="be298799-bd2f-4f2f-bf7b-d052f3ed73d3" eId="hauptteil-1_para-34_abs-1">
                    <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1" GUID="0cbbb0b7-01a4-41c7-8e88-0ab8e789ca3b">
                        <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1" GUID="6a93535b-e0f5-4b06-8345-548d5c25d2c1" name="1"></akn:marker>(1)</akn:num>
                    <akn:content GUID="38ee5b8a-7eba-4793-9a2e-a7ee67c77247" eId="hauptteil-1_para-34_abs-1_inhalt-1"><akn:p GUID="06048738-e2aa-4eab-a13a-7b9d2f73d19d" eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
            </akn:article>
            <!-- §335-->
            <akn:article eId="hauptteil-1_para-35" GUID="1af21459-b9df-47aa-a084-435df14c546b" period="#geltungszeitgr-3" refersTo="geltungszeitregel">
                <akn:num eId="hauptteil-1_para-35_bezeichnung-1" GUID="518b00f3-3f1c-41b2-8075-3bc644ed58b4">
                    <akn:marker eId="hauptteil-1_para-35_bezeichnung-1_zaehlbez-1" GUID="3f788588-1f18-4776-92c4-5465580dae24" name="35"></akn:marker>§35</akn:num>
                <akn:paragraph GUID="7c715b4f-67d0-4665-bc12-de453e8a7119" eId="hauptteil-1_para-35_abs-1">
                    <akn:num eId="hauptteil-1_para-35_abs-1_bezeichnung-1" GUID="8373fc61-cc1b-4588-aa1f-8c29a2bc644e">
                        <akn:marker eId="hauptteil-1_para-35_abs-1_bezeichnung-1_zaehlbez-1" GUID="bfb2cc73-4acd-4bab-a382-fff76266e0f0" name="1"></akn:marker>(1)</akn:num>
                    <akn:list GUID="0af5ebc9-805e-40d7-b5c2-392977595974" eId="hauptteil-1_para-35_abs-1_untergl-1">
                        <akn:intro GUID="59c94bfd-0014-44df-a916-35254a314936" eId="hauptteil-1_para-35_abs-1_untergl-1_intro-1"><akn:p GUID="82015b74-1c1d-4354-a7d8-482a904bbe14" eId="hauptteil-1_para-35_abs-1_untergl-1_intro-1_text-1"></akn:p></akn:intro>
                        <akn:point GUID="9a8983f6-c5a5-4554-8231-cfd33319e0a9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1">
                            <akn:num GUID="e48171db-d319-4b3c-8b6f-a305e401d87f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_bezeichnung-1"><akn:marker name="1" GUID="6f2bb4ba-76ab-4561-b9ff-7f19841e188f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)</akn:num>
                            <akn:content GUID="86f1d04f-cf83-4851-a463-83753ba33208" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_inhalt-1"><akn:p GUID="d917836c-1926-479d-b14f-824dd03d3633" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                                Dieses Gesetz tritt vorbehaltlich der Absätze 2 bis 9 am Tag nach der Verkündung in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="f61ff441-1986-481d-894e-94e9e250519e" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2">
                            <akn:num GUID="cbbf73d6-c5bc-4f04-80d0-bee5183a5168" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_bezeichnung-1"><akn:marker name="2" GUID="bfa00b26-6e7d-45d2-bb9a-c1ab69a8b8b1" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"></akn:marker>(2)</akn:num>
                            <akn:content GUID="d89f2a49-aee7-4ca8-9d56-4b69f7de13b4" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_inhalt-1"><akn:p GUID="aa246a74-02d6-420c-89b4-6375d86547ff" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">
                                Artikel 26 tritt mit Wirkung vom 1. Januar 2020 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="c32b1cd9-e6f1-4bbe-aed4-9c19c9dd5bf6" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3">
                            <akn:num GUID="39e1f85f-f341-45c4-a46e-90cd72665f67" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_bezeichnung-1"><akn:marker name="3" GUID="ee64b4b2-6e17-43c9-b8a9-16ab02976905" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_bezeichnung-1_zaehlbez-1"></akn:marker>(3)</akn:num>
                            <akn:content GUID="30323067-5d43-4859-8f98-3d5c6ce98fe5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_inhalt-1"><akn:p GUID="1d983209-4248-4f09-ad59-e78fc4c8fd83" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">
                                Die Artikel 1 und 7 treten mit Wirkung vom 1. Januar 2023 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="4c623505-79dd-48d2-91e6-b9a658c01b3f" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4">
                            <akn:num GUID="9ececa14-a57d-45bf-89c8-141c42d48368" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_bezeichnung-1"><akn:marker name="4" GUID="b6a42b99-b077-4ae0-b50b-c8008b429884" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_bezeichnung-1_zaehlbez-1"></akn:marker>(4)</akn:num>
                            <akn:content GUID="96e7836c-a6b9-4f70-93dc-87073d22ed40" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_inhalt-1"><akn:p GUID="c8425bf7-47c2-4da7-9904-1e1a72bb7586" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">
                                Die Artikel 2, 12, 20, 31 und 32 Nummer 2 treten mit Wirkung vom 1. Januar 2024 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="db3679b0-8b1e-44eb-a15e-cfabca814a21" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5">
                            <akn:num GUID="6a285e06-75c5-4070-bb47-cff4cb5c26a5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_bezeichnung-1"><akn:marker name="5" GUID="be9cd5b6-7704-449a-ad6f-c59a78d1dcbf" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_bezeichnung-1_zaehlbez-1"></akn:marker>(5)</akn:num>
                            <akn:content GUID="f0f6f73d-6d93-43e1-84c4-798d56ed716c" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_inhalt-1"><akn:p GUID="edefafec-29f3-475f-a03b-69d216a42d93" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-5_inhalt-1_text-1">
                                Die Artikel 4 und 22 treten am 1. April 2024 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="45f70562-f0c9-46ae-bffb-d4d2e763629a" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6">
                            <akn:num GUID="ce2a08f8-1099-4cff-9ed3-4bb4bd386434" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_bezeichnung-1"><akn:marker name="6" GUID="0d569870-d35f-4bc1-94d3-364c38209b83" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_bezeichnung-1_zaehlbez-1"></akn:marker>(6)</akn:num>
                            <akn:content GUID="48da3995-398d-478e-9aa9-5a0ce3904442" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_inhalt-1"><akn:p GUID="0a7edbf9-612c-43ad-b728-d8e37e2e6c68" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-6_inhalt-1_text-1">
                                Die Artikel 5, 9, 23, 24 und 27 Nummer 6 treten am 1. Januar 2025 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="8f6b3b22-c532-429b-b674-220ee7372c12" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7">
                            <akn:num GUID="ad245874-a7bd-41d5-97d3-d316a417bc9a" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_bezeichnung-1"><akn:marker name="7" GUID="b667b6f1-737c-445d-a3fa-6dc12be50c36" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_bezeichnung-1_zaehlbez-1"></akn:marker>(7)</akn:num>
                            <akn:content GUID="5f406ea7-324c-4a90-ba13-6d9d239e8ee9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_inhalt-1"><akn:p GUID="d38908ad-6bbd-4d1a-88e6-f697ec00cf12" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-7_inhalt-1_text-1">
                                Artikel 32 Nummer 3 und 4 tritt am 1. Juli 2025 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="61b0fc82-738c-4ebb-bdff-9f1eea4e96b9" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8">
                            <akn:num GUID="634aa27a-df2f-4bb6-9fab-418965ae7217" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_bezeichnung-1"><akn:marker name="8" GUID="d3b417fa-0747-47db-8280-23c24de06f1d" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_bezeichnung-1_zaehlbez-1"></akn:marker>(8)</akn:num>
                            <akn:content GUID="3d7a8657-95eb-4e6d-9ad0-53e7e31883bc" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_inhalt-1"><akn:p GUID="dd2b467b-54b3-48e3-8e62-cf232df38975" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-8_inhalt-1_text-1">
                                Die Artikel 14 und 16 treten am 1. Januar 2027 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="545da796-e05b-491e-a64d-c4ed9ee5fb09" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9">
                            <akn:num GUID="c5174939-4a22-49b4-8e91-f29f25997c75" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_bezeichnung-1"><akn:marker name="9" GUID="2c26734b-75ac-4744-a815-5880760418b5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_bezeichnung-1_zaehlbez-1"></akn:marker>(9)</akn:num>
                            <akn:content GUID="2430d65b-f2f5-4cf9-a78d-997de8bb1bb1" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_inhalt-1"><akn:p GUID="e37f15d9-0e99-4719-948b-d18a0945f0ac" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-9_inhalt-1_text-1">
                                Artikel 6 tritt am 1. Januar 2028 in Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                        <akn:point GUID="1861791b-36e7-4742-8e81-909e63735de5" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10">
                            <akn:num GUID="569c8de9-11ba-45eb-8c09-d325e6a9f373" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_bezeichnung-1"><akn:marker name="10" GUID="8711e29d-2080-46af-bac7-4a623ce1af18" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_bezeichnung-1_zaehlbez-1"></akn:marker>(10)</akn:num>
                            <akn:content GUID="d199a332-8e7d-46c6-b3c5-84cce841fc29" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_inhalt-1"><akn:p GUID="29b55044-6570-485f-97d1-7db6e17ec6c7" eId="hauptteil-1_para-35_abs-1_untergl-1_listenelem-10_inhalt-1_text-1">
                                Die §§ 124 und 125 des Vierten Buches Sozialgesetzbuch, § 202a des Fünften Buches Sozialgesetzbuch
                                sowie § 55b Absatz 2 des Elften Buches Sozialgesetzbuch treten am 1. Juli 2026 außer Kraft.
                            </akn:p></akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>
        </akn:body>
    </akn:act>
</akn:akomaNtoso>');

-- Target law
DELETE FROM norms where guid = 'a3fc5402-0edc-4ed6-a9fe-027307d20d78';
INSERT INTO norms (guid, eli, xml)
VALUES ('a3fc5402-0edc-4ed6-a9fe-027307d20d78', 'eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?><!--
	##################################################################################
	Discovery Metadaten&#x2D;Struktur für nichtdefinierte Metadaten auf LDML.de&#x2D;Ebene
	2024 Copyright (C) 2024 Digitalservice GmbH des Bundes
	##################################################################################
--><?xml-model href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/26e27e3a56996587fe737639fd80e2c4a8fe0311/Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
   <akn:act name="regelungstext">
<!-- Metadaten-->
      <akn:meta eId="meta-1" GUID="52226401-eb20-4c64-8077-bacdcf3bf584">
         <akn:identification eId="meta-1_ident-1" GUID="e225e375-84cf-4f86-8b81-e3e8d6755e22" source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="1223336c-174a-4ebe-96b6-23b73d74249d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="8ab599fb-6c27-434a-a769-2f0b15ea3761" value="eli/bund/bgbl-1/2006/s2782/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a90733dd-4780-472d-8371-a3690f2e869a" value="eli/bund/bgbl-1/2006/s2782"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="f6c40af6-f65c-4eef-8ffe-9696eb2f0d2b" name="übergreifende-id" value="2c1a6ec9-7161-4e2f-8999-91219180d992"></akn:FRBRalias>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="0b3ed103-3c9f-4df9-970f-4e296b2e6811" date="2006-12-07" name="verkuendungsfassung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="2fa426b3-6592-40ed-a1ff-65fd5ddc07d1" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="0b8bfe94-3e70-4db2-94b5-cf3f1a37fc7e" value="de"></akn:FRBRcountry>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="439e34d4-7228-48e3-bfb3-9c6f30fefdc5" value="s2782"></akn:FRBRnumber>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="a70716e1-c0c7-44d3-a795-a04fcc25cca8" value="bgbl-1"></akn:FRBRname>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="af948830-e615-4dd5-8237-b8c830939f78" value="regelungstext-1"></akn:FRBRsubtype>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="21777967-2f44-49be-845d-d73c0ffb4d5b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="85e064f4-f56f-4cef-86a4-6801cc71c712" value="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="e8f52739-79c1-4e59-9db2-004180c8b29c" value="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu"></akn:FRBRuri>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="c2e78146-833b-4d78-a055-27f05eeaf018" name="aktuelle-version-id" value="a3fc5402-0edc-4ed6-a9fe-027307d20d78"></akn:FRBRalias>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2fd1cd57-d43c-4f95-82ee-cfc6f890741c" name="nachfolgende-version-id" value="336e3d60-663d-43dd-b0f6-5df76557c001"></akn:FRBRalias>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="6aace8d0-001f-4829-9794-79857835ee45" href="recht.bund.de/institution/bundesregierung"></akn:FRBRauthor>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="a6dc32d0-140d-4147-88b2-d64639b91742" date="2021-06-02" name="verkuendung"></akn:FRBRdate>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="b872861e-0add-4bbb-ba90-a5edb3c40038" language="deu"></akn:FRBRlanguage>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="22170c7e-8c51-4d7c-9917-16d5ea7494fc" value="1"></akn:FRBRversionNumber>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="b81a0b62-bd3c-4b02-b134-250115d9f6f8">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="4c881fca-0e36-440e-a5f9-513079ccc77f" value="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1.xml"></akn:FRBRthis>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="d0491f91-d599-46d4-ba61-c7426b607e4f" value="eli/bund/bgbl-1/2006/s2782/2023-12-23/1/deu/regelungstext-1.xml"></akn:FRBRuri>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="1d52860f-0990-4fd4-8b2f-32da6f99c612" date="2023-12-23" name="generierung"></akn:FRBRdate>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="1d015a20-823e-496e-808d-27c8fec85b51" href="recht.bund.de"></akn:FRBRauthor>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="758b3dc8-a5f1-46fe-bda3-97cd0930da67" value="xml"></akn:FRBRformat>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle eId="meta-1_lebzykl-1" GUID="cef30a63-d738-4712-bac8-50fda44949e2" source="attributsemantik-noch-undefiniert">
<!-- 1. Originales Gesetz -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="65f3910d-4e79-45f8-a24d-44ef7523963a" date="1994-10-28" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung"></akn:eventRef>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="22cfa201-1d32-4655-afae-4bf01e9ba75c" date="1994-10-28" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten"></akn:eventRef>
            <!-- Neufassung 1-->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="85e92fc8-b381-47bb-a476-c35fa2794336" date="2002-10-25" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="neufassung"></akn:eventRef>
            <!-- Neufassung 2-->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="42c9fdcd-6335-4e84-9ab7-b2c955f5cbaf" date="2006-12-07" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="neufassung"></akn:eventRef>
            <!-- Letzte Änderung -->
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-5" GUID="85e92fc8-b381-47bb-a476-c35fa2794337" date="2023-12-23" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"></akn:eventRef>
         </akn:lifecycle>
         <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16" start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f" start="#meta-1_lebzykl-1_ereignis-3" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c5">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac755" start="#meta-1_lebzykl-1_ereignis-4" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="d371ce01-5cd9-4bc9-9a54-592d2c2dd59c">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="4d559020-a51a-4dd6-96ab-1984c7f1c118" start="#meta-1_lebzykl-1_ereignis-5" refersTo="geltungszeit"></akn:timeInterval>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron&#x2D;Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1" GUID="4d3855be-763f-4ebf-8ca6-8399b5fb86b7" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>neufassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <meta:fna>611-1-1</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <meta:federfuehrung>
                  <meta:federfuehrend ab="1994-10-28" bis="unbestimmt">Bundesministerium für Finanzen</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
               <meta:subtyp>SN</meta:subtyp>
           </meta:legalDocML.de_metadaten_ds>
         </akn:proprietary>
      </akn:meta>
      <akn:preface eId="einleitung-1" GUID="803123ed-9705-4e0f-8a7c-a648084e1638">
         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="9610fccb-c35a-4756-81fb-0bb8fa111000">
            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a80af8c8-c844-4b75-824f-cdbf2f666d07">
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="d6f256c3-cb1f-4b5f-ab97-bb73a6a4ba23">Umwandlungssteuergesetz
               </akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="f25432ed-590e-4bfc-bd6f-2ded9c586dba">
                  <akn:inline refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert" eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" GUID="3d5ecf29-792c-4854-99e4-0cf18b5c0c9c">UmwStG 2006</akn:inline>
               </akn:shortTitle>
            </akn:p>
         </akn:longTitle>
      </akn:preface>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="7b7416d7-35a0-41cf-902f-583b9cf51a26">
               <!-- §1 -->
               <akn:article eId="hauptteil-1_para-1" GUID="80d1826b-4225-4019-80c7-a10654008b9c" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-1_bezeichnung-1" GUID="a7d5ccda-e00c-4aef-a99f-809b8eafdd71">
                           <akn:marker eId="hauptteil-1_para-1_bezeichnung-1_zaehlbez-1" GUID="e2a587cd-af6f-4ca7-846e-50a1ba0b6520" name="1"></akn:marker>§1</akn:num>
                  <akn:paragraph GUID="7e004c65-aaf4-4f88-94ce-9a7651de7593" eId="hauptteil-1_para-1_abs-1">
                     <akn:num eId="hauptteil-1_para-1_abs-1_bezeichnung-1" GUID="87a0e6d5-2b76-4034-aa20-ef2260f8353f">
                              <akn:marker eId="hauptteil-1_para-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="5820c1f0-ed8e-4a9e-848d-b33ae2e8ef54" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="155de595-1a58-45b5-95a7-7ce0ee6e103b" eId="hauptteil-1_para-1_abs-1_inhalt-1"><akn:p GUID="592e2d87-7843-4f41-b076-5031d254cff2" eId="hauptteil-1_para-1_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §2 -->
               <akn:article eId="hauptteil-1_para-2" GUID="26eb1ba5-2f9d-4efc-8522-061bbcc4a7b9" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-2_bezeichnung-1" GUID="928b837a-52a8-4568-839b-db2866abd202">
                           <akn:marker eId="hauptteil-1_para-2_bezeichnung-1_zaehlbez-1" GUID="39aa4be1-9228-452e-b9a0-9c13501a9b90" name="2"></akn:marker>§2</akn:num>
                  <akn:paragraph GUID="73863cd9-dd0f-472f-85fb-34fb660a2178" eId="hauptteil-1_para-2_abs-1">
                     <akn:num eId="hauptteil-1_para-2_abs-1_bezeichnung-1" GUID="cab55e58-3901-4e19-bb04-a0a1c85ecaad">
                              <akn:marker eId="hauptteil-1_para-2_abs-1_bezeichnung-1_zaehlbez-1" GUID="031c1d63-eb3c-4ce6-a370-1b83a01e1a39" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="41cf5098-928d-4027-b78d-59de7632d758" eId="hauptteil-1_para-2_abs-1_inhalt-1"><akn:p GUID="84c4afdf-9e55-43f3-970e-427b5b5f3bab" eId="hauptteil-1_para-2_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §3 -->
               <akn:article eId="hauptteil-1_para-3" GUID="1fbffcff-3845-4175-af88-e1cac9d8053d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-3_bezeichnung-1" GUID="eb1e352f-4e25-4eea-a1f4-d93920af7b8d">
                           <akn:marker eId="hauptteil-1_para-3_bezeichnung-1_zaehlbez-1" GUID="4fb283d9-f671-4272-86ca-e5e4cbbf0538" name="3"></akn:marker>§3</akn:num>
                  <akn:paragraph GUID="88a7bf19-3379-4bb5-883b-ff7f9873cd13" eId="hauptteil-1_para-3_abs-1">
                     <akn:num eId="hauptteil-1_para-3_abs-1_bezeichnung-1" GUID="68464716-39a9-44dc-bb44-8d2a5ed1916b">
                              <akn:marker eId="hauptteil-1_para-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="143b1044-ee76-42b9-9a86-6fb3ff1a7c2c" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="748cf054-b5c6-473f-8edd-e967d7a605be" eId="hauptteil-1_para-3_abs-1_inhalt-1"><akn:p GUID="530ab977-cf41-4f6d-8db8-b7a164e2595a" eId="hauptteil-1_para-3_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §4 -->
               <akn:article eId="hauptteil-1_para-4" GUID="5a8416ef-c656-4039-a0fb-a2463aa1e0d7" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-4_bezeichnung-1" GUID="8222bebf-7c0a-4a6c-9795-9705c816a2c0">
                           <akn:marker eId="hauptteil-1_para-4_bezeichnung-1_zaehlbez-1" GUID="8a0a9a47-744f-42b3-bc9e-2ce63ec5ff1d" name="4"></akn:marker>§4</akn:num>
                  <akn:paragraph GUID="45bcaf24-834f-4e5e-bbd1-9e8c127bf403" eId="hauptteil-1_para-4_abs-1">
                     <akn:num eId="hauptteil-1_para-4_abs-1_bezeichnung-1" GUID="d791a3d8-8770-4306-ab32-786391c32eb0">
                              <akn:marker eId="hauptteil-1_para-4_abs-1_bezeichnung-1_zaehlbez-1" GUID="d6db7c2e-d058-43cb-b0f4-00445997b3d6" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="8ccbd60b-4f1e-4aad-b712-cf81dc981e8b" eId="hauptteil-1_para-4_abs-1_inhalt-1"><akn:p GUID="08109b53-188a-484d-9aae-94e355cd5c96" eId="hauptteil-1_para-4_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §5 -->
               <akn:article eId="hauptteil-1_para-5" GUID="ec075ce0-a6a5-4075-9041-b0304da221b0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-5_bezeichnung-1" GUID="4bfef6b2-3527-4247-a8b2-dc1bc468e952">
                           <akn:marker eId="hauptteil-1_para-5_bezeichnung-1_zaehlbez-1" GUID="af885e36-4dc7-46de-bc06-68423507c9a6" name="5"></akn:marker>§5</akn:num>
                  <akn:paragraph GUID="5fdaec7c-753e-4218-8040-22fa6d9b594d" eId="hauptteil-1_para-5_abs-1">
                     <akn:num eId="hauptteil-1_para-5_abs-1_bezeichnung-1" GUID="95315d18-139e-436e-8822-ff5e81f16583">
                              <akn:marker eId="hauptteil-1_para-5_abs-1_bezeichnung-1_zaehlbez-1" GUID="603af9f3-25c9-4a5f-bc24-3c78353a34f0" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="7263cdbb-1e3d-4cd4-8491-c901ae605ee0" eId="hauptteil-1_para-5_abs-1_inhalt-1"><akn:p GUID="5740dcc2-4d32-4e55-ac8f-cc6e5886d0a2" eId="hauptteil-1_para-5_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §6 -->
               <akn:article eId="hauptteil-1_para-6" GUID="9d818ef1-7e1a-44b0-b584-0690c7eb636d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-6_bezeichnung-1" GUID="4a538475-efee-458c-9311-005f4dfb72ed">
                           <akn:marker eId="hauptteil-1_para-6_bezeichnung-1_zaehlbez-1" GUID="7456f473-2d28-4931-b850-b3c487d4f5ff" name="6"></akn:marker>§6</akn:num>
                  <akn:paragraph GUID="959ff2b2-0ae9-4687-b76c-f6b6a7020693" eId="hauptteil-1_para-6_abs-1">
                     <akn:num eId="hauptteil-1_para-6_abs-1_bezeichnung-1" GUID="3e39302a-d279-47e7-b2ba-3105b4618a43">
                              <akn:marker eId="hauptteil-1_para-6_abs-1_bezeichnung-1_zaehlbez-1" GUID="24dbaf9f-89f1-41fe-9813-a03c92eceaa7" name="1"></akn:marker>(1)</akn:num>
                              <akn:content GUID="ed798c34-6080-4ee1-b9e3-872936250312" eId="hauptteil-1_para-6_abs-1_inhalt-1"><akn:p GUID="cb6c9acd-327e-4eac-90ec-a1b58d58a969" eId="hauptteil-1_para-6_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                  </akn:paragraph>
               </akn:article>
               <!-- §7-->
               <akn:article eId="hauptteil-1_para-7" GUID="1ceb0d54-0dda-4661-906b-4e2e90b0cd09" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-7_bezeichnung-1" GUID="256fe6ee-be7d-4e06-af6d-f724771935fe">
                         <akn:marker eId="hauptteil-1_para-7_bezeichnung-1_zaehlbez-1" GUID="5f3c4413-476a-4661-9bd3-b7fbc49a01fd" name="7"></akn:marker>§7</akn:num>
                <akn:paragraph GUID="5a6bdfeb-f784-43ef-9eb9-ea4f4fd33405" eId="hauptteil-1_para-7_abs-1">
                   <akn:num eId="hauptteil-1_para-7_abs-1_bezeichnung-1" GUID="cc85ce13-9624-4abb-a77c-3f3433e8e347">
                            <akn:marker eId="hauptteil-1_para-7_abs-1_bezeichnung-1_zaehlbez-1" GUID="2aa1c4aa-722c-4cf4-9507-5e722e07a143" name="1"></akn:marker>(1)</akn:num>
                            <akn:content GUID="149587bf-3cee-471d-8c1f-74f55a352078" eId="hauptteil-1_para-7_abs-1_inhalt-1"><akn:p GUID="90b03eef-fed8-4017-8e8b-c550561cad01" eId="hauptteil-1_para-7_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
             </akn:article>
               <!-- §8-->
               <akn:article eId="hauptteil-1_para-8" GUID="64c63f88-1f83-4044-ae82-5fccbf0bb3bb" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-8_bezeichnung-1" GUID="d6cc2523-3235-4ee6-bb3a-cdc79ff360c7">
                         <akn:marker eId="hauptteil-1_para-8_bezeichnung-1_zaehlbez-1" GUID="514ba761-7761-4c21-9844-a06c89e0778b" name="8"></akn:marker>§8</akn:num>
                <akn:paragraph GUID="2a13a905-8e98-45f8-b5de-a60028163988" eId="hauptteil-1_para-8_abs-1">
                   <akn:num eId="hauptteil-1_para-8_abs-1_bezeichnung-1" GUID="c4fa4971-7380-4b42-a50c-ecaa77faf949">
                            <akn:marker eId="hauptteil-1_para-8_abs-1_bezeichnung-1_zaehlbez-1" GUID="1b1c875e-c5a5-4422-812c-6efc16a0c837" name="1"></akn:marker>(1)</akn:num>
                            <akn:content GUID="5d7d02d5-e68b-4c7b-b629-29eb7fd372b1" eId="hauptteil-1_para-8_abs-1_inhalt-1"><akn:p GUID="a9dc09f9-b174-4e2d-83bc-3b99402da470" eId="hauptteil-1_para-8_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
             </akn:article>
               <!-- §9-->
               <akn:article eId="hauptteil-1_para-9" GUID="50052489-c09c-43e4-a6bf-875714309d37" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-9_bezeichnung-1" GUID="3c7ad660-ac0f-404a-88b1-2c48cc167018">
                         <akn:marker eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1" GUID="6844ba8d-9c9f-42fe-8520-b89f1314ad9e" name="9"></akn:marker>§9</akn:num>
                <akn:paragraph GUID="3275e8b5-c1c5-4c8c-a51a-cd68700f131a" eId="hauptteil-1_para-9_abs-1">
                   <akn:num eId="hauptteil-1_para-9_abs-1_bezeichnung-1" GUID="f62c19e9-8257-4fb6-a597-92293bb0790a">
                            <akn:marker eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1" GUID="d3364ba1-546d-4ad4-b940-50c704d21e7c" name="1"></akn:marker>(1)</akn:num>
                            <akn:content GUID="8ab7b65f-52cf-44ea-bff0-64ef54cd65fb" eId="hauptteil-1_para-9_abs-1_inhalt-1"><akn:p GUID="80c3148a-c174-4f8b-afce-3c2f6a9fcb84" eId="hauptteil-1_para-9_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
             </akn:article>
               <!-- §10-->
               <akn:article eId="hauptteil-1_para-10" GUID="6c753154-bb8f-484c-afe4-0987272b0983" period="#geltungszeitgr-3">
                <akn:num eId="hauptteil-1_para-10_bezeichnung-1" GUID="8a6c51e3-fed3-4bf7-b76b-8c52156f9e5c">
                         <akn:marker eId="hauptteil-1_para-10_bezeichnung-1_zaehlbez-1" GUID="9e9e981d-9de9-4cfb-af9e-b57695c4cbca" name="10"></akn:marker>§10</akn:num>
                <akn:paragraph GUID="3f29a241-3bc5-4f75-80c1-b73be9c1e737" eId="hauptteil-1_para-10_abs-1">
                   <akn:num eId="hauptteil-1_para-10_abs-1_bezeichnung-1" GUID="ebd5f8ce-8a47-4980-80df-6075047e9c85">
                            <akn:marker eId="hauptteil-1_para-10_abs-1_bezeichnung-1_zaehlbez-1" GUID="d335d6e2-2e33-41c8-b7d7-3a97d5444314" name="1"></akn:marker>(1)</akn:num>
                            <akn:content GUID="1b2ff163-ca8e-4824-acc6-3ae8802b5042" eId="hauptteil-1_para-10_abs-1_inhalt-1"><akn:p GUID="47c5c44a-fd23-44a5-93e6-d3b80e5b03a5" eId="hauptteil-1_para-10_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
             </akn:article>
            <!-- §11-->
            <akn:article period="#geltungszeitgr-3" GUID="c2b7f8b5-46b9-48d3-9d8c-f405f2b463a8" eId="hauptteil-1_para-11">
               <akn:num GUID="64cc0a8d-356e-40f8-a6aa-abe3758bd432" eId="hauptteil-1_para-11_bezeichnung-1">
                  <akn:marker name="11" GUID="a52a67b8-153a-4bd2-af52-932e5052743e" eId="hauptteil-1_para-11_bezeichnung-1_zaehlbez-1"></akn:marker>§11
               </akn:num>
               <akn:paragraph GUID="01de6ae0-9503-4b63-9a97-58112689c6af" eId="hauptteil-1_para-11_abs-1">
                  <akn:num GUID="d343196b-96b2-42b5-9d4d-14a7222a7a9b" eId="hauptteil-1_para-11_abs-1_bezeichnung-1">
                     <akn:marker name="1" GUID="147eb576-c056-42c4-8f9f-124c55f912eb" eId="hauptteil-1_para-11_abs-1_bezeichnung-1_zaehlbez-1"></akn:marker>(1)
                  </akn:num>
                  <akn:content GUID="fa13f7a9-1dd1-4cdd-8b75-0fac8d2b21c4" eId="hauptteil-1_para-11_abs-1_inhalt-1">
                     <akn:p GUID="f1991b0b-fb84-45e8-98b7-63a8a4304ece" eId="hauptteil-1_para-11_abs-1_inhalt-1_text-1"></akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
               <!-- §12-->
               <akn:article eId="hauptteil-1_para-12" GUID="170c345b-dd19-4baf-8a5e-9244d025938a" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-12_bezeichnung-1" GUID="4542cf12-8686-4160-917c-d5db81e78cf2">
                     <akn:marker eId="hauptteil-1_para-12_bezeichnung-1_zaehlbez-1" GUID="6b3e057a-0a92-4b64-abe9-a8e2b38e1158" name="12"></akn:marker>§12</akn:num>
                  <akn:paragraph GUID="f14b9add-3dc6-4f6e-a4e2-2f321b9f5dc6" eId="hauptteil-1_para-12_abs-1">
                     <akn:num eId="hauptteil-1_para-12_abs-1_bezeichnung-1" GUID="9a666831-6b7e-42fe-86b2-c459ac6b5074">
                        <akn:marker eId="hauptteil-1_para-12_abs-1_bezeichnung-1_zaehlbez-1" GUID="76cb03da-02ea-4b8b-9290-94cc996b4057" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="aed8b9e3-3557-46bf-8972-9e1f0308b6c8" eId="hauptteil-1_para-12_abs-1_inhalt-1"><akn:p GUID="564042f6-0177-4efd-8157-4f0a6eb9dec2" eId="hauptteil-1_para-12_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §13-->
               <akn:article eId="hauptteil-1_para-13" GUID="705a74fe-0a6b-4b6d-a6f2-5f5cf11ef46e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-13_bezeichnung-1" GUID="35a91591-8b58-47be-8e39-8b4df23eece5">
                     <akn:marker eId="hauptteil-1_para-13_bezeichnung-1_zaehlbez-1" GUID="df6f2f3a-54bf-414b-a78d-f39dccce241e" name="13"></akn:marker>§13</akn:num>
                  <akn:paragraph GUID="0fd92ffa-88be-4e0d-83a4-15616e79b778" eId="hauptteil-1_para-13_abs-1">
                     <akn:num eId="hauptteil-1_para-13_abs-1_bezeichnung-1" GUID="f4b56718-f4d3-413a-aa99-d9280c8ead98">
                        <akn:marker eId="hauptteil-1_para-13_abs-1_bezeichnung-1_zaehlbez-1" GUID="7808cf99-9318-47f6-bb02-eb228c4439ae" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="16e5195d-e15a-4b7c-b4d5-b79c3f8a5798" eId="hauptteil-1_para-13_abs-1_inhalt-1"><akn:p GUID="a3d8cf95-fba1-4a77-a4dc-f5530cacd554" eId="hauptteil-1_para-13_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §14-->
               <akn:article eId="hauptteil-1_para-14" GUID="7dd53189-857c-42c7-ba02-41c3368b2937" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-14_bezeichnung-1" GUID="979f9d20-5f33-4905-9e64-4d1da92e6bdf">
                     <akn:marker eId="hauptteil-1_para-14_bezeichnung-1_zaehlbez-1" GUID="ba42342c-a9ae-4d2d-a555-a3f54e6a0ad1" name="14"></akn:marker>§14</akn:num>
                  <akn:paragraph GUID="0012dc4e-1463-40d8-a479-b84252bd5243" eId="hauptteil-1_para-14_abs-1">
                     <akn:num eId="hauptteil-1_para-14_abs-1_bezeichnung-1" GUID="79629d3f-9c50-495c-bd5b-30e65724dad9">
                        <akn:marker eId="hauptteil-1_para-14_abs-1_bezeichnung-1_zaehlbez-1" GUID="9ec1af32-b50c-427a-8339-ccf2f4e3fed7" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="fb70326c-4fab-4610-9ff5-8aab75afbe98" eId="hauptteil-1_para-14_abs-1_inhalt-1"><akn:p GUID="e08f2997-6017-47fd-8aa6-e2f13c9d3afa" eId="hauptteil-1_para-14_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
            <!--§15-->
            <akn:article eId="hauptteil-1_para-15" GUID="a726a175-3a70-40e7-852f-9d08d1a26a13" period="#geltungszeitgr-3">
               <akn:num eId="hauptteil-1_para-15_bezeichnung-1" GUID="0298199b-a8d3-4f7b-b5e5-16b4dc2501d1">
                        <akn:marker eId="hauptteil-1_para-15_bezeichnung-1_zaehlbez-1" GUID="500625c3-e8c9-4226-99b9-8b175c0d7635" name="15"></akn:marker>§15</akn:num>
                <akn:heading GUID="6b993dcf-84de-48eb-ae6e-035fb985d3d4" eId="hauptteil-1_para-15_überschrift-1">15 Aufspaltung, Abspaltung und Teilübertragung auf andere Körperschaften</akn:heading>
               <!--Absatz 1-->
               <akn:paragraph GUID="eb932754-5119-4fc1-a167-4dfc5381d2ca" eId="hauptteil-1_para-15_abs-1">
                  <akn:num eId="hauptteil-1_para-15_abs-1_bezeichnung-1" GUID="8280d4ef-4a96-44b7-81b9-f30f1006a2fe">
                           <akn:marker eId="hauptteil-1_para-15_abs-1_bezeichnung-1_zaehlbez-1" GUID="0115df75-44e1-46e5-ac41-46c03643a0a6" name="1"></akn:marker>(1)</akn:num>
                           <akn:content GUID="a1259d3c-069c-4379-a1bb-8d4389940fc8" eId="hauptteil-1_para-15_abs-1_inhalt-1">
                              <akn:p GUID="51598f1a-f4e7-4882-83f8-7b9526abe44b" eId="hauptteil-1_para-15_abs-1_inhalt-1_text-1">
                                 Geht Vermögen einer Körperschaft durch Aufspaltung oder Abspaltung oder durch Teilübertragung auf andere Körperschaften über, gelten die §§ 11 bis 13 vorbehaltlich des Satzes 2 und des § 16 entsprechend. § 11 Abs. 2 und § 13 Abs. 2 sind nur anzuwenden, wenn auf die Übernehmerinnen ein Teilbetrieb übertragen wird und im Falle der Abspaltung oder Teilübertragung bei der übertragenden Körperschaft ein Teilbetrieb verbleibt. Als Teilbetrieb gilt auch ein Mitunternehmeranteil oder die Beteiligung an einer Kapitalgesellschaft, die das gesamte Nennkapital der Gesellschaft umfasst.
                              </akn:p>
                           </akn:content>
               </akn:paragraph>
               <!--Absatz 2-->
               <akn:paragraph GUID="cd17b333-e7d5-4f0f-9406-5d50eb12f783" eId="hauptteil-1_para-15_abs-2">
                  <akn:num eId="hauptteil-1_para-15_abs-2_bezeichnung-1" GUID="48d155ca-ec9e-4136-a14a-96d4f7e7a017">
                           <akn:marker eId="hauptteil-1_para-15_abs-2_bezeichnung-1_zaehlbez-1" GUID="68f1d541-9740-43e2-950d-4c8bfb38a3ab" name="2"></akn:marker>(2)</akn:num>
                           <akn:content GUID="0d40dad9-3bef-4dca-acc9-be0d29f94d57" eId="hauptteil-1_para-15_abs-2_inhalt-1">
                              <akn:p GUID="5293ade2-7f6a-473d-a681-ccd5d9e4a2cd" eId="hauptteil-1_para-15_abs-2_inhalt-1_text-1">
                                 § 11 Abs. 2 ist auf Mitunternehmeranteile und Beteiligungen im Sinne des Absatzes 1 nicht anzuwenden, wenn sie innerhalb eines Zeitraums von drei Jahren vor dem steuerlichen Übertragungsstichtag durch Übertragung von Wirtschaftsgütern, die kein Teilbetrieb sind, erworben oder aufgestockt worden sind. § 11 Abs. 2 ist ebenfalls nicht anzuwenden, wenn durch die Spaltung die Veräußerung an außenstehende Personen vollzogen oder vorbereitet wird. Als außenstehende Personen gelten Personen, die nicht ununterbrochen fünf Jahre vor der Spaltung an der übertragenden Körperschaft beteiligt waren. In den Fällen der Vorbereitung einer Veräußerung kommt Satz 2 nur zur Anwendung, wenn innerhalb von fünf Jahren nach dem steuerlichen Übertragungsstichtag eine Veräußerung mindestens eines Anteils an einer an der Spaltung beteiligten Körperschaft an außenstehende Personen erfolgt; die Veräußerung des Anteils gilt als rückwirkendes Ereignis im Sinne von § 175 Absatz 1 Satz 1 Nummer 2 der Abgabenordnung. Werden innerhalb von fünf Jahren nach dem steuerlichen Übertragungsstichtag Anteile an einer an der Spaltung beteiligten Körperschaft, die mehr als 20 Prozent des Wertes der Anteile an der übertragenden Körperschaft am steuerlichen Übertragungsstichtag ausmachen, an außenstehende Personen veräußert, ist unwiderlegbar zu vermuten, dass durch die Spaltung eine Veräußerung im Sinne des Satzes 2 vorbereitet wurde. Sind an der übertragenden Körperschaft außenstehende Personen beteiligt, gilt die Spaltung nur dann als Veräußerung an außenstehende Personen im Sinne des Satzes 2, wenn die Spaltung zu einer Wertverschiebung zugunsten dieser Personen führt. Verbundene Unternehmen im Sinne des § 271 Absatz 2 des Handelsgesetzbuchs gelten nicht als außenstehende Personen im Sinne dieses Absatzes; als Veräußerung eines Anteils an einer an der Spaltung beteiligten Körperschaft im Sinne des Satzes 4 gilt auch die mittelbare Veräußerung dieses Anteils durch ein verbundenes Unternehmen. Bei der Trennung von Gesellschafterstämmen setzt die Anwendung des § 11 Abs. 2 außerdem voraus, dass die Beteiligungen an der übertragenden Körperschaft mindestens fünf Jahre vor dem steuerlichen Übertragungsstichtag bestanden haben.
                              </akn:p>
                           </akn:content>
               </akn:paragraph>
               <!--Absatz 3-->
               <akn:paragraph GUID="ae4eb8b9-a588-4b2b-af93-1923a0a2decb" eId="hauptteil-1_para-15_abs-3">
                  <akn:num GUID="a85d5c2a-1a11-4608-b9bb-b413294d3ec1" eId="hauptteil-1_para-15_abs-3_bezeichnung-1">
                     <akn:marker name="3" GUID="fc14ac44-53da-467c-b31d-cce168e125c3" eId="hauptteil-1_para-15_abs-3_bezeichnung-1_zaehlbez-1"></akn:marker>(3)
                  </akn:num>
                  <akn:content GUID="a139b3d0-0422-49b4-83a4-d97f24a1554f" eId="hauptteil-1_para-15_abs-3_inhalt-1">
                     <akn:p GUID="41a0a463-54d8-4bcb-9dd9-d10b6561560d" eId="hauptteil-1_para-15_abs-3_inhalt-1_text-1">
                        Bei einer Abspaltung mindern sich verrechenbare Verluste, verbleibende Verlustvorträge, nicht ausgeglichene negative Einkünfte, ein Zinsvortrag nach § 4h Absatz 1 Satz 5 des Einkommensteuergesetzes und ein EBITDA-Vortrag nach § 4h Absatz 1 Satz 3 des Einkommensteuergesetzes der übertragenden Körperschaft in dem Verhältnis, in dem bei Zugrundelegung des gemeinen Werts das Vermögen auf eine andere Körperschaft übergeht.
                     </akn:p>
                  </akn:content>
               </akn:paragraph>
            </akn:article>
               <!-- §16-->
               <akn:article eId="hauptteil-1_para-16" GUID="cd51af28-5db1-4d4c-ae23-ac5b3742613e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-16_bezeichnung-1" GUID="80d8f934-8f58-4ae8-a8f9-843f66390fec">
                     <akn:marker eId="hauptteil-1_para-16_bezeichnung-1_zaehlbez-1" GUID="b6b32743-dc1a-4d3a-8bd2-6f307394a485" name="16"></akn:marker>§16</akn:num>
                  <akn:paragraph GUID="66eb1100-279b-48b8-8419-425192bc9c53" eId="hauptteil-1_para-16_abs-1">
                     <akn:num eId="hauptteil-1_para-16_abs-1_bezeichnung-1" GUID="e4ac9fad-2131-48a6-9831-794023c22f31">
                        <akn:marker eId="hauptteil-1_para-16_abs-1_bezeichnung-1_zaehlbez-1" GUID="12595933-9cfb-4e0b-8299-1d0960e55a16" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="2b4fdbe0-f388-4337-998e-22adeaace890" eId="hauptteil-1_para-16_abs-1_inhalt-1"><akn:p GUID="0fa7e6e5-9645-4fe3-bc5d-030e0c2ce9c9" eId="hauptteil-1_para-16_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §17-->
               <akn:article eId="hauptteil-1_para-17" GUID="56bc720f-e450-4288-b024-0c7a6d0a86a6" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-17_bezeichnung-1" GUID="106acd9d-deb4-45c1-99be-8a81b07dd3de">
                     <akn:marker eId="hauptteil-1_para-17_bezeichnung-1_zaehlbez-1" GUID="e2aa6a48-1145-4668-a6a9-ee213372b6b2" name="17"></akn:marker>§17</akn:num>
                  <akn:paragraph GUID="b1fd674e-f956-4ab5-800e-fbd9fb130e39" eId="hauptteil-1_para-17_abs-1">
                     <akn:num eId="hauptteil-1_para-17_abs-1_bezeichnung-1" GUID="a59bd695-5a5c-40a4-a3a4-dd8de608250f">
                        <akn:marker eId="hauptteil-1_para-17_abs-1_bezeichnung-1_zaehlbez-1" GUID="7971a6b1-7119-40c1-b690-b8aa0fd70565" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="fc1bc033-49c3-4674-b8f5-923bba3620e8" eId="hauptteil-1_para-17_abs-1_inhalt-1"><akn:p GUID="3f23ef34-e2be-4f48-945b-95031cb81c6d" eId="hauptteil-1_para-17_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §18-->
               <akn:article eId="hauptteil-1_para-18" GUID="4b386140-6c09-47d2-8c2b-c9d4061b1d16" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-18_bezeichnung-1" GUID="12f88467-2c2c-49bd-959d-e264b0b90374">
                     <akn:marker eId="hauptteil-1_para-18_bezeichnung-1_zaehlbez-1" GUID="c173ef78-10d0-4c3c-9332-df3cecc655c0" name="18"></akn:marker>§18</akn:num>
                  <akn:paragraph GUID="69ad2f8a-1074-4f5b-9429-1a34220e67f0" eId="hauptteil-1_para-18_abs-1">
                     <akn:num eId="hauptteil-1_para-18_abs-1_bezeichnung-1" GUID="e8498a3b-261f-4e60-ae89-96020d20f710">
                        <akn:marker eId="hauptteil-1_para-18_abs-1_bezeichnung-1_zaehlbez-1" GUID="9709cc18-71b2-41d2-85ec-afaabc2a7781" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="90c86a81-0448-4fac-855b-9877b7873345" eId="hauptteil-1_para-18_abs-1_inhalt-1"><akn:p GUID="9ec30711-98f1-43a9-94b0-1f6d3e1a6eb6" eId="hauptteil-1_para-18_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §19-->
               <akn:article eId="hauptteil-1_para-19" GUID="954deb64-7f39-49a4-9646-372d3f1a2d0d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-19_bezeichnung-1" GUID="7716eb01-457e-4237-a8c4-efd97a7adb42">
                     <akn:marker eId="hauptteil-1_para-19_bezeichnung-1_zaehlbez-1" GUID="52ac1e49-fd0f-440d-939c-83407a0c4ddf" name="19"></akn:marker>§19</akn:num>
                  <akn:paragraph GUID="f88df7aa-09b8-4ac4-a60f-4399ac8c0ea7" eId="hauptteil-1_para-19_abs-1">
                     <akn:num eId="hauptteil-1_para-19_abs-1_bezeichnung-1" GUID="8e9c9fff-fd18-44a5-b2a7-24c7b4aeabef">
                        <akn:marker eId="hauptteil-1_para-19_abs-1_bezeichnung-1_zaehlbez-1" GUID="6284a6c3-6e28-4607-b7ec-a557cbbd2623" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="1b39fc5f-95d6-44a7-9fc5-7fa7dcc35401" eId="hauptteil-1_para-19_abs-1_inhalt-1"><akn:p GUID="3002b828-7e20-4d0a-b6c9-34446dea8150" eId="hauptteil-1_para-19_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §20-->
               <akn:article eId="hauptteil-1_para-20" GUID="ae509f70-aa93-4169-8e31-277c7a0cd762" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="d98ea318-c296-473e-be51-e08903bdd26c">
                     <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" GUID="bfa7e7f1-81c6-4419-a9aa-21e5dddc24e4" name="20"></akn:marker>§20</akn:num>
                  <akn:paragraph GUID="0f540208-d535-4df1-97fd-2ebcec47c073" eId="hauptteil-1_para-20_abs-1">
                     <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="7ee7f421-8684-4796-b7fd-b3758ca2598c">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" GUID="21b1dc7c-ed36-4d5c-a10f-666ecdd80c5f" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="b6634e8f-c8f1-4d7f-9c33-e70692be3c9c" eId="hauptteil-1_para-20_abs-1_inhalt-1"><akn:p GUID="16c99b24-06be-4af4-97b8-a9a6c773c22f" eId="hauptteil-1_para-20_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §21-->
               <akn:article eId="hauptteil-1_para-21" GUID="424939b2-67e3-4185-b146-42d71c631fe5" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-21_bezeichnung-1" GUID="f3bf0469-d948-4d12-8b9a-c768c13ade2d">
                     <akn:marker eId="hauptteil-1_para-21_bezeichnung-1_zaehlbez-1" GUID="12f2206e-a73f-43e4-b060-fbe2e1297ce4" name="21"></akn:marker>§21</akn:num>
                  <akn:paragraph GUID="feea1193-b843-45ef-82d9-9e20f1453bc1" eId="hauptteil-1_para-21_abs-1">
                     <akn:num eId="hauptteil-1_para-21_abs-1_bezeichnung-1" GUID="5c2d60ba-ea74-44c3-9518-aa26178b94bf">
                        <akn:marker eId="hauptteil-1_para-21_abs-1_bezeichnung-1_zaehlbez-1" GUID="db08acbc-72ea-4c43-a690-07a5d4b778b0" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="1f916138-af53-4f17-94ff-cb4a9d63f4fb" eId="hauptteil-1_para-21_abs-1_inhalt-1"><akn:p GUID="7bbc1faa-6c4f-487b-9ae7-6b1aa0f4b35f" eId="hauptteil-1_para-21_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §22-->
               <akn:article eId="hauptteil-1_para-22" GUID="a4c0c103-ed32-4ed6-81b6-b41957c782d0" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-22_bezeichnung-1" GUID="2fbcd6d1-8720-4d57-ba14-28985f926966">
                     <akn:marker eId="hauptteil-1_para-22_bezeichnung-1_zaehlbez-1" GUID="acdf3def-f910-44e0-97a5-ddf70aa63f79" name="22"></akn:marker>§22</akn:num>
                  <akn:paragraph GUID="c61ea3f8-baab-4f81-a0c2-adb2381f5e70" eId="hauptteil-1_para-22_abs-1">
                     <akn:num eId="hauptteil-1_para-22_abs-1_bezeichnung-1" GUID="2cdca59c-e3d1-4fde-95ef-ad0521eb8c2c">
                        <akn:marker eId="hauptteil-1_para-22_abs-1_bezeichnung-1_zaehlbez-1" GUID="b78ac3e1-2466-46fe-a6fd-e90762ad575d" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="3eb5536e-29cb-4533-89e5-f0b309c73397" eId="hauptteil-1_para-22_abs-1_inhalt-1"><akn:p GUID="fcda42f9-8d60-4f85-b18d-6b1e614e8528" eId="hauptteil-1_para-22_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §23-->
               <akn:article eId="hauptteil-1_para-23" GUID="e405acf2-b145-4ec6-b368-28586c3c2c5e" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-23_bezeichnung-1" GUID="1ff4d744-7446-49dc-a4ba-8cb48e80127c">
                     <akn:marker eId="hauptteil-1_para-23_bezeichnung-1_zaehlbez-1" GUID="ce3d18cb-c329-4c3d-9a6a-83c10ac8e6f5" name="23"></akn:marker>§23</akn:num>
                  <akn:paragraph GUID="e59512ac-e2c4-4d61-9fcd-49ccc58e7751" eId="hauptteil-1_para-23_abs-1">
                     <akn:num eId="hauptteil-1_para-23_abs-1_bezeichnung-1" GUID="697904a4-a568-4780-8f9a-abcfcd31e51b">
                        <akn:marker eId="hauptteil-1_para-23_abs-1_bezeichnung-1_zaehlbez-1" GUID="eb61c7e7-f341-49a7-be79-f0204fbb2c89" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="81c478aa-4ef7-408a-be13-09015f11209d" eId="hauptteil-1_para-23_abs-1_inhalt-1"><akn:p GUID="9c3aa6cf-be45-424a-bca9-3040c03dd973" eId="hauptteil-1_para-23_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §24-->
               <akn:article eId="hauptteil-1_para-24" GUID="53edf023-d23e-4c3f-aadc-d317342e0295" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-24_bezeichnung-1" GUID="b911ed14-7e1b-4c92-bbab-95a0d63f13bc">
                     <akn:marker eId="hauptteil-1_para-24_bezeichnung-1_zaehlbez-1" GUID="d5e49400-64b9-485c-b6ae-79acc786bfe1" name="24"></akn:marker>§24</akn:num>
                  <akn:paragraph GUID="14ef0b6d-a177-4cee-bdc5-8df5e82c5599" eId="hauptteil-1_para-24_abs-1">
                     <akn:num eId="hauptteil-1_para-24_abs-1_bezeichnung-1" GUID="cd05cc93-3e85-42ea-b8d6-143d8de153d6">
                        <akn:marker eId="hauptteil-1_para-24_abs-1_bezeichnung-1_zaehlbez-1" GUID="744a977d-2979-4d89-a787-c3c7129a3399" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="51db4766-dfd1-41b5-aa14-963e237ec73a" eId="hauptteil-1_para-24_abs-1_inhalt-1"><akn:p GUID="df45acbd-b294-455e-8ac4-0dd688221178" eId="hauptteil-1_para-24_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §25-->
               <akn:article eId="hauptteil-1_para-25" GUID="fbab827d-776f-4959-b700-b99558a3219d" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-25_bezeichnung-1" GUID="98d1047f-8d44-4c90-81c6-3f65fd8e2487">
                     <akn:marker eId="hauptteil-1_para-25_bezeichnung-1_zaehlbez-1" GUID="4c41e917-a382-43ba-955b-c812b0dfff31" name="25"></akn:marker>§25</akn:num>
                  <akn:paragraph GUID="cfcc9b5f-e67f-4409-93b6-5473fb9c015f" eId="hauptteil-1_para-25_abs-1">
                     <akn:num eId="hauptteil-1_para-25_abs-1_bezeichnung-1" GUID="c6a22e73-283a-4a3a-b4c6-6cdfe9f43a9d">
                        <akn:marker eId="hauptteil-1_para-25_abs-1_bezeichnung-1_zaehlbez-1" GUID="c4c2bdb1-67c4-4e11-8ec4-6437a5f4dfea" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="068a1f6e-c65c-4146-a1d8-09150bd9b44b" eId="hauptteil-1_para-25_abs-1_inhalt-1"><akn:p GUID="6d28c311-fb08-465e-81bd-626ac712e520" eId="hauptteil-1_para-25_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §26-->
               <akn:article eId="hauptteil-1_para-26" GUID="6c7b37c6-7dc8-4a83-bcf0-acf67b69f377" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-26_bezeichnung-1" GUID="4e9e2071-ab50-40f4-8bdf-ba8c88a19ddc">
                     <akn:marker eId="hauptteil-1_para-26_bezeichnung-1_zaehlbez-1" GUID="66adfd43-ecc7-4740-a6f5-98d812130a8b" name="26"></akn:marker>§26</akn:num>
                  <akn:paragraph GUID="0dbd7eb7-7bf9-43d5-b731-0be947f85105" eId="hauptteil-1_para-26_abs-1">
                     <akn:num eId="hauptteil-1_para-26_abs-1_bezeichnung-1" GUID="a9de9165-dbe0-410d-aca8-f5df6716e780">
                        <akn:marker eId="hauptteil-1_para-26_abs-1_bezeichnung-1_zaehlbez-1" GUID="bc76c077-e269-4521-b2d6-25ccb3a31d83" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="ceb3a98e-abdb-44af-ab4b-42a446d0edc2" eId="hauptteil-1_para-26_abs-1_inhalt-1"><akn:p GUID="4f717e61-7a14-4e83-8c6d-45aba2f14ed9" eId="hauptteil-1_para-26_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>
               <!-- §27-->
               <akn:article eId="hauptteil-1_para-27" GUID="06bef0e9-0e24-4e6e-ae67-0f1427d2596f" period="#geltungszeitgr-3">
                  <akn:num eId="hauptteil-1_para-27_bezeichnung-1" GUID="280b8ba7-c0c4-4642-a41f-80d74195a764">
                     <akn:marker eId="hauptteil-1_para-27_bezeichnung-1_zaehlbez-1" GUID="696158fb-0e1c-45f7-b6f3-947fea7dc3fa" name="27"></akn:marker>§27</akn:num>
                  <akn:paragraph GUID="1a841b5c-8791-4fa0-9710-b7ab07b906c4" eId="hauptteil-1_para-27_abs-1">
                     <akn:num eId="hauptteil-1_para-27_abs-1_bezeichnung-1" GUID="07e714b7-1489-42e8-b5c1-e9af4a251c30">
                        <akn:marker eId="hauptteil-1_para-27_abs-1_bezeichnung-1_zaehlbez-1" GUID="83732175-acbb-4d9f-b03b-2442db59c96c" name="1"></akn:marker>(1)</akn:num>
                     <akn:content GUID="6dc35edf-93af-4fc3-9dd1-df7029f789bd" eId="hauptteil-1_para-27_abs-1_inhalt-1"><akn:p GUID="753b51f7-a2b0-4e55-aff2-4b1cf5cce4e1" eId="hauptteil-1_para-27_abs-1_inhalt-1_text-1"></akn:p></akn:content>
                </akn:paragraph>
               </akn:article>

         </akn:body>
   </akn:act>
</akn:akomaNtoso>');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('17088800-fe06-4f8e-94c3-d8254b37476c', 'eli/bund/bgbl-1/2024/108/2024-03-27/2/deu/regelungstext-1', NULL);
