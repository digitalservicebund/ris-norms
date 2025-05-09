import { getModEIds } from "@/lib/ldmldeMod"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import { keyBy, sortBy } from "lodash"
import type { ComputedRef, MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

type HighlightClasses = { selected: string[]; default: string[] }

type HighlightClassesMap = Record<number | "default", HighlightClasses>

// Color specific classes, we need to list them all as tailwind is otherwise
// unable to detect them
const highlightClassesMap: HighlightClassesMap = {
  1: {
    selected: ["bg-highlight-1-selected"],
    default: [
      "bg-highlight-1-default",
      "hover:bg-highlight-1-hover",
      "focus:bg-highlight-1-hover",
    ],
  },
  2: {
    selected: ["bg-highlight-2-selected"],
    default: [
      "bg-highlight-2-default",
      "hover:bg-highlight-2-hover",
      "focus:bg-highlight-2-hover",
    ],
  },
  3: {
    selected: ["bg-highlight-3-selected"],
    default: [
      "bg-highlight-3-default",
      "hover:bg-highlight-3-hover",
      "focus:bg-highlight-3-hover",
    ],
  },
  4: {
    selected: ["bg-highlight-4-selected"],
    default: [
      "bg-highlight-4-default",
      "hover:bg-highlight-4-hover",
      "focus:bg-highlight-4-hover",
    ],
  },
  5: {
    selected: ["bg-highlight-5-selected"],
    default: [
      "bg-highlight-5-default",
      "hover:bg-highlight-5-hover",
      "focus:bg-highlight-5-hover",
    ],
  },
  6: {
    selected: ["bg-highlight-6-selected"],
    default: [
      "bg-highlight-6-default",
      "hover:bg-highlight-6-hover",
      "focus:bg-highlight-6-hover",
    ],
  },
  7: {
    selected: ["bg-highlight-7-selected"],
    default: [
      "bg-highlight-7-default",
      "hover:bg-highlight-7-hover",
      "focus:bg-highlight-7-hover",
    ],
  },
  8: {
    selected: ["bg-highlight-8-selected"],
    default: [
      "bg-highlight-8-default",
      "hover:bg-highlight-8-hover",
      "focus:bg-highlight-8-hover",
    ],
  },
  9: {
    selected: ["bg-highlight-9-selected"],
    default: [
      "bg-highlight-9-default",
      "hover:bg-highlight-9-hover",
      "focus:bg-highlight-9-hover",
    ],
  },
  10: {
    selected: ["bg-highlight-10-selected"],
    default: [
      "bg-highlight-10-default",
      "hover:bg-highlight-10-hover",
      "focus:bg-highlight-10-hover",
    ],
  },
  default: {
    selected: ["bg-highlight-default-selected"],
    default: [
      "bg-highlight-default-default",
      "hover:bg-highlight-default-hover",
      "focus:bg-highlight-default-hover",
    ],
  },
} as const

export function getHighlightClasses(index: number): HighlightClasses {
  const id = index > -1 && index < 10 ? index + 1 : "default"
  return highlightClassesMap[id]
}

/**
 * Provides the classes for highlighting elements based on an associated Zeitgrenze.
 * These can be used in combination with the `eIdClasses` prop of `RisLawPreview`.
 *
 * @param highlightElements The elements that should be highlighted
 * @param isSelected function returning true when the element with the given eId
 *  is currently selected and false otherwise
 * @returns An object containing for each eId an array of classes to apply to
 *  the element of that eId
 */
export function useZeitgrenzenHighlightClasses(
  highlightElements: MaybeRefOrGetter<{ eId: string; geltungszeit?: string }[]>,
  isSelected: (eId: string) => boolean,
  zeitgrenzen: MaybeRefOrGetter<Zeitgrenze[]>,
): ComputedRef<{ [eId: string]: string[] }> {
  // Index of Zeitgrenzen, sorted by their date and put into a map with the ID as
  // the key for easier access later
  const zeitgrenzenById = computed<
    Record<string, Zeitgrenze & { index: number }>
  >(() => {
    const sortedZeitgrenzen = sortBy(toValue(zeitgrenzen) ?? [], "date").map(
      (zeitgrenze, i) => ({ ...zeitgrenze, index: i }),
    )

    return keyBy(sortedZeitgrenzen, "id")
  })

  function findColorsForZeitgrenze(id?: string): HighlightClasses {
    if (!id) return getHighlightClasses(-1)

    return getHighlightClasses(
      id in zeitgrenzenById.value ? zeitgrenzenById.value[id].index : -1,
    )
  }

  return computed(() =>
    Object.fromEntries(
      toValue(highlightElements).map(({ eId, geltungszeit }) => {
        const colors = findColorsForZeitgrenze(geltungszeit)
        const classes = ["px-2", "outline-blue-800"]

        if (isSelected(eId)) {
          classes.push(...colors.selected, "outline-2", "outline")
        } else {
          classes.push(
            ...colors.default,
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
 * @param isSelected function returning true when the mod of the given eId is
 *  currently selected and false otherwise
 * @returns An object containing for each eId an array of classes to apply to
 *  the element of that eId
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

  // Note: Due to the empty Zeitgrenzen array, this will currently always return an
  // empty list of classes. Leaving it broken since we don't currently use the views
  // that rely on this, and will most likely remove them soon.
  return useZeitgrenzenHighlightClasses(modsWithTimeBoundaries, isSelected, [])
}
