import type { Norm } from "@/types/norm"
import type { ComputedRef, MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { RisZielnormenListItem } from "./RisZielnormenList.vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

/**
 * Takes a flat list of Norm expressions and groups them based on their work ELI.
 *
 * @param zielnormen List of expressions
 * @returns Grouped list
 */
export function useGroupedZielnormen(
  zielnormen: MaybeRefOrGetter<Norm[] | null>,
): ComputedRef<RisZielnormenListItem[]> {
  return computed(() => {
    const zielnormenVal = toValue(zielnormen)
    if (!zielnormenVal?.length) return []

    const groups = zielnormenVal
      .toSorted((a, b) => {
        const dateA = NormExpressionEli.fromString(a.eli).pointInTime
        const dateB = NormExpressionEli.fromString(b.eli).pointInTime
        return dateA.localeCompare(dateB)
      })
      .reduce((all, current) => {
        const eli = DokumentExpressionEli.fromString(current.eli)
          .asNormWorkEli()
          .toString()

        const group = all.get(eli) ?? {
          eli,
          title: current.title ?? "",
          fna: current.fna ?? "",
          expressions: [],
        }

        group.expressions.push(current)

        return all.set(eli, group)
      }, new Map<string, RisZielnormenListItem>())

    return Array.from(groups.values())
  })
}
