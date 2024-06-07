package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterRangeTest {

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
      var characterRangeStart = new CharacterRange("100-200").getStart();

      // then
      assertThat(characterRangeStart).isEqualTo(100);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      boolean isValid = new CharacterRange("-200").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }
  }

  @Nested
  class getEnd {
    @Test
    void itShouldReturnEnd() {
      // given //when
      var characterRangeEnd = new CharacterRange("100-200").getEnd();

      // then
      assertThat(characterRangeEnd).isEqualTo(200);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      boolean isValid = new CharacterRange("100-").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }

    @Test
    void itShouldDetectInvalidRangesOne() {
      // given //when
      boolean isValid = new CharacterRange("1").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }

    @Test
    void itShouldDetectValidRangeLargerNumbers() {
      // given //when
      boolean isValid = new CharacterRange("2500-3000").isValidCharacterRange();

      // then
      assertThat(isValid).isTrue();
    }
  }

  @Nested
  class Builder {
    @Test
    void itShouldCreateCharacterCount() {
      // given // when
      var characterRange = new CharacterRange.Builder().start(100).end(200).build();

      // then
      assertThat(characterRange).hasToString("100-200");
    }
  }
}
