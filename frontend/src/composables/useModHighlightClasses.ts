import { getModEIds, getTimeBoundaryDate } from "@/services/ldmldeModService"
import { computed, ComputedRef, MaybeRefOrGetter, toValue } from "vue"
import sortBy from "lodash.sortby"
import uniqWith from "lodash.uniqwith"
import isEqual from "lodash.isequal"

/**
 * Provides the classes for highlighting the akn:mod elements of the given document.
 * These can be used in combination with the eIdClasses param of RisLawPreview.
 *
 * @param normDocument the norm whose akn:mod elements should be highlighted
 * @param isSelected function returning true when the mod of the given eId is currently selected and false otherwise
 * @returns An object containing for each eId an array of classes to apply to the element of that eId
 */
export function useModHighlightClasses(
  normDocument: MaybeRefOrGetter<Document | null>,
  isSelected: (modEId: string) => boolean,
): ComputedRef<{ [eId: string]: string[] }> {
  const modEIds = computed(() => {
    const doc = toValue(normDocument)

    if (!doc) {
      return []
    }

    return getModEIds(doc)
  })

  const modsWithTimeBoundaries = computed(() => {
    const doc = toValue(normDocument)

    if (!doc) {
      return []
    }

    return modEIds.value.map((eId) => ({
      eId,
      timeBoundary: getTimeBoundaryDate(doc, eId),
    }))
  })

  const timeBoundaries: ComputedRef<
    ({ date: string; temporalGroupEid: string } | null)[]
  > = computed(() =>
    sortBy(
      uniqWith(
        modsWithTimeBoundaries.value.map(({ timeBoundary }) => timeBoundary),
        isEqual,
      ),
      (timeBoundary) => timeBoundary?.date,
      (timeBoundary) => timeBoundary?.temporalGroupEid,
    ),
  )

  return computed(() =>
    Object.fromEntries(
      modsWithTimeBoundaries.value.map(({ eId, timeBoundary }) => {
        const timeBoundaryIndex = timeBoundaries.value.findIndex(
          (a) => a?.temporalGroupEid === timeBoundary?.temporalGroupEid,
        )
        const colorId =
          timeBoundaryIndex !== -1 && timeBoundaryIndex < 10
            ? `${timeBoundaryIndex + 1}`
            : "default"

        const classes = ["border", "px-2"]
        if (isSelected(eId)) {
          classes.push(
            `bg-highlight-mod-${colorId}-selected`,
            "border-solid",
            `border-highlight-mod-${colorId}-selected`,
          )
        } else {
          classes.push(
            `bg-highlight-mod-${colorId}-default`,
            `hover:bg-highlight-mod-${colorId}-hover`,
            `focus:bg-highlight-mod-${colorId}-hover`,
            "border-dotted",
            "border-gray-900",
          )
        }

        return [eId, classes]
      }),
    ),
  )
}
