package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.InvalidEliException;
import org.junit.jupiter.api.Test;

class NormExpressionTest {

  @Test
  void createAndAppend_setsAllValuesCorrectly() {
    var parent = toElement(
      "<norms:amended-norm-expressions xmlns:norms=\"http://MetadatenMods.LegalDocML.de/1.8.2/\"/>"
    );
    var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu");

    var normExpression = NormExpression.createAndAppend(parent, eli, true, false);

    assertThat(normExpression.getNormExpressionEli()).isEqualTo(eli);
    assertThat(normExpression.getCreatedByZeitgrenze()).isTrue();
    assertThat(normExpression.getCreatedByReplacingExistingExpression()).isFalse();
  }

  @Test
  void throwsExceptionWhenTextContentIsEmpty() {
    var normExpression = new NormExpression(
      toElement(
        """
              <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/" created-by-zeitgrenze="true" created-by-replacing-existing-expression="false"></norms:norm-expression>
        """
      )
    );

    assertThatThrownBy(normExpression::getNormExpressionEli)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Missing text content in <norm-expression> node");
  }

  @Test
  void throwsExceptionWhenNotValidExpressionEli() {
    var normExpression = new NormExpression(
      toElement(
        """
              <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/" created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">not-valid-expression-eli</norms:norm-expression>
        """
      )
    );

    assertThatThrownBy(normExpression::getNormExpressionEli)
      .isInstanceOf(InvalidEliException.class)
      .hasMessageContaining("Invalid NormExpressionEli: \"not-valid-expression-eli\"");
  }

  @Test
  void getNormExpressionEli() {
    var normExpression = new NormExpression(
      toElement(
        """
              <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/" created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
        """
      )
    );
    assertThat(normExpression.getNormExpressionEli()).hasToString(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"
    );
  }

  @Test
  void getCreatedByZeitgrenze() {
    var normExpression = new NormExpression(
      toElement(
        """
              <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/" created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
        """
      )
    );
    assertThat(normExpression.getCreatedByZeitgrenze()).isTrue();
  }

  @Test
  void getCreatedByReplacingExistingExpression() {
    var normExpression = new NormExpression(
      toElement(
        """
              <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/" created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
        """
      )
    );
    assertThat(normExpression.getCreatedByReplacingExistingExpression()).isFalse();
  }

  @Test
  void equalsAndHashCode_workCorrectly() {
    var element1 = toElement(
      """
      <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/"
        created-by-zeitgrenze="true"
        created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
      """
    );

    var element2 = toElement(
      """
      <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/"
        created-by-zeitgrenze="true"
        created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
      """
    );

    var norm1 = new NormExpression(element1);
    var norm2 = new NormExpression(element2);

    assertThat(norm1).isEqualTo(norm2);
    assertThat(norm1.hashCode()).hasSameHashCodeAs(norm2.hashCode());
  }

  @Test
  void equals_returnsFalseWhenAttributesDiffer() {
    var e1 = toElement(
      """
      <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/"
        created-by-zeitgrenze="false"
        created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
      """
    );

    var e2 = toElement(
      """
      <norms:norm-expression xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/"
        created-by-zeitgrenze="true"
        created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
      """
    );

    var n1 = new NormExpression(e1);
    var n2 = new NormExpression(e2);

    assertThat(n1).isNotEqualTo(n2);
  }
}
