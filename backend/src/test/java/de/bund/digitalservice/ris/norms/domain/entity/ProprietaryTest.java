package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryTest {

  @Nested
  class Fna {
    @Test
    void returnsTheFna() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                        <akn:proprietary eId="meta-1_proprietary-1"
                                                                         GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                         source="attributsemantik-noch-undefiniert">
                                                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                                                <meta:typ>gesetz</meta:typ>
                                                                <meta:fna>754-28-1</meta:fna>
                                                                <meta:fassung>verkuendungsfassung</meta:fassung>
                                                            </meta:legalDocML.de_metadaten>
                                                        </akn:proprietary>
                                                        """))
              .build();

      assertThat(proprietary.getFna()).contains("754-28-1");
    }

    @Test
    void returnsEmptyOptionalIfFnaIsMissing() {

      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                        <akn:proprietary eId="meta-1_proprietary-1"
                                                                         GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                                         source="attributsemantik-noch-undefiniert">
                                                            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                                                <meta:typ>gesetz</meta:typ>
                                                                <meta:form>stammform</meta:form>
                                                                <meta:fassung>verkuendungsfassung</meta:fassung>
                                                            </meta:legalDocML.de_metadaten>
                                                        </akn:proprietary>
                                                        """))
              .build();

      assertThat(proprietary.getFna()).isEmpty();
    }

    @Test
    void returnsTheFnaAtDate() {
      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
                      """
                            <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                    <meta:typ>gesetz</meta:typ>
                                    <meta:fna>000-00-0</meta:fna>
                                    <meta:fassung>verkuendungsfassung</meta:fassung>
                                </meta:legalDocML.de_metadaten>
                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                    <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                    <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                    <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                </meta:legalDocML.de_metadaten_ds>
                            </akn:proprietary>
                            """))
              .build();

      assertThat(proprietary.getFna(LocalDate.parse("1980-01-01"))).contains("000-00-0");

      assertThat(proprietary.getFna(LocalDate.parse("1990-01-01"))).contains("111-11-1");
      assertThat(proprietary.getFna(LocalDate.parse("1992-01-01"))).contains("111-11-1");
      assertThat(proprietary.getFna(LocalDate.parse("1994-12-31"))).contains("111-11-1");

      assertThat(proprietary.getFna(LocalDate.parse("1995-01-01"))).contains("222-22-2");
      assertThat(proprietary.getFna(LocalDate.parse("1998-01-01"))).contains("222-22-2");
      assertThat(proprietary.getFna(LocalDate.parse("2000-12-31"))).contains("222-22-2");

      assertThat(proprietary.getFna(LocalDate.parse("2001-01-01"))).contains("333-33-3");
      assertThat(proprietary.getFna(LocalDate.parse("2024-01-01"))).contains("333-33-3");
    }
  }
}
