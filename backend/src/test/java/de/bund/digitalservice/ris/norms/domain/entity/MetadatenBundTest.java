package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MetadatenBundTest {

  @Test
  void getRessort() {
    final MetadatenBund metadatenBund = new MetadatenBund(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
            <meta:federfuehrung>
                <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
            </meta:federfuehrung>
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(
      metadatenBund.getSimpleMetadatum(
        MetadatenBund.Metadata.RESSORT,
        LocalDate.parse("1990-01-01")
      )
    )
      .isEmpty();
    assertThat(
      metadatenBund.getSimpleMetadatum(
        MetadatenBund.Metadata.RESSORT,
        LocalDate.parse("2002-10-01")
      )
    )
      .contains("Bundesministerium der Justiz");
    assertThat(
      metadatenBund.getSimpleMetadatum(
        MetadatenBund.Metadata.RESSORT,
        LocalDate.parse("2022-12-01")
      )
    )
      .contains("Bundesministerium des Innern und für Heimat");
    assertThat(
      metadatenBund.getSimpleMetadatum(
        MetadatenBund.Metadata.RESSORT,
        LocalDate.parse("2024-06-18")
      )
    )
      .contains("Bundesministerium des Innern und für Heimat");
  }

  @Test
  void getRessortNotPresent() {
    final MetadatenBund metadatenBund = new MetadatenBund(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenBund.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, LocalDate.MAX))
      .isEmpty();
  }

  @Test
  void setRessortUpdate() {
    final MetadatenBund metadatenBund = new MetadatenBund(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
            <meta:federfuehrung>
                <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
            </meta:federfuehrung>
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    final LocalDate atDate = LocalDate.parse("2002-10-01");
    assertThat(metadatenBund.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate))
      .contains("Bundesministerium der Justiz");
    assertThat(metadatenBund.getNodes(MetadatenBund.Metadata.RESSORT.getXpath())).hasSize(2);

    metadatenBund.updateSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate, "test ressort");

    assertThat(metadatenBund.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate))
      .contains("test ressort");
    assertThat(metadatenBund.getNodes(MetadatenBund.Metadata.RESSORT.getXpath())).hasSize(2);
  }

  @Test
  void setRessortCreate() {
    final MetadatenBund metadatenBund = new MetadatenBund(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://MetadatenBundesregierung.LegalDocML.de/1.7.2/">
            <meta:federfuehrung>
                <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
            </meta:federfuehrung>
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    final LocalDate atDate = LocalDate.parse("1990-01-01");
    assertThat(metadatenBund.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate)).isEmpty();
    assertThat(metadatenBund.getNodes(MetadatenBund.Metadata.RESSORT.getXpath())).hasSize(2);

    metadatenBund.updateSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate, "test ressort");

    assertThat(metadatenBund.getSimpleMetadatum(MetadatenBund.Metadata.RESSORT, atDate))
      .contains("test ressort");
    assertThat(metadatenBund.getNodes(MetadatenBund.Metadata.RESSORT.getXpath())).hasSize(3);
  }
}
