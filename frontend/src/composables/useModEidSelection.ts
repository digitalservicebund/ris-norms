import { useMultiSelection } from "@/composables/useMultiSelection"
import { useModEidPathParameter } from "@/composables/useModEidPathParameter"
import { MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"

/**
 * Provides an array of selected akn:mod elements (identified by their eIds) and provides an event-handler for events that should impact this selection.
 * The selection is also synchronized with the respective path-parameter.
 *
 * @param modEIds Array of the eIds of all akn:mod elements that can be selected. Ordered in the same way as in the UI.
 */
export function useModEidSelection(modEIds: MaybeRefOrGetter<string[]>): {
  /**
   * The eIds of the currently selected akn:mod elements
   */
  values: Ref<string[]>
  /**
   * Deselect all currently selected akn:mod elements
   */
  deselectAll: () => void
  /**
   * Handle a click (or keyboard) event that should impact the selection of the given akn:mod element. Takes care of multiselect functionality when the meta or ctrl keys are pressed during the event.
   *
   * @param eid the eId of the akn:mod element that should be impacted by this event
   * @param originalEvent the original mouse or keyboard event
   */
  handleAknModClick: ({
    eid,
    originalEvent,
  }: {
    eid: string
    originalEvent: MouseEvent | KeyboardEvent
  }) => void
} {
  const { values, select, toggle, deselectAll, selectAll, clear } =
    useMultiSelection<string>()

  const lastClickedModEid = ref<string | null>(null)

  const modEidPathParameter = useModEidPathParameter()

  watch(
    modEidPathParameter,
    () => {
      if (modEidPathParameter.value !== "") {
        select(modEidPathParameter.value)
      }
    },
    { immediate: true },
  )

  watch(values, () => {
    if (values.value.length === 1) {
      modEidPathParameter.value = values.value[0]
    } else {
      modEidPathParameter.value = ""
    }
  })

  function handleAknModClick({
    eid,
    originalEvent,
  }: {
    eid: string
    originalEvent: MouseEvent | KeyboardEvent
  }) {
    const currentModEIds = toValue(modEIds)
    if (originalEvent.shiftKey) {
      const indexOfClickedMod = currentModEIds.indexOf(eid)
      const indexOfLastClickedMod = lastClickedModEid.value
        ? currentModEIds.indexOf(lastClickedModEid.value)
        : 0

      const eIdsOfRange =
        indexOfLastClickedMod < indexOfClickedMod
          ? currentModEIds.slice(indexOfLastClickedMod, indexOfClickedMod + 1)
          : currentModEIds.slice(indexOfClickedMod, indexOfLastClickedMod + 1)

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
    } else if (originalEvent.ctrlKey || originalEvent.metaKey) {
      toggle(eid)
    } else {
      clear()
      select(eid)
    }

    lastClickedModEid.value = eid
  }

  return {
    values,
    handleAknModClick,
    deselectAll: clear,
  }
}
