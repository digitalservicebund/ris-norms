import { useMultiSelection } from "@/composables/useMultiSelection"
import type { MaybeRefOrGetter, Ref } from "vue"
import { ref, toValue } from "vue"

/**
 * Provides an array of selected elements (identified by their eIds) and provides an event-handler for events that should impact this selection.
 *
 * @param eIds Array of the eIds of all elements that can be selected. Ordered in the same way as in the UI.
 */
export function useAknElementEidSelection(eIds: MaybeRefOrGetter<string[]>): {
  values: Ref<string[]>
  deselectAll: () => void
  selectAll: () => void
  select: (eId: string) => void
  handleAknElementClick: ({
    eid,
    originalEvent,
  }: {
    eid: string
    originalEvent: MouseEvent | KeyboardEvent
  }) => void
} {
  const { values, select, toggle, deselectAll, selectAll, clear } =
    useMultiSelection<string>()

  const lastClickedEId = ref<string | null>(null)

  function handleAknElementShiftClick({
    eid,
    originalEvent,
  }: {
    eid: string
    originalEvent: MouseEvent | KeyboardEvent
  }) {
    const currentEIds = toValue(eIds)

    const indexOfClickedElement = currentEIds.indexOf(eid)
    const indexOfLastClickedElement = lastClickedEId.value
      ? currentEIds.indexOf(lastClickedEId.value)
      : 0

    const eIdsOfRange =
      indexOfLastClickedElement < indexOfClickedElement
        ? currentEIds.slice(
            indexOfLastClickedElement,
            indexOfClickedElement + 1,
          )
        : currentEIds.slice(
            indexOfClickedElement,
            indexOfLastClickedElement + 1,
          )

    if (originalEvent.ctrlKey || originalEvent.metaKey) {
      if (values.value.includes(eid)) {
        deselectAll(eIdsOfRange)
      } else {
        selectAll(eIdsOfRange)
      }
    } else {
      clear()
      selectAll(eIdsOfRange)
    }

    // clear up selection created by shift click
    getSelection()?.removeAllRanges()
  }

  function handleAknElementClick({
    eid,
    originalEvent,
  }: {
    eid: string
    originalEvent: MouseEvent | KeyboardEvent
  }) {
    if (originalEvent.shiftKey) {
      handleAknElementShiftClick({ eid, originalEvent })
    } else if (originalEvent.ctrlKey || originalEvent.metaKey) {
      toggle(eid)
    } else {
      clear()
      select(eid)
    }

    lastClickedEId.value = eid
  }

  return {
    values,
    handleAknElementClick,
    deselectAll: clear,
    selectAll: () => selectAll(toValue(eIds) ?? []),
    select,
  }
}
