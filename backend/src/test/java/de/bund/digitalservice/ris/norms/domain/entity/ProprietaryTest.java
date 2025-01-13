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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                      <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:typ>gesetz</meta:typ>
              <meta:fna>754-28-1</meta:fna>
              <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getFna()).contains("754-28-1");
    }

    @Test
    void returnsEmptyOptionalIfFnaIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                      <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:typ>gesetz</meta:typ>
              <meta:form>stammform</meta:form>
              <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getFna()).isEmpty();
    }

    @Test
    void returnsTheFnaAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                      <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:typ>gesetz</meta:typ>
              <meta:fna>000-00-0</meta:fna>
              <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
              <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
              <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
              <ris:fna start="2001-01-01">333-33-3</ris:fna>
            </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">

                            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/">
                                <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                                <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                                <ris:fna start="2001-01-01">333-33-3</ris:fna>
                            </ris:legalDocML.de_metadaten>
                        </akn:proprietary>
                        """
        )
      );

      assertThat(proprietary.getMetadatenDs()).isNotEmpty();
    }

    @Test
    void returnsNodeEmpty() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                            <meta:typ>gesetz</meta:typ>
                            <meta:fna>000-00-0</meta:fna>
                            <meta:fassung>verkuendungsfassung</meta:fassung>
                        </meta:legalDocML.de_metadaten>
                        </akn:proprietary>
                        """
        )
      );

      assertThat(proprietary.getMetadatenDs()).isEmpty();
    }

    @Test
    void returnsNodeByCreatingIt() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                            <meta:typ>gesetz</meta:typ>
                            <meta:fna>000-00-0</meta:fna>
                            <meta:fassung>verkuendungsfassung</meta:fassung>
                        </meta:legalDocML.de_metadaten>
                        </akn:proprietary>
                        """
        )
      );

      assertThat(proprietary.getOrCreateMetadatenDs()).isNotNull();
    }
  }

  @Nested
  class Art {

    @Test
    void returnsTheArt() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:art>rechtsetzungsdokument</meta:art>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getArt(LocalDate.parse("2010-10-10")))
        .contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfArtIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
            <!-- Art is missing -->
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getArt(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheArtAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:art>rechtsetzungsdokument</meta:art>
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
              <ris:art start="1990-01-01" end="1994-12-31">regelungstext</ris:art>
              <ris:art start="1995-01-01" end="2000-12-31">begruendung</ris:art>
              <ris:art start="2001-01-01">anschreiben</ris:art>
            </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:typ>rechtsetzungsdokument</meta:typ>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getTyp(LocalDate.parse("2010-10-10")))
        .contains("rechtsetzungsdokument");
    }

    @Test
    void returnsEmptyOptionalIfTypIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
            <!-- Typ is missing -->
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getTyp(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheTypAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <meta:legalDocML.de_metadaten
                  xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/"
            >
              <meta:typ>gesetz</meta:typ>
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
              <ris:typ start="1990-01-01" end="1994-12-31">verordnung</ris:typ>
              <ris:typ start="1995-01-01" end="2000-12-31">begruendung</ris:typ>
              <ris:typ start="2001-01-01">satzung</ris:typ>
            </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
              <ris:subtyp>Anordnung des Bundespräsidenten</ris:subtyp>
           </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getSubtyp(LocalDate.parse("2010-10-10")))
        .contains("Anordnung des Bundespräsidenten");
    }

    @Test
    void returnsEmptyOptionalIfSubtypIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
            <!-- Subtyp is missing -->
            </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getSubtyp(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheSubtypAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
          <akn:proprietary
                        xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert"
          >
            <ris:legalDocML.de_metadaten
              xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
            >
              <ris:subtyp end="1989-12-31">Anordnung des Bundespräsidenten</ris:subtyp>
              <ris:subtyp start="1990-01-01" end="1994-12-31">Bekanntmachung vor einer Neufassung</ris:subtyp>
              <ris:subtyp start="1995-01-01" end="2000-12-31">Völkerrechtliche Vereinbarung</ris:subtyp>
              <ris:subtyp start="2001-01-01">Geschäftsordnung</ris:subtyp>
            </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                    eId="meta-1_proprietary-1"
                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                    source="attributsemantik-noch-undefiniert"
                  >
                    <ris:legalDocML.de_metadaten
                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                    >
                      <ris:bezeichnungInVorlage>Bezeichnung gemäß Vorlage</ris:bezeichnungInVorlage>
                   </ris:legalDocML.de_metadaten>
                  </akn:proprietary>
                  """
        )
      );

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2010-10-10")))
        .contains("Bezeichnung gemäß Vorlage");
    }

    @Test
    void returnsEmptyOptionalIfBezeichnungInVorlageIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                    eId="meta-1_proprietary-1"
                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                    source="attributsemantik-noch-undefiniert"
                  >
                    <ris:legalDocML.de_metadaten
                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                    >
                    <!-- BezeichnungInVorlage is missing -->
                    </ris:legalDocML.de_metadaten>
                  </akn:proprietary>
                  """
        )
      );

      assertThat(proprietary.getBezeichnungInVorlage(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheBezeichnungInVorlageAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                    eId="meta-1_proprietary-1"
                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                    source="attributsemantik-noch-undefiniert"
                  >
                    <ris:legalDocML.de_metadaten
                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                    >
                      <ris:bezeichnungInVorlage end="1989-12-31">Bezeichnung gemäß Vorlage 1</ris:bezeichnungInVorlage>
                      <ris:bezeichnungInVorlage start="1990-01-01" end="1994-12-31">Bezeichnung gemäß Vorlage 2</ris:bezeichnungInVorlage>
                      <ris:bezeichnungInVorlage start="1995-01-01" end="2000-12-31">Bezeichnung gemäß Vorlage 3</ris:bezeichnungInVorlage>
                      <ris:bezeichnungInVorlage start="2001-01-01">Bezeichnung gemäß Vorlage 4</ris:bezeichnungInVorlage>
                    </ris:legalDocML.de_metadaten>
                  </akn:proprietary>
                  """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                    eId="meta-1_proprietary-1"
                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                    source="attributsemantik-noch-undefiniert"
                                  >
                                    <ris:legalDocML.de_metadaten
                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                    >
                                      <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
                                   </ris:legalDocML.de_metadaten>
                                  </akn:proprietary>
                                  """
        )
      );

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"))).contains("SN,ÄN,ÜN");
    }

    @Test
    void returnsEmptyOptionalIfArtDerNormIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                    eId="meta-1_proprietary-1"
                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                    source="attributsemantik-noch-undefiniert"
                                  >
                                    <ris:legalDocML.de_metadaten
                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                    >
                                    <!-- ArtDerNorm is missing -->
                                    </ris:legalDocML.de_metadaten>
                                  </akn:proprietary>
                                  """
        )
      );

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheArtDerNormAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                    eId="meta-1_proprietary-1"
                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                    source="attributsemantik-noch-undefiniert"
                                  >
                                    <ris:legalDocML.de_metadaten
                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                    >
                                      <ris:artDerNorm end="1989-12-31">SN,ÄN,ÜN</ris:artDerNorm>
                                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN,ÄN</ris:artDerNorm>
                                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">SN,ÜN</ris:artDerNorm>
                                      <ris:artDerNorm start="2001-01-01">ÄN,ÜN</ris:artDerNorm>
                                    </ris:legalDocML.de_metadaten>
                                  </akn:proprietary>
                                  """
        )
      );

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
  class Staat {

    @Test
    void returnsStaat() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                    eId="meta-1_proprietary-1"
                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                    source="attributsemantik-noch-undefiniert"
                                                  >
                                                    <ris:legalDocML.de_metadaten
                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                    >
                                                      <ris:normgeber>DEU</ris:normgeber>
                                                   </ris:legalDocML.de_metadaten>
                                                  </akn:proprietary>
                                                  """
        )
      );

      assertThat(proprietary.getStaat(LocalDate.parse("2010-10-10"))).contains("DEU");
    }

    @Test
    void returnsEmptyOptionalIfStaatIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                    eId="meta-1_proprietary-1"
                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                    source="attributsemantik-noch-undefiniert"
                                                  >
                                                    <ris:legalDocML.de_metadaten
                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                    >
                                                    <!-- Normgeber is missing -->
                                                    </ris:legalDocML.de_metadaten>
                                                  </akn:proprietary>
                                                  """
        )
      );

      assertThat(proprietary.getStaat(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheStaatAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                    eId="meta-1_proprietary-1"
                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                    source="attributsemantik-noch-undefiniert"
                                                  >
                                                    <ris:legalDocML.de_metadaten
                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                    >
                                                      <ris:normgeber end="1989-12-31">DEU</ris:normgeber>
                                                      <ris:normgeber start="1990-01-01" end="1994-12-31">DDR</ris:normgeber>
                                                      <ris:normgeber start="1995-01-01" end="2000-12-31">BW</ris:normgeber>
                                                      <ris:normgeber start="2001-01-01">BY</ris:normgeber>
                                                    </ris:legalDocML.de_metadaten>
                                                  </akn:proprietary>
                                                  """
        )
      );

      assertThat(proprietary.getStaat(LocalDate.parse("1980-01-01"))).contains("DEU");

      assertThat(proprietary.getStaat(LocalDate.parse("1990-01-01"))).contains("DDR");
      assertThat(proprietary.getStaat(LocalDate.parse("1992-01-01"))).contains("DDR");
      assertThat(proprietary.getStaat(LocalDate.parse("1994-12-31"))).contains("DDR");

      assertThat(proprietary.getStaat(LocalDate.parse("1995-01-01"))).contains("BW");
      assertThat(proprietary.getStaat(LocalDate.parse("1998-01-01"))).contains("BW");
      assertThat(proprietary.getStaat(LocalDate.parse("2000-12-31"))).contains("BW");

      assertThat(proprietary.getStaat(LocalDate.parse("2001-01-01"))).contains("BY");
      assertThat(proprietary.getStaat(LocalDate.parse("2024-01-01"))).contains("BY");
    }
  }

  @Nested
  class BeschliessendesOrgan {

    @Test
    void returnsBeschliessendesOrgan() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                    eId="meta-1_proprietary-1"
                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                    source="attributsemantik-noch-undefiniert"
                                                                  >
                                                                    <ris:legalDocML.de_metadaten
                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                    >
                                                                      <ris:beschliessendesOrgan qualifizierteMehrheit="true">Bundestag</ris:beschliessendesOrgan>
                                                                   </ris:legalDocML.de_metadaten>
                                                                  </akn:proprietary>
                                                                  """
        )
      );

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2010-10-10")))
        .contains("Bundestag");
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2010-10-10")))
        .contains(true);
    }

    @Test
    void returnsEmptyOptionalIfBeschliessendesOrganIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                    eId="meta-1_proprietary-1"
                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                    source="attributsemantik-noch-undefiniert"
                                                                  >
                                                                    <ris:legalDocML.de_metadaten
                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                    >
                                                                    <!-- BeschliessendesOrgan is missing -->
                                                                    </ris:legalDocML.de_metadaten>
                                                                  </akn:proprietary>
                                                                  """
        )
      );

      assertThat(proprietary.getBeschliessendesOrgan(LocalDate.parse("2010-10-10"))).isEmpty();
      assertThat(proprietary.getQualifizierteMehrheit(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheBeschliessendesOrganAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                    eId="meta-1_proprietary-1"
                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                    source="attributsemantik-noch-undefiniert"
                                                                  >
                                                                    <ris:legalDocML.de_metadaten
                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                    >
                                                                      <ris:beschliessendesOrgan end="1989-12-31" qualifizierteMehrheit="true">Bundestag 1</ris:beschliessendesOrgan>
                                                                      <ris:beschliessendesOrgan start="1990-01-01" end="1994-12-31" qualifizierteMehrheit="true">Bundestag 2</ris:beschliessendesOrgan>
                                                                      <ris:beschliessendesOrgan start="1995-01-01" end="2000-12-31" qualifizierteMehrheit="true">Bundestag 3</ris:beschliessendesOrgan>
                                                                      <ris:beschliessendesOrgan start="2001-01-01" qualifizierteMehrheit="true">Bundestag 4</ris:beschliessendesOrgan>
                                                                    </ris:legalDocML.de_metadaten>
                                                                  </akn:proprietary>
                                                                  """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                                    eId="meta-1_proprietary-1"
                                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                                    source="attributsemantik-noch-undefiniert"
                                                                                  >
                                                                                    <ris:legalDocML.de_metadaten
                                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                                    >
                                                                                      <ris:beschliessendesOrgan end="1989-12-31" qualifizierteMehrheit="true">Bundestag 1</ris:beschliessendesOrgan>
                                                                                      <ris:beschliessendesOrgan start="1990-01-01" end="1994-12-31" qualifizierteMehrheit="false">Bundestag 2</ris:beschliessendesOrgan>
                                                                                      <ris:beschliessendesOrgan start="1995-01-01" end="2000-12-31" qualifizierteMehrheit="true">Bundestag 3</ris:beschliessendesOrgan>
                                                                                      <ris:beschliessendesOrgan start="2001-01-01" qualifizierteMehrheit="false">Bundestag 4</ris:beschliessendesOrgan>
                                                                                    </ris:legalDocML.de_metadaten>
                                                                                  </akn:proprietary>
                                                                                  """
        )
      );

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
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                    eId="meta-1_proprietary-1"
                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                    source="attributsemantik-noch-undefiniert"
                  >
                    <ris:legalDocML.de_metadaten
                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                    >
                      <ris:organisationsEinheit>Organisationseinheit 1</ris:organisationsEinheit>
                   </ris:legalDocML.de_metadaten>
                  </akn:proprietary>
                  """
        )
      );

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2010-10-10")))
        .contains("Organisationseinheit 1");
    }

    @Test
    void returnsEmptyOptionalIfOrganisationsEinheitIsMissing() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                    eId="meta-1_proprietary-1"
                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                    source="attributsemantik-noch-undefiniert"
                                                                  >
                                                                    <ris:legalDocML.de_metadaten
                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                    >
                                                                    <!-- OrganisationsEinheit is missing -->
                                                                    </ris:legalDocML.de_metadaten>
                                                                  </akn:proprietary>
                                                                  """
        )
      );

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.parse("2010-10-10"))).isEmpty();
    }

    @Test
    void returnsTheOrganisationsEinheitAtDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                                    eId="meta-1_proprietary-1"
                                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                    source="attributsemantik-noch-undefiniert"
                                                                  >
                                                                    <ris:legalDocML.de_metadaten
                                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                                    >
                                                                      <ris:organisationsEinheit end="1989-12-31">Organisationseinheit 1</ris:organisationsEinheit>
                                                                      <ris:organisationsEinheit start="1990-01-01" end="1994-12-31">Organisationseinheit 2</ris:organisationsEinheit>
                                                                      <ris:organisationsEinheit start="1995-01-01" end="2000-12-31">Organisationseinheit 3</ris:organisationsEinheit>
                                                                      <ris:organisationsEinheit start="2001-01-01">Organisationseinheit 4</ris:organisationsEinheit>
                                                                    </ris:legalDocML.de_metadaten>
                                                                  </akn:proprietary>
                                                                  """
        )
      );

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

  @Nested
  class EinzelelementArtDerNorm {

    @Test
    void returnsEinzelelementArtDerNorm() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                        <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                          eId="meta-1_proprietary-1"
                          GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                          source="attributsemantik-noch-undefiniert"
                        >
                          <ris:legalDocML.de_metadaten
                            xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                          >
                            <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
                                    <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                                <ris:artDerNorm>ÜN</ris:artDerNorm>
                            </ris:einzelelement>
                         </ris:legalDocML.de_metadaten>
                        </akn:proprietary>
                        """
        )
      );

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"), eid)).contains("ÜN");
    }

    @Test
    void returnsEmptyOptionalIfEinzelelementArtDerNormIsMissing() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                    eId="meta-1_proprietary-1"
                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                    source="attributsemantik-noch-undefiniert"
                                                  >
                                                    <ris:legalDocML.de_metadaten
                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                    >
                                                    <!-- ArtDerNorm is missing -->
                                                    </ris:legalDocML.de_metadaten>
                                                  </akn:proprietary>
                                                  """
        )
      );

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2010-10-10"), eid)).isEmpty();
    }

    @Test
    void returnsTheEinzelelementArtDerNormAtDate() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toNode(
          """
                                                  <akn:proprietary
          xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/"
                                                    eId="meta-1_proprietary-1"
                                                    GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                    source="attributsemantik-noch-undefiniert"
                                                  >
                                                    <ris:legalDocML.de_metadaten
                                                      xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/"
                                                    >
                                                      <ris:artDerNorm end="1989-12-31">SN,ÄN,ÜN</ris:artDerNorm>
                                                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN,ÄN</ris:artDerNorm>
                                                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">SN,ÜN</ris:artDerNorm>
                                                      <ris:artDerNorm start="2001-01-01">ÄN,ÜN</ris:artDerNorm>
                                                              <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                                                          <ris:artDerNorm end="1989-12-31">ÜN</ris:artDerNorm>
                                                          <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                                                          <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                                                          <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                                                      </ris:einzelelement>
                                                    </ris:legalDocML.de_metadaten>
                                                  </akn:proprietary>
                                                  """
        )
      );

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1980-01-01"), eid)).contains("ÜN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1990-01-01"), eid)).contains("SN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1992-01-01"), eid)).contains("SN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1994-12-31"), eid)).contains("SN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1995-01-01"), eid)).contains("ÄN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("1998-01-01"), eid)).contains("ÄN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2000-12-31"), eid)).contains("ÄN");

      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2001-01-01"), eid)).contains("ÜN");
      assertThat(proprietary.getArtDerNorm(LocalDate.parse("2024-01-01"), eid)).contains("ÜN");
    }
  }
}
