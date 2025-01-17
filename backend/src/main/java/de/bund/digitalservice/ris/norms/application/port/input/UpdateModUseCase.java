package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Optional;
import javax.annotation.Nullable;

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
  Result updateMod(Query query);

  /**
   * A record representing the query for updating an amending command.
   *
   * @param eli - the ELI of the amending law
   * @param eid - the eId of the akn:mod within the amending law
   * @param refersTo - the type of the amending command
   * @param timeBoundaryEid - the eId of the temporal group of the time boundary
   * @param destinationHref - the ELI + eid + counting of the target law
   * @param destinationUpTo - the ELI + eid of the last element to be replaced in the target law
   * @param newContent - the new text to replace the old one
   * @param dryRun - if true the updating is executed but the results are discarded and not saved.
   *     Default: false
   */
  record Query(
    DokumentExpressionEli eli,
    String eid,
    String refersTo,
    String timeBoundaryEid,
    Href destinationHref,
    @Nullable Href destinationUpTo,
    String newContent,
    boolean dryRun
  ) {
    public Query(
      DokumentExpressionEli eli,
      String eid,
      String refersTo,
      String timeBoundaryEid,
      Href destinationHref,
      Href destinationUpTo,
      String newContent
    ) {
      this(
        eli,
        eid,
        refersTo,
        timeBoundaryEid,
        destinationHref,
        destinationUpTo,
        newContent,
        false
      );
    }
  }

  /**
   * The results of updating an amending command. Includes both the updated amending norm and the
   * updated norm targeted by the mod.
   *
   * @param amendingNormXml The xml of the amending norm, in which the akn:mod and
   *     akn:activeModifications are updated.
   * @param targetNormZf0Xml The xml of the norm targeted by the akn:mod, in which the
   *     akn:passiveModifications are updated. (This is the zf0 version of the target norm)
   */
  record Result(String amendingNormXml, String targetNormZf0Xml) {}
}
