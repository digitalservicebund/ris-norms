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
   <sch:rule id="SCH-ENTWF-hrefLiterals.expression.FRBRauthor"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href">
      <sch:assert test="if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundestag", "recht.bund.de/institution/bundesrat", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-hrefLiterals.work.FRBRauthor"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRauthor/@href">
      <sch:assert test="if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundestag", "recht.bund.de/institution/bundesrat", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-nameLiterals.expression.FRBRdate"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRdate/@name">
      <sch:assert test="if ($ist-entwurfsfassung) then (. = ('version')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt ist ausschließlich "version".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.expression.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.expression.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRExpression/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.manifestation.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.manifestation.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.work.FRBRname"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRname/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (. = ('regelungsentwurf')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt ist ausschließlich "regelungsentwurf".</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.work.FRBRnumber"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRnumber/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^[0-9]+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "[0-9]+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.work.FRBRsubtype"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^(begruendung|anschreiben|vorblattregelungstext|vorblattbeschlussempfehlung|beschlussempfehlung|bericht|aenderungsantrag|entschliessungsantrag|unterrichtung|begruendungaenderungsantrag|begruendungentschliessungsantrag|denkschrift|regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(begruendung|anschreiben|vorblattregelungstext|vorblattbeschlussempfehlung|beschlussempfehlung|bericht|aenderungsantrag|entschliessungsantrag|unterrichtung|begruendungaenderungsantrag|begruendungentschliessungsantrag|denkschrift|regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.work.FRBRthis"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRthis/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß\-]+-\d+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß\-]+-\d+" entsprechen.</sch:assert>
   </sch:rule>
   <sch:rule id="SCH-ENTWF-valueLiterals.work.FRBRuri"
             role="error"
             context="akn:identification/akn:FRBRWork/akn:FRBRuri/@value">
      <sch:assert test="if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+$')) else true()">In der Entwurfsfassung ist das Literal "<sch:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+" entsprechen.</sch:assert>
   </sch:rule>
</sch:pattern>
