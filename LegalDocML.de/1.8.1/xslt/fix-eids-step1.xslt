<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
  xmlns:meta_regelungstext="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/"
  xmlns:meta_rechtsetzungsdokument="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/"
  exclude-result-prefixes="#all"
>
    <xsl:output method="xml"/>

  <!-- Creates new eIds for all elements that should have eIds and stores the old eIds in a oldEId attribute -->
  <!-- This attribute is needed to be able to replace references to the old eId with the new one in step2 -->
  <!-- It does not work correctly when an ordinal number is requested using @refersTo="ordinale-zaehlung-eid" -->

  <xsl:template mode="step1" match="@*|node()">
        <xsl:copy>
           <xsl:apply-templates mode="step1" select='@* | node()'/>
        </xsl:copy>
    </xsl:template>

    <xsl:template mode="step1" match="akn:article">
        <xsl:call-template name="updateEIdZaehlbezeichnung">
            <xsl:with-param name="partName">art</xsl:with-param>
            <xsl:with-param name="parentEId" />
        </xsl:call-template>
    </xsl:template>


    <xsl:template mode="step1" match="akn:quotedStructure//akn:article">
        <xsl:param name="parentEId" required="no"/>

        <xsl:call-template name="updateEIdZaehlbezeichnung">
            <xsl:with-param name="partName">art</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:a">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">a</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:abbr">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">abbr</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:paragraph">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdZaehlbezeichnung">
            <xsl:with-param name="partName">abs</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:section">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">abschnitt</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:activeModifications">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">activemod</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:mod">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ändbefehl</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:analysis">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">analysis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:attachment">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">anlage</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:attachments">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">anlagen</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:num">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">bezeichnung</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:affectedDocument">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">bezugsdoc</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:relatedDocument">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">bezugsdok</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:img">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">bild</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:block">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">block</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:blockContainer">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">blockcontainer</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:br">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">br</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:rref">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">bref</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:book">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">buch</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:hcontainer">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">container</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:date">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">datum</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:destination">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">destination</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docAuthority">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">docauth</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docDate">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">docdatum</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docProponent">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">docproponent</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docStage">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">docstadium</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docTitle">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">doctitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docType">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">doctype</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:longTitle">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">doktitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:docNumber">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">drucksachennr</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:preface">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">einleitung</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:tocItem">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">eintrag</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:eol">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">eol</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:eop">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">eop</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:eventRef">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ereignis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:citation">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ernorm</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:citations">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ernormen</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:foreign">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">exmarkup</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:b">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">fettschrift</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:role">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">fktbez</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:authorialNote">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">amtlfnote</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:formula">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">formel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRalias">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbralias</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRauthor">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrauthor</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRcountry">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrcountry</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRdate">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrdate</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRversionNumber">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrversionnumber</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRExpression">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrexpression</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRformat">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrformat</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRlanguage">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrlanguage</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRManifestation">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrmanifestation</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRname">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrname</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRnumber">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrnumber</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRsubtype">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrsubtype</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRthis">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrthis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRuri">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbruri</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:FRBRWork">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">frbrwork</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:temporalGroup">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">geltungszeitgr</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:temporalData">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">geltzeiten</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:forceMod">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">gelzeitaend</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:timeInterval">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">gelzeitintervall</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:force">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">gelzeitnachw</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:body">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">hauptteil</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:mainBody">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">hauptteil</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:identification">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ident</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:content">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">inhalt</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:tblock">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">inhaltabschnitt</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:toc">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">inhuebs</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:inline">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">inline</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:intro">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">intro</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:chapter">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">kapitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:mmod">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">kombändbefehl</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:i">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">kursiv</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:shortTitle">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">kurztitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:lifecycle">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">lebzykl</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:blockList">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">liste</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:ol">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listegeor</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:listIntroduction">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listeneing</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:item">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listenelem</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:li">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listenelem</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:point">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listenelem</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:listWrapUp">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listenschl</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:ul">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">listeunge</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:meta">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">meta</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:new">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">new</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:organization">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">org</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:location">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ort</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:passiveModifications">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">pasmod</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:person">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">person</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:preamble">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">präambel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:proprietary">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">proprietary</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:recital">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">präambelinh</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:recitals">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">präambeln</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:quotedStructure">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">quotstruct</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:quotedText">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">quottext</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:collectionBody">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">rdokhauptteil</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:ref">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ref</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:conclusions">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">schluss</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:wrapUp">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">schlusstext</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:signature">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">signatur</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:session">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">sitzung</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:source">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">source</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:span">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">span</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:componentRef">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">stfmverweis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:sub">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">sub</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:sup">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">sup</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:table">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tabelle</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:td">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tabelleinh</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:th">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tabellekopf</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:tr">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tabellereihe</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:caption">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tblue</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:part">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">teil</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:p">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">text</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:textualMod">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">textualmod</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:title">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">titel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:component">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tldokverweis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:components">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">tldokverweise</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:u">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">u</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:subsection">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">uabschnitt</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:heading">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">überschrift</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:subchapter">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">ukapitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:list">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">untergl</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:subtitle">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">utitel</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:documentRef">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">verweis</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:marker">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">marker</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:note">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="updateEIdOrdinal">
            <xsl:with-param name="partName">editfnote</xsl:with-param>
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="step1" match="akn:notes">
        <xsl:param name="parentEId" required="no"/>
        <xsl:call-template name="noEIdParentEIdPassthrough">
            <xsl:with-param name="parentEId" select="$parentEId"/>
        </xsl:call-template>
    </xsl:template>

    <!-- MISSING (as it is not important for the files we are converting): "Im Fall von akn:article wird die Positionsangabe in diesem Fall allerdings im Kontext des beinhaltenden Elternelements ermittelt, sondern innerhalb des gesamten Dokuments." -->

    <xsl:template name="updateEIdOrdinal">
        <xsl:param name="partName" required="yes"/>
        <xsl:param name="parentEId" required="no"/>

        <xsl:variable name="nodeName" select="name()"/>
        <xsl:variable name="partCount" select="count(preceding-sibling::node()[name() = $nodeName]) + 1"/>

        <xsl:variable name="newEId">
            <xsl:choose>
                <xsl:when test="$parentEId">
                    <xsl:value-of select="concat($parentEId, '_', $partName ,'-n', $partCount)"/>
                </xsl:when>
                <xsl:otherwise>
                   <xsl:value-of select="concat($partName ,'-n', $partCount)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:copy>
            <xsl:apply-templates mode="step1" select='@*'/>
            <xsl:attribute name="oldEId" select="@eId"/>
            <xsl:attribute name="eId" select="$newEId"/>

            <xsl:apply-templates mode="step1" select='node()'>
                <xsl:with-param name="parentEId" select="$newEId"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>

    <!-- The element itself does not get an eId, but it has child elements that should use the eid of the surrounding element as parentEId. -->
    <xsl:template name="noEIdParentEIdPassthrough">
        <xsl:param name="parentEId" required="no"/>

        <xsl:copy>
            <xsl:apply-templates mode="step1" select='@*'/>

            <xsl:apply-templates mode="step1" select='node()'>
                <xsl:with-param name="parentEId" select="$parentEId"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>


    <xsl:template name="updateEIdZaehlbezeichnung">
        <xsl:param name="partName" required="yes"/>
        <xsl:param name="parentEId" required="no"/>

        <!-- See lmdl_de 1.8.1 section 7.1.1 the point about "Zählbezeichnungsbasierte Positionsangabe" -->
        <xsl:variable name="num-step1"
                      select="normalize-space(lower-case(./akn:num/text()))"/>
        <xsl:variable name="num-step2-part1"
                 select="replace($num-step1, '(§ )|(art\. )|(art )|(artikel )', '')"/>
        <xsl:variable name="num-step2-part2"
                 select="replace($num-step2-part1, '(\()(\d+[a-z]*)(\))', '$2')"/>
        <xsl:variable name="num-step3" select="translate($num-step2-part2, '-_.', '~~~')"/>
        <xsl:variable name="partCount"
                      select="lower-case(encode-for-uri($num-step3))"/>

        <xsl:variable name="newEId">
            <xsl:choose>
                <xsl:when test="$parentEId">
                    <xsl:value-of select="concat($parentEId, '_', $partName ,'-z', $partCount)"/>
                </xsl:when>
                <xsl:otherwise>
                   <xsl:value-of select="concat($partName ,'-z', $partCount)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:copy>
            <xsl:apply-templates mode="step1" select='@*'/>
            <xsl:attribute name="oldEId" select="@eId"/>
            <xsl:attribute name="eId" select="$newEId"/>

            <xsl:apply-templates mode="step1" select='node()'>
                <xsl:with-param name="parentEId" select="$newEId"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
