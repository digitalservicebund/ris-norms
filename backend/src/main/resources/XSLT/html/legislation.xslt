<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/">

    <xsl:param name="show-metadata"/>

    <xsl:import href="./legislation-metadata.xslt"/>

    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/akn:akomaNtoso">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="akn:act">
        <article>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </article>
    </xsl:template>

    <xsl:template match="akn:preamble | akn:body | akn:conclusions">
        <section>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </section>
    </xsl:template>

    <xsl:template match="akn:preface">
        <section>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </section>
        <xsl:if test="$show-metadata">
            <xsl:call-template name="metadata"/>
        </xsl:if>
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

    <xsl:template match="akn:blockContainer | akn:signature | akn:wrapUp | akn:block">
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
    <!-- Temporarily disabled because it breaks templates in the metadata table -->
    <!--<xsl:template match="*">-->
    <!--    <div style="background-color: red">Unimplemented tag-->
    <!--        <xsl:value-of select="name(.)"/>-->
    <!--    </div>-->
    <!--</xsl:template>-->
</xsl:stylesheet>
