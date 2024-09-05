import {
  ErrorResponse,
  ErrorResponseMessageMapping,
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
  // eslint-disable-next-line @typescript-eslint/no-explicit-any -- Has to be any because fetch errors are always any
  e: any,
  mapping = errorMessages,
): e is ErrorResponse {
  return typeof e === "object" && e.type in mapping
}

/**
 * For any backend error response, maps the technical details of that response
 * to a human-readable message in the UI.
 *
 * @param e Response received from the backend
 * @param mapping Mapping to retrieve the messages from. The default mapping should
 *  already include everything you need, this is only intended for testing.
 * @returns
 */
export function mapErrorResponse(
  e: ErrorResponse,
  mapping = errorMessages,
): MappedErrorResponse {
  const mapper = mapping[e.type]
  let message = mapper?.(e) ?? mapping.__fallback__
  if (typeof message === "string") message = { title: message }
  return message
}

/* -------------------------------------------------- *
 * All error messages                                 *
 * -------------------------------------------------- */

const errorMessages: ErrorResponseMessageMapping = {
  // TODO: These are currently just examples for testing

  __fallback__: () => "Ein unbekannter Fehler ist aufgetreten.",

  "/errors/article-not-found": (e) => ({
    title: `Article with eID ${e.eid} not found`,
  }),

  "/errors/article-of-type-not-found": (e) =>
    `Norm with eli ${e.eli} does not include articles of type ${e.type?.join(",")}`,

  "/errors/norm-not-found": (e) => `Norm with ELI ${e.instance} not found`,
}
