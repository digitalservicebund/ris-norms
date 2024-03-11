import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"

/**
 * Returns a string containing a set of named path params that can be used
 * for creating a part of a route that specifies an ELI.
 *
 * Use in combination with `useEliPathParameter` to get the ELI from the
 * current route.
 *
 * @param prefix If provided, prefixes each named path param with the value.
 *  This allows you to have multiple ELIs in a single route.
 */
export function createEliPathParameter(prefix?: string) {
  const name = prefix ? `${prefix}Eli` : "eli"

  // The regular expressions for the parts of the ELI are based on the definitions
  // from LDML.de 1.6 for ELIs for "Verkündungsfassungen und Neufassungen". It was
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
    `:${name}Subtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)`,
  ].join("/")
}

/**
 * Provides a reference to the ELI of the current route. This is reconstructed
 * from the named path params created by `createEliPathParameter`.
 *
 * @param prefix If provided, only returns the path parameters that use the
 *  specified prefix. This allows you to get multiple ELIs from a single route.
 * @returns A reference to the ELI of the current route
 */
export function useEliPathParameter(prefix?: string): ComputedRef<string> {
  const { params } = useRoute()
  const name = prefix ? `${prefix}Eli` : "eli"

  const parameterNames = [
    `${name}Jurisdiction`,
    `${name}Agent`,
    `${name}Year`,
    `${name}NaturalIdentifier`,
    `${name}PointInTime`,
    `${name}Version`,
    `${name}Language`,
    `${name}Subtype`,
  ]

  return computed(() => {
    const parameterValues = parameterNames.map((name) => params[name])

    if (parameterValues.some((value) => value === undefined)) {
      throw new Error(
        `useEliPathParameter: You can only use this composable on pages which have an ELI${prefix ? " prefixed with " + prefix : ""} in their route`,
      )
    }

    return ["eli", ...parameterValues].join("/")
  })
}
