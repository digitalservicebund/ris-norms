package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

/**
 * Projection of {@link DokumentDto} for loading only the fields required for the expression list
 */
public interface NormExpressionListElementDto {
  String getEliNormExpression();
  Boolean getGegenstandlos();
}
