package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import javax.annotation.Nullable;

/** Use case for getting a list of {@link Article}s from a {@link Norm}. */
public interface LoadArticlesFromNormUseCase {
  /**
   * Load the list of articles from a norm. Articles can be filtered based on whether they have
   * pending (passive) modifications that originate from a specific law, or that will be applied at
   * a specific date.
   *
   * @param query Query used for identifying the articles
   * @return List of articles (can be empty)
   * @throws NormNotFoundException when the norm is not found
   */
  List<Article> loadArticlesFromNorm(Query query) throws NormNotFoundException;

  /**
   * Contains the parameters needed for loading articles from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param amendedBy ELI of an amending law. When specified, only articles with passive
   *     modifications from that amending law are included in the result.
   * @param amendedAt eId of a lifecycle event. When specified, only articles with passive
   *     modifications that will be applied at the date of this lifecycle event will be included in
   *     the result.
   */
  record Query(String eli, @Nullable String amendedBy, @Nullable String amendedAt) {
    public Query(String eli) {
      this(eli, null, null);
    }
  }
}
