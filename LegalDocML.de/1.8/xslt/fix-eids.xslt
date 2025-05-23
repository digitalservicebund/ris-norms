<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  exclude-result-prefixes="#all"
>
    <xsl:import href="fix-eids-step1.xslt"/>
    <xsl:import href="fix-eids-step2.xslt"/>

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <xsl:variable name="step1-result">
            <xsl:apply-templates select="." mode="step1"/>
        </xsl:variable>
        <xsl:apply-templates select="$step1-result" mode="step2"/>
    </xsl:template>
</xsl:stylesheet>
