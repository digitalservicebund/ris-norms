package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProprietaryValueTest {

  @Test
  void getValue() {
    final ProprietaryValue fna =
        ProprietaryValue.builder()
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
    final ProprietaryValue fna =
        ProprietaryValue.builder()
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
    final ProprietaryValue fna =
        ProprietaryValue.builder()
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
    final ProprietaryValue fna =
        ProprietaryValue.builder()
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
    final ProprietaryValue fna =
        ProprietaryValue.builder()
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
    final ProprietaryValue fna =
        ProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:fna start="1990-01-01">111-11-1</meta:fna>
                                           """))
            .build();

    assertThat(fna.getEnd()).isEmpty();
  }

  @Nested
  class CompareByStartDate {
    @Test
    void keepsTheOrderIfStartDatesAreEmpty() {
      // Given
      final var a = new ProprietaryValue(XmlMapper.toNode("<meta:fna>111-11-1</meta:fna>"));
      final var b = new ProprietaryValue(XmlMapper.toNode("<meta:fna>222-22-2</meta:fna>"));
      final var list = List.of(a, b);

      // When
      final var sorted = list.stream().sorted(ProprietaryValue::compareByStartDate).toList();

      // Then
      assertThat(sorted).containsExactly(a, b);
    }

    @Test
    void sortsByStartDateIfBothArePresent() {
      // Given
      final var a =
          new ProprietaryValue(
              XmlMapper.toNode("<meta:fna start='2023-01-01'>111-11-1</meta:fna>"));
      final var b =
          new ProprietaryValue(
              XmlMapper.toNode("<meta:fna start='2011-01-01'>222-22-2</meta:fna>"));
      final var list = List.of(a, b);

      // When
      final var sorted = list.stream().sorted(ProprietaryValue::compareByStartDate).toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }

    @Test
    void sortsMissingStartDateBeforePresentStartDate() {
      // Given
      final var a =
          new ProprietaryValue(
              XmlMapper.toNode("<meta:fna start='2023-01-01'>111-11-1</meta:fna>"));
      final var b = new ProprietaryValue(XmlMapper.toNode("<meta:fna>222-22-2</meta:fna>"));
      final var list = List.of(a, b);

      // When
      final var sorted = list.stream().sorted(ProprietaryValue::compareByStartDate).toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }
  }
}
