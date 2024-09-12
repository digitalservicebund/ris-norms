<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
               xmlns:error="https://doi.org/10.5281/zenodo.1495494#error"
               xmlns:fkt="lokale-funktionen"
               xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
               xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
               xmlns:sch="http://purl.oclc.org/dsdl/schematron"
               xmlns:schxslt="https://doi.org/10.5281/zenodo.1495494"
               xmlns:schxslt-api="https://doi.org/10.5281/zenodo.1495494#api"
               xmlns:xs="http://www.w3.org/2001/XMLSchema"
               xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               version="2.0">
   <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/"
                    xmlns:dct="http://purl.org/dc/terms/"
                    xmlns:skos="http://www.w3.org/2004/02/skos/core#">
      <dct:creator>
         <dct:Agent>
            <skos:prefLabel>SchXslt/1.9.5 SAXON/HE 12.4</skos:prefLabel>
            <schxslt.compile.typed-variables
                    xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
         </dct:Agent>
      </dct:creator>
      <dct:created>2024-02-19T09:29:44.564688421Z</dct:created>
   </rdf:Description>
   <xsl:output indent="yes"/>
   <xsl:param name="form"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:form"/>
   <xsl:param name="form-stammform" select="'stammform'"/>
   <xsl:param name="form-mantelform" select="'mantelform'"/>
   <xsl:param name="form-eingebundene-stammform" select="'eingebundene-stammform'"/>
   <xsl:param name="form-nicht-vorhanden" select="'nicht-vorhanden'"/>
   <xsl:param name="fassung"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:fassung"/>
   <xsl:param name="fassung-entwurfsfassung" select="'entwurfsfassung'"/>
   <xsl:param name="fassung-verkündungsfassung" select="'verkuendungsfassung'"/>
   <xsl:param name="fassung-neufassung" select="'neufassung'"/>
   <xsl:param name="typ"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:typ"/>
   <xsl:param name="typ-gesetz" select="'gesetz'"/>
   <xsl:param name="typ-sonstige-bekanntmachung" select="'sonstige-bekanntmachung'"/>
   <xsl:param name="typ-verordnung" select="'verordnung'"/>
   <xsl:param name="typ-vertragsgesetz" select="'vertragsgesetz'"/>
   <xsl:param name="typ-vertragsverordnung" select="'vertragsverordnung'"/>
   <xsl:param name="typ-verwaltungsvorschrift" select="'verwaltungsvorschrift'"/>
   <xsl:param name="typ-satzung" select="'satzung'"/>
   <xsl:param name="art"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:art"/>
   <xsl:param name="art-anschreiben" select="'anschreiben'"/>
   <xsl:param name="art-begründung" select="'begruendung'"/>
   <xsl:param name="art-regelungstext" select="'regelungstext'"/>
   <xsl:param name="art-vereinbarung" select="'vereinbarung'"/>
   <xsl:param name="art-vorblatt" select="'vorblatt'"/>
   <xsl:param name="art-bericht" select="'bericht'"/>
   <xsl:param name="art-bekanntmachungstext" select="'bekanntmachungstext'"/>
   <xsl:param name="initiant"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:initiant"/>
   <xsl:param name="bearbeitende-institution"
              select="/akn:akomaNtoso/*/akn:meta/akn:proprietary/meta:legalDocML.de_metadaten/meta:bearbeitendeInstitution"/>
   <xsl:param name="refersto-literal-geltungszeitregel" select="'geltungszeitregel'"/>
   <xsl:param name="refersto-literal-hauptaenderung" select="'hauptaenderung'"/>
   <xsl:param name="refersto-literal-folgeaenderung" select="'folgeaenderung'"/>
   <xsl:param name="refersto-literal-eingebundene-stammform"
              select="'eingebundene-stammform'"/>
   <xsl:param name="refersto-literal-stammform" select="'stammform'"/>
   <xsl:param name="refersto-literal-mantelform" select="'mantelform'"/>
   <xsl:param name="refersto-literal-vertragsgesetz" select="'vertragsgesetz'"/>
   <xsl:param name="refersto-literal-vertragsverordnung"
              select="'vertragsverordnung'"/>
   <xsl:param name="type-literal-ereignisreferenz-generation" select="'generation'"/>
   <xsl:param name="type-literal-ereignisreferenz-repeal" select="'repeal'"/>
   <xsl:param name="type-literal-ereignisreferenz-amendment" select="'amendment'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"
              select="'ausfertigung-mit-noch-unbekanntem-datum'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten"
              select="'inkrafttreten'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum"
              select="'inkrafttreten-mit-noch-unbekanntem-datum'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten"
              select="'ausserkrafttreten'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten-mit-unbekanntem-datum"
              select="'ausserkrafttreten-mit-noch-unbekanntem-datum'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"
              select="'ausfertigung'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten"
              select="'inkrafttreten'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum"
              select="'inkrafttreten-mit-noch-unbekanntem-datum'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten"
              select="'ausserkrafttreten'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-verkündungssfassung-ausserkrafttreten-mit-unbekanntem-datum"
              select="'ausserkrafttreten-mit-noch-unbekanntem-datum'"/>
   <xsl:param name="refersto-literal-ereignisreferenz-neufassung"
              select="'neufassung'"/>
   <xsl:param name="dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"
              select="'Ein Regelungstext, ein Bekanntmachungstext oder eine Vereinbarung'"/>
   <xsl:param name="refersto-literal-vorblattabschnitt-erfüllungsaufwand"
              select="'vorblattabschnitt-erfuellungsaufwand'"/>
   <xsl:param name="eli-präfix-entwurfsfassung" select="'eli/dl'"/>
   <xsl:param name="eli-präfix-verkündungsfassung" select="'eli/bund'"/>
   <xsl:param name="eli-agent-entwurf"
              select="tokenize(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRauthor/@href, '/')[last()]"/>
   <xsl:param name="eli-agent-verkündung"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRname/@value"/>
   <xsl:param name="eli-year"
              select="substring(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@date, 1, 4)"/>
   <xsl:param name="eli-natural-identifier"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRnumber/@value"/>
   <xsl:param name="eli-process-identifier" select="$eli-natural-identifier"/>
   <xsl:param name="eli-type-of-legislative-process-document"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRname/@value"/>
   <xsl:param name="eli-point-in-time"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRdate/@date"/>
   <xsl:param name="eli-version"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRversionNumber/@value"/>
   <xsl:param name="eli-language"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRlanguage/@language"/>
   <xsl:param name="eli-subtype"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value"/>
   <xsl:param name="eli-format"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRformat/@value"/>
   <xsl:param name="eli-subagent"
              select="tokenize(/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href, '/')[last()]"/>
   <xsl:param name="ist-entwurfsfassung"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@name = 'entwurfsfassung'"/>
   <xsl:param name="ist-verkündungsfassung"
              select="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRdate/@name = ( 'verkuendungsfassung', 'neufassung' )"/>
   <xsl:param name="FRBRthis-verkündungsfassung-work-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Work-Ebene'"/>
   <xsl:param name="FRBRthis-verkündungsfassung-work-aufbau"
              select="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{subtype}')"/>
   <xsl:param name="FRBRthis-verkündungsfassung-work-inhalt"
              select="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-subtype), '/')"/>
   <xsl:param name="FRBRthis-verkündungsfassung-expression-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Expression-Ebene'"/>
   <xsl:param name="FRBRthis-verkündungsfassung-expression-aufbau"
              select="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}/{subtype}')"/>
   <xsl:param name="FRBRthis-verkündungsfassung-expression-inhalt"
              select="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language, $eli-subtype), '/')"/>
   <xsl:param name="FRBRthis-verkündungsfassung-manifestation-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Verkündungsfassung auf der Manifestation-Ebene'"/>
   <xsl:param name="FRBRthis-verkündungsfassung-manifestation-aufbau"
              select="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}/{subtype}.{format}')"/>
   <xsl:param name="FRBRthis-verkündungsfassung-manifestation-inhalt"
              select="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-work-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Work-Ebene'"/>
   <xsl:param name="FRBRthis-entwurfsfassung-work-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subtype}')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-work-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subtype), '/')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-expression-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Expression-Ebene'"/>
   <xsl:param name="FRBRthis-entwurfsfassung-expression-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-expression-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, $eli-subtype), '/')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-manifestation-beschreibung"
              select="'Der eindeutige Bezeichner für Teildokumente in der Entwurfsfassung auf der Manifestation-Ebene'"/>
   <xsl:param name="FRBRthis-entwurfsfassung-manifestation-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}.{format}')"/>
   <xsl:param name="FRBRthis-entwurfsfassung-manifestation-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <xsl:param name="FRBRuri-verkündungsfassung-work-beschreibung"
              select="'Der eindeutige Bezeichner für die Work-Ebene in der Verkündungsfassung'"/>
   <xsl:param name="FRBRuri-verkündungsfassung-work-aufbau"
              select="concat($eli-präfix-verkündungsfassung, '/{agent}/{year}/{natural identifier}')"/>
   <xsl:param name="FRBRuri-verkündungsfassung-work-inhalt"
              select="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier), '/')"/>
   <xsl:param name="FRBRuri-verkündungsfassung-expression-beschreibung"
              select="'Der eindeutige Bezeichner für die Expression-Ebene in der Verkündungsfassung'"/>
   <xsl:param name="FRBRuri-verkündungsfassung-expression-aufbau"
              select="concat($eli-präfix-verkündungsfassung, '{agent}/{year}/{natural identifier}/{point in time}/{version}/{language}')"/>
   <xsl:param name="FRBRuri-verkündungsfassung-expression-inhalt"
              select="string-join(($eli-präfix-verkündungsfassung, $eli-agent-verkündung, $eli-year, $eli-natural-identifier, $eli-point-in-time, $eli-version, $eli-language), '/')"/>
   <xsl:param name="FRBRuri-verkündungsfassung-manifestation-beschreibung"
              select="'Der eindeutige Bezeichner für die Manifestation-Ebene in der Verkündungsfassung'"/>
   <xsl:param name="FRBRuri-verkündungsfassung-manifestation-aufbau"
              select="$FRBRthis-verkündungsfassung-manifestation-aufbau"/>
   <xsl:param name="FRBRuri-verkündungsfassung-manifestation-inhalt"
              select="$FRBRthis-verkündungsfassung-manifestation-inhalt"/>
   <xsl:param name="FRBRuri-entwurfsfassung-work-beschreibung"
              select="'Der eindeutige Bezeichner für die Work-Ebene in der Entwurfsfassung'"/>
   <xsl:param name="FRBRuri-entwurfsfassung-work-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}')"/>
   <xsl:param name="FRBRuri-entwurfsfassung-work-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document), '/')"/>
   <xsl:param name="FRBRuri-entwurfsfassung-expression-beschreibung"
              select="'Der eindeutige Bezeichner für die Expression-Ebene in der Entwurfsfassung'"/>
   <xsl:param name="FRBRuri-entwurfsfassung-expression-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}')"/>
   <xsl:param name="FRBRuri-entwurfsfassung-expression-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language), '/')"/>
   <xsl:param name="FRBRuri-entwurfsfassung-manifestation-beschreibung"
              select="'Der eindeutige Bezeichner für die Manifestation-Ebene in der Entwurfsfassung'"/>
   <xsl:param name="FRBRuri-entwurfsfassung-manifestation-aufbau"
              select="concat($eli-präfix-entwurfsfassung, '/{year}/{agent}/{process identifier}/{type of legislative process document}/{subagent}/{point in time}/{version}/{language}/{subtype}.{format}')"/>
   <xsl:param name="FRBRuri-entwurfsfassung-manifestation-inhalt"
              select="string-join(($eli-präfix-entwurfsfassung, $eli-year, $eli-agent-entwurf, $eli-process-identifier, $eli-type-of-legislative-process-document, $eli-subagent, $eli-point-in-time, $eli-version, $eli-language, concat($eli-subtype, '.', $eli-format)), '/')"/>
   <xsl:variable name="zulässige-literale-in-kombination-mit-repeal"
                 select="($refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten, $refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten, $refersto-literal-ereignisreferenz-entwurfsfassung-ausserkrafttreten-mit-unbekanntem-datum, $refersto-literal-ereignisreferenz-verkündungssfassung-ausserkrafttreten-mit-unbekanntem-datum)"/>
   <xsl:template match="root()">
      <xsl:variable name="metadata" as="element()?">
         <svrl:metadata xmlns:dct="http://purl.org/dc/terms/"
                        xmlns:skos="http://www.w3.org/2004/02/skos/core#"
                        xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
            <dct:creator>
               <dct:Agent>
                  <skos:prefLabel>
                     <xsl:value-of separator="/"
                                   select="(system-property('xsl:product-name'), system-property('xsl:product-version'))"/>
                  </skos:prefLabel>
               </dct:Agent>
            </dct:creator>
            <dct:created>
               <xsl:value-of select="current-dateTime()"/>
            </dct:created>
            <dct:source>
               <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/">
                  <dct:creator>
                     <dct:Agent>
                        <skos:prefLabel>SchXslt/1.9.5 SAXON/HE 12.4</skos:prefLabel>
                        <schxslt.compile.typed-variables
                                xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
                     </dct:Agent>
                  </dct:creator>
                  <dct:created>2024-02-19T09:29:44.564688421Z</dct:created>
               </rdf:Description>
            </dct:source>
         </svrl:metadata>
      </xsl:variable>
      <xsl:variable name="report" as="element(schxslt:report)">
         <schxslt:report>
            <xsl:call-template name="d14e147"/>
         </schxslt:report>
      </xsl:variable>
      <xsl:variable name="schxslt:report" as="node()*">
         <xsl:sequence select="$metadata"/>
         <xsl:for-each select="$report/schxslt:document">
            <xsl:for-each select="schxslt:pattern">
               <xsl:sequence select="node()"/>
               <xsl:sequence select="../schxslt:rule[@pattern = current()/@id]/node()"/>
            </xsl:for-each>
         </xsl:for-each>
      </xsl:variable>
      <svrl:schematron-output xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                              schemaVersion="LegalDocML.de 1.6 (Dezember 2023)"
                              title="Regelungstext Entwurfsfassung Regelungstext Verkündungsfassung/Neufassung Regelungstext Allgemein Klasse Inhaltsverzeichnis Klasse: Hauptteil Aufzählungen Gliederungsebenen Schlussteil Vorblatt (Regelungstext) Begründung Prüfkriterien Prüfkriterien Allgemeiner Teil Regelungsfolgen Begründung zu einem Vertragsrechtsakt Anschreiben Ausschussüberweisung/Legislaturperiode/Drucksachennummer Vereinbarung Änderungsbefehle Struktureller Aufbau von eId-Textknoten Struktureller Aufbau der ELI-Uris (Metadaten) Zulässigkeit von Literalen / Mustern je Attribut an FRBR-Typen, abhängig von der Fassung (Entwurf vs. Verkündung)">
         <svrl:ns-prefix-in-attribute-values prefix="fkt" uri="lokale-funktionen"/>
         <svrl:ns-prefix-in-attribute-values prefix="akn" uri="http://Inhaltsdaten.LegalDocML.de/1.6/"/>
         <svrl:ns-prefix-in-attribute-values prefix="meta" uri="http://Metadaten.LegalDocML.de/1.6/"/>
         <xsl:sequence select="$schxslt:report"/>
      </svrl:schematron-output>
   </xsl:template>
   <xsl:template match="text() | @*" mode="#all" priority="-10"/>
   <xsl:template match="/" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template match="*" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="@*"/>
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template name="d14e147">
      <schxslt:document>
         <schxslt:pattern id="d14e147">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e191">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e239">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e251">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e263">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e278">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e287">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e296">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e305">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e317">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e331">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e340">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e358">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e367">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e378">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e432">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e453">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e508">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e541">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e565">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e595">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e612">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e624">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e641">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e655">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e665">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e674">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e689">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e707">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e736">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e776">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e914">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e987">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1070">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1099">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1111">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1188">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1215">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1237">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1278">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1352">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1389">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1400">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1414">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d14e1515">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <xsl:apply-templates mode="d14e147" select="root()"/>
      </schxslt:document>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $form = $form-eingebundene-stammform]"
                 priority="94"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e147']">
            <schxslt:rule pattern="d14e147">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00019 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $form = $form-eingebundene-stammform]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00019">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e147">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00019">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preface/akn:block))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00019-000">
                     <xsl:attribute name="test">not(./akn:preface/akn:block)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keinen
            Datumscontainer innerhalb des Dokumentenkopfes enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00019-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keine
            Eingangsformel enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00019-010">
                     <xsl:attribute name="test">not(./akn:conclusions)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keinen Schlussteil
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e147')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]"
           priority="93"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e147']">
            <schxslt:rule pattern="d14e147">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00020 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00020">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e147">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00020">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:preamble/akn:formula)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00020-005">
                     <xsl:attribute name="test">./akn:preamble/akn:formula</xsl:attribute>
                     <svrl:text>Für ein Gesetz muss eine Eingangsformel verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00020-010">
                     <xsl:attribute name="test">not(./akn:conclusions/akn:formula)</xsl:attribute>
                     <svrl:text>Für ein Gesetz in der Entwurfsfassung wird in der Regel keine
            Schlussformel benutzt.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e147')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]"
           priority="92"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e147']">
            <schxslt:rule pattern="d14e147">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00030 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00030">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e147">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00030">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00030-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Eine Verordnung darf keine Eingangsformel enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00030-010">
                     <xsl:attribute name="test">not(./akn:conclusions/akn:formula)</xsl:attribute>
                     <svrl:text>Für eine Verordnung in der Entwurfsfassung wird in der Regel keine
            Schlussformel benutzt.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e147')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]"
                 priority="91"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e147']">
            <schxslt:rule pattern="d14e147">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00040 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00040">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e147">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00040">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00040-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Eine Verwaltungsvorschrift darf keine Eingangsformel
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00040-010">
                     <xsl:attribute name="test">not(./akn:conclusions/akn:formula)</xsl:attribute>
                     <svrl:text>Für eine Verwaltungsvorschrift in der Entwurfsfassung wird in der
            Regel keine Schlussformel benutzt.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e147')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $form = $form-eingebundene-stammform]"
                 priority="90"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e191']">
            <schxslt:rule pattern="d14e191">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00048 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $form = $form-eingebundene-stammform]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00048">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e191">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00048">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preface/akn:block))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00048-000">
                     <xsl:attribute name="test">not(./akn:preface/akn:block)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keinen
            Datums-Container innerhalb des Dokumentenkopfes enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00048-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keine
            Eingangsformel enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00048-010">
                     <xsl:attribute name="test">not(./akn:conclusions)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform in einer Mantelform darf keinen Schlussteil
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e191')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-neufassung]"
                 priority="89"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e191']">
            <schxslt:rule pattern="d14e191">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00049 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-neufassung]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00049">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-neufassung]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e191">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00049">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-neufassung]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00049-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in der Neufassung darf keine Eingangsformel
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:conclusions))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00049-010">
                     <xsl:attribute name="test">not(./akn:conclusions)</xsl:attribute>
                     <svrl:text>Ein Regelungstext in der Neufassung darf keine Schlussformel
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not($form = $form-stammform)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00049-015">
                     <xsl:attribute name="test">$form = $form-stammform</xsl:attribute>
                     <svrl:text>Ein Regelungstext als Neufassung darf nur in einer Stammform
            vorkommen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(./akn:preface/akn:block))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00049-020">
                     <xsl:attribute name="test">not(./akn:preface/akn:block)</xsl:attribute>
                     <svrl:text>Für einen Regelungstext in der Neufassung darf kein Datums-Container
            innerhalb des Dokumentenkopfes verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e191')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]"
           priority="88"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e191']">
            <schxslt:rule pattern="d14e191">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00050 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00050">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e191">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00050">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:preamble/akn:formula)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00050-005">
                     <xsl:attribute name="test">./akn:preamble/akn:formula</xsl:attribute>
                     <svrl:text>Für ein Gesetz muss eine Eingangsformel verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e191')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]"
           priority="87"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e191']">
            <schxslt:rule pattern="d14e191">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00060 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00060">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e191">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00060">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00060-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Eine Verordnung darf keine Eingangsformel enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e191')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]"
                 priority="86"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e191']">
            <schxslt:rule pattern="d14e191">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00070 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00070">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e191">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00070">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = $typ-verwaltungsvorschrift]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:preamble/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00070-005">
                     <xsl:attribute name="test">not(./akn:preamble/akn:formula)</xsl:attribute>
                     <svrl:text>Eine Verwaltungsvorschrift darf keine Eingangsformel
            enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e191')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-verkündungsfassung]"
                 priority="85"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e239']">
            <schxslt:rule pattern="d14e239">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00071 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-verkündungsfassung]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00071">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-verkündungsfassung]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e239">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00071">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $fassung = $fassung-verkündungsfassung]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:preface/akn:block)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00071-005">
                     <xsl:attribute name="test">./akn:preface/akn:block</xsl:attribute>
                     <svrl:text>Für einen Regelungstext in der Verkündungsfassung muss ein Datums-Container
            innerhalb des Dokumentenkopfes verwendet werden. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(./akn:conclusions/akn:blockContainer)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00071-010">
                     <xsl:attribute name="test">./akn:conclusions/akn:blockContainer</xsl:attribute>
                     <svrl:text>Für einen Regelungstext in der Verkündungsfassung muss ein
            Signaturblock verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e239')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz) and not($fassung = $fassung-neufassung)]"
           priority="84"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e251']">
            <schxslt:rule pattern="d14e251">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00072 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz) and not($fassung = $fassung-neufassung)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00072">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz) and not($fassung = $fassung-neufassung)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e251">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00072">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext and $typ = ($typ-gesetz) and not($fassung = $fassung-neufassung)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(exists(./akn:conclusions/akn:formula))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00072-005">
                     <xsl:attribute name="test">exists(./akn:conclusions/akn:formula)</xsl:attribute>
                     <svrl:text>Für einen Regelungstext in der Verkündungsfassung muss eine
            Schlussformel verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e251')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]/akn:preamble"
           priority="83"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e263']">
            <schxslt:rule pattern="d14e263">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00100 for context "/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]/akn:preamble" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00100">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]/akn:preamble</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e263">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00100">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-gesetz, $typ-vertragsgesetz)]/akn:preamble</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(./akn:citations))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00100-005">
                     <xsl:attribute name="test">not(./akn:citations)</xsl:attribute>
                     <svrl:text>Ein Gesetz darf keine Ermächtigungsnorm enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e263')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung) and not($fassung = $fassung-neufassung)]/akn:preamble"
           priority="82"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e263']">
            <schxslt:rule pattern="d14e263">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00110 for context "/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung) and not($fassung = $fassung-neufassung)]/akn:preamble" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00110">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung) and not($fassung = $fassung-neufassung)]/akn:preamble</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e263">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00110">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*[@name = $art-regelungstext and $typ = ($typ-verordnung, $typ-vertragsverordnung) and not($fassung = $fassung-neufassung)]/akn:preamble</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:citations)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00110-005">
                     <xsl:attribute name="test">./akn:citations</xsl:attribute>
                     <svrl:text>Eine Verordnung muss Ermächtigungsnormen bereitstellen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e263')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:quotedStructure//akn:article [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]"
           priority="81"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e278']">
            <schxslt:rule pattern="d14e278">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00120 for context "akn:quotedStructure//akn:article [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00120">
                  <xsl:attribute
                          name="context">akn:quotedStructure//akn:article [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e278">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00120">
                  <xsl:attribute
                          name="context">akn:quotedStructure//akn:article [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not((@refersTo = ($refersto-literal-mantelform, $refersto-literal-stammform, $refersto-literal-geltungszeitregel, $refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00120-000">
                     <xsl:attribute
                             name="test">(@refersTo = ($refersto-literal-mantelform, $refersto-literal-stammform, $refersto-literal-geltungszeitregel, $refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung))</xsl:attribute>
                     <svrl:text>Wird innerhalb eines Änderungsbefehls eine Einzelvorschrift in Gänze geändert, neugefasst, hinzugefügt oder gelöscht, so muss diese näher - als
            Mantelform oder Stammform, als Geltungszeitregel oder als Vertragsgesetz bzw. Vertragsverordnung - bestimmt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e278')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:article[not(ancestor::akn:quotedStructure)]/@refersTo [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]"
           priority="80"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e287']">
            <schxslt:rule pattern="d14e287">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00121 for context "akn:article[not(ancestor::akn:quotedStructure)]/@refersTo [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00121">
                  <xsl:attribute
                          name="context">akn:article[not(ancestor::akn:quotedStructure)]/@refersTo [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e287">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00121">
                  <xsl:attribute
                          name="context">akn:article[not(ancestor::akn:quotedStructure)]/@refersTo [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-eingebundene-stammform, $refersto-literal-geltungszeitregel))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00121-000">
                     <xsl:attribute
                             name="test">. = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-eingebundene-stammform, $refersto-literal-geltungszeitregel)</xsl:attribute>
                     <svrl:text>Liegt ein Regelungstext in Mantelform vor und seine Einzelvorschriften sind mittels @refersTo näher bestimmt, so dürfen lediglich folgende Literale
            verwendet werden: "hauptaenderung", "folgeaenderung", "eingebundene-stammform", "geltungszeitregel".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e287')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:article/@refersTo [ $form = $form-stammform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]"
           priority="79"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e296']">
            <schxslt:rule pattern="d14e296">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00122 for context "akn:article/@refersTo [ $form = $form-stammform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00122">
                  <xsl:attribute
                          name="context">akn:article/@refersTo [ $form = $form-stammform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e296">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00122">
                  <xsl:attribute
                          name="context">akn:article/@refersTo [ $form = $form-stammform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = $refersto-literal-geltungszeitregel and count(//akn:article/@refersTo) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00122-000">
                     <xsl:attribute
                             name="test">. = $refersto-literal-geltungszeitregel and count(//akn:article/@refersTo) = 1</xsl:attribute>
                     <svrl:text>Ein Regelungstext in Stammform
            darf als nähere Bestimmung seiner Einzelvorschriften (mittels @refersTo) höchstens genau eine Geltungszeitregel und keine sonstigen
            besitzen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e296')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:article[descendant::akn:mod and not(ancestor::akn:quotedStructure)] [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]"
           priority="78"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e305']">
            <schxslt:rule pattern="d14e305">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00123 for context "akn:article[descendant::akn:mod and not(ancestor::akn:quotedStructure)] [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00123">
                  <xsl:attribute
                          name="context">akn:article[descendant::akn:mod and not(ancestor::akn:quotedStructure)] [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e305">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00123">
                  <xsl:attribute
                          name="context">akn:article[descendant::akn:mod and not(ancestor::akn:quotedStructure)] [ $form = $form-mantelform and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-verwaltungsvorschrift, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung) ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(@refersTo = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-geltungszeitregel))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00123-000">
                     <xsl:attribute
                             name="test">@refersTo = ($refersto-literal-hauptaenderung, $refersto-literal-folgeaenderung, $refersto-literal-geltungszeitregel)</xsl:attribute>
                     <svrl:text>Eine Einzelvorschrift,
            die einen Änderungsbefehl beinhaltet, muss entweder als Hauptänderung, Folgeänderung oder als Geltungszeit ausgezeichnet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e305')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:preamble"
                 priority="77"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e317']">
            <schxslt:rule pattern="d14e317">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00130 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:preamble" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00130">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:preamble</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e317">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00130">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:preamble</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="count(./akn:blockContainer[@refersTo = 'inhaltsuebersicht']) gt 1">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00130-005">
                     <xsl:attribute
                             name="test">count(./akn:blockContainer[@refersTo = 'inhaltsuebersicht']) gt 1</xsl:attribute>
                     <svrl:text>Es darf maximal eine
            Inhaltsübersicht geben.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
               <xsl:if test="count(./akn:blockContainer[@refersTo = 'anlagenuebersicht']) gt 1">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00130-010">
                     <xsl:attribute
                             name="test">count(./akn:blockContainer[@refersTo = 'anlagenuebersicht']) gt 1</xsl:attribute>
                     <svrl:text>Es darf maximal eine
            Anlagenübersicht geben.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e317')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:body[$form = $form-mantelform]"
                 priority="76"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e331']">
            <schxslt:rule pattern="d14e331">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00150 for context "/akn:akomaNtoso/*/akn:body[$form = $form-mantelform]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00150">
                  <xsl:attribute name="context">/akn:akomaNtoso/*/akn:body[$form = $form-mantelform]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e331">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00150">
                  <xsl:attribute name="context">/akn:akomaNtoso/*/akn:body[$form = $form-mantelform]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00150-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle</xsl:attribute>
                     <svrl:text>Der
            Hauptteil einer Mantelform wird nicht in weitere Gliederungsabschnitte untergliedert.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e331')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/*/akn:body[ $form = ($form-mantelform, $form-stammform) and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]"
           priority="75"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e340']">
            <schxslt:rule pattern="d14e340">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00160 for context "/akn:akomaNtoso/*/akn:body[ $form = ($form-mantelform, $form-stammform) and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00160">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:body[ $form = ($form-mantelform, $form-stammform) and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e340">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00160">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:body[ $form = ($form-mantelform, $form-stammform) and $typ = ($typ-gesetz, $typ-verordnung, $typ-satzung, $typ-vertragsgesetz, $typ-vertragsverordnung) and $fassung = ($fassung-entwurfsfassung, $fassung-verkündungsfassung, $fassung-neufassung) ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00160-010">
                     <xsl:attribute
                             name="test">count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 1</xsl:attribute>
                     <svrl:text>Innerhalb eines Regelungstextes (sowohl in der Entwurfs- als auch der Verkündungsfassung), der in Stamm- oder Mantelform vorliegt,
            muss es genau eine Einzelvorschrift bezüglich der Geltungszeit geben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e340')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:body[$form = $form-eingebundene-stammform]"
                 priority="74"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e340']">
            <schxslt:rule pattern="d14e340">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00170 for context "/akn:akomaNtoso/*/akn:body[$form = $form-eingebundene-stammform]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00170">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:body[$form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e340">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00170">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:body[$form = $form-eingebundene-stammform]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($form = $form-eingebundene-stammform) then (count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 0) else ())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00170-000">
                     <xsl:attribute
                             name="test">if ($form = $form-eingebundene-stammform) then (count(//akn:article[@refersTo = $refersto-literal-geltungszeitregel]) = 0) else ()</xsl:attribute>
                     <svrl:text>Ein Regelungstext als eingebundene Stammform darf keine Einzelvorschrift bezüglich der Geltungszeit besitzen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e340')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:article/akn:paragraph//akn:list"
                 priority="73"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e358']">
            <schxslt:rule pattern="d14e358">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00200 for context "//akn:article/akn:paragraph//akn:list" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00200">
                  <xsl:attribute name="context">//akn:article/akn:paragraph//akn:list</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e358">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00200">
                  <xsl:attribute name="context">//akn:article/akn:paragraph//akn:list</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="count(ancestor::akn:list) &gt; 4">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="warn"
                                          id="SCH-00200-005">
                     <xsl:attribute name="test">count(ancestor::akn:list) &gt; 4</xsl:attribute>
                     <svrl:text>Es ist maximal eine Vierfachuntergliederung von Sätzen
            erlaubt.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e358')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:article" priority="72" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e367']">
            <schxslt:rule pattern="d14e367">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00210 for context "//akn:article" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00210">
                  <xsl:attribute name="context">//akn:article</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e367">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00210">
                  <xsl:attribute name="context">//akn:article</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="count(./akn:paragraph) &gt; 6">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="warn"
                                          id="SCH-00210-005">
                     <xsl:attribute name="test">count(./akn:paragraph) &gt; 6</xsl:attribute>
                     <svrl:text>Es ist maximal eine Sechsfachuntergliederung in Absätzen
            erlaubt.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e367')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:book" priority="71" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00230 for context "//akn:book" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00230">
                  <xsl:attribute name="context">//akn:book</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00230">
                  <xsl:attribute name="context">//akn:book</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00230-005">
                     <xsl:attribute name="test">./akn:book</xsl:attribute>
                     <svrl:text> Innerhalb eines Gliederungsabschnitts "Buch" darf diese Gliederungsebene nicht verwendet
            werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:part" priority="70" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00240 for context "//akn:part" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00240">
                  <xsl:attribute name="context">//akn:part</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00240">
                  <xsl:attribute name="context">//akn:part</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00240-005">
                     <xsl:attribute name="test">./akn:book or ./akn:part</xsl:attribute>
                     <svrl:text> Innerhalb eines Gliederungsabschnitts "Teil" darf diese Gliederungsebene
            nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:chapter" priority="69" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00250 for context "//akn:chapter" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00250">
                  <xsl:attribute name="context">//akn:chapter</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00250">
                  <xsl:attribute name="context">//akn:chapter</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00250-005">
                     <xsl:attribute name="test">./akn:book or ./akn:part or ./akn:chapter</xsl:attribute>
                     <svrl:text> Innerhalb eines Gliederungsabschnitts "Kapitel" darf
            diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:subchapter" priority="68" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00260 for context "//akn:subchapter" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00260">
                  <xsl:attribute name="context">//akn:subchapter</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00260">
                  <xsl:attribute name="context">//akn:subchapter</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00260-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter</xsl:attribute>
                     <svrl:text> Innerhalb eines Gliederungsabschnitts
            "Unterkapitel" darf diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:section" priority="67" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00270 for context "//akn:section" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00270">
                  <xsl:attribute name="context">//akn:section</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00270">
                  <xsl:attribute name="context">//akn:section</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00270-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section</xsl:attribute>
                     <svrl:text> Innerhalb eines
            Gliederungsabschnitts "Abschnitt" darf diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:subsection" priority="66" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00280 for context "//akn:subsection" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00280">
                  <xsl:attribute name="context">//akn:subsection</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00280">
                  <xsl:attribute name="context">//akn:subsection</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00280-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection</xsl:attribute>
                     <svrl:text>
            Innerhalb eines Gliederungsabschnitts "Unterabschnitt" darf diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:title" priority="65" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00290 for context "//akn:title" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00290">
                  <xsl:attribute name="context">//akn:title</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00290">
                  <xsl:attribute name="context">//akn:title</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00290-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title</xsl:attribute>
                     <svrl:text>Innerhalb eines
            Gliederungsabschnitts "Titel" darf diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:subtitle" priority="64" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e378']">
            <schxslt:rule pattern="d14e378">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00300 for context "//akn:subtitle" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00300">
                  <xsl:attribute name="context">//akn:subtitle</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e378">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00300">
                  <xsl:attribute name="context">//akn:subtitle</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00300-005">
                     <xsl:attribute
                             name="test">./akn:book or ./akn:part or ./akn:chapter or ./akn:subchapter or ./akn:section or ./akn:subsection or ./akn:title or ./akn:subtitle</xsl:attribute>
                     <svrl:text>Innerhalb
            eines Gliederungsabschnitts "Untertitel" darf diese Gliederungsebene nicht verwendet werden.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e378')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:conclusions"
                 priority="63"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e432']">
            <schxslt:rule pattern="d14e432">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00310 for context "/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:conclusions" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00310">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:conclusions</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e432">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00310">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:bill[@name = $art-regelungstext]/akn:conclusions</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:blockContainer and $bearbeitende-institution = 'bundesregierung'">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00310-005">
                     <xsl:attribute
                             name="test">./akn:blockContainer and $bearbeitende-institution = 'bundesregierung'</xsl:attribute>
                     <svrl:text>Der Signaturblock steht nur dem
            Bundestag in der Entwurfsfassung optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
               <xsl:if test="./akn:blockContainer and $bearbeitende-institution = 'bundesrat'">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00310-010">
                     <xsl:attribute
                             name="test">./akn:blockContainer and $bearbeitende-institution = 'bundesrat'</xsl:attribute>
                     <svrl:text>Der Signaturblock steht nur dem Bundestag in der Entwurfsfassung optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e432')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-regelungstext]/akn:conclusions/akn:formula"
                 priority="62"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e432']">
            <schxslt:rule pattern="d14e432">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00320 for context "/akn:akomaNtoso/akn:act[@name = $art-regelungstext]/akn:conclusions/akn:formula" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00320">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext]/akn:conclusions/akn:formula</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e432">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00320">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-regelungstext]/akn:conclusions/akn:formula</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:p)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00320-005">
                     <xsl:attribute name="test">./akn:p</xsl:attribute>
                     <svrl:text>Wenn eine Schlussformel in einem verkündeten Regelungstext verwendet wird, dann muss
            mindestens ein Schlusssatz verwendet werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e432')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:doc[@name = $art-vorblatt]/akn:mainBody"
                 priority="61"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e453']">
            <schxslt:rule pattern="d14e453">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00330 for context "//akn:doc[@name = $art-vorblatt]/akn:mainBody" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00330">
                  <xsl:attribute name="context">//akn:doc[@name = $art-vorblatt]/akn:mainBody</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e453">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00330">
                  <xsl:attribute name="context">//akn:doc[@name = $art-vorblatt]/akn:mainBody</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-problem-und-ziel']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-005">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-problem-und-ziel'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Problem und Ziel' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-loesung']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-010">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-loesung'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass ein Vorblattabschnitt 'Lösung' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-alternativen']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-015">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-alternativen'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass ein Vorblattabschnitt 'Alternativen' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-020">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-haushaltsausgaben-ohne-erfuellungsaufwand'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass ein Vorblattabschnitt 'Haushaltsausgaben ohne Erfüllungsaufwand' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-025">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Erfüllungsaufwand' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00330-030">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand Für die Wirtschaft' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft']/akn:tblock[@refersTo = 'davon-buerokratiekosten-aus-informationspflichten']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00330-035">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-die-wirtschaft']/akn:tblock[@refersTo = 'davon-buerokratiekosten-aus-informationspflichten'])</xsl:attribute>
                     <svrl:text>Es wird empfohlen, dass innerhalb des Erfüllungsaufwands für die Wirtschaft ein Unterabschnitt 'Davon Bürokratiekosten aus Informationspflichten'
            verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-buergerinnen-und-buerger']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00330-040">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-fuer-buergerinnen-und-buerger'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand Für Bürgerinnen und Bürger' verwendet
            wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-der-verwaltung']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00330-045">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-erfuellungsaufwand']//akn:tblock[@refersTo = 'erfuellungsaufwand-der-verwaltung'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb des Erfüllungsaufwands ein Unterabschnitt 'Erfüllungsaufwand der Verwaltung' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-weitere-kosten']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00330-050">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'vorblattabschnitt-weitere-kosten'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Vorblattabschnitt 'Weitere Kosten' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e453')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="//akn:doc[@name = $art-vorblatt]/akn:mainBody/akn:hcontainer[@refersTo = $refersto-literal-vorblattabschnitt-erfüllungsaufwand]//akn:tblock"
           priority="60"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="zulässige-literale-zum-erfüllungsaufwand"
                    select="( 'erfuellungsaufwand-fuer-buergerinnen-und-buerger', 'erfuellungsaufwand-fuer-die-wirtschaft', 'davon-buerokratiekosten-aus-informationspflichten', 'erfuellungsaufwand-der-verwaltung' )"/>
      <xsl:variable name="mehr-als-ein-literal"
                    select="count($zulässige-literale-zum-erfüllungsaufwand)"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e453']">
            <schxslt:rule pattern="d14e453">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00335 for context "//akn:doc[@name = $art-vorblatt]/akn:mainBody/akn:hcontainer[@refersTo = $refersto-literal-vorblattabschnitt-erfüllungsaufwand]//akn:tblock" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00335">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-vorblatt]/akn:mainBody/akn:hcontainer[@refersTo = $refersto-literal-vorblattabschnitt-erfüllungsaufwand]//akn:tblock</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e453">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00335">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-vorblatt]/akn:mainBody/akn:hcontainer[@refersTo = $refersto-literal-vorblattabschnitt-erfüllungsaufwand]//akn:tblock</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zum-erfüllungsaufwand) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00335-000">
                     <xsl:attribute
                             name="test">if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zum-erfüllungsaufwand) else true()</xsl:attribute>
                     <svrl:text> Im Kontext eines Vorblattabschnitts zum Erfüllungsaufwand kann das Literal '<xsl:value-of
                             select="@refersTo"/>' nicht
            verwendet werden; zulässig <xsl:value-of
                                 select="if (xs:boolean($mehr-als-ein-literal)) then 'sind' else 'ist'"/> hier ausschließlich <xsl:value-of
                                 select="if (xs:boolean($mehr-als-ein-literal)) then concat(string-join( for $literal in $zulässige-literale-zum-erfüllungsaufwand[position() lt last()] return concat('''', $literal, ''''), ', '), ' oder ', concat('''', $zulässige-literale-zum-erfüllungsaufwand[last()], '''') ) else concat('''', exactly-one($zulässige-literale-zum-erfüllungsaufwand), '''')"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e453')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-gesetz, $typ-verordnung, $typ-verwaltungsvorschrift)]"
           priority="59"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e508']">
            <schxslt:rule pattern="d14e508">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00340 for context "/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-gesetz, $typ-verordnung, $typ-verwaltungsvorschrift)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00340">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-gesetz, $typ-verordnung, $typ-verwaltungsvorschrift)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e508">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00340">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-gesetz, $typ-verordnung, $typ-verwaltungsvorschrift)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil'])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00340-005">
                     <xsl:attribute
                             name="test">./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']</xsl:attribute>
                     <svrl:text> Es muss ein
            allgemeiner Teil der Begründung vorliegen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil'])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00340-010">
                     <xsl:attribute
                             name="test">./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil']</xsl:attribute>
                     <svrl:text> Es muss ein
            besonderer Teil der Begründung vorliegen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="./akn:conclusions">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00340-015">
                     <xsl:attribute name="test">./akn:conclusions</xsl:attribute>
                     <svrl:text> Eine Schlussbemerkung wird nur innerhalb einer Begründung von Vertragsrechtsakten
            benutzt.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e508')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="//akn:doc[@name = $art-begründung]/akn:mainBody//akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']//akn:tblock"
           priority="58"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="zulässige-literale-zu-regelungsfolgen"
                    select="( 'begruendung-erfuellungsaufwand-fuer-buergerinnen-und-buerger', 'begruendung-erfuellungsaufwand-fuer-die-wirtschaft', 'begruendung-erfuellungsaufwand-der-verwaltung', 'regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung', 'regelungsfolgen-abschnitt-nachhaltigkeitsaspekte', 'regelungsfolgen-abschnitt-erfuellungsaufwand', 'regelungsfolgen-abschnitt-weitere-kosten', 'regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung', 'regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand', 'regelungsfolgen-abschnitt-weitere-regelungsfolgen' )"/>
      <xsl:variable name="mehr-als-ein-literal"
                    select="count($zulässige-literale-zu-regelungsfolgen)"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e508']">
            <schxslt:rule pattern="d14e508">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00345 for context "//akn:doc[@name = $art-begründung]/akn:mainBody//akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']//akn:tblock" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00345">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody//akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']//akn:tblock</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e508">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00345">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody//akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']//akn:tblock</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zu-regelungsfolgen) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00345-000">
                     <xsl:attribute
                             name="test">if (exists(@refersTo)) then (@refersTo = $zulässige-literale-zu-regelungsfolgen) else true()</xsl:attribute>
                     <svrl:text> Im Kontext eines Begründungsabschnitt zu Regelungsfolgen kann das Literal '<xsl:value-of
                             select="@refersTo"/>' nicht
            verwendet werden; zulässig <xsl:value-of
                                 select="if (xs:boolean($mehr-als-ein-literal)) then 'sind' else 'ist'"/> hier ausschließlich <xsl:value-of
                                 select="if (xs:boolean($mehr-als-ein-literal)) then concat(string-join( for $literal in $zulässige-literale-zu-regelungsfolgen[position() lt last()] return concat('''', $literal, ''''), ', '), ' oder ', concat('''', $zulässige-literale-zu-regelungsfolgen[last()], '''') ) else concat('''', exactly-one($zulässige-literale-zu-regelungsfolgen), '''')"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e508')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']"
           priority="57"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e541']">
            <schxslt:rule pattern="d14e541">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00350 for context "//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00350">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e541">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00350">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-zielsetzung-und-notwendigkeit']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00350-005">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-zielsetzung-und-notwendigkeit'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass ein Begründungsabschnitt 'Zielsetzung und Notwendigkeit' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-wesentlicher-inhalt']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00350-010">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-wesentlicher-inhalt'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Wesentlicher Inhalt' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-alternativen']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00350-015">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-alternativen'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass ein Begründungsabschnitt 'Alternativen' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungskompetenz']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00350-020">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungskompetenz'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Regelungskompetenz' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00350-025">
                     <xsl:attribute
                             name="test">exists(./akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen'])</xsl:attribute>
                     <svrl:text> Es
            wird empfohlen, dass ein Begründungsabschnitt 'Regelungsfolgen' oder 'Gesetzesfolgen' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e541')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']/akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']/akn:content"
           priority="56"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e565']">
            <schxslt:rule pattern="d14e565">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00360 for context "//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']/akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']/akn:content" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00360">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']/akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']/akn:content</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e565">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00360">
                  <xsl:attribute
                          name="context">//akn:doc[@name = $art-begründung]/akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-allgemeiner-teil']/akn:hcontainer[@refersTo = 'begruendungsabschnitt-regelungsfolgen']/akn:content</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-005">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-rechts-und-verwaltungsvereinfachung'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Rechts und Verwaltungsvereinfachung' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-nachhaltigkeitsaspekte']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-010">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-nachhaltigkeitsaspekte'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Nachhaltigkeitsaspekte' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-erfuellungsaufwand']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-015">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-erfuellungsaufwand'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Erfuellungsaufwand' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-kosten']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-020">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-kosten'])</xsl:attribute>
                     <svrl:text> Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Weitere Kosten' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-025">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-gleichstellungspolitische-relevanzpruefung'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Gleichstellungspolitische Relevanzprüfung' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-030">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-haushaltsausgaben-ohne-erfuellungsaufwand'])</xsl:attribute>
                     <svrl:text> Es wird empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Haushaltsausgaben ohne Erfüllungsaufwand' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-regelungsfolgen']))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="warn"
                                      id="SCH-00360-035">
                     <xsl:attribute
                             name="test">exists(./akn:tblock[@refersTo = 'regelungsfolgen-abschnitt-weitere-regelungsfolgen'])</xsl:attribute>
                     <svrl:text>Es wird
            empfohlen, dass innerhalb der Regelungsfolgen ein Abschnitt 'Weitere Regelungsfolgen' verwendet wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e565')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)]"
           priority="55"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e595']">
            <schxslt:rule pattern="d14e595">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00370 for context "/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00370">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e595">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00370">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = $art-begründung and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(./akn:mainBody/akn:hcontainer) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00370-005">
                     <xsl:attribute name="test">count(./akn:mainBody/akn:hcontainer) = 1</xsl:attribute>
                     <svrl:text> Eine Begründung zu einem Vertragsrechtsakt besitzt genau
            einen Begründungsabschnitt.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil'])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00370-010">
                     <xsl:attribute
                             name="test">./akn:mainBody/akn:hcontainer[@refersTo = 'begruendung-besonderer-teil']</xsl:attribute>
                     <svrl:text> Eine Begründung zu
            einem Vertragsrechtsakt besitzt einen Begründungsabschnitt 'Besonderer Teil'.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(./akn:conclusions)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00370-015">
                     <xsl:attribute name="test">./akn:conclusions</xsl:attribute>
                     <svrl:text> Eine Begründung zu einem Vertragsrechtsakt besitzt eine
            Schlussbemerkung.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e595')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:doc[@name = $art-anschreiben]"
                 priority="54"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e612']">
            <schxslt:rule pattern="d14e612">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00380 for context "/akn:akomaNtoso/akn:doc[@name = $art-anschreiben]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00380">
                  <xsl:attribute name="context">/akn:akomaNtoso/akn:doc[@name = $art-anschreiben]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e612">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00380">
                  <xsl:attribute name="context">/akn:akomaNtoso/akn:doc[@name = $art-anschreiben]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="$bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' ) and ./akn:mainBody/akn:p/akn:date[@refersTo = 'fristablauf-datum']">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00380-005">
                     <xsl:attribute
                             name="test">$bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' ) and ./akn:mainBody/akn:p/akn:date[@refersTo = 'fristablauf-datum']</xsl:attribute>
                     <svrl:text> Das Fristablaufsdatum steht
            nur dem Bundesrat optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e612')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:doc[@name = ($art-anschreiben, $art-vorblatt)]/akn:preface/akn:p"
                 priority="53"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e624']">
            <schxslt:rule pattern="d14e624">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00390 for context "/akn:akomaNtoso/akn:doc[@name = ($art-anschreiben, $art-vorblatt)]/akn:preface/akn:p" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00390">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = ($art-anschreiben, $art-vorblatt)]/akn:preface/akn:p</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e624">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00390">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:doc[@name = ($art-anschreiben, $art-vorblatt)]/akn:preface/akn:p</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="./akn:inline and $bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00390-005">
                     <xsl:attribute
                             name="test">./akn:inline and $bearbeitende-institution = ( 'bundesregierung', 'bundestag', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )</xsl:attribute>
                     <svrl:text> Eine Ausschussüberweisung steht nur dem Bundesrat optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
               <xsl:if test="./akn:legislature and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00390-015">
                     <xsl:attribute
                             name="test">./akn:legislature and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )</xsl:attribute>
                     <svrl:text> Die Legislaturperiode steht nur dem Bundestag/Bundesrat optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
               <xsl:if test="./akn:docNumber and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )">
                  <svrl:successful-report xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                          location="{schxslt:location(.)}"
                                          role="error"
                                          id="SCH-00390-020">
                     <xsl:attribute
                             name="test">./akn:docNumber and $bearbeitende-institution = ( 'bundesregierung', 'bundeskanzler', 'bundespräsident', 'nicht-vorhanden' )</xsl:attribute>
                     <svrl:text> Die Drucksachennummer steht nur dem Bundestag/Bundesrat optional zur Verfügung.</svrl:text>
                  </svrl:successful-report>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e624')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act[@name = $art-vereinbarung]/akn:body"
                 priority="52"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e641']">
            <schxslt:rule pattern="d14e641">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00400 for context "/akn:akomaNtoso/akn:act[@name = $art-vereinbarung]/akn:body" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00400">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-vereinbarung]/akn:body</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e641">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00400">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/akn:act[@name = $art-vereinbarung]/akn:body</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(@refersTo = ( 'notenwechsel', 'vertrag', 'fakultativprotokoll' ))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00400-005">
                     <xsl:attribute
                             name="test">@refersTo = ( 'notenwechsel', 'vertrag', 'fakultativprotokoll' )</xsl:attribute>
                     <svrl:text> Der Hauptteil einer Vereinbarung muss entweder ein Notenwechsel, ein Vertrag oder ein Fakultativprotokoll sein. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(./akn:hcontainer[@refersTo = 'verbindliche-sprachfassung']) &gt;= 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00400-010">
                     <xsl:attribute
                             name="test">count(./akn:hcontainer[@refersTo = 'verbindliche-sprachfassung']) &gt;= 1</xsl:attribute>
                     <svrl:text> Es
            muss mindestens eine verbindliche Sprachfassung existieren. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e641')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:textualMod[@type = 'insertion']"
                 priority="51"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e655']">
            <schxslt:rule pattern="d14e655">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00420 for context "//akn:textualMod[@type = 'insertion']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00420">
                  <xsl:attribute name="context">//akn:textualMod[@type = 'insertion']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e655">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00420">
                  <xsl:attribute name="context">//akn:textualMod[@type = 'insertion']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(./akn:destination/@pos)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00420-005">
                     <xsl:attribute name="test">./akn:destination/@pos</xsl:attribute>
                     <svrl:text>Wenn ein Änderungsbefehl eine Einfügung beinhaltet, muss in seinen Metadaten
            eine Positionsangabe mittels @pos angegeben werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e655')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:article [ $form = ($form-stammform, $form-eingebundene-stammform) or $fassung = $fassung-neufassung or $typ = $typ-sonstige-bekanntmachung ]"
           priority="50"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e665']">
            <schxslt:rule pattern="d14e665">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00421 for context "akn:article [ $form = ($form-stammform, $form-eingebundene-stammform) or $fassung = $fassung-neufassung or $typ = $typ-sonstige-bekanntmachung ]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00421">
                  <xsl:attribute
                          name="context">akn:article [ $form = ($form-stammform, $form-eingebundene-stammform) or $fassung = $fassung-neufassung or $typ = $typ-sonstige-bekanntmachung ]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e665">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00421">
                  <xsl:attribute
                          name="context">akn:article [ $form = ($form-stammform, $form-eingebundene-stammform) or $fassung = $fassung-neufassung or $typ = $typ-sonstige-bekanntmachung ]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(exists(descendant::akn:mod)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00421-000">
                     <xsl:attribute name="test">not(exists(descendant::akn:mod))</xsl:attribute>
                     <svrl:text>Änderungsbefehle dürfen nur im Rahmen einer Mantelform vorkommen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e665')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//akn:mod[not(ancestor::akn:mod)]"
                 priority="49"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="alle-textänderungen"
                    select="/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/(akn:textualMod, akn:forceMod)/akn:source"/>
      <xsl:variable name="gesuchte-änderungsbefehl-id" select="concat('#', @eId)"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e674']">
            <schxslt:rule pattern="d14e674">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00422 for context "//akn:mod[not(ancestor::akn:mod)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00422">
                  <xsl:attribute name="context">//akn:mod[not(ancestor::akn:mod)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e674">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00422">
                  <xsl:attribute name="context">//akn:mod[not(ancestor::akn:mod)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count($alle-textänderungen[@href = $gesuchte-änderungsbefehl-id]) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00422-000">
                     <xsl:attribute
                             name="test">count($alle-textänderungen[@href = $gesuchte-änderungsbefehl-id]) = 1</xsl:attribute>
                     <svrl:text>Zu jedem Änderungsbefehl im Hauptteil
            (akn:mod) muss es im Metadatenblock genau eine zugehörige Text- oder Geltungszeitänderung geben. Es existiert jedoch keine solche Änderung
            (akn:textualMod bzw. akn:forceMod), deren Quellenangabe "<xsl:value-of
                                 select="$gesuchte-änderungsbefehl-id"/>" referenziert.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e674')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:textualMod/akn:source | /akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:forceMod/akn:source"
           priority="48"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="referenzierte-änderungsbefehl-id"
                    select="substring(@href, string-length('#') + 1)"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e689']">
            <schxslt:rule pattern="d14e689">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00423 for context "/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:textualMod/akn:source | /akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:forceMod/akn:source" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00423">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:textualMod/akn:source | /akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:forceMod/akn:source</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e689">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00423">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:textualMod/akn:source | /akn:akomaNtoso/*/akn:meta/akn:analysis/akn:activeModifications/akn:forceMod/akn:source</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(//akn:mod[@eId = $referenzierte-änderungsbefehl-id]) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00423-000">
                     <xsl:attribute
                             name="test">count(//akn:mod[@eId = $referenzierte-änderungsbefehl-id]) = 1</xsl:attribute>
                     <svrl:text>Zu jeder im Metadatenblock deklarierten
            Textänderung (akn:textualMod) oder Geltungszeitänderung (akn:forceMod) muss es im Hauptteil genau einen zugehörigen Änderungsbefehl (akn:mod) geben.
            Es existiert jedoch kein solcher Änderungsbefehl, dessen @eId "<xsl:value-of
                                 select="$referenzierte-änderungsbefehl-id"/>" lautet.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(starts-with(@href, '#'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00423-010">
                     <xsl:attribute name="test">starts-with(@href, '#')</xsl:attribute>
                     <svrl:text> Die Referenz auf die Quelle eines Änderungsbefehls innerhalb von akn:activeModifications
            ist stets ein interner Verweis; sie muss deshalb eine Raute ("#") als erstes Zeichen besitzen. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e689')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="/akn:akomaNtoso/(akn:act | akn:bill | akn:doc | akn:statement | akn:documentCollection)/*[@eId]//*[@eId]"
           priority="47"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="trennzeichen-zwischen-eids" select="'_'"/>
      <xsl:variable name="geprüfte-eid" select="@eId"/>
      <xsl:variable name="geprüfte-eid-lokaler-teil"
                    select="tokenize(@eId, $trennzeichen-zwischen-eids)[last()]"/>
      <xsl:variable name="vorgänger-eid" select="ancestor::*[@eId and position() = 1]/@eId"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e707']">
            <schxslt:rule pattern="d14e707">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00430 for context "/akn:akomaNtoso/(akn:act | akn:bill | akn:doc | akn:statement | akn:documentCollection)/*[@eId]//*[@eId]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00430">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/(akn:act | akn:bill | akn:doc | akn:statement | akn:documentCollection)/*[@eId]//*[@eId]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e707">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00430">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/(akn:act | akn:bill | akn:doc | akn:statement | akn:documentCollection)/*[@eId]//*[@eId]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not($geprüfte-eid = concat($vorgänger-eid, $trennzeichen-zwischen-eids, $geprüfte-eid-lokaler-teil))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00430-000">
                     <xsl:attribute
                             name="test">$geprüfte-eid = concat($vorgänger-eid, $trennzeichen-zwischen-eids, $geprüfte-eid-lokaler-teil)</xsl:attribute>
                     <svrl:text>Die @eId muss als Präfix vor ihrem
            lokalen Teil (hier: "<xsl:value-of select="$geprüfte-eid-lokaler-teil"/>") die @eId ihres nächstgelegenen und ebenfalls über eine @eId verfügenden
            Vorgängers besitzen, verbunden durch das Zeichen ("<xsl:value-of select="$trennzeichen-zwischen-eids"/>"). Erwartet wird im vorliegenden Fall damit
            konkret: "<xsl:value-of
                                 select="concat($vorgänger-eid, $trennzeichen-zwischen-eids, $geprüfte-eid-lokaler-teil)"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (child::akn:num) then $geprüfte-eid-lokaler-teil = concat(substring-before($geprüfte-eid-lokaler-teil, '-'), '-', akn:num/akn:marker/@name) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00430-005">
                     <xsl:attribute
                             name="test">if (child::akn:num) then $geprüfte-eid-lokaler-teil = concat(substring-before($geprüfte-eid-lokaler-teil, '-'), '-', akn:num/akn:marker/@name) else true()</xsl:attribute>
                     <svrl:text>Für Elemente, die eine Art- und Zählbezeichnung besitzen, muss die Positionsangabe ihrer @eId aus der Zählbezeichnung
            gebildet werden; konkret wird im vorliegenden Fall als lokaler Teil "<xsl:value-of
                                 select="concat(substring-before($geprüfte-eid-lokaler-teil, '-'), '-', akn:num/akn:marker/@name)"/>" erwartet.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e707')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="*[not(local-name() = 'li' and parent::akn:ol) and @eId and not(akn:num)]"
                 priority="46"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="kontextelement-typ" select="local-name(.)"/>
      <xsl:variable name="kontext-eId-ohne-ordinalzahl"
                    select="string-join(tokenize(@eId, '-')[position() lt last()], '-')"/>
      <xsl:variable name="kontext-eId-ordinalzahl" select="tokenize(@eId, '-')[last()]"/>
      <xsl:variable name="anzahl-vorgängerelemente-gleichen-typs"
                    select="count(preceding-sibling::*[not(akn:num) and local-name() eq $kontextelement-typ and starts-with(@eId, $kontext-eId-ohne-ordinalzahl)])"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e736']">
            <schxslt:rule pattern="d14e736">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00440 for context "*[not(local-name() = 'li' and parent::akn:ol) and @eId and not(akn:num)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00440">
                  <xsl:attribute
                          name="context">*[not(local-name() = 'li' and parent::akn:ol) and @eId and not(akn:num)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e736">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00440">
                  <xsl:attribute
                          name="context">*[not(local-name() = 'li' and parent::akn:ol) and @eId and not(akn:num)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($anzahl-vorgängerelemente-gleichen-typs gt 1) then $anzahl-vorgängerelemente-gleichen-typs eq (xs:int($kontext-eId-ordinalzahl) - 1) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00440-000">
                     <xsl:attribute
                             name="test">if ($anzahl-vorgängerelemente-gleichen-typs gt 1) then $anzahl-vorgängerelemente-gleichen-typs eq (xs:int($kontext-eId-ordinalzahl) - 1) else true()</xsl:attribute>
                     <svrl:text>Als Ordinalzahl (Positionsangabe) wurde im vorliegenden Fall "<xsl:value-of
                             select="$kontext-eId-ordinalzahl"/>" angegeben.
            Da diese Positionsangaben der ELI-Kürzel im Dokument fortlaufend vergeben werden müssen, wird an dieser Stelle jedoch der ELI-URI "<xsl:value-of
                                 select="concat($kontext-eId-ohne-ordinalzahl, '-', $anzahl-vorgängerelemente-gleichen-typs + 1)"/>" erwartet.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e736')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="@eId" priority="45" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="kontext-eId-inhalt" select="."/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e736']">
            <schxslt:rule pattern="d14e736">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00450 for context "@eId" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00450">
                  <xsl:attribute name="context">@eId</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e736">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00450">
                  <xsl:attribute name="context">@eId</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(//*[@eId eq $kontext-eId-inhalt]) eq 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00450-000">
                     <xsl:attribute name="test">count(//*[@eId eq $kontext-eId-inhalt]) eq 1</xsl:attribute>
                     <svrl:text>Eine eId muss dokumentweit einmalig sein; eId
               "<xsl:value-of select="$kontext-eId-inhalt"/>" kommt im vorliegenden Dokument jedoch <xsl:value-of
                                 select="count(//*[@eId eq $kontext-eId-inhalt])"/>-mal vor!</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e736')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="@GUID" priority="44" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="kontext-guid-inhalt" select="."/>
      <xsl:variable name="häufigkeit-der-aktuellen-guid"
                    select="count(//*[@GUID = $kontext-guid-inhalt])"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e776']">
            <schxslt:rule pattern="d14e776">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00460 for context "@GUID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00460">
                  <xsl:attribute name="context">@GUID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e776">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00460">
                  <xsl:attribute name="context">@GUID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(xs:int($häufigkeit-der-aktuellen-guid) eq 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00460-000">
                     <xsl:attribute name="test">xs:int($häufigkeit-der-aktuellen-guid) eq 1</xsl:attribute>
                     <svrl:text>GUIDs müssen einmalig sein; "<xsl:value-of select="$kontext-guid-inhalt"/>" kommt jedoch <xsl:value-of
                             select="$häufigkeit-der-aktuellen-guid"/>-mal im Dokument vor!</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e776')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis"
                 priority="43"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e914']">
            <schxslt:rule pattern="d14e914">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00500 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00500">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e914">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00500">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-work-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00500-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-work-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-entwurfsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der Form
               "<xsl:value-of
                             select="$FRBRthis-entwurfsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-entwurfsfassung-work-inhalt"/>". </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-work-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00500-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-work-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-verkündungsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<xsl:value-of
                             select="$FRBRthis-verkündungsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-verkündungsfassung-work-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e914')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis"
                 priority="42"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e914']">
            <schxslt:rule pattern="d14e914">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00510 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00510">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e914">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00510">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-expression-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00510-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-expression-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-entwurfsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<xsl:value-of
                             select="$FRBRthis-entwurfsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-entwurfsfassung-expression-inhalt"/>". </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-expression-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00510-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-expression-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-verkündungsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<xsl:value-of select="$FRBRthis-verkündungsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-verkündungsfassung-expression-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e914')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis"
                 priority="41"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e914']">
            <schxslt:rule pattern="d14e914">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00520 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00520">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e914">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00520">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-manifestation-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00520-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRthis-entwurfsfassung-manifestation-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-entwurfsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<xsl:value-of select="$FRBRthis-entwurfsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-entwurfsfassung-manifestation-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-manifestation-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00520-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRthis-verkündungsfassung-manifestation-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRthis-verkündungsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen
            in der Form "<xsl:value-of select="$FRBRthis-verkündungsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRthis-verkündungsfassung-manifestation-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e914')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRuri"
                 priority="40"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e987']">
            <schxslt:rule pattern="d14e987">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00530 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRuri" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00530">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRuri</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e987">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00530">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRuri</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-work-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00530-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-work-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-entwurfsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der Form
               "<xsl:value-of
                             select="$FRBRuri-entwurfsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-entwurfsfassung-work-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-work-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00530-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-work-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-verkündungsfassung-work-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<xsl:value-of
                             select="$FRBRuri-verkündungsfassung-work-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-verkündungsfassung-work-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e987')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRuri"
                 priority="39"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e987']">
            <schxslt:rule pattern="d14e987">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00540 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRuri" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00540">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRuri</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e987">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00540">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRuri</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-expression-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00540-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-expression-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-entwurfsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in der
            Form "<xsl:value-of
                             select="$FRBRuri-entwurfsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-entwurfsfassung-expression-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-expression-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00540-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-expression-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-verkündungsfassung-expression-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<xsl:value-of select="$FRBRuri-verkündungsfassung-expression-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-verkündungsfassung-expression-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e987')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRuri"
                 priority="38"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e987']">
            <schxslt:rule pattern="d14e987">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00550 for context "/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRuri" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00550">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRuri</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e987">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00550">
                  <xsl:attribute
                          name="context">/akn:akomaNtoso/*/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRuri</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-manifestation-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00550-005">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (@value = $FRBRuri-entwurfsfassung-manifestation-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-entwurfsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen in
            der Form "<xsl:value-of select="$FRBRuri-entwurfsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-entwurfsfassung-manifestation-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-manifestation-inhalt) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      role="error"
                                      id="SCH-00550-010">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (@value = $FRBRuri-verkündungsfassung-manifestation-inhalt) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of select="$FRBRuri-verkündungsfassung-manifestation-beschreibung"/> muss sich aus den Inhalten der jeweiligen Metadaten zusammensetzen
            in der Form "<xsl:value-of select="$FRBRuri-verkündungsfassung-manifestation-aufbau"/>". Erwartet würde hier konkret: "<xsl:value-of
                             select="$FRBRuri-verkündungsfassung-manifestation-inhalt"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e987')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/akn:akomaNtoso/akn:act/@name" priority="37" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e987']">
            <schxslt:rule pattern="d14e987">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00560 for context "/akn:akomaNtoso/akn:act/@name" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00560" role="error">
                  <xsl:attribute name="context">/akn:akomaNtoso/akn:act/@name</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e987">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00560" role="error">
                  <xsl:attribute name="context">/akn:akomaNtoso/akn:act/@name</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if (. = 'regelungstext') then $ist-verkündungsfassung else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00560-005">
                     <xsl:attribute
                             name="test">if (. = 'regelungstext') then $ist-verkündungsfassung else true()</xsl:attribute>
                     <svrl:text> Ein Regelungstext in der Verkündungsfassung darf nicht als Entwurfsfassung gekennzeichnet sein, wie es jedoch aktuell anhand
            von akn:FRBRWork/akn:FRBRdate/@name deklariert ist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e987')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:article[not(ancestor::akn:quotedStructure)]"
                 priority="36"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1070']">
            <schxslt:rule pattern="d14e1070">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00570 for context "akn:article[not(ancestor::akn:quotedStructure)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00570">
                  <xsl:attribute name="context">akn:article[not(ancestor::akn:quotedStructure)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1070">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00570">
                  <xsl:attribute name="context">akn:article[not(ancestor::akn:quotedStructure)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if (($form = $form-stammform and not($typ = ($typ-vertragsgesetz, $typ-vertragsverordnung))) or $form = $form-eingebundene-stammform) then matches(@eId, '.*para-.*$') else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00570-000">
                     <xsl:attribute
                             name="test">if (($form = $form-stammform and not($typ = ($typ-vertragsgesetz, $typ-vertragsverordnung))) or $form = $form-eingebundene-stammform) then matches(@eId, '.*para-.*$') else true()</xsl:attribute>
                     <svrl:text> Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einer Stammform oder eingebundenen Stammform muss "para" lauten. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($form = $form-stammform and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)) then matches(@eId, '.*art-.*$') else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00570-005">
                     <xsl:attribute
                             name="test">if ($form = $form-stammform and $typ = ($typ-vertragsgesetz, $typ-vertragsverordnung)) then matches(@eId, '.*art-.*$') else true()</xsl:attribute>
                     <svrl:text> Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einem Vertragsgesetz oder einer Vertragsverordnung muss "art" lauten. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($form = $form-mantelform) then matches(@eId, '.*art-.*$') else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00570-010">
                     <xsl:attribute
                             name="test">if ($form = $form-mantelform) then matches(@eId, '.*art-.*$') else true()</xsl:attribute>
                     <svrl:text>Der ELI-Kurzbezeichner für &lt;akn:article&gt; in einer Mantelform muss "art" lauten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1070')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:article[ancestor::akn:quotedStructure]"
                 priority="35"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="lokaler-teil-des-eli-kürzels"
                    select="tokenize(@eId, '_')[last()]"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1070']">
            <schxslt:rule pattern="d14e1070">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00580 for context "akn:article[ancestor::akn:quotedStructure]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00580">
                  <xsl:attribute name="context">akn:article[ancestor::akn:quotedStructure]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1070">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00580">
                  <xsl:attribute name="context">akn:article[ancestor::akn:quotedStructure]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if (not(@refersTo = ($refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung))) then starts-with($lokaler-teil-des-eli-kürzels, 'para-') else starts-with($lokaler-teil-des-eli-kürzels, 'art-'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00580-000">
                     <xsl:attribute
                             name="test">if (not(@refersTo = ($refersto-literal-vertragsgesetz, $refersto-literal-vertragsverordnung))) then starts-with($lokaler-teil-des-eli-kürzels, 'para-') else starts-with($lokaler-teil-des-eli-kürzels, 'art-')</xsl:attribute>
                     <svrl:text> Der lokale Teil des ELI für eine Einzelvorschrift innerhalb eines
            Änderungsbefehls (also eines &lt;akn:article&gt; mit dem Vorfahren &lt;quotedStructure&gt;) muss anhand des Kurzbezeichners "para" gebildet werden,
            es sei denn, das zu ändernde Artefakt ist ein Vertragsrechtsakt (d. h. ein Vertragsgesetz oder eine Vertragsverordnung), in welchem Fall nur "art"
            zulässig ist. Der lokale Bezeichner im vorliegenden Fall lautet "<xsl:value-of
                                 select="$lokaler-teil-des-eli-kürzels"/>" in Kombination mit der
            Qualifizierung des Zielartefaktes qua "<xsl:value-of select="@refersTo"/>".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1070')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:meta/akn:proprietary/meta:legalDocML.de_metadaten"
                 priority="34"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1099']">
            <schxslt:rule pattern="d14e1099">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00590 for context "akn:meta/akn:proprietary/meta:legalDocML.de_metadaten" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00590">
                  <xsl:attribute name="context">akn:meta/akn:proprietary/meta:legalDocML.de_metadaten</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1099">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00590">
                  <xsl:attribute name="context">akn:meta/akn:proprietary/meta:legalDocML.de_metadaten</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($fassung = $fassung-entwurfsfassung) then (meta:fna = 'nicht-vorhanden' (: das Literal ist im Metadatenmodell als @default des einfachen Typs xs:token umgesetzt, daher hier als Literal anstatt einer dynamischen Referenzierung :)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(meta:fna)}"
                                      id="SCH-00590-000">
                     <xsl:attribute
                             name="test">if ($fassung = $fassung-entwurfsfassung) then (meta:fna = 'nicht-vorhanden' (: das Literal ist im Metadatenmodell als @default des einfachen Typs xs:token umgesetzt, daher hier als Literal anstatt einer dynamischen Referenzierung :)) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung muss als Wert für den Fundstellennachweis das Literal "nicht-vorhanden" angegeben werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1099')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:timeInterval" priority="33" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="beginn-geltungszeitintervall-uri" select="@start"/>
      <xsl:variable name="ende-geltungszeitintervall-uri" select="@end"/>
      <xsl:variable name="beginn-geltungszeitintervall"
                    select="ancestor::akn:meta/akn:lifecycle/akn:eventRef[@eId = substring($beginn-geltungszeitintervall-uri, 2)]/@date"/>
      <xsl:variable name="ende-geltungszeitintervall"
                    select="ancestor::akn:meta/akn:lifecycle/akn:eventRef[@eId = substring($ende-geltungszeitintervall-uri, 2)]/@date"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1111']">
            <schxslt:rule pattern="d14e1111">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00600 for context "akn:timeInterval" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00600">
                  <xsl:attribute name="context">akn:timeInterval</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1111">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00600">
                  <xsl:attribute name="context">akn:timeInterval</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(not(exists(@end) and exists(@duration)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00600-000">
                     <xsl:attribute name="test">not(exists(@end) and exists(@duration))</xsl:attribute>
                     <svrl:text>Die Attribute @end und @duration schließen einander aus: Es darf nur
            entweder eine Dauer oder ein Endzeitpunkt angegeben werden, aber nicht beides.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(substring($beginn-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00600-005">
                     <xsl:attribute
                             name="test">substring($beginn-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId</xsl:attribute>
                     <svrl:text>Der
            Verweis auf den Beginn des Geltungsintervalls muss auf eine in der vorliegenden Instanz vorhandene @eId einer Ereignis-Deklaration sein. Für den
            Verweis "<xsl:value-of select="$beginn-geltungszeitintervall-uri"/>" existiert jedoch kein passendes Verweisziel.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (not(empty($ende-geltungszeitintervall))) then (substring($ende-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00600-010">
                     <xsl:attribute
                             name="test">if (not(empty($ende-geltungszeitintervall))) then (substring($ende-geltungszeitintervall-uri, 2) = /akn:akomaNtoso/*/akn:meta/akn:lifecycle/akn:eventRef/@eId) else true()</xsl:attribute>
                     <svrl:text>Der Verweis auf das Ende des Geltungsintervalls muss auf eine in der vorliegenden Instanz vorhandene @eId einer
            Ereignis-Deklaration sein. Für den Verweis "<xsl:value-of select="$ende-geltungszeitintervall-uri"/>" existiert jedoch kein passendes
            Verweisziel.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (not(empty($beginn-geltungszeitintervall)) and not(empty($ende-geltungszeitintervall))) then (xs:date($beginn-geltungszeitintervall) le xs:date($ende-geltungszeitintervall)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00600-015">
                     <xsl:attribute
                             name="test">if (not(empty($beginn-geltungszeitintervall)) and not(empty($ende-geltungszeitintervall))) then (xs:date($beginn-geltungszeitintervall) le xs:date($ende-geltungszeitintervall)) else true()</xsl:attribute>
                     <svrl:text>Das Ende des Geltungszeitintervalls darf zeitlich nicht vor seinem Beginn liegen. Es sind jedoch als Beginn "<xsl:value-of
                             select="$beginn-geltungszeitintervall"/>" und als Ende "<xsl:value-of
                             select="$ende-geltungszeitintervall"/>" angegeben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1111')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="(akn:activeModifications | akn:passiveModifications)/akn:textualMod/akn:force"
                 priority="32"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="verweis-auf-geltungszeitgruppe" select="substring(@period, 2)"/>
      <xsl:variable name="geltungszeitgruppen-im-dokument"
                    select="/akn:akomaNtoso/*/akn:meta/akn:temporalData/akn:temporalGroup/@eId"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1111']">
            <schxslt:rule pattern="d14e1111">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00610 for context "(akn:activeModifications | akn:passiveModifications)/akn:textualMod/akn:force" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00610">
                  <xsl:attribute
                          name="context">(akn:activeModifications | akn:passiveModifications)/akn:textualMod/akn:force</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1111">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00610">
                  <xsl:attribute
                          name="context">(akn:activeModifications | akn:passiveModifications)/akn:textualMod/akn:force</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not($verweis-auf-geltungszeitgruppe = $geltungszeitgruppen-im-dokument)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00610-000">
                     <xsl:attribute
                             name="test">$verweis-auf-geltungszeitgruppe = $geltungszeitgruppen-im-dokument</xsl:attribute>
                     <svrl:text>Der angegebene Wert "<xsl:value-of
                             select="concat('#', $verweis-auf-geltungszeitgruppe)"/>" ist kein gültiger Verweis auf eine Geltungszeitgruppe in diesem Dokument. <xsl:value-of
                             select="if (not(empty($geltungszeitgruppen-im-dokument))) then if (count($geltungszeitgruppen-im-dokument) gt 2) then concat(' Technisch mögliche Angaben wären im aktuellen Dokument: ', (string-join( for $geltungszeitgruppe in $geltungszeitgruppen-im-dokument[position() lt last()] return concat('&#34;#', $geltungszeitgruppe, '&#34;'), ', ')), ' oder &#34;#', $geltungszeitgruppen-im-dokument[last()], '&#34;.') else concat(' Die technisch einzig mögliche Angabe ist im aktuellen Dokument &#34;#', $geltungszeitgruppen-im-dokument, '&#34;.') else ()"/>
                     </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1111')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:eventRef" priority="31" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="ereignisart" select="@refersTo"/>
      <xsl:variable name="ereignisdatum" select="@date"/>
      <xsl:variable name="platzhalterwert-bei-unbekanntem-datum" select="'0001-01-01'"/>
      <xsl:variable name="literalsuffix-für-unbekanntes-datum"
                    select="'-mit-noch-unbekanntem-datum'"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1111']">
            <schxslt:rule pattern="d14e1111">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00620 for context "akn:eventRef" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00620">
                  <xsl:attribute name="context">akn:eventRef</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1111">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00620">
                  <xsl:attribute name="context">akn:eventRef</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) then (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00620-000">
                     <xsl:attribute
                             name="test">if ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) then (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) else true()</xsl:attribute>
                     <svrl:text>Bei Angabe eines unbekannten Ereignisdatums (durch Verwendung des Platzhaltertextes "<xsl:value-of
                             select="$platzhalterwert-bei-unbekanntem-datum"/>") muss das zur näheren Bestimmung der Ereignisart im Attribut @refersTo angegebene Literal auf
            den Ausdruck "<xsl:value-of select="$literalsuffix-für-unbekanntes-datum"/>" enden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) then ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00620-005">
                     <xsl:attribute
                             name="test">if (ends-with($ereignisart, $literalsuffix-für-unbekanntes-datum)) then ($ereignisdatum = $platzhalterwert-bei-unbekanntem-datum) else true()</xsl:attribute>
                     <svrl:text>Für ein Ereignis, dessen Datum noch unbekannt ist (hier: @refersTo = "<xsl:value-of
                             select="$ereignisart"/>"), muss in seinem
            Attribut @date als Platzhalterwert "<xsl:value-of select="$platzhalterwert-bei-unbekanntem-datum"/>" angegeben werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1111')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:FRBRExpression" priority="30" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="guid-vorherige-version" select="'vorherige-version-id'"/>
      <xsl:variable name="guid-aktuelle-version" select="'aktuelle-version-id'"/>
      <xsl:variable name="guid-nächste-version" select="'nachfolgende-version-id'"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1188']">
            <schxslt:rule pattern="d14e1188">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00630 for context "akn:FRBRExpression" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00630">
                  <xsl:attribute name="context">akn:FRBRExpression</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1188">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00630">
                  <xsl:attribute name="context">akn:FRBRExpression</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(akn:FRBRalias[@name = $guid-vorherige-version]) le 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(akn:FRBRalias[@name = $guid-vorherige-version])}"
                                      id="SCH-00630-000">
                     <xsl:attribute
                             name="test">count(akn:FRBRalias[@name = $guid-vorherige-version]) le 1</xsl:attribute>
                     <svrl:text>Es darf höchstens einen einzigen Verweis auf eine Vorgängerversion
            geben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(akn:FRBRalias[@name = $guid-aktuelle-version]) eq 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(akn:FRBRalias[@name = $guid-aktuelle-version])}"
                                      id="SCH-00630-005">
                     <xsl:attribute
                             name="test">count(akn:FRBRalias[@name = $guid-aktuelle-version]) eq 1</xsl:attribute>
                     <svrl:text>Es muss genau einen Identifikator (GUID) für die vorliegende Version geben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(akn:FRBRalias[@name = $guid-nächste-version]) eq 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(akn:FRBRalias[@name = $guid-nächste-version])}"
                                      id="SCH-00630-010">
                     <xsl:attribute name="test">count(akn:FRBRalias[@name = $guid-nächste-version]) eq 1</xsl:attribute>
                     <svrl:text>Es muss genau eine Angabe zur GUID der (ggf. künftigen) Nachfolgeversion geben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(akn:FRBRalias) eq count(distinct-values(akn:FRBRalias/@value)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(akn:FRBRalias)}"
                                      id="SCH-00630-015">
                     <xsl:attribute
                             name="test">count(akn:FRBRalias) eq count(distinct-values(akn:FRBRalias/@value))</xsl:attribute>
                     <svrl:text>Sämtliche mittels
            @value angegebenen GUIDs der Versionen müssen sich voneinander unterscheiden."/&gt;</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1188')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:meta/akn:lifecycle (: Entwurfs- und Verkündungsfassung sowie Neufassung :)"
                 priority="29"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1215']">
            <schxslt:rule pattern="d14e1215">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00640 for context "akn:meta/akn:lifecycle (: Entwurfs- und Verkündungsfassung sowie Neufassung :)" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00640">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle (: Entwurfs- und Verkündungsfassung sowie Neufassung :)</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1215">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00640">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle (: Entwurfs- und Verkündungsfassung sowie Neufassung :)</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00640-010">
                     <xsl:attribute
                             name="test">count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> kann nicht mehr als ein Außerkraftsetzen
            (&lt;eventRef;&gt; mit @type='<xsl:value-of select="$type-literal-ereignisreferenz-repeal"/>') enthalten, da das Rechtsetzungsartefakt dadurch in
            Gänze aufgehoben wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00640-015">
                     <xsl:attribute
                             name="test">count(akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $zulässige-literale-in-kombination-mit-repeal]) le 1</xsl:attribute>
                     <svrl:text>Für die Deklaration eines Außerkrafttretens (&lt;eventRef&gt; mit
               @type='<xsl:value-of select="$type-literal-ereignisreferenz-repeal"/>') ist als @refersTo-Angabe ausschließlich <xsl:value-of
                                 select="string-join(distinct-values(for $literal in $zulässige-literale-in-kombination-mit-repeal return concat('''', $literal, '''')), ' oder ')"/> zulässig.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1215')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:meta/akn:lifecycle[$fassung = $fassung-entwurfsfassung] (: Entwurfsfassung :)"
                 priority="28"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1237']">
            <schxslt:rule pattern="d14e1237">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00650 for context "akn:meta/akn:lifecycle[$fassung = $fassung-entwurfsfassung] (: Entwurfsfassung :)" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00650">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = $fassung-entwurfsfassung] (: Entwurfsfassung :)</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1237">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00650">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = $fassung-entwurfsfassung] (: Entwurfsfassung :)</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum] and (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten])) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00650-000">
                     <xsl:attribute
                             name="test">if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum] and (akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten])) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung muss immer
            mindestens zwei Ereignisse auszeichnen: Erstens einen Platzhalter für das Ausfertigungsdatum; dieser wird angegeben mittels &lt;eventRef&gt; mit
               @type='<xsl:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"/>'. Und zweitens eine Angabe zum Inkrafttreten
            mittels &lt;eventRef&gt; mit @type='<xsl:value-of select="$type-literal-ereignisreferenz-generation"/> und @refersTo='<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten-mit-unbekanntem-datum"/>' bzw., sofern das Datum bereits bekannt ist,
               @refersTo='<xsl:value-of select="$refersto-literal-ereignisreferenz-entwurfsfassung-inkrafttreten"/>'.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($fassung = $fassung-entwurfsfassung) then (akn:eventRef[@type = ($type-literal-ereignisreferenz-generation, $type-literal-ereignisreferenz-repeal)]) else ())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00650-005">
                     <xsl:attribute
                             name="test">if ($fassung = $fassung-entwurfsfassung) then (akn:eventRef[@type = ($type-literal-ereignisreferenz-generation, $type-literal-ereignisreferenz-repeal)]) else ()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung kann nur initiale
            Ereignisse (Inkrafttreten, Ausfertigung, teilweises Außerkrafttreten, d.h. @type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-generation"/>') oder ein finales Außerkrafttreten (d.h. @type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-repeal"/>') aufweisen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum]) = 1) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00650-010">
                     <xsl:attribute
                             name="test">if ($art = ($art-regelungstext, $art-vereinbarung, $art-bekanntmachungstext)) then (count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum]) = 1) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Entwurfsfassung
            muss genau einen Platzhalter für das noch unbekannte Datum der Ausfertigung enthalten (&lt;eventRef&gt; mit @type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-entwurfsfassung-ausfertigung-mit-unbekanntem-datum"/>').</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1237')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:meta/akn:lifecycle[$fassung = ($fassung-verkündungsfassung, $fassung-neufassung)] (: Verkündungfassung oder Neufassung :)"
           priority="27"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="datum-ausfertigung"
                    select="exactly-one(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung] (: immer den ersten Wert nehmen, falls es unerwartet mehrere gibt, damit hier diejenige SCH-Regel zum Tragen kommt, die prüft, dass es nur genau ein initiales Ausfertigungsdatum gibt, und nicht ein Fehler auf Ebene des SCH-Prozessors die weitere Verarbeitung blockiert. :)[1]/@date)"/>
      <xsl:variable name="frühestes-datum-inkrafttreten-als-reine-ziffern"
                    select="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
      <xsl:variable name="datum-inkrafttreten"
                    select="if (not(empty($frühestes-datum-inkrafttreten-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-inkrafttreten-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
      <xsl:variable name="datum-ausserkafttreten"
                    select="akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten]/@date"/>
      <xsl:variable name="frühestes-datum-amendment-als-reine-ziffern"
                    select="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-amendment (: inkl. aller möglichen @refersTo :)]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
      <xsl:variable name="frühestes-datum-amendment"
                    select="if (not(empty($frühestes-datum-amendment-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-amendment-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-amendment-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-amendment-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
      <xsl:variable name="ausfertigungsdatum"
                    select="exactly-one(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]/@date)"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1278']">
            <schxslt:rule pattern="d14e1278">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00660 for context "akn:meta/akn:lifecycle[$fassung = ($fassung-verkündungsfassung, $fassung-neufassung)] (: Verkündungfassung oder Neufassung :)" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00660">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = ($fassung-verkündungsfassung, $fassung-neufassung)] (: Verkündungfassung oder Neufassung :)</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1278">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00660">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = ($fassung-verkündungsfassung, $fassung-neufassung)] (: Verkündungfassung oder Neufassung :)</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung] and ( akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum] ))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00660-000">
                     <xsl:attribute
                             name="test">akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung] and ( akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten] or akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum] )</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Verkündungsfassung muss
            immer mindestens zwei Ereignisse auszeichnen: Erstens das Ausfertigungsdatum; dieses wird angegeben mittels &lt;eventRef&gt; mit
               @type='<xsl:value-of select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>'. Und zweitens eine Angabe zum Inkrafttreten mittels
            &lt;eventRef&gt; mit @type='<xsl:value-of select="$type-literal-ereignisreferenz-generation"/> und @refersTo='<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten"/>' bzw., sofern das Datum noch unbekannt ist,
               @refersTo='<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-verkündungsfassung-inkrafttreten-mit-unbekanntem-datum"/>'.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00660-005">
                     <xsl:attribute
                             name="test">count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]) = 1</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> in der Verkündungsfassung muss genau ein
            konkretes Ausfertigungsdatum enthalten (&lt;eventRef&gt; mit @type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-generation"/>' und
            @refersTo = '<xsl:value-of select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>').</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (not(xs:date($datum-inkrafttreten) = xs:date('0001-01-01'))) then (xs:date($datum-ausfertigung) le xs:date($datum-inkrafttreten)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00660-010">
                     <xsl:attribute
                             name="test">if (not(xs:date($datum-inkrafttreten) = xs:date('0001-01-01'))) then (xs:date($datum-ausfertigung) le xs:date($datum-inkrafttreten)) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> kann frühestens am Tag der Ausfertigung
            inkrafttreten. Als Datum des Inkrafttretens wurde "<xsl:value-of select="$datum-inkrafttreten"/>" und als Ausfertigungsdatum "<xsl:value-of
                             select="$datum-ausfertigung"/>" angegeben.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten]) then (xs:date($datum-ausserkafttreten) gt xs:date($datum-ausfertigung)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00660-015">
                     <xsl:attribute
                             name="test">if (akn:eventRef[@type = $type-literal-ereignisreferenz-repeal and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausserkrafttreten]) then (xs:date($datum-ausserkafttreten) gt xs:date($datum-ausfertigung)) else true()</xsl:attribute>
                     <svrl:text>Das Datum des Außerkrafttretens muss nach der Ausfertigung liegen; angegeben wurden jedoch für das Außerkrafttreten
               '<xsl:value-of select="$datum-ausserkafttreten"/>' und für die Ausfertigung '<xsl:value-of
                                 select="$datum-ausfertigung"/>'.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(if (not(xs:date($frühestes-datum-amendment) = xs:date('0001-01-01'))) then (xs:date($frühestes-datum-amendment) gt xs:date($ausfertigungsdatum)) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00660-020">
                     <xsl:attribute
                             name="test">if (not(xs:date($frühestes-datum-amendment) = xs:date('0001-01-01'))) then (xs:date($frühestes-datum-amendment) gt xs:date($ausfertigungsdatum)) else true()</xsl:attribute>
                     <svrl:text>
                        <xsl:value-of
                                select="$dokumentarten-mit-lebenszyklus-angaben-formulierung-satzanfang-nominativ"/> können erst geändert werden, nachdem er/sie
            initial ausgefertigt wurde (d. h. die früheste Änderung - akn:eventRef[@type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-amendment"/>']/@date - muss nach der initialen Ausfertigung - &lt;eventRef&gt; mit @type = '<xsl:value-of
                             select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<xsl:value-of
                             select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>' - erfolgen).</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1278')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template
           match="akn:meta/akn:lifecycle[$fassung = $fassung-neufassung and $art = $art-regelungstext] (: nur Neufassung :)"
           priority="26"
           mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="frühestes-datum-neufassung-als-reine-ziffern"
                    select="min(for $n in akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $fassung-neufassung]/@date return format-date($n, '[Y,4][M,2][D,2]'))"/>
      <xsl:variable name="früheste-neufassung"
                    select="if (not(empty($frühestes-datum-neufassung-als-reine-ziffern))) then (xs:date(concat( substring($frühestes-datum-neufassung-als-reine-ziffern, 1, 4), '-', substring($frühestes-datum-neufassung-als-reine-ziffern, 5, 2), '-', substring($frühestes-datum-neufassung-als-reine-ziffern, 7, 2)))) else '0001-01-01'"/>
      <xsl:variable name="ausfertigung"
                    select="akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung]/@date"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1352']">
            <schxslt:rule pattern="d14e1352">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00670 for context "akn:meta/akn:lifecycle[$fassung = $fassung-neufassung and $art = $art-regelungstext] (: nur Neufassung :)" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00670">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = $fassung-neufassung and $art = $art-regelungstext] (: nur Neufassung :)</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1352">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00670">
                  <xsl:attribute
                          name="context">akn:meta/akn:lifecycle[$fassung = $fassung-neufassung and $art = $art-regelungstext] (: nur Neufassung :)</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-neufassung]) ge 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00670-000">
                     <xsl:attribute
                             name="test">count(akn:eventRef[@type = $type-literal-ereignisreferenz-generation and @refersTo = $refersto-literal-ereignisreferenz-neufassung]) ge 1</xsl:attribute>
                     <svrl:text>Ein
            Regelungstext als Neufassung muss mindestens ein Neufassungsereignis enthalten (&lt;eventRef&gt; mit @type = '<xsl:value-of
                                 select="$type-literal-ereignisreferenz-generation"/>' und @refersTo = '<xsl:value-of
                                 select="$refersto-literal-ereignisreferenz-neufassung"/>'.).</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(xs:date($früheste-neufassung) gt xs:date($ausfertigung))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      id="SCH-00670-005">
                     <xsl:attribute name="test">xs:date($früheste-neufassung) gt xs:date($ausfertigung)</xsl:attribute>
                     <svrl:text>Das Datum der frühesten Neufassung (&lt;eventRef&gt; mit
               @type='<xsl:value-of select="$type-literal-ereignisreferenz-generation"/>' und @rfersTo='<xsl:value-of
                                 select="$refersto-literal-ereignisreferenz-neufassung"/>') muss nach dem initialen Ausfertigungsdatum (&lt;eventRef&gt; mit @type='<xsl:value-of
                                 select="$type-literal-ereignisreferenz-generation"/>' und @refersTo='<xsl:value-of
                                 select="$refersto-literal-ereignisreferenz-verkündungsfassung-ausfertigung"/>') liegen; angegeben wurden jedoch als Ausfertigungsdatum
               '<xsl:value-of select="$ausfertigung"/>' und als Datum der Neufassung '<xsl:value-of
                                 select="$früheste-neufassung"/>'.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1352')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:p[parent::akn:longTitle and $art = $art-regelungstext]"
                 priority="25"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1389']">
            <schxslt:rule pattern="d14e1389">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00680 for context "akn:p[parent::akn:longTitle and $art = $art-regelungstext]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00680">
                  <xsl:attribute
                          name="context">akn:p[parent::akn:longTitle and $art = $art-regelungstext]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1389">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00680">
                  <xsl:attribute
                          name="context">akn:p[parent::akn:longTitle and $art = $art-regelungstext]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(akn:docTitle) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(parent::akn:longTitle)}"
                                      id="SCH-00680-000">
                     <xsl:attribute name="test">count(akn:docTitle) = 1</xsl:attribute>
                     <svrl:text>Ein dokumentenkopfTitel (akn:longtitle) muss genau einen
            Dokumententitel (akn:docTitle) besitzen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1389')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:session" priority="24" mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1400']">
            <schxslt:rule pattern="d14e1400">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-00730 for context "akn:session" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00730">
                  <xsl:attribute name="context">akn:session</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1400">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl" id="SCH-00730">
                  <xsl:attribute name="context">akn:session</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(starts-with(@refersTo, '#') and substring(@refersTo, 2) = //akn:organization/@eId)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(@refersTo)}"
                                      id="SCH-00730-000">
                     <xsl:attribute
                             name="test">starts-with(@refersTo, '#') and substring(@refersTo, 2) = //akn:organization/@eId</xsl:attribute>
                     <svrl:text>Es muss einen lokalen Verweis
            auf eine Organisation geben, deren Sitzung ausgezeichnet wird. Dieser besteht aus einer Raute (#), gefolgt von der @eId der betreffenden
            akn:organization.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1400')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href"
                 priority="23"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-hrefLiterals.expression.FRBRauthor for context "akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-hrefLiterals.expression.FRBRauthor"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-hrefLiterals.expression.FRBRauthor"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundestag", "recht.bund.de/institution/bundesrat", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRauthor/@href"
                 priority="22"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-hrefLiterals.work.FRBRauthor for context "akn:identification/akn:FRBRWork/akn:FRBRauthor/@href" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-hrefLiterals.work.FRBRauthor"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-hrefLiterals.work.FRBRauthor"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundestag', 'recht.bund.de/institution/bundesrat', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundestag", "recht.bund.de/institution/bundesrat", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRdate/@name"
                 priority="21"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-nameLiterals.expression.FRBRdate for context "akn:identification/akn:FRBRExpression/akn:FRBRdate/@name" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-nameLiterals.expression.FRBRdate"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRdate/@name</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-nameLiterals.expression.FRBRdate"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRdate/@name</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (. = ('version')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (. = ('version')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt ist ausschließlich "version".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value"
                 priority="20"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.manifestation.FRBRuri for context "akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.manifestation.FRBRuri"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.manifestation.FRBRuri"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRname/@value"
                 priority="19"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.work.FRBRname for context "akn:identification/akn:FRBRWork/akn:FRBRname/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.work.FRBRname"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRname/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.work.FRBRname"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRname/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (. = ('regelungsentwurf')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (. = ('regelungsentwurf')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt ist ausschließlich "regelungsentwurf".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRnumber/@value"
                 priority="18"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.work.FRBRnumber for context "akn:identification/akn:FRBRWork/akn:FRBRnumber/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.work.FRBRnumber"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRnumber/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.work.FRBRnumber"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRnumber/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^[0-9]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^[0-9]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "[0-9]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value"
                 priority="17"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.work.FRBRsubtype for context "akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.work.FRBRsubtype"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.work.FRBRsubtype"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^(begruendung|anschreiben|vorblattregelungstext|vorblattbeschlussempfehlung|beschlussempfehlung|bericht|aenderungsantrag|entschliessungsantrag|unterrichtung|begruendungaenderungsantrag|begruendungentschliessungsantrag|denkschrift|regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^(begruendung|anschreiben|vorblattregelungstext|vorblattbeschlussempfehlung|beschlussempfehlung|bericht|aenderungsantrag|entschliessungsantrag|unterrichtung|begruendungaenderungsantrag|begruendungentschliessungsantrag|denkschrift|regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(begruendung|anschreiben|vorblattregelungstext|vorblattbeschlussempfehlung|beschlussempfehlung|bericht|aenderungsantrag|entschliessungsantrag|unterrichtung|begruendungaenderungsantrag|begruendungentschliessungsantrag|denkschrift|regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRthis/@value"
                 priority="16"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.expression.FRBRthis for context "akn:identification/akn:FRBRExpression/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.expression.FRBRthis"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.expression.FRBRthis"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRuri/@value"
                 priority="15"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.expression.FRBRuri for context "akn:identification/akn:FRBRExpression/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.expression.FRBRuri"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.expression.FRBRuri"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value"
                 priority="14"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.manifestation.FRBRthis for context "akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.manifestation.FRBRthis"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.manifestation.FRBRthis"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß-]+/\d{4}-\d{2}-\d{2}/[0-9]+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRthis/@value"
                 priority="13"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.work.FRBRthis for context "akn:identification/akn:FRBRWork/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.work.FRBRthis"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.work.FRBRthis"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß\-]+-\d+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß\-]+-\d+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+/[a-zäöüß\-]+-\d+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRuri/@value"
                 priority="12"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1414']">
            <schxslt:rule pattern="d14e1414">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-ENTWF-valueLiterals.work.FRBRuri for context "akn:identification/akn:FRBRWork/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-ENTWF-valueLiterals.work.FRBRuri"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1414">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-ENTWF-valueLiterals.work.FRBRuri"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-entwurfsfassung) then (matches(., '^eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Entwurfsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/dl/\d{4}/[a-zäöüß-]+/[0-9]+/[a-zäöüß-]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1414')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href"
                 priority="11"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERKF-hrefLiterals.expression.FRBRauthor for context "akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERKF-hrefLiterals.expression.FRBRauthor"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERKF-hrefLiterals.expression.FRBRauthor"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRauthor/@href"
                 priority="10"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERKF-hrefLiterals.work.FRBRauthor for context "akn:identification/akn:FRBRWork/akn:FRBRauthor/@href" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERKF-hrefLiterals.work.FRBRauthor"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERKF-hrefLiterals.work.FRBRauthor"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRauthor/@href</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (. = ('recht.bund.de/institution/bundesregierung', 'recht.bund.de/institution/bundeskanzler', 'recht.bund.de/institution/bundespraesident')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "recht.bund.de/institution/bundesregierung", "recht.bund.de/institution/bundeskanzler" sowie "recht.bund.de/institution/bundespraesident".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRdate/@name"
                 priority="9"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERKF-nameLiterals.expression.FRBRdate for context "akn:identification/akn:FRBRExpression/akn:FRBRdate/@name" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERKF-nameLiterals.expression.FRBRdate"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRdate/@name</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERKF-nameLiterals.expression.FRBRdate"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRdate/@name</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (. = ('verkuendung', 'aenderung', 'berichtigung')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (. = ('verkuendung', 'aenderung', 'berichtigung')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "verkuendung", "aenderung" sowie "berichtigung".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value"
                 priority="8"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.manifestation.FRBRuri for context "akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.manifestation.FRBRuri"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.manifestation.FRBRuri"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRname/@value"
                 priority="7"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERKF-valueLiterals.work.FRBRname for context "akn:identification/akn:FRBRWork/akn:FRBRname/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERKF-valueLiterals.work.FRBRname"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRname/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERKF-valueLiterals.work.FRBRname"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRname/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (. = ('bgbl-1', 'bgbl-2', 'banz-at')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (. = ('bgbl-1', 'bgbl-2', 'banz-at')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich "bgbl-1", "bgbl-2" sowie "banz-at".</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRnumber/@value"
                 priority="6"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.work.FRBRnumber for context "akn:identification/akn:FRBRWork/akn:FRBRnumber/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.work.FRBRnumber"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRnumber/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.work.FRBRnumber"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRnumber/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^(s[0-9]+[a-zäöüß]*)|([0-9]+)$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^(s[0-9]+[a-zäöüß]*)|([0-9]+)$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(s[0-9]+[a-zäöüß]*)|([0-9]+)" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value"
                 priority="5"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.work.FRBRsubtype for context "akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.work.FRBRsubtype"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.work.FRBRsubtype"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRsubtype/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^(regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^(regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "(regelungstext|offenestruktur|vereinbarung|bekanntmachungstext|externesdokument|rechtsetzungsdokument)(-[0-9]+)" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRthis/@value"
                 priority="4"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.expression.FRBRthis for context "akn:identification/akn:FRBRExpression/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.expression.FRBRthis"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.expression.FRBRthis"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRExpression/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRExpression/akn:FRBRuri/@value"
                 priority="3"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.expression.FRBRuri for context "akn:identification/akn:FRBRExpression/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.expression.FRBRuri"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.expression.FRBRuri"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRExpression/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value"
                 priority="2"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.manifestation.FRBRthis for context "akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.manifestation.FRBRthis"
                                     role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.manifestation.FRBRthis"
                                role="error">
                  <xsl:attribute
                          name="context">akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/\d{4}-\d{2}-\d{2}/\d+/[a-z]{3}/[a-zöäüß\-]+-\d+\.[a-zöäüß]+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRthis/@value"
                 priority="1"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.work.FRBRthis for context "akn:identification/akn:FRBRWork/akn:FRBRthis/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.work.FRBRthis"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRthis/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.work.FRBRthis"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRthis/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/[a-zöäüß\-]+-\d+$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/[a-zöäüß\-]+-\d+$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)/[a-zöäüß\-]+-\d+" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="akn:identification/akn:FRBRWork/akn:FRBRuri/@value"
                 priority="0"
                 mode="d14e147">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd14e1515']">
            <schxslt:rule pattern="d14e1515">
               <xsl:comment
                       xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule SCH-VERK-valueLiterals.work.FRBRuri for context "akn:identification/akn:FRBRWork/akn:FRBRuri/@value" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     id="SCH-VERK-valueLiterals.work.FRBRuri"
                                     role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRuri/@value</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d14e1515">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                id="SCH-VERK-valueLiterals.work.FRBRuri"
                                role="error">
                  <xsl:attribute name="context">akn:identification/akn:FRBRWork/akn:FRBRuri/@value</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)$')) else true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}">
                     <xsl:attribute
                             name="test">if ($ist-verkündungsfassung) then (matches(., '^eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)$')) else true()</xsl:attribute>
                     <svrl:text>In der Verkündungsfassung ist das Literal "<xsl:value-of select="."/>" an dieser Stelle nicht
                                    zulässig. Erlaubt sind ausschließlich Werte, die dem Muster "eli/bund/[a-zäöüß]+-[0-9]+/\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+)" entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd14e1515')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:function name="schxslt:location" as="xs:string">
      <xsl:param name="node" as="node()"/>
      <xsl:variable name="segments" as="xs:string*">
         <xsl:for-each select="($node/ancestor-or-self::node())">
            <xsl:variable name="position">
               <xsl:number level="single"/>
            </xsl:variable>
            <xsl:choose>
               <xsl:when test=". instance of element()">
                  <xsl:value-of select="concat('Q{', namespace-uri(.), '}', local-name(.), '[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of attribute()">
                  <xsl:value-of select="concat('@Q{', namespace-uri(.), '}', local-name(.))"/>
               </xsl:when>
               <xsl:when test=". instance of processing-instruction()">
                  <xsl:value-of select="concat('processing-instruction(&#34;', name(.), '&#34;)[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of comment()">
                  <xsl:value-of select="concat('comment()[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of text()">
                  <xsl:value-of select="concat('text()[', $position, ']')"/>
               </xsl:when>
               <xsl:otherwise/>
            </xsl:choose>
         </xsl:for-each>
      </xsl:variable>
      <xsl:value-of select="concat('/', string-join($segments, '/'))"/>
   </xsl:function>
</xsl:transform>
