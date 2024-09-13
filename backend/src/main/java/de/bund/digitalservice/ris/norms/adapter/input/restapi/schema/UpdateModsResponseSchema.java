package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

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

  /** The xml of the zf0-version of the norm targeted by the updated mods. */
  private String targetNormZf0Xml;
}
