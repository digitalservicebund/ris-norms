package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** The response schema of PATCH /norms/{eli}/mods. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UpdateModsResponseSchema {
  /** The xml of the amending norm. */
  private String amendingNormXml;

  /** The xmls of the zf0-versions of the norms targeted by the new mods. */
  private Collection<String> targetNormZf0Xmls;
}
