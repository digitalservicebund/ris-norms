package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HrefTest {

  @Nested
  class Builder {
    @Test
    void itShouldCreateRelativeHrefWithCharacterCount() {
      // given // when
      var href =
          new Href.Builder()
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("para-20_abs-1")
              .setCharacterCount("100-126")
              .setFileExtension("xml")
              .buildRelative();

      // then
      assertThat(href).hasToString("#para-20_abs-1/100-126");
    }

    @Test
    void itShouldCreateRelativeHrefWithoutCharacterCount() {
      // given // when
      var href =
          new Href.Builder()
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("para-20_abs-1")
              .setFileExtension("xml")
              .buildRelative();

      // then
      assertThat(href).hasToString("#para-20_abs-1");
    }

    @Test
    void itShouldCreateAbsoluteHrefWithCharacterCount() {
      // given // when
      var href =
          new Href.Builder()
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("para-20_abs-1")
              .setCharacterCount("100-126")
              .setFileExtension("xml")
              .buildRelative();

      // then
      assertThat(href)
          .hasToString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml");
    }

    @Test
    void itShouldCreateAbsoluteHrefWithoutCharacterCount() {
      // given // when
      var href =
          new Href.Builder()
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("para-20_abs-1")
              .setCharacterCount("100-126")
              .setFileExtension("xml")
              .buildRelative();

      // then
      assertThat(href)
          .hasToString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1.xml");
    }
  }
}
