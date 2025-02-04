package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;
import javax.annotation.Nullable;

/** Use case for getting a list of {@link Article}s from a Dokument (eg. {@link de.bund.digitalservice.ris.norms.domain.entity.Regelungstext}). */
public interface LoadArticlesFromDokumentUseCase {
  /**
   * Load the list of articles from a dokument. Articles can be filtered based on whether they have
   * pending (passive) modifications that originate from a specific law, or that will be applied at
   * a specific date.
   *
   * @param query Query used for identifying the articles
   * @return List of articles (can be empty)
   */
  List<Article> loadArticlesFromDokument(Query query);

  /**
   * Contains the parameters needed for loading articles from a dokument.
   *
   * @param eli The ELI used to identify the dokument
   * @param amendedBy ELI of an amending law. When specified, only articles with passive
   *     modifications from that amending law are included in the result.
   * @param amendedAt eId of a lifecycle event. When specified, only articles with passive
   *     modifications that will be applied at the date of this lifecycle event will be included in
   *     the result.
   */
  record Query(
    DokumentExpressionEli eli,
    @Nullable DokumentExpressionEli amendedBy,
    @Nullable String amendedAt
  ) {
    public Query(DokumentExpressionEli eli) {
      this(eli, null, null);
    }
  }
}
