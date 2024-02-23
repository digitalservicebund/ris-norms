package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAmendingLawsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the database and implementing {@link LoadAmendingLawPort}.
 * This class is annotated with {@link Service} to indicate that it's a service component in the
 * Spring context.
 */
@Service
public class DBService implements LoadAmendingLawPort, LoadAllAmendingLawsPort {

  private final AmendingLawRepository amendingLawRepository;

  public DBService(AmendingLawRepository amendingLawRepository) {
    this.amendingLawRepository = amendingLawRepository;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public Optional<AmendingLaw> loadAmendingLawByEli(Command command) {
    return amendingLawRepository
        .findByEli(command.eli())
        .map(AmendingLawMapper::mapToDomainWithArticles);
  }

  @Override
  public List<AmendingLaw> loadAllAmendingLaws() {
    return amendingLawRepository.findAll().stream()
        .map(AmendingLawMapper::mapToDomain)
        .collect(Collectors.toList());
  }
}
