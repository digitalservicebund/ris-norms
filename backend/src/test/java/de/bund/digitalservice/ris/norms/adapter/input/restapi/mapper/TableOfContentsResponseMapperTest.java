package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TableOfContentsResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TableOfContentsResponseMapperTest {

  @Test
  void shouldMapTableOfContentsItemsToResponseSchema() {
    // Given
    final TableOfContentsItem childItem = new TableOfContentsItem(
      new EId("child-n1"),
      "child-marker",
      "child-heading",
      "child-type",
      false,
      Collections.emptyList()
    );
    final TableOfContentsItem parentItem = new TableOfContentsItem(
      new EId("parent-n1"),
      "parent-marker",
      "parent-heading",
      "parent-type",
      false,
      List.of(childItem)
    );

    final List<TableOfContentsItem> tableOfContents = List.of(parentItem);

    // When
    final List<TableOfContentsResponseSchema> response =
      TableOfContentsResponseMapper.fromTableOfContents(tableOfContents);

    // Then
    assertThat(response).hasSize(1);
    final TableOfContentsResponseSchema parentResponse = response.getFirst();
    assertThat(parentResponse.getId()).hasToString("parent-n1");
    assertThat(parentResponse.getMarker()).isEqualTo("parent-marker");
    assertThat(parentResponse.getHeading()).isEqualTo("parent-heading");
    assertThat(parentResponse.getType()).isEqualTo("parent-type");
    assertThat(parentResponse.getChildren()).hasSize(1);

    final TableOfContentsResponseSchema childResponse = parentResponse.getChildren().getFirst();
    assertThat(childResponse.getId()).hasToString("child-n1");
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
