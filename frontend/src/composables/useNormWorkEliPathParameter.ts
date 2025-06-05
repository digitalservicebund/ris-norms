import type { ComputedRef } from "vue"
import { computed } from "vue"
import { useRoute } from "vue-router"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

/**
 * Creates a path pattern for route definition for a NormWorkEli.
 */
export function createNormWorkEliPathParameter(prefix?: string): string {
  const name = prefix ? `${prefix}Eli` : "eli"

  return [
    "eli",
    `:${name}Jurisdiction(bund)`,
    `:${name}Agent(bgbl-1|bgbl-2|banz-at)`,
    `:${name}Year([12][0-9]{3})`,
    `:${name}NaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)`,
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
