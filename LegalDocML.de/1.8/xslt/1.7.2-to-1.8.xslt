<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:akn_old="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/"
  xmlns:meta_bundesregierung_old="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/"
  xmlns:meta_old="http://Metadaten.LegalDocML.de/1.7.2/"
  xmlns:meta_regelungstext="http://MetadatenRegelungstext.LegalDocML.de/1.8/"
  xmlns:meta_rechtsetzungsdokument="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/"
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
     <xsl:element namespace="http://Inhaltsdaten.LegalDocML.de/1.8/" name="{name()}">
       <xsl:apply-templates select='@* | node()'/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="meta_bundesregierung_old:*">
       <xsl:element namespace="http://MetadatenBundesregierung.LegalDocML.de/1.8/" name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <!-- Split old metadata into two elements -->
  <xsl:template match="meta_old:legalDocML.de_metadaten">
    <xsl:element name="meta:legalDocML.de_metadaten" namespace="http://MetadatenRegelungstext.LegalDocML.de/1.8/">
       <xsl:apply-templates select="./meta_old:typ" />
       <xsl:apply-templates select="./meta_old:form" />
       <xsl:apply-templates select="./meta_old:vomHdrAbweichendeGliederung" />
    </xsl:element>

    <xsl:element name="meta:legalDocML.de_metadaten" namespace="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/">
       <xsl:apply-templates select="./meta_old:initiant" />
       <xsl:apply-templates select="./meta_old:bearbeitendeInstitution" />
       <xsl:apply-templates select="./meta_old:fna" />
       <xsl:apply-templates select="./meta_old:gesta" />
       <xsl:apply-templates select="./meta_old:gegenstandlos" />
       <xsl:apply-templates select="./meta_old:migrationsstatusNeuRIS" />
     </xsl:element>
  </xsl:template>

  <xsl:template match="meta_old:typ | meta_old:form | meta_old:vomHdrAbweichendeGliederung">
       <xsl:element namespace="http://MetadatenRegelungstext.LegalDocML.de/1.8/" name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="meta_old:migrationsstatusNeuRIS | meta_old:gegenstandlos | meta_old:gesta | meta_old:fna | meta_old:bearbeitendeInstitution | meta_old:initiant">
       <xsl:element namespace="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/" name="{name()}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <!-- Replace neuris specific metadata area namespace -->
  <xsl:template match="meta_neuris_old:*">
      <xsl:element name="{name()}" namespace="http://MetadatenRIS.LegalDocML.de/1.8/">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="meta_neuris_mods_old:*">
      <xsl:element name="{name()}" namespace="http://MetadatenMods.LegalDocML.de/1.8/">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:doc[@name='bekanntmachung']/@name">
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

  <xsl:template match="akn_old:signature">
    <xsl:element name="akn:p">

      <xsl:element name="akn:signature">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
    </xsl:element>
  </xsl:template>

  <xsl:template match="akn_old:container/@refersTo" />

  <!-- todo: fix duplicated guids -->
  <!-- todo: fix elis -->
</xsl:stylesheet>
