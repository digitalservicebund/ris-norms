package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SimpleProprietaryTest {

  @Test
  void getValue() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
        """
      )
    );

    assertThat(fna.getValue()).isEqualTo("111-11-1");
  }

  @Test
  void getValueNull() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" start="1990-01-01" end="1994-12-31"></ris:fna>
        """
      )
    );

    assertThat(fna.getValue()).isEmpty();
  }

  @Test
  void getStart() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
        """
      )
    );

    assertThat(fna.getStart()).contains(LocalDate.parse("1990-01-01"));
  }

  @Test
  void getStartEmpty() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" end="1994-12-31">111-11-1</ris:fna>
        """
      )
    );

    assertThat(fna.getStart()).isEmpty();
  }

  @Test
  void getEnd() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
        """
      )
    );

    assertThat(fna.getEnd()).contains(LocalDate.parse("1994-12-31"));
  }

  @Test
  void getEndEmpty() {
    final SimpleProprietary fna = new SimpleProprietary(
      XmlMapper.toNode(
        """
                 <ris:fna xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" start="1990-01-01">111-11-1</ris:fna>
        """
      )
    );

    assertThat(fna.getEnd()).isEmpty();
  }
}
