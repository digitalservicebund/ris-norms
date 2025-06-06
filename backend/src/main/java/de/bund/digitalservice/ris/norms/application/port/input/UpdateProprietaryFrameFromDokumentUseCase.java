package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;

/** Use case for updating metadata within the {@link Proprietary} node of a {@link Dokument}. */
public interface UpdateProprietaryFrameFromDokumentUseCase {
  /**
   * Updates specific metadata from a {@link Dokument}.
   *
   * @param options Options used for identifying the {@link Dokument} as well as the metadata themselves.
   * @return The updated metadata.
   */
  RahmenMetadata updateProprietaryFrameFromDokument(Options options);

  /**
   * Contains the parameters needed for loading proprietary metadata from a {@link Dokument}.
   *
   * @param dokumentExpressionEli The ELI used to identify the {@link Dokument} at the expression level
   * @param inputMetadata object containing the metadata to update
   */
  record Options(DokumentExpressionEli dokumentExpressionEli, InputMetadata inputMetadata) {}

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
  record InputMetadata(
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
