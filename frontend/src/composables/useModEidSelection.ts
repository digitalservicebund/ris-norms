import { useMultiSelection } from "@/composables/useMultiSelection"
import { useModEidPathParameter } from "@/composables/useModEidPathParameter"
import { Ref, watch } from "vue"

/**
 * Provides an array of selected akn:mod elements (identified by their eIds) and provides an event-handler for events that should impact this selection.
 * The selection is also synchronized with the respective path-parameter.
 */
export function useModEidSelection(): {
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
  const { values, select, toggle, deselectAll } = useMultiSelection<string>()

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
    if (originalEvent.ctrlKey || originalEvent.metaKey) {
      toggle(eid)
    } else {
      deselectAll()
      select(eid)
    }
  }

  return {
    values,
    handleAknModClick,
    deselectAll,
  }
}
