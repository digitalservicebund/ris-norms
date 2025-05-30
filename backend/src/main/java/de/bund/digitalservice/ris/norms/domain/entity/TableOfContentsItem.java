package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import java.util.List;

/**
 * A record representing a single item of the table of contents.
 * @param id - containing the eId of the XML node
 * @param marker - containing the "akn:num" of the XML node
 * @param heading - containing the "akn:heading" of the XML node
 * @param type - containing the tag name of the XML node
 * @param children - containing, if any, further {@link TableOfContentsItem}
 */
public record TableOfContentsItem(
  EId id,
  String marker,
  String heading,
  String type,
  List<TableOfContentsItem> children
) {}
