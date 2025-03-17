package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import org.junit.jupiter.api.Test;

class CustomModsMetadataTest {

  @Test
  void getAmendedNormExpressionElis() {
    var customModsMetadata = new CustomModsMetadata(
      toElement(
        """
        <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
            <norms:amended-norm-expressions>
                <norms:norm-expression>eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
                <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
                <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-02-12/1/deu</norms:norm-expression>
                <norms:norm-expression>eli/bund/bgbl-1/2023/413/0001-01-01/24/deu</norms:norm-expression>
                <norms:norm-expression>eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu</norms:norm-expression>
            </norms:amended-norm-expressions>
        </norms:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(customModsMetadata.getAmendedNormExpressionElis())
      .hasSize(5)
      .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"))
      .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-01-11/2/deu"))
      .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-02-12/1/deu"))
      .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/0001-01-01/24/deu"))
      .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu"));
  }
}
