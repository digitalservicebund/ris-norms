package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ArticleMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.TargetLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.TargetLawRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAmendingLawsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadArticlePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadArticlesPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the database and implementing {@link LoadAmendingLawPort}.
 * This class is annotated with {@link Service} to indicate that it's a service component in the
 * Spring context.
 */
@Service
public class DBService
    implements LoadAmendingLawPort,
        LoadAllAmendingLawsPort,
        LoadArticlesPort,
        LoadArticlePort,
        LoadTargetLawPort {

  private final AmendingLawRepository amendingLawRepository;

  private final TargetLawRepository targetLawRepository;

  /**
   * Constructs a new {@link DBService} instance.
   *
   * @param amendingLawRepository The repository for amending laws.
   * @param targetLawRepository The repository for target laws.
   */
  public DBService(
      AmendingLawRepository amendingLawRepository, TargetLawRepository targetLawRepository) {
    this.amendingLawRepository = amendingLawRepository;
    this.targetLawRepository = targetLawRepository;
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

  @Override
  @Transactional
  public Optional<Article> loadArticleByEliAndEid(LoadArticlePort.Command command) {
    final Optional<AmendingLawDto> amendingLawDtoOptional =
        amendingLawRepository.findByEli(command.eli());
    return amendingLawDtoOptional.flatMap(
        amendingLawDto ->
            amendingLawDto.getArticleDtos().stream()
                .filter(articleDto -> Objects.equals(articleDto.getEid(), command.eId()))
                .findFirst()
                .map(ArticleMapper::mapToDomain));
  }

  @Override
  public Optional<TargetLaw> loadTargetLawByEli(LoadTargetLawPort.Command command) {
    return targetLawRepository.findByEli(command.eli()).map(TargetLawMapper::mapToDomain);
  }
}
