export const amendingLawXml = `<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
\t##################################################################################
\tProjekt E-Gesetzgebung
\tNicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

\t2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
\tReferat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
\tVerwaltungsarbeit

\tVeröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
\t##################################################################################
-->
<?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                       http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    <akn:act name="regelungstext">
        <!-- Metadaten -->
        <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
            <akn:identification eId="meta-1_ident-1" GUID="be8ecb75-0f1a-4209-b3a4-17d55bdffb47" source="attributsemantik-noch-undefiniert">
                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="d4f77434-7c1f-4496-917a-262a82a7070c">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="ad47b5be-0012-447a-90db-71438b38650e" value="eli/bund/bgbl-1/2017/s419/regelungstext-1" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a739e012-fe1d-4411-91b8-58e0de76fc28" value="eli/bund/bgbl-1/2017/s419" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="42ae272d-4b4a-4d25-9965-79e76c741b5b" name="übergreifende-id" value="c53036e4-14ac-420f-b01a-bae05a0a9756" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="525ff48c-a66e-45f6-b036-884935f7ba7d" date="2017-03-15" name="verkuendungsfassung" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="27fa3047-26e1-4c59-8701-76dd34043d71" href="recht.bund.de/institution/bundesregierung" />
                    <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="fa3d22d4-4f01-4486-9d45-c1edcf50729e" value="de" />
                    <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="565c2f06-c2c9-4a27-aeb3-ca34199ce08c" value="s419" />
                    <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="7219aecc-e1eb-49a1-abf5-bba8a8be721c" value="bgbl-1" />
                    <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="c5bc9d46-575f-4808-90e8-a354a227d701" value="regelungstext-1" />
                </akn:FRBRWork>
                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4c69a6d2-8988-4581-bfa9-df9e8e24f321">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="5a2c4542-56cc-4c70-8b80-e2041b5b75e1" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="9063f5e7-c3c5-4ab4-8e15-459b11d7a9f2" href="recht.bund.de/institution/bundesregierung" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="1e8f33a8-d124-48c3-a864-7968701816ee" date="2017-03-15" name="verkuendung" />
                    <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="9c61581b-ce24-4589-8db8-533262149b90" language="deu" />
                    <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="de475d52-7263-4c05-8014-e92a7785b784" value="1" />
                </akn:FRBRExpression>
                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml" />
                    <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="6e12c94c-f206-4144-bedf-dcab30867f4c" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="791a8124-d12e-45e1-9c80-5f0438e4d046" date="2022-08-23" name="generierung" />
                    <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26" href="recht.bund.de" />
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d" value="xml" />
                </akn:FRBRManifestation>
            </akn:identification>
            <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2017-03-15" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2017-03-16" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
            </akn:lifecycle>
            <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                    <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                        <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" />
                        <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-0.xml" />
                        <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1" />
                    </akn:textualMod>
                </akn:activeModifications>
            </akn:analysis>
            <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                    <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                </akn:temporalGroup>
            </akn:temporalData>
            <!-- Diese Metadaten sind die Konstituenten für die Schematron-Validierung. -->
            <akn:proprietary eId="meta-1_proprietary-1" GUID="fe419055-3201-41b1-b096-402eabcbe6a1" source="attributsemantik-noch-undefiniert">
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                    <meta:typ>gesetz</meta:typ>
                    <meta:form>mantelform</meta:form>
                    <meta:fassung>verkuendungsfassung</meta:fassung>
                    <meta:art>regelungstext</meta:art>
                    <meta:initiant>bundesregierung</meta:initiant>
                    <meta:bearbeitendeInstitution>bundesregierung</meta:bearbeitendeInstitution>
                    <meta:fna>nicht-vorhanden</meta:fna>
                    <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
                    <meta:gesta>nicht-vorhanden</meta:gesta>
                    <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
                    <meta:federfuehrung>
                        <meta:federfuehrend ab="2002-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                        <meta:federfuehrend ab="2002-10-01" bis="2002-12-01">Bundesministerium der Justiz</meta:federfuehrend>
                    </meta:federfuehrung>
                </meta:legalDocML.de_metadaten>
            </akn:proprietary>
        </akn:meta>

        <!-- Dokumentenkopf -->
        <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
            <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                    <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                    <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                        Innern</akn:docProponent>
                    <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                </akn:p>
            </akn:longTitle>
            <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
            </akn:block>
        </akn:preface>

        <!-- Eingangsformel -->
        <akn:preamble eId="preambel-1" GUID="7eae9fd3-d601-40ba-a4a1-f9416d89e586">
            <akn:formula eId="preambel-1_formel-1" GUID="a7bbd756-c50a-4944-8f64-49134e1166cc" refersTo="eingangsformel" name="attributsemantik-noch-undefiniert">
                <akn:p eId="preambel-1_formel-1_text-1" GUID="7e0cf2ac-b45a-40b8-8369-c5013ada9f48"> Der <akn:organization eId="preambel-1_formel-1_text-1_org-1" GUID="05004de4-44d4-4e59-a1ef-5912003a6f36" refersTo="attributsemantik-noch-undefiniert" title="Bundestag">Bundestag</akn:organization> hat
                    das folgende Gesetz beschlossen:</akn:p>
            </akn:formula>
        </akn:preamble>

        <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
            <!-- Artikel 1 : Hauptänderung -->
            <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                    <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                <!-- Absatz (1) -->
                <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                    <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                        <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                    </akn:num>
                    <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                        <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                            <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                                                                                                                                 href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                        </akn:intro>
                        <!-- Nummer 1 wurde entfernt -->
                        <!-- Nummer 2 -->
                        <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2" GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                            <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1" GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                                <akn:marker eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="5d7d54f0-8a4e-4d8f-b5d0-93d0ca393e82" name="2" />2.</akn:num>
                            <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1" GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                                <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                                    <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                           GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-0.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird die Angabe <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="694459c4-ef66-4f87-bb78-a332054a2216" startQuote="„" endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die Wörter <akn:quotedText
                                            eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" startQuote="„" endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:quotedText> ersetzt.</akn:mod>
                                </akn:p>
                            </akn:content>
                        </akn:point>
                    </akn:list>
                </akn:paragraph>
            </akn:article>

            <!-- Artikel 3: Geltungszeitregel-->
            <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                    <akn:marker eId="hauptteil-1_art-3_bezeichnung-1_zaehlbez-1" GUID="7bbcdd71-a27b-4932-91b7-6c18356ed3e5" name="3" />Artikel 3</akn:num>
                <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                <!-- Absatz (1) -->
                <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                    <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">
                        <akn:marker eId="hauptteil-1_art-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="1d41fa26-e36a-4d03-8f4a-4790d3184944" name="1" />
                    </akn:num>
                    <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                        <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                            nach der Verkündung</akn:date> in Kraft. </akn:p>
                    </akn:content>
                </akn:paragraph>
            </akn:article>
        </akn:body>

        <!-- Schluss -->
        <akn:conclusions eId="schluss-1" GUID="5814d07a-3869-489d-b03e-239304e841cb">
            <akn:formula eId="schluss-1_formel-1" GUID="a269a1a9-e8ca-471a-a9d4-ce61e9af05aa" name="attributsemantik-noch-undefiniert" refersTo="schlussformel">
                <akn:p eId="schluss-1_formel-1_text-1" GUID="1354658b-fdb7-46b4-96e2-e194b16ee103">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
            </akn:formula>
            <akn:blockContainer eId="schluss-1_blockcontainer-1" GUID="35e11af5-ff7c-4422-af51-87cff3cc7ceb">
                <akn:p eId="schluss-1_blockcontainer-1_text-1" GUID="b55d6230-d71c-49d5-bf86-dab1db53ba6f">
                    <akn:location eId="schluss-1_blockcontainer-1_text-1_ort-1" GUID="19ffc7e1-add6-40a4-b9af-2f0927e46e9c" refersTo="attributsemantik-noch-undefiniert">Bonn</akn:location>, den <akn:date eId="schluss-1_blockcontainer-1_text-1_datum-1" GUID="34cf44aa-73a4-4dee-9827-4d890da7aac7"
                                                                                                                                                                                                            refersTo="ausfertigung-datum" date="1964-08-05">5. August 1964</akn:date>
                </akn:p>
                <akn:signature eId="schluss-1_blockcontainer-1_signatur-1" GUID="b092cd1d-2f77-4fe6-a63d-5bcabeb410ad">
                    <akn:role eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1" GUID="1cd9ff70-4004-48dc-8c76-7fb295852717" refersTo="attributsemantik-noch-undefiniert">Der Bundespräsident</akn:role>
                    <akn:person eId="schluss-1_blockcontainer-1_signatur-1_person-1"
                                GUID="3d296b9f-6b7d-4c34-a091-23291a39917a" refersTo="attributsemantik-noch-undefiniert">Lübke</akn:person>
                </akn:signature>
                <akn:signature eId="schluss-1_blockcontainer-1_signatur-2" GUID="2c6c0dc9-32b6-46ee-a1fa-9b8a0bc7c193">
                    <akn:role eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1" GUID="bea9417f-96d8-4a0b-a923-4541ed04b921" refersTo="attributsemantik-noch-undefiniert">Der Stellvertreter des Bundeskanzlers</akn:role>
                    <akn:person eId="schluss-1_blockcontainer-1_signatur-2_person-1"
                                GUID="33105c66-afdb-4a8d-b397-59c357bf2713" refersTo="attributsemantik-noch-undefiniert">Mende</akn:person>
                </akn:signature>
                <akn:signature eId="schluss-1_blockcontainer-1_signatur-3" GUID="669a4ce1-2782-4623-8af7-83e7bcf0917a">
                    <akn:role eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1" GUID="0732a41f-abe1-4774-b6c0-b4934082ca2c" refersTo="attributsemantik-noch-undefiniert">Der Bundesminister des Innern</akn:role>
                    <akn:person eId="schluss-1_blockcontainer-1_signatur-3_person-1"
                                GUID="a8095a73-f0f1-44ec-8c4e-92bc59ff44a7" refersTo="attributsemantik-noch-undefiniert">Hermann Höcherl</akn:person>
                </akn:signature>
                <akn:signature eId="schluss-1_blockcontainer-1_signatur-4" GUID="22a6ce3d-3ae9-4189-bfed-d544efe7e50b">
                    <akn:role eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1" GUID="cacb2add-a766-4f2e-ac4a-7b437b226d0d" refersTo="attributsemantik-noch-undefiniert">Der Bundesminister der Justiz</akn:role>
                    <akn:person eId="schluss-1_blockcontainer-1_signatur-4_person-1"
                                GUID="44bb454b-33df-4ee6-a29a-048d7b7f1148" refersTo="attributsemantik-noch-undefiniert">Bucher</akn:person>
                </akn:signature>
            </akn:blockContainer>
        </akn:conclusions>
    </akn:act>
</akn:akomaNtoso>`
