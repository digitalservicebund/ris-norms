<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn_old="http://Inhaltsdaten.LegalDocML.de/1.6/"
        xmlns:akn_new="http://Inhaltsdaten.LegalDocML.de/1.7/"
        xmlns:meta_old="http://Metadaten.LegalDocML.de/1.6/"
        xmlns:meta_new="http://Metadaten.LegalDocML.de/1.7/"
        xmlns:meta_ds_old="http://DS.Metadaten.LegalDocML.de/1.6/"
        xmlns:meta_ds_new="http://MetadatenRIS.LegalDocML.de/1.7/"
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
         <xsl:element namespace="http://Inhaltsdaten.LegalDocML.de/1.7/" name="{name()}">
           <xsl:apply-templates select='@* | node()'/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="meta_old:*">
         <xsl:element namespace="http://Metadaten.LegalDocML.de/1.7/" name="{name()}">
           <xsl:apply-templates select='@* | node()'/>
        </xsl:element>
    </xsl:template>

    <!-- Remove old marker elements -->
    <xsl:template match="akn_old:marker"/>

    <!-- Update manifest-eli to include the point in time manifestation -->
    <xsl:template match="
        akn_old:meta/akn_old:identification/akn_old:FRBRManifestation/akn_old:FRBRthis/@value |
        akn_old:meta/akn_old:identification/akn_old:FRBRManifestation/akn_old:FRBRuri/@value
    ">
        <xsl:variable name="point-in-time-manifestation" select="../../akn_old:FRBRdate/@date"/>

        <xsl:attribute name="value">
            <xsl:for-each select="tokenize(.,'/')">
               <xsl:sequence select="."/>
               <xsl:if test="position() eq 8"><xsl:value-of select="concat('/', $point-in-time-manifestation)"/></xsl:if>
               <xsl:if test="not(position() eq last())">/</xsl:if>
            </xsl:for-each>
        </xsl:attribute>
    </xsl:template>

    <!-- Replace unbekannt dates with unbestimmt/unbekannt -->
    <xsl:template match="akn_old:date/@refersTo">
        <xsl:attribute name="refersTo">
            <xsl:choose>
                <xsl:when
                        test=". eq 'inkrafttreten-mit-noch-unbekanntem-datum'">inkrafttreten-mit-noch-unbestimmtem-unbekanntem-datum</xsl:when>
                <xsl:when
                        test=". eq 'ausserkrafttreten-mit-noch-unbekanntem-datum-komplett'">ausserkrafttreten-mit-noch-unbestimmtem-unbekanntem-datum-komplett</xsl:when>
                <xsl:when
                        test=". eq 'ausserkrafttreten-mit-noch-unbekanntem-datum-teil'">ausserkrafttreten-mit-noch-unbestimmtem-unbekanntem-datum-teil</xsl:when>
                <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
            </xsl:choose>
        </xsl:attribute>
    </xsl:template>

    <!-- Replace neuris specific metadata area name and namespace -->
    <xsl:template match="meta_ds_old:legalDocML.de_metadaten_ds">
        <xsl:element name="ris:legalDocML.de_metadaten" namespace="http://MetadatenRIS.LegalDocML.de/1.7/">
           <xsl:apply-templates select='@* | node()'/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="meta_ds_old:*">
         <xsl:element name="{concat('ris:', local-name())}" namespace="http://MetadatenRIS.LegalDocML.de/1.7/">
             <xsl:apply-templates select='@* | node()'/>
         </xsl:element>
    </xsl:template>
</xsl:stylesheet>
