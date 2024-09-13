import { errorMessages } from "@/lib/errorMessages"
import {
  ErrorResponse,
  ErrorResponseMapping,
  MappedErrorResponse,
} from "@/types/errorResponse"

/**
 * Determines whether the parameter is an error response. Will return true
 * if mapping for the error type exists. Note that this will not check if
 * the extension properties are available for that type as that would make
 * the check very complicated.
 *
 * @param e Candidate for a possible error response
 * @param mapping Mapping to check the error response. The default mapping
 *  should already include everything you need, this is only intended for
 *  testing.
 * @returns Whether the candidate is an error response
 */
export function isErrorResponse(
  e: any, // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any
): e is ErrorResponse {
  return typeof e === "object" && e.type in errorMessages
}

/**
 * Returns a fallback error message. Useful when you want to handle all errors
 * as if they were returned from the API, even if they may come from different
 * sources or have a different format.
 */
export function getFallbackError(): ErrorResponse {
  return { type: "__fallback__" }
}

/**
 * For any backend error response, maps the technical details of that response
 * to a human-readable message in the UI.
 *
 * @param e Response received from the backend
 * @param mapping Mapping to retrieve the messages from. The default mapping should
 *  already include everything you need, this is only intended for testing.
 * @returns Mapped response. This will always have a value, even if the type of the
 *  input resoponse is unknown. In that case, a fallback is returned.
 */
export function mapErrorResponse(e: ErrorResponse): MappedErrorResponse {
  const mapper = (errorMessages as ErrorResponseMapping)[e.type]
  let message = mapper?.(e) ?? errorMessages.__fallback__()
  if (typeof message === "string") message = { title: message }
  return message
}
