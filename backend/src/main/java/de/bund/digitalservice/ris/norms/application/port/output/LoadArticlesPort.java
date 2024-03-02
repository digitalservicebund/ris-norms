package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/** Port interface for loading all articles of a given amending law from the storage. */
public interface LoadArticlesPort {

  /**
   * Loads all {@link Article}s available in the system of a given amending law.
   *
   * @param command The command specifying the ELI to identify the amending law of the articles to
   *     be loaded.
   * @return A {@link List} of {@link Article}, which may be empty if no articles are found.
   */
  List<Article> loadArticlesByAmendingLaw(final Command command);

  /**
   * A record representing the command for loading articles. The command includes the ELI (European
   * Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     command.
   */
  record Command(String eli) {}
}
