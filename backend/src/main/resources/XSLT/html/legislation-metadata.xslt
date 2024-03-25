<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
        xmlns:meta="http://Inhaltsdaten.LegalDocML.de/1.6/">

    <!-- This is just a placeholder implementation for showing how we can separate the xslt for the metadata and "normal render -->

    <xsl:template name="metadata">
        <xsl:apply-templates mode="metadata" select="ancestor::akn:akomaNtoso[1]"/>
    </xsl:template>

    <xsl:template match="akn:akomaNtoso" mode="metadata">
        <article>
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
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:Aktenzeichen"/>
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
                        <!--Assumption: We want to show the expression Fundstelle-->
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
                            <!--Currently not in test data but wouldn't be able to load from proprietary section either way-->
                            <xsl:value-of select="//meta:celex"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum des Außerkrafttretens</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//akn:eventRef[@refersTo='ausserkrafttreten']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum des Inkrafttretens</td>
                        <td>
                            <xsl:value-of select="//akn:eventRef[@refersTo='inkrafttreten']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Dokumententyp</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:typ"/>
                        </td>
                    </tr>
                    <tr>
                        <td>ELI</td>
                        <td>
                            <!--Assumption: We want to show the expression ELI-->
                            <xsl:value-of select="//akn:FRBRExpression/akn:FRBRthis/@value"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Fassungsdatum</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:fassungsdatum"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Federführung</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:federfuehrung"/>
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
                            <!--Currently not in test data-->
                            <xsl:value-of
                                    select="//akn:eventRef[@refersTo='ausserkrafttreten-mit-noch-unbekanntem-datum']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Grundsätzliches Inkrafttretedatum</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of
                                    select="//akn:eventRef[@refersTo='inkrafttreten-mit-noch-unbekanntem-datum']/@date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Mitwirkende Organe</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:mitwirkung"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Normgeber</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:initiant"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Sachgebiet (FNA-Nummer)</td>
                        <td>
                            <!--Currently not in test data-->
                            <xsl:value-of select="//meta:fna"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </article>
    </xsl:template>
</xsl:stylesheet>
