package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRExpression;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRManifestation;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    var norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
    var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    when(eliService.findNextExpressionEli(any(), any(), any()))
      .thenReturn(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu"));
    when(loadNormByGuidPort.loadNormByGuid(any()))
      .thenReturn(Optional.of(Fixtures.loadNormFromDisk("Vereinsgesetz_2017_s419_2017-03-15.xml")));

    // When
    var result = createNewVersionOfNormService.createNewExpression(
      norm,
      LocalDate.parse("1964-08-05")
    );

    // Then
    assertThat(result.newExpression().getExpressionEli())
      .hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/regelungstext-1");
    FRBRExpression expression = result.newExpression().getMeta().getFRBRExpression();
    assertThat(expression.getURI()).hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu");
    assertThat(expression.getFBRDate()).isEqualTo("1964-08-05");
    assertThat(expression.getFRBRVersionNumber()).contains(2);
    assertThat(expression.getFRBRaliasCurrentVersionId())
      .isNotEqualTo(norm.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId());
    assertThat(expression.getFRBRaliasPreviousVersionId())
      .isEqualTo(norm.getMeta().getFRBRExpression().getFRBRaliasPreviousVersionId());
    assertThat(expression.getFRBRaliasNextVersionId()).isEmpty();

    assertThat(result.newExpression().getManifestationEli())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    FRBRManifestation manifestation = result.newExpression().getMeta().getFRBRManifestation();
    assertThat(manifestation.getURI())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    assertThat(manifestation.getFBRDate()).isEqualTo(currentDate);

    assertThat(result.newManifestationOfOldExpression().getManifestationEli())
      .hasToString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    FRBRExpression expressionOldExpression = result
      .newManifestationOfOldExpression()
      .getMeta()
      .getFRBRExpression();
    assertThat(expressionOldExpression.getFRBRaliasNextVersionId())
      .contains(expression.getFRBRaliasCurrentVersionId());
    FRBRManifestation manifestationOldExpression = result
      .newManifestationOfOldExpression()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI())
      .hasToString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo(currentDate);
  }

  @Test
  void createNewExpressionWithDifferentDate() {
    // Given
    when(eliService.findNextExpressionEli(any(), any(), any()))
      .thenReturn(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu"));
    var norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
    var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    // When
    var result = createNewVersionOfNormService.createNewExpression(
      norm,
      LocalDate.parse("2024-01-01")
    );

    // Then
    assertThat(result.newExpression().getExpressionEli())
      .hasToString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/regelungstext-1");
    FRBRExpression expression = result.newExpression().getMeta().getFRBRExpression();
    assertThat(expression.getURI()).hasToString("eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu");
    assertThat(expression.getFBRDate()).isEqualTo("2024-01-01");
    assertThat(expression.getFRBRVersionNumber()).contains(1);
    assertThat(expression.getFRBRaliasCurrentVersionId())
      .isNotEqualTo(norm.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId());
    assertThat(expression.getFRBRaliasPreviousVersionId())
      .contains(norm.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId());
    assertThat(expression.getFRBRaliasNextVersionId()).isEmpty();

    assertThat(result.newExpression().getManifestationEli())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    FRBRManifestation manifestation = result.newExpression().getMeta().getFRBRManifestation();
    assertThat(manifestation.getURI())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/2024-01-01/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    assertThat(manifestation.getFBRDate()).isEqualTo(currentDate);

    assertThat(result.newManifestationOfOldExpression().getManifestationEli())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    FRBRExpression expressionOldExpression = result
      .newManifestationOfOldExpression()
      .getMeta()
      .getFRBRExpression();
    assertThat(expressionOldExpression.getFRBRaliasNextVersionId())
      .contains(expression.getFRBRaliasCurrentVersionId());
    FRBRManifestation manifestationOldExpression = result
      .newManifestationOfOldExpression()
      .getMeta()
      .getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI())
      .hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(currentDate)
      );
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo(currentDate);
  }

  @Test
  void createNewManifestation() {
    // Given
    var norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");

    // When
    var result = createNewVersionOfNormService.createNewManifestation(
      norm,
      LocalDate.parse("2024-01-01")
    );

    // Then
    assertThat(result.getExpressionEli()).isEqualTo(norm.getExpressionEli());
    assertThat(result.getManifestationEli())
      .isEqualTo(
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2024-01-01/regelungstext-1.xml"
        )
      );
    FRBRManifestation manifestationOldExpression = result.getMeta().getFRBRManifestation();
    assertThat(manifestationOldExpression.getURI())
      .hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2024-01-01/regelungstext-1.xml");
    assertThat(manifestationOldExpression.getFBRDate()).isEqualTo("2024-01-01");
  }
}
