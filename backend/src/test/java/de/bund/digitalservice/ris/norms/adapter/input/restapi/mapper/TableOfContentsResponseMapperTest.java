package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TableOfContentsResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
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
      null,
      Collections.emptyList()
    );
    final TableOfContentsItem childItemWithStammform = new TableOfContentsItem(
      new EId("child-n2"),
      "child-marker",
      "child-stammform-heading",
      "child-type",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2024/109/2024-03-27/1/deu/2024-03-27/regelungstext-2.xml"
      ),
      Collections.emptyList()
    );
    final TableOfContentsItem parentItem = new TableOfContentsItem(
      new EId("parent-n1"),
      "parent-marker",
      "parent-heading",
      "parent-type",
      null,
      List.of(childItem, childItemWithStammform)
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
    assertThat(parentResponse.getEingebundeneStammformEli()).isNull();
    assertThat(parentResponse.getChildren()).hasSize(2);

    final TableOfContentsResponseSchema childResponse = parentResponse.getChildren().getFirst();
    assertThat(childResponse.getId()).hasToString("child-n1");
    assertThat(childResponse.getMarker()).isEqualTo("child-marker");
    assertThat(childResponse.getHeading()).isEqualTo("child-heading");
    assertThat(childResponse.getType()).isEqualTo("child-type");
    assertThat(childResponse.getEingebundeneStammformEli()).isNull();
    assertThat(childResponse.getChildren()).isEmpty();

    final TableOfContentsResponseSchema childWithStammformResponse = parentResponse
      .getChildren()
      .get(1);
    assertThat(childWithStammformResponse.getId()).hasToString("child-n2");
    assertThat(childWithStammformResponse.getMarker()).isEqualTo("child-marker");
    assertThat(childWithStammformResponse.getHeading()).isEqualTo("child-stammform-heading");
    assertThat(childWithStammformResponse.getType()).isEqualTo("child-type");
    assertThat(childWithStammformResponse.getEingebundeneStammformEli()).hasToString(
      "eli/bund/bgbl-1/2024/109/2024-03-27/1/deu/2024-03-27/regelungstext-2.xml"
    );
    assertThat(childWithStammformResponse.getChildren()).isEmpty();
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
