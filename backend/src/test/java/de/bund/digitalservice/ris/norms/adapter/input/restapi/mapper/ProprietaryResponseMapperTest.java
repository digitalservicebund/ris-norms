package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryResponseMapperTest {

  @Nested
  class fromProprietary {

    @Test
    void convertsWithMetadataPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
              <meta:typ>gesetz</meta:typ>
              <meta:fna>754-28-1</meta:fna>
              <meta:art>rechtsetzungsdokument</meta:art>
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
              <meta:federfuehrung>
                <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
              </meta:federfuehrung>
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
              <ris:subtyp>Subtyp</ris:subtyp>
              <ris:bezeichnungInVorlage>bezeichnungInVorlage</ris:bezeichnungInVorlage>
              <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
              <ris:beschliessendesOrgan qualifizierteMehrheit="true">organ</ris:beschliessendesOrgan>
              <ris:normgeber>DE</ris:normgeber>
              <ris:organisationsEinheit>Organisationseinheit</ris:organisationsEinheit>
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietary(
        proprietary,
        LocalDate.parse("2002-10-02")
      );

      // Then
      assertThat(responseSchema.getTyp()).isEqualTo("gesetz");
      assertThat(responseSchema.getFna()).isEqualTo("754-28-1");
      assertThat(responseSchema.getArt()).isEqualTo("rechtsetzungsdokument");

      assertThat(responseSchema.getRessort()).isEqualTo("Bundesministerium der Justiz");

      assertThat(responseSchema.getSubtyp()).isEqualTo("Subtyp");
      assertThat(responseSchema.getBezeichnungInVorlage()).isEqualTo("bezeichnungInVorlage");
      assertThat(responseSchema.getArtDerNorm()).isEqualTo("SN,ÄN,ÜN");
      assertThat(responseSchema.getBeschliessendesOrgan()).isEqualTo("organ");
      assertThat(responseSchema.getQualifizierteMehrheit()).isTrue();
      assertThat(responseSchema.getStaat()).isEqualTo("DE");
      assertThat(responseSchema.getOrganisationsEinheit()).isEqualTo("Organisationseinheit");
    }

    @Test
    void convertsWithMetadataAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
            </meta:legalDocML.de_metadaten>
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
            </meta:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietary(
        proprietary,
        LocalDate.parse("2002-10-02")
      );

      // Then
      assertThat(responseSchema.getTyp()).isNull();
      assertThat(responseSchema.getFna()).isNull();
      assertThat(responseSchema.getArt()).isNull();
      assertThat(responseSchema.getRessort()).isNull();
      assertThat(responseSchema.getSubtyp()).isNull();
      assertThat(responseSchema.getBezeichnungInVorlage()).isNull();
      assertThat(responseSchema.getArtDerNorm()).isNull();
      assertThat(responseSchema.getBeschliessendesOrgan()).isNull();
      assertThat(responseSchema.getQualifizierteMehrheit()).isNull();
      assertThat(responseSchema.getStaat()).isNull();
      assertThat(responseSchema.getOrganisationsEinheit()).isNull();
    }

    @Test
    void convertsWithParentAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietary(
        proprietary,
        LocalDate.parse("2002-10-02")
      );

      // Then
      assertThat(responseSchema.getTyp()).isNull();
      assertThat(responseSchema.getFna()).isNull();
      assertThat(responseSchema.getArt()).isNull();
      assertThat(responseSchema.getRessort()).isNull();
      assertThat(responseSchema.getSubtyp()).isNull();
      assertThat(responseSchema.getBezeichnungInVorlage()).isNull();
      assertThat(responseSchema.getArtDerNorm()).isNull();
      assertThat(responseSchema.getBeschliessendesOrgan()).isNull();
      assertThat(responseSchema.getQualifizierteMehrheit()).isNull();
      assertThat(responseSchema.getStaat()).isNull();
      assertThat(responseSchema.getOrganisationsEinheit()).isNull();
    }
  }

  @Nested
  class fromProprietarySingleElement {

    @Test
    void convertsWithMetadataPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
                  <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-1_abschnitt-0_art-1")
      );

      // Then
      assertThat(responseSchema.getArtDerNorm()).isEqualTo("SN");
    }

    @Test
    @SuppressWarnings("java:S5976") // so that tests are more readable and parameterization not needed.
    void convertsWithMetadataAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
                  <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-1_abschnitt-0_art-1")
      );

      // Then
      assertThat(responseSchema.getArtDerNorm()).isNull();
    }

    @Test
    void convertsWithSingleElementAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/">
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-1_abschnitt-0_art-1")
      );

      // Then
      assertThat(responseSchema.getArtDerNorm()).isNull();
    }

    @Test
    void convertsWithParentAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-1_proprietary-1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-1_abschnitt-0_art-1")
      );

      // Then
      assertThat(responseSchema.getArtDerNorm()).isNull();
    }
  }
}
