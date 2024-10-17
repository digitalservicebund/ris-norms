package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a href within LDML.de.
 *
 * @param value the value of the href as {@link String}
 */
public record Href(String value) {
  static final int NUMBER_OF_ELI_PARTS = 9;
  static final int ABSOLUTE_POSITION_OF_EID = NUMBER_OF_ELI_PARTS;
  static final int RELATIVE_POSITION_OF_EID = 0;
  static final int ABSOLUTE_POSITION_OF_CHARACTER_RANGE = ABSOLUTE_POSITION_OF_EID + 1;
  static final int RELATIVE_POSITION_OF_CHARACTER_RANGE = RELATIVE_POSITION_OF_EID + 1;

  /**
   * Is the href relative to the current norm?
   *
   * @return true when it is a relative href, false if it is absolute
   */
  public boolean isRelative() {
    return value.startsWith("#");
  }

  /**
   * Get the eli of the href
   *
   * @return The eli of the href or empty if no eli is included.
   * @deprecated use getExpressionEli instead.
   */
  @Deprecated(forRemoval = true)
  public Optional<String> getEli() {
    if (isRelative()) {
      return Optional.empty();
    }

    return Optional
      .of(
        Arrays
          .stream(value().split("/"))
          .limit(NUMBER_OF_ELI_PARTS)
          .collect(Collectors.joining("/"))
      )
      .map(Href::removeFileExtension);
  }

  /**
   * Get the eli of the href
   *
   * @return The eli of the href or empty if no eli is included.
   */
  public Optional<ExpressionEli> getExpressionEli() {
    if (isRelative()) {
      return Optional.empty();
    }

    return Optional
      .of(
        Arrays
          .stream(value().split("/"))
          .limit(NUMBER_OF_ELI_PARTS)
          .collect(Collectors.joining("/"))
      )
      .map(Href::removeFileExtension)
      .map(ExpressionEli::fromString);
  }

  /**
   * Get the eId of the href
   *
   * @return The eId of the href or empty if no eid is included.
   */
  public Optional<String> getEId() {
    if (isRelative()) {
      var splitHref = value().replaceFirst("^#", "").split("/");

      if (splitHref.length <= RELATIVE_POSITION_OF_EID) {
        return Optional.empty();
      }

      return Optional.of(splitHref[RELATIVE_POSITION_OF_EID]);
    }

    var splitHref = value().split("/");

    if (splitHref.length <= ABSOLUTE_POSITION_OF_EID) {
      return Optional.empty();
    }

    return Optional.of(Href.removeFileExtension(splitHref[ABSOLUTE_POSITION_OF_EID]));
  }

  /**
   * Get the eId of the parent element in the href
   *
   * @return The parent's eId of the href or empty if no eid is included.
   */
  public Optional<String> getParentEId() {
    Optional<String> wholeEid = getEId();
    if (wholeEid.isPresent()) {
      int lastUnderscoreIndex = wholeEid.get().lastIndexOf("_");
      return Optional.of(wholeEid.get().substring(0, lastUnderscoreIndex));
    } else return Optional.empty();
  }

  /**
   * Get the character range of the href
   *
   * @return The character range of the href or empty if no character range is included.
   */
  public Optional<CharacterRange> getCharacterRange() {
    if (isRelative()) {
      var splitHref = value().replaceFirst("^#", "").split("/");

      if (splitHref.length <= RELATIVE_POSITION_OF_CHARACTER_RANGE) {
        return Optional.empty();
      }

      return Optional.of(new CharacterRange(splitHref[RELATIVE_POSITION_OF_CHARACTER_RANGE]));
    }

    var splitHref = value().split("/");

    if (splitHref.length <= ABSOLUTE_POSITION_OF_CHARACTER_RANGE) {
      return Optional.empty();
    }

    return Optional.of(
      new CharacterRange(Href.removeFileExtension(splitHref[ABSOLUTE_POSITION_OF_CHARACTER_RANGE]))
    );
  }

  /** Builder for creating a new {@link Href}. */
  public static class Builder {

    private String eli;
    private String eId;
    private CharacterRange characterRange;
    private String fileExtension;

    /**
     * Sets the eli part of the href.
     *
     * @param eli the eli
     * @return the builder instance
     */
    public Builder setEli(ExpressionEli eli) {
      this.eli = eli.toString();
      return this;
    }

    /**
     * Sets the eId part of the href.
     *
     * @param eId the eid
     * @return the builder instance
     */
    public Builder setEId(String eId) {
      this.eId = eId;
      return this;
    }

    /**
     * Sets the character range part of the href.
     *
     * @param characterRange the character range
     * @return the builder instance
     */
    public Builder setCharacterRange(CharacterRange characterRange) {
      this.characterRange = characterRange;
      return this;
    }

    /**
     * Sets the file extension part of the href.
     *
     * @param fileExtension the file extension (without a leading dot)
     * @return the builder instance
     */
    public Builder setFileExtension(String fileExtension) {
      this.fileExtension = fileExtension;
      return this;
    }

    /**
     * Creates an absolute Href with the information provided to this builder.
     *
     * @return a new {@link Href} instance
     */
    public Href buildAbsolute() {
      var href = eli;

      if (eId != null) {
        href += "/" + eId;
      }

      if (characterRange != null) {
        href += "/" + characterRange;
      }

      if (fileExtension != null) {
        href += "." + fileExtension;
      }

      return new Href(href);
    }

    /**
     * Creates an internal reference Href with the information provided to this builder.
     *
     * @return a new {@link Href} instance
     */
    public Href buildInternalReference() {
      var href = "#" + eId;

      if (characterRange != null) {
        href += "/" + characterRange;
      }

      return new Href(href);
    }
  }

  private static String removeFileExtension(String href) {
    return href.replaceFirst(".xml$", "");
  }

  @Override
  public String toString() {
    return value;
  }
}
