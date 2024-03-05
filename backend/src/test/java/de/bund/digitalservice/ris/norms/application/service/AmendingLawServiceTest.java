package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAmendingLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AmendingLawServiceTest {

  final LoadAmendingLawPort loadAmendingLawAdapter = mock(LoadAmendingLawPort.class);
  final LoadAllAmendingLawsPort loadAllAmendingLawsAdapter = mock(LoadAllAmendingLawsPort.class);
  final LoadArticlesPort loadArticlesPort = mock(LoadArticlesPort.class);
  final LoadArticlePort loadArticlePort = mock(LoadArticlePort.class);
  final LoadAmendingLawXmlPort loadAmendingLawXmlAdapter = mock(LoadAmendingLawXmlPort.class);

  final AmendingLawService service =
      new AmendingLawService(
          loadAmendingLawAdapter,
          loadAmendingLawXmlAdapter,
          loadAllAmendingLawsAdapter,
          loadArticlesPort,
          loadArticlePort);

  @Test
  void itCallsLoadAmendingLawByEliUsingInputQueryEli() {
    // Given
    final String eli = "someEli";

    final LoadAmendingLawUseCase.Query query = new LoadAmendingLawUseCase.Query(eli);
    when(loadAmendingLawAdapter.loadAmendingLawByEli(any())).thenReturn(Optional.empty());

    // When
    service.loadAmendingLaw(query);

    // Then
    verify(loadAmendingLawAdapter, times(1))
        .loadAmendingLawByEli(argThat(argument -> Objects.equals(argument.eli(), eli)));
  }

  @Test
  void canLoadAmendingLawByEliIfAdapterFindsOne() {
    // Given
    final String eli = "someEli";

    final LoadAmendingLawUseCase.Query query = new LoadAmendingLawUseCase.Query(eli);

    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .title(title)
            .xml(xml)
            .build();

    when(loadAmendingLawAdapter.loadAmendingLawByEli(any())).thenReturn(Optional.of(amendingLaw));

    // When
    final Optional<AmendingLaw> amendingLawLoaded = service.loadAmendingLaw(query);

    // Then
    assertThat(amendingLawLoaded).isPresent().contains(amendingLaw);
  }

  @Test
  void canNotLoadAmendingLawByEliIfAdapterDoesNotFindOne() {
    // Given
    final String eli = "someEli";

    final LoadAmendingLawUseCase.Query query = new LoadAmendingLawUseCase.Query(eli);
    when(loadAmendingLawAdapter.loadAmendingLawByEli(any())).thenReturn(Optional.empty());

    // When
    final Optional<AmendingLaw> amendingLawsLoaded = service.loadAmendingLaw(query);

    // Then
    assertThat(amendingLawsLoaded).isEmpty();
  }

  @Test
  void canLoadAllAmendingLaws() {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final List<AmendingLaw> expectedAmendingLaws =
        List.of(
            AmendingLaw.builder()
                .eli(eli)
                .printAnnouncementGazette(printAnnouncementGazette)
                .publicationDate(publicationDate)
                .printAnnouncementPage(printAnnouncementPage)
                .digitalAnnouncementMedium(digitalAnnouncementMedium)
                .digitalAnnouncementEdition(digitalAnnouncementEdition)
                .title(title)
                .xml(xml)
                .build(),
            AmendingLaw.builder()
                .eli(eli)
                .printAnnouncementGazette(printAnnouncementGazette)
                .publicationDate(publicationDate)
                .printAnnouncementPage(printAnnouncementPage)
                .digitalAnnouncementMedium(digitalAnnouncementMedium)
                .digitalAnnouncementEdition(digitalAnnouncementEdition)
                .title(title)
                .xml(xml)
                .build());

    when(loadAllAmendingLawsAdapter.loadAllAmendingLaws()).thenReturn(expectedAmendingLaws);

    // When
    List<AmendingLaw> amendingLaw = service.loadAllAmendingLaws();

    // Then
    assertThat(amendingLaw).hasSize(2).containsExactlyElementsOf(expectedAmendingLaws);
    verify(loadAllAmendingLawsAdapter, times(1)).loadAllAmendingLaws();
  }
}
