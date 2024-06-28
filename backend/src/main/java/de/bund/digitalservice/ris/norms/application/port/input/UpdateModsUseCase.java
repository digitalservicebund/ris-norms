package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Interface representing the use case for updating amending commands within an amending law. It
 * will load the amending law by its eli first, secondly it will validate if the amending command is
 * valid, and lastly it will update both the amending law and the corresponding ZF0 versions of the
 * target laws. It supports a dry-run for not saving the changes to the norms.
 */
public interface UpdateModsUseCase {

  /**
   * Updates amending commands of an amending law and also updates the corresponding ZF0 version of
   * the affected target laws.
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
   * @param mods - a map between eIds of akn:mod elements within the amending law and the new data
   *     for the mods
   * @param dryRun - if true the updating is executed but the results are discarded and not saved.
   *     Default: false
   */
  record Query(String eli, Map<String, NewModData> mods, boolean dryRun) {
    public Query(String eli, Map<String, NewModData> mods) {
      this(eli, mods, false);
    }
  }

  /**
   * A record representing the new data for an akn:mod element.
   *
   * @param refersTo - the type of the amending command
   * @param timeBoundaryEid - the eId of the temporal group of the time boundary
   * @param destinationHref - the ELI + eid + counting of the target law
   * @param newText - the new text to replace the old one
   */
  record NewModData(
      String refersTo, String timeBoundaryEid, String destinationHref, String newText) {}

  /**
   * The results of updating an amending command. Includes both the updated amending norm and the
   * updated norms targeted by the mods.
   *
   * @param amendingNormXml The xml of the amending norm, in which the akn:mod and
   *     akn:activeModifications are updated.
   * @param targetNormZf0Xmls The xmls of the norms targeted by the akn:mods, in which the
   *     akn:passiveModifications are updated. (These are the zf0 versions of the target norms)
   */
  record Result(String amendingNormXml, Collection<String> targetNormZf0Xmls) {}
}
