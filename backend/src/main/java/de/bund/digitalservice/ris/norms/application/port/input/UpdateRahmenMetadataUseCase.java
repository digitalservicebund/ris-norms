package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;

/** Use case for updating {@link RahmenMetadata} of a {@link Norm}. */
public interface UpdateRahmenMetadataUseCase {
  /**
   * Updates rahmen metadata.
   *
   * @param options Options used for identifying the {@link Norm} as well as the metadata themselves.
   * @return The updated metadata.
   */
  RahmenMetadata updateRahmenMetadata(Options options);

  /**
   * Contains the parameters needed for updating metadata.
   *
   * @param normExpressionEli The ELI used to identify the {@link Norm} at the expression level
   * @param inputMetadata object containing the metadata to update
   */
  record Options(NormExpressionEli normExpressionEli, InputMetadata inputMetadata) {}

  /**
   * Record representing the list of metadata to update.
   *
   * @param fna - "Fundstellennachweis A"
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
  record InputMetadata(
    String fna,
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
