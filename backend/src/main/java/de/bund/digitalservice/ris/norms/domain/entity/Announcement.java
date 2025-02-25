package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing an announcement entity. This class is annotated with Lombok annotations for
 * generating getters, setters, constructors, and builder methods.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class Announcement {

  private NormExpressionEli eli;

  @Builder.Default
  private List<Release> releases = new ArrayList<>();
}
