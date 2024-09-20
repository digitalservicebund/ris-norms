<?xml version="1.0" encoding="UTF-8"?>
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron"
            xmlns:fkt="lokale-funktionen"
            queryBinding="xslt2"
            schemaVersion="LegalDocML.de 1.7 (irgendwann 2024)">
<!--

********************************* Hinweis zur Lizensierung ***********************************
 2024 Copyright © 2021–2024 Bundesministerium des Innern und für Heimat,
 Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische Verwaltungsarbeit

      Lizenz: CC-BY-3.0 (Creative Commons Namensnennung 3.0)
 Information: https://creativecommons.org/licenses/by/3.0/legalcode.de
**********************************************************************************************

-->
   <sch:ns uri="lokale-funktionen" prefix="fkt"/>
   <sch:ns uri="http://Inhaltsdaten.LegalDocML.de/1.7/" prefix="akn"/>
   <sch:ns uri="http://Metadaten.LegalDocML.de/1.7/" prefix="meta"/>
   <!-- Determinanten für Dokumentvarianten: form -->
   <sch:let name="form"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:form"/>
   <sch:let name="form-stammform" value="'stammform'"/>
   <sch:let name="form-mantelform" value="'mantelform'"/>
   <sch:let name="form-eingebundene-stammform" value="'eingebundene-stammform'"/>
   <sch:let name="form-nicht-vorhanden" value="'nicht-vorhanden'"/>
   <!-- Determinanten für Dokumentvarianten: fassung -->
   <sch:let name="fassung"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:fassung"/>
   <sch:let name="fassung-entwurfsfassung" value="'entwurfsfassung'"/>
   <sch:let name="fassung-verkündungsfassung" value="'verkuendungsfassung'"/>
   <sch:let name="fassung-neufassung" value="'neufassung'"/>
   <!-- Determinanten für Dokumentvarianten: typ -->
   <sch:let name="typ"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:typ"/>
   <sch:let name="typ-gesetz" value="'gesetz'"/>
   <sch:let name="typ-sonstige-bekanntmachung" value="'sonstige-bekanntmachung'"/>
   <sch:let name="typ-verordnung" value="'verordnung'"/>
   <sch:let name="typ-vertragsgesetz" value="'vertragsgesetz'"/>
   <sch:let name="typ-vertragsverordnung" value="'vertragsverordnung'"/>
   <sch:let name="typ-verwaltungsvorschrift" value="'verwaltungsvorschrift'"/>
   <sch:let name="typ-satzung" value="'satzung'"/>
   <!-- Determinanten für Dokumentvarianten: art -->
   <sch:let name="art"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:art"/>
   <sch:let name="art-anschreiben" value="'anschreiben'"/>
   <sch:let name="art-begründung" value="'begruendung'"/>
   <sch:let name="art-regelungstext" value="'regelungstext'"/>
   <sch:let name="art-vereinbarung" value="'vereinbarung'"/>
   <sch:let name="art-vorblatt" value="'vorblatt'"/>
   <sch:let name="art-bericht" value="'bericht'"/>
   <sch:let name="art-bekanntmachungstext" value="'bekanntmachungstext'"/>
   <!-- Determinanten für Dokumentvarianten: initiant -->
   <sch:let name="initiant"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:initiant"/>
   <!-- Determinanten für Dokumentvarianten: bearbeitende-institution -->
   <sch:let name="bearbeitende-institution"
            value="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:bearbeitendeInstitution"/>
   <!-- Nähere Qualifizierungen innerhalb von Einzelvorschriften -->
   <sch:let name="refersto-literal-geltungszeitregel" value="'geltungszeitregel'"/>
   <sch:let name="refersto-literal-hauptaenderung" value="'hauptaenderung'"/>
   <sch:let name="refersto-literal-folgeaenderung" value="'folgeaenderung'"/>
   <sch:let name="refersto-literal-eingebundene-stammform"
            value="'eingebundene-stammform'"/>
   <sch:let name="refersto-literal-stammform" value="'stammform'"/>
   <sch:let name="refersto-literal-mantelform" value="'mantelform'"/>
   <sch:let name="refersto-literal-vertragsgesetz" value="'vertragsgesetz'"/>
   <sch:let name="refersto-literal-vertragsverordnung" value="'vertragsverordnung'"/>
   <!-- Lebenszyklus-bezogene Angaben -->
   <sch:let name="type-literal-ereignisreferenz-generation" value="'generation'"/>
   <sch:let name="type-literal-ereignisreferenz-repeal" value="'repeal'"/>
   <sch:let name="type-literal-ereignisreferenz-amendment" value="'amendment'"/>
   <sch:let name="refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"
            value="'ausfertigung-mit-noch-unbekanntem-datum'"/>
   <sch:let name="refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten"
            value="'inkrafttreten'"/>
   <sch:let name="refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum"
            value="'inkrafttreten-mit-noch-unbekanntem-datum'"/>
   <sch:let name="refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten"
            value="'ausserkrafttreten'"/>
   <sch:let name="refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten-mit-unbekanntem-datum"
            value="'ausserkrafttreten-mit-noch-unbekanntem-datum'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"
            value="'ausfertigung'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten"
            value="'inkrafttreten'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich"
            value="'inkrafttreten-grundsaetzlich'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend"
            value="'inkrafttreten-abweichend'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum"
            value="'inkrafttreten-mit-noch-unbekanntem-datum'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten"
            value="'ausserkrafttreten'"/>
   <sch:let name="refersto-literal-ereignisreferenz-verkündungssfassung-ausserkrafttreten-mit-unbekanntem-datum"
            value="'ausserkrafttreten-mit-noch-unbekanntem-datum'"/>
   <sch:let name="refersto-literal-ereignisreferenz-neufassung"
            value="'neufassung'"/>
   <sch:let name="dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"
            value="'Ein Regelungstext, ein Bekanntmachungstext oder eine Vereinbarung'"/>
   <!-- Sonstige Literale innerhalb der fachlichen Semantik -->
   <sch:let name="refersto-literal-vorblattabschnitt-erfüllungsaufwand"
            value="'vorblattabschnitt-erfuellungsaufwand'"/>
   <!-- ================================================================================================================================================ -->
   <!-- Regeln -->
   <sch:title>Regelungstext Entwurfsfassung</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00019"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $form = $form-eingebundene-stammform]">
         <sch:assert id="SCH-00019-000" role="error" test="not(./akn:preface/akn:block)">Ein Regelungstext in Stammform in einer Mantelform darf keinen
            Datumscontainer innerhalb des Dokumentenkopfes enthalten.</sch:assert>
         <sch:assert id="SCH-00019-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Ein Regelungstext in Stammform in einer Mantelform darf keine
            Eingangsformel enthalten.</sch:assert>
         <sch:assert id="SCH-00019-010" role="error" test="not(./akn:conclusions)">Ein Regelungstext in Stammform in einer Mantelform darf keinen Schlussteil
            enthalten.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00020"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]">
         <sch:assert id="SCH-00020-005" role="error" test="./akn:preamble/akn:formula">Für ein Gesetz muss eine Eingangsformel verwendet werden.</sch:assert>
         <sch:assert id="SCH-00020-010"
                     role="warn"
                     test="not(./akn:conclusions/akn:formula)">Für ein Gesetz in der Entwurfsfassung wird in der Regel keine
            Schlussformel benutzt.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00030"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]">
         <sch:assert id="SCH-00030-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Eine Verordnung darf keine Eingangsformel enthalten.</sch:assert>
         <sch:assert id="SCH-00030-010"
                     role="warn"
                     test="not(./akn:conclusions/akn:formula)">Für eine Verordnung in der Entwurfsfassung wird in der Regel keine
            Schlussformel benutzt.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00040"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]">
         <sch:assert id="SCH-00040-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Eine Verwaltungsvorschrift darf keine Eingangsformel
            enthalten.</sch:assert>
         <sch:assert id="SCH-00040-010"
                     role="warn"
                     test="not(./akn:conclusions/akn:formula)">Für eine Verwaltungsvorschrift in der Entwurfsfassung wird in der
            Regel keine Schlussformel benutzt.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Regelungstext Verkündungsfassung/Neufassung</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00048"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $form = $form-eingebundene-stammform]">
         <sch:assert id="SCH-00048-000" role="error" test="not(./akn:preface/akn:block)">Ein Regelungstext in Stammform in einer Mantelform darf keinen
            Datums-Container innerhalb des Dokumentenkopfes enthalten.</sch:assert>
         <sch:assert id="SCH-00048-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Ein Regelungstext in Stammform in einer Mantelform darf keine
            Eingangsformel enthalten.</sch:assert>
         <sch:assert id="SCH-00048-010" role="error" test="not(./akn:conclusions)">Ein Regelungstext in Stammform in einer Mantelform darf keinen Schlussteil
            enthalten.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00049"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-neufassung]">
         <sch:assert id="SCH-00049-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Ein Regelungstext in der Neufassung darf keine Eingangsformel
            enthalten.</sch:assert>
         <sch:assert id="SCH-00049-010" role="error" test="not(./akn:conclusions)">Ein Regelungstext in der Neufassung darf keine Schlussformel
            enthalten.</sch:assert>
         <sch:assert id="SCH-00049-015" role="error" test="$form = $form-stammform">Ein Regelungstext als Neufassung darf nur in einer Stammform
            vorkommen.</sch:assert>
         <sch:assert id="SCH-00049-020" role="error" test="not(./akn:preface/akn:block)">Für einen Regelungstext in der Neufassung darf kein Datums-Container
            innerhalb des Dokumentenkopfes verwendet werden.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00050"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]">
         <sch:assert id="SCH-00050-005" role="error" test="./akn:preamble/akn:formula">Für ein Gesetz muss eine Eingangsformel verwendet werden.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00060"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]">
         <sch:assert id="SCH-00060-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Eine Verordnung darf keine Eingangsformel enthalten.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00070"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]">
         <sch:assert id="SCH-00070-005"
                     role="error"
                     test="not(./akn:preamble/akn:formula)">Eine Verwaltungsvorschrift darf keine Eingangsformel
            enthalten.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00071"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-verkündungsfassung]">
         <sch:assert id="SCH-00071-005" role="error" test="./akn:preface/akn:block">Für einen Regelungstext in der Verkündungsfassung muss ein Datums-Container
            innerhalb des Dokumentenkopfes verwendet werden. </sch:assert>
         <sch:assert id="SCH-00071-010"
                     role="error"
                     test="./akn:conclusions/akn:blockContainer">Für einen Regelungstext in der Verkündungsfassung muss ein
            Signaturblock verwendet werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00072"
                context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz) and not($fassung = $fassung-neufassung)]">
         <sch:assert id="SCH-00072-005"
                     role="error"
                     test="exists(./akn:conclusions/akn:formula)">Für einen Regelungstext in der Verkündungsfassung muss eine
            Schlussformel verwendet werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Regelungstext Allgemein</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00100"
                context="/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]/akn:preamble">
         <sch:assert id="SCH-00100-005" role="error" test="not(./akn:citations)">Ein Gesetz darf keine Ermächtigungsnorm enthalten.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00110"
                context="/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung) and not($fassung = $fassung-neufassung)]/akn:preamble">
         <sch:assert id="SCH-00110-005" role="error" test="./akn:citations">Eine Verordnung muss Ermächtigungsnormen bereitstellen.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00120"
                context="akn:quotedStructure//akn:article [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]">
         <sch:assert id="SCH-00120-000"
                     test="(@refersTo = ($refersto-literal-mantelform, $refersto-literal-stammform, $refersto-literal-geltungszeitregel, $refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung))">Wird innerhalb eines Änderungsbefehls eine Einzelvorschrift in Gänze geändert, neugefasst, hinzugefügt oder gelöscht, so muss diese näher - als
            Mantelform oder Stammform, als Geltungszeitregel oder als Vertragsgesetz bzw. Vertragsverordnung - bestimmt werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00121"
                context="akn:article[not(ancestor::akn:quotedStructure)]/@refersTo [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]">
         <sch:assert id="SCH-00121-000"
                     test=". = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-eingebundene-stammform, $refersto-literal-geltungszeitregel)">Liegt ein Regelungstext in Mantelform vor und seine Einzelvorschriften sind mittels @refersTo näher bestimmt, so dürfen lediglich folgende Literale
            verwendet werden: "hauptaenderung", "folgeaenderung", "eingebundene-stammform", "geltungszeitregel".</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00122"
                context="akn:article/@refersTo [ $form = $form-stammform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]">
         <sch:assert id="SCH-00122-000"
                     test=". = $refersto-literal-geltungszeitregel and count(//akn:article/@refersTo) = 1">Ein Regelungstext in Stammform
            darf als nähere Bestimmung seiner Einzelvorschriften (mittels @refersTo) höchstens genau eine Geltungszeitregel und keine sonstigen
            besitzen.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00123"
                context="akn:article[descendant::akn:mod and not(ancestor::akn:quotedStructure)] [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]">
         <sch:assert id="SCH-00123-000"
                     test="@refersTo = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-geltungszeitregel)">Eine Einzelvorschrift,
            die einen Änderungsbefehl beinhaltet, muss entweder als Hauptänderung, Folgeänderung oder als Geltungszeit ausgezeichnet werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Klasse Inhaltsverzeichnis</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00130"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:preamble">
         <sch:report id="SCH-00130-005"
                     role="error"
                     test="count(./akn:blockContainer[@refersTo = 'inhaltsuebersicht']) gt 1">Es darf maximal eine
            Inhaltsübersicht geben.</sch:report>
         <sch:report id="SCH-00130-010"
                     role="error"
                     test="count(./akn:blockContainer[@refersTo = 'anlagenuebersicht']) gt 1">Es darf maximal eine
            Anlagenübersicht geben.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:title>Klasse: Hauptteil</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00150"
                context="/akn:akomaNtoso/*/akn:body[$form = $form-mantelform]">
         <sch:report id="SCH-00150-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle">Der
            Hauptteil einer Mantelform wird nicht in weitere Gliederungsabschnitte untergliedert.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00160"
                context="/akn:akomaNtoso/*/akn:body[ $form = ($form-mantelform, $form-stammform) and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]">
         <sch:assert id="SCH-00160-010"
                     role="error"
                     test="count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 1">Innerhalb eines Regelungstextes (sowohl in der Entwurfs- als auch der Verkündungsfassung), der in Stamm- oder Mantelform vorliegt,
            muss es genau eine Einzelvorschrift bezüglich der Geltungszeit geben.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00170"
                context="/akn:akomaNtoso/*/akn:body[$form = $form-eingebundene-stammform]">
         <sch:assert id="SCH-00170-000"
                     test="if ($form = $form-eingebundene-stammform) then (count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 0) else ()">Ein Regelungstext als eingebundene Stammform darf keine Einzelvorschrift bezüglich der Geltungszeit besitzen.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Aufzählungen</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00200" context="//akn:article/akn:paragraph//akn:list">
         <sch:report id="SCH-00200-005" role="warn" test="count(ancestor::akn:list) &gt; 4">Es ist maximal eine Vierfachuntergliederung von Sätzen
            erlaubt.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00210" context="//akn:article">
         <sch:report id="SCH-00210-005" role="warn" test="count(./akn:paragraph) &gt; 6">Es ist maximal eine Sechsfachuntergliederung in Absätzen
            erlaubt.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:title>Gliederungsebenen</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00230" context="//akn:book">
         <sch:report id="SCH-00230-005" role="error" test="./akn:book"> Innerhalb eines Gliederungsabschnitts "Buch" darf diese Gliederungsebene nicht verwendet
            werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00240" context="//akn:part">
         <sch:report id="SCH-00240-005" role="error" test="./akn:book or ./akn:part"> Innerhalb eines Gliederungsabschnitts "Teil" darf diese Gliederungsebene
            nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00250" context="//akn:chapter">
         <sch:report id="SCH-00250-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter"> Innerhalb eines Gliederungsabschnitts "Kapitel" darf
            diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00260" context="//akn:subchapter">
         <sch:report id="SCH-00260-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter"> Innerhalb eines Gliederungsabschnitts
            "Unterkapitel" darf diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00270" context="//akn:section">
         <sch:report id="SCH-00270-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section"> Innerhalb eines
            Gliederungsabschnitts "Abschnitt" darf diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00280" context="//akn:subsection">
         <sch:report id="SCH-00280-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection">
            Innerhalb eines Gliederungsabschnitts "Unterabschnitt" darf diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00290" context="//akn:title">
         <sch:report id="SCH-00290-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title">Innerhalb eines
            Gliederungsabschnitts "Titel" darf diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00300" context="//akn:subtitle">
         <sch:report id="SCH-00300-005"
                     role="error"
                     test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle">Innerhalb
            eines Gliederungsabschnitts "Untertitel" darf diese Gliederungsebene nicht verwendet werden.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:title>Schlussteil</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00310"
                context="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:conclusions">
         <sch:report id="SCH-00310-005"
                     role="error"
                     test="./akn:blockContainer and $bearbeitende-institution = 'bundesregierung'">Der Signaturblock steht nur dem
            Bundestag in der Entwurfsfassung optional zur Verfügung.</sch:report>
         <sch:report id="SCH-00310-010"
                     role="error"
                     test="./akn:blockContainer and $bearbeitende-institution = 'bundesrat'">Der Signaturblock steht nur dem Bundestag in der Entwurfsfassung optional zur Verfügung.</sch:report>
      </sch:rule>
      <!-- <sch:rule id="SCH-00320" context="/akn:akomaNtoso/akn:act[@name = $art-regelungstext]/akn:conclusions/akn:formula">
         <sch:assert id="SCH-00320-005" role="error" test="./akn:p">Wenn eine Schlussformel in einem verkündeten Regelungstext verwendet wird, dann muss
            mindestens ein Schlusssatz verwendet werden.</sch:assert>
      </sch:rule>-->
   </sch:pattern>
   <sch:title>Vorblatt (Regelungstext)</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00330" context="//akn:doc[@name = $art-vorblatt]/akn:mainBody">
         <sch:assert id="SCH-00330-005"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-problem-und-ziel'])"> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Problem und Ziel' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-010"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-loesung'])"> Es wird
            empfohlen, dass ein Vorblattabschnitt 'Lösung' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-015"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-alternativen'])"> Es wird
            empfohlen, dass ein Vorblattabschnitt 'Alternativen' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-020"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand'])"> Es wird empfohlen, dass ein Vorblattabschnitt 'Haushaltsausgaben ohne Erfüllungsaufwand' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-025"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand'])"> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Erfüllungsaufwand' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-030"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft'])"> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand Für die Wirtschaft' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-035"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft']/akn:tblock[@refersTo = 'davon-buerokratiekosten-aus-informationspflichten'])">Es wird empfohlen, dass innerhalb des Erfüllungsaufwands für die Wirtschaft ein Unterabschnitt 'Davon Bürokratiekosten aus Informationspflichten'
            verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-040"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-buergerinnen-und-buerger'])"> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand Für Bürgerinnen und Bürger' verwendet
            wird.</sch:assert>
         <sch:assert id="SCH-00330-045"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-der-verwaltung'])"> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand der Verwaltung' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00330-050"
                     role="error"
                     test="exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-weitere-kosten'])"> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Weitere Kosten' verwendet wird.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00335"
                context="//akn:doc[@name = $art-vorblatt]/akn:mainBody/akn:hcontainer[@refersTo = $refersto-literal-vorblattabschnitt-erfüllungsaufwand]//akn:tblock">
         <sch:let name="zulässige-literale-zum-erfüllungsaufwand"
                  value="( 'erfuellungsaufwand-fuer-buergerinnen-und-buerger', 'erfuellungsaufwand-fuer-die-wirtschaft', 'davon-buerokratiekosten-aus-informationspflichten', 'erfuellungsaufwand-der-verwaltung' )"/>
         <sch:let name="mehr-als-ein-literal"
                  value="count($zulässige-literale-zum-erfüllungsaufwand)"/>
         <sch:assert id="SCH-00335-000"
                     role="error"
                     test="if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zum-erfüllungsaufwand) else true()"> Im Kontext eines Vorblattabschnitts zum Erfüllungsaufwand kann das Literal '<sch:value-of select="@refersTo"/>' nicht
            verwendet werden; zulässig <sch:value-of select="if (xs:boolean($mehr-als-ein-literal)) then 'sind' else 'ist'"/> hier ausschließlich <sch:value-of select="if (xs:boolean($mehr-als-ein-literal)) then concat(string-join( for $literal in $zulässige-literale-zum-erfüllungsaufwand[position() lt last()] return concat('''', $literal, ''''), ', '), ' oder ', concat('''', $zulässige-literale-zum-erfüllungsaufwand[last()], '''') ) else concat('''', exactly-one($zulässige-literale-zum-erfüllungsaufwand), '''')"/>.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Begründung</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00340"
                context="/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-gesetz, $typ-verordnung, $typ-verwaltungsvorschrift)]">
         <sch:assert id="SCH-00340-005"
                     role="error"
                     test="./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']"> Es muss ein
            allgemeiner Teil der Begründung vorliegen.</sch:assert>
         <sch:assert id="SCH-00340-010"
                     role="error"
                     test="./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil']"> Es muss ein
            besonderer Teil der Begründung vorliegen.</sch:assert>
         <sch:report id="SCH-00340-015" role="error" test="./akn:conclusions"> Eine Schlussbemerkung wird nur innerhalb einer Begründung von Vertragsrechtsakten
            benutzt.</sch:report>
      </sch:rule>
      <sch:rule id="SCH-00345"
                context="//akn:doc[@name = $art-begründung]/akn:mainBody//akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']//akn:tblock">
         <sch:let name="zulässige-literale-zu-regelungsfolgen"
                  value="( 'begruendung-erfuellungsaufwand-fuer-buergerinnen-und-buerger', 'begruendung-erfuellungsaufwand-fuer-die-wirtschaft', 'begruendung-erfuellungsaufwand-der-verwaltung', 'regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung', 'regelungsfolgen-abschnitt-nachhaltigkeitsaspekte', 'regelungsfolgen-abschnitt-erfuellungsaufwand', 'regelungsfolgen-abschnitt-weitere-kosten', 'regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung', 'regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand', 'regelungsfolgen-abschnitt-weitere-regelungsfolgen' )"/>
         <sch:let name="mehr-als-ein-literal"
                  value="count($zulässige-literale-zu-regelungsfolgen)"/>
         <sch:assert id="SCH-00345-000"
                     role="error"
                     test="if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zu-regelungsfolgen) else true()"> Im Kontext eines Begründungsabschnitt zu Regelungsfolgen kann das Literal '<sch:value-of select="@refersTo"/>' nicht
            verwendet werden; zulässig <sch:value-of select="if (xs:boolean($mehr-als-ein-literal)) then 'sind' else 'ist'"/> hier ausschließlich <sch:value-of select="if (xs:boolean($mehr-als-ein-literal)) then concat(string-join( for $literal in $zulässige-literale-zu-regelungsfolgen[position() lt last()] return concat('''', $literal, ''''), ', '), ' oder ', concat('''', $zulässige-literale-zu-regelungsfolgen[last()], '''') ) else concat('''', exactly-one($zulässige-literale-zu-regelungsfolgen), '''')"/>.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Prüfkriterien</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00350"
                context="//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']">
         <sch:assert id="SCH-00350-005"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-zielsetzung-und-notwendigkeit'])"> Es wird empfohlen, dass ein Begründungsabschnitt 'Zielsetzung und Notwendigkeit' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00350-010"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-wesentlicher-inhalt'])"> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Wesentlicher Inhalt' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00350-015"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-alternativen'])"> Es wird
            empfohlen, dass ein Begründungsabschnitt 'Alternativen' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00350-020"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungskompetenz'])"> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Regelungskompetenz' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00350-025"
                     role="warn"
                     test="exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen'])"> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Regelungsfolgen' oder 'Gesetzesfolgen' verwendet wird.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Prüfkriterien Allgemeiner Teil Regelungsfolgen</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00360"
                context="//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']/akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']/akn:content">
         <sch:assert id="SCH-00360-005"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung'])"> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Rechts und Verwaltungsvereinfachung' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-010"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-nachhaltigkeitsaspekte'])"> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Nachhaltigkeitsaspekte' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-015"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-erfuellungsaufwand'])"> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Erfuellungsaufwand' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-020"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-kosten'])"> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Weitere Kosten' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-025"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung'])"> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Gleichstellungspolitische Relevanzprüfung' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-030"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand'])"> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Haushaltsausgaben ohne Erfüllungsaufwand' verwendet wird.</sch:assert>
         <sch:assert id="SCH-00360-035"
                     role="warn"
                     test="exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-regelungsfolgen'])">Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Weitere Regelungsfolgen' verwendet wird.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Begründung zu einem Vertragsrechtsakt</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00370"
                context="/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)]">
         <sch:assert id="SCH-00370-005"
                     role="error"
                     test="count(./akn:mainBody/akn:hcontainer) = 1"> Eine Begründung zu einem Vertragsrechtsakt besitzt genau
            einen Begründungsabschnitt.</sch:assert>
         <sch:assert id="SCH-00370-010"
                     role="error"
                     test="./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil']"> Eine Begründung zu
            einem Vertragsrechtsakt besitzt einen Begründungsabschnitt 'Besonderer Teil'.</sch:assert>
         <sch:assert id="SCH-00370-015" role="error" test="./akn:conclusions"> Eine Begründung zu einem Vertragsrechtsakt besitzt eine
            Schlussbemerkung.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Anschreiben</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00380"
                context="/akn:akomaNtoso/akn:doc[@name = $art-anschreiben]">
         <sch:report id="SCH-00380-005"
                     role="error"
                     test="$bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' ) and ./akn:mainBody/akn:p/akn:date[@refersTo = 'fristablauf-datum']"> Das Fristablaufsdatum steht
            nur dem Bundesrat optional zur Verfügung.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:title>Ausschussüberweisung/Legislaturperiode/Drucksachennummer</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00390"
                context="/akn:akomaNtoso/akn:doc[@name = ($art-anschreiben, $art-vorblatt)]/akn:preface/akn:p">
         <sch:report id="SCH-00390-005"
                     role="error"
                     test="./akn:inline and $bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )"> Eine Ausschussüberweisung steht nur dem Bundesrat optional zur Verfügung.</sch:report>
         <sch:report id="SCH-00390-015"
                     role="error"
                     test="./akn:legislature and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )"> Die Legislaturperiode steht nur dem Bundestag/Bundesrat optional zur Verfügung.</sch:report>
         <sch:report id="SCH-00390-020"
                     role="error"
                     test="./akn:docNumber and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )"> Die Drucksachennummer steht nur dem Bundestag/Bundesrat optional zur Verfügung.</sch:report>
      </sch:rule>
   </sch:pattern>
   <sch:title>Vereinbarung</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00400"
                context="/akn:akomaNtoso/akn:act[@name = $art-vereinbarung]/akn:body">
         <sch:assert id="SCH-00400-005"
                     role="error"
                     test="@refersTo = ( 'notenwechsel', 'vertrag', 'fakultativprotokoll' )"> Der Hauptteil einer Vereinbarung muss entweder ein Notenwechsel, ein Vertrag oder ein Fakultativprotokoll sein. </sch:assert>
         <sch:assert id="SCH-00400-010"
                     role="error"
                     test="count(./akn:hcontainer[@refersTo = 'verbindliche-sprachfassung']) &gt;= 1"> Es
            muss mindestens eine verbindliche Sprachfassung existieren. </sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Änderungsbefehle</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00420" context="//akn:textualMod[@type = 'insertion']">
         <sch:assert id="SCH-00420-005" role="error" test="./akn:destination/@pos">Wenn ein Änderungsbefehl eine Einfügung beinhaltet, muss in seinen Metadaten
            eine Positionsangabe mittels @pos angegeben werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00421"
                context="akn:article [ $form = ($form-stammform, $form-eingebundene-stammform) or $fassung = $fassung-neufassung or $typ = $typ-sonstige-bekanntmachung ]">
         <sch:assert id="SCH-00421-000" test="not(exists(descendant::akn:mod))">Änderungsbefehle dürfen nur im Rahmen einer Mantelform vorkommen.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00422" context="//akn:mod[not(ancestor::akn:mod)]">
         <sch:let name="alle-textänderungen"
                  value="/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/(akn:textualMod, akn:forceMod)/akn:source"/>
         <sch:let name="gesuchte-änderungsbefehl-id" value="concat('#', @eId)"/>
         <sch:assert id="SCH-00422-000"
                     test="count($alle-textänderungen[@href = $gesuchte-änderungsbefehl-id]) = 1">Zu jedem Änderungsbefehl im Hauptteil
            (akn:mod) muss es im Metadatenblock genau eine zugehörige Text- oder Geltungszeitänderung geben. Es existiert jedoch keine solche Änderung
            (akn:textualMod bzw. akn:forceMod), deren Quellenangabe "<sch:value-of select="$gesuchte-änderungsbefehl-id"/>" referenziert.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00423"
                context="/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:textualMod/akn:source | /akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:forceMod/akn:source">
         <sch:let name="referenzierte-änderungsbefehl-id"
                  value="substring(@href, string-length('#') + 1)"/>
         <sch:assert id="SCH-00423-000"
                     test="count(//akn:mod[@eId = $referenzierte-änderungsbefehl-id]) = 1">Zu jeder im Metadatenblock deklarierten
            Textänderung (akn:textualMod) oder Geltungszeitänderung (akn:forceMod) muss es im Hauptteil genau einen zugehörigen Änderungsbefehl (akn:mod) geben.
            Es existiert jedoch kein solcher Änderungsbefehl, dessen @eId "<sch:value-of select="$referenzierte-änderungsbefehl-id"/>" lautet.</sch:assert>
         <sch:assert id="SCH-00423-010" test="starts-with(@href, '#')"> Die Referenz auf die Quelle eines Änderungsbefehls innerhalb von akn:activeModifications
            ist stets ein interner Verweis; sie muss deshalb eine Raute ("#") als erstes Zeichen besitzen. </sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00424"
                context="akn:mod[@refersTo='aenderungsbefehl-umnummerierung']">
         <sch:assert id="SCH-00424-000"
                     test="exists(akn:span[@refersTo='neue-verweisangabe'])">Ein Umnummerierungsbefehl (akn:mod mit refers-to='aenderungsbefehl-umnummerierung') muss eine neue Verweisangabe (Element akn:span mit refers-to='neue-verweisangabe') enthalten.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00425" context="akn:span[@refersTo='neue-verweisangabe']">
         <sch:assert id="SCH-00425-000"
                     test="exists(parent::akn:mod[@refersTo='aenderungsbefehl-umnummerierung'])">Die Auszeichnung als neue Verweisangabe (akn:span mit refers-to='neue-verweisangabe') ist nur zulässig als Kindelement eines Umnummerierungsbefehls (akn:mod mit refers-to='aenderungsbefehl-umnummerierung').</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Struktureller Aufbau von eId-Textknoten</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00430"
                context="/akn:akomaNtoso/(akn:act | akn:bill | akn:doc | akn:statement | akn:documentCollection)/*[@eId]//*[@eId]">
         <sch:let name="trennzeichen-zwischen-eids" value="'_'"/>
         <sch:let name="geprüfte-eid" value="@eId"/>
         <sch:let name="geprüfte-eid-lokaler-teil"
                  value="tokenize(@eId, $trennzeichen-zwischen-eids)[last()]"/>
         <sch:let name="vorgänger-eid" value="ancestor::*[@eId and position() = 1]/@eId"/>
         <sch:assert id="SCH-00430-000"
                     role="error"
                     test="$geprüfte-eid = concat($vorgänger-eid, $trennzeichen-zwischen-eids, $geprüfte-eid-lokaler-teil)">Die @eId muss als Präfix vor ihrem
            lokalen Teil (hier: "<sch:value-of select="$geprüfte-eid-lokaler-teil"/>") die @eId ihres nächstgelegenen und ebenfalls über eine @eId verfügenden
            Vorgängers besitzen, verbunden durch das Zeichen ("<sch:value-of select="$trennzeichen-zwischen-eids"/>"). Erwartet wird im vorliegenden Fall damit
            konkret: "<sch:value-of select="concat($vorgänger-eid, $trennzeichen-zwischen-eids, $geprüfte-eid-lokaler-teil)"/>".</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Dereferenzierbarkeit lokaler Verweise von akn:destinations innerhalb einer akn:passiveModification</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00435"
                context="akn:passiveModifications/akn:textualMod/akn:destination/@href">
         <sch:assert id="SCH-00435-000" test="starts-with(., '#')">Die Zielangabe einer Textänderung innerhalb von "aenderungenPassiv" (also:
            akn:passiveModifications/akn:textualMod/akn:destination/@href) muss stets einen Verweis auf ein Element innerhalb des aktuellen Dokumentes
            enthalten. Solche lokalen Verweise beginnen zwingend mit einer Raute ("#").</sch:assert>
         <sch:let name="lokaler-verweis"
                  value="substring(., 2) (: das Rautesymbol des lokalen Verweises überspringen :)"/>
         <sch:let name="lokaler-verweis-ohne-zeichenbereich"
                  value="replace($lokaler-verweis, '(/\d+-\d+)$', '')"/>
         <sch:assert id="SCH-00435-010"
                     test="count(/akn:akomaNtoso//*[@eId eq $lokaler-verweis-ohne-zeichenbereich]) = 1">Verweise auf ein jeweiliges ELement
            sind innerhalb von passiveModifications grundsätzlich lokal. Zur hier angegebenen Referenz existiert jedoch im vorliegenden Dokument kein Element
            mit korrespondierender @eId ("<sch:value-of select="$lokaler-verweis-ohne-zeichenbereich"/>")!</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <!-- Lückenlose Nummerierung von eIds ohne Zählbezeichnung -->
      <sch:rule id="SCH-00440" context="*[@eId]">
         <sch:let name="kontextelement-typ" value="local-name(.)"/>
         <sch:let name="kontext-eId-ohne-ordinalzahl"
                  value="string-join(tokenize(@eId, '-')[position() lt last()], '-')"/>
         <sch:let name="kontext-eId-ordinalzahl" value="tokenize(@eId, '-')[last()]"/>
         <sch:let name="anzahl-vorgängerelemente-gleichen-typs"
                  value="count(preceding-sibling::*[local-name() eq $kontextelement-typ (: and starts-with(@eId, $kontext-eId-ohne-ordinalzahl) :)])"/>
         <sch:assert id="SCH-00440-000"
                     role="error"
                     test="if ($anzahl-vorgängerelemente-gleichen-typs gt 1) then $anzahl-vorgängerelemente-gleichen-typs eq (xs:int($kontext-eId-ordinalzahl) - 1) else true()">Als Ordinalzahl (Positionsangabe) wurde im vorliegenden Fall "<sch:value-of select="$kontext-eId-ordinalzahl"/>" angegeben.
            Da diese Positionsangaben der ELI-Kürzel im Dokument fortlaufend vergeben werden müssen, wird an dieser Stelle jedoch der ELI-URI "<sch:value-of select="concat($kontext-eId-ohne-ordinalzahl, '-', $anzahl-vorgängerelemente-gleichen-typs + 1)"/>" erwartet.</sch:assert>
         <sch:assert id="SCH-00440-005"
                     role="error"
                     test="if ($anzahl-vorgängerelemente-gleichen-typs eq 0) then (xs:int($kontext-eId-ordinalzahl) eq 1) else true()">Als Ordinalzahl (Positionsangabe) wurde im vorliegenden Fall "<sch:value-of select="$kontext-eId-ordinalzahl"/>" angegeben.
            Da es sich hierbei um das erste <sch:value-of select="local-name(.)"/>-Element innerhalb seines Elternelements handelt, wird an dieser Stelle jedoch
            als @eId "<sch:value-of select="concat($kontext-eId-ohne-ordinalzahl, '-1')"/>" erwartet.</sch:assert>
      </sch:rule>
      <!-- dokumentweite eId-uniqueness -->
      <sch:rule id="SCH-00450" context="@eId">
         <sch:let name="kontext-eId-inhalt" value="."/>
         <sch:assert id="SCH-00450-000"
                     role="error"
                     test="count(//*[@eId eq $kontext-eId-inhalt]) eq 1">Eine eId muss dokumentweit einmalig sein; eId
               "<sch:value-of select="$kontext-eId-inhalt"/>" kommt im vorliegenden Dokument jedoch <sch:value-of select="count(//*[@eId eq $kontext-eId-inhalt])"/>-mal vor!</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- dokumentweite GUID-uniqueness -->
   <sch:pattern>
      <sch:rule id="SCH-00460" context="@GUID">
         <sch:let name="kontext-guid-inhalt" value="."/>
         <sch:let name="häufigkeit-der-aktuellen-guid"
                  value="count(//*[@GUID = $kontext-guid-inhalt])"/>
         <sch:assert id="SCH-00460-000"
                     role="error"
                     test="xs:int($häufigkeit-der-aktuellen-guid) eq 1">GUIDs müssen einmalig sein; "<sch:value-of select="$kontext-guid-inhalt"/>" kommt jedoch <sch:value-of select="$häufigkeit-der-aktuellen-guid"/>-mal im Dokument vor!</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Struktureller Aufbau der ELI-Uris (Metadaten)</sch:title>
   <sch:let name="eli-präfix-entwurfsfassung" value="'eli/dl'"/>
   <sch:let name="eli-präfix-verkündungsfassung" value="'eli/bund'"/>
   <sch:let name="eli-agent-entwurf"
            value="tokenize(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRauthor/@href, '/')[last()]"/>
   <sch:let name="eli-agent-verkündung"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRname/@value"/>
   <sch:let name="eli-year"
            value="substring(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@date, 1, 4)"/>
   <sch:let name="eli-natural-identifier"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRnumber/@value"/>
   <sch:let name="eli-process-identifier" value="$eli-natural-identifier"/>
   <sch:let name="eli-type-of-legislative-process-document"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRname/@value"/>
   <sch:let name="eli-point-in-time"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRdate/@date"/>
   <sch:let name="eli-version"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRversionNumber/@value"/>
   <sch:let name="eli-language"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRlanguage/@language"/>
   <sch:let name="eli-subtype"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value"/>
   <sch:let name="eli-format"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRformat/@value"/>
   <sch:let name="eli-point-in-time-manifestation"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRdate/@date"/>
   <sch:let name="eli-subagent"
            value="tokenize(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href, '/')[last()]"/>
   <sch:let name="ist-entwurfsfassung"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@name = 'entwurfsfassung'"/>
   <sch:let name="ist-verkündungsfassung"
            value="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@name = ( 'verkuendungsfassung', 'neufassung' )"/>
   <!-- FRBRthis -->
   <sch:let name="FRBRthis-verkündungsfassung-work-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Work-Ebene'"/>
   <sch:let name="FRBRthis-verkündungsfassung-work-aufbau"
            value="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{subtype}')"/>
   <sch:let name="FRBRthis-verkündungsfassung-work-inhalt"
            value="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-subtype), '/')"/>
   <sch:let name="FRBRthis-verkündungsfassung-expression-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Expression-Ebene'"/>
   <sch:let name="FRBRthis-verkündungsfassung-expression-aufbau"
            value="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}/{subtype}')"/>
   <sch:let name="FRBRthis-verkündungsfassung-expression-inhalt"
            value="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language, $eli-subtype), '/')"/>
   <sch:let name="FRBRthis-verkündungsfassung-manifestation-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Manifestation-Ebene'"/>
   <sch:let name="FRBRthis-verkündungsfassung-manifestation-aufbau"
            value="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}/{point in time manifestation}/{subtype}.{format}')"/>
   <sch:let name="FRBRthis-verkündungsfassung-manifestation-inhalt"
            value="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language,$eli-point-in-time-manifestation, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <sch:let name="FRBRthis-entwurfsfassung-work-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Work-Ebene'"/>
   <sch:let name="FRBRthis-entwurfsfassung-work-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subtype}')"/>
   <sch:let name="FRBRthis-entwurfsfassung-work-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subtype), '/')"/>
   <sch:let name="FRBRthis-entwurfsfassung-expression-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Expression-Ebene'"/>
   <sch:let name="FRBRthis-entwurfsfassung-expression-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}')"/>
   <sch:let name="FRBRthis-entwurfsfassung-expression-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, $eli-subtype), '/')"/>
   <sch:let name="FRBRthis-entwurfsfassung-manifestation-beschreibung"
            value="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Manifestation-Ebene'"/>
   <sch:let name="FRBRthis-entwurfsfassung-manifestation-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}.{format}')"/>
   <sch:let name="FRBRthis-entwurfsfassung-manifestation-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <!-- FRBRuri -->
   <sch:let name="FRBRuri-verkündungsfassung-work-beschreibung"
            value="'Der eindeutige Bezeichner für die Work-Ebene in der Verkündungsfassung'"/>
   <sch:let name="FRBRuri-verkündungsfassung-work-aufbau"
            value="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}')"/>
   <sch:let name="FRBRuri-verkündungsfassung-work-inhalt"
            value="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier), '/')"/>
   <sch:let name="FRBRuri-verkündungsfassung-expression-beschreibung"
            value="'Der eindeutige Bezeichner für die Expression-Ebene in der Verkündungsfassung'"/>
   <sch:let name="FRBRuri-verkündungsfassung-expression-aufbau"
            value="concat($eli-präfix-verkündungsfassung, '{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}')"/>
   <sch:let name="FRBRuri-verkündungsfassung-expression-inhalt"
            value="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language), '/')"/>
   <sch:let name="FRBRuri-verkündungsfassung-manifestation-beschreibung"
            value="'Der eindeutige Bezeichner für die Manifestation-Ebene in der Verkündungsfassung'"/>
   <sch:let name="FRBRuri-verkündungsfassung-manifestation-aufbau"
            value="$FRBRthis-verkündungsfassung-manifestation-aufbau"/>
   <sch:let name="FRBRuri-verkündungsfassung-manifestation-inhalt"
            value="$FRBRthis-verkündungsfassung-manifestation-inhalt"/>
   <sch:let name="FRBRuri-entwurfsfassung-work-beschreibung"
            value="'Der eindeutige Bezeichner für die Work-Ebene in der Entwurfsfassung'"/>
   <sch:let name="FRBRuri-entwurfsfassung-work-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}')"/>
   <sch:let name="FRBRuri-entwurfsfassung-work-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document), '/')"/>
   <sch:let name="FRBRuri-entwurfsfassung-expression-beschreibung"
            value="'Der eindeutige Bezeichner für die Expression-Ebene in der Entwurfsfassung'"/>
   <sch:let name="FRBRuri-entwurfsfassung-expression-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}')"/>
   <sch:let name="FRBRuri-entwurfsfassung-expression-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language), '/')"/>
   <sch:let name="FRBRuri-entwurfsfassung-manifestation-beschreibung"
            value="'Der eindeutige Bezeichner für die Manifestation-Ebene in der Entwurfsfassung'"/>
   <sch:let name="FRBRuri-entwurfsfassung-manifestation-aufbau"
            value="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}.{format}')"/>
   <sch:let name="FRBRuri-entwurfsfassung-manifestation-inhalt"
            value="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <!-- Regeln für die Zusammensetzung der ELI-Uris -->
   <sch:pattern>
      <!-- FRBRthis: Work-Ebene -->
      <sch:rule id="SCH-00500"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis">
         <sch:assert id="SCH-00500-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-work-inhalt) else true()">
            <sch:value-of select="$FRBRthis-entwurfsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der Form
               "<sch:value-of select="$FRBRthis-entwurfsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-entwurfsfassung-work-inhalt"/>". </sch:assert>
         <sch:assert id="SCH-00500-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-work-inhalt) else true()">
            <sch:value-of select="$FRBRthis-verkündungsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<sch:value-of select="$FRBRthis-verkündungsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-verkündungsfassung-work-inhalt"/>".</sch:assert>
      </sch:rule>
      <!-- FRBRthis: Expression-Ebene -->
      <sch:rule id="SCH-00510"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis">
         <sch:assert id="SCH-00510-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-expression-inhalt) else true()">
            <sch:value-of select="$FRBRthis-entwurfsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<sch:value-of select="$FRBRthis-entwurfsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-entwurfsfassung-expression-inhalt"/>". </sch:assert>
         <sch:assert id="SCH-00510-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-expression-inhalt) else true()">
            <sch:value-of select="$FRBRthis-verkündungsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<sch:value-of select="$FRBRthis-verkündungsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-verkündungsfassung-expression-inhalt"/>".</sch:assert>
      </sch:rule>
      <!-- FRBRthis: Manifestation-Ebene -->
      <sch:rule id="SCH-00520"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis">
         <sch:assert id="SCH-00520-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-manifestation-inhalt) else true()">
            <sch:value-of select="$FRBRthis-entwurfsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<sch:value-of select="$FRBRthis-entwurfsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-entwurfsfassung-manifestation-inhalt"/>".</sch:assert>
         <sch:assert id="SCH-00520-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-manifestation-inhalt) else true()">
            <sch:value-of select="$FRBRthis-verkündungsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen
            in der Form "<sch:value-of select="$FRBRthis-verkündungsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRthis-verkündungsfassung-manifestation-inhalt"/>".</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <!-- FRBRuri: Work-Ebene -->
      <sch:rule id="SCH-00530"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRuri">
         <sch:assert id="SCH-00530-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-work-inhalt) else true()">
            <sch:value-of select="$FRBRuri-entwurfsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der Form
               "<sch:value-of select="$FRBRuri-entwurfsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-entwurfsfassung-work-inhalt"/>".</sch:assert>
         <sch:assert id="SCH-00530-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-work-inhalt) else true()">
            <sch:value-of select="$FRBRuri-verkündungsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<sch:value-of select="$FRBRuri-verkündungsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-verkündungsfassung-work-inhalt"/>".</sch:assert>
      </sch:rule>
      <!-- FRBRuri: Expression-Ebene -->
      <sch:rule id="SCH-00540"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRuri">
         <sch:assert id="SCH-00540-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-expression-inhalt) else true()">
            <sch:value-of select="$FRBRuri-entwurfsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<sch:value-of select="$FRBRuri-entwurfsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-entwurfsfassung-expression-inhalt"/>".</sch:assert>
         <sch:assert id="SCH-00540-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-expression-inhalt) else true()">
            <sch:value-of select="$FRBRuri-verkündungsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<sch:value-of select="$FRBRuri-verkündungsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-verkündungsfassung-expression-inhalt"/>".</sch:assert>
      </sch:rule>
      <!-- FRBRuri: Manifestation-Ebene -->
      <sch:rule id="SCH-00550"
                context="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRuri">
         <sch:assert id="SCH-00550-005"
                     role="error"
                     test="if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-manifestation-inhalt) else true()">
            <sch:value-of select="$FRBRuri-entwurfsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<sch:value-of select="$FRBRuri-entwurfsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-entwurfsfassung-manifestation-inhalt"/>".</sch:assert>
         <sch:assert id="SCH-00550-010"
                     role="error"
                     test="if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-manifestation-inhalt) else true()">
            <sch:value-of select="$FRBRuri-verkündungsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen
            in der Form "<sch:value-of select="$FRBRuri-verkündungsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<sch:value-of select="$FRBRuri-verkündungsfassung-manifestation-inhalt"/>".</sch:assert>
      </sch:rule>
      <!-- akn:FRBRdate/@name auf Work-Ebene ist abhängig von Verkündungs- vs. Entwurfsfassung -->
      <sch:rule id="SCH-00560" context="/akn:akomaNtoso/akn:act/@name" role="error">
         <sch:assert id="SCH-00560-005"
                     test="if (. = 'regelungstext') then $ist-verkündungsfassung else true()"> Ein Regelungstext in der Verkündungsfassung darf nicht als Entwurfsfassung gekennzeichnet sein, wie es jedoch aktuell anhand
            von akn:FRBRWork/akn:FRBRdate/@name deklariert ist.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Regeln zum korrekten ELI-Kürzel für akn:article ("para" vs. "art") abhängig von der fachlichen Konstellation 
        (Form und Typ sowie außerhalb vs. innerhalb von Änderungsbefehlen) -->
   <sch:pattern>
      <sch:rule id="SCH-00570"
                context="akn:article[not(ancestor::akn:quotedStructure)]">
         <sch:assert id="SCH-00570-000"
                     test="if (($form = $form-stammform and not($typ = ($typ-vertragsgesetz, $typ-vertragsverordnung))) or $form = $form-eingebundene-stammform) then matches(@eId, '.*para-.*$') else true()"> Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einer Stammform oder eingebundenen Stammform muss "para" lauten. </sch:assert>
         <sch:assert id="SCH-00570-005"
                     test="if ($form = $form-stammform and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)) then matches(@eId, '.*art-.*$') else true()"> Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einem Vertragsgesetz oder einer Vertragsverordnung muss "art" lauten. </sch:assert>
         <sch:assert id="SCH-00570-010"
                     test="if ($form = $form-mantelform) then matches(@eId, '.*art-.*$') else true()">Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einer Mantelform muss "art" lauten.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00580" context="akn:article[ancestor::akn:quotedStructure]">
         <sch:let name="lokaler-teil-des-eli-kürzels" value="tokenize(@eId, '_')[last()]"/>
         <sch:assert id="SCH-00580-000"
                     test="if (not(@refersTo = ($refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung))) then starts-with($lokaler-teil-des-eli-kürzels, 'para-') else starts-with($lokaler-teil-des-eli-kürzels, 'art-')"> Der lokale Teil des ELI für eine Einzelvorschrift innerhalb eines
            Änderungsbefehls (also eines &lt;akn:article&gt; mit dem Vorfahren &lt;quotedStructure&gt;) muss anhand des Kurzbezeichners "para" gebildet werden,
            es sei denn, das zu ändernde Artefakt ist ein Vertragsrechtsakt (d. h. ein Vertragsgesetz oder eine Vertragsverordnung), in welchem Fall nur "art"
            zulässig ist. Der lokale Bezeichner im vorliegenden Fall lautet "<sch:value-of select="$lokaler-teil-des-eli-kürzels"/>" in Kombination mit der
            Qualifizierung des Zielartefaktes qua "<sch:value-of select="@refersTo"/>".</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Metadaten: proprietary -->
   <sch:pattern>
      <sch:rule id="SCH-00590"
                context="akn:meta/akn:proprietary/meta:legalDocML.de_metadaten">
         <sch:assert id="SCH-00590-000"
                     subject="meta:fna"
                     test="if ($fassung = $fassung-entwurfsfassung) then (meta:fna = 'nicht-vorhanden' (: das Literal ist im Metadatenmodell als @default des einfachen Typs xs:token umgesetzt, daher hier als Literal anstatt einer dynamischen Referenzierung :)) else true()">In der Entwurfsfassung muss als Wert für den Fundstellennachweis das Literal "nicht-vorhanden" angegeben werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Regeln zur Geltungszeit -->
   <sch:pattern>
      <sch:rule id="SCH-00600" context="akn:timeInterval">
         <sch:let name="beginn-geltungszeitintervall-uri" value="@start"/>
         <sch:let name="ende-geltungszeitintervall-uri" value="@end"/>
         <sch:let name="beginn-geltungszeitintervall"
                  value="ancestor::akn:meta/akn:lifecycle/akn:eventRef[@eId = substring($beginn-geltungszeitintervall-uri, 2)]/@date"/>
         <sch:let name="ende-geltungszeitintervall"
                  value="ancestor::akn:meta/akn:lifecycle/akn:eventRef[@eId = substring($ende-geltungszeitintervall-uri, 2)]/@date"/>
         <sch:assert id="SCH-00600-000" test="not(exists(@end) and exists(@duration))">Die Attribute @end und @duration schließen einander aus: Es darf nur
            entweder eine Dauer oder ein Endzeitpunkt angegeben werden, aber nicht beides.</sch:assert>
         <sch:assert id="SCH-00600-005"
                     test="substring($beginn-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId">Der
            Verweis auf den Beginn des Geltungsintervalls muss auf eine in der vorliegenden Instanz vorhandene @eId einer Ereignis-Deklaration sein. Für den
            Verweis "<sch:value-of select="$beginn-geltungszeitintervall-uri"/>" existiert jedoch kein passendes Verweisziel.</sch:assert>
         <sch:assert id="SCH-00600-010"
                     test="if (not(empty($ende-geltungszeitintervall))) then (substring($ende-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId) else true()">Der Verweis auf das Ende des Geltungsintervalls muss auf eine in der vorliegenden Instanz vorhandene @eId einer
            Ereignis-Deklaration sein. Für den Verweis "<sch:value-of select="$ende-geltungszeitintervall-uri"/>" existiert jedoch kein passendes
            Verweisziel.</sch:assert>
         <sch:assert id="SCH-00600-015"
                     test="if (not(empty($beginn-geltungszeitintervall)) and not(empty($ende-geltungszeitintervall))) then (xs:date($beginn-geltungszeitintervall) le xs:date($ende-geltungszeitintervall)) else true()">Das Ende des Geltungszeitintervalls darf zeitlich nicht vor seinem Beginn liegen. Es sind jedoch als Beginn "<sch:value-of select="$beginn-geltungszeitintervall"/>" und als Ende "<sch:value-of select="$ende-geltungszeitintervall"/>" angegeben.</sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00610"
                context="(akn:activeModifications | akn:passiveModifications)/akn:textualMod/akn:force">
         <sch:let name="verweis-auf-geltungszeitgruppe" value="substring(@period, 2)"/>
         <sch:let name="geltungszeitgruppen-im-dokument"
                  value="/akn:akomaNtoso/*/akn:meta/akn:temporalData/akn:temporalGroup/@eId"/>
         <sch:assert id="SCH-00610-000"
                     test="$verweis-auf-geltungszeitgruppe = $geltungszeitgruppen-im-dokument">Der angegebene Wert "<sch:value-of select="concat('#', $verweis-auf-geltungszeitgruppe)"/>" ist kein gültiger Verweis auf eine Geltungszeitgruppe in diesem Dokument. <sch:value-of select="if (not(empty($geltungszeitgruppen-im-dokument))) then if (count($geltungszeitgruppen-im-dokument) gt 2) then concat(' Technisch mögliche Angaben wären im aktuellen Dokument: ', (string-join( for $geltungszeitgruppe in $geltungszeitgruppen-im-dokument[position() lt last()] return concat('&#34;#', $geltungszeitgruppe, '&#34;'), ', ')), ' oder &#34;#', $geltungszeitgruppen-im-dokument[last()], '&#34;.') else concat(' Die technisch einzig mögliche Angabe ist im aktuellen Dokument &#34;#', $geltungszeitgruppen-im-dokument, '&#34;.') else ()"/>
         </sch:assert>
      </sch:rule>
      <sch:rule id="SCH-00620" context="akn:eventRef">
         <sch:let name="ereignisart" value="@refersTo"/>
         <sch:let name="ereignisdatum" value="@date"/>
         <sch:let name="platzhalterwert-bei-unbekanntem-datum" value="'0001-01-01'"/>
         <sch:let name="literalsuffix-für-unbekanntes-datum"
                  value="'-mit-noch-unbekanntem-datum'"/>
         <sch:assert id="SCH-00620-000"
                     test="if ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) then (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) else true()">Bei Angabe eines unbekannten Ereignisdatums (durch Verwendung des Platzhaltertextes "<sch:value-of select="$platzhalterwert-bei-unbekanntem-datum"/>") muss das zur näheren Bestimmung der Ereignisart im Attribut @refersTo angegebene Literal auf
            den Ausdruck "<sch:value-of select="$literalsuffix-für-unbekanntes-datum"/>" enden.</sch:assert>
         <sch:assert id="SCH-00620-005"
                     test="if (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) then ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) else true()">Für ein Ereignis, dessen Datum noch unbekannt ist (hier: @refersTo = "<sch:value-of select="$ereignisart"/>"), muss in seinem
            Attribut @date als Platzhalterwert "<sch:value-of select="$platzhalterwert-bei-unbekanntem-datum"/>" angegeben werden.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Regeln zur Versionierung (Identifikation von Vorgänger- aktueller und Nachfolger-Version) -->
   <sch:pattern>
      <sch:rule id="SCH-00630" context="akn:FRBRExpression">
         <sch:let name="guid-vorherige-version" value="'vorherige-version-id'"/>
         <sch:let name="guid-aktuelle-version" value="'aktuelle-version-id'"/>
         <sch:let name="guid-nächste-version" value="'nachfolgende-version-id'"/>
         <sch:assert id="SCH-00630-000"
                     subject="akn:FRBRalias[@name = $guid-vorherige-version]"
                     test="count(akn:FRBRalias[@name = $guid-vorherige-version]) le 1">Es darf höchstens einen einzigen Verweis auf eine Vorgängerversion
            geben.</sch:assert>
         <sch:assert id="SCH-00630-005"
                     subject="akn:FRBRalias[@name = $guid-aktuelle-version]"
                     test="count(akn:FRBRalias[@name = $guid-aktuelle-version]) eq 1">Es muss genau einen Identifikator (GUID) für die vorliegende Version geben.</sch:assert>
         <sch:assert id="SCH-00630-015"
                     subject="akn:FRBRalias"
                     test="count(akn:FRBRalias) eq count(distinct-values(akn:FRBRalias/@value))">Sämtliche mittels
            @value angegebenen GUIDs der Versionen müssen sich voneinander unterscheiden."/&gt;</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Regeln zum Lebenszyklus -->
   <sch:pattern>
      <sch:let name="zulässige-literale-in-kombination-mit-repeal"
               value="($refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten, $refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten, $refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten-mit-unbekanntem-datum, $refersto-literal-ereignisreferenz-verkündungssfassung-ausserkrafttreten-mit-unbekanntem-datum)"/>
      <sch:rule id="SCH-00640"
                context="akn:meta/akn:lifecycle (: Entwurfs- und Verkündungsfassung sowie Neufassung :)">
         <sch:assert id="SCH-00640-010"
                     test="count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> kann nicht mehr als ein Außerkraftsetzen
            (&lt;eventRef;&gt; mit @type='<sch:value-of select="$type-literal-ereignisreferenz-repeal"/>') enthalten, da das Rechtsetzungsartefakt dadurch in
            Gänze aufgehoben wird.</sch:assert>
         <sch:assert id="SCH-00640-015"
                     test="count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1">Für die Deklaration eines Außerkrafttretens (&lt;eventRef&gt; mit
               @type='<sch:value-of select="$type-literal-ereignisreferenz-repeal"/>') ist als @refersTo-Angabe ausschließlich <sch:value-of select="string-join(distinct-values(for $literal in $zulässige-literale-in-kombination-mit-repeal return concat('''', $literal, '''')), ' oder ')"/> zulässig.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00650"
                context="akn:meta/akn:lifecycle[$fassung = $fassung-entwurfsfassung] (: Entwurfsfassung :)">
         <sch:assert id="SCH-00650-000"
                     test="if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum] and (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten])) else true()">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung muss immer
            mindestens zwei Ereignisse auszeichnen: Erstens einen Platzhalter für das Ausfertigungsdatum; dieser wird angegeben mittels &lt;eventRef&gt; mit
               @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"/>'. Und zweitens eine Angabe zum Inkrafttreten
            mittels &lt;eventRef&gt; mit @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/> und @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum"/>' bzw., sofern das Datum bereits bekannt ist,
               @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten"/>'.</sch:assert>
         <sch:assert id="SCH-00650-005"
                     test="if ($fassung = $fassung-entwurfsfassung) then (akn:eventRef[@type = ($type-literal-ereignisreferenz-generation, $type-literal-ereignisreferenz-repeal)]) else ()">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung kann nur initiale
            Ereignisse (Inkrafttreten, Ausfertigung, teilweises Außerkrafttreten, d.h. @type = '<sch:value-of select="$type-literal-ereignisreferenz-generation"/>') oder ein finales Außerkrafttreten (d.h. @type = '<sch:value-of select="$type-literal-ereignisreferenz-repeal"/>') aufweisen.</sch:assert>
         <sch:assert id="SCH-00650-010"
                     test="if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum]) = 1) else true()">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung
            muss genau einen Platzhalter für das noch unbekannte Datum der Ausfertigung enthalten (&lt;eventRef&gt; mit @type = '<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<sch:value-of select="$refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"/>').</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00660"
                context="akn:meta/akn:lifecycle[$fassung = ($fassung-verkündungsfassung, $fassung-neufassung)] (: Verkündungfassung oder Neufassung :)">
         <sch:assert id="SCH-00660-000"
                     test="akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung] and ( akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum] )">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Verkündungsfassung muss
            immer mindestens zwei Ereignisse auszeichnen: Erstens das Ausfertigungsdatum; dieses wird angegeben mittels &lt;eventRef&gt; mit
               @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>'. Und zweitens eine Angabe zum Inkrafttreten mittels
            &lt;eventRef&gt; mit @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/> und @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten"/>' bzw. bei grundsätzlichem oder abweichendem Inkrafttreten
               @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich"/>' oder
               @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend"/>'. Oder sofern das Datum noch
            unbekannt ist, @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum"/>'.</sch:assert>
         <sch:assert id="SCH-00660-005"
                     test="count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]) = 1">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Verkündungsfassung muss genau ein
            konkretes Ausfertigungsdatum enthalten (&lt;eventRef&gt; mit @type = '<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und
            @refersTo = '<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>').</sch:assert>
         <sch:let name="datum-ausfertigung"
                  value="exactly-one(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung] (: immer den ersten Wert nehmen, falls es unerwartet mehrere gibt, damit hier diejenige SCH-Regel zum Tragen kommt, die prüft, dass es nur genau ein initiales Ausfertigungsdatum gibt, und nicht ein Fehler auf Ebene des SCH-Prozessors die weitere Verarbeitung blockiert. :)[1]/@date)"/>
         <sch:let name="frühestes-datum-inkrafttreten-als-reine-ziffern"
                  value="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
         <sch:let name="datum-inkrafttreten"
                  value="if (not(empty($frühestes-datum-inkrafttreten-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
         <sch:let name="datum-ausserkafttreten"
                  value="akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten]/@date"/>
         <sch:assert id="SCH-00660-015"
                     test="if (akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten]) then (xs:date($datum-ausserkafttreten) gt xs:date($datum-ausfertigung)) else true()">Das Datum des Außerkrafttretens muss nach der Ausfertigung liegen; angegeben wurden jedoch für das Außerkrafttreten
               '<sch:value-of select="$datum-ausserkafttreten"/>' und für die Ausfertigung '<sch:value-of select="$datum-ausfertigung"/>'.</sch:assert>
         <sch:let name="frühestes-datum-amendment-als-reine-ziffern"
                  value="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-amendment (: inkl. aller möglichen @refersTo :)]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
         <sch:let name="frühestes-datum-amendment"
                  value="if (not(empty($frühestes-datum-amendment-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-amendment-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-amendment-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-amendment-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
         <sch:let name="ausfertigungsdatum"
                  value="exactly-one(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]/@date)"/>
         <sch:assert id="SCH-00660-020"
                     test="if (not(xs:date($frühestes-datum-amendment) = xs:date('0001-01-01'))) then (xs:date($frühestes-datum-amendment) gt xs:date($ausfertigungsdatum)) else true()">
            <sch:value-of select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> können erst geändert werden, nachdem er/sie
            initial ausgefertigt wurde (d. h. die früheste Änderung - akn:eventRef[@type = '<sch:value-of select="$type-literal-ereignisreferenz-amendment"/>']/@date - muss nach der initialen Ausfertigung - &lt;eventRef&gt; mit @type = '<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>' - erfolgen).</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00665"
                context="akn:meta/akn:lifecycle/akn:eventRef [$fassung = ($fassung-verkündungsfassung, $fassung-neufassung) and @type = $type-literal-ereignisreferenz-generation ]">
         <sch:assert id="SCH-00665-025"
                     test="if (@refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich) then not( preceding-sibling::akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend]/@date eq current()/@date or following-sibling::akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend]/@date eq current()/@date ) else if (@refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-abweichend) then not( preceding-sibling::akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich]/@date eq current()/@date or following-sibling::akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-grundsaetzlich]/@date eq current()/@date ) else true()">Das grundsätzliche und das abweichende Inkrafttretensdatum dürfen nicht identisch sein.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:pattern>
      <sch:rule id="SCH-00670"
                context="akn:meta/akn:lifecycle[$fassung = $fassung-neufassung and $art = $art-regelungstext] (: nur Neufassung :)">
         <sch:assert id="SCH-00670-000"
                     test="count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-neufassung]) ge 1">Ein
            Regelungstext als Neufassung muss mindestens ein Neufassungsereignis enthalten (&lt;eventRef&gt; mit @type = '<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<sch:value-of select="$refersto-literal-ereignisreferenz-neufassung"/>'.).</sch:assert>
         <sch:let name="frühestes-datum-neufassung-als-reine-ziffern"
                  value="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $fassung-neufassung]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
         <sch:let name="früheste-neufassung"
                  value="if (not(empty($frühestes-datum-neufassung-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-neufassung-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-neufassung-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-neufassung-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
         <sch:let name="ausfertigung"
                  value="akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]/@date"/>
         <sch:assert id="SCH-00670-005"
                     test="xs:date($früheste-neufassung) gt xs:date($ausfertigung)">Das Datum der frühesten Neufassung (&lt;eventRef&gt; mit
               @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @rfersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-neufassung"/>') muss nach dem initialen Ausfertigungsdatum (&lt;eventRef&gt; mit @type='<sch:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<sch:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>') liegen; angegeben wurden jedoch als Ausfertigungsdatum
               '<sch:value-of select="$ausfertigung"/>' und als Datum der Neufassung '<sch:value-of select="$früheste-neufassung"/>'.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Regeln zu Kardinalitäten von Inline-Elementen -->
   <sch:pattern>
      <sch:rule id="SCH-00680"
                context="akn:p[parent::akn:longTitle and $art = $art-regelungstext]">
         <sch:assert id="SCH-00680-000"
                     subject="parent::akn:longTitle"
                     test="count(akn:docTitle) = 1">Ein dokumentenkopfTitel (akn:longtitle) muss genau einen
            Dokumententitel (akn:docTitle) besitzen.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Sitzung (z. B. eines Ausschusses) -->
   <sch:pattern>
      <sch:rule id="SCH-00730" context="akn:session" subject="@refersTo">
         <sch:assert id="SCH-00730-000"
                     test="starts-with(@refersTo, '#') and substring(@refersTo, 2) = //akn:organization/@eId">Es muss einen lokalen Verweis
            auf eine Organisation geben, deren Sitzung ausgezeichnet wird. Dieser besteht aus einer Raute (#), gefolgt von der @eId der betreffenden
            akn:organization.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <sch:title>Verwendung von Markern</sch:title>
   <sch:pattern>
      <sch:rule id="SCH-00802" context="akn:marker[@refersTo = 'satzende']">
         <sch:assert id="SCH-00802-000" test="empty(@name)">Ein akn:marker mit @refersTo='satzende' darf kein Attribut @name haben.</sch:assert>
      </sch:rule>
   </sch:pattern>
   <!-- Einschränkungen für Attributtypen, deren Facetten nur in Verkündungsfassungen bzw. Entwurfsfassungen Gültigkeit besitzen -->
   <sch:title>Zulässigkeit von Literalen / Mustern je Attribut an FRBR-Typen, abhängig von der Fassung (Entwurf vs. Verkündung)</sch:title>
   <sch:include href="legalDocML.de-frbr-metadaten-facetten-entwurfsfassung.sch"/>
   <sch:include href="legalDocML.de-frbr-metadaten-facetten-verkündungsfassung.sch"/>
</sch:schema>
