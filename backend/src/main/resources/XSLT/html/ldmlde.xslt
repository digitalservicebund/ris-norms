<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        exclude-result-prefixes="#all"
>

    <xsl:output method="xml" encoding="utf-8" indent="no"/>

    <xsl:template match="text()">
        <xsl:value-of select="normalize-space()"/>
    </xsl:template>
    <xsl:template match="@*">
        <xsl:copy/>
    </xsl:template>

    <xsl:template match="*">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
