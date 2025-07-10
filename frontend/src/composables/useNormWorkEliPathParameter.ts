import type { ComputedRef } from "vue"
import { computed } from "vue"
import { useRoute } from "vue-router"
import {
  NormWorkEli,
  PATH_PARAMETER_AGENT,
  PATH_PARAMETER_JURISDICTION,
  PATH_PARAMETER_NATURAL_IDENTIFIER,
  PATH_PARAMETER_YEAR,
} from "@/lib/eli/NormWorkEli"

/**
 * Creates a path pattern for route definition for a NormWorkEli.
 */
export function createNormWorkEliPathParameter(prefix?: string): string {
  const name = prefix ? `${prefix}Eli` : "eli"

  return [
    "eli",
    `:${name}${PATH_PARAMETER_JURISDICTION}`,
    `:${name}${PATH_PARAMETER_AGENT}`,
    `:${name}${PATH_PARAMETER_YEAR}`,
    `:${name}${PATH_PARAMETER_NATURAL_IDENTIFIER}`,
  ].join("/")
}

/**
 * Extracts a NormWorkEli from route parameters using optional prefix.
 */
export function useNormWorkEliPathParameter(
  prefix?: string,
): ComputedRef<NormWorkEli> {
  const { params } = useRoute()
  const name = prefix ? `${prefix}Eli` : "eli"

  return computed(() => {
    const agent = params[`${name}Agent`]
    const year = params[`${name}Year`]
    const naturalIdentifier = params[`${name}NaturalIdentifier`]

    if (
      typeof agent !== "string" ||
      typeof year !== "string" ||
      typeof naturalIdentifier !== "string"
    ) {
      throw new Error(
        `useNormWorkEliPathParameter: Missing required route parameters${prefix ? " for prefix " + prefix : ""}`,
      )
    }

    return new NormWorkEli(agent, year, naturalIdentifier)
  })
}
