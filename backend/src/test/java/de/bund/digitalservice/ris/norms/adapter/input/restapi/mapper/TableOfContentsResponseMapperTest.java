package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TableOfContentsResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TableOfContentsResponseMapperTest {

  @Test
  void shouldMapTableOfContentsItemsToResponseSchema() {
    // Given
    final TableOfContentsItem childItem = new TableOfContentsItem(
      "child-id",
      "child-marker",
      "child-heading",
      "child-type",
      Collections.emptyList()
    );
    final TableOfContentsItem parentItem = new TableOfContentsItem(
      "parent-id",
      "parent-marker",
      "parent-heading",
      "parent-type",
      List.of(childItem)
    );

    final List<TableOfContentsItem> tableOfContents = List.of(parentItem);

    // When
    final List<TableOfContentsResponseSchema> response =
      TableOfContentsResponseMapper.fromTableOfContents(tableOfContents);

    // Then
    assertThat(response).hasSize(1);
    final TableOfContentsResponseSchema parentResponse = response.getFirst();
    assertThat(parentResponse.getId()).isEqualTo("parent-id");
    assertThat(parentResponse.getMarker()).isEqualTo("parent-marker");
    assertThat(parentResponse.getHeading()).isEqualTo("parent-heading");
    assertThat(parentResponse.getType()).isEqualTo("parent-type");
    assertThat(parentResponse.getChildren()).hasSize(1);

    final TableOfContentsResponseSchema childResponse = parentResponse.getChildren().getFirst();
    assertThat(childResponse.getId()).isEqualTo("child-id");
    assertThat(childResponse.getMarker()).isEqualTo("child-marker");
    assertThat(childResponse.getHeading()).isEqualTo("child-heading");
    assertThat(childResponse.getType()).isEqualTo("child-type");
    assertThat(childResponse.getChildren()).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenTableOfContentsIsEmpty() {
    // Given
    final List<TableOfContentsItem> tableOfContents = List.of();

    // When
    final List<TableOfContentsResponseSchema> response =
      TableOfContentsResponseMapper.fromTableOfContents(tableOfContents);

    // Then
    assertThat(response).isEmpty();
  }
}
