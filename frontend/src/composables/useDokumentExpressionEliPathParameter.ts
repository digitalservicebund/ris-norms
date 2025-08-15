import type { ComputedRef } from "vue"
import { computed } from "vue"
import { useRoute } from "vue-router"
import {
  DokumentExpressionEli,
  PATH_PARAMETER_SUBTYPE,
} from "@/lib/eli/DokumentExpressionEli"
import {
  PATH_PARAMETER_AGENT,
  PATH_PARAMETER_JURISDICTION,
  PATH_PARAMETER_NATURAL_IDENTIFIER,
  PATH_PARAMETER_YEAR,
} from "@/lib/eli/NormWorkEli"
import {
  PATH_PARAMETER_LANGUAGE,
  PATH_PARAMETER_POINT_IN_TIME,
  PATH_PARAMETER_VERSION,
} from "@/lib/eli/NormExpressionEli"

/**
 * Returns a string containing a set of named path params that can be used
 * for creating a part of a route that specifies a Dokument Expression ELI.
 *
 * Use in combination with `useDokumentExpressionEliPathParameter` to get the ELI from the
 * current route.
 *
 * @param prefix If provided, prefixes each named path param with the value.
 *  This allows you to have multiple ELIs in a single route.
 */
export function createDokumentExpressionEliPathParameter(prefix?: string) {
  const name = prefix ? `${prefix}Eli` : "eli"

  // The regular expressions for the parts of the ELI are based on the definitions
  // from LDML.de 1.8.2 for ELIs for "Verkündungsfassungen und Neufassungen". It was
  // not possible to put the ELI in just one combined path parameter as vue-router
  // replaces slashes in path parameters automatically by %2F when routing to a new
  // route.
  return [
    "eli",
    `:${name}${PATH_PARAMETER_JURISDICTION}`,
    `:${name}${PATH_PARAMETER_AGENT}`,
    `:${name}${PATH_PARAMETER_YEAR}`,
    `:${name}${PATH_PARAMETER_NATURAL_IDENTIFIER}`,
    `:${name}${PATH_PARAMETER_POINT_IN_TIME}`,
    `:${name}${PATH_PARAMETER_VERSION}`,
    `:${name}${PATH_PARAMETER_LANGUAGE}`,
    `:${name}${PATH_PARAMETER_SUBTYPE}`,
  ].join("/")
}

/**
 * Provides a reference to the ELI of the current route. This is reconstructed
 * from the named path params created by `createDokumentExpressionEliPathParameter`.
 *
 * @param prefix If provided, only returns the path parameters that use the
 *  specified prefix. This allows you to get multiple ELIs from a single route.
 * @returns A reference to the ELI of the current route
 */
export function useDokumentExpressionEliPathParameter(
  prefix?: string,
): ComputedRef<DokumentExpressionEli> {
  const route = useRoute()
  const name = prefix ? `${prefix}Eli` : "eli"

  return computed(() => {
    const agent = route.params[`${name}Agent`]
    const year = route.params[`${name}Year`]
    const naturalIdentifier = route.params[`${name}NaturalIdentifier`]
    const pointInTime = route.params[`${name}PointInTime`]
    const version = route.params[`${name}Version`]
    const language = route.params[`${name}Language`]
    const subtype = route.params[`${name}Subtype`]

    if (
      typeof agent != "string" ||
      typeof year != "string" ||
      typeof naturalIdentifier != "string" ||
      typeof pointInTime != "string" ||
      typeof version != "string" ||
      typeof language != "string" ||
      typeof subtype != "string"
    ) {
      throw new Error(
        `useDokumentExpressionEliPathParameter: You can only use this composable on pages which have a DokumentExpressionELI${prefix ? " prefixed with " + prefix : ""} in their route`,
      )
    }

    return new DokumentExpressionEli(
      agent,
      year,
      naturalIdentifier,
      pointInTime,
      parseInt(version, 10),
      language,
      subtype,
    )
  })
}
