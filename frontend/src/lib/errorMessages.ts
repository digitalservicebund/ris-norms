import { ErrorResponse, ErrorResponseMapping } from "@/types/errorResponse"

export const errorMessages = {
  // TODO: These are currently just examples for testing

  __fallback__: () => "Ein unbekannter Fehler ist aufgetreten.",

  "/errors/article-not-found": (e: ErrorResponse<{ eid: string }>) => ({
    title: `Article with eID ${e.eid} not found`,
  }),

  "/errors/article-of-type-not-found": (
    e: ErrorResponse<{ eli: string; types: string[] }>,
  ) =>
    `Norm with eli ${e.eli} does not include articles of type ${e.types?.join(",")}`,

  "/errors/norm-not-found": (e: ErrorResponse) =>
    `Norm with ELI ${e.instance} not found`,
} satisfies ErrorResponseMapping
