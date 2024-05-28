package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** The response schema of PUT /norms/{eli}/mods/{eid}. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UpdateModResponseSchema {
  /** The xml of the amending norm. */
  private String amendingNormXml;

  /** The xml of the zf0-version of the norm targeted by the new mod. */
  private String targetNormZf0Xml;
}
