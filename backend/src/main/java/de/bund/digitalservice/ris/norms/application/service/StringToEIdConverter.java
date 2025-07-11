package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

/**
 * Service for converting a {@link String} into an {@link EId}.
 * Needed for properly handeling the URL-encoding of eids in parameters annotated with {@link RequestParam}. The eIds
 * are URL-Encoded but spring decodes that when parsing the URL to extract the parameters, so we need to re-encode them
 * again.
 */
@Service
public final class StringToEIdConverter implements Converter<String, EId> {

  @Override
  public EId convert(@NotNull String source) {
    return new EId(UriUtils.encode(source, StandardCharsets.UTF_8).toLowerCase());
  }
}
