package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlProcessor;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryTest {
  @Nested
  class Fna {
    @Test
    void returnsTheFna() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ>gesetz</meta:typ>
                          <meta:fna>754-28-1</meta:fna>
                          <meta:fassung>verkuendungsfassung</meta:fassung>
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getFna()).contains("754-28-1");
    }

    @Test
    void returnsEmptyOptionalIfFnaIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ>gesetz</meta:typ>
                          <meta:form>stammform</meta:form>
                          <meta:fassung>verkuendungsfassung</meta:fassung>
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getFna()).isEmpty();
    }

    @Test
    void returnsTheFnaAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ>gesetz</meta:typ>
                          <meta:fna>000-00-0</meta:fna>
                          <meta:fassung>verkuendungsfassung</meta:fassung>
                        </meta:legalDocML.de_metadaten>
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                          <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                          <meta:fna start="2001-01-01">333-33-3</meta:fna>
                        </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getFna(LocalDate.parse("1980-01-01"))).contains("000-00-0");

      assertThat(proprietary.getFna(LocalDate.parse("1990-01-01"))).contains("111-11-1");
      assertThat(proprietary.getFna(LocalDate.parse("1992-01-01"))).contains("111-11-1");
      assertThat(proprietary.getFna(LocalDate.parse("1994-12-31"))).contains("111-11-1");

      assertThat(proprietary.getFna(LocalDate.parse("1995-01-01"))).contains("222-22-2");
      assertThat(proprietary.getFna(LocalDate.parse("1998-01-01"))).contains("222-22-2");
      assertThat(proprietary.getFna(LocalDate.parse("2000-12-31"))).contains("222-22-2");

      assertThat(proprietary.getFna(LocalDate.parse("2001-01-01"))).contains("333-33-3");
      assertThat(proprietary.getFna(LocalDate.parse("2024-01-01"))).contains("333-33-3");
    }
  }

  @Nested
  class MetadatenDs {
    @Test
    void returnsNode() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                            <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">

                                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                    <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                    <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                    <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                                </meta:legalDocML.de_metadaten_ds>
                                            </akn:proprietary>
                                            """))
              .build();

      assertThat(proprietary.getMetadatenDs()).isNotEmpty();
    }

    @Test
    void returnsNodeEmpty() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                            <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                                <meta:typ>gesetz</meta:typ>
                                                <meta:fna>000-00-0</meta:fna>
                                                <meta:fassung>verkuendungsfassung</meta:fassung>
                                            </meta:legalDocML.de_metadaten>
                                            </akn:proprietary>
                                            """))
              .build();

      assertThat(proprietary.getMetadatenDs()).isEmpty();
    }

    @Test
    void returnsNodeByCreatingIt() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                            <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                                <meta:typ>gesetz</meta:typ>
                                                <meta:fna>000-00-0</meta:fna>
                                                <meta:fassung>verkuendungsfassung</meta:fassung>
                                            </meta:legalDocML.de_metadaten>
                                            </akn:proprietary>
                                            """))
              .build();

      assertThat(proprietary.getOrCreateMetadatenDs()).isNotNull();
    }
  }

  @Nested
  class Art {
    @Test
    void returnsTheArt() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:art>rechtsetzungsdokument</meta:art>
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getArt(LocalDate.parse("2010-10-10")))
          .contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfArtIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                        <!-- Art is missing -->
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getArt(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheArtAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:art>rechtsetzungsdokument</meta:art>
                        </meta:legalDocML.de_metadaten>
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:art start="1990-01-01" end="1994-12-31">regelungstext</meta:art>
                          <meta:art start="1995-01-01" end="2000-12-31">begruendung</meta:art>
                          <meta:art start="2001-01-01">anschreiben</meta:art>
                        </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getArt(LocalDate.parse("1980-01-01")))
          .contains("rechtsetzungsdokument");

      assertThat(proprietary.getArt(LocalDate.parse("1990-01-01"))).contains("regelungstext");
      assertThat(proprietary.getArt(LocalDate.parse("1992-01-01"))).contains("regelungstext");
      assertThat(proprietary.getArt(LocalDate.parse("1994-12-31"))).contains("regelungstext");

      assertThat(proprietary.getArt(LocalDate.parse("1995-01-01"))).contains("begruendung");
      assertThat(proprietary.getArt(LocalDate.parse("1998-01-01"))).contains("begruendung");
      assertThat(proprietary.getArt(LocalDate.parse("2000-12-31"))).contains("begruendung");

      assertThat(proprietary.getArt(LocalDate.parse("2001-01-01"))).contains("anschreiben");
      assertThat(proprietary.getArt(LocalDate.parse("2024-01-01"))).contains("anschreiben");
    }
  }

  @Nested
  class Typ {
    @Test
    void returnsTheTyp() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ>rechtsetzungsdokument</meta:typ>
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getTyp(LocalDate.parse("2010-10-10")))
          .contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfTypIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                        <!-- Typ is missing -->
                        </meta:legalDocML.de_metadaten>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getTyp(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheTypAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten
                          xmlns:meta="http://Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ>gesetz</meta:typ>
                        </meta:legalDocML.de_metadaten>
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:typ start="1990-01-01" end="1994-12-31">verordnung</meta:typ>
                          <meta:typ start="1995-01-01" end="2000-12-31">begruendung</meta:typ>
                          <meta:typ start="2001-01-01">satzung</meta:typ>
                        </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getTyp(LocalDate.parse("1980-01-01"))).contains("gesetz");

      assertThat(proprietary.getTyp(LocalDate.parse("1990-01-01"))).contains("verordnung");
      assertThat(proprietary.getTyp(LocalDate.parse("1992-01-01"))).contains("verordnung");
      assertThat(proprietary.getTyp(LocalDate.parse("1994-12-31"))).contains("verordnung");

      assertThat(proprietary.getTyp(LocalDate.parse("1995-01-01"))).contains("begruendung");
      assertThat(proprietary.getTyp(LocalDate.parse("1998-01-01"))).contains("begruendung");
      assertThat(proprietary.getTyp(LocalDate.parse("2000-12-31"))).contains("begruendung");

      assertThat(proprietary.getTyp(LocalDate.parse("2001-01-01"))).contains("satzung");
      assertThat(proprietary.getTyp(LocalDate.parse("2024-01-01"))).contains("satzung");
    }
  }

  @Nested
  class Subtyp {
    @Test
    void returnsTheSubtyp() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:subtyp>Anordnung des Bundespräsidenten</meta:subtyp>
                       </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getSubtyp(LocalDate.parse("2010-10-10")))
          .contains("Anordnung des Bundespräsidenten");
    }

    @Test
    void returnsEmptyOptionalIfSubtypIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                        <!-- Subtyp is missing -->
                        </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getSubtyp(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheSubtypAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                      <akn:proprietary
                        eId="meta-1_proprietary-1"
                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                        source="attributsemantik-noch-undefiniert"
                      >
                        <meta:legalDocML.de_metadaten_ds
                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                        >
                          <meta:subtyp end="1989-12-31">Anordnung des Bundespräsidenten</meta:subtyp>
                          <meta:subtyp start="1990-01-01" end="1994-12-31">Bekanntmachung vor einer Neufassung</meta:subtyp>
                          <meta:subtyp start="1995-01-01" end="2000-12-31">Völkerrechtliche Vereinbarung</meta:subtyp>
                          <meta:subtyp start="2001-01-01">Geschäftsordnung</meta:subtyp>
                        </meta:legalDocML.de_metadaten_ds>
                      </akn:proprietary>
                      """))
              .build();

      assertThat(proprietary.getSubtyp(LocalDate.parse("1980-01-01")))
          .contains("Anordnung des Bundespräsidenten");

      assertThat(proprietary.getSubtyp(LocalDate.parse("1990-01-01")))
          .contains("Bekanntmachung vor einer Neufassung");
      assertThat(proprietary.getSubtyp(LocalDate.parse("1992-01-01")))
          .contains("Bekanntmachung vor einer Neufassung");
      assertThat(proprietary.getSubtyp(LocalDate.parse("1994-12-31")))
          .contains("Bekanntmachung vor einer Neufassung");

      assertThat(proprietary.getSubtyp(LocalDate.parse("1995-01-01")))
          .contains("Völkerrechtliche Vereinbarung");
      assertThat(proprietary.getSubtyp(LocalDate.parse("1998-01-01")))
          .contains("Völkerrechtliche Vereinbarung");
      assertThat(proprietary.getSubtyp(LocalDate.parse("2000-12-31")))
          .contains("Völkerrechtliche Vereinbarung");

      assertThat(proprietary.getSubtyp(LocalDate.parse("2001-01-01"))).contains("Geschäftsordnung");
      assertThat(proprietary.getSubtyp(LocalDate.parse("2024-01-01"))).contains("Geschäftsordnung");
    }
  }

  @Nested
  class BezeichnungInVorlage {
    @Test
    void returnsTheBezeichnungInVorlage() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                      <akn:proprietary
                                        eId="meta-1_proprietary-1"
                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                        source="attributsemantik-noch-undefiniert"
                                      >
                                        <meta:legalDocML.de_metadaten_ds
                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                        >
                                          <meta:bezeichnungInVorlage>Bezeichnung gemäß Vorlage</meta:bezeichnungInVorlage>
                                       </meta:legalDocML.de_metadaten_ds>
                                      </akn:proprietary>
                                      """))
              .build();

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2010-10-10")))
          .contains("Bezeichnung gemäß Vorlage");
    }

    @Test
    void returnsEmptyOptionalIfBezeichnungInVorlageIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                      <akn:proprietary
                                        eId="meta-1_proprietary-1"
                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                        source="attributsemantik-noch-undefiniert"
                                      >
                                        <meta:legalDocML.de_metadaten_ds
                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                        >
                                        <!-- BezeichnungInVorlage is missing -->
                                        </meta:legalDocML.de_metadaten_ds>
                                      </akn:proprietary>
                                      """))
              .build();

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheBezeichnungInVorlageAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                      <akn:proprietary
                                        eId="meta-1_proprietary-1"
                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                        source="attributsemantik-noch-undefiniert"
                                      >
                                        <meta:legalDocML.de_metadaten_ds
                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                        >
                                          <meta:bezeichnungInVorlage end="1989-12-31">Bezeichnung gemäß Vorlage 1</meta:bezeichnungInVorlage>
                                          <meta:bezeichnungInVorlage start="1990-01-01" end="1994-12-31">Bezeichnung gemäß Vorlage 2</meta:bezeichnungInVorlage>
                                          <meta:bezeichnungInVorlage start="1995-01-01" end="2000-12-31">Bezeichnung gemäß Vorlage 3</meta:bezeichnungInVorlage>
                                          <meta:bezeichnungInVorlage start="2001-01-01">Bezeichnung gemäß Vorlage 4</meta:bezeichnungInVorlage>
                                        </meta:legalDocML.de_metadaten_ds>
                                      </akn:proprietary>
                                      """))
              .build();

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1980-01-01")))
          .contains("Bezeichnung gemäß Vorlage 1");

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1990-01-01")))
          .contains("Bezeichnung gemäß Vorlage 2");
      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1992-01-01")))
          .contains("Bezeichnung gemäß Vorlage 2");
      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1994-12-31")))
          .contains("Bezeichnung gemäß Vorlage 2");

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1995-01-01")))
          .contains("Bezeichnung gemäß Vorlage 3");
      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("1998-01-01")))
          .contains("Bezeichnung gemäß Vorlage 3");
      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2000-12-31")))
          .contains("Bezeichnung gemäß Vorlage 3");

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2001-01-01")))
          .contains("Bezeichnung gemäß Vorlage 4");
      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2024-01-01")))
          .contains("Bezeichnung gemäß Vorlage 4");
    }
  }

  @Nested
  class ArtDerNorm {
    @Test
    void returnsArtDerNorm() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                      <akn:proprietary
                                                        eId="meta-1_proprietary-1"
                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                        source="attributsemantik-noch-undefiniert"
                                                      >
                                                        <meta:legalDocML.de_metadaten_ds
                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                        >
                                                          <meta:artDerNorm>SN,ÄN,ÜN</meta:artDerNorm>
                                                       </meta:legalDocML.de_metadaten_ds>
                                                      </akn:proprietary>
                                                      """))
              .build();

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"))).contains("SN,ÄN,ÜN");
    }

    @Test
    void returnsEmptyOptionalIfArtDerNormIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                      <akn:proprietary
                                                        eId="meta-1_proprietary-1"
                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                        source="attributsemantik-noch-undefiniert"
                                                      >
                                                        <meta:legalDocML.de_metadaten_ds
                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                        >
                                                        <!-- ArtDerNorm is missing -->
                                                        </meta:legalDocML.de_metadaten_ds>
                                                      </akn:proprietary>
                                                      """))
              .build();

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheArtDerNormAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                      <akn:proprietary
                                                        eId="meta-1_proprietary-1"
                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                        source="attributsemantik-noch-undefiniert"
                                                      >
                                                        <meta:legalDocML.de_metadaten_ds
                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                        >
                                                          <meta:artDerNorm end="1989-12-31">SN,ÄN,ÜN</meta:artDerNorm>
                                                          <meta:artDerNorm start="1990-01-01" end="1994-12-31">SN,ÄN</meta:artDerNorm>
                                                          <meta:artDerNorm start="1995-01-01" end="2000-12-31">SN,ÜN</meta:artDerNorm>
                                                          <meta:artDerNorm start="2001-01-01">ÄN,ÜN</meta:artDerNorm>
                                                        </meta:legalDocML.de_metadaten_ds>
                                                      </akn:proprietary>
                                                      """))
              .build();

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1980-01-01"))).contains("SN,ÄN,ÜN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1990-01-01"))).contains("SN,ÄN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1992-01-01"))).contains("SN,ÄN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1994-12-31"))).contains("SN,ÄN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1995-01-01"))).contains("SN,ÜN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1998-01-01"))).contains("SN,ÜN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2000-12-31"))).contains("SN,ÜN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2001-01-01"))).contains("ÄN,ÜN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2024-01-01"))).contains("ÄN,ÜN");
    }
  }

  @Nested
  class Normgeber {
    @Test
    void returnsNormgeber() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                      <akn:proprietary
                                                                        eId="meta-1_proprietary-1"
                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                        source="attributsemantik-noch-undefiniert"
                                                                      >
                                                                        <meta:legalDocML.de_metadaten_ds
                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                        >
                                                                          <meta:normgeber>DEU</meta:normgeber>
                                                                       </meta:legalDocML.de_metadaten_ds>
                                                                      </akn:proprietary>
                                                                      """))
              .build();

      assertThat(proprietary.getNormgeber(LocalDate.parse("2010-10-10"))).contains("DEU");
    }

    @Test
    void returnsEmptyOptionalIfNormgeberIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                      <akn:proprietary
                                                                        eId="meta-1_proprietary-1"
                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                        source="attributsemantik-noch-undefiniert"
                                                                      >
                                                                        <meta:legalDocML.de_metadaten_ds
                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                        >
                                                                        <!-- Normgeber is missing -->
                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                      </akn:proprietary>
                                                                      """))
              .build();

      assertThat(proprietary.getNormgeber(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheNormgeberAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                      <akn:proprietary
                                                                        eId="meta-1_proprietary-1"
                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                        source="attributsemantik-noch-undefiniert"
                                                                      >
                                                                        <meta:legalDocML.de_metadaten_ds
                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                        >
                                                                          <meta:normgeber end="1989-12-31">DEU</meta:normgeber>
                                                                          <meta:normgeber start="1990-01-01" end="1994-12-31">DDR</meta:normgeber>
                                                                          <meta:normgeber start="1995-01-01" end="2000-12-31">BW</meta:normgeber>
                                                                          <meta:normgeber start="2001-01-01">BY</meta:normgeber>
                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                      </akn:proprietary>
                                                                      """))
              .build();

      assertThat(proprietary.getNormgeber(LocalDate.parse("1980-01-01"))).contains("DEU");

      assertThat(proprietary.getNormgeber(LocalDate.parse("1990-01-01"))).contains("DDR");
      assertThat(proprietary.getNormgeber(LocalDate.parse("1992-01-01"))).contains("DDR");
      assertThat(proprietary.getNormgeber(LocalDate.parse("1994-12-31"))).contains("DDR");

      assertThat(proprietary.getNormgeber(LocalDate.parse("1995-01-01"))).contains("BW");
      assertThat(proprietary.getNormgeber(LocalDate.parse("1998-01-01"))).contains("BW");
      assertThat(proprietary.getNormgeber(LocalDate.parse("2000-12-31"))).contains("BW");

      assertThat(proprietary.getNormgeber(LocalDate.parse("2001-01-01"))).contains("BY");
      assertThat(proprietary.getNormgeber(LocalDate.parse("2024-01-01"))).contains("BY");
    }
  }

  @Nested
  class BeschliessendesOrgan {
    @Test
    void returnsBeschliessendesOrgan() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                      <akn:proprietary
                                                                                        eId="meta-1_proprietary-1"
                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                      >
                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                        >
                                                                                          <meta:beschliessendesOrgan qualifizierteMehrheit="true">Bundestag</meta:beschliessendesOrgan>
                                                                                       </meta:legalDocML.de_metadaten_ds>
                                                                                      </akn:proprietary>
                                                                                      """))
              .build();

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2010-10-10")))
          .contains("Bundestag");
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2010-10-10")))
          .contains(true);
    }

    @Test
    void returnsEmptyOptionalIfBeschliessendesOrganIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                      <akn:proprietary
                                                                                        eId="meta-1_proprietary-1"
                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                      >
                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                        >
                                                                                        <!-- BeschliessendesOrgan is missing -->
                                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                                      </akn:proprietary>
                                                                                      """))
              .build();

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2010-10-10"))).isEmpty();
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheBeschliessendesOrganAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                      <akn:proprietary
                                                                                        eId="meta-1_proprietary-1"
                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                      >
                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                        >
                                                                                          <meta:beschliessendesOrgan end="1989-12-31" qualifizierteMehrheit="true">Bundestag 1</meta:beschliessendesOrgan>
                                                                                          <meta:beschliessendesOrgan start="1990-01-01" end="1994-12-31" qualifizierteMehrheit="true">Bundestag 2</meta:beschliessendesOrgan>
                                                                                          <meta:beschliessendesOrgan start="1995-01-01" end="2000-12-31" qualifizierteMehrheit="true">Bundestag 3</meta:beschliessendesOrgan>
                                                                                          <meta:beschliessendesOrgan start="2001-01-01" qualifizierteMehrheit="true">Bundestag 4</meta:beschliessendesOrgan>
                                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                                      </akn:proprietary>
                                                                                      """))
              .build();

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1980-01-01")))
          .contains("Bundestag 1");

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1990-01-01")))
          .contains("Bundestag 2");
      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1992-01-01")))
          .contains("Bundestag 2");
      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1994-12-31")))
          .contains("Bundestag 2");

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1995-01-01")))
          .contains("Bundestag 3");
      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("1998-01-01")))
          .contains("Bundestag 3");
      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2000-12-31")))
          .contains("Bundestag 3");

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2001-01-01")))
          .contains("Bundestag 4");
      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2024-01-01")))
          .contains("Bundestag 4");
    }

    @Test
    void returnsTheBeschliessendesOrganQualifizierteMehrheitAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                                      <akn:proprietary
                                                                                                        eId="meta-1_proprietary-1"
                                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                                      >
                                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                                        >
                                                                                                          <meta:beschliessendesOrgan end="1989-12-31" qualifizierteMehrheit="true">Bundestag 1</meta:beschliessendesOrgan>
                                                                                                          <meta:beschliessendesOrgan start="1990-01-01" end="1994-12-31" qualifizierteMehrheit="false">Bundestag 2</meta:beschliessendesOrgan>
                                                                                                          <meta:beschliessendesOrgan start="1995-01-01" end="2000-12-31" qualifizierteMehrheit="true">Bundestag 3</meta:beschliessendesOrgan>
                                                                                                          <meta:beschliessendesOrgan start="2001-01-01" qualifizierteMehrheit="false">Bundestag 4</meta:beschliessendesOrgan>
                                                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                                                      </akn:proprietary>
                                                                                                      """))
              .build();

      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1980-01-01")))
          .contains(true);

      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1990-01-01")))
          .contains(false);
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1992-01-01")))
          .contains(false);
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1994-12-31")))
          .contains(false);

      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1995-01-01")))
          .contains(true);
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("1998-01-01")))
          .contains(true);
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2000-12-31")))
          .contains(true);

      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2001-01-01")))
          .contains(false);
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2024-01-01")))
          .contains(false);
    }
  }

  @Nested
  class OrganisationsEinheit {
    @Test
    void returnsOrganisationsEinheit() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                      <akn:proprietary
                                        eId="meta-1_proprietary-1"
                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                        source="attributsemantik-noch-undefiniert"
                                      >
                                        <meta:legalDocML.de_metadaten_ds
                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                        >
                                          <meta:organisationsEinheit>Organisationseinheit 1</meta:organisationsEinheit>
                                       </meta:legalDocML.de_metadaten_ds>
                                      </akn:proprietary>
                                      """))
              .build();

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2010-10-10")))
          .contains("Organisationseinheit 1");
    }

    @Test
    void returnsEmptyOptionalIfOrganisationsEinheitIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                      <akn:proprietary
                                                                                        eId="meta-1_proprietary-1"
                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                      >
                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                        >
                                                                                        <!-- OrganisationsEinheit is missing -->
                                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                                      </akn:proprietary>
                                                                                      """))
              .build();

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheOrganisationsEinheitAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlProcessor.toNode(
                      """
                                                                                      <akn:proprietary
                                                                                        eId="meta-1_proprietary-1"
                                                                                        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                        source="attributsemantik-noch-undefiniert"
                                                                                      >
                                                                                        <meta:legalDocML.de_metadaten_ds
                                                                                          xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/"
                                                                                        >
                                                                                          <meta:organisationsEinheit end="1989-12-31">Organisationseinheit 1</meta:organisationsEinheit>
                                                                                          <meta:organisationsEinheit start="1990-01-01" end="1994-12-31">Organisationseinheit 2</meta:organisationsEinheit>
                                                                                          <meta:organisationsEinheit start="1995-01-01" end="2000-12-31">Organisationseinheit 3</meta:organisationsEinheit>
                                                                                          <meta:organisationsEinheit start="2001-01-01">Organisationseinheit 4</meta:organisationsEinheit>
                                                                                        </meta:legalDocML.de_metadaten_ds>
                                                                                      </akn:proprietary>
                                                                                      """))
              .build();

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1980-01-01")))
          .contains("Organisationseinheit 1");

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1990-01-01")))
          .contains("Organisationseinheit 2");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1992-01-01")))
          .contains("Organisationseinheit 2");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1994-12-31")))
          .contains("Organisationseinheit 2");

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1995-01-01")))
          .contains("Organisationseinheit 3");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("1998-01-01")))
          .contains("Organisationseinheit 3");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2000-12-31")))
          .contains("Organisationseinheit 3");

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2001-01-01")))
          .contains("Organisationseinheit 4");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2024-01-01")))
          .contains("Organisationseinheit 4");
    }
  }
}
