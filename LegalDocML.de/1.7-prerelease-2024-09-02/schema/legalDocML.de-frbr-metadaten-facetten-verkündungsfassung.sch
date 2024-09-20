<?xml version="1.0" encoding="UTF-8"?>
<!--

Diese Datei ist Bestandteil der Schematronregeln des Standards LegalDocML.de 1.7 (irgendwann 2024).
Sie wird inkludiert von der Schematron-Hauptdatei.

                
********************************* Hinweis zur Lizensierung ***********************************
 2024 Copyright © 2021–2024 Bundesministerium des Innern und für Heimat,
 Referat DG II 6, Maßnahmen Enterprise Resource Management und Elektronische Verwaltungsarbeit

      Lizenz: CC-BY-3.0 (Creative Commons Namensnennung 3.0)
 Information: https://creativecommons.org/licenses/by/3.0/legalcode.de
**********************************************************************************************

-->
<sch:pattern xmlns:sch="http://purl.oclc.org/dsdl/schematron">
   <sch:rule id="SCH-VERKF-hrefLiterals.expression.FRBRauthor"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href">
      <sch:assert test="if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERKF-hrefLiterals.work.FRBRauthor"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRauthor/@href">
      <sch:assert test="if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERKF-nameLiterals.expression.FRBRdate"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRdate/@name">
      <sch:assert test="if ($ist-verkündungsfassung) then (. = ('verkuendung', 'aenderung', 'berichtigung')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "verkuendung", "aenderung" sowie "berichtigung".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.expression.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.expression.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.manifestation.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/\d{4}-\d{2}-\d{2}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/\d{4}-\d{2}-\d{2}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.manifestation.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/\d{4}-\d{2}-\d{2}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/\d{4}-\d{2}-\d{2}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERKF-valueLiterals.work.FRBRname"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRname/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (. = ('bgbl', 'bgbl-1', 'bgbl-2', 'banz-at')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "bgbl", "bgbl-1", "bgbl-2" sowie "banz-at".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.work.FRBRnumber"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRnumber/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^(s[0-9]+[a-zäöüß]*)|([0-9]+)$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(s[0-9]+[a-zäöüß]*)|([0-9]+)" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.work.FRBRsubtype"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^(regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.work.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/[a-zöäüß\-]+-\d+$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/[a-zöäüß\-]+-\d+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-VERK-valueLiterals.work.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)$')) else true()">In der Verkündungsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+(-[12]+)?/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)" entsprechen.</sch:assert>
   </sch:rule>
</sch:pattern>
