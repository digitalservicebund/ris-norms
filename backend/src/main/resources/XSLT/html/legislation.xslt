<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
        xmlns:math="http://www.w3.org/1998/Math/MathML"
        exclude-result-prefixes="#all"
>
    <xsl:import href="./legislation-metadata.xslt"/>

    <xsl:param name="show-metadata"/>

    <xsl:output method="html" version="5" include-content-type="no" encoding="utf-8" indent="yes"/>

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

    <!-- Priority template for akn:longTitle within akn:quotedStructure -->
    <xsl:template match="akn:quotedStructure//akn:longTitle" priority="2">
        <span>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </span>
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
            <xsl:apply-templates select="text()|*[not(self::akn:num|self::akn:heading)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:article">
        <section>
            <xsl:call-template name="attributes"/>
            <h3>
                <xsl:apply-templates select="akn:num"/>
                <xsl:apply-templates select="akn:heading"/>
            </h3>
            <xsl:apply-templates select="text()|*[not(self::akn:num|self::akn:heading)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:paragraph">
        <section>
            <xsl:call-template name="attributes"/>
            <h4>
                <xsl:apply-templates select="akn:num"/>
            </h4>
            <xsl:apply-templates select="text()|*[not(self::akn:num)]"/>
        </section>
    </xsl:template>

    <xsl:template match="akn:blockContainer | akn:signature | akn:wrapUp | akn:block">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template
            match="akn:longTitle/akn:p | akn:shortTitle | akn:docTitle | akn:num | akn:heading | akn:subheading | akn:marker | akn:content | akn:intro | akn:formula | akn:location | akn:role | akn:person | akn:inline | akn:affectedDocument | akn:organization | akn:ref| akn:rref">
        <span>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </span>
    </xsl:template>

    <xsl:template
            match=" akn:mod">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <!-- handle the quote characters of the attribute-group akn:quote (used by e.g. quotedText and quotedStructure)-->
    <xsl:template name="quote">
        <span class="akn-quote-startQuote">
            <xsl:value-of select="@startQuote"/>
        </span>
        <xsl:apply-templates/>
        <span class="akn-quote-endQuote">
            <xsl:choose>
                <xsl:when test="@endQuote">
                    <xsl:value-of select="@endQuote"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="@startQuote"/>
                </xsl:otherwise>
            </xsl:choose>
        </span>
    </xsl:template>

    <xsl:template match="akn:quotedText">
        <span>
            <xsl:call-template name="attributes"/>
            <xsl:call-template name="quote"/>
        </span>
    </xsl:template>

    <xsl:template match="akn:quotedStructure">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:call-template name="quote"/>
        </div>
    </xsl:template>

    <!--
        Paragraph elements - usually we'd want them to show up as <p> elements in HTML too, but not everything
        that can be contained in an akn:p is also allowed inside <p>. Rendering as a <div> for now so we at least
        keep the block-appearance.
    -->
    <xsl:template match="akn:p">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <!--
        We normally create a li-Element for each akn:point. A li-Element can only be nested within some specific HTML elements.
        A akn:quotedStructure is not rendered as one of these. Therefor we need to handle akn:point in a special way when it is directly nested in a akn:quotedStructure.
    -->
    <xsl:template match="akn:quotedStructure/akn:point" priority="2">
        <span>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </span>
    </xsl:template>


    <xsl:template
            match="akn:date">
        <time>
            <xsl:call-template name="attributes"/>
            <xsl:attribute name="datetime" select="@date"/>
            <xsl:apply-templates/>
        </time>
    </xsl:template>

    <!-- Lists -->
    <xsl:template match="akn:list">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates select="akn:intro"/>
            <ol>
                <xsl:apply-templates
                        select="text()|*[not(self::akn:intro|self::akn:wrapUp)]"/>
            </ol>
            <xsl:apply-templates select="akn:wrapUp"/>
        </div>
    </xsl:template>

    <xsl:template match="akn:point">
        <li>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </li>
    </xsl:template>

    <xsl:template match="akn:a[@href]">
        <a href="{@href}">
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </a>
    </xsl:template>

    <!-- Handle "normal" html elements -->
    <xsl:template
            match="akn:a | akn:abbr | akn:b | akn:br | akn:caption | akn:i | akn:sub | akn:sup | akn:td | akn:th | akn:tr | akn:u | akn:span | akn:table">
        <xsl:element name="{local-name()}">
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

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

    <xsl:template match="akn:citations | akn:citation | akn:recitals | akn:recital | akn:foreign | akn:tblock | akn:toc | akn:tocItem">
        <div>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="akn:book | akn:part | akn:chapter | akn:subchapter | akn:subsection | akn:title | akn:subtitle">
        <section>
            <xsl:call-template name="attributes"/>
            <xsl:apply-templates/>
        </section>
    </xsl:template>

    <xsl:template match="akn:documentRef | akn:componentRef">
        <div>
            <xsl:call-template name="attributes"/>
            Ref: <xsl:value-of select="@eli"/>
        </div>
    </xsl:template>

    <!-- MathML -->
    <xsl:template match="math:math">
        <xsl:copy-of select="."/>
    </xsl:template>

    <!-- Ignore these elements when rendering html -->
    <xsl:template match="akn:meta"/>
    <xsl:template match="akn:docStage"/>
    <xsl:template match="akn:docProponent"/>

    <!--<xsl:template match="text()">-->
    <!--    <xsl:value-of select="normalize-space()"/>-->
    <!--</xsl:template>-->

    <!-- Fallback for missing elements -->
    <xsl:template match="*">
        <div style="background-color: red">Unimplemented tag
            <xsl:value-of select="name(.)"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
