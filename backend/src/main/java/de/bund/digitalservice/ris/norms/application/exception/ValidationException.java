package de.bund.digitalservice.ris.norms.application.exception;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

/** This exception indicates that there was a validation error. */
@Getter
public class ValidationException extends RuntimeException implements NormsAppException {

  private final ErrorType errorType;
  private final Pair<String, String>[] fields;

  @SafeVarargs
  public ValidationException(final ErrorType errorType, final Pair<String, String>... args) {
    super(errorType.resolveMessage(Arrays.stream(args).map(Pair::getRight).toList()));
    this.errorType = errorType;
    this.fields = args;
  }

  // TODO to remove, needed now for non-yet migrated ValidationException
  public ValidationException(final String message) {
    super(message);
    this.errorType = ErrorType.TARGET_NODE_NOT_PRESENT;
    this.fields = null;
  }

  /** Error types for validation errors, including a message template */
  @Getter
  public enum ErrorType {
    VALIDATION_ERROR("/errors/validation-error", "General validation error occurred."),
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

    public String resolveMessage(final List<String> args) {
      return String.format(this.message, args.toArray());
    }
  }
}
