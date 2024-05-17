package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import java.time.LocalDate;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
@Slf4j
public class TimeBoundaryService implements LoadTimeBoundariesUseCase, UpdateTimeBoundariesUseCase {
  private final DBService dbService;

  public TimeBoundaryService(DBService dbService) {
    this.dbService = dbService;
  }

  /**
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return a List of TimeBoundaries
   */
  @Override
  public List<TimeBoundary> loadTimeBoundariesOfNorm(LoadTimeBoundariesUseCase.Query query) {
    return dbService
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(Norm::getTimeBoundaries)
        .orElse(List.of());
  }

  /**
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return a List of TimeBoundaries
   */
  @Override
  public List<TimeBoundary> updateTimeBoundariesOfNorm(UpdateTimeBoundariesUseCase.Query query) {
    Optional<Norm> norm = dbService.loadNorm(new LoadNormPort.Command(query.eli()));
    Optional<Norm> normResponse = Optional.empty();
    if (norm.isPresent()) {

      // At first time boundaries that shall be deleted need to be selected
      // if we would delete first, there are cases where the next possible eId could not be safely
      // calculated
      // example norm: only one date exists (2023-01-01, id2). That date gets deleted and a new date
      // (2024-01-01, null)
      // is being added. Then id3 could not be calculated.
      List<TimeBoundaryChangeData> timeBoundariesToDelete =
          selectTimeBoundariesToDelete(query.timeBoundaries(), norm.get());

      // Add TimeBoundaries where eid is null|empty
      addTimeBoundaries(query.timeBoundaries(), norm.get());

      deleteTimeBoundaries(timeBoundariesToDelete, norm.get());

      editTimeBoundaries(query.timeBoundaries(), norm.get());

      EidConsistencyGuardian.correctEids(norm.get().getDocument());

      normResponse = dbService.updateNorm(new UpdateNormPort.Command(norm.get()));
    }
    return normResponse.map(Norm::getTimeBoundaries).orElse(List.of());
  }

  private void editTimeBoundaries(List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {

    List<TimeBoundaryChangeData> datesToUpdate =
        timeBoundaryChangeData.stream()
            .filter(tb -> tb.eid() != null && !tb.eid().isEmpty())
            .toList();

    List<TimeBoundary> timeBoundariesToUpdate =
        norm.getTimeBoundaries().stream()
            .filter(tb -> tb.getEventRefEid().isPresent())
            .filter(
                tb ->
                    datesToUpdate.stream()
                        .map(TimeBoundaryChangeData::eid)
                        .toList()
                        .contains(tb.getEventRefEid().get()))
            .toList();

    timeBoundariesToUpdate.forEach(
        tb -> {
          LocalDate newDate =
              datesToUpdate.stream()
                  .filter(date -> date.eid().equals(tb.getEventRefEid().get()))
                  .map(TimeBoundaryChangeData::date)
                  .findFirst()
                  .orElse(LocalDate.MIN);
          tb.setEventRefDate(newDate);
        });

    logChangeDataWithoutCorrespondingEidInXml(norm, datesToUpdate);
  }

  private void logChangeDataWithoutCorrespondingEidInXml(
      Norm norm, List<TimeBoundaryChangeData> datesToUpdate) {
    List<String> timeBoundaryEids =
        norm.getTimeBoundaries().stream()
            .map(TimeBoundary::getEventRefEid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    List<TimeBoundaryChangeData> timeBoundariesListedButNotUpdated =
        datesToUpdate.stream()
            .filter(changeData -> changeData.eid() != null && !changeData.eid().isEmpty())
            .filter(changeData -> changeData.date() != null)
            .filter(changeData -> !timeBoundaryEids.contains(changeData.eid()))
            .toList();

    if (!timeBoundariesListedButNotUpdated.isEmpty()) {
      log.error(
          "The following time boundaries should be changed but the eId was not found: {}",
          timeBoundariesListedButNotUpdated);
    }
  }

  private void addTimeBoundaries(List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {
    timeBoundaryChangeData.stream()
        .filter(tb -> tb.eid() == null || tb.eid().isEmpty())
        .map(TimeBoundaryChangeData::date)
        .forEach(norm::addTimeBoundary);
  }

  private List<TimeBoundaryChangeData> selectTimeBoundariesToDelete(
      List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {

    List<String> allChangeDateEids =
        timeBoundaryChangeData.stream().map(TimeBoundaryChangeData::eid).toList();

    List<String> allEventRefEidsToDelete =
        norm.getTimeBoundaries().stream()
            .map(TimeBoundary::getEventRefEid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(eid -> !allChangeDateEids.contains(eid))
            .toList();

    return allEventRefEidsToDelete.stream()
        .map(eid -> new TimeBoundaryChangeData(eid, null))
        .toList();
  }

  private void deleteTimeBoundaries(
      List<TimeBoundaryChangeData> timeBoundariesToDelete, Norm norm) {
    timeBoundariesToDelete.forEach(norm::deleteTimeBoundary);
  }
}
