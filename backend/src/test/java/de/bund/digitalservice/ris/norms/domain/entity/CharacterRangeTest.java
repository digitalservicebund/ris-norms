package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterRangeTest {

  @Nested
  class isNotValid {
    //    @Test
    //    void itShouldDetectEmptyStrings() {
    //      // given
    //      var characterRange = new CharacterRange("");
    //
    //      // when // then
    //      assertThat(characterRange.isNotValid()).isTrue();
    //    }
    //
    //    @Test
    //    void itShouldDetectInvalidRangesOne() {
    //      // given
    //      var characterRange = new CharacterRange("1");
    //
    //      // when // then
    //      assertThat(characterRange.isNotValid()).isTrue();
    //    }
    //
    //    @Test
    //    void itShouldDetectInvalidRangesTwo() {
    //      // given
    //      var characterRange = new CharacterRange("1-");
    //
    //      // when // then
    //      assertThat(characterRange.isNotValid()).isTrue();
    //    }
    //
    //    @Test
    //    void itShouldDetectValidRange() {
    //      // given
    //      var characterRange = new CharacterRange("1-2");
    //
    //      // when // then
    //      assertThat(characterRange.isNotValid()).isFalse();
    //    }
    //
    //    @Test
    //    void itShouldDetectValidRangeLargerNumbers() {
    //      // given
    //      var characterRange = new CharacterRange("10-200");
    //
    //      // when // then
    //      assertThat(characterRange.isNotValid()).isFalse();
    //    }
  }

  @Nested
  class isEndGreaterEqualsStart {
    @Test
    void itShouldDetectStartIsGreaterThanEnd() {
      // given
      var characterRange = new CharacterRange("200-100");

      // when // then
      assertThat(characterRange.isEndGreaterStart()).isFalse();
    }

    @Test
    void itShouldDetectValidRange() {
      // given
      var characterRange = new CharacterRange("100-101");

      // when // then
      assertThat(characterRange.isEndGreaterStart()).isTrue();
    }
  }

  @Nested
  class getStart {
    @Test
    void itShouldReturnStart() {
      // given //when
      var characterRangeStart = new CharacterRange("100-200").getStart("someArticleId");

      // then
      assertThat(characterRangeStart).isEqualTo(100);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      Throwable thrown = catchThrowable(() -> new CharacterRange("-200").getEnd("someArticleId"));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The range (-200) given at article with eId someArticleId is not valid");
    }
  }

  @Nested
  class getEnd {
    @Test
    void itShouldReturnEnd() {
      // given //when
      var characterRangeEnd = new CharacterRange("100-200").getEnd("someArticleId");

      // then
      assertThat(characterRangeEnd).isEqualTo(200);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      Throwable thrown = catchThrowable(() -> new CharacterRange("100-").getEnd("someArticleId"));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The range (100-) given at article with eId someArticleId is not valid");
    }
  }

  @Nested
  class Builder {
    @Test
    void itShouldCreateCharacterCount() {
      // given // when
      var characterRange = new CharacterRange.Builder().withStart(100).withEnd(200).build();

      // then
      assertThat(characterRange).hasToString("100-200");
    }
  }
}
