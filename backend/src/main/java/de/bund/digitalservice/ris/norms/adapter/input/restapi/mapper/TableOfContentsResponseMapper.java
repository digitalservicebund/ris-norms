package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TableOfContentsResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import java.util.List;

/**
 * Mapper class for converting from the list of {@link TableOfContentsItem} to the list of {@link TableOfContentsResponseSchema}.
 */
public class TableOfContentsResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private TableOfContentsResponseMapper() {}

  /**
   * Creates a {@link TableOfContentsResponseSchema} from the list of {@link TableOfContentsItem}.
   *
   * @param tableOfContents - the table of contents extracted from the LDML.de
   * @return Converted data
   */
  public static List<TableOfContentsResponseSchema> fromTableOfContents(
    final List<TableOfContentsItem> tableOfContents
  ) {
    return tableOfContents.stream().map(TableOfContentsResponseMapper::convertItem).toList();
  }

  private static TableOfContentsResponseSchema convertItem(final TableOfContentsItem item) {
    if (item == null) {
      return null;
    }
    final TableOfContentsResponseSchema schema = TableOfContentsResponseSchema
      .builder()
      .id(item.id())
      .marker(item.marker())
      .heading(item.heading())
      .type(item.type())
      .build();
    schema.getChildren().addAll(fromTableOfContents(item.children()));
    return schema;
  }
}
