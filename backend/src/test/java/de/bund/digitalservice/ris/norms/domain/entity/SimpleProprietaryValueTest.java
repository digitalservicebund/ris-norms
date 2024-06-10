package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SimpleProprietaryValueTest {

  @Test
  void getValue() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                            <meta:metadataum start="1990-01-01" end="1994-12-31">111-11-1</meta:metadataum>
                           """))
            .build();

    assertThat(metadataum.getValue()).isEqualTo("111-11-1");
  }

  @Test
  void getValueNull() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:metadataum start="1990-01-01" end="1994-12-31"></meta:metadataum>
                                           """))
            .build();

    assertThat(metadataum.getValue()).isEmpty();
  }

  @Test
  void getStart() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:metadataum start="1990-01-01" end="1994-12-31">111-11-1</meta:metadataum>
                                           """))
            .build();

    assertThat(metadataum.getStart()).contains(LocalDate.parse("1990-01-01"));
  }

  @Test
  void getStartEmpty() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:metadataum end="1994-12-31">111-11-1</meta:metadataum>
                                           """))
            .build();

    assertThat(metadataum.getStart()).isEmpty();
  }

  @Test
  void getEnd() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:metadataum start="1990-01-01" end="1994-12-31">111-11-1</meta:metadataum>
                                           """))
            .build();

    assertThat(metadataum.getEnd()).contains(LocalDate.parse("1994-12-31"));
  }

  @Test
  void getEndEmpty() {
    final SimpleProprietaryValue metadataum =
        SimpleProprietaryValue.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:metadataum start="1990-01-01">111-11-1</meta:metadataum>
                                           """))
            .build();

    assertThat(metadataum.getEnd()).isEmpty();
  }

  @Nested
  class Comparable {
    @Test
    void keepsTheOrderIfStartDatesAreEmpty() {
      // Given
      var a =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum>111-11-1</meta:metadataum>"));
      var b =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum>222-22-2</meta:metadataum>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(a, b);
    }

    @Test
    void sortsByStartDateIfBothArePresent() {
      // Given
      var a =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum start='2023-01-01'>111-11-1</meta:metadataum>"));
      var b =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum start='2011-01-01'>222-22-2</meta:metadataum>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }

    @Test
    void sortsMissingStartDateBeforePresentStartDate() {
      // Given
      var a =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum start='2023-01-01'>111-11-1</meta:metadataum>"));
      var b =
          new SimpleProprietaryValue(
              XmlMapper.toNode("<meta:metadataum>222-22-2</meta:metadataum>"));
      var list = List.of(a, b);

      // When
      var sorted = list.stream().sorted().toList();

      // Then
      assertThat(sorted).containsExactly(b, a);
    }
  }
}
