package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;

/** Class representing a time boundary. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Zeitgrenze {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "geltungszeit";

  private final Element element;
  private final Function<Zeitgrenze, Boolean> isUseProvider;

  /**
   * Creates a new norms:geltungszeit element and appends it to the norms:geltungszeiten node of the {@link CustomModsMetadata}.
   *
   * @param geltungszeiten the {@link Geltungszeiten} element that contains the new {@link Zeitgrenze}.
   * @param art the art of the {@link Zeitgrenze}
   * @param date the date of the {@link Zeitgrenze}
   * @return the newly created {@link Zeitgrenze}
   */
  public static Zeitgrenze createAndAppend(Geltungszeiten geltungszeiten, Art art, LocalDate date) {
    final var element = NodeCreator.createElement(NAMESPACE, TAG_NAME, geltungszeiten.getElement());

    final var zeitgrenze = new Zeitgrenze(element, geltungszeiten::isZeitgrenzeInUse);

    zeitgrenze.setArt(art);
    zeitgrenze.setDate(date);
    zeitgrenze.getElement().setAttribute("id", UUID.randomUUID().toString());

    return zeitgrenze;
  }

  /**
   * The id for identifying the {@link Zeitgrenze}
   * @return the id
   */
  public Id getId() {
    if (!element.hasAttribute("id")) {
      throw new IllegalArgumentException("Missing required attribute 'id' in <geltungszeit> node.");
    }

    return new Id(element.getAttribute("id"));
  }

  /**
   * The date the {@link Zeitgrenze} refers to
   * @return the date
   */
  public LocalDate getDate() {
    final String textContent = element.getTextContent().trim();

    if (textContent.isEmpty()) {
      throw new IllegalArgumentException(
        "Missing text content in <geltungszeit> node with id: " + getId()
      );
    }

    try {
      return LocalDate.parse(textContent);
    } catch (final DateTimeParseException e) {
      throw new IllegalArgumentException(
        "Invalid date format in <geltungszeit>: '" + textContent + "'",
        e
      );
    }
  }

  public void setDate(LocalDate date) {
    getElement().setTextContent(date.toString());
  }

  /**
   * The type of the {@link Zeitgrenze}
   * @return the type
   */
  public Art getArt() {
    if (!element.hasAttribute("art")) {
      throw new IllegalArgumentException(
        "Missing required attribute 'art' in <geltungszeit> node with id: " + getId()
      );
    }

    var artValue = element.getAttribute("art");

    try {
      return Art.valueOf(artValue.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown art value: '" + artValue + "'", e);
    }
  }

  public void setArt(Art art) {
    getElement().setAttribute("art", art.name().toLowerCase());
  }

  /**
   * Is the {@link Zeitgrenze} in use by a {@link ZielnormReference}?
   * @return true if the {@link Zeitgrenze} is in use.
   */
  public boolean isInUse() {
    return isUseProvider.apply(this);
  }

  /**
   * The possible values for Art
   */
  public enum Art {
    INKRAFT,
    AUSSERKRAFT,
  }

  /**
   * Id of a {@link Zeitgrenze}
   * @param value the value of the id
   */
  public record Id(String value) {
    @Override
    public String toString() {
      return value;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Zeitgrenze that = (Zeitgrenze) o;
    return (
      getId() == that.getId() &&
      Objects.equals(getArt(), that.getArt()) &&
      Objects.equals(getDate(), that.getDate())
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getArt(), getDate());
  }
}
