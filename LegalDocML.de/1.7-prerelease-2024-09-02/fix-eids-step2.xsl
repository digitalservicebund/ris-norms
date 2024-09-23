<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
        exclude-result-prefixes="#all"
>
    <xsl:output method="xml"/>

    <!-- Replaces the references to old eIds with the new eIds and removes the oldEId attribute -->

    <xsl:template mode="step2" match="node()">
        <xsl:copy>
           <xsl:apply-templates select='@* | node()' mode="step2"/>
        </xsl:copy>
    </xsl:template>

    <!-- replace references using the old eids with the new eids -->
    <xsl:template mode="step2" match="@*">
        <xsl:choose>
            <xsl:when test="starts-with(., '#')">
                <xsl:variable name="oldEId" select="replace(tokenize(., '/')[1], '#', '')"/>
                <xsl:variable name="eId" select="//*[@oldEId=$oldEId]/@eId"/>

                <xsl:attribute name="{ name() }" namespace="{ namespace-uri() }">
                    <xsl:choose>
                        <xsl:when test="$eId">
                            <xsl:value-of select="replace(., $oldEId, $eId)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="."/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                   <xsl:apply-templates select='@* | node()' mode="step2"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- remove the temporary oldEId attributes -->
    <xsl:template mode="step2" match="@oldEId"/>
</xsl:stylesheet>
