<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
  exclude-result-prefixes="#all"
>
  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="@*|node()">
      <xsl:copy>
         <xsl:apply-templates select='@* | node()'/>
      </xsl:copy>
  </xsl:template>

  <xsl:template match="akn:paragraph/akn:list | akn:quotedStructure/akn:point/akn:list">
    <akn:content>
      <xsl:attribute name="GUID" select="'REPLACE_WITH_NEW_UUID'" />
      <akn:blockList>
        <xsl:apply-templates select="@* | node()" mode="replace-list"/>
      </akn:blockList>
    </akn:content>
  </xsl:template>

  <xsl:template match="akn:list">
    <akn:blockList>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="node()" mode="replace-list"/>
    </akn:blockList>
  </xsl:template>

  <xsl:template match="akn:intro" mode="replace-list">
    <akn:listIntroduction>
      <xsl:apply-templates select="@* | akn:p/node() | node()[not(self::akn:p)]"/>
    </akn:listIntroduction>
  </xsl:template>

  <xsl:template match="akn:wrapUp" mode="replace-list">
    <akn:listWrapUp>
      <xsl:apply-templates select="@* | akn:p/node() | node()[not(self::akn:p)]"/>
    </akn:listWrapUp>
  </xsl:template>

  <xsl:template match="akn:point" mode="replace-list">
    <akn:item>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="akn:content/akn:p | node()[not(self::akn:content)]"/>
    </akn:item>
  </xsl:template>


  <xsl:template match="@*|node()" mode="replace-list">
      <xsl:copy>
         <xsl:apply-templates select='@* | node()'/>
      </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
