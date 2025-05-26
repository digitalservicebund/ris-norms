<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:meta-rechtsetzungsdokument="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/"
  xmlns:meta-bundesregierung="http://MetadatenBundesregierung.LegalDocML.de/1.8/"
  exclude-result-prefixes="#all"
>
  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="@*|node()">
     <xsl:copy>
       <xsl:apply-templates select="@* | node()"/>
     </xsl:copy>
  </xsl:template>

  <xsl:template match="meta-rechtsetzungsdokument:*" />
  <xsl:template match="meta-bundesregierung:*" />
</xsl:stylesheet>
