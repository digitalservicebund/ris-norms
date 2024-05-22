package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.domain.entity.ActiveModification;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** */
@Service
public class ModificationValidator {
  public ModificationValidator(DBService dbService) {
    this.dbService = dbService;
  }

  private final DBService dbService;

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void validate(Norm amendingLaw) {
    throwErrorNoDestinationSet(amendingLaw);
    affectedDocumentExists(amendingLaw);
    nodeWithGivenDestEidExists(amendingLaw);

    destNodeHasTextOnlyContent(amendingLaw);
    modHasValidDestRangeForDestNode(amendingLaw);
    oldTextExistsInTargetLaw(amendingLaw);
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void oldTextExistsInTargetLaw(Norm amendingLaw) {
    // TODO
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void modHasValidDestRangeForDestNode(Norm amendingLaw) {
    amendingLaw.getActiveModifications().stream()
        .map(ActiveModification::getDestinationCharacterRange)
        .toList();
    // TODO
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void destNodeHasTextOnlyContent(Norm amendingLaw) {
    // TODO no "<>" exists
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void affectedDocumentExists(Norm amendingLaw) {
    List<String> affectedDocumentElis =
        amendingLaw.getActiveModifications().stream()
            .map(ActiveModification::getDestinationEli)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    // TODO
  }

  private void throwErrorNoDestinationSet(Norm amendingLaw) {
    List<String> emptyElis =
        amendingLaw.getActiveModifications().stream()
            .filter(am -> am.getDestinationEli().isEmpty())
            .map(ActiveModification::getEid)
            .map(Optional::get)
            .toList();
    // TODO
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void nodeWithGivenDestEidExists(Norm amendingLaw) {
    List<String> affectedDocumentEids =
        amendingLaw.getActiveModifications().stream()
            .map(ActiveModification::getDestinationEid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    // TODO
  }
}
