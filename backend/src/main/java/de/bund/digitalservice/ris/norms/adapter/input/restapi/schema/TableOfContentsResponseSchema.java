package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Schema for the API when fetching the table of contents. */
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class TableOfContentsResponseSchema {

  private String id;
  private String marker;
  private String heading;
  private String type;
  private String eingebundeneStammformEli;
  final List<TableOfContentsResponseSchema> children = new ArrayList<>();
}
