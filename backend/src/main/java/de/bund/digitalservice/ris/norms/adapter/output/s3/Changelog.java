package de.bund.digitalservice.ris.norms.adapter.output.s3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * Class for holding a changelog data.
 */
public class Changelog {

  public static final String FILE_NAME_PREFFIX = "changelog-";
  public static final String FILE_NAME_SUFFIX = ".json";
  public static final Pattern FILE_NAME_PATTERN = Pattern.compile(
    FILE_NAME_PREFFIX + "\\d{4}-\\d{2}-\\d{2}" + FILE_NAME_SUFFIX
  );

  public static final String CHANGED = "changed";
  public static final String DELETED = "deleted";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final Map<String, Set<String>> content = new HashMap<>();

  @Getter
  private final String fileName;

  public Changelog() {
    this.fileName = FILE_NAME_PREFFIX + LocalDate.now() + FILE_NAME_SUFFIX;
  }

  /**
   * Retrieves the content of the changelog in string format
   * @return string formatted version of the changelog
   * @throws JsonProcessingException - if the parsing fails
   */
  public String getContent() throws JsonProcessingException {
    return objectMapper.writeValueAsString(content);
  }

  /**
   * Set the content of the changelog object using the input stream
   * @param changelogContent the content of the changelog
   * @throws IOException - if the conversion from stream to the map fails
   */
  public void setContent(final String changelogContent) throws IOException {
    final Map<String, List<String>> changelogListMap = objectMapper.readValue(
      changelogContent,
      new TypeReference<>() {}
    );
    changelogListMap.forEach((key, value) -> content.put(key, new HashSet<>(value)));
  }

  /**
   * Updates the content of the changelog with the passed information
   * @param operation - the operation run
   * @param normEli - the eli of the norm on which the operation was run
   */
  public void addContent(final String operation, final String normEli) {
    if (CHANGED.equalsIgnoreCase(operation)) {
      // If it's in "deleted", remove it from there
      content.getOrDefault(DELETED, new HashSet<>()).remove(normEli);
      // Add to "changed"
      content.computeIfAbsent(CHANGED, k -> new HashSet<>()).add(normEli);
    } else if (DELETED.equalsIgnoreCase(operation)) {
      final Set<String> changed = content.get(CHANGED);
      if (changed != null && changed.contains(normEli)) {
        // If it's in "changed", remove it from there
        changed.remove(normEli);
      } else {
        // If it's not in "changed", add it to deleted
        content.computeIfAbsent(DELETED, k -> new HashSet<>()).add(normEli);
      }
    }
    // Remove "changed" or "deleted" categories if empty
    content.entrySet().removeIf(entry -> entry.getValue().isEmpty());
  }
}
