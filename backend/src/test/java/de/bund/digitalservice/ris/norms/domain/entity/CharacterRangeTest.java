package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterRangeTest {

  @Nested
  class isNotValid {
    @Test
    void itShouldDetectEmptyStrings() {
      // given
      var characterRange = new CharacterRange("");

      // when // then
      assertThat(characterRange.isNotValid()).isTrue();
    }

    @Test
    void itShouldDetectInvalidRangesOne() {
      // given
      var characterRange = new CharacterRange("1");

      // when // then
      assertThat(characterRange.isNotValid()).isTrue();
    }

    @Test
    void itShouldDetectInvalidRangesTwo() {
      // given
      var characterRange = new CharacterRange("1-");

      // when // then
      assertThat(characterRange.isNotValid()).isTrue();
    }

    @Test
    void itShouldDetectValidRange() {
      // given
      var characterRange = new CharacterRange("1-2");

      // when // then
      assertThat(characterRange.isNotValid()).isFalse();
    }

    @Test
    void itShouldDetectValidRangeLargerNumbers() {
      // given
      var characterRange = new CharacterRange("10-200");

      // when // then
      assertThat(characterRange.isNotValid()).isFalse();
    }
  }

  @Nested
  class isEndGreaterEqualsStart {
    @Test
    void itShouldDetectStartIsGreaterThanEnd() {
      // given
      var characterRange = new CharacterRange("200-100");

      // when // then
      assertThat(characterRange.isEndGreaterEqualsStart()).isFalse();
    }

    @Test
    void itShouldDetectStartEqualsEnd() {
      // given
      var characterRange = new CharacterRange("100-100");

      // when // then
      assertThat(characterRange.isEndGreaterEqualsStart()).isTrue();
    }

    @Test
    void itShouldDetectValidRange() {
      // given
      var characterRange = new CharacterRange("100-101");

      // when // then
      assertThat(characterRange.isEndGreaterEqualsStart()).isTrue();
    }
  }

  @Nested
  class getStart {
    @Test
    void itShouldReturnStart() {
      // given //when
      var characterRangeStart = new CharacterRange("100-200").getStart();

      // then
      assertThat(characterRangeStart).isPresent().contains(100);
    }

    @Test
    void itShouldReturnEmpty() {
      // given //when
      var characterRangeStart = new CharacterRange("100-").getStart();

      // then
      assertThat(characterRangeStart).isEmpty();
    }
  }

  @Nested
  class getEnd {
    @Test
    void itShouldReturnEnd() {
      // given //when
      var characterRangeEnd = new CharacterRange("100-200").getEnd();

      // then
      assertThat(characterRangeEnd).isPresent().contains(200);
    }

    @Test
    void itShouldReturnEmpty() {
      // given //when
      var characterRangeEnd = new CharacterRange("100-").getEnd();

      // then
      assertThat(characterRangeEnd).isEmpty();
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
