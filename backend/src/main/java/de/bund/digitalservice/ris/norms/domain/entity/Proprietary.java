package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/**
 * Encapsulates metadata that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to DigitalService.
 */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Proprietary {
  private final Node node;

  /**
   * Retrieves the optional {@link MetadatenDe} instance from the {@link Proprietary}.
   *
   * @return an optional with a {@link MetadatenDe}
   */
  public Optional<MetadatenDe> getMetadatenDe() {
    return NodeParser.getNodeFromExpression("./legalDocML.de_metadaten", node)
        .map(MetadatenDe::new);
  }

  /**
   * Retrieves the optional {@link MetadatenDs} instance from the {@link Proprietary}.
   *
   * @return an optional with a {@link MetadatenDs}
   */
  public Optional<MetadatenDs> getMetadatenDs() {
    return NodeParser.getNodeFromExpression("./legalDocML.de_metadaten_ds", node)
        .map(MetadatenDs::new);
  }

  /**
   * Retrieves the {@link MetadatenDs} instance from the {@link Proprietary}.
   *
   * @return the retrieved {@link MetadatenDs} or the newly created one.
   */
  public MetadatenDs getOrCreateMetadatenDs() {
    return NodeParser.getNodeFromExpression("./legalDocML.de_metadaten_ds", node)
        .map(MetadatenDs::new)
        .orElseGet(
            () -> {
              final var newElement =
                  NodeCreator.createElement("meta:legalDocML.de_metadaten_ds", node);
              newElement.setAttribute("xmlns:meta", "http://DS.Metadaten.LegalDocML.de/1.6/");
              return new MetadatenDs(newElement);
            });
  }

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm from the MetadatenDe block
   *
   * @return FNA or empty if it doesn't exist.
   */
  public Optional<String> getFna() {
    return getMetadatenDe().flatMap(MetadatenDe::getFna);
  }

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm from the MetadatenDs block at a specific
   * date, if empty it will get the one from MetadatenDe.
   *
   * @param date - the specific date of the time boundary.
   * @return FNA or empty if present neither in the MetadatenDe nor in the MetadatenDS block.
   */
  public Optional<String> getFna(final LocalDate date) {
    return getMetadatenDs().flatMap(m -> m.getFnaAt(date)).or(this::getFna);
  }
}
