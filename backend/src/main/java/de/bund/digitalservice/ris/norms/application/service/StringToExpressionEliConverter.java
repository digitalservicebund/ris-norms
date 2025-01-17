package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Service for converting a {@link String} into an {@link DokumentExpressionEli}.
 * Needed for using {@link DokumentExpressionEli}s directly in parameters annotated with {@link RequestParam}.
 */
@Service
public final class StringToExpressionEliConverter
  implements Converter<String, DokumentExpressionEli> {

  @Override
  public DokumentExpressionEli convert(@NotNull String source) {
    return DokumentExpressionEli.fromString(source);
  }
}
