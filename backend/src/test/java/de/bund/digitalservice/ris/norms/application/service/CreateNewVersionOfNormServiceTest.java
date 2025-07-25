package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CreateNewVersionOfNormServiceTest {

  final EliService eliService = mock(EliService.class);
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);

  private final CreateNewVersionOfNormService createNewVersionOfNormService =
    new CreateNewVersionOfNormService(eliService, loadNormByGuidPort);

  @Test
  void createNewExpressionWithSameDate() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
      NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu")
    );
    when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(
      Optional.of(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
      )
    );

    // When
    var result = createNewVersionOfNormService.createNewExpression(
      norm,
      LocalDate.parse("1964-08-05")
    );

    // Then
    assertThat(result.newExpression().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu"
    );
    assertThat(result.newExpression().getRegelungstexte()).hasSize(2);
    assertThat(result.newExpression().getRegelungstext1().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/regelungstext-verkuendung-1"
    );
    FRBRExpression expression = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expression.getURI()).hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu");
    assertThat(expression.getFBRDate()).isEqualTo("1964-08-05");
    assertThat(expression.getFRBRVersionNumber()).contains(2);
    assertThat(expression.getFRBRaliasCurrentVersionId()).isNotEqualTo(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasPreviousVersionId()).isEqualTo(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasPreviousVersionId()
    );
    assertThat(expression.getFRBRaliasNextVersionId()).isEmpty();

    assertThat(result.newExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/%s".formatted(currentDate)
    );

    assertThat(result.newExpression().getRegelungstext1().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRManifestation manifestation = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestation.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestation.getFBRDate()).isEqualTo(currentDate);

    assertThat(result.newManifestationOfOldExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(currentDate)
    );

    assertThat(
      result.newManifestationOfOldExpression().getRegelungstext1().getManifestationEli()
    ).hasToString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRExpression expressionOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expressionOldExpression.getFRBRaliasNextVersionId()).contains(
      expression.getFRBRaliasCurrentVersionId()
    );
    FRBRManifestation manifestationOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI()).hasToString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo(currentDate);
  }

  @Test
  void createNewExpressionWithDifferentDate() {
    // Given
    when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
      NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu")
    );
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    // When
    var result = createNewVersionOfNormService.createNewExpression(
      norm,
      LocalDate.parse("2024-01-01")
    );

    // Then

    assertThat(result.newExpression().getRegelungstexte()).hasSize(2);
    assertThat(result.newExpression().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu"
    );
    assertThat(result.newExpression().getRegelungstext1().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/regelungstext-verkuendung-1"
    );
    FRBRExpression expression = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expression.getURI()).hasToString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu");
    assertThat(expression.getFBRDate()).isEqualTo("2024-01-01");
    assertThat(expression.getFRBRVersionNumber()).contains(1);
    assertThat(expression.getFRBRaliasCurrentVersionId()).isNotEqualTo(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasPreviousVersionId()).contains(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasNextVersionId()).isEmpty();
    assertThat(result.newExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s".formatted(currentDate)
    );
    assertThat(result.newExpression().getRegelungstext1().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRManifestation manifestation = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestation.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestation.getFBRDate()).isEqualTo(currentDate);

    assertThat(result.newManifestationOfOldExpression().getRegelungstexte()).hasSize(2);
    assertThat(result.newManifestationOfOldExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s".formatted(currentDate)
    );
    assertThat(
      result.newManifestationOfOldExpression().getRegelungstext1().getManifestationEli()
    ).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRExpression expressionOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expressionOldExpression.getFRBRaliasNextVersionId()).contains(
      expression.getFRBRaliasCurrentVersionId()
    );
    FRBRManifestation manifestationOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo(currentDate);
  }

  @Test
  void createNewOverridenExpression() {
    // Given
    when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
      NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu")
    );
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    // When
    var resultWithExpressionToOverride = createNewVersionOfNormService.createNewExpression(
      norm,
      LocalDate.parse("2024-01-01")
    );
    var expressionToOverride = resultWithExpressionToOverride.newExpression();
    var result = createNewVersionOfNormService.createNewOverridenExpression(
      norm,
      expressionToOverride
    );

    // Then

    assertThat(result.newExpression().getRegelungstexte()).hasSize(2);
    assertThat(result.newExpression().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu"
    );
    assertThat(result.newExpression().getRegelungstext1().getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/regelungstext-verkuendung-1"
    );
    FRBRExpression expression = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expression.getURI()).hasToString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu");
    assertThat(expression.getFBRDate()).isEqualTo("2024-01-01");
    assertThat(expression.getFRBRVersionNumber()).contains(1);
    assertThat(expression.getFRBRaliasCurrentVersionId()).isNotEqualTo(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasCurrentVersionId()).isEqualTo(
      expressionToOverride
        .getRegelungstext1()
        .getMeta()
        .getFRBRExpression()
        .getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasPreviousVersionId()).contains(
      norm.getRegelungstext1().getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId()
    );
    assertThat(expression.getFRBRaliasNextVersionId()).isEmpty();
    assertThat(result.newExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s".formatted(currentDate)
    );
    assertThat(result.newExpression().getRegelungstext1().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRManifestation manifestation = result
      .newExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestation.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestation.getFBRDate()).isEqualTo(currentDate);

    assertThat(result.newManifestationOfOldExpression().getRegelungstexte()).hasSize(2);
    assertThat(result.newManifestationOfOldExpression().getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s".formatted(currentDate)
    );
    assertThat(
      result.newManifestationOfOldExpression().getRegelungstext1().getManifestationEli()
    ).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    FRBRExpression expressionOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRExpression();
    assertThat(expressionOldExpression.getFRBRaliasNextVersionId()).contains(
      expression.getFRBRaliasCurrentVersionId()
    );
    FRBRManifestation manifestationOldExpression = result
      .newManifestationOfOldExpression()
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-verkuendung-1.xml".formatted(
        currentDate
      )
    );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo(currentDate);
  }

  @Test
  void createNewManifestation() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // When
    var result = createNewVersionOfNormService.createNewManifestation(
      norm,
      LocalDate.parse("2024-01-01")
    );

    // Then
    assertThat(result.getExpressionEli()).isEqualTo(norm.getExpressionEli());
    assertThat(result.getManifestationEli()).isEqualTo(
      NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2024-01-01")
    );
    FRBRManifestation manifestationOldExpression = result
      .getRegelungstext1()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2024-01-01/regelungstext-verkuendung-1.xml"
    );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo("2024-01-01");
  }

  @Test
  void createNewManifestationWithUpdatingComponentRef() {
    // Given
    Norm norm = Fixtures.loadNormFromDisk(
      CreateNewVersionOfNormServiceTest.class,
      "gleichstellung_bundeswehr_with_eingebundeneStammform"
    );

    // When
    var result = createNewVersionOfNormService.createNewManifestation(
      norm,
      LocalDate.parse("2222-02-22")
    );

    // Then
    Article updatedArticle = result
      .getRegelungstext1()
      .getArticles()
      .stream()
      .filter(article -> article.getEingebundeneStammform().isPresent())
      .findFirst()
      .get();

    assertThat(updatedArticle.getEingebundeneStammform()).isPresent();
    DokumentManifestationEli expected = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2222-02-22/regelungstext-verkuendung-2.xml"
    );
    DokumentManifestationEli actual = updatedArticle.getEingebundeneStammform().get();
    assertThat(actual).isEqualTo(expected);
    List<DokumentManifestationEli> dokumentElis = result
      .getDokumente()
      .stream()
      .map(Dokument::getManifestationEli)
      .toList();
    assertThat(dokumentElis).contains(expected);
  }
}
