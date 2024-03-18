<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xpath-default-namespace="http://Inhaltsdaten.LegalDocML.de/1.4/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:math="http://www.w3.org/1998/Math/MathML"
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.4/"
                xmlns:local="http://example.com/ns/1.0"
                exclude-result-prefixes="xs math akn local">
  <xsl:param name="legislation-identifier" as="xs:string" />

  <xsl:output method="html" encoding="UTF-8" />

  <xsl:function name="local:display-class" as="xs:string">
    <xsl:param name="n" as="node()" />
    <xsl:choose>
      <xsl:when
              test="local-name($n) = ('article', 'paragraph', 'list', 'content', 'p')">
        <xsl:sequence select="xs:string('block')" />
      </xsl:when>
      <xsl:when
              test="local-name($n) = ('num', 'heading', 'marker', 'span', 'i', 'b')">
        <xsl:sequence select="xs:string('inline')" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:sequence select="xs:string('')" />
      </xsl:otherwise>
    </xsl:choose>
  </xsl:function>

  <xsl:function name="local:encode-for-uri" as="xs:string">
    <xsl:param name="uri" as="xs:string" />
    <xsl:variable name="uri"
                  select="replace($uri, 'ä', 'ae')" />
    <xsl:variable name="uri"
                  select="replace($uri, 'ö', 'oe')" />
    <xsl:variable name="uri"
                  select="replace($uri, 'ü', 'ue')" />
    <xsl:value-of select="$uri" />
  </xsl:function>

  <xsl:template match="/akomaNtoso">
    <xsl:apply-templates />
  </xsl:template>

  <!-- Handle document types -->
  <xsl:template match="act">
    <article>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </article>
  </xsl:template>

  <!-- We'll ignore some elements (for now) -->
  <xsl:template match="meta" />
  <xsl:template
          match="blockContainer[@refersTo='inhaltsuebersicht']" />

  <!-- Handle top level elements -->
  <xsl:template match="body | preface">
    <div>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </div>
  </xsl:template>

  <!-- Title -->
  <xsl:template match="p[docTitle]">
    <div>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </div>
  </xsl:template>

  <xsl:template match="longTitle">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="docTitle">
    <h1>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </h1>
  </xsl:template>

  <!-- Handle document structure -->
  <!-- TODO: At the moment we only handle <articles> -->
  <xsl:template match="article">
    <section>
      <xsl:attribute name="id">
        <xsl:value-of select="./heading/@eId" />
      </xsl:attribute>
      <h2 class="block">
        <xsl:attribute name="id">
          <xsl:value-of select="encode-for-uri(@GUID)" />
        </xsl:attribute>
        <a>
          <xsl:attribute name="href">
            <xsl:text>/legislation/</xsl:text>
            <xsl:value-of select="$legislation-identifier" />
            <xsl:text>/article/</xsl:text>
            <xsl:value-of select="encode-for-uri(@GUID)" />
          </xsl:attribute>
          <xsl:value-of select="./heading" />
        </a>
      </h2>
      <xsl:apply-templates
              select="node()[not(self::heading)]" />
    </section>
  </xsl:template>

  <xsl:template match="paragraph | content">
    <div>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </div>
  </xsl:template>

  <!-- Headings -->
  <xsl:template match="heading | subheading">
    <h2 class="block">
      <xsl:attribute name="id">
        <xsl:value-of select="local:encode-for-uri(@GUID)" />
      </xsl:attribute>
      <xsl:apply-templates />
    </h2>
  </xsl:template>

  <!-- Lists -->
  <xsl:template match="list[point/num]">
    <div>
      <xsl:apply-templates select="intro/node()" />
      <ol style="list-style:none">
        <xsl:call-template name="attrs" />
        <xsl:apply-templates
                select="node()[not(self::intro | self::wrapUp)]" />
      </ol>
      <xsl:apply-templates select="wrapUp/node()" />
    </div>
  </xsl:template>

  <xsl:template match="point">
    <li>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </li>
  </xsl:template>

  <!-- Num & Markers -->
  <xsl:template match="num">
    <span>
      <xsl:call-template name="attrs" />
      <xsl:if test="marker">
        <xsl:attribute name="data-marker">
          <xsl:value-of select="normalize-space(marker/@name)" />
        </xsl:attribute>
      </xsl:if>
      <xsl:apply-templates
              select="@*|node()[not(self::marker)]" />
    </span>
  </xsl:template>

  <!-- Basic elements -->
  <xsl:template match="p">
    <p>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </p>
  </xsl:template>

  <xsl:template
          match="a | abbr | b | br | caption | del | i | ins | li | ol | sub | sup | td | th | tr | u | ul">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="@*|node()" />
    </xsl:element>
  </xsl:template>

  <!-- Fallbacks -->
  <xsl:template match="@*">
    <xsl:attribute name="data-{replace(name(), ':', '-')}"><xsl:value-of
            select="." /></xsl:attribute>
  </xsl:template>

  <xsl:template match="*">
    <span>
      <xsl:call-template name="attrs" />
      <xsl:apply-templates />
    </span>
  </xsl:template>

  <xsl:template match="text()">
    <xsl:value-of select="." />
  </xsl:template>

  <xsl:template name="attrs">
    <xsl:attribute name="class">
      <xsl:value-of
              select="string-join((local-name(), @class, local:display-class(.)), ' ')" />
    </xsl:attribute>
    <xsl:apply-templates select="@*[name()!='class']" />
  </xsl:template>
</xsl:stylesheet>
