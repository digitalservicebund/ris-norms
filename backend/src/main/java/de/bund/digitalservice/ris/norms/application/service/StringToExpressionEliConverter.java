package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Service for converting a {@link String} into an {@link ExpressionEli}.
 * Needed for using {@link ExpressionEli}s directly in parameters annotated with {@link RequestParam}.
 */
@Service
public final class StringToExpressionEliConverter implements Converter<String, ExpressionEli> {

  @Override
  public ExpressionEli convert(@NotNull String source) {
    return ExpressionEli.fromString(source);
  }
}
