<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:local="http://example.com/ns/1.0"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
        xmlns:meta="http://Inhaltsdaten.LegalDocML.de/1.6/">

    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/akn:akomaNtoso">
        <div>
            <xsl:call-template name="attributes"/>

            <!-- Metadata -->
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

            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="akn:act">
        <article>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </article>
    </xsl:template>

    <xsl:template match="akn:preface | akn:preamble | akn:conclusions">
        <section>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </section>
    </xsl:template>

    <xsl:template match="akn:longTitle">
        <h1>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </h1>
    </xsl:template>

    <xsl:template match="akn:section">
        <section>
            <xsl:call-template name="attributes"/>
            <h2>
                <xsl:apply-templates select="akn:num"/>
                <xsl:apply-templates select="akn:heading"/>
            </h2>
            <xsl:apply-templates select="*[not(self::akn:num|self::akn:heading)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:article">
        <section>
            <xsl:call-template name="attributes"/>
            <h3>
                <xsl:apply-templates select="akn:num"/>
                <xsl:apply-templates select="akn:heading"/>
            </h3>
            <xsl:apply-templates select="*[not(self::akn:num|self::akn:heading)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:paragraph">
        <section>
            <xsl:call-template name="attributes"/>
            <h4>
                <xsl:apply-templates select="akn:num"/>
            </h4>
            <xsl:apply-templates select="*[not(self::akn:num)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:body | akn:blockContainer | akn:signature | akn:wrapUp | akn:block">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template
            match="akn:longTitle/akn:p | akn:shortTitle | akn:docTitle | akn:num | akn:heading | akn:marker | akn:content | akn:intro | akn:formula | akn:location | akn:role | akn:person | akn:date | akn:inline">
        <span>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </span>
    </xsl:template>

    <!-- Lists -->
    <xsl:template match="akn:list">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates select="akn:intro"/>
            <ol>
                <xsl:apply-templates
                        select="*[not(self::akn:intro | self::akn:wrapUp)]"/>
            </ol>
            <xsl:apply-templates select="akn:wrapUp"/>
        </div>
    </xsl:template>

    <xsl:template match="akn:point">
        <li>
            <xsl:call-template name="attributes"/>
            <span>
                <xsl:apply-templates select="akn:num"/>
            </span>
            <xsl:apply-templates select="*[not(self::akn:num)]"/>
        </li>
    </xsl:template>

    <xsl:template match="akn:p">
        <p>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <!-- Handle "normal" html elements -->
    <xsl:template
            match="akn:a | akn:abbr | akn:b | akn:br | akn:caption | akn:del | akn:i | akn:ins | akn:li | akn:ol | akn:sub | akn:sup | akn:td | akn:th | akn:tr | akn:u | akn:ul">
        <xsl:element name="{local-name()}">
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <!-- Ignore these elements when rendering html -->
    <xsl:template match="akn:meta"/>
    <xsl:template match="akn:docStage"/>
    <xsl:template match="akn:docProponent"/>
    <xsl:template match="akn:blockContainer[@refersTo='inhaltsuebersicht']"/>

    <xsl:template name="attributes">
        <!-- set the class to the ldml.de tag -->
        <xsl:attribute name="class">
            <xsl:value-of select="string-join((@class, replace(name(.), ':', '-')), ' ')"/>
        </xsl:attribute>
        <xsl:apply-templates select="@*[name()!='class']"/>
    </xsl:template>

    <!-- Convert all attributes of the xml to data attributes -->
    <xsl:template match="@*">
        <xsl:attribute name="data-{replace(name(), ':', '-')}">
            <xsl:value-of select="."/>
        </xsl:attribute>
    </xsl:template>

    <!-- Fallback for missing elements -->
    <xsl:template match="*">
        <div style="background-color: red">Unimplemented tag
            <xsl:value-of select="name(.)"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
