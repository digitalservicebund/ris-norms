package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ArticleMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAmendingLawsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadArticlesPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the database and implementing {@link LoadAmendingLawPort}.
 * This class is annotated with {@link Service} to indicate that it's a service component in the
 * Spring context.
 */
@Service
public class DBService implements LoadAmendingLawPort, LoadAllAmendingLawsPort, LoadArticlesPort {

  private final AmendingLawRepository amendingLawRepository;

  public DBService(AmendingLawRepository amendingLawRepository) {
    this.amendingLawRepository = amendingLawRepository;
  }

  @Override
  @Transactional
  public Optional<AmendingLaw> loadAmendingLawByEli(LoadAmendingLawPort.Command command) {
    return amendingLawRepository
        .findByEli(command.eli())
        .map(AmendingLawMapper::mapToDomainWithArticles);
  }

  @Override
  public List<AmendingLaw> loadAllAmendingLaws() {
    return amendingLawRepository.findAllByOrderByPublicationDateDesc().stream()
        .map(AmendingLawMapper::mapToDomain)
        .toList();
  }

  @Override
  @Transactional
  public List<Article> loadArticlesByAmendingLaw(LoadArticlesPort.Command command) {
    final Optional<AmendingLawDto> amendingLawDtoOptional =
        amendingLawRepository.findByEli(command.eli());
    return amendingLawDtoOptional
        .map(
            amendingLawDto ->
                amendingLawDto.getArticleDtos().stream().map(ArticleMapper::mapToDomain).toList())
        .orElse(Collections.emptyList());
  }
}
