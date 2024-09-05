/**
 * An error response returned by the API. This implements the error responses
 * ADR, please refer to doc/adr/0012-error-responses.md for more information.
 */
export type ErrorResponse<
  T extends keyof ErrorResponseDetail = "__fallback__",
  Children = never,
> = {
  /**
   * Type of the error.
   */
  type: T

  /**
   * Title of the error. This is intended for debugging purposes and should
   * not be parsed or displayed in the UI.
   */
  title?: string

  /**
   * Details of the error. This is intended for debugging purposes and should
   * not be parsed or displayed in the UI.
   */
  details?: string

  /**
   * Instance of the error. This can be used for matching errors to specific
   * locations in the UI (e.g. an invalid form field).
   */
  instance?: string

  /**
   * Nested errors. If an error response contains multiple messages (e.g.
   * for listing all fields that failed validation of a form), they will
   * appear here.
   */
  errors?: Array<Children>
} & {
  // Merge the shared properties of the error response with the extension fields
  // declared by the different error types (see below). Note that all extension
  // fields are made optional here to encourage defensive programming as we
  // cannot 100% guarantee all of them will always be available.
  [K in keyof ErrorResponseDetail[T]]?: ErrorResponseDetail[T][K]
}

/**
 * Specifies additional extension fields specific to each error type.
 *
 * All possible error types must be specified here in order to ensure type
 * safety for the different error types.
 *
 * If an error type does not provide any extension fields, set the value for that
 * type to `never`.
 */
export type ErrorResponseDetail = {
  // TODO: These are currently just examples for testing

  "/errors/norm-not-found": never

  "/errors/article-not-found": { eid: string }

  "/errors/article-of-type-not-found": { eli: string; type: string[] }

  /**
   * This will be used for any errors returned from the backend that haven't been
   * translated yet or don't receive any explicit handling in the frontend.
   */
  __fallback__: never
}

/**
 * Defines a configuration object that maps from an error response as returned by
 * the API to a human-readable message that can be displayed in the UI.
 *
 * This is a helper that guarantees (1) that any mapping is required to provide
 * information for all available error types, and (2) type-safety for functions
 * that generate messages.
 */
export type ErrorResponseMessageMapping = {
  [K in keyof ErrorResponseDetail]: (
    e: ErrorResponse<K>,
  ) => MappedErrorResponse | string
}

/**
 * The human-readable error message that should be displayed in the UI for
 * for each error type returned by the API.
 */
export type MappedErrorResponse = {
  /** Title of the error message. */
  title: string

  /** Optional additional detail information for the error message. */
  message?: string
}
