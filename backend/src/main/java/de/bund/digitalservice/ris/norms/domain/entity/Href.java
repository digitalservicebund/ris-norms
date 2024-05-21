package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * Represents a href within LDML.de.
 *
 * @param value the value of the href as {@link String}
 */
public record Href(String value) {

  /** Builder for creating a new {@link Href}. */
  public static class Builder {
    private String eli;
    private String eId;
    private String characterCount;
    private String fileExtension;

    /**
     * Sets the eli part of the href.
     *
     * @param eli the eli
     * @return the builder instance
     */
    public Builder setEli(String eli) {
      this.eli = eli;
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
     * Sets the character count part of the href.
     *
     * @param characterCount the character count
     * @return the builder instance
     */
    public Builder setCharacterCount(String characterCount) {
      this.characterCount = characterCount;
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

      if (characterCount != null) {
        href += "/" + characterCount;
      }

      if (fileExtension != null) {
        href += "." + fileExtension;
      }

      return new Href(href);
    }

    /**
     * Creates a relative Href with the information provided to this builder.
     *
     * @return a new {@link Href} instance
     */
    public Href buildRelative() {
      var href = "#" + eId;

      if (characterCount != null) {
        href += "/" + characterCount;
      }

      return new Href(href);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
