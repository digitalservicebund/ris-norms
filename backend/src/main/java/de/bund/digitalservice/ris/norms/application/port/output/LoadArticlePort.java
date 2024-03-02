package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.Optional;

/**
 * Interface representing a port for loading an {@link Article} based on the ELI (European
 * Legislation Identifier) of the amending law and the eid of the article. Implementations of this
 * interface should provide functionality to load an article using the specified command.
 */
public interface LoadArticlePort {

  /**
   * Loads a {@link Article} based on the provided ELI and eId specified in the command.
   *
   * @param command The command specifying the ELI to identify the amending law to be loaded and the
   *     eId for the article.
   * @return An {@link Optional} containing the loaded {@link Article} if found, or empty if not
   *     found.
   */
  Optional<Article> loadArticleByEliAndEid(final Command command);

  /**
   * A record representing the command for loading an article. The command includes the ELI
   * (European Legislation Identifier) to identify the amending law and the eId to identify the
   * article.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     command.
   * @param eId The expression level identifier of the article within the XML.
   */
  record Command(String eli, String eId) {}
}
