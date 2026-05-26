package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryTest {

  @Nested
  class getMetadataValue {

    @Test
    void returnsValues() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.2/">
              <redok:fna>754-28-1</redok:fna>
              <redok:gesta>i am gesta</redok:gesta>
            </redok:legalDocML.de_metadaten>
            <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.2/">
              <regtxt:typ>gesetz</regtxt:typ>
            </regtxt:legalDocML.de_metadaten>
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.2/">
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

      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).contains(
        "Andere Organisationseinheit"
      );
    }

    @Test
    void returnsEmptyOptionalIfMissingButParentPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
              <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/"
                eId="meta-n1_proprietary-n1"
                GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                source="attributsemantik-noch-undefiniert">
                <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.2/">
                </redok:legalDocML.de_metadaten>
                <regtxt:legalDocML.de_metadaten xmlns:regtxt="http://MetadatenRegelungstext.LegalDocML.de/1.8.2/">
                </regtxt:legalDocML.de_metadaten>
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.2/">
                </ris:legalDocML.de_metadaten>
              </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }

    @Test
    void returnsEmptyOptionalIfMissingBecauseParentNotPresent() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
              <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/"
                eId="meta-n1_proprietary-n1"
                GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                source="attributsemantik-noch-undefiniert">
              </akn:proprietary>
          """
        )
      );

      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }
  }

  @Nested
  class getInkraftAusserkraft {

    @Test
    void getInkrafttreteDatum() {
      final Proprietary proprietary = new Proprietary(
        XmlMapper.toElement(
          """
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
             <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.2/">
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
           <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
             <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.2/">
               <ris:ausserkraft date="1985-12-31" />
             </ris:legalDocML.de_metadaten>
           </akn:proprietary>
          """
        )
      );
      assertThat(proprietary.getAusserkrafttreteDatum()).contains(LocalDate.parse("1985-12-31"));
    }
  }
}
