package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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

      assertThat(proprietary.getArt()).contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfArtIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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

      assertThat(proprietary.getArt()).isEmpty();
    }

    @Test
    void returnsTheArtAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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

      assertThat(proprietary.getTyp()).contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfTypIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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

      assertThat(proprietary.getTyp()).isEmpty();
    }

    @Test
    void returnsTheTypAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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
                  XmlMapper.toNode(
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

      assertThat(proprietary.getSubtyp()).contains("Anordnung des Bundespräsidenten");
    }

    @Test
    void returnsEmptyOptionalIfSubtypIsMissing() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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

      assertThat(proprietary.getSubtyp()).isEmpty();
    }

    @Test
    void returnsTheSubtypAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
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
}
