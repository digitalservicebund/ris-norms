import { getModEIds } from "@/services/ldmldeModService"
import { computed, ComputedRef, MaybeRefOrGetter, toValue } from "vue"

/**
 * Provides the classes for highlighting elements based on an associated temporal group.
 * These can be used in combination with the eIdClasses param of RisLawPreview.
 *
 * @param highlightElements the elements that should be highlighted together with the associated temporal group
 * @param isSelected function returning true when the element with the given eId is currently selected and false otherwise
 * @returns An object containing for each eId an array of classes to apply to the element of that eId
 */
export function useTimeBoundaryHighlightClasses(
  highlightElements: MaybeRefOrGetter<
    {
      eId: string
      temporalGroupEid?: string
    }[]
  >,
  isSelected: (eId: string) => boolean,
): ComputedRef<{ [eId: string]: string[] }> {
  const orderedTemporalGroupEIds = computed(() =>
    [
      ...new Set(
        toValue(highlightElements).map(
          ({ temporalGroupEid }) => temporalGroupEid,
        ),
      ),
    ].toSorted(
      (a, b) =>
        // sort by number part of the eId so eid-11 is after eid-3
        parseInt(a?.match(/\d+$/)?.[0] ?? "0") -
        parseInt(b?.match(/\d+$/)?.[0] ?? "0"),
    ),
  )

  /**
   * Find the color that should be used for the given time boundary.
   *
   * There are 10 numbered colors from 1 to 10 and a default color as a fallback, see tailwind.config.js
   *
   * @param eId the temporal group for which the color needs to be determined
   */
  function findColorIdForTimeBoundary(eId?: string) {
    if (eId == null) {
      return "default"
    }

    const temporalGroupIndex = orderedTemporalGroupEIds.value.findIndex(
      (temporalGroupEId) => temporalGroupEId === eId,
    )

    return temporalGroupIndex !== -1 && temporalGroupIndex < 10
      ? `${temporalGroupIndex + 1}`
      : "default"
  }

  return computed(() =>
    Object.fromEntries(
      toValue(highlightElements).map(({ eId, temporalGroupEid }) => {
        const colorId = findColorIdForTimeBoundary(temporalGroupEid)

        const classes = ["px-2", "outline-blue-800"]
        if (isSelected(eId)) {
          classes.push(
            `bg-highlight-${colorId}-selected`,
            "outline-2",
            "outline",
          )
        } else {
          classes.push(
            `bg-highlight-${colorId}-default`,
            `hover:bg-highlight-${colorId}-hover`,
            `focus:bg-highlight-${colorId}-hover`,
            "outline-dotted",
            "outline",
            "outline-1",
            "hover:outline-2",
          )
        }

        return [eId, classes]
      }),
    ),
  )
}

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

  const modsWithTimeBoundaries = computed(() =>
    modEIds.value.map((eId) => ({
      eId,
      temporalGroupEid: `eid-${Math.random().toFixed(1)}`,
    })),
  )

  return useTimeBoundaryHighlightClasses(modsWithTimeBoundaries, isSelected)
}
