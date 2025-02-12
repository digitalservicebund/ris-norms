package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/** Use case for getting the table of contents metadata from a {@link Regelungstext}. */
public interface LoadTocFromRegelungstextUseCase {
  /**
   * Retrieves the table of contents from a {@link Regelungstext}.
   *
   * @param query Query used for identifying the {@link Regelungstext}.
   * @return the list of {@link TableOfContentsItem} from the {@link Regelungstext}.
   */
  List<TableOfContentsItem> loadTocFromRegelungstext(Query query);

  /**
   * Query with the expression eli of the {@link Regelungstext}
   *
   * @param dokumentExpressionEli The ELI used to identify the {@link Regelungstext} at the expression level
   */
  record Query(DokumentExpressionEli dokumentExpressionEli) {}
}
