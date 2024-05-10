/** Form field validation error. */
export type ValidationError = {
  /** Error code (intended for differentiating types in code). */
  code?: string

  /** Error message (intended for displaying to the user). */
  message: string

  /** Identifier that can be used for connecting the error with a UI control. */
  instance: string
}
