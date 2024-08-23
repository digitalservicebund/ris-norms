-- First delete announcement because of foreign key
DELETE FROM announcements where eli = 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1';

-- Amending law "Vereinsgesetz"
DELETE FROM norms where guid = 'e47a5106-c153-4da4-8d94-8cc2ebf9b232';
INSERT INTO norms (guid, eli, xml)
VALUES ('e47a5106-c153-4da4-8d94-8cc2ebf9b232', 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
	##################################################################################
	Projekt E-Gesetzgebung
	Nicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
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
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="e47a5106-c153-4da4-8d94-8cc2ebf9b232" />
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
                  <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml" />
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
                                 GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird die Angabe <akn:quotedText
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
</akn:akomaNtoso>');

-- Target law
DELETE FROM norms where guid = 'd04791fc-dcdc-47e6-aefb-bc2f7aaee151';
INSERT INTO norms (guid, eli, xml)
VALUES ('d04791fc-dcdc-47e6-aefb-bc2f7aaee151', 'eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?>
<!--
   This is a modified example from the LDML.de specification.
-->
<!--
	##################################################################################
	Projekt E-Gesetzgebung
	Nicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                       http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
   <akn:act name="regelungstext">
      <!-- Metadaten -->
      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
         <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="7ec6fe21-8b58-4e10-8b5d-6c9e179f725b" value="eli/bund/bgbl-1/1964/s593/regelungstext-1" />
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="c2182564-fee7-45f3-bcf2-df42bcb5ab25" value="eli/bund/bgbl-1/1964/s593" />
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="84dd7529-36d6-4504-8c1c-6298f2610873" name="übergreifende-id" value="3e61d5e0-3e99-46ab-b328-ad880d9c9a4a" />
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="b6ee5aa8-8950-4b81-be73-0f468c62c509" href="recht.bund.de/institution/bundesregierung" />
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="6c0a9858-7faf-4d88-b944-e60270d687a9" value="de" />
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="bgbl-1" />
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="f0d6366e-854f-4901-ab66-b054509e9364" value="regelungstext-1" />
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="ad94eb82-1a61-4210-939f-7e423fbc78d4" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu" />
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="76d63725-d669-4189-995d-92b9f8e4dadb" name="vorherige-version-id" value="30c19ca3-cf77-4ff9-8623-0cf60abac28e" />
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="519cad64-9102-4808-99ba-ea64988745bd" name="aktuelle-version-id" value="d04791fc-dcdc-47e6-aefb-bc2f7aaee151" />
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-3" GUID="849f7a4f-0477-4e00-8631-ca8232b32d2f" name="nachfolgende-version-id" value="a0bdb90e-31ca-4a48-b773-89cf858208fa" />
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="e4334f35-501a-46f0-a8f5-a02d9a03aca3" href="recht.bund.de/institution/bundesregierung" />
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="1bfbd095-93f3-4674-8096-be18041cbc65" date="1964-08-05" name="verkuendung" />
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="69cb29f5-3d31-48a4-8b48-14b1f23b64fb" language="deu" />
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="e346f244-30e0-40fa-81d0-ff21c4f34fb3" value="1" />
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="c2bb71f6-2e7f-4ba2-9f6e-fd4834114338">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="e3e3a77d-4027-4d7f-9e8d-66393d4f3a26" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml" />
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="a1118c13-2463-4c73-9375-dd39eab2e701" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml" />
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="fbdae376-ccbe-4097-9bbe-4e91203b15d8" date="1964-08-05" name="generierung" />
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1" GUID="5bbaf2cc-d861-4a57-b019-c4596b816673" href="recht.bund.de" />
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="6b4cbf44-5fd0-416c-9206-c0a48f778d29" value="xml" />
            </akn:FRBRManifestation>
         </akn:identification>

         <akn:lifecycle eId="meta-1_lebzykl-1" GUID="206a6403-76aa-4c9a-b03b-8c3d510d81db" source="attributsemantik-noch-undefiniert">
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="e545b87d-3f02-4fd1-b199-089f19c395c3" date="1964-08-05" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="3f84ea91-54e6-44c7-8e24-009d68a7c911" date="1964-09-21" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
         </akn:lifecycle>

         <akn:temporalData eId="meta-1_geltzeiten-1" GUID="0b03ee18-0131-47ec-bd46-519d60209cc7" source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="2b728878-4b15-4b14-89cd-ecc270202a81">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="10819a15-54fe-4924-a469-4a097f9c0e41" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
            </akn:temporalGroup>
         </akn:temporalData>

         <!-- Diese Metadaten sind die Konstituenten für die Schematron-Validierung besitzen keine fachliche Korrektheit. -->
         <akn:proprietary eId="meta-1_proprietary-1" GUID="7bd9d0dd-6dfe-4307-a699-fcce5ea3f03c" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <meta:fna>754-28-1</meta:fna>
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <meta:federfuehrung>
                  <meta:federfuehrend ab="1964-08-05" bis="unbestimmt">nicht-vorhanden</meta:federfuehrend>
                  <meta:federfuehrend ab="1964-08-05" bis="unbestimmt">nicht-vorhanden</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
         </akn:proprietary>
      </akn:meta>

      <!-- Dokumentenkopf Regelungstext -->
      <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
               <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="3b355cab-ce10-45b5-9cde-cc618fbf491f" />
               <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="c83abe1e-5fde-4e4e-a9b5-7293505ffeff" />
               <akn:docTitle
                  eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716">( <akn:inline
                     eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" GUID="bdff7240-266e-4ff3-b311-60342bd1afa2" refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert">Vereinsgesetz</akn:inline>)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
         <akn:block eId="einleitung-1_block-1" GUID="010d9df0-817a-49b6-a121-d0a1d412a3e3" name="attributsemantik-noch-undefiniert">
            <akn:date eId="einleitung-1_block-1_datum-1" GUID="28fafbe4-403d-4436-8d0d-7241cbbdade0" refersTo="ausfertigung-datum" date="1964-08-05">Vom 5. August 1964 </akn:date>
         </akn:block>
      </akn:preface>

      <!-- Eingangsformel + Inhaltsübersicht -->
      <akn:preamble eId="preambel-1" GUID="d3023c46-1e74-4c1f-b101-b29898267f71">
         <akn:formula eId="preambel-1_formel-1" GUID="1b476669-1cbb-4633-a0ed-04783680b841" refersTo="eingangsformel" name="attributsemantik-noch-undefiniert">
            <akn:p eId="preambel-1_formel-1_text-1" GUID="c1793533-b05a-4fc4-be62-bd6c3449c53a">Der Bundestag hat mit Zustimmung des Bundesrates das folgende Gesetz beschlossen:</akn:p>
         </akn:formula>
      </akn:preamble>

      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="2094abe2-16e6-44d5-9198-3ef9486833e9">
         <akn:article eId="hauptteil-1_para-20" GUID="b1b4bd3b-e007-4d84-af83-b8e36a0ae50b" period="#geltungszeitgr-1">
            <akn:num eId="hauptteil-1_para-20_bezeichnung-1" GUID="f82ab983-5498-49ab-918f-5cf5e730e5ec">
               <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1" GUID="24098f2f-05e0-4add-8941-286c8b239194" name="20" />§ 20</akn:num>
            <akn:paragraph eId="hauptteil-1_para-20_abs-1" GUID="6aa3a7ca-f30a-43b6-950b-b1e942fd1842" period="#geltungszeitgr-2">
               <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1" GUID="e363f12d-7918-435c-b3a1-182c5e03ff43">
                  <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1" GUID="3d4cac8d-fd22-4944-a767-3bf0e5f2c5b0" name="1" />(1) </akn:num>
               <akn:list eId="hauptteil-1_para-20_abs-1_untergl-1" GUID="97e930bf-49f8-472a-a1fa-3c3a401caa13">
                  <akn:intro eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1" GUID="e2261ee5-feda-4691-b1a0-8a18f43720e7">
                     <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1_text-1" GUID="f5b78960-3376-4f19-a1de-05e24443a141">Wer</akn:p>
                  </akn:intro>
                  <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1" GUID="6f1b22e6-2fcc-4e29-b3c1-fd67d8cee45c">
                     <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1" GUID="7947817d-e127-49ca-82c3-a1dfeaa73748">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1" GUID="7b98614e-78d8-4c77-98ed-d00841545900" name="1" />1. </akn:num>
                     <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1" GUID="fff000f9-bed8-41eb-b26b-93a5da40ff5f">
                        <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1_text-1" GUID="6ad07a0f-46f6-45b5-8d1a-ac79b8f704fe">entgegen einem vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als Mitglied
                           beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2" GUID="94af45e6-d49b-4bcc-84b9-5e5c01d5a3ba">
                     <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1" GUID="18a496f3-c0e7-4fb3-a41d-5c9fd75374c7">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1" GUID="f026a323-e5ff-4784-80f9-dbb1c2c3bad5" name="2" />2. </akn:num>
                     <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1" GUID="7e835e4b-52eb-4fa1-9698-d7a42589d715">
                        <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="0ba9a471-e9ef-44c4-b5da-f69f068a4483">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1" GUID="107ed2fd-e041-4ee4-9eee-b559f84a4ce8">
                     <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1_text-1" GUID="53af6200-56c2-4fed-bd27-25ddefe7b204">wird mit Gefängnis bis zu einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit
                        schwerer Strafe bedroht ist.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_para-20_abs-2" GUID="f0f31024-8381-4a62-8980-5f432d03a925" period="#geltungszeitgr-1">
               <akn:num eId="hauptteil-1_para-20_abs-2_bezeichnung-1" GUID="c0ea5d8c-8d91-4e0c-812e-429a7a24a1a2">
                  <akn:marker eId="hauptteil-1_para-20_abs-2_bezeichnung-1_zaehlbez-1" GUID="a78eb14d-6ba5-429d-bd48-3cbb0789f9c0" name="2" />(2) </akn:num>
               <akn:content eId="hauptteil-1_para-20_abs-2_inhalt-1" GUID="8b51c358-ca54-4949-b957-bcbe5e55f33d">
                  <akn:p eId="hauptteil-1_para-20_abs-2_inhalt-1_text-1" GUID="924d7140-bdfd-4a50-9b7f-feb006d19aae">In den Fällen des Absatzes 1 Nr. 1 gilt § 90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>

         <!-- Artikel 34: Geltungszeitregel-->
         <akn:article eId="hauptteil-1_para-34" GUID="6574da6e-2a23-4280-80dd-b4f4c5416cdf" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
            <akn:num eId="hauptteil-1_para-34_bezeichnung-1" GUID="19b65c8b-44ae-4092-be09-7de64ac623e5">
               <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1" GUID="e175bd87-32c5-45ae-ae76-b6c78b446729" name="34" />Artikel 34</akn:num>
            <akn:heading eId="hauptteil-1_para-34_überschrift-1" GUID="772db065-a163-4453-a22f-269f6aebddaa">Inkrafttreten</akn:heading>
            <!-- Absatz (1) -->
            <akn:paragraph eId="hauptteil-1_para-34_abs-1" GUID="38c00dca-43e1-4cb5-aa69-bd03cc486c99">
               <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1" GUID="b5758ec8-bcb9-4b69-8764-fb86911e937c">
                  <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1" GUID="20787d69-f97d-4451-9afa-d643d503ade6" name="1" />
               </akn:num>
               <akn:content eId="hauptteil-1_para-34_abs-1_inhalt-1" GUID="45ca26fa-b7c0-416b-9a0a-7f2bd7c86873">
                  <akn:p eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1" GUID="aa475227-3bb4-4568-99b3-98467b8daf91">Dieses Gesetz tritt einen Monat nach seiner Verkündung in Kraft.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
      </akn:body>

      <!-- Schluss -->
      <akn:conclusions eId="schluss-1" GUID="5814d07a-3869-489d-b03e-239304e841cb">
         <akn:formula eId="schluss-1_formel-1" GUID="a269a1a9-e8ca-471a-a9d4-ce61e9af05aa" name="attributsemantik-noch-undefiniert" refersTo="schlussformel">
            <akn:p eId="schluss-1_formel-1_text-1" GUID="1354658b-fdb7-46b4-96e2-e194b16ee103">Das vorstehende Gesetz ist hiermit verkündet.</akn:p>
         </akn:formula>
         <!-- Signaturen -->
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
</akn:akomaNtoso>');

-- ZF0
DELETE FROM norms where guid = 'a0bdb90e-31ca-4a48-b773-89cf858208fa';
INSERT INTO norms (guid, eli, xml)
VALUES ('a0bdb90e-31ca-4a48-b773-89cf858208fa', 'eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1', '<?xml version="1.0" encoding="UTF-8"?>
<!--
	##################################################################################
	Projekt E-Gesetzgebung
	Nicht-normative Exemplifikation für den Standard LegalDocML.de 1.6 (Dezember 2023)

	2023 Copyright (C) 2021-2023 Bundesministerium des Innern und für Heimat,
	Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische
	Verwaltungsarbeit

	Veröffentlicht unter der Lizenz CC-BY-3.0 (Creative Commons Namensnennung 3.0)
	##################################################################################
-->
<?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../ldml_de/Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd http://DS.Metadaten.LegalDocML.de/1.6/ ../metadata.xsd">
<!-- Regelungstext des Stammgesetzes nach der Ausfertigung des Änderungsgesetzes. Da die Änderungsbefehle noch nicht in Kraft getreten sind, werden diese lediglich in den Metadaten
       hinterlegt. Sie stellen nun schwebende Änderungen dar, bis sie in Kraft treten und angewendet werden. -->
   <akn:act name="regelungstext">
<!-- Metadaten -->
      <akn:meta eId="meta-1" GUID="849a7fcc-fa01-4b64-92d9-4843520d5db1">
         <akn:identification eId="meta-1_ident-1"
                             GUID="7fdd6c0b-2f11-495a-821a-ad37280c0f47"
                             source="attributsemantik-noch-undefiniert">
            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1"
                          GUID="077a0879-2de5-4b47-9471-ee7041b85e1b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1"
                             GUID="4e2aecf1-9ab6-4fa5-8588-dd9c91e39298"
                             value="eli/bund/bgbl-1/1964/s593/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1"
                            GUID="c508aec6-0922-4076-980c-5e3b52e1b281"
                            value="eli/bund/bgbl-1/1964/s593"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1"
                              GUID="1d845947-087f-4596-be99-306c2821b6a3"
                              name="übergreifende-id"
                              value="fbccff4a-b7b6-440f-a04b-5b2bef0f7aee"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1"
                             GUID="2a8db309-d5db-439b-b973-1064e05fea56"
                             date="1964-08-21"
                             name="verkuendungsfassung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"
                               GUID="ea6d398f-7942-46bc-aff0-83a8020a7af7"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"
                                GUID="620bbbc5-02d6-4713-95ec-0144741b053e"
                                value="de"/>
               <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"
                               GUID="e68755ec-9ec2-4f02-a748-98584a83597e"
                               value="s593"/>
               <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1"
                             GUID="6e00c4a4-5599-4b21-9179-719eb44e3386"
                             value="bgbl-1"/>
               <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"
                                GUID="b19c9acc-3194-4ee0-a138-093240bb7e58"
                                value="regelungstext-1"/>
            </akn:FRBRWork>
            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                GUID="076b44c2-e151-4251-8d51-d74a79f6cd81">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                             GUID="69a015c8-63fc-48a9-a49d-2f202d697e53"
                             value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1"
                            GUID="02469405-56fe-4ade-92f6-83b4a8a2cf6b"
                            value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
                              GUID="400bbb40-67aa-4cd3-b8b3-6c811947e8bd"
                              name="vorherige-version-id"
                              value="d04791fc-dcdc-47e6-aefb-bc2f7aaee151"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
                              GUID="6ae442b5-6601-4a72-bfa2-50a059348cb3"
                              name="aktuelle-version-id"
                              value="a0bdb90e-31ca-4a48-b773-89cf858208fa"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-3"
                              GUID="a1e5c767-7742-4edd-a0ca-52318cb555ba"
                              name="nachfolgende-version-id"
                              value="8de2c22c-d706-498a-9d96-930d7a03d224"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                               GUID="66705cfb-e2e2-45f0-912e-2b3dfa771f0c"
                               href="recht.bund.de/institution/bundesregierung"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"
                             GUID="6349f092-66e6-43ab-9046-6e5caec8726d"
                             date="2017-03-15"
                             name="aenderung"/>
               <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"
                                 GUID="33d28f79-e81d-48b9-a485-c3c867a2c4fb"
                                 language="deu"/>
               <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1"
                                      GUID="a573e1be-af0f-476c-905d-795762dc7f69"
                                      value="1"/>
            </akn:FRBRExpression>
            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                   GUID="bd2375e5-3e81-435d-a4f8-159d8572c46b">
               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                             GUID="9dcc818e-3ed8-4414-b562-342bd5f405b3"
                             value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1.xml"/>
               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                            GUID="cbf8ad34-8a5f-4323-a883-877784b593cb"
                            value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1.xml"/>
               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                             GUID="f3288a2a-3511-454e-ada1-9de8c33f6dbe"
                             date="2022-08-23"
                             name="generierung"/>
               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                               GUID="5fc7b4f8-ef64-4cc0-ae53-d79d25c06cb3"
                               href="recht.bund.de"/>
               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                               GUID="634de2b4-5d14-4144-9e8e-80548da73b73"
                               value="xml"/>
            </akn:FRBRManifestation>
         </akn:identification>
         <akn:lifecycle eId="meta-1_lebzykl-1"
                        GUID="f551699c-7848-4a0d-ab11-1ef44f309504"
                        source="attributsemantik-noch-undefiniert">
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1"
                          GUID="1efea719-b5d3-45c6-b5e9-31ecd7ead326"
                          date="1964-08-05"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="ausfertigung"/>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
                          GUID="df6b5e63-72de-45c2-80e9-742e3a5e92c1"
                          date="1964-09-21"
                          source="attributsemantik-noch-undefiniert"
                          type="generation"
                          refersTo="inkrafttreten"/>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3"
                          GUID="c02fd58b-6621-4867-a7d9-87b1b179afd5"
                          date="2017-03-15"
                          source="attributsemantik-noch-undefiniert"
                          type="amendment"
                          refersTo="ausfertigung"/>
            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4"
                          GUID="2f0febd4-edbe-4c02-9aca-827ee943ae28"
                          date="2017-03-16"
                          source="attributsemantik-noch-undefiniert"
                          type="amendment"
                          refersTo="inkrafttreten"/>
         </akn:lifecycle>
         <akn:analysis eId="meta-1_analysis-1"
                       GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4"
                       source="attributsemantik-noch-undefiniert">
            <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1"
                                      GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
               <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2"
                               GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2"
                               type="substitution">
                  <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1"
                              GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"
                              href="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml"/>
                  <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1"
                                   GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd"
                                   href="#auptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/100-126"/>
                  <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1"
                             GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb"
                             period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
               </akn:textualMod>
            </akn:passiveModifications>
         </akn:analysis>
         <akn:temporalData eId="meta-1_geltzeiten-1"
                           GUID="2fcdfa3e-1460-4ef4-b22b-5ff4a897538f"
                           source="attributsemantik-noch-undefiniert">
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1"
                               GUID="7b13adb9-ef62-43c4-bf1b-155561edf89b">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
                                 GUID="0ff5d5c7-3901-4277-8ba6-4111d7c0f703"
                                 refersTo="geltungszeit"
                                 start="#meta-1_lebzykl-1_ereignis-2"/>
            </akn:temporalGroup>
            <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2"
                               GUID="7af9337a-3727-424c-a3df-dee918a79b22">
               <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1"
                                 GUID="826b00c9-1069-44fa-a5fd-a5676e56c2f1"
                                 refersTo="geltungszeit"
                                 start="#meta-1_lebzykl-1_ereignis-4"/>
            </akn:temporalGroup>
         </akn:temporalData>
         <!-- Diese Metadaten sind die Konstituenten für die Schematron-Validierung. -->
         <akn:proprietary eId="meta-1_proprietary-1"
                          GUID="cbeef40f-ddc7-4ea5-9d4d-c0077844b58f"
                          source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
               <meta:typ>gesetz</meta:typ>
               <meta:form>stammform</meta:form>
               <meta:fassung>verkuendungsfassung</meta:fassung>
               <meta:art>regelungstext</meta:art>
               <meta:initiant>nicht-vorhanden</meta:initiant>
               <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
               <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
               <meta:fna>754-28-1</meta:fna>
               <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
               <meta:gesta>nicht-vorhanden</meta:gesta>
               <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
               <meta:federfuehrung>
                  <meta:federfuehrend ab="2002-12-01" bis="unbestimmt">BMI - Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                  <meta:federfuehrend ab="2002-10-01" bis="2002-12-01">BMJ - Bundesministerium der Justiz</meta:federfuehrend>
               </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
               <meta:celex></meta:celex>
               <meta:interne-referenz href="">
                  <meta:normgeber></meta:normgeber>
               </meta:interne-referenz>
            </meta:legalDocML.de_metadaten_ds>
         </akn:proprietary>
      </akn:meta>
      <!-- Dokumentenkopf Regelungstext -->
      <akn:preface eId="einleitung-1" GUID="6ad78914-08f7-42dd-8762-bc7f0e226121">
         <akn:longTitle eId="einleitung-1_doktitel-1"
                        GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
            <akn:p eId="einleitung-1_doktitel-1_text-1"
                   GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
               <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1"
                             GUID="7a8a9da0-0fed-4cf1-9fd5-18c149e4457a"/>
               <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1"
                                 GUID="d446d041-9b65-4c17-b250-106a808da3e9"/>
               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1"
                             GUID="b842df1f-4d69-4dfc-91aa-bf30d3d80485">Gesetz zur Regelung des öffentlichen
                  Vereinsrechts</akn:docTitle>
               <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1"
                               GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716">( <akn:inline eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1"
                              GUID="c0a4ae01-6176-438f-b98a-a10a949c6d9c"
                              refersTo="amtliche-abkuerzung"
                              name="attributsemantik-noch-undefiniert">Vereinsgesetz</akn:inline>)</akn:shortTitle>
            </akn:p>
         </akn:longTitle>
         <akn:block eId="einleitung-1_block-1"
                    GUID="010d9df0-817a-49b6-a121-d0a1d412a3e3"
                    name="attributsemantik-noch-undefiniert">
            <akn:date eId="einleitung-1_block-1_datum-1"
                      GUID="b9407154-03fb-44f2-933e-5cf8c4015cda"
                      refersTo="ausfertigung-datum"
                      date="1964-08-05">Vom 5.
               August 1964 </akn:date>
         </akn:block>
      </akn:preface>
      <!-- Eingangsformel + Inhaltsübersicht -->
      <akn:preamble eId="preambel-1" GUID="9688a7b7-6365-4992-ad77-a31857d20453">
<!-- Eingangsformel -->
         <akn:formula eId="preambel-1_formel-1"
                      GUID="1b476669-1cbb-4633-a0ed-04783680b841"
                      refersTo="eingangsformel"
                      name="attributsemantik-noch-undefiniert">
            <akn:p eId="preambel-1_formel-1_text-1"
                   GUID="c1793533-b05a-4fc4-be62-bd6c3449c53a">Der Bundestag hat mit Zustimmung des Bundesrates das folgende
               Gesetz beschlossen:</akn:p>
         </akn:formula>
      </akn:preamble>
      <!-- Hauptteil -->
      <akn:body eId="hauptteil-1" GUID="6c5d6dc7-f14b-4e1d-8f8b-570a13aeff9c">
<!-- § 9 -->
         <akn:article eId="hauptteil-1_para-9"
                      GUID="b2390631-4ed0-45f6-af24-dd4deeaeae06"
                      period="#geltungszeitgr-1">
            <akn:num eId="hauptteil-1_para-9_bezeichnung-1"
                     GUID="5ef4111b-874d-4843-8467-bbccf6bceb96">
               <akn:marker eId="hauptteil-1_para-9_bezeichnung-1_zaehlbez-1"
                           GUID="c7f9b0af-3aae-4fe1-be31-505c44f9fce7"
                           name="9"/>§ 9</akn:num>
            <akn:heading eId="hauptteil-1_para-9_überschrift-1"
                         GUID="c1717cbd-7785-4127-a9d5-b6423c49ab3d">Kennzeichenverbot</akn:heading>
            <!-- Absatz (1) -->
            <akn:paragraph eId="hauptteil-1_para-9_abs-1"
                           GUID="15f33532-babe-4c6f-817c-ccfd7e369191"
                           period="#geltungszeitgr-1">
               <akn:num eId="hauptteil-1_para-9_abs-1_bezeichnung-1"
                        GUID="5df1db91-e49a-4195-9552-3dddc4304912">
                  <akn:marker eId="hauptteil-1_para-9_abs-1_bezeichnung-1_zaehlbez-1"
                              GUID="2dac96e1-b4ff-4581-82b0-217b04a64266"
                              name="1"/>
               </akn:num>
               <akn:list eId="hauptteil-1_para-9_abs-1_untergl-1"
                         GUID="582db518-f7ad-493f-b8b1-e21e4ebbb014">
                  <akn:intro eId="hauptteil-1_para-9_abs-1_untergl-1_intro-1"
                             GUID="f8685276-25df-43a8-81f2-90fe48c30639">
                     <akn:p eId="hauptteil-1_para-9_abs-1_untergl-1_intro-1_text-1"
                            GUID="54473d0a-ad82-48c5-b537-7ea9cb49f91d">Kennzeichen des verbotenen
                        Vereins dürfen für die Dauer der Vollziehbarkeit des Verbots nicht mehr</akn:p>
                  </akn:intro>
                  <akn:point eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-1"
                             GUID="862be77d-ac5b-4dd7-8509-beb2d3620dda">
                     <akn:num eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-1_bezeichnung-1"
                              GUID="a5e0f415-85f8-4e67-a944-5de26305f657">
                        <akn:marker eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"
                                    GUID="97168e0f-716a-4eda-9df5-dd34306fea6d"
                                    name="1"/>
                     </akn:num>
                     <akn:content eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-1_inhalt-1"
                                  GUID="d9a79058-1058-477f-957d-0802540a3026">
                        <akn:p eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"
                               GUID="976fe7d2-fd6e-4428-b3e3-e9bf8ea6bac3">öffentlich, in
                           einer Versammlung oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-2"
                             GUID="9bf3bfd3-9cc9-4a79-aaed-ca1306bbaa19">
                     <akn:num eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                              GUID="4633e320-e1b0-4e29-a642-f5cb7f65f6c6">
                        <akn:marker eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                    GUID="81ec0b92-3e0e-4b8b-a42b-858b4c7a4aef"
                                    name="2"/>
                     </akn:num>
                     <akn:content eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-2_inhalt-1"
                                  GUID="c2146cd4-efb0-4dee-b3df-ee153329acc8">
                        <akn:p eId="hauptteil-1_para-9_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                               GUID="e37987b6-5b22-4c0c-b4af-079722c45811">in Schriften,
                           Schallaufnahmen, Abbildungen oder Darstellungen, die verbreitet werden oder zur Verbreitung bestimmt sind,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp eId="hauptteil-1_para-9_abs-1_untergl-1_schlusstext-1"
                              GUID="5d4ecf82-556b-4235-8103-f8ff4cfb13a6">
                     <akn:p eId="hauptteil-1_para-9_abs-1_untergl-1_schlusstext-1_text-1"
                            GUID="f5d07efa-ed20-4317-85ff-11501845009d">verwendet werden.
                        Ausgenommen ist eine Verwendung von Kennzeichen im Rahmen der staatsbürgerlichen Aufklärung, der Abwehr verfassungswidriger Bestrebungen
                        und ähnlicher Zwecke.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <!-- Absatz 2 -->
            <akn:paragraph eId="hauptteil-1_para-9_abs-2"
                           GUID="402455e1-4290-4ef6-9046-14c349d4ead3"
                           period="#geltungszeitgr-1">
               <akn:num eId="hauptteil-1_para-9_abs-2_bezeichnung-1"
                        GUID="705fa60b-a782-48a3-b42d-115e20f4a0fc">
                  <akn:marker eId="hauptteil-1_para-9_abs-2_bezeichnung-1_zaehlbez-1"
                              GUID="dcad1386-52c7-42c5-8574-6ab93bee35e3"
                              name="2"/>(2) </akn:num>
               <akn:content eId="hauptteil-1_para-9_abs-2_inhalt-1"
                            GUID="b516d734-edc0-4497-bd56-950241dbbcc3">
                  <akn:p eId="hauptteil-1_para-9_abs-2_inhalt-1_text-1"
                         GUID="5b5cb25e-3d9f-44dc-a49e-c800df5e34f4">Kennzeichen im Sinnde des Absatzes 1 sind
                     insbesondere Fahnen, Abzeichen, Uniformstücke, Parolen und Grußformen.</akn:p>
               </akn:content>
            </akn:paragraph>
            <!-- Absatz 3 -->
            <akn:paragraph eId="hauptteil-1_para-9_abs-3"
                           GUID="87634142-4c46-456e-8f22-631810e868ff"
                           period="#geltungszeitgr-2">
               <akn:num eId="hauptteil-1_para-9_abs-3_bezeichnung-1"
                        GUID="0e865617-4a90-4431-aeca-bdb70408008e">
                  <akn:marker eId="hauptteil-1_para-9_abs-3_bezeichnung-1_zaehlbez-1"
                              GUID="a4ec34e4-b589-49c4-93c8-632f469bbedc"
                              name="3"/>(3) </akn:num>
               <akn:content eId="hauptteil-1_para-9_abs-3_inhalt-1"
                            GUID="b516ebae-eaa5-4f06-954a-963b4f07fc24">
                  <akn:p eId="hauptteil-1_para-9_abs-3_inhalt-1_text-1"
                         GUID="3662b21f-73d9-4a93-b57c-399fea9cd4e6">Diese Vorschriften gelten auch für die
                     Verwendung von Kennzeichen einer Ersatzorganisation für die Dauer der Vollziehbarkeit einer Verfügung nach § 8 Abs. 2 Satz 1.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-20"
                      GUID="b1b4bd3b-e007-4d84-af83-b8e36a0ae50b"
                      period="#geltungszeitgr-1">
            <akn:num eId="hauptteil-1_para-20_bezeichnung-1"
                     GUID="f82ab983-5498-49ab-918f-5cf5e730e5ec">
               <akn:marker eId="hauptteil-1_para-20_bezeichnung-1_zaehlbez-1"
                           GUID="b4b8fb7e-bf8b-45b9-9118-fd797600d2fa"
                           name="20"/>§ 20</akn:num>
            <akn:paragraph eId="hauptteil-1_para-20_abs-1"
                           GUID="6aa3a7ca-f30a-43b6-950b-b1e942fd1842"
                           period="#geltungszeitgr-2">
               <akn:num eId="hauptteil-1_para-20_abs-1_bezeichnung-1"
                        GUID="e363f12d-7918-435c-b3a1-182c5e03ff43">
                  <akn:marker eId="hauptteil-1_para-20_abs-1_bezeichnung-1_zaehlbez-1"
                              GUID="e5d8cf58-1ed0-45e4-9450-254738249dda"
                              name="1"/>(1) </akn:num>
               <akn:list eId="hauptteil-1_para-20_abs-1_untergl-1"
                         GUID="97e930bf-49f8-472a-a1fa-3c3a401caa13">
                  <akn:intro eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1"
                             GUID="e2261ee5-feda-4691-b1a0-8a18f43720e7">
                     <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_intro-1_text-1"
                            GUID="f5b78960-3376-4f19-a1de-05e24443a141">Wer</akn:p>
                  </akn:intro>
                  <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1"
                             GUID="6f1b22e6-2fcc-4e29-b3c1-fd67d8cee45c">
                     <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1"
                              GUID="7947817d-e127-49ca-82c3-a1dfeaa73748">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_bezeichnung-1_zaehlbez-1"
                                    GUID="aff56862-ab0e-4db9-add5-26cbaaaa40ef"
                                    name="1"/>1. </akn:num>
                     <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1"
                                  GUID="fff000f9-bed8-41eb-b26b-93a5da40ff5f">
                        <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1_text-1"
                               GUID="6ad07a0f-46f6-45b5-8d1a-ac79b8f704fe">entgegen einem
                           vollziehbaren Verbot den Verein fortführt, seinen organisatorischen Zusammenhalt auf andere Weise aufrechterhält, sich an ihm als
                           Mitglied beteiligt, für ihn wirbt, ihn unterstützt oder eine Tätigkeit ausübt (§ 18 Satz 2) oder</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:point eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2"
                             GUID="94af45e6-d49b-4bcc-84b9-5e5c01d5a3ba">
                     <akn:num eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                              GUID="18a496f3-c0e7-4fb3-a41d-5c9fd75374c7">
                        <akn:marker eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                    GUID="0e883c7b-3c72-4a41-a182-a44b26763e75"
                                    name="2"/>2. </akn:num>
                     <akn:content eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1"
                                  GUID="7e835e4b-52eb-4fa1-9698-d7a42589d715">
                        <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="0ba9a471-e9ef-44c4-b5da-f69f068a4483">entgegen § 9 Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,</akn:p>
                     </akn:content>
                  </akn:point>
                  <akn:wrapUp eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1"
                              GUID="107ed2fd-e041-4ee4-9eee-b559f84a4ce8">
                     <akn:p eId="hauptteil-1_para-20_abs-1_untergl-1_schlusstext-1_text-1"
                            GUID="53af6200-56c2-4fed-bd27-25ddefe7b204">wird mit Gefängnis bis zu
                        einem Jahr oder mit Geldstrafe bestraft, wenn die Tat nicht in den §§ 49 b, 90 a, 90 b, 96 a, 128 oder 129 des Strafgesetzbuches, mit
                        schwerer Strafe bedroht ist.</akn:p>
                  </akn:wrapUp>
               </akn:list>
            </akn:paragraph>
            <akn:paragraph eId="hauptteil-1_para-20_abs-2"
                           GUID="f0f31024-8381-4a62-8980-5f432d03a925"
                           period="#geltungszeitgr-1">
               <akn:num eId="hauptteil-1_para-20_abs-2_bezeichnung-1"
                        GUID="c0ea5d8c-8d91-4e0c-812e-429a7a24a1a2">
                  <akn:marker eId="hauptteil-1_para-20_abs-2_bezeichnung-1_zaehlbez-1"
                              GUID="11894e76-fcfa-4d43-bfac-9350f6659fe4"
                              name="2"/>(2) </akn:num>
               <akn:content eId="hauptteil-1_para-20_abs-2_inhalt-1"
                            GUID="8b51c358-ca54-4949-b957-bcbe5e55f33d">
                  <akn:p eId="hauptteil-1_para-20_abs-2_inhalt-1_text-1"
                         GUID="924d7140-bdfd-4a50-9b7f-feb006d19aae">In den Fällen des Absatzes 1 Nr. 1 gilt §
                     90 a Abs. 5 und 6 des Strafgesetzbuches entsprechend.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
         <akn:article eId="hauptteil-1_para-34"
                      GUID="9aefac39-6322-462f-9b93-7d1a046f2913"
                      period="#geltungszeitgr-1"
                      refersTo="geltungszeitregel">
            <akn:num eId="hauptteil-1_para-34_bezeichnung-1"
                     GUID="6ef44f75-f570-49f2-8d4d-1deeb5eacdbc">
               <akn:marker eId="hauptteil-1_para-34_bezeichnung-1_zaehlbez-1"
                           GUID="23ef3aff-9b01-4575-9d79-d0ebad22bbd9"
                           name="34"/>§ 34 </akn:num>
            <akn:paragraph eId="hauptteil-1_para-34_abs-1"
                           GUID="33e50f7e-4639-4c56-8f7d-d53a423b305d"
                           period="#geltungszeitgr-1">
               <akn:num eId="hauptteil-1_para-34_abs-1_bezeichnung-1"
                        GUID="573d539f-44e1-47a9-8259-307f8536005e">
                  <akn:marker eId="hauptteil-1_para-34_abs-1_bezeichnung-1_zaehlbez-1"
                              GUID="33b0dd8e-55d0-4676-a778-fc1f2a03e1e1"
                              name="1"/>
               </akn:num>
               <akn:content eId="hauptteil-1_para-34_abs-1_inhalt-1"
                            GUID="1a983020-0a5c-4e20-8d65-7908affd6afc">
                  <akn:p eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1"
                         GUID="7f60908e-6cd0-445b-903a-f56dec498743">Dieses Gesetz tritt <akn:date eId="hauptteil-1_para-34_abs-1_inhalt-1_text-1_datum-1"
                               GUID="c374dd52-4cd1-4396-9ec2-39613d5850e9"
                               date="1964-09-05"
                               refersTo="inkrafttreten-datum">einen Monat nach seiner Verkündung</akn:date> in Kraft.</akn:p>
               </akn:content>
            </akn:paragraph>
         </akn:article>
      </akn:body>
      <akn:conclusions eId="schluss-1" GUID="96132f00-aa0f-4a61-938c-0c7489a5951c">
         <akn:formula eId="schluss-1_formel-1"
                      GUID="cb33e6f0-b38b-4be9-a0a3-84ce4d7d0097"
                      refersTo="schlussformel"
                      name="attributsemantik-noch-undefiniert">
            <akn:p eId="schluss-1_formel-1_text-1"
                   GUID="332b89f7-7f46-43eb-b660-9b325ed52b57">Das vorstehende Gesetz wird hiermit verkündet.</akn:p>
         </akn:formula>
         <!-- Signaturen -->
         <akn:blockContainer eId="schluss-1_blockcontainer-1"
                             GUID="35e11af5-ff7c-4422-af51-87cff3cc7ceb">
            <akn:p eId="schluss-1_blockcontainer-1_text-1"
                   GUID="11c37201-31d9-445f-9e11-9d0e3cf17932">
               <akn:location eId="schluss-1_blockcontainer-1_text-1_ort-1"
                             GUID="19ffc7e1-add6-40a4-b9af-2f0927e46e9c"
                             refersTo="attributsemantik-noch-undefiniert">Bonn</akn:location>, den <akn:date eId="schluss-1_blockcontainer-1_text-1_datum-1"
                         GUID="a698ed17-c4f1-4c2f-b8a7-a9adef0d4a83"
                         refersTo="ausfertigung-datum"
                         date="1964-08-05">5. August 1964 </akn:date>
            </akn:p>
            <akn:signature eId="schluss-1_blockcontainer-1_signatur-1"
                           GUID="b092cd1d-2f77-4fe6-a63d-5bcabeb410ad">
               <akn:role eId="schluss-1_blockcontainer-1_signatur-1_fktbez-1"
                         GUID="1cd9ff70-4004-48dc-8c76-7fb295852717"
                         refersTo="attributsemantik-noch-undefiniert">Der Bundespräsident</akn:role>
               <akn:person eId="schluss-1_blockcontainer-1_signatur-1_person-1"
                           GUID="7d3059e8-9419-4eea-bcc0-334aeef946c4"
                           refersTo="attributsemantik-noch-undefiniert">Lübke</akn:person>
            </akn:signature>
            <akn:signature eId="schluss-1_blockcontainer-1_signatur-2"
                           GUID="2c6c0dc9-32b6-46ee-a1fa-9b8a0bc7c193">
               <akn:role eId="schluss-1_blockcontainer-1_signatur-2_fktbez-1"
                         GUID="bea9417f-96d8-4a0b-a923-4541ed04b921"
                         refersTo="attributsemantik-noch-undefiniert">Der Stellvertreter des Bundeskanzlers</akn:role>
               <akn:person eId="schluss-1_blockcontainer-1_signatur-2_person-1"
                           GUID="3820781f-9308-45cd-ae69-39515927eff3"
                           refersTo="attributsemantik-noch-undefiniert">Mende</akn:person>
            </akn:signature>
            <akn:signature eId="schluss-1_blockcontainer-1_signatur-3"
                           GUID="669a4ce1-2782-4623-8af7-83e7bcf0917a">
               <akn:role eId="schluss-1_blockcontainer-1_signatur-3_fktbez-1"
                         GUID="0732a41f-abe1-4774-b6c0-b4934082ca2c"
                         refersTo="attributsemantik-noch-undefiniert">Der Bundesminister des Innern</akn:role>
               <akn:person eId="schluss-1_blockcontainer-1_signatur-3_person-1"
                           GUID="e3e63678-1e6e-4a5a-afed-3affb5ee979f"
                           refersTo="attributsemantik-noch-undefiniert">Hermann Höcherl</akn:person>
            </akn:signature>
            <akn:signature eId="schluss-1_blockcontainer-1_signatur-4"
                           GUID="22a6ce3d-3ae9-4189-bfed-d544efe7e50b">
               <akn:role eId="schluss-1_blockcontainer-1_signatur-4_fktbez-1"
                         GUID="cacb2add-a766-4f2e-ac4a-7b437b226d0d"
                         refersTo="attributsemantik-noch-undefiniert">Der Bundesminister der Justiz</akn:role>
               <akn:person eId="schluss-1_blockcontainer-1_signatur-4_person-1"
                           GUID="2682f933-0c75-4f9d-9599-1128dec5a3e4"
                           refersTo="attributsemantik-noch-undefiniert">Bucher</akn:person>
            </akn:signature>
         </akn:blockContainer>
      </akn:conclusions>
   </akn:act>
</akn:akomaNtoso>');

-- Announcement
INSERT INTO announcements (id, eli, released_by_documentalist_at)
VALUES ('620bbbc5-02d6-4713-95ec-0144741b053e', 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1', NULL);
