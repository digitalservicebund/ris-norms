package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface representing the use case for updating amending commands within an amending law. It
 * will load the amending law by its eli first, secondly it will validate if the amending command is
 * valid, and lastly it will update both the amending law and the corresponding ZF0 version of the
 * target law. It supports a dry-run for not saving the changes to the norms. All mods need to
 * target the same norm.
 */
public interface UpdateModsUseCase {

  /**
   * Updates amending commands of an amending law and also updates the corresponding ZF0 version of
   * the affected target law.
   *
   * @param query containing the ELI of the amending law and the properties of the update mods
   * @return An {@link Optional} containing the saved xml representation of the changed {@link
   *     Norm}s.
   */
  Optional<Result> updateMods(Query query);

  /**
   * A record representing the query for updating multiple amending command.
   *
   * @param eli - the ELI of the amending law
   * @param mods - a collection of eIds of akn:mod elements within the amending law and the new data
   *     for the mods
   * @param dryRun - if true the updating is executed but the results are discarded and not saved.
   *     Default: false
   */
  record Query(String eli, Collection<NewModData> mods, boolean dryRun) {
    public Query(String eli, Collection<NewModData> mods) {
      this(eli, mods, false);
    }
  }

  /**
   * A record representing the new data for an akn:mod element.
   *
   * @param eId - the eId of the akn:mod element to modify
   * @param timeBoundaryEId - the eId of the temporal group of the time boundary
   */
  record NewModData(String eId, String timeBoundaryEId) {}

  /**
   * The results of updating an amending command. Includes both the updated amending norm and the
   * updated norms targeted by the mods.
   *
   * @param amendingNormXml The xml of the amending norm, in which the akn:mod and
   *     akn:activeModifications are updated.
   * @param targetNormZf0Xml The xml of the norm targeted by the akn:mods, in which the
   *     akn:passiveModifications are updated. (This is the zf0 version of the target norm)
   */
  record Result(String amendingNormXml, String targetNormZf0Xml) {}
}
