import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

/**
 * Returns a string containing a set of named path params that can be used
 * for creating a part of a route that specifies a Norm Expression ELI.
 *
 * Use in combination with `useNormExpressionEliPathParameter` to get the ELI from the
 * current route.
 *
 * @param prefix If provided, prefixes each named path param with the value.
 *  This allows you to have multiple ELIs in a single route.
 */
export function createNormExpressionEliPathParameter(prefix?: string) {
  const name = prefix ? `${prefix}Eli` : "eli"

  // The regular expressions for the parts of the ELI are based on the definitions
  // from LDML.de 1.7.2 for ELIs for "Verkündungsfassungen und Neufassungen". It was
  // not possible to put the ELI in just one combined path parameter as vue-router
  // replaces slashes in path parameters automatically by %2F when routing to a new
  // route.
  return [
    "eli",
    `:${name}Jurisdiction(bund)`,
    `:${name}Agent(bgbl-1|bgbl-2|banz-at)`,
    `:${name}Year([12][0-9]{3})`,
    `:${name}NaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)`,
    `:${name}PointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})`,
    `:${name}Version([0-9]+)`,
    `:${name}Language(deu)`,
  ].join("/")
}

/**
 * Provides a reference to the ELI of the current route. This is reconstructed
 * from the named path params created by `createNormExpressionEliPathParameter`.
 *
 * @param prefix If provided, only returns the path parameters that use the
 *  specified prefix. This allows you to get multiple ELIs from a single route.
 * @returns A reference to the ELI of the current route
 */
export function useNormExpressionEliPathParameter(
  prefix?: string,
): ComputedRef<NormExpressionEli> {
  const { params } = useRoute()
  const name = prefix ? `${prefix}Eli` : "eli"

  return computed(() => {
    const agent = params[`${name}Agent`]
    const year = params[`${name}Year`]
    const naturalIdentifier = params[`${name}NaturalIdentifier`]
    const pointInTime = params[`${name}PointInTime`]
    const version = params[`${name}Version`]
    const language = params[`${name}Language`]

    if (
      typeof agent != "string" ||
      typeof year != "string" ||
      typeof naturalIdentifier != "string" ||
      typeof pointInTime != "string" ||
      typeof version != "string" ||
      typeof language != "string"
    ) {
      throw new Error(
        `useNormExpressionEliPathParameter: You can only use this composable on pages which have a NormExpressionELI${prefix ? " prefixed with " + prefix : ""} in their route`,
      )
    }

    return new NormExpressionEli(
      agent,
      year,
      naturalIdentifier,
      pointInTime,
      parseInt(version, 10),
      language,
    )
  })
}
