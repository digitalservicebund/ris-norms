package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Represents a character range within LDML.de.
 *
 * @param characterRange the value of the character range as {@link String}
 */
public record CharacterRange(String characterRange) {
  static final int ABSOLUTE_POSITION_OF_START = 0;
  static final int ABSOLUTE_POSITION_OF_END = 1;

  /**
   * Checks if character range is valid.
   *
   * @return boolean
   */
  public boolean isNotValid() {
    final String regex = "^\\d+-\\d+$";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    return !pattern.matcher(characterRange()).matches();
  }

  /**
   * Returns true if start and end are properly configured
   *
   * @return boolean
   */
  public boolean isEndGreaterEqualsStart() {
    if (isNotValid()) return false;
    String[] splitCharacterRange = characterRange().split("-");
    int start = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_START]);
    int end = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_END]);
    return start <= end;
  }

  /**
   * Returns the start value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Optional<Integer> getStart() {
    if (isNotValid()) return Optional.empty();
    String[] splitCharacterRange = characterRange().split("-");
    return Optional.of(Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_START]));
  }

  /**
   * Returns the end value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Optional<Integer> getEnd() {
    if (isNotValid()) return Optional.empty();
    String[] splitCharacterRange = characterRange().split("-");
    return Optional.of(Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_END]));
  }

  /** Builder for creating a new {@link CharacterRange}. */
  public static class Builder {
    private int start;
    private int end;

    /**
     * Sets the start of the character range
     *
     * @param start the start of the character range
     * @return the builder instance
     */
    public Builder withStart(int start) {
      this.start = start;
      return this;
    }

    /**
     * Sets the end of the character range
     *
     * @param end the end of the character range
     * @return the builder instance
     */
    public Builder withEnd(int end) {
      this.end = end;
      return this;
    }

    /**
     * Creates an absolute character range with the information provided to this builder.
     *
     * @return a new {@link CharacterRange} instance
     */
    public CharacterRange build() {
      return new CharacterRange(start + "-" + end);
    }
  }

  @Override
  public String toString() {
    return characterRange();
  }
}
