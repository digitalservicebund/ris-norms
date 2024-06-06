package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
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
   * Returns true if start and end are properly configured
   *
   * @return boolean
   */
  public boolean isEndGreaterStart() {
    String[] splitCharacterRange = characterRange().split("-");
    int start = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_START]);
    int end = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_END]);
    return start < end;
  }

  /**
   * Returns the start value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Integer getStart(String modEid) {
    isValidCharacterRange(modEid);
    String[] splitCharacterRange = characterRange().split("-");
    return Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_START]);
  }

  /**
   * Returns the end value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Integer getEnd(String modEid) {
    isValidCharacterRange(modEid);
    String[] splitCharacterRange = characterRange().split("-");
    return Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_END]);
  }

  private void isValidCharacterRange(String modEId) {
    final String regex = "^\\d+-\\d+$";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    // TODO discuss: I don't see throwing the Exception here
    if (!pattern.matcher(characterRange()).matches())
      throw new XmlContentException(
          "The range (%s) given at mod with eId %s is not valid"
              .formatted(characterRange(), modEId),
          null);
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