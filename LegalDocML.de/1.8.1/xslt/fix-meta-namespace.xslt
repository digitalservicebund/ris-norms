<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/"
  xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/"
  exclude-result-prefixes="#all"
>
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
           <xsl:apply-templates select='@* | node()'/>
        </xsl:copy>
    </xsl:template>

  <xsl:template match="regtxt:*">
       <xsl:element namespace="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/" name="{concat('regtxt:', local-name())}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>

  <xsl:template match="redok:*">
       <xsl:element namespace="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/" name="{concat('redok:', local-name())}">
         <xsl:apply-templates select='@* | node()'/>
      </xsl:element>
  </xsl:template>
</xsl:stylesheet>
