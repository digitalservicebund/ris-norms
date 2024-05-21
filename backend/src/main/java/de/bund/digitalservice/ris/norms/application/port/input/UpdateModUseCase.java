package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/**
 * Interface representing the use case for updating an amending command within an amending law. It
 * will load the amending law by its eli first, secondly it will validate if the amending command is
 * valid (target law exists, eid of node exists, character counting for replacement is within range
 * and if the range corresponds to the old text) and lastly it will update both the amending law and
 * the corresponding ZF0 versions of the target law.
 */
public interface UpdateModUseCase {

  /**
   * Updates an amending command of an amending law and also updates the corresponding ZF0 version
   * of the affected target laws.
   *
   * @param query containing the ELI of the amending law and the properties of the update mod
   * @return An {@link Optional} containing the saved xml representation of {@link Norm} of the
   *     amending law.
   */
  Optional<String> updateMod(Query query);

  /**
   * A record representing the query for updating an amending command.
   *
   * @param eli - the ELI of the amending law
   * @param eid - the eId of the akn:mod within the amending law
   * @param refersTo - the type of the amending command
   * @param timeBoundaryEid - the eId of the temporal group of the time boundary
   * @param destinationHref - the ELI + eid + counting of the target law
   * @param oldText - the previous text that should be replaced
   * @param newText - the new text to replace the old one
   */
  record Query(
      String eli,
      String eid,
      String refersTo,
      String timeBoundaryEid,
      String destinationHref,
      String oldText,
      String newText) {}

  /** This exception indicates that a proposed change to an amending command is not possible. */
  class InvalidUpdateModException extends Exception {
    public InvalidUpdateModException(String message) {
      super(message);
    }
  }
}
