<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
        xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
        xmlns:meta-ds="http://DS.Metadaten.LegalDocML.de/1.6/">

    <!-- This is just a placeholder implementation for showing how we can separate the xslt for the metadata and "normal render -->

    <xsl:template name="metadata">
        <xsl:apply-templates mode="metadata" select="ancestor::akn:akomaNtoso[1]"/>
    </xsl:template>

    <xsl:template match="akn:akomaNtoso" mode="metadata">
        <section class="metadata">
            <table>
                <thead>
                    <tr>
                        <td>Metadatum</td>
                        <td>Wert</td>
                    </tr>
                </thead>

                <tbody>
                    <tr>
                        <td>Aktenzeichen</td>
                        <td>
                            <xsl:value-of select="//meta-ds:aktenzeichen"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Amtliche Buchstabenkürzung</td>
                        <td>
                            <xsl:value-of select="//akn:inline[@refersTo='amtliche-abkuerzung']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Amtliche Fundstelle</td>
                        <td>
                            <xsl:value-of select="//akn:FRBRExpression/akn:FRBRuri/@value"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Amtliche Kurzüberschrift</td>
                        <td>
                            <xsl:value-of select="//akn:shortTitle"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Amtliche Langüberschrift</td>
                        <td>
                            <xsl:value-of select="//akn:docTitle"/>
                        </td>
                    </tr>
                    <tr>
                        <td>CELEX-Nummer</td>
                        <td>
                            <xsl:value-of select="//meta-ds:celex"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum des Außerkrafttretens</td>
                        <td>
                            <xsl:value-of select="format-date(//akn:eventRef[@refersTo='ausserkrafttreten']/@date, '[D01].[M01].[Y0001]')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum des Inkrafttretens</td>
                        <td>
                            <xsl:value-of select="format-date(//akn:eventRef[@refersTo='inkrafttreten' and @type='generation']/@date, '[D01].[M01].[Y0001]')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Dokumententyp</td>
                        <td>
                            <xsl:value-of select="//meta:typ"/>
                        </td>
                    </tr>
                    <tr>
                        <td>ELI</td>
                        <td>
                            <xsl:value-of select="//akn:FRBRExpression/akn:FRBRthis/@value"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Fassungsdatum</td>
                        <td>
                            <xsl:value-of select="format-date(//meta:fassungsdatum, '[D01].[M01].[Y0001]')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Federführung</td>
                        <td>
                            <xsl:apply-templates select="//meta:federfuehrung"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Fußnoten</td>
                        <td>
                            <!--Currently not in test data and unclear how to display-->
                        </td>
                    </tr>
                    <tr>
                        <td>Grundsätzliches Außerkrafttretedatum</td>
                        <td>
                            <xsl:value-of
                                    select="//akn:eventRef[@refersTo='ausserkrafttreten-mit-noch-unbekanntem-datum']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Grundsätzliches Inkrafttretedatum</td>
                        <td>
                            <xsl:value-of
                                    select="//akn:eventRef[@refersTo='inkrafttreten-mit-noch-unbekanntem-datum']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Mitwirkende Organe</td>
                        <td>
                            <xsl:value-of select="//meta-ds:mitwirkung"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Normgeber</td>
                        <td>
                            <xsl:value-of select="//meta:initiant"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Sachgebiet (FNA-Nummer)</td>
                        <td>
                            <xsl:value-of select="//meta:fna"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
    </xsl:template>

    <xsl:template match="//meta:federfuehrung">
        <xsl:for-each select="meta:federfuehrend">
            <li><xsl:value-of select="."/></li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
