package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class MetaTest {

  @Test
  void returnsOptionalProprietaryIfItExists() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
        <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
          <akn:proprietary eId="meta-n1_proprietary-n1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
            <redok:legalDocML.de_metadaten xmlns:redok="http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.2/">
             <redok:fna>754-28-1</redok:fna>
           </redok:legalDocML.de_metadaten>
          </akn:proprietary>
        </akn:meta>
        """
      )
    );

    assertThat(meta.getProprietary()).isNotEmpty();
  }

  @Test
  void returnsEmptyOptionalIfProprietaryDoesNotExist() {
    // Given
    var xml = """
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/">
        <akn:act name="regelungstext">
          <!-- Metadaten -->
          <akn:meta eId="meta-n1" GUID="000"></akn:meta>
        </akn:act>
      </akn:akomaNtoso>
      """;

    var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // When
    var result = regelungstext.getMeta().getProprietary();

    // Then
    assertThat(result).isEmpty();
  }
}
