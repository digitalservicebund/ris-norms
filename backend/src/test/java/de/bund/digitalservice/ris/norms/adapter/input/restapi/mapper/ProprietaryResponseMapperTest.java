package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryResponseMapperTest {

  @Test
  void fromRahmenMetadata() {
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    var responseSchema = ProprietaryResponseMapper.fromRahmenMetadata(norm.getRahmenMetadata());
    assertThat(responseSchema.getTyp()).isEqualTo("gesetz");
    assertThat(responseSchema.getFna()).isEqualTo("754-28-1");
    assertThat(responseSchema.getArt()).isEqualTo("regelungstext");
    assertThat(responseSchema.getRessort()).isEqualTo("Bundesministerium der Justiz");
    assertThat(responseSchema.getSubtyp()).isEqualTo("rechtsverordnung");
    assertThat(responseSchema.getBezeichnungInVorlage()).isEqualTo("Bezeichnung gemäß Vorlage");
    assertThat(responseSchema.getArtDerNorm()).isEqualTo("SN,ÄN,ÜN");
    assertThat(responseSchema.getBeschliessendesOrgan()).isEqualTo("Bundestag");
    assertThat(responseSchema.getQualifizierteMehrheit()).isTrue();
    assertThat(responseSchema.getStaat()).isEqualTo("DEU");
    assertThat(responseSchema.getOrganisationsEinheit()).isEqualTo("Aktuelle Organisationseinheit");
  }

  @Nested
  class fromProprietarySingleElement {

    @Test
    void convertsWithMetadataPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
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
        new EId("hauptteil-n1_abschnitt-n0_art-n1")
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
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-n1_abschnitt-n0_art-n1")
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
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8/">
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-n1_abschnitt-n0_art-n1")
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
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );

      // When
      var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
        proprietary,
        new EId("hauptteil-n1_abschnitt-n0_art-n1")
      );

      // Then
      assertThat(responseSchema.getArtDerNorm()).isNull();
    }
  }
}
