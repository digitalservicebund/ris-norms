package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

/** This exception indicates that there was a validation error. */
@Getter
public class ValidationException extends RuntimeException implements NormsAppException {

  private final ErrorType errorType;
  private final Pair<FieldName, String>[] fields;

  @SafeVarargs
  public ValidationException(final ErrorType errorType, final Pair<FieldName, String>... fields) {
    super(errorType.resolveMessage(Arrays.stream(fields).map(Pair::getRight).toList()));
    this.errorType = errorType;
    this.fields = fields;
  }

  /** List of all possible field names in validation errors. */
  @Getter
  public enum FieldName {
    CHARACTER_RANGE("characterRange"),
    EID("eId"),
    ELI("eli"),
    DESTINATION_HREF("destinationHref"),
    TARGET_NODE_EID("targetNodeEid"),
    TARGET_UPTO_NODE_EID("targetUpToNodeEid");

    private final String name;

    FieldName(final String name) {
      this.name = name;
    }
  }

  /** Error types for validation errors, including a message template */
  @Getter
  public enum ErrorType {
    ABSOLUTE_HREF_WITHOUT_ELI(
        "/errors/absolute-href-without-eli",
        "The provided Href with value %s does not contain the required 9 ELI parts."),
    META_MOD_NOT_FOUND(
        "/errors/meta-mod-not-found", "Did not find a textual mod with eId %s in the norm %s"),
    ELI_NOT_IN_HREF(
        "/errors/eli-not-in-href", "The destinationHref with value %s does not contain a eli"),
    SOURCE_HREF_IN_META_MOD_MISSING(
        "/errors/source-href-in-meta-mod-missing",
        "Did not find source href for textual mod with eId %s"),
    START_DATE_IN_META_MOD_MISSING(
        "/errors/start-date-in-meta-mod-missing",
        "Did not find a start date for textual mod with eId %s"),
    DESTINATION_ELIS_IN_META_MODS_NOT_CONSISTENT_WITH_BODY_MODS(
        "/errors/elis-not-consistent-between-meta-and-body",
        "For norm with Eli (%s): Eids are not consistent"),
    DESTINATION_ELIS_NOT_CONSISTENT(
        "/errors/destination-elis-not-consistent",
        "For norm with Eli (%s): Elis are not consistent"),
    TARGET_NODE_NOT_PRESENT(
        "/errors/target-node-not-present",
        "Target node with eid %s not present in ZF0 norm with eli %s."),
    TARGET_UPTO_NODE_NOT_PRESENT(
        "/errors/target-upto-node-not-present",
        "Target upTo node with eid %s not present in ZF0 norm with eli %s."),
    TARGET_AND_UPTO_NODES_NOT_SIBLINGS(
        "/errors/target-and-upto-nodes-not-siblings",
        "Target node with eid %s and target upTo node with eid %s are not siblings in ZF0 norm with eli %s."),
    TARGET_NODE_AFTER_UPTO_NODE(
        "/errors/target-node-before-upto-node",
        "Target node with eid %s does not appear before target upTo node with eid %s in ZF0 norm with eli %s."),
    CHARACTER_RANGE_NOT_PRESENT(
        "/errors/character-range-not-present",
        "In the destination href with value %s of passive mod with eId %s within ZF0 norm with eli %s, the character range not present."),
    CHARACTER_RANGE_INVALID_FORMAT(
        "/errors/character-range-with-invalid-format",
        "The character range %s of passive mod with eId %s within ZF0 norm with eli %s has invalid format."),
    CHARACTER_RANGE_NOT_RESOLVE_TARGET(
        "/errors/character-range-not-resolve-to-target",
        "The character range %s of passive mod with eId %s within ZF0 norm with eli %s does not resolve to the targeted text to be replaced."),
    CHARACTER_RANGE_NOT_WITHIN_NODE_RANGE(
        "/errors/character-range-not-within-node-range",
        "The character range %s of passive mod with eId %s within ZF0 norm with eli %s is not within the target node.");

    private final String type;
    private final String message;

    ErrorType(final String type, final String message) {
      this.type = type;
      this.message = message;
    }

    public String resolveMessage(final List<String> values) {
      return String.format(this.message, values.toArray());
    }
  }
}
