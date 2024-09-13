package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Schema for the API when fetching/updating proprietary metadata form a norm. */
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ProprietaryFrameSchema {

  private String fna;
  private String art;
  private String typ;
  private String subtyp;
  private String bezeichnungInVorlage;
  private String artDerNorm;
  private String staat;
  private String beschliessendesOrgan;
  private Boolean qualifizierteMehrheit;
  private String ressort;
  private String organisationsEinheit;
}
