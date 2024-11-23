package de.bund.digitalservice.ris.norms.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ChangelogTest {

  @Test
  void createsChangelogWithGivenDate() {
    final Changelog changelog = new Changelog();
    assertThat(changelog.getFileName()).isNotEmpty();
    assertThat(changelog.getFileName())
      .isEqualTo("changelogs/changelog-%s.json".formatted(LocalDate.now().toString()));
  }

  @Test
  void itReturnsEmptyContent() throws JsonProcessingException {
    final Changelog changelog = new Changelog();
    assertThat(changelog.getContent()).isEqualTo("{}");
  }

  @Test
  void itSetsContent() throws IOException {
    final Changelog changelog = new Changelog();
    assertThat(changelog.getContent()).isEqualTo("{}");
    changelog.setContent("{\"changed\":[\"eli/norm/to/add\"]}");
    assertThat(changelog.getContent()).isEqualTo("{\"changed\":[\"eli/norm/to/add\"]}");
  }

  @Test
  void itAddsContentToEmptyContent() throws JsonProcessingException {
    final Changelog changelog = new Changelog();
    assertThat(changelog.getContent()).isEqualTo("{}");
    changelog.addContent(Changelog.CHANGED, "eli/norm/to/add");
    changelog.addContent(Changelog.DELETED, "eli/norm/to/delete");
    assertThat(changelog.getContent())
      .isEqualTo("{\"deleted\":[\"eli/norm/to/delete\"],\"changed\":[\"eli/norm/to/add\"]}");
  }

  @Test
  void itAddsChangedToExistingChanged() throws IOException {
    final Changelog changelog = new Changelog();
    changelog.setContent("{\"changed\":[\"eli/norm/to/add\"]}");
    changelog.addContent(Changelog.CHANGED, "eli/another-norm/to/add");
    assertThat(changelog.getContent())
      .isEqualTo("{\"changed\":[\"eli/another-norm/to/add\",\"eli/norm/to/add\"]}");
  }

  @Test
  void itAddsDeletedBecauseNotFoundInChanged() throws IOException {
    final Changelog changelog = new Changelog();
    changelog.setContent("{\"changed\":[\"eli/norm/to/add\"]}");
    changelog.addContent(Changelog.DELETED, "eli/another-norm/to/add");
    assertThat(changelog.getContent())
      .isEqualTo("{\"deleted\":[\"eli/another-norm/to/add\"],\"changed\":[\"eli/norm/to/add\"]}");
  }

  @Test
  void itAddDeletedAndRemovesFromChanged() throws IOException {
    final Changelog changelog = new Changelog();
    changelog.setContent("{\"changed\":[\"eli/norm/to/add\"]}");
    changelog.addContent(Changelog.DELETED, "eli/norm/to/add");
    assertThat(changelog.getContent()).isEqualTo("{}");
  }

  @Test
  void itAddsToChangedAndRemovesFromDeleted() throws IOException {
    final Changelog changelog = new Changelog();
    changelog.setContent("{\"deleted\":[\"eli/norm/to/add\"]}");
    changelog.addContent(Changelog.CHANGED, "eli/norm/to/add");
    assertThat(changelog.getContent()).isEqualTo("{\"changed\":[\"eli/norm/to/add\"]}");
  }
}
