package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HrefTest {

  @Nested
  class isRelative {

    @Test
    void itShouldDetectRelativeHrefs() {
      // given
      var href = new Href("#art-z20_abs-z1/100-126");

      // when // then
      assertThat(href.isRelative()).isTrue();
    }

    @Test
    void itShouldDetectAbsoluteHrefs() {
      // given
      var href = new Href(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml"
      );

      // when // then
      assertThat(href.isRelative()).isFalse();
    }
  }

  @Nested
  class getExpressionEli {

    @Test
    void itShouldBeEmptyForRelativeHrefs() {
      // given
      var href = new Href("#art-z20_abs-z1/100-126");

      // when
      var eli = href.getExpressionEli();

      // then
      assertThat(eli).isEmpty();
    }

    @Test
    void itShouldProvideTheEli() {
      // given // when
      var href = new Href(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml"
      );

      // when
      var eli = href.getExpressionEli();

      // then
      assertThat(eli).contains(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
        )
      );
    }
  }

  @Nested
  class getEId {

    @ParameterizedTest
    @ValueSource(
      strings = {
        "#art-z20_abs-z1/100-126",
        "#art-z20_abs-z1",
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml",
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1.xml",
      }
    )
    void itShouldProvideTheEId(String hrefString) {
      // given
      var href = new Href(hrefString);

      // when
      var eId = href.getEId();

      // then
      assertThat(eId).contains(new EId("art-z20_abs-z1"));
    }
  }

  @Nested
  class getParentEId {

    @ParameterizedTest
    @ValueSource(
      strings = {
        "#art-z20_abs-z1/100-126",
        "#art-z20_abs-z1",
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml",
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1.xml",
      }
    )
    void itShouldProvideTheParentEId(String hrefString) {
      // given
      var href = new Href(hrefString);

      // when
      var eId = href.getParentEId();

      // then
      assertThat(eId).contains(new EId("art-z20"));
    }
  }

  @Nested
  class getCharacterRange {

    @Test
    void itShouldProvideTheCharacterRangeForRelativeHrefs() {
      // given
      var href = new Href("#art-z20_abs-z1/100-126");

      // when
      Optional<CharacterRange> characterRange = href.getCharacterRange();

      // then
      assertThat(characterRange).isPresent();
      assertThat(characterRange.get().characterRange()).contains("100-126");
    }

    @Test
    void itShouldBeEmptyForRelativeHrefsWithoutCharacterRange() {
      // given
      var href = new Href("#art-z20_abs-z1");

      // when
      var characterRange = href.getCharacterRange();

      // then
      assertThat(characterRange).isEmpty();
    }

    @Test
    void itShouldProvideTheCharacterRangeForAbsoluteHrefs() {
      // given
      var href = new Href(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml"
      );

      // when
      Optional<CharacterRange> characterRange = href.getCharacterRange();

      // then
      assertThat(characterRange).isPresent();
      assertThat(characterRange.get().characterRange()).contains("100-126");
    }

    @Test
    void itShouldBeEmptyForAbsoluteHrefsWithoutCharacterRange() {
      // given
      var href = new Href(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1.xml"
      );

      // when
      var characterRange = href.getCharacterRange();

      // then
      assertThat(characterRange).isEmpty();
    }
  }

  @Nested
  class Builder {

    @Test
    void itShouldCreateRelativeHrefWithCharacterCount() {
      // given // when
      var href = new Href.Builder()
        .setEli(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .setEId(new EId("art-z20_abs-z1"))
        .setCharacterRange(new CharacterRange.Builder().start(100).end(126).build())
        .setFileExtension("xml")
        .buildInternalReference();

      // then
      assertThat(href).hasToString("#art-z20_abs-z1/100-126");
    }

    @Test
    void itShouldCreateRelativeHrefWithoutCharacterCount() {
      // given // when
      var href = new Href.Builder()
        .setEli(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .setEId(new EId("art-z20_abs-z1"))
        .setFileExtension("xml")
        .buildInternalReference();

      // then
      assertThat(href).hasToString("#art-z20_abs-z1");
    }

    @Test
    void itShouldCreateAbsoluteHrefWithCharacterCount() {
      // given // when
      var href = new Href.Builder()
        .setEli(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .setEId(new EId("art-z20_abs-z1"))
        .setCharacterRange(new CharacterRange.Builder().start(100).end(126).build())
        .setFileExtension("xml")
        .buildAbsolute();

      // then
      assertThat(href).hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1/100-126.xml"
      );
    }

    @Test
    void itShouldCreateAbsoluteHrefWithoutCharacterCount() {
      // given // when
      var href = new Href.Builder()
        .setEli(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .setEId(new EId("art-z20_abs-z1"))
        .setFileExtension("xml")
        .buildAbsolute();

      // then
      assertThat(href).hasToString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/art-z20_abs-z1.xml"
      );
    }
  }
}
