package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadPortalPublishingAllowListPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PortalPrototypePublishServiceTest {

  final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort =
    mock(LoadNormManifestationElisByPublishStatePort.class);
  final PublishNormPort publishNormPort = mock(PublishNormPort.class);
  final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort = mock(
    DeleteAllPublishedDokumentePort.class
  );
  final PublishChangelogPort publishChangelogPort = mock(PublishChangelogPort.class);
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadPortalPublishingAllowListPort loadPortalPublishingAllowListPort = mock(
    LoadPortalPublishingAllowListPort.class
  );
  final ConfidentialDataCleanupService confidentialDataCleanupService = mock(
    ConfidentialDataCleanupService.class
  );
  final PrototypeCleanupService prototypeCleanupService = mock(PrototypeCleanupService.class);
  final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  final PortalPrototypePublishService portalPrototypePublishService =
    new PortalPrototypePublishService(
      loadNormManifestationElisByPublishStatePort,
      publishNormPort,
      deleteAllPublishedDokumentePort,
      publishChangelogPort,
      loadNormPort,
      loadPortalPublishingAllowListPort,
      confidentialDataCleanupService,
      prototypeCleanupService,
      ldmlDeValidator
    );

  @Test
  void itShouldPublishNorm() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm1));
    when(loadPortalPublishingAllowListPort.loadPortalPublishingAllowListPort()).thenReturn(
      List.of("Vereinsgesetz")
    );

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, times(1)).publishNorm(
      assertArg(arg ->
        assertThat(arg.norm().getManifestationEli()).isEqualTo(norm1.getManifestationEli())
      )
    );
    verify(confidentialDataCleanupService, times(1)).clean(any());
    verify(deleteAllPublishedDokumentePort, times(1)).deleteAllPublishedDokumente(any());
    verify(publishChangelogPort, times(1)).publishChangelogs(any());
  }

  @Test
  void itShouldNotPublishNormIfNormNotFound() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, never()).publishNorm(any());
  }

  @Test
  void itShouldNotPublishNormIfNotInAllowList() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm1));
    when(loadPortalPublishingAllowListPort.loadPortalPublishingAllowListPort()).thenReturn(
      List.of("ErbbauRG")
    );

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, times(0)).publishNorm(any());
    verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    verify(publishChangelogPort, times(0)).publishChangelogs(any());
  }

  @Test
  void itShouldNotPublishNormIfNotInkraft() {
    // Given
    var norm = mock(Norm.class);
    var eli = NormManifestationEli.fromString(
      "eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/2024-03-24"
    );

    when(norm.getManifestationEli()).thenReturn(eli);
    when(norm.isInkraftAt(any())).thenReturn(false);
    when(norm.getShortTitle()).thenReturn(Optional.of("Vereinsgesetz"));
    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(eli));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
    when(loadPortalPublishingAllowListPort.loadPortalPublishingAllowListPort()).thenReturn(
      List.of("Vereinsgesetz")
    );

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, times(0)).publishNorm(any());
    verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    verify(publishChangelogPort, times(0)).publishChangelogs(any());
  }

  @Test
  void itShouldNotPublishNormIfNotXSDValid() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm1));
    when(loadPortalPublishingAllowListPort.loadPortalPublishingAllowListPort()).thenReturn(
      List.of("Vereinsgesetz")
    );

    var error = new LdmlDeNotValidException.ValidationError(
      URI.create("/errors/ldml-de-not-valid/cvc-pattern-valid"),
      23,
      64,
      "cvc-pattern-valid: Value 'invalid-guid-to-break-xsd' is not facet-valid with respect to pattern '([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})|(\\{[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\})' for type 'GUIDLiterals'."
    );
    var exception = new LdmlDeNotValidException(List.of(error));
    doThrow(exception).when(ldmlDeValidator).validateXSDSchema(norm1);

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, times(0)).publishNorm(any());
    verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    verify(publishChangelogPort, times(0)).publishChangelogs(any());
  }

  @Test
  void itShouldNotPublishNormIfPublishPortFails() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm1));
    when(loadPortalPublishingAllowListPort.loadPortalPublishingAllowListPort()).thenReturn(
      List.of("Vereinsgesetz")
    );

    doThrow(new RuntimeException()).when(publishNormPort).publishNorm(any());

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, times(1)).publishNorm(any());
    verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    verify(publishChangelogPort, times(0)).publishChangelogs(any());
  }

  @Test
  void itShouldNotPublishNormIfShortTitleIsEmpty() {
    // Given
    // Norm without short title
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
    when(
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
    ).thenReturn(List.of(norm1.getManifestationEli()));
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm1));

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    verify(publishNormPort, never()).publishNorm(any());
  }
}
