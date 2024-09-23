<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
        xmlns:meta="http://Metadaten.LegalDocML.de/1.7/"
        exclude-result-prefixes="#all"
>
    <xsl:import href="fix-eids-step1.xsl"/>
    <xsl:import href="fix-eids-step2.xsl"/>

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <xsl:variable name="step1-result">
            <xsl:apply-templates select="." mode="step1"/>
        </xsl:variable>
        <xsl:apply-templates select="$step1-result" mode="step2"/>
    </xsl:template>
</xsl:stylesheet>
