package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class ProprietaryTest {

  @Nested
  class getMetadataValue {

    @Test
    void returnsValues() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
              <redok:fna>754-28-1</redok:fna>
              <redok:gesta>i am gesta</redok:gesta>
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
              <regtxt:typ>gesetz</regtxt:typ>
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <ris:subtyp>new-subtyp</ris:subtyp>
              <ris:bezeichnungInVorlage>new-bezeichnungInVorlage</ris:bezeichnungInVorlage>
              <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
              <ris:beschliessendesOrgan qualifizierteMehrheit="true">organ 1</ris:beschliessendesOrgan>
              <ris:normgeber>DE</ris:normgeber>
              <ris:organisationsEinheit>Andere Organisationseinheit</ris:organisationsEinheit>
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("754-28-1");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("i am gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("organ 1");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "true"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Andere Organisationseinheit"
      );
    }

    @Test
    void returnsEmptyOptionalIfMissingButParentPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
              <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
                eId="meta-n1_proprietary-n1"
                GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                source="attributsemantik-noch-undefiniert">
                <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
                </redok:legalDocML.de_metadaten>
                <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
                </regtxt:legalDocML.de_metadaten>
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                </ris:legalDocML.de_metadaten>
              </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }

    @Test
    void returnsEmptyOptionalIfMissingBecauseParentNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
              <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
                eId="meta-n1_proprietary-n1"
                GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                source="attributsemantik-noch-undefiniert">
              </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }
  }

  @Nested
  class getMetadataValueEid {

    @Test
    void returnsValueArtDerNormOfSingleElement() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                  </ris:einzelelement>
               </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );
      assertThat(
        proprietary.getMetadataValue(
          Metadata.ART_DER_NORM,
          new EId("hauptteil-n1_abschnitt-n0_art-n1")
        )
      ).contains("SN");
    }

    @Test
    @SuppressWarnings("java:S5976") // so that tests are more readable and parameterization not needed.
    void returnsOptionalEmptyIfArtDerNormNotPresentButParentPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                  </ris:einzelelement>
               </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );
      assertThat(
        proprietary.getMetadataValue(
          Metadata.ART_DER_NORM,
          new EId("hauptteil-n1_abschnitt-n0_art-n1")
        )
      ).isEmpty();
    }

    @Test
    void returnsOptionalEmptyIfArtDerNormNotPresentBecauseEinzelElementNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
               </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );
      assertThat(
        proprietary.getMetadataValue(
          Metadata.ART_DER_NORM,
          new EId("hauptteil-n1_abschnitt-n0_art-n1")
        )
      ).isEmpty();
    }

    @Test
    void returnsOptionalEmptyIfArtDerNormNotPresentBecauseParentNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );
      assertThat(
        proprietary.getMetadataValue(
          Metadata.ART_DER_NORM,
          new EId("hauptteil-n1_abschnitt-n0_art-n1")
        )
      ).isEmpty();
    }
  }

  @Nested
  class getRessort {

    @Test
    void returnsValueByDate() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
             <breg:legalDocML.de_metadaten xmlns:breg="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/">
                <breg:federfuehrung>
                    <breg:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</breg:federfuehrend>
                    <breg:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</breg:federfuehrend>
                </breg:federfuehrung>
             </breg:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getRessort(LocalDate.parse("1990-01-01"))).isEmpty();
      assertThat(proprietary.getRessort(LocalDate.parse("2002-10-01"))).contains(
        "Bundesministerium der Justiz"
      );
      assertThat(proprietary.getRessort(LocalDate.parse("2022-12-01"))).contains(
        "Bundesministerium des Innern und für Heimat"
      );
      assertThat(proprietary.getRessort(LocalDate.parse("2024-06-18"))).contains(
        "Bundesministerium des Innern und für Heimat"
      );
    }

    @Test
    @SuppressWarnings("java:S5976") // so that tests are more readable and parameterization not needed.
    void returnsOptionalEmptyIfRessortByDateNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <breg:legalDocML.de_metadaten xmlns:breg="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/">
                  <breg:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</breg:federfuehrend>
              </breg:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getRessort(LocalDate.MAX)).isEmpty();
    }

    @Test
    void returnsOptionalEmptyIfRessortBecauseNoChildsPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <breg:legalDocML.de_metadaten xmlns:breg="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/">
              </breg:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getRessort(LocalDate.MAX)).isEmpty();
    }

    @Test
    void returnsOptionalEmptyIfRessortBecauseParentNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getRessort(LocalDate.MAX)).isEmpty();
    }
  }

  @Nested
  class setMetadataValue {

    @Test
    void setNewValuesMetadataPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
              <redok:fna>754-28-1</redok:fna>
              <redok:gesta>i am gesta</redok:gesta>
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
              <regtxt:typ>gesetz</regtxt:typ>
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <ris:subtyp>new-subtyp</ris:subtyp>
              <ris:bezeichnungInVorlage>new-bezeichnungInVorlage</ris:bezeichnungInVorlage>
              <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
              <ris:beschliessendesOrgan qualifizierteMehrheit="true">organ 1</ris:beschliessendesOrgan>
              <ris:normgeber>DE</ris:normgeber>
              <ris:organisationsEinheit>Andere Organisationseinheit</ris:organisationsEinheit>
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("754-28-1");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("i am gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("organ 1");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "true"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Andere Organisationseinheit"
      );

      proprietary.setMetadataValue(Metadata.TYP, "new-gesetz");
      proprietary.setMetadataValue(Metadata.FNA, "new-fna");
      proprietary.setMetadataValue(Metadata.GESTA, "new-gesta");
      proprietary.setMetadataValue(Metadata.SUBTYP, "new-subtyp-updated");
      proprietary.setMetadataValue(
        Metadata.BEZEICHNUNG_IN_VORLAGE,
        "new-bezeichnungInVorlage-updated"
      );
      proprietary.setMetadataValue(Metadata.ART_DER_NORM, "new-SN,ÄN,ÜN");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, "new-organ 1");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR, "false");
      proprietary.setMetadataValue(Metadata.STAAT, "new-DE");
      proprietary.setMetadataValue(
        Metadata.ORGANISATIONS_EINHEIT,
        "new-Andere Organisationseinheit"
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("new-gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp-updated");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage-updated"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("new-SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains(
        "new-organ 1"
      );
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "false"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("new-DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "new-Andere Organisationseinheit"
      );
    }

    @Test
    void setNewValuesMetadataAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();

      proprietary.setMetadataValue(Metadata.FNA, "new-fna");
      proprietary.setMetadataValue(Metadata.TYP, "new-gesetz");
      proprietary.setMetadataValue(Metadata.FNA, "new-fna");
      proprietary.setMetadataValue(Metadata.GESTA, "new-gesta");
      proprietary.setMetadataValue(Metadata.SUBTYP, "new-subtyp-updated");
      proprietary.setMetadataValue(
        Metadata.BEZEICHNUNG_IN_VORLAGE,
        "new-bezeichnungInVorlage-updated"
      );
      proprietary.setMetadataValue(Metadata.ART_DER_NORM, "new-SN,ÄN,ÜN");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, "new-organ 1");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR, "false");
      proprietary.setMetadataValue(Metadata.STAAT, "new-DE");
      proprietary.setMetadataValue(
        Metadata.ORGANISATIONS_EINHEIT,
        "new-Andere Organisationseinheit"
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("new-gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp-updated");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage-updated"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("new-SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains(
        "new-organ 1"
      );
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "false"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("new-DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "new-Andere Organisationseinheit"
      );
    }

    @Test
    void setNewValuesParentAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();

      proprietary.setMetadataValue(Metadata.FNA, "new-fna");
      proprietary.setMetadataValue(Metadata.TYP, "new-gesetz");
      proprietary.setMetadataValue(Metadata.FNA, "new-fna");
      proprietary.setMetadataValue(Metadata.GESTA, "new-gesta");
      proprietary.setMetadataValue(Metadata.SUBTYP, "new-subtyp-updated");
      proprietary.setMetadataValue(
        Metadata.BEZEICHNUNG_IN_VORLAGE,
        "new-bezeichnungInVorlage-updated"
      );
      proprietary.setMetadataValue(Metadata.ART_DER_NORM, "new-SN,ÄN,ÜN");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, "new-organ 1");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR, "false");
      proprietary.setMetadataValue(Metadata.STAAT, "new-DE");
      proprietary.setMetadataValue(
        Metadata.ORGANISATIONS_EINHEIT,
        "new-Andere Organisationseinheit"
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("new-gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp-updated");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage-updated"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("new-SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains(
        "new-organ 1"
      );
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "false"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("new-DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "new-Andere Organisationseinheit"
      );
    }

    @Test
    void setValuesToEmptyStrings() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
              <redok:fna>754-28-1</redok:fna>
              <redok:gesta>i am gesta</redok:gesta>
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
              <regtxt:typ>gesetz</regtxt:typ>
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <ris:subtyp>new-subtyp</ris:subtyp>
              <ris:bezeichnungInVorlage>new-bezeichnungInVorlage</ris:bezeichnungInVorlage>
              <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
              <ris:beschliessendesOrgan qualifizierteMehrheit="true">organ 1</ris:beschliessendesOrgan>
              <ris:normgeber>DE</ris:normgeber>
              <ris:organisationsEinheit>Andere Organisationseinheit</ris:organisationsEinheit>
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("754-28-1");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("i am gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("organ 1");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "true"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Andere Organisationseinheit"
      );

      proprietary.setMetadataValue(Metadata.TYP, "");
      proprietary.setMetadataValue(Metadata.FNA, "");
      proprietary.setMetadataValue(Metadata.GESTA, "");
      proprietary.setMetadataValue(Metadata.SUBTYP, "");
      proprietary.setMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE, "");
      proprietary.setMetadataValue(Metadata.ART_DER_NORM, "");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, "");
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR, "");
      proprietary.setMetadataValue(Metadata.STAAT, "");
      proprietary.setMetadataValue(Metadata.ORGANISATIONS_EINHEIT, "");

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }

    @Test
    void setValuesToNull() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
              <redok:fna>754-28-1</redok:fna>
              <redok:gesta>i am gesta</redok:gesta>
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.1/">
              <regtxt:typ>gesetz</regtxt:typ>
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <ris:subtyp>new-subtyp</ris:subtyp>
              <ris:bezeichnungInVorlage>new-bezeichnungInVorlage</ris:bezeichnungInVorlage>
              <ris:artDerNorm>SN,ÄN,ÜN</ris:artDerNorm>
              <ris:beschliessendesOrgan qualifizierteMehrheit="true">organ 1</ris:beschliessendesOrgan>
              <ris:normgeber>DE</ris:normgeber>
              <ris:organisationsEinheit>Andere Organisationseinheit</ris:organisationsEinheit>
             </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("gesetz");
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("754-28-1");
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).contains("i am gesta");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).contains(
        "new-bezeichnungInVorlage"
      );
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("organ 1");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).contains(
        "true"
      );
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DE");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Andere Organisationseinheit"
      );

      proprietary.setMetadataValue(Metadata.TYP, null);
      proprietary.setMetadataValue(Metadata.FNA, null);
      proprietary.setMetadataValue(Metadata.GESTA, null);
      proprietary.setMetadataValue(Metadata.SUBTYP, null);
      proprietary.setMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE, null);
      proprietary.setMetadataValue(Metadata.ART_DER_NORM, null);
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, null);
      proprietary.setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR, null);
      proprietary.setMetadataValue(Metadata.STAAT, null);
      proprietary.setMetadataValue(Metadata.ORGANISATIONS_EINHEIT, null);

      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.GESTA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
    }
  }

  @Nested
  class setMetadataValueEid {

    @Test
    void setNewValueMetadataPresent() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("SN");

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "ÜN");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("ÜN");
    }

    @Test
    @SuppressWarnings("java:S5976") // so that tests are more readable and parameterization not needed.
    void setNewValueMetadataAbsent() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "ÜN");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("ÜN");
    }

    @Test
    void setNewValueEinzelElementAbsent() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              </ris:legalDocML.de_metadaten>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "ÜN");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("ÜN");
    }

    @Test
    void setNewValueParentAbsent() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "ÜN");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("ÜN");
    }

    @Test
    void setValueToEmptyString() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("SN");

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();
    }

    @Test
    void setValueToNull() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("SN");

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, null);

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();
    }

    @Test
    void setValueToEmptyStringAndNotRemoveEinzelelement() {
      var eid = new EId("hauptteil-n1_abschnitt-n0_art-n1");

      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                  <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
                      <ris:artDerNorm>SN</ris:artDerNorm>
                      <ris:typ>Typ</ris:typ>
                   </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).contains("SN");
      assertThat(proprietary.getMetadataValue(Metadata.TYP, eid)).contains("Typ");

      proprietary.setMetadataValue(Metadata.ART_DER_NORM, eid, "");

      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.TYP, eid)).contains("Typ");

      final Optional<Element> einzelElement = NodeParser.getElementFromExpression(
        "./legalDocML.de_metadaten/einzelelement[@href='#%s']".formatted(eid),
        proprietary.getElement()
      );
      assertThat(einzelElement).isNotEmpty();
    }
  }

  @Nested
  class setRessort {

    @Test
    void setRessortMetadataPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <breg:legalDocML.de_metadaten xmlns:breg="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/">
                <breg:federfuehrung>
                    <breg:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</breg:federfuehrend>
                    <breg:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</breg:federfuehrend>
                </breg:federfuehrung>
              </breg:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      final LocalDate atDate = LocalDate.parse("2002-10-01");
      assertThat(proprietary.getRessort(atDate)).contains("Bundesministerium der Justiz");
      final List<Element> elements = NodeParser.getElementsFromExpression(
        "./legalDocML.de_metadaten/federfuehrung/federfuehrend",
        proprietary.getElement()
      );
      assertThat(elements).hasSize(2);

      proprietary.setRessort("test ressort", atDate);

      assertThat(proprietary.getRessort(atDate)).contains("test ressort");
      final List<Element> elementsReLoaded = NodeParser.getElementsFromExpression(
        "./legalDocML.de_metadaten/federfuehrung/federfuehrend",
        proprietary.getElement()
      );
      assertThat(elementsReLoaded).hasSize(2);
    }

    @Test
    void setRessortMetadataAbsent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
              <breg:legalDocML.de_metadaten xmlns:breg="http://MetadatenBundesregierung.LegalDocML.de/1.8.1/">
                <breg:federfuehrung>
                    <breg:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</breg:federfuehrend>
                    <breg:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</breg:federfuehrend>
                </breg:federfuehrung>
              </breg:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      final LocalDate atDate = LocalDate.parse("1990-01-01");
      assertThat(proprietary.getRessort(atDate)).isEmpty();

      final List<Element> elements = NodeParser.getElementsFromExpression(
        "./legalDocML.de_metadaten/federfuehrung/federfuehrend",
        proprietary.getElement()
      );
      assertThat(elements).hasSize(2);

      proprietary.setRessort("test ressort", atDate);

      assertThat(proprietary.getRessort(atDate)).contains("test ressort");
      final List<Element> elementsReLoaded = NodeParser.getElementsFromExpression(
        "./legalDocML.de_metadaten/federfuehrung/federfuehrend",
        proprietary.getElement()
      );
      assertThat(elementsReLoaded).hasSize(3);

      final Optional<Element> newElementAdded = elementsReLoaded
        .stream()
        .filter(f -> f.getAttribute("ab").equals("1990-01-01"))
        .findFirst();
      assertThat(newElementAdded).isNotEmpty();
      assertThat(newElementAdded.get().getAttribute("bis")).isEqualTo("2002-09-30");
    }
  }

  @Nested
  class getInkraftAusserkraft {

    @Test
    void getInkrafttreteDatum() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
             <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
               <ris:inkraft date="1985-12-25" />
             </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getInkrafttreteDatum()).contains(LocalDate.parse("1985-12-25"));
    }

    @Test
    void getAusserkrafttreteDatum() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
             <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
               <ris:ausserkraft date="1985-12-31" />
             </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getAusserkrafttreteDatum()).contains(LocalDate.parse("1985-12-31"));
    }
  }

  @Nested
  class getOrCreateCustomModsMetadata {

    @Test
    void getCustomModsMetadata() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
                <norms:geltungszeiten>
                  <norms:geltungszeit id="gz-1" art="inkraft">2017-03-16</norms:geltungszeit>
                </norms:geltungszeiten>
              </norms:legalDocML.de_metadaten>
            </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      final CustomModsMetadata customModsMetadata = proprietary.getOrCreateCustomModsMetadata();
      assertThat(customModsMetadata).isNotNull();
      assertThat(customModsMetadata.getElement()).isNotNull();
      assertThat(customModsMetadata.getOrCreateGeltungszeiten())
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple(new Zeitgrenze.Id("gz-1"), LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT)
        );
    }

    @Test
    void createCustomModsMetadataNoMods() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">

            </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      final CustomModsMetadata customModsMetadata = proprietary.getOrCreateCustomModsMetadata();
      assertThat(customModsMetadata).isNotNull();
      assertThat(customModsMetadata.getElement()).isNotNull();
      assertThat(customModsMetadata.getGeltungszeiten()).isEmpty();
    }

    @Test
    void createCustomModsMetadataNoRisMetadata() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
           </akn:proprietary>
          """
        )
      );
      final CustomModsMetadata customModsMetadata = proprietary.getOrCreateCustomModsMetadata();
      assertThat(customModsMetadata).isNotNull();
      assertThat(customModsMetadata.getElement()).isNotNull();
      assertThat(customModsMetadata.getGeltungszeiten()).isEmpty();
    }
  }

  @Nested
  class removeMetadataParentIfEmpty {

    @Test
    void metadataParentIsEmpty() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/"/>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isNotEmpty();
      proprietary.removeMetadataParentIfEmpty(Namespace.METADATEN_RIS);
      assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isEmpty();
    }

    @Test
    void metadataParentIsNotEmpty() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
                <ris:inkraft>2020-01-01</ris:inkraft>
            </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isNotEmpty();
      proprietary.removeMetadataParentIfEmpty(Namespace.METADATEN_RIS);
      assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isNotEmpty();
    }
  }

  @Nested
  class gegenstandslos {

    @Test
    void getGegenstandlos() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
              <redok:gegenstandlos seit="2020-01-01" />
            </redok:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getGegenstandlos()).isPresent();
      assertThat(proprietary.getGegenstandlos().get().getSinceDate()).isEqualTo(
        LocalDate.parse("2020-01-01")
      );
    }

    @Test
    void getOrCreateGegenstandlos() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.1/">
            </redok:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      );

      proprietary.getOrCreateGegenstandlos();
      assertThat(proprietary.getGegenstandlos()).isPresent();
      assertThat(proprietary.getGegenstandlos().get().getSinceDate()).isEqualTo(
        LocalDate.now().toString()
      );
    }
  }
}
