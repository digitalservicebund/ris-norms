<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:akn_old="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
  xmlns:meta_bundesregierung_old="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/"
  xmlns:meta_old="http://Metadaten.LegalDocML.de/1.7.2/"
  xmlns:meta_neuris_old="http://MetadatenRIS.LegalDocML.de/1.7.2/"
  xmlns:meta_neuris_mods_old="http://MetadatenMods.LegalDocML.de/1.7.2/"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  exclude-result-prefixes="#all"
>
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
           <xsl:apply-templates select='@* | node()'/>
        </xsl:copy>
    </xsl:template>

  <!-- Update namespaces -->
  <xsl:template match="akn_old:*">
     <xsl:element namespace="http://Inhaltsdaten.LegalDocML.de/1.8.1/" name="{name()}">
       <xsl:apply-templates select='@* | node()'/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="meta_bundesregierung_old:*">
       <xsl:element namespace="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/" name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <!-- Split old metadata into two elements -->
  <xsl:template match="meta_old:legalDocML.de_metadaten">
    <xsl:element name="regtxt:legalDocML.de_metadaten" namespace="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
       <xsl:apply-templates select="./meta_old:typ" />
       <xsl:apply-templates select="./meta_old:form" />
       <xsl:apply-templates select="./meta_old:vomHdrAbweichendeGliederung" />
    </xsl:element>

    <xsl:element name="redok:legalDocML.de_metadaten" namespace="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
       <xsl:apply-templates select="./meta_old:initiant" />
       <xsl:apply-templates select="./meta_old:bearbeitendeInstitution" />
       <xsl:apply-templates select="./meta_old:fna" />
       <xsl:apply-templates select="./meta_old:gesta" />
       <xsl:apply-templates select="./meta_old:gegenstandlos" />
       <xsl:apply-templates select="./meta_old:migrationsstatusNeuRIS" />
     </xsl:element>
  </xsl:template>

  <xsl:template match="meta_old:typ | meta_old:form | meta_old:vomHdrAbweichendeGliederung">
       <xsl:element namespace="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/" name="{concat('regtxt:', local-name())}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="meta_old:migrationsstatusNeuRIS | meta_old:gegenstandlos | meta_old:gesta | meta_old:fna | meta_old:bearbeitendeInstitution | meta_old:initiant">
       <xsl:element namespace="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/" name="{concat('redok:', local-name())}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <!-- Replace neuris specific metadata area namespace -->
  <xsl:template match="meta_neuris_old:*">
      <xsl:element name="{name()}" namespace="http://MetadatenRIS.LegalDocML.de/1.8.1/">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="meta_neuris_mods_old:*">
      <xsl:element name="{name()}" namespace="http://MetadatenMods.LegalDocML.de/1.8.1/">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:doc[@name='bekanntmachungstext']/@name">
    <xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/bekanntmachungstext</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:doc[@name='offene-struktur']/@name"><xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext</xsl:attribute></xsl:template>
  <xsl:template match="akn_old:documentCollection[@name='rechtsetzungsdokument-entwurfsfassung']/@name"><xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument</xsl:attribute></xsl:template>
  <xsl:template match="akn_old:documentCollection[@name='rechtsetzungsdokument-verkuendungsfassung']/@name"><xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument</xsl:attribute></xsl:template>
  <xsl:template match="akn_old:bill[@name='regelungstext']/@name"><xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/regelungstext-entwurf</xsl:attribute></xsl:template>
  <xsl:template match="akn_old:act[@name='regelungstext']/@name"><xsl:attribute name="name">/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung</xsl:attribute></xsl:template>

  <xsl:template match="akn_old:FRBRWork/akn_old:FRBRdate[@name='entwurfsfassung']/@name">
      <xsl:attribute name="name">erstellungsdatum</xsl:attribute>
  </xsl:template>

  <xsl:template match="akn_old:FRBRWork/akn_old:FRBRdate[@name='verkuendungsfassung']">
      <xsl:element name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
         <xsl:attribute name="name">verkuendungsfassung-ausfertigungsdatum</xsl:attribute>
      </xsl:element>
      <xsl:element name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
         <xsl:attribute name="GUID" select="'REPLACE_WITH_NEW_UUID'" />
         <xsl:attribute name="name">verkuendungsfassung-verkuendungsdatum</xsl:attribute>
      </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:FRBRWork/akn_old:FRBRdate[@name='neufassung']">
      <xsl:element name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
         <xsl:attribute name="name">neufassung-ausfertigungsdatum</xsl:attribute>
      </xsl:element>
      <xsl:element name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
         <xsl:attribute name="GUID" select="'REPLACE_WITH_NEW_UUID'" />
         <xsl:attribute name="name">neufassung-verkuendungsdatum</xsl:attribute>
      </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:documentRef[@showAs='regelungstext']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:documentRef[@showAs='offene-struktur']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:documentRef[@showAs='externes-dokument']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/sonstiges-dokument</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:documentRef[@showAs='bekanntmachungstext']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/bekanntmachungstext</xsl:attribute>
  </xsl:template>

  <xsl:template match="akn_old:componentRef[@showAs='regelungstext']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:componentRef[@showAs='offene-struktur']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:componentRef[@showAs='externes-dokument']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/sonstiges-dokument</xsl:attribute>
  </xsl:template>
  <xsl:template match="akn_old:componentRef[@showAs='bekanntmachungstext']/@showAs">
    <xsl:attribute name="showAs">/akn/ontology/de/concept/documenttype/bund/bekanntmachungstext</xsl:attribute>
  </xsl:template>

  <xsl:template match="akn_old:signature">
    <xsl:element name="akn:p">
      <xsl:attribute name="GUID" select="'REPLACE_WITH_NEW_UUID'" />

      <xsl:element name="akn:signature">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
    </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:container/@refersTo" />

  <xsl:template match="@xsi:schemaLocation">
    <xsl:if test="//akn_old:doc[@name='offene-struktur']">
      <xsl:attribute name="xsi:schemaLocation">http://Inhaltsdaten.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-offenestruktur.xsd http://MetadatenBundesregierung.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-metadaten-bundesregierung.xsd http://MetadatenRIS.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/legalDocML.de-metadaten-ris.xsd http://MetadatenMods.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/norms-application-only-metadata.xsd</xsl:attribute>
    </xsl:if>
    <xsl:if test="//akn_old:documentCollection[@name='rechtsetzungsdokument-verkuendungsfassung']">
      <xsl:attribute name="xsi:schemaLocation">http://Inhaltsdaten.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-rechtsetzungsdokument.xsd http://MetadatenBundesregierung.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-metadaten-bundesregierung.xsd http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-metadaten-rechtsetzungsdokument.xsd http://MetadatenRIS.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/legalDocML.de-metadaten-ris.xsd http://MetadatenMods.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/norms-application-only-metadata.xsd</xsl:attribute>
    </xsl:if>
    <xsl:if test="//akn_old:act[@name='regelungstext']">
      <xsl:attribute name="xsi:schemaLocation">http://Inhaltsdaten.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd http://MetadatenBundesregierung.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-metadaten-bundesregierung.xsd http://MetadatenRegelungstext.LegalDocML.de/1.8.1/ /LegalDocML.de/1.8.1/schema/legalDocML.de-metadaten-regelungstext.xsd http://MetadatenRIS.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/legalDocML.de-metadaten-ris.xsd http://MetadatenMods.LegalDocML.de/1.8.1/ /LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1/norms-application-only-metadata.xsd</xsl:attribute>
    </xsl:if>
  </xsl:template>

  <xsl:template match="processing-instruction('xml-model')">
    <xsl:processing-instruction name="xml-model">href="/LegalDocML.de/1.8.1/schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"</xsl:processing-instruction>
  </xsl:template>

  <!-- replace regelungstext-n with regelungstext-verkuendung-n and offenestruktur-n with anlage-regelungstext-n in elis and subtype -->

  <xsl:template match="akn_old:FRBRsubtype/@value">
    <xsl:attribute name="value">
      <xsl:for-each select="tokenize(.,'-')">
        <xsl:if test=". eq 'regelungstext'"><xsl:value-of select="'regelungstext-verkuendung'"/></xsl:if>
        <xsl:if test=". eq 'offenestruktur'"><xsl:value-of select="'anlage-regelungstext'"/></xsl:if>
        <xsl:if test="not(. eq 'regelungstext') and not(. eq 'offenestruktur')"><xsl:value-of select="."/></xsl:if>
        <xsl:if test="not(position() eq last())">-</xsl:if>
      </xsl:for-each>
    </xsl:attribute>
  </xsl:template>

  <xsl:template match="akn_old:FRBRExpression/akn_old:FRBRthis/@value | akn_old:FRBRWork/akn_old:FRBRthis/@value | akn_old:FRBRManifestation/akn_old:FRBRthis/@value | akn_old:FRBRManifestation/akn_old:FRBRuri/@value ">
    <xsl:attribute name="value">
      <xsl:for-each select="tokenize(.,'/')">
        <xsl:for-each select="tokenize(.,'-')">
          <xsl:if test=". eq 'regelungstext'"><xsl:value-of select="'regelungstext-verkuendung'"/></xsl:if>
          <xsl:if test=". eq 'offenestruktur'"><xsl:value-of select="'anlage-regelungstext'"/></xsl:if>
          <xsl:if test="not(. eq 'regelungstext') and not(. eq 'offenestruktur')"><xsl:value-of select="."/></xsl:if>
          <xsl:if test="not(position() eq last())">-</xsl:if>
        </xsl:for-each>
        <xsl:if test="not(position() eq last())">/</xsl:if>
      </xsl:for-each>
    </xsl:attribute>
  </xsl:template>
</xsl:stylesheet>
