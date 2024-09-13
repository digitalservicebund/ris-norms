/**
 * An error response returned by the API. This implements the error responses
 * ADR, please refer to doc/adr/0012-error-responses.md for more information.
 */
export type ErrorResponse<ExtensionFields extends object = object> = {
  /**
   * Type of the error.
   */
  type: string

  /**
   * Title of the error. This is intended for debugging purposes and should
   * not be parsed or displayed in the UI.
   */
  title?: string

  /**
   * Details of the error. This is intended for debugging purposes and should
   * not be parsed or displayed in the UI.
   */
  detail?: string

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
  errors?: Array<ErrorResponse>
} & {
  // Merge the shared properties of the error response with the extension fields
  // declared by the different error types (see below). Note that all extension
  // fields are made optional here to encourage defensive programming as we
  // cannot 100% guarantee all of them will always be available.
  [K in keyof ExtensionFields]?: ExtensionFields[K]
}

/**
 * Defines a configuration object that maps from an error response as returned by
 * the API to a human-readable message that can be displayed in the UI.
 */
export type ErrorResponseMapping = {
  [key: string]: (e: ErrorResponse) => string | MappedErrorResponse
}

/**
 * The human-readable error message that should be displayed in the UI for
 * each error type returned by the API.
 */
export type MappedErrorResponse = {
  /** Title of the error message. */
  title: string

  /** Optional additional detail information for the error message. */
  message?: string
}
