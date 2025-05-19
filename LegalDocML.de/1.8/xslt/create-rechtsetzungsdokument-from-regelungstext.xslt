<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/"
  xmlns:meta="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/"
  exclude-result-prefixes="#all"
>
  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="@*|node()">
     <xsl:copy>
       <xsl:apply-templates select="@* | node()"/>
     </xsl:copy>
  </xsl:template>

  <xsl:template match="akn:act">
     <akn:documentCollection name="/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument">
       <xsl:apply-templates select='./akn:meta'/>

       <akn:collectionBody GUID="REPLACE_WITH_NEW_UUID">
         <akn:component GUID="REPLACE_WITH_NEW_UUID">
            <akn:documentRef GUID="REPLACE_WITH_NEW_UUID" href="regelungstext-1.xml" showAs="/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung"/>
         </akn:component>
       </akn:collectionBody>
     </akn:documentCollection>
  </xsl:template>

  <xsl:template match="akn:meta">
    <akn:meta GUID="REPLACE_WITH_NEW_UUID">
      <xsl:apply-templates select='./akn:identification' />
      <xsl:apply-templates select='./akn:proprietary' />
    </akn:meta>
  </xsl:template>

  <xsl:template match="akn:proprietary">
    <akn:proprietary GUID="REPLACE_WITH_NEW_UUID" source="attributsemantik-noch-undefiniert">
       <xsl:apply-templates select='./meta:legalDocML.de_metadaten '/>
    </akn:proprietary>
  </xsl:template>

  <xsl:template match="akn:FRBRthis/@value | akn:FRBRManifestation/akn:FRBRuri/@value">
    <xsl:attribute name="value">
      <xsl:for-each select="tokenize(.,'/')">
         <xsl:if test="position() eq last()"><xsl:value-of select="'rechtsetzungsdokument-1.xml'"/></xsl:if>
         <xsl:if test="not(position() eq last())"><xsl:sequence select="."/>/</xsl:if>
      </xsl:for-each>
    </xsl:attribute>
  </xsl:template>

  <xsl:template match="akn:FRBRsubtype/@value">
    <xsl:attribute name="value">rechtsetzungsdokument-1.xml</xsl:attribute>
  </xsl:template>

  <xsl:template match="@GUID">
    <xsl:attribute name="GUID">REPLACE_WITH_NEW_UUID</xsl:attribute>
  </xsl:template>
</xsl:stylesheet>
