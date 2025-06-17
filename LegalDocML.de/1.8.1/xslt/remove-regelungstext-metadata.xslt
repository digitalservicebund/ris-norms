<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:meta="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/"
  exclude-result-prefixes="#all"
>
  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="@*|node()">
     <xsl:copy>
       <xsl:apply-templates select="@* | node()"/>
     </xsl:copy>
  </xsl:template>

  <xsl:template match="meta:*" />
</xsl:stylesheet>
