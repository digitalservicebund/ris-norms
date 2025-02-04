package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.LocalDate;

/** Use case for updating metadata within the {@link Proprietary} node of a {@link Norm}. */
public interface UpdateProprietaryFrameFromNormUseCase {
  /**
   * Updates specific metadata from a {@link Norm}.
   *
   * @param query specifying the eli of ZF0, the date for the metadata as well as the metadata
   *     themselves.
   * @return Proprietary node of the norm with the updated metadata.
   */
  Proprietary updateProprietaryFrameFromNorm(Query query);

  /**
   * Contains the parameters needed for loading proprietary metadata from a norm.
   *
   * @param eli The ELI used to identify the norm.
   * @param atDate the date at which the metadata are being updated.
   * @param metadata object containing the metadata to update
   */
  record Query(NormExpressionEli eli, LocalDate atDate, Metadata metadata) {}

  /**
   * Record representing the list of metadata to update.
   *
   * @param fna - "Fundstellennachweis A"
   * @param art - the type of the norm
   * @param typ - the variant of the norm
   * @param subtyp - subtype of the norm
   * @param bezeichnungInVorlage - designation according to specification of the norm - "Bezeichnung
   *     gemäß Vorlage"
   * @param artDerNorm - "Art der Norm"
   * @param staat - "Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder
   *     Rechtsmacht die Norm trägt"
   * @param beschliessendesOrgan - "Beschließendes Organ"
   * @param qualifizierterMehrheit - "Beschlussfassung mit qualifizierter Mehrheit"
   * @param ressort - "Ressort"
   * @param organisationsEinheit - "Organisationseinheit"
   */
  record Metadata(
    String fna,
    String art,
    String typ,
    String subtyp,
    String bezeichnungInVorlage,
    String artDerNorm,
    String staat,
    String beschliessendesOrgan,
    Boolean qualifizierterMehrheit,
    String ressort,
    String organisationsEinheit
  ) {}
}
