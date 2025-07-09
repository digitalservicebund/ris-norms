package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import org.junit.jupiter.api.Test;

class AmendedNormExpressionsTest {

  @Test
  void add_addsNewNormExpressionIfNotPresent() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
        <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
          <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
          <norms:norm-expression created-by-zeitgrenze="false" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
          <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/2023/413/2024-02-12/1/deu
          </norms:norm-expression>
        </norms:amended-norm-expressions>
        """
      )
    );

    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(3);

    amendedNormExpressions.add(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu"),
      true,
      true
    );

    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(4);

    var added = amendedNormExpressions.find(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu")
    );

    assertThat(added).isPresent();
    assertThat(added.get().getCreatedByZeitgrenze()).isTrue();
    assertThat(added.get().getCreatedByReplacingExistingExpression()).isTrue();
  }

  @Test
  void add_updatesExistingNormExpressionIfPresent() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
        <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
          <norms:norm-expression created-by-zeitgrenze="false" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
        </norms:amended-norm-expressions>
        """
      )
    );

    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(1);

    amendedNormExpressions.add(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"),
      true,
      true
    );

    var updated = amendedNormExpressions.find(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu")
    );

    assertThat(updated).isPresent();
    assertThat(updated.get().getCreatedByZeitgrenze()).isTrue();
    assertThat(updated.get().getCreatedByReplacingExistingExpression()).isTrue();

    // still only 1 item in the list
    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(1);
  }

  @Test
  void remove() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
         <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
              <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
              <norms:norm-expression created-by-zeitgrenze="false" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
              <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/2023/413/2024-02-12/1/deu</norms:norm-expression>
         </norms:amended-norm-expressions>
        """
      )
    );

    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(3);

    amendedNormExpressions.remove(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu")
    );

    assertThat(amendedNormExpressions.getNormExpressions()).hasSize(2);
  }

  @Test
  void contains() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
          <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
            <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
          </norms:amended-norm-expressions>
        """
      )
    );

    var present = NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu");
    var notPresent = NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-01-01/1/deu");

    assertThat(amendedNormExpressions.contains(present)).isTrue();
    assertThat(amendedNormExpressions.contains(notPresent)).isFalse();
  }

  @Test
  void find() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
          <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
            <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
          </norms:amended-norm-expressions>
        """
      )
    );

    var found = amendedNormExpressions.find(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu")
    );
    var notFound = amendedNormExpressions.find(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-01-01/1/deu")
    );

    assertThat(found).isPresent();
    assertThat(found.get().getCreatedByZeitgrenze()).isTrue();
    assertThat(notFound).isEmpty();
  }

  @Test
  void getNormExpressions() {
    var amendedNormExpressions = new AmendedNormExpressions(
      toElement(
        """
          <norms:amended-norm-expressions xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
            <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2024-02-12/1/deu</norms:norm-expression>
            <norms:norm-expression created-by-zeitgrenze="false" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
          </norms:amended-norm-expressions>
        """
      )
    );

    var normExpressions = amendedNormExpressions.getNormExpressions();

    assertThat(normExpressions).hasSize(2);
    assertThat(normExpressions.get(0).getNormExpressionEli()).hasToString(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"
    );
    assertThat(normExpressions.get(1).getNormExpressionEli()).hasToString(
      "eli/bund/bgbl-1/2023/413/2024-02-12/1/deu"
    );
  }
}
