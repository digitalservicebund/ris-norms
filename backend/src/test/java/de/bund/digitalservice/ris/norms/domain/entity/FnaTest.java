package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FnaTest {

  @Test
  void getValue() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                            <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                           """))
            .build();

    assertThat(fna.getValue()).isEqualTo("111-11-1");
  }

  @Test
  void getValueNull() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna start="1990-01-01" end="1994-12-31"></meta:fna>
                                           """))
            .build();

    assertThat(fna.getValue()).isEmpty();
  }

  @Test
  void getStart() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                           """))
            .build();

    assertThat(fna.getStart()).contains(LocalDate.parse("1990-01-01"));
  }

  @Test
  void getStartEmpty() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna end="1994-12-31">111-11-1</meta:fna>
                                           """))
            .build();

    assertThat(fna.getStart()).isEmpty();
  }

  @Test
  void getEnd() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                           """))
            .build();

    assertThat(fna.getEnd()).contains(LocalDate.parse("1994-12-31"));
  }

  @Test
  void getEndEmpty() {
    final Fna fna =
        Fna.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna start="1990-01-01">111-11-1</meta:fna>
                                           """))
            .build();

    assertThat(fna.getEnd()).isEmpty();
  }

  @Nested
  class Comparable {
    @Test
    void keepsTheOrderIfStartDatesAreEmpty() {
      // Given
      var a = new Fna(XmlMapper.toNode("<meta:fna>111-11-1</meta:fna>"));
      var b = new Fna(XmlMapper.toNode("<meta:fna>222-22-2</meta:fna>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(a, b);
    }

    @Test
    void sortsByStartDateIfBothArePresent() {
      // Given
      var a = new Fna(XmlMapper.toNode("<meta:fna start='2023-01-01'>111-11-1</meta:fna>"));
      var b = new Fna(XmlMapper.toNode("<meta:fna start='2011-01-01'>222-22-2</meta:fna>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }

    @Test
    void sortsMissingStartDateBeforePresentStartDate() {
      // Given
      var a = new Fna(XmlMapper.toNode("<meta:fna start='2023-01-01'>111-11-1</meta:fna>"));
      var b = new Fna(XmlMapper.toNode("<meta:fna>222-22-2</meta:fna>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }
  }
}
