package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MetadatenDsTest {

  @Test
  void getFnaAtDate() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                    <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                    <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                    <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                </meta:legalDocML.de_metadaten_ds>
                            """))
            .build();

    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1980-01-01"))).isEmpty();

    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1990-01-01"))).contains("111-11-1");
    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1992-01-01"))).contains("111-11-1");
    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1994-12-31"))).contains("111-11-1");

    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1995-01-01"))).contains("222-22-2");
    assertThat(metadatenDs.getFnaAt(LocalDate.parse("1998-01-01"))).contains("222-22-2");
    assertThat(metadatenDs.getFnaAt(LocalDate.parse("2000-12-31"))).contains("222-22-2");

    assertThat(metadatenDs.getFnaAt(LocalDate.parse("2001-01-01"))).contains("333-33-3");
    assertThat(metadatenDs.getFnaAt(LocalDate.parse("2024-01-01"))).contains("333-33-3");
  }

  @Test
  void setFnaAtDateUpdate() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                  <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                              </meta:legalDocML.de_metadaten_ds>
                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1990-01-01");
    assertThat(metadatenDs.getFnaNodes()).hasSize(3);
    assertThat(metadatenDs.getFnaAt(newDate)).contains("111-11-1");

    metadatenDs.setFnaAt(newDate, "000-00-0");

    assertThat(metadatenDs.getFnaAt(newDate)).contains("000-00-0");
    assertThat(metadatenDs.getFnaNodes()).hasSize(3);
  }

  @Test
  void setFnaAtDateCreateWithoutEnd() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                              </meta:legalDocML.de_metadaten_ds>
                                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1980-01-01");

    assertThat(metadatenDs.getFnaAt(newDate)).isEmpty();
    assertThat(metadatenDs.getFnaNodes()).isEmpty();

    metadatenDs.setFnaAt(newDate, "000-00-0");

    assertThat(metadatenDs.getFnaAt(newDate)).contains("000-00-0");
    assertThat(metadatenDs.getFnaNodes()).hasSize(1);

    metadatenDs.getFnaNodes().stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).isEmpty());
  }

  @Test
  void setFnaAtDateCreateWitEnd() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                  <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                              </meta:legalDocML.de_metadaten_ds>
                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1980-01-01");
    assertThat(metadatenDs.getFnaAt(newDate)).isEmpty();
    assertThat(metadatenDs.getFnaNodes()).hasSize(3);

    metadatenDs.setFnaAt(newDate, "000-00-0");

    assertThat(metadatenDs.getFnaAt(newDate)).contains("000-00-0");
    assertThat(metadatenDs.getFnaNodes()).hasSize(4);

    metadatenDs.getFnaNodes().stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.parse("1989-12-31")));
  }

  @Test
  void setFnaAtDateCreateAndSetEndPrevious() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                                  <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                                              </meta:legalDocML.de_metadaten_ds>
                                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("2005-01-01");
    assertThat(metadatenDs.getFnaAt(newDate)).contains("333-33-3");
    assertThat(metadatenDs.getFnaNodes()).hasSize(3);

    metadatenDs.setFnaAt(newDate, "000-00-0");

    assertThat(metadatenDs.getFnaAt(newDate)).contains("000-00-0");
    assertThat(metadatenDs.getFnaNodes()).hasSize(4);

    metadatenDs.getFnaNodes().stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).isEmpty());

    metadatenDs.getFnaNodes().stream()
        .filter(
            f ->
                f.getStart().isPresent()
                    && f.getStart().get().isEqual(LocalDate.parse("2001-01-01")))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(newDate.minusDays(1)));
  }
}
