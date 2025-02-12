package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTocFromRegelungstextUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TableOfContentsServiceTest {

  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);
  final TableOfContentsService tableOfContentsService = new TableOfContentsService(
    loadRegelungstextPort
  );

  @Test
  void itThrowsRegelungstextNotFoundException() {
    final LoadTocFromRegelungstextUseCase.Query query = new LoadTocFromRegelungstextUseCase.Query(
      DokumentExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
    );

    when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> tableOfContentsService.loadTocFromRegelungstext(query))
      // then
      .isInstanceOf(RegelungstextNotFoundException.class);
  }

  @Test
  @SuppressWarnings("java:S5961")
  void itRetrievesToc() {
    // Given
    final Regelungstext regelungstext = Fixtures.loadRegelungstextFromDisk(
      "NormWithGliederung.xml"
    );

    when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

    // When
    final List<TableOfContentsItem> toc = tableOfContentsService.loadTocFromRegelungstext(
      new LoadTocFromRegelungstextUseCase.Query(regelungstext.getExpressionEli())
    );

    // Then
    verify(loadRegelungstextPort, times(1))
      .loadRegelungstext(
        argThat(command ->
          Objects.equals(
            command.eli().toString(),
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
          )
        )
      );
    // Assert overall structure size
    assertThat(toc).hasSize(1);

    // Verify first book
    TableOfContentsItem book1 = toc.getFirst();
    assertThat(book1.id()).isEqualTo("hauptteil-1_buch-1");
    assertThat(book1.marker()).isEqualTo("Buch 1");
    assertThat(book1.heading()).isEqualTo("Überschrift Buch");
    assertThat(book1.type()).isEqualTo("book");
    assertThat(book1.children()).hasSize(2);

    // Verify first part inside the book
    TableOfContentsItem part1 = book1.children().getFirst();
    assertThat(part1.id()).isEqualTo("hauptteil-1_buch-1_teil-1");
    assertThat(part1.marker()).isEqualTo("Teil 1");
    assertThat(part1.heading()).isEqualTo("Überschrift Teil");
    assertThat(part1.type()).isEqualTo("part");
    assertThat(part1.children()).hasSize(2);

    // Verify first chapter inside part
    TableOfContentsItem chapter1 = part1.children().getFirst();
    assertThat(chapter1.id()).isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1");
    assertThat(chapter1.marker()).isEqualTo("Kapitel 1");
    assertThat(chapter1.heading()).isEqualTo("Überschrift Kapitel");
    assertThat(chapter1.type()).isEqualTo("chapter");
    assertThat(chapter1.children()).hasSize(1);

    // Verify first section inside chapter
    TableOfContentsItem section1 = chapter1.children().getFirst();
    assertThat(section1.id()).isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1");
    assertThat(section1.marker()).isEqualTo("Abschnitt 1");
    assertThat(section1.heading()).isEqualTo("Überschrift Abschnitt");
    assertThat(section1.type()).isEqualTo("section");
    assertThat(section1.children()).hasSize(2);

    // Verify second chapter inside first part
    TableOfContentsItem chapter2 = part1.children().getLast();
    assertThat(chapter2.id()).isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-2");
    assertThat(chapter2.marker()).isEqualTo("Kapitel 2");
    assertThat(chapter2.heading()).isEqualTo("Anwendungsbereich und anderes");
    assertThat(chapter2.type()).isEqualTo("chapter");
    assertThat(chapter2.children()).hasSize(1);

    // Verify article inside chapter 2
    TableOfContentsItem article4 = chapter2.children().getFirst();
    assertThat(article4.id()).isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-2_art-1");
    assertThat(article4.marker()).isEqualTo("§ 4");
    assertThat(article4.heading()).isEqualTo("Tabelle 1");
    assertThat(article4.type()).isEqualTo("article");
    assertThat(article4.children()).isEmpty();

    // Verify first subsection inside section
    TableOfContentsItem subsection1 = section1.children().getFirst();
    assertThat(subsection1.id())
      .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1");
    assertThat(subsection1.marker()).isEqualTo("Unterabschnitt 1");
    assertThat(subsection1.heading()).isEqualTo("Überschrift Unterabschnitt");
    assertThat(subsection1.type()).isEqualTo("subsection");
    assertThat(subsection1.children()).hasSize(1);

    // Verify second subsection inside section
    TableOfContentsItem subsection2 = section1.children().getLast();
    assertThat(subsection2.id())
      .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-2");
    assertThat(subsection2.marker()).isEqualTo("Unterabschnitt 2");
    assertThat(subsection2.heading()).isEqualTo("Überschrift Unterabschnitt");
    assertThat(subsection2.type()).isEqualTo("subsection");
    assertThat(subsection2.children()).hasSize(1);

    // Verify first title inside subsection
    TableOfContentsItem title1 = subsection1.children().getFirst();
    assertThat(title1.id())
      .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1");
    assertThat(title1.marker()).isEqualTo("Titel 1");
    assertThat(title1.heading()).isEqualTo("Überschrift Titel");
    assertThat(title1.type()).isEqualTo("title");
    assertThat(title1.children()).hasSize(1);

    // Verify first subtitle inside title
    TableOfContentsItem subtitle1 = title1.children().getFirst();
    assertThat(subtitle1.id())
      .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1");
    assertThat(subtitle1.marker()).isEqualTo("Untertitel 1");
    assertThat(subtitle1.heading()).isEqualTo("Überschrift Untertitel");
    assertThat(subtitle1.type()).isEqualTo("subtitle");
    assertThat(subtitle1.children()).hasSize(2);

    // Verify first article inside subtitle
    TableOfContentsItem article1 = subtitle1.children().getFirst();
    assertThat(article1.id())
      .isEqualTo(
        "hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_art-1"
      );
    assertThat(article1.marker()).isEqualTo("§ 1");
    assertThat(article1.heading()).isEqualTo("Anwendungsbereich");
    assertThat(article1.type()).isEqualTo("article");
    assertThat(article1.children()).isEmpty();

    // Verify article 2 inside subtitle
    TableOfContentsItem article2 = subtitle1.children().getLast();
    assertThat(article2.id())
      .isEqualTo(
        "hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_art-2"
      );
    assertThat(article2.marker()).isEqualTo("§ 2");
    assertThat(article2.heading()).isEqualTo("Paragrafenüberschrift");
    assertThat(article2.type()).isEqualTo("article");
    assertThat(article2.children()).isEmpty();

    // Verify article 3 inside subsection 2
    TableOfContentsItem article3 = subsection2.children().getFirst();
    assertThat(article3.id())
      .isEqualTo("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-2_art-1");
    assertThat(article3.marker()).isEqualTo("§ 3");
    assertThat(article3.heading()).isEqualTo("Grafik");
    assertThat(article3.type()).isEqualTo("article");
    assertThat(article3.children()).isEmpty();

    // Verify second part inside the book
    TableOfContentsItem part2 = book1.children().getLast();
    assertThat(part2.id()).isEqualTo("hauptteil-1_buch-1_teil-2");
    assertThat(part2.marker()).isEqualTo("Teil 2");
    assertThat(part2.heading()).isEqualTo("Überschrift Teil");
    assertThat(part2.type()).isEqualTo("part");
    assertThat(part2.children()).hasSize(2);

    // Verify article 5 inside part 2
    TableOfContentsItem article5 = part2.children().getFirst();
    assertThat(article5.id()).isEqualTo("hauptteil-1_buch-1_teil-2_art-1");
    assertThat(article5.marker()).isEqualTo("§ 5");
    assertThat(article5.heading()).isEqualTo("Formel");
    assertThat(article5.type()).isEqualTo("article");
    assertThat(article5.children()).isEmpty();

    // Verify article 6 inside part 2
    TableOfContentsItem article6 = part2.children().getLast();
    assertThat(article6.id()).isEqualTo("hauptteil-1_buch-1_teil-2_art-2");
    assertThat(article6.marker()).isEqualTo("§ 6");
    assertThat(article6.heading()).isEqualTo("Übergangsregelung");
    assertThat(article6.type()).isEqualTo("article");
    assertThat(article6.children()).isEmpty();
  }
}
